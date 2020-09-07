module nts.uk.at.view.kaf007_ref.a.viewmodel {
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;
    import AppWorkChange = nts.uk.at.view.kaf007_ref.shr.viewmodel.AppWorkChange; 
    
    @bean()
    class Kaf007AViewModel extends ko.ViewModel {
        
        application: KnockoutObservable<Application>;
        appWorkChange: KnockoutObservable<AppWorkChange>;
        appDispInfoStartupOutput: any;
    
        created(params: any) {
            const vm = this;
            vm.application = ko.observable(new Application("", 1, 2, ""));
            vm.appWorkChange = ko.observable(new AppWorkChange(100, 200));
            vm.appDispInfoStartupOutput = ko.observable(CommonProcess.initCommonSetting());
        }
    
        mounted() {
            const vm = this;
            vm.application().appDate(moment(new Date()).format("YYYY/MM/DD"));
            vm.$ajax(API.startNew, {
                empLst: [],
                dateLst: []    
            }).done((successData: any) => {
                vm.appDispInfoStartupOutput(successData.appDispInfoStartupOutput);
            });
        }
        
        register() {
            const vm = this;
            console.log(ko.toJS(vm.application()));
            console.log(ko.toJS(vm.appWorkChange()));
        }
    }
    
    const API = {
        startNew: "at/request/application/workchange/startNew"
    }
}