var qpp014;
(function (qpp014) {
    var j;
    (function (j) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                self.dataBankBranch = ko.observableArray([]);
                self.dataBankBranch2 = ko.observableArray([]);
                self.dataLineBank = ko.observableArray([]);
                self.itemList_J_SEL_001 = ko.observableArray([
                    new BoxModel_J_SEL_001(0, '銀行集計'),
                    new BoxModel_J_SEL_001(1, '明細出力')
                ]);
                self.selectedId_J_SEL_001 = ko.observable(0);
                self.itemList_J_SEL_002 = ko.observableArray([
                    new ItemModel_J_SEL_002('振込先順 '),
                    new ItemModel_J_SEL_002('個人　コード順'),
                ]);
                self.selectedCode_J_SEL_002 = ko.observable(self.itemList_J_SEL_002()[0]);
                self.itemList_J_SEL_003 = ko.observableArray([
                    new ItemModel_J_SEL_002('漢字出力'),
                    new ItemModel_J_SEL_002('カナ出力'),
                ]);
                self.selectedCode_J_SEL_003 = ko.observable(self.itemList_J_SEL_003()[0]);
                self.isEnable = ko.observable(false);
                self.items_J_LST_001 = ko.observableArray([]);
                self.currentCode_J_LST_001 = ko.observable();
                self.currentCode_J_SEL_004 = ko.observable(1);
                self.currentProcessingYm = nts.uk.time.parseYearMonth(nts.uk.ui.windows.getShared("data").currentProcessingYm).format() + "(" +
                    nts.uk.time.yearmonthInJapanEmpire(nts.uk.time.parseYearMonth(nts.uk.ui.windows.getShared("data").currentProcessingYm).format()) + ")";
                self.dateOfPayment = ko.observable(moment(nts.uk.ui.windows.getShared("dateOfPayment")).format("YYYY/MM/DD") +
                    "(" + nts.uk.time.yearmonthInJapanEmpire(moment(nts.uk.ui.windows.getShared("dateOfPayment")).format("YYYY/MM")).toString() + " " +
                    moment(nts.uk.ui.windows.getShared("dateOfPayment")).format("DD") + " 日)");
                self.isEnable = ko.computed(function () {
                    return self.selectedId_J_SEL_001() == 0 ? false : true;
                });
                if (nts.uk.ui.windows.getShared("sparePayAtr") != 3) {
                    $('#J_SEL_004').css('display', 'none');
                    $('#J_LBL_009').css('display', 'none');
                }
                else {
                    $('#J_SEL_004').css('display', '');
                    $('#J_LBL_009').css('display', '');
                }
                $.when(self.findAllBankBranch()).done(function () {
                    self.findAllLineBank().done(function () {
                        var _loop_1 = function(i_1) {
                            tmp = _.find(self.dataBankBranch2(), function (x) {
                                return x.branchId === self.dataLineBank()[i_1].branchId;
                            });
                            self.items_J_LST_001.push(new ItemModel_J_LST_001(self.dataLineBank()[i_1].lineBankCode, tmp.code, self.dataLineBank()[i_1].lineBankName, tmp.name, self.dataLineBank()[i_1].accountAtr, self.dataLineBank()[i_1].accountNo, self.dataLineBank()[i_1].branchId));
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
             * Close dialog
             */
            ScreenModel.prototype.closeDialog = function () {
                nts.uk.ui.windows.close();
            };
            /**
             * Print file PDF
             */
            ScreenModel.prototype.saveAsPdf = function () {
                var self = this;
                if (self.currentCode_J_LST_001() == undefined || self.currentCode_J_LST_001().length < 1) {
                    nts.uk.ui.dialog.alert("振込元銀行が選択されていません。"); //ER007
                }
                else {
                    var branchIdList = [];
                    var _loop_2 = function(i_2) {
                        branchIdList.push(_.find(self.items_J_LST_001(), function (x) {
                            return x.label === self.currentCode_J_LST_001()[i_2];
                        }).branchId);
                    };
                    for (var i_2 = 0; i_2 < self.currentCode_J_LST_001().length; i_2++) {
                        _loop_2(i_2);
                    }
                    var command = {
                        fromBranchId: branchIdList,
                        processingNo: nts.uk.ui.windows.getShared("processingNo"),
                        processingYm: nts.uk.ui.windows.getShared("data").currentProcessingYm,
                        payDate: moment.utc(nts.uk.ui.windows.getShared("dateOfPayment"), "YYYY/MM/DD").toISOString(),
                        sparePayAtr: nts.uk.ui.windows.getShared("sparePayAtr"),
                        selectedId_J_SEL_001: self.selectedId_J_SEL_001(),
                        currentCode_J_SEL_004: self.currentCode_J_SEL_004(),
                        transferDate: nts.uk.time.yearmonthInJapanEmpire(moment(nts.uk.ui.windows.getShared("dateOfPayment")).format("YYYY/MM")).toString() + " "
                            + moment(nts.uk.ui.windows.getShared("dateOfPayment")).format("DD") + " 日"
                    };
                    if (self.selectedId_J_SEL_001() == 1) {
                        qpp014.j.service.saveAsPdfA(command)
                            .done(function () { })
                            .fail(function (error) {
                            if (error.messageId == 'ER010') {
                                nts.uk.ui.dialog.alert("対象データがありません。");
                            }
                        });
                    }
                    else {
                        qpp014.j.service.saveAsPdfB(command)
                            .done(function () { })
                            .fail(function (error) {
                            if (error.messageId == 'ER010') {
                                nts.uk.ui.dialog.alert("対象データがありません。");
                            }
                        });
                    }
                }
            };
            return ScreenModel;
        }());
        j.ScreenModel = ScreenModel;
        var BoxModel_J_SEL_001 = (function () {
            function BoxModel_J_SEL_001(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
            return BoxModel_J_SEL_001;
        }());
        j.BoxModel_J_SEL_001 = BoxModel_J_SEL_001;
        var ItemModel_J_SEL_002 = (function () {
            function ItemModel_J_SEL_002(name) {
                this.name = name;
            }
            return ItemModel_J_SEL_002;
        }());
        j.ItemModel_J_SEL_002 = ItemModel_J_SEL_002;
        var ItemModel_J_LST_001 = (function () {
            function ItemModel_J_LST_001(lineBankCode, branchCode, lineBankName, branchName, accountAtr, accountNo, branchId) {
                this.lineBankCode = lineBankCode;
                this.branchCode = branchCode;
                this.lineBankName = lineBankName;
                this.branchName = branchName;
                this.accountAtr = accountAtr;
                this.accountNo = accountNo;
                this.branchId = branchId;
                this.label = this.lineBankCode + ' - ' + this.branchCode;
            }
            return ItemModel_J_LST_001;
        }());
        j.ItemModel_J_LST_001 = ItemModel_J_LST_001;
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
    })(j = qpp014.j || (qpp014.j = {}));
})(qpp014 || (qpp014 = {}));
;
//# sourceMappingURL=qpp014.j.viewmodel.js.map