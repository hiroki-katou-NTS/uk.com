module nts.uk.pr.view.qmm010.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getAllOffice: "ctx/pr/core/laborinsurance/laborinsuranceoffice/getAll",
        findByOfficeCode: "ctx/pr/core/laborinsurance/laborinsuranceoffice/findLaborOfficeByCode/{0}",
        addLaborOffice: "ctx/pr/core/laborinsurance/laborinsuranceoffice/addLaborOffice",
        updateLaborOffice: "ctx/pr/core/laborinsurance/laborinsuranceoffice/updateLaborOffice",
        removeLaborOffice: "ctx/pr/core/laborinsurance/laborinsuranceoffice/removeLaborOffice"
    }
    /**
     * get all
     */
    export function findAllOffice(): JQueryPromise<any> {
        return ajax(paths.getAllOffice);
    }

    export function findByOfficeCode(officeCode: string): JQueryPromise<any> {
        return ajax(format(paths.findByOfficeCode, officeCode));
    }

    export function addLaborOffice(command): JQueryPromise<any> {
        return ajax(paths.addLaborOffice, command);
    }

    export function updateLaborOffice(command): JQueryPromise<any> {
        return ajax(paths.updateLaborOffice, command);
    }

    export function removeLaborOffice(command): JQueryPromise<any> {
        return ajax(paths.removeLaborOffice, command);
    }

}
