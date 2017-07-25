module nts.uk.at.view.kdl023.a.service {

    let servicePath: any = {
        getHoliday: 'at/schedule/holiday/getHolidayByListDate',
        getWorkingHour: 'at/shared/worktime/findByCodeList',
        getWorkType: 'at/share/worktype/findAll',
        getAllPattern: 'ctx/at/share/vacation/setting/patterncalendar/getallpattcal'
    }

    export function save(key: any, data: model.PatternReflection): JQueryPromise<void> {
        return nts.uk.characteristics.save(key, data);
    }

    export function find(key: string): JQueryPromise<model.PatternReflection> {
        return nts.uk.characteristics.restore(key);
    }
    export function findAllPattern(): JQueryPromise<model.DailyPatternSetting> {
        return nts.uk.request.ajax(servicePath.getAllPattern);
    }
    export function getHolidayByListDate(dates: Array<string>): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(servicePath.getHoliday, dates);
    }
    export function getAllWorktype(): JQueryPromise<Array<model.WorkType>> {
        return nts.uk.request.ajax(servicePath.getWorkType);
    }
    export function getListWorkingHour(codes: Array<string>): JQueryPromise<Array<model.WorkTime>> {
        return nts.uk.request.ajax(servicePath.getWorkingHour, codes);
    }
    export function findWeeklyWorkSetting(): JQueryPromise<model.WeeklyWorkSetting> {
        let dfd = $.Deferred();
        // default la working day.
        dfd.resolve({
            monday: 0,
            tuesday: 0,
            wednesday: 2,
            thursday: 0,
            friday: 0,
            saturday: 1,
            sunday: 2,
        });
        return dfd.promise();
    }

    export module model {
        export interface PatternReflection {
            employeeId: string;
            reflectionMethod: ReflectionMethod;
            patternClassification: PatternClassification;
            statutorySetting: DayOffSetting;
            nonStatutorySetting: DayOffSetting;
            holidaySetting: DayOffSetting;
        }
        export interface DailyPatternSetting {
            patternCode: string;
            patternName: string;
            workPatterns: Array<DailyPatternValue>;
        }
        export interface DailyPatternValue {
            dispOrder: number;
            workTypeCode: string;
            workingHoursCode: string;
            days: number;
        }
        export interface WorkType {
            workTypeCode: string;
            name: string;
        }
        export interface WorkTime {
            code: string;
            name: string;
        }
        export interface DayOffSetting {
            useClassification: boolean;
            workTypeCode: string;
        }
        export enum PatternClassification {
            Confirmation,
            Configuration
        }
        export enum ReflectionMethod {
            Overwrite,
            FillInTheBlank
        }
        export enum WorkDayDivision {
            WorkingDay = 0,
            NonWorkingDayInLaw = 1,
            NonWorkingDayOutrage = 2
        }
        export interface WeeklyWorkSetting {
            monday: WorkDayDivision;
            tuesday: WorkDayDivision;
            wednesday: WorkDayDivision;
            thursday: WorkDayDivision;
            friday: WorkDayDivision;
            saturday: WorkDayDivision;
            sunday: WorkDayDivision;
        }
        export interface PublicHoliday {
            day: number;
            holidayName: string;
        }
    }
}