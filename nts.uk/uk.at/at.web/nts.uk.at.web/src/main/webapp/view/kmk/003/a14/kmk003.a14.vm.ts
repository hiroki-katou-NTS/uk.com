module a14 {
    
    import WorkTimeDailyAtr = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeDailyAtr;
    import WorkTimeMethodSet = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeMethodSet
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    
    import WorkTimezoneShortTimeWorkSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneShortTimeWorkSetModel;
    
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    
     /**
     * Screen Model - Tab 14
     * 就業時間帯の共通設定 -> 短時間勤務設定
     * WorkTimeCommonSet -> ShortTimeWorkSetOfWorkTime
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
        childCareWorkUse: KnockoutObservable<boolean>;
        nursingTimeWorkUse: KnockoutObservable<boolean>;
        
        lstChildCareEnum: KnockoutObservableArray<any>;
        lstNursingTimeEnum: KnockoutObservableArray<any>;   
        
        // Simple mode - Data  
    
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
            _self.childCareWorkUse = ko.observable(true);
            _self.nursingTimeWorkUse = ko.observable(true);
            _self.lstChildCareEnum = ko.observableArray([
                {value: true, localizedName: nts.uk.resource.getText("KMK003_116")},
                {value: false, localizedName: nts.uk.resource.getText("KMK003_117")}
            ]);          
            _self.lstNursingTimeEnum = ko.observableArray([
                {value: true, localizedName: nts.uk.resource.getText("KMK003_196")},
                {value: false, localizedName: nts.uk.resource.getText("KMK003_197")}
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
                        if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.commonSetting.shortTimeWorkSet)) {
                            _self.model.fixedWorkSetting.commonSetting.shortTimeWorkSet = _self.createBinding();                           
                        } 
                        _self.changeBinding(_self.model.fixedWorkSetting.commonSetting.shortTimeWorkSet);                                    
                    } break;
                    case WorkTimeMethodSet.DIFFTIME_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.diffWorkSetting.commonSet.shortTimeWorkSet)) {
                            _self.model.diffWorkSetting.commonSet.shortTimeWorkSet = _self.createBinding();                           
                        }
                        _self.changeBinding(_self.model.diffWorkSetting.commonSet.shortTimeWorkSet);
                    } break;
                    case WorkTimeMethodSet.FLOW_WORK: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.flowWorkSetting.commonSetting.shortTimeWorkSet)) {
                            _self.model.flowWorkSetting.commonSetting.shortTimeWorkSet = _self.createBinding();                           
                        } 
                        _self.changeBinding(_self.model.flowWorkSetting.commonSetting.shortTimeWorkSet);
                    } break;               
                    default: {
                        if (nts.uk.util.isNullOrUndefined(_self.model.fixedWorkSetting.commonSetting.shortTimeWorkSet)) {
                            _self.model.fixedWorkSetting.commonSetting.shortTimeWorkSet = _self.createBinding();                           
                        } 
                        _self.changeBinding(_self.model.fixedWorkSetting.commonSetting.shortTimeWorkSet);
                    }
                } 
            } else {
                // Flex work
                if (nts.uk.util.isNullOrUndefined(_self.model.flexWorkSetting.commonSetting.shortTimeWorkSet)) {
                    _self.model.flexWorkSetting.commonSetting.shortTimeWorkSet = _self.createBinding();                           
                } 
                _self.changeBinding(_self.model.flexWorkSetting.commonSetting.shortTimeWorkSet); 
            }               
        }       
        
        /**
         * UI - All: create new Binding data
         */
        private createBinding(): WorkTimezoneShortTimeWorkSetModel {
            let _self = this;
            
            let result: WorkTimezoneShortTimeWorkSetModel = new WorkTimezoneShortTimeWorkSetModel();           
            return result;
        }
        
        /**
         * UI - All: change Binding mode
         */
        private changeBinding(shortTimeWorkSet: WorkTimezoneShortTimeWorkSetModel): void {
            let _self = this;
            if (_self.isDetailMode()) {
                _self.changeBindingDetail(shortTimeWorkSet); 
            } else {
                _self.changeBindingSimple(shortTimeWorkSet); 
            }  
        }
        
        /**
         * UI - Detail: change Binding Detail mode
         */
        private changeBindingDetail(shortTimeWorkSet: WorkTimezoneShortTimeWorkSetModel): void {
            let _self = this;           
            _self.childCareWorkUse = shortTimeWorkSet.childCareWorkUse;
            _self.nursingTimeWorkUse = shortTimeWorkSet.nursTimezoneWorkUse;
        }
        
        /**
         * UI - Simple: change Binding Simple mode 
         */
        private changeBindingSimple(shortTimeWorkSet: WorkTimezoneShortTimeWorkSetModel): void {
            let _self = this;
            _self.childCareWorkUse = shortTimeWorkSet.childCareWorkUse;
            _self.nursingTimeWorkUse = shortTimeWorkSet.nursTimezoneWorkUse;
        }
    }
    
    /**
     * KMK003A14BindingHandler
     */
    class KMK003A14BindingHandler implements KnockoutBindingHandler {
        
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
                .mergeRelativePath('/view/kmk/003/a14/index.xhtml').serialize();
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
    ko.bindingHandlers['ntsKMK003A14'] = new KMK003A14BindingHandler();
}
