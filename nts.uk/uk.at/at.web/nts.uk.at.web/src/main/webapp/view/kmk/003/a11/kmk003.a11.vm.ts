module a11 {
    
    import WorkTimeDailyAtr = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeDailyAtr;
    import WorkTimeMethodSet = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeMethodSet
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    
    import WorkTimezoneOtherSubHolTimeSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneOtherSubHolTimeSetModel;
    import SubHolTransferSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.SubHolTransferSetModel;
    
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    import TabMode = nts.uk.at.view.kmk003.a.viewmodel.TabMode;
    
    /**
     * Screen Model - Tab 11
     * 就業時間帯の共通設定 -> 代休時間設定
     * WorkTimeCommonSet -> subHolTimeSet (SubstitutionWorkTimeSetting)
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
        workdayOffTimeUseDivision: KnockoutObservable<boolean>;
        workdayOffTimeSubHolTransferSetAtr: KnockoutObservable<number>;
        workdayOffTimeOneDayTime: KnockoutObservable<number>;
        workdayOffTimeHalfDayTime: KnockoutObservable<number>;
        workdayOffTimeCertainTime: KnockoutObservable<number>;
        
        fromOverTimeUseDivision: KnockoutObservable<boolean>;
        fromOverTimeSubHolTransferSetAtr: KnockoutObservable<number>;
        fromOverTimeOneDayTime: KnockoutObservable<number>;
        fromOverTimeHalfDayTime: KnockoutObservable<number>;
        fromOverTimeCertainTime: KnockoutObservable<number>;
    
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
    
            // Init all data          
            _self.workdayOffTimeUseDivision = ko.observable(false);
            _self.workdayOffTimeSubHolTransferSetAtr = ko.observable(0);
            _self.workdayOffTimeOneDayTime = ko.observable(0);
            _self.workdayOffTimeHalfDayTime = ko.observable(0);
            _self.workdayOffTimeCertainTime = ko.observable(0);
            
            _self.fromOverTimeUseDivision = ko.observable(false);
            _self.fromOverTimeSubHolTransferSetAtr = ko.observable(0);
            _self.fromOverTimeOneDayTime = ko.observable(0);
            _self.fromOverTimeHalfDayTime = ko.observable(0);
            _self.fromOverTimeCertainTime = ko.observable(0);
    
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
                        if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.commonSetting.subHolTimeSet) || 
                                _self.model.fixedWorkSetting.commonSetting.subHolTimeSet.length === 0) {
                            _self.model.fixedWorkSetting.commonSetting.subHolTimeSet = _self.createBinding();                           
                        } 
                        _self.changeBinding(_self.model.fixedWorkSetting.commonSetting.subHolTimeSet);                                    
                    } break;
                    case WorkTimeMethodSet.DIFFTIME_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.diffWorkSetting.commonSet.subHolTimeSet) || 
                                _self.model.diffWorkSetting.commonSet.subHolTimeSet.length === 0) {
                            _self.model.diffWorkSetting.commonSet.subHolTimeSet = _self.createBinding();                           
                        } 
                        _self.changeBinding(_self.model.diffWorkSetting.commonSet.subHolTimeSet);                                      
                    } break;
                    case WorkTimeMethodSet.FLOW_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.flowWorkSetting.commonSetting.subHolTimeSet) || 
                                _self.model.flowWorkSetting.commonSetting.subHolTimeSet.length === 0) {
                            _self.model.flowWorkSetting.commonSetting.subHolTimeSet = _self.createBinding();                           
                        } 
                        _self.changeBinding(_self.model.flowWorkSetting.commonSetting.subHolTimeSet);                 
                    } break;               
                    default: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.commonSetting.subHolTimeSet) || 
                                _self.model.fixedWorkSetting.commonSetting.subHolTimeSet.length === 0) {
                            _self.model.fixedWorkSetting.commonSetting.subHolTimeSet = _self.createBinding();                           
                        } 
                        _self.changeBinding(_self.model.fixedWorkSetting.commonSetting.subHolTimeSet);                 
                    }
                } 
            } else {
                // Flex work
                if (nts.uk.util.isNullOrUndefined(_self.model.flexWorkSetting.commonSetting.subHolTimeSet) || 
                        _self.model.flexWorkSetting.commonSetting.subHolTimeSet.length === 0) {
                    _self.model.flexWorkSetting.commonSetting.subHolTimeSet = _self.createBinding();                           
                } 
                _self.changeBinding(_self.model.flexWorkSetting.commonSetting.subHolTimeSet);           
            }               
        }       
        
        /**
         * UI - All: create new Binding data
         */
        private createBinding(): WorkTimezoneOtherSubHolTimeSetModel[] {
            let _self = this;           
            let result: WorkTimezoneOtherSubHolTimeSetModel[] = [];
            
            let fromOverTimeSubstitutionSet: WorkTimezoneOtherSubHolTimeSetModel = new WorkTimezoneOtherSubHolTimeSetModel();
            fromOverTimeSubstitutionSet.originAtr(OriginAtr.FROM_OVER_TIME);
            result.push(fromOverTimeSubstitutionSet);
            
            let workdayOffTimeSubstitutionSet: WorkTimezoneOtherSubHolTimeSetModel = new WorkTimezoneOtherSubHolTimeSetModel();
            workdayOffTimeSubstitutionSet.originAtr(OriginAtr.WORK_DAY_OFF_TIME);
            result.push(workdayOffTimeSubstitutionSet);
            
            return result;
        }
        
        /**
         * UI - All: change Binding mode
         */
        private changeBinding(subHolTimeSet: WorkTimezoneOtherSubHolTimeSetModel[]): void {
            let _self = this;
            if (_self.isDetailMode()) {
                _self.changeBindingDetail(subHolTimeSet); 
            } else {
                _self.changeBindingSimple(subHolTimeSet); 
            }  
        }
        
        /**
         * UI - Detail: change Binding Detail mode
         */
        private changeBindingDetail(subHolTimeSet: WorkTimezoneOtherSubHolTimeSetModel[]): void {
            let _self = this;    
            let workdayOffTimeSubstitutionSet: WorkTimezoneOtherSubHolTimeSetModel = _.find(subHolTimeSet, (o) => o.originAtr() === OriginAtr.WORK_DAY_OFF_TIME);       
            if (nts.uk.util.isNullOrUndefined(workdayOffTimeSubstitutionSet)) {
                workdayOffTimeSubstitutionSet = new WorkTimezoneOtherSubHolTimeSetModel();
                workdayOffTimeSubstitutionSet.originAtr(OriginAtr.WORK_DAY_OFF_TIME);
            }
            let fromOverTimeSubstitutionSet: WorkTimezoneOtherSubHolTimeSetModel = _.find(subHolTimeSet, (o) => o.originAtr() === OriginAtr.FROM_OVER_TIME);
            if (nts.uk.util.isNullOrUndefined(fromOverTimeSubstitutionSet)) {
                fromOverTimeSubstitutionSet = new WorkTimezoneOtherSubHolTimeSetModel();
                fromOverTimeSubstitutionSet.originAtr(OriginAtr.FROM_OVER_TIME);
            }

            // Get model value into view model
            _self.workdayOffTimeUseDivision(workdayOffTimeSubstitutionSet.subHolTimeSet.useDivision());
            _self.workdayOffTimeSubHolTransferSetAtr(workdayOffTimeSubstitutionSet.subHolTimeSet.subHolTransferSetAtr());
            _self.workdayOffTimeOneDayTime(workdayOffTimeSubstitutionSet.subHolTimeSet.designatedTime.oneDayTime());
            _self.workdayOffTimeHalfDayTime(workdayOffTimeSubstitutionSet.subHolTimeSet.designatedTime.halfDayTime());
            _self.workdayOffTimeCertainTime(workdayOffTimeSubstitutionSet.subHolTimeSet.certainTime());
            
            _self.fromOverTimeUseDivision(fromOverTimeSubstitutionSet.subHolTimeSet.useDivision());
            _self.fromOverTimeSubHolTransferSetAtr(fromOverTimeSubstitutionSet.subHolTimeSet.subHolTransferSetAtr());
            _self.fromOverTimeOneDayTime(fromOverTimeSubstitutionSet.subHolTimeSet.designatedTime.oneDayTime());
            _self.fromOverTimeHalfDayTime(fromOverTimeSubstitutionSet.subHolTimeSet.designatedTime.halfDayTime());
            _self.fromOverTimeCertainTime(fromOverTimeSubstitutionSet.subHolTimeSet.certainTime());
            
            // Update into model in case of data change
            _self.workdayOffTimeUseDivision.subscribe(newValue => workdayOffTimeSubstitutionSet.subHolTimeSet.useDivision(newValue));            
            _self.workdayOffTimeSubHolTransferSetAtr.subscribe(newValue => {
                if (newValue == SubHolTransferSetAtr.SPECIFIED_TIME_SUB_HOL) {
                    if (typeof _self.workdayOffTimeCertainTime() !== 'number') {
                        _self.workdayOffTimeCertainTime(0);
                    }                   
                } else {
                    if (typeof _self.workdayOffTimeOneDayTime() !== 'number') {
                        _self.workdayOffTimeOneDayTime(0);
                    }  
                    if (typeof _self.workdayOffTimeHalfDayTime() !== 'number') {
                        _self.workdayOffTimeHalfDayTime(0);
                    }                 
                }
                workdayOffTimeSubstitutionSet.subHolTimeSet.subHolTransferSetAtr(newValue);
            });           
            _self.workdayOffTimeOneDayTime.subscribe(newValue => workdayOffTimeSubstitutionSet.subHolTimeSet.designatedTime.oneDayTime(newValue));
            _self.workdayOffTimeHalfDayTime.subscribe(newValue => workdayOffTimeSubstitutionSet.subHolTimeSet.designatedTime.halfDayTime(newValue));
            _self.workdayOffTimeCertainTime.subscribe(newValue => workdayOffTimeSubstitutionSet.subHolTimeSet.certainTime(newValue));          
            
            _self.fromOverTimeUseDivision.subscribe(newValue => fromOverTimeSubstitutionSet.subHolTimeSet.useDivision(newValue));
            _self.fromOverTimeSubHolTransferSetAtr.subscribe(newValue => {                            
                if (newValue == SubHolTransferSetAtr.SPECIFIED_TIME_SUB_HOL) {
                    if (typeof _self.fromOverTimeCertainTime() !== 'number') {
                        _self.fromOverTimeCertainTime(0);
                    }                  
                } else {
                    if (typeof _self.fromOverTimeOneDayTime() !== 'number') {
                        _self.fromOverTimeOneDayTime(0);
                    }  
                    if (typeof _self.fromOverTimeHalfDayTime() !== 'number') {
                        _self.fromOverTimeHalfDayTime(0);
                    }                 
                }
                fromOverTimeSubstitutionSet.subHolTimeSet.subHolTransferSetAtr(newValue);
            }); 
            _self.fromOverTimeOneDayTime.subscribe(newValue => fromOverTimeSubstitutionSet.subHolTimeSet.designatedTime.oneDayTime(newValue));
            _self.fromOverTimeHalfDayTime.subscribe(newValue => fromOverTimeSubstitutionSet.subHolTimeSet.designatedTime.halfDayTime(newValue));
            _self.fromOverTimeCertainTime.subscribe(newValue => fromOverTimeSubstitutionSet.subHolTimeSet.certainTime(newValue));           
        }
        
        /**
         * UI - Simple: change Binding Simple mode 
         */
        private changeBindingSimple(subHolTimeSet: WorkTimezoneOtherSubHolTimeSetModel[]): void {
            let _self = this;            
            
            // Handle workdayOffTimeTransferSet.designatedTime only
            let workdayOffTimeSubstitutionSet: WorkTimezoneOtherSubHolTimeSetModel = _.find(subHolTimeSet, (o) => o.originAtr() === OriginAtr.WORK_DAY_OFF_TIME);
            _self.workdayOffTimeOneDayTime = workdayOffTimeSubstitutionSet.subHolTimeSet.designatedTime.oneDayTime;
            _self.workdayOffTimeHalfDayTime = workdayOffTimeSubstitutionSet.subHolTimeSet.designatedTime.halfDayTime;
        }     
    }
    
    enum OriginAtr {
        FROM_OVER_TIME,
        WORK_DAY_OFF_TIME
    }

    enum SubHolTransferSetAtr {
        SPECIFIED_TIME_SUB_HOL,
        CERTAIN_TIME_EXC_SUB_HOL
    }
   
    /**
     * Knockout Binding Handler - Tab 11
     */
    class KMK003A11BindingHandler implements KnockoutBindingHandler {
        
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any,
            bindingContext: KnockoutBindingContext): void {
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any,
            bindingContext: KnockoutBindingContext): void {
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a11/index.xhtml').serialize();
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
                
                // update name id for radio
                $('.inputRadioHol').attr('name', nts.uk.util.randomId() + '_Hol');
                $('.inputRadioOT').attr('name', nts.uk.util.randomId() + '_Ot');
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A11'] = new KMK003A11BindingHandler();
}
