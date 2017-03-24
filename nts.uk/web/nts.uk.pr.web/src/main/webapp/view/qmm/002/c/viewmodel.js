var qmm002;
(function (qmm002) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.lst_001 = ko.observableArray([]);
                    self.lst_002 = ko.observableArray([]);
                    self.singleSelectedCode = ko.observable();
                    self.selectedCodes = ko.observableArray([]);
                    self.selectedCodes2 = ko.observableArray([]);
                    self.selectedCodes.subscribe(function (items) {
                    });
                    self.singleSelectedCode.subscribe(function (val) {
                    });
                }
                ;
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var list = nts.uk.ui.windows.getShared('listItem');
                    self.lst_001(list);
                    self.lst_002(list);
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.tranferBranch = function () {
                    var self = this;
                    var message = "*が選択されていません。";
                    if (!self.singleSelectedCode()) {
                        nts.uk.ui.dialog.alert(message.replace("*", "統合元情報"));
                        return;
                    }
                    if (!self.selectedCodes().length) {
                        nts.uk.ui.dialog.alert(message.replace("*", "統合先情報"));
                        return;
                    }
                    if (self.selectedCodes().length) {
                        _.forEach(self.selectedCodes(), function (item) {
                            if (item == self.singleSelectedCode()) {
                                nts.uk.ui.dialog.alert("統合元と統合先で同じコードの＊が選択されています。\r\n");
                            }
                            else {
                                nts.uk.ui.dialog.confirm("統合元から統合先へデータを置換えます。\r\nよろしいですか？").ifYes(function () {
                                    var branchCodesMap = [];
                                    _.forEach(self.selectedCodes(), function (item) {
                                        var code = item.split('-');
                                        var bankCode = code[0];
                                        var branchCode = code[1];
                                        branchCodesMap.push({
                                            bankCode: bankCode,
                                            branchCode: branchCode
                                        });
                                    });
                                    var code = self.singleSelectedCode().split('-');
                                    var bankNewCode = code[0];
                                    var branchNewCode = code[1];
                                    var data = {
                                        branchCodes: branchCodesMap,
                                        bankNewCode: bankNewCode,
                                        branchNewCode: branchNewCode
                                    };
                                    c.service.tranferBranch(data).done(function () {
                                        self.getBankList();
                                    });
                                });
                            }
                        });
                    }
                };
                ScreenModel.prototype.getBankList = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    c.service.getBankList().done(function (data) {
                        var list001 = [];
                        _.forEach(data, function (itemBank) {
                            var childs = _.map(itemBank.bankBranch, function (item) {
                                return new BankInfo(itemBank.bankCode + "-" + item["bankBranchCode"], item["bankBranchCode"], item["bankBranchName"], item["bankBranchNameKana"], item["memo"], null, itemBank.bankCode);
                            });
                            list001.push(new BankInfo(itemBank.bankCode, itemBank.bankCode, itemBank.bankName, itemBank.bankNameKana, itemBank.memo, childs, null));
                        });
                        self.lst_001(list001);
                        dfd.resolve(list001);
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var BankInfo = (function () {
                function BankInfo(treeCode, code, name, nameKata, memo, childs, parentCode) {
                    var self = this;
                    self.treeCode = treeCode;
                    self.code = code;
                    self.name = name;
                    self.nameKata = nameKata;
                    self.memo = memo;
                    self.childs = childs;
                    self.parentCode = parentCode;
                }
                return BankInfo;
            }());
            viewmodel.BankInfo = BankInfo;
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qmm002.c || (qmm002.c = {}));
})(qmm002 || (qmm002 = {}));
;
