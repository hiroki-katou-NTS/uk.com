module nts.uk.com.view.cps009.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let paths = {
        getAll: "ctx/bs/person/info/setting/init/findAll",
        getAllCtg: "ctx/bs/person/info/setting/init/ctg/findAll",
        getAllItemByCtgId: "ctx/bs/person/info/setting/init/item/find/{0}"

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
    export function getAllCtg() {
        return ajax(paths.getAllCtg);
    }

    /**
   * Get all init value setting
   */
    export function getAllItemByCtgId(perInfoCtgId: string) {
        return ajax(format(paths.getAllItemByCtgId, perInfoCtgId));
    }



}
