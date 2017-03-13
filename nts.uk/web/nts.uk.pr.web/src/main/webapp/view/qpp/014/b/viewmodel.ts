module qpp014.b.viewmodel {
    export class ScreenModel {
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymentDate: KnockoutObservable<any>;
        stepList: Array<nts.uk.ui.NtsWizardStep>;
        stepSelected: KnockoutObservable<nts.uk.ui.NtsWizardStep>;
        constructor() {
            var self = this;
            self.paymentDateProcessingList = ko.observableArray([]);
            self.selectedPaymentDate = ko.observable(null);
            self.stepList = [
            {content: '.step-1'},
            {content: '.step-2'},
            {content: '.step-3'},
            {content: '.step-4'},
            {content: '.step-5'},
            {content: '.step-6'}
            ];
            self.stepSelected = ko.observable({id: 'step-1', content: '.step-1'});
            $("#wizard").steps({
                headerTag: "h3",
                bodyTag: "section",
                transitionEffect: "slideLeft",
                stepsOrientation: "vertical"
            });
            $(document).ready(function() { 
                $('div.content.clearfix').css("display","none"); 
                $('div.steps.clearfix').css("width","250px");
                $('div.steps.clearfix').css("height","697px");});
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;
            
            var dfd = $.Deferred();
            qpp014.b.service.getPaymentDateProcessingList().done(function(data) {
                self.paymentDateProcessingList(data);
                dfd.resolve();
            }).fail(function(res) {
                
            });
            return dfd.promise();
        }
        
        begin() {
        $('#wizard').ntsWizard("begin");
        }
        end() {
            $('#wizard').ntsWizard("end");
        }
        next() {
            $('#wizard').ntsWizard("next");
        }
        previous() {
            $('#wizard').ntsWizard("prev");
        }
        getCurrentStep() {
            alert($('#wizard').ntsWizard("getCurrentStep"));
        }
        goto() {
            var index = this.stepList.indexOf(this.stepSelected());
            $('#wizard').ntsWizard("goto", index);
        }
    }
}