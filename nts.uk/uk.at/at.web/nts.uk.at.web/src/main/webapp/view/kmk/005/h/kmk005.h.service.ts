module nts.uk.at.view.kmk005.h.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'gets': 'at/share/wpBonusPaySetting/getListWPBonusPaySetting',
        'get': 'at/share/wpBonusPaySetting/getWPBPSetting/{0}',
        'save': 'at/share/wpBonusPaySetting/saveWpBonsPaySetting',
        getName: 'at/share/bonusPaySetting/getBonusPaySetting/{0}'
    }

    export function getData(data: Array<string>) {
        return ajax(paths.gets, data);
    }

    export function getSetting(wid: string) {
        if (!wid) {
            return $.Deferred().resolve(undefined).promise();
        }
        return ajax(format(paths.get, wid));
    }

    export function getName(bonusPaySettingCode) {
        return ajax(format(paths.getName, bonusPaySettingCode));
    }

    export function saveData(command) {

        // if remove action
        if (!command.bonusPaySettingCode || command.bonusPaySettingCode == '000') {
            command.action = 1;
        }

        // push to webservice
        return ajax(paths.save, command);
    }
}