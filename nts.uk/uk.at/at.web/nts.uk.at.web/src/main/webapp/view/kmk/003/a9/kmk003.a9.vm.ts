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
        
        // Screen mode
        isDetailMode: KnockoutObservable<boolean>;
        workTimeDailyAtr: KnockoutObservable<number>;
        workTimeMethodSet: KnockoutObservable<number>;
        
        // Screen data model
        model: MainSettingModel;
        settingEnum: WorkTimeSettingEnumDto;

        // Detail mode - Data
        lateSettingRoundingTime: KnockoutObservable<number>;
        lateSettingRounding: KnockoutObservable<number>;
        leaveEarlySettingRoundingTime: KnockoutObservable<number>;
        leaveEarlySettingRounding: KnockoutObservable<number>;   
        
        listRoundingTimeValue: KnockoutObservableArray<EnumConstantDto>;
        listRoundingValue: KnockoutObservableArray<EnumConstantDto>;
        
        // Simple mode - Data (nothing)        
        
        
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
            _self.lateSettingRoundingTime = ko.observable(0);
            _self.lateSettingRounding = ko.observable(0);
            _self.leaveEarlySettingRoundingTime = ko.observable(0);
            _self.leaveEarlySettingRounding = ko.observable(0);
                             
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
                value == TabMode.DETAIL ? _self.isDetailMode(true) : _self.isDetailMode(false);
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
                              
            if (_self.workTimeDailyAtr() === WorkTimeDailyAtr.REGULAR_WORK) {
                // Regular work
                switch (_self.workTimeMethodSet()) {
                    case WorkTimeMethodSet.FIXED_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.commonSetting.lateEarlySet.otherClassSets) || 
                                _self.model.fixedWorkSetting.commonSetting.lateEarlySet.otherClassSets.length === 0) {
                            _self.model.fixedWorkSetting.commonSetting.lateEarlySet.otherClassSets = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.fixedWorkSetting.commonSetting.lateEarlySet.otherClassSets);                                
                    } break;
                    case WorkTimeMethodSet.DIFFTIME_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.diffWorkSetting.commonSet.lateEarlySet.otherClassSets) || 
                                _self.model.diffWorkSetting.commonSet.lateEarlySet.otherClassSets.length === 0) {
                            _self.model.diffWorkSetting.commonSet.lateEarlySet.otherClassSets = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.diffWorkSetting.commonSet.lateEarlySet.otherClassSets);
                    } break;
                    case WorkTimeMethodSet.FLOW_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.flowWorkSetting.commonSetting.lateEarlySet.otherClassSets) ||
                                _self.model.flowWorkSetting.commonSetting.lateEarlySet.otherClassSets.length === 0) {
                            _self.model.flowWorkSetting.commonSetting.lateEarlySet.otherClassSets = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.flowWorkSetting.commonSetting.lateEarlySet.otherClassSets);
                    } break;               
                    default: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.commonSetting.lateEarlySet.otherClassSets) || 
                                _self.model.fixedWorkSetting.commonSetting.lateEarlySet.otherClassSets.length === 0) {
                            _self.model.fixedWorkSetting.commonSetting.lateEarlySet.otherClassSets = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.fixedWorkSetting.commonSetting.lateEarlySet.otherClassSets);      
                    }
                } 
            } else {
                // Flex work
                if (nts.uk.util.isNullOrUndefined(_self.model.flexWorkSetting.commonSetting.lateEarlySet.otherClassSets) || 
                        _self.model.flexWorkSetting.commonSetting.lateEarlySet.otherClassSets.length === 0) {
                    _self.model.flexWorkSetting.commonSetting.lateEarlySet.otherClassSets = _self.createBinding();                           
                }
                _self.changeBinding(_self.model.flexWorkSetting.commonSetting.lateEarlySet.otherClassSets);             
            }               
        }       
        
        /**
         * UI - All: create new Binding data
         */
        private createBinding(): OtherEmTimezoneLateEarlySetModel[] {
            let _self = this;           
            let result: OtherEmTimezoneLateEarlySetModel[] = [];
            
            let otherClassSetLate: OtherEmTimezoneLateEarlySetModel = new OtherEmTimezoneLateEarlySetModel();
            otherClassSetLate.lateEarlyAtr(LateEarlyAtr.LATE);
            result.push(otherClassSetLate);
            
            let otherClassSetLeaveEarly: OtherEmTimezoneLateEarlySetModel = new OtherEmTimezoneLateEarlySetModel();
            otherClassSetLeaveEarly.lateEarlyAtr(LateEarlyAtr.EARLY);
            result.push(otherClassSetLeaveEarly);
            
            return result;
        }
        
        /**
         * UI - All: change Binding mode
         */
        private changeBinding(otherClassSets: OtherEmTimezoneLateEarlySetModel[]): void {
            let _self = this;
            if (_self.isDetailMode()) {
                _self.changeBindingDetail(otherClassSets); 
            } else {
                _self.changeBindingSimple(otherClassSets); 
            }  
        }
        
        /**
         * UI - Detail: change Binding Detail mode
         */
        private changeBindingDetail(otherClassSets: OtherEmTimezoneLateEarlySetModel[]): void {
            let _self = this;
            let otherClassSetLate: OtherEmTimezoneLateEarlySetModel = _.find(otherClassSets, (o) => o.lateEarlyAtr() === LateEarlyAtr.LATE);
            if (nts.uk.util.isNullOrUndefined(otherClassSetLate)) {
                otherClassSetLate = new OtherEmTimezoneLateEarlySetModel();
                otherClassSetLate.lateEarlyAtr(LateEarlyAtr.LATE);
            }
            let otherClassSetLeaveEarly: OtherEmTimezoneLateEarlySetModel = _.find(otherClassSets, (o) => o.lateEarlyAtr() === LateEarlyAtr.EARLY);
            if (nts.uk.util.isNullOrUndefined(otherClassSetLeaveEarly)) {
                otherClassSetLeaveEarly = new OtherEmTimezoneLateEarlySetModel();
                otherClassSetLeaveEarly.lateEarlyAtr(LateEarlyAtr.EARLY);
            }
            
            // Get model value into view model
            _self.lateSettingRoundingTime(otherClassSetLate.delTimeRoundingSet.roundingTime());
            _self.lateSettingRounding(otherClassSetLate.delTimeRoundingSet.rounding());
            _self.leaveEarlySettingRoundingTime(otherClassSetLeaveEarly.delTimeRoundingSet.roundingTime());
            _self.leaveEarlySettingRounding(otherClassSetLeaveEarly.delTimeRoundingSet.rounding());
            
            // Update into model in case of data change
            _self.lateSettingRoundingTime.subscribe(newValue => otherClassSetLate.delTimeRoundingSet.roundingTime(newValue));
            _self.lateSettingRounding.subscribe(newValue => otherClassSetLate.delTimeRoundingSet.rounding(newValue));
            _self.leaveEarlySettingRoundingTime.subscribe(newValue => otherClassSetLeaveEarly.delTimeRoundingSet.roundingTime(newValue));
            _self.leaveEarlySettingRounding.subscribe(newValue => otherClassSetLeaveEarly.delTimeRoundingSet.rounding(newValue));
        }
        
        /**
         * UI - Simple: change Binding Simple mode 
         */
        private changeBindingSimple(otherClassSets: OtherEmTimezoneLateEarlySetModel[]): void {
            let _self = this;
            _self.changeBindingDetail(otherClassSets); 
        }
    }
    
    enum LateEarlyAtr {
        LATE,
        EARLY
    }
    
    /**
     * Knockout Binding Handler - Tab 9
     */
    class KMK003A9BindingHandler implements KnockoutBindingHandler {
        
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
                .mergeRelativePath('/view/kmk/003/a9/index.xhtml').serialize();
            // Get data
            let input = valueAccessor();
            let screenMode = input.screenMode;
            let model = input.model;
            let settingEnum = input.enum;

            let screenModel = new ScreenModel(screenMode, model, settingEnum);
            $(element).load(webserviceLocator, () => {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                screenModel.startTab(screenMode);
            });
        }

    }
    
    ko.bindingHandlers['ntsKMK003A9'] = new KMK003A9BindingHandler();
}