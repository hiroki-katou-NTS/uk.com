module nts.uk.pr.view.qmm002.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getAllBank: "ctx/pr/transfer/bank/get-all-bank",
        getAllBankBranch: "ctx/pr/transfer/bank/get-all-bank-branch",
        getBankBranch: "ctx/pr/transfer/bank/get-bank-branch/{0}",
        registerBank: "ctx/pr/transfer/bank/reg-bank",
        registerBranch: "ctx/pr/transfer/bank/reg-bank-branch",
        checkBeforeDeleteBranch: "ctx/pr/transfer/bank/check-before-delete-branch/{0}",
        deleteBranch: "ctx/pr/transfer/bank/delete-branch/{0}",
        exportFile: "ctx/pr/transfer/bank/export",
    }

    export function exportFile(): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.exportFile);
    };

    export function getAllBank(): JQueryPromise<any> {
        return ajax("pr", paths.getAllBank);
    };

    export function getAllBankBranch(data: Array<string>): JQueryPromise<any> {
        return ajax("pr", paths.getAllBankBranch, data);
    };
    
    export function getBankBranch(data: string): JQueryPromise<any> {
        let _path = format(paths.getBankBranch, data);
        return ajax("pr", _path);
    }
    
    export function registerBank(data: any): JQueryPromise<any> {
        return ajax("pr", paths.registerBank, data);
    };
    
    export function registerBranch(data: any): JQueryPromise<any> {
        return ajax("pr", paths.registerBranch, data);
    };
    
    export function checkBeforeDeleteBranch(data: string): JQueryPromise<any> {
        let _path = format(paths.checkBeforeDeleteBranch, data);
        return ajax("pr", _path);
    }
    
    export function deleteBranch(data: string): JQueryPromise<any> {
        let _path = format(paths.deleteBranch, data);
        return ajax("pr", _path);
    }

}
