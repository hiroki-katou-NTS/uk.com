module nts.uk.at.view.kdw003.b.service {
    var paths: any = {
        getErrorRefer: "screen/at/correctionofdailyperformance/getErrors"
    }
    
    export function getErrorRefer(param){
        return nts.uk.request.ajax(paths.getErrorRefer, param);
    }
    
}