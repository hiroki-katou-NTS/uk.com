var cmm013;
(function (cmm013) {
    var test;
    (function (test) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.lst_001 = ko.observableArray([]);
                    self.filteredData = ko.observableArray([]);
                    self.singleSelectedCode = ko.observable(null);
                    self.selectedCodes = ko.observableArray([]);
                    self.currentEra = ko.observable();
                    self.nodeParent = ko.observable(null);
                    self.lst_002 = ko.observableArray([]);
                    self.isCreated = ko.observable(false);
                    self.A_INP_005 = {
                        value: ko.observable(''),
                        constraint: 'BankBranchNameKana',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "180px",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(),
                        readonly: ko.observable(false)
                    };
                    self.A_INP_002 = {
                        value: ko.observable(''),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                            resizeable: true,
                            placeholder: "",
                            width: "250px",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(false),
                        readonly: ko.observable(false)
                    };
                    self.A_INP_006 = {
                        value: ko.observable(''),
                        constraint: 'Memo',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                            resizeable: true,
                            placeholder: "",
                            width: "400px",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                    self.currentEra = ko.observable(''),
                        self.singleSelectedCode.subscribe(function (codeChanged) {
                            var x = self.getEra(codeChanged, undefined);
                            if (x.parentCode !== null) {
                                self.currentEra(x);
                                self.nodeParent(self.getEra(codeChanged, x.parentCode));
                            }
                            else {
                                self.nodeParent(x);
                                self.currentEra(new BankInfo());
                            }
                            self.A_INP_003.value(self.currentEra().code);
                            self.A_INP_004.value(self.currentEra().name);
                            self.A_INP_005.value(self.currentEra().nameKata);
                            self.A_INP_006.value(self.currentEra().memo);
                            self.A_INP_003.enable(false);
                            self.isCreated(false);
                        });
                    self.A_INP_003 = {
                        value: ko.observable(''),
                        constraint: 'BankBranchCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "45px",
                            textalign: "left"
                        })),
                        required: ko.observable(false),
                        enable: ko.observable(false),
                        readonly: ko.observable(false)
                    };
                    self.A_INP_004 = {
                        value: ko.observable(''),
                        constraint: 'BankBranchName',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "180px",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    $.when(self.getBankList()).done(function () {
                        self.singleSelectedCode(self.lst_001()[0].code);
                    });
                };
                ScreenModel.prototype.OpenBdialog = function () {
                    var self = this;
                    nts.uk.ui.windows.sub.modal("/view/qmm/002/b/index.xhtml", { title: "銀行の登録　＞　一括削除" });
                    nts.uk.ui.windows.setShared('listItem', self.lst_001);
                };
                ScreenModel.prototype.OpenCdialog = function () {
                    nts.uk.ui.windows.sub.modal("/view/qmm/002/c/index.xhtml", { title: "銀行の登録　＞　銀行の統合" });
                };
                ScreenModel.prototype.OpenDdialog = function () {
                    nts.uk.ui.windows.sub.modal("/view/qmm/002/d/index.xhtml", { title: "銀行の登録　＞　銀行の追加" });
                };
                ScreenModel.prototype.addBranch = function () {
                    var self = this;
                    var branchInfo = {
                        bankCode: self.nodeParent().code,
                        branchCode: self.A_INP_003.value(),
                        branchName: self.A_INP_004.value(),
                        branchKnName: self.A_INP_005.value(),
                        memo: self.A_INP_006.value()
                    };
                    var dfd = $.Deferred();
                    service.addBank(self.isCreated(), branchInfo).done(function () {
                        // reload tree
                        self.getBankList();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                };
                ScreenModel.prototype.getEra = function (codeNew, parentId) {
                    var self = this;
                    self.lst_002(nts.uk.util.flatArray(self.lst_001(), "childs"));
                    var node = _.find(self.lst_002(), function (item) {
                        return item.code == codeNew;
                    });
                    if (parentId !== undefined) {
                        node = _.find(self.lst_002(), function (item) {
                            return item.code == node.parentCode;
                        });
                    }
                    return node;
                };
                //            deleteItem() {
                //                var self = this;
                //                debugger
                //                self.lst_001.remove(self.currentEra());
                //                var i = self.nodeParent().childs.indexOf(self.currentEra());
                //                if (i != -1) {
                //                    self.nodeParent().childs.splice(i, 1);
                //                    var tempNodeParent = self.nodeParent();
                //                    var tempLst001 = self.lst_001();
                //                    self.nodeParent(tempNodeParent);
                //        self.lst_001(tempLst001)
                //                }
                //                console.log(self.lst_001());
                //            }
                ScreenModel.prototype.getBankList = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    service.getBankList().done(function (data) {
                        var list001 = [];
                        _.forEach(data, function (itemBank) {
                            var childs = _.map(itemBank.bankBranch, function (item) {
                                return new BankInfo(item["bankBrandCode"], item["bankBrandName"], item["bankBrandNameKana"], item["memo"], null, itemBank.bankCode);
                            });
                            list001.push(new BankInfo(itemBank.bankCode, itemBank.bankName, itemBank.bankNameKana, itemBank.memo, childs, null));
                        });
                        self.lst_001(list001);
                        dfd.resolve(list001);
                    }).fail(function (res) {
                        // error
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.cleanBranch = function () {
                    var self = this;
                    self.A_INP_003.value('');
                    self.A_INP_004.value('');
                    self.A_INP_005.value('');
                    self.A_INP_003.enable(true);
                    self.isCreated(true);
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var BankInfo = (function () {
                function BankInfo(code, name, nameKata, memo, childs, parentCode) {
                    var self = this;
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
            var Node = (function () {
                function Node(code, name, childs) {
                    var self = this;
                    self.code = code;
                    self.name = name;
                    self.nodeText = self.code + ' ' + self.name;
                    self.childs = childs;
                }
                return Node;
            }());
            viewmodel.Node = Node;
        })(viewmodel = test.viewmodel || (test.viewmodel = {}));
    })(test = cmm013.test || (cmm013.test = {}));
})(cmm013 || (cmm013 = {}));
