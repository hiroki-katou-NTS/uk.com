module ksm002.d.service {
     var paths = {
         getAllSpecificDate: "at/schedule/shift/businesscalendar/specificdate/getallspecificdate"
    }

     /**
    * get all SpecificDate
    */
    export function getAllSpecificDate(): JQueryPromise<Array<any>>{
        return nts.uk.request.ajax("at", paths.getAllSpecificDate);
    }  
}
