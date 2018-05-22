module nts.uk.at.view.kdm001.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths: any = {
        getSubsitutionData: "at/record/remainnumber/subhd/getSubsitutionData",
        getExtraHolidayData: "at/record/remainnumber/subhd/getExtraHolidayData"
    }
    export function getSubsitutionData(searchCondition): JQueryPromise<any> {
        return ajax(paths.getSubsitutionData, searchCondition); 
    }
    export function getExtraHolidayData(searchCondition): JQueryPromise<any> {
        return ajax(paths.getExtraHolidayData, searchCondition);  
    }
}