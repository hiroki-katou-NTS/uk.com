module nts.uk.at.view.kal003.a.tab {
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.at.view.kal003.share.model;
    import dialog = nts.uk.ui.dialog;

    export class AgreementHourTab {
        listAgreementHour: KnockoutObservableArray<model.AgreeCondOt>;

        constructor() {
            let self = this;
            let list = [];
            self.listAgreementHour = ko.observableArray([]);
            self.init();

            $("#fixed-table-agreement-hour").ntsFixedTable({ height: 310, width: 500 });
        } 

        init(): void {
            let self = this;
            let listAgHour = [];
            service.getAgreementHour().done((data) => {
                _.each(data, (x) => {
                    listAgHour.push(new model.AgreeCondOt({
                        id: x.id,
                        no: x.no,
                        ot36: x.ot36,
                        excessNum: x.excessNum,
                        messageDisp: x.messageDisp
                    }));
                });

                self.listAgreementHour(listAgHour);
            });
        }

        createNewLine(): void {
            let self = this;
            if (self.listAgreementHour == null || self.listAgreementHour == undefined) {
                self.listAgreementHour([]);
            }
            if (self.listAgreementHour().length == 50) {
                dialog.alertError({ messageId: "Msg_833" });
                return;
            }
        }

        deleteLine(): void {

        }
    }

}//end tab



