module nts.uk.com.view.cmm008.a {
    export module service {
        /**
         *  Service paths
         */
        var paths = {
            findEmployment: 'basic/company/organization/employment/findById',
            saveEmployment: 'basic/company/organization/employment/save',
            removeEmployment: 'basic/company/organization/employment/remove'
            
        };

        
        export function findEmployment(employmentCode: string): JQueryPromise<model.EmploymentFindDto> {
            return nts.uk.request.ajax(paths.findEmployment + "/" + employmentCode);
        }

        export function saveEmployment(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.saveEmployment, command);
        }
        
        export function removeEmployment(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.removeEmployment, command);
        }
        
        /**
        * Model namespace.
        */
        export module model {
            
            export class EmploymentFindDto{
                code: string;
                name: string;
                empExternalCode: string;
                memo: string;
            }
            
            
        }
    }
}
