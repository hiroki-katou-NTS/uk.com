module kdp003.s.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import info = nts.uk.ui.dialog.info;
    import error = nts.uk.ui.dialog.error;

    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];


    export class ViewModel {

        multipleSelectMode: KnockoutObservable<boolean> = ko.observable(true);
        singleSelectMode: KnockoutObservable<boolean> = ko.observable(false);

        // S21
        yearMonth: KnockoutObservable<number>;
        // S22   
        listTypeOfEngraving: KnockoutObservableArray<TypeOfEngraving>;
        selectedCode_TypeOfEngraving: KnockoutObservable<string>;

        // S3
        listOfStamps: KnockoutObservableArray<Stamp>;
        columns2: KnockoutObservableArray<NtsGridListColumn>;
        currentCodeOfStamp: KnockoutObservable<any>;

        listStampRecord: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor() {
            let self = this;

            let today = moment(new Date(), 'YYYY/MM/DD');
            let year = today.format('YYYY');
            let month = today.format('MM');
            self.yearMonth = ko.observable(year + '' + month);

            self.cssRangerYM = {
                2000: [{ 1: "round-green" }, { 5: "round-yellow" }],
                2002: [1, { 5: "round-gray" }]
            };

            self.listTypeOfEngraving = ko.observableArray([
                new TypeOfEngraving('1', text('KDP003_36')),
                new TypeOfEngraving('2', text('KDP003_37')),
                new TypeOfEngraving('3', text('KDP003_38')),
                new TypeOfEngraving('4', text('KDP003_39'))
            ]);

            self.selectedCode_TypeOfEngraving = ko.observable('1');

            // S3
            self.listOfStamps = ko.observableArray([]);

            self.columns2 = ko.observableArray([
                { headerText: "id", key: 'id', width: 0, hidden: true },
                { headerText: "<div style='text-align: center;'>" + nts.uk.resource.getText("KDP003_40") + "</div>", key: 'stampDate', width: 150 },
                { headerText: "<div style='text-align: center;'>" + nts.uk.resource.getText("KDP003_41") + "</div>", key: 'stampHowAndTime', width: 100 },
                { headerText: "<div style='text-align: center;'>" + nts.uk.resource.getText("KDP003_42") + "</div>", key: 'timeStampType', width: 200 }
            ]);

            self.currentCodeOfStamp = ko.observable();

            self.yearMonth.subscribe(newValue => {
                console.log(newValue);
            });

            self.selectedCode_TypeOfEngraving.subscribe(newValue => {
                console.log(newValue);
            });
        }


        /**
         * start page  
         */
        public start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            let dfdGetAllStampingResult = self.getAllStampingResult();
            $.when(dfdGetAllStampingResult).done(function(dfdGetAllStampingResultData) {
                dfd.resolve();
            });
            return dfd.promise();
        }

        getAllStampingResult(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let sid = __viewContext.user.employeeId;
            service.getAllStampingResult(sid).done(function(data) {
                _.forEach(data, (a) => {
                    let items = _.orderBy(a.stampDataOfEmployeesDto.stampRecords, ['stampDate', 'stampTime'], ['desc', 'desc']);
                    _.forEach(items, (sr) => {
                        self.listStampRecord.push(sr);
                    });
                });
                if (_.size(self.listStampRecord()) > 0) {
                    self.listStampRecord(_.orderBy(self.listStampRecord(), ['stampDate', 'stampTime'], ['desc', 'desc']));
                    _.forEach(self.listStampRecord(), (sr) => {
                        let changeClockArtDisplay = "<div class='full-width' style='text-align: center'> " + sr.stampArtName + " </div>";
                        if (sr.changeClockArt == 0) {
                            changeClockArtDisplay = "<div class='full-width' style='text-align: left'> " + sr.stampArtName + " </div>";
                        } else if (sr.changeClockArt == 1) {
                            changeClockArtDisplay = "<div class='full-width' style='text-align: right'> " + sr.stampArtName + " </div>";
                        }
                        let dateDisplay = nts.uk.time.applyFormat("Short_YMDW", sr.stampDate)
                        if (moment(sr.stampDate).day() == 6) {
                            dateDisplay = "<span class='color-schedule-saturday' >" + dateDisplay + "</span>";
                            sr.stampDate = "<span class='color-schedule-saturday' style='float:left;'>" + sr.stampDate + "</span>";
                        } else if (moment(sr.stampDate).day() == 0) {
                            dateDisplay = "<span class='color-schedule-sunday'>" + dateDisplay + "</span>";
                            sr.stampDate = "<span class='color-schedule-sunday' style='float:left;'>" + sr.stampDate + "</span>";
                        }
                        self.listOfStamps.push(new Stamp(
                            dateDisplay,
                            "<div class='inline-bl'>" + sr.stampHow + "</div>" + sr.stampTime,
                            changeClockArtDisplay,
                            sr.stampDate,
                            sr.stampTime
                        ));
                    });
                    dfd.resolve();
                }
            });
            return dfd.promise();
        }
        
        closeDialog() {
            close();
        }

    }

    class TypeOfEngraving {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export class Stamp {
        id: string;
        stampDate: string;
        stampHowAndTime: string;
        timeStampType: string;
        date: string;
        time: string
        constructor(stampDate: string, stampHowAndTime: string, timeStampType: string, date: string, time: string) {
            this.id = nts.uk.util.randomId();
            this.stampDate = stampDate;
            this.stampHowAndTime = stampHowAndTime;
            this.timeStampType = timeStampType;
            this.date = date;
            this.time = time;
        }
    }
}

