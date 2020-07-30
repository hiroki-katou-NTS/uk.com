/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kdp005.a {

	export module viewmodel {

		import modal = nts.uk.ui.windows.sub.modal;
		import setShared = nts.uk.ui.windows.setShared;
		import getShared = nts.uk.ui.windows.getShared;
		import block = nts.uk.ui.block;
		import dialog = nts.uk.ui.dialog;
		import jump = nts.uk.request.jump;
		import getText = nts.uk.resource.getText;
		import getMessage = nts.uk.resource.getMessage;
        import characteristics = nts.uk.characteristics;

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
            listCompany = [];
            btnHistory: KnockoutObservable<boolean> = ko.observable(false);
            btnChangeCompany: KnockoutObservable<boolean> = ko.observable(false);
			constructor() {
				let self = this;
                self.isUsed.subscribe((value) => {
                    let cid = __viewContext.user.companyId;
                    if(value && _.find(self.listCompany, ['companyId', cid])){
                        self.btnHistory(true);
                    }else{
                        self.btnHistory(false);
                    }    
                });
			}

			public startPage(): JQueryPromise<void> {
				let self = this;
				let dfd = $.Deferred<void>();
                service.getLogginSetting().done((res) => {
                    self.listCompany = _.filter(res, 'icCardStamp');
                    if(self.listCompany.length == 0){
                        self.errorMessage(getMessage("Msg_1527"));
                        self.isUsed(false);
                        dfd.resolve();
                    }else{
                        self.btnChangeCompany(self.listCompany.length > 0);
                        characteristics.restore("loginKDP005").done(function(loginInfo: ILoginInfo) {
                            if (loginInfo && loginInfo.companyId === __viewContext.user.companyId) {
                                self.loginInfo = loginInfo;
                                self.doFirstLoad().done(() => {
                                    dfd.resolve();
                                });
                            } else {
                                self.setLoginInfo().done((loginResult) => {
                                    if (!loginResult) {
                                        self.isUsed(false);
                                        dfd.resolve();
                                    }else{
                                        self.doFirstLoad().done(() => {
                                            dfd.resolve();
                                        });
                                    }
                                });
                            }
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
				return item ? getMessage(item.text, [getText('KDP002_4')]) : '';
			}

			doFirstLoad(): JQueryPromise<any> {
				let dfd = $.Deferred<any>(), self = this;
				let loginInfo = self.loginInfo;
				block.grayout();
				service.confirmUseOfStampInput({ employeeId: null, stampMeans: 2 }).done((res) => {
					self.isUsed(res.used == 0);
					if (self.isUsed()) {
						let isAdmin = true;
						service.login(isAdmin, loginInfo).done((res) => {
							block.grayout();
							service.startPage().done((res: any) => {
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
					mode: 'admin',
                    companyId: __viewContext.user.companyId
				}).done((loginResult) => {
					if (!loginResult) {
						self.errorMessage(getMessage("Msg_1647"));
						dfd.resolve();
					}
					self.loginInfo = loginResult.em;
					self.openDialogK().done((result) => {
						if (!result) {
                            self.errorMessage(getMessage("Msg_1647"));
							dfd.resolve();
						}else {
    						self.loginInfo.selectedWP = result;
                            characteristics.save("loginKDP005", self.loginInfo).done(() => {
                                location.reload();
                                dfd.resolve(self.loginInfo);
                            });
                        }
					});
				}).always(() => {
					
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
				vm.$window.modal('at', '/view/kdp/003/k/index.xhtml', { multiSelect: true }).then((selectedWP) => {
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

			public clickBtn1(vm, layout) {
				let button = this;
                modal('/view/kdp/005/h/index.xhtml').onClosed(function(): any {
                    let ICCard = getShared('ICCard');
                    if (ICCard && ICCard != '') {
                        console.log(ICCard);
                        block.grayout();
                        vm.getEmployeeIdByICCard(ICCard).done((employeeId: string) => {
                            vm.authentic(employeeId).done(() => {
                                vm.registerData(button, layout, ICCard, employeeId);
                            }).fail((errorMessage: string) => {
                                setShared("errorMessage", errorMessage);
                                vm.openIDialog();
                            });
                        }).fail(() => {
                            vm.openIDialog();
                        }).always(() => {
                            block.clear();    
                        });
                    }
                });
			}
            
            public openIDialog(){
                let self = this;
                setShared("resultDisplayTime", self.stampSetting().resultDisplayTime);
                modal('/view/kdp/005/i/index.xhtml');     
            }
            
            public getEmployeeIdByICCard(cardNumber: string): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                service.getEmployeeIdByICCard({ cardNumber: cardNumber}).done((data) => {
                    if (data.employeeId) {
                        dfd.resolve(data.employeeId);
                    }else{
                        dfd.reject();    
                    }
                });
                
                return dfd.promise();
            }
            
            //<<ScreenQuery>> 打刻入力社員の認証のみを行う
            public authentic(employeeId: string): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                let param = {
                    companyId: self.loginInfo.companyId,
                    employeeCode: null,
                    employeeId: employeeId,
                    password: null,
                    passwordInvalid: true,
                    isAdminMode: false,
                    runtimeEnvironmentCreat: false
                };
                service.authenticateOnlyStamped(param).done((res) => {
                    if (res.result) {
                        dfd.resolve();
                    }else{
                        dfd.reject(res.errorMessage);    
                    }
                }).fail((res) => {
                    setShared("errorMessage", getMessage(res.messageId));
                    self.openIDialog();
                });
                return dfd.promise();
            }

			playAudio(audioType: number) {
                const url = {
                    oha: '../../share/voice/0_oha.mp3',
                    otsu: '../../share/voice/1_otsu.mp3'
                }

                let source = '';

                if (audioType === 1) {
                    source = url.oha;
                }

                if (audioType === 2) {
                    source = url.otsu;
                }
                return source;
			}

			checkHis(self: ScreenModel) {
				let vm = new ko.ViewModel();
                modal('/view/kdp/005/h/index.xhtml').onClosed(function(): any {
                    let ICCard = getShared('ICCard');
                    if (ICCard && ICCard != '') {
                        console.log(ICCard);
                        block.grayout();
                        self.getEmployeeIdByICCard(ICCard).done((employeeId: string) => {
                            self.authentic(employeeId).done(() => {
                                vm.$window.modal('at', '/view/kdp/003/s/index.xhtml', {employeeId: employeeId});
                            }).fail((errorMessage: string) => {
                                setShared("errorMessage", errorMessage);
                                self.openIDialog();
                            });
                        }).fail(() => {
                            self.openIDialog();
                        }).always(() => {
                            block.clear();    
                        });
                    }
                });
			}
            
			settingUser(self: ScreenModel) {
				self.openDialogF({
					mode: 'admin',
                    companyId: __viewContext.user.companyId
				}).done((loginResult) => {
					if (loginResult && loginResult.result) {
                        loginResult.em.selectedWP = self.loginInfo ? self.loginInfo.selectedWP : null;
                        self.loginInfo = loginResult.em;
                        self.openDialogK().done((result) => {
                            if (result) {
                                self.loginInfo.selectedWP = result;
                                characteristics.save("loginKDP005", self.loginInfo).done(() => {
                                    location.reload();
                                });
                            } else {
                                self.isUsed(false);
                                self.errorMessage(getMessage("Msg_1647"));
                            }
                        });
                    } else {
                        if (loginResult.msgErrorId == "Msg_1527") {
                            self.isUsed(false);
                            self.errorMessage(getMessage("Msg_1527"));
                        }
                    }
				});
			}

			public registerData(button, layout, stampedCardNumber, employeeIdRegister) {
				let self = this;
				let vm = new ko.ViewModel();
				block.invisible();
				let data = {
					stampedCardNumber: stampedCardNumber,
					datetime: moment(vm.$date.now()).format('YYYY/MM/DD HH:mm:ss'),
					stampButton: {
						pageNo: layout.pageNo,
						buttonPositionNo: button.btnPositionNo
					},
					refActualResult: {
						cardNumberSupport: null,
						workLocationCD: null,
						workTimeCode: null,
						overtimeDeclaration: null
					}
				};
                let source = self.playAudio(button.audioType);
				service.addCheckCard(data).done((res) => {
					//phat nhac
					if (source) {
                        let audio = new Audio(source);
                        audio.play();
                    }

					if (self.stampResultDisplay().notUseAttr == 1 && (button.changeClockArt == 1 || button.changeClockArt == 9)) {
						self.openScreenC(button, layout, employeeIdRegister);
					} else {
						self.openScreenB(button, layout, employeeIdRegister);
					}
				}).fail((res) => {
					dialog.alertError({ messageId: res.messageId });
				}).always(() => {
					self.getStampToSuppress();
					block.clear();
				});

			}

			public openScreenB(button, layout, employeeIdRegister) {
				let self = this;
				let vm = new ko.ViewModel();
				setShared("resultDisplayTime", self.stampSetting().resultDisplayTime);
				setShared("infoEmpToScreenB", {
					employeeId: employeeIdRegister,
					mode: Mode.Personal,
				});
				modal('/view/kdp/002/b/index.xhtml').onClosed(() => {
					self.openKDP002T(button, layout);
				});
			}

			public openScreenC(button, layout, employeeIdRegister) {
				let self = this;
				let vm = new ko.ViewModel();
				setShared('KDP010_2C', self.stampResultDisplay().displayItemId, true);
				setShared("infoEmpToScreenC", {
					employeeId: employeeIdRegister,
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
								let transfer = returnData.btn.transfer;
								jump(returnData.btn.screen, transfer);
							}
						});
					}
				});
			}
		}
	}
    
	enum Mode {
		Personal = 1, // 個人
		Shared = 2  // 共有 
	}
}