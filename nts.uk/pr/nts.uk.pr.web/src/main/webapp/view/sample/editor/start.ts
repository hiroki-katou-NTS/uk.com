import editorOption = nts.uk.ui.option;

__viewContext.ready(function () {
    var vm = {
            // TextEditor
            texteditor: {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new editorOption.TextEditorOption()),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            },
            employeeeditor: {
                value: ko.observable(19),
                constraint: 'EmployeeCode',
                option: ko.mapping.fromJS(new editorOption.TextEditorOption({
                    filldirection: "right",
                    fillcharacter: "0"
                })),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            },
            // MultilineEditor
            multilineeditor: {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new editorOption.MultilineEditorOption({resizeable: true})),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            },
            // NumberEditor
            numbereditor: {
                value: ko.observable(12),
                constraint: '',
                option: ko.mapping.fromJS(new editorOption.NumberEditorOption({grouplength: 3, decimallength: 2})),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            },
            // TimeEditor
            timeeditor: {
                value: ko.observable(-1222),
                constraint: 'LayoutCode',
                option: ko.mapping.fromJS(new editorOption.TimeEditorOption({inputFormat: 'time'})),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            },
            // TimeEditor
            yearMonthEditor: {
                value: ko.observable(200001),
                constraint: 'LayoutCode',
                option: ko.mapping.fromJS(new editorOption.TimeEditorOption({inputFormat: 'yearmonth'})),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            }
        };
     // developer's view model
    this.bind(vm);
    
});