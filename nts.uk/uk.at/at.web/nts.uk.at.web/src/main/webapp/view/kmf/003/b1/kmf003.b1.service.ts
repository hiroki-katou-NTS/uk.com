module nts.uk.at.view.kmf003.b1.service {
    /**
     *  Service paths
     */
    var servicePath = {
        findLengthOfService: "at/share/lengthofservice/findByCode/{0}",
        findByCode: "at/share/grantholidaytbl/findByCode/{0}/{1}",
        addGrantHdTbl: "at/share/grantholidaytbl/add",
        checkData: "ctx/at/share/vacation/setting/annualpaidleave/find/checkkmf003"
    }  
    
    /**
     *  Find length of service data by codes
     */
    export function findLengthOfService(yearHolidayCode: string): JQueryPromise<LengthServiceTblDto> {
        var path = nts.uk.text.format(servicePath.findLengthOfService, yearHolidayCode);
        return nts.uk.request.ajax(path);
    } 
    
    /**
     *  Find data by codes
     */
    export function findByCode(conditionNo: number, yearHolidayCode: string): JQueryPromise<GrantHolidayTblDto> {
        var path = nts.uk.text.format(servicePath.findByCode, conditionNo, yearHolidayCode);
        return nts.uk.request.ajax(path);
    }  
    
    /**
     *  Check data before load
     */
    export function checkData(): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.checkData);
    }
    
    /**
     *  Add data
     */
    export function addYearHolidayGrant(data: Array<GrantHolidayTblDto>): JQueryPromise<any> {
        var path = nts.uk.text.format(servicePath.addGrantHdTbl);
        return nts.uk.request.ajax("at", path, data);
    }
    
    export interface GrantHolidayTblDto {
        conditionNo: number,
        yearHolidayCode: string,
        grantHolidayList: Array<GrantHolidayTbl>
    }
    
    export interface GrantHolidayTbl {
        conditionNo: number,
        yearHolidayCode: string,
        grantNum: number,
        grantDays: number,
        limitTimeHd: number,
        limitDayYear: number
    }
    
    export interface LengthServiceTblDto {
        yearHolidayCode: string,
        grantNum: number,
        allowStatus: number,
        standGrantDay: number,
        year: number,
        month: number
    }
}
