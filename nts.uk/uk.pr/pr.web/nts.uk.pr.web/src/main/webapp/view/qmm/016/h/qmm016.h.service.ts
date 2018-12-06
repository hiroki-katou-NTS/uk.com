module nts.uk.pr.view.qmm016.h.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getAllQualificationGroup: "ctx/pr/core/wageprovision/wagetable/getAllQualificationGroup",
        getAllQualificationInformation: "ctx/pr/core/wageprovision/wagetable/getAllQualificationInformation",
        getAllQualificationGroupAndInformation: "ctx/pr/core/wageprovision/wagetable/getAllQualificationGroupAndInformation",
        getQualificationGroupByCode: "ctx/pr/core/wageprovision/wagetable/getQualificationGroupByCode/{0}",
        addQualificationGroup: "ctx/pr/core/wageprovision/wagetable/addQualificationGroup",
        updateQualificationGroup: "ctx/pr/core/wageprovision/wagetable/updateQualificationGroup",
        deleteQualificationGroup: "ctx/pr/core/wageprovision/wagetable/deleteQualificationGroup"
    }

    export function getAllQualificationGroup(): JQueryPromise<any> {
        return ajax(paths.getAllQualificationGroup);
    }

    export function getAllQualificationInformation(): JQueryPromise<any> {
        return ajax(paths.getAllQualificationInformation);
    }

    export function getAllQualificationGroupAndInformation(): JQueryPromise<any> {
        return ajax(paths.getAllQualificationGroupAndInformation);
    }

    export function getQualificationGroupByCode(qualificationGroupCode): JQueryPromise<any> {
        return ajax(format(paths.getQualificationGroupByCode, qualificationGroupCode));
    }

    export function addQualificationGroup(command): JQueryPromise<any> {
        return ajax(paths.addQualificationGroup, command);
    }

    export function updateQualificationGroup(command): JQueryPromise<any> {
        return ajax(paths.updateQualificationGroup, command);
    }

    export function deleteQualificationGroup(command): JQueryPromise<any> {
        return ajax(paths.deleteQualificationGroup, command);
    }
}