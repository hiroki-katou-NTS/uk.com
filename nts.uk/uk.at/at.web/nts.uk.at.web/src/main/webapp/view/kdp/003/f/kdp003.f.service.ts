module kdp003.f.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        getLogginSetting:  'ctx/sys/gateway/kdp/login/getLogginSetting',
        adminMode :        'ctx/sys/gateway/kdp/login/adminmode'
    };

    export function getLogginSetting() {
        return ajax('at', paths.getLogginSetting);
    }
    
    export function logginModeAdmin(data: any): JQueryPromise<string> {
            return nts.uk.request.ajax('at', paths.adminMode , data);
        }

}