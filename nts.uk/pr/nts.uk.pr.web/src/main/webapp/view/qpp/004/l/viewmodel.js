var qpp004;
(function (qpp004) {
    var l;
    (function (l) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                /**
                 * Init screen model.
                 */
                function ScreenModel() {
                    var self = this;
                    self.completeList = ko.observableArray([]);
                    self.errorList = ko.observableArray([]);
                    self.countError = ko.computed(function () {
                        return self.errorList.length;
                    });
                }
                /**
                 * Start page.
                 * Load all data which is need for binding data.
                 */
                ScreenModel.prototype.startPage = function (data) {
                    var self = this;
                    // Page load dfd.
                    var dfd = $.Deferred();
                    _.forEach(data.personIdList, function (personId) {
                        var parameter = {
                            personId: personId,
                            processingNo: data.processingNo,
                            processingYearMonth: data.processingYearMonth
                        };
                        // Resolve start page dfd after load all data.
                        $.when(qpp004.l.service.createPaymentData(parameter)).done(function (data) {
                            self.completeList.push(personId);
                            dfd.resolve();
                        }).fail(function (res) {
                            self.errorList.push({
                                personId: personId,
                                errorMessage: res.message
                            });
                        });
                    });
                    return dfd.promise();
                };
                /**
                 * Redirect to page process create data
                 */
                ScreenModel.prototype.redirectToCreateData = function () {
                    nts.uk.request.jump("/view/qpp/004/b/index.xhtml");
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = l.viewmodel || (l.viewmodel = {}));
    })(l = qpp004.l || (qpp004.l = {}));
})(qpp004 || (qpp004 = {}));
