/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf000.a.viewmodel {
    import CommonProcess = nts.uk.at.view.kaf000.shr.viewmodel.CommonProcess;
	import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;

    export class Kaf000AViewModel extends ko.ViewModel {
    	appDispInfoStartupOutput: KnockoutObservable<any> = ko.observable(CommonProcess.initCommonSetting());
    	
        loadData(empLst: Array<string>, dateLst: Array<string>, appType: AppType) {
            const vm = this;
            let command = { empLst, dateLst, appType };
            return vm.$ajax(API.startNew, command)
            .then((data: any) => {
                vm.appDispInfoStartupOutput(data);
                return CommonProcess.checkUsage(true, "#kaf000-a-component4-singleDate", vm);
            },(res: any) => {
                if (res.messageId == "Msg_426") {
                    vm.$dialog.error({ messageId: "Msg_426" }).then(() => {
                        vm.$jump("com", "/view/ccg/008/a/index.xhtml"); 
                    });    
                } else {
                    vm.$dialog.error(res.message).then(() => {
                        vm.$jump("com", "/view/ccg/008/a/index.xhtml"); 
                    }); 
                }
                return false;
            });
        }

		sendMailAfterRegister(mailServerSet: boolean, sendMailHandler: boolean) {
			const vm = this;
			return vm.$ajax(API.sendMailAfterRegister)
			.then((data: any) => {
				if(data) {
					if(data.isAutoSendMail) {
						let mailResult = [];
						mailResult.push({ value: data.autoSuccessMail, type: 'info' });
						mailResult.push({ value: data.autoFailMail, type: 'error' });
						mailResult.push({ value: data.autoFailServer, type: 'error' });
						CommonProcess.showMailResult(_.slice(mailResult, 1), vm).then(() => window.location.reload());
					}
					return data;
				}
			}).then((data: any) => {
				if(data) {
					if(sendMailHandler) {
						let command = { appID: data.appID };
						vm.$window.modal('/view/screen/a/index.xhtml', command)
	            		.then(() => window.location.reload());	
					}
				}
			});	
		}
		
		handleErrorCommon(failData: any) {
			const vm = this;
			if(failData.messageId == "Msg_324") {
				vm.$dialog.error({ messageId: failData.messageId, messageParams: failData.parameterIds });
				return;
			}	
			vm.$dialog.error({ messageId: failData.messageId, messageParams: failData.parameterIds });
		}
    }

    const API = {
        startNew: "at/request/application/getStartPC",
		sendMailAfterRegister: ""
    }
}