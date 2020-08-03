module nts.uk.at.view.kdl005.a {
    export module viewmodel {
        export class ScreenModel {
            //Grid data
            listComponentOption: any;
            selectedCode: KnockoutObservable<string> = ko.observable('');
            multiSelectedCode: KnockoutObservableArray<string>;
            isShowAlreadySet: KnockoutObservable<boolean>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            isDialog: KnockoutObservable<boolean>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            isMultiSelect: KnockoutObservable<boolean>;
            isShowWorkPlaceName: KnockoutObservable<boolean>;
            isShowSelectAllButton: KnockoutObservable<boolean>;
            employeeList: KnockoutObservableArray<UnitModel>;

            legendOptions: any;
            kdl005Data: any;
            employeeInfo: KnockoutObservable<string>;

            isManagementSection: KnockoutObservable<boolean> = ko.observable(true);
            dataItems: KnockoutObservableArray<any>;

            value01: KnockoutObservable<string> = ko.observable("");
            value02: KnockoutObservable<string> = ko.observable("");
            hint02: KnockoutObservable<string> = ko.observable("");
            value03: KnockoutObservable<string> = ko.observable("");
            hint03: KnockoutObservable<string> = ko.observable("");
            value04: KnockoutObservable<string> = ko.observable("");
            hint04: KnockoutObservable<string> = ko.observable("");
            expirationDateText: KnockoutObservable<string> = ko.observable("");

            constructor() {
                var self = this;

                self.kdl005Data = nts.uk.ui.windows.getShared("KDL005_DATA");

                self.employeeInfo = ko.observable("");

                self.dataItems = ko.observableArray([]);

                this.legendOptions = {
                    items: [
                        { labelText: nts.uk.resource.getText("KDL005_20") },
                        { labelText: nts.uk.resource.getText("KDL005_22") }
                    ]
                };

                service.getEmployeeList(self.kdl005Data).done(function(data: any) {
                    if(data.employeeBasicInfo.length > 1) {
                        self.selectedCode.subscribe(function(value) {
                            let itemData: any = _.find(data.employeeBasicInfo, ['employeeCode', value]);
                            self.onSelectEmployee(
                                itemData.employeeId,
                                self.kdl005Data.baseDate,
                                itemData.employeeCode,
                                itemData.businessName,
                            );
                        });

                        // self.baseDate = ko.observable(new Date());
                        self.selectedCode(data.employeeBasicInfo[0].employeeCode);
                        self.multiSelectedCode = ko.observableArray([]);
                        self.isShowAlreadySet = ko.observable(false);
                        self.alreadySettingList = ko.observableArray([
                            {code: '1', isAlreadySetting: true},
                            {code: '2', isAlreadySetting: true}
                        ]);
                        self.isDialog = ko.observable(false);
                        self.isShowNoSelectRow = ko.observable(false);
                        self.isMultiSelect = ko.observable(false);
                        self.isShowWorkPlaceName = ko.observable(false);
                        self.isShowSelectAllButton = ko.observable(false);
                        self.employeeList = ko.observableArray<UnitModel>(_.map(data.employeeBasicInfo,
                            (x: any) => {
                                return {code:x.employeeCode ,name:x.businessName};
                            }));
                        self.listComponentOption = {
                            isShowAlreadySet: self.isShowAlreadySet(),
                            isMultiSelect: self.isMultiSelect(),
                            listType: ListType.EMPLOYEE,
                            employeeInputList: self.employeeList,
                            selectType: SelectType.SELECT_BY_SELECTED_CODE,
                            selectedCode: self.selectedCode,
                            isDialog: self.isDialog(),
                            isShowNoSelectRow: self.isShowNoSelectRow(),
                            alreadySettingList: self.alreadySettingList,
                            isShowWorkPlaceName: self.isShowWorkPlaceName(),
                            isShowSelectAllButton: self.isShowSelectAllButton(),
                            maxRows: 12
                        };


                        $('#component-items-list').ntsListComponent(self.listComponentOption)
                        .done(() => {
                            $('#component-items-list').focusComponent();
                            // Employment List
                            self.employeeList($('#component-items-list').getDataList());
                        });

                        $("#date-fixed-table").ntsFixedTable({ height: 155 });
                    } else if(data.employeeBasicInfo.length == 1) {
                        // self.employeeInfo(nts.uk.resource.getText("KDL009_25", [data.employeeBasicInfo[0].employeeCode, data.employeeBasicInfo[0].businessName]));

                        self.onSelectEmployee(
                            data.employeeBasicInfo[0].employeeId,
                            self.kdl005Data.baseDate,
                            data.employeeBasicInfo[0].employeeCode,
                            data.employeeBasicInfo[0].businessName,
                        );
                        $("#date-fixed-table").ntsFixedTable({ height: 155 });
                    } else {
                        self.employeeInfo(nts.uk.resource.getText("KDL009_25", ["", ""]));
                        $("#date-fixed-table").ntsFixedTable({ height: 155 });
                    }
                });
            }

            // 社員リストの先頭を選択
            onSelectEmployee(id: string, baseDate: any, employeeCode: string, employeeName: string) {
                const self = this;
                // Show employee name
                self.employeeInfo(nts.uk.resource.getText("KDL009_25", [employeeCode, employeeName]));
                // Get employee data
                service.getDetailsConfirm(id, baseDate)
                    .done(function(data) {
                        if (data.deadLineDetails && data.deadLineDetails.isManaged) {
                            self.expirationDateText(ExpirationDate[data.deadLineDetails.expirationTime]);
                        }
                        self.bindTimeData(data);
                        self.bindSummaryData(data);
                        self.isManagementSection(data.isManagementSection);
                    });
            }

            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                dfd.resolve();

                return dfd.promise();
            }

            private bindTimeData(data: AcquisitionNumberRestDayDto) {
                let self = this;

                self.dataItems.removeAll();

                // Convert to list item
                ko.utils.arrayPushAll(self.dataItems, self.convertDetailToItem(data.listRemainNumberDetail, data.listPegManagement));
            }

            bindSummaryData(data: any) {
                const self = this;
                const numberFormat = new nts.uk.ui.option.NumberEditorOption({ decimallength: 1 });

                self.value01(nts.uk.resource.getText("KDL005_27", [nts.uk.ntsNumber.formatNumber(data.carryForwardDay, numberFormat)]));
                self.value02(nts.uk.resource.getText("KDL005_27", [nts.uk.ntsNumber.formatNumber(data.occurrenceDay, numberFormat)]));
                self.hint02(nts.uk.resource.getText("KDL005_33", [nts.uk.ntsNumber.formatNumber(data.scheduleOccurrencedDay, numberFormat)]));
                self.value03(nts.uk.resource.getText("KDL005_27", [nts.uk.ntsNumber.formatNumber(data.usageDay, numberFormat)]));
                self.hint03(nts.uk.resource.getText("KDL005_34", [nts.uk.ntsNumber.formatNumber(data.scheduledUsageDay, numberFormat)]));
                self.value04(nts.uk.resource.getText("KDL005_27", [nts.uk.ntsNumber.formatNumber(data.remainingDay, numberFormat)]));
                self.hint04(nts.uk.resource.getText("KDL005_35", [nts.uk.ntsNumber.formatNumber(data.scheduledRemainingDay, numberFormat)]));
                // if (data.totalInfor != null) {
                //     self.value01(data.totalInfor.carryForwardDays + nts.uk.resource.getText("KDL005_27"));
                // } else {
                //     self.value01(nts.uk.resource.getText("KDL005_27", ["0"]));
                // }
                // if (data.breakDay != null) {

                //     self.value02(data.breakDay.occurrenceDays + nts.uk.resource.getText("KDL005_27"));
                //     self.value03(data.breakDay.useDays + nts.uk.resource.getText("KDL005_27"));
                //     self.value04(data.breakDay.remainDays + nts.uk.resource.getText("KDL005_27"));
                // } else {
                //     self.value02(nts.uk.resource.getText("KDL005_27", ["0"]));
                //     self.value03(nts.uk.resource.getText("KDL005_27", ["0"]));
                //     self.value04(nts.uk.resource.getText("KDL005_27", ["0"]));
                // }
            }

            cancel() {
                nts.uk.ui.windows.close();
            }

            private convertDetailToItem(listDetail: RemainNumberDetailDto[], listPeg: PegManagementDto[]): DataItems[] {
                const self = this;
                const listItem: DataItems[] = [];
                const mapOccurenceDate: Map<String, any[]> = {};
                const mapUsageDate: Map<String, any[]> = {};
                for (const itemPeg of listPeg) {
                    let listOccurenceDate = mapOccurenceDate[itemPeg.occurrenceDate];
                    if (listOccurenceDate) {
                        listOccurenceDate.push(itemPeg);
                    } else {
                        listOccurenceDate = [itemPeg];
                    }
                    mapOccurenceDate[itemPeg.occurrenceDate] = listOccurenceDate;
                    let listUsageDate = mapUsageDate[itemPeg.usageDate];
                    if (listUsageDate) {
                        listUsageDate.push(itemPeg);
                    } else {
                        listUsageDate = [itemPeg];
                    }
                    mapUsageDate[itemPeg.usageDate] = listUsageDate;
                }

                // Mapping from list detail + list peg to list combined item
                let item: DataItems = undefined;
                for (const itemDetail of listDetail) {
                    if (itemDetail.occurrenceDate) {
                        let listOccurenceDate: any[] = mapOccurenceDate[itemDetail.occurrenceDate];
                        if (listOccurenceDate) {
                            // Combined records
                            if (item) {
                                item.listOccurrence.push(self.convertDetailDtoToModel(itemDetail));
                            } else {
                                item = new DataItems({
                                    isMultiOccurrence: false,
                                    isMultiDigestion: false,
                                    listOccurrence: [self.convertDetailDtoToModel(itemDetail)],
                                    listDigestion: [],
                                    singleRowDetail: self.convertDetailDtoToModel(itemDetail),
                                });
                            }
                        } else {
                            // Single record
                            listItem.push(new DataItems({
                                isMultiOccurrence: false,
                                isMultiDigestion: false,
                                singleRowDetail: self.convertDetailDtoToModel(itemDetail),
                            }));
                        }
                    } else if (itemDetail.digestionDate) {
                        let listDigestionDate: any[] = mapUsageDate[itemDetail.digestionDate];
                        if (listDigestionDate) {
                            // Combined records
                            if (item) {
                                item.listDigestion.push(self.convertDetailDtoToModel(itemDetail));
                            } else {
                                item = new DataItems({
                                    isMultiOccurrence: false,
                                    isMultiDigestion: false,
                                    listOccurrence: [],
                                    listDigestion: [self.convertDetailDtoToModel(itemDetail)],
                                    singleRowDetail: self.convertDetailDtoToModel(itemDetail),
                                });
                            }
                        } else {
                            // Single record
                            listItem.push(new DataItems({
                                isMultiOccurrence: false,
                                isMultiDigestion: false,
                                singleRowDetail: self.convertDetailDtoToModel(itemDetail),
                            }));
                        }
                    }
                    // Check if item is complete
                    if (item) {
                        if ((item.listOccurrence.length === 1 && item.listDigestion.length === 2)
                            || (item.listOccurrence.length === 2 && item.listDigestion.length === 1)) {
                            item.isMultiDigestion = (item.listDigestion.length === 2);
                            item.isMultiOccurrence = (item.listOccurrence.length === 2);
                            listItem.push(item);
                            item = undefined;
                        } else if (item.listDigestion.length === 1 && item.listOccurrence.length === 1) {
                            const occurrenceDate = item.listOccurrence[0];
                            const digestionDate = item.listDigestion[0];
                            let listOccurenceDate: any[] = mapOccurenceDate[occurrenceDate.occurrenceDate];
                            let listDigestionDate: any[] = mapUsageDate[digestionDate.digestionDate];
                            if (listOccurenceDate && listOccurenceDate.length === 1 && listDigestionDate && listDigestionDate.length === 1) {
                                (<any>Object).assign(item.singleRowDetail, {
                                    expirationDate: occurrenceDate.expirationDate,
                                    expirationDateText: occurrenceDate.expirationDateText,
                                    occurrenceNumber: occurrenceDate.occurrenceNumber,
                                    occurrenceDate: occurrenceDate.occurrenceDate,
                                    occurrenceHour: occurrenceDate.occurrenceHour,
                                    occurrenceDateText: occurrenceDate.occurrenceDateText,
                                    occurrenceHourText: occurrenceDate.occurrenceHourText,
                                    occurrenceNumberText: occurrenceDate.occurrenceNumberText,
                                    digestionNumber: digestionDate.digestionNumber,
                                    digestionDate: digestionDate.digestionDate,
                                    digestionHour: digestionDate.digestionHour,
                                    digestionDateText: digestionDate.digestionDateText,
                                    digestionHourText: digestionDate.digestionHourText,
                                    digestionNumberText: digestionDate.digestionNumberText,
                                });
                                listItem.push(item);
                                item = undefined;
                            }
                        }
                    }
                }
                return listItem;
            }

            private convertDetailDtoToModel(item: RemainNumberDetailDto): RemainNumberDetailModel {
                let digestionDateText: string = item.digestionDate ? (nts.uk.time as any).applyFormat("Short_YMDW", [item.digestionDate]) : '';
                let occurrenceDateText: string = item.occurrenceDate ? (nts.uk.time as any).applyFormat("Short_YMDW", [item.occurrenceDate]) : '';
                // 代休残数.休出代休残数詳細.管理データ状態区分をチェック
                if ([2, 3].indexOf(item.managementDataStatus) !== -1) {
                    occurrenceDateText = occurrenceDateText ? nts.uk.resource.getText("KDL005_36", [occurrenceDateText]) : '';
                }
                let expirationDateText: string = item.expirationDate ? (nts.uk.time as any).applyFormat("Short_YMDW", [item.expirationDate]) : '';
                // 代休残数.休出代休残数詳細.当月で期限切れをチェック
                if (item.expiredInCurrentMonth) {
                    expirationDateText = expirationDateText ? nts.uk.resource.getText("KDL005_38", [expirationDateText]) : '';
                } else {
                    expirationDateText = expirationDateText ? nts.uk.resource.getText("KDL005_37", [expirationDateText]) : '';
                }
                // 「日数」の場合
                let digestionNumberText = '';
                if (item.digestionNumber === 0.5) {
                    digestionNumberText = nts.uk.resource.getText("KDL005_27", [item.digestionNumber]);
                }
                let occurrenceNumberText = '';
                if (item.occurrenceNumber === 0.5) {
                    occurrenceNumberText = nts.uk.resource.getText("KDL005_27", [item.occurrenceNumber]);
                }
                return new RemainNumberDetailModel({
                    expirationDate: item.expirationDate,
                    expirationDateText: expirationDateText,
                    digestionNumber: item.digestionNumber,
                    digestionDate: item.digestionDate,
                    digestionHour: item.digestionHour ? item.digestionHour.toString() : '',
                    digestionNumberText: digestionNumberText,
                    digestionDateText: digestionDateText,
                    digestionHourText: item.digestionHour ? item.digestionHour.toString() : '',
                    occurrenceNumber: item.occurrenceNumber,
                    occurrenceDate: item.occurrenceDate,
                    occurrenceHour: item.occurrenceHour ? item.occurrenceHour.toString() : '',
                    occurrenceNumberText: occurrenceNumberText,
                    occurrenceDateText: occurrenceDateText,
                    occurrenceHourText: item.occurrenceHour ? item.occurrenceHour.toString() : '',
                });
            }

        }

        export enum ExpirationDate {
            "当月",//0
            "無期限",//1
            "年度末クリア",//2
            "1ヶ月",//3
            "2ヶ月",//4
            "3ヶ月",//5
            "4ヶ月",//6
            "5ヶ月",//7
            "6ヶ月",//8
            "7ヶ月",//9
            "8ヶ月",//10
            "9ヶ月",//11
            "10ヶ月",//12
            "11ヶ月",//13
            "1年",//14
        }

        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }

        export interface UnitModel {
            code: string;
            name?: string;
            workplaceName?: string;
            isAlreadySetting?: boolean;
        }

        export class SelectType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }

        export interface UnitAlreadySettingModel {
            code: string;
            isAlreadySetting: boolean;
        }

        class RemainNumberDetailModel {
            expirationDate: string;
            expirationDateText: string;
            digestionNumber: number;
            digestionDate: string;
            digestionHour: string;
            digestionNumberText: string;
            digestionDateText: string;
            digestionHourText: string;
            occurrenceNumber: number;
            occurrenceDate: string;
            occurrenceHour: string;
            occurrenceNumberText: string;
            occurrenceDateText: string;
            occurrenceHourText: string;

            constructor(init?: Partial<RemainNumberDetailModel>) {
                (<any>Object).assign(this, init);
            }
        }

        class DataItems {
            isMultiOccurrence: boolean;
            isMultiDigestion: boolean;
            listOccurrence: RemainNumberDetailModel[];
            listDigestion: RemainNumberDetailModel[];
            singleRowDetail: RemainNumberDetailModel;

            constructor(init?: Partial<DataItems>) {
                (<any>Object).assign(this, init);
            }

            public isSingleRow() {
                return !this.isMultiOccurrence && !this.isMultiDigestion;
            }
        }
    }
}