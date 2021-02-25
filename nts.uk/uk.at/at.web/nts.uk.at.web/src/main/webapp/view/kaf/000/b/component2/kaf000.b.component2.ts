module nts.uk.at.view.kaf000.b.component2.viewmodel {

    @component({
        name: 'kaf000-b-component2',
        template: `
            <div id="kaf000-b-component2">
                <div class="table">
                    <div id="opReversionReason" data-bind="html: opReversionReason"></div>
                </div>
            </div>

        `
    })
    class Kaf000BComponent2ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
        opReversionReason: KnockoutObservable<string>;
        opReversionReasonDisp: KnockoutObservable<boolean>;
        created(params: any) {
            const vm = this;
			vm.appType = params.appType;
            vm.opReversionReason = ko.observable("opReversionReason");
            vm.opReversionReasonDisp = ko.observable(false);
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;

            vm.opReversionReason(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.opReversionReason);
            vm.opReversionReasonDisp(!_.isEmpty(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.opReversionReason));
            if (vm.opReversionReasonDisp()) {
                let opReversionReason = vm.appDispInfoStartupOutput().appDetailScreenInfo.application.opReversionReason;
                vm.opReversionReason(opReversionReason.replace(/\n/g, "\<br/>"));
            }

			vm.appDispInfoStartupOutput.subscribe(value => {
            	vm.opReversionReason(value.appDetailScreenInfo.application.opReversionReason);
	            vm.opReversionReasonDisp(!_.isEmpty(value.appDetailScreenInfo.application.opReversionReason));
	            if (vm.opReversionReasonDisp()) {
	                let opReversionReason = value.appDetailScreenInfo.application.opReversionReason;
	                vm.opReversionReason(opReversionReason.replace(/\n/g, "\<br/>"));
	            }
            });
        }

        mounted() {
            const vm = this;
        }
    }
}