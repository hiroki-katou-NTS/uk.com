module nts.uk.com.view.cdl003.a {
    
    
    export module service {
        
         var servicePath = {
            findAllClassifications: 'basic/company/organization/classification/findAll'
        }
        
        /**
         * call service find all classification 
         */
         export function findAllClassifications(): JQueryPromise<model.ClassificationFindDto[]> {
             return nts.uk.request.ajax('com', servicePath.findAllClassifications);
         }
        
        export module model{
            export interface ClassificationFindDto {
                code: string;
                name: string;
            }    
        }
    }
}