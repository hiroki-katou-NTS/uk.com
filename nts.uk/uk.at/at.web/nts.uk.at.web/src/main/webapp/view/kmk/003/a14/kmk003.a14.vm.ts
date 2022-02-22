module a14 {
    
    import WorkTimeDailyAtr = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeDailyAtr;
    import WorkTimeMethodSet = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeMethodSet
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    
    import WorkTimezoneShortTimeWorkSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneShortTimeWorkSetModel;
    
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    import TabMode = nts.uk.at.view.kmk003.a.viewmodel.TabMode;
    
     /**
     * Screen Model - Tab 14
     * 就業時間帯の共通設定 -> 短時間勤務設定
     * WorkTimeCommonSet -> ShortTimeWorkSetOfWorkTime
     */
    class ScreenModel {
        // Detail mode - Data
        childCareWorkRoundingTime: KnockoutObservable<number>;
        childCareWorkRounding: KnockoutObservable<number>;
        // Screen mode
        isDetailMode: KnockoutObservable<boolean>;
        
        // Screen data model
        model: MainSettingModel;
        settingEnum: WorkTimeSettingEnumDto;
        
        // Detail mode - Data
        childCareWorkUse: KnockoutObservable<boolean>;
        nursingTimeWorkUse: KnockoutObservable<boolean>;
        
        lstChildCareEnum: KnockoutObservableArray<any>;
        lstNursingTimeEnum: KnockoutObservableArray<any>;
        listRoundingTimeValue: KnockoutObservableArray<EnumConstantDto>;
        listRoundingValue: KnockoutObservableArray<EnumConstantDto>;
        // Simple mode - Data
        isNewMode: KnockoutObservable<boolean>;
        /**
        * Constructor.
        */
        constructor(isNewMode: KnockoutObservable<boolean>,screenMode: any, model: MainSettingModel, settingEnum: WorkTimeSettingEnumDto) {
            let _self = this;
            _self.isNewMode = isNewMode;
            _self.isNewMode.subscribe((v) => {
                // Set default value for switch button
                if (v) {
                    if (!nts.uk.util.isNullOrUndefined(_self.childCareWorkRounding)) _self.childCareWorkRounding(0);
                }
            });
            // Check exist
            if (nts.uk.util.isNullOrUndefined(model) || nts.uk.util.isNullOrUndefined(settingEnum)) {
                // Stop rendering page
                return;    
            }
            
            // Binding data
            _self.model = model; 
            _self.settingEnum = settingEnum;
            _self.bindingData();
            
            // Init all data               
            _self.lstChildCareEnum = ko.observableArray([
                {value: true, localizedName: nts.uk.resource.getText("KMK003_116")},
                {value: false, localizedName: nts.uk.resource.getText("KMK003_117")}
            ]);          
            _self.lstNursingTimeEnum = ko.observableArray([
                {value: true, localizedName: nts.uk.resource.getText("KMK003_196")},
                {value: false, localizedName: nts.uk.resource.getText("KMK003_197")}
            ]);
            _self.listRoundingTimeValue = ko.observableArray(_self.settingEnum.roundingTime);
            _self.listRoundingValue = ko.observableArray(_self.settingEnum.roundingSimple);
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
            screenMode() == TabMode.DETAIL ? _self.isDetailMode(true) : _self.isDetailMode(false);
        }
        
        /**
         * Binding data
         */
        private bindingData() {
            let _self = this;            
            _self.childCareWorkUse = _self.model.commonSetting.shortTimeWorkSet.childCareWorkUse;
            _self.nursingTimeWorkUse = _self.model.commonSetting.shortTimeWorkSet.nursTimezoneWorkUse;
            _self.childCareWorkRoundingTime = _self.model.commonSetting.shortTimeWorkSet.roundingSet.roundingTime;
            _self.childCareWorkRounding = _self.model.commonSetting.shortTimeWorkSet.roundingSet.rounding;
        }

        public initDataModel(): void {
            var self = this;
            self.fixTableOptionFlow = {
                maxRow: 1,
                minRow: 1,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceFlow,
                isMultipleSelect: true,
                columns: self.columnSettingFlow(),
                tabindex: 90
            };
            self.fixTableOptionFlex = {
                maxRow: 1,
                minRow: 1,
                maxRowDisplay: 10,
                isShowButton: true,
                dataSource: self.dataSourceFlex,
                isMultipleSelect: true,
                columns: self.columnSettingOtherFlow(),
                tabindex: 89
            };
            self.fixTableOptionFixed = {
                maxRow: 1,
                minRow: 1,
                maxRowDisplay: 10,
                isShowButton: true,
                dataSource: self.dataSourceFixed,
                isMultipleSelect: true,
                columns: self.columnSettingOtherFlow(),
                tabindex: 89
            };
            self.fixTableOptionDiffTime = {
                maxRow: 1,
                minRow: 1,
                maxRowDisplay: 10,
                isShowButton: true,
                dataSource: self.dataSourceDiffTime,
                isMultipleSelect: true,
                columns: self.columnSettingOtherFlow(),
                tabindex: 89
            };
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

            let screenModel = new ScreenModel(input.isNewMode,screenMode, model, settingEnum);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                screenModel.startTab(screenMode);
            });
        }
    }
    ko.bindingHandlers['ntsKMK003A14'] = new KMK003A14BindingHandler();
}
