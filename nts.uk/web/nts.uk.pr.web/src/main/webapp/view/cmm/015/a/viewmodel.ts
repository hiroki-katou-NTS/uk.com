module cmm015.a.viewmodel {

    export class ScreenModel {
        payClassificationCode: KnockoutObservable<string>;
        payClassificationName: KnockoutObservable<string>;
        payClassificationMemo: KnockoutObservable<string>;
        payClassificationList: KnockoutObservableArray<PayClassification>;
        selectedPayClassificationCode: any;
        texteditorcode: any;
        texteditorname: any;
        multilineeditor: any;

        constructor() {
            var self = this;
            self.payClassificationCode = ko.observable("");
            self.payClassificationName = ko.observable("");
            self.payClassificationMemo = ko.observable("");
            self.payClassificationList = ko.observableArray([]);
            self.selectedPayClassificationCode = ko.observable(null);
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

    class PayClassification {
        payClassificationCode: string;
        payClassificationName: string;
        payClassificationMemo: string;
        constructor(payClassificationCode: string, payClassificationName: string) {
            this.payClassificationCode = payClassificationCode;
            this.payClassificationName = payClassificationName;
        }
    }
}