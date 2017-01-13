module nts.uk.pr.view.qmm010.a {
    import option = nts.uk.ui.option;
    import LaborInsuranceOffice = service.model.LaborInsuranceOffice;
    import LaborInsuranceOfficeInDTO = service.model.LaborInsuranceOfficeInDTO;
    export module viewmodel {
        export class ScreenModel {
            //ojbect value binding
            laborInsuranceOffice: KnockoutObservable<LaborInsuranceOfficeModel>;
            lstlaborInsuranceOffice: KnockoutObservableArray<LaborInsuranceOffice>;
            columnsLstlaborInsuranceOffice: KnockoutObservableArray<any>;
            selectCodeLstlaborInsuranceOffice: KnockoutObservable<string>;
            textSearch: any;
            constructor() {
                var self = this;
                var officeInfo = new LaborInsuranceOffice('companyCode002', '000000000002', 'B事業所', 'shortName', 'picName', 'picPosition', 'potalCode', 'prefecture', 'address1st', 'address2nd', 'kanaAddress1st', 'kanaAddress2nd', 'phoneNumber', '01', 'officeMark', '1234', '567890', '1', 'memo');
                self.laborInsuranceOffice = ko.observable(new LaborInsuranceOfficeModel(officeInfo));
                self.lstlaborInsuranceOffice = ko.observableArray([new LaborInsuranceOfficeInDTO('companyCode001', '000000000001', 'A事業所'),
                    new LaborInsuranceOfficeInDTO('companyCode002', '000000000002', 'B事業所'), new LaborInsuranceOfficeInDTO('companyCode003', '000000000003', 'C事業所')]);
                self.textSearch = {
                    valueSearch: ko.observable(""),
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "コード・名称で検索・・・",
                        width: "270",
                        textalign: "left"
                    }))
                }
                self.columnsLstlaborInsuranceOffice = ko.observableArray([
                    { headerText: 'コード', prop: 'code', width: 120 },
                    { headerText: '名称', prop: 'name', width: 120 }
                ]);
                self.selectCodeLstlaborInsuranceOffice = ko.observable('');
            }
        }

        export class LaborInsuranceOfficeModel {
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            shortName: KnockoutObservable<string>;
            picName: KnockoutObservable<string>;
            picPosition: KnockoutObservable<string>;
            postalCode: KnockoutObservable<string>;
            address1st: KnockoutObservable<string>;
            kanaAddress1st: KnockoutObservable<string>;
            address2nd: KnockoutObservable<string>;
            kanaAddress2nd: KnockoutObservable<string>;
            phoneNumber: KnockoutObservable<string>;
            citySign: KnockoutObservable<string>;
            officeMark: KnockoutObservable<string>;
            officeNoA: KnockoutObservable<string>;
            officeNoB: KnockoutObservable<string>;
            officeNoC: KnockoutObservable<string>;
            memo: KnockoutObservable<string>;
            textEditorOption: KnockoutObservable<any>;
            multilineeditor: any;
            constructor(officeInfo: LaborInsuranceOffice) {
                this.code = ko.observable(officeInfo.code);
                this.name = ko.observable(officeInfo.name);
                this.shortName = ko.observable(officeInfo.shortName);
                this.picName = ko.observable(officeInfo.picName);
                this.picPosition = ko.observable(officeInfo.picPosition);
                this.postalCode = ko.observable(officeInfo.potalCode);
                this.address1st = ko.observable(officeInfo.address1st);
                this.kanaAddress1st = ko.observable(officeInfo.kanaAddress1st);
                this.address2nd = ko.observable(officeInfo.address2nd);
                this.kanaAddress2nd = ko.observable(officeInfo.kanaAddress2nd);
                this.phoneNumber = ko.observable(officeInfo.phoneNumber);
                this.citySign = ko.observable(officeInfo.citySign);
                this.officeMark = ko.observable(officeInfo.officeMark);
                this.officeNoA = ko.observable(officeInfo.officeNoA);
                this.officeNoB = ko.observable(officeInfo.officeNoB);
                this.officeNoC = ko.observable(officeInfo.officeNoC);
                this.memo = ko.observable(officeInfo.memo);
                this.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                this.multilineeditor = {
                    memo: ko.observable(officeInfo.memo),
                    readonly: false,
                    constraint: 'ResidenceCode',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                        resizeable: true,
                        placeholder: "Placeholder for text editor",
                        width: "",
                        textalign: "left"
                    })),
                }


            }
        }
    }
}
