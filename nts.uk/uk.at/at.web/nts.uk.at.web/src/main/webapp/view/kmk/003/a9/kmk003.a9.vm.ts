module a9 {
    
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    
    import TimeRoundingSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRoundingSettingModel;
    import OtherEmTimezoneLateEarlySetModel = nts.uk.at.view.kmk003.a.viewmodel.common.OtherEmTimezoneLateEarlySetModel;
    
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    
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
        lateSetting: TimeRoundingSettingModel;
        leaveEarlySetting: TimeRoundingSettingModel;     
        
        listRoundingTimeValue: KnockoutObservableArray<EnumConstantDto>;
        listRoundingValue: KnockoutObservableArray<EnumConstantDto>;
        
        // Simple mode - Data        
        
        
        /**
         * Constructor
         */
        constructor(screenMode: any, model: MainSettingModel, settingEnum: WorkTimeSettingEnumDto) {
            let _self = this;
                    
            // Binding data
            _self.model = model; 
            _self.settingEnum = settingEnum;
            
            // Init all data                                            
            _self.lateSetting = new TimeRoundingSettingModel();
            _self.leaveEarlySetting = new TimeRoundingSettingModel();
            _self.listRoundingTimeValue = ko.observableArray([]);
            _self.listRoundingValue = ko.observableArray([]);
            
            _self.listRoundingTimeValue(_self.settingEnum.roundingTime);
            _self.listRoundingValue(_self.settingEnum.roundingSimple.reverse());
            
            // Detail mode and simple mode is same
            _self.isDetailMode = ko.observable(null);
            _self.isDetailMode.subscribe(newValue => {
                (newValue === true) ? _self.loadDetailMode() : _self.loadSimpleMode();
            });                                   
            _self.workTimeDailyAtr = ko.observable(0);
            _self.workTimeMethodSet = ko.observable(0);               
            
            // Subscribe Detail/Simple mode 
            screenMode.subscribe((value: any) => {
                value == "2" ? _self.isDetailMode(true) : _self.isDetailMode(false);
            });
            
            // Call first time
            screenMode == "2" ? _self.isDetailMode(true) : _self.isDetailMode(false);
        }            
        
        /**
         * UI - Detail: change to Detail mode
         */
        private loadDetailMode(): void {
            let _self = this;
            
            // Subscribe Work Setting mode
            _self.model.workTimeSetting.workTimeDivision.workTimeDailyAtr.subscribe(newValue => {
                console.log('change workTimeDailyAtr');
                _self.workTimeDailyAtr(newValue);
                _self.changeWorkSettingModeDetail();
            });  
            _self.model.workTimeSetting.workTimeDivision.workTimeMethodSet.subscribe(newValue => {
                console.log('change workTimeMethodSet');
                _self.workTimeMethodSet(newValue);
                _self.changeWorkSettingModeDetail();
            });  
            
            // Call first time
            _self.workTimeDailyAtr(_self.model.workTimeSetting.workTimeDivision.workTimeDailyAtr());
            _self.workTimeMethodSet(_self.model.workTimeSetting.workTimeDivision.workTimeMethodSet());
            _self.changeWorkSettingModeDetail();
        }
        
        /**
         * UI - Detail: change WorkSetting mode
         */
        private changeWorkSettingModeDetail(): void {
            let _self = this;        
                       
            //TODO
            if (_self.workTimeDailyAtr() === _self.settingEnum.workTimeDailyAtr[0].value) {
                // Regular work
                switch (_self.workTimeMethodSet()) {
                    case 0: {
                        console.log("fixed");
                        //_self.changeBindingDetail(_self.model.fixedWorkSetting.commonSetting.lateEarlySet.otherClassSets);                                    
                    } break;
                    case 1: {
                        console.log("diff");
                        //_self.changeBindingDetail(_self.model.diffWorkSetting.commonSetting.lateEarlySet.otherClassSets);
                    } break;
                    case 2: {
                        console.log("flow");
                        //_self.changeBindingDetail(_self.model.flowWorkSetting.commonSetting.lateEarlySet.otherClassSets);
                    } break;               
                    default: {
                        console.log("fixed default");
                        //_self.changeBindingDetail(_self.model.fixedWorkSetting.commonSetting.lateEarlySet.otherClassSets);
                    }
                } 
            } else {
                // Flex work
                console.log("flex");
                //_self.changeBindingDetail(_self.model.flexWorkSetting.commonSetting.lateEarlySet.otherClassSets); 
            }               
        }       
        
        /**
         * UI - Detail: change Binding Detail mode
         */
        private changeBindingDetail(otherClassSets: OtherEmTimezoneLateEarlySetModel[]): void {
            let _self = this;
            let otherClassSet1: OtherEmTimezoneLateEarlySetModel = otherClassSets[0];
            let otherClassSet2: OtherEmTimezoneLateEarlySetModel = otherClassSets[1];
            // Check late and early
            if (otherClassSet1.lateEarlyAtr() === _self.settingEnum.lstLateEarlyAtr[0].value) {
                _self.lateSetting = otherClassSet1.delTimeRoundingSet;
                _self.leaveEarlySetting = otherClassSet2.delTimeRoundingSet;
            } else {
                _self.lateSetting = otherClassSet2.delTimeRoundingSet;
                _self.leaveEarlySetting = otherClassSet1.delTimeRoundingSet;
            }  
        }
        
        /**
         * UI - Simple: change to Simple mode
         */
        private loadSimpleMode(): void {          
            let _self = this;
            
            // No simple mode
            _self.loadDetailMode();
        }
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
            });
        }

    }
    
    ko.bindingHandlers['ntsKMK003A9'] = new KMK003A9BindingHandler();
}