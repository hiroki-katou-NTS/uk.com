module nts.uk.com.view.cmf002.d {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        var paths = {
            getListCtgItems: "exio/exo/condset/getListCtgItems/{0}/{1}"
        }
 
        export function getListCtgItems(condSetCd: string, categoryId: string): JQueryPromise<any> {
            let _path = format(paths.getListCtgItems, condSetCd, categoryId); 
            return ajax('com', _path);
        }; 

    }
}
