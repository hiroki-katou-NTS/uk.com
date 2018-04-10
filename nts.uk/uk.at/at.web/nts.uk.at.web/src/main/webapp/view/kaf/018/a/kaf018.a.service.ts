module nts.uk.at.view.kaf018.a.service {
    import format = nts.uk.text.format;
    var paths: any = {
        getAll: "at/request/application/setting/workplace/getall",
        findAllClosure: "at/request/application/approvalstatus/findAllClosure",
        getApprovalStatusPerior: "at/request/application/approvalstatus/getApprovalStatusPerior/{0}/{1}",
        getUseSetting: "at/record/application/realitystatus/getUseSetting"
    }

    export function getAll(lstWkpId): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(paths.getAll, lstWkpId);
    }

    export function findAllClosure(): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(paths.findAllClosure);
    }

    export function getApprovalStatusPerior(closureId: number, closureDate: number): JQueryPromise<any> {
        let path = format(paths.getApprovalStatusPerior, closureId, closureDate);
        return nts.uk.request.ajax(path);
    }

    export function getUseSetting(): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getUseSetting);
    }
}