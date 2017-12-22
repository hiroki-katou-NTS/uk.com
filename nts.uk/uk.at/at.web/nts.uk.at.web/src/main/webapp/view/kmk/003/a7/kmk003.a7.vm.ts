module a7 {
    import EnumWorkForm = nts.uk.at.view.kmk003.a.viewmodel.EnumWorkForm;
    import SettingMethod = nts.uk.at.view.kmk003.a.viewmodel.SettingMethod;
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    import TabMode = nts.uk.at.view.kmk003.a.viewmodel.TabMode;
    class ScreenModel {

        tabMode: KnockoutObservable<TabMode>;
        
        mainSettingModel: KnockoutObservable<MainSettingModel>;
        fixTableOption: any;
        dataSource: KnockoutObservableArray<any>;
        isFlowOrFlex: KnockoutObservable<boolean>;
        
        useFixedRestTimeOptions: KnockoutObservableArray<any>;
        useFixedRestTime: KnockoutObservable<string>;
        
        isFixedRestTime: KnockoutObservable<boolean>;
        /**
        * Constructor.
        */
        constructor(tabMode: any, enumSetting: WorkTimeSettingEnumDto, mainSettingModel: MainSettingModel) {
            let self = this;
            
            self.tabMode = tabMode;
            
            //main model
            self.mainSettingModel = ko.observable(mainSettingModel);
            
            self.dataSource = ko.observableArray([]);
            self.dataSource.subscribe((newList) => {
                console.log(newList);
            });
            self.fixTableOption = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSource,
                isMultipleSelect: true,
                columns: self.columnSetting(),
                tabindex: 10
            };
            
            //subscrible worktime ssettingmethod
            self.isFlowOrFlex = ko.computed(function() {
                return (mainSettingModel.workTimeSetting.workTimeDivision.workTimeDailyAtr() == EnumWorkForm.FLEX) || (mainSettingModel.workTimeSetting.workTimeDivision.workTimeMethodSet() == SettingMethod.FLOW);
            });
            
            self.useFixedRestTimeOptions = ko.observableArray([
                { code: 1, name: 　nts.uk.resource.getText("KMK003_142") },
                { code: 2, name: 　nts.uk.resource.getText("KMK003_143") },
            ]);
            self.useFixedRestTime = ko.observable('1');
            
            self.isFixedRestTime = ko.computed(function(){
                return self.useFixedRestTime() =='1';
            });
        }

        private columnSetting(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_54"), key: "column1", defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), width: 243, template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`}
            ];
        }
    }

    class KMK003A7BindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        }

        private getData() {
            let self = this;
            //            service.findWorkTimeSetByCode()    
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a7/index.xhtml').serialize();

            //get data
            let input = valueAccessor();
            let tabMode = input.tabMode;
            let enumSetting = input.enum;
            let mainSettingModel = input.mainSettingModel;

            var screenModel = new ScreenModel(tabMode, enumSetting, mainSettingModel);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);

                // TODO: need to check
//                $('#nts-fix-table-a7').ntsFixTableCustom(screenModel.fixTableOption);
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A7'] = new KMK003A7BindingHandler();

}
