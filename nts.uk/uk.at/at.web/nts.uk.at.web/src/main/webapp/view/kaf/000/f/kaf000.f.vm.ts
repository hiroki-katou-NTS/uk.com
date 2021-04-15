 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf000.f.viewmodel {
	import CommonProcess = nts.uk.at.view.kaf000.shr.viewmodel.CommonProcess;

    @bean()
    class Kaf000FViewModel extends ko.ViewModel {
		approvalReason: KnockoutObservable<string> = ko.observable("");
		appDispInfoStartupOutput: any;
		
        created(params: KAF000FParam) {
			const vm = this;
			vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
        }
		mounted() {
			
			$('#inpReasonTextareaDeny').focus();
		}

		deny() {
			const vm = this;
            vm.$blockui("show");
            let memo = vm.approvalReason(),
            	appDispInfoStartupOutput = vm.appDispInfoStartupOutput,
            	command = { memo, appDispInfoStartupOutput };

            vm.$ajax(API.deny, command)
            .done((successData: any) => {
                if(successData.processDone) {
                    vm.$dialog.info({ messageId: "Msg_222" }).then(() => {
						CommonProcess.handleMailResult(successData, vm).then(() => {
		                	vm.$window.close({reload: true});
						});
                    });
                }
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

	export interface KAF000FParam {
		appDispInfoStartupOutput: any;
	}

    const API = {
		deny: "at/request/application/denyapp",
		reflectAppSingle: "at/request/application/reflect-app"
    }
}