module nts.uk.at.view.kdw008.c {
    export module viewmodel {
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
}
