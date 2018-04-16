module nts.uk.at.view.kmk013.g {
    export module service {
        let paths: any = {
            regAgg : "shared/selection/func/regAgg",
            regWorkMulti : "shared/selection/func/regWorkMulti",
            regTempWork : "shared/selection/func/regTempWork",
            regFlexWorkSet : "shared/selection/func/regFlexWorkSet",
            loadAllSetting : "shared/selection/func/loadAllSetting",
        }
        export function loadAllSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.loadAllSetting);
        }
        export function regWorkMulti(obj): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.regWorkMulti,obj);
        }
        export function regTempWork(obj): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.regTempWork,obj);
        }
        export function regAgg(obj): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.regAgg,obj);
        }
        export function regFlexWorkSet(obj): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.regFlexWorkSet,obj);
        }
    }
}