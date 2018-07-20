module nts.uk.com.view.cas004.b {
    export module service {
        var paths = {
            findAllCompany: "ctx/sys/auth/ws/company/findAllCompany",
            findEmployeesByCId: "ctx/sys/auth/ws/employeeInfo/findEmployeesByCId"
        }

        //Fine All Company
        export function findAllCompany(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", paths.findAllCompany);
        }

        // get list employee by company id
        export function findEmployeesByCId(companyId: string): JQueryPromise<any> {
            return nts.uk.request.ajax("com", paths.findEmployeesByCId, companyId);
        }
    }
}
