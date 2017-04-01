module qmm002.c.service {
    var paths: any = {
        transfer: "basic/system/bank/branch/transfer",
        getBankList: "basic/system/bank/find/all"
    }

    export function tranferBranch(data): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax('com', paths.transfer, data);
    }
    
    export function getBankList(): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax("com", paths.getBankList);
    }
}