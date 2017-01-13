module nts.uk.pr.view.qmm002_1.a {
    export module viewmodel {

        export class ScreenModel {

            currentEra: any;
            lst_001: any;
            filteredData: any;
            nodeParent: KnockoutObservable<Node>;
            singleSelectedCode: any;
            selectedCodes: any;
            parentNode: KnockoutObservable<Node>;
            A_INP_002: any;
            A_LBL_006: any;
            A_INP_003: any;
            A_INP_004: any;
            A_INP_005: any;


            constructor() {
                var self = this;
                self.lst_001 = ko.observableArray([]);
                self.filteredData = ko.observableArray([]);
                self.singleSelectedCode = ko.observable(null);
                self.selectedCodes = ko.observableArray([])
                self.currentEra = ko.observable();
                self.nodeParent = ko.observable(null);


                self.A_INP_005 = {
                    value: ko.observable(''),
                    constraint: 'ResidenceCode',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "",
                        width: "180px",
                        textalign: "left"
                    })),
                    required: ko.observable(true),
                    enable: ko.observable(true),
                    readonly: ko.observable(true)
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
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };

                self.A_LBL_006 = {
                    value: ko.observable(''),
                    constraint: 'ResidenceCode',
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

                self.currentEra = ko.observable(_.first(self.lst_001()));
                self.singleSelectedCode.subscribe(function(codeChanged) {
                    self.currentEra(self.getEra(codeChanged));
                    self.nodeParent(self.getEra(self.currentEra().parentcode))
                    if (self.nodeParent() == undefined) {
                        self.nodeParent(self.getEra(codeChanged))
                    }


                });

                self.A_INP_003 = {
                    value: ko.computed(function() {
                        if (self.currentEra() != undefined) {
                            if (self.currentEra().parentcode != "1") {
                                return !self.currentEra() ? '' : self.currentEra().code;
                            }
                        }
                        else {
                            return ko.observable('');
                        }
                    }),
                    constraint: 'ResidenceCode',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "",
                        width: "45px",
                        textalign: "left"
                    })),
                    required: ko.observable(true),
                    enable: ko.observable(false),
                    readonly: ko.observable(true)
                };

                self.A_INP_004 = {
                    value: ko.computed(function() {
                        if (self.currentEra() != undefined) {
                            if (self.currentEra().parentcode != "1") {
                                return !self.currentEra() ? '' : self.currentEra().name;
                            }
                        }
                        else {
                            return ko.observable('');
                        }
                    }),
                    constraint: 'ResidenceCode',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "",
                        width: "180px",
                        textalign: "left"
                    })),
                    required: ko.observable(true),
                    enable: ko.observable(true),
                    readonly: ko.observable(true)
                };


            }

            startPage() {
                var self = this;
                service.getBankList().done(function(result) {

                    self.lst_001(result);
                    self.filteredData(nts.uk.util.flatArray(result, "childs"));
                    if (result.length > 0) {
                        self.singleSelectedCode(result[0].code);
                    }
                });
            }

            OpenBdialog(): any {
                var self = this;
                nts.uk.ui.windows.sub.modal("/view/qmm/002/b/index.xhtml", { title: "銀行の登録　＞　一括削除" });
                nts.uk.ui.windows.setShared('listItem', self.lst_001);
            }

            OpenCdialog(): any {
                nts.uk.ui.windows.sub.modal("/view/qmm/002/c/index.xhtml", { title: "銀行の登録　＞　銀行の統合" });
            }

            OpenDdialog(): any {
                nts.uk.ui.windows.sub.modal("/view/qmm/002/d/index.xhtml", { title: "銀行の登録　＞　銀行の追加" });
            }


            getEra(codeNew): Node {
                var self = this;
                var node = _.find(self.filteredData(), function(item: Node) {
                    return item.code == codeNew;
                });
                return node;
            }

            deleteItem() {
                var self = this;
                self.lst_001.remove(self.currentEra());
                var i = self.nodeParent().childs.indexOf(self.currentEra());
                if (i != -1) {
                    self.nodeParent().childs.splice(i, 1);
                    var tempNodeParent = self.nodeParent();
                    var tempLst001 = self.lst_001();
                    self.nodeParent(tempNodeParent);
                    self.lst_001(tempLst001)
                }
                console.log(self.lst_001());
            }
        }


        export class BankInfo {
            code: string;
            name: string;
            nameKata: string;
            memo: string;
            child: BankInfo;

            constructor(code: string, name: string, nameKata: string, memo: string, child: BankInfo) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nameKata = nameKata;
                self.memo = memo;
                self.child = child;
            }
        }

        export class Node {
            code: string;
            parentcode: string;
            name: string;
            nodeText: string;
            childs: any;
            constructor(code: string, parentcode: string, name: string, childs: Array<Node>) {
                var self = this;
                self.code = code;
                self.parentcode = parentcode;
                self.name = name;
                self.nodeText = self.code + ' ' + self.name;
                self.childs = childs;
            }
        }
    }

}