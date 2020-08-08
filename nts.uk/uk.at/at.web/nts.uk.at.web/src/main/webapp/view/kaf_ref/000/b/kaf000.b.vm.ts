/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf000_ref.b.viewmodel {
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;
    import UserType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.UserType;
    import Status = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.Status;
    import ApprovalAtr = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.ApprovalAtr;
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
	import PrintContentOfEachAppDto = nts.uk.at.view.kaf000_ref.shr.viewmodel.PrintContentOfEachAppDto;

    @bean()
    class Kaf000BViewModel extends ko.ViewModel {
        listApp: Array<string>;
        currentApp: string;
        appType: KnockoutObservable<number>;
        appDispInfoStartupOutput: KnockoutObservable<any> = ko.observable(null);
        application: KnockoutObservable<Application> = ko.observable(new Application(0));
        approvalReason: KnockoutObservable<string> = ko.observable("");
		opPrintContentOfEachApp: PrintContentOfEachAppDto = {
			opPrintContentOfWorkChange: null,
			opAppStampOutput: null,
			opArrivedLateLeaveEarlyInfo: null,
			opInforGoBackCommonDirectOutput: null,
		};
        childParam: any = {};

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
            vm.childParam = {
            	application: vm.application,
				printContentOfEachAppDto: vm.opPrintContentOfEachApp,
                approvalReason: vm.approvalReason,
                appDispInfoStartupOutput: vm.appDispInfoStartupOutput,
                eventUpdate: function(a) { vm.getChildUpdateEvent.apply(vm, [a]) }
            }
            vm.loadData();
        }

        loadData() {
            const vm = this;
            vm.$blockui("show");
            vm.$ajax(`${API.getDetailPC}/${vm.currentApp}`).done((successData: any) => {
            	vm.approvalReason("");
                vm.appType(successData.appDetailScreenInfo.application.appType);
                vm.application().appID = successData.appDetailScreenInfo.application.appID;
		        vm.application().appType = vm.appType();
		        vm.application().opAppReason(successData.appDetailScreenInfo.application.opAppReason);
		        vm.application().opAppStandardReasonCD(successData.appDetailScreenInfo.application.opAppStandardReasonCD);
		        vm.application().opReversionReason(successData.appDetailScreenInfo.application.opReversionReason);
                vm.appDispInfoStartupOutput(successData);
                let viewContext: any = __viewContext,
                    loginID = viewContext.user.employeeId,
                    loginFlg = successData.appDetailScreenInfo.application.enteredPerson == loginID || successData.appDetailScreenInfo.application.employeeID == loginID;
                vm.setControlButton(
                    successData.appDetailScreenInfo.user,
                    successData.appDetailScreenInfo.approvalATR,
                    successData.appDetailScreenInfo.reflectPlanState,
                    successData.appDetailScreenInfo.authorizableFlags,
                    successData.appDetailScreenInfo.alternateExpiration,
                    loginFlg);
            }).fail((res: any) => {
                vm.handlerExecuteErrorMsg(res);
            }).always(() => vm.$blockui("hide"));
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
			const vm = this;
            vm.$blockui("show");
            let memo = vm.approvalReason(),
            	appDispInfoStartupOutput = vm.appDispInfoStartupOutput(),
            	command = { memo, appDispInfoStartupOutput };

            vm.$ajax(API.approve, command)
            .done((successData: any) => {
                vm.$dialog.info({ messageId: "Msg_220" }).then(() => {
                    vm.loadData();
                });
            }).fail((res: any) => {
                vm.handlerExecuteErrorMsg(res);
            }).always(() => vm.$blockui("hide"));
        }

        btnDeny() {
			const vm = this;
            vm.$blockui("show");
            let memo = vm.approvalReason(),
            	appDispInfoStartupOutput = vm.appDispInfoStartupOutput(),
            	command = { memo, appDispInfoStartupOutput };

            vm.$ajax(API.deny, command)
            .done((successData: any) => {
                if(successData.processDone) {
                    vm.$dialog.info({ messageId: "Msg_222" }).then(() => {
                        vm.loadData();
                    });
                }
            }).fail((res: any) => {
                vm.handlerExecuteErrorMsg(res);
            }).always(() => vm.$blockui("hide"));
        }

        btnRelease() {
			const vm = this;
            vm.$blockui("show");
            vm.$dialog.confirm({ messageId: "Msg_248" }).then((result: 'no' | 'yes' | 'cancel') => {
                if (result === 'yes') {
                    return vm.$ajax(API.release, ko.toJS(vm.appDispInfoStartupOutput()));
                }
            }).done((successData: any) => {
                if(successData.processDone) {
                    vm.$dialog.info({ messageId: "Msg_221" }).then(() => {
                        vm.loadData();
                    });
                }
            }).fail((res: any) => {
                vm.handlerExecuteErrorMsg(res);
            }).always(() => vm.$blockui("hide"));
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
            vm.$dialog.confirm({ messageId: "Msg_18" }).then((result: 'no' | 'yes' | 'cancel') => {
                if (result === 'yes') {
                    return vm.$ajax(API.deleteapp, ko.toJS(vm.appDispInfoStartupOutput()));
                }
            }).done((successData: any) => {
                vm.$dialog.info({ messageId: "Msg_16" }).then(() => {
                    vm.$jump("at", "/view/cmm/045/a/index.xhtml");
                });
            }).fail((res: any) => {
                vm.handlerExecuteErrorMsg(res);
            }).always(() => vm.$blockui("hide"));

        }

        btnCancel() {
            const vm = this;
            vm.$blockui("show");
            vm.$dialog.confirm({ messageId: "Msg_249" }).then((result: 'no' | 'yes' | 'cancel') => {
                if (result === 'yes') {
                    return vm.$ajax(API.deleteapp, ko.toJS(vm.appDispInfoStartupOutput()));
                }
            }).done((successData: any) => {
                vm.$dialog.info({ messageId: "Msg_224" }).then(() => {
                    vm.loadData();
                });
            }).fail((res: any) => {
                vm.handlerExecuteErrorMsg(res);
            }).always(() => vm.$blockui("hide"));
        }

        handlerExecuteErrorMsg(res: any) {
            const vm = this;
            switch(res.messageId) {
            case "Msg_426":
                vm.$dialog.error({ messageId: "Msg_426" }).then(() => {
                    vm.$jump("at", "/view/cmm/045/a/index.xhtml");
                });
                break;
            case "Msg_197":
                vm.$dialog.error({ messageId: "Msg_197" }).then(() => {
                    vm.loadData();
                });
                break;
            case "Msg_198":
                vm.$dialog.error({ messageId: "Msg_198" }).then(() => {
                    vm.$jump("at", "/view/cmm/045/a/index.xhtml");
                });
                break;
            default:
                vm.$dialog.error(res.message).then(() => {
                    vm.$jump("com", "/view/ccg/008/a/index.xhtml");
                });
                break;
            }
        }

        btnPrint() {
            const vm = this;
            vm.$blockui("show");
            let appDispInfoStartupOutput = ko.toJS(vm.appDispInfoStartupOutput()),
				opPrintContentOfEachApp = vm.opPrintContentOfEachApp,
                command = { appDispInfoStartupOutput, opPrintContentOfEachApp };
            nts.uk.request.exportFile("at", API.print, command)
            .done((successData: any) => {

            }).fail((res: any) => {
                vm.handlerExecuteErrorMsg(res);
            }).always(() => vm.$blockui("hide"));
        }
    }

    const API = {
        getDetailPC: "at/request/application/getDetailPC",
        deleteapp: "at/request/application/deleteapp",
        approve: "at/request/application/approveapp",
    	deny: "at/request/application/denyapp",
        release: "at/request/application/releaseapp",
        cancel: "at/request/application/cancelapp",
        print: "at/request/application/print"
    }
}