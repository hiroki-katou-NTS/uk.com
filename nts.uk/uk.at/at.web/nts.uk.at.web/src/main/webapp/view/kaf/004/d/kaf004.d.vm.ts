module nts.uk.at.view.kaf004.d.viewmodel {
    export class ScreenModel {


        constructor() {
            var self = this;

        }

        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();

            dfd.resolve();

            return dfd.promise();
        }
    }
}