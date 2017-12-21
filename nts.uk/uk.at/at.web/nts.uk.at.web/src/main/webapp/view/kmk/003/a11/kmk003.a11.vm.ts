module a11 {
    
    import WorkTimeDailyAtr = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeDailyAtr;
    import WorkTimeMethodSet = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeMethodSet
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    
    import WorkTimezoneOtherSubHolTimeSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneOtherSubHolTimeSetModel;
    import SubHolTransferSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.SubHolTransferSetModel;
    
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    
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
        fromOverTimeTransferSet: SubHolTransferSetModel;
        workdayOffTimeTransferSet: SubHolTransferSetModel;
    
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
            _self.fromOverTimeTransferSet = new SubHolTransferSetModel();
            _self.workdayOffTimeTransferSet = new SubHolTransferSetModel();
    
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
            let fromOverTimeSubstitutionSet: WorkTimezoneOtherSubHolTimeSetModel = _.find(subHolTimeSet, (o) => o.originAtr() === OriginAtr.FROM_OVER_TIME);
            let workdayOffTimeSubstitutionSet: WorkTimezoneOtherSubHolTimeSetModel = _.find(subHolTimeSet, (o) => o.originAtr() === OriginAtr.WORK_DAY_OFF_TIME);
            _self.fromOverTimeTransferSet = fromOverTimeSubstitutionSet.subHolTimeSet;
            _self.workdayOffTimeTransferSet = workdayOffTimeSubstitutionSet.subHolTimeSet;

            // Filter time data when switch radio button
            _self.fromOverTimeTransferSet.subHolTransferSetAtr.subscribe((newValue) => {                            
                if (newValue == SubHolTransferSetAtr.SPECIFIED_TIME_SUB_HOL) {
                    console.log(_self.fromOverTimeTransferSet.certainTime());
                    if (typeof _self.fromOverTimeTransferSet.certainTime() !== 'number') {
                        _self.fromOverTimeTransferSet.certainTime(0);
                    }                  
                } else {
                    if (typeof _self.fromOverTimeTransferSet.designatedTime.oneDayTime() !== 'number') {
                        _self.fromOverTimeTransferSet.designatedTime.oneDayTime(0);
                    }  
                    if (typeof _self.fromOverTimeTransferSet.designatedTime.halfDayTime() !== 'number') {
                        _self.fromOverTimeTransferSet.designatedTime.halfDayTime(0);
                    }                 
                }
            });
            _self.workdayOffTimeTransferSet.subHolTransferSetAtr.subscribe((newValue) => {
                if (newValue == SubHolTransferSetAtr.SPECIFIED_TIME_SUB_HOL) {
                    if (typeof _self.workdayOffTimeTransferSet.certainTime() !== 'number') {
                        _self.workdayOffTimeTransferSet.certainTime(0);
                    }                   
                } else {
                    if (typeof _self.workdayOffTimeTransferSet.designatedTime.oneDayTime() !== 'number') {
                        _self.workdayOffTimeTransferSet.designatedTime.oneDayTime(0);
                    }  
                    if (typeof _self.workdayOffTimeTransferSet.designatedTime.halfDayTime() !== 'number') {
                        _self.workdayOffTimeTransferSet.designatedTime.halfDayTime(0);
                    }                 
                }
            });
        }
        
        /**
         * UI - Simple: change Binding Simple mode 
         */
        private changeBindingSimple(subHolTimeSet: WorkTimezoneOtherSubHolTimeSetModel[]): void {
            let _self = this;            
            
            // Handle workdayOffTimeTransferSet.designatedTime only
            let workdayOffTimeSubstitutionSet: WorkTimezoneOtherSubHolTimeSetModel = _.find(subHolTimeSet, (o) => o.originAtr() === OriginAtr.WORK_DAY_OFF_TIME);
            _self.workdayOffTimeTransferSet = workdayOffTimeSubstitutionSet.subHolTimeSet;
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
