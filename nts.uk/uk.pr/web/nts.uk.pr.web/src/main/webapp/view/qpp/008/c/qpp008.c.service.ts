module qpp008.c.service {
    var paths: any = {
        getListComparingFormHeader: "report/payment/comparing/find/formHeader",
        getComparingFormForTab: "report/payment/comparing/getDataTab/{0}",
        insertdata: "report/payment/comparing/insertData",
        updateData: "report/payment/comparing/updateData",
        deleteData: "report/payment/comparing/deleteData",
    }

    export function getListComparingFormHeader(): JQueryPromise<Array<any>> {
        let dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getListComparingFormHeader)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            }).fail(function(error: any) {
                dfd.reject(error);
            })
        return dfd.promise();
    }

    export function getComparingFormForTab(formCode: string): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        let _path = nts.uk.text.format(paths.getComparingFormForTab, formCode);
        nts.uk.request.ajax(_path).done(function(res: any) {
            dfd.resolve(res);
        }).fail(function(error: any) {
            dfd.reject(error);
        })
        return dfd.promise();
    }

    export function insertUpdateComparingForm(insertUpdateDataModel: any, isUpdate: boolean): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        let _path = isUpdate ? paths.updateData : paths.insertdata;
        nts.uk.request.ajax(_path, ko.toJS(insertUpdateDataModel)).done(function(res: any) {
            dfd.resolve(res);
        }).fail(function(error: any) {
            dfd.reject(error);
        })
        return dfd.promise();
    }

    export function deleteComparingForm(deleteDataModel: any): JQueryPromise<any> {
        let dfd = $.Deferred<any>();;
        nts.uk.request.ajax(paths.deleteData, ko.toJS(deleteDataModel)).done(function(res: any) {
            dfd.resolve(res);
        }).fail(function(error: any) {
            dfd.reject(error);
        })
        return dfd.promise();
    }

}