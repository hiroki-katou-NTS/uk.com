var qmm018;
(function (qmm018) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                //getPaymentDateProcessingList: "pr/proto/paymentdatemaster/processing/findall",
                saveData: "pr/core/avepay/register"
            };
            /*
            export function getPaymentDateProcessingList(): JQueryPromise<Array<any>> {
                var dfd = $.Deferred<Array<any>>();
                nts.uk.request.ajax(paths.getPaymentDateProcessingList)
                    .done(function(res: Array<any>) {
                        dfd.resolve(res);
                    })
                    .fail(function(res) {
                        dfd.reject(res);
                    })
                return dfd.promise();
            }
            */
            function saveData(command) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.saveData, command)
                    .done(function (res) {
                    console.log(res);
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.saveData = saveData;
        })(service = a.service || (a.service = {}));
    })(a = qmm018.a || (qmm018.a = {}));
})(qmm018 || (qmm018 = {}));
