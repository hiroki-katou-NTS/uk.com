module nts.uk.at.view.kaf022.o.service {
    import ajax = nts.uk.request.ajax;

    const paths = {
        getOTQuota: "at/request/setting/company/applicationapproval/appovertime/getOTQuota",
        getOTQuotaByAtr: "at/request/setting/company/applicationapproval/appovertime/getOTQuotaByAtr",
        registerOTQuota: "at/request/setting/company/applicationapproval/appovertime/registerOTQuota",
        getFrames: "at/shared/overtimeworkframe/findall/used"
    };

    export function getOTQuota() {
        return ajax("at", paths.getOTQuota);
    }

    export function getOTQuotaByAtr(overtimeAtr: number, flexAtr: number) {
        return ajax("at", paths.getOTQuotaByAtr, {overtimeAtr: overtimeAtr, flexWorkAtr: flexAtr});
    }

    export function getOTFrames() {
        return ajax("at", paths.getFrames);
    }

    export function registerOTQuota(command: any): JQueryPromise<void>{
        return ajax("at", paths.registerOTQuota, command);
    }
}