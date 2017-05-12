var qpp008;
(function (qpp008) {
    var c;
    (function (c) {
        var service;
        (function (service) {
            var paths = {
                getListComparingFormHeader: "report/payment/comparing/find/formHeader",
                getComparingFormForTab: "report/payment/comparing/getDataTab/{0}",
                insertdata: "report/payment/comparing/insertData",
                updateData: "report/payment/comparing/updateData",
                deleteData: "report/payment/comparing/deleteData",
            };
            function getListComparingFormHeader() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getListComparingFormHeader)
                    .done(function (res) {
                    dfd.resolve(res);
                }).fail(function (error) {
                    dfd.reject(error);
                });
                return dfd.promise();
            }
            service.getListComparingFormHeader = getListComparingFormHeader;
            function getComparingFormForTab(formCode) {
                var dfd = $.Deferred();
                var _path = nts.uk.text.format(paths.getComparingFormForTab, formCode);
                nts.uk.request.ajax(_path).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (error) {
                    dfd.reject(error);
                });
                return dfd.promise();
            }
            service.getComparingFormForTab = getComparingFormForTab;
            function insertUpdateComparingForm(insertUpdateDataModel, isUpdate) {
                var dfd = $.Deferred();
                var _path = isUpdate ? paths.updateData : paths.insertdata;
                nts.uk.request.ajax(_path, ko.toJS(insertUpdateDataModel)).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (error) {
                    dfd.reject(error);
                });
                return dfd.promise();
            }
            service.insertUpdateComparingForm = insertUpdateComparingForm;
            function deleteComparingForm(deleteDataModel) {
                var dfd = $.Deferred();
                ;
                nts.uk.request.ajax(paths.deleteData, ko.toJS(deleteDataModel)).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (error) {
                    dfd.reject(error);
                });
                return dfd.promise();
            }
            service.deleteComparingForm = deleteComparingForm;
        })(service = c.service || (c.service = {}));
    })(c = qpp008.c || (qpp008.c = {}));
})(qpp008 || (qpp008 = {}));
//# sourceMappingURL=qpp008.c.service.js.map