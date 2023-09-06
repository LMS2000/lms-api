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
          <!--  <el-form-item label="手机号码" prop="phonenumber">
            <el-input
              v-model="queryParams.phonenumber"
              placeholder="请输入手机号码"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item> -->
          <el-form-item label="状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="接口状态" clearable style="width: 240px">
              <el-option key="0" label="使用中" value="0"></el-option>
              <el-option key="1" label="已停用" value="1"></el-option>
            </el-select>
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

        <el-row class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate">修改
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete">
              删除</el-button>
          </el-col>

        </el-row>

        <el-table v-loading="loading" :data="interfaceList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="接口编号" align="center" key="id" prop="id" />
          <el-table-column label="接口名字" align="center" key="name" prop="name" :show-overflow-tooltip="true" />
          <el-table-column label="接口地址" align="center" key="url" prop="url" :show-overflow-tooltip="true" />
          <el-table-column label="请求方法" align="center" key="method" prop="method">

            <template slot-scope="scope">
              <el-tag v-if="scope.row.method==0" type="success">GET</el-tag>
              <el-tag v-else-if="scope.row.method==1" type="info">POST</el-tag>
              <el-tag v-else-if="scope.row.method==2" type="warning">PUT</el-tag>
              <el-tag v-else-if="scope.row.method==3" type="danger">DELETE</el-tag>
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
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />

      </el-col>
    </el-row>

    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-row>
          <el-col :span="12">
            <el-form-item v-if="form.id == undefined" label="接口名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入接口名称" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.id == undefined" label="接口地址" prop="url">
              <el-input v-model="form.url" placeholder="请输入接口地址" maxlength="20" />
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="请求方法" prop="method">
              <el-select v-model="form.method" placeholder="请求方法" clearable style="width: 240px">
                <el-option key="0" label="GET" value="0"></el-option>
                <el-option key="1" label="POST" value="1"></el-option>
                <el-option key="2" label="PUT" value="2"></el-option>
                <el-option key="3" label="DELETE" value="3"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio :key="0" :label="0">
                  上线
                </el-radio>
                <el-radio :key="1" :label="1">
                  关闭
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="接口描述" prop="description">
              <el-input v-model="form.description" placeholder="请输入接口描述" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="请求参数" prop="requestParams">
              <el-input v-model="form.requestParams" placeholder="请输入请求参数" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="请求头" prop="requestHeader">
              <el-input v-model="form.requestHeader" placeholder="请输入请求头" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="响应头" prop="responseHeader">
              <el-input v-model="form.responseHeader" placeholder="请输入响应头" maxlength="50" />
            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import {
    pageInterfaceList,
    updateInterfaceInfo,
    saveInterfaceInfo,
    deleteInterfaceInfos,
    getInterfaceInfo,
    changeInterface
  } from '../../api/interfaceInfo.js'

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
        pageInterfaceList(this.queryParams).then(res => {
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
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加接口";


      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        this.open = true;

        const id = row.id || this.ids;
        getInterfaceInfo(id).then(res => {
          this.form = res.data
        })
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != undefined) {
              updateInterfaceInfo(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              saveInterfaceInfo(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const arr = row.uid || this.ids;

        console.log(row)
        this.$modal.confirm('是否确认删除接口编号为"' + arr + '"的数据项？').then(function() {
          if (typeof arr === "number") {
            const userIds = [arr];
            return deleteInterfaceInfos(userIds);
          }
          const userIds = arr;
          return deleteInterfaceInfos(userIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      // 接口状态修改
      handleStatusChange(row) {
        let text = row.status === 0 ? "上线" : "关闭";

        this.$confirm('确认要"' + text + '""' + row.name + '"这个接口吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 用户点击了确定按钮，执行操作

          changeInterface(row.id, row.status).then(res => {
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
