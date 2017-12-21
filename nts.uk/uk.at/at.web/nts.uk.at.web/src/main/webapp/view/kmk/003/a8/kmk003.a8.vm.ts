module a8 {
    
    import WorkTimeDailyAtr = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeDailyAtr;
    import WorkTimeMethodSet = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeMethodSet
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    
    import WorkTimezoneGoOutSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneGoOutSetModel;
    import TotalRoundingSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.TotalRoundingSetModel;
    import GoOutTypeRoundingSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.GoOutTypeRoundingSetModel;
    import TimeRoundingSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRoundingSettingModel;
    
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    
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
               
        //TODO create model
        otTimePersonApproTimeRoundingEnable: KnockoutObservable<boolean>;
        otTimePersonApproTimeRoundingMethod: KnockoutObservable<number>;
        otTimePersonApproTimeRoundingTime: KnockoutObservable<number>;
        otTimePersonApproTimeRounding: KnockoutObservable<number>;
        workTimePersonApproTimeRoundingMethod: KnockoutObservable<number>;
        workTimePersonApproTimeRoundingTime: KnockoutObservable<number>;
        workTimePersonApproTimeRounding: KnockoutObservable<number>;
        pubHolWorkTimePersonApproTimeRoundingMethod: KnockoutObservable<number>;
        pubHolWorkTimePersonApproTimeRoundingTime: KnockoutObservable<number>;
        pubHolWorkTimePersonApproTimeRounding: KnockoutObservable<number>;
        
        otTimePersonDeductTimeRoundingMethod: KnockoutObservable<number>;
        otTimePersonDeductTimeRoundingTime: KnockoutObservable<number>;
        otTimePersonDeductTimeRounding: KnockoutObservable<number>;
        workTimePersonDeductTimeRoundingMethod: KnockoutObservable<number>;
        workTimePersonDeductTimeRoundingTime: KnockoutObservable<number>;
        workTimePersonDeductTimeRounding: KnockoutObservable<number>;
        pubHolWorkTimePersonDeductTimeRoundingMethod: KnockoutObservable<number>;
        pubHolWorkTimePersonDeductTimeRoundingTime: KnockoutObservable<number>;
        pubHolWorkTimePersonDeductTimeRounding: KnockoutObservable<number>;
        
        otTimePublicApproTimeRoundingMethod: KnockoutObservable<number>;
        otTimePublicApproTimeRoundingTime: KnockoutObservable<number>;
        otTimePublicApproTimeRounding: KnockoutObservable<number>;
        workTimePublicApproTimeRoundingMethod: KnockoutObservable<number>;
        workTimePublicApproTimeRoundingTime: KnockoutObservable<number>;
        workTimePublicApproTimeRounding: KnockoutObservable<number>;
        pubHolWorkTimePublicApproTimeRoundingMethod: KnockoutObservable<number>;
        pubHolWorkTimePublicApproTimeRoundingTime: KnockoutObservable<number>;
        pubHolWorkTimePublicApproTimeRounding: KnockoutObservable<number>;
        
        listRoundingBreakTimezone: KnockoutObservableArray<any>;
        listRoundingBreakTime: KnockoutObservableArray<any>;
        listRoundingTimeValue: KnockoutObservableArray<EnumConstantDto>;
        listRoundingValue: KnockoutObservableArray<EnumConstantDto>;
        
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
            
            _self.otTimePersonApproTimeRoundingEnable = ko.observable(null);
            _self.otTimePersonApproTimeRoundingMethod = ko.observable(0); 
            _self.otTimePersonApproTimeRoundingMethod.subscribe((v) => {
                if (v === 1) {
                    _self.otTimePersonApproTimeRoundingEnable(true);
                } else {
                    _self.otTimePersonApproTimeRoundingEnable(false);
                }
            });
            _self.otTimePersonApproTimeRoundingTime = ko.observable(0);
            _self.otTimePersonApproTimeRounding = ko.observable(0);
            _self.workTimePersonApproTimeRoundingMethod = ko.observable(0);
            _self.workTimePersonApproTimeRoundingTime = ko.observable(0);
            _self.workTimePersonApproTimeRounding = ko.observable(0);
            _self.pubHolWorkTimePersonApproTimeRoundingMethod = ko.observable(0);
            _self.pubHolWorkTimePersonApproTimeRoundingTime = ko.observable(0);
            _self.pubHolWorkTimePersonApproTimeRounding = ko.observable(0);
            
            _self.otTimePersonDeductTimeRoundingMethod = ko.observable(0);
            _self.otTimePersonDeductTimeRoundingTime = ko.observable(0);
            _self.otTimePersonDeductTimeRounding = ko.observable(0);
            _self.workTimePersonDeductTimeRoundingMethod = ko.observable(0);
            _self.workTimePersonDeductTimeRoundingTime = ko.observable(0);
            _self.workTimePersonDeductTimeRounding = ko.observable(0);
            _self.pubHolWorkTimePersonDeductTimeRoundingMethod = ko.observable(0);
            _self.pubHolWorkTimePersonDeductTimeRoundingTime = ko.observable(0);
            _self.pubHolWorkTimePersonDeductTimeRounding = ko.observable(0);
            
            _self.otTimePublicApproTimeRoundingMethod = ko.observable(0);
            _self.otTimePublicApproTimeRoundingTime = ko.observable(0);
            _self.otTimePublicApproTimeRounding = ko.observable(0);
            _self.workTimePublicApproTimeRoundingMethod = ko.observable(0);
            _self.workTimePublicApproTimeRoundingTime = ko.observable(0);
            _self.workTimePublicApproTimeRounding = ko.observable(0);
            _self.pubHolWorkTimePublicApproTimeRoundingMethod = ko.observable(0);
            _self.pubHolWorkTimePublicApproTimeRoundingTime = ko.observable(0);
            _self.pubHolWorkTimePublicApproTimeRounding = ko.observable(0);          
            
            //_self.listRoundingBreakTimezone = ko.observableArray([]);
            //_self.listRoundingBreakTime = ko.observableArray([]);
            _self.listRoundingBreakTimezone = ko.observableArray([
                { value: 0, localizedName: nts.uk.resource.getText("KMK003_198") },
                { value: 1, localizedName: nts.uk.resource.getText("KMK003_199") }
            ]);
            _self.listRoundingBreakTime = ko.observableArray([
                { value: 0, localizedName: nts.uk.resource.getText("KMK003_87") },
                { value: 1, localizedName: nts.uk.resource.getText("KMK003_88") }
            ]);
            _self.listRoundingTimeValue = ko.observableArray([]);
            _self.listRoundingValue = ko.observableArray([]);
            
            //_self.listRoundingBreakTimezone(_self.settingEnum.roundingBreakTimezone);
            //_self.listRoundingBreakTime(_self.settingEnum.roundingBreakTime);            
            _self.listRoundingTimeValue(_self.settingEnum.roundingTime);
            _self.listRoundingValue(_self.settingEnum.roundingSimple);        
            
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
                value == "2" ? _self.isDetailMode(true) : _self.isDetailMode(false);
            });
        }
        
        /**
         * Start tab
         */
        public startTab(screenMode: any): void {
            let _self = this;
            screenMode() == "2" ? _self.isDetailMode(true) : _self.isDetailMode(false);
            _self.workTimeDailyAtr(_self.model.workTimeSetting.workTimeDivision.workTimeDailyAtr());
            _self.workTimeMethodSet(_self.model.workTimeSetting.workTimeDivision.workTimeMethodSet());
        }
        
        /**
         * UI - All: change WorkSetting mode
         */
        private changeWorkSettingMode(): void {
            let _self = this;        
                          
            if (_self.workTimeDailyAtr() === WorkTimeDailyAtr.REGULAR_WORK) {
                // Regular work
                switch (_self.workTimeMethodSet()) {
                    case WorkTimeMethodSet.FIXED_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.commonSetting.goOutSet)) {
                            _self.model.fixedWorkSetting.commonSetting.goOutSet = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.fixedWorkSetting.commonSetting.goOutSet);                                
                    } break;
                    case WorkTimeMethodSet.DIFFTIME_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.diffWorkSetting.commonSet.goOutSet)) {
                            _self.model.diffWorkSetting.commonSet.goOutSet = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.diffWorkSetting.commonSet.goOutSet);                   
                    } break;
                    case WorkTimeMethodSet.FLOW_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.flowWorkSetting.commonSetting.goOutSet)) {
                            _self.model.flowWorkSetting.commonSetting.goOutSet = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.flowWorkSetting.commonSetting.goOutSet);                                  
                    } break;               
                    default: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.commonSetting.goOutSet)) {
                            _self.model.fixedWorkSetting.commonSetting.goOutSet = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.fixedWorkSetting.commonSetting.goOutSet);    
                    }
                } 
            } else {
                // Flex work
                if (nts.uk.util.isNullOrUndefined(_self.model.flexWorkSetting.commonSetting.goOutSet)) {
                    _self.model.flexWorkSetting.commonSetting.goOutSet = _self.createBinding();                           
                }
                _self.changeBinding(_self.model.flexWorkSetting.commonSetting.goOutSet);           
            }               
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
            
            _self.totalRoundingSameFrameRoundingSet = goOutSet.totalRoundingSet.setSameFrameRounding;
            _self.totalRoundingFrameStraddRoundingSet = goOutSet.totalRoundingSet.frameStraddRoundingSet;
            
            _self.otTimePersonApproTimeRoundingMethod = goOutSet.diffTimezoneSetting.ottimezone.privateUnionGoOut.approTimeRoundingSetting.roundingMethod;
            _self.otTimePersonApproTimeRoundingTime = goOutSet.diffTimezoneSetting.ottimezone.privateUnionGoOut.approTimeRoundingSetting.roundingSetting.roundingTime;
            _self.otTimePersonApproTimeRounding = goOutSet.diffTimezoneSetting.ottimezone.privateUnionGoOut.approTimeRoundingSetting.roundingSetting.rounding;
            _self.workTimePersonApproTimeRoundingMethod = goOutSet.diffTimezoneSetting.workTimezone.privateUnionGoOut.approTimeRoundingSetting.roundingMethod;
            _self.workTimePersonApproTimeRoundingTime = goOutSet.diffTimezoneSetting.workTimezone.privateUnionGoOut.approTimeRoundingSetting.roundingSetting.roundingTime;
            _self.workTimePersonApproTimeRounding = goOutSet.diffTimezoneSetting.workTimezone.privateUnionGoOut.approTimeRoundingSetting.roundingSetting.rounding;
            _self.pubHolWorkTimePersonApproTimeRoundingMethod = goOutSet.diffTimezoneSetting.pubHolWorkTimezone.privateUnionGoOut.approTimeRoundingSetting.roundingMethod;
            _self.pubHolWorkTimePersonApproTimeRoundingTime = goOutSet.diffTimezoneSetting.pubHolWorkTimezone.privateUnionGoOut.approTimeRoundingSetting.roundingSetting.roundingTime;
            _self.pubHolWorkTimePersonApproTimeRounding = goOutSet.diffTimezoneSetting.pubHolWorkTimezone.privateUnionGoOut.approTimeRoundingSetting.roundingSetting.rounding;
            
            _self.otTimePersonDeductTimeRoundingMethod = goOutSet.diffTimezoneSetting.ottimezone.privateUnionGoOut.deductTimeRoundingSetting.roundingMethod;
            _self.otTimePersonDeductTimeRoundingTime = goOutSet.diffTimezoneSetting.ottimezone.privateUnionGoOut.deductTimeRoundingSetting.roundingSetting.roundingTime;
            _self.otTimePersonDeductTimeRounding = goOutSet.diffTimezoneSetting.ottimezone.privateUnionGoOut.deductTimeRoundingSetting.roundingSetting.rounding;
            _self.workTimePersonDeductTimeRoundingMethod = goOutSet.diffTimezoneSetting.workTimezone.privateUnionGoOut.deductTimeRoundingSetting.roundingMethod;
            _self.workTimePersonDeductTimeRoundingTime = goOutSet.diffTimezoneSetting.workTimezone.privateUnionGoOut.deductTimeRoundingSetting.roundingSetting.roundingTime;
            _self.workTimePersonDeductTimeRounding = goOutSet.diffTimezoneSetting.workTimezone.privateUnionGoOut.deductTimeRoundingSetting.roundingSetting.rounding;
            _self.pubHolWorkTimePersonDeductTimeRoundingMethod = goOutSet.diffTimezoneSetting.pubHolWorkTimezone.privateUnionGoOut.deductTimeRoundingSetting.roundingMethod;
            _self.pubHolWorkTimePersonDeductTimeRoundingTime = goOutSet.diffTimezoneSetting.pubHolWorkTimezone.privateUnionGoOut.deductTimeRoundingSetting.roundingSetting.roundingTime;
            _self.pubHolWorkTimePersonDeductTimeRounding = goOutSet.diffTimezoneSetting.pubHolWorkTimezone.privateUnionGoOut.deductTimeRoundingSetting.roundingSetting.rounding;
            
            _self.otTimePublicApproTimeRoundingMethod = goOutSet.diffTimezoneSetting.ottimezone.officalUseCompenGoOut.approTimeRoundingSetting.roundingMethod;
            _self.otTimePublicApproTimeRoundingTime = goOutSet.diffTimezoneSetting.ottimezone.officalUseCompenGoOut.approTimeRoundingSetting.roundingSetting.roundingTime;
            _self.otTimePublicApproTimeRounding = goOutSet.diffTimezoneSetting.ottimezone.officalUseCompenGoOut.approTimeRoundingSetting.roundingSetting.rounding;
            _self.workTimePublicApproTimeRoundingMethod = goOutSet.diffTimezoneSetting.workTimezone.officalUseCompenGoOut.approTimeRoundingSetting.roundingMethod;
            _self.workTimePublicApproTimeRoundingTime = goOutSet.diffTimezoneSetting.workTimezone.officalUseCompenGoOut.approTimeRoundingSetting.roundingSetting.roundingTime;
            _self.workTimePublicApproTimeRounding = goOutSet.diffTimezoneSetting.workTimezone.officalUseCompenGoOut.approTimeRoundingSetting.roundingSetting.rounding;
            _self.pubHolWorkTimePublicApproTimeRoundingMethod = goOutSet.diffTimezoneSetting.pubHolWorkTimezone.officalUseCompenGoOut.approTimeRoundingSetting.roundingMethod;
            _self.pubHolWorkTimePublicApproTimeRoundingTime = goOutSet.diffTimezoneSetting.pubHolWorkTimezone.officalUseCompenGoOut.approTimeRoundingSetting.roundingSetting.roundingTime;
            _self.pubHolWorkTimePublicApproTimeRounding = goOutSet.diffTimezoneSetting.pubHolWorkTimezone.officalUseCompenGoOut.approTimeRoundingSetting.roundingSetting.rounding;          
        }
        
        /**
         * UI - Simple: change Binding Simple mode 
         */
        private changeBindingSimple(goOutSet: WorkTimezoneGoOutSetModel): void {
            let _self = this;
            _self.changeBindingDetail(goOutSet);
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
