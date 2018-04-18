module nts.uk.at.view.kdr001.b.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
        export class ScreenModel {
            textName1: KnockoutObservable<string>;
            textName2: KnockoutObservable<string>;
            lstHolidays: KnockoutObservableArray<HolidayRemaining> = ko.observableArray([]);
            columns2: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<String>;

            currentHoliday: KnockoutObservable<HolidayRemaining> = ko.observable(new HolidayRemaining(null));

            count: number = 100;
            switchOptions: KnockoutObservableArray<any>;

            constructor() {
                let self = this;
                self.textName1 = ko.observable('');
                self.textName2 = ko.observable('');

                let params = getShared("KDR001Params");
                self.currentCode = ko.observable(params || '');

                this.switchOptions = ko.observableArray([
                    { code: "1", name: '四捨五入' },
                    { code: "2", name: '切り上げ' },
                    { code: "3", name: '切り捨て' }
                ]);


                self.currentCode.subscribe(cd => {
                    errors.clearAll();
                    let lstHolidays = self.lstHolidays();
                    // do not process anything if it is new mode.
                    //if (roleSetCd) {
                    if (cd && lstHolidays && lstHolidays.length > 0) {

                        let index: number = 0;
                        if (cd) {
                            index = _.findIndex(lstHolidays, function(x)
                            { return x.cd == cd });
                            if (index === -1) index = 0;
                        }
                        let _holiday = lstHolidays[index];
                        if (_holiday && _holiday.cd) {

                            self.currentHoliday(_holiday);
                        } else {
                            self.currentHoliday(null);
                        }
                    }
            });
        }
        
        /**
         * 開始
         **/
        private start() : JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            nts.uk.ui.block.invisible();
            service.findAll()
                .done(function(data: Array<HolidayRemaining>) {
                    if (data.length > 0) {
                        self.items(data);
                        self.currentCode(data[0].cd());
                    }
                    // no data
                    else {
                        self.items([]);
                        self.currentCode('');
                    }

                    dfd.resolve();
                })
                .fail(function(res) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function() { nts.uk.ui.block.clear(); });
                     dfd.rejected();
                });
            return dfd.promise();
        }

    }
    class HolidayRemaining {

        cid: KnockoutObservable<String>;
        cd: KnockoutObservable<String>;
        name: KnockoutObservable<String>;
        displayCd: String;
        displayName: String;
        yearlyHoliday: KnockoutObservable<boolean>;
        insideHalfDay: KnockoutObservable<boolean>;
        insideHours: KnockoutObservable<boolean>;
        yearlyReserved: KnockoutObservable<boolean>;
        outItemSub: KnockoutObservable<boolean>;
        representSub: KnockoutObservable<boolean>;
        remainChargeSub: KnockoutObservable<boolean>;
        pauseItem: KnockoutObservable<boolean>;
        undigestedPause: KnockoutObservable<boolean>;
        numRemainPause: KnockoutObservable<boolean>;
        outputItemsHolidays: KnockoutObservable<boolean>;
        outputHolidayForward: KnockoutObservable<boolean>;
        monthlyPublic: KnockoutObservable<boolean>;
        childCareLeave: KnockoutObservable<boolean>;
        nursingCareLeave: KnockoutObservable<boolean>;
        constructor(param: any) {
            let self = this();
            self.cid = ko.observable(param ? param.cid || '' : '');
            self.cd = ko.observable(param ? param.cd || '' : '');
            self.name = ko.observable(param ? param.name || '' : '');
            self.displayCd = param ? param.cd || '' : '';
            self.displayName = param ? param.name || '' : '';
            self.yearlyHoliday = ko.observable(param ? param.yearlyHoliday || false : false);
            self.insideHalfDay = ko.observable(param ? param.insideHalfDay || false : false);
            self.insideHours = ko.observable(param ? param.insideHours || false : false);
            self.yearlyReserved = ko.observable(param ? param.yearlyReserved || false : false);
            self.outItemSub = ko.observable(param ? param.outItemSub || false : false);
            self.representSub = ko.observable(param ? param.representSub || false : false);
            self.remainChargeSub = ko.observable(param ? param.remainChargeSub || false : false);
            self.pauseItem = ko.observable(param ? param.pauseItem || false : false);
            self.undigestedPause = ko.observable(param ? param.undigestedPause || false : false);
            self.numRemainPause = ko.observable(param ? param.numRemainPause || false : false);
            self.outputItemsHolidays = ko.observable(param ? param.outputItemsHolidays || false : false);
            self.outputHolidayForward = ko.observable(param ? param.outputHolidayForward || false : false);
            self.monthlyPublic = ko.observable(param ? param.monthlyPublic || false : false);
            self.childCareLeave = ko.observable(param ? param.childCareLeave || false : false);
            self.nursingCareLeave = ko.observable(param ? param.nursingCareLeave || false : false);
        }
    }

        class ItemModel {
        code: string;
        name: string;
        description: string;
        other1: string;
        other2: string;
        deletable: boolean;
        switchValue: boolean;
        constructor(code: string, name: string, description: string, deletable: boolean, other1?: string, other2?: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
            this.other2 = other2 || other1;
            this.deletable = deletable;
            this.switchValue = ((code % 3) + 1).toString();
        }
    }
}