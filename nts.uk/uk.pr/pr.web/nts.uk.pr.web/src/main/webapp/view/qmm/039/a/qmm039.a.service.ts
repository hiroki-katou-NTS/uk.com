module nts.uk.pr.view.qmm039.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        get: "#",
    }


    // service 1
    // domain PerProcessClsSet


    export function get (param: string): JQueryPromise<any> {
        let _path = format(paths.get, param);
        return ajax(_path);
    }
}