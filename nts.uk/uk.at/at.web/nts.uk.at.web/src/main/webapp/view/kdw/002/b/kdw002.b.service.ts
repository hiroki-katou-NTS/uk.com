module nts.uk.at.view.kdw002.b {
    export module service {
        var paths: any = {
            getAttendanceItems: "at/record/businesstype/attendanceItem/getAttendanceItems",
            getBusinessTypes: "at/record/businesstype/findAll",
            getListDailyServiceTypeControl: "at/record/DailyServiceTypeControl/getListDailyServiceTypeControl/",
            updateDailyService: "at/record/DailyServiceTypeControl/updateListDailyServiceTypeControlItem",
            getDailyServiceTypeControl: "at/record/DailyServiceTypeControl/getDailyServiceTypeControl/"
            }
        export function getAttendanceItems(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getAttendanceItems);
        }
        export function getBusinessTypes(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getBusinessTypes);
        }
        
         export function getListDailyServiceTypeControl(businessCode): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListDailyServiceTypeControl + businessCode);
        }
        
         export function updateDailyService(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateDailyService , command);
        }
        export function getDailyServiceTypeControl(businessCode,attendanceItemId): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getDailyServiceTypeControl + businessCode + "/"+attendanceItemId );
        }
        
    }
}
