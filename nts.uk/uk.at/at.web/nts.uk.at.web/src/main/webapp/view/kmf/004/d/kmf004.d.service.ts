module nts.uk.at.view.kmf004.d.service {
    
    var paths: any = {
        findBySphdCd: "shared/grantdatetbl/findBySphdCd/{0}",
        findByGrantDateCd: "shared/grantdatetbl/findByGrantDateCd/{0}/{1}"
    }
        
    export function findBySphdCd(specialHolidayCode: number): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.findBySphdCd, specialHolidayCode);
        return nts.uk.request.ajax("at", path);
    }

    export function findByGrantDateCd(specialHolidayCode: number, grantDateCode: number): JQueryPromise<any> {
        var path = nts.uk.text.format(paths.findByGrantDateCd, specialHolidayCode, grantDateCode);
        return nts.uk.request.ajax("at", path);
    }
}          