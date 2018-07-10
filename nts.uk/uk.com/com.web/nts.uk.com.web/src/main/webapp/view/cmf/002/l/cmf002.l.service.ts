module nts.uk.com.view.cmf002.l {
    export module service {
        var path: any = {
            sendPerformSettingByTime: "exio/exo/dataformat/sendPerformSettingByTime"
        };
        export function sendPerformSettingByTime(data): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.sendPerformSettingByTime, data);
        }
    }
}