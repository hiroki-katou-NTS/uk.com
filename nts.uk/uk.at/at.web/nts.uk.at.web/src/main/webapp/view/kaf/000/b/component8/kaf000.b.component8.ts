module nts.uk.at.view.kaf000.b.component8.viewmodel {

    @component({
        name: 'kaf000-b-component8',
        template: `
            <div id="kaf000-b-component8" data-bind="click: openApproverDetail">
				<div style="display: inline-block; width: calc(100% - 30px);" data-bind="if: approvalRootStateShort().length != 0">
					<div data-bind="foreach: approvalRootStateShort">
						<div class="approver-block">
							<div data-bind="text: $component.getApproverLabel($index())"></div>
							<div style="min-width: 112px;">
								<div class="limited-label" style="padding-right: 4px;" data-bind="text: $data.approverName"></div>
							</div>
							<div data-bind="if: $data.representerName()">
								<div style="min-width: 112px;" class="limited-label" data-bind="text: '(' + $data.representerName() + ')'"></div>	
							</div>
						</div>
					</div>
				</div>
				<div style="display: inline-block; vertical-align: top;" data-bind="if: numberApprover() > 5">
					<div data-bind="text: $i18n('KAF000_62')"></div>
				</div>
			</div>
        `
    })
    class Kaf000BComponent8ViewModel extends ko.ViewModel {
		appDispInfoStartupOutput: any;
		isAgentMode: KnockoutObservable<boolean>;
		approvalRootStateShort: KnockoutObservableArray<ApproverInforShort>;
		numberApprover: KnockoutObservable<number>;
		
        created(params: any) {
            const vm = this;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
			vm.isAgentMode = params.isAgentMode ? params.isAgentMode : ko.observable(false);
			vm.approvalRootStateShort = ko.observableArray([]);
			vm.numberApprover = ko.observable(0);
			
            vm.appDispInfoStartupOutput.subscribe(value => {
				let approvalRootStateShort = [],
					listPhase: any = value.appDetailScreenInfo.approvalLst;
				for(let phase of listPhase) {
					for(let frame of phase.listApprovalFrame) {
						for(let approver of frame.listApprover) {
							approvalRootStateShort.push(new ApproverInforShort(approver.approverName, approver.representerName));
						}
					}
				}
				vm.numberApprover(_.size(approvalRootStateShort));
				vm.approvalRootStateShort(_.take(approvalRootStateShort, 5));    
            });
        }

		openApproverDetail() {
			const vm = this;
			let appDispInfoStartupOutput = vm.appDispInfoStartupOutput(),
				isAgentMode = vm.isAgentMode(),
				dParam = { appDispInfoStartupOutput, isAgentMode };
			vm.$window.modal('/view/kaf/000/d/index.xhtml', dParam);
		}
		
		getApproverLabel(index: string) {
			const vm = this;
			return vm.$i18n("KAF000_9", [index+1]);	
		}
    }

	class ApproverInforShort {
		
		approverName: KnockoutObservable<string>;
		representerName: KnockoutObservable<string>;
		
		constructor(approverName: string, representerName: string) {
			this.approverName = ko.observable(approverName);
			this.representerName = ko.observable(representerName);
		}
	}
}