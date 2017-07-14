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

        function begin(wizard: JQuery): JQuery {
            wizard.setStep(0);
            return wizard;
        }

        function end(wizard: JQuery): JQuery {
            wizard.setStep(wizard.data("length") - 1);
            return wizard;
        }

        function goto(wizard: JQuery, index: number): JQuery {
            wizard.setStep(index);
            return wizard;
        }

        function prev(wizard: JQuery): JQuery {
            wizard.steps("previous");
            return wizard;
        }

        function next(wizard: JQuery): JQuery {
            wizard.steps("next");
            return wizard;
        }

        function getCurrentStep(wizard: JQuery): number {
            return wizard.steps("getCurrentIndex");
        }

    }
}