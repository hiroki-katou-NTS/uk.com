module nts.uk.at.view.kmk010.a {
    export module service {
        var paths = {
            findAllOvertimeCalculationMethod: "ctx/at/shared/overtime/setting/findAll/method",
            findAllOvertimeUnit: "ctx/at/shared/overtime/setting/findAll/unit",
            findAllOvertimeRounding: "ctx/at/shared/overtime/setting/findAll/rounding",
            findByIdOvertimeSetting: "ctx/at/shared/overtime/setting/findById",
            findAllPremiumExtra60HRate: "ctx/at/shared/overtime/premium/extra/findAll",
            findByIdSuperHD60HConMed: "ctx/at/shared/overtime/super/holiday/findById",
            saveOvertimeSetting: "ctx/at/shared/overtime/setting/save",
            saveSuperHD60HConMed: "ctx/at/shared/overtime/super/holiday/save",
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
         * find all data overtime rounding
         */
        export function findAllOvertimeRounding(): JQueryPromise<model.EnumConstantDto[]> {
            return nts.uk.request.ajax(paths.findAllOvertimeRounding);
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
        /**
         * save overtime setting to service
         */
        export function saveOvertimeSetting(dto: model.OvertimeSettingDto): JQueryPromise<void>{
            return nts.uk.request.ajax(paths.saveOvertimeSetting, { setting: dto });
        }

        /**
         * save super holiday 60h method
         */
        export function saveSuperHD60HConMed(dto: model.SuperHD60HConMedDto): JQueryPromise<void> {
            return nts.uk.request.ajax(paths.saveSuperHD60HConMed, { setting: dto });
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
                setting: boolean;
                rounding: number;
                superHolidayOccurrenceUnit: number;
                premiumExtra60HRates: PremiumExtra60HRateDto[];
            }
        }
    }
}