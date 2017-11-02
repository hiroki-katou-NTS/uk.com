module nts.uk.com.view.cps009.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let paths = {
        getAll: "ctx/bs/person/info/setting/init/findAll",
        getAllCtg: "ctx/bs/person/info/setting/init/ctg/find/{0}",
        getAllItemByCtgId: "ctx/bs/person/info/setting/init/item/find/{0}/{1}",
        deleteInitVal: "ctx/bs/person/info/setting/init/delete",
        update : "ctx/bs/person/info/setting/init/ctg/update",
        refHistSel: "ctx/bs/person/info/setting/init/item/referenceHistory"
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
    export function getAllItemByCtgId(settingId: string, perInfoCtgId: string) {
        return ajax(format(paths.getAllItemByCtgId, settingId, perInfoCtgId));
    }

    /**
     * delete init value setting
     */
    export function deleteInitVal(obj: any) {
        return ajax(paths.deleteInitVal, obj);
    }
    
    /**
     *update init value setting
     */
    export function update(obj: any) {
        return ajax(paths.update, obj);
    }

    /**
     * Reference History
     */
    export function refHistSel(param) {
        return ajax(paths.refHistSel, param);
    }

}
