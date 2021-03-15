module nts.uk.com.view.cmf001.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        getAllData: "exio/exi/item/getAllStdAcceptItem/{0}",
        registerData: "exio/exi/item/register",
        registerDataAndReturn: "exio/exi/item/registerReturn",
        getAllCategoryBystem: "exio/exi/condset/getAllCategoryBystem/{0}",
        getCategoryItem: "exio/exi/condset/getCategoryItemData/{0}",
        getOneStdSettingData: "exio/exi/condset/getOneStdCondSet/{0}",
        getNumberOfLine: "exio/exi/csvimport/getNumberOfLine/{0}/{1}",
        getRecord: "exio/exi/csvimport/getRecord/{0}/{1}/{2}/{3}",
        getSystemType: "exio/exi/condset/getSysType",
        // update system
        registerStdData: "exio/exi/condset/registerStd",
    }
    // update system
    export function registerStdData(data: any): JQueryPromise<any> {
        return ajax("com", paths.registerStdData, data);
    };

    export function getAllData(condCode: string): JQueryPromise<any> {
        let _path = format(paths.getAllData, condCode);
        return ajax("com", _path);
    };
    
    export function registerData(data: any): JQueryPromise<any> {
        return ajax("com", paths.registerData, data);
    };
    
    export function registerDataAndReturn(data: any): JQueryPromise<any> {
        return ajax("com", paths.registerDataAndReturn, data);
    };
    
    export function getAllCategoryBystem(system: number): JQueryPromise<any> {
        let _path = format(paths.getAllCategoryBystem, system);
        return ajax("com", _path);
    }
    
    export function getCategoryItem(categoryId: any): JQueryPromise<any> {
        let _path = format(paths.getCategoryItem, categoryId);
        return ajax("com", _path);
    };
    
    export function getOneStdData(code: string): JQueryPromise<any> {
        let _path = format(paths.getOneStdSettingData, code);
        return ajax("com", _path);
    };
    
    export function getNumberOfLine(fileId: string, endCoding: number): JQueryPromise<any> {
        let _path = format(paths.getNumberOfLine, fileId, endCoding);
        return ajax('com', _path);
    };

    export function getRecord(fileId: string, dataLineNum: number, startLine: number, endCoding: number): JQueryPromise<any> {
        let _path = format(paths.getRecord, fileId, dataLineNum, startLine, endCoding);
        return ajax('com', _path);
    };
    
    export function getSysTypes(): JQueryPromise<any> {
        return ajax("com", paths.getSystemType);
    }
}