module qmm002.c.viewmodel {
    export class ScreenModel {
        lst_001: any;
        lst_002: any;
        lst_003: any;
        singleSelectedCode: any;
        selectedCodes: KnockoutObservableArray<any>;;
        selectedCodes2: any;
        constructor() {
            var self = this;
            self.lst_001 = ko.observableArray([]);
            self.lst_002 = ko.observableArray([]);
            self.lst_003 = ko.observableArray([]);
            self.singleSelectedCode = ko.observable();
            self.selectedCodes = ko.observableArray([]);
            self.selectedCodes2 = ko.observableArray([]);
            self.selectedCodes.subscribe(function(items) {
            });

            self.singleSelectedCode.subscribe(function(val) {
            });

        }

        startPage() {
            var self = this;
            var list = nts.uk.ui.windows.getShared('listItem');
            self.lst_001(list);
            self.lst_002(list);
        }

        closeDialog(): any {
            nts.uk.ui.windows.close();
        }

        tranferBranch() {
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
                _.forEach(self.selectedCodes(), function(item) {
                    if (item == self.singleSelectedCode()) {
                        nts.uk.ui.dialog.alert("統合元と統合先で同じコードの＊が選択されています。\r\n");
                    } else {
                        nts.uk.ui.dialog.confirm("統合元から統合先へデータを置換えます。\r\nよろしいですか？").ifYes(function() {
                            debugger;
                            var branchId = new Array();
                            _.forEach(self.selectedCodes(), function(item) {
                                var code = item.split('-');
                                var bankCode = code[0];
                                var branchIdNode = self.getNode(item, bankCode);
                                branchId.push(branchIdNode);
                            });
                            var code = self.singleSelectedCode().split('-');
                            var bankNewCode = code[0];
                            var branchNewId = self.getNode(self.singleSelectedCode(), bankNewCode);
                            var data =
                                {
                                    branchId: branchId,
                                    branchNewId: branchNewId
                                };

                            service.tranferBranch(data).done(function() {
                                //self.getBankList();
                            });
                        })
                    }
                });
            }
        }

        getNode(codeNew, parentId): String {
            var self = this;
            self.lst_003(nts.uk.util.flatArray(self.lst_001(), "childs"))
            var node = _.find(self.lst_003(), function(item: BankInfo) {
                return item.treeCode == codeNew;
            });

            //                if (parentId !== undefined) {
            //                    node = _.find(self.lst_003(), function(item: BankInfo) {
            //                        return item.treeCode == node.parentCode;
            //                    });
            //                }

            return node.branchId;
        }


        //        getBankList(): any {
        //            var self = this;
        //            var dfd = $.Deferred();
        //            service.getBankList().done(function(data) {
        //                var list001: Array<BankInfo> = [];
        //                _.forEach(data, function(itemBank) {
        //                    var childs = _.map(itemBank.bankBranch, function(item) {
        //                        return new BankInfo(itemBank.bankCode + "-" + item["bankBranchCode"], item["bankBranchCode"], item["bankBranchName"], item["bankBranchNameKana"], item["memo"], null, itemBank.bankCode);
        //                    });
        //
        //                    list001.push(new BankInfo(itemBank.bankCode, itemBank.bankCode, itemBank.bankName, itemBank.bankNameKana, itemBank.memo, childs, null));
        //                });
        //                self.lst_001(list001);
        //                dfd.resolve(list001);
        //            }).fail(function(res) {
        //                // error
        //            });
        //
        //            return dfd.promise();
        //        }
    }

    export class BankInfo {
        treeCode: string;
        code: string;
        branchId: string;
        name: string;
        displayName: string;
        nameKata: string;
        memo: string;
        childs: Array<BankInfo>;
        parentCode: string;

        constructor(treeCode?: string, code?: string, branchId?: string, name?: string, nameKata?: string, memo?: string, childs?: Array<BankInfo>, parentCode?: string) {
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
    }
};