module nts.uk.at.view.kmf003.b.service {
    /**
     *  Service paths
     */
    var servicePath = {
        findByCode: "at/share/grantholidaytbl/findByCode/{0}/{1}",
        addGrantHdTbl: "at/share/grantholidaytbl/add",
        updateGrantHdTbl: "at/share/grantholidaytbl/update",
        deleteGrantHdTbl: "at/share/grantholidaytbl/delete"
    }  
    
    /**
     *  Find data by codes
     */
    export function findByCode(conditionNo: number, yearHolidayCode: string): JQueryPromise<GrantHolidayTblDto> {
        var path = nts.uk.text.format(servicePath.findByCode, conditionNo, yearHolidayCode);
        return nts.uk.request.ajax(path);
    }  
    
    /**
     *  Add data
     */
    export function addYearHolidayGrant(data: GrantHolidayTblDto): JQueryPromise<any> {
        var path = nts.uk.text.format(servicePath.addGrantHdTbl, data);
        return nts.uk.request.ajax(path);
    }  
    
    /**
     *  Update data
     */
    export function updateYearHolidayGrant(data: GrantHolidayTblDto): JQueryPromise<any> {
        var path = nts.uk.text.format(servicePath.updateGrantHdTbl, data);
        return nts.uk.request.ajax(path);
    }  
    
    /**
     *  Delete data
     */
    export function deleteYearHolidayGrant(codes: Array<Codes>): JQueryPromise<any> {
        var path = nts.uk.text.format(servicePath.deleteGrantHdTbl, codes);
        return nts.uk.request.ajax(path);
    } 
    
    export interface GrantHolidayTblDto {
        grantYearHolidayNo: number,
        conditionNo: number,
        yearHolidayCode: string,
        grantDays: number,
        limitedTimeHdDays: number,
        limitedHalfHdCnt: number,
        lengthOfServiceMonths: number,
        lengthOfServiceYears: number,
        grantReferenceDate: number,
        grantSimultaneity: number
    }
    
    export interface Codes {
        grantYearHolidayNo: number,
        conditionNo: number,
        yearHolidayCode: string,
    }
}
