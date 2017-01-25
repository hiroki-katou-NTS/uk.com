__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            //start Switch Data
            self.enable = ko.observable(true);
            self.roundingRules = ko.observableArray([
                { code: '1', name: '支給項目ボタン' },
                { code: '2', name: '支給項目ボタン' },
                { code: '3', name: '支給項目ボタン' }
            ]);
            self.selectedRuleCode = ko.observable(1);
            //endSwitch Data
        }
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
function closeDialog() {
    nts.uk.ui.windows.close();
}
function submitInfo() {
    nts.uk.ui.windows.close();
}
