import editorOption = nts.uk.ui.option;

__viewContext.ready(function () {
    class ScreenModel {
        texteditor: any;
        dynamiceditor: any;
        employeeeditor: any;
        multilineeditor: any;
        numbereditor: any;
        currencyeditor: any;
        currencyeditor2: any;
        timeeditor: any;
        yearmontheditor: any;
        file: any;
        
        constructor() {
            var self = this;
            self.dynamiceditor = {
                editortype: 'texteditor',
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new editorOption.TextEditorOption()),
                enable: ko.observable(true),
                readonly: ko.observable(false) 
            };
                      
            // TextEditor
            self.texteditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new editorOption.TextEditorOption()),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            // EmployeeCodeEditor
            self.employeeeditor = {
                value: ko.observable('19'),
                constraint: 'EmployeeCode',
                option: ko.mapping.fromJS(new editorOption.TextEditorOption({
                    filldirection: "right",
                    fillcharacter: "0"
                })),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            // MultilineEditor
            self.multilineeditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new editorOption.MultilineEditorOption({resizeable: true})),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            // NumberEditor
            self.numbereditor = {
                value: ko.observable(12),
                constraint: '',
                option: ko.mapping.fromJS(new editorOption.NumberEditorOption({grouplength: 3, decimallength: 2})),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            },
            // CurrencyEditor
            self.currencyeditor = {
                value: ko.observable(1200),
                constraint: '',
                option: new editorOption.CurrencyEditorOption({grouplength: 3, decimallength: 2, currencyformat: "JPY", currencyposition: 'right'}),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            // CurrencyEditor
            self.currencyeditor2 = {
                value: ko.observable(200000),
                constraint: '',
                option: new editorOption.CurrencyEditorOption({grouplength: 4, decimallength: 2, currencyformat: "USD", currencyposition: 'left'}),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            // TimeEditor
            self.timeeditor = {
                value: ko.observable(-1222),
                constraint: 'LayoutCode',
                option: ko.mapping.fromJS(new editorOption.TimeEditorOption({inputFormat: 'time'})),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            // TimeEditor
            self.yearmontheditor = {
                value: ko.observable(200001),
                constraint: 'LayoutCode',
                option: ko.mapping.fromJS(new editorOption.TimeEditorOption({inputFormat: 'yearmonth'})),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            // File
            self.file = {
                file: new nts.uk.ui.file.FileDownload("/file/company/print"), 
                print: function(){
                    this.file.print();                          
                }
            }
        }
    }
        
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
            value: ko.observable('19'),
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
        // CurrencyEditor
        currencyeditor: {
            value: ko.observable(1200),
            constraint: '',
            option: new editorOption.CurrencyEditorOption({grouplength: 3, decimallength: 2, currencyformat: "JPY", currencyposition: 'right'}),
            enable: ko.observable(true),
            readonly: ko.observable(false)
        },
        // CurrencyEditor
        currencyeditor2: {
            value: ko.observable(200000),
            constraint: '',
            option: new editorOption.CurrencyEditorOption({grouplength: 4, decimallength: 2, currencyformat: "USD", currencyposition: 'left'}),
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
        }, 
        file: {
            file: new nts.uk.ui.file.FileDownload("/file/company/print"), 
            print: function(){
                this.file.print();                          
            }
        }
    };

    var viewmodel = new ScreenModel();
    var dirty = new nts.uk.ui.DirtyChecker(viewmodel.numbereditor.value);
    nts.uk.ui.confirmSave(dirty);
    this.bind(viewmodel);    
});