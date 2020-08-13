module nts.uk.at.view.kaf000_ref.b.component3.viewmodel {
    
    import UserType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.UserType;
    import Status = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.Status;
    
    @component({
        name: 'kaf000-b-component3',
        template: '/nts.uk.at.web/view/kaf_ref/000/b/component3/index.html'
    })
    class Kaf000BComponent3ViewModel extends ko.ViewModel {
        appDispInfoStartupOutput: any;
        approvalReason: KnockoutObservable<string>;
        dispApprovalReason: KnockoutObservable<boolean>;
        enableApprovalReason: KnockoutObservable<boolean>;
        created(params: any) {
            const vm = this;
            vm.approvalReason = params.approvalReason;
            vm.dispApprovalReason = ko.observable(false);
            vm.enableApprovalReason = ko.observable(false);
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput();
            
            let userTypeValue = vm.appDispInfoStartupOutput.appDetailScreenInfo.user;
            let state = vm.appDispInfoStartupOutput.appDetailScreenInfo.reflectPlanState;
            let canApprove = vm.appDispInfoStartupOutput.appDetailScreenInfo.authorizableFlags;
            let expired = vm.appDispInfoStartupOutput.appDetailScreenInfo.alternateExpiration;
            vm.dispApprovalReason((userTypeValue == UserType.APPLICANT_APPROVER || userTypeValue == UserType.APPROVER));
            vm.enableApprovalReason((state == Status.DENIAL || state == Status.WAITREFLECTION || state == Status.NOTREFLECTED || state == Status.REMAND)
                && canApprove
                && !expired);
        }
    
        mounted() {
            const vm = this;
        }
    }
}