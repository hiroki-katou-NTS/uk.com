__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.enable = ko.observable(true);
            self.roundingRules = ko.observableArray([
                { code: '1', name: '四捨五入' },
                { code: '2', name: '切り上げ' },
                { code: '3', name: '切り捨て' }
            ]);
            self.selectedRuleCode = ko.observable();
        }
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
