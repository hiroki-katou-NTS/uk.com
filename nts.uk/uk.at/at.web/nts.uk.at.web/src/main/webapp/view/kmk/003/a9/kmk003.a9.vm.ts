module a9 {

    import WorkTimeDailyAtr = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeDailyAtr;
    import WorkTimeMethodSet = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeMethodSet
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;

    import TimeRoundingSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRoundingSettingModel;
    import OtherEmTimezoneLateEarlySetModel = nts.uk.at.view.kmk003.a.viewmodel.common.OtherEmTimezoneLateEarlySetModel;

    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    import TabMode = nts.uk.at.view.kmk003.a.viewmodel.TabMode;

    /**
     * Screen Model - Tab 9
     * 就業時間帯の共通設定 -> 遅刻早退設定
     * WorkTimeCommonSet -> LateLeaveEarlySettingOfWorkTime
     */
    class ScreenModel {

        isNewMode: KnockoutObservable<boolean>;

        // Screen mode
        isDetailMode: KnockoutObservable<boolean>;

        // Screen data model
        model: MainSettingModel;
        settingEnum: WorkTimeSettingEnumDto;

        // Detail mode - Data
        recordLateSettingRoundingTime: KnockoutObservable<number>;
        recordLateSettingRounding: KnockoutObservable<number>;
        recordLeaveEarlySettingRoundingTime: KnockoutObservable<number>;
        recordLeaveEarlySettingRounding: KnockoutObservable<number>;

        deductionLateSettingRoundingTime: KnockoutObservable<number>;
        deductionLateSettingRounding: KnockoutObservable<number>;
        deductionLeaveEarlySettingRoundingTime: KnockoutObservable<number>;
        deductionLeaveEarlySettingRounding: KnockoutObservable<number>;

        listRoundingTimeValue: KnockoutObservableArray<EnumConstantDto>;
        listRoundingValue: KnockoutObservableArray<EnumConstantDto>;

        // Simple mode - Data (nothing)        


        /**
         * Constructor
         */
        constructor(isNewMode: KnockoutObservable<boolean>, screenMode: any, model: MainSettingModel, settingEnum: WorkTimeSettingEnumDto) {
            let _self = this;
            _self.isNewMode = isNewMode;
            _self.isNewMode.subscribe((v) => {
                // Set default value for switch button
                if (v) {
                    if (!nts.uk.util.isNullOrUndefined(_self.recordLateSettingRounding)) _self.recordLateSettingRounding(0);
                    if (!nts.uk.util.isNullOrUndefined(_self.recordLeaveEarlySettingRounding)) _self.recordLeaveEarlySettingRounding(0);
                    if (!nts.uk.util.isNullOrUndefined(_self.deductionLateSettingRounding)) _self.deductionLateSettingRounding(0);
                    if (!nts.uk.util.isNullOrUndefined(_self.deductionLeaveEarlySettingRounding)) _self.deductionLeaveEarlySettingRounding(0);
                }    
            });

            // Check exist
            if (nts.uk.util.isNullOrUndefined(model) || nts.uk.util.isNullOrUndefined(settingEnum)) {
                // Stop rendering page
                return;
            }

            // Binding data
            _self.model = model;
            _self.settingEnum = settingEnum;
            _self.bindingData();

            // Init all data                               
            _self.listRoundingTimeValue = ko.observableArray(_self.settingEnum.roundingTime);
            _self.listRoundingValue = ko.observableArray(_self.settingEnum.roundingSimple);

            // Detail mode and simple mode is same
            _self.isDetailMode = ko.observable(null);
            _self.isDetailMode.subscribe(newValue => {
                // Nothing to do
            });
            // Subscribe Detail/Simple mode 
            screenMode.subscribe((value: any) => {
                value == TabMode.DETAIL ? _self.isDetailMode(true) : _self.isDetailMode(false);
            });
        }

        /**
         * Start tab
         */
        public startTab(screenMode: any): void {
            let _self = this;
            screenMode() == TabMode.DETAIL ? _self.isDetailMode(true) : _self.isDetailMode(false);
        }

        /**
         * Binding data
         */
        private bindingData() {
            let _self = this;

            _self.recordLateSettingRoundingTime = _self.model.commonSetting.lateEarlySet.getLateSet().recordTimeRoundingSet.roundingTime;
            _self.recordLateSettingRounding = _self.model.commonSetting.lateEarlySet.getLateSet().recordTimeRoundingSet.rounding;
            _self.recordLeaveEarlySettingRoundingTime = _self.model.commonSetting.lateEarlySet.getLeaveEarlySet().recordTimeRoundingSet.roundingTime;
            _self.recordLeaveEarlySettingRounding = _self.model.commonSetting.lateEarlySet.getLeaveEarlySet().recordTimeRoundingSet.rounding;

            _self.deductionLateSettingRoundingTime = _self.model.commonSetting.lateEarlySet.getLateSet().delTimeRoundingSet.roundingTime;
            _self.deductionLateSettingRounding = _self.model.commonSetting.lateEarlySet.getLateSet().delTimeRoundingSet.rounding;
            _self.deductionLeaveEarlySettingRoundingTime = _self.model.commonSetting.lateEarlySet.getLeaveEarlySet().delTimeRoundingSet.roundingTime;
            _self.deductionLeaveEarlySettingRounding = _self.model.commonSetting.lateEarlySet.getLeaveEarlySet().delTimeRoundingSet.rounding;
        }

        /**
         * Open Dialog Advance Setting
         */
        public openAdvanceSettingDialog(): void {
            let _self = this;

            nts.uk.ui.block.grayout();
            
            let dataObject: any = {
                isFlow: _self.model.workTimeSetting.isFlow(),
                delFromEmTime: _self.model.commonSetting.lateEarlySet.commonSet.delFromEmTime(),       
                lateStampExactlyTimeIsLateEarly: _self.model.commonSetting.lateEarlySet.getLateSet().stampExactlyTimeIsLateEarly(),
                lateGraceTime: _self.model.commonSetting.lateEarlySet.getLateSet().graceTimeSet.graceTime(),
                lateIncludeWorkingHour: _self.model.commonSetting.lateEarlySet.getLateSet().graceTimeSet.includeWorkingHour(),
                leaveEarlyStampExactlyTimeIsLateEarly: _self.model.commonSetting.lateEarlySet.getLeaveEarlySet().stampExactlyTimeIsLateEarly(),
                leaveEarlyGraceTime: _self.model.commonSetting.lateEarlySet.getLeaveEarlySet().graceTimeSet.graceTime(),
                leaveEarlyIncludeWorkingHour: _self.model.commonSetting.lateEarlySet.getLeaveEarlySet().graceTimeSet.includeWorkingHour()
            };
            nts.uk.ui.windows.setShared("KMK003_DIALOG_I_INPUT_DATA", dataObject);
            nts.uk.ui.windows.sub.modal("/view/kmk/003/i/index.xhtml").onClosed(() => {
                let outputDataObject: any = nts.uk.ui.windows.getShared("KMK003_DIALOG_I_OUTPUT_DATA");               
                if (nts.uk.util.isNullOrUndefined(outputDataObject)) {
                    return;    
                }
                
                _self.model.commonSetting.lateEarlySet.commonSet.delFromEmTime(outputDataObject.delFromEmTime);       
                _self.model.commonSetting.lateEarlySet.getLateSet().stampExactlyTimeIsLateEarly(outputDataObject.lateStampExactlyTimeIsLateEarly);
                _self.model.commonSetting.lateEarlySet.getLateSet().graceTimeSet.graceTime(outputDataObject.lateGraceTime);
                _self.model.commonSetting.lateEarlySet.getLateSet().graceTimeSet.includeWorkingHour(outputDataObject.lateIncludeWorkingHour);
                _self.model.commonSetting.lateEarlySet.getLeaveEarlySet().stampExactlyTimeIsLateEarly(outputDataObject.leaveEarlyStampExactlyTimeIsLateEarly);
                _self.model.commonSetting.lateEarlySet.getLeaveEarlySet().graceTimeSet.graceTime(outputDataObject.leaveEarlyGraceTime);
                _self.model.commonSetting.lateEarlySet.getLeaveEarlySet().graceTimeSet.includeWorkingHour(outputDataObject.leaveEarlyIncludeWorkingHour);
                
                nts.uk.ui.block.clear();
            });
        }
    } 

    /**
     * Knockout Binding Handler - Tab 9
     */
    class KMK003A9BindingHandler implements KnockoutBindingHandler {

        /**
         * Constructor
         */
        constructor() { }

        /**
         * Init
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void { }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            let webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a9/index.xhtml').serialize();
            // Get data
            let input = valueAccessor();
            let screenMode = input.screenMode;
            let model = input.model;
            let settingEnum = input.enum;

            let screenModel = new ScreenModel(input.isNewMode, screenMode, model, settingEnum);
            $(element).load(webserviceLocator, () => {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                screenModel.startTab(screenMode);
            });
        }
    }
    ko.bindingHandlers['ntsKMK003A9'] = new KMK003A9BindingHandler();
}