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
                { headerText: 'コード', key: 'lineBankCode', width: 45, formatter: _.escape },
                { headerText: '名称', key: 'lineBankName', width: 120, formatter: _.escape },
                { headerText: '口座区分', key: 'accountAtr', width: 110, formatter: _.escape },
                { headerText: '口座番号', key: 'accountNo', width: 100, formatter: _.escape }
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
                    if (data.length > 1) {
                        self.items(data);
                        self.items1(data);
                    } else {
                        //ER010
                        nts.uk.ui.dialog.alert("対象データがありません。");
                    }
                    dfd.resolve();
                }).fail(function() { });
            return dfd.promise();

        }

        transferData(data, newLineBankCode) {
            service.transfer(data)
                .done(function() {
                    nts.uk.ui.windows.setShared("currentCode", newLineBankCode, true);
                    nts.uk.ui.windows.close();
                })
                .fail(function(error) {
                    nts.uk.ui.dialog.alert(error.message);
                });
        }

        transfer() {
            var self = this;
            var oldLineBankCode = self.currentCode();
            var newLineBankCode = self.currentCode1();
            if (oldLineBankCode == null || newLineBankCode == null) {
                nts.uk.ui.dialog.alert("＊が選択されていません。");//ER007
                return;
            }
            else if (oldLineBankCode == newLineBankCode) {
                nts.uk.ui.dialog.alert("統合元と統合先で同じコードの＊が選択されています。\r\n  ＊を確認してください。");//ER009
                return;
            } else {
                //Al003
                nts.uk.ui.dialog.confirm("統合元から統合先へデータを置換えます。\r\n よろしいですか？").ifYes(function() {
                    nts.uk.ui.dialog.confirm("置換元のマスタを削除しますか？[はい/いいえ]").ifYes(function() {
                        var data = {
                            oldLineBankCode: oldLineBankCode,
                            newLineBankCode: newLineBankCode,
                            allowDelete: 1,
                        }
                        self.transferData(data, newLineBankCode);
                    }).ifNo(function() {
                        var data = {
                            oldLineBankCode: oldLineBankCode,
                            newLineBankCode: newLineBankCode,
                            allowDelete: 0,
                        }
                        self.transferData(data, newLineBankCode);
                    })
                }).ifNo(function() {
                    return;
                })
            }
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