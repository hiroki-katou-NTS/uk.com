module nts.uk.at.view.kmk005.g.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        'get': 'at/share/cpBonusPaySetting/getCBPSettingSetting',
        'add': 'at/share/cpBonusPaySetting/CBPSettingSetting',
        'update': 'at/share/cpBonusPaySetting/updateCBPSettingSetting',
        'remove': 'at/share/cpBonusPaySetting/removeCBPSettingSetting'
    }

    export function get() {
        return ajax(paths.get);
    }

    export function add(command) {
        return ajax(paths.add, command);
    }

    export function update(command) {
        return ajax(paths.update, command);
    }

    export function remove(command) {
        return ajax(paths.remove, command);
    }
}