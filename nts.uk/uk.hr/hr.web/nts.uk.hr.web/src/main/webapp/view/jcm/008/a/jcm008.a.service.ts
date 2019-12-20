module jcm008.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        getData: 'databeforereflecting/startPage',
        searchRetirementInfo: 'databeforereflecting/search-retired',
        export: 'file/hr/report/retirementinformation/export'
    }
       
    export function getData() : JQueryPromise<any>{
        return ajax(paths.getData);
    }

    export function searchRetireData(data: ISearchParams) : JQueryPromise<any>{
        return ajax(paths.searchRetirementInfo, data);
    }

    export function outPutFileExcel(data: any): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.export, data);
    }
   
}