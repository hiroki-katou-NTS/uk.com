module nts.uk.at.view.kmk002.a {
    import OptionalItemDto = nts.uk.at.view.kmk002.a.service.model.OptionalItemDto;
    import OptionalItemHeaderDto = nts.uk.at.view.kmk002.a.service.model.OptionalItemHeader;
    import CalcResultRangeDto = nts.uk.at.view.kmk002.a.service.model.CalcResultRangeDto;
    import FormulaDto = nts.uk.at.view.kmk002.a.service.model.FormulaDto;
    import CalcFormulaSettingDto = nts.uk.at.view.kmk002.a.service.model.CalcFormulaSettingDto;
    import FormulaSettingDto = nts.uk.at.view.kmk002.a.service.model.FormulaSettingDto;
    import ItemSelectionDto = nts.uk.at.view.kmk002.a.service.model.ItemSelectionDto;
    import SettingItemDto = nts.uk.at.view.kmk002.a.service.model.SettingItemDto;;
    import AttendanceItemDto = nts.uk.at.view.kmk002.a.service.model.AttendanceItemDto;;
    import RoundingDto = nts.uk.at.view.kmk002.a.service.model.RoundingDto;;
    import TypeAtr = nts.uk.at.view.kmk002.a.service.model.TypeAtr;
    import OptItemEnumDto = nts.uk.at.view.kmk002.a.service.model.OptItemEnumDto;;
    import FormulaEnumDto = nts.uk.at.view.kmk002.a.service.model.FormulaEnumDto;;

    export module viewmodel {

        export class ScreenModel {
            optionalItemHeader: OptionalItemHeader;

            constructor() {
                let self = this;
                self.optionalItemHeader = new OptionalItemHeader();

            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                nts.uk.ui.block.invisible();
                $.when(self.loadEnum(),
                    self.optionalItemHeader.loadOptionalItemHeaders().done(res => {
                        self.optionalItemHeader.initialize();
                        dfd.resolve();
                    }).always(() => nts.uk.ui.block.clear()));

                return dfd.promise();
            }

            private loadEnum(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                $.when(service.getFormulaEnum(),
                    service.getOptItemEnum()).done((formulaEnum, optItemEnum) => {
                        Enums.optItemEnum = optItemEnum;
                        Enums.formulaEnum = formulaEnum;
                        dfd.resolve();
                    });
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
            optionalItemNo: KnockoutObservable<string>;
            optionalItemName: KnockoutObservable<string>;
            optionalItemAtr: KnockoutObservable<number>;
            usageAtr: KnockoutObservable<number>;
            empConditionAtr: KnockoutObservable<number>;
            performanceAtr: KnockoutObservable<number>;
            calcResultRange: CalculationResultRange;
            calcFormulas: Array<Formula>;
            applyFormula: KnockoutObservable<string>;

            // Switch button data source
            usageClsDatasource: KnockoutObservableArray<any>;
            empConClsDatasource: KnockoutObservableArray<any>;
            perfClsDatasource: KnockoutObservableArray<any>;
            atrDataSource: KnockoutObservableArray<any>;

            // flag
            hasChanged: boolean;

            // stash
            optionalItemAtrStash: number;
            performanceAtrStash: number;

            constructor() {
                this.optionalItemNo = ko.observable('');
                this.optionalItemName = ko.observable('');
                this.optionalItemAtr = ko.observable(1);
                this.usageAtr = ko.observable(1);
                this.empConditionAtr = ko.observable(1);
                this.performanceAtr = ko.observable(1);
                this.calcResultRange = new CalculationResultRange();
                this.calcFormulas = new Array<Formula>();
                this.applyFormula = ko.observable('test');
                this.hasChanged = false;

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
                    { code: '0', name: nts.uk.resource.getText("KMK002_23") }, // monthly
                    { code: '1', name: nts.uk.resource.getText("KMK002_22") } // daily
                ]);
                this.atrDataSource = ko.observableArray([
                    //TODO: lay tren server: EnumAdaptor.convertToValueNameList
                    { code: '0', name: '回数' }, // TIMES
                    { code: '1', name: '金額' }, // AMOUNT
                    { code: '2', name: '時間' } // TIME
                ]);

                // subscribe

                this.optionalItemNo.subscribe(v => {
                    this.hasChanged = true;
                });

                // Sua phan loai thanh tich
                this.performanceAtr.subscribe(value => {
                    if (this.hasChanged) {
                        return;
                    }
                    // Neu co formulas roi.
                    if (this.isFormulaSet()) {
                        nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage('Msg_506')).ifYes(() => {

                            // xoa het formulas.
                            this.calcFormulas = [];

                            // reload nts grid
                            this.initNtsGrid();

                            // save new value to stash
                            this.performanceAtrStash = this.performanceAtr();

                        }).ifNo(() => {
                            // de nguyen gia tri cu.
                            this.performanceAtr(this.performanceAtrStash);
                        });
                    }
                });

                // Sua phan loai thuoc tinh
                this.optionalItemAtr.subscribe(value => {
                    if (this.hasChanged || this.optionalItemAtr() == this.optionalItemAtrStash) {
                        return;
                    }
                    // Check xem co formulas va pham vi tinh toan chua
                    if (this.isFormulaSet() || this.calcResultRange.isSet(this.optionalItemAtr())) {
                        nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage('Msg_573')).ifYes(() => {

                            // xoa het formulas.
                            this.calcFormulas = [];

                            // reload nts grid
                            this.initNtsGrid();

                            // reset calc result range.
                            this.calcResultRange.resetValue();

                            // save new value to stash
                            this.optionalItemAtrStash = this.optionalItemAtr();

                        }).ifNo(() => {
                            // de nguyen gia tri cu.
                            this.optionalItemAtr(this.optionalItemAtrStash);
                        });
                    }
                });
            }

            /**
             * Add formula above
             */
            public addFormulaAbove(): void {
                let self = this;
                let selectedId = '';

                let arr = [];
                let order = 0;

                for (var i = 97; i <= 122; i++) {
                    arr.push({ order: order, value: String.fromCharCode(i) });
                    order++;
                }

                for (var i = 97; i <= 122; i++) {
                    for (var j = 97; j <= 122; j++) {
                        arr.push({ order: order, value: String.fromCharCode(i) + String.fromCharCode(j) });
                        order++;
                    }
                }
                console.log(arr);
                self.calcFormulas.push(new Formula());

                // reload nts grid.
                self.initNtsGrid();
            }

            /**
             * Add formula below
             */
            public addFormulaBelow(): void {
                let self = this;
                self.calcFormulas.push(new Formula());

                // reload nts grid.
                self.initNtsGrid();
            }

            /**
             * Add formula below
             */
            public removeFormula(): void {
                let self = this;
                let id = ''; //selected id.
                _.remove(self.calcFormulas, item => item.formulaId == id);
            }

            /**
             * Update nts grid.
             */
            public updateNtsGrid(): void {
                let self = this;
                _.each(self.calcFormulas, item => {
                    let data = item.toDto();
                    delete data.formulaId;
                    $("#tbl-calc-formula").ntsGrid("updateRow", item.formulaId, data);
                });
            }

            /**
             * Open dialog C
             */
            public openDialogC(): void {
                //TODO move to formula view model later
                nts.uk.ui.windows.sub.modal('/view/kmk/002/c/index.xhtml');
            }

            /**
             * Open dialog D
             */
            public openDialogD(): void {
                //TODO move to formula view model later
                let self = this;
                let dto = new FormulaSetting().toDto();
                nts.uk.ui.windows.setShared('formulaParams', dto);
                nts.uk.ui.windows.sub.modal('/view/kmk/002/d/index.xhtml').onClosed(() => {
                    let dto = nts.uk.ui.windows.getShared("formulaReturned");
                    //TODO: lay gia tri tra ve
                });
            }

            /**
             * Load formula
             */
            public loadFormulas(itemNo: string): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.findFormulas(itemNo).done(res => {
                    let list: Array<Formula> = res.map(item => {
                        let formula = new Formula();
                        formula.fromDto(item);

                        //TODO remove later.
                        formula.optionalItemNo = itemNo;

                        return formula;
                    });
                    self.calcFormulas = list;

                    // init nts grid.
                    self.initNtsGrid();

                    dfd.resolve();
                });
                return dfd.promise();

            }

            public isFormulaSet(): boolean {
                //TODO check if calculation formula is set.
                if (this.calcFormulas.length > 0) {
                    return true;
                }
                return false;
            }

            /**
             * Init NtsGrid
             */
            public initNtsGrid(): void {
                let self = this;

                // data source
                let comboColumns = [{ prop: 'localizedName', length: 10 }];

                $("#tbl-calc-formula").ntsGrid({
                    width: '1000px',
                    height: '300px',
                    dataSource: self.calcFormulas,
                    primaryKey: 'formulaId',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: [
                        { headerText: 'ID', key: 'formulaId', dataType: 'string', width: '50px', hidden: true },
                        { headerText: nts.uk.resource.getText('KMK002_33'), key: 'symbolValue', dataType: 'string', width: '50px' },
                        { headerText: nts.uk.resource.getText('KMK002_24'), key: 'formulaAtr', dataType: 'number', width: '100px', ntsControl: 'FormulaAtr' },
                        { headerText: nts.uk.resource.getText('KMK002_34'), key: 'formulaName', dataType: 'string', width: '150px' },
                        {
                            headerText: nts.uk.resource.getText('KMK002_35'), key: 'ahihi', group: [
                                { headerText: '', headerCssClass: 'hidden', key: 'calcAtr', dataType: 'number', width: '200px', ntsControl: 'SwitchButtons' },
                                { headerText: '', headerCssClass: 'hidden', key: 'open', dataType: 'string', width: '80px', unbound: true, ntsControl: 'Button' },
                                { headerText: '', headerCssClass: 'hidden', key: 'formulaName', dataType: 'string', width: '150px' },
                            ]
                        },
                        { headerText: nts.uk.resource.getText('KMK002_36'), key: 'dailyUnit', dataType: 'number', width: '100px', ntsControl: 'DailyUnit' },
                        { headerText: nts.uk.resource.getText('KMK002_37'), key: 'dailyRounding', dataType: 'number', width: '100px', ntsControl: 'DailyRounding' },
                        { headerText: nts.uk.resource.getText('KMK002_38'), key: 'monthlyUnit', dataType: 'number', width: '100px', ntsControl: 'MonthlyUnit' },
                        { headerText: nts.uk.resource.getText('KMK002_39'), key: 'monthlyRounding', dataType: 'number', width: '100px', ntsControl: 'MonthlyRounding' }
                    ],
                    features: [
                        { name: 'MultiColumnHeaders' },
                        {
                            name: "Selection",
                            mode: "row",
                            multipleSelection: true,
                            enableCheckBoxes: true,
                            activation: true
                        }],
                    ntsFeatures: [{ name: 'CopyPaste' }],
                    ntsControls: [
                        {
                            name: 'SwitchButtons', options: Enums.formulaEnum.calcAtr,
                            optionsValue: 'value', optionsText: 'localizedName', controlType: 'SwitchButtons', enable: true
                        },
                        { name: 'FormulaAtr', options: Enums.formulaEnum.formulaAtr, optionsValue: 'value', optionsText: 'localizedName', columns: comboColumns, controlType: 'ComboBox', enable: true },
                        { name: 'DailyUnit', options: Enums.formulaEnum.timeRounding.unit, optionsValue: 'value', optionsText: 'localizedName', columns: comboColumns, controlType: 'ComboBox', enable: true },
                        { name: 'DailyRounding', options: Enums.formulaEnum.timeRounding.rounding, optionsValue: 'value', optionsText: 'localizedName', columns: comboColumns, controlType: 'ComboBox', enable: true },
                        { name: 'MonthlyUnit', options: Enums.formulaEnum.timeRounding.unit, optionsValue: 'value', optionsText: 'localizedName', columns: comboColumns, controlType: 'ComboBox', enable: true },
                        { name: 'MonthlyRounding', options: Enums.formulaEnum.timeRounding.rounding, optionsValue: 'value', optionsText: 'localizedName', columns: comboColumns, controlType: 'ComboBox', enable: true },
                        { name: 'Button', text: 'Open', click: self.openDialogD, controlType: 'Button' }
                    ]
                });
            }

            /**
             * Open dialog B
             */
            public openDialogB(): void {
                let self = this;
                nts.uk.ui.windows.setShared('paramForB', self.optionalItemNo());
                nts.uk.ui.windows.sub.modal('/view/kmk/002/b/index.xhtml');
            }

            public toDto(): OptionalItemDto {
                let self = this;
                let dto: OptionalItemDto = <OptionalItemDto>{};

                dto.optionalItemNo = self.optionalItemNo();
                dto.optionalItemName = self.optionalItemName();
                dto.optionalItemAtr = self.optionalItemAtr();
                dto.usageAtr = self.usageAtr();
                dto.empConditionAtr = self.empConditionAtr();
                dto.performanceAtr = self.performanceAtr();
                dto.calcResultRange = self.calcResultRange.toDto();

                return dto;
            }

            public fromDto(dto: OptionalItemDto): void {
                let self = this;
                self.optionalItemNo(dto.optionalItemNo);
                self.optionalItemName(dto.optionalItemName);
                self.optionalItemAtr(dto.optionalItemAtr);
                self.usageAtr(dto.usageAtr);
                self.empConditionAtr(dto.empConditionAtr);
                self.performanceAtr(dto.performanceAtr);
                self.calcResultRange.fromDto(dto.calcResultRange);

                // Stash
                self.optionalItemAtrStash = dto.optionalItemAtr;
                self.performanceAtrStash = dto.performanceAtr;

                // reset flag.
                self.hasChanged = false;
            }

            public isTimeSelected(): boolean {
                let self = this;
                if (self.optionalItemAtr() == TypeAtr.TIME) {
                    return true;
                }
                return false;
            }
            public isNumberSelected(): boolean {
                let self = this;
                if (self.optionalItemAtr() == TypeAtr.NUMBER) {
                    return true;
                }
                return false;
            }
            public isAmountSelected(): boolean {
                let self = this;
                if (self.optionalItemAtr() == TypeAtr.AMOUNT) {
                    return true;
                }
                return false;
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
                this.numberUpper = ko.observable(3);
                this.numberLower = ko.observable(3);
                this.amountUpper = ko.observable(2);
                this.amountLower = ko.observable(2);
                this.timeUpper = ko.observable(1);
                this.timeLower = ko.observable(1);
            }

            /**
             * Check if limit range is set.
             */
            public isSet(itemAtr: number): boolean {
                let self = this;
                if (self.upperCheck() || self.lowerCheck()) {
                    return true;
                }
                return false;

            }

            public resetValue(): void {
                let self = this;
                this.upperCheck(false);
                this.lowerCheck(false);
                this.numberUpper(null);
                this.numberLower(null);
                this.amountUpper(null);
                this.amountLower(null);
                this.timeUpper(null);
                this.timeLower(null);
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
         * The class Optional Item Header
         */
        class OptionalItemHeader {
            columns: any;
            selectedCode: KnockoutObservable<string>;
            optionalItemHeaders: KnockoutObservableArray<OptionalItemHeaderDto>;
            optionalItem: OptionalItem;

            constructor() {
                let self = this;
                self.optionalItemHeaders = ko.observableArray([]);
                self.optionalItem = new OptionalItem();

                self.selectedCode = ko.observable('');
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KMK002_7'), key: 'itemNo', width: 50 },
                    { headerText: nts.uk.resource.getText('KMK002_8'), key: 'itemName', width: 100 },
                    { headerText: nts.uk.resource.getText('KMK002_9'), key: 'performanceAtr', width: 50 },
                    { headerText: nts.uk.resource.getText('KMK002_10'), key: 'usageAtr', width: 50 }
                ]);

            }

            public initialize(): void {
                let self = this;
                let itemNo = self.optionalItemHeaders()[0].itemNo;
                // Select first item
                self.selectedCode(itemNo);
                self.loadOptionalItemDetail(itemNo).done(() => {
                    // init selected code subscribe
                    self.selectedCode.subscribe(itemNo => {
                        if (itemNo) {
                            self.loadOptionalItemDetail(itemNo);
                        }
                    });
                });
            }

            /**
             * Save optional item detail.
             */
            public saveOptionalItemDetail(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                nts.uk.ui.block.invisible();

                let command = self.optionalItem.toDto();
                service.saveOptionalItem(command).done(() => {
                    dfd.resolve();
                }).fail().always(() => nts.uk.ui.block.clear());

                let test: Array<FormulaDto> = self.optionalItem.calcFormulas.map(item => {
                    return item.toDto();
                });
                service.saveFormula(test);

                return dfd.promise();
            }

            /**
             * Load optional item headers
             */
            public loadOptionalItemHeaders(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.findOptionalItemHeaders().done(res => {
                    self.optionalItemHeaders(res);
                    dfd.resolve();
                });
                return dfd.promise();

            }

            /**
             * Load optional item detail.
             */
            private loadOptionalItemDetail(itemNo: string): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                service.findOptionalItemDetail(itemNo).done(res => {
                    self.optionalItem.fromDto(res);
                    self.optionalItem.loadFormulas(itemNo).done(() => dfd.resolve());
                });

                return dfd.promise();
            }
        }

        /**
         * The class Formula
         */
        class Formula {
            formulaId: string;
            optionalItemNo: string;
            formulaName: string;
            formulaAtr: number;
            symbolValue: string;
            orderNo: number;

            // Calculation setting
            calcAtr: number;
            formulaSetting: FormulaSetting;
            itemSelection: ItemSelection;

            //Rounding
            monthlyRounding: number;
            monthlyUnit: number;
            dailyRounding: number;
            dailyUnit: number;

            constructor() {
                this.formulaId = nts.uk.util.randomId();
                this.optionalItemNo = '001';
                this.formulaName = 'asdvxzc';
                this.formulaAtr = 1;
                this.symbolValue = 'aa';
                this.orderNo = 1;

                // Calculation setting.
                this.calcAtr = 1;
                this.formulaSetting = new FormulaSetting();
                this.itemSelection = new ItemSelection();

                // Rounding
                this.monthlyRounding = 1;
                this.monthlyUnit = 1;
                this.dailyRounding = 1;
                this.dailyUnit = 1;

                //TODO dang test.
                // Sua phan loai thuoc tinh
                //                this.formulaAtr.subscribe(value => {
                //                    // Kiem tra formula nay co cai dat chua
                //                    //if (self.calcFormulas.length > 0) {
                //                    if (1 > 0) { //test
                //                        nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage('Msg_192')).ifYes(() => {
                //                            // xoa cai dat
                //                            // cap nhat gia tri moi.
                //                        }).ifNo(() => {
                //                            // de nguyen gia tri cu.
                //                        });
                //                    }
                //                });

                // Sua phan loai tinh toan
                //                this.calcAtr.subscribe(value => {
                //                    // Kiem tra formula nay co cai dat chua
                //                    //if (self.calcFormulas.length > 0) {
                //                    if (1 > 0) { //test
                //                        nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage('Msg_126')).ifYes(() => {
                //                            // xoa cai dat
                //                            // cap nhat gia tri moi.
                //                        }).ifNo(() => {
                //                            // de nguyen gia tri cu.
                //                        });
                //                    }
                //                });
            }

            public toDto(): FormulaDto {
                let self = this;
                let dto: FormulaDto = <FormulaDto>{};

                dto.formulaId = self.formulaId;
                dto.optionalItemNo = self.optionalItemNo;
                dto.formulaName = self.formulaName;
                dto.formulaAtr = self.formulaAtr;
                dto.symbolValue = self.symbolValue;

                // Calc setting
                let calcSetting = <CalcFormulaSettingDto>{};
                calcSetting.calcAtr = self.calcAtr;
                calcSetting.formulaSetting = self.formulaSetting.toDto();
                calcSetting.itemSelection = self.itemSelection.toDto();
                dto.calcFormulaSetting = calcSetting;

                // Rounding
                //TODO mock data.
                let monthly = <RoundingDto>{};
                monthly.numberRounding = self.monthlyRounding;
                monthly.numberUnit = self.monthlyUnit;
                monthly.timeRounding = self.monthlyRounding;
                monthly.timeUnit = self.monthlyUnit;
                monthly.amountRounding = self.monthlyRounding;
                monthly.amountUnit = self.monthlyUnit;

                let daily = <RoundingDto>{};
                daily.numberRounding = self.dailyRounding;
                daily.numberUnit = self.dailyUnit;
                daily.timeRounding = self.dailyRounding;
                daily.timeUnit = self.dailyUnit;
                daily.amountRounding = self.dailyRounding;
                daily.amountUnit = self.dailyUnit;

                dto.monthlyRounding = monthly;
                dto.dailyRounding = daily;

                return dto;
            }

            public fromDto(dto: FormulaDto): void {
                let self = this;
                self.formulaId = dto.formulaId;
                self.optionalItemNo = dto.optionalItemNo;
                self.formulaName = dto.formulaName;
                self.formulaAtr = dto.formulaAtr;
                self.symbolValue = dto.symbolValue;

                //TODO testing.
                // Calc setting
                self.calcAtr = 1;
                //self.formulaSetting.fromDto(dto.calcFormulaSetting.formulaSetting);
                //self.itemSelection.fromDto(dto.calcFormulaSetting.itemSelection);

                // rounding
                //                self.monthlyRounding = dto.monthlyRounding.numberRounding;
                //                self.monthlyUnit = dto.monthlyRounding.numberUnit;
                //                self.dailyRounding = dto.dailyRounding.numberRounding;
                //                self.dailyUnit = dto.dailyRounding.numberUnit;

            }
        }

        /**
         * Formula setting.
         */
        export class FormulaSetting {
            minusSegment: KnockoutObservable<number>;
            operator: KnockoutObservable<number>;
            leftItem: FormulaSettingItem;
            rightItem: FormulaSettingItem;

            operatorDatasource: KnockoutObservableArray<any>;

            constructor() {
                this.minusSegment = ko.observable(0);
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

            public fromDto(dto: FormulaSettingDto): void {
                let self = this;
                self.minusSegment(dto.minusSegment);
                self.operator(dto.operator);
                self.leftItem.fromDto(dto.leftItem);
                self.rightItem.fromDto(dto.rightItem);
            }

            public toDto(): FormulaSettingDto {
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
            settingMethod: KnockoutObservable<number>;
            dispOrder: number;
            inputValue: KnockoutObservable<number>;
            formulaItemId: KnockoutObservable<string>;

            constructor() {
                this.settingMethod = ko.observable(1);
                this.dispOrder = 1;
                this.inputValue = ko.observable(1);
                this.formulaItemId = ko.observable(nts.uk.util.randomId());
            }

            public isInputValue(): boolean {
                if (this.settingMethod() == 0) {
                    return false;
                }
                return true;
            }

            public fromDto(dto: SettingItemDto): void {
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
            minusSegment: KnockoutObservable<number>;
            attendanceItems: Array<AttendanceItem>;

            constructor() {
                this.minusSegment = ko.observable(1);
                this.attendanceItems = []; //TODO
            }

            public fromDto(dto: ItemSelectionDto): void {
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
            operator: KnockoutObservable<number>;

            constructor() {
                this.id = 'asdf';
                this.operator = ko.observable(1);
            }

            public fromDto(dto: AttendanceItemDto): void {
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

        class Enums {
            static optItemEnum: OptItemEnumDto;
            static formulaEnum: FormulaEnumDto;
        }

    }
}