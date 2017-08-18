module nts.uk.at.view.kdw003.b {
    export module service {
        var paths: any = {
            getErrorReferences: "screen/at/correctionofdailyperformance/errorreference/getErrorReferences",
        }
        
        export function getErrorReferences(obj): JQueryPromise<any> {
            return nts.uk.request.ajax("at",paths.getErrorReferences,obj);
        }
    }
}
