var qpp007;
(function (qpp007) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var servicePath = {
                getallOutputSetting: "?"
            };
            function getallOutputSetting() {
                var dfd = $.Deferred();
                var data = [];
                for (var i = 1; i <= 10; i++) {
                    data.push({ code: '0' + i, name: 'Output Item Setting ' + i });
                }
                dfd.resolve(data);
                return dfd.promise();
            }
            service.getallOutputSetting = getallOutputSetting;
            var model;
            (function (model) {
                var OutputSetting = (function () {
                    function OutputSetting() {
                    }
                    return OutputSetting;
                }());
                model.OutputSetting = OutputSetting;
            })(model = service.model || (service.model = {}));
        })(service = a.service || (a.service = {}));
    })(a = qpp007.a || (qpp007.a = {}));
})(qpp007 || (qpp007 = {}));
