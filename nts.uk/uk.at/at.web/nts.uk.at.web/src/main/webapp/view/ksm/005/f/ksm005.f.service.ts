module nts.uk.at.view.ksm005.f {
    export module service {
        var paths = {
            findAllMonthlyPattern : "ctx/at/schedule/pattern/monthly/findAll",
        }
        
         /**
         * call service find all monthly pattern
         */
        export function findAllMonthlyPattern(): JQueryPromise<model.MonthlyPatternDto[]> {
            return nts.uk.request.ajax('at', paths.findAllMonthlyPattern);
        }
        
        
        export module model {
            export interface MonthlyPatternDto {
                code: string;
                name: string;
            }
        }
    }
}