module nts.uk.at.view.kdw006.b {
    export module viewmodel {
        export class ScreenModel {
            
            //Define textbox
            simpleValue: KnockoutObservable<string>;
            multilineeditor: any;
            
            //Define switch button
            roundingRules: KnockoutObservableArray<any>;
            selectedRuleCode: any;
            
            constructor() {
                var self = this;
                
                //Init for textbox
                self.simpleValue = ko.observable("123");
                self.multilineeditor = {
                    value: ko.observable(''),
                    constraint: 'ResidenceCode',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                        resizeable: true,
                        placeholder: "",
                        width: "380px",
                        textalign: "left"
                    })),
                    required: ko.observable(true),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };
                
                //Init for switch buton
                self.roundingRules = ko.observableArray([
                    { code: '1', name: '四捨五入' },
                    { code: '2', name: '切り上げ' }
                ]);
                self.selectedRuleCode = ko.observable(1);
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            
        }
    }
}
