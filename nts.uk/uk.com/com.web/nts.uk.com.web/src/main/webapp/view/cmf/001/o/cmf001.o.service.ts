module nts.uk.com.view.cmf001.o {
    import model = cmf001.share.model;
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        var paths = {
            getSystemType: "exio/exi/condset/getSysType",
            getConditionList: "exio/exi/condset/getStdAcceptCondSetBySysType/{0}",
            getNumberOfLine: "exio/exi/csvimport/getNumberOfLine/{0}/{1}",
            getAllStdAcceptItem: "exio/exi/item/getAllStdAcceptItem/{0}/{1}",
            getCategoryItem: "exio/exi/condset/getCategoryItemData/{0}",
            getRecord: "exio/exi/csvimport/getCsvRecord"
        }

        export function getSysTypes(): JQueryPromise<any> {
            return ajax("com", paths.getSystemType);
        }

        export function getConditionList(systemType: number): JQueryPromise<any> {
            let _path = format(paths.getConditionList, systemType);
            return ajax('com', _path);
        };

        export function getNumberOfLine(fileId: string, endCoding: number): JQueryPromise<any> {
            let _path = format(paths.getNumberOfLine, fileId, endCoding);
            return ajax('com', _path);
        };

        export function getStdAcceptItem(systemType: number, conditionSetCd: string): JQueryPromise<any> {
            let _path = format(paths.getAllStdAcceptItem, systemType, conditionSetCd);
            return ajax('com', _path);
        };

        export function getCategoryItem(categoryId: string): JQueryPromise<any> {
            let _path = format(paths.getCategoryItem, categoryId);
            return ajax("com", _path);
        };
        
        export function getRecord(fileId: string, columns: Array<number>, index: number, endCoding: number): JQueryPromise<any> {
            let dto: any = {fileId: fileId, columns: columns, index: index, endCoding: endCoding}
            return ajax('com', paths.getRecord, dto);
        };
    }
}
