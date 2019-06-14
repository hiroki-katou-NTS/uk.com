module nts.uk.pr.view.qmm006.b.service {
    
    import ajax = nts.uk.request.ajax;

    var paths = {
        getAllBank: "ctx/pr/transfer/bank/get-all-bank",
        getAllBankBranch: "ctx/pr/transfer/bank/get-all-bank-branch"
    }

    export function getAllBank(): JQueryPromise<any> {
        return ajax("pr", paths.getAllBank);
    };

    export function getAllBankBranch(data: Array<string>): JQueryPromise<any> {
        return ajax("pr", paths.getAllBankBranch, data);
    };

}