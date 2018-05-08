module nts.uk.at.view.kal003.a.tab {
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.at.view.kal003.share.model;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class AgreementErrorTab {
        listAgreementError: KnockoutObservableArray<model.AgreeConditionErrorDto>;

        constructor() {
            let self = this;
            self.listAgreementError = ko.observableArray([]);
            self.init();

            $("#fixed-table-agreement-error").ntsFixedTable({ height: 415, width: 490 });
        }

        init(): void {
            let self = this;
            let listAgErr = [];
            service.getAgreementError().done((data) => {
                _.each(data, (x) => {
                    listAgErr.push(new model.AgreeConditionErrorDto({
                        id: x.id,
                        useAtr: x.useAtr ? true : false,
                        period: x.period,
                        errorAlarm: x.errorAlarm,
                        messageDisp: x.messageDisp,
                        name: x.name
                    }));
                });
                self.listAgreementError(listAgErr);
            });
        }
    }
}//end tab



