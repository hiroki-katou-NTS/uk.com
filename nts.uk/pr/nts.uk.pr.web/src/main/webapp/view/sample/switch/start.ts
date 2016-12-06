__viewContext.ready(function () {
    class ScreenModel {
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        constructor() {
            var self = this;
            self.roundingRules = ko.observableArray([
                { code: '1', name: '四捨五入' },
                { code: '2', name: '切り上げ' },
                { code: '3', name: '切り捨て' }
            ]);
            self.selectedRuleCode = ko.observable(1);
        }
    }
    
    this.bind(new ScreenModel());
    
});