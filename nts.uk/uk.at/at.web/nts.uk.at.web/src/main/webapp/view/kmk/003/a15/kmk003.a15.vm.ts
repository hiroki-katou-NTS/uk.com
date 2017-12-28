module a15 {
    
    import WorkTimeDailyAtr = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeDailyAtr;
    import WorkTimeMethodSet = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeMethodSet
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    
    import TimeRoundingSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRoundingSettingModel;
    import WorkTimezoneMedicalSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneMedicalSetModel;
    
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    import TabMode = nts.uk.at.view.kmk003.a.viewmodel.TabMode;
    
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
        fixedDayShiftApplicationTime: KnockoutObservable<number>;
        fixedNightShiftApplicationTime: KnockoutObservable<number>;
        fixedDayShiftSettingRoundingTime: KnockoutObservable<number>;
        fixedDayShiftSettingRounding: KnockoutObservable<number>;
        fixedNightShiftSettingRoundingTime: KnockoutObservable<number>;
        fixedNightShiftSettingRounding: KnockoutObservable<number>;   
        
        diffDayShiftApplicationTime: KnockoutObservable<number>;
        diffNightShiftApplicationTime: KnockoutObservable<number>;
        diffDayShiftSettingRoundingTime: KnockoutObservable<number>;
        diffDayShiftSettingRounding: KnockoutObservable<number>;
        diffNightShiftSettingRoundingTime: KnockoutObservable<number>;
        diffNightShiftSettingRounding: KnockoutObservable<number>; 
        
        flowDayShiftApplicationTime: KnockoutObservable<number>;
        flowNightShiftApplicationTime: KnockoutObservable<number>;
        flowDayShiftSettingRoundingTime: KnockoutObservable<number>;
        flowDayShiftSettingRounding: KnockoutObservable<number>;
        flowNightShiftSettingRoundingTime: KnockoutObservable<number>;
        flowNightShiftSettingRounding: KnockoutObservable<number>; 
        
        flexDayShiftApplicationTime: KnockoutObservable<number>;
        flexNightShiftApplicationTime: KnockoutObservable<number>;
        flexDayShiftSettingRoundingTime: KnockoutObservable<number>;
        flexDayShiftSettingRounding: KnockoutObservable<number>;
        flexNightShiftSettingRoundingTime: KnockoutObservable<number>;
        flexNightShiftSettingRounding: KnockoutObservable<number>; 
        
        listRoundingTimeValue: KnockoutObservableArray<EnumConstantDto>;
        listRoundingValue: KnockoutObservableArray<EnumConstantDto>;       
        
        // Simple mode - Data  
        
        // UI
        isFixedMode: KnockoutObservable<boolean>;  
        isDiffMode: KnockoutObservable<boolean>;      
        isFlowMode: KnockoutObservable<boolean>;  
        isFlexMode: KnockoutObservable<boolean>;
        
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
            _self.isFixedMode = _self.model.workTimeSetting.isFixed;      
            _self.isDiffMode = _self.model.workTimeSetting.isDiffTime;
            _self.isFlowMode = _self.model.workTimeSetting.isFlow;      
            _self.isFlexMode = _self.model.workTimeSetting.isFlex;     
            
            // Init all data           
            _self.bindingData();           

            _self.listRoundingTimeValue = ko.observableArray([]);
            _self.listRoundingValue = ko.observableArray([]);           
            
            _self.listRoundingTimeValue(_self.settingEnum.roundingTime);
            _self.listRoundingValue(_self.settingEnum.roundingSimple); 
            
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
            screenMode() == TabMode.DETAIL ? _self.isDetailMode(true) : _self.isDetailMode(false); console.log(_self.model);
        }
        
        /**
         * Binding data
         */
        private bindingData() {
            let _self = this;
            
            // Fixed
            let fixedDayShiftMedicalSet: WorkTimezoneMedicalSetModel = _.find(_self.model.fixedWorkSetting.commonSetting.medicalSet(), 
                    (o) => o.workSystemAtr() === WorkSystemAtr.DAY_SHIFT);
            if (nts.uk.util.isNullOrUndefined(fixedDayShiftMedicalSet)) {
                fixedDayShiftMedicalSet = new WorkTimezoneMedicalSetModel();
                fixedDayShiftMedicalSet.workSystemAtr(WorkSystemAtr.DAY_SHIFT);
                _self.model.fixedWorkSetting.commonSetting.medicalSet.push(fixedDayShiftMedicalSet);
            }
            let fixedNightShiftMedicalSet: WorkTimezoneMedicalSetModel = _.find(_self.model.fixedWorkSetting.commonSetting.medicalSet(), 
                    (o) => o.workSystemAtr() === WorkSystemAtr.NIGHT_SHIFT);  
            if (nts.uk.util.isNullOrUndefined(fixedNightShiftMedicalSet)) {
                fixedNightShiftMedicalSet = new WorkTimezoneMedicalSetModel();
                fixedNightShiftMedicalSet.workSystemAtr(WorkSystemAtr.NIGHT_SHIFT);
                _self.model.fixedWorkSetting.commonSetting.medicalSet.push(fixedNightShiftMedicalSet);
            }           
            _self.fixedDayShiftApplicationTime = fixedDayShiftMedicalSet.applicationTime;
            _self.fixedNightShiftApplicationTime = fixedNightShiftMedicalSet.applicationTime;
            _self.fixedDayShiftSettingRoundingTime = fixedDayShiftMedicalSet.roundingSet.roundingTime;
            _self.fixedDayShiftSettingRounding = fixedDayShiftMedicalSet.roundingSet.rounding;
            _self.fixedNightShiftSettingRoundingTime = fixedNightShiftMedicalSet.roundingSet.roundingTime;
            _self.fixedNightShiftSettingRounding = fixedNightShiftMedicalSet.roundingSet.rounding;
            
            // Diff
            let diffDayShiftMedicalSet: WorkTimezoneMedicalSetModel = _.find(_self.model.diffWorkSetting.commonSet.medicalSet(), 
                    (o) => o.workSystemAtr() === WorkSystemAtr.DAY_SHIFT);
            if (nts.uk.util.isNullOrUndefined(diffDayShiftMedicalSet)) {
                diffDayShiftMedicalSet = new WorkTimezoneMedicalSetModel();
                diffDayShiftMedicalSet.workSystemAtr(WorkSystemAtr.DAY_SHIFT);
                _self.model.diffWorkSetting.commonSet.medicalSet.push(diffDayShiftMedicalSet);
            }
            let diffNightShiftMedicalSet: WorkTimezoneMedicalSetModel = _.find(_self.model.diffWorkSetting.commonSet.medicalSet(), 
                    (o) => o.workSystemAtr() === WorkSystemAtr.NIGHT_SHIFT);  
            if (nts.uk.util.isNullOrUndefined(diffNightShiftMedicalSet)) {
                diffNightShiftMedicalSet = new WorkTimezoneMedicalSetModel();
                diffNightShiftMedicalSet.workSystemAtr(WorkSystemAtr.NIGHT_SHIFT);
                _self.model.diffWorkSetting.commonSet.medicalSet.push(diffNightShiftMedicalSet);
            }           
            _self.diffDayShiftApplicationTime = diffDayShiftMedicalSet.applicationTime;
            _self.diffNightShiftApplicationTime = diffNightShiftMedicalSet.applicationTime;
            _self.diffDayShiftSettingRoundingTime = diffDayShiftMedicalSet.roundingSet.roundingTime;
            _self.diffDayShiftSettingRounding = diffDayShiftMedicalSet.roundingSet.rounding;
            _self.diffNightShiftSettingRoundingTime = diffNightShiftMedicalSet.roundingSet.roundingTime;
            _self.diffNightShiftSettingRounding = diffNightShiftMedicalSet.roundingSet.rounding;
            
            // Flow
            let flowDayShiftMedicalSet: WorkTimezoneMedicalSetModel = _.find(_self.model.flowWorkSetting.commonSetting.medicalSet(), 
                    (o) => o.workSystemAtr() === WorkSystemAtr.DAY_SHIFT);
            if (nts.uk.util.isNullOrUndefined(flowDayShiftMedicalSet)) {
                flowDayShiftMedicalSet = new WorkTimezoneMedicalSetModel();
                flowDayShiftMedicalSet.workSystemAtr(WorkSystemAtr.DAY_SHIFT);
                _self.model.flowWorkSetting.commonSetting.medicalSet.push(flowDayShiftMedicalSet);
            }
            let flowNightShiftMedicalSet: WorkTimezoneMedicalSetModel = _.find(_self.model.flowWorkSetting.commonSetting.medicalSet(), 
                    (o) => o.workSystemAtr() === WorkSystemAtr.NIGHT_SHIFT);  
            if (nts.uk.util.isNullOrUndefined(flowNightShiftMedicalSet)) {
                flowNightShiftMedicalSet = new WorkTimezoneMedicalSetModel();
                flowNightShiftMedicalSet.workSystemAtr(WorkSystemAtr.NIGHT_SHIFT);
                _self.model.flowWorkSetting.commonSetting.medicalSet.push(flowNightShiftMedicalSet);
            }           
            _self.flowDayShiftApplicationTime = flowDayShiftMedicalSet.applicationTime;
            _self.flowNightShiftApplicationTime = flowNightShiftMedicalSet.applicationTime;
            _self.flowDayShiftSettingRoundingTime = flowDayShiftMedicalSet.roundingSet.roundingTime;
            _self.flowDayShiftSettingRounding = flowDayShiftMedicalSet.roundingSet.rounding;
            _self.flowNightShiftSettingRoundingTime = flowNightShiftMedicalSet.roundingSet.roundingTime;
            _self.flowNightShiftSettingRounding = flowNightShiftMedicalSet.roundingSet.rounding;
            
            // Flex
            let flexDayShiftMedicalSet: WorkTimezoneMedicalSetModel = _.find(_self.model.flexWorkSetting.commonSetting.medicalSet(), 
                    (o) => o.workSystemAtr() === WorkSystemAtr.DAY_SHIFT);
            if (nts.uk.util.isNullOrUndefined(flexDayShiftMedicalSet)) {
                flexDayShiftMedicalSet = new WorkTimezoneMedicalSetModel();
                flexDayShiftMedicalSet.workSystemAtr(WorkSystemAtr.DAY_SHIFT);
                _self.model.flexWorkSetting.commonSetting.medicalSet.push(flexDayShiftMedicalSet);
            }
            let flexNightShiftMedicalSet: WorkTimezoneMedicalSetModel = _.find(_self.model.flexWorkSetting.commonSetting.medicalSet(), 
                    (o) => o.workSystemAtr() === WorkSystemAtr.NIGHT_SHIFT);  
            if (nts.uk.util.isNullOrUndefined(flexNightShiftMedicalSet)) {
                flexNightShiftMedicalSet = new WorkTimezoneMedicalSetModel();
                flexNightShiftMedicalSet.workSystemAtr(WorkSystemAtr.NIGHT_SHIFT);
                _self.model.flexWorkSetting.commonSetting.medicalSet.push(flexNightShiftMedicalSet);
            }           
            _self.flexDayShiftApplicationTime = flexDayShiftMedicalSet.applicationTime;
            _self.flexNightShiftApplicationTime = flexNightShiftMedicalSet.applicationTime;
            _self.flexDayShiftSettingRoundingTime = flexDayShiftMedicalSet.roundingSet.roundingTime;
            _self.flexDayShiftSettingRounding = flexDayShiftMedicalSet.roundingSet.rounding;
            _self.flexNightShiftSettingRoundingTime = flexNightShiftMedicalSet.roundingSet.roundingTime;
            _self.flexNightShiftSettingRounding = flexNightShiftMedicalSet.roundingSet.rounding;
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