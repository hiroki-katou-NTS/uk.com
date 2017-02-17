module nts.uk.pr.view.qpp007.b {
    export module viewmodel {

        export class ScreenModel {
            // Switch button data source
            switchButtonDataSource: KnockoutObservableArray<any>;
            switchValue: KnockoutObservable<string>;

            constructor() {
                var self = this;
                self.switchButtonDataSource = ko.observableArray([
                    { code: 'Apply', name: '時間' },
                    { code: 'NotApply', name: '分' }
                ]);
                self.switchValue = ko.observable('Apply');
            }


        }
    }
}