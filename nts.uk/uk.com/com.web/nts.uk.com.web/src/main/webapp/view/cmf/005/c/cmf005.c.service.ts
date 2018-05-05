module nts.uk.com.view.cmf003.c {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    export module service {
            var paths = {
                //TODO: Fake ws
                getCategoryBySytem: "ctx/sys/assist/app/findCategory/{0}",
                getSystemType: "exio/exi/condset/getSysType"
            }
        
            // Get 出力条件設定（定型）
            export function getSysTypes(condSetCode: string): JQueryPromise<any> {
                return ajax("com", paths.getSystemType);
            }
        
            export function getCategoryListBySystem(systemType: number): JQueryPromise<any> {
                let _path = format(paths.getCategoryBySytem, systemType);
                return ajax('com', _path);
            };
        
    }
}