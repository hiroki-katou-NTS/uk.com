module nts.uk.at.view.ksm005.b {
    
    export module service {
        
        var paths = {
            findAllWorkType : "at/share/worktype/findAll",
            findAllWorkTime: "at/shared/worktimesetting/findAll",
            checkPublicHoliday: "at/schedule/holiday/getHolidayByDate",
            checkWeeklyWorkSetting: "ctx/at/schedule/pattern/work/weekly/setting/checkDate",
            batchWorkMonthlySetting: "ctx/at/schedule/pattern/work/monthly/setting/batch",
            getWeeklyWork: 'screen/at/record/weekly-work/get',
	        findAllMonthlyPattern: 'ctx/at/schedule/pattern/monthly/findAll'
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
         * check public holiday by date (YYYYMMDD)
         */
        export function checkPublicHoliday(baseDate: string): JQueryPromise<model.OptionalPublicHoliday> {
            return nts.uk.request.ajax('at', paths.checkPublicHoliday + '/' + baseDate);
        }
        /**
         * batch monthly pattern setting call service
         */
        export function batchWorkMonthlySetting(command: model.MonthlyPatternSettingBatchDto): JQueryPromise<void> {
            command.monthlyPatternCode = nts.uk.text.padLeft(command.monthlyPatternCode, '0', 3);
            return nts.uk.request.ajax('at', paths.batchWorkMonthlySetting, command);
        }
        /**
         * save to client service MonthlyPatternSettingBatch
         */
        export function saveMonthlyPatternSettingBatch(key: model.KeyMonthlyPatternSettingBatch,data: model.MonthlyPatternSettingBatch): void {
            nts.uk.characteristics.save(service.toKey(key), data);
        }

        /**
         * find data client service MonthlyPatternSettingBatch
         */
        export function findMonthlyPatternSettingBatch(key: model.KeyMonthlyPatternSettingBatch): JQueryPromise<model.MonthlyPatternSettingBatch> {
            return nts.uk.characteristics.restore(service.toKey(key));
        }
        
        /**
         * convert object key to string
         */
        export function toKey(key: model.KeyMonthlyPatternSettingBatch): string {
            return key.companyId + '_' + key.employeeId + '_' + key.businessDayClassification;
        }

	    /**
	     * find data client service MonthlyPatternSettingBatch
	     */
	    export function getWeeklyWork(params: model.WeeklyWork) {
		    return nts.uk.request.ajax('at', paths.getWeeklyWork, params);
	    }
	    /**
	     * call service find all monthly pattern
	     */
	    export function findAllMonthlyPattern(): JQueryPromise<model.MonthlyPatternDto[]> {
		    return nts.uk.request.ajax('at', paths.findAllMonthlyPattern);
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
                code: string;
                name: string;
            }
            
            export interface WeeklyWorkSettingDto{
                dayOfWeek: number;
                workdayDivision: number;    
            }
            
            export interface KeyMonthlyPatternSettingBatch{
                companyId: string;
                employeeId: string;
                businessDayClassification: BusinessDayClassification;
            }
            
            export interface UserInfoDto{
                companyId: string;
                employeeId: string;    
            }
            
            export interface OptionalPublicHoliday{
                present: boolean;    
            }

	        export interface WeeklyWork {
		        listWorkTypeCd: Array<string> ;
		        worktimeCode: string;
	        }

	        // ?????????????????????????????????
            export class MonthlyPatternSettingBatch {
                // ??????ID
                companyId: string;
                // ?????????????????????
                workTypeCode: string;
                // ????????????????????????
                workingCode: string;
                // ??????ID
                employeeId: string;
                // ???????????????
                isHolidayPriority: boolean;
                //???????????????
                businessDayClassification: BusinessDayClassification;
            }

            //???????????????
            export enum BusinessDayClassification{
                // ?????????
                WORK_DAYS = 0,
                //???????????????
                STATUTORY_HOLIDAYS = 1,
                //???????????????
                NONE_STATUTORY_HOLIDAYS = 2,
                //??????
                PUBLIC_HOLIDAYS = 3
            }
            
            //???????????????
            export enum WorkdayDivision{
                // ?????????
                WORKINGDAYS = 0,
                // ????????????????????????
                NON_WORKINGDAY_INLAW = 1,
                // ????????????????????????
                NON_WORKINGDAY_EXTRALEGAL = 2
            }
            
            export interface MonthlyPatternSettingBatchDto {
                settingWorkDays: MonthlyPatternSettingBatch;
                settingStatutoryHolidays: MonthlyPatternSettingBatch;
                settingNoneStatutoryHolidays: MonthlyPatternSettingBatch;
                settingPublicHolidays: MonthlyPatternSettingBatch;
                overwrite: boolean;
                startYearMonth: number;
                endYearMonth: number;
                monthlyPatternCode: string;
                monthlyPatternName: string;
            }
                       
        }

    }
}