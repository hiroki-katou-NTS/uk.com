module nts.uk.at.view.kmk005.k {
    export module service {
        import ajax = nts.uk.request.ajax;
        import format = nts.uk.text.format;

        let paths: any = {
            'get': 'at/share/cpBonusPaySetting/getSetting',
            'save': 'at/share/cpBonusPaySetting/saveSetting',
            'remove': 'at/share/cpBonusPaySetting/removeCBPSettingSetting',
            getName: 'at/share/bonusPaySetting/getBonusPaySetting/{0}'
        }

        export function getData() {
            return ajax(paths.get);
        }

        export function getName(bonusPaySettingCode) {
            return ajax(format(paths.getName, bonusPaySettingCode));
        }

        export function saveData(command) {
            return ajax(paths.add, command);
        }

        export function removeData(command) {
            return ajax(paths.remove, command);
        }
    }
}