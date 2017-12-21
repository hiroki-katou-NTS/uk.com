module a8 {
    
    import WorkTimeDailyAtr = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeDailyAtr;
    import WorkTimeMethodSet = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeMethodSet
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    
    import WorkTimezoneGoOutSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneGoOutSetModel;
    import TotalRoundingSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.TotalRoundingSetModel;
    import GoOutTypeRoundingSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.GoOutTypeRoundingSetModel;
    import TimeRoundingSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRoundingSettingModel;
    
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    
    /**
     * Screen Model - Tab 8
     * 就業時間帯の共通設定 -> 外出設定
     * WorkTimeCommonSet -> WorkTimeGoOutSet
     */
    class ScreenModel {

        // Screen mode
        isDetailMode: KnockoutObservable<boolean>;
        workTimeDailyAtr: KnockoutObservable<number>;
        workTimeMethodSet: KnockoutObservable<number>;
        
        // Screen data model
        model: MainSettingModel;
        settingEnum: WorkTimeSettingEnumDto;
        
        // UI component
        workTimeTabs: KnockoutObservableArray<any>;
        workTimeSelectedTab: KnockoutObservable<string>;
        
        // Detail mode - Data
        totalRoundingModel: TotalRoundingSetModel;
        
        otTimezoneModel: GoOutTypeRoundingSetModel;     
        workTimezoneModel: GoOutTypeRoundingSetModel;
        pubHolWorkTimezoneModel: GoOutTypeRoundingSetModel;
        
        listRoundingBreakTimezone: KnockoutObservableArray<any>;
        listRoundingBreakTime: KnockoutObservableArray<any>;
        listRoundingTimeValue: KnockoutObservableArray<EnumConstantDto>;
        listRoundingValue: KnockoutObservableArray<EnumConstantDto>;
        
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
            
            // Init UI
            _self.workTimeTabs = ko.observableArray([
                { id: 'personal-setting-tab', title: nts.uk.resource.getText("KMK003_194"), content: '.personal-setting-tab-content', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'official-setting-tab', title: nts.uk.resource.getText("KMK003_195"), content: '.official-setting-tab-content', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            _self.workTimeSelectedTab = ko.observable('personal-setting-tab');
            
            // Init all data                        
            _self.totalRoundingModel = new TotalRoundingSetModel();    
            _self.pubHolWorkTimezoneModel = new GoOutTypeRoundingSetModel();
            _self.workTimezoneModel = new GoOutTypeRoundingSetModel();
            _self.otTimezoneModel = new GoOutTypeRoundingSetModel();                
            
            //_self.listRoundingBreakTimezone = ko.observableArray([]);
            //_self.listRoundingBreakTime = ko.observableArray([]);
            _self.listRoundingBreakTimezone = ko.observableArray([
                { value: 0, localizedName: nts.uk.resource.getText("KMK003_198") },
                { value: 1, localizedName: nts.uk.resource.getText("KMK003_199") }
            ]);
            _self.listRoundingBreakTime = ko.observableArray([
                { value: 0, localizedName: nts.uk.resource.getText("KMK003_87") },
                { value: 1, localizedName: nts.uk.resource.getText("KMK003_88") }
            ]);
            _self.listRoundingTimeValue = ko.observableArray([]);
            _self.listRoundingValue = ko.observableArray([]);
            
            //_self.listRoundingBreakTimezone(_self.settingEnum.roundingBreakTimezone);
            //_self.listRoundingBreakTime(_self.settingEnum.roundingBreakTime);            
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
                        if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.commonSetting.goOutSet)) {
                            _self.model.fixedWorkSetting.commonSetting.goOutSet = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.fixedWorkSetting.commonSetting.goOutSet);                                
                    } break;
                    case WorkTimeMethodSet.DIFFTIME_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.diffWorkSetting.commonSet.goOutSet)) {
                            _self.model.diffWorkSetting.commonSet.goOutSet = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.diffWorkSetting.commonSet.goOutSet);                   
                    } break;
                    case WorkTimeMethodSet.FLOW_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.flowWorkSetting.commonSetting.goOutSet)) {
                            _self.model.flowWorkSetting.commonSetting.goOutSet = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.flowWorkSetting.commonSetting.goOutSet);                                  
                    } break;               
                    default: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.commonSetting.goOutSet)) {
                            _self.model.fixedWorkSetting.commonSetting.goOutSet = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.fixedWorkSetting.commonSetting.goOutSet);    
                    }
                } 
            } else {
                // Flex work
                if (nts.uk.util.isNullOrUndefined(_self.model.flexWorkSetting.commonSetting.goOutSet)) {
                    _self.model.flexWorkSetting.commonSetting.goOutSet = _self.createBinding();                           
                }
                _self.changeBinding(_self.model.flexWorkSetting.commonSetting.goOutSet);           
            }               
        }       
        
        /**
         * UI - All: create new Binding data
         */
        private createBinding(): WorkTimezoneGoOutSetModel {
            let _self = this;      
                 
            let result: WorkTimezoneGoOutSetModel = new WorkTimezoneGoOutSetModel();           
            return result;
        }
        
        /**
         * UI - All: change Binding mode
         */
        private changeBinding(goOutSet: WorkTimezoneGoOutSetModel): void {
            let _self = this;
            if (_self.isDetailMode()) {
                _self.changeBindingDetail(goOutSet); 
            } else {
                _self.changeBindingSimple(goOutSet); 
            }  
        }
        
        /**
         * UI - Detail: change Binding Detail mode
         */
        private changeBindingDetail(goOutSet: WorkTimezoneGoOutSetModel): void {
            let _self = this;
            _self.totalRoundingModel = goOutSet.totalRoundingSet;  
            _self.pubHolWorkTimezoneModel = goOutSet.diffTimezoneSetting.pubHolWorkTimezone;
            _self.workTimezoneModel = goOutSet.diffTimezoneSetting.workTimezone;
            _self.otTimezoneModel = goOutSet.diffTimezoneSetting.ottimezone;      
        }
        
        /**
         * UI - Simple: change Binding Simple mode 
         */
        private changeBindingSimple(goOutSet: WorkTimezoneGoOutSetModel): void {
            let _self = this;
            _self.changeBindingDetail(goOutSet);
        } 
    }
    
    /**
     * Knockout Binding Handler - Tab 8
     */
    class KMK003A8BindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {}

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a8/index.xhtml').serialize();
            //get data
            let input = valueAccessor();
            let screenMode = input.screenMode;
            let model = input.model;
            let settingEnum = input.enum;

            let screenModel = new ScreenModel(screenMode, model, settingEnum);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A8'] = new KMK003A8BindingHandler();
}
