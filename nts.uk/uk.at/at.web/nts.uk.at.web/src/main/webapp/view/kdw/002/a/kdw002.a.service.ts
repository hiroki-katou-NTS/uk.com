module nts.uk.at.view.kdw002.a {
    export module service {
        var paths: any = {
            // getAttendanceItems: "at/share/attendanceitem/getAttendanceItems",
            getAttendanceItems: "at/record/businesstype/attendanceItem/getAttendanceItems",
            getListDailyAttdItem: "at/shared/scherec/dailyattditem/getalldailyattd",
            getListMonthlyAttdItem: "at/record/attendanceitem/monthly/findall",
            getControlOfDailyItem: "at/shared/scherec/daily/findById/",
            getControlOfMonthlyItem: "at/shared/scherec/monthly/findById/",
            updateDaily: "at/shared/scherec/daily/update",
            updateMonthly: "at/shared/scherec/monthly/update",
            
            //name
            getNameDaily  :"screen/at/correctionofdailyperformance/getNamedailyAttItem",
            getNameMonthly  :"screen/at/correctionofdailyperformance/getNameMonthlyAttItem",
            
            getDailyAttItem: "at/shared/scherec/attitem/getDailyAttItem",
            getMontlyAttItem: "at/shared/scherec/attitem/getMonthlyAttItem"
        }
        export function getListDailyAttdItem(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListDailyAttdItem);
        }
        export function getListMonthlyAttdItem(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListMonthlyAttdItem);
        }

        export function getControlOfDailyItem(attendanceItemId): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getControlOfDailyItem + attendanceItemId);
        }
        export function getControlOfMonthlyItem(attendanceItemId): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getControlOfMonthlyItem + attendanceItemId);
        }
        export function updateDaily(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateDaily, command);
        }

        export function updateMonthly(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateMonthly, command);
        }
        //
        export function getNameDaily(listID : any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getNameDaily,listID);
        }
        export function getNameMonthly(listID : any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getNameMonthly,listID);
        }
        export function getDailyAttdItem(roleID: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getDailyAttItem);
        }
        export function getMonthlyAttdItem(roleID: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getMontlyAttItem);
        }
    }
}
