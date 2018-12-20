module nts.uk.at.view.ksm002.b {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        var paths: any = {
            getAllSpecDate: "at/schedule/shift/businesscalendar/specificdate/getallspecificdate",
            getSpecDateByIsUse: "at/schedule/shift/businesscalendar/specificdate/getspecificdatebyuse/{0}",
            getCompanyStartDay: "at/schedule/shift/businesscalendar/businesscalendar/getcompanystartday",
            getCalendarWorkPlaceByCode: "at/schedule/shift/specificdayset/workplace/getworkplacespecificdate",
            insertCalendarWorkPlace: "at/schedule/shift/specificdayset/workplace/insertworkplacespecificdate",
            updateCalendarWorkPlace: "at/schedule/shift/specificdayset/workplace/updateworkplacespecificdate",
            deleteCalendarWorkPlace: "at/schedule/shift/specificdayset/workplace/deleteworkplacespecificdate"
        }
        
        export function getAllSpecDate(): JQueryPromise<any> {
            return ajax("at", paths.getAllSpecDate);
        }
        
        export function getSpecificDateByIsUse(useAtr: number): JQueryPromise<any> {
            return ajax("at", format(paths.getSpecDateByIsUse,useAtr));
        }
        
        export function getCompanyStartDay(): JQueryPromise<any> {
            return ajax("at", paths.getCompanyStartDay);
        }
        
        export function getCalendarWorkPlaceByCode(workplaceParam:any): JQueryPromise<any> {
            return ajax(paths.getCalendarWorkPlaceByCode, workplaceParam);
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
        
        export function saveAsExcel(): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "SpecificdaySet", domainType: "特定日の登録", 
            languageId: "ja", reportType: 0, startDate : moment.utc('2017', 'YYYY'), endDate : moment.utc('2017', 'YYYY')});
        }
    }
}