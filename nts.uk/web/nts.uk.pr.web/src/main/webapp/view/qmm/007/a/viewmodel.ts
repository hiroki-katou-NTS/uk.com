module nts.uk.pr.view.qmm007.a {
    export module viewmodel {
        import UnitPriceHistoryDto = service.model.UnitPriceHistoryDto;
        import ScreenBaseModel = base.simplehistory.viewmodel.ScreenBaseModel;
        export class ScreenModel extends ScreenBaseModel<service.model.UnitPrice, service.model.UnitPriceHistory> {
            // UnitPriceHistory Model
            unitPriceHistoryModel: KnockoutObservable<UnitPriceHistoryModel>;

            // Setting type
            isContractSettingEnabled: KnockoutObservable<boolean>;

            // Flags
            isLoading: KnockoutObservable<boolean>;

            // Nts text editor options
            textEditorOption: KnockoutObservable<nts.uk.ui.option.TextEditorOption>;
            // Switch button data source
            switchButtonDataSource: KnockoutObservableArray<SwitchButtonDataSource>;

            constructor() {
                super('会社一律金額', service.instance);
                var self = this;
                self.isLoading = ko.observable(true);

                self.unitPriceHistoryModel = ko.observable(new UnitPriceHistoryModel(self.getDefaultUnitPriceHistory()));
                self.switchButtonDataSource = ko.observableArray<SwitchButtonDataSource>([
                    { code: 'Apply', name: '対象' },
                    { code: 'NotApply', name: '対象外' }
                ]);

                // Setting type
                self.isContractSettingEnabled = ko.computed(() => {
                    return self.unitPriceHistoryModel().fixPaySettingType() == 'Contract';
                })

                // Nts text editor options
                self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    textalign: "left"
                }));
            }

            /**
             * Create or Update UnitPriceHistory.
             */
            private save(): void {
                var self = this;
                if (self.isNewMode()) {
                    service.create(ko.toJS(self.unitPriceHistoryModel()), true).done(() => {
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
             * Load UnitPriceHistory detail.
             */
            onSelectHistory(id: string): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                self.isLoading(true);
                service.instance.findHistoryByUuid(id).done(dto => {
                    self.setUnitPriceHistoryModel(dto);
                    self.isLoading(false);
                    nts.uk.ui.windows.setShared('unitPriceHistoryModel', ko.toJS(this.unitPriceHistoryModel()));
                    $('.save-error').ntsError('clear');
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            /**
             * Clear all input and switch to new mode.
             */
            onRegistNew(): void {
                var self = this;
                $('.save-error').ntsError('clear');
                self.setUnitPriceHistoryModel(self.getDefaultUnitPriceHistory());
            }

            /**
             * Get default history
             */
            private getDefaultUnitPriceHistory(): UnitPriceHistoryDto {
                var defaultHist: any = {};
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

        export interface SwitchButtonDataSource {
            code: string;
            name: string;
        }
    }
}