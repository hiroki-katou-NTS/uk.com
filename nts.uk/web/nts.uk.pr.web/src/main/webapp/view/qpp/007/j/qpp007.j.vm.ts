module nts.uk.pr.view.qpp007.j {
    import SalaryAggregateItemInDto = service.model.SalaryAggregateItemInDto;
    import SalaryAggregateItemFindDto = service.model.SalaryAggregateItemFindDto;
    import SalaryAggregateItemSaveDto = service.model.SalaryAggregateItemSaveDto;
    import SalaryItemDto = service.model.SalaryItemDto;
    import TaxDivision = nts.uk.pr.view.qpp007.c.viewmodel.TaxDivision;

    export module viewmodel {

        export class ScreenModel {
            taxDivisionTab: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            aggregateItemTab: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedDivision: KnockoutObservable<string>;
            selectedAggregateItem: KnockoutObservable<string>;
            salaryAggregateItemModel: KnockoutObservable<SalaryAggregateItemModel>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;

            constructor() {
                var self = this;
                self.taxDivisionTab = ko.observableArray<nts.uk.ui.NtsTabPanelModel>([
                    { id: TaxDivision.PAYMENT, title: '支給集計', content: '#tab-payment', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: TaxDivision.DEDUCTION, title: '控除集計', content: '#tab-deduction', enable: ko.observable(true), visible: ko.observable(true) },
                ]);
                self.aggregateItemTab = ko.observableArray<nts.uk.ui.NtsTabPanelModel>([
                    { id: '001', title: '集計項目1', content: '#aggregate1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: '002', title: '集計項目2', content: '#aggregate2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: '003', title: '集計項目3', content: '#aggregate3', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: '004', title: '集計項目4', content: '#aggregate4', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: '005', title: '集計項目5', content: '#aggregate5', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: '006', title: '集計項目6', content: '#aggregate6', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: '007', title: '集計項目7', content: '#aggregate7', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: '008', title: '集計項目8', content: '#aggregate8', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: '009', title: '集計項目9', content: '#aggregate9', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: '010', title: '集計項目10', content: '#aggregate10', enable: ko.observable(true), visible: ko.observable(true) }
                ]);

                self.selectedDivision = ko.observable(TaxDivision.PAYMENT);
                self.selectedAggregateItem = ko.observable('002');
                self.salaryAggregateItemModel = ko.observable(new SalaryAggregateItemModel());
                self.columns = ko.observableArray<nts.uk.ui.NtsGridListColumn>([
                    { headerText: 'コード', key: 'salaryItemCode', width: 50 },
                    { headerText: '名称', key: 'salaryItemName', width: 150 }
                ]);
                self.selectedAggregateItem.subscribe(function(selectedAggregateItem: string) {
                    self.onShowDataChange(self.selectedDivision(), selectedAggregateItem);
                });
                self.selectedDivision.subscribe(function(selectedDivision: string) {
                    self.onShowDataChange(selectedDivision, self.selectedAggregateItem());
                });
            }
            /**
             * Start page.
             */
            public startPage(): JQueryPromise<any> {
                var self = this;
                return self.initData();
            }

            public initData(): JQueryPromise<any> {
                var self = this;
                var salaryAggregateItemInDto: SalaryAggregateItemInDto;
                salaryAggregateItemInDto = new SalaryAggregateItemInDto();
                salaryAggregateItemInDto.taxDivision = 0;
                salaryAggregateItemInDto.aggregateItemCode = self.selectedAggregateItem();
                return self.showDataModel(salaryAggregateItemInDto);
            }

            public onShowDataChange(selectedDivision: string, selectedAggregateItem: string) {
                var self = this;
                var salaryAggregateItemInDto: SalaryAggregateItemInDto;
                salaryAggregateItemInDto = new SalaryAggregateItemInDto();
                if (selectedDivision === TaxDivision.PAYMENT) {
                    salaryAggregateItemInDto.taxDivision = 0;
                }
                else {
                    salaryAggregateItemInDto.taxDivision = 1;
                }
                salaryAggregateItemInDto.aggregateItemCode = selectedAggregateItem;
                self.showDataModel(salaryAggregateItemInDto);
            }

            public showDataModel(salaryAggregateItemInDto: SalaryAggregateItemInDto): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findSalaryAggregateItem(salaryAggregateItemInDto).done(data => {
                    self.salaryAggregateItemModel().convertDtoToData(data);
                    var fullItemCodes: SalaryItemDto[];
                    fullItemCodes = [];
                    for (var i = 1; i <= 20; i++) {
                        var salaryItemDto: SalaryItemDto = new SalaryItemDto();
                        salaryItemDto.salaryItemCode = '' + i;
                        salaryItemDto.salaryItemName = '基本給 ' + i;
                        fullItemCodes.push(salaryItemDto);
                    }
                    self.salaryAggregateItemModel().setFullItemCode(fullItemCodes);
                    dfd.resolve(self);
                }).fail(function(error: any) {
                });
                return dfd.promise();
            }
            public closeDialogBtnClicked() {
                nts.uk.ui.windows.close();
            }
            private saveSalaryAggregateItem() {
                var self = this;
                if (self.selectedDivision() === TaxDivision.PAYMENT) {
                    self.convertModelToDto(0);
                }
                else {
                    self.convertModelToDto(1);
                }
                //reload///
            }

            private convertModelToDto(taxDivision: number): SalaryAggregateItemSaveDto {
                var self = this;
                var salaryAggregateItemSaveDto: SalaryAggregateItemSaveDto;
                salaryAggregateItemSaveDto = new SalaryAggregateItemSaveDto();
                salaryAggregateItemSaveDto.salaryAggregateItemCode =
                    self.salaryAggregateItemModel().salaryAggregateItemCode();
                salaryAggregateItemSaveDto.salaryAggregateItemName =
                    self.salaryAggregateItemModel().salaryAggregateItemName();
                salaryAggregateItemSaveDto.subItemCodes = [];
                for (var itemModel of self.salaryAggregateItemModel().subItemCodes()) {
                    salaryAggregateItemSaveDto.subItemCodes.push(itemModel);
                }
                salaryAggregateItemSaveDto.taxDivision = taxDivision;
                salaryAggregateItemSaveDto.categoryCode = self.selectedAggregateItem();

                service.saveSalaryAggregateItem(salaryAggregateItemSaveDto).done(function() {
                    //reload
                }).fail(function() {
                    //reload
                });
                return salaryAggregateItemSaveDto;
            }

        }
        export class SalaryItemModel {

            salaryItemCode: string;
            salaryItemName: string;

            //convert dto find => model
            convertDtoToData(salaryItemDto: SalaryItemDto) {
                this.salaryItemCode = salaryItemDto.salaryItemCode;
                this.salaryItemName = salaryItemDto.salaryItemName;
            }
        }
        export class SalaryAggregateItemModel {

            salaryAggregateItemCode: KnockoutObservable<string>;
            salaryAggregateItemName: KnockoutObservable<string>;
            subItemCodes: KnockoutObservableArray<SalaryItemModel>;
            fullItemCodes: KnockoutObservableArray<SalaryItemModel>;

            constructor() {
                this.salaryAggregateItemCode = ko.observable('');
                this.salaryAggregateItemName = ko.observable('');
                this.subItemCodes = ko.observableArray([]);
                this.fullItemCodes = ko.observableArray([]);
            }

            convertDtoToData(salaryAggregateItemFindDto: SalaryAggregateItemFindDto) {
                if (this.salaryAggregateItemCode) {
                    this.salaryAggregateItemCode(salaryAggregateItemFindDto.salaryAggregateItemCode);
                } else {
                    this.salaryAggregateItemCode = ko.observable(salaryAggregateItemFindDto.salaryAggregateItemCode);
                }
                if (this.salaryAggregateItemName) {
                    this.salaryAggregateItemName(salaryAggregateItemFindDto.salaryAggregateItemName);
                } else {
                    this.salaryAggregateItemName = ko.observable(salaryAggregateItemFindDto.salaryAggregateItemName);
                }
                if (salaryAggregateItemFindDto.subItemCodes) {
                    this.subItemCodes([]);
                    var subItemCodes: SalaryItemModel[];
                    subItemCodes = [];
                    for (var item of salaryAggregateItemFindDto.subItemCodes) {
                        var salaryItemModel: SalaryItemModel;
                        salaryItemModel = new SalaryItemModel();
                        salaryItemModel.convertDtoToData(item);
                        subItemCodes.push(salaryItemModel);
                    }
                    this.subItemCodes(subItemCodes);
                } else {
                    this.subItemCodes([]);
                }
            }

            setFullItemCode(fullItemCodes: SalaryItemDto[]) {
                if (fullItemCodes && fullItemCodes.length > 0) {
                    this.fullItemCodes([]);
                    var fullItemCodesModel: SalaryItemModel[];
                    fullItemCodesModel = [];
                    for (var item of fullItemCodes) {
                        var check: number;
                        check = 1;
                        for (var itemSub of this.subItemCodes()) {
                            if (itemSub.salaryItemCode == item.salaryItemCode) {
                                check = 0;
                                break;
                            }
                        }
                        if (check == 1) {
                            var salaryItemModel: SalaryItemModel;
                            salaryItemModel = new SalaryItemModel();
                            salaryItemModel.convertDtoToData(item);
                            fullItemCodesModel.push(salaryItemModel);
                        }
                    }
                    this.fullItemCodes(fullItemCodesModel);
                } else {
                    this.fullItemCodes([]);
                }
            }
        }
    }
}