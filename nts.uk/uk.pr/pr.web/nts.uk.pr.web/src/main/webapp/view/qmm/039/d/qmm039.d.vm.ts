module nts.uk.pr.view.qmm039.d.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import hasError = nts.uk.ui.errors.hasError;

    export class ScreenModel {
        empCode: KnockoutObservable<string> = ko.observable('');
        empName: KnockoutObservable<string> = ko.observable('');
        itemClassification: KnockoutObservable<string> = ko.observable('');
        referenceYear: KnockoutObservable<number>;
        items: KnockoutObservableArray<ItemModel>;
        currentCode: KnockoutObservable<any>;

        constructor() {
            var self = this;
            self.referenceYear = ko.observable(201812);
            self.items =ko.observableArray([
                new ItemModel('1', 'data1 ', '2018/04 ~ 2018/12', '4500'),
                new ItemModel('2', 'data1 ', '2018/04 ~ 2018/12', '4500'),
                new ItemModel('3', 'data1 ', '2018/04 ~ 2018/12', '4500'),
                new ItemModel('4', 'data1 ', '2018/04 ~ 2018/12', '4500'),
                new ItemModel('5', 'data1 ', '2018/04 ~ 2018/12', '4500'),
                new ItemModel('6', 'data1 ', '2018/04 ~ 2018/12', '4500'),
                new ItemModel('7', 'data1 ', '2018/04 ~ 2018/12', '4500'),
                new ItemModel('8', 'data1 ', '2018/04 ~ 2018/12', '4500'),
                new ItemModel('9', 'data1 ', '2018/04 ~ 2018/12', '4500'),
                new ItemModel('10', 'data1 ', '2018/04 ~ 2018/12', '4500'),
            ]);
            this.currentCode = ko.observable();
        }

        cancel() {
            nts.uk.ui.windows.close();
        }

        extract() {

        }

        startPage(params): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.startupScreen();
            dfd.resolve();
            return dfd.promise();
        }

        startupScreen() {
            var self = this;
        }
    }
    class ItemModel {
        code: string;
        //D3_6 コード
        name: string;
        //D3_7 名称
        period: string;
        //D3_8 期間
        amount: string;
        //D3_9 金額
        constructor(code: string, name: string, period: string, amount: string) {
            this.code = code;
            this.name = name;
            this.period = period;
            this.amount = amount;
        }
    }
}
