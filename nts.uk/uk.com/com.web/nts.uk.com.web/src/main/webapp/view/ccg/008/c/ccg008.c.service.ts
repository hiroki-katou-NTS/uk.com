module nts.uk.com.view.ccg008.c.service {

    var paths: any = {
        getSelectMyTopPage: "topageselfsetting/select",
        save: "topageselfsetting/save",
        getTopPageSelfSet: "topageselfsetting/finditemselected"
    }
    
    /**
     * 
     */
    export function getSelectMyTopPage(): JQueryPromise<Array<viewmodel.model.Node>> {
        return nts.uk.request.ajax("com",paths.getSelectMyTopPage);
    }
    
    /**
     * 
     */
    export function save(data: any): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax("com",paths.save, data);
    }
    /**
     * hoatt
     * get top page self set
     */
    export function getTopPageSelfSet(): JQueryPromise<viewmodel.model.TopPageSelfSet>{
        return nts.uk.request.ajax("com",paths.getTopPageSelfSet);
    }
}