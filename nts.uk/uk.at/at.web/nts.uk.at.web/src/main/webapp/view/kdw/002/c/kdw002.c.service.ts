module nts.uk.at.view.kdw002.c {
    export module service {
        var paths: any = {
            getAttendanceItems: "at/record/businesstype/attendanceItem/getAttendanceItems",
            getEmpRole: "at/record/workrecord/authfuncrest/find-emp-roles",
            //getListDailyServiceTypeControl: "at/record/DailyAttdItemAuth/getListDailyAttendanceItemAuthority/",
            updateDailyService: "at/record/DailyAttdItemAuth/UpdateListDailyAttendanceItemAuthority",
            
            getDailyAttdItemByRoleID : "at/shared/scherec/dailyattditem/getdailyattd"
            
            }
        export function getAttendanceItems(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getAttendanceItems);
        }
        export function getEmpRole(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getEmpRole);
        }
        
//         export function getListDailyServiceTypeControl(authorityId): JQueryPromise<any> {
//            return nts.uk.request.ajax(paths.getListDailyServiceTypeControl + authorityId);
//        }
        
         export function updateDailyService(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateDailyService , command);
        }
        
        export function getDailyAttdItemByRoleID(roleID:string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getDailyAttdItemByRoleID +"/" +roleID);
        }
        

        
    }
}
