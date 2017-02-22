module nts.uk.pr.view.qmm007.a {
    export module viewmodel {

        import UnitPriceHistoryDto = service.model.UnitPriceHistoryDto;
        import UnitPriceItemDto = service.model.UnitPriceItemDto;
        import UnitPriceHistoryItemDto = service.model.UnitPriceHistoryItemDto;

        export class ScreenModel {
            // UnitPriceHistory Model
            unitPriceHistoryModel: KnockoutObservable<UnitPriceHistoryModel>;

            // Selected ID of UnitPriceHistory
            selectedId: KnockoutObservable<string>;

            // Tree grid
            filteredData: KnockoutObservableArray<any>;
            historyList: KnockoutObservableArray<UnitPriceHistoryNode>;

            // Setting type
            isContractSettingEnabled: KnockoutObservable<boolean>;

            // New mode flag
            isNewMode: KnockoutObservable<boolean>;

            // Nts text editor options
            textEditorOption: any;
            // Switch button data source
            switchButtonDataSource: KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.isNewMode = ko.observable(true);
                let defaultHist = new UnitPriceHistoryDto();
                defaultHist.id = '';
                defaultHist.unitPriceCode = '';
                defaultHist.unitPriceName = '';
                defaultHist.startMonth = 2017;
                //model.endMonth('');
                defaultHist.budget = 0;
                defaultHist.fixPaySettingType = 'Company';
                defaultHist.fixPayAtr = 'NotApply';
                defaultHist.fixPayAtrMonthly = 'NotApply';
                defaultHist.fixPayAtrDayMonth = 'NotApply';
                defaultHist.fixPayAtrDaily = 'NotApply';
                defaultHist.fixPayAtrHourly = 'NotApply';
                defaultHist.memo = '';

                self.unitPriceHistoryModel = ko.observable(new UnitPriceHistoryModel(defaultHist));
                self.historyList = ko.observableArray<UnitPriceHistoryNode>([]);
                self.switchButtonDataSource = ko.observableArray([
                    { code: 'Apply', name: '対象' },
                    { code: 'NotApply', name: '対象外' }
                ]);

                // Tree grid
                self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.historyList(), "childs"));
                self.selectedId = ko.observable('');
                self.selectedId.subscribe(id => {
                    if (id) {
                        self.isNewMode(false);
                        $('.save-error').ntsError('clear');
                        self.loadUnitPriceDetail(id);
                    }
                });

                // Setting type
                self.isContractSettingEnabled = ko.observable(false);
                self.unitPriceHistoryModel().fixPaySettingType.subscribe(val => {
                    val == 'Contract' ? self.isContractSettingEnabled(true) : self.isContractSettingEnabled(false);
                });

                // Nts text editor options
                self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    textalign: "left"
                }));
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.loadUnitPriceHistoryList().done(() => dfd.resolve());
                return dfd.promise();
            }

            /**
             * Go to Add UnitPriceHistory dialog.
             */
            private goToB() {
                nts.uk.ui.windows.setShared('unitPriceHistoryModel', this.unitPriceHistoryModel());
                nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の追加', dialogClass: 'no-close', height: 360, width: 580 });
            }

            /**
             * Go to Edit UnitPriceHistory dialog.
             */
            private goToC() {
                nts.uk.ui.windows.setShared('unitPriceHistoryModel', this.unitPriceHistoryModel());
                nts.uk.ui.windows.sub.modal('/view/qmm/007/c/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の編集', dialogClass: 'no-close', height: 420, width: 580 });
            }

            /**
             * Create or Update UnitPriceHistory.
             */
            private save() {
                var self = this;
                if (self.isNewMode()) {
                    service.create(self.collectData(self.unitPriceHistoryModel()));
                } else {
                    service.update(self.collectData(self.unitPriceHistoryModel()));
                }
            }

            /**
             * Clear all input and switch to new mode.
             */
            private enableNewMode() {
                var self = this;
                $('.save-error').ntsError('clear');
                self.clearUnitPriceDetail();
                self.isNewMode(true);
            }

            /**
             * Clear all input.
             */
            private clearUnitPriceDetail() {
                var model = this.unitPriceHistoryModel()
                model.id = '';
                model.unitPriceCode('');
                model.unitPriceName('');
                model.startMonth('');
                //model.endMonth('');
                model.budget(null);
                model.fixPaySettingType('Company');
                model.fixPayAtr('NotApply');
                model.fixPayAtrMonthly('NotApply');
                model.fixPayAtrDayMonth('NotApply');
                model.fixPayAtrDaily('NotApply');
                model.fixPayAtrHourly('NotApply');
                model.memo('');
            }

            /**
             * Load UnitPriceHistory detail.
             */
            private loadUnitPriceDetail(id: string) {
                var self = this;
                if (id) {
                    service.find(id).done(data => {
                        self.unitPriceHistoryModel(new UnitPriceHistoryModel(data));
                    });
                }
            }

            /**
             * Load UnitPriceHistory tree list.
             */
            private loadUnitPriceHistoryList(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.getUnitPriceHistoryList().done(data => {
                    self.historyList(data.map((item, index) => { return new UnitPriceHistoryNode(item) }));
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Collect data from model and convert to dto.
             */
            private collectData(unitPriceHistoryModel): UnitPriceHistoryDto {
                let dto = new UnitPriceHistoryDto();
                dto.id = unitPriceHistoryModel.id;
                dto.unitPriceCode = unitPriceHistoryModel.unitPriceCode();
                dto.unitPriceName = unitPriceHistoryModel.unitPriceName();
                dto.startMonth = unitPriceHistoryModel.startMonth();
                dto.endMonth = unitPriceHistoryModel.endMonth();
                dto.budget = unitPriceHistoryModel.budget();
                dto.fixPaySettingType = unitPriceHistoryModel.fixPaySettingType();
                dto.fixPayAtr = unitPriceHistoryModel.fixPayAtr();
                dto.fixPayAtrMonthly = unitPriceHistoryModel.fixPayAtrMonthly();
                dto.fixPayAtrDayMonth = unitPriceHistoryModel.fixPayAtrDayMonth();
                dto.fixPayAtrDaily = unitPriceHistoryModel.fixPayAtrDaily();
                dto.fixPayAtrHourly = unitPriceHistoryModel.fixPayAtrHourly();
                dto.memo = unitPriceHistoryModel.memo();
                return dto;
            }
        }

        export class UnitPriceHistoryModel {
            id: string;
            unitPriceCode: KnockoutObservable<string>;
            unitPriceName: KnockoutObservable<string>;
            startMonth: KnockoutObservable<string>;
            endMonth: KnockoutObservable<string>;
            budget: KnockoutObservable<number>;
            fixPaySettingType: KnockoutObservable<string>;
            fixPayAtr: KnockoutObservable<string>;
            fixPayAtrMonthly: KnockoutObservable<string>;
            fixPayAtrDayMonth: KnockoutObservable<string>;
            fixPayAtrDaily: KnockoutObservable<string>;
            fixPayAtrHourly: KnockoutObservable<string>;
            memo: KnockoutObservable<string>;

            constructor(historyDto: UnitPriceHistoryDto) {
                this.id = historyDto.id;
                this.unitPriceCode = ko.observable(historyDto.unitPriceCode);
                this.unitPriceName = ko.observable(historyDto.unitPriceName);
                this.startMonth = ko.observable(historyDto.startMonth + '');
                this.endMonth = ko.observable(historyDto.endMonth + '');
                this.budget = ko.observable(historyDto.budget);
                this.fixPaySettingType = ko.observable(historyDto.fixPaySettingType);
                this.fixPayAtr = ko.observable(historyDto.fixPayAtr);
                this.fixPayAtrMonthly = ko.observable(historyDto.fixPayAtrMonthly);
                this.fixPayAtrDayMonth = ko.observable(historyDto.fixPayAtrDayMonth);
                this.fixPayAtrDaily = ko.observable(historyDto.fixPayAtrDaily);
                this.fixPayAtrHourly = ko.observable(historyDto.fixPayAtrHourly);
                this.memo = ko.observable(historyDto.memo);
            }
        }

        export class UnitPriceHistoryNode {
            id: string
            nodeText: string;
            childs: Array<UnitPriceHistoryNode>;
            constructor(item: UnitPriceItemDto | UnitPriceHistoryItemDto) {
                var self = this;
                self.id = "";

                if ((<UnitPriceItemDto>item).histories !== undefined) {
                    let unitPriceItemDto: UnitPriceItemDto = item;
                    self.nodeText = unitPriceItemDto.unitPriceCode + '~' + unitPriceItemDto.unitPriceName;
                    self.childs = unitPriceItemDto.histories.map((item, index) => { return new UnitPriceHistoryNode(item) });
                }

                if ((<UnitPriceItemDto>item).histories === undefined) {
                    let <UnitPriceHistoryItemDto>unitPriceHistoryItemDto = item;
                    self.id = item.id;
                    self.nodeText = unitPriceHistoryItemDto.startMonth + '~' + unitPriceHistoryItemDto.endMonth;
                }
            }
        }

    }
}