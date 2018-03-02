module nts.uk.com.view.cmf001.o {
    import model = cmf001.share.model;
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        var paths = {
            getConditionList: "exio/exi/condset/getStdAcceptCondSetBySysType/{0}",
            getNumberOfLine: "exio/exi/condset/getNumberOfLine/{0}",
            getAllStdAcceptItem: "exio/exi/item/getAllStdAcceptItem/{0}/{1}",
            getRecord: "exio/exi/condset/getRecord/{0}/{1}/{2}",
        }

        export function getConditionList(systemType: number): JQueryPromise<any> {
            let _path = format(paths.getConditionList, systemType);
            return ajax('com', _path);
        };

        export function getNumberOfLine(fileId: string): JQueryPromise<any> {
            let _path = format(paths.getNumberOfLine, fileId);
            return ajax('com', _path);
        };

        export function getStdAcceptItem(systemType: number, conditionSetCd: string): JQueryPromise<any> {
            let _path = format(paths.getAllStdAcceptItem, systemType, conditionSetCd);
            return ajax('com', _path);
        };

        export function getRecord(fileId: string, numOfCol: number, index: number): JQueryPromise<any> {
            let _path = format(paths.getRecord, fileId, numOfCol, index);
            return ajax('com', _path);
        };
    }
}
