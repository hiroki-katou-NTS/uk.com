module nts.uk.at.view.kaf009.b.service {
    var paths = {
        getGoBackDirectly: "/at/request/application/gobackdirectly/getGoBackDirectlyByAppID",
        getGoBackDirectlySetting: "/at/request/application/gobackdirectly/getGoBackCommonSetting"
    }
    /**
     * get GoBackDirectly
     */
    export function getGoBackDirectly(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getGoBackDirectly, {});
    }

    /**
     * get GoBackSetting
     */
    export function getGoBackSetting(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getGoBackDirectlySetting, {});
    }
}