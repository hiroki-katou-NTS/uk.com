module nts.uk.pr.view.kmf001.c {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {

        };

        /**
         * Normal service.
         */
        export class Service {
            constructor() {
            }
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
