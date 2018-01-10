module a6 {
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import FlTimeSettingDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlTimeSettingDto;
    import TimeRoundingSettingDto = nts.uk.at.view.kmk003.a.service.model.common.TimeRoundingSettingDto;
    import TimeZoneRoundingDto = nts.uk.at.view.kmk003.a.service.model.common.TimeZoneRoundingDto;
    import HDWorkTimeSheetSettingDto = nts.uk.at.view.kmk003.a.service.model.common.HDWorkTimeSheetSettingDto;
    import FlWorkHdTimeZoneDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlWorkHdTimeZoneDto;
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    import WorkDayoffFrameFindDto = nts.uk.at.view.kmk003.a6.service.model.WorkDayoffFrameFindDto;
    class ScreenModel {

        fixTableOptionFlow: any;
        fixTableOptionFlex: any;
        fixTableOptionFixed: any;
        selectedTab: KnockoutObservable<string>;
        isFlowMode: KnockoutObservable<boolean>;
        isFlexMode: KnockoutObservable<boolean>;
        isFixedMode: KnockoutObservable<boolean>;
        isLoading: KnockoutObservable<boolean>;
        dataSourceFlow: KnockoutObservableArray<any>;
        dataSourceFlex: KnockoutObservableArray<any>;
        dataSourceFixed: KnockoutObservableArray<any>;
        settingEnum: WorkTimeSettingEnumDto;
        mainSettingModel: MainSettingModel;
        
        //update specs 7.6
//        lstOvertimeWorkFrame: OvertimeWorkFrameFindDto[];
        lstWorkDayOffFrame: WorkDayoffFrameFindDto[];

        /**
        * Constructor.
        */
        constructor(settingEnum: WorkTimeSettingEnumDto, mainSettingModel: MainSettingModel, isLoading: KnockoutObservable<boolean>) {
            let self = this;
            self.settingEnum = settingEnum;
            self.mainSettingModel = mainSettingModel;
            self.isFlowMode = self.mainSettingModel.workTimeSetting.isFlow;
            self.isFlexMode = self.mainSettingModel.workTimeSetting.isFlex;
            self.isFixedMode = self.mainSettingModel.workTimeSetting.isFixed;
            self.isLoading = isLoading;
            self.dataSourceFlow = ko.observableArray([]);
            self.dataSourceFlex = ko.observableArray([]);
            self.dataSourceFixed = ko.observableArray([]);
            self.updateDataByUpdateModel();
            self.lstWorkDayOffFrame = [];
            self.isLoading.subscribe(function(isLoading: boolean){
               self.updateDataByUpdateModel(); 
            });
            

            self.dataSourceFlow.subscribe(function(dataFlow: any[]) {
                var lstWorkTimezone: FlWorkHdTimeZoneDto[] = [];
                var workTimezoneNo: number = 0;
                for (var dataModel of dataFlow) {
                    workTimezoneNo++;
                    lstWorkTimezone.push(self.toModelFlowDto(dataModel, workTimezoneNo));
                }
                self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.updateHDTimezone(lstWorkTimezone);
            });

            self.dataSourceFlex.subscribe(function(dataFlex: any[]) {
                var lstWorkTimezone: HDWorkTimeSheetSettingDto[] = [];
                var workTimezoneNo: number = 0;
                for (var dataModel of dataFlex) {
                    workTimezoneNo++;
                    lstWorkTimezone.push(self.toModelOtherFlowDto(dataModel, workTimezoneNo));
                }
                self.mainSettingModel.flexWorkSetting.offdayWorkTime.updateHDTimezone(lstWorkTimezone);
            });

            self.dataSourceFixed.subscribe(function(dataFixed: any[]) {
                var lstWorkTimezone: HDWorkTimeSheetSettingDto[] = [];
                var workTimezoneNo: number = 0;
                for (var dataModel of dataFixed) {
                    workTimezoneNo++;
                    lstWorkTimezone.push(self.toModelOtherFlowDto(dataModel, workTimezoneNo));
                }
                self.mainSettingModel.fixedWorkSetting.offdayWorkTimezone.updateHDTimezone(lstWorkTimezone);
            });

        }

        /**
         * function init data model
         */
        public initDataModel(): void {
            var self = this;
            self.fixTableOptionFlow = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 10,
                isShowButton: true,
                dataSource: self.dataSourceFlow,
                isMultipleSelect: true,
                columns: self.columnSettingFlow(),
                tabindex: -1
            };
            self.fixTableOptionFlex = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 10,
                isShowButton: true,
                dataSource: self.dataSourceFlex,
                isMultipleSelect: true,
                columns: self.columnSettingOtherFlow(),
                tabindex: -1
            };
            self.fixTableOptionFixed = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 10,
                isShowButton: true,
                dataSource: self.dataSourceFixed,
                isMultipleSelect: true,
                columns: self.columnSettingOtherFlow(),
                tabindex: -1
            };
        }
        /**
         * function update data by update model
         */
        private updateDataByUpdateModel(): void {
            var self = this;
            
            // mode flow
            if (self.isFlowMode()) {
                var dataFlow: any[] = [];
                for (var dataModelFlow of self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.lstWorkTimezone) {
                    dataFlow.push(self.toModelFlowColumnSetting(dataModelFlow.toDto()));
                }
                self.dataSourceFlow(dataFlow);
            }
            
            // mode flex
            if (self.isFlexMode()) {
                var dataFlex: any[] = [];
                for (var dataModelFlex of self.mainSettingModel.flexWorkSetting.offdayWorkTime.lstWorkTimezone()) {
                    dataFlex.push(self.toModelOtherFlowColumnSetting(dataModelFlex.toDto()));
                }
                self.dataSourceFlex(dataFlex);
            }
            
            // mode fixed
            if (self.isFixedMode()) {
                var dataFixed: any[] = [];
                for (var dataModelFixed of self.mainSettingModel.fixedWorkSetting.offdayWorkTimezone.lstWorkTimezone()) {
                    dataFixed.push(self.toModelOtherFlowColumnSetting(dataModelFixed.toDto()));
                }
                self.dataSourceFixed(dataFixed);
            }
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
        private toModelOtherFlowColumnSetting(dataDTO: HDWorkTimeSheetSettingDto): any {
            return {
                timezone: ko.observable({ startTime: dataDTO.timezone.start, endTime: dataDTO.timezone.end }),
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
        private toModelOtherFlowDto(dataModel: any, worktimeNo: number): HDWorkTimeSheetSettingDto {
            var rounding: TimeRoundingSettingDto = {
                roundingTime: dataModel.roundingTime(),
                rounding: dataModel.rounding(),
            };
            var timezone: TimeZoneRoundingDto = {
                rounding: rounding,
                start: dataModel.timezone().startTime,
                end: dataModel.timezone().endTime,
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
                    defaultValue: ko.observable({ startTime: 0, endTime: 0 }),
                    width: 250,
                    template: `<div class= "fixtable" data-bind="ntsTimeRangeEditor: { 
                        startConstraint: 'TimeWithDayAttr', endConstraint: 'TimeWithDayAttr',
                        startTimeNameId: '#[KMK003_163]', endTimeNameId: '#[KMK003_164]',
                        required: true, enable: true, inputFormat: 'time', paramId: 'KMK003_90'}"/>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_77"),
                    key: "inLegalBreakFrameNo",
                    dataSource: self.lstWorkDayOffFrame,
                    defaultValue: ko.observable(1),
                    width: 120,
                    template: `<div class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'workdayoffFrNo',
                                    visibleItemsCount: 10,
                                    optionsText: 'workdayoffFrName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'workdayoffFrName', length: 12 }]}">
                                </div>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_78"),
                    key: "outLegalBreakFrameNo",
                    dataSource: self.lstWorkDayOffFrame,
                    defaultValue: ko.observable(1),
                    width: 120,
                    template: `<div class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'workdayoffFrNo',
                                    visibleItemsCount: 10,
                                    optionsText: 'workdayoffFrName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'workdayoffFrName', length: 12 }]}">
                                </div>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_79"),
                    key: "outLegalPubHolFrameNo",
                    defaultValue: ko.observable(1),
                    dataSource: self.lstWorkDayOffFrame,
                    width: 180,
                    template: `<div class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'workdayoffFrNo',
                                    visibleItemsCount: 10,
                                    optionsText: 'workdayoffFrName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'workdayoffFrName', length: 12 }]}">
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
                                    visibleItemsCount: 8,
                                    optionsText: 'localizedName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'localizedName', length: 10 }]}">
                                </div>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_57"),
                    key: "rounding",
                    isRoudingColumn: true,
                    unitAttrName: 'roundingTime',
                    dataSource: self.settingEnum.rounding,
                    defaultValue: ko.observable(0),
                    width: 150,
                    template: `<div class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'value',
                                    visibleItemsCount: 3,
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
                    dataSource: self.lstWorkDayOffFrame,
                    defaultValue: ko.observable(1),
                    width: 120,
                    template: `<div class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'workdayoffFrNo',
                                    visibleItemsCount: 10,
                                    optionsText: 'workdayoffFrName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'workdayoffFrName', length: 12 }]}">
                                </div>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_78"),
                    key: "outLegalBreakFrameNo",
                    dataSource: self.lstWorkDayOffFrame,
                    defaultValue: ko.observable(1),
                    width: 120,
                    template: `<div class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'workdayoffFrNo',
                                    visibleItemsCount: 10,
                                    optionsText: 'workdayoffFrName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'workdayoffFrName', length: 12 }]}">
                                </div>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_79"),
                    key: "outLegalPubHolFrameNo",
                    dataSource: self.lstWorkDayOffFrame,
                    defaultValue: ko.observable(1),
                    width: 180,
                    template: `<div class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'workdayoffFrNo',
                                    visibleItemsCount: 10,
                                    optionsText: 'workdayoffFrName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'workdayoffFrName', length: 12 }]}">
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
                                    visibleItemsCount: 8,
                                    optionsText: 'localizedName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'localizedName', length: 10 }]}">
                                </div>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_57"),
                    key: "rounding",
                    isRoudingColumn: true,
                    unitAttrName: 'roundingTime',
                    dataSource: self.settingEnum.rounding,
                    defaultValue: ko.observable(0),
                    width: 150,
                    template: `<div class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'value',
                                    visibleItemsCount: 3,
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
        code: number;
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
            var isLoading: KnockoutObservable<boolean> = input.isLoading;
            let screenModel = new ScreenModel(settingEnum, mainSettingModel, isLoading);
            nts.uk.at.view.kmk003.a6.service.findAllWorkDayoffFrame().done(function(data){
                screenModel.lstWorkDayOffFrame = data;
                screenModel.initDataModel();
                $(element).load(webserviceLocator, function() {
                    ko.cleanNode($(element)[0]);
                    ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                });
            });
            
        }

    }
    ko.bindingHandlers['ntsKMK003A6'] = new KMK003A6BindingHandler();

}
