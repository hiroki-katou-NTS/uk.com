module nts.uk.com.view.ccg008.a.service {
    import model = nts.uk.com.view.ccg.model;

    var paths = {
        getMypage: "topageselfsetting/getmypage",
        active: "sys/portal/layout/active",
        getTopPage: "topageselfsetting/gettoppage/"
    }
    
    export function getMyPage(layoutID: string): JQueryPromise<model.LayoutDto> {
        if (nts.uk.text.isNullOrEmpty(layoutID))
            return nts.uk.request.ajax("com", paths.getMypage);
        else
            return nts.uk.request.ajax("com", paths.getMypage, layoutID);
    }    
    export function getTopPageByCode(code: string):JQueryPromise<any>{
        return nts.uk.request.ajax("com",paths.getTopPage + code);
    }
}
