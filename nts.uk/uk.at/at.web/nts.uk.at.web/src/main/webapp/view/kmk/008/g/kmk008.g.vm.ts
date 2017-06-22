module nts.uk.at.view.kmk008.g {
    export module viewmodel {
        export class ScreenModel {
            printType: number;
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
                this.code = code;
                this.name = name;
            }
        }
    }
}
