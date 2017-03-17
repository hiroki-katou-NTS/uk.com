var qpp008;
(function (qpp008) {
    var c;
    (function (c) {
        var service;
        (function (service) {
            var paths = {
                getListComparingFormHeader: "report/payment/comparing/find/formHeader"
            };
            function getListComparingFormHeader() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getListComparingFormHeader)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (error) {
                    dfd.reject(error);
                });
                return dfd.promise();
            }
            service.getListComparingFormHeader = getListComparingFormHeader;
            var model;
            (function (model) {
                var ComparingFormHeader = (function () {
                    function ComparingFormHeader(formCode, formName) {
                        this.formCode = formCode;
                        this.formName = formName;
                    }
                    return ComparingFormHeader;
                }());
                model.ComparingFormHeader = ComparingFormHeader;
            })(model = service.model || (service.model = {}));
        })(service = c.service || (c.service = {}));
    })(c = qpp008.c || (qpp008.c = {}));
})(qpp008 || (qpp008 = {}));
