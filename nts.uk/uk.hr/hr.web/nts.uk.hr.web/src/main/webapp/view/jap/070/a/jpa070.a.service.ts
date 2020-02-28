module jcm007.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        getData: 'databeforereflecting/startPage'
    }
       
    export function getData() : JQueryPromise<any>{
        return ajax(paths.getData);
    }   
}