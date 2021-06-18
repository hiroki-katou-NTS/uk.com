module nts.uk.at.view.knr002.c.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getData: 'screen/knr002/c/getRemoteSettings',
        register: 'at/record/knr002/cmd/c/registerAndSubmit',
        updateRemoteSettings: 'screen/knr002/c/updateRemoteSetting'
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

    export function updateRemoteSettings(input: any): JQueryPromise<any> {
        return ajax(paths.updateRemoteSettings, input);
    }
}