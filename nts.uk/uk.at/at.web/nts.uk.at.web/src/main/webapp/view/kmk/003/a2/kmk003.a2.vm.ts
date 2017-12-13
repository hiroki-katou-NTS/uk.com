module a2 {
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EmTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.EmTimeZoneSetModel;
    import EmTimeZoneSetDto = nts.uk.at.view.kmk003.a.service.model.common.EmTimeZoneSetDto;
    class ScreenModel {

        fixTableOptionOneDay: any;
        fixTableOptionMorning: any;
        fixTableOptionAfternoon: any;
        selectedSettingMethod: KnockoutObservable<string>;
        selectedTab: KnockoutObservable<string>;
        isFlowMode: KnockoutObservable<boolean>;
        isActiveTab: KnockoutObservable<boolean>;
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
        settingEnum: WorkTimeSettingEnumDto;
        dataModelOneDay: EmTimeZoneSetModel[];
        /**
        * Constructor.
        */
        constructor(selectedSettingMethod: any, selectedTab: any, settingEnum: WorkTimeSettingEnumDto) {
            let self = this;
            self.selectedSettingMethod = selectedSettingMethod;
            self.selectedTab = selectedTab;
            self.settingEnum = settingEnum;
            self.dataModelOneDay = [];
            self.isFlowMode = ko.observable(self.getFlowModeBySelected(self.selectedSettingMethod()));
            self.isActiveTab = ko.observable(self.getActiveTabBySelected(self.selectedTab()));
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
                maxRow: 5,
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
            self.selectedTab.subscribe(function(selectedTab) {
                self.isActiveTab(self.getActiveTabBySelected(selectedTab));
            });
            self.selectedSettingMethod.subscribe(function(selectedSettingMethod) {
                self.isFlowMode(self.getFlowModeBySelected(selectedSettingMethod));
            });
            self.isFlowMode.subscribe(function(isFlowMode) {
                if (!isFlowMode && self.isActiveTab()) {
                    self.updateViewByFlowMode();
                }
            });

            self.isActiveTab.subscribe(function(isActiveTab) {
                if (!self.isFlowMode() && isActiveTab) {
                    self.updateViewByFlowMode();
                }
            });
            
             // Create Customs handle For event rened nts grid.
            
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
            return (selectedTab === 'tab-2');
        }
        /**
         * function update view by selected mode flow
         */
       /* public updateByFlowMode(): void {
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
        public updateViewByFlowMode(): void {
            var self = this;
            // TODO: need to check
//            _.defer(() => {
//                $('#nts-fix-table-a2-oneday').ntsFixTableCustom(self.fixTableOptionOneDay);
//                $('#nts-fix-table-a2-morning').ntsFixTableCustom(self.fixTableOptionMorning);
//                $('#nts-fix-table-a2-afternoon').ntsFixTableCustom(self.fixTableOptionAfternoon);
//            });
        }

        /**
         * function convert dto to model
         */
        private toModelColumnSetting(dataModel: EmTimeZoneSetDto): any {
            return {
                columnOneDay1: ko.observable({ startTime: dataModel.timezone.start, endTime: dataModel.timezone.end }),
                columnOneDay2: ko.observable(dataModel.timezone.rounding.roundingTime),
                columnOneDay3: ko.observable(dataModel.timezone.rounding.rounding)
            }
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
                    dataSource: self.settingEnum.roundingTime,
                    defaultValue: ko.observable(0), 
                    width: 120, 
                    template: `<div class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'value',
                                    visibleItemsCount: 5,
                                    optionsText: 'localizedName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'localizedName', length: 10 }]}">
                                </div>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_57"), 
                    key: "columnOneDay3", 
                    dataSource: self.settingEnum.rounding,
                    defaultValue: ko.observable(0), 
                    width: 150,
                   template: `<div class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'value',
                                    visibleItemsCount: 5,
                                    optionsText: 'localizedName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'localizedName', length: 10 }]}">
                                </div>`
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
                    width: 50, 
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
            let selectedSettingMethod = input.settingMethod;
            let selectedTab = input.settingTab;
            var settingEnum: WorkTimeSettingEnumDto = input.enum;
            let screenModel = new ScreenModel(selectedSettingMethod, selectedTab, settingEnum);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                if(!screenModel.isFlowMode()){
                    screenModel.updateViewByFlowMode();
                }
            });
            
        }

    }
    ko.bindingHandlers['ntsKMK003A2'] = new KMK003A2BindingHandler();

}
