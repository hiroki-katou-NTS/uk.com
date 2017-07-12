module ksm002.c.service {
     var paths = {
         getAllSpecificDateByCompany: "at/schedule/specificdateitem/getallspecificdate"
    }

    /**
    * get all SpecificDateByCompany 
    */
    export function getAllSpecificDate(): JQueryPromise<Array<any>>{
        return nts.uk.request.ajax("at", paths.getAllSpecificDateByCompany);
    }  
}