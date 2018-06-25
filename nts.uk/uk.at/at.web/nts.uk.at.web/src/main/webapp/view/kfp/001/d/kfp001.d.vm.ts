module nts.uk.at.view.kfp001.d {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            aggrFrameCode: KnockoutObservable<string>;
            optionalAggrName: KnockoutObservable<string>;
            startDate: KnockoutObservable<string>;
            endDate: KnockoutObservable<string>;
            peopleNo: KnockoutObservable<number>;

            constructor() {
                var self = this;
                self.aggrFrameCode = ko.observable('');
                self.optionalAggrName = ko.observable('');
                self.startDate = ko.observable('');
                self.endDate = ko.observable('');
                self.peopleNo = ko.observable(0);

            }
            start() {
                let self = this;
                let dataA =[]; 
                dataA = nts.uk.ui.windows.getShared("KFP001_DATA");
                self.aggrFrameCode(dataA.aggrFrameCode);
                self.optionalAggrName(dataA.optionalAggrName);
                self.startDate(dataA.startDate);
                self.endDate(dataA.endDate);
                self.peopleNo(dataA.peopleNo);
                empList = nts.uk.ui.windows.getShared("KFP001_DATAC");
            }

            addData() {
                let self = this;
                var optionalAggr = {
                    aggrFrameCode: self.aggrFrameCode,
                    optionalAggrName: self.optionalAggrName,
                    startDate: self.startDate,
                    endDate: self.endDate
                }

                service.addOptionalAggrPeriod(optionalAggr).done(function(errors) {

                })
            }

            opendScreenF() {
                nts.uk.request.jump("/view/kfp/001/b/index.xhtml");
            }

            opendScreenJC() {
                let self = this;

                nts.uk.request.jump("/view/kfp/001/c/index.xhtml");
            }

            opendScreenBC() {
                let self = this;

                nts.uk.request.jump("/view/kfp/001/d/index.xhtml");
            }

            prevC() {
                $("#wizard").ntsWizard("prev").done(function() {
                });
            }

        }



    }
}
