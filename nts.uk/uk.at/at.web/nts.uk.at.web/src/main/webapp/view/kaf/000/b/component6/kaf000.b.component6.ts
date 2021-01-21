module nts.uk.at.view.kaf000.b.component6.viewmodel {

    @component({
        name: 'kaf000-b-component6',
        template: `
            <div id="kaf000-b-component6">
                <div class="table">
                    <div class="cell col-1">
                        <div class="cell valign-center" data-bind="ntsFormLabel:{}, text: $i18n('KAF000_49')"></div>
                    </div>
                    <div class="cell valign-center" data-bind="text: appDateString"></div>
                </div>
            </div>
        `
    })
    class Kaf000BComponent6ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
        appDateString: KnockoutObservable<string>;
        created(params: any) {
            const vm = this;
			vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.appDateString = ko.observable("appDateString");


            params.application().appDate(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.appDate);
            params.application().opAppStartDate(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.opAppStartDate);
		    params.application().opAppEndDate(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.opAppEndDate);

			let inputDate = moment(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.inputDate).format("YYYY/MM/DD hh:mm");
			let appDateString = "";
			if(params.application().opAppStartDate()==params.application().opAppEndDate()) {
				appDateString = params.application().appDate();
			} else {
				appDateString = params.application().opAppStartDate() + '～' +params.application().opAppEndDate();
			}
			vm.appDateString(vm.$i18n('KAF011_23', [appDateString, inputDate]));

			vm.appDispInfoStartupOutput.subscribe(value => {
         		params.application().appDate(value.appDetailScreenInfo.application.appDate);
	            params.application().opAppStartDate(value.appDetailScreenInfo.application.opAppStartDate);
			    params.application().opAppEndDate(value.appDetailScreenInfo.application.opAppEndDate);

				let inputDate = moment(value.appDetailScreenInfo.application.inputDate).format("YYYY/MM/DD hh:mm");
				let appDateString = "";
				if(params.application().opAppStartDate()==params.application().opAppEndDate()) {
					appDateString = params.application().appDate();
				} else {
					appDateString = params.application().opAppStartDate() + '～' +params.application().opAppEndDate();
				}
				vm.appDateString(vm.$i18n('KAF011_23', [appDateString, inputDate]));
            });
        }

        mounted() {
            const vm = this;
        }
    }
}