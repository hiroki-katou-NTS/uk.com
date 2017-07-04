module nts.uk.com.view.ccg008.a.service {
    import model = nts.uk.com.view.ccg.model;

    var paths = {
        getTopPage: "topageselfsetting/gettoppage"
    }
        
    export function getTopPageByCode(code: string):JQueryPromise<any>{
        return nts.uk.request.ajax("com",paths.getTopPage, code);
    }
}
