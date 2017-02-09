module qmm002.c.viewmodel {
    export class ScreenModel {
        lst_001: any;
        lst_002: any;
        singleSelectedCode: any;
        selectedCodes: KnockoutObservableArray<any>;;
        selectedCodes2: any;
        constructor() {
            var self = this;
            self.lst_001 = ko.observableArray([]);
            self.lst_002 = ko.observableArray([]);
            self.singleSelectedCode = ko.observable();
            self.selectedCodes = ko.observableArray([]);
            self.selectedCodes2 = ko.observableArray([]);
            self.selectedCodes.subscribe(function(items) {
                console.log(items);
            });

            self.singleSelectedCode.subscribe(function(val) {
                console.log(val);
            });

        }

        startPage() {
            var self = this;
            var list = nts.uk.ui.windows.getShared('listItem');
            self.lst_001(list);
            console.log(self.lst_001());
            self.lst_002(list);
        }

        closeDialog(): any {
            nts.uk.ui.windows.close();
        }

        tranferBranch() {
            var self = this;
            var branchCodesMap = [];
            _.forEach(self.selectedCodes(), function(item) {
                var code = item.split('-');
                var bankCode = code[0];
                var branchCode = code[1];
                branchCodesMap.push({
                    bankCode: bankCode,
                    branchCode: branchCode
                });
            });

            var data =
                {
                    branchCodes: branchCodesMap,
                    bankNewCode: self.singleSelectedCode()
                };

            service.tranferBranch(data).done(function() {
                self.getBankList();
            });
        }

        getBankList(): any {
            var self = this;
            var dfd = $.Deferred();
            service.getBankList().done(function(data) {
                var list001: Array<BankInfo> = [];
                _.forEach(data, function(itemBank) {
                    var childs = _.map(itemBank.bankBranch, function(item) {
                        return new BankInfo(itemBank.bankCode + "-" + item["bankBranchCode"], item["bankBranchCode"], item["bankBranchName"], item["bankBranchNameKana"], item["memo"], null, itemBank.bankCode);
                    });

                    list001.push(new BankInfo(itemBank.bankCode, itemBank.bankCode, itemBank.bankName, itemBank.bankNameKana, itemBank.memo, childs, null));
                });
                self.lst_001(list001);
                dfd.resolve(list001);
            }).fail(function(res) {
                // error
            });

            return dfd.promise();
        }
    }

    export class BankInfo {
        treeCode: string;
        code: string;
        name: string;
        nameKata: string;
        memo: string;
        childs: Array<BankInfo>;
        parentCode: string;

        constructor(treeCode?: string, code?: string, name?: string, nameKata?: string, memo?: string, childs?: Array<BankInfo>, parentCode?: string) {
            var self = this;
            self.treeCode = treeCode;
            self.code = code;
            self.name = name;
            self.nameKata = nameKata;
            self.memo = memo;
            self.childs = childs;
            self.parentCode = parentCode;
        }
    }
};