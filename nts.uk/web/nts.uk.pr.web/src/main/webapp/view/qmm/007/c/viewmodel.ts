module nts.uk.pr.view.qmm007.c {
    export module viewmodel {
        export class ScreenModel {
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            endDate: KnockoutObservable<string>;
            startDate: any;
            edittingMethod: KnockoutObservable<string>;

            constructor() {
                var self = this;
                self.code = ko.observable('001');
                self.name = ko.observable('ガソリン単価');
                self.endDate = ko.observable('~ 9999/12');
                self.edittingMethod = ko.observable('2');

                self.startDate = {
                    value: ko.observable('2016/04'),
                    constraint: '',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "",
                        width: "",
                        textalign: "left"
                    })),
                    required: ko.observable(false),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };
            }
            btnApplyClicked() {
                nts.uk.ui.windows.close()
            }
            btnCancelClicked() {
                nts.uk.ui.windows.close()
            }
        }
    }
}