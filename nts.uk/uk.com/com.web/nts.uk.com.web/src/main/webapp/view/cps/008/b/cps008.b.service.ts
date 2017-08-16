module cps008.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'getListCls': 'ctx/bs/person/itemcls/getById/{0}',
        'getData': 'ctx/bs/person/newlayout/get',
        'saveData': 'ctx/bs/person/newlayout/save'
    };

    export function getData() {
        return ajax(paths.getData);
    }
    
    export function getListCls(lid) {
        return ajax(format(paths.getListCls, lid));
    }

    export function saveData(command) {
        return ajax(paths.saveData, command);
    }


}