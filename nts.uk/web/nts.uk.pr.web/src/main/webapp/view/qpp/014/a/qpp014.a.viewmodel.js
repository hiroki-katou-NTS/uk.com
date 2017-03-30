// TreeGrid Node
var qpp014;
(function (qpp014) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    //
                    this.viewmodelb = new qpp014.b.viewmodel.ScreenModel();
                    this.viewmodeld = new qpp014.d.viewmodel.ScreenModel();
                    this.viewmodelg = new qpp014.g.viewmodel.ScreenModel();
                    this.viewmodelh = new qpp014.h.viewmodel.ScreenModel();
                    $('.func-btn').css('display', 'none');
                    $('#screenB').css('display', 'none');
                    var self = this;
                    //viewmodel A
                    self.a_SEL_001_items = ko.observableArray([
                        new PayDayProcessing('基本給1', '基本給'),
                        new PayDayProcessing('基本給2', '役職手当'),
                        new PayDayProcessing('0003', '基本給')
                    ]);
                    self.a_SEL_001_itemSelected = ko.observable('0003');
                }
                ScreenModel.prototype.nextScreen = function () {
                    $("#screenA").css("display", "none");
                    $("#screenB").css("display", "");
                    $("#screenB").ready(function () {
                        $(".func-btn").css("display", "");
                    });
                };
                ScreenModel.prototype.backScreen = function () {
                    $("#screenB").css("display", "none");
                    $("#screenA").css("display", "");
                    $(".func-btn").css("display", "none");
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            // Pay day processing
            var PayDayProcessing = (function () {
                function PayDayProcessing(companyCode, processingNumber, processingName, displaySet, currentProcessing, bonusAttribute, bonusCurrentProcessing) {
                    this.companyCode = companyCode;
                    this.processingNumber = processingNumber;
                    this.processingName = processingName;
                    this.displaySet = displaySet;
                    this.currentProcessing = currentProcessing;
                    this.bonusAttribute = bonusAttribute;
                    this.bonusCurrentProcessing = bonusCurrentProcessing;
                }
                return PayDayProcessing;
            }());
            viewmodel.PayDayProcessing = PayDayProcessing;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qpp014.a || (qpp014.a = {}));
})(qpp014 || (qpp014 = {}));
;
