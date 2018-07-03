module nts.uk.at.view.kfp001.e {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            aggrFrameCode: KnockoutObservable<string>;
            optionalAggrName: KnockoutObservable<string>;
            startDate: KnockoutObservable<string>;
            endDate: KnockoutObservable<string>;
            peopleNo: KnockoutObservable<number>;
            mode : KnockoutObservable<boolean>;
            
            //
            startDateTime : KnockoutObservable<string>;
            endDateTime : KnockoutObservable<string>;
            
            constructor() {
                var self = this;
                self.aggrFrameCode = ko.observable('');
                self.optionalAggrName = ko.observable('');
                self.startDate = ko.observable('');
                self.endDate = ko.observable('');
                self.peopleNo = ko.observable(0);
                self.mode = ko.observable(false);

            }
            start() {

            }

            addData() {
                let self = this;
                var optionalAggr = {
                    aggrFrameCode: self.aggrFrameCode(),
                    optionalAggrName: self.optionalAggrName(),
                    startDate: self.startDate(),
                    endDate: self.endDate(),
                    peopleNo: self.peopleNo()
                }

                service.findExecAggr(self.aggrFrameCode()).done(function(data) {
                    
                    
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                })
            }

        }
    }
}
