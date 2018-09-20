module nts.uk.pr.view.qmm011.a.viewmodel {
    export class ScreenModel {
        
        constructor() {
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            return dfd.promise();
        }
    }
}
