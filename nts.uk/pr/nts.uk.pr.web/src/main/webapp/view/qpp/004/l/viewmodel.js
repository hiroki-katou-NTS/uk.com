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
                    self.timer = new nts.uk.ui.sharedvm.KibanTimer('timer');
                    self.completeList = ko.observableArray([]);
                    self.errorList = ko.observableArray([]);
                    self.countError = ko.computed(function () {
                        return self.errorList().length;
                    });
                    self.buttonStatus = ko.observable(null);
                    self.buttonText = ko.observable(null);
                    self.visibleErrorList = ko.observable(false);
                    self.processingState = ko.observable(null);
                    self.processingStateText = ko.observable(null);
                    self.processingPersonIndex = ko.observable(0);
                    self.numberOfPerson = ko.observable(0);
                    self.displayProcessingNumberOfPerson = ko.computed(function () {
                        return nts.uk.text.format("{0}/{1}人", self.processingPersonIndex(), self.numberOfPerson());
                    });
                    self.stopProcess = function () {
                        var self = this;
                        var status = self.buttonStatus().status;
                        if (status == 1) {
                            self.timer.end();
                            self.buttonStatus({ status: 0, displayText: "閉じる" });
                            self.buttonText("閉じる");
                        }
                        else {
                        }
                    };
                }
                /**
                 * Start page.
                 * Load all data which is need for binding data.
                 */
                ScreenModel.prototype.startPage = function (data) {
                    var self = this;
                    var index = 0;
                    self.numberOfPerson(data.personIdList.length);
                    if (data.personIdList.length > 0) {
                        self.processingState(0);
                        self.processingStateText("データの作成中");
                    }
                    _.forEach(data.personIdList, function (personId) {
                        index = index + 1;
                        self.processingPersonIndex(index);
                        self.buttonStatus({ status: 1, displayText: "中止" });
                        self.buttonText("中止");
                        // Resolve start page dfd after load all data.
                        $.when(self.createPaymentData(personId, data)).done(function (res) {
                            self.errorList.push(res);
                        });
                    });
                    if (index == data.personIdList.length) {
                        self.timer.end();
                        self.buttonStatus({ status: 0, displayText: "閉じる" });
                        self.buttonText("閉じる");
                        self.processingState(1);
                        self.processingStateText("完了");
                    }
                };
                /**
                 * Request create data payment
                 */
                ScreenModel.prototype.createPaymentData = function (personId, data) {
                    var self = this;
                    var dfd = $.Deferred();
                    var parameter = {
                        personId: personId.id,
                        processingNo: data.processingNo,
                        processingYearMonth: data.processingYearMonth
                    };
                    $.when(qpp004.l.service.processCreatePaymentData(parameter)).done(function (data) {
                        self.completeList.push(personId);
                    }).fail(function (res) {
                        self.visibleErrorList(true);
                        var error = {};
                        error = {
                            personId: personId.id,
                            personName: personId.name,
                            errorMessage: res.message,
                            contenError: nts.uk.text.format("{0} (社員CD: {1})", res.message, personId.id)
                        };
                        dfd.resolve(error);
                    });
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = l.viewmodel || (l.viewmodel = {}));
    })(l = qpp004.l || (qpp004.l = {}));
})(qpp004 || (qpp004 = {}));
