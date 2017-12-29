module a8 {
    
    import WorkTimeDailyAtr = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeDailyAtr;
    import WorkTimeMethodSet = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeMethodSet
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    
    import WorkTimezoneGoOutSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneGoOutSetModel;
    import TotalRoundingSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.TotalRoundingSetModel;
    import GoOutTypeRoundingSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.GoOutTypeRoundingSetModel;
    import GoOutTimeRoundingSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.GoOutTimeRoundingSettingModel;
    import TimeRoundingSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRoundingSettingModel;
    
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    import TabMode = nts.uk.at.view.kmk003.a.viewmodel.TabMode;
    
    /**
     * Screen Model - Tab 8
     * 就業時間帯の共通設定 -> 外出設定
     * WorkTimeCommonSet -> WorkTimeGoOutSet
     */
    class ScreenModel {

        // Screen mode
        isDetailMode: KnockoutObservable<boolean>;
        workTimeDailyAtr: KnockoutObservable<number>;
        workTimeMethodSet: KnockoutObservable<number>;
        
        // Screen data model
        model: MainSettingModel;
        settingEnum: WorkTimeSettingEnumDto;
        
        // UI component
        workTimeTabs: KnockoutObservableArray<any>;
        workTimeSelectedTab: KnockoutObservable<string>;
        
        // Detail mode - Data       
        totalRoundingSameFrameRoundingSet: KnockoutObservable<number>;
        totalRoundingFrameStraddRoundingSet: KnockoutObservable<number>;

        otTimePersonApproTimeSetting: TimeRoundingSetting;
        workTimePersonApproTimeSetting: TimeRoundingSetting;
        pubHolWorkTimePersonApproTimeSetting: TimeRoundingSetting;
        
        otTimePersonDeductTimeSetting: TimeRoundingSetting;
        workTimePersonDeductTimeSetting: TimeRoundingSetting;
        pubHolWorkTimePersonDeductTimeSetting: TimeRoundingSetting;
        
        otTimePublicApproTimeSetting: TimeRoundingSetting;
        workTimePublicApproTimeSetting: TimeRoundingSetting;
        pubHolWorkTimePublicApproTimeSetting: TimeRoundingSetting;
        
        listRoundingBreakTimezone: KnockoutObservableArray<any>;
        
        
        screenMode: any;
        
        // Simple mode - Data (nothing)      
        
        /**
         * Constructor.
         */
        constructor(screenMode: any, model: MainSettingModel, settingEnum: WorkTimeSettingEnumDto) {
            let _self = this;
            
            // Check exist
            if (nts.uk.util.isNullOrUndefined(model) || nts.uk.util.isNullOrUndefined(settingEnum)) {
                // Stop rendering page
                return;    
            }
            
            // Binding data
            _self.screenMode = screenMode;
            _self.model = model; 
            _self.settingEnum = settingEnum;
            
            // Init UI
            _self.workTimeTabs = ko.observableArray([
                { id: 'personal-setting-tab', title: nts.uk.resource.getText("KMK003_194"), content: '.personal-setting-tab-content', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'official-setting-tab', title: nts.uk.resource.getText("KMK003_195"), content: '.official-setting-tab-content', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            _self.workTimeSelectedTab = ko.observable('personal-setting-tab');
            
            // Init all data                        
            _self.totalRoundingSameFrameRoundingSet = ko.observable(0);
            _self.totalRoundingFrameStraddRoundingSet = ko.observable(0);
            
            _self.otTimePersonApproTimeSetting = new TimeRoundingSetting(settingEnum);
            _self.workTimePersonApproTimeSetting = new TimeRoundingSetting(settingEnum);
            _self.pubHolWorkTimePersonApproTimeSetting = new TimeRoundingSetting(settingEnum);
            
            _self.otTimePersonDeductTimeSetting = new TimeRoundingSetting(settingEnum);
            _self.workTimePersonDeductTimeSetting = new TimeRoundingSetting(settingEnum);
            _self.pubHolWorkTimePersonDeductTimeSetting = new TimeRoundingSetting(settingEnum);
        
            _self.otTimePublicApproTimeSetting = new TimeRoundingSetting(settingEnum);
            _self.workTimePublicApproTimeSetting = new TimeRoundingSetting(settingEnum);
            _self.pubHolWorkTimePublicApproTimeSetting = new TimeRoundingSetting(settingEnum);          
            
            _self.listRoundingBreakTimezone = ko.observableArray([
                { value: 0, localizedName: nts.uk.resource.getText("KMK003_198") },
                { value: 1, localizedName: nts.uk.resource.getText("KMK003_199") }
            ]);          
            //_self.listRoundingBreakTimezone(_self.settingEnum.roundingBreakTimezone);   
            
            // Detail mode and simple mode is same
            _self.isDetailMode = ko.observable(null);
            _self.isDetailMode.subscribe(newValue => {
                _self.changeWorkSettingMode();
            });                                   
            // Subscribe Work Setting Regular/Flex mode
            _self.workTimeDailyAtr = ko.observable(0);
            _self.model.workTimeSetting.workTimeDivision.workTimeDailyAtr.subscribe(newValue => {
                _self.workTimeDailyAtr(newValue);
                _self.changeWorkSettingMode();
            });  
            // Subscribe Work Setting Fixed/Diff/Flow mode
            _self.workTimeMethodSet = ko.observable(0); 
            _self.model.workTimeSetting.workTimeDivision.workTimeMethodSet.subscribe(newValue => {
                _self.workTimeMethodSet(newValue);
                _self.changeWorkSettingMode();
            });                          
            // Subscribe Detail/Simple mode 
            screenMode.subscribe((value: any) => {
                value == TabMode.DETAIL ? _self.isDetailMode(true) : _self.isDetailMode(false);
            });
            
            // Subscribe change select item in table on hand left
            _self.model.isChangeItemTable.subscribe(newValue => {
                _self.startTab(_self.screenMode);
                _self.changeWorkSettingMode();
            });
        }
        
        /**
         * Start tab
         */
        public startTab(screenMode: any): void {
            let _self = this;
            screenMode() == TabMode.DETAIL ? _self.isDetailMode(true) : _self.isDetailMode(false);
            _self.workTimeDailyAtr(_self.model.workTimeSetting.workTimeDivision.workTimeDailyAtr());
            _self.workTimeMethodSet(_self.model.workTimeSetting.workTimeDivision.workTimeMethodSet());
        }
        
        /**
         * UI - All: change WorkSetting mode
         */
        private changeWorkSettingMode(): void {
            let _self = this;        
                          
            if (nts.uk.util.isNullOrUndefined(_self.model.commonSetting.goOutSet)) {
                _self.model.commonSetting.goOutSet = _self.createBinding();                           
            }
            _self.changeBinding(_self.model.commonSetting.goOutSet);                                         
        }       
        
        /**
         * UI - All: create new Binding data
         */
        private createBinding(): WorkTimezoneGoOutSetModel {
            let _self = this;      
                 
            let result: WorkTimezoneGoOutSetModel = new WorkTimezoneGoOutSetModel();           
            return result;
        }
        
        /**
         * UI - All: change Binding mode
         */
        private changeBinding(goOutSet: WorkTimezoneGoOutSetModel): void {
            let _self = this;
            if (_self.isDetailMode()) {
                _self.changeBindingDetail(goOutSet); 
            } else {
                _self.changeBindingSimple(goOutSet); 
            }  
        }
        
        /**
         * UI - Detail: change Binding Detail mode
         */
        private changeBindingDetail(goOutSet: WorkTimezoneGoOutSetModel): void {
            let _self = this;
            
            // Get model value into view model
            _self.totalRoundingSameFrameRoundingSet(goOutSet.totalRoundingSet.setSameFrameRounding());
            _self.totalRoundingFrameStraddRoundingSet(goOutSet.totalRoundingSet.frameStraddRoundingSet());
            
            // Update into model in case of data change
            _self.totalRoundingSameFrameRoundingSet.subscribe(newValue => goOutSet.totalRoundingSet.setSameFrameRounding(newValue));
            _self.totalRoundingFrameStraddRoundingSet.subscribe(newValue => goOutSet.totalRoundingSet.frameStraddRoundingSet(newValue));
            
            // Update binding for Time Setting model
            _self.otTimePersonApproTimeSetting.updateBinding(goOutSet.diffTimezoneSetting.ottimezone.privateUnionGoOut.approTimeRoundingSetting);
            _self.workTimePersonApproTimeSetting.updateBinding(goOutSet.diffTimezoneSetting.workTimezone.privateUnionGoOut.approTimeRoundingSetting);
            _self.pubHolWorkTimePersonApproTimeSetting.updateBinding(goOutSet.diffTimezoneSetting.pubHolWorkTimezone.privateUnionGoOut.approTimeRoundingSetting);
            
            _self.otTimePersonDeductTimeSetting.updateBinding(goOutSet.diffTimezoneSetting.ottimezone.privateUnionGoOut.deductTimeRoundingSetting);
            _self.workTimePersonDeductTimeSetting.updateBinding(goOutSet.diffTimezoneSetting.workTimezone.privateUnionGoOut.deductTimeRoundingSetting);
            _self.pubHolWorkTimePersonDeductTimeSetting.updateBinding(goOutSet.diffTimezoneSetting.pubHolWorkTimezone.privateUnionGoOut.deductTimeRoundingSetting);
            
            _self.otTimePublicApproTimeSetting.updateBinding(goOutSet.diffTimezoneSetting.ottimezone.officalUseCompenGoOut.approTimeRoundingSetting);
            _self.workTimePublicApproTimeSetting.updateBinding(goOutSet.diffTimezoneSetting.workTimezone.officalUseCompenGoOut.approTimeRoundingSetting);
            _self.pubHolWorkTimePublicApproTimeSetting.updateBinding(goOutSet.diffTimezoneSetting.pubHolWorkTimezone.officalUseCompenGoOut.approTimeRoundingSetting);         
        }
        
        /**
         * UI - Simple: change Binding Simple mode 
         */
        private changeBindingSimple(goOutSet: WorkTimezoneGoOutSetModel): void {
            let _self = this;
            _self.changeBindingDetail(goOutSet);
        } 
    }
    
    class TimeRoundingSetting {
        timeRoundingMethod: KnockoutObservable<number>;
        timeRoundingTime: KnockoutObservable<number>;
        timeRounding: KnockoutObservable<number>;        
        isEnable: KnockoutObservable<boolean>;
        
        listRoundingBreakTime: KnockoutObservableArray<any>;
        listRoundingTimeValue: KnockoutObservableArray<EnumConstantDto>;
        listRoundingValue: KnockoutObservableArray<EnumConstantDto>;
        
        constructor(settingEnum: WorkTimeSettingEnumDto) {
            let _self = this;
            
            _self.listRoundingBreakTime = ko.observableArray([
                { value: 0, localizedName: nts.uk.resource.getText("KMK003_87") },
                { value: 1, localizedName: nts.uk.resource.getText("KMK003_88") }
            ]);
            _self.listRoundingTimeValue = ko.observableArray([]);
            _self.listRoundingValue = ko.observableArray([]);
            
            //_self.listRoundingBreakTime(settingEnum.roundingBreakTime);
            _self.listRoundingTimeValue(settingEnum.roundingTime);
            _self.listRoundingValue(settingEnum.roundingSimple);   
            _self.isEnable = ko.observable(true);
        }
        
        updateBinding(modelValue: GoOutTimeRoundingSettingModel) {
            let _self = this;
            
            // Get model value into view model
            _self.timeRoundingMethod = modelValue.roundingMethod;
            _self.timeRoundingTime = modelValue.roundingSetting.roundingTime;
            _self.timeRounding = modelValue.roundingSetting.rounding;
            
            // Update into model in case of data change
            _self.timeRoundingMethod.subscribe(newValue => {
                if (newValue === 1) {
                    _self.isEnable(false);
                } else {
                    _self.isEnable(true);
                }
            });
        }
    }
    
    /**
     * Knockout Binding Handler - Tab 8
     */
    class KMK003A8BindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {}

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a8/index.xhtml').serialize();
            //get data
            let input = valueAccessor();
            let screenMode = input.screenMode;
            let model = input.model;
            let settingEnum = input.enum;

            let screenModel = new ScreenModel(screenMode, model, settingEnum);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                screenModel.startTab(screenMode);
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A8'] = new KMK003A8BindingHandler();
}
