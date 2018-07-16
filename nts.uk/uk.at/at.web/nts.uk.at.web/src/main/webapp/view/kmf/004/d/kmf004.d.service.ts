module nts.uk.at.view.kmf004.d.service {
    
    var paths: any = {
        findBySphdCd: "shared/grantdatetbl/findBySphdCd/{0}",
        findByGrantDateCd: "shared/grantdatetbl/findByGrantDateCd/{0}",
        addGrantDate: "shared/grantdatetbl/add",
        deleteGrantDate: "shared/grantdatetbl/delete"
    }
        
    export function findBySphdCd(specialHolidayCode: number): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.findBySphdCd, specialHolidayCode);
        return nts.uk.request.ajax("at", path);
    }

    export function findByGrantDateCd(grantDateCode: number): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.findByGrantDateCd, grantDateCode);
        return nts.uk.request.ajax("at", path);
    }
    
    export function deleteGrantDate(specialHolidayCode: number, grantDateCode: number): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.deleteGrantDate);
        return nts.uk.request.ajax(path, { specialHolidayCode: specialHolidayCode, grantDateCode: grantDateCode });
    }
}          