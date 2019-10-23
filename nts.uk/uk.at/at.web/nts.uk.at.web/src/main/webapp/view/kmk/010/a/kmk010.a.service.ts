module nts.uk.at.view.kmk010.a {
    
    export module service {
        var paths = {
            findAllOvertimeCalculationMethod: "ctx/at/shared/outsideot/setting/findAll/method",
            findAllAttendanceItemOvertime: "at/share/attendanceType/getAtdId",
            findAllOvertimeUnit: "ctx/at/shared/outsideot/setting/findAll/unit",
            findAllOvertimeRounding: "ctx/at/shared/outsideot/setting/findAll/rounding",
            findAllOvertimeRoundingSub: "ctx/at/shared/outsideot/setting/findAll/roundingsub",
            findByIdOutsideOTSetting: "ctx/at/shared/outsideot/setting/findById",
            findAllPremiumExtra60HRate: "ctx/at/shared/outsideot/holiday/findAll/premiumExtra",
            findByIdSuperHD60HConMed: "ctx/at/shared/outsideot/holiday/findById",
            saveOutsideOTSettingAndSupperHD60H: "ctx/at/shared/outsideot/setting/save",
            findAllOvertimeNameLanguage: "ctx/at/shared/outsideot/overtime/name/language/findAll",
            findAllOvertime : "ctx/at/shared/outsideot/overtime/findAll",
            findAllOvertimeLanguageBRDItem : "ctx/at/shared/outsideot/breakdown/language/findAll",
            findAllOutsideOTBRDItem : "ctx/at/shared/outsideot/breakdown/findAll",
            findAllDailyAttendanceItem: "at/record/businesstype/attendanceItem/getAttendanceItems",
            findAllMonthlyAttendanceItem: "at/record/businesstype/attendanceItem/getMonthlyAttendanceItems",
            checkManageSixtyHourVacationSetting: "ctx/at/shared/vacation/setting/sixtyhourvacation/com/check/manage",
            exportOutsideOTSettingExcelMasterList: "/masterlist/report/print"
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
         * find all data overtime rounding
         */
        export function findAllOvertimeRoundingSub(): JQueryPromise<model.EnumConstantDto[]> {
            return nts.uk.request.ajax(paths.findAllOvertimeRoundingSub);
        }

        /**
         * find all data overtime calculation method
         */
        export function findByIdOutsideOTSetting(): JQueryPromise<model.OutsideOTSettingDto> {
            return nts.uk.request.ajax(paths.findByIdOutsideOTSetting);
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
        export function saveOutsideOTSettingAndSupperHD60H(dtoSetting: model.OutsideOTSettingDto, dtoSuper: model.SuperHD60HConMedDto): JQueryPromise<void>{
            return nts.uk.request.ajax(paths.saveOutsideOTSettingAndSupperHD60H, { setting: dtoSetting, superholidayConvertMethod :dtoSuper});
        }

        /**
         * find all overtime language name
         */
        export function findAllOvertimeNameLanguage(languageId: string): JQueryPromise<model.OvertimeNameLangDto[]> {
            return nts.uk.request.ajax(paths.findAllOvertimeNameLanguage + '/' + languageId);
        }
        
         /**
         * call service find all overtime
         */
        export function findAllOvertime(): JQueryPromise<model.OvertimeDto[]> {
            return nts.uk.request.ajax('at', paths.findAllOvertime);
        }
        
        /**
         * find all overtime language breakdown item
         */
        export function findAllOvertimeLanguageBRDItem(languageId: string): JQueryPromise<model.OutsideOTBRDItemLangDto[]> {
            return nts.uk.request.ajax('at', paths.findAllOvertimeLanguageBRDItem + '/' + languageId);
        } 
        /**
         * call service find all overtime breakdown item
         */
        export function findAllOutsideOTBRDItem(): JQueryPromise<model.OutsideOTBRDItemDto[]> {
            return nts.uk.request.ajax('at', paths.findAllOutsideOTBRDItem);
        }
        /**
         * call service find all daily attendance item
         */
        export function findAllDailyAttendanceItem(): JQueryPromise<model.DailyAttendanceItemDto[]> {
            return nts.uk.request.ajax('at', paths.findAllDailyAttendanceItem);
        }
        
        /**
         * call service find all monthly attendance item
         */
        export function findAllMonthlyAttendanceItem(): JQueryPromise<model.DailyAttendanceItemDto[]> {
            return nts.uk.request.ajax('at', paths.findAllMonthlyAttendanceItem);
        }

        /**
         * call service check manage sixty hour vacation setting
         */
        export function checkManageSixtyHourVacationSetting(): JQueryPromise<model.SixtyHourVacationSettingCheckDto> {
            return nts.uk.request.ajax('at', paths.checkManageSixtyHourVacationSetting);
        }
        
        /**
         * call service find all attendance item overtime
         */
        export function findAllAttendanceItemOvertime(): JQueryPromise<number[]> {
            return nts.uk.request.ajax('at', paths.findAllAttendanceItemOvertime);
        }
        
        /**
         * function call service export
         */
        
        export function exportOutsideOTSettingExcelMasterList(languageId: string): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportOutsideOTSettingExcelMasterList, { domainId: "outsideot", domainType: "時間外超過設定", languageId: languageId, reportType: 0, data: [] });
        }


        /**
         * Init tooltip service.
         */
        export function initTooltip() {
            $('.tooltip-label').each((index, e) => {
                let $label = $(e);

                // Check if contents is overflow
                if ($label.outerWidth() < $label[0].scrollWidth) {
                    $label.attr('title', $label.text());
                } else {
                    $label.removeAttr('title');
                }
            })
            $('.tooltip-label').tooltip({
                tooltipClass: 'tooltip-label-view',
                show: { duration: 100 },
                track: true
            });
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
                superHoliday60HOccurs: boolean;
            }

            export interface OutsideOTBRDItemDto {
                useClassification: boolean;
                breakdownItemNo: number;
                name: string;
                productNumber: number;
                attendanceItemIds: number[];
            }
            
            export interface OutsideOTSettingDto {
                note: string;
                calculationMethod: number;
                overtimes: OvertimeDto[];
                breakdownItems: OutsideOTBRDItemDto[];
            }
            export interface PremiumExtra60HRateDto {
                overtimeNo: number;
                breakdownItemNo: number;
                premiumRate: number;
                tabIndex: number;
            }
            
            export interface SuperHD60HConMedDto {
                roundingTime: number;
                setting: boolean;
                rounding: number;
                superHolidayOccurrenceUnit: number;
                premiumExtra60HRates: PremiumExtra60HRateDto[];
            }
            
            export interface OvertimeNameLangDto {
                name: string;
                languageId: string;
                overtimeNo: number;
            }
            
            export interface OutsideOTBRDItemLangDto {
                name: string;
                languageId: string;
                breakdownItemNo: number;
            }  
            
            export interface DailyAttendanceItemDto {
                attendanceItemId: number;
                attendanceItemName: string;
            }
            
            export interface SixtyHourVacationSettingCheckDto{
                manage: boolean;
            }
            
        }
    }
}