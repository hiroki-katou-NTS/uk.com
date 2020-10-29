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
						<div id="list-box" data-bind="ntsListBox: {
							options: employeeLst,
							optionsValue: 'sid',
							optionsText: 'bussinessName',
							value: ko.observableArray([]),
							rows: 6,
							columns: [
								{ key: 'bussinessName', length: 20 }
							]}"></div>
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
		isAgentMode: boolean = false;
        created(params: any) {
            const vm = this;
			vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.employeeName = ko.observable("employeeName");
			vm.employeeLst = ko.observableArray([]);
			vm.isAgentMode = params.isAgentMode();

            vm.appDispInfoStartupOutput.subscribe((value: any) => {
                vm.employeeName(value.appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName);
				vm.employeeLst(value.appDispInfoNoDateOutput.employeeInfoLst);
                params.application().employeeIDLst(_.map(value.appDispInfoNoDateOutput.employeeInfoLst, (o: any) => o.sid));
            });
        }

        mounted() {
            const vm = this;
        }
    }
}