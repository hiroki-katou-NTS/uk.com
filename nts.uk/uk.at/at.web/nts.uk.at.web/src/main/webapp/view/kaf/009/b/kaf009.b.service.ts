module nts.uk.at.view.kaf009.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getAllWorkLocation: "at/record/worklocation/findall",
        getGoBackDirectly: "/at/request/application/gobackdirectly/getGoBackDirectlyByAppID",
        getGoBackDirectlySetting: "/at/request/application/gobackdirectly/getGoBackCommonSetting",
        getGoBackDirectDetail: "/at/request/application/gobackdirectly/getGoBackDirectDetail",
        insertGoBackDirectly: "/at/request/application/gobackdirectly/insertGoBackDirectly",
        updateGoBackDirectly: "/at/request/application/gobackdirectly/updateGoBackDirectly"
    }
    /**
     * get all Work Location  
     */
    export function getAllLocation(): JQueryPromise<any> {
        return ajax("at", paths.getAllWorkLocation, {});
    }

    /**
     * get GoBackDirectly
     */
    export function getGoBackDirectly(): JQueryPromise<any> {
        return ajax("at", paths.getGoBackDirectly, {});
    }

    /**
     * get GoBackSetting
     */
    export function getGoBackSetting(): JQueryPromise<any> {
        return ajax("at", paths.getGoBackDirectlySetting, {});
    }

    /**
     * get Go Back Detail Data
     */
    export function getGoBackDirectDetail(): JQueryPromise<any> {
        return ajax("at", paths.getGoBackDirectDetail, {});
    }
    /**
     * 
     */
    export function insertGoBackDirect(currentGoBack: viewmodel.IGoBackCommand): JQueryPromise<Array<any>> {
        return ajax("at", paths.insertGoBackDirectly, currentGoBack);
        //return ajax("at",paths.updateGoBackDirectly,currentGoBack);
    }
    /**
     * 
     */
    export function updateGoBackDirect(currentGoBack: viewmodel.IGoBackCommand): JQueryPromise<Array<any>> {
        return ajax("at", paths.updateGoBackDirectly, currentGoBack);
    }
}