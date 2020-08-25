module nts.uk.at.view.kaf022.o.service {
    import ajax = nts.uk.request.ajax;

    const paths = {
        getOTQuota: "at/request/setting/company/applicationapproval/appovertime/getOTQuota",
        registerOTQuota: "at/request/setting/company/applicationapproval/appovertime/registerOTQuota",
        getFrames: "at/shared/overtimeworkframe/findall/used"
    };

    export function getOTQuota() {
        return ajax("at", paths.getOTQuota);
    }

    export function getOTFrames() {
        return ajax("at", paths.getFrames);
    }

    export function registerOTQuota(command): JQueryPromise<void>{
        return ajax("at", paths.registerOTQuota, command);
    }
}