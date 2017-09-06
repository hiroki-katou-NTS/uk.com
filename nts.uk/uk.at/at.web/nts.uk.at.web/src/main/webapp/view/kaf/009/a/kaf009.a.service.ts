module nts.uk.at.view.kaf009.a.service {
    var paths = {
        getGoBackDirectly: "/at/request/application/gobackdirectly/getGoBackDirectlyByAppID",
        test: "/at/request/application/getall"
    }
    /**
     * get Go Back Directly
     */
    export function getGoBackDirectly(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.test, {});
    }

}