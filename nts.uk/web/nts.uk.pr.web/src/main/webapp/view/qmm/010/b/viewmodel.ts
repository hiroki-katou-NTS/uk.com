module nts.uk.pr.view.qmm010.b {
    import option = nts.uk.ui.option;
    import SocialInsuranceOfficeInDTO = service.model.SocialInsuranceOfficeInDTO;
    export module viewmodel {
        export class ScreenModel {
            textSearch: any;
            lstSocialInsuranceOffice: KnockoutObservableArray<SocialInsuranceOfficeInDTO>;
            selectLstSocialInsuranceOffice: KnockoutObservable<string>;
            columnsLstSocialInsuranceOffice: KnockoutObservableArray<any>;
            multilineeditor: any;
            employmentName: KnockoutObservable<string>;


            currentCode: KnockoutObservable<any>;
            currentCodeList: KnockoutObservableArray<any>;
            constructor() {
                var self = this;
                self.lstSocialInsuranceOffice = ko.observableArray([new SocialInsuranceOfficeInDTO('companyCode001', '000000000001', 'A事業所'),
                    new SocialInsuranceOfficeInDTO('companyCode001', '000000000002', 'B事業所'), new SocialInsuranceOfficeInDTO('companyCode001', '000000000003', 'C事業所')]);

                self.employmentName = ko.observable("");
                self.textSearch = {
                    valueSearch: ko.observable(""),
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "コード・名称で検索・・・",
                        width: "75%",
                        textalign: "left"
                    }))
                }
                self.selectLstSocialInsuranceOffice = ko.observable("");
                self.columnsLstSocialInsuranceOffice = ko.observableArray([
                    { headerText: 'コード', prop: 'code', width: 120 },
                    { headerText: '名称', prop: 'name', width: 120 }
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
        }
    }
}
