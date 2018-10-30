module nts.uk.at.view.kaf009.b.service {
    import ajax = nts.uk.request.ajax;
    import common = nts.uk.at.view.kaf009.share.common;
    import format = nts.uk.text.format;
    var paths = {
        getAllWorkLocation: "at/record/worklocation/findall",
        getGoBackDirectlySetting: "/at/request/application/gobackdirectly/getGoBackCommonSetting",
        getGoBackDirectDetail: "/at/request/application/gobackdirectly/getGoBackDirectDetail/{0}",
        checkBeforeChange: "/at/request/application/gobackdirectly/checkBeforeUpdateGoBackData",
        updateGoBackDirectly: "/at/request/application/gobackdirectly/updateGoBackDirectly",
        getDetailRealData: "/at/request/application/getDetailRealData"
    }
    /**
     * get all Work Location  
     */
    export function getAllLocation(): JQueryPromise<any> {
        return ajax("at", paths.getAllWorkLocation, {});
    }

    /**
     * get GoBackSetting
     */
    export function getGoBackSetting(param: any): JQueryPromise<any> {
        return ajax("at", paths.getGoBackDirectlySetting, param);
    }

    /**
     * get Go Back Detail Data
     */
    export function getGoBackDirectDetail(appId:string): JQueryPromise<any> {
        return ajax("at", nts.uk.text.format(paths.getGoBackDirectDetail, appId));
    }
    /**
     * 
     */
    export function checkBeforeChangeGoBackDirect(currentGoBack: common.GoBackCommand): JQueryPromise<Array<any>> {
        return ajax("at", paths.checkBeforeChange, currentGoBack);
    }
    /**
     * 
     */
    export function updateGoBackDirect(currentGoBack: common.GoBackCommand): JQueryPromise<Array<any>> {
        return ajax("at", paths.updateGoBackDirectly, currentGoBack);
    }
    
    export function getDetailRealData(appID: string): JQueryPromise<Array<any>> {
        return ajax("at", paths.getDetailRealData, appID);
    }
}