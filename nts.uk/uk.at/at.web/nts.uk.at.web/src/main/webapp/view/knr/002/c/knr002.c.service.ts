module nts.uk.at.view.knr002.c.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getData: 'screen/at/knr002/c/getRemoteSettings'
    };

    export function getAll(empInfoTerminalCode: string): JQueryPromise<any> {
        const terminalCode = {
            empInfoTerminalCode
        };
        return ajax(paths.getData, terminalCode);
    }
}