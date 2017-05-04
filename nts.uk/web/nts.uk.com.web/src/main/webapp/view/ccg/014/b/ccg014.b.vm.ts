module ccg014.b.viewmodel {

    
    export class ScreenModel {
           //editor
     titlecode: any;
     titlename: any;   
        
        constructor() {
               var self = this;            
            self.titlecode = {
            value: ko.observable(''),
            constraint: 'TitleMenuCode',
            option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                filldirection: "right",
                fillcharacter: "0",
                textmode: "text",
                placeholder: "",
                width: "30px",
                textalign: "left"
            })),
            required: ko.observable(true),
            enable: ko.observable(true),
            readonly: ko.observable(false)
        };
            self.titlename = {
            value: ko.observable(''),
            constraint: 'Name',
            option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                filldirection: "right",
                fillcharacter: "0",
                textmode: "text",
                placeholder: "",
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
            dfd.resolve();
            return dfd.promise();
        }
        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }
    }
}