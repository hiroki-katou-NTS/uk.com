module nts.uk.at.view.kmf001.a.viewmodel {
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