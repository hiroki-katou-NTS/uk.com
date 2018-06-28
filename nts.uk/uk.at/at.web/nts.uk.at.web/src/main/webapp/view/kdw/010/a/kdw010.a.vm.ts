module nts.uk.at.view.kdw010.a {
    import blockUI = nts.uk.ui.block;
    export module viewmodel {
        export class ScreenModel {
            useSet:KnockoutObservableArray<any>;
            selectUse:KnockoutObservable<number>;

            constructor() {
                var self = this;
                self.useSet = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText("Enum_UseAtr_Use") },
                    { code: '0', name: nts.uk.resource.getText("Enum_UseAtr_NotUse") },
                ]);
            }
            startPage(): JQueryPromise<any> {
                blockUI.invisible();
                var self = this;
                var dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
        }
    }
}