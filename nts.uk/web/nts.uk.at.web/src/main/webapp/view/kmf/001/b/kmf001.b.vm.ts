module nts.uk.pr.view.kmf001.b {
    export module viewmodel {
        import EnumertionModel = service.model.EnumerationModel;
        export class ScreenModel {
            priority : KnockoutObservableArray<EnumertionModel>;
            selectedPriority : KnockoutObservable<number>;
            enablePriority : KnockoutObservable<boolean>;

            constructor() {
                let self = this;
                self.priority = ko.observableArray([
                    { value: 0, name: "設定する" },
                    { value: 1, name: "設定しない" }
                ]);
                self.selectedPriority = ko.observable(0);
                self.enablePriority = ko.computed(function () {
                    return self.selectedPriority() == 0;
                }, self);
            }
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                dfd.resolve();
                return dfd.promise();
            }
        }
    }
}