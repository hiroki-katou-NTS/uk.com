module nts.uk.pr.view.qmm006.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getAllSourceBank: "ctx/pr/transfer/sourcebank/get-all-source-bank",
        integration: "ctx/pr/transfer/emppaymentinfo/source-bank-integration"
    };

    export function getAllSourceBank(): JQueryPromise<any> {
        return ajax('pr', paths.getAllSourceBank);
    }
    
    export function integration(data: any): JQueryPromise<any> {
        return ajax('pr', paths.integration, data);
    }
    
}