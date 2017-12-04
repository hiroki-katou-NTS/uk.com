module a3 {
    class ScreenModel {

        fixTableOptionOvertime: any;
        fixTableOptionMorning: any;
        fixTableOptionAfternoon: any;
        fixTableOptionOvertimeFlex: any;
        fixTableOptionMorningFlex: any;
        fixTableOptionAfternoonFlex: any;
        fixTableOptionOvertimeFlow: any;
        selectedSettingMethod: KnockoutObservable<string>;
        selectedWorkForm: KnockoutObservable<string>;
        selectedTab: KnockoutObservable<string>;
        isFlowMode: KnockoutObservable<boolean>;
        isFlexWorkMode: KnockoutObservable<boolean>;
        isActiveTab: KnockoutObservable<boolean>;
        dataSourceOvertime: KnockoutObservableArray<any>;
        dataSourceMorning: KnockoutObservableArray<any>;
        dataSourceAfternoon: KnockoutObservableArray<any>;
        dataSourceOvertimeFlex: KnockoutObservableArray<any>;
        dataSourceMorningFlex: KnockoutObservableArray<any>;
        dataSourceAfternoonFlex: KnockoutObservableArray<any>;
        dataSourceOvertimeFlow: KnockoutObservableArray<any>;
        autoCalUseAttrs: KnockoutObservableArray<any>;
        selectedCodeAutoCalUse: KnockoutObservable<any>;

        data: KnockoutObservable<any>;
        /**
        * Constructor.
        */
        constructor(data: any, selectedSettingMethod: any, selectedTab: any, selectedWorkForm: any) {
            let self = this;
            self.data = ko.observable(data());
            self.selectedSettingMethod = selectedSettingMethod;
            self.selectedWorkForm = selectedWorkForm;
            self.selectedTab = selectedTab;
            self.isFlowMode = ko.observable(self.getFlowModeBySelected(self.selectedSettingMethod()));
            self.isFlexWorkMode = ko.observable(self.getFlexWorkModeBySelected(self.selectedWorkForm()));
            self.isActiveTab = ko.observable(self.getActiveTabBySelected(self.selectedTab()));
            self.dataSourceOvertime = ko.observableArray([]);
            self.dataSourceMorning = ko.observableArray([]);
            self.dataSourceAfternoon = ko.observableArray([]);
            self.dataSourceOvertimeFlex = ko.observableArray([]);
            self.dataSourceMorningFlex = ko.observableArray([]);
            self.dataSourceAfternoonFlex = ko.observableArray([]);
            self.dataSourceOvertimeFlow = ko.observableArray([]);
            self.autoCalUseAttrs = ko.observableArray([
                { code: 1, name: nts.uk.resource.getText("KMK003_142") },
                { code: 2, name: nts.uk.resource.getText("KMK003_143") }
            ]);
            self.selectedCodeAutoCalUse = ko.observable('1');
            self.fixTableOptionOvertime = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceOvertime,
                isMultipleSelect: true,
                columns: self.columnSettingOvertime(),
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
            self.fixTableOptionOvertimeFlex = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceOvertimeFlex,
                isMultipleSelect: true,
                columns: self.columnSettingOvertimeFlex(),
                tabindex: -1
            };
            self.fixTableOptionMorningFlex = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceMorningFlex,
                isMultipleSelect: true,
                columns: self.columnSettingMorningFlex(),
                tabindex: -1
            };
            self.fixTableOptionAfternoonFlex = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceAfternoonFlex,
                isMultipleSelect: true,
                columns: self.columnSettingAfternoonFlex(),
                tabindex: -1
            };
            self.fixTableOptionOvertimeFlow = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceOvertimeFlow,
                isMultipleSelect: true,
                columns: self.columnSettingOvertimeFlow(),
                tabindex: -1
            };
            self.selectedTab.subscribe(function(selectedTab) {
                self.isActiveTab(self.getActiveTabBySelected(selectedTab));
            });
            self.selectedSettingMethod.subscribe(function(selectedSettingMethod) {
                self.isFlowMode(self.getFlowModeBySelected(selectedSettingMethod));
            });
            self.selectedWorkForm.subscribe(function(selectedWorkForm){
                self.isFlexWorkMode(self.getFlexWorkModeBySelected(selectedWorkForm));
            });
            self.isFlowMode.subscribe(function(isFlowMode) {
                if (!self.isFlexWorkMode()) {
                    if (!isFlowMode && self.isActiveTab()) {
                        self.updateViewByNotFlowMode();
                    }
                    if (isFlowMode && self.isActiveTab()) {
                        self.updateViewByFlowMode();
                    }
                }
            });

            self.isActiveTab.subscribe(function(isActiveTab) {
                if (!self.isFlexWorkMode()) {
                    if (!self.isFlowMode() && isActiveTab) {
                        self.updateViewByNotFlowMode();
                    }
                    if (self.isFlowMode() && self.isActiveTab()) {
                        self.updateViewByFlowMode();
                    }
                }
                if(self.isFlexWorkMode() && isActiveTab){
                    self.updateViewByFlexMode();    
                }
            });
            
            self.isFlexWorkMode.subscribe(function(isFlexWorkMode){
                if (isFlexWorkMode && self.isActiveTab()) {
                    self.updateViewByFlexMode();
                }
                if (!isFlexWorkMode) {
                    if (!self.isFlowMode() && self.isActiveTab()) {
                        self.updateViewByNotFlowMode();
                    }
                    if (self.isFlowMode() && self.isActiveTab()) {
                        self.updateViewByFlowMode();
                    }
                }
            });
            

             // Create Customs handle For event rened nts grid.
            
        }

        /**
         * function update view by selected mode fixed
         */
        /*public updateByFixedMode(): void {
            var self = this;
            (<any>ko.bindingHandlers).rended = {
                update: function(element: any, valueAccessor: any, allBindings: KnockoutAllBindingsAccessor) {
                   
                }
            }
        }*/
        //bind data to screen items
        public bindDataToScreen(data: any) {
            let self = this;
        }
        
        /**
         * update view by flow mode
         */
        public updateViewByNotFlowMode(): void {
            var self = this;
            _.defer(() => {
                $('#nts-fix-table-a3-overtime').ntsFixTableCustom(self.fixTableOptionOvertime);
                $('#nts-fix-table-a3-morning').ntsFixTableCustom(self.fixTableOptionMorning);
                $('#nts-fix-table-a3-afternoon').ntsFixTableCustom(self.fixTableOptionAfternoon);
            });
        }
        /**
         * update view by flow mode
         */
        public updateViewByFlowMode(): void {
            var self = this;
            _.defer(() => {
                $('#nts-fix-table-a3-overtime-flow').ntsFixTableCustom(self.fixTableOptionOvertime);
            });
        }
        
        /**
         * update view by flex mode
         */
        public updateViewByFlexMode(): void {
            var self = this;
            _.defer(() => {
                $('#nts-fix-table-a3-overtime-flex').ntsFixTableCustom(self.fixTableOptionOvertimeFlex);
                $('#nts-fix-table-a3-morning-flex').ntsFixTableCustom(self.fixTableOptionMorningFlex);
                $('#nts-fix-table-a3-afternoon-flex').ntsFixTableCustom(self.fixTableOptionAfternoonFlex);
            });
        }
         /**
         * init array setting column option overtime flex mode
         */
         private columnSettingOvertime(): Array<any> {
            let self = this;
            var res: Array<any> = [
                {
                    headerText: nts.uk.resource.getText("KMK003_186"), 
                    key: "columnOvertime6", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 143, 
                    template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_187"), 
                    key: "columnOvertime7", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 143, 
                    template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                }
            ];
            var arrayFlex: Array<any> = self.columnSettingOvertimeFlex();
             for(var item of res){
                 arrayFlex.push(item);   
             }
             return arrayFlex;
        }
        /**
         * init array setting column option overtime flex mode
         */
         private columnSettingOvertimeFlex(): Array<any> {
             let self = this;
             return [
                 {
                     headerText: nts.uk.resource.getText("KMK003_54"),
                     key: "columnOvertime1",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: { 
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_56"),
                     key: "columnOvertime2",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_57"),
                     key: "columnOvertime3",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_58"),
                     key: "columnOvertime4",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_182"),
                     key: "columnOvertime5",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 }
             ];
         }
        /**
         * init array setting column option overtime flow mode
         */
         private columnSettingOvertimeFlow(): Array<any> {
             let self = this;
             return [
                 {
                     headerText: nts.uk.resource.getText("KMK003_174"),
                     key: "columnFlowOvertime1",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: { 
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_56"),
                     key: "columnFlowOvertime2",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_57"),
                     key: "columnFlowOvertime3",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_58"),
                     key: "columnFlowOvertime4",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_186"),
                     key: "columnFlowOvertime5",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_187"),
                     key: "columnFlowOvertime6",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
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
             var arrayMorning : Array<any> = self.columnSettingMorningFlex();
             arrayMorning.push({
                 headerText: nts.uk.resource.getText("KMK003_186"),
                 key: "columnA3Morning6",
                 defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                 width: 100,
                 template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
             });
             arrayMorning.push({
                 headerText: nts.uk.resource.getText("KMK003_187"),
                 key: "columnA3Morning7",
                 defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                 width: 100,
                 template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
             });
            return arrayMorning;
        }
        
        /**
         * init array setting column option morning flex
         */
         private columnSettingMorningFlex(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_54"), 
                    key: "columnA3Morning1", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 100, 
                    template: `<div data-bind="ntsTimeRangeEditor: { 
                        required: true, enable: true, inputFormat: 'time'}"/>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_56"), 
                    key: "columnA3Morning2", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 100, 
                    template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_57"), 
                    key: "columnA3Morning3", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 100, 
                    template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_58"), 
                    key: "columnA3Morning4", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 100, 
                    template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_182"), 
                    key: "columnA3Morning5", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 100, 
                    template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                }
            ];
        }
        /**
         * init array setting column option afternoon flex
         */
         private columnSettingAfternoonFlex(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_54"), 
                    key: "columnA3Afternoon1", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 143, 
                    template: `<div data-bind="ntsTimeRangeEditor: { 
                        required: true, enable: true, inputFormat: 'time'}"/>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_56"), 
                    key: "columnA3Afternoon2", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 143, 
                    template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_57"), 
                    key: "columnA3Afternoon3", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 143, 
                    template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_58"), 
                    key: "columnA3Afternoon4", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 143, 
                    template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_182"), 
                    key: "columnA3Afternoon5", 
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), 
                    width: 143, 
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
             var arrayAfternoon: Array<any> = self.columnSettingAfternoonFlex();
             arrayAfternoon.push(
                 {
                     headerText: nts.uk.resource.getText("KMK003_186"),
                     key: "columnA3Afternoon6",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 });
             arrayAfternoon.push(
                 {
                     headerText: nts.uk.resource.getText("KMK003_187"),
                     key: "columnA3Afternoon7",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 });
             return arrayAfternoon;
         }
        
         /**
         * function get flow mode by selection ui
         */
        private getFlowModeBySelected(selectedSettingMethod: string): boolean {
            return (selectedSettingMethod === '3');
        }
        /**
         * function get flex work mode by selection ui
        */
        private getFlexWorkModeBySelected(selectedWorkForm: string): boolean {
            return (selectedWorkForm === '2');
        }

        /**
        * function get active tab by selection ui
        */
        private getActiveTabBySelected(selectedTab: string): boolean {
            return (selectedTab === 'tab-3');
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

    class KMK003A3BindingHandler implements KnockoutBindingHandler {
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
                .mergeRelativePath('/view/kmk/003/a3/index.xhtml').serialize();
            //get data
            let input = valueAccessor();
            let data = input.data;
            let selectedSettingMethod = input.settingMethod;
            let selectedTab = input.settingTab;
            let selectedWorkForm = input.settingWorkFrom;

            let screenModel = new ScreenModel(data, selectedSettingMethod, selectedTab, selectedWorkForm);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                screenModel.bindDataToScreen(data);
                if (screenModel.isFlexWorkMode()) {
                    screenModel.updateViewByFlexMode();
                }
                else {
                    if (!screenModel.isFlowMode()) {
                        screenModel.updateViewByNotFlowMode();
                    } else {
                        screenModel.updateViewByFlowMode();
                    }
                }
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A3'] = new KMK003A3BindingHandler();

}
