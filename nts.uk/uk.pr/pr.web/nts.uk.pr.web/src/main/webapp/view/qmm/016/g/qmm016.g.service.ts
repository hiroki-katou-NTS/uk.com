module nts.uk.pr.view.qmm016.g.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getAllElements: "ctx/pr/core/wageprovision/wagetable/get-all-elements"
    }

    export function getAllStatementItemData(): JQueryPromise<any> {
        return ajax('pr', paths.getAllElements);
    }
    
}
