module nts.uk.at.view.kmk010.a {
    export module service {
        var paths = {
            findAllOvertimeCalculationMethod: "ctx/at/shared/overtime/setting/findAll/method",
            findAllOvertimeUnit: "ctx/at/shared/overtime/setting/findAll/unit",
            findByIdOvertimeSetting: "ctx/at/shared/overtime/setting/findById",
            findAllPremiumExtra60HRate: "ctx/at/shared/overtime/premium/extra/findAll",
            findByIdSuperHD60HConMed: "ctx/at/shared/overtime/super/holiday/findById"
        }

        /**
         * find all data overtime calculation method
         */
        export function findAllOvertimeCalculationMethod(): JQueryPromise<model.EnumConstantDto[]> {
            return nts.uk.request.ajax(paths.findAllOvertimeCalculationMethod);
        }
        
        /**
         * find all data overtime unit
         */
        export function findAllOvertimeUnit(): JQueryPromise<model.EnumConstantDto[]> {
            return nts.uk.request.ajax(paths.findAllOvertimeUnit);
        }

        /**
         * find all data overtime calculation method
         */
        export function findByIdOvertimeSetting(): JQueryPromise<model.OvertimeSettingDto> {
            return nts.uk.request.ajax(paths.findByIdOvertimeSetting);
        }
        
        /**
         * find all data premium extra 60h rate
         */
        export function findAllPremiumExtra60HRate(): JQueryPromise<model.PremiumExtra60HRateDto> {
            return nts.uk.request.ajax(paths.findAllPremiumExtra60HRate);
        }

        /**
         *  find by id super holiday method
         */
        export function findByIdSuperHD60HConMed(): JQueryPromise<model.SuperHD60HConMedDto> {
            return nts.uk.request.ajax(paths.findByIdSuperHD60HConMed);
        }


        export module model {

            export interface EnumConstantDto {
                value: number;
                fieldName: string;
                localizedName: string;
            }

            export interface OvertimeDto {
                name: string;
                overtime: number;
                overtimeNo: number;
                useClassification: boolean;
            }

            export interface OvertimeBRDItemDto {
                useClassification: boolean;
                breakdownItemNo: number;
                name: string;
                productNumber: number;
            }
            
            export interface OvertimeSettingDto {
                note: string;
                calculationMethod: number;
                overtimes: OvertimeDto[];
                breakdownItems: OvertimeBRDItemDto[];
            }
            export interface PremiumExtra60HRateDto {
                overtimeNo: number;
                breakdownItemNo: number;
                premiumRate: number;
            }
            
            export interface SuperHD60HConMedDto {
                roundingTime: number;
                rounding: number;
                superHolidayOccurrenceUnit: number;
                premiumExtra60HRates: PremiumExtra60HRateDto[];
            }
        }
    }
}