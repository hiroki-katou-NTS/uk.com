module nts.uk.at.view.kml001.shr {
    export module servicebase {
        var paths: any = {
            personCostCalculationSelect: "at/schedule/budget/premium/findPersonCostCalculationByCompanyID",
            personCostCalculationInsert: "at/schedule/budget/premium/insertPersonCostCalculation",
            personCostCalculationUpdate: "at/schedule/budget/premium/updatePersonCostCalculation",
            personCostCalculationDelete: "at/schedule/budget/premium/deletePersonCostCalculation",
            findByHistoryID: "at/schedule/budget/premium/findByHistoryID",
            premiumItemSelect: "at/schedule/budget/premium/findPremiumItemByCompanyID",
            premiumItemUpdate: "at/schedule/budget/premium/updatePremiumItem",
            getAttendanceItems: "at/schedule/budget/premium/attendancePremiumName",
            getAttendanceItemByType:  "at/schedule/budget/premium/attendancePremiumItem",
            //getAttendanceItemByType:  "at/share/attendanceType/getByType"
        }
        
        export function personCostCalculationSelect(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.personCostCalculationSelect);
        }
       
        export function personCostCalculationInsert(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.personCostCalculationInsert, command);
        }
        
        export function personCostCalculationUpdate(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.personCostCalculationUpdate, command);
        }
        
        export function personCostCalculationDelete(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.personCostCalculationDelete, command);
        }
        
        export function findByHistoryID(historyID): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findByHistoryID, historyID);
        }
        
        export function premiumItemSelect(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.premiumItemSelect);
        }
        
        export function premiumItemUpdate(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.premiumItemUpdate, command);
        }
        
        export function getAttendanceItems(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getAttendanceItems, command);
        }
        
        export function getAttendanceItemByType(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getAttendanceItemByType);
        }
        export function saveAsExcel(param): JQueryPromise<any> {
        return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "PersonCostCalculation", domainType: "KML001"+__viewContext.program.programName,languageId: 'ja',baseDate:moment.utc(param).format() 
        ,mode:1 , reportType: 0});
    }
    } 
}