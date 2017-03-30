var qmm006;
(function (qmm006) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.dataSource = ko.observableArray([]),
                        self.dataSource2 = ko.observableArray([]),
                        self.singleSelectedCode = ko.observable();
                    self.selectedBank = ko.observable();
                    self.selectedCodes = ko.observableArray([]);
                    self.headers = ko.observableArray(["Item Value Header", "コード/名称"]);
                    self.singleSelectedCode.subscribe(function (codeChanged) {
                        self.selectedBank(self.getBank(codeChanged));
                    });
                }
                ScreenModel.prototype.getBank = function (codeNew) {
                    var self = this;
                    self.dataSource2(nts.uk.util.flatArray(self.dataSource(), "childs"));
                    var bank = _.find(self.dataSource2(), function (item) {
                        return item.treeCode === codeNew;
                    });
                    return bank;
                };
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.findBankAll().done(function (data) {
                        if (nts.uk.ui.windows.getShared("bankBranchCode") != null) {
                            self.singleSelectedCode(nts.uk.ui.windows.getShared("bankBranchCode"));
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.findBankAll = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qmm006.b.service.findBankAll()
                        .done(function (data) {
                        if (data.length > 0) {
                            var bankData = [];
                            _.forEach(data, function (item) {
                                var childs = _.map(item.bankBranch, function (itemChild) {
                                    return new Bank(itemChild.bankBranchCode, itemChild.bankBranchName, item.bankCode, item.bankName, item.bankCode + itemChild.bankBranchCode, []);
                                });
                                bankData.push(new Bank(item.bankCode, item.bankName, null, null, item.bankCode, childs));
                            });
                            self.dataSource(bankData);
                        }
                        dfd.resolve();
                    }).fail(function (res) { });
                    return dfd.promise();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.transferData = function () {
                    var self = this;
                    if (self.singleSelectedCode()) {
                        nts.uk.ui.windows.setShared("selectedBank", this.selectedBank(), true);
                        nts.uk.ui.windows.close();
                    }
                    else {
                        nts.uk.ui.dialog.alert("＊が選択されていません。");
                    }
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var Bank = (function () {
                function Bank(code, name, parentCode, parentName, treeCode, childs) {
                    var self = this;
                    self.code = code;
                    self.name = name;
                    self.nodeText = self.code + ' ' + self.name;
                    self.childs = childs;
                    self.parentCode = parentCode;
                    self.parentName = parentName;
                    self.treeCode = treeCode;
                }
                return Bank;
            }());
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qmm006.b || (qmm006.b = {}));
})(qmm006 || (qmm006 = {}));
;
//# sourceMappingURL=viewmodel.js.map