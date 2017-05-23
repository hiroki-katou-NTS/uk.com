module qmm019.f.articleItem.viewmodel {
    export class ScreenModel {
        itemDtoSelected: KnockoutObservable<qmm019.f.viewmodel.ItemDto> = ko.observable(null);
        distributeWayselected: KnockoutObservable<number> = ko.observable(0);
        itemRegInfo: KnockoutObservable<service.model.ItemRegInfo> = ko.observable(null);
        itemRegExpand: KnockoutObservable<boolean> = ko.observable(false);
        constructor() {
            let self = this;

            self.itemDtoSelected.subscribe(function(NewItem) {
                self.distributeWayselected(NewItem.distributeWay());
                self.loadItemRegInfo(NewItem);
            });
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
        loadItemRegInfo(itemDto: qmm019.f.viewmodel.ItemDto): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();
            if (self.itemRegExpand()) {
                service.getItemArticleRegInfo(itemDto.categoryAtr(), itemDto.itemCode()).done(function(itemRegInfoResult) {
                    if (itemRegInfoResult) {
                        let itemRegInfo = new service.model.ItemRegInfo(itemRegInfoResult);
                        self.itemRegInfo(itemRegInfo);
                    }
                    dfd.resolve();
                });
            }
            return dfd.promise();
        }
        createNewItemDto(itemMaster: qmm019.f.service.model.ItemMasterDto): qmm019.f.viewmodel.ItemDto {
            let ItemDto;
            let self = this;
            ItemDto = self.createDefaltItemDto(itemMaster);
            return ItemDto;
        }
        createDefaltItemDto(itemMaster: qmm019.f.service.model.ItemMasterDto): qmm019.f.viewmodel.ItemDto {
            return new qmm019.f.viewmodel.ItemDto(itemMaster.itemCode, itemMaster.categoryAtr, itemMaster.itemAbName, false, 0, false, 0,
                false, 0, false, 0, 0, 0, 0, 0, '00', 0, 0);
        }
        setItemDtoSelected(itemDto: qmm019.f.viewmodel.ItemDto, itemMasterDto?) {
            let self = this;
            self.itemDtoSelected(itemDto);
        }

        getItemDto(): qmm019.f.viewmodel.ItemDto {
            let self = this;
            let ItemDto = new qmm019.f.viewmodel.ItemDto(self.itemDtoSelected().itemCode(), self.itemDtoSelected().categoryAtr(), self.itemDtoSelected().itemAbName(), false, self.itemDtoSelected().errRangeHigh(), false, self.itemDtoSelected().errRangeLow(),
                false, self.itemDtoSelected().alamRangeHigh(), false, self.itemDtoSelected().alamRangeLow(), 0, 0, 0, 0, '00', 0, 0);
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