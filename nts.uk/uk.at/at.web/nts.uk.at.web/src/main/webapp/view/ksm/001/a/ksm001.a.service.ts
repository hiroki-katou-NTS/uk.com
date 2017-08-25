module nts.uk.at.view.ksm001.a {
    export module service {
        var paths = {
            findCompanyEstablishment: "ctx/at/schedule/shift/estimate/company/find",
            saveCompanyEstablishment: "ctx/at/schedule/shift/estimate/company/save",
            findEmploymentEstablishment: "ctx/at/schedule/shift/estimate/employment/find",
            saveEmploymentEstablishment: "ctx/at/schedule/shift/estimate/employment/save"
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
        export function saveCompanyEstablishment(targetYear: number, dto: model.CompanyEstablishmentDto): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.saveCompanyEstablishment, {
                estimateTime: dto.estimateTime,
                estimatePrice: dto.estimatePrice,
                estimateNumberOfDay: dto.estimateNumberOfDay,
                targetYear: targetYear
            });
        }
        
        /**
         * call service get all monthly estimate time of employment
         */
        export function findEmploymentEstablishment(targetYear: number, employmentCode: string): JQueryPromise<model.CompanyEstablishmentDto> {
            return nts.uk.request.ajax('at', paths.findEmploymentEstablishment + '/' + employmentCode + '/' + targetYear);
        }

        /**
        * call service save estimate employment
        */
        export function saveEmploymentEstablishment(targetYear: number, dto: model.EmploymentEstablishmentDto): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.saveEmploymentEstablishment, {
                estimateTime: dto.estimateTime,
                estimatePrice: dto.estimatePrice,
                estimateNumberOfDay: dto.estimateNumberOfDay,
                employmentName: dto.employmentName,
                employmentCode: dto.employmentCode,
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

            export interface EstablishmentTimeDto {
                monthlyEstimates: EstimateTimeDto[];
                yearlyEstimate: EstimateTimeDto;
            }
            

            export interface EstablishmentPriceDto {
                monthlyEstimates: EstimatePriceDto[];
                yearlyEstimate: EstimatePriceDto;
            }
            
            export interface EstablishmentDaysDto {
                monthlyEstimates: EstimateDaysDto[];
                yearlyEstimate: EstimateDaysDto;
            }

            export interface CompanyEstablishmentDto {
                estimateTime: EstablishmentTimeDto;
                estimatePrice: EstablishmentPriceDto;
                estimateNumberOfDay: EstablishmentDaysDto;
            }
            

            export interface EmploymentEstablishmentDto {
                employmentCode: string;
                employmentName: string;
                estimateTime: EstablishmentTimeDto;
                estimatePrice: EstablishmentPriceDto;
                estimateNumberOfDay: EstablishmentDaysDto;
            }

        }
    }


}