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
            getAttendanceItemByType: "at/schedule/budget/premium/attendancePremiumItem",
            findByLangId: "at/schedule/budget/premium/getByCIdAndLangId",
            insertOrUpdatePremiumItemLanguage: "at/schedule/budget/premium/language/insert",
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
        export function saveAsExcel(params : any): JQueryPromise<any> {
            let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
            let programName = program[1] != null ? program[1] : "";
            return nts.uk.request.exportFile('/masterlist/report/print',
                {
                    domainId: "PersonCostCalculation", domainType: "KML001" + programName, languageId: params.languageId , baseDate: moment.utc(params.baseDate).format()
                    , reportType: 0
                });
        }
        export function findByLangId(langId: string): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.findByLangId + '/' + langId);
        }
        
        export function insertPremiumItemLang(premiumItemLang: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.insertOrUpdatePremiumItemLanguage, premiumItemLang);
    }
    }
}