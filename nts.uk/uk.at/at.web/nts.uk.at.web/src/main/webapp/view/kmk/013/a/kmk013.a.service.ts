module nts.uk.at.view.kmk013.a {
    export module service {
        let paths: any = {
            getDomainSet: "shared/selection/func/loadAllSetting"
        }
        export function getDomainSet(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getDomainSet);
        }
    }
}