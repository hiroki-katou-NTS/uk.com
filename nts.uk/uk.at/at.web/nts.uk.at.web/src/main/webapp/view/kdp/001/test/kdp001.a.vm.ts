module nts.uk.at.view.kdp001.a {

    export module viewmodel {

        export class ScreenModel {
            dataScreenT: KnockoutObservable<any> = ko.observable({});
            dataScreenC: KnockoutObservable<any> = ko.observable({});
            constructor() {
                let self = this;

            }

            public startPage(): JQueryPromise<void> {
                let self = this;
                var dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();

            }

            public openKDP001() {
                nts.uk.ui.windows.sub.modal('/view/kdp/001/a/index.xhtml').onClosed(function(): any {

                });
            }

        }

    }
}