module nts.uk.com.view.cmf001.o {
    import model = cmf001.share.model;
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        var paths = {
            getConditionList: "exio/exi/condset/getStdAcceptCondSetBySysType/{0}",
            getFileInfo: "exio/exi/condset/getTotalRecordCsv/{0}",
            getStdAcceptItem: "exio/exi/item/getAllStdAcceptItem"
        }

        export function getConditionList(systemType: number): JQueryPromise<any> {
            let _path = format(paths.getConditionList, systemType);
            return ajax('com', _path);
        };

        export function getTotalRecord(fileId: string): JQueryPromise<any> {
            let _path = format(paths.getFileInfo, fileId);
            return ajax('com', _path);
        };

        export function getStdAcceptItem(cid: string, systemType: number, conditionSetCd: string): JQueryPromise<any> {
            debugger;
            //let _path = format(paths.getStdAcceptItem, cid, systemType, conditionSetCd);
            //param: ParamDto = { cid: cid, systemType: systemType, conditionSetCd: conditionSetCd };
            return ajax('com', paths.getStdAcceptItem);
        };
    }

    class ParamDto {
        cid: string;
        systemType: number;
        conditionSetCd: string;
    }
}
