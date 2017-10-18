module cps001.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'getEmpFileMn': ''
    };

    export function getCategory(cid) {
        return ajax(format(paths.getCat, cid));
    }

    export function getItemDefinitions(cid) {
        return ajax(format(paths.getItemDs, cid));
    }
}