module nts.uk.com.view.cmf005.c {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    export module service {
            var paths = {
                getCategoryBySytem: "ctx/sys/assist/app/findCategory/{0}",
                getSystemType: "exio/exi/condset/getSysType"
            }
        
            // Get system type
            export function getSysTypes(condSetCode: string): JQueryPromise<any> {
                return ajax("com", paths.getSystemType);
            }
        
            export function getCategoryListBySystem(systemType: number): JQueryPromise<any> {
                let _path = format(paths.getCategoryBySytem, systemType);
                return ajax('com', _path);
            };
        
    }
}