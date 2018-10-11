module nts.uk.pr.view.qmm039.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getInfoEmLogin: "workflow/approvermanagement/workroot/getInforPsLogin",
        getWpName: "screen/com/kcp010/getLoginWorkPlace",
    }

    export function getInfoEmLogin(): JQueryPromise<any> {
        return ajax("com", paths.getInfoEmLogin);
    }
    export function getWpName(): JQueryPromise<any> {
        return ajax("com", paths.getWpName);
    }
}