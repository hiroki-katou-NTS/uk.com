module nts.uk.at.view.kmk005.g.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'get': 'at/share/cpBonusPaySetting/getSetting',
        'save': 'at/share/cpBonusPaySetting/saveSetting',
        getName: 'at/share/bonusPaySetting/getBonusPaySetting/{0}',
        getUseSetting: "at/share/bpUnitUseSetting/getSetting",
    }

    export function getData() {
        return ajax(paths.get);
    }

    export function getName(bonusPaySettingCode) {
        return ajax(format(paths.getName, bonusPaySettingCode));
    }

    export function saveData(command: any) {
        return ajax(paths.save, command);
    }

    export function getUseSetting(): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getUseSetting);
    }
    
}