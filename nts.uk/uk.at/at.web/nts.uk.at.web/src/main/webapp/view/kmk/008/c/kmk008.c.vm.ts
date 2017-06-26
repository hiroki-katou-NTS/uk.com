module nts.uk.at.view.kmk008.c {
    export module viewmodel {
        export class ScreenModel {
          
            constructor() {
                let self = this;
                
            }
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

        }

        export class ItemModel {
            code: number;
            name: string;
            constructor(code: number, name: string) {
                let self = this;
                self.code = code;
                self.name = name;
            }
        }
    }
}
