module nts.uk.com.view.cmm008.a {
    export module service {
        /**
         *  Service paths
         */
        var paths = {
            findEmployment: 'bs/employee/employment/findByCode',
            saveEmployment: 'bs/employee/employment/save',
            removeEmployment: 'bs/employee/employment/remove',
  
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
        
        
        //saveAsExcel
        export function saveAsExcel(languageId: string): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "TotalTimes", languageId: languageId, domainType: "KMK009回数集計", reportType: 0});
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
