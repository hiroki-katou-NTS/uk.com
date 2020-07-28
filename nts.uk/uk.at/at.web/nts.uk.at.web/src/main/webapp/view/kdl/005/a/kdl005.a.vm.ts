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
                        self.employeeInfo(nts.uk.resource.getText("KDL009_25", [data.employeeBasicInfo[0].employeeCode, data.employeeBasicInfo[0].businessName]));

                        //data.employeeBasicInfo[0].employeeId
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
                    });
            }

            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                dfd.resolve();

                return dfd.promise();
            }

            bindTimeData(data: any) {
                let self = this;


                self.dataItems.removeAll();

                if(data.lstHistory != null && data.lstHistory.length >= 1) {
                    _.each(data.lstHistory, function (item) {

                        let leaveDate = "";
                        let dayOffDateTop = "";
                        let dayOffDateBot = "";
                        let duedateHoliday = "";
                        let occurrenceDays1 = "";
                        let occurrenceDays2Top = "";
                        let occurrenceDays2Bot = "";
                        let isHalfDay = false;

                        if(item.breakHis != null) {
                            if(!item.breakHis.chkDisappeared) {
                                let dayoffDateStr = item.breakHis.breakDate.dayoffDate != null ? item.breakHis.breakDate.dayoffDate : "";

                                if(dayoffDateStr !== "") {
                                    if(item.breakHis.mngAtr == 2 || item.breakHis.mngAtr == 3) {
                                        leaveDate = nts.uk.resource.getText("KDL005_19", [nts.uk.time.applyFormat("Short_YMDW", [dayoffDateStr])]);
                                    } else {
                                        leaveDate = nts.uk.time.applyFormat("Short_YMDW", [dayoffDateStr]);
                                    }
                                } else {
                                    leaveDate = "";
                                }
                            } else {
                                leaveDate = "";
                            }

                            if(Number(item.breakHis.occurrenceDays) == 0 || Number(item.breakHis.occurrenceDays) == 1) {
                                occurrenceDays1 = "";
                            } else {
                                occurrenceDays1 = item.breakHis.occurrenceDays + nts.uk.resource.getText("KDL005_27");
                            }

                            if(item.breakHis.expirationDate != null && item.isManaged == 1) {
                                duedateHoliday = nts.uk.time.applyFormat("Short_YMDW", [item.breakHis.expirationDate]);
                            } else {
                                duedateHoliday = "";
                            }
                        }

                        if(item.dayOffHis != null) {
                            if(item.dayOffHis.createAtr == 2 || item.dayOffHis.createAtr == 3) {
                                if(item.dayOffHis.dayOffDate.dayoffDate != null) {
                                    dayOffDateTop = nts.uk.resource.getText("KDL005_19", [item.dayOffHis.dayOffDate.dayoffDate]);
                                } else {
                                    dayOffDateTop = "";
                                }
                            } else {
                                if(item.dayOffHis.dayOffDate.dayoffDate != null) {
                                    dayOffDateTop = nts.uk.time.applyFormat("Short_YMDW", [item.dayOffHis.dayOffDate.dayoffDate]);
                                } else {
                                    dayOffDateTop = "";
                                }
                            }

                            if(Number(item.dayOffHis.requeiredDays) == 0 || Number(item.dayOffHis.requeiredDays) == 1) {
                                occurrenceDays2Top = "";
                            } else {
                                occurrenceDays2Top = item.dayOffHis.requeiredDays + nts.uk.resource.getText("KDL005_27");
                            }
                        }

                        if(item.breakHis.mngAtr == 0 && Number(item.dayOffHis.requeiredDays) % 1 !== 0) {
                            isHalfDay = true;
                        }

                        let temp = new DataItems(leaveDate, dayOffDateTop, dayOffDateBot, duedateHoliday, occurrenceDays1, occurrenceDays2Top, occurrenceDays2Bot, isHalfDay);

                        if(temp.leaveDate !== "" || temp.dayOffDateTop !== "" || temp.dayOffDateBot !== "" || temp.duedateHoliday !== ""
                                || temp.occurrenceDays1 !== "" || temp.occurrenceDays2Top !== "" || temp.occurrenceDays2Bot !== "") {
                            self.dataItems.push(temp);
                        }
                    });
                }
            }

            bindSummaryData(data: any) {
                var self = this;
                if (data.totalInfor != null) {
                    self.value01(data.totalInfor.carryForwardDays + nts.uk.resource.getText("KDL005_27"));
                } else {
                    self.value01(nts.uk.resource.getText("KDL005_27", ["0"]));
                }
                if (data.breakDay != null) {

                    self.value02(data.breakDay.occurrenceDays + nts.uk.resource.getText("KDL005_27"));
                    self.value03(data.breakDay.useDays + nts.uk.resource.getText("KDL005_27"));
                    self.value04(data.breakDay.remainDays + nts.uk.resource.getText("KDL005_27"));
                } else {

                    self.value02(nts.uk.resource.getText("KDL005_27", ["0"]));
                    self.value03(nts.uk.resource.getText("KDL005_27", ["0"]));
                    self.value04(nts.uk.resource.getText("KDL005_27", ["0"]));
                }
            }

            cancel() {
                nts.uk.ui.windows.close();
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

        class DataItems {
            leaveDate: string;
            dayOffDateTop: string;
            dayOffDateBot: string;
            duedateHoliday: string;
            occurrenceDays1: string;
            occurrenceDays2Top: string;
            occurrenceDays2Bot: string;
            isHalfDay: boolean;

            constructor(leaveDate: string, dayOffDateTop: string, dayOffDateBot: string, duedateHoliday: string, occurrenceDays1: string,
                    occurrenceDays2Top: string, occurrenceDays2Bot: string, isHalfDay: boolean) {
                this.leaveDate = leaveDate;
                this.dayOffDateTop = dayOffDateTop;
                this.dayOffDateBot = dayOffDateBot;
                this.duedateHoliday = duedateHoliday;
                this.occurrenceDays1 = occurrenceDays1;
                this.occurrenceDays2Top = occurrenceDays2Top;
                this.occurrenceDays2Bot = occurrenceDays2Bot;
                this.isHalfDay = isHalfDay;
            }
        }
    }
}