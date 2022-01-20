module nts.uk.at.view.kaf000.b.component8.viewmodel {

    @component({
        name: 'kaf000-b-component8',
        template: `
            <div id="kaf000-b-component8" data-bind="click: openApproverDetail">
				<div style="display: inline-block;" data-bind="if: approvalRootStateShort().length != 0">
					<div data-bind="foreach: approvalRootStateShort" style="padding-top: 5px;">
						<div class="approver-block">
							<div class="table">
								<div class="cell">
									<div class="limited-label" data-bind="ntsFormLabel:{}, text: $component.getApproverLabel($index())"></div>
								</div>
								<div class="cell status-icon">
									<i data-bind="ntsIcon: { no: $component.getApprovalIconNo($data.approvalAtrValue()) }"></i>
								</div>
							</div>
							<div>
								<div class="limited-label" style="vertical-align: middle;" data-bind="text: $data.approverName"></div>
							</div>
							<div data-bind="if: $data.representerName()">
								<div class="limited-label" style="vertical-align: middle;" data-bind="text: '(' + $data.representerName() + ')'"></div>
							</div>
							<div data-bind="if: $data.approvalReason">
								<div class="limited-label" style="vertical-align: middle;" data-bind="text: $data.approvalReason"></div>
							</div>
						</div>
					</div>
				</div>
				<div style="display: inline-block; width: 8px;">
					<div data-bind="if: numberApprover() > 5">
						<div data-bind="text: $i18n('KAF000_62')"></div>
					</div>
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
			
			let approvalRootStateShort = [],
				listPhase: any = vm.appDispInfoStartupOutput().appDetailScreenInfo.approvalLst;
			for(let phase of listPhase) {
				for(let frame of phase.listApprovalFrame) {
					for(let approver of frame.listApprover) {
						approvalRootStateShort.push(new ApproverInforShort(
							approver.approverName, 
							approver.representerName,
							approver.approvalAtrValue,
							approver.approvalAtrName,
							approver.approvalReason));
					}
				}
			}
			vm.numberApprover(_.size(approvalRootStateShort));
			vm.approvalRootStateShort(_.take(approvalRootStateShort, 5));
			
            vm.appDispInfoStartupOutput.subscribe(value => {
				let approvalRootStateShort = [],
					listPhase: any = value.appDetailScreenInfo.approvalLst;
				for(let phase of listPhase) {
					for(let frame of phase.listApprovalFrame) {
						for(let approver of frame.listApprover) {
							approvalRootStateShort.push(new ApproverInforShort(
								approver.approverName, 
								approver.representerName,
								approver.approvalAtrValue,
								approver.approvalAtrName,
								approver.approvalReason));
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
				approvalRootState = vm.appDispInfoStartupOutput().appDetailScreenInfo.approvalLst,
				dParam = { appDispInfoStartupOutput, isAgentMode, approvalRootState };
			vm.$window.modal('/view/kaf/000/d/index.xhtml', dParam);
		}
		
		getApproverLabel(index: string) {
			const vm = this;
			return vm.$i18n("KAF000_9", [index+1]);	
		}
		
		getMaxWidth() {
			const vm = this;
			switch(vm.numberApprover()) {
				case 1: return "calc(100% - 30px)";
				case 2: return "calc(50% - 15px)";
				case 3: return "calc(33% - 10px)";
				case 4: return "calc(25% - 8px)";
				default: return "calc(20% - 6px)";
			}
		}
		
		getApprovalIconNo(approvalAtrValue: number) {
			return 300 + approvalAtrValue;
		}
		
		getApprovalColor(approvalAtrValue: number) {
			switch(approvalAtrValue) {
				case 1: return '#BFEA60';
				case 2: return '#FD4D4D';
				case 3: return '#FD4D4D';
				case 4: return '#FD4D4D';
				default: return '';
			}
		}
  }

	class ApproverInforShort {
		
		approverName: KnockoutObservable<string>;
		representerName: KnockoutObservable<string>;
		approvalAtrValue: KnockoutObservable<number>;
		approvalAtrName: KnockoutObservable<string>;
		approvalReason: KnockoutObservable<string>;
		
		constructor(approverName: string, representerName: string, approvalAtrValue: number, approvalAtrName: string, approvalReason: string) {
			this.approverName = ko.observable(approverName);
			this.representerName = ko.observable(representerName);
			this.approvalAtrValue = ko.observable(approvalAtrValue);
			this.approvalAtrName = ko.observable(approvalAtrName);
			this.approvalReason = ko.observable(approvalReason);
		}
	}
}