module nts.uk.at.view.kmk004.e {
    export module viewmodel {

        export class ScreenModel {
            enable: KnockoutObservable<number>;
            checked: KnockoutObservable<boolean>;
            value: KnockoutObservable<number>;
            constructor() {
                let self = this;
                self.checked = ko.observable(true);
                self.value = ko.observable(0);
                self.enable = ko.observable(true);
            }

            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                dfd.resolve();
                return dfd.promise();
            }

            public save(): void {
                nts.uk.ui.windows.close();
            }

            public cancel(): void {
                nts.uk.ui.windows.close();
            }
        }
    }
}