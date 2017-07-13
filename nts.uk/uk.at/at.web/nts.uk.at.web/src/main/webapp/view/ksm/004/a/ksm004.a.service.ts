module nts.uk.at.view.ksm004.f {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        var paths: any = {
            getAllCalendarCompany: "at/schedule/calendar/getallcalendarcompany",
            insertCalendarCompany: "at/schedule/calendar/addcalendarcompany",
            updateCalendarCompany: "at/schedule/calendar/updatecalendarcompany",
            deleteCalendarCompany: "at/schedule/calendar/deletecalendarcompany",
            
            getCalendarClassById: "at/schedule/calendar/getallcalendarclass/{0}",
            insertCalendarClass: "at/schedule/calendar/addcalendarclass",
            updateCalendarClass: "at/schedule/calendar/updatecalendarclass",
            deleteCalendarClass: "at/schedule/calendar/deletecalendarclass",
            
            getCalendarWorkPlaceByCode: "at/schedule/calendar/getCalendarWorkplaceByCode/{0}",
            insertCalendarWorkPlace: "at/schedule/calendar/addcalendarworkplace",
            updateCalendarWorkPlace: "at/schedule/calendar/updatecalendarworkplace",
            deleteCalendarWorkPlace: "at/schedule/calendar/deletecalendarworkplace"
        }
        
        export function getAllCalendarCompany(): JQueryPromise<any> {
            return ajax(paths.getAllCalendarCompany);
        }
        
        export function insertCalendarCompany(command): JQueryPromise<any> {
            return ajax(paths.insertCalendarCompany, command);
        }
        
        export function updateCalendarCompany(command): JQueryPromise<any> {
            return ajax(paths.updateCalendarCompany, command);
        }
        
        export function deleteCalendarCompany(command): JQueryPromise<any> {
            return ajax(paths.deleteCalendarCompany, command);
        }
        
        
        export function getCalendarClassById(classId): JQueryPromise<any> {
            return ajax(format(paths.getCalendarClassById, classId));
        }
        
        export function insertCalendarClass(command): JQueryPromise<any> {
            return ajax(paths.insertCalendarClass, command);
        }
        
        export function updateCalendarClass(command): JQueryPromise<any> {
            return ajax(paths.updateCalendarClass, command);
        }
        
        export function deleteCalendarClass(command): JQueryPromise<any> {
            return ajax(paths.deleteCalendarClass, command);
        }
        
        
        export function getCalendarWorkPlaceByCode(workPlaceCode): JQueryPromise<any> {
            return ajax(format(paths.getCalendarWorkPlaceByCode, workPlaceCode));
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