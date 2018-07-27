module nts.uk.com.view.cmf002.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getAllCategoryItem: "exio/exo/condset/getAllCategoryItem/{0}/{1}",
        findByCode: "exio/exo/condset/findByCode/{0}/{1}",
        getOutItems: "exio/exo/outputitem/getOutItems",
        addOutputItem: "exio/exo/outputitem/add",
        updateOutputItem:"exio/exo/outputitem/update",
        removeOutputItem: "exio/exo/outputitem/remove",
        getAtWorkClsDfs: "exio/exo/dataformatsetting/getAtWorkClsDfs/{0}/{1}",
        getCharacterDfs: "exio/exo/dataformatsetting/getCharacterDfs/{0}/{1}",
        getDateDfs: "exio/exo/dataformatsetting/getDateDfs/{0}/{1}",
        getInstantTimeDfs: "exio/exo/dataformatsetting/getInstantTimeDfs/{0}/{1}",
        getNumberDfs: "exio/exo/dataformatsetting/getNumberDfs/{0}/{1}",
        getTimeDfs: "exio/exo/dataformatsetting/getTimeDfs/{0}/{1}",
        addAtWorkClsDfs: "exio/exo/dataformatsetting/addAtWorkClsDfs",
        addCharacterDfs: "exio/exo/dataformatsetting/addCharacterDfs",
        addDateDfs: "exio/exo/dataformatsetting/addDateDfs",
        addInstantTimeDfs: "exio/exo/dataformatsetting/addInstantTimeDfs",
        addNumberDfs: "exio/exo/dataformatsetting/addNumberDfs",
        addTimeDfs: "exio/exo/dataformatsetting/addTimeDfs",
        updateAtWorkClsDfs: "exio/exo/dataformatsetting/updateAtWorkClsDfs",
        updateCharacterDfs: "exio/exo/dataformatsetting/updateCharacterDfs",
        updateDateDfs: "exio/exo/dataformatsetting/updateDateDfs",
        updateInstantTimeDfs: "exio/exo/dataformatsetting/updateInstantTimeDfs",
        updateNumberDfs: "exio/exo/dataformatsetting/updateNumberDfs",
        updateTimeDfs: "exio/exo/dataformatsetting/updateTimeDfs"
    }

    export function getAllCategoryItem(categoryId: number, itemType: number): JQueryPromise<any> {
        return ajax(format(paths.getAllCategoryItem, categoryId, itemType));
    }

    export function findByCode(conditionSettingCode: string, outputItemCode: string): JQueryPromise<any> {
        return ajax(format(paths.findByCode, conditionSettingCode, outputItemCode));
    }

    export function getOutItems(condSetCd: string): JQueryPromise<any> {
        return ajax("com", paths.getOutItems, condSetCd);
    }
    
    // add
    export function addOutputItem(command): JQueryPromise<any> {
        return ajax(paths.addOutputItem, command);
    }
    
    // update
    export function updateOutputItem(command): JQueryPromise<any> {
        return ajax(paths.updateOutputItem, command);
    }
    
    // delete
    export function removeOutputItem(command): JQueryPromise<any> {
        return ajax(paths.removeOutputItem, command);
    }
    
    export function getAtWorkClsDfs(conditionSettingCode: string, outputItemCode: string): JQueryPromise<any> {
        return ajax(format(paths.getAtWorkClsDfs, conditionSettingCode, outputItemCode));
    }
    
    export function getCharacterDfs(conditionSettingCode: string, outputItemCode: string): JQueryPromise<any> {
        return ajax(format(paths.getCharacterDfs, conditionSettingCode, outputItemCode));
    }
    
    export function getDateDfs(conditionSettingCode: string, outputItemCode: string): JQueryPromise<any> {
        return ajax(format(paths.getDateDfs, conditionSettingCode, outputItemCode));
    }
    
    export function getInstantTimeDfs(conditionSettingCode: string, outputItemCode: string): JQueryPromise<any> {
        return ajax(format(paths.getInstantTimeDfs, conditionSettingCode, outputItemCode));
    }
    
    export function getNumberDfs(conditionSettingCode: string, outputItemCode: string): JQueryPromise<any> {
        return ajax(format(paths.getNumberDfs, conditionSettingCode, outputItemCode));
    }
    
    export function getTimeDfs(conditionSettingCode: string, outputItemCode: string): JQueryPromise<any> {
        return ajax(format(paths.getTimeDfs, conditionSettingCode, outputItemCode));
    }
}