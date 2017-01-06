module qmm023.a.viewmodel{
    export class ScreenModel{
        texteditor: any;
        constructor(){
            var self = this;                  
        self.texteditor = {
            value: ko.observable(''),
            constraint: 'ResidenceCode',
            option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                textmode: "text",
                placeholder: "Placeholder for text editor",
                width: "100px",
                textalign: "left"
            })),
            required: ko.observable(true),
            enable: ko.observable(true),
            readonly: ko.observable(false)
        };
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;
            
            var dfd = $.Deferred();
            
            return dfd.promise();
        }
    }
 
}