module nts.uk.com.view.ccg008.c.service {

    var paths: any = {
        getSelectMyTopPage: "topageselfsetting/select",
        save: "topageselfsetting/save"
    }
    
    /**
     * 
     */
    export function getSelectMyTopPage(): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(paths.getSelectMyTopPage);
    }
    
    /**
     * 
     */
    export function save(data: any): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(paths.save, data);
    }
}