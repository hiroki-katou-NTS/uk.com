module nts.uk.at.view.kmf003.a.service {
    /**
     *  Service paths
     */
    var servicePath = {
        findAll: "at/share/yearholidaygrant/findAll",
        findByCode: "at/share/yearholidaygrant/findByCode/{0}",
        addYearHolidayGrant: "at/share/yearholidaygrant/add",
        updateYearHolidayGrant: "at/share/yearholidaygrant/update",
        deleteYearHolidayGrant: "at/share/yearholidaygrant/delete"
    }  
    
    /**
     *  Find all data
     */
    export function findAll(): JQueryPromise<Array<YearHolidayGrantDto>> {
        return nts.uk.request.ajax(servicePath.findAll);
    }
    
    /**
     *  Find data by code
     */
    export function findByCode(yearHolidayCode: string): JQueryPromise<YearHolidayGrantDto> {
        var path = nts.uk.text.format(servicePath.findByCode, yearHolidayCode);
        return nts.uk.request.ajax(path);
    }  
    
    /**
     *  Add data
     */
    export function addYearHolidayGrant(data: YearHolidayGrantDto): JQueryPromise<any> {
        var path = nts.uk.text.format(servicePath.addYearHolidayGrant, data);
        return nts.uk.request.ajax(path);
    }  
    
    /**
     *  Update data
     */
    export function updateYearHolidayGrant(data: YearHolidayGrantDto): JQueryPromise<any> {
        var path = nts.uk.text.format(servicePath.updateYearHolidayGrant, data);
        return nts.uk.request.ajax(path);
    }  
    
    /**
     *  Delete data
     */
    export function deleteYearHolidayGrant(yearHolidayCode: string): JQueryPromise<any> {
        var path = nts.uk.text.format(servicePath.deleteYearHolidayGrant, {yearHolidayCode: yearHolidayCode});
        return nts.uk.request.ajax(path);
    } 
    
    export interface YearHolidayGrantDto {
        yearHolidayCode: string,
        yearHolidayName: string,
        calculationMethod: number,
        standardCalculation: number,
        useSimultaneousGrant: number,
        simultaneousGrandMonthDays: number,
        yearHolidayNote: string,
        grantConditions: Array<GrantCondition>   
    }
    
    export interface GrantCondition {
        yearHolidayCode: string,
        conditionNo: number,
        conditionValue: number,
        useConditionAtr: number 
    }
}
