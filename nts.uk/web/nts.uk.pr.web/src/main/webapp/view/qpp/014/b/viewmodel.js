var qpp014;
(function (qpp014) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.paymentDateProcessingList = ko.observableArray([]);
                    self.selectedPaymentDate = ko.observable(null);
                    self.stepList = [
                        { content: '.step-1' },
                        { content: '.step-2' },
                        { content: '.step-3' },
                        { content: '.step-4' },
                        { content: '.step-5' },
                        { content: '.step-6' }
                    ];
                    self.stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });
                    $("#wizard").steps({
                        headerTag: "h3",
                        bodyTag: "section",
                        transitionEffect: "slideLeft",
                        stepsOrientation: "vertical"
                    });
                    $(document).ready(function () {
                        $('div.content.clearfix').css("display", "none");
                        $('div.steps.clearfix').css("width", "250px");
                        $('div.steps.clearfix').css("height", "697px");
                    });
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qpp014.b.service.getPaymentDateProcessingList().done(function (data) {
                        self.paymentDateProcessingList(data);
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.begin = function () {
                    $('#wizard').ntsWizard("begin");
                };
                ScreenModel.prototype.end = function () {
                    $('#wizard').ntsWizard("end");
                };
                ScreenModel.prototype.next = function () {
                    $('#wizard').ntsWizard("next");
                };
                ScreenModel.prototype.previous = function () {
                    $('#wizard').ntsWizard("prev");
                };
                ScreenModel.prototype.getCurrentStep = function () {
                    alert($('#wizard').ntsWizard("getCurrentStep"));
                };
                ScreenModel.prototype.goto = function () {
                    var index = this.stepList.indexOf(this.stepSelected());
                    $('#wizard').ntsWizard("goto", index);
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qpp014.b || (qpp014.b = {}));
})(qpp014 || (qpp014 = {}));
