module nts.uk.pr.view.qmm010.b {
    import option = nts.uk.ui.option;
    import SocialInsuranceOfficeInDto = service.model.SocialInsuranceOfficeInDto;
    export module viewmodel {
        export class ScreenModel {
            textSearch: any;
            lstSocialInsuranceOffice: KnockoutObservableArray<SocialInsuranceOfficeInDto>;
            selectLstSocialInsuranceOffice: KnockoutObservableArray<any>;
            columnsLstSocialInsuranceOffice: KnockoutObservableArray<any>;
            multilineeditor: any;
            employmentName: KnockoutObservable<string>;
            constructor() {
                var self = this;
                self.employmentName = ko.observable("");
                self.textSearch = {
                    valueSearch: ko.observable(""),
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "コード・名称で検索・・・",
                        width: "340",
                        textalign: "left"
                    }))
                }
                self.selectLstSocialInsuranceOffice = ko.observableArray([]);
                self.columnsLstSocialInsuranceOffice = ko.observableArray([
                    { headerText: 'コード', prop: 'code', width: 120 },
                    { headerText: '名称', prop: 'name', width: 120 }
                ]);
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
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                self.findAllInsuranceOffice().done(data => {
                    dfd.resolve(self);
                });
                return dfd.promise();
            }
            //Connection service find All InsuranceOffice
            findAllInsuranceOffice(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findAllSocialInsuranceOffice().done(data => {
                    self.lstSocialInsuranceOffice = ko.observableArray<SocialInsuranceOfficeInDto>(data);
                    dfd.resolve(self);
                });
                return dfd.promise();
            }
        }
    }
}
