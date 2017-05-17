module qmm019.f.deductItem.viewmodel {
    export class ScreenModel {
        itemMasterDto: KnockoutObservable<qmm019.f.service.model.ItemMasterDto> = ko.observable(null);
        itemDtoSelected: KnockoutObservable<qmm019.f.viewmodel.ItemDto> = ko.observable(null);
        checkUseHighError: KnockoutObservable<boolean> = ko.observable(false);
        checkUseLowError: KnockoutObservable<boolean> = ko.observable(false);
        checkUseHighAlam: KnockoutObservable<boolean> = ko.observable(false);
        checkUseLowAlam: KnockoutObservable<boolean> = ko.observable(false);
        constructor(param) {
            let self = this;
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
        }
        loadLayoutData(itemMaster: qmm019.f.service.model.ItemMasterDto): JQueryPromise<qmm019.f.viewmodel.ItemDto> {
            let self = this;
            let dfd = $.Deferred<qmm019.f.viewmodel.ItemDto>();
            service.getSalaryItem(itemMaster.itemCode).done(function(itemDeduct: service.model.DeductItemModel) {
                dfd.resolve(self.createNewItemDto(itemMaster, itemDeduct));
            });
            return dfd.promise();
        }
        createNewItemDto(itemMaster: qmm019.f.service.model.ItemMasterDto, itemDeduct: service.model.DeductItemModel): qmm019.f.viewmodel.ItemDto {
            let ItemDto = new qmm019.f.viewmodel.ItemDto(itemMaster.itemCode, itemMaster.categoryAtr, itemMaster.itemAbName, itemDeduct.errRangeHighAtr == 1, itemDeduct.errRangeHigh, itemDeduct.errRangeLowAtr == 1, itemDeduct.errRangeLow,
                itemDeduct.alRangeHighAtr == 1, itemDeduct.alRangeHigh, itemDeduct.alRangeLowAtr == 1, itemDeduct.alRangeLow, 0, 0, 0, 0, '000', 0, itemDeduct.deductAtr);
            return ItemDto;
        }
        setItemDtoSelected(itemDto: qmm019.f.viewmodel.ItemDto) {
            let self = this;
            self.itemDtoSelected(itemDto);
        }
    }
}