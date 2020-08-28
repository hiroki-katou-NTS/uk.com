/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
/// <reference path="kdp004.a.model.ts" />


const url = {
	startPage: 'at/record/stamp/finger/get-finger-stamp-setting',
	stampInput: 'at/record/stamp/finger/get-finger-stamp-setting',
	confirmUseOfStampInput: 'at/record/stamp/employment_system/confirm_use_of_stamp_input',
	loginAdminMode: 'ctx/sys/gateway/kdp/login/adminmode',
	loginEmployeeMode: 'ctx/sys/gateway/kdp/login/employeemode',
	fingerAuth: '',
	getError: 'at/record/stamp/employment_system/get_omission_contents'
}


@bean()
class KDP004AViewModel extends ko.ViewModel {

	stampSetting: KnockoutObservable<Kdp004StampSetting> = ko.observable(new Kdp004StampSetting());
	stampTab: KnockoutObservable<StampTab> = ko.observable(new StampTab());
	stampToSuppress: KnockoutObservable<StampToSuppress> = ko.observable(
		{
	goingToWork: true,
    departure: true,
    goOut: true,
    turnBack: true,
    isUse: true
	});
	stampResultDisplay: KnockoutObservable<IStampResultDisplay> = ko.observable();
	serverTime: KnockoutObservable<any> = ko.observable('');
	isUsed: KnockoutObservable<boolean> = ko.observable(false);
	errorMessage: KnockoutObservable<string> = ko.observable('');
	loginInfo: any = null;
	retry: number = 0;

	constuctor() {
		let vm = this;
		$(window).resize(function() {
			vm.reCalGridWidthHeight();
		});
	}


	created(params: any) {
		let vm = this;
		vm.startPage().done(() => {
			vm.reCalGridWidthHeight();
		});
	}

	public startPage(): JQueryPromise<void> {
		let vm = this;
		let dfd = $.Deferred<void>();
		nts.uk.characteristics.restore("loginKDP004").done(function(loginInfo: ILoginInfo) {
			if (!loginInfo) {
				vm.setLoginInfo().done((loginInfo) => {

					if (loginInfo) {
						vm.doFirstLoad(loginInfo).done(() => {
							dfd.resolve();
						});
					} else {
						vm.$ajax(url.startPage)
							.done((res: any) => {
								vm.stampSetting(res.stampSetting);
									vm.stampTab().bindData(res.stampSetting.pageLayouts);
								
								vm.stampResultDisplay(res.stampResultDisplay);
								dfd.resolve();
							}).fail((res) => {
								vm.$dialog.error({ messageId: res.messageId }).then(() => {
									vm.$jump("com", "/view/ccg/008/a/index.xhtml");
								});
							}).always(() => {
								vm.$blockui("clear");
							});
						vm.isUsed(false);
						vm.errorMessage(vm.$i18n.message('Msg_1647'));
					}
				});

			} else {
				vm.doFirstLoad(loginInfo).done(() => {
					dfd.resolve();
				});

			}
		});

		return dfd.promise();
	}

	doFirstLoad(loginInfo) {
		let vm = this, dfd = $.Deferred<void>();;
		vm.loginInfo = loginInfo;
		//login
		vm.$blockui("grayout");
		vm.$ajax(url.confirmUseOfStampInput, { employeeId: null, stampMeans: 1 }).then((res) => {
			vm.isUsed(res.used == 0);
			if (vm.isUsed()) {

				vm.$ajax(url.loginAdminMode, loginInfo).then((res) => {
					if (res.result) {
						vm.$blockui("grayout");
						vm.$ajax(url.startPage)
							.done((res: any) => {
								vm.stampSetting(res.stampSetting);
								vm.stampTab().bindData(res.stampSetting.pageLayouts);
								vm.stampResultDisplay(res.stampResultDisplay);
								dfd.resolve();
							}).fail((res) => {
								vm.$dialog.error({ messageId: res.messageId }).then(() => {
									vm.$jump("com", "/view/ccg/008/a/index.xhtml");
								});
							}).always(() => {
								vm.$blockui("clear");
							});
					} else {
						vm.isUsed(false);
						vm.errorMessage(vm.$i18n.message(res.errorMessage));
						dfd.resolve();
					}
				}).fail((res) => {
					vm.isUsed(false);
					vm.errorMessage(vm.$i18n.message(res.messageId));
					dfd.resolve();
				}).always(() => {
					vm.$blockui("clear");
				});
			} else {
				vm.errorMessage(vm.$i18n.message(res.messageId));
			}
		});
		return dfd.promise();
	}

	setLoginInfo(): JQueryPromise<any> {
		let vm = this, dfd = $.Deferred<any>();
		vm.openDialogF({
			mode: 'admin',
			companyDesignation: false
		}).done((loginResult) => {
			if (!loginResult) {
				vm.isUsed(false);
				vm.errorMessage(vm.$i18n.text("Msg_1647"));
				dfd.resolve();
				return;
			}
			vm.loginInfo = loginResult;
			vm.openDialogK().done((selectedWP) => {
				if (!selectedWP) {
					vm.isUsed(false);
					vm.errorMessage(vm.$i18n.text("Msg_1647"));
					dfd.resolve();
					return;
				}
				vm.loginInfo.selectedWP = selectedWP;
				nts.uk.characteristics.save("loginKDP004", vm.loginInfo);
				dfd.resolve(vm.loginInfo);

			});
		});

		return dfd.promise();
	}

	public fingerAuth(): JQueryPromise<any> {
		let dfd = $.Deferred<any>(),
			vm = this;

		vm.$ajax(url.fingerAuth).then((res) => {
			dfd.resolve(res);
		});

		return dfd.promise();
	}

	public openDialogF(param): JQueryPromise<any> {
		let vm = this;
		let dfd = $.Deferred<any>();

		vm.$window.modal('at', '/view/kdp/003/f/index.xhtml', param).then(function(result): any {
			vm.$window.storage('form3LoginInfo').then((loginResult)=>{
				dfd.resolve(loginResult);
			})
			
		});

		return dfd.promise();
	}

	public openDialogK(): JQueryPromise<any> {
		let vm = this;
		let dfd = $.Deferred<any>();
		
			dfd.resolve('123');
		/*vm.$window.modal('at','/view/kdp/003/k/index.xhtml').then((result) => {
			let chooseItem = result.selectedId;
			dfd.resolve(chooseItem);
		});*/
		return dfd.promise();
	}

	public getPageLayout(pageNo: number) {
		let vm = this;
		let layout = _.find(vm.stampTab().layouts(), (ly) => { return ly.pageNo === pageNo });

		if (layout) {
			let btnSettings = layout.buttonSettings;
			btnSettings.forEach(btn => {
				btn.onClick = vm.clickBtn1;
			});
			layout.buttonSettings = btnSettings;
		}

		return layout;
	}

	public openScreenG(button, layout): JQueryPromise<IAuthResult> {
		let vm = this;
		let dfd = $.Deferred<any>();
		setShared('ModelGParam', { retry: vm.retry, errorMessage: vm.errorMessage() });
		vm.$window.modal('/view/kdp/003/k/index.xhtml', vm.retry).then((result) => {
			let redirect: "retry" | "loginPass" | "cancel" = getShared('actionName');
			if (redirect === "retry") {
				vm.retry = vm.retry + 1;
				vm.doAuthent(button, layout).done((res: IAuthResult) => {
					dfd.resolve(res);
				});
			}
			if (redirect === "loginPass") {
				vm.openDialogF().done((res) => {
					if (res) {
						vm.retry = 0;
					} else {
						vm.errorMessage(vm.$i18n.text("Msg_1647"));
					}
					dfd.resolve({ isSuccess: vm.isUsed(), authType: 2 });
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
		let vm = this;
		let dfd = $.Deferred<any>();

		vm.fingerAuth().done((res) => {
			if (res.result) {
				vm.$ajax(url.confirmUseOfStampInput, { employeeId: vm.loginInfo.employeeId, stampMeans: 1 }).then((res) => {
					vm.isUsed(res.used == 0);
					if (!vm.isUsed()) {
						vm.errorMessage(vm.$i18n.message(res.messageId));
					}
					dfd.resolve({ isSuccess: vm.isUsed(), authType: 0 });
				});

			} else {
				vm.errorMessage(vm.$i18n.message(res.messageId));
				vm.openScreenG(button, layout).done((res: IAuthResult) => {
					dfd.resolve(res);
				});

			}

		});

		return dfd.promise();
	}

	public registerData(button, layout, authcMethod) {
		let vm = this;

		vm.$ajax("com", "server/time/now/").then((res) => {
			let data = {
				employeeId: vm.loginInfo.employeeId,
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


			vm.$ajax(url.stampInput, data).then((res) => {
				if (vm.stampResultDisplay().notUseAttr == 1 && (button.changeClockArt == 1 || button.changeClockArt == 9)) {
					vm.openScreenC(button, layout);
				} else {
					vm.openScreenB(button, layout);
				}
			}).fail((res) => {
				vm.$dialog.error({ messageId: res.messageId });
			});
		});


	}

	public openScreenB(button, layout) {
		let vm = this;

		setShared("resultDisplayTime", vm.stampSetting().resultDisplayTime);
		setShared("infoEmpToScreenB", {
			employeeId: vm.$user.employeeId,
			employeeCode: vm.$user.employeeCode,
			mode: Mode.Personal,
		});

		vm.$window.modal('/view/kdp/002/b/index.xhtml').then(() => {

			vm.stampToSuppress.valueHasMutated();
			vm.openKDP002T(button, layout);
		});
	}

	public openScreenC(button, layout) {
		let vm = this;
		setShared('KDP010_2C', vm.stampResultDisplay().displayItemId, true);
		setShared("infoEmpToScreenC", {
			employeeId: vm.$user.employeeId,
			employeeCode: vm.$user.employeeCode,
			mode: Mode.Personal,
		});

		vm.$window.modal('/view/kdp/002/c/index.xhtml').then(function(): any {

			vm.stampToSuppress.valueHasMutated();
			vm.openKDP002T(button, layout);
		});
	}

	public openKDP002T(button: ButtonSetting, layout) {
		let vm = this;
		let data = {
			pageNo: layout.pageNo,
			buttonDisNo: button.btnPositionNo
		}
		vm.$ajax(url.getError, data).then((res) => {
			if (res && res.dailyAttdErrorInfos && res.dailyAttdErrorInfos.length > 0) {
				setShared('KDP010_2T', res, true);
				vm.$window.modal('/view/kdp/002/t/index.xhtml').then(function(): any {
					let returnData = getShared('KDP010_T');
					if (!returnData.isClose && returnData.errorDate) {
						console.log(returnData);
						// T1	打刻結果の取得対象項目の追加
						// 残業申請（早出）
						let transfer = returnData.btn.transfer;
						vm.$jump(returnData.btn.screen, transfer);
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