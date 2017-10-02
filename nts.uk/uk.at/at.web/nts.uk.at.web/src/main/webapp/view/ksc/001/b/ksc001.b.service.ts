module nts.uk.at.view.ksc001.b {
    export module service {
        var paths = {
           findPeriodById: "ctx/at/shared/workrule/closure/findPeriodById",

        }
        
        /**
         * call service find by period closure 
         */
        export function findPeriodById(closureId: number): JQueryPromise<model.PeriodDto> {
            return nts.uk.request.ajax('at', paths.findPeriodById + '/' + closureId);
        }
        
        export module model {

            export interface NtsWizardStep {
                content: string;
            }

            export interface PeriodDto {
                startDate: Date;
                endDate: Date;
            }
            export interface UserInfoDto{
                companyId: string;
                employeeId: string;    
            }
        }

    }
}
