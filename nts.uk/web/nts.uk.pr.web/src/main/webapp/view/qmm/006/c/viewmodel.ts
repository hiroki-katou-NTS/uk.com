module qmm006.c.viewmodel {
    export class ScreenModel {
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        items: KnockoutObservableArray<any>;
        items1: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentCode1: KnockoutObservable<any>;

        constructor() {
            var self = this;

            self.currentCode = ko.observable();
            self.currentCode1 = ko.observable();

            self.items = ko.observableArray([]);
            self.items1 = ko.observableArray([]);

            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'lineBankCode', width: 50 },
                { headerText: '名称', key: 'lineBankName', width: 160 },
                { headerText: '口座区分', key: 'accountAtr', width: 120 },
                { headerText: '口座番号', key: 'accountNo', width: 100 }
            ]);
        }

        startPage(): JQueryPromise<any> {
            return this.findAll();
        }

        findAll() {
            var self = this;
            var dfd = $.Deferred();
            qmm006.c.service.findAll()
                .done(function(data) {
                    if (data.length > 0) {
                        self.items(data);
                        self.items1(data);
                    } else {
                        self.items([]);
                        self.items1([]);
                    }
                    dfd.resolve();
                }).fail(function(res) { });
            return dfd.promise();

        }

        closeDialog(): any {
            nts.uk.ui.windows.close();
        }
    }

    class LineBankC {
        lineBankCode: KnockoutObservable<string>;
        lineBankName: KnockoutObservable<string>;
        accountAtr: KnockoutObservable<number>;
        accountNo: KnockoutObservable<string>;

        constructor(lineBankCode: string, lineBankName: string, accountAtr: number, accountNo: string) {
            this.lineBankCode = ko.observable(lineBankCode);
            this.lineBankName = ko.observable(lineBankName);
            this.accountAtr = ko.observable(accountAtr);
            this.accountNo = ko.observable(accountNo);
        }
    }
};