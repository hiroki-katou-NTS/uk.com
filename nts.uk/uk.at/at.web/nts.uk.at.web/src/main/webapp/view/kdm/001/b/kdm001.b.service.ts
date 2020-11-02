module nts.uk.at.view.kdm001.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths: any = {
        getSubsitutionData: "at/record/remainnumber/subhd/getSubsitutionData",
        getExtraHolidayData: "at/record/remainnumber/subhd/getExtraHolidayData",
        getInfoEmLogin: "workflow/approvermanagement/workroot/getInforPsLogin",
        deleteHolidaySetting : "at/record/remainnumber/submana/holidaysetting/delete",
        getWpName: "screen/com/kcp010/getLoginWkp"
    }

    export function getSubsitutionData(searchCondition): JQueryPromise<any> {
        return ajax(paths.getSubsitutionData, searchCondition); 
    }

    export function getExtraHolidayData(searchCondition): JQueryPromise<any> {
        return ajax(paths.getExtraHolidayData, searchCondition);  
    }

    export function getInfoEmLogin(): JQueryPromise<any> {
        return ajax("com", paths.getInfoEmLogin);
    }

    // B4_2_9 削除
    export function deleteHolidaySetting(command: any): JQueryPromise<any> {
        return ajax(paths.deleteHolidaySetting, command);
    }

     export function getWpName(): JQueryPromise<any> {
        return ajax("com", paths.getWpName);
    }
}