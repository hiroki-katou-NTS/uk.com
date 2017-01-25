__viewContext.ready(function() {
    class ScreenModel {
        //Switch
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        enable: KnockoutObservable<boolean>;

        constructor() {
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
    this.bind(new ScreenModel());
});
function closeDialog(): any {
    nts.uk.ui.windows.close();
}
function submitInfo(): any {
    nts.uk.ui.windows.close();
}
