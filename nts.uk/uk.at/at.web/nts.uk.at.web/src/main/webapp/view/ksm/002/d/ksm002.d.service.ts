module ksm002.d.service {
     var paths = {
         getSpecDateByIsUse: "at/schedule/shift/businesscalendar/specificdate/getspecificdatebyuse/",
         updateSpecificDateSet: "at/schedule/shift/businesscalendar/specificdate/updatespecificdateSet"
    }

     /**
     * 
     */
    export function getSpecificDateByIsUse(useAtr: number): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getSpecDateByIsUse + useAtr);
    }

    
    export function updateSpecificDateSet(objectToUpdate: viewmodel.ObjectToUpdate): JQueryPromise<void> {
        return nts.uk.request.ajax("at", paths.updateSpecificDateSet, objectToUpdate);
    }
}
