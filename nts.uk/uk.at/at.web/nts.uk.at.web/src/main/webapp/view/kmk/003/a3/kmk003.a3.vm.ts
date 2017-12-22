module a3 {
    import TimeRoundingSettingDto = nts.uk.at.view.kmk003.a.service.model.common.TimeRoundingSettingDto;
    import FlOTTimezoneDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlOTTimezoneDto;
    import FlTimeSettingDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlTimeSettingDto;
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import FlOTTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.flowset.FlOTTimezoneModel;
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    class ScreenModel {

        fixTableOptionOvertime: any;
        fixTableOptionMorning: any;
        fixTableOptionAfternoon: any;
        fixTableOptionOvertimeFlex: any;
        fixTableOptionMorningFlex: any;
        fixTableOptionAfternoonFlex: any;
        fixTableOptionOvertimeFlow: any;
        isFlowMode: KnockoutObservable<boolean>;
        isFlexMode: KnockoutObservable<boolean>;
        dataSourceOvertime: KnockoutObservableArray<any>;
        dataSourceMorning: KnockoutObservableArray<any>;
        dataSourceAfternoon: KnockoutObservableArray<any>;
        dataSourceOvertimeFlex: KnockoutObservableArray<any>;
        dataSourceMorningFlex: KnockoutObservableArray<any>;
        dataSourceAfternoonFlex: KnockoutObservableArray<any>;
        dataSourceOvertimeFlow: KnockoutObservableArray<any>;
        autoCalUseAttrs: KnockoutObservableArray<any>;
        selectedCodeAutoCalUse: KnockoutObservable<any>;
        settingEnum: WorkTimeSettingEnumDto;
        mainSettingModel: MainSettingModel;
        lstSelectOrderModel: SettlementOrder[];

        /**
        * Constructor.
        */
        constructor(settingEnum: WorkTimeSettingEnumDto, mainSettingModel: MainSettingModel) {
            let self = this;
            self.settingEnum = settingEnum;
            self.mainSettingModel = mainSettingModel;
            self.isFlexMode = self.mainSettingModel.workTimeSetting.isFlex;
            self.isFlowMode = self.mainSettingModel.workTimeSetting.isFlow;
            self.dataSourceOvertime = ko.observableArray([]);
            self.dataSourceMorning = ko.observableArray([]);
            self.dataSourceAfternoon = ko.observableArray([]);
            self.dataSourceOvertimeFlex = ko.observableArray([]);
            self.dataSourceMorningFlex = ko.observableArray([]);
            self.dataSourceAfternoonFlex = ko.observableArray([]);
            self.dataSourceOvertimeFlow = ko.observableArray([]);
            var dataFlow: any[] = [];
            for (var dataModel of self.mainSettingModel.flowWorkSetting.halfDayWorkTimezone.workTimeZone.lstOTTimezone) {
                dataFlow.push(self.toModelColumnSetting(dataModel.toDto()));
            }
            self.dataSourceOvertimeFlow(dataFlow);
            self.autoCalUseAttrs = ko.observableArray([
                { code: 1, name: nts.uk.resource.getText("KMK003_142") },
                { code: 2, name: nts.uk.resource.getText("KMK003_143") }
            ]);
            self.lstSelectOrderModel = [];
            for (var i: number = 1; i <= 10; i++) {
                self.lstSelectOrderModel.push({ code: '' + i, name: '' + i });
            }
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
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 10,
                isShowButton: true,
                dataSource: self.dataSourceOvertimeFlow,
                isMultipleSelect: true,
                columns: self.columnSettingOvertimeFlow(),
                tabindex: -1
            };
            
            // update time zone
            self.dataSourceOvertimeFlow.subscribe(function(dataFlow: any[]){
                var lstTimezone : FlOTTimezoneDto[] = [];
                var worktimeNo: number = 0;
                for (var dataModel of dataFlow) {
                    worktimeNo++;
                    lstTimezone.push(self.toModelDto(dataModel, worktimeNo));
                } 
                self.mainSettingModel.flowWorkSetting.halfDayWorkTimezone.workTimeZone.updateTimezone(lstTimezone);
            });
            
        }
        
        /**
         * function convert dto to model
         */
        private toModelColumnSetting(dataDTO: FlOTTimezoneDto): any {
            return {
                elapsedTime: ko.observable(dataDTO.flowTimeSetting.elapsedTime),
                rounding: ko.observable(dataDTO.flowTimeSetting.rounding.rounding),
                roundingTime: ko.observable(dataDTO.flowTimeSetting.rounding.roundingTime),
                inLegalOTFrameNo: ko.observable(dataDTO.inLegalOTFrameNo),
                settlementOrder: ko.observable(dataDTO.settlementOrder)
            }
        }
        
        /**
         * function convert data model of client to parent
         */
        private toModelDto(dataModel: any, worktimeNo: number): FlOTTimezoneDto {
            var rounding: TimeRoundingSettingDto = {
                roundingTime: dataModel.roundingTime(),
                rounding: dataModel.rounding()
            };
            var flowTimeSetting: FlTimeSettingDto = {
                rounding: rounding,
                elapsedTime: dataModel.elapsedTime(),
            };
            var dataDTO: FlOTTimezoneDto = {
                worktimeNo: worktimeNo,
                restrictTime: false,
                overtimeFrameNo: 1,
                flowTimeSetting: flowTimeSetting,
                inLegalOTFrameNo: dataModel.inLegalOTFrameNo(),
                settlementOrder: dataModel.settlementOrder()
            };
            return dataDTO;
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
                     key: "elapsedTime",
                     defaultValue: ko.observable(1200), 
                     width: 100, 
                     template: `<input data-bind="ntsTimeEditor: {
                        inputFormat: 'time'}" />`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_56"),
                     key: "roundingTime",
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
                     key: "rounding",
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
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_58"),
                     key: "oTFrameNo",
                     dataSource: self.lstSelectOrderModel,
                     defaultValue: ko.observable(1),
                     width: 120,
                     template: `<div class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'code',
                                    visibleItemsCount: 5,
                                    optionsText: 'name',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'name', length: 2 }]}">
                                </div>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_186"),
                     key: "inLegalOTFrameNo",
                     dataSource: self.lstSelectOrderModel,
                     defaultValue: ko.observable(1),
                     width: 120,
                     template:  `<div class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'code',
                                    visibleItemsCount: 5,
                                    optionsText: 'name',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'name', length: 2 }]}">
                                </div>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_187"),
                     key: "settlementOrder",
                     dataSource: self.lstSelectOrderModel,
                     defaultValue: ko.observable(1),
                     width: 120,
                     template:  `<div class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'code',
                                    visibleItemsCount: 5,
                                    optionsText: 'name',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'name', length: 2 }]}">
                                </div>`
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
    export interface SettlementOrder {
        code: string;
        name: string;
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
            var settingEnum: WorkTimeSettingEnumDto = input.enum;
            var mainSettingModel: MainSettingModel = input.mainModel;

            let screenModel = new ScreenModel(settingEnum, mainSettingModel);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A3'] = new KMK003A3BindingHandler();

}
