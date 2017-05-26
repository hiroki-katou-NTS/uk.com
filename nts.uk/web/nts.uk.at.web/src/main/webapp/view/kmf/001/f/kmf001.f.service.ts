module nts.uk.pr.view.kmf001.f {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            update: '',
            find: ''
        };

        export function update(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.update, command);
        }

        export function find(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.find);
        }

        /**
        * Model namespace.
        */
        export module model {

            export class EnumerationModel {

                code: string;
                name: string;

                constructor(code: string, name: string) {
                    let self = this;
                    self.name = name;
                    self.code = code;
                }
            }
        }
    }
}
