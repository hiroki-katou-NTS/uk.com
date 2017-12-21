module a16 {
    
    import WorkTimeDailyAtr = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeDailyAtr;
    import WorkTimeMethodSet = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeMethodSet
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    
    /**
     * Screen Model - Tab 16
     * 就業時間帯の共通設定 -> 0時跨ぎ計算
     * WorkTimeCommonSet -> overDayCalcSetting
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
        zeroHStraddCalculateSetting: KnockoutObservable<boolean>;
        
        listZeroHStraddCalculateSetting: KnockoutObservableArray<any>;
        
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
            _self.zeroHStraddCalculateSetting = ko.observable(true);
            _self.listZeroHStraddCalculateSetting = ko.observableArray([
                { value: true, localizedName: nts.uk.resource.getText("KMK003_142") },
                { value: false, localizedName: nts.uk.resource.getText("KMK003_143") }
            ]);
            
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
                        if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.commonSetting.zeroHStraddCalculateSet)) {
                            _self.model.fixedWorkSetting.commonSetting.zeroHStraddCalculateSet = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.fixedWorkSetting.commonSetting.zeroHStraddCalculateSet);                                    
                    } break;
                    case WorkTimeMethodSet.DIFFTIME_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.diffWorkSetting.commonSet.zeroHStraddCalculateSet)) {
                            _self.model.diffWorkSetting.commonSet.zeroHStraddCalculateSet = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.diffWorkSetting.commonSet.zeroHStraddCalculateSet);
                    } break;
                    case WorkTimeMethodSet.FLOW_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.flowWorkSetting.commonSetting.zeroHStraddCalculateSet)) {
                            _self.model.flowWorkSetting.commonSetting.zeroHStraddCalculateSet = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.flowWorkSetting.commonSetting.zeroHStraddCalculateSet);
                    } break;               
                    default: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.commonSetting.zeroHStraddCalculateSet)) {
                            _self.model.fixedWorkSetting.commonSetting.zeroHStraddCalculateSet = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.fixedWorkSetting.commonSetting.zeroHStraddCalculateSet);
                    }
                } 
            } else {
                // Flex work
                if (nts.uk.util.isNullOrUndefined(_self.model.flexWorkSetting.commonSetting.zeroHStraddCalculateSet)) {
                    _self.model.flexWorkSetting.commonSetting.zeroHStraddCalculateSet = _self.createBinding();                           
                }
                _self.changeBinding(_self.model.flexWorkSetting.commonSetting.zeroHStraddCalculateSet); 
            }               
        }       
        
        /**
         * UI - All: create new Binding data
         */
        private createBinding(): KnockoutObservable<boolean> {
            let _self = this;
            
            let result: KnockoutObservable<boolean> = ko.observable(true);           
            return result;
        }
        
        /**
         * UI - All: change Binding mode
         */
        private changeBinding(zeroHStraddCalculateSet: KnockoutObservable<boolean>): void {
            let _self = this;
            if (_self.isDetailMode()) {
                _self.changeBindingDetail(zeroHStraddCalculateSet); 
            } else {
                _self.changeBindingSimple(zeroHStraddCalculateSet); 
            }  
        }
        
        /**
         * UI - Detail: change Binding Detail mode
         */
        private changeBindingDetail(zeroHStraddCalculateSet: KnockoutObservable<boolean>): void {
            let _self = this;        
            _self.zeroHStraddCalculateSetting = zeroHStraddCalculateSet;
        }
        
        /**
         * UI - Simple: change Binding Simple mode 
         */
        private changeBindingSimple(zeroHStraddCalculateSet: KnockoutObservable<boolean>): void {
            let _self = this;
            _self.changeBindingDetail(zeroHStraddCalculateSet);  
        }
    }
    
    /**
     * Knockout Binding Handler - Tab 16
     */
    class KMK003A16BindingHandler implements KnockoutBindingHandler {
        
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
                .mergeRelativePath('/view/kmk/003/a16/index.xhtml').serialize();
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
    
    ko.bindingHandlers['ntsKMK003A16'] = new KMK003A16BindingHandler();
}