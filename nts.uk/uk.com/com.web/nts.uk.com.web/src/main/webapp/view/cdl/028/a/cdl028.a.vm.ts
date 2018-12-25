module nts.uk.com.view.cdl028.a.viewmodel {

    import setShared = nts.uk.ui.windows.setShared;

    export class ScreenModel {
        required: KnockoutObservable<boolean>;
        standardDate: KnockoutObservable<number> =ko.observable(null);
        enable: KnockoutObservable<boolean>;
        yearValue: KnockoutObservable<any> = ko.observable({startDate: moment.utc().format("YYYY"), endDate: moment.utc().format("YYYY")
        });
        startDateString: KnockoutObservable<string>;
        endDateString: KnockoutObservable<string>;
        modeScreen : KnockoutObservable<number> = ko.observable(null);

        firstMonth : KnockoutObservable<number> =ko.observable(null);
        financialYear: KnockoutObservable<number> =ko.observable(null);
        standardDate: KnockoutObservable<number> =ko.observable(null);

        startDateFiscalYear: KnockoutObservable<number> =ko.observable(null);
        endDateFiscalYear: KnockoutObservable<number> =ko.observable(null);
        endDateDay: KnockoutObservable<number> =ko.observable(null);
        constructor() {
            var self = this;
            let params: any = nts.uk.ui.windows.getShared("CDL028_INPUT");
            if (params == null || params === undefined) {
                return;
            }
            self.modeScreen(params.mode);
            self.standardDate(params.date);
        }
        /**
         * startPage
         */
        public startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            var now = moment();
            let newDate : string = now.format("YYYY/MM/DD");
            self.required = ko.observable(false);
            self.startDateString = ko.observable();
            self.endDateString = ko.observable("");
            self.standardDate() == null ? newDate : self.standardDate();
            let startDateTemp:number = self.convertYearToInt(self.standardDate());
            let startMonthDB: number;
            switch (self.modeScreen()){

                case MODE_SCREEN.BASE_DATE:
                    self.standardDate();
                    break;

                case MODE_SCREEN.ALL:
                    self.standardDate(self.convertYearToInt(self.standardDate())+"0101");
                    console.log(self.standardDate() + "   ----MODE_SCREEN.ALL");
                    service.getStartMonth().done(function(response: IStartMonth) {
                        if(response.startMonth != null){
                            startMonthDB = response.startMonth;
                            console.log(startMonthDB);
                            if(( startMonthDB) >= self.getMonthToInt(self.standardDate())){
                                self.financialYear(startDateTemp+""+startMonthDB +"01");
                            } else {
                                self.financialYear((startDateTemp - 1)+""+startMonthDB+"01");
                            }
                            self.firstMonth(startMonthDB);
                        }
                    }).fail(function() {
                        setShared('CDL028_A_PARAMS', {
                            status: "NG"
                        });
                        nts.uk.ui.windows.close();
                    });
                    break;

                case MODE_SCREEN.YEAR_PERIOD_FINANCE:
                    self.standardDate(self.convertYearToInt(self.standardDate())+"0101");
                    break;

                case MODE_SCREEN.YEAR_PERIOD:
                    service.getStartMonth().done(function(response: IStartMonth) {
                        if(response.startMonth != null){
                            startMonthDB = response.startMonth;
                            console.log(startMonthDB);
                            if(( startMonthDB) >= self.getMonthToInt(self.standardDate())){
                                self.financialYear(startDateTemp+""+startMonthDB +"01");
                            } else {
                                self.financialYear((startDateTemp - 1)+""+startMonthDB+"01");
                            }
                            self.firstMonth(startMonthDB);
                        }
                    }).fail(function() {
                        setShared('CDL028_A_PARAMS', {
                            status: "NG"
                        });
                        nts.uk.ui.windows.close();
                    });

                    break;
            }

            dfd.resolve();
            return dfd.promise();
        }

        /**
         * proceed
         */
        proceed(){
            let self = this, dfd = $.Deferred();
            switch (self.modeScreen()){

                case MODE_SCREEN.BASE_DATE:
                    self.standardDate();
                    self.startDateFiscalYear(null);
                    self.endDateFiscalYear(null);
                    break;
                case MODE_SCREEN.ALL:
                case MODE_SCREEN.YEAR_PERIOD_FINANCE:
                    self.standardDate();
                    self.startDateFiscalYear(parseInt(self.startDateTemp)+""+self.firstMonth()+"01"));
                    if( self.firstMonth()!= 1){
                        self.endDateDay (moment((self.financialYear()+1)+"-"+self.firstMonth(), "YYYY-MM").daysInMonth() -1);
                        self.endDateFiscalYear(self.convertYearToInt(self.financialYear())+1)+""+self.firstMonth()+""+self.endDateDay());
                    }
                    self.endDateFiscalYear(self.convertYearToInt((self.financialYear()))+"1231")
                    break;

                case MODE_SCREEN.YEAR_PERIOD:
                    self.standardDate();
                    self.startDateFiscalYear(parseInt(self.convertYearToInt(self.financialYear().toString())+"0101"));
                    self.endDateFiscalYear(parseInt(self.convertYearToInt(self.financialYear().toString())+"1231"));
                    break;
            }
               /**
               * share param
               * status,standardDate,startDateFiscalYear,endDateFiscalYear
               */
                setShared('CDL028_A_PARAMS', {
                 status: "OK",
                 standardDate: self.standardDate(),
                 startDateFiscalYear: self.startDateFiscalYear(),
                 endDateFiscalYear: self.startDateFiscalYear(),
                });
                 console.log(status+ "--"+self.standardDate()+ "--"+self.startDateFiscalYear()+ "--"+self.endDateFiscalYear())
                 nts.uk.ui.windows.close();
                 dfd.resolve();
                 return dfd.promise();
        };

        /**
         * cancel
         */
        cancel(){
            setShared('CDL028_A_PARAMS', {
                status: "NG"
            });
            nts.uk.ui.windows.close();
        };

        convertYearToInt(standardDate: any) {
            let year: string;
            standardDate = standardDate+"";
            year = standardDate.slice(0, 4);
            return parseInt(year);
        }

        getMonthToInt(standardDate: any){
            let month: string;
            standardDate = standardDate+"";
            month = standardDate.slice(4, 6);
            return parseInt(month,10);
        }
    }
    export enum MODE_SCREEN {
        //mode standard date
        BASE_DATE = 1,

        //YEAR PERIOD FINANCE
        YEAR_PERIOD_FINANCE = 2,

        //All
        ALL = 3,

        //YEAR PERIOD
        YEAR_PERIOD = 4
    }

    interface  IStartMonth{
        startMonth: number;
        companyId : string;
    }
    class StartMonth {
        startMonth: number;
        companyId : string;
        constructor(startMonth: IStartMonth) {
            this.startMonth = startMonth.startMonth;
            this.companyId = startMonth.companyId;

        }
    }
}

