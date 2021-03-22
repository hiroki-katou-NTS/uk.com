module nts.uk.at.view.kmk005.i.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'gets': 'at/share/psBonusPaySetting/getList',
        'get': 'at/share/psBonusPaySetting/getSetting/{0}',
        'save': 'at/share/psBonusPaySetting/saveSetting',
        getName: 'at/share/bonusPaySetting/getBonusPaySetting/{0}',
        getListHist: 'ctx/at/shared/workingcondition/getList',
        getHistItem: 'ctx/at/shared/wcitem/findOne',
        register: 'ctx/at/shared/wcitem/register'
    }

    export function getList(wids: Array<string>) {
        return ajax(paths.gets, wids);
    }

    export function getData(wid) {
        if (!wid) {
            return $.Deferred().resolve(undefined).promise();
        }
        return ajax(format(paths.get, wid));
    }

    export function getName(bonusPaySettingCode) {
        return ajax(format(paths.getName, bonusPaySettingCode));
    }

    export function saveData(command) {
        // remove if bonusPaySettingCode is undefined
        if (!command.bonusPaySettingCode || command.bonusPaySettingCode == '000') {
            command.action = 1;
        }
        // call webservices
        return ajax(paths.save, command);
    }
    export function getHist(employeeId: string) : JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getListHist + "/" + employeeId);
    }

    export function getHistItem(histId: string) : JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getHistItem + "/" + histId);
    }

    export function register(command: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.register, command);
    }
}