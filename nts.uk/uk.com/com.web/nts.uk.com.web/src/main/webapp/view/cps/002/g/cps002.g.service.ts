module cps002.g.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getUserSetting: 'ctx/pereg/usersetting/getUserSetting',
        setUserSetting: 'ctx/pereg/usersetting/update/updateUserSetting'
    };

    export function setUserSetting(command) {
        return ajax("com", paths.setUserSetting, command);
    }


    export function getUserSetting() {
        return ajax(paths.getUserSetting);
    }
}