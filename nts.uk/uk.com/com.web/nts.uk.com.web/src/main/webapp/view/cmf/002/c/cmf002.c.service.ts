module nts.uk.com.view.cmf002.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getAllCategoryItem: "exio/exo/condset/getAllCategoryItem/{0}/{1}",
        findByCode: "exio/exo/condset/findByCode/{0}/{1}",
        getOutItems: "exio/exo/outputitem/getOutItems",
        addOutputItem: "exio/exo/outputitem/add",
        updateOutputItem:"exio/exo/outputitem/update",
        removeOutputItem: "exio/exo/outputitem/remove"
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
}