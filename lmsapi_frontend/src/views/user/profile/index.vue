<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div>
            <div class="text-center">
              <userAvatar :user="user" />
            </div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <svg-icon icon-class="user" />账号
                <div class="pull-right">{{ user.username }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="user" />用户昵称
                <div class="pull-right">{{ user.nickname }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="email" />用户邮箱
                <div class="pull-right">{{ user.email }}</div>
              </li>
              <li class="list-group-item">
                accessKey
                <div class="pull-right">{{ user.accessKey }}</div>
                <i class="el-icon-document" v-clipboard:copy="user.accessKey" v-clipboard:success="onCopy"></i>
              </li>
              <li class="list-group-item">
                secretKey
                <div class="pull-right">{{ user.secretKey }}</div>
              <i class="el-icon-document" v-clipboard:copy="user.secretKey" v-clipboard:success="onCopy"></i>

              </li>

              <li class="list-group-item">
                <svg-icon icon-class="date" />创建日期
                <div class="pull-right">{{ user.createTime }}</div>
              </li>

              <li class="list-group-item">
                <el-button type="text" @click="handleSendEmail(user.email)" >刷新AKSK</el-button>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :span="18" :xs="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span>基本资料</span>
          </div>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="基本资料" name="userinfo">
              <userInfo :user="user" />
            </el-tab-pane>
            <el-tab-pane label="修改密码" name="resetPwd">
              <resetPwd :user="user" />
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>

    <!-- 发送邮件 -->
    <el-dialog title="发送验证码" :visible="dialogSendEmailVisiable">


    	<el-form ref="sendEmailInfo" :model="sendEmailInfo" :rules="sendEmailRule">

    		<el-form-item prop="email" >
          	<div class="check-code-panel">
              		<el-input ref="email" v-model="user.email" disabled></el-input>
                  <el-button type="text"  class="check-code" @click="doSendEmail()" >发送</el-button>
            </div>

    		</el-form-item>

    	<el-form-item prop="code">
    		<div class="check-code-panel">
    			<el-input ref="code"   placeholder="请输入校验码" v-model="sendEmailInfo.code">
    			</el-input>
    			<img :src="sendCheckCodeUrl" class="check-code" @click="changeCheckCode(1)" />
    		</div>

    	</el-form-item>

      <el-form-item prop="emailCode">
      	<div class="check-code-panel">
      		<el-input ref="emailCode" size="large" placeholder="请输入邮箱验证码" v-model="sendEmailCode">
      		</el-input>
      	</div>

      </el-form-item>

    	</el-form>


    	<div slot="footer" class="dialog-footer">
    		<el-button @click="closeSendEmail()">取 消</el-button>
    		<el-button type="primary" @click="doRefreshAkSk">刷新</el-button>
    	</div>
    </el-dialog>



  </div>
</template>

<script>
import userAvatar from "./userAvatar";
import userInfo from "./userInfo";
import resetPwd from "./resetPwd";
import { getInfo,sendEmail,changeAkAndSk } from "../../../api/user.js";

export default {
  name: "profile",
  components: { userAvatar, userInfo, resetPwd },
  data() {
    const validateCode = (rule, value, callback) => {
    	if (value.length < 5||value.length>5) {
    		callback(new Error('请输入5位的校验码'))
    	} else {
    		callback()
    	}
    }

    const validateEmail = (rule, value, callback) => {
    	if (value.length < 5) {
    		callback(new Error('请输入正确的email'))
    	} else {
    		callback()
    	}
    }
    const validateEmailCode = (rule, value, callback) => {
    	if (value.length < 5) {
    		callback(new Error('请输入正确的邮箱验证码'))
    	} else {
    		callback()
    	}
    }
    return {
      user: {},
      sendEmailInfo: {
      	email: '',
      	code: '',
      	type: 1
      },
      sendEmailRule: {
      	email: [{
      		required: true,
      		trigger: 'blur',
      		validator: validateEmail
      	}],
      	code: [{
      		required: true,
      		trigger: 'blur',
      		validator: validateCode
      	}]
      },
      dialogSendEmailVisiable: false,
      sendCheckCodeUrl:'',
      sendEmailCode:'',
      checkCodeUrl: "http://localhost:9000/lmsapi/user/checkCode",
      roleGroup: {},
      postGroup: {},
      activeTab: "userinfo"
    };
  },
  created() {
    this.getUser();
  },
  methods: {
    doRefreshAkSk(){

      if(this.code==''){
        this.$message.warning("请输入邮箱校验码!");
      }
      changeAkAndSk(this.sendEmailCode).then(res=>{
        if(res.code==20000){
          this.$message.success("刷新成功!");
        }else{
          	this.$message.warning(res.msg);
        }
      })
       this.closeSendEmail()
    },
    //显示发送验证码框
    handleSendEmail(email) {
    	console.log(email)
    	if (email == undefined || email == '') {
    		console.log(email)
    		this.$message.warning("请输入正确的邮箱!");
    		return;
    	}
    	this.sendEmailInfo.email = email;
    	//type=1表示邮箱校验码
    	this.changeCheckCode(1)
    	this.dialogSendEmailVisiable = true;
    },
    closeSendEmail() {
    	this.dialogSendEmailVisiable = false;
    	//type回到0
    	this.changeCheckCode(0)
    	this.sendEmailInfo.code = '';
    },	//显示发送验证码框
			handleSendEmail(email) {
				console.log(email)
				if (email == undefined || email == '') {
					console.log(email)
					this.$message.warning("请输入正确的邮箱!");
					return;
				}
				this.sendEmailInfo.email = email;
				//type=1表示邮箱校验码
				this.changeCheckCode(1)
				this.dialogSendEmailVisiable = true;
			},
			closeSendEmail() {
				this.dialogSendEmailVisiable = false;
				//type回到0
				this.changeCheckCode(0)
				this.sendEmailInfo.code = '';
			},
    doSendEmail() {
    	this.$refs.sendEmailInfo.validate(valid => {
    		if (valid) {
    			sendEmail(this.sendEmailInfo).then(res => {
    				if (res.code == 20000) {
    					  this.$message.success("校验码已发送到"+this.sendEmailInfo.email);

    				} else {
    					this.changeCheckCode(1)
              this.sendEmailInfo.code=''
    					this.$message.error(res.msg);
    				}
    			}).catch(error => {
            console.log(error)
    				this.$message.error("发送邮件失败!");
            this.changeCheckCode(1)
            this.sendEmailInfo.code=''
    			})

    		}

    	})
    },
    changeCheckCode(type) {
    	console.log(this.sendCheckCodeUrl)
    	this.sendCheckCodeUrl = this.checkCodeUrl + "?type=" + type+"&time=" + new Date().getTime();
    },
    onCopy() {
    	this.$message({
    		type: 'success',
    		message: '复制成功!'
    	});
    	this.closeShareLinkDialog()
    },
    getUser() {
      getInfo().then(response => {
        this.user = response.data;
        // this.roleGroup = response.roleGroup;
        // this.postGroup = response.postGroup;
      });
    }
  }
};
</script>
<style scoped>


  .check-code-panel {
  	width: 100%;
  	display: flex;

  	.check-code {
  		margin-left: 5px;
  		cursor: pointer;
  	}
  }
  .check-code-panel .el-input{
    width: 250x;
  }

</style>
