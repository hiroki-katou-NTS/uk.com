module nts.uk.com.view.ccg008.a.service {
    import model = nts.uk.com.view.ccg.model;

    var paths = {
        getTopPage: "topageselfsetting/gettoppage/{0}",
        getCache: "screen/com/ccg008/get-cache",
        getClosure: "screen/com/ccg008/get-closure"
    }
        
    export function getTopPageByCode(screen: string, code: string):JQueryPromise<any>{
        var pathRequest = nts.uk.text.format(paths.getTopPage, screen);
        return nts.uk.request.ajax("com", pathRequest, code);
    }
    
    export function getCache():JQueryPromise<any>{
        return nts.uk.request.ajax("com",paths.getCache);
    }
    
    export function getClosure():JQueryPromise<any>{
        return nts.uk.request.ajax("com",paths.getClosure);
    }
}
