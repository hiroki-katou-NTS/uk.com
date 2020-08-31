module nts.uk.at.view.kdl023.base.service {

    import GetStartupInfoParam = nts.uk.at.view.kdl023.base.service.model.GetStartupInfoParam;
    let servicePath: any = {
        getHoliday: 'at/schedule/holiday/getHolidayByListDate',
        getWorkTime: 'at/shared/worktimesetting/findAll',
        getWorkType: 'at/share/worktype/findAll',
        getAllPattern: 'ctx/at/schedule/shift/pattern/daily/getall',
        findPatternByCode: 'ctx/at/schedule/shift/pattern/daily/find',
        getWeeklyWorkSetting: 'ctx/at/schedule/pattern/work/weekly/setting/findAll',
        registerMonthlyPattern: 'ctx/at/schedule/pattern/work/monthly/setting/register',
        startUp: 'screen/at/shift/workcycle/workcycle-reflection/start',
        getWorkCycleAppImage: 'screen/at/shift/workcycle/workcycle-reflection/get-reflection-image'
    }

    export function findAllPattern(): JQueryPromise<Array<model.DailyPatternSetting>> {
        return nts.uk.request.ajax(servicePath.getAllPattern);
    }
    export function findPatternByCode(code: string): JQueryPromise<model.DailyPatternSetting> {
        return nts.uk.request.ajax(servicePath.findPatternByCode + '/' + code);
    }
    export function getHolidayByListDate(dates: Array<string>): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(servicePath.getHoliday, dates);
    }
    export function getAllWorkType(): JQueryPromise<Array<model.WorkType>> {
        return nts.uk.request.ajax(servicePath.getWorkType);
    }
    export function getAllWorkTime(): JQueryPromise<Array<model.WorkTime>> {
        return nts.uk.request.ajax(servicePath.getWorkTime);
    }
    export function findWeeklyWorkSetting(): JQueryPromise<model.WeeklyWorkSetting> {
        return nts.uk.request.ajax(servicePath.getWeeklyWorkSetting);
    }
    export function registerMonthlyPattern(data: any){
        return nts.uk.request.ajax(servicePath.registerMonthlyPattern)
    }
    export function startUpWindows(data: GetStartupInfoParam): JQueryPromise<model.WorkCycleReflectionDto>{
        return nts.uk.request.ajax(servicePath.startUp, data)
    }
    export function getReflectionWorkCycleAppImage( data: any): JQueryPromise<model.WorkCycleReflectionDto>{
        return nts.uk.request.ajax(servicePath.getWorkCycleAppImage)
    }

    export module model {
        export interface ReflectionSetting {
            calendarStartDate?: string;
            calendarEndDate?: string;
            selectedPatternCd: string;
            patternStartDate: string; // 'YYYY-MM-DD'
            reflectionMethod: ReflectionMethod;
            statutorySetting: DayOffSetting;
            nonStatutorySetting: DayOffSetting;
            holidaySetting: DayOffSetting;
			bootMode: number;
        }
        export interface DailyPatternSetting {
            patternCode: string;
            patternName: string;
            dailyPatternVals: Array<DailyPatternValue>;
        }
        export interface DailyPatternValue {
            dispOrder: number;
            workTypeSetCd: string;
            workingHoursCd: string;
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
        export enum ReflectionMethod {
            Overwrite = 0,
            FillInTheBlank = 1
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
        export enum BootMode{
            REF_MODE = 0,
            EXEC_MODE = 1
        }

        export interface WorkCycleReflectionDto{
            pubHoliday: Array<WorkType>;
            satHoliday: Array<WorkType>;
            nonSatHoliday: Array<WorkType>;
            reflectionImage: Array<RefImageEachDayDto>;
            workCycleList: Array<any>;
    }

        export interface RefImageEachDayDto{
            workCreateMethod: number;
            workInformation: WorkInformationDto
            date: Date;
            workStyles: number;
        }

        export interface WorkInformationDto{
            workTypeCode: string;
            workTimeCode: string;
        }

        export interface GetStartupInfoParam{
            bootMode: number;
            creationPeriodStartDate: string;
            creationPeriodEndDate: string;
            workCycleCode: string;
            refOrder: Array<number>;
            numOfSlideDays: number;
        }
    }
}