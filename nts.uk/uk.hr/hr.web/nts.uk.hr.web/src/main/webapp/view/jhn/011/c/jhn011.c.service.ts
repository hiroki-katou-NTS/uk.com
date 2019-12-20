module jhn011.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getListCls: "ctx/pereg/person/maintenance/findOne/{0}",
        getData: "ctx/pereg/person/newlayout/get",
        saveData: "ctx/pereg/person/newlayout/save",
    };

    export function getData() {
        return ajax("com", paths.getData);
    }
    
    export function getListCls(lid) {
         return ajax("com", format(paths.getListCls, lid));
    }

    export function saveData(command) {
        return ajax("com", paths.saveData, command);
    }


}