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
                    self.numberOfPerson = ko.observable(0);
                    self.processingNumberOfPerson = ko.observable(0);
                    self.stopProcess = function () {
                        var self = this;
                        var status = self.buttonStatus().status;
                        if (status == 1) {
                            self.stopTimer();
                        }
                        else {
                            // close dialog
                            nts.uk.ui.windows.close();
                        }
                    };
                }
                /**
                 * Start page.
                 * Load all data which is need for binding data.
                 */
                ScreenModel.prototype.startPage = function (data) {
                    var self = this;
                    var index = ko.observable(0);
                    self.numberOfPerson(data.personIdList.length);
                    if (data.personIdList.length > 0) {
                        self.buttonStatus({ status: 1, displayText: "中止" });
                        self.buttonText("中止");
                        self.processingState(0);
                        self.processingStateText("データの作成中");
                    }
                    _.forEach(data.personIdList, function (personId) {
                        if (self.buttonStatus().status == 1) {
                            index(index() + 1);
                            // Resolve start page dfd after load all data.
                            $.when(self.createPaymentData(personId, data, index())).done(function (res) {
                                self.errorList.push(res);
                                self.processingNumberOfPerson(index());
                            }).fail(function () {
                                self.stopTimer();
                            });
                        }
                    });
                    self.processingNumberOfPerson.subscribe(function (value) {
                        console.log(value);
                        if (value == data.personIdList.length) {
                            self.stopTimer();
                        }
                    });
                };
                ScreenModel.prototype.stopTimer = function () {
                    var self = this;
                    self.timer.end();
                    self.buttonStatus({ status: 0, displayText: "閉じる" });
                    self.buttonText("閉じる");
                    self.processingState(1);
                    self.processingStateText("完了");
                };
                /**
                 * Request create data payment
                 */
                ScreenModel.prototype.createPaymentData = function (personId, data, index) {
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
                            index: index,
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
