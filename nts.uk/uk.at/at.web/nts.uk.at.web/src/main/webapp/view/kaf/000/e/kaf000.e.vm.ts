 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf000.e.viewmodel {
	import CommonProcess = nts.uk.at.view.kaf000.shr.viewmodel.CommonProcess;

    @bean()
    class Kaf000EViewModel extends ko.ViewModel {
		approvalReason: KnockoutObservable<string> = ko.observable("");
		appDispInfoStartupOutput: any;
		
        created(params: KAF000EParam) {
			const vm = this;
			vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
        }
		mounted() {
			const vm = this;
			$('#inpReasonTextareaApprovalReason').focus();
		}
		approve() {
			const vm = this;
            vm.$blockui("show");
            let memo = vm.approvalReason(),
            	appDispInfoStartupOutput = vm.appDispInfoStartupOutput,
            	command = { memo, appDispInfoStartupOutput };

            vm.$ajax(API.approve, command)
            .done((successData: any) => {
                vm.$dialog.info({ messageId: "Msg_220" }).then(() => {
					CommonProcess.handleMailResult(successData, vm).then(() => {
						let param = [successData.reflectAppId];
	                	nts.uk.request.ajax("at", API.reflectAppSingle, param);
	                    vm.$window.close({reload: true});
					});
                });
            }).fail((res: any) => {
                vm.$dialog.error({ messageId: res.messageId, messageParams: res.parameterIds }).then(() => {
					vm.$window.close({reload: true});
				});
            }).always(() => vm.$blockui("hide"));	
		}
		
		close() {
			const vm = this;
			vm.$window.close({reload: false});	
		}
    }

	export interface KAF000EParam {
		appDispInfoStartupOutput: any;	
	}

    const API = {
		approve: "at/request/application/approveapp",
		reflectAppSingle: "at/request/application/reflect-app"
    }
}