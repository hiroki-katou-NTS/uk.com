module nts.uk.at.view.kdp004.a {

	export module viewmodel {

		import modal = nts.uk.ui.windows.sub.modal;
		import setShared = nts.uk.ui.windows.setShared;
		import getShared = nts.uk.ui.windows.getShared;
		import block = nts.uk.ui.block;
		import dialog = nts.uk.ui.dialog;
		import jump = nts.uk.request.jump;

		export class ScreenModel {
			stampSetting: KnockoutObservable<StampSetting> = ko.observable({});
			stampTab: KnockoutObservable<StampTab> = ko.observable(new StampTab());
			stampToSuppress: KnockoutObservable<StampToSuppress> = ko.observable({});
			stampResultDisplay: KnockoutObservable<IStampResultDisplay> = ko.observable({});
			serverTime: KnockoutObservable<any> = ko.observable('');
			isUsed: KnockoutObservable<boolean> = ko.observable(true);
			errorMessage: KnockoutObservable<string> = ko.observable('');
			constructor() {
				let self = this;
			}

			public startPage(): JQueryPromise<void> {
				let self = this;
				let dfd = $.Deferred<void>();

				nts.uk.characteristics.restore("loginKDP004").done(function(loginInfo: ILoginInfo) {
					if (!loginInfo) {
						self.openDialogF();
					} else {

					}
				});
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

				return dfd.promise();
			}

			public openDialogF(): JQueryPromise<void> {
				let self = this;
				let dfd = $.Deferred<void>();
				dfd.resolve();
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

			public clickBtn1(vm, layout) {
				let button = this;
				nts.uk.request.syncAjax("com", "server/time/now/").done((res) => {
					let data = {
						datetime: moment.utc(res).format('YYYY/MM/DD HH:mm:ss'),
						authcMethod: 0,
						stampMeans: 3,
						reservationArt: button.btnReservationArt,
						changeHalfDay: button.changeHalfDay,
						goOutArt: button.goOutArt,
						setPreClockArt: button.setPreClockArt,
						changeClockArt: button.changeClockArt,
						changeCalArt: button.changeCalArt
					};
					service.stampInput(data).done((res) => {
						if (vm.stampResultDisplay().notUseAttr == 1 && (button.changeClockArt == 1 || button.changeClockArt == 9)) {
							vm.openScreenC(button, layout);
						} else {
							vm.openScreenB(button, layout);
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
					employeeCode __viewContext.user.employeeCode,
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