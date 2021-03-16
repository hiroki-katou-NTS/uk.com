module nts.uk.at.view.knr002.e.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getInitData: 'screen/knr002/e/initialDisplayBackupScreen',
        getBackup: 'screen/knr002/e/getBackup',
        getBackupContent: 'screen/knr002/e/getBackupContent',
        getLocationSetting: 'screen/knr002/e/getLocationSetting'
    };

    export function getInitData(): JQueryPromise<any> {
        return ajax(paths.getInitData);
    }

    export function getBackup(code: string): JQueryPromise<any> {
        return ajax(paths.getBackup + "/" + code);
    }

    export function getBackupContent(code: string): JQueryPromise<any> {
        return ajax(paths.getBackupContent + "/" + code);
    }

    export function getLocationSetting(code: string): JQueryPromise<any> {
        return ajax(paths.getLocationSetting + "/" + code);
    }
}