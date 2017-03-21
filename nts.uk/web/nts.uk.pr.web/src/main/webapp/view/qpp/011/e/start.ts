__viewContext.ready(function() {
    class ScreenModel {

        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        enable: KnockoutObservable<boolean>;
        constructor() {
            var self = this; 
            self.enable = ko.observable(true);
            self.roundingRules = ko.observableArray([
                { code: '1', name: 'Item1' },
                { code: '2', name: 'Item2' }
            ]);
            self.selectedRuleCode = ko.observable(1);
        }
    }
    class GridItemModel {
            code: string;
        name: string;
        description: string;

        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }

    } 
    this.bind(        new ScreenModel());
});
function closeDialog(): any {
    nts.uk.ui.windows.close();
}
function submitInfo(): any {
    nts.uk.ui.windows.close();
}