module nts.uk.at.view.ksm004.a {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    import getText = nts.uk.resource.getText;
    export module service {
        var paths: any = {
            getCalendarCompanySet: "at/schedule/calendar/getCalendarCompanySetByYear",
            getAllCalendarCompany: "at/schedule/calendar/getcalendarcompanyByYearMonth",
            insertCalendarCompany: "at/schedule/calendar/addcalendarcompany",
            updateCalendarCompany: "at/schedule/calendar/updatecalendarcompany",
            deleteCalendarCompany: "at/schedule/calendar/deletecalendarcompany",
            
            getCalendarWorkplaceSet: "at/schedule/calendar/getCalendarWorkplaceSetByYear",
            getCalendarClassById: "at/schedule/calendar/getcalendarclassByYearMonth",
            insertCalendarClass: "at/schedule/calendar/addcalendarclass",
            updateCalendarClass: "at/schedule/calendar/updatecalendarclass",
            deleteCalendarClass: "at/schedule/calendar/deletecalendarclass",
            
            getCalendarClassSet: "at/schedule/calendar/getCalendarClassSetByYear",
            getCalendarWorkPlaceByCode: "at/schedule/calendar/getcalendarWorkPlaceByYearMonth",
            insertCalendarWorkPlace: "at/schedule/calendar/addcalendarworkplace",
            updateCalendarWorkPlace: "at/schedule/calendar/updatecalendarworkplace",
            deleteCalendarWorkPlace: "at/schedule/calendar/deletecalendarworkplace"
        }
        
        export function getCalendarCompanySet(yearMonth): JQueryPromise<any> {
            return ajax(paths.getCalendarCompanySet, {'key': '', 'yearMonth': yearMonth});
        }
        
        export function getAllCalendarCompany(yearMonth): JQueryPromise<any> {
            return ajax(paths.getAllCalendarCompany, {'key': '', 'yearMonth': yearMonth});
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
        
        
        export function getCalendarClassSet(classId, yearMonth): JQueryPromise<any> {
            return ajax(paths.getCalendarClassSet, {'key': classId, 'yearMonth': yearMonth});
        }
        export function getCalendarClassById(classId, yearMonth): JQueryPromise<any> {
            return ajax(paths.getCalendarClassById, {'key': classId, 'yearMonth': yearMonth});
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
        
        
        export function getCalendarWorkplaceSet(workPlaceCode, yearMonth): JQueryPromise<any> {
            return ajax(paths.getCalendarWorkplaceSet, {'key': workPlaceCode, 'yearMonth': yearMonth});
        }
        export function getCalendarWorkPlaceByCode(workPlaceCode, yearMonth): JQueryPromise<any> {
            return ajax(paths.getCalendarWorkPlaceByCode, {'key': workPlaceCode, 'yearMonth': yearMonth});
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
          
        export function saveAsExcel(mode: string, startDate: string, endDate: string): JQueryPromise<any> {
            let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
            let domainType = "KSM004";
            if (program.length > 1) {
                program.shift();
                domainType = domainType + program.join(" ");
            }
            return nts.uk.request.exportFile('/masterlist/report/print', 
                {domainId: 'DayCalendar', domainType: domainType, 
                languageId: 'ja', reportType: 0, mode: mode, 
                startDate : moment.utc(startDate).format(), 
                endDate : moment.utc(endDate).format()});
    }
    }
}