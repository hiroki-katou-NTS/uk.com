module nts.uk.at.view.kal003.a.tab {
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.at.view.kal003.share.model;
    import dialog = nts.uk.ui.dialog;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class AgreementHourTab {
        listAgreementHour: KnockoutObservableArray<model.AgreeCondOt> = ko.observableArray([]);
        currentRowSelected: KnockoutObservable<any> = ko.observable(null);

        constructor() {
            let self = this;
            self.init();
            self.currentRowSelected.subscribe((data) => {
                $("#fixed-table-agreement-hour tr").removeClass("ui-state-active");
                $("#fixed-table-agreement-hour tr[data-id='" + data + "']").addClass("ui-state-active");
            });

            $("#fixed-table-agreement-hour").ntsFixedTable({ height: 285, width: 500 });
        }

        init(): void {
            let self = this;
            let listAgHour = [];
//            service.getAgreementHour().done((data) => {
//                _.each(data, (x) => {
//                    listAgHour.push(new model.AgreeCondOt({
//                        id: x.id,
//                        no: x.no,
//                        ot36: x.ot36,
//                        excessNum: x.excessNum,
//                        messageDisp: x.messageDisp
//                    }));
//                });
//
//                self.listAgreementHour(listAgHour);
//            });
        }

        createNewLine(): void {
            let self = this;
            if (self.listAgreementHour == null || self.listAgreementHour == undefined) {
                self.listAgreementHour([]);
            }
            if (self.listAgreementHour().length >= 10) {
                dialog.alertError({ messageId: "Msg_1242" });
                return;
            }
            let defaultAgreementHour = shareutils.getDefaultAgreementHour();
            defaultAgreementHour.no = self.listAgreementHour().length + 1;

            self.listAgreementHour.push(defaultAgreementHour);
            self.currentRowSelected(self.listAgreementHour().length);
            $("#fixed-table-agreement-hour tr")[self.listAgreementHour().length - 1].scrollIntoView();
        }

        deleteLine(): void {
            let self = this;
            if (self.listAgreementHour().length <= 0 || self.currentRowSelected() == null) {
                return;
            }
            let indexRowSelected = self.listAgreementHour().findIndex(x => self.currentRowSelected() == x.no);
            _.pullAt(self.listAgreementHour(), [indexRowSelected]);
            self.listAgreementHour(self.listAgreementHour());
            if (self.listAgreementHour().length == 0) {
                self.currentRowSelected(null);
                return;
            }
            if (indexRowSelected == self.listAgreementHour().length) {
                self.currentRowSelected(indexRowSelected);
            } else {
                self.currentRowSelected(self.listAgreementHour()[indexRowSelected].no);
            }

            $("#fixed-table-agreement-hour tr.ui-state-active")[0].scrollIntoView();
        }
    }

    $(function() {
        $("#fixed-table-agreement-hour").on("click", "tr", function() {
            var id = $(this).attr("data-id");
            nts.uk.ui._viewModel.content.tabAgreementHour.currentRowSelected(id);
        })
    })


}//end tab



