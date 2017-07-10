module nts.uk.at.view.kmk005.d {
    export module service {
        var paths: any = {
            getSetting: "at/share/bpUnitUseSetting/getSetting",
            updateSetting: "at/share/bpUnitUseSetting/updateSetting",
        }
        
        export function getSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getSetting);
        }
        
        export function updateSetting(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateSetting, command);
        }
    }
}