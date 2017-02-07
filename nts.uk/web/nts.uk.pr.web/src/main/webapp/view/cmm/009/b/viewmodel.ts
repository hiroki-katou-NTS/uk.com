module cmm009.b.viewmodel {

    export class ScreenModel {
        
        memo: KnockoutObservable<string>;
        multilineeditor: any;
        
        constructor() {
            var self = this;
            
            self.memo = ko.observable("");
            self.multilineeditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                    resizeable: true,
                    placeholder: "",
                    width: "",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
        }

        start(){
        }
    }
}