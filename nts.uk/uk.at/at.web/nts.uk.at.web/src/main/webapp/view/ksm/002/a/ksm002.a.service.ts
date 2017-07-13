module ksm002.a.service {
    var paths = {
        getSpecificDateByCompany: "at/schedule/specificdateitem/getallspecificdate"
    }
    //get lst SpecificDate 
    export function getSpecificDateByCompany(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getSpecificDateByCompany);
    }    
}