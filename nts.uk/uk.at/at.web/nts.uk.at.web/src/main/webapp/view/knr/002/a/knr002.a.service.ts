module nts.uk.at.view.knr002.a.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getData: 'screen/at/infoempinfoterminal/getlistinfoempterminal'
    };

    export function getAll(): JQueryPromise<any> {
        return ajax(paths.getData);
    }
}