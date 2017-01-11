module nts.uk.pr.view.qmm007.b {
    export module viewmodel {

        export class ScreenModel {
            lbl_003: KnockoutObservable<string>;
            lbl_004: KnockoutObservable<string>;
            lbl_006: KnockoutObservable<string>;
            lbl_007: KnockoutObservable<string>;

            sel_001_radio: KnockoutObservable<string>;

            inp_001: any;

            constructor() {
                var self = this;
                self.lbl_003 = ko.observable('001');
                self.lbl_004 = ko.observable('ガソリン単価');
                self.lbl_006 = ko.observable('（平成29年01月）');
                self.lbl_007 = ko.observable('~ 9999/12');

                self.sel_001_radio = ko.observable('1');

                self.inp_001 = {
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

            btn_001_clicked() {
                nts.uk.ui.windows.close()
            }
            btn_002_clicked() {
                nts.uk.ui.windows.close()
            }

        }
    }
}