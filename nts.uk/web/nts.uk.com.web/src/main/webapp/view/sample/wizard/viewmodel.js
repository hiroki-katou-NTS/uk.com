var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var wizard;
            (function (wizard) {
                var viewmodel;
                (function (viewmodel) {
                    var ScreenModel = (function () {
                        function ScreenModel() {
                            var self = this;
                            self.stepList = [
                                { content: '.step-1' },
                                { content: '.step-2' },
                                { content: '.step-3' },
                                { content: '.step-4' },
                                { content: '.step-5' },
                                { content: '.step-6' }
                            ];
                            self.stepSelected = ko.observable({ content: '.step-1' });
                        }
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
                })(viewmodel = wizard.viewmodel || (wizard.viewmodel = {}));
            })(wizard = ui.wizard || (ui.wizard = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
