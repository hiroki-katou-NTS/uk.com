var qmm006;
(function (qmm006) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
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
                ScreenModel.prototype.startPage = function () {
                    return this.findAll();
                };
                ScreenModel.prototype.findAll = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qmm006.c.service.findAll()
                        .done(function (data) {
                        if (data.length > 1) {
                            self.items(data);
                            self.items1(data);
                        }
                        else {
                            nts.uk.ui.dialog.alert("対象データがありません。");
                        }
                        dfd.resolve();
                    }).fail(function () { });
                    return dfd.promise();
                };
                ScreenModel.prototype.transferData = function (data, newLineBankCode) {
                    c.service.transfer(data)
                        .done(function () {
                        nts.uk.ui.windows.setShared("currentCode", newLineBankCode, true);
                        nts.uk.ui.windows.close();
                    })
                        .fail(function (error) {
                        nts.uk.ui.dialog.alert(error.message);
                    });
                };
                ScreenModel.prototype.transfer = function () {
                    var self = this;
                    var oldLineBankCode = self.currentCode();
                    var newLineBankCode = self.currentCode1();
                    if (oldLineBankCode == null || newLineBankCode == null) {
                        nts.uk.ui.dialog.alert("＊が選択されていません。");
                        return;
                    }
                    else if (oldLineBankCode == newLineBankCode) {
                        nts.uk.ui.dialog.alert("統合元と統合先で同じコードの＊が選択されています。\r\n  ＊を確認してください。");
                        return;
                    }
                    else {
                        nts.uk.ui.dialog.confirm("統合元から統合先へデータを置換えます。\r\n よろしいですか？").ifYes(function () {
                            nts.uk.ui.dialog.confirm("置換元のマスタを削除しますか？[はい/いいえ]").ifYes(function () {
                                var data = {
                                    oldLineBankCode: oldLineBankCode,
                                    newLineBankCode: newLineBankCode,
                                    allowDelete: 1,
                                };
                                self.transferData(data, newLineBankCode);
                            }).ifNo(function () {
                                var data = {
                                    oldLineBankCode: oldLineBankCode,
                                    newLineBankCode: newLineBankCode,
                                    allowDelete: 0,
                                };
                                self.transferData(data, newLineBankCode);
                            });
                        }).ifNo(function () {
                            return;
                        });
                    }
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var LineBankC = (function () {
                function LineBankC(lineBankCode, lineBankName, accountAtr, accountNo) {
                    this.lineBankCode = ko.observable(lineBankCode);
                    this.lineBankName = ko.observable(lineBankName);
                    this.accountAtr = ko.observable(accountAtr);
                    this.accountNo = ko.observable(accountNo);
                }
                return LineBankC;
            }());
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qmm006.c || (qmm006.c = {}));
})(qmm006 || (qmm006 = {}));
;
