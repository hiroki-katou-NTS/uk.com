/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kafsample.a.viewmodel {
	import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
	import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
	import Kaf000AViewModel = nts.uk.at.view.kaf000.a.viewmodel.Kaf000AViewModel;
	import AppInitParam = nts.uk.at.view.kaf000.shr.viewmodel.AppInitParam;

	@bean()
	export class KafSampleViewModel extends Kaf000AViewModel {

		appType: KnockoutObservable<number> = ko.observable(AppType.WORK_CHANGE_APPLICATION);
		isAgentMode : KnockoutObservable<boolean> = ko.observable(false);
		application: KnockoutObservable<Application> = ko.observable(new Application(this.appType()));
		isSendMail: KnockoutObservable<Boolean>;

		created(params: AppInitParam) {
			const vm = this;
			let empLst: Array<string> = [],
				dateLst: Array<string> = [];
			if (!_.isEmpty(params)) {
				if (!_.isEmpty(params.employeeIds)) {
					empLst = params.employeeIds;
				}
				if (!_.isEmpty(params.baseDate)) {
					let paramDate = moment(params.baseDate).format('YYYY/MM/DD');
					dateLst = [paramDate];
					vm.application().appDate(paramDate);
					vm.application().opAppStartDate(paramDate);
                    vm.application().opAppEndDate(paramDate);
				}
				if (params.isAgentMode) {
					vm.isAgentMode(params.isAgentMode);
				}
			}
			vm.isSendMail = ko.observable(false);

			vm.$blockui("show");
			vm.loadData(empLst, dateLst, vm.appType())
				.then((loadDataFlag: any) => {
					if (loadDataFlag) {
						let appDispInfoStartupOutput = ko.toJS(vm.appDispInfoStartupOutput),
							command = { empLst, dateLst, appDispInfoStartupOutput };
						return vm.$ajax(API.startNew, command);
					}
				}).then((successData: any) => {
					if (successData) {
						if (!_.isEmpty(params)) {
							if (!_.isEmpty(params.baseDate)) {
								
							}
						}
					}
				}).fail((failData: any) => {
					console.log(failData);
					if (failData.messageId === "Msg_43") {
						vm.$dialog.error(failData).then(() => { vm.$jump("com", "/view/ccg/008/a/index.xhtml"); });

					} else {
						vm.$dialog.error(failData);
					}
				}).always(() => {vm.$blockui("hide"); $('#kaf000-a-component4-singleDate').focus();});
		}

		mounted() {
			const vm = this;
		}
		
		register() {
			const vm = this;

			vm.$blockui("show");
			
			vm.$validate('#kaf000-a-component4 .nts-input', '#kaf000-a-component3-prePost', '#kaf000-a-component5-comboReason')
				.then(isValid => {
					if (isValid) {
						return true;
					}
				})
				.then(result => {
					if(result) {
						return vm.$ajax(API.checkBeforeRegisterSample, ["Msg_234"]);
					}
				}).then(res => {
//					if (res) {
//						if (!_.isEmpty(res.holidayDateLst)) {
//							holidayDateLst = res.holidayDateLst;
//						}
//
//						return vm.handleConfirmMessage(_.clone(res.confirmMsgLst), command);
//					};
				}).then((result) => {
//					if(result) {
//						return vm.registerData(command);
//					};
				})
				.done(result => {
					
				})
				.fail(err => {
					let messageId, messageParams;
					if(err.errors) {
						let errors = err.errors;
						messageId = errors[0].messageId;
					} else {
						messageId = err.messageId;
						messageParams = [err.parameterIds.join('、')];
					}
					vm.$dialog.error({ messageId: messageId, messageParams: messageParams });
				})
				.always(() => vm.$blockui("hide"));
		}

		handleConfirmMessage(listMes: any, vmParam: any): any {
			const vm = this;

			return new Promise((resolve: any) => {
				if(_.isEmpty(listMes)) {
					resolve(true);
				}
				let msg = listMes[0].value;

				return vm.$dialog.confirm({ messageId: msg.msgID, messageParams: msg.paramLst })
					.then((value) => {
						if (value === 'yes') {
							return vm.handleConfirmMessage(listMes, vmParam);
						} else {
							resolve(false);
						}
					})
	        });
		}

		registerData(params: any): any {
			let vm = this;

			return vm.$ajax(API.register, params);
		}
	}

	const API = {
		startNew: "at/request/application/workchange/startNew",
		registerSample: "at/request/application/registerSample",
		checkBeforeRegisterSample: "at/request/application/checkBeforeRegisterSample"
	}
}