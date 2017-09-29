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

        /**
         * Find Employment
         */
        export function findEmployment(employmentCode: string): JQueryPromise<model.EmploymentDto> {
            return nts.uk.request.ajax(paths.findEmployment + "/" + employmentCode);
        }

        /**
         * Save Employment
         */
        export function saveEmployment(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.saveEmployment, command);
        }
        
        /**
         * Remove Employment
         */
        export function removeEmployment(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.removeEmployment, command);
        }
        
        /**
        * Model namespace.
        */
        export module model {
            
            /**
             * class EmploymentDto
             */
            export class EmploymentDto{
                code: string;
                name: string;
                empExternalCode: string;
                memo: string;
            }
            
            
        }
    }
}
