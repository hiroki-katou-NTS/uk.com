var qmm002;
(function (qmm002) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.confirmDirty = false;
                    var self = this;
                    self.items = ko.observableArray([]);
                    self.currentItem = ko.observable(new Bank("", "", "", ""));
                    self.currentCode = ko.observable();
                    self.currentCodeList = ko.observableArray([]);
                    self.dirty = new nts.uk.ui.DirtyChecker(ko.observable(new Bank("", "", "", "")));
                    self.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'bankCode', width: 50, key: 'bankCode' },
                        { headerText: 'コード', prop: 'bankName', width: 50, key: 'bankName' }
                    ]);
                    self.currentCode.subscribe(function (codeChanged) {
                        if (!self.checkDirty()) {
                            self.currentItem(self.getCode(codeChanged));
                            self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                        }
                        else {
                            if (self.confirmDirty) {
                                self.confirmDirty = false;
                                return;
                            }
                            debugger;
                            nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function () {
                                self.currentItem(self.getCode(codeChanged));
                                self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                            }).ifNo(function () {
                                self.confirmDirty = true;
                                self.currentCode(self.currentItem().code());
                            });
                        }
                        self.isCreated(false);
                    });
                    self.isCreated = ko.observable(true);
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.getBankList().done(function (data) {
                        var hadData = self.isEmptyList(data);
                        self.isCreated(!hadData);
                        if (hadData) {
                            self.currentItem(self.selectedFirst(data[0]));
                            self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                        }
                        dfd.resolve();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.addBank = function () {
                    var self = this;
                    var bankInfo = {
                        bankCode: self.currentItem().code(),
                        bankName: self.currentItem().name(),
                        bankNameKana: self.currentItem().nameKana(),
                        memo: self.currentItem().memo()
                    };
                    var dfd = $.Deferred();
                    qmm002.d.service.addBank(self.isCreated(), bankInfo).done(function () {
                        self.dirty = new nts.uk.ui.DirtyChecker(ko.observable(bankInfo));
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    }).then(function () {
                        $.when(self.getBankList()).done(function () {
                            self.currentCode(bankInfo.bankCode);
                        });
                    });
                };
                ScreenModel.prototype.close = function () {
                    var self = this;
                    if (!self.checkDirty()) {
                        nts.uk.ui.windows.close();
                    }
                    else {
                        debugger;
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function () {
                            nts.uk.ui.windows.close();
                        });
                    }
                };
                ScreenModel.prototype.Delete = function () {
                    var self = this;
                    var bankInfo = {
                        bankCode: self.currentItem().code(),
                        bankName: self.currentItem().name(),
                        bankNameKana: self.currentItem().nameKana(),
                        memo: self.currentItem().memo()
                    };
                    self.cleanForm();
                    var dfd = $.Deferred();
                    qmm002.d.service.removeBank(bankInfo).done(function () {
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error);
                    }).then(function () {
                        $.when(self.getBankList()).done(function () {
                        });
                    });
                };
                ScreenModel.prototype.cleanForm = function () {
                    var self = this;
                    self.confirmDirty = true;
                    if (!self.checkDirty()) {
                        self.currentItem(new Bank("", "", "", ""));
                        self.currentCode("");
                        self.isCreated(true);
                    }
                    else {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function () {
                            self.currentItem(new Bank("", "", "", ""));
                            self.currentCode("");
                            self.isCreated(true);
                        });
                    }
                };
                ScreenModel.prototype.getCode = function (codeChange) {
                    var self = this;
                    var itemBank = _.find(self.items(), function (item) {
                        return item.bankCode.trim() === codeChange.trim();
                    });
                    if (!itemBank) {
                        return new Bank("", "", "", "");
                    }
                    return new Bank(itemBank.bankCode, itemBank.bankName, itemBank.bankNameKana, itemBank.memo);
                };
                ScreenModel.prototype.getBankList = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qmm002.d.service.getBankList().done(function (data) {
                        self.items(data);
                        dfd.resolve(data);
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.isEmptyList = function (data) {
                    return data.length > 0;
                };
                ScreenModel.prototype.selectedFirst = function (item) {
                    var self = this;
                    self.currentCode(item.bankCode);
                    return new Bank(item.bankCode, item.bankName, item.bankNameKana, item.memo);
                };
                ScreenModel.prototype.checkDirty = function () {
                    var self = this;
                    if (self.dirty.isDirty()) {
                        return true;
                    }
                    else {
                        return false;
                    }
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var Bank = (function () {
                function Bank(bankCode, bankName, bankNameKana, memo) {
                    this.code = ko.observable(bankCode);
                    this.name = ko.observable(bankName);
                    this.nameKana = ko.observable(bankNameKana);
                    this.memo = ko.observable(memo);
                }
                return Bank;
            }());
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = qmm002.d || (qmm002.d = {}));
})(qmm002 || (qmm002 = {}));
