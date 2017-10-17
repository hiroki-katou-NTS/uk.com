module nts.uk.at.view.kdl032.a {
    export module service {
        var paths: any = {
        getData: "at/record/divergencetime/getalldivreason/{0}",
        }
         export function getData(divTimeId : string ): JQueryPromise<Array<DeviationTime>> {
             
            return nts.uk.request.ajax("at", nts.uk.text.format(paths.getData, divTimeId));
        }
      
        
    }
}