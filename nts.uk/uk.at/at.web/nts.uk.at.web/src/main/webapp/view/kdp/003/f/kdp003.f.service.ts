module kdp003.f.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        getLogginSetting:  'ctx/sys/gateway/kdp/login/getLogginSetting'
    };

    export function getLogginSetting() {
        return ajax('at', paths.getLogginSetting);
    }

}