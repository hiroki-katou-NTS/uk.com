module nts.uk.com.view.cps009.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let paths = {
        getAll: "ctx/pereg/person/info/setting/init/findAll",
        getAllCtg: "ctx/pereg/person/info/setting/init/ctg/find/{0}",
        getAllItemByCtgId: "ctx/pereg/person/info/setting/init/item/find/{0}/{1}/{2}",
        deleteInitVal: "ctx/pereg/person/info/setting/init/delete",
        update: "ctx/pereg/person/info/setting/init/ctg/update",
        filterHisSel: "ctx/pereg/person/info/setting/selection/findAllCombox",
        checkItemWorkType: "ctx/pereg/person/common/checkStartEnd",
        checkItemWorkTime: "ctx/pereg/person/common/checkAllMutilTime"
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
    export function getAllItemByCtgId(settingId: string, perInfoCtgId: string, baseDate: any) {
        return ajax(format(paths.getAllItemByCtgId, settingId, perInfoCtgId, baseDate));
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
    * Get all init value setting
    */
    export function getAllSelByHistory(selectionItemId: string, baseDate: any) {
        return ajax(format(paths.filterHisSel, selectionItemId, baseDate));
    }

    export function getAllComboxByHistory(query: any) {
        return ajax(paths.filterHisSel, query);
    }


    export function checkStartEnd(params: any) {
        return ajax(paths.checkItemWorkType, params);
    }


    export function checkMutiTime(params: any) {
        return ajax(paths.checkItemWorkTime, params);
    }


}
