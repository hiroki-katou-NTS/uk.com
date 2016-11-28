var option = nts.uk.ui.option;
__viewContext.ready(function () {
    var vm = {
        // TextEditor
        texteditor: {
            value: ko.observable('For Lordaeron'),
            constraint: 'LayoutCode',
            option: ko.mapping.fromJS(new option.TextEditorOption()),
            enable: function (enable) {
                var self = this;
                self.option.enable(enable);
            }
        },
        // NumberEditor
        numbereditor: {
            value: ko.observable(1234),
            constraint: 'ProcessingNo',
            option: ko.mapping.fromJS(new option.NumberEditorOption({ grouplength: 3, decimallength: 2 })),
            enable: function (enable) {
                var self = this;
                self.option.enable(enable);
            }
        },
        // TimeEditor
        timeeditor: {
            value: ko.observable("-1259"),
            constraint: '',
            option: ko.mapping.fromJS(new option.TimeEditorOption()),
            enable: function (enable) {
                var self = this;
                self.option.enable(enable);
            }
        },
        // TimeEditor
        yearMonthEditor: {
            value: ko.observable("199912"),
            constraint: '',
            option: ko.mapping.fromJS(new option.TimeEditorOption({ inputFormat: "yearmonth" })),
            enable: function (enable) {
                var self = this;
                self.option.enable(enable);
            }
        },
        // MaskEditor
        maskeditor: {
            value: ko.observable("0001"),
            constraint: 'LayoutCode',
            option: ko.mapping.fromJS(new option.MaskEditorOption()),
            enable: function (enable) {
                var self = this;
                self.option.enable(enable);
            }
        },
    };
    // developer's view model.
    this.bind(vm);
});
