module nts.uk.pr.view.qmm002.c {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        var paths = {
            getAllBank: "ctx/pr/transfer/bank/get-all-bank",
            getAllBankBranch: "ctx/pr/transfer/bank/get-all-bank-branch",
            integration: "ctx/pr/transfer/emppaymentinfo/bank-integration"
        }

        export function getAllBank(): JQueryPromise<any> {
            return ajax("pr", paths.getAllBank);
        };

        export function getAllBankBranch(data: Array<string>): JQueryPromise<any> {
            return ajax("pr", paths.getAllBankBranch, data);
        };
        
        export function integration(data: any): JQueryPromise<any> {
            return ajax("pr", paths.integration, data);
        };

    }
}
