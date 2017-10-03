module nts.uk.at.view.ksc001.b {
    export module service {
        var paths = {
           findPeriodById: "ctx/at/shared/workrule/closure/findPeriodById",
           checkThreeMonth: "ctx/at/shared/workrule/closure/checkThreeMonth",
           checkMonthMax: "ctx/at/shared/workrule/closure/checkMonthMax"
            

        }
        
        /**
         * call service find by period closure 
         */
        export function findPeriodById(closureId: number): JQueryPromise<model.PeriodDto> {
            return nts.uk.request.ajax('at', paths.findPeriodById + '/' + closureId);
        }
        /**
         * call service check three month by base date
         */
        
        export function checkThreeMonth(baseDate: Date): JQueryPromise<boolean> {
            return nts.uk.request.ajax('at', paths.checkThreeMonth, baseDate);
        }
        
        /**
         * call service check max month by base date
         */
        
        export function checkMonthMax(baseDate: Date): JQueryPromise<boolean> {
            return nts.uk.request.ajax('at', paths.checkMonthMax, baseDate);
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
