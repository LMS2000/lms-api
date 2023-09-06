<template>
	<div class="login">



		<!-- 发送邮件 -->
		<el-dialog title="发送验证码" :visible="dialogSendEmailVisiable">


			<el-form ref="sendEmailInfo" :model="sendEmailInfo" :rules="sendEmailRule">

				<el-form-item prop="email" label-width="100px">
					<el-input ref="email" v-model="sendEmailInfo.email" disabled></el-input>
				</el-form-item>

			<el-form-item prop="code">
				<div class="check-code-panel">
					<el-input ref="code" size="large" placeholder="请输入验证码" v-model="sendEmailInfo.code">
					</el-input>
					<img :src="sendCheckCodeUrl" class="check-code" @click="changeCheckCode(1)" />
				</div>

			</el-form-item>
			</el-form>


			<div slot="footer" class="dialog-footer">
				<el-button @click="closeSendEmail()">取 消</el-button>
				<el-button type="primary" @click="doSendEmail()">发 送</el-button>
			</div>
		</el-dialog>



		<div class="main">


			<div class="logoContainer">
				<div class="logo"><!-- <img src="~assets/img/logo.png" alt="" /> --></div>
				<div class="name">LMSAPI开放平台</div>
			</div>
			<div class="mainBox" :class="activeName == 'first' ? '' : 'mainBoxRegistered'">
				<el-tabs v-model="activeName" type="card" @tab-click="handleClick" stretch>
					<el-tab-pane label="登录" name="first">
						<div class="loginInput">
							<el-form ref="loginInfo" :model="loginInfo" :rules="loginRules" label-width="80px">
								<el-form-item prop="username">
									<el-input ref="username" v-model="loginInfo.username" placeholder="请输入用户名">
									</el-input>
								</el-form-item>
								<el-form-item prop="password">
									<el-input ref="password" v-model="loginInfo.password" show-password
										placeholder="请输入密码"></el-input>
								</el-form-item>
								<el-form-item prop="code">
									<div class="check-code-panel">
										<el-input size="large" placeholder="请输入验证码" v-model.trim="loginInfo.code">
										</el-input>
										<img :src="sendCheckCodeUrl" class="check-code" @click="changeCheckCode(0)" />
									</div>

								</el-form-item>
								<el-form-item>
									<el-button class="pan-button" type="primary" @click="onSubmit">登录</el-button>
								</el-form-item>
							</el-form>
						</div>
					</el-tab-pane>
					<el-tab-pane label="注册" name="second">
						<div class="registeredInput">
							<el-form ref="registeredInfo" :model="registeredInfo" :rules="registerRules"
								label-width="80px">

								<el-form-item prop="email">
									<el-input ref="email" v-model="registeredInfo.email" placeholder="请输入邮箱">
									</el-input>
								</el-form-item>

								<el-form-item prop="emailCode">
									<div class="check-code-panel">
										<el-input ref="emailCode" v-model="registeredInfo.emailCode"
											placeholder="请输入邮箱验证码">
										</el-input>
										<el-button type="primary" @click="handleSendEmail(registeredInfo.email)">获取
										</el-button>
									</div>
								</el-form-item>
								<el-form-item prop="username">
									<el-input ref="username" v-model="registeredInfo.username" placeholder="请输入用户名">
									</el-input>
								</el-form-item>
								<el-form-item prop="password">
									<el-input ref="password" v-model="registeredInfo.password" placeholder="请输入密码"
										show-password>
									</el-input>
								</el-form-item>
								<el-form-item prop="checkPassword">
									<el-input ref="checkPassword" v-model="registeredInfo.checkPassword"
										placeholder="请输入确定密码" show-password></el-input>
								</el-form-item>

								<el-form-item>
									<el-button class="pan-button" type="primary" @click="clickRegistered">注册</el-button>
								</el-form-item>
							</el-form>
						</div>
					</el-tab-pane>
				</el-tabs>
			</div>
		</div>
	</div>
</template>

<script>
	// 倒计时名称
	let timer;
	import Cookies from 'js-cookie';
	import {
		login,
		register,
		getInfo,
		sendEmail
	} from '../../api/user.js'
	import {
		validUsername
	} from '@/utils/validate'
	export default {
		name: "Login",
		data() {
			const validateUsername = (rule, value, callback) => {
				if (!validUsername(value)) {
					callback(new Error('请输入正确的用户名'))
				} else {
					callback()
				}
			}
			const validatePassword = (rule, value, callback) => {
				if (value.length < 6) {
					callback(new Error('请输入大于6位数的密码'))
				} else {
					callback()
				}
			}
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
			const validateCheckPassword = (rule, value, callback) => {
				const pwd = this.registeredInfo.password
				if (value.length < 6) {
					callback(new Error('请输入大于6位数的确定密码'))
				} else if (value !== pwd) {
					callback(new Error('两次密码不一致'))
				} else {
					callback()
				}
			}
			return {
				loginInfo: {
					username: "jzdn",
					password: "12345678",
					code: ""
				},
				loginRules: {
					username: [{
						required: true,
						trigger: 'blur',
						validator: validateUsername
					}],
					password: [{
						required: true,
						trigger: 'blur',
						validator: validatePassword
					}],
					code: [{
						required: true,
						trigger: 'blur',
						validator: validateCode
					}],
					checkPassword: [{
						required: true,
						trigger: 'blur',
						validator: validateCheckPassword
					}]
				},
				registeredInfo: {
					username: "",
					password: "",
					checkPassword: "",
					email: '',
					emailCode: ''
				},
				registerRules: {
					username: [{
						required: true,
						trigger: 'blur',
						validator: validateUsername
					}],
					password: [{
						required: true,
						trigger: 'blur',
						validator: validatePassword
					}],
					checkPassword: [{
						required: true,
						trigger: 'blur',
						validator: validateCheckPassword
					}],
					email: [{
						required: true,
						trigger: 'blur',
						validator: validateEmail
					}],
					emailCode: [{
						required: true,
						trigger: 'blur',
						validator: validateCode
					}],

				},

				sendEmailInfo: {
					email: '',
					code: '',
					type: 0
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
				activeName: "first",
				// 倒计时秒数
				countDownSecond: 60,
				// 是否显示秒数
				isCountDownShow: false,
				sendCheckCodeUrl:'',
				checkCodeUrl: "http://localhost:9000/lmsapi/user/checkCode"
			};
		},
		created(){
			//一开始是登录，所以type=0
			this.changeCheckCode(0)
		},
		methods: {


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
			},
			doSendEmail() {
				this.$refs.sendEmailInfo.validate(valid => {
					if (valid) {
						sendEmail(this.sendEmailInfo).then(res => {
							if (res.code = 20000) {
								this.$message.sucess("验证码发送成功!");
								this.closeSendEmail()
							} else {
								this.changeCheckCode(1)
								this.$message.error(res.msg);
							}
						}).catch(error => {
							this.$message.error("发送邮件失败!");
						})

					}

				})
			},
			changeCheckCode(type) {
				console.log(this.sendCheckCodeUrl)
				this.sendCheckCodeUrl = this.checkCodeUrl + "?type=" + type+"&time=" + new Date().getTime();
			},
			//   点击登录的回调
			async onSubmit() {

			this.$refs.loginInfo.validate(valid => {
			  if (valid) {
			    this.loading = true
			    login(this.loginInfo).then(res=>{
			      if(res.code==20000){
			        getInfo().then(res=>{
			          var loginUser = res.data;
			          if (loginUser == null || loginUser == undefined) {
			            return;
			          }
			          window.localStorage.setItem(
			            "userInfo",
			            JSON.stringify(loginUser)
			          );

			          this.$router.push({
			            path: this.redirect || '/'
			          })

			        })

			      }else{
			         this.$modal.msgError("登录失败，账号或者密码错误");
			          this.loading = false;
			      }
			    })
			  } else {
			    console.log('error submit!!')
			    return false
			  }
			})

			},

			handleClick(e) {
				console.log(e.name);
			},

			// // 获取验证码
			// async getCode() {
			//   this.isCountDownShow = true;
			//   let res = await this.$request(
			//     `/edumsm/msm/send/${this.registered.mobile}`
			//   );
			//   console.log(res);
			//   if (res.data.success) {
			//     this.startCountDown();
			//   }
			// },

			// 倒计时
			startCountDown() {
				this.countDownSecond = 60;
				timer = setInterval(() => {
					this.countDownSecond--;
					if (this.countDownSecond == 0) {
						clearInterval(timer);
						this.isCountDownShow = false;
					}
				}, 1000);
			},
			// 点击注册的回调
			async clickRegistered() {
				this.$refs.registeredInfo.validate(valid => {
					if (valid) {
						register(this.registeredInfo).then(res => {
							if (res.code == 20000) {
								this.$message.success("注册成功!");
								this.loginInfo.username = this.registeredInfo.username;
								this.activeName = "first";
								this.registered = {
									username: "",
									password: "",
									checkPassword: "",
									email: "",
									emailCode: ""
								};
							} else {
								this.$message.error("注册失败,请稍后重试!");
							}
						}).catch(error => {
							console.log(error)
							this.$message.warning("系统错误！");
						})

					} else {
						return false;
					}


				})

			},
		},
	};
</script>

<style scoped>
	.login {
		flex: 1;
		background-size: cover;
	background-position: 150px 60px;
		background-size: 800px;
		background-repeat: no-repeat;
		 background-color: #f0f8ff;
		background-image: url("../../assets/img/login_img.png");
		height: 100vh;
	}

	.logoContainer {
		display: flex;
		align-items: center;
		margin-bottom: 10px;
		justify-content: center;
	}

	.logo {
		width: 50px;
	}

	.logo img {
		width: 100%;
	}

	.name {
		color: #25262b;
		font-size: 20px;
		letter-spacing: 4px;
		font-weight: bold;
		margin-left: 7px;
	}

	.main {
		width: 350px;
		height: 400px;
		position: absolute;
		right: 10vw;
		top: 15vh;
	}

	.mainBox {
		width: 350px;
		background-color: #fff;
		height: 400px;
		border-radius: 10px;
		overflow: hidden;
	 border: 2px #e1e1e1 solid;
	}

	.mainBoxRegistered {
		height: 500px;
	}

	.el-form /deep/ .el-form-item__content {
		margin: 0 !important;
		padding: 0 20px;
	}

	.el-input /deep/ input {
		border-radius: 10px;
	}

	.loginInput {
		margin-top: 20px;
	}

	.el-tabs /deep/ .is-active,
	.el-tabs /deep/ div:hover {
		color: #595bb3;
	}

	.el-tabs /deep/ .is-active {
		background-color: #fff;
	}

	.el-tabs /deep/ .el-tabs__item {
		border: none !important;
		font-size: 18px;
		height: 50px;
		line-height: 50px;
	}

	.el-tabs /deep/ .el-tabs__nav {
		border: none;
	}

	.el-tabs /deep/ .el-tabs__nav-scroll {
		background-color: #f5f5f6;
	}

	.el-input /deep/ .el-input__inner {
		height: 48px;
		font-size: 15px;
	}

	.pan-button {
		width: 100%;
		background-color: #6c6dbb;
		border: none;
		border-radius: 10px;
		margin-top: 10px;
		height: 45px;
		font-size: 15px;
	}

	.pan-button:hover {
		background-color: #595bb3;
	}

	.codeContainer {
		position: relative;
	}

	.codeButtonContainer {
		position: absolute;
		top: 50%;
		right: 30px;
		transform: translateY(-50%);
	}

	.getcode {
		background-color: #6c6dbb;
		color: white;
		height: 35px;
		margin: 0;
	}

	.countDown {
		color: rgb(141, 141, 141);
	}

	.check-code-panel {
		width: 100%;
		display: flex;

		.check-code {
			margin-left: 5px;
			cursor: pointer;
		}
	}
	.bg {

	}

</style>
