module nts.uk.at.view.kdw006.b.service {
    let servicePath = {
        getAllFormatPerformanceById: 'at/record/workrecord/operationsetting/getFormat',
        getDaiPerformanceFunById: 'at/record/workrecord/operationsetting/getdaily',
        getAllMonPerformanceFunById: 'at/record/workrecord/operationsetting/getMonthy'
    };

    export function getAllFormatPerformanceById() : JQueryPromise<any>{
        return nts.uk.request.ajax(servicePath.getAllFormatPerformanceById);
    }
    
    export function getDaiPerformanceFunById() : JQueryPromise<any>{
        return nts.uk.request.ajax(servicePath.getDaiPerformanceFunById);
    }
    
    export function getAllMonPerformanceFunById() : JQueryPromise<any>{
        return nts.uk.request.ajax(servicePath.getAllMonPerformanceFunById);
    }
}
