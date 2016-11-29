import option = nts.uk.ui.option;

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
            },            // NumberEditor
            numbereditor: {
                value: ko.observable(12),
                constraint: '',
                option: ko.mapping.fromJS(new option.NumberEditorOption({grouplength: 3, decimallength: 2})),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            },
            // TimeEditor
            timeeditor: {
                value: ko.observable(null),
                constraint: 'LayoutCode',
                option: ko.mapping.fromJS(new option.TimeEditorOption({inputFormat: 'time'})),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            },
            // TimeEditor
            yearMonthEditor: {
                value: ko.observable(null),
                constraint: 'LayoutCode',
                option: ko.mapping.fromJS(new option.TimeEditorOption({inputFormat: 'yearmonth'})),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            }
        };
     // developer's view model
    this.bind(vm);
    
});