module cps002.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'getData': 'ctx/bs/person/newlayout/get'
    };

    export function getLayout() {
        return ajax(paths.getData);
    }
}

