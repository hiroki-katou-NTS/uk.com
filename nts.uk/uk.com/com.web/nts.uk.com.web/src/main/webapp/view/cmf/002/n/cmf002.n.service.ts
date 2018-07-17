module nts.uk.com.view.cmf002.n.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        var path: any = {
            getAWDataFormatSetting: "exio/exo/aw/getdatatype",
            setAWDataFormatSetting: "exio/exo/aw/add"
        };
        export function setAWDataFormatSetting(data): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.setAWDataFormatSetting, data);
        }
        export function getAWDataFormatSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.getAWDataFormatSetting);
        }
    }
}