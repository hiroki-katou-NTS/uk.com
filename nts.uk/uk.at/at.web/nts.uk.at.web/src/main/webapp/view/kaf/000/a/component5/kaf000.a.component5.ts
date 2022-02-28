module nts.uk.at.view.kaf000.a.component5.viewmodel {

    @component({
        name: 'kaf000-a-component5',
        template: `
    <div id="kaf000-a-component5">
				<div class="table item" data-bind="if: appReasonCDDisp() || appReasonDisp()">
					<div class="cell cm-column2 valign-top" style="width: 120px;">
						<div class="cell valign-center" data-bind="ntsFormLabel: {required: appReasonCDRequired}, text: '理由'"></div>
					</div>
					<div class="cell valign-top">
						<div data-bind="if: appReasonCDDisp" class="combo-reason-wrapper">
							<div id="kaf000-a-component5-comboReason" style="width: 472px;"
										data-bind="ntsComboBox: {
													name: $i18n('KAF000_51'),
																		options: reasonTypeItemLst,
																		optionsValue: 'appStandardReasonCD',
																		optionsText: 'reasonForFixedForm',
																		value: opAppStandardReasonCD,
																		columns: [{ prop: 'reasonForFixedForm', length: 20 }],
																		required: appReasonCDRequired }"></div>
						</div>
						<div data-bind="if: appReasonDisp" class="text-reason-wrapper">
							<textarea style="height: 80px;" id="kaf000-a-component5-textReason"
									data-bind="ntsMultilineEditor: {
															name: $i18n('KAF000_52'),
															value: opAppReason,
															constraint: 'AppReason',
															option: {
																	resizeable: false,
																	width: '450',
																	textalign: 'left'
															},
															required : appReasonRequired }"></textarea>
						</div>
					</div>
				</div>
		</div>
        `
    })
    class Kaf000AComponent5ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
        opAppStandardReasonCD: KnockoutObservable<number>;
        opAppReason: KnockoutObservable<string>;
        reasonTypeItemLst: KnockoutObservableArray<any> = ko.observableArray([]);
        appReasonCDRequired: KnockoutObservable<boolean> = ko.observable(false);
        appReasonRequired: KnockoutObservable<boolean> = ko.observable(false);
        appReasonCDDisp: KnockoutObservable<boolean> = ko.observable(false);
        appReasonDisp: KnockoutObservable<boolean> = ko.observable(false);

        created(params: any) {
            const vm = this;
			vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.opAppStandardReasonCD = params.application().opAppStandardReasonCD;
            vm.opAppReason = params.application().opAppReason;

            vm.appDispInfoStartupOutput.subscribe(value => {
                vm.appReasonCDRequired(value.appDispInfoNoDateOutput.applicationSetting.appLimitSetting.standardReasonRequired);
                vm.appReasonRequired(value.appDispInfoNoDateOutput.applicationSetting.appLimitSetting.requiredAppReason);
                vm.appReasonCDDisp(value.appDispInfoNoDateOutput.displayStandardReason == 1);
                vm.appReasonDisp(value.appDispInfoNoDateOutput.displayAppReason == 1);
                vm.reasonTypeItemLst(value.appDispInfoNoDateOutput.reasonTypeItemLst);
                let defaultReasonTypeItem = _.find(value.appDispInfoNoDateOutput.reasonTypeItemLst, (o) => o.defaultValue);
                if(_.isUndefined(defaultReasonTypeItem)) {
					let dataLst = [{
			            appStandardReasonCD: '',
			            displayOrder: 0,
			            defaultValue: false,
			            reasonForFixedForm: vm.$i18n('KAFS00_23'),
			        }];
					vm.reasonTypeItemLst(_.concat(dataLst, vm.reasonTypeItemLst()));
                    vm.opAppStandardReasonCD(_.head(vm.reasonTypeItemLst()).appStandardReasonCD);
                } else {
                    vm.opAppStandardReasonCD(defaultReasonTypeItem.appStandardReasonCD);
                }
            });
        }
    }
}