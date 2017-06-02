// TreeGrid Node
var qpp014;
(function (qpp014) {
    var h;
    (function (h) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel(data) {
                    var self = this;
                    self.dataBankBranch = ko.observableArray([]);
                    self.dataBankBranch2 = ko.observableArray([]);
                    self.dataLineBank = ko.observableArray([]);
                    self.h_INP_001 = ko.observable();
                    self.h_LST_001_items = ko.observableArray([]);
                    self.h_LST_001_itemsSelected = ko.observable();
                    self.yearMonthDateInJapanEmpire = ko.computed(function () {
                        if (self.h_INP_001() == undefined || self.h_INP_001() == null || self.h_INP_001() == "") {
                            return '';
                        }
                        return "(" + nts.uk.time.yearInJapanEmpire(moment(self.h_INP_001()).format('YYYY')).toString() +
                            moment(self.h_INP_001()).format('MM') + " 月 " + moment(self.h_INP_001()).format('DD') + " 日)";
                    });
                    self.processingDate = ko.observable(nts.uk.time.formatYearMonth(data.currentProcessingYm));
                    self.processingDateInJapanEmprire = ko.computed(function () {
                        return "(" + nts.uk.time.yearmonthInJapanEmpire(self.processingDate()).toString() + ")";
                    });
                    self.processingNo = ko.observable(data.processingNo);
                    self.processingName = ko.observable(data.processingName + ' )');
                    $.when(self.findAllBankBranch()).done(function () {
                        self.findAllLineBank().done(function () {
                            var _loop_1 = function(i_1) {
                                tmp = _.find(self.dataBankBranch2(), function (x) {
                                    return x.branchId === self.dataLineBank()[i_1].branchId;
                                });
                                self.h_LST_001_items.push(new ItemModel_H_LST_001(self.dataLineBank()[i_1].lineBankCode, tmp.code, self.dataLineBank()[i_1].lineBankName, tmp.name, self.dataLineBank()[i_1].accountAtr, self.dataLineBank()[i_1].accountNo, self.dataLineBank()[i_1].branchId));
                            };
                            var tmp;
                            for (var i_1 = 0; i_1 < self.dataLineBank().length; i_1++) {
                                _loop_1(i_1);
                            }
                        });
                    });
                }
                /**
                 * get data from DB BRANCH
                 */
                ScreenModel.prototype.findAllBankBranch = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qpp014.g.service.findAllBankBranch()
                        .done(function (dataBr) {
                        var bankData = [];
                        _.forEach(dataBr, function (item) {
                            var childs = _.map(item.bankBranch, function (itemChild) {
                                return new BankBranch(itemChild.bankBranchCode, itemChild.bankBranchID, itemChild.bankBranchName, item.bankCode, item.bankName, item.bankCode + itemChild.bankBranchCode, []);
                            });
                            bankData.push(new BankBranch(item.bankCode, null, item.bankName, null, null, item.bankCode, childs));
                        });
                        self.dataBankBranch(bankData);
                        self.dataBankBranch2(nts.uk.util.flatArray(self.dataBankBranch(), "childs"));
                        dfd.resolve();
                    })
                        .fail(function () {
                        dfd.reject();
                    });
                    return dfd.promise();
                };
                /**
                 * find all lineBank from DB LINE_BANK
                 */
                ScreenModel.prototype.findAllLineBank = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qpp014.g.service.findAllLineBank()
                        .done(function (dataLB) {
                        if (dataLB.length > 0) {
                            self.dataLineBank(dataLB);
                        }
                        else {
                            nts.uk.ui.dialog.alert("対象データがありません。"); //ER010
                        }
                        dfd.resolve();
                    })
                        .fail(function () {
                        dfd.reject();
                    });
                    return dfd.promise();
                };
                /**
                 * Print file PDF
                 */
                ScreenModel.prototype.saveAsPdf = function () {
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel_H_LST_001 = (function () {
                function ItemModel_H_LST_001(lineBankCode, branchCode, lineBankName, branchName, accountAtr, accountNo, branchId) {
                    this.lineBankCode = lineBankCode;
                    this.branchCode = branchCode;
                    this.lineBankName = lineBankName;
                    this.branchName = branchName;
                    this.accountAtr = accountAtr;
                    this.accountNo = accountNo;
                    this.branchId = branchId;
                    this.label = this.lineBankCode + ' - ' + this.branchCode;
                }
                return ItemModel_H_LST_001;
            }());
            viewmodel.ItemModel_H_LST_001 = ItemModel_H_LST_001;
            var BankBranch = (function () {
                function BankBranch(code, branchId, name, parentCode, parentName, treeCode, childs) {
                    var self = this;
                    self.code = code;
                    self.name = name;
                    self.nodeText = self.code + ' ' + self.name;
                    self.childs = childs;
                    self.parentCode = parentCode;
                    self.parentName = parentName;
                    self.treeCode = treeCode;
                    self.branchId = branchId;
                }
                return BankBranch;
            }());
        })(viewmodel = h.viewmodel || (h.viewmodel = {}));
    })(h = qpp014.h || (qpp014.h = {}));
})(qpp014 || (qpp014 = {}));
;
//# sourceMappingURL=qpp014.h.viewmodel.js.map