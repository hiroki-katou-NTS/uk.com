var cmm014;
(function (cmm014) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    this.classificationCode = ko.observable("");
                    this.classificationName = ko.observable("");
                    this.classificationMemo = ko.observable("");
                    this.classificationList = ko.observableArray([]);
                    this.selectedClassificationCode = ko.observable(null);
                }
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    dfd.resolve();
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var Classification = (function () {
                function Classification(classificationCode, classificationName) {
                    this.classificationCode = classificationCode;
                    this.classificationName = classificationName;
                }
                return Classification;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = cmm014.a || (cmm014.a = {}));
})(cmm014 || (cmm014 = {}));
