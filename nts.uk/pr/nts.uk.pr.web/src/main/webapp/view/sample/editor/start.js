var option = nts.uk.ui.option;
__viewContext.ready(function () {
    var vm = {
        // TextEditor
        texteditor: {
            value: ko.observable(''),
            constraint: 'ResidenceCode',
            option: ko.mapping.fromJS(new option.TextEditorOption()),
            enable: ko.observable(true),
            readonly: ko.observable(false)
        },
        employeeeditor: {
            value: ko.observable('0132'),
            constraint: 'EmployeeCode',
            option: ko.mapping.fromJS(new option.TextEditorOption({
                filldirection: "left",
                fillcharacter: "0"
            })),
            enable: ko.observable(true),
            readonly: ko.observable(false)
        },
        numbereditor: {
            value: ko.observable(12),
            constraint: '',
            option: ko.mapping.fromJS(new option.NumberEditorOption({ grouplength: 3, decimallength: 2 })),
            enable: ko.observable(true),
            readonly: ko.observable(false)
        },
        // TimeEditor
        timeeditor: {
            value: ko.observable(-1222),
            constraint: 'LayoutCode',
            option: ko.mapping.fromJS(new option.TimeEditorOption({ inputFormat: 'time' })),
            enable: ko.observable(true),
            readonly: ko.observable(false)
        },
        // TimeEditor
        yearMonthEditor: {
            value: ko.observable(200001),
            constraint: 'LayoutCode',
            option: ko.mapping.fromJS(new option.TimeEditorOption({ inputFormat: 'yearmonth' })),
            enable: ko.observable(true),
            readonly: ko.observable(false)
        }
    };
    // developer's view model
    var dirty = new nts.uk.ui.DirtyChecker(vm.numbereditor.value);
    nts.uk.ui.confirmSave(dirty);
    this.bind(vm);
});
