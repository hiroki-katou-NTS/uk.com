module nts.uk.pr.view.qmm007.b {
    export module viewmodel {

        export class ScreenModel {
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            jpnDate: KnockoutObservable<string>;
            endDate: KnockoutObservable<string>;

            historyTakeOver: KnockoutObservable<string>;

            startDate: any;

            constructor() {
                var self = this;
                self.code = ko.observable(nts.uk.ui.windows.getShared('code'));
                self.name = ko.observable('ガソリン単価');
                self.jpnDate = ko.observable('（平成29年01月）');
                self.endDate = ko.observable('~ 9999/12');

                self.historyTakeOver = ko.observable('1');

                self.startDate = {
                    value: ko.observable('2017/01'),
                    constraint: '',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "",
                        width: "",
                        textalign: "left"
                    })),
                    required: ko.observable(true),
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