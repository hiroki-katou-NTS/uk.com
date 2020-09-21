module nts.uk.at.view.kaf000.a.component2.viewmodel {

    @component({
        name: 'kaf000-a-component2',
        template: `
            <div id="kaf000-a-component2">
                <div class="table">
                    <div class="cell col-1">
                        <div class="cell valign-center" data-bind="ntsFormLabel:{}, text: $i18n('KAF000_44')"></div>
                    </div>
                    <div class="cell valign-center" data-bind="text: employeeName"></div>
                </div>
            </div>
        `
    })
    class Kaf000AComponent2ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
        employeeName: KnockoutObservable<string>;
        created(params: any) {
            const vm = this;
			vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.employeeName = ko.observable("employeeName");

            vm.appDispInfoStartupOutput.subscribe(value => {
                vm.employeeName(value.appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName);
                params.application().employeeIDLst(_.map(value.appDispInfoNoDateOutput.employeeInfoLst, o => o.sid));
            });
        }

        mounted() {
            const vm = this;
        }
    }
}