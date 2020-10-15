module nts.uk.at.view.kaf000.a.component3.viewmodel {
	import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
	
    @component({
        name: 'kaf000-a-component3',
        template: `
            <div id="kaf000-a-component3">
                <div class="table" data-bind="if: prePostAtrDisp">
                    <div class="cell col-1">
                        <div class="cell valign-center" data-bind="ntsFormLabel:{required: true}, text: $i18n('KAF000_46')"></div>
                    </div>
                    <div class="cell valign-center">
                        <div id="kaf000-a-component3-prePost" data-bind="ntsSwitchButton: {
                                    name: $i18n('KAF000_46'),
                                    options: ko.observableArray([
                                            { prePostCode: 0, prePostName: function() { return $i18n('KAF000_47'); } },
                                            { prePostCode: 1, prePostName: function() { return $i18n('KAF000_48'); } }
                                            ]),
                                    optionsValue: 'prePostCode',
                                    optionsText: 'prePostName',
                                    value: prePostAtr,
                                    enable: prePostAtrEnable,
                                    required: true }">
                        </div>
                    </div>
                </div>
            </div>
        `
    })
    class Kaf000AComponent3ViewModel extends ko.ViewModel {
        appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
        prePostAtr: KnockoutObservable<number>;
        prePostAtrDisp: KnockoutObservable<boolean> = ko.observable(false);
        prePostAtrEnable: KnockoutObservable<boolean> = ko.observable(false);
		application: KnockoutObservable<Application>;

        created(params: any) {
            const vm = this;
            vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.prePostAtr = params.application().prePostAtr;
			vm.application = params.application;
			
            vm.appDispInfoStartupOutput.subscribe(value => {
				if(vm.application().opStampRequestMode()==1) {
					vm.application().prePostAtr(1);
					vm.prePostAtrDisp(false);
				} else {
					vm.prePostAtr(value.appDispInfoWithDateOutput.prePostAtr);
                	vm.prePostAtrDisp(value.appDispInfoNoDateOutput.applicationSetting.appDisplaySetting.prePostDisplayAtr == 1);
				}
                let appTypeSetting = _.find(value.appDispInfoNoDateOutput.applicationSetting.appTypeSetting, (o: any) => o.appType == vm.appType());
                vm.prePostAtrEnable(appTypeSetting.canClassificationChange);
            });
        }
    }
}