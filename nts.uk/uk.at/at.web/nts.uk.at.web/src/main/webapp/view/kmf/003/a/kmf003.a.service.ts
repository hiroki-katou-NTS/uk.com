module nts.uk.at.view.kmf003.a.service {
    /**
     *  Service paths
     */
    var servicePath = {
        findAll: "at/share/yearholidaygrant/findAll",
        findByCode: "at/share/yearholidaygrant/findByCode/{0}",
        addYearHolidayGrant: "at/share/yearholidaygrant/add",
        updateYearHolidayGrant: "at/share/yearholidaygrant/update",
        deleteYearHolidayGrant: "at/share/yearholidaygrant/delete",
        findGrantHolidayTblByCodes: "at/share/grantholidaytbl/findByCode/{0}/{1}"
    }  
    
    /**
     *  Find Grant Holiday Tbl By Codes
     */
    export function findGrantHolidayTblByCodes(conditionNo: number, yearHolidayCode: string): JQueryPromise<GrantHolidayTblDto> {
        var path = nts.uk.text.format(servicePath.findGrantHolidayTblByCodes, conditionNo, yearHolidayCode);
        return nts.uk.request.ajax(path);
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
        return nts.uk.request.ajax(servicePath.addYearHolidayGrant, data);
    }  
    
    /**
     *  Update data
     */
    export function updateYearHolidayGrant(data: YearHolidayGrantDto): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.updateYearHolidayGrant, data);
    }  
    
    /**
     *  Delete data
     */
    export function deleteYearHolidayGrant(yearHolidayCode: string): JQueryPromise<any> {
        var path = nts.uk.text.format(servicePath.deleteYearHolidayGrant);
        return nts.uk.request.ajax(path, {yearHolidayCode: yearHolidayCode});
    } 
    //saveAsExcel

    
//    export function saveAsExcel(languageId: String): JQueryPromise<any> {
//        let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
//        let domainType = "KMF002";
//            if (program.length > 1) {
//                program.shift();
//                domainType = domainType + program.join(" ");
//            }
//        return nts.uk.request.exportFile('/masterlist/report/print', {
//            domainId: "YearHoliday",
//            domainType: domainType, languageId: languageId, reportType: 0
//        });
//    }

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
    
    export interface GrantHolidayTblDto {
        grantYearHolidayNo: number,
        conditionNo: number,
        yearHolidayCode: string,
        grantDays: string,
        limitedTimeHdDays: number,
        limitedHalfHdCnt: number,
        lengthOfServiceMonths: number,
        lengthOfServiceYears: number,
        grantReferenceDate: number,
        grantSimultaneity: number,
        grantDate: string
    }
}
