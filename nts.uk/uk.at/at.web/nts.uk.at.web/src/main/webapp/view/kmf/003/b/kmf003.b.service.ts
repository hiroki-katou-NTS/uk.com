module nts.uk.at.view.kmf003.b.service {
    /**
     *  Service paths
     */
    var servicePath = {
        findByCode: "at/share/grantholidaytbl/findByCode/{0}/{1}",
        addGrantHdTbl: "at/share/grantholidaytbl/add",
        updateGrantHdTbl: "at/share/grantholidaytbl/update",
        deleteGrantHdTbl: "at/share/grantholidaytbl/delete",
        calculateGrantDate: "at/share/grantholidaytbl/calculateGrantDate"
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
    export function addYearHolidayGrant(data: Array<GrantHolidayTblDto>): JQueryPromise<any> {
        var path = nts.uk.text.format(servicePath.addGrantHdTbl);
        return nts.uk.request.ajax("at", path, data);
    }  
    
    /**
     *  Update data
     */
    export function updateYearHolidayGrant(data: Array<GrantHolidayTblDto>): JQueryPromise<any> {
        var path = nts.uk.text.format(servicePath.updateGrantHdTbl);
        return nts.uk.request.ajax("at", path, data);
    }  
    
    /**
     *  Delete data
     */
    export function deleteYearHolidayGrant(codes: Array<Codes>): JQueryPromise<any> {
        var path = nts.uk.text.format(servicePath.deleteGrantHdTbl);
        return nts.uk.request.ajax("at", path, codes);
    } 
    
    /**
     *  Calculate grant dates
     */
    export function calculateGrantDate(param: DataTranfer): JQueryPromise<any> {
        var path = nts.uk.text.format(servicePath.calculateGrantDate);
        return nts.uk.request.ajax("at", path, param);
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
    
    export interface DataTranfer {
        grantHolidayTblList: Array<GrantHolidayTblDto>,
        useSimultaneousGrant: number, 
        referDate: number, 
        simultaneousGrantDate: number
    }
}
