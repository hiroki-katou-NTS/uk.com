module nts.uk.at.view.kmk005.i.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'gets': 'at/share/psBonusPaySetting/getList',
        'get': 'at/share/psBonusPaySetting/getSetting/{0}',
        'save': 'at/share/psBonusPaySetting/saveSetting',
        getName: 'at/share/bonusPaySetting/getBonusPaySetting/{0}'
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
}