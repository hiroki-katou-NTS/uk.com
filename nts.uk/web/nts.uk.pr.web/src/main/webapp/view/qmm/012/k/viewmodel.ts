module qmm012.k.viewmodel {
    export class ScreenModel {
        GridColumns_K_001: any;
        GridlistItems_K_001: KnockoutObservableArray<qmm023.a.service.model.CommuteNoTaxLimitDto> = ko.observableArray([]);
        GridlistCurrentCode_K_001: KnockoutObservable<string> = ko.observable('');
        GridlistCurrentItem: KnockoutObservable<qmm023.a.service.model.CommuteNoTaxLimitDto> = ko.observable(null);
        constructor() {
            let self = this;
            self.GridColumns_K_001 = ko.observableArray([
                { headerText: 'コード', prop: 'commuNoTaxLimitCode', width: 40 },
                { headerText: '名称', prop: 'commuNoTaxLimitName', width: 110 },
                { headerText: '限度額', prop: 'commuNoTaxLimitValue', width: 110 }
            ]);
            self.LoadData();
            self.GridlistCurrentCode_K_001.subscribe(function(newValue) {
                var item = _.find(self.GridlistItems_K_001(), function(ItemModel: qmm023.a.service.model.CommuteNoTaxLimitDto) {
                    return ItemModel.commuNoTaxLimitCode == newValue;
                });
                self.GridlistCurrentItem(item);
            })
        }

        LoadData() {
            let self = this;
            service.getCommutelimitsByCompanyCode().done(function(CommuteNoTaxLimits: Array<qmm023.a.service.model.CommuteNoTaxLimitDto>) {
                self.GridlistItems_K_001(CommuteNoTaxLimits);
            }).fail(function(res) {
                alert(res);
            });
        }
        SubmitDialog() {
            let self = this;
            nts.uk.ui.windows.setShared('CommuteNoTaxLimitDto', self.GridlistCurrentItem());
            nts.uk.ui.windows.close();
        }
        CloseDialog() {
            nts.uk.ui.windows.close();
        }
    }


}