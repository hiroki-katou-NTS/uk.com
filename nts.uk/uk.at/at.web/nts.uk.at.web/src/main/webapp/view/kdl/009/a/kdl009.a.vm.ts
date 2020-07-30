module nts.uk.at.view.kdl009.a {
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
            kdl009Data: any;
            employeeInfo: KnockoutObservable<string>;

            isManagementSection: KnockoutObservable<boolean> = ko.observable(true);
            dataItems: KnockoutObservableArray<DataItems>;

            value01: KnockoutObservable<string> = ko.observable("");
            value02: KnockoutObservable<string> = ko.observable("");
            hint02: KnockoutObservable<string> = ko.observable("");
            value03: KnockoutObservable<string> = ko.observable("");
            hint03: KnockoutObservable<string> = ko.observable("");
            value04: KnockoutObservable<string> = ko.observable("");
            hint04: KnockoutObservable<string> = ko.observable("");
            expirationDateText: KnockoutObservable<string> = ko.observable("");

            constructor() {
                const self = this;

                self.kdl009Data = nts.uk.ui.windows.getShared("KDL009_DATA");
                self.employeeInfo = ko.observable("");
                self.dataItems = ko.observableArray([]);
                self.legendOptions = {
                    items: [
                        { labelText: nts.uk.resource.getText("KDL009_18") + " : " + nts.uk.resource.getText("KDL009_19") }
                    ]
                };

                service.getEmployee(self.kdl009Data)
                    .done((data: any) => {
                        if (data.employeeBasicInfo.length > 1) {
                            self.selectedCode.subscribe((value) => {
                                const itemSelected: any = _.find(data.employeeBasicInfo, ['employeeCode', value]);
                                self.onEmployeeSelect(
                                    data.baseDate,
                                    itemSelected.employeeId,
                                    itemSelected.employeeCode,
                                    itemSelected.businessName,
                                );
                            });
                            self.bindEmpList(data.employeeBasicInfo);
                        } else if (data.employeeBasicInfo.length == 1) {
                            const itemSelected: any = data.employeeBasicInfo[0];
                            self.onEmployeeSelect(
                                data.baseDate,
                                itemSelected.employeeId,
                                itemSelected.employeeCode,
                                itemSelected.businessName,
                            );
                        } else {
                            self.employeeInfo(nts.uk.resource.getText("KDL009_25", ["", ""]));
                            nts.uk.ui.dialog.alertError({ messageId: "Msg_918" });
                        }
                        $("#date-fixed-table").ntsFixedTable({ height: 155 });
                    })
                    .fail(self.onError);
            }

            // On select employee
            private onEmployeeSelect(baseDate: any, employeeId: string, employeeCode: string, employeeName: string) {
                const self = this;
                self.employeeInfo(nts.uk.resource.getText("KDL009_25", [employeeCode, employeeName]));
                service.getAcquisitionNumberRestDays(employeeId, baseDate)
                    .done((data) => {
                        self.expirationDateText(ExpirationDate[data.expiredDay]);
                        self.bindTimeData(data);
                        self.bindSummaryData(data);
                        self.isManagementSection(data.isManagementSection);
                    })
                    .fail(self.onError);
            }

            // On error
            private onError(res: any) {
                nts.uk.ui.dialog.alertError({ messageId: res.messageId });
            }

            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                dfd.resolve();

                return dfd.promise();
            }

            bindEmpList(data: any) {
                const self = this;

                // self.baseDate = ko.observable(new Date());
                self.selectedCode(data[0].employeeCode);
                self.multiSelectedCode = ko.observableArray([]);
                self.isShowAlreadySet = ko.observable(false);
                self.alreadySettingList = ko.observableArray([
                    { code: '1', isAlreadySetting: true },
                    { code: '2', isAlreadySetting: true }
                ]);
                self.isDialog = ko.observable(false);
                self.isShowNoSelectRow = ko.observable(false);
                self.isMultiSelect = ko.observable(false);
                self.isShowWorkPlaceName = ko.observable(false);
                self.isShowSelectAllButton = ko.observable(false);
                this.employeeList = ko.observableArray<UnitModel>(_.map(data, (x: any) => ({ code: x.employeeCode, name: x.businessName })));
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

                $('#component-items-list').ntsListComponent(self.listComponentOption);
            }

            bindTimeData(data: AcquisitionNumberRestDayDto) {
                const self = this;
                self.dataItems.removeAll();
                if (data.listRemainNumberDetail.length > 0) {
                    data.listRemainNumberDetail.forEach(item => {
                        let digestionDate: string = item.digestionDate ? (nts.uk.time as any).applyFormat("Short_YMDW", [item.digestionDate]) : '';
                        let occurrenceDate: string = item.occurrenceDate ? (nts.uk.time as any).applyFormat("Short_YMDW", [item.occurrenceDate]) : '';
                        // 代休残数.休出代休残数詳細.管理データ状態区分をチェック
                        if ([2, 3].indexOf(item.managementDataStatus) !== -1) {
                            occurrenceDate = occurrenceDate ? nts.uk.resource.getText("KDL005_36", [occurrenceDate]) : '';
                        }
                        let expirationDate: string = item.expirationDate ? (nts.uk.time as any).applyFormat("Short_YMDW", [item.expirationDate]) : '';
                        // 代休残数.休出代休残数詳細.当月で期限切れをチェック
                        if (item.isExpiredInCurrentMonth) {
                            expirationDate = expirationDate ? nts.uk.resource.getText("KDL005_38", [expirationDate]) : '';
                        } else {
                            expirationDate = expirationDate ? nts.uk.resource.getText("KDL005_37", [expirationDate]) : '';
                        }
                        // 「日数」の場合
                        let digestionNumber = '';
                        if (item.digestionNumber === 0.5) {
                            digestionNumber = nts.uk.resource.getText("KDL005_27", [item.digestionNumber]);
                        }
                        let occurrenceNumber = '';
                        if (item.occurrenceNumber === 0.5) {
                            occurrenceNumber = nts.uk.resource.getText("KDL005_27", [item.occurrenceNumber]);
                        }
                        const dataItem: DataItems = new DataItems({
                            expirationDate: expirationDate,
                            digestionNumber: digestionNumber,
                            digestionDate: digestionDate,
                            digestionHour: item.digestionHour ? item.digestionHour.toString() : '',
                            occurrenceNumber: occurrenceNumber,
                            occurrenceDate: occurrenceDate,
                            occurrenceHour: item.occurrenceHour ? item.occurrenceHour.toString() : '',
                        });
                        self.dataItems.push(dataItem);
                    });

                    // _.each(, function (item) {
                    //     let isHalfDay = false;
                    //     let issueDate = "";
                    //     let holidayDateTop = "";
                    //     let holidayDateBot = "";
                    //     let expirationDate = "";
                    //     let occurrenceDays1 = "";
                    //     let occurrenceDays2Top = "";
                    //     let occurrenceDays2Bot = "";
                    //     if (item.recHisData != null) {
                    //         if (!item.recHisData.recDate.unknownDate) {
                    //             if (item.recHisData.dataAtr == 3) {
                    //                 issueDate = nts.uk.resource.getText("KDL009_13", [
                    //                     (nts.uk.time as any).applyFormat("Short_YMDW", [item.recHisData.recDate.dayoffDate])
                    //                 ]);
                    //             } else {
                    //                 issueDate = (nts.uk.time as any).applyFormat("Short_YMDW", [item.recHisData.recDate.dayoffDate]);
                    //             }
                    //         } else {
                    //             issueDate = "";
                    //         }

                    //         if (item.recHisData.occurrenceDays == 0.5) {
                    //             occurrenceDays1 = nts.uk.resource.getText("KDL009_14", [item.recHisData.occurrenceDays]);
                    //         }

                    //         if (item.recHisData.expirationDate != null) {
                    //             expirationDate = (nts.uk.time as any).applyFormat("Short_YMDW", [item.recHisData.expirationDate]);
                    //         } else {
                    //             expirationDate = "";
                    //         }
                    //     }

                    //     if (item.absHisData != null) {
                    //         if (item.absHisData.absDate.unknownDate) {
                    //             holidayDateTop = nts.uk.resource.getText("KDL009_11");
                    //         } else {
                    //             if (item.absHisData.createAtr == 3) {
                    //                 holidayDateTop = nts.uk.resource.getText("KDL009_13", [
                    //                     (nts.uk.time as any).applyFormat("Short_YMDW", [item.absHisData.absDate.dayoffDate])
                    //                 ]);
                    //             } else {
                    //                 holidayDateTop = (nts.uk.time as any).applyFormat("Short_YMDW", [item.absHisData.absDate.dayoffDate]);
                    //             }
                    //         }

                    //         if (item.absHisData.requeiredDays == 0.5) {
                    //             occurrenceDays2Top = nts.uk.resource.getText("KDL009_14", [item.absHisData.requeiredDays]);
                    //         }

                    //         if (holidayDateTop === nts.uk.resource.getText("KDL009_11")) {
                    //             holidayDateTop = "";
                    //             occurrenceDays2Top = "";

                    //         }
                    //     }

                    //     if (item.recHisData.dataAtr == 1) {
                    //         if (holidayDateTop === "" && occurrenceDays2Top === "") {
                    //             isHalfDay = false;
                    //         } else {
                    //             isHalfDay = true;
                    //         }
                    //     }

                    //     if (issueDate == "" && holidayDateTop == "" && expirationDate != "") {
                    //         issueDate = nts.uk.resource.getText("KDL009_11");
                    //     }

                    //     let temp = new DataItems(issueDate, holidayDateTop, holidayDateBot, expirationDate, occurrenceDays1, occurrenceDays2Top, occurrenceDays2Bot, isHalfDay);

                    // });
                }
            }

            bindSummaryData(data: AcquisitionNumberRestDayDto) {
                const self = this;
                const numberFormat = new nts.uk.ui.option.NumberEditorOption({ decimallength: 1 });
                self.value01(nts.uk.resource.getText("KDL005_27", [nts.uk.ntsNumber.formatNumber(data.carryForwardDay, numberFormat)]));
                self.value02(nts.uk.resource.getText("KDL005_27", [nts.uk.ntsNumber.formatNumber(data.occurrenceDay, numberFormat)]));
                self.hint02(nts.uk.resource.getText("KDL005_33", [nts.uk.ntsNumber.formatNumber(data.scheduleOccurrencedDay, numberFormat)]));
                self.value03(nts.uk.resource.getText("KDL005_27", [nts.uk.ntsNumber.formatNumber(data.usageDay, numberFormat)]));
                self.hint03(nts.uk.resource.getText("KDL005_34", [nts.uk.ntsNumber.formatNumber(data.scheduledUsageDay, numberFormat)]));
                self.value04(nts.uk.resource.getText("KDL005_27", [nts.uk.ntsNumber.formatNumber(data.remainingDay, numberFormat)]));
                self.hint04(nts.uk.resource.getText("KDL005_35", [nts.uk.ntsNumber.formatNumber(data.scheduledRemainingDay, numberFormat)]));
            }

            cancel() {
                nts.uk.ui.windows.close();
            }
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

        class DataItems {
            expirationDate: string;
            digestionNumber: string;
            digestionDate: string;
            digestionHour: string;
            occurrenceNumber: string;
            occurrenceDate: string;
            occurrenceHour: string;

            constructor(init?: Partial<DataItems>) {
                (<any>Object).assign(this, init);
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
    }
}