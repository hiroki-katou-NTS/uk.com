module qpp021.b.viewmodel {
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