module qmm020.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    //duong dan   
    var paths = {
        getAllot: "pr/core/allot/findallemployeeallotheader",
        deleteAllot: 'pr/core/allot/deleteallotheader',
        getMaxDate: "pr/core/allot/findallemployeeallotheadermax",
        getEmpDetail: "pr/core/allot/findemployeedetail/{0}",
        getEmp: "basic/organization/employment/findallemployments",
        getLayoutName: "pr/core/allot/findcompanyallotlayoutname/{0}",
    }

    export function getAllots() {
        var dfd = $.Deferred();

        ajax(paths.getAllot)
            .done((res) => { dfd.resolve(res); })
            .fail((res) => { dfd.reject(res); });

        return dfd.promise();
    }


    export function getEmployees() {
        let dfd = $.Deferred();

        ajax("com", paths.getEmp)
            .done((res) => { dfd.resolve(res); })
            .fail((msg) => { dfd.reject(msg); });

        return dfd.promise();
    }

    export function getEmployeeDetail(histId: string) {
        let dfd = $.Deferred();
        let _path = format(paths.getEmpDetail, histId);

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



    export function getAllotLayoutName(param) {
        let dfd = $.Deferred();

        let _path = format(paths.getLayoutName, param);

        ajax(_path, undefined, {
            dataType: 'text',
            contentType: 'text/plain'
        })
            .done((resp: string) => { dfd.resolve(resp); })
            .fail((msg: string) => { dfd.reject(msg); });

        return dfd.promise();
    }

    export function saveData(models: Array<any>) {
        let dfd = $.Deferred();
        if (models.length > 0) {
            models.map((m) => {
                let data: any = {
                    historyId: '',
                    endYm: 0,
                    startYm: 0,
                    arrays: [
                        {},
                        {},
                        {},
                        {}
                    ]
                }
            });
        }
        return dfd.promise();
    }

    export function deleteData(models: Array<any>) {
        let dfd = $.Deferred();
        if (models.length > 0) {
            models.map((m) => {
                console.log(m);
                let data: any = {
                    historyId: m.historyId,
                    startYm: m.startYm,
                    endYm: m.endYm,
                    employees: [
                        
                    ]
                };
                ajax(paths.deleteAllot, data)
                    .done((resp) => { dfd.resolve(resp); })
                    .fail((msg) => { dfd.reject(msg); });
            });
        }
        return dfd.promise();
    }
}
