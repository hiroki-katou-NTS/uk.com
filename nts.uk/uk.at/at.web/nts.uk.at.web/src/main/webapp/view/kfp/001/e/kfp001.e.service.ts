module nts.uk.at.view.kfp001.e {
    import format = nts.uk.text.format;
    import ajax = nts.uk.request.ajax;
    export module service {

        var paths: any = {
            executeAggr: "ctx/at/record/optionalaggr/executeAggr/{0}",
            getErrorMessageInfo: "ctx/at/record/optionalaggr/getErrorMessageInfo/{0}",
            getErrorInfos: "ctx/at/record/optionalaggr/finderrorinfo/{0}",
            stopExecute: "ctx/at/record/optionalaggr/stopExecute/{0}",
            getAggrPeriod: "ctx/at/record/optionalaggr/aggrPeriod/{0}"
        }


        export function executeAggr(excuteId: any): JQueryPromise<any> {
            let _path = format(paths.executeAggr, excuteId);
            return ajax("at", _path);
        }
        export function getErrorMessageInfo(excuteId: any): JQueryPromise<any> {
            let _path = format(paths.getErrorMessageInfo, excuteId);
            return ajax("at", _path);
        }
        export function getErrorInfos(aggrId: string): JQueryPromise<any> {
            let _path = format(paths.getErrorInfos, aggrId);
            return ajax("at", _path);
        }
        export function stopExecute(dataFromD: any) {
            let _path = format(paths.stopExecute, dataFromD);
            return ajax("at", _path);
        }
        export function getAggrPeriod(id: any) {
            let _path = format(paths.getAggrPeriod, id);
            return ajax("at", _path);
        }



    }
}
