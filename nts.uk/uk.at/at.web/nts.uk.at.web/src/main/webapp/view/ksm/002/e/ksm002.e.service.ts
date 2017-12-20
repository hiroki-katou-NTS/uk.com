module ksm002.e.service {
     var paths = {
         getSpecificdateByListCode: "at/schedule/shift/businesscalendar/specificdate/getspecificdatebylistcode"
    }

    /**
    * get SpecificDate by list code
    */
    export function getSpecificdateByListCode(lstSpecificDateItem: Array<number>): JQueryPromise<Array<any>>{
        return nts.uk.request.ajax("at", paths.getSpecificdateByListCode,lstSpecificDateItem);
    }  
}