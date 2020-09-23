module nts.uk.at.view.kaf000.b.component5.viewmodel {

    @component({
        name: 'kaf000-b-component5',
        template: `
            <div id="kaf000-b-component5">
                <div class="table" data-bind="if: prePostAtrDisp">
                    <div class="cell col-1">
                        <div class="cell valign-center" data-bind="ntsFormLabel:{required: true}, text: $i18n('KAF000_46')"></div>
                    </div>
                    <div class="cell valign-center" data-bind="text: prePostAtrName"></div>
                </div>
            </div>
        `
    })
    class Kaf000BComponent5ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
        prePostAtrName: KnockoutObservable<string>;
        prePostAtrDisp: KnockoutObservable<boolean>;
        created(params: any) {
            const vm = this;
			vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.prePostAtrName = ko.observable("prePostAtrName");
            vm.prePostAtrDisp = ko.observable(false);

            vm.prePostAtrName(vm.getPrePostAtrName(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.prePostAtr));
			if(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.opStampRequestMode==1) {
				vm.prePostAtrDisp(false);
			} else {
            	vm.prePostAtrDisp(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.applicationSetting.appDisplaySetting.prePostDisplayAtr == 1);
			}
            params.application().prePostAtr(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.prePostAtr);

			vm.appDispInfoStartupOutput.subscribe(value => {
         		vm.prePostAtrName(vm.getPrePostAtrName(value.appDetailScreenInfo.application.prePostAtr));
				if(value.appDetailScreenInfo.application.opStampRequestMode==1) {
					vm.prePostAtrDisp(false);
				} else {
	            	vm.prePostAtrDisp(value.appDispInfoNoDateOutput.applicationSetting.appDisplaySetting.prePostDisplayAtr == 1);
				}
	            params.application().prePostAtr(value.appDetailScreenInfo.application.prePostAtr);
            });
        }

        mounted() {
            const vm = this;
        }

        getPrePostAtrName(prePostAtr: number) {
            const vm = this;
            if(prePostAtr==0) {
                return vm.$i18n('KAF000_47');
            } else {
                return vm.$i18n('KAF000_48');
            }
        }
    }
}