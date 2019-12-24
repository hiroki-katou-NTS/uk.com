module nts.uk.com.view.qmm012.k.viewmodel {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;

    export class ScreenModel {
        listTaxExemptLimit: KnockoutObservableArray<TaxExemptLimit> = ko.observableArray([]);
        selectedTaxExemptLimit: KnockoutObservable<TaxExemptLimit> = ko.observable(new TaxExemptLimit('', '', 1));
        currentCode: KnockoutObservable<String> = ko.observable('');
        constructor() {
            let self = this;

            self.currentCode.subscribe(taxFreeamountCode => {
                let getTaxExemptLimit = _.find(self.listTaxExemptLimit(), function(x) { return x.taxFreeamountCode == taxFreeamountCode; });
                self.selectedTaxExemptLimit(getTaxExemptLimit);
            });
            service.getAllTaxAmountByCompanyId().done(function(data: Array<TaxExemptLimit>) {
                if (data) {
                    data = _.sortBy(data, ["taxFreeamountCode"]);
                    self.listTaxExemptLimit(data);
                    self.currentCode(self.listTaxExemptLimit()[0].taxFreeamountCode);
                }
            }).fail(function(error) {
                alertError(error);
            }).always(() => {
            });
        }

        setTaxExemption() {
            let self = this;
            if (self.selectedTaxExemptLimit().length == 0) {
                setShared("QMM012_TaxExemptLimit", self.selectedTaxExemptLimit(null));
            } else {
                setShared("QMM012_TaxExemptLimit", self.selectedTaxExemptLimit());
            }
            nts.uk.ui.windows.close();
        }

        cancelSetting() {
            nts.uk.ui.windows.close();
        }
    }
    class TaxExemptLimit {
        taxFreeamountCode: string;
        taxExemptionName: string;
        taxExemption: number;

        constructor(taxFreeamountCode: string, taxExemptionName: string, taxExemption: number) {
            this.taxFreeamountCode = taxFreeamountCode;
            this.taxExemptionName = taxExemptionName;
            this.taxExemption = taxExemption;
        }
    }
}