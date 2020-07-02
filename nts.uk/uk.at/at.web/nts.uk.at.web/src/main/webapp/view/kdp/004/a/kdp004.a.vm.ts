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
			stampToSuppress: KnockoutObservable<StampToSuppress> = ko.observable({});
			stampResultDisplay: KnockoutObservable<IStampResultDisplay> = ko.observable({});
			serverTime: KnockoutObservable<any> = ko.observable('');
			isUsed: KnockoutObservable<boolean> = ko.observable(false);
			errorMessage: KnockoutObservable<string> = ko.observable('');
			loginInfo: any = null;
			retry: number = 0;
			constructor() {
				let self = this;
			}

			public startPage(): JQueryPromise<void> {
				let self = this;
				let dfd = $.Deferred<void>();

				nts.uk.characteristics.restore("loginKDP004").done(function(loginInfo: ILoginInfo) {
					if (!loginInfo) {
						self.openDialogF().done((loginSuccess) => {
							if (!loginSuccess) {
								self.isUsed(false);
								self.errorMessage(getText("Msg_1647"));
								dfd.resolve();
								return;
							}
							self.openDialogK().done((chooseItem) => {
								if (!chooseItem) {
									self.isUsed(false);
									self.errorMessage(getText("Msg_1647"));
									dfd.resolve();
									return;
								}

								nts.uk.characteristics.save("loginKDP004", {});
								dfd.resolve();

							});
						});
					} else {

						self.loginInfo = loginInfo;
						//login
						block.grayout();
						service.confirmUseOfStampInput({ employeeId: null, stampMeans: 1 }).done((res) => {
							self.isUsed(res.used == 0);
							if (self.isUsed()) {
								let isAdmin = true;
								service.login(isAdmin, loginInfo).done((res) => {
									if (res.result) {
										block.grayout();
										service.startPage()
											.done((res: any) => {
												self.stampSetting(res.stampSetting);
												self.stampTab().bindData(res.stampSetting.pageLayouts);
												self.stampResultDisplay(res.stampResultDisplay);
												dfd.resolve();
											}).fail((res) => {
												dialog.alertError({ messageId: res.messageId }).then(() => {
													jump("com", "/view/ccg/008/a/index.xhtml");
												});
											}).always(() => {
												block.clear();
											});
									} else {
										self.isUsed(false);
										self.errorMessage(getMessage(res.errorMessage));
										dfd.resolve();
									}
								}).fail((res) => {
									self.isUsed(false);
									self.errorMessage(getMessage(res.messageId));
									dfd.resolve();
								}).always(() => {
									block.clear();
								});
							} else {
								self.errorMessage(getMessage(res.messageId));
							}
						});
					}
				});


				return dfd.promise();
			}

			public fingerAuth(): JQueryPromise<any> {
				let dfd = $.Deferred<any>();

				service.fingerAuth().done((res) => {
					dfd.resolve(res);
				});

				return dfd.promise();
			}

			public openDialogF(): JQueryPromise<any> {
				let self = this;
				let dfd = $.Deferred<any>();

				modal('/view/kdp/003/f/index.xhtml').onClosed(function(): any {
					let loginSuccess = true;

					dfd.resolve(loginSuccess);
				});

				return dfd.promise();
			}

			public openDialogK(): JQueryPromise<any> {
				let self = this;
				let dfd = $.Deferred<any>();
				modal('/view/kdp/003/k/index.xhtml').onClosed(() => {
					let chooseItem = true;
					dfd.resolve(chooseItem);
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

			public openScreenG(button, layout): JQueryPromise<IAuthResult> {
				let self = this;
				let dfd = $.Deferred<any>();
				setShared('ModelGParam', { retry: self.retry, errorMessage: self.errorMessage() });
				modal('/view/kdp/003/k/index.xhtml', self.retry).onClosed(() => {
					let redirect: "retry" | "loginPass" | "cancel" = getShared('actionName');
					if (redirect === "retry") {
						self.retry = self.retry + 1;
						self.doAuthent(button, layout).done((res: IAuthResult) => {
							dfd.resolve(res);
						});
					}
					if (redirect === "loginPass") {
						self.openDialogF().done((res) => {
							if (res) {
								self.retry = 0;
							} else {
								self.errorMessage(getText("Msg_1647"));
							}
							dfd.resolve({ isSuccess: self.isUsed(), authType: 2 });
						});
					}
					dfd.resolve({ isSuccess: false, authType: 0 });
				});
				return dfd.promise();
			}

			public clickBtn1(vm, layout) {
				let button = this;

				vm.doAuthent(layout, button).done((res: IAuthResult) => {
					if (res.isSuccess) {
						vm.registerData(button, layout, res.authType);
					}
					vm.isUsed(res.isSuccess);
				});
			}

			public doAuthent(layout, button): JQueryPromise<IAuthResult> {
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
						self.errorMessage(getMessage(res.messageId));
						self.openScreenG(button, layout).done((res: IAuthResult) => {
							dfd.resolve(res);
						});

					}

				});

				return dfd.promise();
			}

			public registerData(button, layout, authcMethod) {
				let self = this;

				nts.uk.request.syncAjax("com", "server/time/now/").done((res) => {
					let data = {
						employeeId: self.loginInfo.employeeId,
						datetime: moment.utc(res).format('YYYY/MM/DD HH:mm:ss'),
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
						if (self.stampResultDisplay().notUseAttr == 1 && (button.changeClockArt == 1 || button.changeClockArt == 9)) {
							self.openScreenC(button, layout);
						} else {
							self.openScreenB(button, layout);
						}
					}).fail((res) => {
						dialog.alertError({ messageId: res.messageId });
					});
				});


			}

			public openScreenB(button, layout) {
				let self = this;

				setShared("resultDisplayTime", self.stampSetting().resultDisplayTime);
				setShared("infoEmpToScreenB", {
					employeeId: __viewContext.user.employeeId,
					employeeCode: __viewContext.user.employeeCode,
					mode: Mode.Personal,
				});

				modal('/view/kdp/002/b/index.xhtml').onClosed(() => {

					self.stampToSuppress.valueHasMutated();
					self.openKDP002T(button, layout);
				});
			}

			public openScreenC(button, layout) {
				let self = this;
				setShared('KDP010_2C', self.stampResultDisplay().displayItemId, true);
				setShared("infoEmpToScreenC", {
					employeeId: __viewContext.user.employeeId,
					employeeCode: __viewContext.user.employeeCode,
					mode: Mode.Personal,
				});

				modal('/view/kdp/002/c/index.xhtml').onClosed(function(): any {

					self.stampToSuppress.valueHasMutated();
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