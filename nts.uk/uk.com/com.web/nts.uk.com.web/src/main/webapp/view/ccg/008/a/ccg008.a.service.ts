module nts.uk.com.view.ccg008.a.service {
    import model = nts.uk.com.view.ccg.model;

    var paths = {
        getTopPage: "topageselfsetting/gettoppage/{0}"
    }
        
    export function getTopPageByCode(screen: string, code: string):JQueryPromise<any>{
        var pathRequest = nts.uk.text.format(paths.getTopPage, screen);
        return nts.uk.request.ajax("com", pathRequest, code);
    }
}
