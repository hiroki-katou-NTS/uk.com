module nts.uk.pr.view.kmf001.c {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            update: 'ctx/pr/core/vacation/setting/annualpaidleave/update'
        };

       export function update(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.update, command);
        }

        /**
        * Model namespace.
        */
        export module model {
            
            export class EnumerationModel {
                
                value: number;
                name: string;
                
                constructor(value: number, name: string) {
                    let self = this;
                    self.name = name;
                    self.value = value;
                }
            }
        }
    }
}
