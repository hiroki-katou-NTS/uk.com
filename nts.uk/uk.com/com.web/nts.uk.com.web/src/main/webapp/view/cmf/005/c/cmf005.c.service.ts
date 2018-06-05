module nts.uk.com.view.cmf005.c {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        var paths = {
            getCategoryBySytem: "ctx/sys/assist/app/findCategoryByCodeOrName",
            getSystemTypes: "ctx/sys/assist/systemtype/getsystemtypes"
        }

        // Get system type
        export function getSysTypes(condSetCode: string): JQueryPromise<any> {
            return ajax("com", paths.getSystemTypes);
        }

        export function getCategoryListBySystem(systemType: number, categoriesIgnore: any): JQueryPromise<any> {
            var data = {
                systemType: systemType,
                categoriesIgnore: categoriesIgnore
            };
            return ajax('com', paths.getCategoryBySytem, data);
        };

    }
}