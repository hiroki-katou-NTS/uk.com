/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf000_ref.b.viewmodel {
    import Kaf007Process = nts.uk.at.view.kaf007_ref.shr.viewmodel.Kaf007Process;
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;

    @bean()
    class Kaf000BViewModel extends ko.ViewModel {
        listApp: Array<string>;
        currentApp: string;
        appType: KnockoutObservable<number>;
        appDispInfoStartupOutput: KnockoutObservable<any> = ko.observable(null);
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
        
        childUpdateEvent!: () => void;

        created(params: any) {
            const vm = this;
            vm.listApp = params.listApp;
            vm.currentApp = params.currentApp;
            vm.appType = ko.observable(99);
            vm.$blockui("show");
            vm.$ajax(`${API.getDetailPC}/${vm.currentApp}`).done((successData: any) => {
                vm.appType(successData.appDetailScreenInfo.application.appType);
                vm.appDispInfoStartupOutput(successData);
                vm.$blockui("hide");
            }).fail((failData: any) => {
                vm.$blockui("hide");
            });
        }

        mounted() {

        }

        back() {
            const vm = this;
            let index = _.findIndex(vm.listApp, vm.currentApp);
            if (index >= 0) {
                if (index > 0) {
                    vm.currentApp = vm.listApp[index - 1];
                }
            }
        }

        next() {
            const vm = this;
            let index = _.findIndex(vm.listApp, vm.currentApp);
            if (index >= 0) {
                if (index < vm.listApp.length - 1) {
                    vm.currentApp = vm.listApp[index + 1];
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
           
            // nếu component con có bind event ra
            if(_.isFunction(vm.childUpdateEvent)) {
                vm.childUpdateEvent();
            }
        }
    
        // Hàm phục vụ việc gọi lại trong viewmodel của component con
        // và cập nhật update function của component con ra ngoài
        getChildEvent(evt: () => void) {
            const vm = this;
            // gán event update của component vào childUpdateEvent
            vm.childUpdateEvent = evt;
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

    const API = {
        getDetailPC: "at/request/application/getDetailPC"
    }
}