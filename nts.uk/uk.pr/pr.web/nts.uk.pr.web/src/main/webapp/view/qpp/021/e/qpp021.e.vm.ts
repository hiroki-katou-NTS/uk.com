module qpp021.e.viewmodel {
    export class ScreenModel {
        constructor() {

        }
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

    }
}