module a2 {
    class ScreenModel {

        fixTableOptionOneDay: any;
        fixTableOptionMorning: any;
        fixTableOptionAfternoon: any;
        isFlowMode: KnockoutObservable<boolean>;
        dataSourceOneDay: KnockoutObservableArray<any>;
        dataSourceMorning: KnockoutObservableArray<any>;
        dataSourceAfternoon: KnockoutObservableArray<any>;
        comboxRoundings: KnockoutObservableArray<any>;
        roundingProcsses: KnockoutObservableArray<any>;
        settingAttrs: KnockoutObservableArray<any>;
        calculationMethods: KnockoutObservableArray<any>;
        selectedCodeRounding: KnockoutObservable<any>;
        selectedCodeRoundingProcess: KnockoutObservable<any>;
        selectedCodeCalMed: KnockoutObservable<any>;
        selectedCodeSetting: KnockoutObservable<any>;

        data: KnockoutObservable<any>;
        /**
        * Constructor.
        */
        constructor(data: any, isFlowMode: any) {
            let self = this;
            self.isFlowMode = isFlowMode;
            self.data = ko.observable(data());
            self.dataSourceOneDay = ko.observableArray([]);
            self.dataSourceMorning = ko.observableArray([]);
            self.dataSourceAfternoon = ko.observableArray([]);
            self.comboxRoundings = ko.observableArray([
                { code: 1, name: '基本給1' },
                { code: 2, name: '役職手当2' },
                { code: 3, name: '基本給3' }
            ]);
            self.settingAttrs = ko.observableArray([
                { code: 1, name: '基本給1' },
                { code: 2, name: '役職手当2' },
                { code: 3, name: '基本給3' }
            ]);
            self.roundingProcsses = ko.observableArray([
                { code: 1, name: nts.uk.resource.getText("KMK003_91") },
                { code: 2, name: nts.uk.resource.getText("KMK003_92") }
            ]);
            self.calculationMethods = ko.observableArray([
                { code: 1, name: nts.uk.resource.getText("KMK003_136") },
                { code: 2, name: nts.uk.resource.getText("KMK003_137") }
            ]);
            self.selectedCodeRounding = ko.observable('1');
            self.selectedCodeRoundingProcess = ko.observable('1');
            self.selectedCodeCalMed = ko.observable('1');
            self.selectedCodeSetting = ko.observable('1');
            self.fixTableOptionOneDay = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceOneDay,
                isMultipleSelect: true,
                columns: self.columnSettingOneDay(),
                tabindex: -1
            };
            self.fixTableOptionMorning = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceMorning,
                isMultipleSelect: true,
                columns: self.columnSettingMorning(),
                tabindex: -1
            };
            self.fixTableOptionAfternoon = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceAfternoon,
                isMultipleSelect: true,
                columns: self.columnSettingAfternoon(),
                tabindex: -1
            };
             self.isFlowMode.subscribe(function(isFlowMode) {
                if (!isFlowMode) {
                   self.updateByFlowMode();
                }
            });
            
             // Create Customs handle For event rened nts grid.
            
        }

        /**
         * function update view by selected mode flow
         */
        public updateByFlowMode(): void {
            var self = this;
            (<any>ko.bindingHandlers).rended = {
                update: function(element: any, valueAccessor: any, allBindings: KnockoutAllBindingsAccessor) {
                    $('#nts-fix-table-a2-oneday').ntsFixTableCustom(self.fixTableOptionOneDay);
                    $('#nts-fix-table-a2-morning').ntsFixTableCustom(self.fixTableOptionMorning);
                    $('#nts-fix-table-a2-afternoon').ntsFixTableCustom(self.fixTableOptionMorning);
                }
            }
        }
        //bind data to screen items
        public bindDataToScreen(data: any) {
            let self = this;
        }

        /**
         * init array setting column option one day
         */
         private columnSettingOneDay(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_54"), 
                    key: "columnOneDay1", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 243, 
                    template: `<div data-bind="ntsTimeRangeEditor: { 
                        required: true, enable: true, inputFormat: 'time'}"/>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_56"), 
                    key: "columnOneDay2", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 243, 
                    template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_57"), 
                    key: "columnOneDay3", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 243, 
                    template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                }
            ];
        }
        /**
         * init array setting column option morning
         */
         private columnSettingMorning(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_54"), 
                    key: "columnMorning1", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 243, 
                    template: `<div data-bind="ntsTimeRangeEditor: { 
                        required: true, enable: true, inputFormat: 'time'}"/>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_56"), 
                    key: "columnMorning2", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 243, 
                    template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_57"), 
                    key: "columnMorning3", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 243, 
                    template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                }
            ];
        }
        /**
         * init array setting column option afternoon
         */
         private columnSettingAfternoon(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_54"), 
                    key: "columnAfternoon1", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 243, 
                    template: `<div data-bind="ntsTimeRangeEditor: { 
                        required: true, enable: true, inputFormat: 'time'}"/>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_56"), 
                    key: "columnAfternoon2", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 243, 
                    template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_57"), 
                    key: "columnAfternoon3", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 243, 
                    template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                }
            ];
        }

    }
    export class Item {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    class KMK003A2BindingHandler implements KnockoutBindingHandler {
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
            // service.findWorkTimeSetByCode()
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a2/index.xhtml').serialize();
            //get data
            let input = valueAccessor();
            let data = input.data;
            let isFlowMode = input.flowAction;

            let screenModel = new ScreenModel(data, isFlowMode);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                screenModel.bindDataToScreen(data);
                if (!screenModel.isFlowMode()) {
                    screenModel.updateByFlowMode();
                }
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A2'] = new KMK003A2BindingHandler();

}
