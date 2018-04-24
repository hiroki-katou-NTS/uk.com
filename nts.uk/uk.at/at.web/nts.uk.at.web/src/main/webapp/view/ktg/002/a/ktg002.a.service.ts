module nts.uk.at.view.ktg002.a.service {
    import ajax = nts.uk.request.ajax;
    
    var paths: any = {
        checkApproved: "screen/at/ktg002/checkApproved",
    }
    export function getData(): JQueryPromise<any> {
        return ajax("at", paths.checkApproved);
    }
}  