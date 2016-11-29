var option = nts.uk.ui.option;
__viewContext.ready(function () {
    var vm = {
        // TextEditor
        texteditor: {
            value: ko.observable(''),
            constraint: 'EmployeeCode',
            option: ko.mapping.fromJS(new option.TextEditorOption()),
            enable: ko.observable(true),
            readonly: ko.observable(false)
        },
        // NumberEditor
        numbereditor: {
            value: ko.observable(12),
            constraint: 'ProcessingNo',
            option: ko.mapping.fromJS(new option.NumberEditorOption({ grouplength: 3, decimallength: 2 })),
            enable: ko.observable(true),
            readonly: ko.observable(false)
        },
        // TimeEditor
        timeeditor: {
            value: ko.observable("-1200"),
            constraint: 'LayoutCode',
            option: ko.mapping.fromJS(new option.TimeEditorOption({ inputFormat: 'time' })),
            enable: ko.observable(true),
            readonly: ko.observable(false)
        },
        // TimeEditor
        yearMonthEditor: {
            value: ko.observable("199911"),
            constraint: 'LayoutCode',
            option: ko.mapping.fromJS(new option.TimeEditorOption({ inputFormat: 'yearmonth' })),
            enable: ko.observable(true),
            readonly: ko.observable(false)
        },
        // MaskEditor
        maskeditor: {
            value: ko.observable("0001"),
            constraint: 'LayoutCode',
            option: ko.mapping.fromJS(new option.MaskEditorOption()),
            enable: ko.observable(true),
            readonly: ko.observable(false)
        },
    };
    // developer's view model
    this.bind(vm);
});
