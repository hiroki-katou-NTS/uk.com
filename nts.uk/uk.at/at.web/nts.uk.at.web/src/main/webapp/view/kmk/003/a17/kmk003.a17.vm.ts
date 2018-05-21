module a17 {
    
    import WorkTimeDailyAtr = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeDailyAtr;
    import WorkTimeMethodSet = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeMethodSet
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    import TabMode = nts.uk.at.view.kmk003.a.viewmodel.TabMode;
    import OvertimeWorkFrameFindDto = nts.uk.at.view.kmk003.a.service.model.OvertimeWorkFrameFindDto;
    import SettingMethod = nts.uk.at.view.kmk003.a.viewmodel.SettingMethod;
    
    /**
     * Screen Model - Tab 17
     * 
     */
    class ScreenModel {
        
        isNewMode: KnockoutObservable<boolean>;
        
        // Screen mode
        selectedCode: KnockoutObservable<any>;
        isDetailMode: KnockoutObservable<boolean>;
        isFixed: KnockoutObservable<boolean>;
        isDiff: KnockoutObservable<boolean>;
        isFlow: KnockoutObservable<boolean>;
        isFlex: KnockoutObservable<boolean>;
        
        // Screen data model
        model: MainSettingModel;
        settingEnum: WorkTimeSettingEnumDto;
        
        // Data - Common
        listNotUseAtr: KnockoutObservableArray<any>;
        listCalcMethodNoBreak: KnockoutObservableArray<any>;
        listCalcMethodExceededPredAddVacation: KnockoutObservableArray<any>;
        listOtFrameNo: KnockoutObservableArray<any>;
        notUseAtr: KnockoutObservable<number>;
        // Data - Fixed 
        fixedCalcMethodNoBreak: KnockoutObservable<number>;
        fixedCalcMethodExceededPredAddVacation: KnockoutObservable<number>;      
        fixedOtFrameNo: KnockoutObservable<number>;
        fixedInLawOT: KnockoutObservable<number>;
        fixedNotInLawOT: KnockoutObservable<number>;
        
        fixedIsCalcExceededPredAddVacation: KnockoutObservable<boolean>;
        fixedIsCalcNoBreak: KnockoutObservable<boolean>;
        // Data - Diff      
        diffCalcMethodNoBreak: KnockoutObservable<number>;
        diffCalcMethodExceededPredAddVacation: KnockoutObservable<number>;      
        diffOtFrameNo: KnockoutObservable<number>;
        diffInLawOT: KnockoutObservable<number>;
        diffNotInLawOT: KnockoutObservable<number>;
        
        diffIsCalcExceededPredAddVacation: KnockoutObservable<boolean>;
        diffIsCalcNoBreak: KnockoutObservable<boolean>;
        lstOvertimeWorkFrame: OvertimeWorkFrameFindDto[];
        
        // Temp data variable (using in create mode)
        tempCalcMethodNoBreak: KnockoutObservable<number>;
        tempCalcMethodExceededPredAddVacation: KnockoutObservable<number>;      
        tempOtFrameNo: KnockoutObservable<number>;
        tempInLawOT: KnockoutObservable<number>;
        tempNotInLawOT: KnockoutObservable<number>;
        
        /**
         * Constructor
         */
        constructor(isNewMode: KnockoutObservable<boolean>, screenMode: any, model: MainSettingModel, settingEnum: WorkTimeSettingEnumDto,lstOvertimeWorkFrame:any) {
            let _self = this;
            
            // Check exist
            if (nts.uk.util.isNullOrUndefined(model) || nts.uk.util.isNullOrUndefined(settingEnum)) {
                // Stop rendering page
                return;    
            }
            
            // Save value when switch between mode
            _self.isNewMode = isNewMode;
            
            // Binding data
            _self.model = model;     
            _self.settingEnum = settingEnum;
            _self.isFixed = _self.model.workTimeSetting.isFixed; 
            _self.isDiff = _self.model.workTimeSetting.isDiffTime; 
            _self.isFlow = _self.model.workTimeSetting.isFlow;  
            _self.isFlex = _self.model.workTimeSetting.isFlex; 
            _self.bindingData();
            
            // Init all data                 
            _self.listNotUseAtr = ko.observableArray([
                { value: 1, localizedName: nts.uk.resource.getText("KMK003_142") },
                { value: 0, localizedName: nts.uk.resource.getText("KMK003_143") }
            ]);                           
            _self.listCalcMethodNoBreak = ko.observableArray([
                { value: 1, localizedName: nts.uk.resource.getText("KMK003_142") },
                { value: 0, localizedName: nts.uk.resource.getText("KMK003_143") }
            ]);                           
            _self.listCalcMethodExceededPredAddVacation = ko.observableArray([
                { value: 1, localizedName: nts.uk.resource.getText("KMK003_142") },
                { value: 0, localizedName: nts.uk.resource.getText("KMK003_143") }
            ]);            
            _self.listOtFrameNo = ko.observableArray(lstOvertimeWorkFrame);             

            _self.tempCalcMethodNoBreak = ko.observable(0); 
            _self.tempCalcMethodExceededPredAddVacation = ko.observable(0);      
            _self.tempOtFrameNo = ko.observable(0);
            _self.tempInLawOT = ko.observable(0);
            _self.tempNotInLawOT = ko.observable(0);
            
            // Detail mode and simple mode is same     
            _self.isDetailMode = ko.observable(null);                         
            _self.fixedIsCalcExceededPredAddVacation = ko.computed(() => {
                return (_self.model.workTimeSetting.isFixed() || _self.model.workTimeSetting.isDiffTime()) && _self.fixedCalcMethodExceededPredAddVacation() === 0;
            });            
            _self.fixedIsCalcNoBreak = ko.computed(() => {
                return (_self.model.workTimeSetting.isFixed() || _self.model.workTimeSetting.isDiffTime()) && _self.fixedCalcMethodNoBreak() === 0;
            });    
            _self.diffIsCalcExceededPredAddVacation = ko.computed(() => {
                return (_self.model.workTimeSetting.isFixed() || _self.model.workTimeSetting.isDiffTime()) && _self.diffCalcMethodExceededPredAddVacation() === 0;
            });            
            _self.diffIsCalcNoBreak = ko.computed(() => {
                return (_self.model.workTimeSetting.isFixed() || _self.model.workTimeSetting.isDiffTime()) && _self.diffCalcMethodNoBreak() === 0;
            });          
            // Subscribe Detail/Simple mode 
            screenMode.subscribe((value: any) => {
                value == TabMode.DETAIL ? _self.isDetailMode(true) : _self.isDetailMode(false);
            });
            _self.model.workTimeSetting.workTimeDivision.workTimeMethodSet.subscribe((v)=>{
                if (v == SettingMethod.FIXED) {
                    _self.fixedCalcMethodNoBreak(_self.diffCalcMethodNoBreak());
                    _self.fixedCalcMethodExceededPredAddVacation(_self.diffCalcMethodExceededPredAddVacation());
                    _self.fixedOtFrameNo(_self.diffOtFrameNo());
                    _self.fixedInLawOT(_self.diffInLawOT());
                    _self.fixedNotInLawOT(_self.diffNotInLawOT());  
                }
                if (v == SettingMethod.DIFFTIME) {
                    _self.diffCalcMethodNoBreak(_self.fixedCalcMethodNoBreak());
                    _self.diffCalcMethodExceededPredAddVacation(_self.fixedCalcMethodExceededPredAddVacation());
                    _self.diffOtFrameNo(_self.fixedOtFrameNo());
                    _self.diffInLawOT(_self.fixedInLawOT());
                    _self.diffNotInLawOT(_self.fixedNotInLawOT());
                }
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
            
            // Common 
            if (nts.uk.util.isNullOrUndefined(_self.model.commonSetting.holidayCalculation.isCalculate())) {     
                _self.model.commonSetting.holidayCalculation.isCalculate(0)                              
            } 
            _self.notUseAtr = _self.model.commonSetting.holidayCalculation.isCalculate;
            
            // Fixed
            if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.calculationSetting.overTimeCalcNoBreak.calcMethod())) {     
                _self.model.fixedWorkSetting.calculationSetting.overTimeCalcNoBreak.calcMethod(0);                              
            } 
            _self.fixedCalcMethodNoBreak = _self.model.fixedWorkSetting.calculationSetting.overTimeCalcNoBreak.calcMethod;
            if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.calculationSetting.overTimeCalcNoBreak.inLawOT())) {     
                _self.model.fixedWorkSetting.calculationSetting.overTimeCalcNoBreak.inLawOT(1);                              
            } 
            _self.fixedInLawOT = _self.model.fixedWorkSetting.calculationSetting.overTimeCalcNoBreak.inLawOT;           
            if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.calculationSetting.overTimeCalcNoBreak.notInLawOT())) {     
                _self.model.fixedWorkSetting.calculationSetting.overTimeCalcNoBreak.notInLawOT(1);                                 
            } 
            _self.fixedNotInLawOT = _self.model.fixedWorkSetting.calculationSetting.overTimeCalcNoBreak.notInLawOT;           
            if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.calculationSetting.exceededPredAddVacationCalc.calcMethod())) {     
                _self.model.fixedWorkSetting.calculationSetting.exceededPredAddVacationCalc.calcMethod(0);                                
            } 
            _self.fixedCalcMethodExceededPredAddVacation = _self.model.fixedWorkSetting.calculationSetting.exceededPredAddVacationCalc.calcMethod;          
            if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.calculationSetting.exceededPredAddVacationCalc.otFrameNo())) {     
                _self.model.fixedWorkSetting.calculationSetting.exceededPredAddVacationCalc.otFrameNo(1);                                 
            } 
            _self.fixedOtFrameNo = _self.model.fixedWorkSetting.calculationSetting.exceededPredAddVacationCalc.otFrameNo;
            
            // Diff
            if (nts.uk.util.isNullOrUndefined(_self.model.diffWorkSetting.calculationSetting.overTimeCalcNoBreak.calcMethod())) {     
                _self.model.diffWorkSetting.calculationSetting.overTimeCalcNoBreak.calcMethod(0);                              
            } 
            _self.diffCalcMethodNoBreak = _self.model.diffWorkSetting.calculationSetting.overTimeCalcNoBreak.calcMethod;
            if (nts.uk.util.isNullOrUndefined(_self.model.diffWorkSetting.calculationSetting.overTimeCalcNoBreak.inLawOT())) {     
                _self.model.diffWorkSetting.calculationSetting.overTimeCalcNoBreak.inLawOT(1);                              
            } 
            _self.diffInLawOT = _self.model.diffWorkSetting.calculationSetting.overTimeCalcNoBreak.inLawOT;           
            if (nts.uk.util.isNullOrUndefined(_self.model.diffWorkSetting.calculationSetting.overTimeCalcNoBreak.notInLawOT())) {     
                _self.model.diffWorkSetting.calculationSetting.overTimeCalcNoBreak.notInLawOT(1);                               
            } 
            _self.diffNotInLawOT = _self.model.diffWorkSetting.calculationSetting.overTimeCalcNoBreak.notInLawOT;           
            if (nts.uk.util.isNullOrUndefined(_self.model.diffWorkSetting.calculationSetting.exceededPredAddVacationCalc.calcMethod())) {     
                _self.model.diffWorkSetting.calculationSetting.exceededPredAddVacationCalc.calcMethod(0);                                
            } 
            _self.diffCalcMethodExceededPredAddVacation = _self.model.diffWorkSetting.calculationSetting.exceededPredAddVacationCalc.calcMethod;          
            if (nts.uk.util.isNullOrUndefined(_self.model.diffWorkSetting.calculationSetting.exceededPredAddVacationCalc.otFrameNo())) {     
                _self.model.diffWorkSetting.calculationSetting.exceededPredAddVacationCalc.otFrameNo(1);         
            } 
            _self.diffOtFrameNo = _self.model.diffWorkSetting.calculationSetting.exceededPredAddVacationCalc.otFrameNo;
            
            // Subscribe
            _self.notUseAtr.subscribe(v => {
                if (nts.uk.util.isNullOrUndefined(v)) { _self.notUseAtr(0); }
            });
        }
        
    }
    
    /**
     * Knockout Binding Handler - Tab 17
     */
    class KMK003A17BindingHandler implements KnockoutBindingHandler {
        
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
                .mergeRelativePath('/view/kmk/003/a17/index.xhtml').serialize();
            // Get data
            let input = valueAccessor();
            let screenMode = input.screenMode;
            let model = input.model;
            let settingEnum = input.enum;

            let screenModel = new ScreenModel(input.isNewMode, screenMode, model, settingEnum,input.overTimeWorkFrameOptions());
            $(element).load(webserviceLocator, () => {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                screenModel.startTab(screenMode);
            });
        }
    }   
    ko.bindingHandlers['ntsKMK003A17'] = new KMK003A17BindingHandler();
}