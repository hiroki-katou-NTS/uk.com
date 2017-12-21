module a13 {
    
    import WorkTimeDailyAtr = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeDailyAtr;
    import WorkTimeMethodSet = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeMethodSet
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    
    import TimeRoundingSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRoundingSettingModel;
    
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    
    /**
     * Screen Model - Tab 13
     * 就業時間帯の共通設定 -> 臨時設定
     * WorkTimeCommonSet -> TemporaryWorkTimeSet
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
        temporaryWorkTimeSetting: TimeRoundingSettingModel;
           
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
            _self.temporaryWorkTimeSetting = new TimeRoundingSettingModel();
            _self.listRoundingTimeValue = ko.observableArray([]);
            _self.listRoundingValue = ko.observableArray([]);
            
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
                        if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.commonSetting.extraordTimeSet.timeRoundingSet)) {
                            _self.model.fixedWorkSetting.commonSetting.extraordTimeSet.timeRoundingSet = _self.createBinding();                           
                        } 
                        _self.changeBinding(_self.model.fixedWorkSetting.commonSetting.extraordTimeSet.timeRoundingSet);                                    
                    } break;
                    case WorkTimeMethodSet.DIFFTIME_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.diffWorkSetting.commonSet.extraordTimeSet.timeRoundingSet)) {
                            _self.model.diffWorkSetting.commonSet.extraordTimeSet.timeRoundingSet = _self.createBinding();                           
                        } 
                        _self.changeBinding(_self.model.diffWorkSetting.commonSet.extraordTimeSet.timeRoundingSet);
                    } break;
                    case WorkTimeMethodSet.FLOW_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.flowWorkSetting.commonSetting.extraordTimeSet.timeRoundingSet)) {
                            _self.model.flowWorkSetting.commonSetting.extraordTimeSet.timeRoundingSet = _self.createBinding();                           
                        } 
                        _self.changeBinding(_self.model.flowWorkSetting.commonSetting.extraordTimeSet.timeRoundingSet);
                    } break;               
                    default: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.commonSetting.extraordTimeSet.timeRoundingSet)) {
                            _self.model.fixedWorkSetting.commonSetting.extraordTimeSet.timeRoundingSet = _self.createBinding();                           
                        } 
                        _self.changeBinding(_self.model.fixedWorkSetting.commonSetting.extraordTimeSet.timeRoundingSet);
                    }
                } 
            } else {
                // Flex work
                if (nts.uk.util.isNullOrUndefined(_self.model.flexWorkSetting.commonSetting.extraordTimeSet.timeRoundingSet)) {
                    _self.model.flexWorkSetting.commonSetting.extraordTimeSet.timeRoundingSet = _self.createBinding();                           
                } 
                _self.changeBinding(_self.model.flexWorkSetting.commonSetting.extraordTimeSet.timeRoundingSet); 
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
        private changeBinding(temporaryWorkTimeSetting: TimeRoundingSettingModel): void {
            let _self = this;
            if (_self.isDetailMode()) {
                _self.changeBindingDetail(temporaryWorkTimeSetting); 
            } else {
                _self.changeBindingSimple(temporaryWorkTimeSetting); 
            }  
        }
        
        /**
         * UI - Detail: change Binding Detail mode
         */
        private changeBindingDetail(temporaryWorkTimeSetting: TimeRoundingSettingModel): void {
            let _self = this;           
            _self.temporaryWorkTimeSetting = temporaryWorkTimeSetting;
        }
        
        /**
         * UI - Simple: change Binding Simple mode 
         */
        private changeBindingSimple(temporaryWorkTimeSetting: TimeRoundingSettingModel): void {
            let _self = this;
            _self.changeBindingDetail(temporaryWorkTimeSetting);  
        }
    }
    
    /**
     * Knockout Binding Handler - Tab 13
     */
    class KMK003A13BindingHandler implements KnockoutBindingHandler {
        
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
                .mergeRelativePath('/view/kmk/003/a13/index.xhtml').serialize();
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
    
    ko.bindingHandlers['ntsKMK003A13'] = new KMK003A13BindingHandler();
}