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
                <svg-icon icon-class="date" />创建日期
                <div class="pull-right">{{ user.createTime }}</div>
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





  </div>
</template>

<script>
import userAvatar from "./userAvatar";
import userInfo from "./userInfo";
import resetPwd from "./resetPwd";
import { getInfo,sendEmail } from "../../../api/user.js";

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
      roleGroup: {},
      postGroup: {},
      activeTab: "userinfo"
    };
  },
  created() {
    this.getUser();
  },
  methods: {
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
