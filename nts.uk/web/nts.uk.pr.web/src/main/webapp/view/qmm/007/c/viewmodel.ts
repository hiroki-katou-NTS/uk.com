module qmm007.c {
    export module viewmodel {

        export class ScreenModel {
            lbl_003: KnockoutObservable<string>;
            lbl_004: KnockoutObservable<string>;
            lbl_008: KnockoutObservable<string>;
            inp_001: any;
            sel_001_radio: KnockoutObservable<string>;
            constructor() {
                var self = this;
                self.lbl_003 = ko.observable('001');
                self.lbl_004 = ko.observable('ガソリン単価');
                self.lbl_008 = ko.observable('~ 9999/12');
                self.sel_001_radio = ko.observable('2');
                
                self.inp_001 = {
                    value: ko.observable('2016/04'),
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