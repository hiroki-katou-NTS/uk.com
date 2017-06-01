module kdl014.a.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<StampModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;

        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: '日付', key: 'date', width: 120 },
                { headerText: '打刻時間', key: 'time', width: 80 },
                { headerText: '打刻理由', key: 'reason', width: 80 },
                { headerText: '打刻区分', key: 'attribute', width: 80 },
                { headerText: '打刻方法', key: 'method', width: 100 },
                { headerText: '打刻場所', key: 'location', width: 80 },
                { headerText: '組み合わせ区分', key: 'combination', width: 100 }
            ]);
            self.currentCode = ko.observable();
        }

        /** Start page */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            // Get list stamp
            let cardNumber: string = '00000000000000000001';
            let startDate: string = '20160808';
            let endDate: string = '20170808';
            service.getStampByCode(cardNumber, startDate, endDate).done(function(lstStamp: any) {

                console.log(lstStamp);
                //TODO
                if (lstStamp.length > 0) {
                    _.forEach(lstStamp, function(item) {
                        self.items.push(new StampModel(item.date, item.time, item.reason, item.attribute, item.method, item.method, item.location, item.combination);
                    });
                }
                dfd.resolve();
            }).fail(function(res) {
                nts.uk.ui.dialog.alertError(res.message);
                dfd.reject();
            });
            return dfd.promise();
        }

        /**Close function*/
        close() {
            nts.uk.ui.windows.close();
        }
    }


    class StampModel {
        date: string;
        time: string;
        reason: string;
        attribute: string;
        method: string;
        location: string;
        combination: string;
        constructor(date: string, time: string, reason: string, attribute: string, method: string, location: string, combination: string) {
            var self = this;
            self.date = date;
            self.time = time;
            self.reason = reason;
            self.attribute = attribute;
            self.method = method;
            self.location = location;
            self.combination = combination;
        }
    }
}