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
                    self.lst_003 = ko.observableArray([]);
                    self.singleSelectedCode = ko.observable();
                    self.selectedCodes = ko.observableArray([]);
                    self.selectedCodes2 = ko.observableArray([]);
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
                                    var branchId = new Array();
                                    _.forEach(self.selectedCodes(), function (item) {
                                        var code = item.split('-');
                                        var bankCode = code[0];
                                        var branchIdNode = self.getNode(item, bankCode);
                                        branchId.push(branchIdNode);
                                    });
                                    var code = self.singleSelectedCode().split('-');
                                    var bankNewCode = code[0];
                                    var branchNewId = self.getNode(self.singleSelectedCode(), bankNewCode);
                                    var data = {
                                        branchId: branchId,
                                        branchNewId: branchNewId
                                    };
                                    c.service.tranferBranch(data).done(function () {
                                    });
                                });
                            }
                        });
                    }
                };
                ScreenModel.prototype.getNode = function (codeNew, parentId) {
                    var self = this;
                    self.lst_003(nts.uk.util.flatArray(self.lst_001(), "childs"));
                    var node = _.find(self.lst_003(), function (item) {
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
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qmm002.c || (qmm002.c = {}));
})(qmm002 || (qmm002 = {}));
;
