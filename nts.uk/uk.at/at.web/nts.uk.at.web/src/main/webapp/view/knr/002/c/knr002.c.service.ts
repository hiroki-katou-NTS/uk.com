module nts.uk.at.view.knr002.c.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getData: 'screen/knr002/c/getRemoteSettings',
        register: 'at/record/knr002/cmd/c/registerAndSubmit',
    };

    export function getAll(empInfoTerminalCode: string): JQueryPromise<any> {
        const terminalCode = {
            empInfoTerminalCode
        };
        return ajax(paths.getData, terminalCode);
    }

    export function register(command: any): JQueryPromise<any> {
        return ajax(paths.register, command);
    }
}