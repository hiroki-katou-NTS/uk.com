class EmployeeModel {
    flag: boolean
    retirementStatus: number;
    registrationStatus: string;
    desiredWorkingCourse: string;
    employeeCode: string;
    employeeName: string;
    employeeKanaName: string;
    employeeDob: string;
    employeeAge: string;
    department: string;
    employment: string;
    hireDate: string;
    retirementDate: string;

    constructor() {

    }
}

interface IEmployee {
    image: string;
    code: string;
    name: string;
    kanaName: string;
    sex: string;
    dob: string;
    age: string;
    department: string;
    position: string;
    employment: string;
}

class SearchFilterModel {
    includingReflected: KnockoutObservable<boolean> = ko.observable(false);
    retirementAgeDesignation: KnockoutObservable<boolean> = ko.observable(false);
    retirementPeriod: KnockoutObservable<IDateRange> = ko.observable({startDate:'', endDate: ''});
    department: KnockoutObservable<string> = ko.observable("");
    selectAllDepartment: KnockoutObservable<boolean> = ko.observable(false);
    employment: KnockoutObservable<string> = ko.observable("");
    selectAllEmployment: KnockoutObservable<boolean> = ko.observable(false);
    constructor() {

    }
}

class ScreenSetting {
    enableRetirementAge: Boolean;
    constructor() {

    }
}

class RetirementSetting {
    code: string;
    name: string;

    constructor(code: string, name: string) {
        this.code = code;
        this.name = name;
    }
}

interface IStartPageDto {
    dateDisplaySettingPeriod: IDateDisplaySettingPeriod;
    retirementCourses: Array<IRetirementCourses>;
    referEvaluationItems: Array<any>;
}

interface IDateDisplaySettingPeriod {
    periodStartdate: String;
    periodEnddate: String;
}

interface IRetirementCourses {
    employmentCode: string;
    employmentName: string;
    retirePlanCourseClass: 0;
    retirementAge: number;
    retireDateTerm: IRetireDateTerm;
    retirePlanCourseId: number;
    retirePlanCourseCode: string;
    retirePlanCourseName: string;
    durationFlg: boolean;
}

interface IRetireDateTerm {
    retireDateTerm: number;
    retireDateSettingDate: number;
}

class ItemModel {
    code: string;
    name: string;

    constructor(code: string, name: string) {
        this.code = code;
        this.name = name;
    }
}

interface IDateRange {
    startDate: string;
    endDate: string;
}