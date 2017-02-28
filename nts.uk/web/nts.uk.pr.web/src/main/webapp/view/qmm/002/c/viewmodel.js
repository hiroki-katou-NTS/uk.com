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
                        console.log(items);
                    });
                    self.singleSelectedCode.subscribe(function (val) {
                        console.log(val);
                    });
                }
                ;
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var list = nts.uk.ui.windows.getShared('listItem');
                    self.lst_001(list);
                    console.log(self.lst_001());
                    self.lst_002(list);
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.tranferBranch = function () {
                    var self = this;
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
                        // error
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
