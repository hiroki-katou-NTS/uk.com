module nts.uk.com.view.cps009.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let paths = {
        getAll: "ctx/bs/person/info/setting/init/findAll",
        getAllCtg: "ctx/bs/person/info/setting/init/ctg/find/{0}",
        getAllItemByCtgId: "ctx/bs/person/info/setting/init/item/find/{0}",
        deleteInitVal: "ctx/bs/person/info/setting/init/delete"

    }
    /**
     * Get all init value setting
     */
    export function getAll() {
        return ajax(paths.getAll);
    }

    /**
     * Get all init value setting
     */
    export function getAllCtg(settingId: string) {
        return ajax(format(paths.getAllCtg, settingId));
    }

    /**
   * Get all init value setting
   */
    export function getAllItemByCtgId(perInfoCtgId: string) {
        return ajax(format(paths.getAllItemByCtgId, perInfoCtgId));
    }

    /**
     * delete init value setting
     */
    export function deleteInitVal(obj: any) {
        return ajax(paths.deleteInitVal, obj);
    }



}
