module nts.uk.com.view.cmf002.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
     export module service {
        var paths = {
            getListCtgItems: "exio/condset/getListCtgItems/{0}"
        }
        
        export function getListCtgItems(categoryId :string): JQueryPromise<any> {
            let _path = format(paths.getListCtgItems,categoryId);
            
            return ajax('com', _path);
        }; 

    }
}