module qmm020.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    //duong dan   
    var paths = {
        getEmployAllotSettingHeaderList: "pr/core/allot/findallemployeeallotheader", //1
        getMaxDate: "pr/core/allot/findallemployeeallotheaderMax",  //2
        getEmployeeDetail: "pr/core/allot/findEmployeeDetail/{0}",       //5
        getEmployeeName: "basic/organization/employment/findallemployments",   //3
        getLayoutName: "pr/core/allot/findcompanyallotlayoutname/{0}"    //4
    }

    export function getEmployeeAllotHeaderList() {
        var dfd = $.Deferred();

        ajax(paths.getEmployAllotSettingHeaderList)
            .done((res) => { dfd.resolve(res); })
            .fail((res) => { dfd.reject(res); });

        return dfd.promise();
    }



    export function getEmployeeName() {
        let dfd = $.Deferred();

        ajax("com", paths.getEmployeeName)
            .done((res) => { dfd.resolve(res); })
            .fail((msg) => { dfd.reject(msg); });

        return dfd.promise();
    }

    export function getEmployeeDetail(histId: string) {
        let dfd = $.Deferred();
        let _path = format(paths.getEmployeeDetail, histId);

        ajax(_path)
            .done((res) => { dfd.resolve(res); })
            .fail((msg) => { dfd.reject(msg); });

        return dfd.promise();
    }

    export function getMaxDate() {
        let dfd = $.Deferred();

        ajax(paths.getMaxDate)
            .done((res) => { dfd.resolve(res); })
            .fail((error) => { dfd.reject(error); });

        return dfd.promise();
    }


    export function getAllotLayoutName(stmtCode: string) {
        var dfd = $.Deferred<any>();

        var _path = format(paths.getLayoutName, stmtCode);

        ajax(_path)
            .done((res) => { dfd.resolve(res); })
            .fail((msg) => { dfd.reject(msg); });

        return dfd.promise();
    }
}
