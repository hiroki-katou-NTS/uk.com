import ajax = nts.uk.request.ajax;
import format = nts.uk.text.format;

module cps007.b.service {
    let paths: any = {
        'getCat': 'ctx/bs/person/person/info/category/findby/{0}',
        'getItemDs': 'ctx/bs/person/person/info/ctgItem/findby/categoryId/{0}'
    };

    export function getCategory(cid) {
        return ajax(format(paths.getCat, cid));
    }

    export function getItemDefinitions(cid) {
        return ajax(format(paths.getItemDs, cid));
    }
}