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
        category: KnockoutObservable<number>;
        checkUseAtr: KnockoutObservable<boolean> = ko.observable(false);
        constructor(category: number, listAgreementHour?: Array<model.AgreeCondOt>) {
            let self = this;
            self.category = ko.observable(category);
            self.currentRowSelected.subscribe((data) => {
                $("#fixed-table-agreement-hour tr").removeClass("ui-state-active");
                $("#fixed-table-agreement-hour tr[data-id='" + data + "']").addClass("ui-state-active");
            });
            if (listAgreementHour) {
                self.listAgreementHour(listAgreementHour);
            }
            
            self.checkUseAtr = ko.pureComputed({
                read: function() {
                    if (self.listAgreementHour().length > 0) {
                        return true;
                    } else {
                        return false;
                    }
                },
                owner: self
            });

            $("#fixed-table-agreement-hour").ntsFixedTable({ height: 285, width: 500 });
        }

        createNewLine(): void {
            let self = this;
            if (self.listAgreementHour == null || self.listAgreementHour == undefined) {
                self.listAgreementHour([]);
            }

            let lengthListAgreementHour: number = self.listAgreementHour().length;
            if (lengthListAgreementHour >= 10) {
                dialog.alertError({ messageId: "Msg_1242" });
                return;
            }
            let defaultAgreementHour = shareutils.getDefaultAgreementHour();
            if (lengthListAgreementHour == 0) {
                defaultAgreementHour.no = 1;
            } else {
                defaultAgreementHour.no = self.listAgreementHour()[lengthListAgreementHour - 1].no + 1;
            }

            self.listAgreementHour.push(defaultAgreementHour);

            self.currentRowSelected(self.listAgreementHour()[lengthListAgreementHour].no);
            $("#fixed-table-agreement-hour tr")[lengthListAgreementHour].scrollIntoView();
        }

        deleteLine(): void {
            let self = this;
            if (self.listAgreementHour().length <= 0 || self.currentRowSelected() == null) {
                return;
            }
            let indexRowSelected = _.findIndex(self.listAgreementHour(), function(x) {
                return x.no == self.currentRowSelected();
            });

            _.pullAt(self.listAgreementHour(), [indexRowSelected]);
            self.listAgreementHour(self.listAgreementHour());
            if (self.listAgreementHour().length == 0) {
                self.currentRowSelected(null);
                return;
            }
            if (indexRowSelected == self.listAgreementHour().length) {
                self.currentRowSelected(self.listAgreementHour()[indexRowSelected - 1].no);
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



