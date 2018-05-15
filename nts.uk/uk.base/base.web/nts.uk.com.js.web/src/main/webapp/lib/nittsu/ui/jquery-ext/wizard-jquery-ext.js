var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var jqueryExtentions;
            (function (jqueryExtentions) {
                var ntsWizard;
                (function (ntsWizard) {
                    $.fn.ntsWizard = function (action, index) {
                        var $wizard = $(this);
                        if (action === "begin") {
                            return begin($wizard);
                        }
                        else if (action === "end") {
                            return end($wizard);
                        }
                        else if (action === "goto") {
                            return goto($wizard, index);
                        }
                        else if (action === "prev") {
                            return prev($wizard);
                        }
                        else if (action === "next") {
                            return next($wizard);
                        }
                        else if (action === "getCurrentStep") {
                            return getCurrentStep($wizard);
                        }
                        else {
                            return $wizard;
                        }
                        ;
                    };
                    function begin(wizard) {
                        var dfd = $.Deferred();
                        wizard.data("waitStepShowed", true);
                        wizard.setStep(0);
                        wizard.bind("stepShowed", function (evt, ui) {
                            wizard.unbind("stepShowed");
                            wizard.data("waitStepShowed", false);
                            dfd.resolve();
                        });
                        return dfd.promise();
                    }
                    function end(wizard) {
                        var dfd = $.Deferred();
                        wizard.data("waitStepShowed", true);
                        wizard.setStep(wizard.data("length") - 1);
                        wizard.bind("stepShowed", function (evt, ui) {
                            wizard.unbind("stepShowed");
                            wizard.data("waitStepShowed", false);
                            dfd.resolve();
                        });
                        return dfd.promise();
                    }
                    function goto(wizard, index) {
                        var dfd = $.Deferred();
                        wizard.data("waitStepShowed", true);
                        wizard.setStep(index);
                        wizard.bind("stepShowed", function (evt, ui) {
                            wizard.unbind("stepShowed");
                            wizard.data("waitStepShowed", false);
                            dfd.resolve();
                        });
                        return dfd.promise();
                    }
                    function prev(wizard) {
                        var dfd = $.Deferred();
                        wizard.data("waitStepShowed", true);
                        wizard.steps("previous");
                        wizard.bind("stepShowed", function (evt, ui) {
                            wizard.unbind("stepShowed");
                            wizard.data("waitStepShowed", false);
                            dfd.resolve();
                        });
                        return dfd.promise();
                    }
                    function next(wizard) {
                        var dfd = $.Deferred();
                        wizard.data("waitStepShowed", true);
                        wizard.steps("next");
                        wizard.bind("stepShowed", function (evt, ui) {
                            wizard.unbind("stepShowed");
                            wizard.data("waitStepShowed", false);
                            dfd.resolve();
                        });
                        return dfd.promise();
                    }
                    function getCurrentStep(wizard) {
                        return wizard.steps("getCurrentIndex");
                    }
                })(ntsWizard || (ntsWizard = {}));
            })(jqueryExtentions = ui_1.jqueryExtentions || (ui_1.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=wizard-jquery-ext.js.map