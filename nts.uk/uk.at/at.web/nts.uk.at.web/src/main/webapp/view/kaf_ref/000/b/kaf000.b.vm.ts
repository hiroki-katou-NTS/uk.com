module nts.uk.at.view.kaf000_ref.b.viewmodel {
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import ApplicationSub = nts.uk.at.view.kaf000_ref.shr.viewmodel.ApplicationSub;
    import AppWorkChange = nts.uk.at.view.kaf007_ref.shr.viewmodel.AppWorkChange; 
    import Kaf007Process = nts.uk.at.view.kaf007_ref.shr.viewmodel.Kaf007Process;
    
    @bean()
    class Kaf000BViewModel extends ko.ViewModel {    
        listAppSub: KnockoutObservableArray<ApplicationSub>;
        currentAppSub: KnockoutObservable<ApplicationSub>;
        displayApprovalButton: KnockoutObservable<boolean> = ko.observable(true);
        enableApprovalButton: KnockoutObservable<boolean> = ko.observable(true);
        displayApprovalLabel: KnockoutObservable<boolean> = ko.observable(false);
        displayDenyButton: KnockoutObservable<boolean> = ko.observable(true);
        enableDenyButton: KnockoutObservable<boolean> = ko.observable(true);
        displayDenyLabel: KnockoutObservable<boolean> = ko.observable(false);
        displayReleaseButton: KnockoutObservable<boolean> = ko.observable(true);
        enableReleaseButton: KnockoutObservable<boolean> = ko.observable(true);
        displayRemandButton: KnockoutObservable<boolean> = ko.observable(true);
        enableRemandButton: KnockoutObservable<boolean> = ko.observable(true);
        displayUpdateButton: KnockoutObservable<boolean> = ko.observable(true);
        enableUpdateButton: KnockoutObservable<boolean> = ko.observable(true);
        displayDeleteButton: KnockoutObservable<boolean> = ko.observable(true);
        enableDeleteButton: KnockoutObservable<boolean> = ko.observable(true);
        displayCancelButton: KnockoutObservable<boolean> = ko.observable(true);
        enableCancelButton: KnockoutObservable<boolean> = ko.observable(true);
        errorEmpty: KnockoutObservable<boolean> = ko.observable(true);
        
        kaf007ViewModel: any; 
    
        created(params: any) {
            const vm = this;
            vm.listAppSub = ko.observableArray([
                new ApplicationSub("app1", 0),
                new ApplicationSub("app2", 1),
                new ApplicationSub("app3", 2),
            ]);
            vm.currentAppSub = ko.observable(new ApplicationSub("app1", 0));
            vm.kaf007ViewModel = {
                application: ko.observable(new Application("", 0, 2, "")),
                appWorkChange: ko.observable(new AppWorkChange(0, 0))        
            }
        }
    
        mounted() {
            
        }
        
        back() {
            const vm = this;    
            let index = _.findIndex(vm.listAppSub(), vm.currentAppSub());
            if(index >= 0) {
                if(index > 0) {
                    vm.currentAppSub(vm.listAppSub()[index - 1]);    
                }    
            }
        }
        
        next() {
            const vm = this;
            let index = _.findIndex(vm.listAppSub(), vm.currentAppSub());
            if(index >= 0) {
                if(index < vm.listAppSub().length - 1) {
                    vm.currentAppSub(vm.listAppSub()[index + 1]);    
                }    
            }
        }
        
        btnApprove() {
            
        }
        
        btnDeny() {
            
        }
        
        btnRelease() {
            
        }
        
        btnRemand() {
            
        }
        
        updateData() {
            const vm = this;
            switch(vm.currentAppSub().appType) {
                case 2: {
                    console.log(ko.toJS(vm.kaf007ViewModel));
                    Kaf007Process.update();    
                    break;
                }  
                default: console.log("not yet");  
            }
        }
        
        btnReferences() {
            
        }
        
        btnSendEmail() {
            
        }
        
        btnDelete() {
            
        }
        
        btnCancel() {
            
        }
    }
}