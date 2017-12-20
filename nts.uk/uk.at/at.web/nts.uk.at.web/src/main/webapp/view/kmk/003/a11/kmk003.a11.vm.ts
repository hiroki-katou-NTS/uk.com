module a11 {
    
    import WorkTimeDailyAtr = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeDailyAtr;
    import WorkTimeMethodSet = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeMethodSet
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    
    import WorkTimezoneOtherSubHolTimeSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneOtherSubHolTimeSetModel;
    
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    
    /**
     * Screen Model - Tab 11
     * 就業時間帯の共通設定 -> 代休時間設定
     * WorkTimeCommonSet -> subHolTimeSet (SubstitutionWorkTimeSetting)
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
        
        // Simple mode - Data  
    
        // 休日出勤
        timeSetHol: KnockoutObservable<TimeSetModel>;
        // 残業
        timeSetOT: KnockoutObservable<TimeSetModel>;
        
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
    
            // initial data
            _self.timeSetHol = ko.observable(new TimeSetModel(_self));
            _self.timeSetOT = ko.observable(new TimeSetModel(_self));
        }

        /**
         * bindDataToScreen
         */
    }
    
    /**
     * TimeSetModel
     */
    class TimeSetModel {
        
        timeOption: KnockoutObservable<any>;
        isCertainDaySet: KnockoutObservable<boolean>;
        
        checked: KnockoutObservable<boolean>;
        subTransferSelected: KnockoutObservable<number>;
        oneDayTime: KnockoutObservable<string>;
        halfDayTime: KnockoutObservable<string>;
        certainDayTime: KnockoutObservable<string>;
        nameIdRadioGroup: string;
        
        /**
         * Constructor
         */
        constructor(parentModel: ScreenModel) {
            let self = this;
            
            self.timeOption = ko.observable(new nts.uk.ui.option.TimeEditorOption({
                width: "50"
            }));
            
            self.checked = ko.observable(true);
            self.subTransferSelected = ko.observable(0);
            self.oneDayTime = ko.observable(null);
            self.halfDayTime = ko.observable(null);
            self.certainDayTime = ko.observable(null);
            self.nameIdRadioGroup = nts.uk.util.randomId();
            
            self.isCertainDaySet = ko.computed(() => {
                return self.subTransferSelected() == 1;
            });
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
    
            let screenModel = new ScreenModel(screenMode, model, settingEnum);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                
                // update name id for radio
                $('.inputRadioHol').attr('name', screenModel.timeSetHol().nameIdRadioGroup);
                $('.inputRadioOT').attr('name', screenModel.timeSetOT().nameIdRadioGroup);
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A11'] = new KMK003A11BindingHandler();
}
