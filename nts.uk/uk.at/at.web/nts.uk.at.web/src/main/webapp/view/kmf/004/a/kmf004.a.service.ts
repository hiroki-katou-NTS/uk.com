module nts.uk.at.view.kmf004.a.service {
    import format = nts.uk.text.format;
    import ajax = nts.uk.request.ajax;
    var paths: any = {
        findByCid: "shared/specialholiday/findByCid",
        getSpecialHoliday: "shared/specialholiday/getSpecialHoliday/{0}",
        add: "shared/specialholiday/add",
        update: "shared/specialholiday/update",
        remove: "shared/specialholiday/delete",
        getAllAbsenceFrame: "at/share/worktype/absenceframe/findAll",
        getAllSpecialHolidayFrame: "at/share/worktype/specialholidayframe/findAll",
        findEmpByCodes: "bs/employee/employment/findNamesByCodesWithNull",
        findClsByCodes: "bs/employee/classification/getClsNameByCds",
        findAllItemFrame: "shared/specialholiday/findAllItemFrame",
    }

    export function findAllItemFrame(): JQueryPromise<Array<any>> {
        var path = paths.findAllItemFrame;
        return nts.uk.request.ajax("at", path);
    }
    
 
    export function findByCid(): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.findByCid);
        return nts.uk.request.ajax("at", path);
    }
    
    export function getSpecialHoliday(specialHolidayCode: number): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.getSpecialHoliday, specialHolidayCode);
        return nts.uk.request.ajax("at", path);
    }
 
    export function add(data: SpecialHolidayItem): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.add);
        return nts.uk.request.ajax("at", path, data);
    }
 
    export function update(data: SpecialHolidayItem): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.update);
        return nts.uk.request.ajax("at", path, data);
    }
 
    export function remove(specialHolidayCode: number): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.remove);
        return nts.uk.request.ajax("at", path, { specialHolidayCode: specialHolidayCode });
    }
 
    export function getAllAbsenceFrame(): JQueryPromise<Array<any>> {
        var path = paths.getAllAbsenceFrame;
        return nts.uk.request.ajax("at", path);
    }
 
    export function getAllSpecialHolidayFrame(): JQueryPromise<Array<any>> {
        var path = paths.getAllSpecialHolidayFrame;
        return nts.uk.request.ajax("at", path);
    }

    export function findEmpByCodes(codes: any): JQueryPromise<any> {
        return ajax("com", paths.findEmpByCodes,codes);
    }
    export function findClsByCodes(codes: any): JQueryPromise<any> {
        return ajax("com",paths.findClsByCodes,codes);
    }
 // 0
    export interface SpecialHolidayItem {
        companyId: string,
        specialHolidayCode: number,
        specialHolidayName: string,
        grantRegular: GrantRegular,
        specialLeaveRestriction: SpecialLeaveRestriction,
        targetItem: TargetItem,
        autoGrant: number,
        continuousAcquisition: number,
        memo: string
    }
// 1
 /** SpecialHolidayItem */
    export interface GrantRegular {
        typeTime: number,
        grantDate: number,
        fixGrantDate: FixGrantDate,
        grantPeriodic: GrantDeadline,
        periodGrantDate: PeriodGrantDate,
    }
    export interface SpecialLeaveRestriction {
        companyId: string,
        specialHolidayCode: number,
        restrictionCls: number,
        ageLimit: number,
        genderRest: number,
        restEmp: number,
        listCls: Array<string>,
        ageStandard: AgeStandard,
        ageRange: AgeRange,
        gender: number,
        listEmp: Array<string>
    }
    export interface TargetItem {
        absenceFrameNo: Array<number>,
        frameNo: Array<number>
    }

// 2
  /** GrantRegular */
    export interface FixGrantDate {
        grantDays: RegularGrantDays,
        grantPeriodic: GrantDeadline,
        grantMonthDay: MonthDay  
    }
    export interface RegularGrantDays {
        grantDays: number
    }
    export interface GrantDeadline {
        timeSpecifyMethod: number,
        expirationDate: SpecialVacationDeadline,
        limitAccumulationDays: LimitAccumulationDays
    }
    export interface PeriodGrantDate {
        period: DatePeriod,
        grantDays: RegularGrantDays
    }
    /** SpecialLeaveRestriction */
    export interface AgeStandard {
        ageCriteriaCls: number,
        ageBaseDate: MonthDay
    }
    export interface AgeRange {
        ageLowerLimit: number,
        ageHigherLimit: number
    }

// 3
    /** FixGrantDate*/
    export interface MonthDay {
        month: number,
        day: number
    }
    export interface LimitAccumulationDays{
        limit: boolean,
        limitCarryoverDays: number
    }
    export interface DatePeriod{
        start: number,
        end: number
    }
    export interface SpecialVacationDeadline {
        months: number,
        years: number
    }
}