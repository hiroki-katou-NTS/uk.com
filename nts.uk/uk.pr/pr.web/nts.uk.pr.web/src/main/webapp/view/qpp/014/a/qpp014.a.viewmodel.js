// TreeGrid Node
var qpp014;
(function (qpp014) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.a_SEL_001_items = ko.observableArray([]);
                    self.a_SEL_001_itemSelected = ko.observable(0);
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.findAll().done(function () {
                        dfd.resolve();
                    });
                    return dfd.promise();
                };
                /**
                 * get data from table PAYDAY_PROCESSING
                 */
                ScreenModel.prototype.findAll = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    //get data with DISP_SET = 1
                    qpp014.a.service.findAll()
                        .done(function (data) {
                        if (data.length > 0) {
                            _.forEach(data, function (x) {
                                self.a_SEL_001_items().push(new PayDayProcessing(x.processingNo, x.processingName, x.dispSet, x.currentProcessingYm, x.bonusAtr, x.bcurrentProcessingYm));
                            });
                            self.a_SEL_001_items(_.sortBy(self.a_SEL_001_items(), 'currentProcessingYm'));
                            self.a_SEL_001_itemSelected(self.a_SEL_001_items()[0].processingNo);
                        }
                        else {
                            nts.uk.ui.dialog.alert("対象データがありません。"); //ER010
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                        dfd.reject(res);
                    });
                    return dfd.promise();
                };
                /**
                 * transfer data to screen D, go to next screen
                 */
                ScreenModel.prototype.nextScreen = function () {
                    var self = this;
                    var data = _.find(self.a_SEL_001_items(), function (x) {
                        return x.processingNo === self.a_SEL_001_itemSelected();
                    });
                    nts.uk.request.jump("/view/qpp/014/b/index.xhtml", data);
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var PayDayProcessing = (function () {
                function PayDayProcessing(processingNo, processingName, displaySet, currentProcessingYm, bonusAtr, bcurrentProcessingYm) {
                    this.processingNo = processingNo;
                    this.processingName = processingName;
                    this.displaySet = displaySet;
                    this.currentProcessingYm = currentProcessingYm;
                    this.bonusAtr = bonusAtr;
                    this.bcurrentProcessingYm = bcurrentProcessingYm;
                    this.label = this.processingNo + ' : ' + this.processingName;
                }
                return PayDayProcessing;
            }());
            viewmodel.PayDayProcessing = PayDayProcessing;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qpp014.a || (qpp014.a = {}));
})(qpp014 || (qpp014 = {}));
;
//# sourceMappingURL=qpp014.a.viewmodel.js.map