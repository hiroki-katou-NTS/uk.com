module sample.importsettingform.viewmodel {
    export class ScreenModel {
        importSettingForm: nts.uk.ui.importSettingForm.ImportSettingForm;
        functionItems: KnockoutObservableArray<any>;

        constructor() {
            var self = this;
            // Create a ImportSettingFrom
            self.importSettingForm = new nts.uk.ui.importSettingForm.ImportSettingForm({
                onClosed: function(result) {
                    console.log(result);
                }
            });
            
            self.functionItems = ko.observableArray([]);
            var functionItems = [];
            for (let i = 0; i < 10; i++) {
                let x = i;
                functionItems.push({
                    icon: "", text: 'trigger-' + i, action: function(evt, ui) {
                        self.importSettingForm.show();
                    }
                });
            }
            self.functionItems(functionItems);
        }
        
        init() {
            this.importSettingForm.init({
                selector: ".import-setting-trigger",
                onClosed: function(result) {
                    if (result !== undefined)
                        alert(result.encodeType.fieldName);
                }
            });
        }
        
        destroy() {
            this.importSettingForm.destroy();
        }
    }
}