import request = nts.uk.request;
module nts.uk.com.view.cmm018.l.service {
    // Service paths.
    var servicePath = {
        searchModeEmployee: "workflow/approvermanagement/workroot/testInUnregistry",
        saveExcel: ""
    }
    export function searchModeEmployee(baseDate: string) {
        return request.ajax('com', servicePath.searchModeEmployee, baseDate);
    }

    export function saveExcel(date: string) {
        return request.exportFile(servicePath.saveExcel , date);
    }
}
