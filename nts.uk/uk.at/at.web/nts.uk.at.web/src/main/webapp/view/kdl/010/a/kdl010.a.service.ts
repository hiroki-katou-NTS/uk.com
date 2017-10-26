module kdl010.a.service {
    var paths: any = {
     getAllWorkLocation: "at/record/worklocation/findall",
    }
    
    export function getAllWorkLocation(): JQueryPromise<Array<viewmodel.WorkLocation>> {
        return nts.uk.request.ajax("at", paths.getAllWorkLocation);
    }
       
}