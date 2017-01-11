module qmm010.a.viewmodel {
    import option = nts.uk.ui.option;
    export class ScreenModel {
        laborInsuranceOffice: KnockoutObservable<LaborInsuranceOffice>;
        textSearch: any;
        ainp001: KnockoutObservable<string>;
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        ainp004: KnockoutObservable<string>;
        ainp005: KnockoutObservable<string>;
        postalCode: KnockoutObservable<string>;
        ainp007: KnockoutObservable<string>;
        address1st: KnockoutObservable<string>;
        kanaAddress1st: KnockoutObservable<string>;
        address2nd: KnockoutObservable<string>;
        kanaAddress2nd: KnockoutObservable<string>;
        phoneNumber: KnockoutObservable<string>;
        ainp013: KnockoutObservable<string>;
        ainp014: KnockoutObservable<string>;
        officeNoA: KnockoutObservable<string>;
        officeNoB: KnockoutObservable<string>;
        officeNoC: KnockoutObservable<string>;
        memo: KnockoutObservable<string>;
        multilineeditor: any;
        employmentName: KnockoutObservable<string>;
        textEditorOption: KnockoutObservable<any>;
        items: KnockoutObservableArray<viewmodel.ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        constructor() {
            var self = this;
            self.laborInsuranceOffice = ko.observable(new LaborInsuranceOffice('code', 'name', 'postalCode', 'address1st', 'address2nd', 'kanaAddress1st', 'kanaAddress2nd', 'phoneNumber', 'officeNoA', 'officeNoB', 'officeNoC','memo'));
            self.ainp001 = ko.observable("");
            self.code = ko.observable(self.laborInsuranceOffice().code);
            self.name = ko.observable(self.laborInsuranceOffice().name);
            self.ainp004 = ko.observable("");
            self.ainp005 = ko.observable("");
            self.postalCode = ko.observable(self.laborInsuranceOffice().postalCode);
            self.ainp007 = ko.observable("");
            self.address1st = ko.observable(self.laborInsuranceOffice().address1st);
            self.kanaAddress1st = ko.observable(self.laborInsuranceOffice().kanaAddress1st);
            self.address2nd = ko.observable(self.laborInsuranceOffice().address2nd);
            self.kanaAddress2nd = ko.observable(self.laborInsuranceOffice().kanaAddress2nd);
            self.phoneNumber = ko.observable(self.laborInsuranceOffice().phoneNumber);
            self.ainp013 = ko.observable("");
            self.ainp014 = ko.observable("");
            self.officeNoA = ko.observable(self.laborInsuranceOffice().officeNoA);
            self.officeNoB = ko.observable(self.laborInsuranceOffice().officeNoB);
            self.officeNoC = ko.observable(self.laborInsuranceOffice().officeNoC);
            self.memo = ko.observable(self.laborInsuranceOffice().memo);
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
            self.items = ko.observableArray([
                new ItemModel('001', '基本給'),
                new ItemModel('150', '役職手当'),
                new ItemModel('ABC', '基12本ghj給')
            ]);
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 100 },
                { headerText: '名称', prop: 'name', width: 150 }
            ]);
            self.currentCode = ko.observable();
            self.currentCodeList = ko.observableArray([]);
            self.multilineeditor = {
                memo: ko.observable(self.laborInsuranceOffice().memo),
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
    export class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export class LaborInsuranceOffice {
        code: string;
        name: string;
        postalCode: string;
        address1st: string;
        address2nd: string;
        kanaAddress1st: string;
        kanaAddress2nd: string;
        phoneNumber: string;
        officeNoA: string;
        officeNoB: string;
        officeNoC: string;
        memo: string;
        constructor(code: string, name: string, postalCode: string, address1st: string, address2nd: string,
            kanaAddress1st: string, kanaAddress2nd: string, phoneNumber: string, officeNoA: string, officeNoB: string, officeNoC: string, memo: string) {
            this.code = code;
            this.name = name;
            this.postalCode = postalCode;
            this.address1st = address1st;
            this.address2nd = address2nd;
            this.kanaAddress1st = kanaAddress1st;
            this.kanaAddress2nd = kanaAddress2nd;
            this.phoneNumber = phoneNumber;
            this.officeNoA = officeNoA;
            this.officeNoB = officeNoB;
            this.officeNoC = officeNoC;
            this.memo = memo;
        }
    }
}
