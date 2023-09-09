<template>
  <div>

    <div class="card">
      <div class="card-title">接口信息</div>
      <div class="card-item">
        <label>接口名称:</label>
        <span>{{ interfaceInfo.name}}</span>
      </div>
      <div class="card-item">
        <label>接口地址:</label>
        <span>{{ interfaceInfo.url}}</span>
      </div>

      <div class="card-item">
        <label>请求方法:</label>
        <el-tag v-if="interfaceInfo.method === '0'" type="success">GET</el-tag>
        <el-tag v-else-if="interfaceInfo.method === '1'" type="primary">POST</el-tag>
        <el-tag v-else-if="interfaceInfo.method === '2'" type="warning">PUT</el-tag>
        <el-tag v-else-if="interfaceInfo.method === '3'" type="danger">DELETE</el-tag>
      </div>

      <div class="card-item">
        <label>请求头:</label>
        <span>{{ interfaceInfo.requestHeader}}</span>
      </div>
      <div class="card-item">
        <label>请求参数:</label>
        <span>{{ interfaceInfo.requestParams}}</span>
      </div>
      <div class="card-item">
        <label>响应头:</label>
        <span>{{ interfaceInfo.responseHeader}}</span>
      </div>
    </div>

    <div class="card">
      <div class="card-title">在线调试</div>
      <div class="card-item">
        <label>请求参数</label>
        <el-input type="textarea" v-model="invokeData.requestParams"></el-input>
      </div>
      <div class="card-item">
        <el-button @click="handleInvoke">调用</el-button>
        <el-button @click="invokeData.requestParams=''">清除</el-button>
      </div>
    </div>

    <div class="card">
      <div class="card-title">调用结果</div>
      <div class="card-item">
        <el-input type="textarea" disabled v-model="resultData"></el-input>
      </div>
    </div>

  </div>

</template>

<script>
  import {
    getInterfaceById,
    invoketestRequest
  } from '../../api/interfaceInfo.js'

  export default {
    name: "mode",
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
        // 接口信息
        interfaceInfo: null,
        invokeData: {
          id: '',
          requestParams: ''
        },
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        //最大用户使用配额
        maxQuota: undefined,
        resultData: '',
        // 表单参数
        form: {},
        defaultProps: {
          children: "children",
          label: "label"
        },
      };
    },
    created() {
      const id = this.$route.params && this.$route.params.id;
      this.invokeData.id = id;
      this.getList(id);
    },
    methods: {


      //获取接口列表
      getList(id) {

        //获取接口列表
        getInterfaceById(id).then(res => {
          if (res.code == 20000) {
            this.interfaceInfo = res.data;


          }
        }).catch(error => {
          console.log(error)
        })
      },

      handleInvoke() {

        invoketestRequest(this.invokeData).then(res => {
          if (res.code == 20000) {
            this.resultData = res.data;
          }
        })
      }
    }
  }
</script>

<style>
  .card {
    border: 1px solid #ccc;
    border-radius: 4px;
    padding: 20px;
    margin-bottom: 20px;
    background-color: #fff;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    margin-left: 10px;
    /* 左边距 */
    margin-top: 10px;
    /* 上边距 */
  }

  .card-title {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 20px;
  }

  .card-item {
    margin-bottom: 15px;

  }

  .card-item label {
    font-weight: normal;
    margin-right: 5px;
  }

  .card-item label::after {
    content: '\00a0\00a0\00a0';
    /* Unicode 编码中的空格字符 */
  }
</style>
