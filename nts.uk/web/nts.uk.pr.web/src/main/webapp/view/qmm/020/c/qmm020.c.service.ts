module qmm020.c.service {
    //duong dan   
    var paths = {
        getEmployAllotSettingHeaderList: "pr/core/allot/findallemployeeallotheader",
        getEmployAllotSettingDetailList: "pr/core/allot/findallemployeeallotdetail",
        getAllEmployeeAllotSettingList: "pr/core/allot/findAllEmployeeAllotSettingList/{0}"
    }
    /**
     * Get list payment date processing.
     */
    export function getEmployeeAllotHeaderList(): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getEmployAllotSettingHeaderList)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    /**
     * Get employee list with payment doc, bunus doc
     */
    export function getEmployeeAllotDetailList(): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getEmployAllotSettingDetailList)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function getAllEmployeeAllotSetting(histId: string): JQueryPromise<Array<any>> {
        let dfd = $.Deferred<Array<any>>();
        let _path = nts.uk.text.format(paths.getAllEmployeeAllotSettingList, histId);
        nts.uk.request.ajax(_path).done(function(res: Array<any>) {
            dfd.resolve(res);
        }).fail(function(error) {
            dfd.reject(error);
        })
        return dfd.promise();
    }

}
