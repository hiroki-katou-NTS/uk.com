module nts.uk.at.view.kdw002.c {
    export module service {
        var paths: any = {
            //getAttendanceItems: "at/record/businesstype/attendanceItem/getAttendanceItems",
            getEmpRole: "at/record/workrecord/authfuncrest/find-emp-roles",
            //updateDailyService: "at/record/DailyAttdItemAuth/UpdateListDailyAttendanceItemAuthority",
            getDailyAttdItemByRoleID : "at/shared/scherec/dailyattditem/auth/getdailyattd",
            getListDailyAttdItem : "at/shared/scherec/dailyattditem/getalldailyattd"
            
            }
//        export function getAttendanceItems(): JQueryPromise<any> {
//            return nts.uk.request.ajax(paths.getAttendanceItems);
//        }
        export function getEmpRole(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getEmpRole);
        }
//        
//        export function updateDailyService(command): JQueryPromise<any> {
//            return nts.uk.request.ajax(paths.updateDailyService , command);
//        }
            
        export function getListDailyAttdItem(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListDailyAttdItem);
        }
        
        export function getDailyAttdItemByRoleID(roleID:string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getDailyAttdItemByRoleID +"/" +roleID);
        }
        

        
    }
}
