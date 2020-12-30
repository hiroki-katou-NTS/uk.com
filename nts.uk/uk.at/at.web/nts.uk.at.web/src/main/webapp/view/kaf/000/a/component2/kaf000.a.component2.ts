module nts.uk.at.view.kaf000.a.component2.viewmodel {

    @component({
        name: 'kaf000-a-component2',
        template: `
            <div id="kaf000-a-component2">
                <div class="table">
                    <div class="cell col-1">
						<div class="cell valign-center" data-bind="ntsFormLabel:{}, text: $i18n('KAF000_44')"></div>
					</div>
					<div class="cell valign-center" data-bind="if: !isAgentMode">
						<div data-bind="text: employeeName"></div>
					</div>
					<div class="cell valign-center" data-bind="if: isAgentMode">
						<div style="overflow-y: auto; height: 120px; width: 325px; border: 1px solid grey;">
							<table id="kaf000-a-component2-table">
								<tbody data-bind="foreach: employeeLst">
									<tr>
										<td data-bind="text: bussinessName"></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div style="margin-top: 5px;" data-bind="text: sumEmp"></div>
					</div>
                </div>
            </div>
        `
    })
    class Kaf000AComponent2ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
        employeeName: KnockoutObservable<string>;
		employeeLst: KnockoutObservable<any>;
		sumEmp: KnockoutObservable<string>;
		isAgentMode: boolean = false;
        created(params: any) {
            const vm = this;
			vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.employeeName = ko.observable("employeeName");
			vm.employeeLst = ko.observableArray([]);
			vm.sumEmp = ko.observable("");
			vm.isAgentMode = params.isAgentMode();

            vm.appDispInfoStartupOutput.subscribe((value: any) => {
                vm.employeeName(value.appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName);
				vm.employeeLst(value.appDispInfoNoDateOutput.employeeInfoLst);
				vm.sumEmp(vm.$i18n('KAF000_45', [vm.employeeLst().length]));
                params.application().employeeIDLst(_.map(value.appDispInfoNoDateOutput.employeeInfoLst, (o: any) => o.sid));
            });
        }

        mounted() {
            const vm = this;
        }
    }
}