module cps001.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'getCat': '',
        'getItemDs': ''
    };

    export function getCategory(cid) {
        return ajax(format(paths.getCat, cid));
    }

    export function getItemDefinitions(cid) {
        return ajax(format(paths.getItemDs, cid));
    }
}