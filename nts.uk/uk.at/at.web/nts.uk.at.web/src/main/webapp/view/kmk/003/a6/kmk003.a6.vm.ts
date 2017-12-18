module a6 {
    class ScreenModel {

        fixTableOptionLeavetime: any;
        fixTableOptionClosework: any;
        selectedSettingMethod: KnockoutObservable<string>;
        selectedTab: KnockoutObservable<string>;
        isFlowMode: KnockoutObservable<boolean>;
        isActiveTab: KnockoutObservable<boolean>;
        dataSourceLeavetime: KnockoutObservableArray<any>;
        dataSourceClosework: KnockoutObservableArray<any>;

        /**
        * Constructor.
        */
        constructor(selectedSettingMethod: any, selectedTab: any) {
            let self = this;
            self.selectedSettingMethod = selectedSettingMethod;
            self.selectedTab = selectedTab;
            self.isFlowMode = ko.observable(self.getFlowModeBySelected(self.selectedSettingMethod()));
            self.isActiveTab = ko.observable(self.getActiveTabBySelected(self.selectedTab()));
            self.dataSourceLeavetime = ko.observableArray([]);
            self.dataSourceClosework = ko.observableArray([]);
            self.fixTableOptionLeavetime = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceLeavetime,
                isMultipleSelect: true,
                columns: self.columnSettingLeavetime(),
                tabindex: -1
            };
            self.fixTableOptionClosework = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceClosework,
                isMultipleSelect: true,
                columns: self.columnSettingClosework(),
                tabindex: -1
            };
            self.selectedTab.subscribe(function(selectedTab) {
                self.isActiveTab(self.getActiveTabBySelected(selectedTab));
            });
            self.selectedSettingMethod.subscribe(function(selectedSettingMethod) {
                self.isFlowMode(self.getFlowModeBySelected(selectedSettingMethod));
            });
            self.isFlowMode.subscribe(function(isFlowMode) {
                if (!isFlowMode && self.isActiveTab()) {
                    self.updateViewByNotFlowMode();
                }
                if (isFlowMode && self.isActiveTab()) {
                    self.updateViewByFlowMode();
                }
            });

            self.isActiveTab.subscribe(function(isActiveTab) {
                if (!self.isFlowMode() && isActiveTab) {
                    self.updateViewByNotFlowMode();
                }
                if (self.isFlowMode() && self.isActiveTab()) {
                    self.updateViewByFlowMode();
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
            // TODO: need to check
//            _.defer(() => {
//                $('#nts-fix-table-a6-leavetime').ntsFixTableCustom(self.fixTableOptionLeavetime);
//            });
        }
        /**
         * update view by flow mode
         */
        public updateViewByFlowMode(): void {
            var self = this;
            // TODO: need to check
//            _.defer(() => {
//                $('#nts-fix-table-a6-closework').ntsFixTableCustom(self.fixTableOptionClosework);
//            });
        }
        
         /**
         * init array setting column option leave time mode
         */
         private columnSettingLeavetime(): Array<any> {
            let self = this;
             return [
                 {
                     headerText: nts.uk.resource.getText("KMK003_54"),
                     key: "columnLeavetime1",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: { 
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_77"),
                     key: "columnLeavetime2",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_78"),
                     key: "columnLeavetime3",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_79"),
                     key: "columnLeavetime4",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_56"),
                     key: "columnLeavetime5",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_57"),
                     key: "columnLeavetime5",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 }
             ];
        }
        /**
         * init array setting column option close work
         */
         private columnSettingClosework(): Array<any> {
             let self = this;
             return [
                 {
                     headerText: nts.uk.resource.getText("KMK003_174"),
                     key: "columnClosework1",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: { 
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_77"),
                     key: "columnClosework2",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_78"),
                     key: "columnClosework3",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_79"),
                     key: "columnClosework4",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_56"),
                     key: "columnClosework5",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_57"),
                     key: "columnClosework5",
                     defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                     width: 143,
                     template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 }
             ];
         }
        
         /**
         * function get flow mode by selection ui
         */
        private getFlowModeBySelected(selectedSettingMethod: string): boolean {
            return (selectedSettingMethod === '3');
        }

        /**
        * function get active tab by selection ui
        */
        private getActiveTabBySelected(selectedTab: string): boolean {
            return (selectedTab === 'tab-6');
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

    class KMK003A6BindingHandler implements KnockoutBindingHandler {
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
                .mergeRelativePath('/view/kmk/003/a6/index.xhtml').serialize();
            //get data
            let input = valueAccessor();
            let selectedSettingMethod = input.settingMethod;
            let selectedTab = input.settingTab;

            let screenModel = new ScreenModel(selectedSettingMethod, selectedTab);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                if (!screenModel.isFlowMode()) {
                    screenModel.updateViewByNotFlowMode();
                } else {
                    screenModel.updateViewByFlowMode();
                }
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A6'] = new KMK003A6BindingHandler();

}
