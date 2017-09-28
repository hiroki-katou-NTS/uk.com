module nts.uk.at.view.kdw006.g {
    export module viewmodel {
        export class ScreenModel {
            constructor() {
            }

            saveData(){
                alert('screen g');
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
