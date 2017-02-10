var qet002;
(function (qet002) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.targetYear = ko.observable(2016);
                    this.isLowerLimit = ko.observable(true);
                    this.isUpperLimit = ko.observable(true);
                    this.lowerLimitValue = ko.observable(null);
                    this.upperLimitValue = ko.observable(null);
                }
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.print = function () {
                    alert('print report!');
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qet002.a || (qet002.a = {}));
})(qet002 || (qet002 = {}));
