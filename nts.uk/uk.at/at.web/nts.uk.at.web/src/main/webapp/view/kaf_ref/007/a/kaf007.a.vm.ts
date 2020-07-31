/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf007_ref.a.viewmodel {
	import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
	import AppWorkChange = nts.uk.at.view.kaf007_ref.shr.viewmodel.AppWorkChange;
	import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;
	import Kaf000AViewModel = nts.uk.at.view.kaf000_ref.a.viewmodel.Kaf000AViewModel;
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;

	@bean()
	export class Kaf007AViewModel extends Kaf000AViewModel {

		application: KnockoutObservable<Application> = ko.observable(new Application(AppType.WORK_CHANGE_APPLICATION));
		appWorkChange: KnockoutObservable<AppWorkChange> = ko.observable(new AppWorkChange("001", "001", 100, 200));

		created(params: any) {
			const vm = this;
            vm.$blockui("show");
            vm.loadData([], [])
            .then((loadDataFlag: any) => {
                if(loadDataFlag) {
                    let empLst = [],
                        dateLst = [],
                        appDispInfoStartupOutput = ko.toJS(vm.appDispInfoStartupOutput),
                        command = { empLst, dateLst, appDispInfoStartupOutput };
                    return vm.$ajax(API.startNew, command);
                }
            }).then((successData: any) => {
                if(successData) {
                    console.log(successData);        
                }
            }).fail((failData: any) => {
                console.log(failData);         
            }).always(() => vm.$blockui("hide"));
		}

		register() {
            const vm = this;
            let workChange = ko.toJS(vm.appWorkChange),
                application = ko.toJS(vm.application),
                appDispInfoStartupOutput = ko.toJS(vm.appDispInfoStartupOutput),
                command = { workChange, application, appDispInfoStartupOutput };
			vm.$validate().then((valid: boolean) => {
				if(valid) {
					return vm.$blockui("show").then(() => vm.$ajax(API.register, command));
				}
			}).done((data: any) => {
				if(data) {
					vm.$dialog.info({ messageId: "Msg_15" });	
				}
			})
        	.always(() => vm.$blockui("hide"));
		}
	}

	const API = {
		startNew: "at/request/application/workchange/startNew",
		register: "at/request/application/workchange/addworkchange_PC"
	}
}