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

        startPage() {
            var self = this;
            var dfd = $.Deferred();
            self.findBankAll().done(function() {
                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);
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
                        //select first row child of first row parent
                        if (data[0].bankBranch != null) {
                            self.singleSelectedCode(data[0].bankCode + data[0].bankBranch[0].bankBranchCode);
                        }
                        //select first row parent
                        else {
                            self.singleSelectedCode(data[0].bankCode);
                        }
                    }

                    dfd.resolve();
                }).fail(function(res) { });
            return dfd.promise();
        }

        closeDialog() {
            nts.uk.ui.windows.setShared("selectedBank", null, true);
            nts.uk.ui.windows.close();
        }

        transferData() {
            var self = this;
            //define row selected
            if (_.find(self.dataSource(), function(x: any) {
                return x.treeCode === self.singleSelectedCode();
            }) == undefined) {
                // select row child will transfer data to screen QMM006.a
                nts.uk.ui.windows.setShared("selectedBank", this.selectedBank(), true);
                nts.uk.ui.windows.close();
            } else {
                // select row parent will appear alert
                nts.uk.ui.dialog.alert("＊が選択されていません。");//ER007
            }
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