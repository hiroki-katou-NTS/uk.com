module nts.uk.at.view.kdw002.a {
    export module service {
        var paths: any = {
            getAttendanceItems: "at/share/attendanceitem/getAttendanceItems",
            getControlOfAttendanceItem: "at/share/ControlOfAttendanceItems/getControlOfAttendanceItem/",
            updateControlOfAttendanceItem: "at/share/ControlOfAttendanceItems/updateControlOfAttendanceItem"
            }
        export function getAttendanceItems(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getAttendanceItems);
        }
        
        export function getControlOfAttendanceItem(attendanceItemId): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getControlOfAttendanceItem + attendanceItemId);
        }
        
         export function updateControlOfAttendanceItem(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateControlOfAttendanceItem, command);
        }
    }
}
