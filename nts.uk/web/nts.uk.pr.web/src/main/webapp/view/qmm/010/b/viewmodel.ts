module qmm010.b.viewmodel {
    import option = nts.uk.ui.option;

    export class ScreenModel {
        textSearch: any;
        ainp002: KnockoutObservable<string>;
        ainp003: KnockoutObservable<string>;
        ainp004: KnockoutObservable<string>;
        ainp005: KnockoutObservable<string>;
        ainp006: KnockoutObservable<string>;
        ainp007: KnockoutObservable<string>;
        ainp008: KnockoutObservable<string>;
        ainp009: KnockoutObservable<string>;
        ainp010: KnockoutObservable<string>;
        ainp011: KnockoutObservable<string>;
        ainp012: KnockoutObservable<string>;
        ainp013: KnockoutObservable<string>;
        ainp014: KnockoutObservable<string>;
        ainp015: KnockoutObservable<string>;
        ainp016: KnockoutObservable<string>;
        ainp017: KnockoutObservable<string>;
        
        multilineeditor: any;
        employmentName: KnockoutObservable<string>;
        textEditorOption: KnockoutObservable<any>;
        blst001: KnockoutObservableArray<BItemModelLST001>;
        blstsel001: KnockoutObservable<string>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        constructor() {
            var self = this;
            self.ainp001 = ko.observable("");
            self.ainp002 = ko.observable("");
            self.ainp003 = ko.observable("");
            self.ainp004 = ko.observable("");
            self.ainp005 = ko.observable("");
            self.ainp006 = ko.observable("");
            self.ainp007 = ko.observable("");
            self.ainp008 = ko.observable("");
            self.ainp009 = ko.observable("");
            self.ainp010 = ko.observable("");
            self.ainp011 = ko.observable("");
            self.ainp012 = ko.observable("");
            self.ainp013 = ko.observable("");
            self.ainp014 = ko.observable("");
            self.ainp015 = ko.observable("");
            self.ainp016 = ko.observable("");
            self.ainp017 = ko.observable("");
            
            self.employmentName = ko.observable("");
            self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
            self.textSearch = {
                valueSearch: ko.observable(""),
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "コード・名称で検索・・・",
                    width: "75%",
                    textalign: "left"
                }))
            }
            self.blst001 = ko.observableArray([
                new BItemModelLST001('001', '基本給'),
                new BItemModelLST001('150', '役職手当'),
                new BItemModelLST001('ABC', '基12本ghj給')
            ]);
            self.blstsel001 = ko.observable("");
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 100 },
                { headerText: '名称', prop: 'name', width: 150 }
            ]);
            self.currentCode = ko.observable();
            self.currentCodeList = ko.observableArray([]);
            self.multilineeditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                    resizeable: true,
                    placeholder: "Placeholder for text editor",
                    width: "",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
        }
        selectSomeItems() {
            this.currentCode('150');
            this.currentCodeList.removeAll();
            this.currentCodeList.push('001');
            this.currentCodeList.push('ABC');
        }

        deselectAll() {
            this.currentCode(null);
            this.currentCodeList.removeAll();
        }
    }

    export class ItemCloseDate {
        closeDateCode: number;
        closeDatename: string;
        constructor(closeDateCode: number, closeDatename: string) {
            this.closeDateCode = closeDateCode;
            this.closeDatename = closeDatename;
        }
    }

    export class ItemProcessingDate {
        processingDateCode: number;
        processingDatename: string;
        constructor(processingDateCode: number, processingDatename: string) {
            this.processingDateCode = processingDateCode;
            this.processingDatename = processingDatename;
        }
    }
    export class BItemModelLST001 {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}
