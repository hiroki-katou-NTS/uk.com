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
 
    export function add(data: SpecialHolidayCommand): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.add);
        return nts.uk.request.ajax("at", path, data);
    }
 
    export function update(data: SpecialHolidayCommand): JQueryPromise<any> {
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
    export interface SpecialHolidayCommand {
        companyId: string,
        specialHolidayCode: number,
        specialHolidayName: string,
        regularCommand: GrantRegularCommand,
        leaveResCommand: SpecialLeaveRestrictionCommand,
        targetItemCommand: TargetItemCommand,
        autoGrant: number,
        continuousAcquisition: number,
        memo: string
    }
// 1
 /** SpecialHolidayCommand */
    export interface GrantRegularCommand {
        typeTime: number,
        grantDate: number,
        fixGrantDate: FixGrantDateCommand,
        grantPeriodic: GrantDeadlineCommand,
        periodGrantDate: PeriodGrantDateCommand,
    }
    export interface SpecialLeaveRestrictionCommand {
        companyId: string,
        specialHolidayCode: number,
        restrictionCls: number,
        ageLimit: number,
        genderRest: number,
        restEmp: number,
        listCls: Array<string>,
        ageStandard: AgeStandardCommand,
        ageRange: AgeRangeCommand,
        gender: number,
        listEmp: Array<string>
    }
    export interface TargetItemCommand {
        absenceFrameNo: Array<number>,
        frameNo: Array<number>
    }

// 2
  /** GrantRegularCommand */
    export interface FixGrantDateCommand {
        grantDays: number,
        grantPeriodic: GrantDeadlineCommand,
        grantMonthDay: number  
    }
    export interface GrantDeadlineCommand {
        timeSpecifyMethod: number,
        expirationDate: SpecialVacationDeadlineCommand,
        limitAccumulationDays: LimitAccumulationDaysCommand
    }
    export interface PeriodGrantDateCommand {
        period: DatePeriodCommand,
        grantDays: number
    }
    /** SpecialLeaveRestriction */
    export interface AgeStandardCommand {
        ageCriteriaCls: number,
        ageBaseDate: number
    }
    export interface AgeRangeCommand {
        ageLowerLimit: number,
        ageHigherLimit: number
    }

// 3
    /** GrantDeadlineCommand*/
    export interface LimitAccumulationDaysCommand{
        limit: boolean,
        limitCarryoverDays: number
    }
    export interface DatePeriodCommand{
        start: string,
        end: string
    }
    export interface SpecialVacationDeadlineCommand {
        months: number,
        years: number
    }
}