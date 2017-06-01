var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm002;
                (function (qmm002) {
                    var b;
                    (function (b) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.lst_001 = ko.observableArray([]);
                                    self.lst_002 = ko.observableArray([]);
                                    self.selectedCodes = ko.observableArray([]);
                                    self.messages = ko.observableArray([
                                        { messageId: "AL002", message: "データを削除します。\r\nよろしいですか？" },
                                        { messageId: "ER005", message: "入力した銀行コードは既に存在しています。\r\n銀行コードを確認してください。" },
                                        { messageId: "ER008", message: "選択された＊は使用されているため削除できません。" },
                                        { messageId: "ER007", message: "＊が選択されていません。" }
                                    ]);
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var list = nts.uk.ui.windows.getShared('listItem');
                                    self.lst_001(list);
                                };
                                /**
                                 * close screen qmm002b
                                 */
                                ScreenModel.prototype.close = function () {
                                    var self = this;
                                    nts.uk.ui.windows.close();
                                };
                                /**
                                 * Delete List Bank, Branch
                                 */
                                ScreenModel.prototype.btn_001 = function () {
                                    var self = this;
                                    if (!self.selectedCodes().length) {
                                        nts.uk.ui.dialog.confirm(self.messages()[3].message);
                                        return;
                                    }
                                    nts.uk.ui.dialog.confirm(self.messages()[0].message).ifYes(function () {
                                        var keyBank = [];
                                        _.forEach(self.selectedCodes(), function (item) {
                                            var code = item.split('-');
                                            var bankCode = code[0];
                                            var branchId = self.getNode(item, bankCode);
                                            keyBank.push({
                                                bankCode: bankCode,
                                                branchId: branchId
                                            });
                                        });
                                        var data = {
                                            bank: keyBank,
                                        };
                                        b.service.removeBank(data).done(function () {
                                            self.close();
                                        }).fail(function (error) {
                                            var messageList = self.messages();
                                            if (error.messageId == messageList[2].messageId) {
                                                var messageError = nts.uk.text.format(messageList[2].message, self.selectedCodes());
                                                nts.uk.ui.dialog.alert(messageError);
                                            }
                                        });
                                    });
                                };
                                /**
                                 * select node information
                                 */
                                ScreenModel.prototype.getNode = function (codeNew, parentId) {
                                    var self = this;
                                    self.lst_002(nts.uk.util.flatArray(self.lst_001(), "childs"));
                                    var node = _.find(self.lst_002(), function (item) {
                                        return item.treeCode == codeNew;
                                    });
                                    return node.branchId;
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var BankInfo = (function () {
                                function BankInfo(treeCode, code, branchId, name, nameKata, memo, childs, parentCode) {
                                    var self = this;
                                    self.treeCode = treeCode;
                                    self.code = code;
                                    self.branchId = branchId;
                                    self.name = name;
                                    self.displayName = self.code + "  " + self.name;
                                    self.nameKata = nameKata;
                                    self.memo = memo;
                                    self.childs = childs;
                                    self.parentCode = parentCode;
                                }
                                return BankInfo;
                            }());
                            viewmodel.BankInfo = BankInfo;
                        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
                    })(b = qmm002.b || (qmm002.b = {}));
                })(qmm002 = view.qmm002 || (view.qmm002 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm002.b.vm.js.map