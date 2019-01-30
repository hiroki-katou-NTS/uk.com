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
        
        export function saveAsExcel(languageId: String): JQueryPromise<any> {
            let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
            let programName = program[1] != null ? program[1] : "";
            return nts.uk.request.exportFile('/masterlist/report/print', {
                domainId: "Employment",
                domainType: "CMM008" + if (program.length > 1) {
                    program.shift();
                    domainType = domainType + program.join(" ");
                }, languageId: languageId, reportType: 0
            });
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
