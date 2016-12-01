module nts.uk.pr.view.qpp005.f {
    
    export module viewmodel {
        export class ScreenModel {
            totalCheck : CheckBox;
            oneMonthCheck: CheckBox;
            taxCommuteCheck: CheckBox;
            numberEditor: NumberEditor;
            
            constructor() {
                var self = this;
                self.totalCheck = new CheckBox();
                
                self.oneMonthCheck = new CheckBox();
                self.oneMonthCheck.isChecked = ko.observable(false);
                
                self.taxCommuteCheck = new CheckBox();
                self.taxCommuteCheck.isChecked = ko.observable(false);
                
                self.numberEditor = new NumberEditor();
                self.numberEditor.isEnable = self.totalCheck.isChecked;
                self.numberEditor.value = ko.observable("10000");
            }
            
            IsEnableText() {
                var self = this;
                if(self.totalCheck.isChecked() && self.taxCommuteCheck.isChecked())
                    return ko.observable(true);
                return  ko.observable(false); 
            }
        }
        
        export class CheckBox{
            isChecked: KnockoutObservable<boolean>;
            
            constructor() {
                var self = this;
                self.isChecked = ko.observable(true);    
            }
        }
        
        export class NumberEditor{
            isEnable: KnockoutObservable<boolean>;
            value: KnockoutObservable<string>;
            option: ui.option.CurrencyEditorOption;
            
            constructor() {
                var self = this;
                self.option = ko.mapping.fromJS(new ui.option.CurrencyEditorOption({grouplength: 3, currencyformat: 'JPY', width: '80'}));
            }
        }
    }
}