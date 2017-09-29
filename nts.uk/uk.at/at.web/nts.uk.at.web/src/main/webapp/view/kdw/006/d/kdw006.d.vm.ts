module nts.uk.at.view.kdw006.d {
    export module viewmodel {
        export class ScreenModel {
            constructor() {
            }

            saveData(){
                alert('screen d');
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
