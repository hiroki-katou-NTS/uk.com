module nts.uk.com.view.cmf002.m {
    export module service {
        var path: any = {
            sendPerformSettingByTime: "exio/exo/dataformat/sendPerformSettingByTime",
            findPerformSettingByTime: "exio/exo/dataformat/findPerformSettingByTime"
        };
        export function sendPerformSettingByTime(data): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.sendPerformSettingByTime, data);
 
        }

        export function findPerformSettingByTime(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.findPerformSettingByTime);
        }
    }

}