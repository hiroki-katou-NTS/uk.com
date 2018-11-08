module nts.uk.pr.view.qmm002.c {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        var paths = {
            getAllBank: "ctx/pr/transfer/bank/get-all-bank",
            getAllBankBranch: "ctx/pr/transfer/bank/get-all-bank-branch",
            getBankBranch: "ctx/pr/transfer/bank/get-bank-branch/{0}",
            registerBank: "ctx/pr/transfer/bank/reg-bank",
            registerBranch: "ctx/pr/transfer/bank/reg-bank-branch"
        }

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

    }
}
