module nts.uk.at.view.kaf018.f.viewmodel {
    import text = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import formatDate = nts.uk.time.formatDate;

    export class ScreenModel {
        legendOptions: any;

        closureId: string;
        closureName: string;
        processingYm: string;
        startDateFormat: string;
        endDateFormat: string;
        startDate: Date;
        endDate: Date;
        isConfirmData: boolean
        listWkp: any;
        selectedWplIndex: number;
        selectedWplId: KnockoutObservable<string>;
        selectedWplName: KnockoutObservable<string>;
        listEmpCd: Array<string>;

        enableNext: KnockoutObservable<boolean>;
        enablePre: KnockoutObservable<boolean>;

        listDay: Array<Time>;

        selectedDate: KnockoutObservable<string> = ko.observable('1993/23/12');
        listColorOfHeader: KnockoutObservableArray<kaf018.share.model.CellColor> = ko.observableArray([]);

        arrDay: Time[] = [];
        //Date time
        currentDate: Date = new Date();
        dtPrev: KnockoutObservable<Date> = ko.observable(null);
        dtAft: KnockoutObservable<Date> = ko.observable(null);
        dateTimePrev: KnockoutObservable<string>;
        dateTimeAfter: KnockoutObservable<string>;

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
            self.listDay = [];
            self.listWkp = [];
            $("#fixed-table").ntsFixedTable({ width: 1200, height: 286 });
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
            self.createSampleData();
            self.getWkpName();
            //self.initTable();
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
        }

        preWkp() {
            var self = this;
            self.selectedWplIndex--;
            self.getWkpName();
        }

        createSampleData() {
            var self = this;

            let result: [];
            _.each(self.listWkp, function(item) {
                let currentDay = new Date(self.dtPrev().toString());
                while (currentDay <= self.dtAft()) {
                    let time = new Time(currentDay);
                    item["_" + time.yearMonthDay] = currentDay.getDate();
                    currentDay.setDate(currentDay.getDate() + 1);
                }
            })
        }

        getDay(time: Time): string {
            if (time.day == 1) {
                return time.month + '/' + time.day + "<br/>" + time.weekDay;
            } else {
                return time.day + "<br/>" + time.weekDay;
            }
        }

        /**
         * Create exTable
         */
        initExTable(): void {
            var self = this,
                timeRanges = [],
                //Get dates in time period
                currentDay = new Date(self.dtPrev().toString());

            // create data for columns
            let leftmostDs = [],
                middleDs = [],
                middleContentDeco = [],
                detailHeaderDeco = [],
                detailContentDeco = [],
                detailHeaderDs = [],
                detailContentDs = [],
                objDetailHeaderDs = {},
                detailColumns = [],
                horzSumHeaderDs = [],
                horzSumContentDs = [],
                leftHorzContentDs = [],
                vertSumContentDs = [];

            while (currentDay <= self.dtAft()) {
                self.arrDay.push(new Time(currentDay));
                let time = new Time(currentDay);
                detailColumns.push({
                    key: "_" + time.yearMonthDay, width: "40px", headerText: self.getDay(time)
                });
                currentDay.setDate(currentDay.getDate() + 1);
            }

            //create dataSource for detailHeader
            detailHeaderDs.push({});
            detailHeaderDs.push(new ExHeader(self.arrDay));

            leftmostDs = self.listWkp;

            //create leftMost Header and Content
            let leftmostColumns = [{
                headerText: nts.uk.resource.getText("KAF018_60"), key: "wkpName", width: "160px"
            }];

            let leftmostHeader = {
                columns: leftmostColumns,
                rowHeight: "70px",
                width: "160px"
            };

            let leftmostContent = {
                columns: leftmostColumns,
                dataSource: leftmostDs,
                primaryKey: "wkpId"
            };

            detailContentDs = self.listWkp;
            console.log(detailContentDs);
            //create Detail Content
            let detailContent = {
                columns: detailColumns,
                dataSource: detailContentDs,
                primaryKey: "empId",
                features: [{
                    name: "BodyCellStyle",
                    decorator: detailContentDeco
                }]
            };

            $.when(self.setColorForCellHeaderDetailAndHoz(detailHeaderDeco)).done(() => {
                // create Detail Header               
                let detailHeader = {
                    columns: [{
                        headerText: nts.uk.resource.getText("KAF018_64"),
                        group: detailColumns
                    }],
                    //dataSource: detailHeaderDs,
                    width: "800px",
                    features: [
                        {
                            name: "HeaderRowHeight",
                            rows: { 0: "30px", 1: "40px" }
                        },
                        {
                            name: "HeaderCellStyle",
                            decorator: detailHeaderDeco
                        }]
                };

                new nts.uk.ui.exTable.ExTable($("#extable"), {
                    headerHeight: "70px", bodyRowHeight: "30px", bodyHeight: "100px",
                    horizontalSumHeaderHeight: "40px", horizontalSumBodyHeight: "75px",
                    horizontalSumBodyRowHeight: "20px",
                    areaResize: true,
                    bodyHeightMode: "dynamic",
                    windowXOccupation: 25,
                    windowYOccupation: 210,
                    updateMode: "stick",
                    pasteOverWrite: true,
                    stickOverWrite: true,
                    viewMode: "shortName",
                    determination: {
                        rows: [0, 1],
                        columns: ["empName"]
                    },
                })
                    .LeftmostHeader(leftmostHeader).LeftmostContent(leftmostContent)
                    .DetailHeader(detailHeader).DetailContent(detailContent)
                    .create();
            });
        }

        /**
         * Set color for cell header: 日付セル背景色文字色制御
         * 
         */
        setColorForCellHeaderDetailAndHoz(detailHeaderDeco: any): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();

            // getDataSpecDateAndHoliday always query to server
            // because date is changed when click nextMonth or backMonth
            $.when(self.getDataSpecDateAndHoliday()).done(() => {
                _.each(self.arrDay, (date) => {
                    let ymd = date.yearMonthDay;
                    let dateFormat = moment(date.yearMonthDay).format('YYYY/MM/DD');
                    if (_.includes(self.dataWkpSpecificDate(), dateFormat) || _.includes(self.dataComSpecificDate(), dateFormat)) {
                        detailHeaderDeco.push(new kaf018.share.model.CellColor("_" + ymd, undefined, "bg-schedule-specific-date "));
                        //detailHeaderDeco.push(new kaf018.share.model.CellColor("_" + ymd + "_day", 2, "bg-schedule-specific-date"));
                    } else if (_.includes(self.dataPublicHoliday(), dateFormat)) {
                        detailHeaderDeco.push(new kaf018.share.model.CellColor("_" + ymd, undefined, "bg-schedule-sunday color-schedule-sunday"));
                        //detailHeaderDeco.push(new kaf018.share.model.CellColor("_" + ymd + "_day", 2, "bg-schedule-sunday color-schedule-sunday"));
                    } else if (date.weekDay === '土') {
                        detailHeaderDeco.push(new kaf018.share.model.CellColor("_" + ymd, undefined, "bg-schedule-saturday color-schedule-saturday"));
                        //detailHeaderDeco.push(new kaf018.share.model.CellColor("_" + ymd + "_day", 2, "bg-schedule-saturday color-schedule-saturday"));
                    } else if (date.weekDay === '日') {
                        detailHeaderDeco.push(new kaf018.share.model.CellColor("_" + ymd, undefined, "bg-schedule-sunday color-schedule-sunday"));
                        //detailHeaderDeco.push(new kaf018.share.model.CellColor("_" + ymd + "_day", 2, "bg-schedule-sunday color-schedule-sunday"));
                    } else {
                        detailHeaderDeco.push(new kaf018.share.model.CellColor("_" + ymd, undefined, "bg-weekdays color-weekdays"));
                        //detailHeaderDeco.push(new kaf018.share.model.CellColor("_" + ymd + "_day", 2, "bg-weekdays color-weekdays"));
                    }
                });
                self.listColorOfHeader(detailHeaderDeco);
                dfd.resolve();
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
    }

    class Time {
        year: string;
        month: string;
        day: string;
        weekDay: string;
        yearMonthDay: string;
        isSaturday: boolean;
        isSunday: boolean

        constructor(ymd: Date) {
            this.year = moment(ymd).format('YYYY');
            this.month = moment(ymd).format('M');
            this.day = moment(ymd).format('D');
            this.weekDay = moment(ymd).format('dd');
            this.yearMonthDay = this.year + moment(ymd).format('MM') + moment(ymd).format('DD');
            if (this.weekDay === '土')
                this.isSaturday = true;
            else
                this.isSaturday = false;
            if (this.weekDay === '日')
                this.isSunday = true;
            else
                this.isSunday = false;
        }

    class ExHeader {
        constructor(arrDay: Time[]) {
            for (let i = 0; i < arrDay.length; i++) {
                if (+arrDay[i].day == 1) {
                    this['_' + arrDay[i].yearMonthDay] = arrDay[i].month + '/' + arrDay[i].day + "<br/>" + arrDay[i].weekDay;
                } else {
                    this['_' + arrDay[i].yearMonthDay] = arrDay[i].day + "<br/>" + arrDay[i].weekDay;
                }
            }
        }
    }
}