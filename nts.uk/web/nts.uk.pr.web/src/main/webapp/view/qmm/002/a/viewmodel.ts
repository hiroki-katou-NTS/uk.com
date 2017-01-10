module nts.uk.pr.view.qmm002_1.a {
    export module viewmodel {
        
      export class ScreenModel {
            
            currentEra: any;
            lst_001: any;
            filteredData: any;
            singleSelectedCode: any;
            selectedCodes: any;
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
                
                 self.A_INP_003 = {
                    value: ko.observable(''),
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
                });
            }
            
            startPage() {
                var self = this;
                
                service.getBankList().done(function(result) {
                    self.lst_001(result);
                    self.filteredData(nts.uk.util.flatArray(result,"childs"))
                    if (result.length > 0) {
                        self.singleSelectedCode(result[0].code);
                    }
                });        
            }
            
            OpenBdialog(): any
            {
                nts.uk.ui.windows.sub.modal("/view/qmm/002/b/index.xhtml",{title:"銀行の登録　＞　一括削除"});
            }
            
            getEra(codeNew): BankInfo {
                var self = this;
                var node = _.find(self.lst_001(), function(item:Node) {
                    return item.code == codeNew;
                });
                
                if (!node) {
                    return new BankInfo("", "", "", "", null);
                }
                
                return new BankInfo(node.code, node.name, "", "", null);
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
            name: string;
            nodeText: string;
            childs: any;
            constructor(code: string, name: string, childs: Array<Node>) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nodeText = self.code + ' ' + self.name;
                self.childs = childs;
            }
        }
    }
    
}