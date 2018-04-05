module nts.uk.at.view.kdw006.b.service {

    let servicePath = {
        getFormatPerformanceById: 'at/record/workrecord/operationsetting/getFormat',
        getDaiPerformanceFunById: 'at/record/workrecord/operationsetting/getdaily',
        getMonPerformanceFunById: 'at/record/workrecord/operationsetting/getMonthy',
        updateFormatPerformanceById: 'at/record/workrecord/operationsetting/updateFormat',
        updatetDaiPerformanceFunById: 'at/record/workrecord/operationsetting/updateDaily',
        updateMonPerformanceFunById: 'at/record/workrecord/operationsetting/updateMonthly'
    };

    export function getFormatPerformanceById(): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.getFormatPerformanceById);
    }

    export function getDaiPerformanceFunById(): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.getDaiPerformanceFunById);
    }

    export function getMonPerformanceFunById(): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.getMonPerformanceFunById);
    }

    export function updateFormatPerformanceById(data): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.updateFormatPerformanceById, data);
    }

    export function updatetDaiPerformanceFunById(data): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.updatetDaiPerformanceFunById, data);
    }

    export function updateMonPerformanceFunById(data): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.updateMonPerformanceFunById, data);
    }


}

