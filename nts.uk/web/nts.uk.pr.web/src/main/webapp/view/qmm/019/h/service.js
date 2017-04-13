var qmm019;
(function (qmm019) {
    var h;
    (function (h) {
        var service;
        (function (service) {
            var paths = {
                getAll: "pr/proto/personalwage/findalls/{0}",
            };
            function getPersonalWageNames(categoryAtr) {
                var dfd = $.Deferred();
                var _path = nts.uk.text.format(paths.getAll, categoryAtr);
                nts.uk.request.ajax(_path)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getPersonalWageNames = getPersonalWageNames;
            var model;
            (function (model) {
                var PersonalWageNameDto = (function () {
                    function PersonalWageNameDto() {
                    }
                    return PersonalWageNameDto;
                }());
                model.PersonalWageNameDto = PersonalWageNameDto;
            })(model = service.model || (service.model = {}));
        })(service = h.service || (h.service = {}));
    })(h = qmm019.h || (qmm019.h = {}));
})(qmm019 || (qmm019 = {}));
