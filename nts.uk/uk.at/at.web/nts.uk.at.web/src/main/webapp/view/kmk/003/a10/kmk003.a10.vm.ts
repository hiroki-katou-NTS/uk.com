module a10 {
    
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    
    /**
     * Screen Model - Tab 10
     * 就業時間帯の共通設定 -> 加給設定
     * WorkTimeCommonSet -> BonusPaySettingCode
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
        bonusPaySettingCode: KnockoutObservable<string>;
        
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
            _self.bonusPaySettingCode = ko.observable('');                                 
            
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
                _self.workTimeDailyAtr(newValue);
                _self.changeWorkSettingModeDetail();
            });  
            _self.model.workTimeSetting.workTimeDivision.workTimeMethodSet.subscribe(newValue => {
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
                        _self.changeBindingDetail(_self.model.fixedWorkSetting.commonSetting.raisingSalarySet);                                    
                    } break;
                    case 1: {
                        //_self.changeBindingDetail(_self.model.diffWorkSetting.commonSetting.lateEarlySet.otherClassSets);
                    } break;
                    case 2: {
                        //_self.changeBindingDetail(_self.model.flowWorkSetting.commonSetting.lateEarlySet.otherClassSets);
                    } break;               
                    default: {
                        //_self.changeBindingDetail(_self.model.fixedWorkSetting.commonSetting.lateEarlySet.otherClassSets);
                    }
                } 
            } else {
                // Flex work
                //_self.changeBindingDetail(_self.model.flexWorkSetting.commonSetting.lateEarlySet.otherClassSets); 
            }               
        }       
        
        /**
         * UI - Detail: change Binding Detail mode
         */
        private changeBindingDetail(raisingSalarySet: KnockoutObservable<string>): void {
            let _self = this;
            _self.bonusPaySettingCode = raisingSalarySet;
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
     * Knockout Binding Handler - Tab 10
     */
    class KMK003A10BindingHandler implements KnockoutBindingHandler {
        
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
                .mergeRelativePath('/view/kmk/003/a10/index.xhtml').serialize();
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
    
    ko.bindingHandlers['ntsKMK003A10'] = new KMK003A10BindingHandler();
}