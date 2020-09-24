module nts.uk.at.view.kaf000.b.component3.viewmodel {

    import UserType = nts.uk.at.view.kaf000.shr.viewmodel.model.UserType;
    import Status = nts.uk.at.view.kaf000.shr.viewmodel.model.Status;

    @component({
        name: 'kaf000-b-component3',
        template: `
            <div id="kaf000-b-component3">
                <div class="table" data-bind="if: dispApprovalReason">
                    <div class="cell col-1">
                        <div class="cell valign-center" data-bind="ntsFormLabel: {}, text: $i18n('KAF000_28')"></div>
                    </div>
                    <div class="cell valign-center">
                        <textarea style="height: 80px;" id="inpReasonTextarea"
                            data-bind="ntsMultilineEditor: {
                                        name: $i18n('KAF000_28'),
                                        value: approvalReason,
                                        option: {
                                            resizeable: false,
                                            width: '450',
                                            textalign: 'left'
                                        },
                                        required : false,
                                        enable: enableApprovalReason }" />
                    </div>
                </div>
            </div>
        `
    })
    class Kaf000BComponent3ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
        approvalReason: KnockoutObservable<string>;
        dispApprovalReason: KnockoutObservable<boolean>;
        enableApprovalReason: KnockoutObservable<boolean>;
        created(params: any) {
            const vm = this;
			vm.appType = params.appType;
            vm.approvalReason = params.approvalReason;
            vm.dispApprovalReason = ko.observable(false);
            vm.enableApprovalReason = ko.observable(false);
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;

            let userTypeValue = vm.appDispInfoStartupOutput().appDetailScreenInfo.user;
            let state = vm.appDispInfoStartupOutput().appDetailScreenInfo.reflectPlanState;
            let canApprove = vm.appDispInfoStartupOutput().appDetailScreenInfo.authorizableFlags;
            let expired = vm.appDispInfoStartupOutput().appDetailScreenInfo.alternateExpiration;
            vm.dispApprovalReason((userTypeValue == UserType.APPLICANT_APPROVER || userTypeValue == UserType.APPROVER));
            vm.enableApprovalReason((state == Status.DENIAL || state == Status.WAITREFLECTION || state == Status.NOTREFLECTED || state == Status.REMAND)
                && canApprove
                && !expired);

			vm.appDispInfoStartupOutput.subscribe(value => {
            	let userTypeValue = value.appDetailScreenInfo.user;
	            let state = value.appDetailScreenInfo.reflectPlanState;
	            let canApprove = value.appDetailScreenInfo.authorizableFlags;
	            let expired = value.appDetailScreenInfo.alternateExpiration;
	            vm.dispApprovalReason((userTypeValue == UserType.APPLICANT_APPROVER || userTypeValue == UserType.APPROVER));
	            vm.enableApprovalReason((state == Status.DENIAL || state == Status.WAITREFLECTION || state == Status.NOTREFLECTED || state == Status.REMAND)
	                && canApprove
	                && !expired);
            });
        }

        mounted() {
            const vm = this;
        }
    }
}