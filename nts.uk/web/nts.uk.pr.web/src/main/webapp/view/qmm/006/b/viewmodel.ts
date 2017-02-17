module qmm006.b.viewmodel {
    export class ScreenModel {
        dataSource: any;
        dataSource2: any;
        singleSelectedCode: KnockoutObservable<any>;
        selectedBank: KnockoutObservable<any>;
        selectedCodes: any;
        headers: any;

        constructor() {
            var self = this;
            self.dataSource = ko.observableArray([]),
            self.dataSource2 = ko.observableArray([]),
            self.singleSelectedCode = ko.observable();
            self.selectedBank = ko.observable();
            self.selectedCodes = ko.observableArray([]);
            self.headers = ko.observableArray(["Item Value Header", "コード/名称"]);
            self.singleSelectedCode.subscribe(function(codeChanged) {
                self.selectedBank(self.getBank(codeChanged));
            });
        }

        getBank(codeNew): Bank {
            let self = this;
            self.dataSource2(nts.uk.util.flatArray(self.dataSource(), "childs"));
            let bank: Bank = _.find(self.dataSource2(), function(item: any) {
                return item.treeCode === codeNew;
            });
            return bank;
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.findBankAll().done(function(data) {
                dfd.resolve();
            }).fail(function(res) {
            });
            return dfd.promise();
        }

        findBankAll() {
            var self = this;
            var dfd = $.Deferred();
            qmm006.b.service.findBankAll()
                .done(function(data) {
                    if (data.length > 0) {
                        var bankData = [];
                        _.forEach(data, function(item) {
                            var childs = _.map(item.bankBranch, function(itemChild: any) {
                                return new Bank(itemChild.bankBranchCode, itemChild.bankBranchName, item.bankCode, item.bankName, item.bankCode + itemChild.bankBranchCode, []);
                            });
                            bankData.push(new Bank(item.bankCode, item.bankName, null, null, item.bankCode, childs));
                        });
                        self.dataSource(bankData);
                    }
                    dfd.resolve();
                }).fail(function(res) { });
            return dfd.promise();
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }
        transferData() {
            nts.uk.ui.windows.setShared("selectedBank", this.selectedBank(), true);
            nts.uk.ui.windows.close();
        }
    }

    class Bank {
        code: string;
        name: string;
        nodeText: string;
        parentCode: string;
        parentName: string;
        treeCode: string;
        childs: any;
        constructor(code: string, name: string, parentCode: string, parentName: string, treeCode: string, childs: Array<Bank>) {
            var self = this;
            self.code = code;
            self.name = name;
            self.nodeText = self.code + ' ' + self.name;
            self.childs = childs;
            self.parentCode = parentCode;
            self.parentName = parentName;
            self.treeCode = treeCode;
        }
    }
};