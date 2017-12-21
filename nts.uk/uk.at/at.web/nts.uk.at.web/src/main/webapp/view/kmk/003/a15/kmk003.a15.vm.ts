module a15 {
    
    import WorkTimeDailyAtr = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeDailyAtr;
    import WorkTimeMethodSet = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeMethodSet
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    
    import TimeRoundingSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRoundingSettingModel;
    import WorkTimezoneMedicalSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneMedicalSetModel;
    
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    
    /**
     * Screen Model - Tab 15
     * 就業時間帯の共通設定 -> 医療設定
     * WorkTimeCommonSet -> MedicalWorkTimeSetting
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
        dayShiftApplicationTime: KnockoutObservable<number>;
        nightShiftApplicationTime: KnockoutObservable<number>;
        dayShiftSettingRoundingTime: KnockoutObservable<number>;
        dayShiftSettingRounding: KnockoutObservable<number>;
        nightShiftSettingRoundingTime: KnockoutObservable<number>;
        nightShiftSettingRounding: KnockoutObservable<number>;   
        
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
            _self.dayShiftApplicationTime = ko.observable(null);
            _self.nightShiftApplicationTime = ko.observable(null);
            _self.dayShiftSettingRoundingTime = ko.observable(0);
            _self.dayShiftSettingRounding = ko.observable(0);
            _self.nightShiftSettingRoundingTime = ko.observable(0);
            _self.nightShiftSettingRounding = ko.observable(0);

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
                        if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.commonSetting.medicalSet) || 
                                _self.model.fixedWorkSetting.commonSetting.medicalSet.length === 0) {
                            _self.model.fixedWorkSetting.commonSetting.medicalSet = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.fixedWorkSetting.commonSetting.medicalSet);                                    
                    } break;
                    case WorkTimeMethodSet.DIFFTIME_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.diffWorkSetting.commonSet.medicalSet) || 
                                _self.model.diffWorkSetting.commonSet.medicalSet.length === 0) {
                            _self.model.diffWorkSetting.commonSet.medicalSet = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.diffWorkSetting.commonSet.medicalSet);
                    } break;
                    case WorkTimeMethodSet.FLOW_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.flowWorkSetting.commonSetting.medicalSet) || 
                                _self.model.flowWorkSetting.commonSetting.medicalSet.length === 0) {
                            _self.model.flowWorkSetting.commonSetting.medicalSet = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.flowWorkSetting.commonSetting.medicalSet);
                    } break;               
                    default: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.commonSetting.medicalSet) || 
                                _self.model.fixedWorkSetting.commonSetting.medicalSet.length === 0) {
                            _self.model.fixedWorkSetting.commonSetting.medicalSet = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.fixedWorkSetting.commonSetting.medicalSet);
                    }
                } 
            } else {
                // Flex work
                if (nts.uk.util.isNullOrUndefined(_self.model.flexWorkSetting.commonSetting.medicalSet) || 
                        _self.model.flexWorkSetting.commonSetting.medicalSet.length === 0) {
                    _self.model.flexWorkSetting.commonSetting.medicalSet = _self.createBinding();                           
                }
                _self.changeBinding(_self.model.flexWorkSetting.commonSetting.medicalSet); 
            }               
        }       
        
        /**
         * UI - All: create new Binding data
         */
        private createBinding(): WorkTimezoneMedicalSetModel[] {
            let _self = this;           
            let result: WorkTimezoneMedicalSetModel[] = [];      
            
            let dayShiftMedicalSet: WorkTimezoneMedicalSetModel = new WorkTimezoneMedicalSetModel();
            dayShiftMedicalSet.workSystemAtr(WorkSystemAtr.DAY_SHIFT);
            result.push(dayShiftMedicalSet);
            
            let nightShiftMedicalSet: WorkTimezoneMedicalSetModel = new WorkTimezoneMedicalSetModel();
            nightShiftMedicalSet.workSystemAtr(WorkSystemAtr.NIGHT_SHIFT);
            result.push(nightShiftMedicalSet);
            
            return result;
        }
        
        /**
         * UI - All: change Binding mode
         */
        private changeBinding(medicalSet: WorkTimezoneMedicalSetModel[]): void {
            let _self = this;
            if (_self.isDetailMode()) {
                _self.changeBindingDetail(medicalSet); 
            } else {
                _self.changeBindingSimple(medicalSet); 
            }  
        }
        
        /**
         * UI - Detail: change Binding Detail mode
         */
        private changeBindingDetail(medicalSet: WorkTimezoneMedicalSetModel[]): void {
            let _self = this;        
            let dayShiftMedicalSet: WorkTimezoneMedicalSetModel = _.find(medicalSet, (o) => o.workSystemAtr() === WorkSystemAtr.DAY_SHIFT);
            let nightShiftMedicalSet: WorkTimezoneMedicalSetModel = _.find(medicalSet, (o) => o.workSystemAtr() === WorkSystemAtr.NIGHT_SHIFT);             
            
            _self.dayShiftApplicationTime = dayShiftMedicalSet.applicationTime;  
            _self.nightShiftApplicationTime = nightShiftMedicalSet.applicationTime;   
            _self.dayShiftSettingRoundingTime = dayShiftMedicalSet.roundingSet.roundingTime;
            _self.dayShiftSettingRounding = dayShiftMedicalSet.roundingSet.rounding;
            _self.nightShiftSettingRoundingTime = nightShiftMedicalSet.roundingSet.roundingTime;
            _self.nightShiftSettingRounding = nightShiftMedicalSet.roundingSet.rounding;
        }
        
        /**
         * UI - Simple: change Binding Simple mode 
         */
        private changeBindingSimple(medicalSet: WorkTimezoneMedicalSetModel[]): void {
            let _self = this;
            _self.changeBindingDetail(medicalSet);  
        }
    }
    
    enum WorkSystemAtr {
        DAY_SHIFT,
        NIGHT_SHIFT
    }
    
    /**
     * Knockout Binding Handler - Tab 15
     */
    class KMK003A15BindingHandler implements KnockoutBindingHandler {
        
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
                .mergeRelativePath('/view/kmk/003/a15/index.xhtml').serialize();
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
    
    ko.bindingHandlers['ntsKMK003A15'] = new KMK003A15BindingHandler();
}