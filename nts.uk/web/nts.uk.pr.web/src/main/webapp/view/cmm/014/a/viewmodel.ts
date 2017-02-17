module cmm014.a.viewmodel {

    export class ScreenModel {
        classificationCode: KnockoutObservable<string>;
        classificationName: KnockoutObservable<string>;
        classificationMemo: KnockoutObservable<string>;
        classificationList: KnockoutObservableArray<Classification>;
        selectedClassificationCode: any;
        texteditorcode: any;
        texteditorname: any;
        multilineeditor: any;
        test: any;

        constructor() {
            var self = this;
            self.classificationCode = ko.observable("");
            self.classificationName = ko.observable("");
            self.classificationMemo = ko.observable("");
            self.classificationList = ko.observableArray([]);
            self.selectedClassificationCode = ko.observable(null);
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

    class Classification {
        classificationCode: string;
        classificationName: string;
        classificationMemo: string;
        constructor(classificationCode: string, classificationName: string) {
            this.classificationCode = classificationCode;
            this.classificationName = classificationName;
        }
    }
}