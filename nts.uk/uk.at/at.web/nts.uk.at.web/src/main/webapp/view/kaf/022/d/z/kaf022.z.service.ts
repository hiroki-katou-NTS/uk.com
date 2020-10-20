module nts.uk.at.view.kaf022.z.service {
    import ajax = nts.uk.request.ajax;

    var paths: any = {
        getComConfig: "at/request/setting/workplace/requestbycompany/getAll",
        saveComConfig: "at/request/setting/workplace/requestbycompany/save"
    };

    export function getCom() { return ajax(paths.getComConfig) };

    export function saveCom(command) { return ajax(paths.saveComConfig, command) };

}