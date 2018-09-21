module nts.uk.com.view.cmf002.j.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getCharacterDataFormatSetting: "exio/exo/char/getdatatype",
        setCharacterDataFormatSetting: "exio/exo/character/add",
        getCdConvertName: "exio/exo/char/getcconvertname/{0}"
    }

    export function getCharacterDataFormatSetting(): JQueryPromise<any> {
        return ajax("com", format(paths.getCharacterDataFormatSetting));
    }
    export function setCharacterDataFormatSetting(data): JQueryPromise<any> {
        return ajax("com", paths.setCharacterDataFormatSetting, data);
    }
    export function getCdConvertName(cdConvertCode: any): JQueryPromise<any> {
        return ajax('com', format(paths.getCdConvertName, cdConvertCode);
}