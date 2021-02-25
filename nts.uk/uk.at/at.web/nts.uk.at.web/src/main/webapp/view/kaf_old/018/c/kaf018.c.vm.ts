module nts.uk.at.view.kaf018_old.c.viewmodel {
    import text = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import formatDate = nts.uk.time.formatDate;
    import shareModel = kaf018_old.share.model;
    import block = nts.uk.ui.block;

    export class ScreenModel {
        legendOptions: any;
        closureId: string;
        closureName: string;
        processingYm: string;
        startDateFormat: string;
        endDateFormat: string;
        startDate: Date;
        endDate: Date;
        listWorkplace: Array<shareModel.ItemModel> = [];;
        selectedWplIndex: number;
        selectedWplId: KnockoutObservable<string>;
        selectedWplName: KnockoutObservable<string>;
        listEmpCd: Array<string>;
        listWkpId: KnockoutObservableArray<string> = ko.observableArray([]);

        enableNext: KnockoutObservable<boolean>;
        enablePre: KnockoutObservable<boolean>;

        listData: any;

        arrDay: shareModel.Time[] = [];
        dtPrev: KnockoutObservable<Date> = ko.observable(null);
        dtAft: KnockoutObservable<Date> = ko.observable(null);

        dataWkpSpecificDate: KnockoutObservableArray<any> = ko.observableArray([]);
        dataComSpecificDate: KnockoutObservableArray<any> = ko.observableArray([]);
        dataPublicHoliday: KnockoutObservableArray<any> = ko.observableArray([]);

        listApprovalEmployee: Array<ApprovalStatusEmployee> = [];
        listDailyStatus: Array<DailyStatusOut> = [];
        inputContent: any;
        dateFormat = "yyyy/MM/dd";
        constructor() {
            var self = this;
            this.legendOptions = {
                items: [
                    { cssClass: { className: 'bg-canceled-application', colorPropertyName: 'background-color' }, labelText: text("KAF018_87") },
                    { cssClass: { className: 'bg-updating-cell', colorPropertyName: 'background-color' }, labelText: text("KAF018_66") },
                    { cssClass: { className: 'bg-unapproved-application', colorPropertyName: 'background-color' }, labelText: text("KAF018_89") },
                    { cssClass: { className: 'bg-weekdays', colorPropertyName: 'background-color' }, labelText: text("KAF018_88") }
                ]
            };
            self.listData = [];
            self.selectedWplId = ko.observable('');
            self.selectedWplName = ko.observable('');
            self.enableNext = ko.observable(false);
            self.enablePre = ko.observable(false);
            window.onresize = function(event) {
            	$(".ex-body-leftmost").height(window.innerHeight - 320);
            	$(".ex-body-detail").height(window.innerHeight - 303);
            };
        }

        /**
         * 起動する
         */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.invisible();
            let params = getShared("KAF018C_PARAMS");
            if (params) {
                self.closureId = params.closureId;
                self.closureName = params.closureName;
                self.processingYm = params.processingYm;
                self.startDateFormat = formatDate(new Date(params.startDate), 'yyyy/MM/dd');
                self.endDateFormat = formatDate(new Date(params.endDate), 'yyyy/MM/dd');
                self.startDate = params.startDate;
                self.endDate = params.endDate;
                self.listWorkplace = params.listWorkplace;
                self.selectedWplIndex = params.selectedWplIndex;
                self.listEmpCd = params.listEmployeeCode;
                self.inputContent = params.inputContent;
            }
            self.dtPrev(new Date(self.startDateFormat));
            self.dtAft(new Date(self.endDateFormat));
            self.setArrDate();
            self.getWkpName();
            _.forEach(self.listWorkplace, function(item: shareModel.ItemModel) {
                self.listWkpId.push(item.code);
            });
            self.initExTable().done(() => {
                dfd.resolve();
            }).fail(() => {
                dfd.reject();
            });
            return dfd.promise();
        }


        getWkpName() {
            var self = this;
            self.enablePre(self.selectedWplIndex != 0)
            self.enableNext(self.selectedWplIndex != (self.listWorkplace.length - 1))

            let wkp: shareModel.ItemModel = self.listWorkplace[self.selectedWplIndex];
            self.selectedWplId(wkp.code);
            self.selectedWplName(wkp.name);
        }

        getStatusSymbol(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            let obj = {
                selectedWkpId: self.selectedWplId(),
                listWkpId: self.listWkpId(),
                startDate: self.startDate,
                endDate: self.endDate,
                listEmpCode: self.listEmpCd,
            };
            service.initApprovalSttByEmployee(obj).done(function(data: Array<ApprovalSttByEmpList>) {
                let lstData = _.sortBy(data, o => o.empName, 'asc');
                _.each(lstData, function(item) {
                    self.listApprovalEmployee.push(new ApprovalStatusEmployee(item.empId, item.startDate, item.endDate));
                })
                let lstApprovalSttByEmp = self.confirmDuplicateEmp(lstData);
                dfd.resolve(self.convertToEmpPerformance(lstApprovalSttByEmp));
            });
            return dfd.promise();
        }

        confirmDuplicateEmp(data: Array<ApprovalSttByEmpList>): Array<EmpApprovalPeriod> {
            let empIdTemp = "";
            let listEmp: Array<EmpApprovalPeriod> = [];
            _.each(data, function(item: ApprovalSttByEmpList) {
                let empPeriod: EmpPeriod = new EmpPeriod(item.startDate, item.endDate,
                    item.listDaily);
                if (empIdTemp == item.empId) {
                    let emp: EmpApprovalPeriod = _.find(listEmp, { empId: item.empId });
                    emp.listEmpPeriod.push(empPeriod);
                } else {
                    empIdTemp = item.empId
                    let listEmpPeriod: Array<EmpPeriod> = [];
                    listEmpPeriod.push(empPeriod);
                    let empDuplicate: EmpApprovalPeriod = new EmpApprovalPeriod(item.empId, item.empName, listEmpPeriod);
                    listEmp.push(empDuplicate);
                }
            });
            return listEmp;
        }

        convertToEmpPerformance(data: Array<EmpApprovalPeriod>): Array<DailyStatusOut> {
            let self = this;
            let lstDailyOut = Array<DailyStatusOut>();
            _.each(data, function(item) {
                let listDaily: Array<DailyStatus> = [];
                _.each(item.listEmpPeriod, function(empPeriod) {
                    let startDate = new Date(empPeriod.startDate.toString());
                    let endDate = new Date(empPeriod.endDate.toString());            
                    let currentDay = new Date(empPeriod.startDate.toString());
                    while (currentDay <= endDate) {
                        let objDaily = _.find(empPeriod.listDailyStt, { date: self.convertDate(currentDay) });
                        let stateSymbol = objDaily != null ? objDaily.stateSymbol : [];
                        let date = self.convertDate(currentDay);
                        listDaily.push(new DailyStatus(date, stateSymbol));
                        currentDay.setDate(currentDay.getDate() + 1);
                    }
                })
                lstDailyOut.push(new DailyStatusOut(item.empId, item.empName, listDaily));
            })
            return lstDailyOut;
        }

        convertDate(date: Date) {
            let self = this;
            return nts.uk.time.formatDate(date, self.dateFormat);
        }

        nextWkp() {
            var self = this;
            self.selectedWplIndex++;
            self.getWkpName();
            self.updateExTable();
        }

        preWkp() {
            var self = this;
            self.selectedWplIndex--;
            self.getWkpName();
            self.updateExTable();
        }

        setArrDate() {
            var self = this;
            let currentDay = new Date(self.dtPrev().toString());
            while (currentDay <= self.dtAft()) {
                self.arrDay.push(new shareModel.Time(currentDay));
                currentDay.setDate(currentDay.getDate() + 1);
            }
        };

        /**
         * Create exTable
         */
        initExTable(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.getStatusSymbol().done(function(data: any) {
                self.listDailyStatus = data;
                let sv1 = self.setColorForCellHeaderDetail();
                let sv2 = self.setSymbolForCellContentDetail(self.listDailyStatus);
                $.when(sv1, sv2).done(function(detailHeaderDeco) {
                    let initExTable = self.setFormatData(detailHeaderDeco, self.listDailyStatus);

                    new nts.uk.ui.exTable.ExTable($("#extable"), {
                        headerHeight: "46px", bodyRowHeight: "23px", bodyHeight: window.innerHeight - 320 + 'px',
                        horizontalSumBodyRowHeight: "0px",
                        areaResize: false,
                        remainSizes: false,
                        bodyHeightMode: "fixed",
                        windowXOccupation: 50,
                        windowYOccupation: 20,
                        showTooltipIfOverflow: true,
                        primaryTable: $("#extable")
                    })
                        .LeftmostHeader(initExTable.leftmostHeader).LeftmostContent(initExTable.leftmostContent)
                        .DetailHeader(initExTable.detailHeader).DetailContent(initExTable.detailContent)
                        .create();
                    dfd.resolve();
                });
            }).fail(() => {
                dfd.reject();
            })
            return dfd.promise();
        }

        /**
        * Update exTable
        */
        updateExTable() {
            let self = this;
            block.invisible();
            self.getStatusSymbol().done(function(data: any) {
                self.listDailyStatus = data;
                let sv1 = self.setColorForCellHeaderDetail();
                let sv2 = self.setSymbolForCellContentDetail(self.listDailyStatus);
                $.when(sv1, sv2).done(function(detailHeaderDeco) {
                    let initExTable = self.setFormatData(detailHeaderDeco, self.listDailyStatus);
                    $("#extable").exTable("updateTable", "leftmost", initExTable.leftmostHeader, initExTable.leftmostContent, true);
                    $("#extable").exTable("updateTable", "detail", initExTable.detailHeader, initExTable.detailContent, true);
                }).always(() => {
                    block.clear();
                })
            }).fail(() => {
                block.clear();
            })
        }

        setFormatData(detailHeaderDeco, listData) {
            var self = this;
            let leftmostColumns = [];
            let leftmostHeader = {};
            let leftmostContent = {};

            let detailHeaderColumns = [];
            let detailHeader = {};
            let detailContentColumns = [];
            let detailContent = {};

            //create leftMost Header and Content
            leftmostColumns = [
                {
                    headerText: text("KAF018_29"),
                    key: "empName",
                    width: "200px",
                    control: "link",
                    handler: function(rData, rowIdx, key) { self.goToD(rData); }
                }
            ];
            leftmostHeader = {
                columns: leftmostColumns,
                rowHeight: "46px",
                width: "200px"
            };
            leftmostContent = {
                columns: leftmostColumns,
                dataSource: listData,
                primaryKey: "sId"
            };

            // create detail Columns and detail Content Columns
            let currentDay = new Date(self.dtPrev().toString());
            let listResize: Array<ResizeColumn> = self.getResizeColumn(listData);
            _.each(listResize, function(item: ResizeColumn) {
                let time = new shareModel.Time(new Date(item.date));
                detailHeaderColumns.push({
                    key: "_" + time.yearMonthDay, 
                    width: item.width, 
                    headerText: time.day,
                    group:[{
                        key: "___" + time.yearMonthDay, 
                        width: item.width, 
                        headerText: time.weekDay
                    }]
                });
                detailContentColumns.push({
                    key: "__" + time.yearMonthDay, width: item.width
                });
                currentDay.setDate(currentDay.getDate() + 1);
            });
            //create Detail Header
            detailHeader = {
                columns: detailHeaderColumns,
                width: "1020px",
                features: [
                    {
                        name: "HeaderRowHeight",
                        rows: { 0: "23px", 1: "23px" }
                    },
                    {
                        name: "HeaderCellStyle",
                        decorator: detailHeaderDeco
                    }]
            };

            //create Detail Content
            detailContent = {
                columns: detailContentColumns,
                dataSource: listData,
                primaryKey: "empId"
            };
            return {
                leftmostHeader: leftmostHeader,
                leftmostContent: leftmostContent,
                detailHeader: detailHeader,
                detailContent: detailContent
            };
        }

        getDay(time: shareModel.Time): string {
            return time.day + "<br/>" + time.weekDay;
        }

        /**
         * Set color for cell header: 日付セル背景色文字色制御
         * 
         */
        setColorForCellHeaderDetail(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            let detailHeaderDeco = [];
            // getDataSpecDateAndHoliday always query to server
            $.when(self.getDataSpecDateAndHoliday()).done(() => {
                _.each(self.arrDay, (date) => {
                    let ymd = date.yearMonthDay;
                    let dateFormat = moment(date.yearMonthDay).format('YYYY/MM/DD');
                    if (_.includes(self.dataWkpSpecificDate(), dateFormat) || _.includes(self.dataComSpecificDate(), dateFormat)) {
                        detailHeaderDeco.push(new shareModel.CellColor("_" + ymd, undefined, "bg-schedule-specific-date"));
                        detailHeaderDeco.push(new shareModel.CellColor("___" + ymd, "1", "bg-schedule-specific-date"));
                    } else if (_.includes(self.dataPublicHoliday(), dateFormat)) {
                        detailHeaderDeco.push(new shareModel.CellColor("_" + ymd, undefined, "bg-schedule-sunday color-schedule-sunday"));
                        detailHeaderDeco.push(new shareModel.CellColor("___" + ymd, "1", "bg-schedule-sunday color-schedule-sunday"));
                    } else if (date.weekDay === '土') {
                        detailHeaderDeco.push(new shareModel.CellColor("_" + ymd, undefined, "bg-schedule-saturday color-schedule-saturday"));
                        detailHeaderDeco.push(new shareModel.CellColor("___" + ymd, "1", "bg-schedule-saturday color-schedule-saturday"));
                    } else if (date.weekDay === '日') {
                        detailHeaderDeco.push(new shareModel.CellColor("_" + ymd, undefined, "bg-schedule-sunday color-schedule-sunday"));
                        detailHeaderDeco.push(new shareModel.CellColor("___" + ymd, "1", "bg-schedule-sunday color-schedule-sunday"));
                    } else {
                        detailHeaderDeco.push(new shareModel.CellColor("_" + ymd, undefined, "bg-weekdays color-weekdays"));
                        detailHeaderDeco.push(new shareModel.CellColor("___" + ymd, "1", "bg-weekdays color-weekdays"));
                    }
                });
                dfd.resolve(detailHeaderDeco);
            });
            return dfd.promise();
        }

        //職場名
        displayWkp(): string {
            var self = this;
            return self.selectedWplName();
        }

        /**
         * Get data WkpSpecificDate, ComSpecificDate, PublicHoliday
         */
        getDataSpecDateAndHoliday(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred(),
                obj = {
                    workplaceId: self.selectedWplId(),
                    startDate: self.startDate,
                    endDate: self.endDate
                };
            service.getDataSpecDateAndHoliday(obj).done(function(data) {
                self.dataWkpSpecificDate(data.listWkpSpecificDate);
                self.dataComSpecificDate(data.listComSpecificDate);
                self.dataPublicHoliday(data.listPublicHoliday);
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }

        /**
         * Set symbol for cell detail
         * 
         */
        setSymbolForCellContentDetail(listData: Array<DailyStatusOut>): JQueryPromise<any> {
            var self = this, dfd = $.Deferred();

            _.each(listData, function(emp: DailyStatusOut) {
                let currentDay = new Date(self.dtPrev().toString());
                while (currentDay <= self.dtAft()) {
                    let time = new shareModel.Time(currentDay);
                    let key = "__" + time.yearMonthDay;
                    let curentDayConvert = nts.uk.time.formatDate(currentDay, 'yyyy/MM/dd');
                    let daily = _.find(emp.listDaily, { date: curentDayConvert });
                    if (daily != null) {
                        daily.stateSymbol = daily.stateSymbol.sort();
                        let valueSb = "";
                        _.each(daily.stateSymbol, function(sb) {
                            switch (sb) {
                                case 0: valueSb += "◎"; break;
                                case 1: valueSb += "〇"; break;
                                case 2: valueSb += "×"; break;
                                case 3: valueSb += "－"; break;
                            }
                        })
                        emp[key] = valueSb;
                    } else {
                        emp[key] = "";
                    }
                    currentDay.setDate(currentDay.getDate() + 1);
                }
            });
            dfd.resolve();
            return dfd.promise();
        }

        getResizeColumn(listData: Array<DailyStatusOut>): Array<ResizeColumn> {
            var self = this;
            let resizeColumn: Array<ResizeColumn> = [];
            let currentDay = new Date(self.dtPrev().toString());
            while (currentDay <= self.dtAft()) {
                resizeColumn.push(new ResizeColumn(nts.uk.time.formatDate(currentDay, 'yyyy/MM/dd'), 0));
                currentDay.setDate(currentDay.getDate() + 1);
            }
            _.each(listData, function(emp: DailyStatusOut) {
                _.each(emp.listDaily, function(daily: DailyStatus) {
                    let dateTemp = _.find(resizeColumn, { date: daily.date });
                    if (dateTemp.size < daily.stateSymbol.length) {
                        dateTemp.size = daily.stateSymbol.length;
                        dateTemp.updateWidth();
                    }
                });
            });
            return resizeColumn;
        }

        goBackA() {
            var self = this;
            let params = {
                inputContent: self.inputContent
            };
            nts.uk.request.jump('/view/kaf/018/a/index.xhtml', params);
        }

        backToB() {
            var self = this;
            let params = {
                closureId: self.closureId,
                processingYm: self.processingYm,
                startDate: self.startDate,
                endDate: self.endDate,
                closureName: self.closureName,
                listWorkplace: self.listWorkplace,
                listEmployeeCode: self.listEmpCd,
                inputContent: self.inputContent
            }
            nts.uk.request.jump('/view/kaf/018/b/index.xhtml', params);
        }

        goToD(rData) {
            var self = this;
            let listStatusEmp: Array<ApprovalStatusEmployee> = [];
            _.each(self.listApprovalEmployee, function(item) {
                if (rData.empId == item.sid) {
                    listStatusEmp.push(new ApprovalStatusEmployee(item.sid, item.startDate, item.endDate));
                }
            });
            let params = {
                empName: rData.empName,
                selectedEmpId: rData.empId,
                listStatusEmp: listStatusEmp,
                inputContent: self.inputContent
            }
            nts.uk.ui.windows.setShared("KAF018D_VALUE", params);
            nts.uk.ui.windows.sub.modal('/view/kaf/018/d/index.xhtml');
        }
    }

    class DailyStatusOut {
        empId: string;
        empName: string;
        listDaily: Array<DailyStatus>;
        constructor(empId: string, empName: string, listDaily: Array<DailyStatus>) {
            this.empId = empId;
            this.empName = empName;
            this.listDaily = listDaily;
        }
    }

    class DailyStatus {
        date: Date;
        stateSymbol: Array<number>;
        constructor(date: Date, stateSymbol: Array<number>) {
            this.date = date;
            this.stateSymbol = stateSymbol;
        }
    }

    class ResizeColumn {
        date: string;
        width: string;
        size: number;
        constructor(date: string, size: number) {
            this.date = date;
            this.size = size;
            this.updateWidth();

        }
        updateWidth() {
            switch (this.size) {
                case 0:
                    this.width = "30px";
                    break;
                case 1:
                    this.width = "30px";
                    break;
                case 2:
                    this.width = "40px";
                    break;
                case 3:
                    this.width = "50px";
                    break;
                case 4:
                    this.width = "60px";
                    break;
            }
        }
    }
    class ApprovalSttByEmp {
        selectedWkpId: string;
        listWkpId: Array<any>;
        startDate: Date;
        endDate: Date;
        listEmpCode: Array<string>;
        constructor(selectedWkpId: string, listWkpId: Array<any>, startDate: Date, endDate: Date, listEmpCode: Array<string>) {
            this.selectedWkpId = selectedWkpId;
            this.listWkpId = listWkpId;
            this.startDate = startDate;
            this.endDate = endDate;
            this.listEmpCode = listEmpCode;
        }
    }

    class ApprovalStatusEmployee {
        sid: string;
        startDate: Date;
        endDate: Date;
        constructor(sid: string, startDate: Date, endDate: Date) {
            this.sid = sid;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    class ApprovalSttByEmpList {
        sid: string;
        empName: string;
        startDate: Date;
        endDate: Date;
        listDaily: Array<DailyStatus>;
        constructor(sid: string, empName: string, listDaily: Array<DailyStatus>, startDate: Date, endDate: Date) {
            this.sid = sid;
            this.empName = empName;
            this.listDaily = listDaily;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    class EmpApprovalPeriod {
        empId: string;
        empName: string;
        listEmpPeriod: Array<EmpPeriod>;
        constructor(empId: string, empName: string, listEmpPeriod: Array<EmpPeriod>) {
            this.empId = empId;
            this.empName = empName;
            this.listEmpPeriod = listEmpPeriod;
        }
    }
    class EmpPeriod {
        startDate: string;
        endDate: string;
        listDailyStt: Array<DailyStatus>;
        constructor(startDate: string, endDate: string, listDailyStt: Array<DailyStatus>) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.listDailyStt = listDailyStt;

        }
    }
}