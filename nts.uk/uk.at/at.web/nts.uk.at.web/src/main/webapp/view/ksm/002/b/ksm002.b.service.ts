module nts.uk.at.view.ksm002.b {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        var paths: any = {
            getAllSpecDate: "at/schedule/shift/businesscalendar/specificdate/getallspecificdate",
            getSpecDateByIsUse: "at/schedule/shift/businesscalendar/specificdate/getspecificdatebyuse",
            getCompanyStartDay: "at/schedule/shift/businesscalendar/businesscalendar/getcompanystartday",
            getCalendarWorkPlaceByCode: "at/schedule/shift/specificdayset/workplace/getworkplacespecificdate/{0}/{1}",
            insertCalendarWorkPlace: "at/schedule/shift/specificdayset/workplace/insertworkplacespecificdate",
            updateCalendarWorkPlace: "at/schedule/shift/specificdayset/workplace/updateworkplacespecificdate",
            deleteCalendarWorkPlace: "at/schedule/shift/specificdayset/workplace/deleteworkplacespecificdate"
        }
        
        export function getAllSpecDate(): JQueryPromise<any> {
            return ajax("at", paths.getAllSpecDate);
        }
        
        export function getSpecificDateByIsUse(useAtr: number): JQueryPromise<any> {
            return ajax("at", paths.getSpecDateByIsUse + "/" + useAtr);
        }
        
        export function getCompanyStartDay(): JQueryPromise<any> {
            return ajax("at", paths.getCompanyStartDay);
        }
        
        export function getCalendarWorkPlaceByCode(workplaceId, processDate): JQueryPromise<any> {
            return ajax(format(paths.getCalendarWorkPlaceByCode, workplaceId, processDate));
        }
        
        export function insertCalendarWorkPlace(command): JQueryPromise<any> {
            return ajax(paths.insertCalendarWorkPlace, command);
        }
        
        export function updateCalendarWorkPlace(command): JQueryPromise<any> {
            return ajax(paths.updateCalendarWorkPlace, command);
        }
        
        export function deleteCalendarWorkPlace(command): JQueryPromise<any> {
            return ajax(paths.deleteCalendarWorkPlace, command);
        }
    }
}