module nts.uk.at.view.kdw002.c {
    export module service {
        var paths: any = {
            //daily
            getEmpRole: "at/record/workrecord/authfuncrest/find-emp-roles",
            getDailyAttItem: "at/shared/scherec/attitem/getDailyAttItem",
            getDailyAttdItemByRoleID : "at/shared/scherec/dailyattditem/auth/getdailyattd",
            getListDailyAttdItem : "at/shared/scherec/dailyattditem/getalldailyattd",
            updateDailyAttdItem :"at/shared/scherec/dailyattditem/auth/updatedailyattd",
            getNameDaily  :"screen/at/correctionofdailyperformance/getNamedailyAttItem", 
            //monthly
            getListMonthlyAttdItem:"at/record/attendanceitem/monthly/findall",
            getMontlyAttItem: "at/shared/scherec/attitem/getMonthlyAttItem",
            getMonthlyAttdItemByRoleID : "at/shared/scherec/monthlyattditem/auth/getmonthlyattd",
            updateMonthlyAttdItem :"at/shared/scherec/monthlyattditem/auth/updatemonthlyattd",
            getNameMonthly  :"screen/at/correctionofdailyperformance/getNameMonthlyAttItem"
        }
        //daily
        export function getEmpRole(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getEmpRole);
        }
        export function updateDailyAttdItem(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateDailyAttdItem,command);
        }
        
        export function getListDailyAttdItem(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListDailyAttdItem);
        }
        
        export function getDailyAttdItemByRoleID(roleID:string): JQueryPromise<any> {
            // return nts.uk.request.ajax(paths.getDailyAttdItemByRoleID +"/" +roleID);
            return nts.uk.request.ajax(paths.getDailyAttItem, {roleId: roleID});
        }
        
        export function getNameDaily(listID : any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getNameDaily,listID);
        }
        
        // monthly
        export function getListMonthlyAttdItem(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListMonthlyAttdItem);
        }
        export function getMonthlyAttdItemByRoleID(roleID:string): JQueryPromise<any> {
            // return nts.uk.request.ajax(paths.getMonthlyAttdItemByRoleID +"/" +roleID);
            return nts.uk.request.ajax(paths.getMontlyAttItem, {roleId: roleID});
        }
        
        export function updateMonthlyAttdItem(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateMonthlyAttdItem,command);
        }
        export function getNameMonthly(listID : any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getNameMonthly,listID);
        }
        
        
    }
}
