module a6 {
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import FlTimeSettingDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlTimeSettingDto;
    import TimeRoundingSettingDto = nts.uk.at.view.kmk003.a.service.model.common.TimeRoundingSettingDto;
    import HDWorkTimeSheetSettingDto = nts.uk.at.view.kmk003.a.service.model.common.HDWorkTimeSheetSettingDto;
    import FlWorkHdTimeZoneDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlWorkHdTimeZoneDto;
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    class ScreenModel {

        fixTableOptionFlow: any;
        fixTableOptionFlex: any;
        selectedTab: KnockoutObservable<string>;
        isFlowMode: KnockoutObservable<boolean>;
        isActiveTab: KnockoutObservable<boolean>;
        dataSourceFlow: KnockoutObservableArray<any>;
        dataSourceFlex: KnockoutObservableArray<any>;
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
            self.isFlowMode = self.mainSettingModel.workTimeSetting.isFlow;
            self.dataSourceFlow = ko.observableArray([]);
            self.dataSourceFlex = ko.observableArray([]);
            self.lstSelectOrderModel = [];
            for (var i: number = 1; i <= 10; i++) {
                self.lstSelectOrderModel.push({ code: '' + i, name: '' + i });
            }
            self.fixTableOptionFlow = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceFlow,
                isMultipleSelect: true,
                columns: self.columnSettingFlow(),
                tabindex: -1
            };
            self.fixTableOptionFlex = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceFlex,
                isMultipleSelect: true,
                columns: self.columnSettingOtherFlow(),
                tabindex: -1
            };
            
            self.dataSourceFlow.subscribe(function(dataFlow: any[]) {
                var lstOTTimezone: FlWorkHdTimeZoneDto[] = [];
                var workTimezoneNo: number = 0;
                for (var dataModel of dataFlow) {
                    workTimezoneNo++;
                    lstOTTimezone.push(self.toModelFlowDto(dataModel, workTimezoneNo));
                }
                self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.updateHDTimezone(lstOTTimezone);
            });
            
            self.dataSourceFlex.subscribe(function(dataFlex: any[]) {
                var lstOTTimezone: HDWorkTimeSheetSettingDto[] = [];
                var workTimezoneNo: number = 0;
                for (var dataModel of dataFlex) {
                    workTimezoneNo++;
                    lstOTTimezone.push(self.toModelFlexDto(dataModel, workTimezoneNo));
                }
                self.mainSettingModel.flexWorkSetting.offdayWorkTime.updateHDTimezone(lstOTTimezone);
            });
            
        }
        /**
         * function convert dto to model
         */
        private toModelFlowColumnSetting(dataDTO: FlWorkHdTimeZoneDto): any {
            return {
                elapsedTime: ko.observable(dataDTO.flowTimeSetting.elapsedTime),
                rounding: ko.observable(dataDTO.flowTimeSetting.rounding.rounding),
                roundingTime: ko.observable(dataDTO.flowTimeSetting.rounding.roundingTime),
                inLegalBreakFrameNo: ko.observable(dataDTO.inLegalBreakFrameNo),
                outLegalBreakFrameNo: ko.observable(dataDTO.outLegalBreakFrameNo),
                outLegalPubHolFrameNo: ko.observable(dataDTO.outLegalPubHolFrameNo)
            }
        }
        
        /**
         * function convert data model of client to parent
         */
        private toModelFlowDto(dataModel: any, worktimeNo: number): FlWorkHdTimeZoneDto {
            var rounding: TimeRoundingSettingDto = {
                roundingTime: dataModel.roundingTime(),
                rounding: dataModel.rounding()
            };
            var flowTimeSetting: FlTimeSettingDto = {
                rounding: rounding,
                elapsedTime: dataModel.elapsedTime(),
            };
            var dataDTO: FlWorkHdTimeZoneDto = {
                worktimeNo: worktimeNo,
                outLegalPubHolFrameNo: dataModel.outLegalPubHolFrameNo(),
                useInLegalBreakRestrictTime: false,
                flowTimeSetting: flowTimeSetting,
                useOutLegalBreakRestrictTime: false,
                useOutLegalPubHolRestrictTime: false,
                inLegalBreakFrameNo: dataModel.inLegalBreakFrameNo(),
                outLegalBreakFrameNo: dataModel.outLegalBreakFrameNo()
            };
            return dataDTO;
        }
        /**
         * function convert dto to model
         */
        private toModelFlexColumnSetting(dataDTO: HDWorkTimeSheetSettingDto): any {
            return {
                timezone: ko.observable({starTime: dataDTO.timezone.start, endTime: dataDTO.timezone.end}),
                rounding: ko.observable(dataDTO.timezone.rounding.rounding),
                roundingTime: ko.observable(dataDTO.timezone.rounding.roundingTime),
                inLegalBreakFrameNo: ko.observable(dataDTO.inLegalBreakFrameNo),
                outLegalBreakFrameNo: ko.observable(dataDTO.outLegalBreakFrameNo),
                outLegalPubHolFrameNo: ko.observable(dataDTO.outLegalPubHDFrameNo)
            }
        }
        
        /**
         * function convert data model of client to parent
         */
        private toModelFlexDto(dataModel: any, worktimeNo: number): HDWorkTimeSheetSettingDto {
            var timezone: TimeRoundingSettingDto = {
                roundingTime: dataModel.roundingTime(),
                rounding: dataModel.rounding()
            };
            var dataDTO: HDWorkTimeSheetSettingDto = {
                workTimeNo: worktimeNo,
                timezone: timezone,
                isLegalHolidayConstraintTime: false,
                inLegalBreakFrameNo: dataModel.inLegalBreakFrameNo(),
                isNonStatutoryDayoffConstraintTime: false,
                isNonStatutoryHolidayConstraintTime: false,
                outLegalBreakFrameNo: dataModel.outLegalBreakFrameNo(),
                outLegalPubHDFrameNo: dataModel.outLegalPubHolFrameNo()
            };
            return dataDTO;
        }
        
        
         /**
         * init array setting column option leave time mode
         */
         private columnSettingOtherFlow(): Array<any> {
            let self = this;
             return [
                 {
                     headerText: nts.uk.resource.getText("KMK003_54"),
                     key: "timezone",
                     defaultValue: ko.observable({ startTime: 1, endTime: 1 }),
                     width: 250,
                     template: `<div class= "fixtable" data-bind="ntsTimeRangeEditor: { 
                        startConstraint: 'TimeWithDayAttr', endConstraint: 'TimeWithDayAttr',
                        required: true, enable: true, inputFormat: 'time'}"/>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_77"),
                     key: "inLegalBreakFrameNo",
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
                     headerText: nts.uk.resource.getText("KMK003_78"),
                     key: "outLegalBreakFrameNo",
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
                     headerText: nts.uk.resource.getText("KMK003_79"),
                     key: "outLegalPubHolFrameNo",
                     defaultValue: ko.observable(1),
                     dataSource: self.lstSelectOrderModel,
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
                 }
             ];
        }
        /**
         * init array setting column option close work
         */
         private columnSettingFlow(): Array<any> {
             let self = this;
             return [
                 {
                     headerText: nts.uk.resource.getText("KMK003_174"),
                     key: "elapsedTime",
                     defaultValue: ko.observable(0),
                     width: 143,
                     template: `<input data-bind="ntsTimeEditor: {
                        inputFormat: 'time'}" />`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_77"),
                     key: "inLegalBreakFrameNo",
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
                     headerText: nts.uk.resource.getText("KMK003_78"),
                     key: "outLegalBreakFrameNo",
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
                     headerText: nts.uk.resource.getText("KMK003_79"),
                     key: "outLegalPubHolFrameNo",
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
                 }
             ];
         }
        

    }
    
    export interface SettlementOrder {
        code: string;
        name: string;
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
            var settingEnum: WorkTimeSettingEnumDto = input.enum;
            var mainSettingModel: MainSettingModel = input.mainModel;

            let screenModel = new ScreenModel(settingEnum, mainSettingModel);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A6'] = new KMK003A6BindingHandler();

}
