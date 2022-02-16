module nts.uk.at.view.kaf000.a.component2.viewmodel {

	@component({
		name: 'kaf000-a-component2',
		template: `
            <div id="kaf000-a-component2">
				<div style="margin-top: 10px">
				    <div class="col-1">
						<div class="cell valign-center" data-bind="ntsFormLabel:{}, text: $i18n('KAF000_44')"></div>
					</div>
					<div class="valign-center" data-bind="if: employeeLst().length <= 1">
						<div style="display: inline-block; max-width: calc(100% - 26px);">
							<div style="min-width: 112px; vertical-align: bottom; padding-right: 10px; font-size: 1.3rem" class="limited-label" data-bind="text: employeeName"></div>
						</div>
					</div>
					<div class="valign-center" data-bind="if: employeeLst().length > 1">
						<div style="display: inline-block; max-width: calc(100% - 26px);">
							<div style="min-width: 112px; vertical-align: bottom; padding-right: 10px; font-size: 1.3rem" class="limited-label" data-bind="text: employeeName"></div>
						</div>
						<div style="display: inline-block;">
							<button class="popup-btn" style="width: 20px; height: 20px; padding: 0 2px;">
							    <i data-bind="ntsIcon: { no: 11 }"></i>
							</button>
						</div>
						<div class="popup-area">
							<div style="margin-top: 5px;" data-bind="text: sumEmp"></div>
							<div style="overflow-y: auto; height: 125px; width: 325px;">
								<table id="kaf000-a-component2-table">
									<tbody data-bind="foreach: employeeLst">
										<tr>
											<td data-bind="text: bussinessName"></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div style="width: 150px;" data-bind="if: isAgentMode">
						<div class='inputPerson limited-label' data-bind="text: inputPerson"></div>
					</div>
				</div>
			</div>
        `
	})
	export class Kaf000AComponent2ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
		appDispInfoStartupOutput: any;
		employeeName: KnockoutObservable<string>;
		employeeLst: KnockoutObservable<any>;
		sumEmp: KnockoutObservable<string>;
		isAgentMode: KnockoutObservable<boolean> = ko.observable(false);
		inputPerson: KnockoutObservable<string> = ko.observable(null);

		created(params: any) {
			const vm = this;
			const startFormInputPerson = '（入力者： ';
			const endFormInputPerson = '）';
			vm.appType = params.appType;
			vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
			vm.employeeName = ko.observable("employeeName");
			vm.employeeLst = ko.observableArray([]);
			vm.sumEmp = ko.observable("");
			vm.isAgentMode(params.isAgentMode());
			let inputPerson = 
				_.isNil(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.opEmployeeInfo) ? 
				null : 
				(startFormInputPerson + vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.opEmployeeInfo.bussinessName + endFormInputPerson);
			if (_.isNil(inputPerson)) {
				vm.inputPerson(inputPerson);
			} else {
				vm.inputPerson(inputPerson);
			}

			vm.appDispInfoStartupOutput.subscribe((value: any) => {
				vm.employeeName(value.appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName);
				vm.employeeLst(value.appDispInfoNoDateOutput.employeeInfoLst);
				vm.sumEmp(vm.$i18n('KAF000_45', [vm.employeeLst().length]));
				params.application().employeeIDLst(_.map(value.appDispInfoNoDateOutput.employeeInfoLst, (o: any) => o.sid));
				if(vm.employeeLst().length > 1) {
					$(".popup-area").ntsPopup({
						trigger: ".popup-btn",
						position: {
							my: "left top",
							at: "right bottom",
							of: ".popup-btn"
						},
						showOnStart: false,
						dismissible: true
					});
				}
				let inputPerson = 
				_.isNil(value.appDispInfoNoDateOutput.opEmployeeInfo) ? 
				null : 
				(startFormInputPerson + value.appDispInfoNoDateOutput.opEmployeeInfo.bussinessName + endFormInputPerson);
				if (_.isNil(inputPerson)) {	
					vm.inputPerson(inputPerson);
				} else {
					vm.inputPerson(inputPerson);
				}
			});
		}

		mounted() {
			const vm = this;
		}
	}

	@handler({
		bindingName: 'kaf-002-popup'
	})
	export class NtsPopupBindingHandler implements KnockoutBindingHandler {
		init = (element: HTMLElement, target: () => string) => {
			const popup = $(target());

			popup.ntsPopup({
				trigger: element,
				position: {
					my: "left top",
					at: "right bottom",
					of: element
				},
				showOnStart: false,
				dismissible: true
			});
		}
	}
}