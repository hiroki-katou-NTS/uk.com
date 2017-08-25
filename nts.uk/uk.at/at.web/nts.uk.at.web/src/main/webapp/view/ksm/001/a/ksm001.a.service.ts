module nts.uk.at.view.ksm001.a {
    export module service {
        var paths = {
            findCompanyEstablishment: "ctx/at/schedule/shift/estimate/company/find",
            saveCompanyEstablishment: "ctx/at/schedule/shift/estimate/company/save",
            deleteCompanyEstablishment: "ctx/at/schedule/shift/estimate/company/delete",
            findEmploymentEstablishment: "ctx/at/schedule/shift/estimate/employment/find",
            saveEmploymentEstablishment: "ctx/at/schedule/shift/estimate/employment/save",
            deleteEmploymentEstablishment: "ctx/at/schedule/shift/estimate/employment/delete",
            findPersonalEstablishment: "ctx/at/schedule/shift/estimate/personal/find",
            savePersonalEstablishment: "ctx/at/schedule/shift/estimate/personal/save",
            deletePersonalEstablishment: "ctx/at/schedule/shift/estimate/personal/delete"
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
        * call service delete estimate company
        */
        export function deleteCompanyEstablishment(targetYear: number): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.deleteCompanyEstablishment, {
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
        /**
        * call service delete estimate employment
        */
        export function deleteEmploymentEstablishment(targetYear: number, employmentCode: string): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.deleteEmploymentEstablishment, {
                targetYear: targetYear,
                employmentCode: employmentCode
            });
        }
        
        /**
         * call service get all monthly estimate time of personal
         */
        export function findPersonalEstablishment(targetYear: number, employeeId: string): JQueryPromise<model.PersonalEstablishmentDto> {
            return nts.uk.request.ajax('at', paths.findPersonalEstablishment + '/' + targetYear + '/' + employeeId);
        }

        /**
        * call service save estimate personal
        */
        export function savePersonalEstablishment(targetYear: number, dto: model.PersonalEstablishmentDto): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.savePersonalEstablishment, {
                estimateTime: dto.estimateTime,
                estimatePrice: dto.estimatePrice,
                estimateNumberOfDay: dto.estimateNumberOfDay,
                employeeId: dto.employeeId,
                targetYear: targetYear
            });
        }
        
        /**
        * call service delete estimate personal
        */
        export function deletePersonalEstablishment(targetYear: number, employeeId: string): JQueryPromise<void> {
            return nts.uk.request.ajax('at', paths.deletePersonalEstablishment, {
                employeeId: employeeId,
                targetYear: targetYear
            });
        }

        export module model {

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
            export interface PersonalEstablishmentDto {
                employeeId: string;
                estimateTime: EstablishmentTimeDto;
                estimatePrice: EstablishmentPriceDto;
                estimateNumberOfDay: EstablishmentDaysDto;
            }

        }
    }
    


}