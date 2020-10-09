module nts.uk.at.view.kaf018_old.a.service {
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

    export function getApprovalStatusPerior(closureId: number): JQueryPromise<any> {
        let path = format(paths.getApprovalStatusPerior, closureId);
        return nts.uk.request.ajax(path);
    }

    export function getUseSetting(): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getUseSetting);
    }
    
    /**
     * save to client service MonthlyPatternSettingBatch
     */
    export function saveSelectedClosureId(data): void {
        nts.uk.characteristics.save('StoreSelectedClosureId', data);
    }

    /**
     * find data client service MonthlyPatternSettingBatch
     */
    export function restoreSelectedClosureId(): JQueryPromise<any> {
        return nts.uk.characteristics.restore('StoreSelectedClosureId');
    }
}