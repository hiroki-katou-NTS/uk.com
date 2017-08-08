module nts.uk.at.view.ksm001.a {
    export module service {

        /**
         * call service get all monthly
         */
        export function findAllMonthly(): JQueryPromise<model.MonthlyDto[]> {
            var dfd = $.Deferred();
            var arrMonthly: model.MonthlyDto[] = [];
            for(var i: number = 1; i<=12; i++){
                var monthly: model.MonthlyDto = { month: i, time001: 2017, time002: 2017, time003: 2017, time004: 2017, time005: 2017 };
                arrMonthly.push(monthly);
            }
            dfd.resolve(arrMonthly);
            return dfd.promise();
        }
        
        export module model {
            
            export interface TargetYearDto {
                code: string;
                name: number;
            }
            
            export interface MonthlyDto{
                month: number;
                time001: number;
                time002: number;
                time003: number;
                time004: number;
                time005: number;    
            }

        }
    }


}