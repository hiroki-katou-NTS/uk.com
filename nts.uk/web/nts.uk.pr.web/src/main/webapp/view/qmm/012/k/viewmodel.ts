module qmm012.k.viewmodel {
    export class ScreenModel {
        GridColumns_K_001: any;
        GridlistItems_K_001: KnockoutObservableArray<service.model.CommuteNoTaxLimitDto> = ko.observableArray([]);
        GridlistCurrentCode_K_001: KnockoutObservable<string> = ko.observable('');
        constructor() {
            let self = this;
            self.GridColumns_K_001 = ko.observableArray([
                { headerText: 'コード', prop: 'commuNoTaxLimitCode', width: 40 },
                { headerText: '名称', prop: 'commuNoTaxLimitName', width: 110 },
                { headerText: '限度額', prop: 'commuNoTaxLimitValue', width: 110 }
            ]);
            self.LoadData();
        }

        LoadData() {
            let self = this;
            service.getCommutelimitsByCompanyCode().done(function(CommuteNoTaxLimits: Array<service.model.CommuteNoTaxLimitDto>) {
                self.GridlistItems_K_001(CommuteNoTaxLimits);
            }).fail(function(res) {
                alert(res);
            });
        }
        SubmitDialog() {
            let self = this;
            nts.uk.ui.windows.setShared('LimitCode', self.GridlistCurrentCode_K_001());
            nts.uk.ui.windows.close();
        }
        CloseDialog() {
            nts.uk.ui.windows.close();
        }
    }


}