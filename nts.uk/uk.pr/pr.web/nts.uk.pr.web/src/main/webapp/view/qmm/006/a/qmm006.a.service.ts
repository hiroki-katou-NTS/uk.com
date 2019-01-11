module nts.uk.pr.view.qmm006.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getAllSourceBank: "ctx/pr/transfer/sourcebank/get-all-source-bank",
        getSourceBank :"ctx/pr/transfer/sourcebank/get-source-bank/{0}",
        register: "ctx/pr/transfer/sourcebank/reg-source-bank",
        checkBeforeDelete: "ctx/pr/transfer/sourcebank/check-before-delete/{0}",
        remove: "ctx/pr/transfer/sourcebank/del-source-bank/{0}"
    };

    export function getAllSourceBank(): JQueryPromise<any> {
        return ajax('pr', paths.getAllSourceBank);
    }
    
    export function getSourceBank(code: string) {
        return ajax('pr', format(paths.getSourceBank, code));
    }

    export function register(data: any) {
        return ajax('pr', paths.register, data);
    }

    export function checkBeforeDelete(code: string) {
        return ajax('pr', format(paths.checkBeforeDelete, code));
    }
    
    export function remove(code: string) {
        return ajax('pr', format(paths.remove, code));
    }

}