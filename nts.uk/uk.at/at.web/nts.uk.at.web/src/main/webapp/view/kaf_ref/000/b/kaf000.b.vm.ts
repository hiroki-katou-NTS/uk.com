/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf000_ref.b.viewmodel {
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;
    import UserType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.UserType;
    import Status = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.Status;
    import ApprovalAtr = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.ApprovalAtr;

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
                let loginID = __viewContext.user.employeeId;
                let loginFlg = successData.appDetailScreenInfo.application.enteredPerson == loginID || successData.appDetailScreenInfo.application.employeeID == loginID;
                vm.setControlButton(
                    successData.appDetailScreenInfo.user,
                    successData.appDetailScreenInfo.approvalATR,
                    successData.appDetailScreenInfo.reflectPlanState,
                    successData.appDetailScreenInfo.authorizableFlags,
                    successData.appDetailScreenInfo.alternateExpiration,
                    loginFlg);
                vm.$blockui("hide");
            }).fail((failData: any) => {
                vm.$blockui("hide");
            });
        }
    
        setControlButton(
            userTypeValue: any, // phân loại người dùng
            approvalAtrValue: any, // trạng thái approval của Phase
            state: any, // trạng thái đơn
            canApprove: any,  // có thể bấm nút approval không true, false
            expired: any, // phân biệt thời hạn
            loginFlg: any // login có phải người viết đơn/ người xin hay k
        ) {
            const vm = this;
            vm.displayApprovalButton((userTypeValue == UserType.APPLICANT_APPROVER || userTypeValue == UserType.APPROVER)
                && (approvalAtrValue != ApprovalAtr.APPROVED));
            vm.enableApprovalButton((state == Status.DENIAL || state == Status.NOTREFLECTED || state == Status.REMAND)
                && canApprove
                && !expired);

            vm.displayDenyButton((userTypeValue == UserType.APPLICANT_APPROVER || userTypeValue == UserType.APPROVER)
                && (approvalAtrValue != ApprovalAtr.DENIAL));
            vm.enableDenyButton((state == Status.DENIAL || state == Status.WAITREFLECTION || state == Status.NOTREFLECTED || state == Status.REMAND)
                && canApprove
                && !expired);

            vm.displayRemandButton((userTypeValue == UserType.APPLICANT_APPROVER || userTypeValue == UserType.APPROVER));
            vm.enableRemandButton(
                ((state == Status.DENIAL || state == Status.NOTREFLECTED || state == Status.REMAND)
                    && canApprove
                    && !expired) ||
                (state == Status.WAITREFLECTION
                    && canApprove
                    && (approvalAtrValue == ApprovalAtr.APPROVED || approvalAtrValue == ApprovalAtr.DENIAL)
                    && !expired)
            );

            vm.displayReleaseButton((userTypeValue == UserType.APPLICANT_APPROVER || userTypeValue == UserType.APPROVER));
            vm.enableReleaseButton((state == Status.DENIAL || state == Status.WAITREFLECTION || state == Status.NOTREFLECTED || state == Status.REMAND)
                && canApprove
                && (approvalAtrValue == ApprovalAtr.APPROVED || approvalAtrValue == ApprovalAtr.DENIAL));

            vm.displayUpdateButton((userTypeValue == UserType.APPLICANT_APPROVER || userTypeValue == UserType.APPLICANT || userTypeValue == UserType.OTHER));
            vm.enableUpdateButton(state == Status.NOTREFLECTED || state == Status.REMAND);

            vm.displayDeleteButton((userTypeValue == UserType.APPLICANT_APPROVER || userTypeValue == UserType.APPLICANT || userTypeValue == UserType.OTHER));
            vm.enableDeleteButton(state == Status.NOTREFLECTED || state == Status.REMAND);

            vm.displayCancelButton((userTypeValue == UserType.APPLICANT_APPROVER || userTypeValue == UserType.APPLICANT || userTypeValue == UserType.OTHER)
                && loginFlg);
            vm.enableCancelButton(state == Status.REFLECTED);

            vm.displayApprovalLabel((userTypeValue == UserType.APPLICANT_APPROVER || userTypeValue == UserType.APPROVER)
                && (approvalAtrValue == ApprovalAtr.APPROVED));

            vm.displayDenyLabel((userTypeValue == UserType.APPLICANT_APPROVER || userTypeValue == UserType.APPROVER)
                && (approvalAtrValue == ApprovalAtr.DENIAL));    
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
        getChildUpdateEvent(evt: () => void) {
            const vm = this;
            // gán event update của component vào childUpdateEvent
            vm.childUpdateEvent = evt;
        }

        btnReferences() {

        }

        btnSendEmail() {

        }

        btnDelete() {
            const vm = this;
            vm.$blockui("show");
            vm.$ajax(API.deleteapp, ko.toJS(vm.appDispInfoStartupOutput())
            ).done((successData: any) => {
                vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
                    vm.$blockui("hide");
                });
            }).fail((failData: any) => {
                vm.$blockui("hide");    
            });
        }

        btnCancel() {

        }
    }

    const API = {
        getDetailPC: "at/request/application/getDetailPC",
        deleteapp: "at/request/application/deleteapp"
    }
}