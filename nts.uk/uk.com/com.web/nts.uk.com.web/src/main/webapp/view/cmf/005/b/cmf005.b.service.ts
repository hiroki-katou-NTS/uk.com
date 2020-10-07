module nts.uk.com.view.cmf005.b {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        
        var paths = {
            addManualSetDel: "ctx/sys/assist/app/addManualSetDel",
            getSystemDate: "ctx/sys/assist/app/getSystemDate",
            screenDisplayProcess: "ctx/sys/assist/autosetting/screenDelDisplayProcessing",
            patternSettingSelect: "ctx/sys/assist/autosetting/delPatternSettingSelect",
        }

              
        export function addManualSetDel(manualSet: any): JQueryPromise<any> {
            return ajax('com', paths.addManualSetDel, manualSet);
        };
        
        export function getSystemDate(): JQueryPromise<any> {
            return ajax('com', paths.getSystemDate);
        };
     
        export function screenDisplayProcess(): JQueryPromise<any> {
          return ajax('com', paths.screenDisplayProcess);
        }

        export function patternSettingSelect(param: any): JQueryPromise<any> {
          return ajax('com', paths.patternSettingSelect, param);
        }
    }
}
