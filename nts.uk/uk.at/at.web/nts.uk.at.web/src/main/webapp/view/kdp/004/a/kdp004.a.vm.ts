/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
/// <reference path="../../../kdp/003/f/kdp003.f.vm.ts" />

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
		import f = nts.uk.at.kdp003.f;
		import FingerStampSetting = nts.uk.at.kdp003.a.FingerStampSetting;
		import checkType = nts.uk.at.view.kdp.share.checkType;

		const DIALOG = {
			R: '/view/kdp/003/r/index.xhtml',
			F: '/view/kdp/003/f/index.xhtml',
			M: '/view/kdp/003/m/index.xhtml',
			P: '/view/kdp/003/p/index.xhtml'
		};

		const API = {
			NOTICE: 'at/record/stamp/notice/getStampInputSetting',
			GET_LOCATION: 'at/record/stamp/employment_system/get_location_stamp_input',
			SETTING_STAMP_COMMON: 'at/record/stamp/settings_stamp_common'
		};

		const KDP004_SAVE_DATA = 'loginKDP004';

		export class ScreenModel {
			stampSetting: KnockoutObservable<StampSetting> = ko.observable({} as StampSetting);
			stampTab: KnockoutObservable<StampTab> = ko.observable(new StampTab());
			stampToSuppress: KnockoutObservable<StampToSuppress> = ko.observable({
				departure: false,
				goOut: false,
				goingToWork: false,
				turnBack: false,
				isUse: false
			});
			stampResultDisplay: KnockoutObservable<IStampResultDisplay> = ko.observable({} as IStampResultDisplay);
			serverTime: KnockoutObservable<any> = ko.observable('');
			isUsed: KnockoutObservable<boolean> = ko.observable(false);
			errorMessage: KnockoutObservable<string> = ko.observable('');
			loginInfo: any = null;
			retry: number = 0;
			fingerAuthCkb: KnockoutObservable<boolean> = ko.observable(false);
			selectedMsg: KnockoutObservable<string> = ko.observable('Msg_301');
			listCompany: KnockoutObservableArray<any> = ko.observableArray([]);
			messageNoti: KnockoutObservable<IMessage> = ko.observable();
			fingerStampSetting: KnockoutObservable<FingerStampSetting> = ko.observable(DEFAULT_SETTING);

			modeBasyo: KnockoutObservable<boolean> = ko.observable(false);

			// get from basyo;
			workplace: string[] | [] = [];
			worklocationCode: string = '';
			workplaceId: string = null;
			workplaceName: string = null;

			supportUse: KnockoutObservable<boolean> = ko.observable(false);

			constructor() {
				let self = this;
			}

			public startPage(): JQueryPromise<void> {
				let self = this;
				let vm = new ko.ViewModel();
				let dfd = $.Deferred<void>();

				vm.$blockui('invisible')
					.then(() => {
						self.basyo();
					}).then(() => {
						nts.uk.characteristics.restore(KDP004_SAVE_DATA).done(function (loginInfo: ILoginInfo) {


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
								console.log(ko.unwrap(self.modeBasyo));
								if (ko.unwrap(self.modeBasyo)) {
									self.loginInfo.selectedWP = self.workplace;
									nts.uk.characteristics.save(KDP004_SAVE_DATA, self.loginInfo);
								}

								self.doFirstLoad().done(() => {
									dfd.resolve();
								});
								self.loadNotice(self.loginInfo);
							}
						}).always(() => {
							service.getLogginSetting().done((res) => {
								self.listCompany(_.filter(res, 'fingerAuthStamp'));
							});

						});
					})
					.always(() => {
						vm.$blockui('clear');
					});


				return dfd.promise();

			}

			showButton() {
				let self = this;
				if (!self.isUsed()) {
					if (self.listCompany().length > 0) {
						return ButtonDisplayMode.ShowHistory;
					} else {
						return ButtonDisplayMode.NoShow;
					}

				}

				return ButtonDisplayMode.ShowAll;
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
									if (!res.stampSetting || !res.stampResultDisplay || !res.stampSetting.pageLayouts.length) {
										self.errorMessage(self.getErrorNotUsed(1));
										self.isUsed(false);
										dfd.resolve();
										return;
									}
									self.stampSetting(res.stampSetting);
									self.stampTab().bindData(res.stampSetting.pageLayouts);
									self.stampResultDisplay(res.stampResultDisplay);
									self.fingerStampSetting(res);
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

				self.openScreenF({
					mode: 'admin'
				}).done((loginResult) => {
					if (!loginResult || !loginResult.result) {
						self.errorMessage(getMessage(!loginResult ? "Msg_1647" : loginResult.msgErrorId));
						dfd.resolve();
						return;
					}
					self.loginInfo = loginResult.em;

					if (!ko.unwrap(self.modeBasyo)) {
						self.openScreenK().done((result) => {
							if (!result) {
								self.errorMessage(getMessage("Msg_1647"));
								dfd.resolve();
								return;
							}

							self.loginInfo.selectedWP = result;

							nts.uk.characteristics.save(KDP004_SAVE_DATA, self.loginInfo);
							dfd.resolve(self.loginInfo);
						});
					} else {
						self.loginInfo.selectedWP = self.workplace;

						nts.uk.characteristics.save(KDP004_SAVE_DATA, self.loginInfo);
						dfd.resolve(self.loginInfo);
					}

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
							self.fingerStampSetting(res);


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

				service.fingerAuth({ fingerAuthCkb: this.fingerAuthCkb(), selectedMsg: this.selectedMsg() }).done((res) => {
					dfd.resolve(res);
				});

				return dfd.promise();
			}

			public openScreenF(param): JQueryPromise<f.TimeStampLoginData> {
				let vm = new ko.ViewModel();
				let dfd = $.Deferred<f.TimeStampLoginData>();

				vm.$window.modal('at', '/view/kdp/003/f/index.xhtml', param).then(function (loginResult: f.TimeStampLoginData): any {

					dfd.resolve(loginResult);
				});

				return dfd.promise();
			}

			public openScreenK(): JQueryPromise<any> {
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

			public openScreenG(errorMessage): JQueryPromise<any> {
				let self = this;
				const vm = new ko.ViewModel();
				let retry = 0;

				const process = () => {
					return vm.$window.storage('ModelGParam', { displayLoginBtn: retry >= self.stampSetting().authcFailCnt && self.stampSetting().employeeAuthcUseArt, errorMessage })
						.then(() => {
							return vm.$window.modal('at', '/view/kdp/004/g/index.xhtml')
								.then((result) => {
									let redirect: "retry" | "loginPass" | "cancel" = result.actionName;

									if (redirect === "retry") {
										retry = retry + 1;
										return self.fingerAuth();
									}

									if (redirect === "loginPass") {
										return self.openScreenF({
											mode: 'fingerVein',
											companyId: self.loginInfo.companyId,
											employee: { id: self.loginInfo.employeeId, code: self.loginInfo.employeeCode },
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
											return { em: res.em, isSuccess: true, authType: res.em ? 0 : 2 };
										}
									} else {
										return { em: res.em, isSuccess: false, authType: 2 };
									}
								});
						});
				};

				return process();
			}

			public clickBtn1(btn: any, layout: any) {
				const vm = this;

				vm.doAuthent().done((res: IAuthResult) => {
					if (res.isSuccess) {
						vm.registerData(btn, layout, res);
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
							dfd.resolve({ isSuccess: self.isUsed(), authType: 2 });
						});

					} else {

						self.openScreenG(res.messageId).done((res) => {
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

				let source = '';

				if (audioType === 1) {
					source = url.oha;
				}

				if (audioType === 2) {
					source = url.otsu;
				}
				if (source) {
					let audio = new Audio(source);
					audio.play();
				}
			}

			checkHis(self: ScreenModel) {
				let vm = new ko.ViewModel();
				self.doAuthent().done((res: IAuthResult) => {
					if (res.isSuccess) {
						vm.$window.modal('at', '/view/kdp/003/s/index.xhtml', res.em);
					}
				});
			}

			settingUser(self: ScreenModel) {

				let param = self.loginInfo ? {
					mode: 'admin',
					companyId: self.loginInfo.companyId
				} : { mode: 'admin' };
				self.openScreenF(param).done((loginResult) => {
					if (loginResult && loginResult.result) {
						let result: any = loginResult.em;
						result.selectedWP = self.loginInfo ? self.loginInfo.selectedWP : null;
						self.loginInfo = loginResult.em;
						self.openScreenK().done((result) => {
							if (result) {
								self.loginInfo.selectedWP = result;
								nts.uk.characteristics.save(KDP004_SAVE_DATA, self.loginInfo).done(() => {
									location.reload();
								});
							} else {
								location.reload();
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

			public registerData(button: any, layout: any, loginInfo: any) {
				let self = this;
				let vm = new ko.ViewModel();
				block.invisible();
				let registerdata = {
					employeeId: loginInfo && loginInfo.em ? loginInfo.em.employeeId : vm.$user.employeeId,
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
					authcMethod: loginInfo.authType
				};
				const mode: number = 1;
				const { employeeId, employeeCode } = loginInfo.em;
				const workLocationName = self.workplaceName;
				const workpalceId = self.workplaceId;
				const employeeInfo = { mode, employeeId, employeeCode, workLocationName, workpalceId };

				vm.$window.storage(KDP004_SAVE_DATA)
					.then((dataStorage: StorageData) => {

						//打刻入力で共通設定を取得する
						vm.$ajax(API.SETTING_STAMP_COMMON)
							.done((data: ISettingsStampCommon) => {
								if (data) {
									self.supportUse(data.supportUse);
								}
							});

						let btnType = checkType(button.changeClockArt, button.changeCalArt, button.setPreClockArt, button.changeHalfDay, button.btnReservationArt);

						if (dataStorage.WKPID.length > 1 && self.supportUse() === true && _.includes([14, 15, 16, 17, 18], btnType)) {
							vm.$window.modal('at', DIALOG.M)
								.then((result: string) => {
									service.stampInput(registerdata).done((res) => {

										//phat nhac
										self.playAudio(button.audioType);

										if (self.stampResultDisplay().notUseAttr == 1 && button.changeClockArt == 1) {
											vm.$window.storage('infoEmpToScreenC', employeeInfo);
											self.openScreenC(button, layout, loginInfo.em);
										} else {
											vm.$window.storage('infoEmpToScreenB', employeeInfo);
											self.openScreenB(button, layout, loginInfo.em);
										}

									}).fail((res) => {
										dialog.alertError({ messageId: res.messageId });
									}).always(() => {
										self.getStampToSuppress();
										block.clear();
									});

								});
						} else {
							service.stampInput(registerdata).done((res) => {

								//phat nhac
								self.playAudio(button.audioType);

								if (self.stampResultDisplay().notUseAttr == 1 && button.changeClockArt == 1) {
									self.openScreenC(button, layout, loginInfo.em);
								} else {
									self.openScreenB(button, layout, loginInfo.em);
								}

							}).fail((res) => {
								dialog.alertError({ messageId: res.messageId });
							}).always(() => {
								self.getStampToSuppress();
								block.clear();
							});
						}
					});

			}

			public openScreenB(button, layout, loginInfo) {
				let self = this;
				let vm = new ko.ViewModel();

				setShared("resultDisplayTime", self.stampSetting().resultDisplayTime);
				setShared("infoEmpToScreenB", {
					employeeId: loginInfo ? loginInfo.employeeId : vm.$user.employeeId,
					employeeCode: loginInfo ? loginInfo.employeeCode : vm.$user.employeeCode,
					employeeName: loginInfo ? loginInfo.employeeName : self.loginInfo.employeeName,
					mode: Mode.Personal,
				});
				setShared("screenB", {
					screen: "KDP004"
				});

				modal('/view/kdp/002/b/index.xhtml').onClosed(() => {
				});
			}

			public openScreenC(button, layout, loginInfo) {
				let self = this;
				let vm = new ko.ViewModel();
				setShared('KDP010_2C', self.stampResultDisplay().displayItemId, true);
				setShared("infoEmpToScreenC", {
					employeeId: loginInfo ? loginInfo.employeeId : vm.$user.employeeId,
					employeeCode: loginInfo ? loginInfo.employeeCode : vm.$user.employeeCode,
					employeeName: loginInfo ? loginInfo.employeeName : self.loginInfo.employeeName,
					mode: Mode.Personal,
				});
				setShared("screenC", {
					screen: "KDP004"
				});

				modal('/view/kdp/002/c/index.xhtml').onClosed(function (): any {
				});
			}

			public reCalGridWidthHeight() {
				let windowHeight = window.innerHeight - 250;
				$('#stamp-history-list').igGrid("option", "height", windowHeight);
				$('#time-card-list').igGrid("option", "height", windowHeight);
				$('#content-area').css('height', windowHeight + 109);
			}

			shoNoti() {
				const self = this;
				let vm = new ko.ViewModel();
				const param = { setting: ko.unwrap(self.fingerStampSetting).noticeSetDto, screen: 'KDP004' };
				vm.$window.modal(DIALOG.R, param);
			}

			settingNoti() {
				let vm = new ko.ViewModel();
				const self = this;
				vm.$window.storage(KDP004_SAVE_DATA)
					.then((data: any) => {
						if (data) {
							const mode = 'notification';
							const companyId = (data || {}).companyId;

							vm.$window.modal('at', DIALOG.F, { mode, companyId })
								.then((output: string) => {
									if (output !== 'close') {
										vm.$window.modal('at', DIALOG.P)
											.then(() => {
												self.loadNotice(self.loginInfo);
											})
									}
								});
						}
					});
			}

			loadNotice(loginInfo: any) {
				let vm = new ko.ViewModel();
				const self = this;
				let startDate = vm.$date.now();
				startDate.setDate(startDate.getDate() - 3);

				const param = {
					periodDto: {
						startDate: startDate,
						endDate: vm.$date.now()
					},
					wkpIds: loginInfo.selectedWP
				}

				vm.$blockui('invisible')
					.then(() => {
						vm.$ajax(API.NOTICE, param)
							.done((data: IMessage) => {
								self.messageNoti(data);
							});
					})
					.always(() => {
						vm.$blockui('clear');
					});
			}

			// URLOption basyo
			basyo() {
				const self = this,
					vm = new ko.ViewModel(),
					params = new URLSearchParams(window.location.search),
					locationCd = params.get('basyo');

				// URLOption basyoが存在している場合
				if (locationCd) {
					const param = {
						contractCode: '000000000004',
						workLocationCode: locationCd
					}

					vm.$ajax(API.GET_LOCATION, param)
						.done((data: IBasyo) => {
							if (data) {
								self.modeBasyo(true);
								self.workplace = [data.workpalceId];
								self.worklocationCode = locationCd;
								self.workplaceId = data.workpalceId;
								self.workplaceName = data.workLocationName;
							}
						});

				}
			}
		}

	}
	enum ButtonDisplayMode {
		NoShow = 1,
		ShowHistory = 2,
		ShowAll = 3
	}
	enum Mode {
		Personal = 1, // 個人
		Shared = 2  // 共有 
	}

	const DEFAULT_SETTING: any = {
		stampSetting: null,
		stampResultDisplay: null,
	}

	export interface StorageData {
		CID: string;
		CCD: string;
		SID: string;
		SCD: string;
		PWD: string;
		WKPID: string[];
		WKLOC_CD: string;
	}

	export interface IMessage {
		messageNotices: IMessageNotice[];
	}

	interface IMessageNotice {
		creatorID: string;
		inputDate: Date;
		modifiedDate: Date;
		targetInformation: ITargetInformation;
		startDate: Date;
		endDate: Date;
		employeeIdSeen: string[];
		notificationMessage: string;
	}

	interface ITargetInformation {
		targetSIDs: string[];
		targetWpids: string[];
		destination: number | null;
	}

	interface IBasyo {
		workLocationName: string;
		workpalceId: string;
	}

	interface ISettingsStampCommon {
		supportUse: boolean;
		temporaryUse: boolean;
		workUse: boolean;
	}
}