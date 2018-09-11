module nts.uk.pr.view.qmm005.b.service {
    var servicePath = {
        init: "ctx/pr/core/wageprovision/processdatecls/init"
    }

    export function initData (): void{
        return nts.uk.request.ajax(servicePath.init)
    }
}