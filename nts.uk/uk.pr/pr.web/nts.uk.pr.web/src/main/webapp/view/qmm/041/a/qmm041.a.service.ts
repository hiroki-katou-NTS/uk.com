module nts.uk.pr.view.qmm041.a.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getInfoEmpLogin: "workflow/approvermanagement/workroot/getInforPsLogin",
        getWpName: "screen/com/kcp010/getLoginWorkPlace",
    }

    export function getInfoEmpLogin(): JQueryPromise<any> {
        return ajax("com", paths.getInfoEmpLogin);
    }

    export function getWpName(): JQueryPromise<any> {
        return ajax("com", paths.getWpName);
    }
}