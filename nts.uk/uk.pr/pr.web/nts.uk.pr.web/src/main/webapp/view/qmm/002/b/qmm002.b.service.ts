module nts.uk.pr.view.qmm002.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        getAllBank: "ctx/pr/transfer/bank/get-all-bank",
        getAllBankBranch: "ctx/pr/transfer/bank/get-all-bank-branch",
        deleteListBranch: "ctx/pr/transfer/bank/delete-list-branch"
    }

    export function getAllBank(): JQueryPromise<any> {
        return ajax("pr", paths.getAllBank);
    };

    export function getAllBankBranch(data: Array<string>): JQueryPromise<any> {
        return ajax("pr", paths.getAllBankBranch, data);
    };
    
    export function deleteListBranch(data: Array<string>): JQueryPromise<any> {
        return ajax("pr", paths.deleteListBranch, data);
    };

}
