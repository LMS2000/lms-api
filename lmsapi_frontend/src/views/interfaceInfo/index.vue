<template>
  <div class="app-container">
    <el-row :gutter="20">

      <!--用户数据-->
      <el-col>
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
          label-width="68px">
          <el-form-item label="接口名字" prop="name">
            <el-input v-model="queryParams.name" placeholder="请输入接口名字" clearable style="width: 240px"
              @keyup.enter.native="handleQuery" />
          </el-form-item>

          <el-form-item label="状态" prop="method">
            <el-select v-model="queryParams.method" placeholder="请求方法" clearable style="width: 240px">
              <el-option key="0" label="GET" value="0"></el-option>
              <el-option key="1" label="POST" value="1"></el-option>
              <el-option key="2" label="PUT" value="2"></el-option>
              <el-option key="3" label="DELETE" value="3"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>



        <el-table v-loading="loading" :data="interfaceList" @selection-change="handleSelectionChange">


          <el-table-column label="接口名字" align="center" key="name" prop="name" :show-overflow-tooltip="true" />
          <el-table-column label="描述" align="center" key="description" prop="description"
            :show-overflow-tooltip="true" />

          <el-table-column />
          <el-table-column />

          <el-table-column />
          <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleToTry(scope.row)">查看</el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleToBuy(scope.row)">购买</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />

      </el-col>
    </el-row>




  </div>
</template>

<script>
  import {
    pageOnlineInterfaceInfo,
  } from '../../api/interfaceInfo.js'
  import {
    addUserInterface
  } from '../../api/userInterfaceInfo.js'
  export default {
    name: "interfaceInfo",
    data() {
      return {
        //每隔20%显示一个label
        sliderStep: 20,
        //开启显示标签
        showTooltip: true,
        sliderMarks: {
          // 0: '0',
          // 20: '20%',
          // 40: '40%',
          // 60: '60%',
          // 80: '80%',
          // 100: '100'
        },
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 用户表格数据
        interfaceList: null,
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        //最大用户使用配额
        maxQuota: undefined,
        // 表单参数
        form: {},
        defaultProps: {
          children: "children",
          label: "label"
        },
        // // 用户导入参数
        // upload: {
        //   // 是否显示弹出层（用户导入）
        //   open: false,
        //   // 弹出层标题（用户导入）
        //   title: "",
        //   // 是否禁用上传
        //   isUploading: false,
        //   // 是否更新已经存在的用户数据
        //   updateSupport: 0,
        //   // 设置上传的请求头部
        //   headers: { Authorization: "Bearer " + getToken() },
        //   // 上传的地址
        //   url: process.env.VUE_APP_BASE_API + "/system/user/importData"
        // },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          name: undefined,
          method: undefined
        },
        // 表单校验
        rules: {
          name: [{
              required: true,
              message: "接口名称不能为空",
              trigger: "blur"
            },
            {
              min: 2,
              max: 20,
              message: '接口名称长度必须介于 2 和 20 之间',
              trigger: 'blur'
            }
          ],
          url: [{
            required: true,
            message: "接口地址不能为空",
            trigger: "blur"
          }],
          email: [{
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }],
          phonenumber: [{
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }]
        }
      };
    },
    //创建时获取用户列表
    created() {

      this.getList();
    },

    methods: {

      handleToBuy(row) {
        this.$confirm('确认要购买"' + row.name   +'"这个接口吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'info'
        }).then(() => {
          // 用户点击了确定按钮，执行操作
          addUserInterface(row.id).then(res => {
             if(res.code==20000){
                 this.$modal.msgSuccess("购买成功");
             }else{
               this.$modal.msgError(res.msg);
             }
          })

        }).catch(() => {
          // 用户点击了取消按钮，取消操作
          this.$modal.msgSuccess(text + "取消操作");
          // 执行其他操作...
        });
      },
      handleToTry(row) {
        this.$router.push("/mode/test/" + row.id);
      },

      //获取接口列表
      getList() {
        this.loading = true;
        //获取接口列表
        pageOnlineInterfaceInfo(this.queryParams).then(res => {
          if (res.code == 20000) {
            this.interfaceList = res.data.records;
            console.log(res.data)
            this.total = res.data.total;
            this.loading = false;
          }
        }).catch(error => {
          console.log(error)
        })
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.dateRange = [];
        this.resetForm("queryForm");
        // this.$refs.tree.setCurrentKey(null);
        this.handleQuery();
      },
      // 表单重置
      reset() {
        this.form = {
          id: undefined,
          name: undefined,
          method: undefined,
          url: undefined,
          description: undefined,
          status: 0,
          requestParams: undefined,
          requestHeader: undefined,
          responseHeader: undefined,
          remark: undefined,
        };
        this.resetForm("form");
      },


      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id);
        this.single = selection.length != 1;
        this.multiple = !selection.length;
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },


    }



  }
</script>

<style>
  .el-slider__runway {
    width: 350px;
    /* 设置滑块的宽度 */
  }
</style>
