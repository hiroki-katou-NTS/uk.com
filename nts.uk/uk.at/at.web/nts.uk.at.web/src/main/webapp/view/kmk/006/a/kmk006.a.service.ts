module nts.uk.at.view.kmk006.a {
    export module service {
        var paths = {
            findCompanySettingEstimate: "ctx/at/schedule/shift/estimate/usagesetting/find",
            findCompanyEstablishment: "ctx/at/schedule/shift/estimate/company/find",
            saveCompanyEstablishment: "ctx/at/schedule/shift/estimate/company/save",
            deleteCompanyEstablishment: "ctx/at/schedule/shift/estimate/company/delete",
            findEmploymentEstablishment: "ctx/at/schedule/shift/estimate/employment/find",
            saveEmploymentEstablishment: "ctx/at/schedule/shift/estimate/employment/save",
            deleteEmploymentEstablishment: "ctx/at/schedule/shift/estimate/employment/delete",
            findAllEmploymentSetting: "ctx/at/schedule/shift/estimate/employment/findAll",
            findPersonalEstablishment: "ctx/at/schedule/shift/estimate/personal/find",
            findAllPersonalSetting: "ctx/at/schedule/shift/estimate/personal/findAll",
            savePersonalEstablishment: "ctx/at/schedule/shift/estimate/personal/save",
            deletePersonalEstablishment: "ctx/at/schedule/shift/estimate/personal/delete",




            findEnumUnitAutoCal: "ctx/at/schedule/shift/autocalunit/find/autocalunit",
            findEnumAutoCalAtrOvertime: "ctx/at/schedule/shift/autocal/find/autocalatrovertime",
            findEnumUseClassification: "ctx/at/schedule/shift/autocal/find/autocaluseclassification",
            findEnumTimeLimitUpperLimitSetting: "ctx/at/schedule/shift/autocal/find/autocaltimelimitsetting",
            findEnumUseUnitOvertimeSetting: "ctx/at/schedule/shift/autocal/find/autocaluseunitovertimesetting",
            getComAutoCal: "ctx/at/schedule/shift/autocalcom/getautocalcom",
            saveComAutoCal: "ctx/at/schedule/shift/autocalcom/save",
            saveJobAutoCal: "ctx/at/schedule/shift/autocaljob/save",
            saveWkpAutoCal: "ctx/at/schedule/shift/autocalwkp/save",
            saveWkpJobAutoCal: "ctx/at/schedule/shift/autocalwkpjob/save",
            getJobAutoCal: "ctx/at/schedule/shift/autocaljob/getautocaljob",
            getWkpAutoCal: "ctx/at/schedule/shift/autocalwkp/getautocalwkp",
            getWkpJobAutoCal: "ctx/at/schedule/shift/autocalwkpjob/getautocalwkpjob",
            deleteJobAutoCal: "ctx/at/schedule/shift/autocaljob/delete",
            deleteWkpAutoCal: "ctx/at/schedule/shift/autocalwkp/delete",
            deleteWkpJobAutoCal: "ctx/at/schedule/shift/autocalwkpjob/delete"
        }

        /**
        * delete divergence reason
       */
        export function deleteJobAutoCal(jobId: string): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.deleteJobAutoCal + '/' + jobId);
        }


        /**
        * delete divergence reason
       */
        export function deleteWkpAutoCal(wkpId: string): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.deleteWkpAutoCal + '/' + wkpId);
        }


        /**
        * delete divergence reason
       */
        export function deleteWkpJobAutoCal(wkpId: string, jobId: string): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.deleteWkpJobAutoCal + '/' + wkpId + '/' + jobId);
        }


        export function findEnumAutoCalAtrOvertime(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(paths.findEnumAutoCalAtrOvertime);
        }

        export function findEnumUseClassification(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(paths.findEnumUseClassification);
        }

        export function findEnumTimeLimitUpperLimitSetting(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(paths.findEnumTimeLimitUpperLimitSetting);
        }

        export function findEnumUseUnitOvertimeSetting(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(paths.findEnumUseUnitOvertimeSetting);
        }

        /**
        * save
        */
        export function saveComAutoCal(command: model.ComAutoCalSettingDto): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.saveComAutoCal, command);
        }
        /**
      * save
      */
        export function saveJobAutoCal(command: model.JobAutoCalSettingDto): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.saveJobAutoCal, command);
        }
        /**
      * save
      */
        export function saveWkpAutoCal(command: model.WkpAutoCalSettingDto): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.saveWkpAutoCal, command);
        }
        /**
      * save
      */
        export function saveWkpJobAutoCal(command: model.WkpJobAutoCalSettingDto): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.saveWkpJobAutoCal, command);
        }

        export function getComAutoCal(): JQueryPromise<model.ComAutoCalSettingDto> {
            return nts.uk.request.ajax("at", paths.getComAutoCal);
        }

        export function getJobAutoCal(jobId: string): JQueryPromise<model.JobAutoCalSettingDto> {
            return nts.uk.request.ajax("at", paths.getJobAutoCal + '/' + jobId);
        }

        export function getWkpAutoCal(wkpId: string): JQueryPromise<model.WkpAutoCalSettingDto> {
            return nts.uk.request.ajax("at", paths.getWkpAutoCal + '/' + wkpId);
        }
        export function getWkpJobAutoCal(wkpId: string, jobId: string): JQueryPromise<model.WkpJobAutoCalSettingDto> {
            return nts.uk.request.ajax("at", paths.getWkpJobAutoCal + '/' + wkpId + '/' + jobId);
        }
        export function getEnumUnitAutoCal(): JQueryPromise<model.UnitAutoCalSettingDto> {
            return nts.uk.request.ajax("at", paths.findEnumUnitAutoCal);
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

        /**
         * call service find setting estimate 
         */
        export function findCompanySettingEstimate(): JQueryPromise<model.UsageSettingDto> {
            return nts.uk.request.ajax('at', paths.findCompanySettingEstimate);
        }

        /**
         * call service find all setting employment
         */
        export function findAllEmploymentSetting(targetYear: number): JQueryPromise<model.EmploymentEstablishmentDto[]> {
            return nts.uk.request.ajax('at', paths.findAllEmploymentSetting + '/' + targetYear);
        }

        /**
         * call service find all setting personal
         */
        export function findAllPersonalSetting(targetYear: number): JQueryPromise<model.PersonalEstablishmentDto[]> {
            return nts.uk.request.ajax('at', paths.findAllPersonalSetting + '/' + targetYear);
        }

        export module model {
            //modelauto

            export interface WkpJobAutoCalSettingDto {
                wkpId: string;
                jobId: string;
                normalOTTime: AutoCalOvertimeSettingDto;
                flexOTTime: AutoCalFlexOvertimeSettingDto;
                restTime: AutoCalRestTimeSettingDto;
            }

            export interface WkpAutoCalSettingDto {
                wkpId: string;
                normalOTTime: AutoCalOvertimeSettingDto;
                flexOTTime: AutoCalFlexOvertimeSettingDto;
                restTime: AutoCalRestTimeSettingDto;
            }

            export interface JobAutoCalSettingDto {
                jobId: string;
                normalOTTime: AutoCalOvertimeSettingDto;
                flexOTTime: AutoCalFlexOvertimeSettingDto;
                restTime: AutoCalRestTimeSettingDto;
            }

            export interface ComAutoCalSettingDto {
                normalOTTime: AutoCalOvertimeSettingDto;
                flexOTTime: AutoCalFlexOvertimeSettingDto;
                restTime: AutoCalRestTimeSettingDto;
            }

            export interface AutoCalFlexOvertimeSettingDto {
                flexOtTime: AutoCalSettingDto;
                flexOtNightTime: AutoCalSettingDto;
            }

            export interface AutoCalRestTimeSettingDto {
                restTime: AutoCalSettingDto;
                lateNightTime: AutoCalSettingDto;
            }
            export interface AutoCalOvertimeSettingDto {
                earlyOtTime: AutoCalSettingDto;
                earlyMidOtTime: AutoCalSettingDto;
                normalOtTime: AutoCalSettingDto;
                normalMidOtTime: AutoCalSettingDto;
                legalOtTime: AutoCalSettingDto;
                legalMidOtTime: AutoCalSettingDto;
            }

            export interface AutoCalSettingDto {
                upLimitOtSet: number;
                calAtr: number;
            }

            export interface UnitAutoCalSettingDto {
                useJobSet: boolean;
                useWkpSet: boolean;
                useJobwkpSet: boolean;
            }






            export class Enum {
                value: number;
                fieldName: string;
                localizedName: string;

                constructor(value: number, fieldName: string, localizedName: string) {
                    this.value = value;
                    this.fieldName = fieldName;
                    this.localizedName = localizedName;
                }
            }

            export interface UsageSettingDto {
                employmentSetting: boolean;
                personalSetting: boolean;
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
                setting: boolean;
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