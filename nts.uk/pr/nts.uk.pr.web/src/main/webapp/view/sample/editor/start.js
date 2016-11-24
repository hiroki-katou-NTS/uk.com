var option = nts.uk.ui.option;
__viewContext.ready(function () {
    console.log(option);
    var vm = {
        // TextEditor
        texteditor: {
            value: ko.observable('For Lordaeron'),
            constraint: 'LayoutCode',
            option: ko.mapping.fromJS(new option.TextEditorOption()),
            enable: function (enable) {
                var self = this;
                self.option.enable(enable);
            },
            readonly: function (readonly) {
                var self = this;
                self.option.readonly(readonly);
            },
            change: function (value) {
                var self = this;
                $('#textbox-onchange').append(value + "<br/>");
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
            },
            readonly: function (readonly) {
                var self = this;
                self.option.readonly(readonly);
            },
            change: function (value) {
                var self = this;
                $('#numberbox-onchange').append(value + "<br/>");
            }
        },
        // TimeEditor
        timeeditor: {
            value: ko.observable("12:00"),
            constraint: 'LayoutCode',
            option: ko.mapping.fromJS(new option.TimeEditorOption()),
            enable: function (enable) {
                var self = this;
                self.option.enable(enable);
            },
            readonly: function (readonly) {
                var self = this;
                self.option.readonly(readonly);
            },
            change: function (value) {
                var self = this;
                $('#timebox-onchange').append(value + "<br/>");
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
            },
            readonly: function (readonly) {
                var self = this;
                self.option.readonly(readonly);
            },
            change: function (value) {
                var self = this;
                $('#maskbox-onchange').append(value + "<br/>");
            }
        },
    };
    // developer's view model
    this.bind(vm);
});
