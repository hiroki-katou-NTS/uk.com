__viewContext.ready(function () {
    class ScreenModel {
        value: KnockoutObservable<string>;
        texteditor: any;
        employeeeditor: any;
        
        constructor() {
            var self = this;
            self.value = ko.observable("123");
            // TextEditor
            self.texteditor = {
                value: ko.observable(''),
                constraint: 'WLAggregateItemCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    width: "",
                    textalign: "left"
                })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false),
                clear: function(){
                    $("#text-1").ntsError("clear");    
                }
            };
            // EmployeeCodeEditor
            self.employeeeditor = {
                value: ko.observable('19'),
                constraint: 'EmployeeCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    filldirection: "right",
                    fillcharacter: "0"
                })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
        }
    }

    var viewmodel = new ScreenModel();
    this.bind(viewmodel);    
});