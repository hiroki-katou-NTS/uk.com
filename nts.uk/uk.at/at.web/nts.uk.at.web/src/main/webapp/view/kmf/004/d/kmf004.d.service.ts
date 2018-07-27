module nts.uk.at.view.kmf004.d.service {
    
    var paths: any = {
        findBySphdCd: "shared/grantdatetbl/findBySphdCd/{0}",
        findByGrantDateCd: "shared/grantdatetbl/findByGrantDateCd/{0}/{1}",
        addGrantDate: "shared/grantdatetbl/add",
        updateGrantDate: "shared/grantdatetbl/update",
        deleteGrantDate: "shared/grantdatetbl/delete"
    }
        
    export function findBySphdCd(specialHolidayCode: number): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.findBySphdCd, specialHolidayCode);
        return nts.uk.request.ajax("at", path);
    }

    export function findByGrantDateCd(specialHolidayCode: number, grantDateCode: string): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.findByGrantDateCd, specialHolidayCode, grantDateCode);
        return nts.uk.request.ajax("at", path);
    }
    
    export function addGrantDate(data: Array<GrantDateTblDto>): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.addGrantDate);
        return nts.uk.request.ajax("at", path, data);
    }
    
    export function updateGrantDate(data: Array<GrantDateTblDto>): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.updateGrantDate);
        return nts.uk.request.ajax("at", path, data);
    }
    
    export function deleteGrantDate(specialHolidayCode: number, grantDateCode: string): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.deleteGrantDate);
        return nts.uk.request.ajax(path, { specialHolidayCode: specialHolidayCode, grantDateCode: grantDateCode });
    }
    
    export interface GrantDateTblDto {
        specialHolidayCode: number,
        grantDateCode: string,
        grantDateName: string,
        isSpecified: number,
        fixedAssign: number,
        numberOfDays: number,
        elapseYear: Array<ElapseDto>
    }
    
    export interface ElapseDto {
        specialHolidayCode: number,
        grantDateCode: string,
        elapseNo: number,
        months: number,
        years: number,
        grantedDays: number
    }
}          