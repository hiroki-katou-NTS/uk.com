module nts.uk.at.view.kaf011.b.viewmodel {
    import PrintContentOfEachAppDto = nts.uk.at.view.kaf000.shr.viewmodel.PrintContentOfEachAppDto;
	
	import Kaf000AViewModel = nts.uk.at.view.kaf000.a.viewmodel.Kaf000AViewModel;
	import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
	import Application = nts.uk.at.view.kaf011.Application;
	import RecruitmentApp = nts.uk.at.view.kaf011.RecruitmentApp;
	import AbsenceLeaveApp = nts.uk.at.view.kaf011.AbsenceLeaveApp;
	import Comment = nts.uk.at.view.kaf011.Comment;
	import getText = nts.uk.resource.getText;
	import block = nts.uk.ui.block;
	import ajax = nts.uk.request.ajax;

    export class Kaf011BViewModel{

        appType: KnockoutObservable<number> = ko.observable(AppType.COMPLEMENT_LEAVE_APPLICATION);
		applicationCommon: KnockoutObservable<Application> = ko.observable(new Application());
        appDispInfoStartupOutput: any;
        application: KnockoutObservable<Application>;
        approvalReason: KnockoutObservable<string>;
        printContentOfEachAppDto: KnockoutObservable<PrintContentOfEachAppDto>;
		time: KnockoutObservable<number> = ko.observable(1);
		appCombinaSelected = ko.observable(0);
		recruitmentApp = new RecruitmentApp(0, false);
		absenceLeaveApp = new AbsenceLeaveApp(1, false);
		comment = new Comment();
		displayInforWhenStarting = ko.observable(null);
		remainDays = ko.observable('');
		
        constructor(
            params: {
                appType: any,
                application: any,
                printContentOfEachAppDto: PrintContentOfEachAppDto,
                approvalReason: any,
                appDispInfoStartupOutput: any,
                eventUpdate: (evt: () => void) => void,
                eventReload: (evt: () => void) => void
            }
        ) {
            const vm = this;
			vm.appType = params.appType;
			vm.application = params.application;
            vm.printContentOfEachAppDto = ko.observable(params.printContentOfEachAppDto);
            vm.approvalReason = params.approvalReason;
			vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            params.eventUpdate(vm.update.bind(vm));
            params.eventReload(vm.reload.bind(vm));
			if(vm.application().appID()!= null && params.appType() == AppType.COMPLEMENT_LEAVE_APPLICATION){
				vm.loadData();
			}			
        }
		
		loadData(){
			const vm = this;
			block.invisible();
				ajax('at/request/application/holidayshipment/startPageBRefactor',{appID: vm.application().appID(), appDispInfoStartupDto: vm.appDispInfoStartupOutput()}).then((data: any) =>{
					console.log(data);
					vm.displayInforWhenStarting(data);
					vm.remainDays(data.remainingHolidayInfor.remainDays + '日');
					if(data.rec && data.abs){
						vm.recruitmentApp.bindingScreenB(data.rec, data.applicationForWorkingDay.workTypeList, data);
						vm.absenceLeaveApp.bindingScreenB(data.abs, data.applicationForHoliday.workTypeList, data);	
					}else if(data.rec){
						vm.appCombinaSelected(1);
						vm.recruitmentApp.bindingScreenB(data.rec, data.applicationForWorkingDay.workTypeList, data);
					}else if(data.abs){
						vm.appCombinaSelected(2);
						vm.absenceLeaveApp.bindingScreenB(data.abs, data.applicationForHoliday.workTypeList, data);
					}
					
				}).fail((failData: any) => {
					
				}).always(() => {
                    block.clear();
                });
		}

        reload() {
            const vm = this;
            if (vm.appType() == vm.application().appType) {
            }
        }

        // event update cần gọi lại ở button của view cha
        update() {
            const vm = this;
			let command = {};
            vm.$blockui("show");
            let dfd = $.Deferred();
			// validate chung KAF000
			 vm.$validate('#kaf000-a-component4 .nts-input', '#kaf000-a-component3-prePost', '#kaf000-a-component5-comboReason')
            .then((isValid) => {
                if (isValid) {
					// validate riêng cho màn hình
                    return true;
                }
            }).then((result) => {
				// check trước khi update
                if (result) {
					return vm.$ajax(API.checkBeforeUpdateSample, ["Msg_197"]);
                }
            }).then((result) => {
                if (result) {
					// xử lý confirmMsg
                	return vm.handleConfirmMessage(result);
                }
            }).then((result) => {
                if (result) {
					// update
                	return vm.$ajax('at', API.updateSample, ["Msg_15"]).then(() => {
						return vm.$dialog.info({ messageId: "Msg_15"}).then(() => {
							return true;
						});	
					});
                }
            }).then((result) => {
                if(result) {
					// gửi mail sau khi update
					// return vm.$ajax('at', API.sendMailAfterUpdateSample);
					return true;
				}	
            }).then((result) => {
                if(result) {
					return dfd.resolve(true);
				}	
				return dfd.resolve(result);
            }).fail((failData) => {
				// xử lý lỗi nghiệp vụ riêng
				vm.handleErrorCustom(failData).then((result: any) => {
					if(result) {
						return dfd.reject(failData);	
					}	
					return dfd.reject(false);
				});
			});
			return dfd.promise();
        }

		handleErrorCustom(failData: any): any {
			const vm = this;
			if(failData.messageId == "Msg_26") {
				return vm.$dialog.error({ messageId: failData.messageId, messageParams: failData.parameterIds })
				.then(() => {
					return $.Deferred().resolve(false);	
				});	
			}
			return $.Deferred().resolve(true);
		}

		handleConfirmMessage(listMes: any): any {
			const vm = this;
			if(_.isEmpty(listMes)) {
				return $.Deferred().resolve(true);
			}
			let msg = listMes[0];

			return vm.$dialog.confirm({ messageId: msg.msgID, messageParams: msg.paramLst })
			.then((value) => {
				if (value === 'yes') {
					return vm.handleConfirmMessage(_.drop(listMes));
				} else {
					return $.Deferred().resolve(false);
				}
			});
		}
    }

    const API = {
        initAppDetail: "at/request/application/initApp",
        checkBeforeUpdateSample: "at/request/application/checkBeforeSample",
        updateSample: "at/request/application/changeDataSample",
		sendMailAfterUpdateSample: ""
    }

}