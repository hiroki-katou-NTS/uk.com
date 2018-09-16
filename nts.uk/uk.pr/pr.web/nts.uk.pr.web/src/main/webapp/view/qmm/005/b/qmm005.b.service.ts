module nts.uk.pr.view.qmm005.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getProcessInfomation: "ctx/pr/core/wageprovision/processdatecls/getProcessInfomation/{0}",
        getSetDaySupport: "ctx/pr/core/wageprovision/processdatecls/getSetDaySupport/{0}",
        getSelectProcessingYear: "ctx/pr/core/wageprovision/processdatecls/getSelectProcessingYear/{0}/{1}",
        getValPayDateSet: "ctx/pr/core/wageprovision/processdatecls/getValPayDateSet/{0}",
        processingYear: "ctx/pr/core/wageprovision/processdatecls/processingYear/{0}/{1}",
        addDomainModel: "ctx/pr/core/wageprovision/processdatecls/addDomainModel",
        updateDomainModel: "ctx/pr/core/wageprovision/processdatecls/updateDomainModel"

    }

    export function getProcessInfomation (processCateNo: string): JQueryPromise<any> {
        let _path = format(paths.getProcessInfomation, processCateNo);
        return ajax(_path);
    }

    export function getSetDaySupport (processCateNo: string): JQueryPromise<any> {
        let _path = format(paths.getSetDaySupport, processCateNo);
        return ajax(_path);
    }

    export function getSelectProcessingYear (processCateNo: string, year: string): JQueryPromise<any> {
        let _path = format(paths.getSelectProcessingYear, processCateNo, year);
        return ajax(_path);
    }

    export function getValPayDateSet (processingIndicatorNO: string): JQueryPromise<any> {
        let _path = format(paths.getValPayDateSet, processingIndicatorNO);
        return ajax(_path);
    }

    export function addDomainModel(command): JQueryPromise<any> {
        return ajax(paths.addDomainModel, command);
    }

    export function updateDomainModel(command): JQueryPromise<any> {
        return ajax(paths.updateDomainModel, command);
    }
}