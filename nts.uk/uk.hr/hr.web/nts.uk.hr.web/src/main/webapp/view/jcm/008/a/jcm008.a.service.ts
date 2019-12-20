module jcm008.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        getData: 'databeforereflecting/startPage',
        searchRetirementInfo: 'databeforereflecting/search-retired'
    }
       
    export function getData() : JQueryPromise<any>{
        return ajax(paths.getData);
    }

    export function searchRetireData(data: ISearchParams) : JQueryPromise<any>{
        return ajax(paths.searchRetirementInfo, data);
    }
   
}