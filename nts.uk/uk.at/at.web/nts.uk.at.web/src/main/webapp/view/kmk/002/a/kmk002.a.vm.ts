module nts.uk.at.view.kmk002.a {
    import OptionalItemDto = service.model.OptionalItemDto;
    import OptionalItemHeader = service.model.OptionalItemHeader;
    import CalculationResultRangeDto = service.model.CalculationResultRangeDto;
    import OptionalItemFormulaDto = service.model.OptionalItemFormulaDto;
    export module viewmodel {

        export class ScreenModel {
            roundingRules: KnockoutObservableArray<any>;
            selectedRuleCode: any;
            selectedCbb: any;
            checked: any;
            columns: any;
            selectedCode: KnockoutObservable<any>;
            name: KnockoutObservable<any>;

            optionalItem: OptionalItem;
            optionalItemHeaders: Array<OptionalItemHeader>;

            constructor() {
                let self = this;
                self.optionalItem = new OptionalItem();
                self.optionalItemHeaders = new Array<OptionalItemHeader>();


                // mock data.
                self.roundingRules = ko.observableArray([
                    { code: '1', name: '四捨五入' },
                    { code: '2', name: '切り上げ' }
                ]);
                self.selectedRuleCode = ko.observable(1);
                self.selectedCbb = ko.observable();
                self.checked = ko.observable();
                self.selectedCode = ko.observable();
                self.name = ko.observable('');
                self.columns = ko.observableArray([
                    { headerText: 'code', key: 'code', width: 100 },
                    { headerText: 'name', key: 'name', width: 150 },
                ]);
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                self.loadOptionalItemHeaders().done(res => {
                    console.log(self.optionalItem);
                    console.log('name:' + self.optionalItem.optionalItemName());
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Load optional item headers
             */
            private loadOptionalItemHeaders(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.findOptionalItemHeaders().done(res => {
                    self.optionalItemHeaders = res;
                    dfd.resolve();
                });
                return dfd.promise();

            }

            /**
             * Load optional item detail.
             */
            private loadOptionalItemDetail(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.findOptionalItemDetail().done(res => {
                    self.optionalItem.fromDto(res);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Save optional item detail.
             */
            public saveOptionalItemDetail(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                let command = self.optionalItem.toDto();
                service.saveOptionalItem(command).done(() => {
                    dfd.resolve();
                }).fail().always();
                return dfd.promise();
            }
        }

        class OptionalItem {
            optionalItemNo: string;
            optionalItemName: KnockoutObservable<string>;
            optionalItemAtr: KnockoutObservable<number>;
            usageAtr: KnockoutObservable<number>;
            empConditionAtr: KnockoutObservable<number>;
            performanceAtr: KnockoutObservable<number>;
            calculationResultRange: CalculationResultRange;

            // Switch button data source
            usageClsDatasource: KnockoutObservableArray<any>;
            empConClsDatasource: KnockoutObservableArray<any>;
            perfClsDatasource: KnockoutObservableArray<any>;
            atrDataSource: KnockoutObservableArray<any>;

            constructor() {
                this.optionalItemNo = '';
                this.optionalItemName = ko.observable('');
                this.optionalItemAtr = ko.observable(0);
                this.usageAtr = ko.observable(0);
                this.empConditionAtr = ko.observable(0);
                this.performanceAtr = ko.observable(0);
                this.calculationResultRange = new CalculationResultRange();

                // Data source
                this.usageClsDatasource = ko.observableArray([
                    { code: '0', name: nts.uk.resource.getText("KMK002_15") }, // not used
                    { code: '1', name: nts.uk.resource.getText("KMK002_14") } // used
                ]);
                this.empConClsDatasource = ko.observableArray([
                    { code: '0', name: nts.uk.resource.getText("KMK002_17") }, // not apply
                    { code: '1', name: nts.uk.resource.getText("KMK002_18") } // apply
                ]);
                this.perfClsDatasource = ko.observableArray([
                    { code: '0', name: nts.uk.resource.getText("KMK002_22") }, // monthly
                    { code: '1', name: nts.uk.resource.getText("KMK002_23") } // daily
                ]);
                this.atrDataSource = ko.observableArray([
                    //TODO: lay tren server: EnumAdaptor.convertToValueNameList
                    { code: '0', name: '回数' }, // TIMES
                    { code: '1', name: '金額' }, // AMOUNT
                    { code: '2', name: '時間' } // TIME
                ]);
            }

            public toDto(): OptionalItemDto {
                let self = this;
                let dto: OptionalItemDto = <OptionalItemDto>{};

                dto.optionalItemNo = self.optionalItemNo;
                dto.optionalItemName = self.optionalItemName();
                dto.optionalItemAtr = self.optionalItemAtr();
                dto.usageAtr = self.usageAtr();
                dto.empConditionAtr = self.empConditionAtr();
                dto.performanceAtr = self.performanceAtr();
                dto.calculationResultRange = self.calculationResultRange.toDto();

                return dto;
            }

            public fromDto(dto: OptionalItemDto): void {
                let self = this;
                self.optionalItemNo = dto.optionalItemNo;
                self.optionalItemName(dto.optionalItemName);
                self.optionalItemAtr(dto.optionalItemAtr);
                self.usageAtr(dto.usageAtr);
                self.empConditionAtr(dto.empConditionAtr);
                self.performanceAtr(dto.performanceAtr);
                self.calculationResultRange.fromDto(dto.calculationResultRange);
            }
        }

        class CalculationResultRange {
            upperCheck: KnockoutObservable<boolean>;
            lowerCheck: KnockoutObservable<boolean>;
            numberUpper: KnockoutObservable<number>;
            numberLower: KnockoutObservable<number>;
            amountUpper: KnockoutObservable<number>;
            amountLower: KnockoutObservable<number>;
            timeUpper: KnockoutObservable<number>;
            timeLower: KnockoutObservable<number>;

            constructor() {
                this.upperCheck = ko.observable(false);
                this.lowerCheck = ko.observable(false);
                this.numberUpper = ko.observable(0);
                this.numberLower = ko.observable(0);
                this.amountUpper = ko.observable(0);
                this.amountLower = ko.observable(0);
                this.timeUpper = ko.observable(0);
                this.timeLower = ko.observable(0);
            }

            public fromDto(dto: CalculationResultRangeDto): void {
                let self = this;
                self.upperCheck(dto.upperCheck);
                self.lowerCheck(dto.lowerCheck);
                self.numberUpper(dto.numberUpper);
                self.numberLower(dto.numberLower);
                self.timeUpper(dto.timeUpper);
                self.timeLower(dto.timeLower);
                self.amountUpper(dto.amountUpper);
                self.amountLower(dto.amountLower);
            }

            public toDto(): CalculationResultRangeDto {
                let self = this;
                let dto = <CalculationResultRangeDto>{};
                dto.upperCheck = self.upperCheck();
                dto.lowerCheck = self.lowerCheck();
                dto.numberUpper = self.numberUpper();
                dto.numberLower = self.numberLower();
                dto.amountUpper = self.amountUpper();
                dto.amountLower = self.amountLower();
                dto.timeUpper = self.timeUpper();
                dto.timeLower = self.timeLower();
                return dto;
            }
        }

    }
}