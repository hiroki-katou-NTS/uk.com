module nts.uk.at.view.ksm001.a {
    export module service {
        var paths = {
            findCompanyEstablishment: "ctx/at/schedule/shift/estimate/company/find",
            saveCompanyEstimate: "ctx/at/schedule/shift/estimate/company/save"
        }

        /**
         * call service get all monthly estimate time of company
         */
        export function findCompanyEstablishment(targetYear: number): JQueryPromise<model.CompanyEstablishmentDto> {
            return nts.uk.request.ajax('at', paths.findCompanyEstablishment + '/' + targetYear);
        }

        /**
        * call service save estimate company
        */
        export function saveCompanyEstimate(targetYear: number, dto: model.CompanyEstablishmentDto): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.saveCompanyEstimate, {
                estimateTime: dto.estimateTime,
                estimatePrice: dto.estimatePrice,
                estimateNumberOfDay: dto.estimateNumberOfDay,
                targetYear: targetYear
            });
        }

        export module model {

            export interface TargetYearDto {
                code: string;
                name: number;
            }

            export interface EstimateTimeDto {
                month: number;
                time1st: number;
                time2nd: number;
                time3rd: number;
                time4th: number;
                time5th: number;

            }
            export interface EstimatePriceDto {
                month: number;
                price1st: number;
                price2nd: number;
                price3rd: number;
                price4th: number;
                price5th: number;

            }
            
            export interface EstimateDaysDto {
                month: number;
                days1st: number;
                days2nd: number;
                days3rd: number;
                days4th: number;
                days5th: number;

            }

            export interface CompanyEstimateTimeDto {
                monthlyEstimates: EstimateTimeDto[];
                yearlyEstimate: EstimateTimeDto;
            }
            

            export interface CompanyEstimatePriceDto {
                monthlyEstimates: EstimatePriceDto[];
                yearlyEstimate: EstimatePriceDto;
            }
            
            export interface CompanyEstimateDaysDto {
                monthlyEstimates: EstimateDaysDto[];
                yearlyEstimate: EstimateDaysDto;
            }

            export interface CompanyEstablishmentDto {
                estimateTime: CompanyEstimateTimeDto;
                estimatePrice: CompanyEstimatePriceDto;
                estimateNumberOfDay: CompanyEstimateDaysDto;
            }

        }
    }


}