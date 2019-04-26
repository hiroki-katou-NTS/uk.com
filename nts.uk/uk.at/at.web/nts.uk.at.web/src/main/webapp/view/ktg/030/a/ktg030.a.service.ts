module nts.uk.at.view.ktg030.a.service {
    import ajax = nts.uk.request.ajax;
    
    var paths: any = {
        getData: "screen/at/ktg030/checkDisplay"
    }
    export function getData(currentOrNextMonth: number): JQueryPromise<any> {
        return ajax("at", paths.getData,currentOrNextMonth); 
    }
}  