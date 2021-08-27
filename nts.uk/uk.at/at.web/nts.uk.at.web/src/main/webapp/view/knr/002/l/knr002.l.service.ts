module nts.uk.at.view.knr002.l.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getProductionSwitchDate: 'screen/knr002/l/getSwitchDate',
        register: 'screen/knr002/l/registerSwitchDate',
        registerAll: 'screen/knr002/l/registerAllSwitchDates'
    };

    export function getProductionSwitchDate(input: any): JQueryPromise<any> {
        return ajax(paths.getProductionSwitchDate, input);
    }

    export function register(command: any): JQueryPromise<any> {
        return ajax(paths.register, command);
    }

    export function registerAll(command: any): JQueryPromise<any> {
        return ajax(paths.registerAll, command);
    }
}