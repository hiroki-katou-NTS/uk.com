module nts.uk.at.view.kaf000.b.component4.viewmodel {

    @component({
        name: 'kaf000-b-component4',
        template: `
            <div id="kaf000-b-component4">
                <div class="table">
                    <div class="cell col-1">
                        <div class="cell valign-center" data-bind="ntsFormLabel:{}, text: $i18n('KAF000_44')"></div>
                    </div>
                    <div class="cell valign-center" data-bind="text: employeeName"></div>
                </div>
            </div>
        `
    })
    class Kaf000BComponent4ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
        employeeName: KnockoutObservable<string>;
        created(params: any) {
            const vm = this;
			vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.employeeName = ko.observable("employeeName");

            vm.employeeName(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName);
            params.application().employeeIDLst(_.map(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.employeeInfoLst, (o: any) => o.sid));

			vm.appDispInfoStartupOutput.subscribe(value => {
         		vm.employeeName(value.appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName);
            	params.application().employeeIDLst(_.map(value.appDispInfoNoDateOutput.employeeInfoLst, (o: any) => o.sid));
            });
        }

        mounted() {
            const vm = this;
        }
    }
}