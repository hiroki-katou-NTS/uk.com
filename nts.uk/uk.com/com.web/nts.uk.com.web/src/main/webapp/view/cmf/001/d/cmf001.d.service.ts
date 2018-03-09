module nts.uk.com.view.cmf001.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        getAllData: "exio/exi/item/getAllStdAcceptItem/{0}/{1}",
        registerData: "exio/exi/item/register",
        getAllCategory: "exio/exi/condset/getAllCategory",
        getCategoryItem: "exio/exi/condset/getCategoryItemData/{0}",
        getOneStdSettingData: "exio/exi/condset/getOneStdCondSet/{0}/{1}"
    }

    export function getAllData(sysType: number, condCode: string): JQueryPromise<any> {
        let _path = format(paths.getAllData, sysType, condCode);
        return ajax("com", _path);
    };
    
    export function registerData(data: any): JQueryPromise<any> {
        return ajax("com", paths.registerData, data);
    };
    
    export function getAllCategory(): JQueryPromise<any> {
        return ajax("com", paths.getAllCategory);
    }
    
    export function getCategoryItem(categoryId: string): JQueryPromise<any> {
        let _path = format(paths.getCategoryItem, categoryId);
        return ajax("com", _path);
    };
    
    export function getOneStdData(sysType: number, code: string): JQueryPromise<any> {
        let _path = format(paths.getOneStdSettingData, sysType, code);
        return ajax("com", _path);
    };

}