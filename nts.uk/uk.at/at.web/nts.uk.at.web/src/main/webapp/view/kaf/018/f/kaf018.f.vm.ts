module nts.uk.at.view.kaf018.f.viewmodel {
    import text = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import formatDate = nts.uk.time.formatDate;
    import shareModel = kaf018.share.model;

    export class ScreenModel {
        legendOptions: any;

        closureId: string;
        closureName: string;
        processingYm: string;
        startDateFormat: string;
        endDateFormat: string;
        startDate: Date;
        endDate: Date;
        listWkp: any;
        selectedWplIndex: number;
        selectedWplId: KnockoutObservable<string>;
        selectedWplName: KnockoutObservable<string>;
        listEmpCd: Array<string>;

        enableNext: KnockoutObservable<boolean>;
        enablePre: KnockoutObservable<boolean>;

        listData: any;

        arrDay: Time[] = [];
        dtPrev: KnockoutObservable<Date> = ko.observable(null);
        dtAft: KnockoutObservable<Date> = ko.observable(null);

        dataWkpSpecificDate: KnockoutObservableArray<any> = ko.observableArray([]);
        dataComSpecificDate: KnockoutObservableArray<any> = ko.observableArray([]);
        dataPublicHoliday: KnockoutObservableArray<any> = ko.observableArray([]);

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
            self.listWkp = [];
            //$("#fixed-table").ntsFixedTable({ width: 1200, height: 286 });
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

            let params = getShared("KAF018F_PARAMS");
            if (params) {
                self.closureId = params.closureId;
                self.closureName = params.closureName;
                self.processingYm = params.processingYm;
                self.startDateFormat = formatDate(new Date(params.startDate), 'yyyy/MM/dd');
                self.endDateFormat = formatDate(new Date(params.endDate), 'yyyy/MM/dd');
                self.startDate = params.startDate;
                self.endDate = params.endDate;
                self.listWkp = params.listWkp;
                self.selectedWplIndex = params.selectedWplIndex;
                self.listEmpCd = params.listEmployeeCode;
            }
            self.dtPrev(new Date(self.startDateFormat));
            self.dtAft(new Date(self.endDateFormat));
            self.setArrDate();
            self.getWkpName();
            self.initExTable();
            dfd.resolve();
            return dfd.promise();
        }

        getWkpName() {
            var self = this;
            self.enablePre(self.selectedWplIndex != 0)
            self.enableNext(self.selectedWplIndex != (self.listWkp.length - 1))

            let wkp = self.listWkp[self.selectedWplIndex];
            self.selectedWplId(wkp.wkpId);
            self.selectedWplName(wkp.wkpName);
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
        }

        setArrDate() {
            var self = this;
            let currentDay = new Date(self.dtPrev().toString());
            while (currentDay <= self.dtAft()) {
                self.arrDay.push(new Time(currentDay));
                currentDay.setDate(currentDay.getDate() + 1);
            }
        };

        /**
         * Create exTable
         */
        initExTable(): void {
            var self = this;
            self.getSampleData().done(function(listData: any) {
                let sv1 = self.setColorForCellHeaderDetail();
                let sv2 = self.setColorForCellContentDetail(listData);
                $.when(sv1, sv2).done(function(detailHeaderDeco, detailContentDeco) {
                    let initExTable = self.setFormatData(detailHeaderDeco, detailContentDeco, listData);

                    new nts.uk.ui.exTable.ExTable($("#extable"), {
                        headerHeight: "50px", bodyRowHeight: "17px", bodyHeight: "340px",
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
                })
            });
        }

        /**
         * Update exTable
         */
        updateExTable() {
            var self = this;
            self.getSampleData2().done(function(listData: any) {
                let sv1 = self.setColorForCellHeaderDetail();
                let sv2 = self.setColorForCellContentDetail(listData);
                $.when(sv1, sv2).done(function(detailHeaderDeco, detailContentDeco) {
                    let initExTable = self.setFormatData(detailHeaderDeco, detailContentDeco, listData);
                    $("#extable").exTable("updateTable", "leftmost", initExTable.leftmostHeader, initExTable.leftmostContent, true);
                    $("#extable").exTable("updateTable", "detail", initExTable.detailHeader, initExTable.detailContent, true);
                })
            })
        }

        /**
         * Get data
         */
        getSampleData(): JQueryPromise<any> {
            var self = this, dfd = $.Deferred();
            let listData = [];
            let currentDay = new Date(self.dtPrev().toString());
            let r1 = new Date(self.dtPrev().toString()), r2 = new Date(self.dtPrev().toString()), r3 = new Date(self.dtPrev().toString());
            r1.setDate(r1.getDate() + 5);
            r2.setDate(r2.getDate() + 16);
            r3.setDate(r3.getDate() + 25);
            for (let i = 0; i < 30; i++) {
                currentDay = new Date(self.dtPrev().toString());
                let data = {
                    index: i.toString(),
                    sId: "sid-" + i,
                    sName: "sName - " + i,
                    monthConfirm: i % 3 == 0 ? text("KAF018_92") : "",
                    personConfirm: i % 2 == 0 ? text("KAF018_92") : "",
                    bossConfirm: i % 4 == 0 ? text("KAF018_92") : "",
                    dailyReport: []
                };
                let dailyReport = [];
                while (currentDay <= self.dtAft()) {
                    let time = new Time(currentDay);
                    if (currentDay <= r1) {
                        dailyReport.push(0)
                    } else if (currentDay <= r2) {
                        dailyReport.push(1)
                    } else if (currentDay <= r3) {
                        dailyReport.push(2)
                    } else {
                        dailyReport.push(3)
                    }
                    currentDay.setDate(currentDay.getDate() + 1);
                }
                data.dailyReport = dailyReport;
                listData.push(data);
            }
            dfd.resolve(listData);
            return dfd.promise();
        }
        
        /**
         * Get data
         */
        getSampleData2(): JQueryPromise<any> {
            var self = this, dfd = $.Deferred();
            let listData = [];
            let currentDay = new Date(self.dtPrev().toString());
            let r1 = new Date(self.dtPrev().toString()), r2 = new Date(self.dtPrev().toString()), r3 = new Date(self.dtPrev().toString());
            r1.setDate(r1.getDate() + 7);
            r2.setDate(r2.getDate() + 10);
            r3.setDate(r3.getDate() + 20);
            for (let i = 0; i < 60; i++) {
                currentDay = new Date(self.dtPrev().toString());
                let data = {
                    index: i.toString(),
                    sId: "sid-" + i,
                    sName: "sName - " + i,
                    monthConfirm: i % 2 == 0 ? text("KAF018_92") : "",
                    personConfirm: i % 4 == 0 ? text("KAF018_92") : "",
                    bossConfirm: i % 3 == 0 ? text("KAF018_92") : "",
                    dailyReport: []
                };
                let dailyReport = [];
                while (currentDay <= self.dtAft()) {
                    let time = new Time(currentDay);
                    if (currentDay <= r1) {
                        dailyReport.push(0)
                    } else if (currentDay <= r2) {
                        dailyReport.push(1)
                    } else if (currentDay <= r3) {
                        dailyReport.push(2)
                    } else {
                        dailyReport.push(3)
                    }
                    currentDay.setDate(currentDay.getDate() + 1);
                }
                data.dailyReport = dailyReport;
                listData.push(data);
            }
            dfd.resolve(listData);
            return dfd.promise();
        }

        setFormatData(detailHeaderDeco, detailContentDeco, listData) {
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
                { headerText: text("KAF018_60"), key: "sName", width: "150px", control: "link" },
                { headerText: text("KAF018_61"), key: "monthConfirm", width: "50px" },
                { headerText: text("KAF018_62"), key: "personConfirm", width: "50px" },
                { headerText: text("KAF018_63"), key: "bossConfirm", width: "50px" }
            ];
            leftmostHeader = {
                columns: leftmostColumns,
                rowHeight: "50px",
                width: "300px"
            };
            leftmostContent = {
                columns: leftmostColumns,
                dataSource: listData,
                primaryKey: "index"
            };

            // create detail Columns and detail Content Columns
            let currentDay = new Date(self.dtPrev().toString());
            while (currentDay <= self.dtAft()) {
                let time = new Time(currentDay);
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
                columns: [{
                    headerText: text("KAF018_64"),
                    group: detailHeaderColumns
                }],
                width: "900px",
                features: [
                    {
                        name: "HeaderRowHeight",
                        rows: { 0: "20px", 1: "30px" }
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
                primaryKey: "index",
                features: [{
                    name: "BodyCellStyle",
                    decorator: detailContentDeco
                }]
            };
            return {
                leftmostHeader: leftmostHeader,
                leftmostContent: leftmostContent,
                detailHeader: detailHeader,
                detailContent: detailContent
            };
        }

        getDay(time: Time): string {
            if (time.day == "1") {
                return time.month + '/' + time.day + "<br/>" + time.weekDay;
            } else {
                return time.day + "<br/>" + time.weekDay;
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
            // because date is changed when click nextMonth or backMonth
            $.when(self.getDataSpecDateAndHoliday()).done(() => {
                _.each(self.arrDay, (date) => {
                    let ymd = date.yearMonthDay;
                    let dateFormat = moment(date.yearMonthDay).format('YYYY/MM/DD');
                    if (_.includes(self.dataWkpSpecificDate(), dateFormat) || _.includes(self.dataComSpecificDate(), dateFormat)) {
                        detailHeaderDeco.push(new shareModel.CellColor("_" + ymd, "1", "bg-schedule-specific-date "));
                    } else if (_.includes(self.dataPublicHoliday(), dateFormat)) {
                        detailHeaderDeco.push(new shareModel.CellColor("_" + ymd, "1", "bg-schedule-sunday color-schedule-sunday"));
                    } else if (date.weekDay === '土') {
                        detailHeaderDeco.push(new shareModel.CellColor("_" + ymd, "1", "bg-schedule-saturday color-schedule-saturday"));
                    } else if (date.weekDay === '日') {
                        detailHeaderDeco.push(new shareModel.CellColor("_" + ymd, "1", "bg-schedule-sunday color-schedule-sunday"));
                    } else {
                        detailHeaderDeco.push(new shareModel.CellColor("_" + ymd, "1", "bg-weekdays color-weekdays"));
                    }
                });
                dfd.resolve(detailHeaderDeco);
            });
            return dfd.promise();
        }

        /**
         * Get data WkpSpecificDate, ComSpecificDate, PublicHoliday
         */
        getDataSpecDateAndHoliday(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred(),
                obj = {
                    workplaceId: self.selectedWplId,
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
         * Set color for cell detail
         * 
         */
        setColorForCellContentDetail(listData): JQueryPromise<any> {
            var self = this, dfd = $.Deferred();
            let detailContentDeco = [];
            for (let i = 0; i < listData.length; i++) {
                let currentDay = new Date(self.dtPrev().toString());
                let j = 0;
                while (currentDay <= self.dtAft()) {
                    let time = new Time(currentDay);
                    let key = "__" + time.yearMonthDay;
                    let clazz = "";
                    switch (listData[i].dailyReport[j]) {
                        case 0:
                            clazz = "bg-canceled-application";
                            break;
                        case 1:
                            clazz = "bg-updating-cell";
                            break;
                        case 2:
                            clazz = "bg-unapproved-application";
                            break;
                        case 3:
                            clazz = "bg-weekdays";
                            break;
                    }
                    detailContentDeco.push(new shareModel.CellColor(key, i.toString(), clazz));
                    listData[i][key] = "";

                    currentDay.setDate(currentDay.getDate() + 1);
                    j++;
                }
            }
            dfd.resolve(detailContentDeco);
            return dfd.promise();
        }
    }

    class Time {
        year: string;
        month: string;
        day: string;
        weekDay: string;
        yearMonthDay: string;

        constructor(ymd: Date) {
            this.year = moment(ymd).format('YYYY');
            this.month = moment(ymd).format('M');
            this.day = moment(ymd).format('D');
            this.weekDay = moment(ymd).format('dd');
            this.yearMonthDay = this.year + moment(ymd).format('MM') + moment(ymd).format('DD');
        }
    }
}