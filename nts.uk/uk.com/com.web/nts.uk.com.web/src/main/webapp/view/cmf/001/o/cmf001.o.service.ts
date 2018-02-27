module nts.uk.com.view.cmf001.o {
    import model = cmf001.share.model;
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        var paths = {
            getConditionList: "exio/condset/getConditionBySystemType/{0}",
            getFileInfo: "exio/condset/getTotalRecord/{0}"
        }

        export function getConditionList(systemType: number): JQueryPromise<any> {
            let _path = format(paths.getConditionList, systemType);
            return ajax('com', _path);
        };        
        
        export function getTotalRecord(fileId: string): JQueryPromise<any> { 
            debugger;
            let _path = format(paths.getFileInfo, fileId);
            return ajax('com', _path);
        };
    }
}
