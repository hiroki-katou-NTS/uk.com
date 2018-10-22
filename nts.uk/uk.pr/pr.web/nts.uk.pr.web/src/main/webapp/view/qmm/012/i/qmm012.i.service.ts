module nts.uk.pr.view.qmm012.i.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getAllBreakdownItemSetById: "ctx/pr/core/breakdownItem/getAllBreakdownItemSetById/{0}/{}",
        addBreakdownItemSet: "ctx/pr/core/breakdownItem/addBreakdownItemSet",
        updateBreakdownItemSet: "ctx/pr/core/breakdownItem/updateBreakdownItemSet",
        removeBreakdownItemSet: "ctx/pr/core/breakdownItem/removeBreakdownItemSet"
        
    }

    export function getAllBreakdownItemSetById(categoryAtr: number, itemNameCd: string): JQueryPromise<any> {
        var _path = format(paths.getAllBreakdownItemSetById, categoryAtr, itemNameCd);
        return ajax('pr', _path);
    };
    
     export function addBreakdownItemSet(breakdownItem: any): JQueryPromise<any> {
            return ajax('pr', paths.addBreakdownItemSet, breakdownItem);
        };
    
    export function updateBreakdownItemSet(breakdownItem: any): JQueryPromise<any> {
        return ajax('pr', paths.updateBreakdownItemSet, breakdownItem);
    };
    
    export function removeBreakdownItemSet(breakdownItem: any): JQueryPromise<any> {
        return ajax('pr', paths.removeBreakdownItemSet, breakdownItem);
    };
}
