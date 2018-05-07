module nts.uk.at.view.kaf018.c.viewmodel {
    import text = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import formatDate = nts.uk.time.formatDate;
    import shareModel = kaf018.share.model;
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
        listWorkplace: KnockoutObservableArray<any> = ko.observableArray([]);;
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

        dailySttOut: DailyStatusOut = new DailyStatusOut(null, null);
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
            self.listWorkplace = [];
            self.selectedWplId = ko.observable('');
            self.selectedWplName = ko.observable('');
            self.enableNext = ko.observable(false);
            self.enablePre = ko.observable(false);
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
            }
            self.dtPrev(new Date(self.startDateFormat));
            self.dtAft(new Date(self.endDateFormat));
            self.setArrDate();
            self.getWkpName();
            _.forEach(self.listWorkplace, function(item) {
                self.listWkpId.push(item.code);
            });
            self.initExTable();
            dfd.resolve();
            return dfd.promise();
        }


        getWkpName() {
            var self = this;
            self.enablePre(self.selectedWplIndex != 0)
            self.enableNext(self.selectedWplIndex != (self.listWorkplace.length - 1))

            let wkp = self.listWorkplace[self.selectedWplIndex];
            self.selectedWplId(wkp.code);
            self.selectedWplName(wkp.name);
        }

        getStatusSymbol() {
            var self = this;
            var dfd = $.Deferred();
            let obj = {
                selectedWkpId: self.selectedWplId(),
                listWkpId: self.listWkpId(),
                startDate: self.startDate,
                endDate: self.endDate,
                listEmpCode: self.listEmpCd,
            };
            service.initApprovalSttByEmployee(obj).done(function(data: any) {
                dfd.resolve(data);
            });
            return dfd.promise();
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
        initExTable(): void {
            var self = this;
            self.getStatusSymbol().done(function(listData: any) {

                let sv1 = self.setColorForCellHeaderDetail();
                let sv2 = self.setSymbolForCellContentDetail(listData);
                console.log(listData);
                $.when(sv1, sv2).done(function(detailHeaderDeco) {
                    let initExTable = self.setFormatData(detailHeaderDeco, listData);

                    new nts.uk.ui.exTable.ExTable($("#extable"), {
                        headerHeight: "30px", bodyRowHeight: "17px", bodyHeight: "340px",
                        horizontalSumBodyRowHeight: "0px",
                        areaResize: true,
                        bodyHeightMode: "fixed",
                        windowXOccupation: 50,
                        windowYOccupation: 20,
                        primaryTable: $("#extable")
                    })
                        .LeftmostHeader(initExTable.leftmostHeader).LeftmostContent(initExTable.leftmostContent)
                        .DetailHeader(initExTable.detailHeader).DetailContent(initExTable.detailContent)
                        .create();
                }).always(() => {
                    block.clear();
                })
            }).fail(() => {
                block.clear();
            })
        }

        /**
        * Update exTable
        */
        updateExTable() {
            let self = this;
            block.invisible();
            self.getStatusSymbol().done(function(listData: any) {
                let sv1 = self.setColorForCellHeaderDetail();
                let sv2 = self.setSymbolForCellContentDetail(listData);
                $.when(sv1, sv2).done(function(detailHeaderDeco) {
                    let initExTable = self.setFormatData(detailHeaderDeco, listData);
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
                    width: "150px",
                    control: "link",
                    handler: function(rData, rowIdx, key) { self.goToD(rData); }
                }
            ];
            leftmostHeader = {
                columns: leftmostColumns,
                rowHeight: "30px",
                width: "150px"
            };
            leftmostContent = {
                columns: leftmostColumns,
                dataSource: listData,
                primaryKey: "sId"
            };

            // create detail Columns and detail Content Columns
            let currentDay = new Date(self.dtPrev().toString());
            while (currentDay <= self.dtAft()) {
                let time = new shareModel.Time(currentDay);
                detailHeaderColumns.push({
                    key: "_" + time.yearMonthDay, width: "40px", headerText: self.getDay(time)
                });
                detailContentColumns.push({
                    key: "__" + time.yearMonthDay, width: "40px"
                });
                currentDay.setDate(currentDay.getDate() + 1);
            }

            //create Detail Header
            detailHeader = {
                columns: detailHeaderColumns,
                width: "1100px",
                features: [
                    {
                        name: "HeaderRowHeight",
                        rows: { 0: "30px" }
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
                primaryKey: "sId"
            };
            return {
                leftmostHeader: leftmostHeader,
                leftmostContent: leftmostContent,
                detailHeader: detailHeader,
                detailContent: detailContent
            };
        }

        getDay(time: shareModel.Time): string {
            if (time.day == "1") {
                return time.month + '/' + time.day;
            } else {
                return time.day;
            }
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
                        detailHeaderDeco.push(new shareModel.CellColor("_" + ymd, undefined, "bg-schedule-specific-date "));
                    } else if (_.includes(self.dataPublicHoliday(), dateFormat)) {
                        detailHeaderDeco.push(new shareModel.CellColor("_" + ymd, undefined, "bg-schedule-sunday color-schedule-sunday"));
                    } else if (date.weekDay === '土') {
                        detailHeaderDeco.push(new shareModel.CellColor("_" + ymd, undefined, "bg-schedule-saturday color-schedule-saturday"));
                    } else if (date.weekDay === '日') {
                        detailHeaderDeco.push(new shareModel.CellColor("_" + ymd, undefined, "bg-schedule-sunday color-schedule-sunday"));
                    } else {
                        detailHeaderDeco.push(new shareModel.CellColor("_" + ymd, undefined, "bg-weekdays color-weekdays"));
                    }
                });
                dfd.resolve(detailHeaderDeco);
            });
            return dfd.promise();
        }

        //職場名
        private displayWkp(): string {
            var self = this;
            return self.selectedWplId() + "    " + self.selectedWplName();
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
        setSymbolForCellContentDetail(listData): JQueryPromise<any> {
            var self = this, dfd = $.Deferred();
            _.each(listData, function(emp) {
                let currentDay = new Date(self.dtPrev().toString());
                var vaueDate: Date;
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
            }
            nts.uk.request.jump('/view/kaf/018/b/index.xhtml', params);
        }

        goToD(rData: any) {
            var self = this;
            self.dailySttOut = new DailyStatusOut(rData.empId, rData.listDaily);
            let params = {
                listEmp: self.listEmpCd,
                dailyData: self.dailySttOut
            }
            nts.uk.ui.windows.setShared("KAF018D_VALUE", params);
            nts.uk.ui.windows.sub.modal('/view/kaf/018/d/index.xhtml');
        }
    }

    class DailyStatusOut {
        empId: string;
        listDaily: Array<Date>;
        constructor(empId: string, listDaily: Array<Date>) {
            this.empId = empId;
            this.listDaily = listDaily;
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
}