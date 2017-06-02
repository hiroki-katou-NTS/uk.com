module kdl014.a.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<StampModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;

        constructor() {
            this.items = ko.observableArray([]);

            for (let i = 1; i < 11; i++) {
                this.items.push(new StampModel('2' + _.padStart(i.toString(), 3, '0') + '/10/13', '8:20', '戻り', 'Web', '私用', '支店', '残業'));
            }

            this.columns = ko.observableArray([
                { headerText: '日付', key: 'date', width: 120 },
                { headerText: '打刻時間', key: 'time', width: 80 },
                { headerText: '打刻理由', key: 'reason', width: 80 },
                { headerText: '打刻区分', key: 'attribute', width: 80 },
                { headerText: '打刻方法', key: 'method', width: 100 },
                { headerText: '打刻場所', key: 'location', width: 80 },
                { headerText: '組み合わせ区分', key: 'combination', width: 100 }
            ]);
            this.currentCode = ko.observable();
        }
        /**Close function*/
        close(){
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