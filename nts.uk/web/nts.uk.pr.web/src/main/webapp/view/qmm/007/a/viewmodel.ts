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
            isInputEnabled: KnockoutObservable<boolean>;
            isLoading: KnockoutObservable<boolean>;
            isNewMode: KnockoutObservable<boolean>;
            isLatestHistorySelected: KnockoutObservable<boolean>;

            // Nts text editor options
            textEditorOption: KnockoutObservable<nts.uk.ui.option.TextEditorOption>;
            // Switch button data source
            switchButtonDataSource: KnockoutObservableArray<SwitchButtonDataSource>;

            constructor() {
                var self = this;
                self.isNewMode = ko.observable(false);
                self.isLoading = ko.observable(true);
                self.isInputEnabled = ko.observable(false);
                self.isLatestHistorySelected = ko.observable(false);
                self.isLatestHistorySelected.subscribe(val => {
                    if (val == true) {
                        self.isInputEnabled(true);
                    } else {
                        self.isInputEnabled(false);
                    }
                });

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
                            self.isLoading(true);
                            $('.save-error').ntsError('clear');
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
                self.loadUnitPriceHistoryList().done(() => {
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Go to Add UnitPriceHistory dialog.
             */
            private openAddDialog(): void {
                var self = this;
                nts.uk.ui.windows.setShared('unitPriceHistoryModel', ko.toJS(self.unitPriceHistoryModel()));
                nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の追加', dialogClass: 'no-close' }).onClosed(() => {
                    let startMonth = nts.uk.ui.windows.getShared('startMonth');
                    if (startMonth) {
                        // Get the startMonth of the newly added history and set to the endMonth of the latest history.
                        self.unitPriceHistoryModel().endMonth(startMonth);
                        // Update endMonth of previous history
                        service.update(ko.toJS(self.unitPriceHistoryModel())).done(() => {
                            // Reload UnitPriceHistoryList
                            self.loadUnitPriceHistoryList().done(() => {
                                // Select latest history
                                self.selectedId(self.getLatestHistoryId(self.unitPriceHistoryModel().unitPriceCode()));
                            });
                        });
                    }
                });
            }

            /**
             * Go to Edit UnitPriceHistory dialog.
             */
            private openEditDialog(): void {
                var self = this;
                nts.uk.ui.windows.setShared('unitPriceHistoryModel', ko.toJS(this.unitPriceHistoryModel()));
                nts.uk.ui.windows.setShared('isLatestHistory', self.isLatestHistorySelected());
                nts.uk.ui.windows.sub.modal('/view/qmm/007/c/index.xhtml', { title: '会社一律金額 の 登録 > 履歴の編集', dialogClass: 'no-close' }).onClosed(() => {
                    let startMonth = nts.uk.ui.windows.getShared('startMonth');
                    let isRemoved = nts.uk.ui.windows.getShared('isRemoved');

                    // if removed history
                    if (isRemoved) {
                        let secondLatestHistory = self.getSecondLatestHistoryId(self.unitPriceHistoryModel().unitPriceCode());
                        // select second latest history if there are >= 2 histories
                        if (secondLatestHistory) {
                            // Get the latest history
                            service.find(secondLatestHistory).done(dto => {
                                self.setUnitPriceHistoryModel(dto);
                                // Update endMonth
                                self.unitPriceHistoryModel().endMonth('9999/12');
                                service.update(ko.toJS(self.unitPriceHistoryModel())).done(() => {
                                    // Reload UnitPriceHistoryList
                                    self.loadUnitPriceHistoryList().done(() => {
                                        // Select latest history
                                        self.selectedId(self.getLatestHistoryId(self.unitPriceHistoryModel().unitPriceCode()));
                                    });
                                });
                            });
                        }
                        // select null if there is only 1 history
                        else {
                            self.loadUnitPriceHistoryList().done(() => {
                                self.setUnitPriceHistoryModel(self.getDefaultUnitPriceHistory());
                                self.isLatestHistorySelected(false);
                                self.selectedId(undefined);
                            });
                        }
                    }
                    // if updated history
                    else if (startMonth) {
                        let secondLatestHistory = self.getSecondLatestHistoryId(self.unitPriceHistoryModel().unitPriceCode());
                        // update the previous history if exist
                        if (secondLatestHistory) {
                            // Get the previous history
                            service.find(secondLatestHistory).done(dto => {
                                self.setUnitPriceHistoryModel(dto);
                                // Update endMonth
                                self.unitPriceHistoryModel().endMonth(startMonth);
                                // Update the previous history
                                service.update(ko.toJS(self.unitPriceHistoryModel())).done(() => {
                                    // Reload UnitPriceHistoryList
                                    self.loadUnitPriceHistoryList().done(() => {
                                        self.loadUnitPriceDetail(self.selectedId());
                                    });
                                });
                            });
                        } else {
                            // Reload UnitPriceHistoryList
                            self.loadUnitPriceHistoryList().done(() => {
                                self.loadUnitPriceDetail(self.selectedId());
                            });
                        }
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
                self.selectedId('');
                self.setUnitPriceHistoryModel(self.getDefaultUnitPriceHistory());
                self.isInputEnabled(true);
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
            private isLatest(history: UnitPriceHistoryDto): boolean {
                var self = this;
                var latestHistoryId = self.getLatestHistoryId(history.unitPriceCode);
                return history.id == latestHistoryId ? true : false;
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
             * Get the latest historyId by unit price code after remove the latest history
             */
            private getSecondLatestHistoryId(code: string): string {
                var self = this;
                var latestHistoryId: string = '';
                //find the group of the history by unit price code
                self.historyList().some(node => {
                    if (code == node.id) {
                        // get the historyId of the first element (latest history)
                        latestHistoryId = node.childs[1] ? node.childs[1].id : undefined;
                        // break the execution
                        return true;
                    }
                });
                return latestHistoryId;
            }

            /**
             * Load UnitPriceHistory detail.
             */
            private loadUnitPriceDetail(id: string): void {
                var self = this;
                service.find(id).done(dto => {
                    self.setUnitPriceHistoryModel(dto);
                    self.isLatestHistorySelected(self.isLatest(dto));
                    self.isNewMode(false);
                    self.isLoading(false);
                });
            }

            /**
             * Load UnitPriceHistory tree list.
             */
            private loadUnitPriceHistoryList(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
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