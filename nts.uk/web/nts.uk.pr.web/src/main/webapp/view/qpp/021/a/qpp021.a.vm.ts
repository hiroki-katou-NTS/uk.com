module qpp021.a.viewmodel {
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