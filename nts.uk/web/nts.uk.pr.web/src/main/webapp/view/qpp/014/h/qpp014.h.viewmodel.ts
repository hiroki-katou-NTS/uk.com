// TreeGrid Node
module qpp014.h.viewmodel {
    export class ScreenModel {
        dataBankBranch: KnockoutObservableArray<any>;
        dataBankBranch2: KnockoutObservableArray<any>;
        dataLineBank: KnockoutObservableArray<any>;
        h_INP_001: KnockoutObservable<any>;
        h_LST_001_items: KnockoutObservableArray<ItemModel_H_LST_001>;
        h_LST_001_itemsSelected: KnockoutObservable<any>;
        yearMonthDateInJapanEmpire: any;
        processingDate: any;
        processingDateInJapanEmprire: any;
        processingNo: any;
        processingName: any;

        constructor(data: any) {
            let self = this;
            self.dataBankBranch = ko.observableArray([]);
            self.dataBankBranch2 = ko.observableArray([]);
            self.dataLineBank = ko.observableArray([]);
            self.h_INP_001 = ko.observable();
            self.h_LST_001_items = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                self.h_LST_001_items.push(new ItemModel_H_LST_001('00' + i, '基本給', "description " + i));
            }
            self.h_LST_001_itemsSelected = ko.observable();
            self.yearMonthDateInJapanEmpire = ko.computed(function() {
                if (self.h_INP_001() == undefined || self.h_INP_001() == null || self.h_INP_001() == "") {
                    return '';
                }
                return "(" + nts.uk.time.yearInJapanEmpire(moment(self.h_INP_001()).format('YYYY')).toString() +
                    moment(self.h_INP_001()).format('MM') + " 月 " + moment(self.h_INP_001()).format('DD') + " 日)";
            });
            self.processingDate = ko.observable(nts.uk.time.formatYearMonth(data.currentProcessingYm));
            self.processingDateInJapanEmprire = ko.computed(function() {
                return "(" + nts.uk.time.yearmonthInJapanEmpire(self.processingDate()).toString() + ")";
            });
            self.processingNo = ko.observable(data.processingNo + ' : ');
            self.processingName = ko.observable(data.processingName + ' )');
            
            $.when(self.findAllBankBranch()).done(function() {
                self.findAllLineBank().done(function() { });
            });
        }

        /**
         * get data from DB BRANCH
         */
        findAllBankBranch(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            qpp014.g.service.findAllBankBranch()
                .done(function(dataBr) {
                    var bankData = [];
                    _.forEach(dataBr, function(item) {
                        var childs = _.map(item.bankBranch, function(itemChild: any) {
                            return new BankBranch(itemChild.bankBranchCode, itemChild.bankBranchID, itemChild.bankBranchName, item.bankCode, item.bankName, item.bankCode + itemChild.bankBranchCode, []);
                        });
                        bankData.push(new BankBranch(item.bankCode, null, item.bankName, null, null, item.bankCode, childs));
                    });
                    self.dataBankBranch(bankData);
                    self.dataBankBranch2(nts.uk.util.flatArray(self.dataBankBranch(), "childs"));
                    dfd.resolve();
                })
                .fail(function() {
                    dfd.reject();
                });
            return dfd.promise();
        }

        /**
         * find all lineBank from DB LINE_BANK
         */
        findAllLineBank(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            qpp014.g.service.findAllLineBank()
                .done(function(dataLB) {
                    if (dataLB.length > 0) {
                        self.dataLineBank(dataLB);
                    } else {
                        nts.uk.ui.dialog.alert("対象データがありません。");//ER010
                    }
                    dfd.resolve();
                })
                .fail(function() {
                    dfd.reject();
                });
            return dfd.promise();
        }
    }

    export class ItemModel_H_LST_001 {
        code: string;
        name: string;
        description: string;

        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
    }
    class BankBranch {
        code: string;
        name: string;
        nodeText: string;
        parentCode: string;
        parentName: string;
        treeCode: string;
        childs: any;
        branchId: string;
        constructor(code: string, branchId: string, name: string, parentCode: string, parentName: string, treeCode: string, childs: Array<BankBranch>) {
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
    }

};
