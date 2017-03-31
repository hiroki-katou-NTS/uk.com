module nts.uk.pr.view.qmm002.a {
    export module service {
        var paths = {
            getBankList: "basic/system/bank/find/all",
            addBranchList: "basic/system/bank/branch/add",
            updateBranchList: "basic/system/bank/branch/update",
            removeBranch: "basic/system/bank/branch/remove",
            removeBank: "basic/system/bank/remove",
            check: "basic/system/bank/find/check"
        };

        export function getBankList(): JQueryPromise<Array<any>> {
            return nts.uk.request.ajax("com", paths.getBankList);
        }
        
        export function checkBankList(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", paths.check);        
        }

        export function addBank(isCreated, bankInfo): JQueryPromise<any> {
            var path = isCreated ? paths.addBranchList : paths.updateBranchList;
            return nts.uk.request.ajax("com", path, bankInfo)
        }
        
        export function removeBank(isParentNode, bankCode, branchId): JQueryPromise<any> {
            var path = isParentNode ? paths.removeBank : paths.removeBranch;
            var obj = {
                bankCode: bankCode,
                branchId: branchId   
            };
            return nts.uk.request.ajax("com", path, obj);
        }
    }

}