module nts.uk.com.view.cmm007.c {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                find: "sys/env/mailserver/find",
                save: "sys/env/mailserver/save",
            };
        
        /**
         * 
         */
        export function findMailServerSetting(): JQueryPromise<model.SampleDto>{
            return nts.uk.request.ajax(path.find);
        }
        
        /**
         * 
         */
        export function registerMailServerSetting(data: model.SampleDto): JQueryPromise<any> {
            return nts.uk.request.ajax(path.save, data);
        }
    }
    
    /**
     * Model define.
     */
    export module model {
        export class SampleDto {
            
            constructor(){}
        }
    }
    
}