module nts.uk.at.view.ksm001.a {
    export module service {
        var paths = {
            findAllMonthlyEstimateTime: "ctx/at/schedule/shift/estimate/company/find"
        }

        /**
         * call service get all monthly estimate time of company
         */
        export function findAllMonthlyEstimateTime(targetYear: number): JQueryPromise<model.CompanyEstimateTimeDto> {
            return nts.uk.request.ajax('at', paths.findAllMonthlyEstimateTime + '/' + targetYear);
        }
        
        export module model {
            
            export interface TargetYearDto {
                code: string;
                name: number;
            }
            
            export interface EstimateTimeDto{
                time1st: number;
                time2nd: number;
                time3rd: number;
                time4th: number;
                time5th: number;    
                            
            }
            
            export interface CompanyEstimateTimeDto {
                monthlyEstimates: EstimateTimeDto[];
                yearlyEstimate: EstimateTimeDto;
            }

        }
    }


}