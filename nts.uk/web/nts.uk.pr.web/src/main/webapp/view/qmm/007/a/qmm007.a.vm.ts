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
                super({
                    functionName: '会社一律金額',
                    service: service.instance,
                    removeMasterOnLastHistoryRemove: true
                });
                var self = this;
                self.isLoading = ko.observable(true);

                self.unitPriceHistoryModel = ko.observable(new UnitPriceHistoryModel(self.getDefaultUnitPriceHistory()));
                self.switchButtonDataSource = ko.observableArray<SwitchButtonDataSource>([
                    { code: ApplySetting.APPLY, name: '対象' },
                    { code: ApplySetting.NOTAPPLY, name: '対象外' }
                ]);

                // Setting type
                self.isContractSettingEnabled = ko.computed(() => {
                    return self.unitPriceHistoryModel().fixPaySettingType() == SettingType.CONTRACT;
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
            onSave(): JQueryPromise<string> {
                var self = this;
                var dfd = $.Deferred<string>();
                if (self.isNewMode()) {
                    service.instance.create(ko.toJS(self.unitPriceHistoryModel())).done(res => {
                        dfd.resolve(res.uuid);
                    }).fail(res => {
                        self.setMessages(res.messageId);
                    });
                } else {
                    service.instance.update(ko.toJS(self.unitPriceHistoryModel())).done((res) => {
                        dfd.resolve(self.unitPriceHistoryModel().id);
                    });
                }
                return dfd.promise();
            }

            private setMessages(messageId: string): void {
                var self = this;
                switch (messageId) {
                    case 'ER001':
                        if (!self.unitPriceHistoryModel().unitPriceCode()) {
                            $('#inpCode').ntsError('set', '＊が入力されていません。');
                        }
                        if (!self.unitPriceHistoryModel().unitPriceName()) {
                            $('#inpName').ntsError('set', '＊が入力されていません。');
                        }
                        if (!self.unitPriceHistoryModel().budget()) {
                            $('#inpBudget').ntsError('set', '＊が入力されていません。');
                        }
                        break;
                    case 'ER005':
                        $('#inpCode').ntsError('set', '入力した＊は既に存在しています。\r\n ＊を確認してください。');
                        break;
                    case 'ER010':
                        $('#inpStartMonth').ntsError('set', '対象データがありません。');
                        break;
                    default:// Do nothing.
                        break;
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
                model.startMonth(dto.startMonth);
                model.endMonth(dto.endMonth);
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
                defaultHist.startMonth = parseInt(nts.uk.time.formatDate(new Date(), 'yyyyMM'));
                defaultHist.endMonth = 999912;
                defaultHist.budget = 0;
                defaultHist.fixPaySettingType = SettingType.COMPANY;
                defaultHist.fixPayAtr = ApplySetting.APPLY;
                defaultHist.fixPayAtrMonthly = ApplySetting.APPLY;
                defaultHist.fixPayAtrDayMonth = ApplySetting.APPLY;
                defaultHist.fixPayAtrDaily = ApplySetting.APPLY;
                defaultHist.fixPayAtrHourly = ApplySetting.APPLY;
                defaultHist.memo = '';
                return defaultHist;
            }

        }
        export class UnitPriceHistoryModel {
            id: string;
            unitPriceCode: KnockoutObservable<string>;
            unitPriceName: KnockoutObservable<string>;
            startMonth: KnockoutObservable<number>;
            endMonth: KnockoutObservable<number>;
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
                this.startMonth = ko.observable(historyDto.startMonth);
                this.endMonth = ko.observable(historyDto.endMonth);
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

        export class SettingType {
            static COMPANY = 'Company';
            static CONTRACT = 'Contract';
        }

        export class ApplySetting {
            static APPLY = 'Apply';
            static NOTAPPLY = 'NotApply';
        }

        export interface SwitchButtonDataSource {
            code: string;
            name: string;
        }
    }
}