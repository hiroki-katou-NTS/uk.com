module cmm009.a.viewmodel {

    export class ScreenModel {
        
        departmentCode: KnockoutObservable<string>;
        departmentName: KnockoutObservable<string>;
        shortName: KnockoutObservable<string>;
        genericName: KnockoutObservable<string>;
        fullGenericName: KnockoutObservable<string>;
        externalCode: KnockoutObservable<string>;
        texteditorcode: any;
        texteditorname: any;
        texteditorshortname: any;
        texteditorgenericname: any;
        texteditorexternalcode: any;
        multilineeditor: any;

        constructor() {
            var self = this;
            
            self.departmentCode = ko.observable("");
            self.departmentName = ko.observable("");
            self.shortName = ko.observable("");
            self.genericName = ko.observable("");
            self.fullGenericName = ko.observable("");
            self.externalCode = ko.observable("");
            self.texteditorcode = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "100px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true)
            };
            self.texteditorname = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "100px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true)
            };
            self.texteditorshortname = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "100px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true)
            };
            self.texteditorgenericname = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "100px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true)
            };
            self.texteditorexternalcode = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "100px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true)
            };
            self.multilineeditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                    resizeable: true,
                    placeholder: "",
                    width: "",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
        }
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            dfd.resolve();
            return dfd.promise();
        }
    }
}