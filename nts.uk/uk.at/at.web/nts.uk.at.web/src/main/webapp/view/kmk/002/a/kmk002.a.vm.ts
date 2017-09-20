module nts.uk.at.view.kmk002.a {
    import OptionalItemDto = service.model.OptionalItemDto;
    import OptionalItemHeader = service.model.OptionalItemHeader;
    import CalcResultRangeDto = service.model.CalcResultRangeDto;
    import FormulaDto = service.model.FormulaDto;
    import CalcFormulaSettingDto = service.model.CalcFormulaSettingDto;
    import FormulaSettingDto = service.model.FormulaSettingDto;
    import ItemSelectionDto = service.model.ItemSelectionDto;
    import SettingItemDto = service.model.SettingItemDto;;
    import AttendanceItemDto = service.model.AttendanceItemDto;;
    import RoundingDto = service.model.RoundingDto;;
    import EmpConditionAtr = service.model.EmpConditionAtr;
    import PerformanceAtr = service.model.PerformanceAtr;
    import UsageAtr = service.model.UsageAtr;
    import TypeAtr = service.model.TypeAtr;
    import CalcAtr = service.model.CalcAtr;
    import SettingOrder = service.model.SettingOrder;
    import SettingMethod = service.model.SettingMethod;
    import MinusSegment = service.model.MinusSegment;
    import Operator = service.model.Operator;
    import NumberRounding = service.model.NumberRounding;
    import NumberUnit = service.model.NumberUnit;
    import AmountRounding = service.model.AmountRounding;
    import AmountUnit = service.model.AmountUnit;
    import TimeRounding = service.model.TimeRounding;
    import TimeUnit = service.model.TimeUnit;

    export module viewmodel {

        export class ScreenModel {
            checked: any;
            columns: any;
            selectedCode: KnockoutObservable<any>;
            name: KnockoutObservable<any>;

            optionalItem: OptionalItem;
            optionalItemHeaders: KnockoutObservableArray<OptionalItemHeader>;
            calcFormulas: Array<Formula>;

            constructor() {
                let self = this;
                self.optionalItem = new OptionalItem();
                self.calcFormulas = new Array<Formula>();

                // mock data.
                let mock = new Array<OptionalItemHeader>();
                for (var i = 0; i < 5; i++) {
                    self.calcFormulas.push(new Formula());
                    let h = <OptionalItemHeader>{};
                    h.itemNo = ''+i;
                    h.itemName = name+i;
                    h.performanceAtr = 1;
                    h.usageAtr = 1;
                    mock.push(h);
                }

                self.optionalItemHeaders = ko.observableArray(mock);
                console.log(self.optionalItemHeaders());
                self.checked = ko.observable();
                self.selectedCode = ko.observable();
                self.name = ko.observable('');
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KMK002_7'), key: 'itemNo', width: 50 },
                    { headerText: nts.uk.resource.getText('KMK002_8'), key: 'itemName', width: 100 },
                    { headerText: nts.uk.resource.getText('KMK002_9'), key: 'performanceAtr', width: 50 },
                    { headerText: nts.uk.resource.getText('KMK002_10'), key: 'usageAtr', width: 50 }
                ]);

                // ahihi
                var comboItems = [{ code: '0', name: 'TIMES' },
                    { code: '1', name: 'AMOUNT' },
                    { code: '2', name: 'TIME' }];
                var comboColumns = [{ prop: 'name', length: 1 }];

                $("#1").ntsGrid({
                    width: '970px',
                    height: '400px',
                    dataSource: self.calcFormulas,
                    primaryKey: 'formulaId',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: [
                        { headerText: 'SYMBOL', key: 'symbolValue', dataType: 'string', width: '50px' },
                        { headerText: 'ATR', key: 'formulaAtr', dataType: 'number', width: '230px', ntsControl: 'Combobox' },
                        { headerText: 'NAME', key: 'formulaName', dataType: 'string', width: '50px' },
                        { headerText: 'CALC_ATR', key: 'formulaAtr', dataType: 'number', width: '290px', ntsControl: 'SwitchButtons' },
                        { headerText: 'OPEN_DIALOG', key: 'open', dataType: 'string', width: '80px', unbound: true, ntsControl: 'Button' },
                        { headerText: 'TEXT', key: 'formulaName', dataType: 'string', width: '50px' },
                        { headerText: 'DAILY_UNIT', key: 'formulaAtr', dataType: 'string', width: '230px', ntsControl: 'Combobox' },
                        { headerText: 'DAILY_ROUNDING', key: 'formulaAtr', dataType: 'string', width: '230px', ntsControl: 'Combobox' },
                        { headerText: 'MONTHLY_UNIT', key: 'formulaAtr', dataType: 'string', width: '230px', ntsControl: 'Combobox' },
                        { headerText: 'MONTHLY_ROUNDING', key: 'formulaAtr', dataType: 'string', width: '230px', ntsControl: 'Combobox' }
                    ],
                    features: [{ name: 'Sorting', type: 'local' }],
                    ntsControls: [{
                        name: 'SwitchButtons', options: [{ value: 0, text: 'Option 1' }, { value: 1, text: 'Option 2' }],
                        optionsValue: 'value', optionsText: 'text', controlType: 'SwitchButtons', enable: true
                    },
                        { name: 'Combobox', options: comboItems, optionsValue: 'code', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: true },
                        { name: 'Button', text: 'Open', click: function() { alert("Button!!"); }, controlType: 'Button' }]
                });
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                self.loadOptionalItemHeaders().done(res => {
                    dfd.resolve();
                });
                // Test.
                self.loadFormula();

                return dfd.promise();
            }

            /**
             * Open dialog B
             */
            public openDialogB(): void {
                //TODO
                nts.uk.ui.windows.sub.modal('/view/kmk/002/b/index.xhtml');
            }

            /**
             * Open dialog C
             */
            public openDialogC(): void {
                //TODO
                nts.uk.ui.windows.sub.modal('/view/kmk/002/c/index.xhtml');
            }

            /**
             * Open dialog D
             */
            public openDialogD(): void {
                let self = this;
                let dto = self.calcFormulas[0].calcFormulaSetting.formulaSetting.toDto();
                nts.uk.ui.windows.setShared('shared', dto);
                nts.uk.ui.windows.sub.modal('/view/kmk/002/d/index.xhtml').onClosed(() => {
                    //TODO
                });
            }

            /**
             * Load optional item headers
             */
            private loadOptionalItemHeaders(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.findOptionalItemHeaders().done(res => {
                    //TODO
                    dfd.resolve();
                });
                return dfd.promise();

            }

            /**
             * Load formula
             */
            private loadFormula(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.findAllFormula().done(res => {
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

                //test formula
                let test: Array<FormulaDto> = self.calcFormulas.map(item => {
                    return item.toDto();
                });
                console.log(test);
                service.saveFormula(test);
                return dfd.promise();
            }
        }

        /******************************************************************
         ************************* VIEW MODEL *****************************
         *****************************************************************/

        /**
         * The class OptionalItem
         */
        class OptionalItem {
            optionalItemNo: string;
            optionalItemName: KnockoutObservable<string>;
            optionalItemAtr: KnockoutObservable<TypeAtr>;
            usageAtr: KnockoutObservable<UsageAtr>;
            empConditionAtr: KnockoutObservable<EmpConditionAtr>;
            performanceAtr: KnockoutObservable<PerformanceAtr>;
            calculationResultRange: CalculationResultRange;

            // Switch button data source
            usageClsDatasource: KnockoutObservableArray<any>;
            empConClsDatasource: KnockoutObservableArray<any>;
            perfClsDatasource: KnockoutObservableArray<any>;
            atrDataSource: KnockoutObservableArray<any>;

            constructor() {
                this.optionalItemNo = '';
                this.optionalItemName = ko.observable('');
                this.optionalItemAtr = ko.observable(1);
                this.usageAtr = ko.observable(1);
                this.empConditionAtr = ko.observable(1);
                this.performanceAtr = ko.observable(1);
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
                dto.calcResultRange = self.calculationResultRange.toDto();

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
                self.calculationResultRange.fromDto(dto.calcResultRange);
            }
        }

        /**
         * The class CalculationResultRange
         */
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
                this.numberUpper = ko.observable(1);
                this.numberLower = ko.observable(1);
                this.amountUpper = ko.observable(1);
                this.amountLower = ko.observable(1);
                this.timeUpper = ko.observable(1);
                this.timeLower = ko.observable(1);
            }

            public fromDto(dto: CalcResultRangeDto): void {
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

            public toDto(): CalcResultRangeDto {
                let self = this;
                let dto = <CalcResultRangeDto>{};
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

        /**
         * The class Formula
         */
        class Formula {
            formulaId: string;
            optionalItemNo: string;
            formulaName: KnockoutObservable<string>;
            formulaAtr: KnockoutObservable<TypeAtr>;
            symbolValue: string;
            orderNo: KnockoutObservable<number>;
            calcFormulaSetting: CalcFormulaSetting;
            monthlyRounding: Rounding;
            dailyRounding: Rounding;

            constructor() {
                this.formulaId = '000';
                this.optionalItemNo = '000';
                this.formulaName = ko.observable('asdvxzc');
                this.formulaAtr = ko.observable(1);
                this.symbolValue = 'asdvxzc';
                this.orderNo = ko.observable(1);
                this.calcFormulaSetting = new CalcFormulaSetting();
                this.monthlyRounding = new Rounding();
                this.dailyRounding = new Rounding();
            }

            public toDto() {
                let self = this;
                let dto: FormulaDto = <FormulaDto>{};

                dto.formulaId = self.formulaId;
                dto.optionalItemNo = self.optionalItemNo;
                dto.formulaName = self.formulaName();
                dto.formulaAtr = self.formulaAtr();
                dto.symbolValue = self.symbolValue;
                dto.calcFormulaSetting = self.calcFormulaSetting.toDto()
                dto.monthlyRounding = self.monthlyRounding.toDto();
                dto.dailyRounding = self.dailyRounding.toDto();

                return dto;
            }

            public fromDto(dto: FormulaDto) {
                let self = this;
                self.formulaId = dto.formulaId;
                self.optionalItemNo = dto.optionalItemNo;
                self.formulaName(dto.formulaName);
                self.formulaAtr(dto.formulaAtr);
                self.symbolValue = dto.symbolValue;
                self.calcFormulaSetting.fromDto(dto.calcFormulaSetting);
                self.monthlyRounding.fromDto(dto.monthlyRounding);
                self.dailyRounding.fromDto(dto.dailyRounding);
            }
        }

        /**
         * Calculation formula setting.
         */
        class CalcFormulaSetting {
            calcAtr: KnockoutObservable<CalcAtr>;
            formulaSetting: FormulaSetting;
            itemSelection: ItemSelection;

            constructor() {
                this.calcAtr = ko.observable(1);
                this.formulaSetting = new FormulaSetting();
                this.itemSelection = new ItemSelection();
            }

            public fromDto(dto: CalcFormulaSettingDto) {
                this.calcAtr(dto.calcAtr);
                this.formulaSetting.fromDto(dto.formulaSetting);
                this.itemSelection.fromDto(dto.itemSelection);

            }
            public toDto(): CalcFormulaSettingDto {
                let self = this;
                let dto: CalcFormulaSettingDto = <CalcFormulaSettingDto>{};

                dto.calcAtr = this.calcAtr();
                dto.formulaSetting = this.formulaSetting.toDto();
                dto.itemSelection = this.itemSelection.toDto();

                return dto;
            }
        }

        /**
         * Formula setting.
         */
        export class FormulaSetting {
            minusSegment: KnockoutObservable<MinusSegment>;
            operator: KnockoutObservable<Operator>;
            leftItem: FormulaSettingItem;
            rightItem: FormulaSettingItem;

            operatorDatasource: KnockoutObservableArray<any>;

            constructor() {
                this.minusSegment =  ko.observable(0);
                this.operator = ko.observable(0);
                this.leftItem = new FormulaSettingItem();
                this.rightItem = new FormulaSettingItem();

                // fixed 
                this.leftItem.dispOrder = 1;
                this.leftItem.settingMethod(0);
                this.rightItem.dispOrder = 2;
                this.rightItem.settingMethod(1);

                this.operatorDatasource = ko.observableArray([
                    { code: '0', name: '+' },
                    { code: '1', name: '-' },
                    { code: '2', name: '*' },
                    { code: '3', name: '/' }
                ]);
            }

            public fromDto(dto: FormulaSettingDto) {
                let self = this;
                self.minusSegment(dto.minusSegment);
                self.operator(dto.operator);
                self.leftItem.fromDto(dto.leftItem);
                self.rightItem.fromDto(dto.rightItem);
            }

            public toDto(): any {
                let self = this;
                let dto: FormulaSettingDto = <FormulaSettingDto>{};

                dto.minusSegment = self.minusSegment();
                dto.operator = self.operator();
                dto.leftItem = this.leftItem.toDto();
                dto.rightItem = this.rightItem.toDto();

                return dto;
            }
        }

        /**
         * Formula setting item
         */
        class FormulaSettingItem {
            settingMethod: KnockoutObservable<SettingMethod>;
            dispOrder: SettingOrder;
            inputValue: KnockoutObservable<number>;
            formulaItemId: KnockoutObservable<string>;

            constructor() {
                this.settingMethod = ko.observable(1);
                this.dispOrder = 1;
                this.inputValue = ko.observable(1);
                this.formulaItemId = ko.observable('');
            }

            public fromDto(dto: SettingItemDto) {
                this.settingMethod(dto.settingMethod);
                this.dispOrder = dto.dispOrder;
                this.inputValue(dto.inputValue);
                this.formulaItemId(dto.formulaItemId);
            }

            public toDto(): SettingItemDto {
                let self = this;
                let dto: SettingItemDto = <SettingItemDto>{};

                dto.settingMethod = this.settingMethod();
                dto.dispOrder = this.dispOrder;
                dto.inputValue = this.inputValue();
                dto.formulaItemId = this.formulaItemId();

                return dto;
            }
        }

        /**
         * ItemSelection
         */
        class ItemSelection {
            minusSegment: KnockoutObservable<MinusSegment>;
            attendanceItems: Array<AttendanceItem>;

            constructor() {
                this.minusSegment = ko.observable(1);
                this.attendanceItems = []; //TODO
            }

            public fromDto(dto: ItemSelectionDto) {
                this.minusSegment(dto.minusSegment);
                this.attendanceItems = []; //TODO
            }

            public toDto(): ItemSelectionDto {
                let self = this;
                let dto: ItemSelectionDto = <ItemSelectionDto>{};

                dto.minusSegment = this.minusSegment();
                dto.attendanceItems = []; //TODO

                return dto;
            }
        }

        /**
         * AttendanceItem
         */
        class AttendanceItem {
            id: string;
            operator: KnockoutObservable<Operator>;

            constructor() {
                this.id = 'asdf';
                this.operator = ko.observable(1);
            }

            public fromDto(dto: AttendanceItemDto) {
                this.id = dto.id;
                this.operator(dto.operator);
            }

            public toDto(): AttendanceItemDto {
                let self = this;
                let dto: AttendanceItemDto = <AttendanceItemDto>{};

                dto.id = this.id;
                dto.operator = this.operator();

                return dto;
            }
        }

        /**
         * Rounding
         */
        class Rounding {
            numberRounding: KnockoutObservable<NumberRounding>;
            numberUnit: KnockoutObservable<NumberUnit>;
            amountRounding: KnockoutObservable<AmountRounding>;
            amountUnit: KnockoutObservable<AmountUnit>;
            timeRounding: KnockoutObservable<TimeRounding>;
            timeUnit: KnockoutObservable<TimeUnit>;

            constructor() {
                this.numberRounding = ko.observable(1);
                this.numberUnit = ko.observable(1);
                this.amountRounding = ko.observable(1);
                this.amountUnit = ko.observable(1);
                this.timeRounding = ko.observable(1);
                this.timeUnit = ko.observable(1);
            }

            public fromDto(dto: RoundingDto) {
                this.numberRounding(dto.numberRounding);
                this.numberUnit(dto.numberUnit);
                this.amountRounding(dto.amountRounding);
                this.amountUnit(dto.amountUnit);
                this.timeRounding(dto.timeRounding);
                this.timeUnit(dto.timeUnit);
            }

            public toDto(): RoundingDto {
                let self = this;
                let dto: RoundingDto = <RoundingDto>{};

                dto.numberRounding = this.numberRounding();
                dto.numberUnit = this.numberUnit();
                dto.amountRounding = this.amountRounding();
                dto.amountUnit = this.amountUnit();
                dto.timeRounding = this.timeRounding();
                dto.timeUnit = this.timeUnit();

                return dto;
            }
        }

    }
}