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
    retireDateTerm: string;
    retireDateSettingDate: string;
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

interface IDepartment {
    workplaceId: string;
    hierarchyCode: string;
    name: string;
    code: string;
    lever: number;
    nodeText: string;
}

interface IEmployment {
    code: string;
    name: string;
}

class SearchFilterModel {
    includingReflected: KnockoutObservable<boolean> = ko.observable(false);
    retirementAgeDesignation: KnockoutObservable<boolean> = ko.observable(false);
    retirementPeriod: KnockoutObservable<IDateRange> = ko.observable({startDate:'', endDate: ''});
    department: KnockoutObservable<IDepartment> = ko.observable([]);
    departmentDisplay: KnockoutObservable<string> = ko.computed(() => {
        return this.department().map(function(elem){
            return elem.name;
        }).join(', ');
    });
    selectAllDepartment: KnockoutObservable<boolean> = ko.observable(false);
    employment: KnockoutObservable<Array[IEmployment]> = ko.observable([]);
    employmentDisplay: KnockoutObservable<string> = ko.computed(() => {
        return this.employment().map(function(elem){
            return elem.name;
        }).join(', ');
    })
    selectAllEmployment: KnockoutObservable<boolean> = ko.observable(false);
    constructor() {

    }
}

class ISearchParams {
    confirmCheckRetirementPeriod: boolean;
    startDate: Date;
    endDate: Date;
    retirementAgeSetting: boolean;
    retirementAge: number;
    allSelectDepartment: boolean;
    selectDepartment: Array<string>;
    allSelectEmployment: boolean;
    selectEmployment: Array<string>;
    constructor(param: SearchFilterModel) {
        this.startDate = param.retirementPeriod().startDate;
        this.endDate = param.retirementPeriod().endDate;
        this.allSelectDepartment = param.selectAllDepartment();
        this.selectDepartment = _.map(param.department(), (d) => {return d.name;});
        this.allSelectEmployment = param.selectAllEmployment();
        this.selectEmployment = _.map(param.employment(), (d) => {return d.name;});
        this.confirmCheckRetirementPeriod = false;
    }
}

interface ISearchResult {
    interView: IInterviewSummary;
    retiredEmployees: Array<PlannedRetirementDto>;
    employeeImports: Array<IEmployeeInformationImport>;
}

interface IEmployeeInformationImport {
     employeeID : string;
     interviewRecordId : string;
     interviewDate : string;
     mainInterviewerEmployeeID : string;
     employeeCD : string;
     businessName : string;
     businessNameKana : string;
     departmentCd : string;
     departmentDisplayName : string;
     positionCd : string;
     positionName : string;
     employmentCd : string;
     employmentName : string;
}

class PlannedRetirementDto {
    pId : string;
    sId : string;
    scd : string;
    businessName : string;
    businessnameKana : string;
    birthday : string;
    dateJoinComp : string;
    departmentId : string;
    departmentCode : string;
    departmentName : string;
    jobTitleId : string;
    jobTitleCd : string;
    jobTitleName : string;
    employmentCode : string;
    employmentName : string;
    age : number;
    retirementDate : string;
    releaseDate : string;
    hrEvaluation1 : string;
    hrEvaluation2 : string;
    hrEvaluation3 : string;
    healthStatus1 : string;
    healthStatus2 : string;
    healthStatus3 : string;
    stressStatus1 : string;
    stressStatus2 : string;
    stressStatus3 : string;

    constructor() {
        
    }
}

interface IInterviewSummary {
    listInterviewRecordAvailability: Array<IInterviewRecordAvailability>;
}

interface IInterviewRecordAvailability {
    employeeID: string;
    isPresence: boolean;
}