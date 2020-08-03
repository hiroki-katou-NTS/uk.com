/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf000_ref.a.viewmodel {
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;
	import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;

    export class Kaf000AViewModel extends ko.ViewModel {
    	appDispInfoStartupOutput: KnockoutObservable<any> = ko.observable(CommonProcess.initCommonSetting());
    	
        loadData(empLst: Array<string>, dateLst: Array<string>, appType: AppType) {
            const vm = this;
            let command = { empLst, dateLst, appType };
            return vm.$ajax(API.startNew, command)
            .then((data: any) => {
                vm.appDispInfoStartupOutput(data);
                return CommonProcess.checkUsage(true, "#kaf000-a-component4-singleDate", vm);
            },() => {
                return false;
            });
        }
    }

    const API = {
        startNew: "at/request/application/getStartPC"
    }
}