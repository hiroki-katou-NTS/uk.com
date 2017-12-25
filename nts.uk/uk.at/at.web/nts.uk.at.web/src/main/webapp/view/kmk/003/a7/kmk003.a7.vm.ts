module a7 {
    import EnumWorkForm = nts.uk.at.view.kmk003.a.viewmodel.EnumWorkForm;
    import SettingMethod = nts.uk.at.view.kmk003.a.viewmodel.SettingMethod;
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    import TabMode = nts.uk.at.view.kmk003.a.viewmodel.TabMode;
    class ScreenModel {

        tabMode: KnockoutObservable<TabMode>;
        
        mainSettingModel: KnockoutObservable<MainSettingModel>;
        fixTableOptionForFixedOrDiffTime: any;
        fixTableOptionForFlowOrFlexUse: any;
        fixTableOptionForFlowOrFlexNotUse1: any;
        fixTableOptionForFlowOrFlexNotUse2: any;
 
        dataSourceForFixedOrDiffTime: KnockoutObservableArray<any>;
        dataSourceForFlowOrFlexUse: KnockoutObservableArray<any>;
        dataSourceForFlowOrFlexNotUse1: KnockoutObservableArray<any>;
        dataSourceForFlowOrFlexNotUse2: KnockoutObservableArray<any>;
        
        isFlowOrFlex: KnockoutObservable<boolean>;
        
        useFixedRestTimeOptions: KnockoutObservableArray<any>;
        useFixedRestTime: KnockoutObservable<number>;
        
        isFixedRestTime: KnockoutObservable<boolean>;
        isFlexOrFlowNotUse: KnockoutObservable<boolean>;
        /**
        * Constructor.
        */
        constructor(tabMode: any, enumSetting: WorkTimeSettingEnumDto, mainSettingModel: MainSettingModel) {
            let self = this;
            
            self.tabMode = tabMode;
            
            //main model
            self.mainSettingModel = ko.observable(mainSettingModel);
            
            /////////////
            self.dataSourceForFixedOrDiffTime = ko.observableArray([]);
            self.dataSourceForFixedOrDiffTime.subscribe((newList) => {
                console.log(newList);
            });
            self.fixTableOptionForFixedOrDiffTime = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceForFixedOrDiffTime,
                isMultipleSelect: true,
                columns: self.columnSetting(),
                tabindex: 10
            };
            
            /////////////
            self.dataSourceForFlowOrFlexUse = ko.observableArray([]);
            self.dataSourceForFlowOrFlexUse.subscribe((newList) => {
                console.log(newList);
            });
            self.fixTableOptionForFlowOrFlexUse = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceForFlowOrFlexUse,
                isMultipleSelect: true,
                columns: self.columnSetting(),
                tabindex: 10
            };
            
            /////////////
            self.dataSourceForFlowOrFlexNotUse1 = ko.observableArray([]);
            self.dataSourceForFlowOrFlexNotUse1.subscribe((newList) => {
                console.log(newList);
            });
            self.fixTableOptionForFlowOrFlexNotUse1 = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceForFlowOrFlexNotUse1,
                isMultipleSelect: true,
                columns: self.columnSetting2(),
                tabindex: 10
            };
            
            /////////////
            self.dataSourceForFlowOrFlexNotUse2 = ko.observableArray([]);
            self.dataSourceForFlowOrFlexNotUse2.subscribe((newList) => {
                console.log(newList);
            });
            self.fixTableOptionForFlowOrFlexNotUse2 = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceForFlowOrFlexNotUse2,
                isMultipleSelect: true,
                columns: self.columnSetting2(),
                tabindex: 10
            };
            
            //subscrible worktime ssettingmethod
            self.isFlowOrFlex = ko.computed(function() {
                
                return (mainSettingModel.workTimeSetting.workTimeDivision.workTimeDailyAtr() == EnumWorkForm.FLEX) || (mainSettingModel.workTimeSetting.workTimeDivision.workTimeMethodSet() == SettingMethod.FLOW);
            });
           
            self.useFixedRestTimeOptions = ko.observableArray([
                { code: UseDivision.USE, name: 　nts.uk.resource.getText("KMK003_142") },
                { code: UseDivision.NOTUSE, name: 　nts.uk.resource.getText("KMK003_143") },
            ]);
            self.useFixedRestTime = ko.observable(1);
            
            self.isFixedRestTime = ko.computed(function(){
                return self.useFixedRestTime() == UseDivision.USE && self.isFlowOrFlex();
            });
            self.isFlexOrFlowNotUse = ko.computed(function() {
                return self.useFixedRestTime() == UseDivision.NOTUSE && self.isFlowOrFlex();
            });
            
            
        }

        private loadDataToScreen(mainSettingModel: MainSettingModel) {
            let self =this;
            
            //TODO recheck 
            self.dataSourceForFixedOrDiffTime = mainSettingModel.fixedWorkSetting.offdayWorkTimezone.restTimezone.lstTimezone;
            //TODO check 休憩時間を固定にする=true
            self.dataSourceForFlowOrFlexUse = mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixedRestTimezone.timezones;
            //TODO check 休憩時間を固定にする=false
            self.dataSourceForFlowOrFlexNotUse1 = mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowRestTimezone.flowRestSets;
            //TODO check 休憩時間を固定にする=false
//            self.dataSourceForFlowOrFlexNotUse1 = mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixedRestTimezone;
            
        }
        private columnSetting(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_54"), key: "column1", defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), width: 243, template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`}
            ];
        }
        
        private columnSetting2(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_174"), key: "column1", defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), width: 243, template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`},
                {
                    headerText: nts.uk.resource.getText("KMK003_176"), key: "column2", defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), width: 243, template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`}
            ];
        }
        
        export enum UseDivision {
        NOTUSE,
        USE
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
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A7'] = new KMK003A7BindingHandler();

}
