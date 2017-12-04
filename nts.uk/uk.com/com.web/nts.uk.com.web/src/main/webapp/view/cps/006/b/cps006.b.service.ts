module nts.uk.com.view.cps006.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getItemInfoDefList: "ctx/bs/person/info/ctgItem/findby/categoryId/{0}/{1}",
        getPerInfoItemDefById: "ctx/bs/person/info/ctgItem/findby/itemIdOfOtherCompany/{0}/{1}",
        updateItemChange: "ctx/bs/person/info/ctgItem/updateItemChange",
        setOrder: "ctx/bs/person/info/ctgItem/SetOrder",
        filterHisSel: "ctx/bs/person/info/setting/selection/find/{0}/{1}/{2}"
    }

    export function getItemInfoDefList(categoryId, isAbolition): JQueryPromise<any> {
        return ajax(format(paths.getItemInfoDefList, categoryId, isAbolition));
    }

    export function getPerInfoItemDefById(itemId , personEmployeeType): JQueryPromise<any> {
        return ajax(format(paths.getPerInfoItemDefById, itemId, personEmployeeType))
    }

    export function updateItemChange(command): JQueryPromise<any> {
        return ajax(paths.updateItemChange, command)
    }

    export function SetOrder(command): JQueryPromise<any> {
        return ajax(paths.setOrder, command);
    }
    
    export function getAllSelByHistory(selectionItemId: string, baseDate: any, selectionItemClsAtr: number) {
        return ajax(format(paths.filterHisSel , selectionItemId, baseDate, selectionItemClsAtr));
    };

}
