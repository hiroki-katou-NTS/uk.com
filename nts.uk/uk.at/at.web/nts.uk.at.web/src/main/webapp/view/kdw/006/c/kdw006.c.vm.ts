module nts.uk.at.view.kdw006.c {
    export module viewmodel {
        export class ScreenModel {
            constructor() {
            }

            saveData(){
                alert('screen c');
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
