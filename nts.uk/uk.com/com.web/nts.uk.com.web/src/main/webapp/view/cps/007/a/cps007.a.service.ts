module cps007.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'getData': 'ctx/pereg/person/newlayout/get',
        'saveData': 'ctx/pereg/person/newlayout/save'
    };

    export function getData() {
        return ajax(paths.getData);
    }

    export function saveData(command) {
        return ajax(paths.saveData, command);
    }
}