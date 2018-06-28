module nts.uk.at.view.kdw010.a {
    import blockUI = nts.uk.ui.block;
    export module viewmodel {
        export class ScreenModel {
            useSet: KnockoutObservableArray<any>;
            selectUse: KnockoutObservable<number>;
            maxContinuousDay: KnockoutObservable<number>;
            enableState: KnockoutObservable<boolean>;
            targetWorkTypeName: KnockoutObservable<string>;
            ignoreWorkTypeName: KnockoutObservable<string>;
            displayMessege: KnockoutObservable<string>;

            constructor() {
                var self = this;
                self.useSet = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText("Enum_UseAtr_Use") },
                    { code: '0', name: nts.uk.resource.getText("Enum_UseAtr_NotUse") },
                ]);
                self.selectUse = ko.observable(1);
                self.maxContinuousDay = ko.observable(0);
                self.enableState = ko.observable(true);
                self.targetWorkTypeName = ko.observable('');
                self.ignoreWorkTypeName = ko.observable('');
                self.displayMessege = ko.observable('');

                self.selectUse.subscribe(function(codeChanged) {
                    if (codeChanged == 1) {
                        self.enableState(true);
                    } else {
                        self.enableState(false);
                    }
                });

            }
            startPage(): JQueryPromise<any> {
                blockUI.invisible();
                var self = this;
                var dfd = $.Deferred();
                blockUI.clear();
                dfd.resolve();
                return dfd.promise();
            }
        }
    }
}