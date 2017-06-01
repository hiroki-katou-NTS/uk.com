module qmm019.f.salaryItem.viewmodel {
    export class ScreenModel {
        itemDtoSelected: KnockoutObservable<qmm019.f.viewmodel.ItemDto> = ko.observable(null);
        checkUseHighError: KnockoutObservable<boolean> = ko.observable(false);
        checkUseLowError: KnockoutObservable<boolean> = ko.observable(false);
        checkUseHighAlam: KnockoutObservable<boolean> = ko.observable(false);
        checkUseLowAlam: KnockoutObservable<boolean> = ko.observable(false);
        comboBoxCalcMethod: KnockoutObservable<qmm019.f.viewmodel.ComboBox>;
        comboBoxSumScopeAtr: KnockoutObservable<qmm019.f.viewmodel.ComboBox>;
        distributeWaySelectedCode: KnockoutObservable<number> = ko.observable(0);
        wageCode: KnockoutObservable<string> = ko.observable('');
        wageName: KnockoutObservable<string> = ko.observable('');
        switchButton: KnockoutObservable<qmm019.f.viewmodel.SwitchButton>;
        itemListDistributeWay: KnockoutObservableArray<any>;
        comboBoxCommutingClassification: KnockoutObservable<qmm019.f.viewmodel.ComboBox>;
        itemRegInfo: KnockoutObservable<service.model.ItemRegInfo> = ko.observable(null);
        itemRegExpand: KnockoutObservable<boolean> = ko.observable(false);
        breakdownListSelected: KnockoutObservable<any> = ko.observable(null);
        constructor() {
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
            self.itemListDistributeWay = ko.observableArray([
                new qmm019.f.viewmodel.ItemModel(0, '割合で計算'),
                new qmm019.f.viewmodel.ItemModel(1, '日数控除'),
                new qmm019.f.viewmodel.ItemModel(2, '計算式')
            ]);
            var itemListCommutingClassification = ko.observableArray([
                new qmm019.f.viewmodel.ItemModel(0, '交通機関'),
                new qmm019.f.viewmodel.ItemModel(1, '交通用具')
            ]);

            self.itemDtoSelected.subscribe(function(NewItem) {
                self.checkUseHighError(NewItem.checkUseHighError());
                self.checkUseLowError(NewItem.checkUseLowError());
                self.checkUseHighAlam(NewItem.checkUseHighAlam());
                self.checkUseLowAlam(NewItem.checkUseLowAlam());
                self.distributeWaySelectedCode(NewItem.distributeWay());
                self.switchButton().selectedRuleCode(NewItem.distributeSet());
                self.comboBoxCommutingClassification().selectedCode(NewItem.commuteAtr());
                self.loadItemRegInfo(NewItem);
            });
            //計算方法
            self.comboBoxCalcMethod = ko.observable(new qmm019.f.viewmodel.ComboBox(itemListCalcMethod));
            //内訳区分
            //「合計対象内（現物）」と「合計対象外（現物）」は項目区分が「支給項目」の場合のみ表示
            self.comboBoxSumScopeAtr = ko.observable(new qmm019.f.viewmodel.ComboBox(itemListSumScopeAtr));
            self.switchButton = ko.observable(new qmm019.f.viewmodel.SwitchButton());
            self.comboBoxCommutingClassification = ko.observable(new qmm019.f.viewmodel.ComboBox(itemListCommutingClassification));
            self.itemRegExpand.subscribe(function(newValue) {
                if (newValue) {
                    self.loadItemRegInfo(self.itemDtoSelected()).done(function() {
                        $('#content').toggle("slow");
                    });
                } else {
                    $('#content').toggle("slow");
                }
            });


        }
        changeSubItemData(itemMaster: qmm019.f.service.model.ItemMasterDto) {
            let self = this;
            self.loadLayoutData(itemMaster).done(function(itemDto: qmm019.f.viewmodel.ItemDto) {
                self.setItemDtoSelected(itemDto);
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
            let self = this;
            let ItemDto;
            if (itemSalary) {
                ItemDto = new qmm019.f.viewmodel.ItemDto(itemMaster.itemCode, itemMaster.categoryAtr, itemMaster.itemAbName, itemSalary.errRangeHighAtr == 1, itemSalary.errRangeHigh, itemSalary.errRangeLowAtr == 1, itemSalary.errRangeLow,
                    itemSalary.alRangeHighAtr == 1, itemSalary.alRangeHigh, itemSalary.alRangeLowAtr == 1, itemSalary.alRangeLow, 0, 0, 0, 0, '00', 0, itemSalary.taxAtr);
            } else {
                ItemDto = self.createDefaltItemDto(itemMaster);
            }
            return ItemDto;
        }
        createDefaltItemDto(itemMaster: qmm019.f.service.model.ItemMasterDto): qmm019.f.viewmodel.ItemDto {
            return new qmm019.f.viewmodel.ItemDto(itemMaster.itemCode, itemMaster.categoryAtr, itemMaster.itemAbName, false, 0, false, 0,
                false, 0, false, 0, 0, 0, 0, 0, '00', 0, 0);
        }
        setItemDtoSelected(itemDto) {
            let self = this;
            self.itemDtoSelected(itemDto);
        }
        loadItemRegInfo(itemDto: qmm019.f.viewmodel.ItemDto): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();
            if (self.itemRegExpand()) {
                service.getItemSalaryRegInfo(itemDto.itemCode()).done(function(itemRegInfoResult) {
                    if (itemRegInfoResult) {
                        let itemRegInfo = new service.model.ItemRegInfo(itemRegInfoResult);
                        self.itemRegInfo(itemRegInfo);
                    }
                    dfd.resolve();
                });
            }
            return dfd.promise();
        }


        checkPersonalInformationReference(): boolean {
            var self = this;
            if (self.comboBoxCalcMethod().selectedCode() == 1) {
                nts.uk.ui.windows.getSelf().setHeight(670);
                return true;
            } else {
                if ((self.itemDtoSelected().taxAtr() == 3 || self.itemDtoSelected().taxAtr() == 4) && self.comboBoxCalcMethod().selectedCode() == 0) {
                    nts.uk.ui.windows.getSelf().setHeight(670);
                } else {
                    nts.uk.ui.windows.getSelf().setHeight(630);
                }
                return false;
            }
        }
        openHDialog() {
            var self = this;
            nts.uk.ui.windows.setShared('categoryAtr', self.itemDtoSelected().categoryAtr());
            nts.uk.ui.windows.sub.modal('/view/qmm/019/h/index.xhtml', { title: '明細レイアウトの作成＞個人金額コードの選択', dialogClass: 'no-close' }).onClosed(() => {
                self.wageCode(nts.uk.ui.windows.getShared('selectedCode'));
                self.wageName(nts.uk.ui.windows.getShared('selectedName'));
                return this;
            });
        }
        getItemDto(): qmm019.f.viewmodel.ItemDto {
            let self = this;
            let ItemDto = new qmm019.f.viewmodel.ItemDto(self.itemDtoSelected().itemCode(), self.itemDtoSelected().categoryAtr(), self.itemDtoSelected().itemAbName(), self.checkUseHighError(), self.itemDtoSelected().errRangeHigh(), self.checkUseLowError(), self.itemDtoSelected().errRangeLow(),
                self.checkUseHighAlam(), self.itemDtoSelected().alamRangeHigh(), self.checkUseLowAlam(), self.itemDtoSelected().alamRangeLow(), self.comboBoxCommutingClassification().selectedCode(), self.comboBoxCalcMethod().selectedCode(), self.switchButton().selectedRuleCode(), self.distributeWaySelectedCode(), '00', self.comboBoxSumScopeAtr().selectedCode(), self.itemDtoSelected().taxAtr());
            return ItemDto;
        }
        expandContent() {
            let self = this;
            self.itemRegExpand(!self.itemRegExpand());
        }
        genExpandSymbol() {
            let self = this;
            return self.itemRegExpand() ? '&#8896;' : '&#8897;';
        }
    }

}