module a11 {
    
    import WorkTimeDailyAtr = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeDailyAtr;
    import WorkTimeMethodSet = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeMethodSet
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    import SubHolTransferSetAtr = nts.uk.at.view.kmk003.a.service.model.common.SubHolTransferSetAtr;
    
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
        
        isNewMode: KnockoutObservable<boolean>;
        
        // Screen mode
        isDetailMode: KnockoutObservable<boolean>;
        isSimpleMode: KnockoutObservable<boolean>;
        
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
        
        // Old data (using for reset value)
        oldWorkdayOffTimeOneDayTime: KnockoutObservable<number>;
        oldWorkdayOffTimeHalfDayTime: KnockoutObservable<number>;
        oldWorkdayOffTimeCertainTime: KnockoutObservable<number>;        
        oldFromOverTimeOneDayTime: KnockoutObservable<number>;
        oldFromOverTimeHalfDayTime: KnockoutObservable<number>;
        oldFromOverTimeCertainTime: KnockoutObservable<number>;
    
        // Simple mode - Data (nothing)
        
        /**
        * Constructor.
        */
        constructor(isNewMode: KnockoutObservable<boolean>, screenMode: any, model: MainSettingModel, settingEnum: WorkTimeSettingEnumDto) {
            let _self = this;           
            _self.isNewMode = isNewMode;
            _self.isNewMode.subscribe((v) => {
                // Set default value for switch button
                if (v) {
                    if (!nts.uk.util.isNullOrUndefined(_self.workdayOffTimeUseDivision)) _self.workdayOffTimeUseDivision(true);
                }    
            });
            
            // Check exist
            if (nts.uk.util.isNullOrUndefined(model) || nts.uk.util.isNullOrUndefined(settingEnum)) {
                // Stop rendering page
                return;    
            }
            
            // Init all data          
            _self.oldWorkdayOffTimeOneDayTime = ko.observable(0);
            _self.oldWorkdayOffTimeHalfDayTime = ko.observable(0);
            _self.oldWorkdayOffTimeCertainTime = ko.observable(0);       
            _self.oldFromOverTimeOneDayTime = ko.observable(0);
            _self.oldFromOverTimeHalfDayTime = ko.observable(0);
            _self.oldFromOverTimeCertainTime = ko.observable(0);
            
            // Binding data
            _self.model = model; 
            _self.settingEnum = settingEnum;
            _self.bindingData();   
    
            _self.isDetailMode = ko.observable(null);
            _self.isDetailMode.subscribe(newValue => {
                _self.clearError();
            });                                  
            _self.isSimpleMode = ko.observable(null);
            _self.isSimpleMode.subscribe(newValue => {
                _self.clearError();
            });                                                        
            // Subscribe Detail/Simple mode 
            screenMode.subscribe((value: any) => {
                value == TabMode.DETAIL ? _self.isDetailMode(true) : _self.isDetailMode(false);
                value == TabMode.SIMPLE ? _self.isSimpleMode(true) : _self.isSimpleMode(false); 
            });                  
            
            _self.model.isChangeItemTable.subscribe(newValue => {
                _self.bindingData();
            });
            _self.workdayOffTimeSubHolTransferSetAtr.subscribe((v) => {
                if (v == 0) {//一定時間
                    $('#certainDayTimeHol').ntsError('clear');
                }
                else {//指定時間
                    $('#oneDayTimeHol').ntsError('clear');
                    $('#haflDayTimeHol').ntsError('clear');
                }
            });
            _self.fromOverTimeSubHolTransferSetAtr.subscribe((v) => {
                if (v == 0) {//一定時間
                    $('#certainDayTimeOT').ntsError('clear');
                }
                else {//指定時間
                    $('#haflDayTimeOT').ntsError('clear');
                    $('#oneDayTimeOT').ntsError('clear');
                }
            });
            _self.workdayOffTimeUseDivision.subscribe((v) => {
                if (!v) {
                    $('#certainDayTimeHol').ntsError('clear');
                    $('#oneDayTimeHol').ntsError('clear');
                    $('#haflDayTimeHol').ntsError('clear');
                }
            });
            _self.fromOverTimeUseDivision.subscribe((v) => {
                if (!v) {
                    $('#certainDayTimeOT').ntsError('clear');
                    $('#haflDayTimeOT').ntsError('clear');
                    $('#oneDayTimeOT').ntsError('clear');
                }
            })
        }

        /**
         * Start tab
         */
        public startTab(screenMode: any): void {
            let _self = this;
            screenMode() == TabMode.DETAIL ? _self.isDetailMode(true) : _self.isDetailMode(false); 
            screenMode() == TabMode.SIMPLE ? _self.isSimpleMode(true) : _self.isSimpleMode(false);         
        }
        
        /**
         * Binding data
         */
        private bindingData() {
            let _self = this;
        
            _self.workdayOffTimeUseDivision = _self.model.commonSetting.getWorkDayOffTimeSet().subHolTimeSet.useDivision;
            _self.workdayOffTimeSubHolTransferSetAtr = _self.model.commonSetting.getWorkDayOffTimeSet().subHolTimeSet.subHolTransferSetAtr;
            _self.workdayOffTimeOneDayTime = _self.model.commonSetting.getWorkDayOffTimeSet().subHolTimeSet.designatedTime.oneDayTime;
            _self.workdayOffTimeHalfDayTime = _self.model.commonSetting.getWorkDayOffTimeSet().subHolTimeSet.designatedTime.halfDayTime;
            _self.workdayOffTimeCertainTime = _self.model.commonSetting.getWorkDayOffTimeSet().subHolTimeSet.certainTime;
            
            _self.fromOverTimeUseDivision = _self.model.commonSetting.getOverTimeSet().subHolTimeSet.useDivision;
            _self.fromOverTimeSubHolTransferSetAtr = _self.model.commonSetting.getOverTimeSet().subHolTimeSet.subHolTransferSetAtr;
            _self.fromOverTimeOneDayTime = _self.model.commonSetting.getOverTimeSet().subHolTimeSet.designatedTime.oneDayTime;
            _self.fromOverTimeHalfDayTime = _self.model.commonSetting.getOverTimeSet().subHolTimeSet.designatedTime.halfDayTime;
            _self.fromOverTimeCertainTime = _self.model.commonSetting.getOverTimeSet().subHolTimeSet.certainTime;
            
            // Set old data 
            _self.oldWorkdayOffTimeOneDayTime(_self.model.commonSetting.getWorkDayOffTimeSet().subHolTimeSet.designatedTime.oneDayTime());
            _self.oldWorkdayOffTimeHalfDayTime(_self.model.commonSetting.getWorkDayOffTimeSet().subHolTimeSet.designatedTime.halfDayTime());
            _self.oldWorkdayOffTimeCertainTime(_self.model.commonSetting.getWorkDayOffTimeSet().subHolTimeSet.certainTime());        
            _self.oldFromOverTimeOneDayTime(_self.model.commonSetting.getOverTimeSet().subHolTimeSet.designatedTime.oneDayTime());
            _self.oldFromOverTimeHalfDayTime(_self.model.commonSetting.getOverTimeSet().subHolTimeSet.designatedTime.halfDayTime());
            _self.oldFromOverTimeCertainTime(_self.model.commonSetting.getOverTimeSet().subHolTimeSet.certainTime());
            
            // Disable
//            _self.workdayOffTimeUseDivision.subscribe(newValue => {
//                if (typeof _self.workdayOffTimeCertainTime() !== 'number') {                        
//                    _self.workdayOffTimeCertainTime(_self.oldWorkdayOffTimeCertainTime()); 
//                }
//                if (typeof _self.workdayOffTimeOneDayTime() !== 'number') {
//                    _self.workdayOffTimeOneDayTime(_self.oldWorkdayOffTimeOneDayTime()); 
//                }                
//                if (typeof _self.workdayOffTimeHalfDayTime() !== 'number') {
//                    _self.workdayOffTimeHalfDayTime(_self.oldWorkdayOffTimeHalfDayTime()); 
//                }
//                _self.clearError();
//            });
//            
//            _self.workdayOffTimeSubHolTransferSetAtr.subscribe(newValue => {
//                if (newValue === SubHolTransferSetAtr.SPECIFIED_TIME_SUB_HOL) {
//                    if (typeof _self.workdayOffTimeCertainTime() !== 'number') {                        
//                        _self.workdayOffTimeCertainTime(_self.oldWorkdayOffTimeCertainTime()); 
//                    }
//                } else {
//                    if (typeof _self.workdayOffTimeOneDayTime() !== 'number') {
//                        _self.workdayOffTimeOneDayTime(_self.oldWorkdayOffTimeOneDayTime()); 
//                    }                
//                    if (typeof _self.workdayOffTimeHalfDayTime() !== 'number') {
//                        _self.workdayOffTimeHalfDayTime(_self.oldWorkdayOffTimeHalfDayTime()); 
//                    }
//                }
//                _self.clearError();
//            });
//            
//            _self.fromOverTimeUseDivision.subscribe(newValue => {
//                if (typeof _self.fromOverTimeCertainTime() !== 'number') {
//                    _self.fromOverTimeCertainTime(_self.oldFromOverTimeCertainTime()); 
//                }
//                if (typeof _self.fromOverTimeOneDayTime() !== 'number') {
//                    _self.fromOverTimeOneDayTime(_self.oldFromOverTimeOneDayTime()); 
//                }                
//                if (typeof _self.fromOverTimeHalfDayTime() !== 'number') {
//                    _self.fromOverTimeHalfDayTime(_self.oldFromOverTimeHalfDayTime()); 
//                }
//                _self.clearError();
//            });
//            
//            _self.fromOverTimeSubHolTransferSetAtr.subscribe(newValue => {
//                if (newValue === SubHolTransferSetAtr.SPECIFIED_TIME_SUB_HOL) {
//                    if (typeof _self.fromOverTimeCertainTime() !== 'number') {
//                        _self.fromOverTimeCertainTime(_self.oldFromOverTimeCertainTime()); 
//                    }
//                } else {
//                    if (typeof _self.fromOverTimeOneDayTime() !== 'number') {
//                        _self.fromOverTimeOneDayTime(_self.oldFromOverTimeOneDayTime()); 
//                    }                
//                    if (typeof _self.fromOverTimeHalfDayTime() !== 'number') {
//                        _self.fromOverTimeHalfDayTime(_self.oldFromOverTimeHalfDayTime()); 
//                    }
//                }
//                _self.clearError();
//            });
        }
        
        private clearError() {
            if ($('.nts-editor').ntsError("hasError") == true) {
                $('.nts-input').ntsError('clear');
            }    
        }
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
    
            let screenModel = new ScreenModel(input.isNewMode, screenMode, model, settingEnum);
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
