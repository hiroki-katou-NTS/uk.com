module nts.uk.pr.view.qmm020.h {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getEmployee: "workflow/approvermanagement/workroot/getInforPsLogin",
            getWpName: "screen/com/kcp010/getLoginWorkPlace"
        };

        export function getEmployee(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.getEmployee);
        }

        export function getWpName(): JQueryPromise<any> {
            return ajax("com", paths.getWpName);
        }

    }
}