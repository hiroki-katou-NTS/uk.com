module nts.uk.at.view.kaf000.b.component7.viewmodel {

    @component({
        name: 'kaf000-b-component7',
        template: `
            <div id="kaf000-b-component7">
                <div class="table" style="margin-bottom: 5px;" data-bind="if: appReasonCDDisp">
                    <div class="cell col-1">
                        <div class="cell valign-center" data-bind="ntsFormLabel: {required: appReasonCDRequired}, text: $i18n('KAF000_51')"></div>
                    </div>
                    <div class="cell valign-center">
                        <div id="combo-box" style="width: 390px"
                            data-bind="ntsComboBox: {
                                            name: $i18n('KAF000_51'),
                                            options: reasonTypeItemLst,
                                            optionsValue: 'appStandardReasonCD',
                                            optionsText: 'reasonForFixedForm',
                                            value: opAppStandardReasonCD,
                                            columns: [{ prop: 'reasonForFixedForm', length: 20 }],
                                            required: appReasonCDRequired }">
                        </div>
                    </div>
                </div>
                <div class="table" style="margin-top: 5px;" data-bind="if: appReasonDisp">
                    <div class="cell col-1">
                        <div class="cell valign-center" data-bind="ntsFormLabel: {
                                                required: appReasonRequired,
                                                constraint: 'AppReason',
                                                text: $i18n('KAF000_52')
                                            }"></div>
                    </div>
                    <div class="cell valign-center">
                        <textarea style="height: 80px;" id="inpReasonTextarea"
                            data-bind="ntsMultilineEditor: {
                                        name: $i18n('KAF000_52'),
                                        value: opAppReason,
                                        constraint: 'AppReason',
                                        option: {
                                            resizeable: false,
                                            width: '450',
                                            textalign: 'left'
                                        },
                                        required : appReasonRequired }" />
                    </div>
                </div>
            </div>
        `
    })
    class Kaf000BComponent7ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
        opAppStandardReasonCD: KnockoutObservable<number>;
        opAppReason: KnockoutObservable<string>;
        reasonTypeItemLst: KnockoutObservableArray<any>;
        appReasonCDRequired: KnockoutObservable<boolean>;
        appReasonRequired: KnockoutObservable<boolean>;
        appReasonCDDisp: KnockoutObservable<boolean>;
        appReasonDisp: KnockoutObservable<boolean>;
        created(params: any) {
            const vm = this;
			vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.opAppStandardReasonCD = params.application().opAppStandardReasonCD;
            vm.opAppReason = params.application().opAppReason;
            vm.reasonTypeItemLst = ko.observableArray([]);
            vm.appReasonCDRequired = ko.observable(false);
            vm.appReasonRequired = ko.observable(false);
            vm.appReasonCDDisp = ko.observable(false);
            vm.appReasonDisp = ko.observable(false);

            vm.appReasonCDRequired(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.applicationSetting.appLimitSetting.standardReasonRequired);
            vm.appReasonRequired(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.applicationSetting.appLimitSetting.requiredAppReason);
            vm.appReasonCDDisp(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.displayStandardReason == 1);
            vm.appReasonDisp(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.displayAppReason == 1);
            vm.reasonTypeItemLst(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.reasonTypeItemLst);
            vm.opAppStandardReasonCD(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.opAppStandardReasonCD);
			let initReasonTypeItem = _.find(vm.reasonTypeItemLst(), (o) => o.appStandardReasonCD == vm.opAppStandardReasonCD());
			if(_.isUndefined(initReasonTypeItem)) {
				let dataLst = [{
		            appStandardReasonCD: '',
		            displayOrder: 0,
		            defaultValue: false,
		            reasonForFixedForm: vm.$i18n('KAFS00_23'),
		        }];
				vm.reasonTypeItemLst(_.concat(dataLst, vm.reasonTypeItemLst()));
				vm.opAppStandardReasonCD(_.head(vm.reasonTypeItemLst()).appStandardReasonCD);
			}
            vm.opAppReason(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.opAppReason);

			vm.appDispInfoStartupOutput.subscribe(value => {
         		vm.appReasonCDRequired(value.appDispInfoNoDateOutput.applicationSetting.appLimitSetting.standardReasonRequired);
	            vm.appReasonRequired(value.appDispInfoNoDateOutput.applicationSetting.appLimitSetting.requiredAppReason);
	            vm.appReasonCDDisp(value.appDispInfoNoDateOutput.displayStandardReason == 1);
	            vm.appReasonDisp(value.appDispInfoNoDateOutput.displayAppReason == 1);
	            vm.reasonTypeItemLst(value.appDispInfoNoDateOutput.reasonTypeItemLst);
	            vm.opAppStandardReasonCD(value.appDetailScreenInfo.application.opAppStandardReasonCD);
				let initReasonTypeItem = _.find(vm.reasonTypeItemLst(), (o) => o.appStandardReasonCD == vm.opAppStandardReasonCD());
				if(_.isUndefined(initReasonTypeItem)) {
					let dataLst = [{
			            appStandardReasonCD: '',
			            displayOrder: 0,
			            defaultValue: false,
			            reasonForFixedForm: vm.$i18n('KAFS00_23'),
			        }];
					vm.reasonTypeItemLst(_.concat(dataLst, vm.reasonTypeItemLst()));
					vm.opAppStandardReasonCD(_.head(vm.reasonTypeItemLst()).appStandardReasonCD);
				}
	            vm.opAppReason(value.appDetailScreenInfo.application.opAppReason);
            });
        }

        mounted() {
            const vm = this;
        }
    }
}