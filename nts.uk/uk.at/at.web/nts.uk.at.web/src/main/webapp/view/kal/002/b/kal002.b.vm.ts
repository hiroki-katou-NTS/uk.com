module nts.uk.at.view.kal002.b.viewmodel {
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

        openKAL003() { 
        nts.uk.request.jump("/view/kal/003/a/index.xhtml");
        }   
        
    }   
}

