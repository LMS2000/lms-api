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

          <el-form-item label="状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="接口状态" clearable style="width: 240px">
              <el-option key="0" label="使用中" value="0"></el-option>
              <el-option key="1" label="已停用" value="1"></el-option>

            </el-select>
          </el-form-item>
          <el-form-item label="请求方法" prop="method">
            <el-select v-model="queryParams.method" placeholder="接口状态" clearable style="width: 240px">
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

        <el-row class="mb8">


          <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete">
              删除</el-button>
          </el-col>

        </el-row>

        <el-table v-loading="loading" :data="userInterfaceList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="编号" align="center" key="id" prop="id" />
          <el-table-column label="接口名字" align="center" prop="name" width="160">
            <template slot-scope="scope">
              <span>{{ scope.row.interfaceInfoVo.name }}</span>
            </template>
          </el-table-column>
          <el-table-column label="接口地址" align="center" prop="url" width="160">
            <template slot-scope="scope">
              <span>{{ scope.row.interfaceInfoVo.url }}</span>
            </template>
          </el-table-column>
          <el-table-column label="请求方法" align="center" key="method" prop="method">

            <template slot-scope="scope">
              <el-tag v-if="scope.row.interfaceInfoVo.method==0" type="success">GET</el-tag>
              <el-tag v-else-if="scope.row.interfaceInfoVo.method==1" type="info">POST</el-tag>
              <el-tag v-else-if="scope.row.interfaceInfoVo.method==2" type="warning">PUT</el-tag>
              <el-tag v-else-if="scope.row.interfaceInfoVo.method==3" type="danger">DELETE</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="使用用户" align="center" prop="username" width="160">
            <template slot-scope="scope">
              <span>{{ scope.row.userVo.username }}</span>
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" key="status">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.status" :active-value="0" :inactive-value="1"
                @input="handleStatusChange(scope.row)"></el-switch>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime" width="160">
            <template slot-scope="scope">
              <span>{{ scope.row.createTime }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
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
    pageUserInterfaceList,
    pageCurrentUserInterfaceList,
    changeUserInterface,
    deleteUserInterfaceInfos
  } from '../../api/userInterfaceInfo.js'

  export default {
    name: "User",
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
        userInterfaceList: null,
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 表单参数
        form: {},
        defaultProps: {
          children: "children",
          label: "label"
        },

        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          method: undefined,
          name: undefined,
          status: undefined
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


      //获取接口列表
      getList() {
        this.loading = true;
        //获取接口列表
        pageUserInterfaceList(this.queryParams).then(res => {
          if (res.code == 20000) {
            this.userInterfaceList = res.data.records;
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


      /** 删除按钮操作 */
      handleDelete(row) {
        const arr = row.id || this.ids;

        console.log(row)
        this.$modal.confirm('是否确认删除接口编号为"' + arr + '"的数据项？').then(function() {
          if (typeof arr === "number") {
            const interfaceIds = [arr];
            return deleteUserInterfaceInfos(interfaceIds);
          }
          const interfaceIds = arr;
          return deleteUserInterfaceInfos(interfaceIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      // 接口状态修改
      handleStatusChange(row) {
        let text = row.status === 0 ? "上线" : "关闭";

        this.$confirm('确认要"' + text + '""' + row.interfaceInfoVo.name + '"这个接口吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 用户点击了确定按钮，执行操作

          changeUserInterface(row.id, row.status).then(res => {
            if (res.code == 20000) {

              this.$modal.msgSuccess(text + "成功");
            }
          }).catch(error => {
            this.$modal.msgError(error);
          })
          // 执行其他操作...
        }).catch(() => {
          // 用户点击了取消按钮，取消操作
          this.$modal.msgSuccess(text + "取消操作");
          row.status = row.status === 0 ? 1 : 0;
          // 执行其他操作...
        });


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
