module nts.uk.at.view.ktg029.a.viewmodel {
    export class ScreenModel {

        
        constructor() {
            var self = this;
            
        }

        startPage(): JQueryPromise<any> {
            var self = this;

            console.log('abc');
            var dfd = $.Deferred();

            return dfd.promise();
        }
    }

}

