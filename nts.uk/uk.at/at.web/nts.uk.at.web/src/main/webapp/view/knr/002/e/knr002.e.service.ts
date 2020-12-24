module nts.uk.at.view.knr002.e.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getData: 'abc'
    };

    export function getAll(empInfoTerminalCode: string): JQueryPromise<any> {
        const terminalCode = {
            empInfoTerminalCode
        };
        return ajax(paths.getData, terminalCode);
    }
}