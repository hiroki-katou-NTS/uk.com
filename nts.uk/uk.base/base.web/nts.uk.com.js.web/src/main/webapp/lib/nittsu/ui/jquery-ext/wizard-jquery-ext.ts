/// <reference path="../../reference.ts"/>

interface JQuery {
    ntsWizard(action: string, param?: any): any;
}

module nts.uk.ui.jqueryExtentions {

    module ntsWizard {
        $.fn.ntsWizard = function(action: string, index?: number): any {
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
            };
        }

        function begin(wizard: JQuery): JQueryPromise<any> {
            let dfd = $.Deferred();

            wizard.data("waitStepShowed", true);
            
            wizard.setStep(0);
            
            wizard.bind("stepShowed", function(evt, ui) {
                wizard.unbind("stepShowed");
                wizard.data("waitStepShowed", false);
                dfd.resolve();
            });

            return dfd.promise();
        }

        function end(wizard: JQuery): JQueryPromise<any> {
            let dfd = $.Deferred();
            
            wizard.data("waitStepShowed", true);
            wizard.setStep(wizard.data("length") - 1);
            
            wizard.bind("stepShowed", function(evt, ui) {
                wizard.unbind("stepShowed");
                wizard.data("waitStepShowed", false);
                dfd.resolve();
            });

            return dfd.promise();
        }

        function goto(wizard: JQuery, index: number): JQueryPromise<any> {
            let dfd = $.Deferred();

            wizard.data("waitStepShowed", true);
            wizard.setStep(index);
            
            wizard.bind("stepShowed", function(evt, ui) {
                wizard.unbind("stepShowed");
                wizard.data("waitStepShowed", false);
                dfd.resolve();
            });

            return dfd.promise();
        }

        function prev(wizard: JQuery): JQueryPromise<any> {
            let dfd = $.Deferred();

            wizard.data("waitStepShowed", true);
            wizard.steps("previous");
            
            wizard.bind("stepShowed", function(evt, ui) {
                wizard.unbind("stepShowed");
                wizard.data("waitStepShowed", false);
                dfd.resolve();
            });

            return dfd.promise();
        }

        function next(wizard: JQuery): JQueryPromise<any> {
            let dfd = $.Deferred();

            wizard.data("waitStepShowed", true);
            wizard.steps("next");
            
            wizard.bind("stepShowed", function(evt, ui) {
                wizard.unbind("stepShowed");
                wizard.data("waitStepShowed", false);
                dfd.resolve();
            });

            return dfd.promise();
        }

        function getCurrentStep(wizard: JQuery): number {
            return wizard.steps("getCurrentIndex");
        }

    }
}