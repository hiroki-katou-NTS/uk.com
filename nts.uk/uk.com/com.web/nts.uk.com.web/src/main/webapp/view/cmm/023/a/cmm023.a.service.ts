module nts.uk.com.view.cmm023.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getMaster: "bs/employee/group_common_master/get_master",
        getItems: "bs/employee/group_common_master/get_items/{0}",
        saveMaster: "bs/employee/group_common_master/save_master"

    }

    export function getMaster() {
        
        return ajax(paths.getMaster);
    }

    export function getItems(commonMasterId) {
        
        return ajax(format(paths.getItems, commonMasterId));
    }

    export function saveMaster(param) {
        
        return ajax(paths.saveMaster, param);
    }



}
