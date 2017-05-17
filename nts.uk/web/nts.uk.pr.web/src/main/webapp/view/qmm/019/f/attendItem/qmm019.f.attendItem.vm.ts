module qmm019.f.attendItem.viewmodel {
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
            service.getSalaryItem(itemMaster.itemCode).done(function(itemAttend: service.model.AttendItemModel) {
                dfd.resolve(self.createNewItemDto(itemMaster, itemAttend));
            });
            return dfd.promise();
        }
        createNewItemDto(itemMaster: qmm019.f.service.model.ItemMasterDto, itemAttend: service.model.AttendItemModel): qmm019.f.viewmodel.ItemDto {
            let ItemDto = new qmm019.f.viewmodel.ItemDto(itemMaster.itemCode, itemMaster.categoryAtr, itemMaster.itemAbName, itemAttend.errRangeHighAtr == 1, itemAttend.errRangeHigh, itemAttend.errRangeLowAtr == 1, itemAttend.errRangeLow,
                itemAttend.alRangeHighAtr == 1, itemAttend.alRangeHigh, itemAttend.alRangeLowAtr == 1, itemAttend.alRangeLow, 0, 0, 0, 0, '000', 0, itemAttend.avePayAtr);
            return ItemDto;
        }
        setItemDtoSelected(itemDto: qmm019.f.viewmodel.ItemDto) {
            let self = this;
            self.itemDtoSelected(itemDto);
        }
    }
}