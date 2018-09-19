module nts.uk.com.view.cmf005.c {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        var paths = {
            getCategoryBySytem: "ctx/sys/assist/app/findCategory/{0}",
            getSystemTypes: "ctx/sys/assist/systemtype/getsystemtypes"
        }

        // Get system type
        export function getSysTypes(condSetCode: string): JQueryPromise<any> {
            return ajax("com", paths.getSystemTypes);
        }

        export function getConditionList(systemType: number): JQueryPromise<any> {
            let _path = format(paths.getCategoryBySytem, systemType);
            return ajax('com', _path);
        };

    }
}