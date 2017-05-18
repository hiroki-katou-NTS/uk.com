module qmm019.f.attendItem.viewmodel {
    export class ScreenModel {
        itemMasterDto: KnockoutObservable<qmm019.f.service.model.ItemMasterDto> = ko.observable(null);
        itemDtoSelected: KnockoutObservable<qmm019.f.viewmodel.ItemDto> = ko.observable(null);
        checkUseHighError: KnockoutObservable<boolean> = ko.observable(false);
        checkUseLowError: KnockoutObservable<boolean> = ko.observable(false);
        checkUseHighAlam: KnockoutObservable<boolean> = ko.observable(false);
        checkUseLowAlam: KnockoutObservable<boolean> = ko.observable(false);
        timeEditorOption: any;
        constructor() {
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
            self.timeEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TimeEditorOption({
                inputFormat: 'time',
                placeholder: "Enter a valid HH:mm",
                width: "",
                textalign: "left"
            }));
        }
        loadLayoutData(itemMaster: qmm019.f.service.model.ItemMasterDto): JQueryPromise<qmm019.f.viewmodel.ItemDto> {
            let self = this;
            let dfd = $.Deferred<qmm019.f.viewmodel.ItemDto>();
            service.getAttendItem(itemMaster.itemCode).done(function(itemAttend: service.model.AttendItemModel) {
                dfd.resolve(self.createNewItemDto(itemMaster, itemAttend));
            });
            return dfd.promise();
        }
        createNewItemDto(itemMaster: qmm019.f.service.model.ItemMasterDto, itemAttend: service.model.AttendItemModel): qmm019.f.viewmodel.ItemDto {
            let self = this;
            let ItemDto;
            if (itemAttend) {
                ItemDto = new qmm019.f.viewmodel.ItemDto(itemMaster.itemCode, itemMaster.categoryAtr, itemMaster.itemAbName, itemAttend.errRangeHighAtr == 1, itemAttend.errRangeHigh, itemAttend.errRangeLowAtr == 1, itemAttend.errRangeLow,
                    itemAttend.alRangeHighAtr == 1, itemAttend.alRangeHigh, itemAttend.alRangeLowAtr == 1, itemAttend.alRangeLow, 0, 0, 0, 0, '00', 0, itemAttend.avePayAtr);
            } else {
                ItemDto = self.createDefaltItemDto(itemMaster);
            }
            return ItemDto;
        }
        setItemDtoSelected(itemDto: qmm019.f.viewmodel.ItemDto, itemMasterDto?) {
            let self = this;
            self.itemDtoSelected(itemDto);
        }
        createDefaltItemDto(itemMaster: qmm019.f.service.model.ItemMasterDto): qmm019.f.viewmodel.ItemDto {
            return new qmm019.f.viewmodel.ItemDto(itemMaster.itemCode, itemMaster.categoryAtr, itemMaster.itemAbName, false, 0, false, 0,
                false, 0, false, 0, 0, 0, 0, 0, '00', 0, 0);
        }
        getItemDto(): qmm019.f.viewmodel.ItemDto {
            let self = this;
            let ItemDto = new qmm019.f.viewmodel.ItemDto(self.itemDtoSelected().itemCode(), self.itemDtoSelected().categoryAtr(), self.itemDtoSelected().itemAbName(), self.checkUseHighError(), self.itemDtoSelected().errRangeHigh(), self.checkUseLowError(), self.itemDtoSelected().errRangeLow(),
                self.checkUseHighAlam(), self.itemDtoSelected().alamRangeHigh(), self.checkUseLowAlam(), self.itemDtoSelected().alamRangeLow(), 0, 0, 0, 0, '00', 0, self.itemDtoSelected().taxAtr());
            return ItemDto;
        }
        setMasterDto(itemMasterDto) {
            let self = this;
            self.itemMasterDto(itemMasterDto);
        }
    }
}