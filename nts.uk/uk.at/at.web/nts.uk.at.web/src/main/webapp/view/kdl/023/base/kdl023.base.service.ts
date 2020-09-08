module nts.uk.at.view.kdl023.base.service {

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
    export function registerMonthlyPattern(data: model.MonthlyPatternRegisterCommand){
        return nts.uk.request.ajax(servicePath.registerMonthlyPattern, data)
    }
    export function startUpWindows(data: model.GetStartupInfoParam): JQueryPromise<model.WorkCycleReflectionDto>{
        return nts.uk.request.ajax(servicePath.startUp, data)
    }
    export function getReflectionWorkCycleAppImage( data: model.GetWorkCycleAppImageParam): JQueryPromise<Array<model.RefImageEachDayDto>>{
        return nts.uk.request.ajax(servicePath.getWorkCycleAppImage, data)
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

        export enum BootMode {
            REF_MODE = 0,
            EXEC_MODE = 1
        }

        export enum WorkStyle {
            ONE_DAY_REST = 0,
            MORNING_WORK = 1,
            AFTERNOON_WORK = 2,
            ONE_DAY_WORK = 3
        }

        export enum WorkCreateMethod {
            NON = -1,
            WORK_CYCLE = 0,
            WEEKLY_WORK = 1,
            PUB_HOLIDAY = 2
        }

        export interface WorkCycleReflectionDto {
            pubHoliday: Array<WorkType>;
            satHoliday: Array<WorkType>;
            nonSatHoliday: Array<WorkType>;
            reflectionImage: Array<RefImageEachDayDto>;
            workCycleList: Array<WorkCycle>;
        }

        export interface RefImageEachDayDto {
            workCreateMethod: number;
            workInformation: WorkInformationDto
            date: string;
            workStyles: number;
        }

        export interface WorkInformationDto {
            workTypeCode: string;
            workTimeCode: string;
        }

        export interface WorkInformationDto {
            workTypeCode: string;
            workTimeCode: string;
        }

        export interface GetStartupInfoParam {
            bootMode: number;
            creationPeriodStartDate: string;
            creationPeriodEndDate: string;
            workCycleCode: string;
            refOrder: Array<number>;
            numOfSlideDays: number;
        }

        export interface GetWorkCycleAppImageParam {
            creationPeriodStartDate: string;
            creationPeriodEndDate: string;
            workCycleCode: string;
            refOrder: Array<number>;
            numOfSlideDays: number;
            legalHolidayCd: string;
            nonStatutoryHolidayCd: string;
            holidayCd: string;
        }

        export interface MonthlyPatternRegisterCommand {
            isOverWrite: boolean;
            workMonthlySetting: Array<WorkMonthlySetting>;
        }

        export interface WorkMonthlySetting {
            workInformation: WorkInformationDto;
            ymk: string;
            monthlyPatternCode: string;
        }

        export interface WorkCycle {
            code: string;
            name: string;
            infos: Array<WorkCycleInfor>
        }

        export interface WorkCycleInfor {
            typeCode: string;
            timeCode: string;
            days: number;
            dispOrder: number;
        }
    }
}