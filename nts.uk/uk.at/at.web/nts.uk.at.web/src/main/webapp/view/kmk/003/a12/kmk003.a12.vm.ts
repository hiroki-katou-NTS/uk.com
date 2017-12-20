module a12 {
    
    import WorkTimeDailyAtr = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeDailyAtr;
    import WorkTimeMethodSet = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeMethodSet
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    
    import TimeRoundingSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRoundingSettingModel;
    
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    
    /**
     * Screen Model - Tab 12
     * 就業時間帯の共通設定 -> 深夜設定
     * WorkTimeCommonSet -> LateNightSetting
     */
    class ScreenModel {
        
        // Screen mode
        isDetailMode: KnockoutObservable<boolean>;
        workTimeDailyAtr: KnockoutObservable<number>;
        workTimeMethodSet: KnockoutObservable<number>;
        
        // Screen data model
        model: MainSettingModel;
        settingEnum: WorkTimeSettingEnumDto;
        
        // Detail mode - Data
        lateNightSetting: TimeRoundingSettingModel;
           
        listRoundingTimeValue: KnockoutObservableArray<EnumConstantDto>;
        listRoundingValue: KnockoutObservableArray<EnumConstantDto>;
        
        // Simple mode - Data  
        
        
        /**
         * Constructor
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
            
            // Init all data                                            
            _self.lateNightSetting = new TimeRoundingSettingModel();
            _self.listRoundingTimeValue = ko.observableArray([]);
            _self.listRoundingValue = ko.observableArray([]);
            
            _self.listRoundingTimeValue(_self.settingEnum.roundingTime);
            _self.listRoundingValue(_self.settingEnum.roundingSimple.reverse());          
            
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
            
            // Binding value 
            screenMode == "2" ? _self.isDetailMode(true) : _self.isDetailMode(false);
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
                        if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.commonSetting.lateNightTimeSet.roundingSetting)) {
                            _self.model.fixedWorkSetting.commonSetting.lateNightTimeSet.roundingSetting = _self.createBinding();                           
                        } 
                        _self.changeBinding(_self.model.fixedWorkSetting.commonSetting.lateNightTimeSet.roundingSetting);                                    
                    } break;
                    case WorkTimeMethodSet.DIFFTIME_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.diffWorkSetting.commonSet.lateNightTimeSet.roundingSetting)) {
                            _self.model.diffWorkSetting.commonSet.lateNightTimeSet.roundingSetting = _self.createBinding();                           
                        } 
                        _self.changeBinding(_self.model.diffWorkSetting.commonSet.lateNightTimeSet.roundingSetting);
                    } break;
                    case WorkTimeMethodSet.FLOW_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.flowWorkSetting.commonSetting.lateNightTimeSet.roundingSetting)) {
                            _self.model.flowWorkSetting.commonSetting.lateNightTimeSet.roundingSetting = _self.createBinding();                           
                        } 
                        _self.changeBinding(_self.model.flowWorkSetting.commonSetting.lateNightTimeSet.roundingSetting);
                    } break;               
                    default: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.commonSetting.lateNightTimeSet.roundingSetting)) {
                            _self.model.fixedWorkSetting.commonSetting.lateNightTimeSet.roundingSetting = _self.createBinding();                           
                        } 
                        _self.changeBinding(_self.model.fixedWorkSetting.commonSetting.lateNightTimeSet.roundingSetting);
                    }
                } 
            } else {
                // Flex work
                if (nts.uk.util.isNullOrUndefined(_self.model.flexWorkSetting.commonSetting.lateNightTimeSet.roundingSetting)) {
                    _self.model.flexWorkSetting.commonSetting.lateNightTimeSet.roundingSetting = _self.createBinding();                           
                } 
                _self.changeBinding(_self.model.flexWorkSetting.commonSetting.lateNightTimeSet.roundingSetting); 
            }               
        }       
        
        /**
         * UI - All: create new Binding data
         */
        private createBinding(): TimeRoundingSettingModel {
            let _self = this;
            
            let result: TimeRoundingSettingModel = new TimeRoundingSettingModel();           
            return result;
        }
        
        /**
         * UI - All: change Binding mode
         */
        private changeBinding(lateNightSetting: TimeRoundingSettingModel): void {
            let _self = this;
            if (_self.isDetailMode()) {
                _self.changeBindingDetail(lateNightSetting); 
            } else {
                _self.changeBindingSimple(lateNightSetting); 
            }  
        }
        
        /**
         * UI - Detail: change Binding Detail mode
         */
        private changeBindingDetail(lateNightSetting: TimeRoundingSettingModel): void {
            let _self = this;           
            _self.lateNightSetting = lateNightSetting;
        }
        
        /**
         * UI - Simple: change Binding Simple mode 
         */
        private changeBindingSimple(lateNightSetting: TimeRoundingSettingModel): void {
            let _self = this;
            _self.lateNightSetting = lateNightSetting;
        }
    }
      
    /**
     * Knockout Binding Handler - Tab 12
     */
    class KMK003A12BindingHandler implements KnockoutBindingHandler {
        
        /**
         * Constructor
         */
        constructor() {}

        /**
         * Init
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {}

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {          
            let webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a12/index.xhtml').serialize();
            // Get data
            let input = valueAccessor();
            let screenMode = input.screenMode;
            let model = input.model;
            let settingEnum = input.enum;

            let screenModel = new ScreenModel(screenMode, model, settingEnum);
            $(element).load(webserviceLocator, () => {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
            });
        }

    }
    
    ko.bindingHandlers['ntsKMK003A12'] = new KMK003A12BindingHandler();
}