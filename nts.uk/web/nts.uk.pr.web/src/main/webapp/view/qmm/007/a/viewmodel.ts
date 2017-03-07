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
            historyList: KnockoutObservableArray<UnitPriceHistoryNode>;

            // Setting type
            isContractSettingEnabled: KnockoutObservable<boolean>;

            // Flags
            isLoading: KnockoutObservable<boolean>;
            isNewMode: KnockoutObservable<boolean>;
            hasSelected: KnockoutObservable<boolean>;

            // Nts text editor options
            textEditorOption: KnockoutObservable<nts.uk.ui.option.TextEditorOption>;
            // Switch button data source
            switchButtonDataSource: KnockoutObservableArray<SwitchButtonDataSource>;

            constructor() {
                var self = this;
                self.isNewMode = ko.observable(false);
                self.isLoading = ko.observable(true);
                self.hasSelected = ko.observable(false);

                self.unitPriceHistoryModel = ko.observable(new UnitPriceHistoryModel(self.getDefaultUnitPriceHistory()));
                self.historyList = ko.observableArray<UnitPriceHistoryNode>([]);
                self.switchButtonDataSource = ko.observableArray<SwitchButtonDataSource>([
                    { code: 'Apply', name: '対象' },
                    { code: 'NotApply', name: '対象外' }
                ]);

                self.selectedId = ko.observable('');
                self.selectedId.subscribe(id => {
                    if (id) {
                        // when selected a parent node
                        if (id.length < 4) {
                            // jump to latest history
                            self.selectedId(self.getLatestHistoryId(id));
                        }
                        // when selected a child node
                        else {
                            self.loadUnitPriceDetail(id);
                        }
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
            public startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                self.isLoading(true);
                self.loadUnitPriceHistoryList().done(() => {
                    // Select first history.
                    self.selectedId(self.historyList()[0].id);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Select latest history then go to add dialog.
             */
            private openAddDialog(): void {
                var self = this;
                var currentModel = self.unitPriceHistoryModel();
                // Check if selected history is latest
                if (self.isLatest(currentModel.id, currentModel.unitPriceCode())) {
                    self.openAddDialogThenReloadIfAdded();
                }
                // Select latest history
                else {
                    self.selectedId(self.getLatestHistoryId(currentModel.unitPriceCode()));
                    self.openAddDialogThenReloadIfAdded();
                }

            }

            /**
             * Go to Add UnitPriceHistory dialog. Reload history list if a history is added.
             */
            private openAddDialogThenReloadIfAdded(): void {
                var self = this;
                nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の追加', dialogClass: 'no-close' }).onClosed(() => {
                    // Get returned data from dialog.
                    let isCreated = nts.uk.ui.windows.getShared('isCreated');
                    if (isCreated) {
                        // Reload UnitPriceHistoryList
                        self.loadUnitPriceHistoryList().done(() => {
                            // Select latest history
                            self.selectedId(self.getLatestHistoryId(self.unitPriceHistoryModel().unitPriceCode()));
                        });
                    }
                });
            }

            /**
             * Go to Edit UnitPriceHistory dialog.
             */
            private openEditDialog(): void {
                var self = this;
                var currentModel = self.unitPriceHistoryModel();
                nts.uk.ui.windows.setShared('isLatestHistory', self.isLatest(currentModel.id, currentModel.unitPriceCode()));
                nts.uk.ui.windows.sub.modal('/view/qmm/007/c/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の編集', dialogClass: 'no-close' }).onClosed(() => {
                    // Get returned data from dialog.
                    let isUpdated = nts.uk.ui.windows.getShared('isUpdated');
                    let isRemoved = nts.uk.ui.windows.getShared('isRemoved');
                    // if removed history
                    if (isRemoved) {
                        // Reload UnitPriceHistoryList
                        self.loadUnitPriceHistoryList().done(() => {
                            let latestHistory = self.getLatestHistoryId(self.unitPriceHistoryModel().unitPriceCode());
                            // select latest history if exist.
                            if (latestHistory) {
                                self.selectedId(latestHistory);
                            }
                            // select undefined if no history left.
                            else {
                                self.setUnitPriceHistoryModel(self.getDefaultUnitPriceHistory());
                                self.selectedId(undefined);
                            }
                        });
                    }
                    // if updated history
                    if (isUpdated) {
                        // Reload UnitPriceHistoryList
                        self.loadUnitPriceHistoryList().done(() => {
                            // Select updated history
                            self.loadUnitPriceDetail(self.selectedId());
                        });
                    }
                });
            }

            /**
             * Create or Update UnitPriceHistory.
             */
            private save(): void {
                var self = this;
                if (self.isNewMode()) {
                    service.create(ko.toJS(self.unitPriceHistoryModel())).done(() => {
                        self.loadUnitPriceHistoryList().done(() => {
                            self.selectedId(self.getLatestHistoryId(self.unitPriceHistoryModel().unitPriceCode()));
                        });
                    });
                } else {
                    service.update(ko.toJS(self.unitPriceHistoryModel())).done(() => {
                        self.loadUnitPriceHistoryList().done(() => {
                            self.loadUnitPriceDetail(self.selectedId());
                        });
                    });
                }
            }

            /**
             * Clear all input and switch to new mode.
             */
            private enableNewMode(): void {
                var self = this;
                $('.save-error').ntsError('clear');
                self.hasSelected(false);
                self.selectedId('');
                self.setUnitPriceHistoryModel(self.getDefaultUnitPriceHistory());
                self.isNewMode(true);
            }

            /**
             * Set the UnitPriceHistoryModel
             */
            private setUnitPriceHistoryModel(dto: UnitPriceHistoryDto): void {
                var model: UnitPriceHistoryModel = this.unitPriceHistoryModel();
                model.id = dto.id;
                model.unitPriceCode(dto.unitPriceCode);
                model.unitPriceName(dto.unitPriceName);
                model.startMonth(nts.uk.time.formatYearMonth(dto.startMonth));
                model.endMonth(nts.uk.time.formatYearMonth(dto.endMonth));
                model.budget(dto.budget);
                model.fixPaySettingType(dto.fixPaySettingType);
                model.fixPayAtr(dto.fixPayAtr);
                model.fixPayAtrMonthly(dto.fixPayAtrMonthly);
                model.fixPayAtrDayMonth(dto.fixPayAtrDayMonth);
                model.fixPayAtrDaily(dto.fixPayAtrDaily);
                model.fixPayAtrHourly(dto.fixPayAtrHourly);
                model.memo(dto.memo);
            }

            /**
             * Get default history
             */
            private getDefaultUnitPriceHistory(): UnitPriceHistoryDto {
                var defaultHist = new UnitPriceHistoryDto();
                defaultHist.id = '';
                defaultHist.unitPriceCode = '';
                defaultHist.unitPriceName = '';
                defaultHist.startMonth = 201701;
                defaultHist.endMonth = 999912;
                defaultHist.budget = 0;
                defaultHist.fixPaySettingType = 'Company';
                defaultHist.fixPayAtr = 'NotApply';
                defaultHist.fixPayAtrMonthly = 'NotApply';
                defaultHist.fixPayAtrDayMonth = 'NotApply';
                defaultHist.fixPayAtrDaily = 'NotApply';
                defaultHist.fixPayAtrHourly = 'NotApply';
                defaultHist.memo = '';
                return defaultHist;
            }

            /**
             * Check if the if the history is latest
             */
            private isLatest(historyId: string, unitPriceCode: string): boolean {
                var self = this;
                var latestHistoryId = self.getLatestHistoryId(unitPriceCode);
                return historyId == latestHistoryId ? true : false;
            }

            /**
             * Get the latest historyId by unit price code
             */
            private getLatestHistoryId(code: string): string {
                var self = this;
                var lastestHistoryId: string = '';
                //find the group of the history by unit price code
                self.historyList().some(node => {
                    if (code == node.id) {
                        // get the historyId of the first element (latest history)
                        lastestHistoryId = node.childs[0].id;
                        // break the execution
                        return true;
                    }
                });
                return lastestHistoryId;
            }

            /**
             * Load UnitPriceHistory detail.
             */
            private loadUnitPriceDetail(id: string): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                self.isLoading(true);
                service.find(id).done(dto => {
                    self.setUnitPriceHistoryModel(dto);
                    self.isNewMode(false);
                    self.hasSelected(true);
                    self.isLoading(false);
                    nts.uk.ui.windows.setShared('unitPriceHistoryModel', ko.toJS(this.unitPriceHistoryModel()));
                    $('.save-error').ntsError('clear');
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Load UnitPriceHistory tree list.
             */
            private loadUnitPriceHistoryList(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                self.isLoading(true);
                service.getUnitPriceHistoryList().done(data => {
                    self.historyList(data.map((item, index) => { return new UnitPriceHistoryNode(item) }));
                    self.isLoading(false);
                    dfd.resolve();
                });
                return dfd.promise();
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
                this.startMonth = ko.observable(nts.uk.time.formatYearMonth(historyDto.startMonth));
                this.endMonth = ko.observable(nts.uk.time.formatYearMonth(historyDto.endMonth));
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
            id: string;
            nodeText: string;
            childs: Array<UnitPriceHistoryNode>;
            constructor(item: UnitPriceItemDto | UnitPriceHistoryItemDto) {
                var self = this;

                if ((<UnitPriceItemDto>item).histories !== undefined) {
                    self.id = (<UnitPriceItemDto>item).unitPriceCode;
                    self.nodeText = (<UnitPriceItemDto>item).unitPriceCode + ' ' + (<UnitPriceItemDto>item).unitPriceName;
                    self.childs = (<UnitPriceItemDto>item).histories.map((item, index) => { return new UnitPriceHistoryNode(item) });
                }

                if ((<UnitPriceItemDto>item).histories === undefined) {
                    self.id = (<UnitPriceHistoryItemDto>item).id;
                    self.nodeText = nts.uk.time.formatYearMonth((<UnitPriceHistoryItemDto>item).startMonth) + ' ~ ' + nts.uk.time.formatYearMonth((<UnitPriceHistoryItemDto>item).endMonth);
                }
            }
        }

        export interface SwitchButtonDataSource {
            code: string;
            name: string;
        }

    }
}