/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kdp004.a {

	export module viewmodel {

		import modal = nts.uk.ui.windows.sub.modal;
		import setShared = nts.uk.ui.windows.setShared;
		import getShared = nts.uk.ui.windows.getShared;
		import block = nts.uk.ui.block;
		import dialog = nts.uk.ui.dialog;
		import jump = nts.uk.request.jump;
		import getText = nts.uk.resource.getText;
		import getMessage = nts.uk.resource.getMessage;

		export class ScreenModel {
			stampSetting: KnockoutObservable<StampSetting> = ko.observable({});
			stampTab: KnockoutObservable<StampTab> = ko.observable(new StampTab());
			stampToSuppress: KnockoutObservable<StampToSuppress> = ko.observable({
				departure: false,
				goOut: false,
				goingToWork: false,
				turnBack: false
			});
			stampResultDisplay: KnockoutObservable<IStampResultDisplay> = ko.observable({});
			serverTime: KnockoutObservable<any> = ko.observable('');
			isUsed: KnockoutObservable<boolean> = ko.observable(false);
			errorMessage: KnockoutObservable<string> = ko.observable('');
			loginInfo: any = null;
			retry: number = 0;
			fingerAuthCkb: KnockoutObservable<boolean> = ko.observable(false);
			constructor() {
				let self = this;

			}

			public startPage(): JQueryPromise<void> {
				let self = this;
				let dfd = $.Deferred<void>();
				//nts.uk.characteristics.remove("loginKDP004");
				nts.uk.characteristics.restore("loginKDP004").done(function(loginInfo: ILoginInfo) {
					if (!loginInfo) {
						self.setLoginInfo().done((loginResult) => {
							if (!loginResult) {
								self.isUsed(false);
								dfd.resolve();
								return;
							}
							self.doFirstLoad().done(() => {
								dfd.resolve();
								return;
							});
						});
					} else {
						self.loginInfo = loginInfo;
						self.doFirstLoad().done(() => {
							dfd.resolve();
						});
					}
				});
				return dfd.promise();
			}

			getErrorNotUsed(errorType) {
				const notUseMessage = [
					{ text: "Msg_1644", value: 1 },
					{ text: "Msg_1645", value: 2 },
					{ text: "Msg_1619", value: 3 }
				]
				let item = _.find(notUseMessage, ['value', errorType]);
				return item ? getMessage(item.text, [getText('KDP002_3')]) : '';
			}

			doFirstLoad(): JQueryPromise<any> {
				let dfd = $.Deferred<any>(), self = this;
				let loginInfo = self.loginInfo;
				block.grayout();
				service.confirmUseOfStampInput({ employeeId: null, stampMeans: 1 }).done((res) => {
					self.isUsed(res.used == 0);
					if (self.isUsed()) {
						let isAdmin = true;
						service.login(isAdmin, loginInfo).done((res) => {
							block.grayout();
							service.startPage()
								.done((res: any) => {
									if (!res.stampSetting || !res.stampResultDisplay) {
										self.errorMessage(self.getErrorNotUsed(1));
										self.isUsed(false);
										dfd.resolve();
										return;
									}
									self.stampSetting(res.stampSetting);
									self.stampTab().bindData(res.stampSetting.pageLayouts);
									self.stampResultDisplay(res.stampResultDisplay);
									dfd.resolve();
								}).fail((res) => {
									dialog.alertError({ messageId: res.messageId }).then(() => {
										jump("com", "/view/ccg/008/a/index.xhtml");
									});
								}).always(() => {
									dfd.resolve();
									block.clear();
								});

							self.getStampToSuppress();

						}).fail((res) => {
							self.isUsed(false);
							self.errorMessage(getMessage(res.messageId));
							dfd.resolve();
						}).always(() => {

							block.clear();
						});
					} else {
						self.isUsed(false);
						self.errorMessage(self.getErrorNotUsed(res.used));
						dfd.resolve();
					}
				}).always(() => {
					block.clear();
				});
				return dfd.promise();
			}

			public setLoginInfo(): JQueryPromise<any> {
				let dfd = $.Deferred<any>(), self = this;

				self.openDialogF({
					mode: 'admin'
				}).done((loginResult) => {
					if (!loginResult) {
						self.errorMessage(getMessage("Msg_1647"));
						dfd.resolve();
						return;
					}
					self.loginInfo = loginResult.em;

					self.openDialogK().done((result) => {
						if (!result) {
							self.errorMessage(getMessage("Msg_1647"));
							dfd.resolve();
							return;
						}

						self.loginInfo.selectedWP = result;
						nts.uk.characteristics.save("loginKDP004", self.loginInfo);
						dfd.resolve(self.loginInfo);
					});
				}).always(() => {
					block.grayout();
					service.startPage()
						.done((res: any) => {
							if (!res.stampSetting || !res.stampResultDisplay) {
								self.errorMessage(self.getErrorNotUsed(1));
								self.isUsed(false);
								return;
							}
							self.stampSetting(res.stampSetting);
							self.stampTab().bindData(res.stampSetting.pageLayouts);
							self.stampResultDisplay(res.stampResultDisplay);

						}).fail((res) => {
							dialog.alertError({ messageId: res.messageId }).then(() => {
								jump("com", "/view/ccg/008/a/index.xhtml");
							});
						}).always(() => {
							block.clear();
						});
				});
				return dfd.promise();
			}

			public fingerAuth(): JQueryPromise<any> {
				let dfd = $.Deferred<any>();

				service.fingerAuth(this.fingerAuthCkb()).done((res) => {
					dfd.resolve(res);
				});

				return dfd.promise();
			}

			public openDialogF(param): JQueryPromise<any> {
				let vm = new ko.ViewModel();
				let dfd = $.Deferred<any>();

				vm.$window.modal('at', '/view/kdp/003/f/index.xhtml', param).then(function(loginResult): any {

					dfd.resolve(loginResult);
				});

				return dfd.promise();
			}

			public openDialogK(): JQueryPromise<any> {
				let vm = new ko.ViewModel();
				let dfd = $.Deferred<any>();
				vm.$window.modal('at', '/view/kdp/003/k/index.xhtml').then((selectedWP) => {
					if (selectedWP) {
						dfd.resolve(selectedWP.selectedId);
					}
					dfd.resolve(selectedWP);
				});
				return dfd.promise();
			}

			public getPageLayout(pageNo: number) {
				let self = this;
				let layout = _.find(self.stampTab().layouts(), (ly) => { return ly.pageNo === pageNo });

				if (layout) {
					let btnSettings = layout.buttonSettings;
					btnSettings.forEach(btn => {
						btn.onClick = self.clickBtn1;
					});
					layout.buttonSettings = btnSettings;
				}

				return layout;
			}

			public getStampToSuppress() {
				let vm = this;
				block.invisible();
				service.getStampToSuppress().done((data: IStampToSuppress) => {
					block.clear();

					data.isUse = vm.stampSetting() ? vm.stampSetting().buttonEmphasisArt : false;
					vm.stampToSuppress(data);
					vm.stampToSuppress.valueHasMutated();
				});
			}

			public openScreenG(): JQueryPromise<any> {
				let self = this;
				const vm = new ko.ViewModel();
				let retry = 0,
					errorMessage = 'Msg_301';

				const process = () => {
					return vm.$window.storage('ModelGParam', { displayLoginBtn: retry >= self.stampSetting().authcFailCnt, errorMessage })
						.then(() => {
							return vm.$window.modal('at', '/view/kdp/004/g/index.xhtml')
								.then((result) => {
									let redirect: "retry" | "loginPass" | "cancel" = result.actionName;

									if (redirect === "retry") {
										retry = retry + 1;
										return self.fingerAuth();
									}

									if (redirect === "loginPass") {
										return self.openDialogF({
											mode: 'fingerVein',
											company: { id: vm.$user.companyId, code: self.loginInfo.companyCode, name: self.loginInfo.companyCode },
											employee: { id: vm.$user.employeeId, code: self.loginInfo.employeeCode, name: self.loginInfo.employeeName },
											passwordRequired: true
										});
									}
									return 'cancel';
								})
								.then((res) => {

									if (res !== 'cancel' && !!res) {

										if (!res.result) {
											errorMessage = 'Msg_301';
											return process();
										}

										if (res.result) {
											return { isSuccess: true, authType: res.em ? 2 : 0 };
										}
									} else {
										return { isSuccess: false, authType: 0 };
									}
								});
						});
				};

				return process();
			}

			public clickBtn1(vm, layout) {
				let button = this;

				vm.doAuthent().done((res: IAuthResult) => {
					if (res.isSuccess) {
						vm.registerData(button, layout, res.authType);
					}
				});
			}

			public doAuthent(): JQueryPromise<IAuthResult> {
				let self = this;
				let dfd = $.Deferred<any>();

				self.fingerAuth().done((res) => {
					if (res.result) {
						service.confirmUseOfStampInput({ employeeId: self.loginInfo.employeeId, stampMeans: 1 }).done((res) => {
							self.isUsed(res.used == 0);
							if (!self.isUsed()) {
								self.errorMessage(getMessage(res.messageId));
							}
							dfd.resolve({ isSuccess: self.isUsed(), authType: 0 });
						});

					} else {
						self.errorMessage(getMessage("Msg_302"));

						self.openScreenG().done((res) => {
							dfd.resolve(res);
						});
					}

				});

				return dfd.promise();
			}

			playAudio(audioType: number) {

				const url = {
					oha: '../../share/voice/0_oha.mp3',
					otsu: '../../share/voice/1_otsu.mp3'
				}
				const audio: HTMLAudioElement = document.createElement('audio');
				const source: HTMLSourceElement = document.createElement('source');

				if (audioType === 1) {
					source.src = url.oha;
				}

				if (audioType === 2) {
					source.src = url.otsu;
				}
				audio.append(source);
				audio.play();
			}

			checkHis(self: ScreenModel) {
				let vm = new ko.ViewModel();
				self.doAuthent().done((res: IAuthResult) => {
					if (res.isSuccess) {
						vm.$window.modal('at', '/view/kdp/003/s/index.xhtml');
					}
				});
			}

			settingUser(self: ScreenModel) {
				self.openDialogF({
					mode: 'admin'
				}).done((loginResult) => {
					if (loginResult) {
						loginResult.em.selectedWP = self.loginInfo.selectedWP;
						self.loginInfo = loginResult.em;
						self.openDialogK().done((result) => {
							if (result) {
								self.loginInfo.selectedWP = result;
							}
							nts.uk.characteristics.save("loginKDP004", self.loginInfo);
							jump("at", "/view/kdp/004/a/index.xhtml");
						});
					}

				});
			}

			public registerData(button, layout, authcMethod) {
				let self = this;
				let vm = new ko.ViewModel();
				block.invisible();
				let data = {
					employeeId: vm.$user.employeeId,
					datetime: moment(vm.$date.now()).format('YYYY/MM/DD HH:mm:ss'),
					stampNumber: null,
					stampButton: {
						pageNo: layout.pageNo,
						buttonPositionNo: button.btnPositionNo
					},
					refActualResult: {
						cardNumberSupport: null,
						workLocationCD: null,
						workTimeCode: null,
						overtimeDeclaration: null
					},
					authcMethod: authcMethod
				};

				service.stampInput(data).done((res) => {
					//phat nhac
					self.playAudio(button.audioType);

					if (self.stampResultDisplay().notUseAttr == 1 && (button.changeClockArt == 1 || button.changeClockArt == 9)) {
						self.openScreenC(button, layout);
					} else {
						self.openScreenB(button, layout);
					}
				}).fail((res) => {
					dialog.alertError({ messageId: res.messageId });
				}).always(() => {
					self.getStampToSuppress();
					block.clear();
				});


			}

			public openScreenB(button, layout) {
				let self = this;
				let vm = new ko.ViewModel();

				setShared("resultDisplayTime", self.stampSetting().resultDisplayTime);
				setShared("infoEmpToScreenB", {
					employeeId: vm.$user.employeeId,
					employeeCode: vm.$user.employeeCode,
					mode: Mode.Personal,
				});

				modal('/view/kdp/002/b/index.xhtml').onClosed(() => {

					self.openKDP002T(button, layout);
				});
			}

			public openScreenC(button, layout) {
				let self = this;
				let vm = new ko.ViewModel();
				setShared('KDP010_2C', self.stampResultDisplay().displayItemId, true);
				setShared("infoEmpToScreenC", {
					employeeId: vm.$user.employeeId,
					employeeCode: vm.$user.employeeCode,
					mode: Mode.Personal,
				});

				modal('/view/kdp/002/c/index.xhtml').onClosed(function(): any {
					self.openKDP002T(button, layout);
				});
			}

			public openKDP002T(button: ButtonSetting, layout) {
				let data = {
					pageNo: layout.pageNo,
					buttonDisNo: button.btnPositionNo
				}
				service.getError(data).done((res) => {
					if (res && res.dailyAttdErrorInfos && res.dailyAttdErrorInfos.length > 0) {
						setShared('KDP010_2T', res, true);
						modal('/view/kdp/002/t/index.xhtml').onClosed(function(): any {
							let returnData = getShared('KDP010_T');
							if (!returnData.isClose && returnData.errorDate) {
								console.log(returnData);
								// T1	打刻結果の取得対象項目の追加
								// 残業申請（早出）
								let transfer = returnData.btn.transfer;
								jump(returnData.btn.screen, transfer);
							}
						});
					}
				});
			}

			public reCalGridWidthHeight() {
				let windowHeight = window.innerHeight - 250;
				$('#stamp-history-list').igGrid("option", "height", windowHeight);
				$('#time-card-list').igGrid("option", "height", windowHeight);
				$('#content-area').css('height', windowHeight + 109);
			}

		}

	}
	enum Mode {
		Personal = 1, // 個人
		Shared = 2  // 共有 
	}
}