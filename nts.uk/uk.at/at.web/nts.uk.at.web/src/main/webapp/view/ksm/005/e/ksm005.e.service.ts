module nts.uk.at.view.ksm005.e {
    
    export module service {
        
        var paths = {
            findAllWorkType : "at/share/worktype/findAll",
            findAllWorkTime: "at/shared/worktime/findByCompanyID",
            checkWeeklyWorkSetting: "ctx/at/schedule/pattern/work/weekly/setting/checkDate"
        }
        
        /**
         * call service find all work type of company login
         */
        export function findAllWorkType(): JQueryPromise<model.WorkTypeDto[]> {
            return nts.uk.request.ajax('at', paths.findAllWorkType);
        }
        
        /**
         * call service find all work time of company login
         */
        export function findAllWorkTime(): JQueryPromise<model.WorkTimeDto[]> {
            return nts.uk.request.ajax('at', paths.findAllWorkTime);
        }
        /**
         * call service check base date weekly work setting
         */
        export function checkWeeklyWorkSetting(baseDate: Date): JQueryPromise<model.WeeklyWorkSettingDto> {
            return nts.uk.request.ajax('at', paths.checkWeeklyWorkSetting, baseDate);
        }
        
        
        /**
         * save to client service MonthlyPatternSettingBatch
         */
        export function saveMonthlyPatternSettingBatch(businessDayClassification: model.BusinessDayClassification,data: model.MonthlyPatternSettingBatch): void {
            nts.uk.characteristics.save(model.KeyMonthlyPatternSettingBatch.KEY+businessDayClassification, data);
        }

        export function findMonthlyPatternSettingBatch(businessDayClassification: model.BusinessDayClassification): JQueryPromise<model.MonthlyPatternSettingBatch> {
            return nts.uk.characteristics.restore(model.KeyMonthlyPatternSettingBatch.KEY+businessDayClassification);
        }
        
        export module model {

            export interface WorkTypeDto {
                workTypeCode: string;
                name: string;
            }
            export interface WorkTimeDto {
                code: string;
                name: string;
            }
            
            export interface MonthlyPatternDto {
                monthlyPatternCode: string;
                monthlyPatternName: string;
            }
            
            export interface WeeklyWorkSettingDto{
                dayOfWeek: number;
                workdayDivision: number;    
            }
            
            export class KeyMonthlyPatternSettingBatch{
                static KEY = 'KEYMONTHLYPATTERNSETTINGBATCH';    
            }
            
            // 月間パターンの一括設定
            export class MonthlyPatternSettingBatch {
                // 会社ID
                companyId: string;
                // 勤務種類コード
                workTypeCode: string;
                // 就業時間帯コード
                siftCode: string;
                // 社員ID
                employeeId: string;
                //稼働日区分
                businessDayClassification: BusinessDayClassification;
            }

            export enum BusinessDayClassification{
                // 稼働日
                WorkDays = 0,
                //法定内休日
                StatutoryHolidays = 1,
                //法定外休日
                NoneStatutoryHolidays = 2,
                //祝日
                PublicHolidays = 3
            }
            
            //稼働日区分
            export enum WorkdayDivision{
                // 稼働日
                WORKINGDAYS = 0,
                // 非稼働日（法内）
                NON_WORKINGDAY_INLAW = 1,
                // 非稼働日（法外）
                NON_WORKINGDAY_EXTRALEGAL = 2
            }
                       
        }

    }
}