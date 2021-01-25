module nts.uk.com.view.cmf001.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        getSystemType: "exio/exi/condset/getSysType",
        getAllStdData: "exio/exi/condset/getAllStdAcceptCondSet",
        getOneStdData: "exio/exi/condset/getOneStdCondSet/{0}",
        registerStdData: "exio/exi/condset/registerStd",
        deleteStdData: "exio/exi/condset/deleteStd", 
        copyStdData: "exio/exi/condset/copyCondSet",
        getAllStdItemData: "exio/exi/item/getAllStdAcceptItem/{0}"
    }

    export function getSysTypes(): JQueryPromise<any> {
        return ajax("com", paths.getSystemType);
    }
    
    export function getAllStdData(): JQueryPromise<any> {
        let _path = format(paths.getAllStdData);
        return ajax("com", _path);
    };
    
    export function getOneStdData(code: string): JQueryPromise<any> {
        let _path = format(paths.getOneStdData, code);
        return ajax("com", _path);
    };

    export function registerStdData(data: any): JQueryPromise<any> {
        return ajax("com", paths.registerStdData, data);
    };
    
    
    export function copyStdData(data: any): JQueryPromise<any> {
        return ajax("com", paths.copyStdData, data);
    };

    export function deleteStdData(data: any): JQueryPromise<any> {
        return ajax("com", paths.deleteStdData, data);
    }
    
    export function getAllStdItemData(code: string): JQueryPromise<any> {
        let _path = format(paths.getAllStdItemData, code);
        return ajax("com", _path);
    };
    
}