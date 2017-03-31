module qmm002.d.service {
    var paths: any = {
        getBankList: "basic/system/bank/find/all",
        addBank: "basic/system/bank/add",
        updateBank: "basic/system/bank/update",
        removeBank: "basic/system/bank/remove"
    }

    export function getBankList(): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax("com", paths.getBankList);
    }

    export function addBank(isCreated, bankInfo): JQueryPromise<any> {
        var path = isCreated ? paths.addBank : paths.updateBank;
        
        return nts.uk.request.ajax("com", path, bankInfo);
    }
    
    export function removeBank(bankInfo): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.removeBank, bankInfo);
    }
}