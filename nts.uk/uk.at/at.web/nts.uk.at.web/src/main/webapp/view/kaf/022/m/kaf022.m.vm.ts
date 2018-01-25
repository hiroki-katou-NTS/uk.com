module nts.uk.at.view.kmf022.m.viewmodel {
    export class ScreenModel {
        constructor() {

        }
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
    }

}