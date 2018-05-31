module nts.uk.at.view.kdl032.a {
    import ajax = nts.uk.request.ajax;
    export module service {
        var paths: any = {
        getData: "at/record/divergencetime/reason/getalldivreason/{0}",
        }
         export function getData(divTimeId) : JQueryPromise<any> {
             
            return ajax("at", nts.uk.text.format(paths.getData, divTimeId));
        }
      
        
    }
}