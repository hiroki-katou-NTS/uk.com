
module qmm019.f.attendItem.viewmodel {
    export class ScreenModel {
        itemDtoSelected: KnockoutObservable<qmm019.f.viewmodel.ItemDto> = ko.observable(null);
        checkUseHighError: KnockoutObservable<boolean> = ko.observable(false);
        checkUseLowError: KnockoutObservable<boolean> = ko.observable(false);
        checkUseHighAlam: KnockoutObservable<boolean> = ko.observable(false);
        checkUseLowAlam: KnockoutObservable<boolean> = ko.observable(false);
        itemRegInfo: KnockoutObservable<service.model.ItemRegInfo> = ko.observable(null);
        itemRegExpand: KnockoutObservable<boolean> = ko.observable(false);
        timeEditorOption: any;
        constructor() {
            let self = this;

            self.timeEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TimeEditorOption({
                inputFormat: 'time',
                placeholder: "Enter a valid HH:mm",
                width: "",
                textalign: "left"
            }));
            self.itemDtoSelected.subscribe(function(NewItem) {
                self.checkUseHighError(NewItem.checkUseHighError());
                self.checkUseLowError(NewItem.checkUseLowError());
                self.checkUseHighAlam(NewItem.checkUseHighAlam());
                self.checkUseLowAlam(NewItem.checkUseLowAlam());
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
                service.getItemAttendRegInfo(itemDto.itemCode()).done(function(itemRegInfoResult) {
                    if (itemRegInfoResult) {
                        let itemRegInfo = new service.model.ItemRegInfo(itemRegInfoResult);
                        self.itemRegInfo(itemRegInfo);
                    }
                    dfd.resolve();
                });
            }
            return dfd.promise();
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
        changeSubItemData(itemMaster: qmm019.f.service.model.ItemMasterDto) {
            let self = this;
            self.loadLayoutData(itemMaster).done(function(itemDto: qmm019.f.viewmodel.ItemDto) {
                self.setItemDtoSelected(itemDto);
            });
        }
        expandContent() {
            let self = this;
            self.itemRegExpand(!self.itemRegExpand());
        }
        genExpandSymbol() {
            let self = this;
            return self.itemRegExpand() ? '&#8896;' : '&#8897;';
        }
        genTimeText(value) {
            let self = this;
            let timeText;
            if (self.itemRegInfo().itemAttend().itemAtr() == 0) {
                timeText = value;
            } else {
                timeText = Math.floor(value / 60) + ":" + ('0' + (value % 60)).slice(-2);
            }
            return timeText;
        }
    }
}