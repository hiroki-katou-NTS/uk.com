module nts.uk.at.view.kdm001.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths: any = {
        getInfoEmLogin: "workflow/approvermanagement/workroot/getInforPsLogin",
        getWpName: "screen/com/kcp010/getLoginWkp"
    }
    export function getInfoEmLogin(): JQueryPromise<any> {
        return ajax("com", paths.getInfoEmLogin);
    }
    export function getWpName(): JQueryPromise<any> {
        return ajax("com", paths.getWpName);
    }
}