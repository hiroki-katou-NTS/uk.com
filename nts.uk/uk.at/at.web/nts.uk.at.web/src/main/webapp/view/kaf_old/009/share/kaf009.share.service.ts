module nts.uk.at.view.kaf009.share {
    export module service {
        import ajax = nts.uk.request.ajax;
        import common = nts.uk.at.view.kaf009.share.common;
        import format = nts.uk.text.format;
        var paths = {
            getAllWorkLocation: "at/record/worklocation/findall",
            getGoBackDirectlySetting: "/at/request/application/gobackdirectly/getGoBackCommonSetting",
            getGoBackDirectDetail: "/at/request/application/gobackdirectly/getGoBackDirectDetail/{0}",
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
         * get GoBackSetting
         */
        export function getGoBackSetting(param: any): JQueryPromise<any> {
            return ajax("at", paths.getGoBackDirectlySetting, param);
        }

        /**
         * get Go Back Detail Data
         */
        export function getGoBackDirectDetail(appId: string): JQueryPromise<any> {
            return ajax("at", nts.uk.text.format(paths.getGoBackDirectDetail, appId));
        }
        /**
         * 
         */
        export function insertGoBackDirect(currentGoBack: common.GoBackCommand): JQueryPromise<Array<any>> {
            return ajax("at", paths.insertGoBackDirectly, currentGoBack);
        }
        /**
         * 
         */
        export function updateGoBackDirect(currentGoBack: common.GoBackCommand): JQueryPromise<Array<any>> {
            return ajax("at", paths.updateGoBackDirectly, currentGoBack);
        }

    }
}
