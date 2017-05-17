module qmm019.f.salaryItem.viewmodel {
    export class ScreenModel {
        itemMasterDto: KnockoutObservable<qmm019.f.service.model.ItemMasterDto> = ko.observable(null);
        itemDtoSelected: KnockoutObservable<qmm019.f.viewmodel.ItemDto> = ko.observable(null);
        checkUseHighError: KnockoutObservable<boolean> = ko.observable(false);
        checkUseLowError: KnockoutObservable<boolean> = ko.observable(false);
        checkUseHighAlam: KnockoutObservable<boolean> = ko.observable(false);
        checkUseLowAlam: KnockoutObservable<boolean> = ko.observable(false);
        comboBoxCalcMethod: KnockoutObservable<qmm019.f.viewmodel.ComboBox>;
        comboBoxSumScopeAtr: KnockoutObservable<qmm019.f.viewmodel.ComboBox>;
        comboBoxDistributeWay: KnockoutObservable<qmm019.f.viewmodel.ComboBox>;
        wageCode: KnockoutObservable<string> = ko.observable('');
        wageName: KnockoutObservable<string> = ko.observable('');
        switchButton: KnockoutObservable<qmm019.f.viewmodel.SwitchButton>;
        constructor(param) {
            let self = this;
            var itemListSumScopeAtr = ko.observableArray([
                new qmm019.f.viewmodel.ItemModel(0, '合計対象外'),
                new qmm019.f.viewmodel.ItemModel(1, '合計対象内'),
                new qmm019.f.viewmodel.ItemModel(2, '合計対象外（現物）'),
                new qmm019.f.viewmodel.ItemModel(3, '合計対象内（現物）')
            ]);
            var itemListCalcMethod = ko.observableArray([
                new qmm019.f.viewmodel.ItemModel(0, '手入力'),
                new qmm019.f.viewmodel.ItemModel(1, '個人情報'),
                new qmm019.f.viewmodel.ItemModel(2, '計算式'),
                new qmm019.f.viewmodel.ItemModel(3, '賃金テーブル'),
                new qmm019.f.viewmodel.ItemModel(4, '共通金額'),
                new qmm019.f.viewmodel.ItemModel(9, 'システム計算')
            ]);
            var itemListDistributeWay = ko.observableArray([
                new qmm019.f.viewmodel.ItemModel(0, '割合で計算'),
                new qmm019.f.viewmodel.ItemModel(1, '日数控除'),
                new qmm019.f.viewmodel.ItemModel(2, '計算式')
            ]);
            self.itemMasterDto.subscribe(function(NewItem) {
                self.loadLayoutData(NewItem).done(function(itemDto: qmm019.f.viewmodel.ItemDto) {
                    self.setItemDtoSelected(itemDto);
                });
            });
            self.itemDtoSelected.subscribe(function(NewItem) {
                self.checkUseHighError(NewItem.checkUseHighError());
                self.checkUseLowError(NewItem.checkUseLowError());
                self.checkUseHighAlam(NewItem.checkUseHighAlam());
                self.checkUseLowAlam(NewItem.checkUseLowAlam());
            });
            //計算方法
            self.comboBoxCalcMethod = ko.observable(new qmm019.f.viewmodel.ComboBox(itemListCalcMethod));
            //内訳区分
            //「合計対象内（現物）」と「合計対象外（現物）」は項目区分が「支給項目」の場合のみ表示
            self.comboBoxSumScopeAtr = ko.observable(new qmm019.f.viewmodel.ComboBox(itemListSumScopeAtr));
            self.switchButton = ko.observable(new qmm019.f.viewmodel.SwitchButton());
            self.comboBoxDistributeWay = ko.observable(new qmm019.f.viewmodel.ComboBox(itemListDistributeWay));
            self.switchButton().selectedRuleCode.subscribe(function(newValue) {
                //按分方法: 非活性: 項目区分が「支給項目」 or 「控除項目」＆「按分設定」が「按分しない」の場合
                if (newValue == 0) {
                    self.comboBoxDistributeWay().enable(false);
                } else {
                    self.comboBoxDistributeWay().enable(true);
                }
            });
        }
        loadLayoutData(itemMaster: qmm019.f.service.model.ItemMasterDto): JQueryPromise<qmm019.f.viewmodel.ItemDto> {
            let self = this;
            let dfd = $.Deferred<qmm019.f.viewmodel.ItemDto>();
            service.getSalaryItem(itemMaster.itemCode).done(function(itemSalary: service.model.ItemSalaryModel) {
                dfd.resolve(self.createNewItemDto(itemMaster, itemSalary));
            });
            return dfd.promise();
        }
        createNewItemDto(itemMaster: qmm019.f.service.model.ItemMasterDto, itemSalary: service.model.ItemSalaryModel): qmm019.f.viewmodel.ItemDto {
            let ItemDto = new qmm019.f.viewmodel.ItemDto(itemMaster.itemCode, itemMaster.categoryAtr, itemMaster.itemAbName, itemSalary.errRangeHighAtr == 1, itemSalary.errRangeHigh, itemSalary.errRangeLowAtr == 1, itemSalary.errRangeLow,
                itemSalary.alRangeHighAtr == 1, itemSalary.alRangeHigh, itemSalary.alRangeLowAtr == 1, itemSalary.alRangeLow, 0, 0, 0, 0, '000', 0, itemSalary.taxAtr);
            return ItemDto;
        }
        setItemDtoSelected(itemDto: qmm019.f.viewmodel.ItemDto) {
            let self = this;
            self.itemDtoSelected(itemDto);

        }
        checkPersonalInformationReference(): boolean {
            var self = this;
            if (self.comboBoxCalcMethod().selectedCode() == 1) {
                nts.uk.ui.windows.getSelf().setHeight(670);
                return true;
            } else {
                nts.uk.ui.windows.getSelf().setHeight(610);
                return false;
            }
        }
        openHDialog() {
            var self = this;
            nts.uk.ui.windows.setShared('categoryAtr', self.itemDtoSelected().categoryAtr);
            nts.uk.ui.windows.sub.modal('/view/qmm/019/h/index.xhtml', { title: '明細レイアウトの作成＞個人金額コードの選択', dialogClass: 'no-close' }).onClosed(() => {
                self.wageCode(nts.uk.ui.windows.getShared('selectedCode'));
                self.wageName(nts.uk.ui.windows.getShared('selectedName'));
                return this;
            });
        }
    }

}