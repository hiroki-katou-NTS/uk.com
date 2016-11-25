var qmm019;
(function (qmm019) {
    var e;
    (function (e) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                /**
                 * Init screen model.
                 */
                function ScreenModel() {
                    var self = this;
                    self.selectedLayoutAtr = ko.observable(null);
                    self.selectLayoutCode = ko.observable(null);
                    self.selectLayoutName = ko.observable(null);
                    self.selectLayoutStartYm = ko.observable(null);
                    self.selectLayoutEndYm = ko.observable(null);
                    self.selectLayout = ko.observable(null);
                }
                // start function
                ScreenModel.prototype.start = function () {
                    var self = this;
                    e.service.getLayout("1", 201606).done(function (layout) {
                        self.selectLayout(layout);
                        self.startDiaglog();
                    }).fail(function (res) {
                        alert(res);
                    });
                    var dfd = $.Deferred();
                    dfd.resolve();
                    // Return.
                    return dfd.promise();
                };
                ScreenModel.prototype.startDiaglog = function () {
                    var self = this;
                    var layout = self.selectLayout();
                    var code = layout.stmtCode.trim();
                    if (code.length < 2) {
                        code = "0" + code;
                    }
                    self.selectLayoutCode(code);
                    self.selectLayoutName(layout.stmtName);
                    self.selectLayoutStartYm(nts.uk.text.formatYearMonth(layout.startYm));
                    self.selectLayoutEndYm(nts.uk.text.formatYearMonth(layout.endYm));
                };
                ScreenModel.prototype.layoutProcess = function () {
                    var self = this;
                    //履歴の編集-削除処理
                    if ($("#layoutDetele").is(":checked")) {
                        self.dataDelete();
                    }
                };
                ScreenModel.prototype.dataDelete = function () {
                    var self = this;
                    //明細書マスタ.DEL-1
                    e.service.deleteLayout(self.selectLayout()).done(function () {
                        alert("削除しました。");
                    }).fail(function (res) {
                        alert(res);
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = e.viewmodel || (e.viewmodel = {}));
    })(e = qmm019.e || (qmm019.e = {}));
})(qmm019 || (qmm019 = {}));
