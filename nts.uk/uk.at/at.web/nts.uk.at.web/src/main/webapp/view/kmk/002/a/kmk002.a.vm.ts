module nts.uk.at.view.kmk002.a {
    import OptionalItemDto = nts.uk.at.view.kmk002.a.service.model.OptionalItemDto;
    import OptionalItemHeaderDto = nts.uk.at.view.kmk002.a.service.model.OptionalItemHeader;
    import CalcResultRangeDto = nts.uk.at.view.kmk002.a.service.model.CalcResultRangeDto;
    import FormulaDto = nts.uk.at.view.kmk002.a.service.model.FormulaDto;
    import FormulaSettingDto = nts.uk.at.view.kmk002.a.service.model.FormulaSettingDto;
    import ItemSelectionDto = nts.uk.at.view.kmk002.a.service.model.ItemSelectionDto;
    import SettingItemDto = nts.uk.at.view.kmk002.a.service.model.SettingItemDto;
    import AttendanceItemDto = nts.uk.at.view.kmk002.a.service.model.AttendanceItemDto;
    import RoundingDto = nts.uk.at.view.kmk002.a.service.model.RoundingDto;
    import TypeAtr = nts.uk.at.view.kmk002.a.service.model.TypeAtr;
    import OptItemEnumDto = nts.uk.at.view.kmk002.a.service.model.OptItemEnumDto;
    import FormulaEnumDto = nts.uk.at.view.kmk002.a.service.model.FormulaEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk002.a.service.model.EnumConstantDto;
    import EnumAdaptor = nts.uk.at.view.kmk002.a.service.model.EnumAdaptor;

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

                // init formula sorter.
                FormulaSorter.initSorter();

                // Load data.
                self.loadEnum()
                    .done(() => self.optionalItemHeader.loadOptionalItemHeaders()
                        .done(() => self.optionalItemHeader.initialize()
                            .done(() => dfd.resolve())))
                    .always(() => nts.uk.ui.block.clear());

                return dfd.promise();
            }

            private loadEnum(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                $.when(service.getFormulaEnum(), service.getOptItemEnum())
                    .done((formulaEnum: FormulaEnumDto, optItemEnum: OptItemEnumDto) => {
                        Enums.ENUM_OPT_ITEM = optItemEnum;
                        Enums.ENUM_FORMULA = formulaEnum;
                        dfd.resolve();
                    });
                return dfd.promise();
            }

        }

        //******************************************************************
        //************************* VIEW MODEL *****************************
        //******************************************************************

        /**
         * The class OptionalItem
         */
        class OptionalItem {
            static selectedFormulas: KnockoutObservableArray<number>;

            optionalItemNo: KnockoutObservable<string>;
            optionalItemName: KnockoutObservable<string>;
            optionalItemAtr: KnockoutObservable<number>;
            usageAtr: KnockoutObservable<number>;
            empConditionAtr: KnockoutObservable<number>;
            performanceAtr: KnockoutObservable<number>;
            calcResultRange: CalculationResultRange;
            calcFormulas: KnockoutObservableArray<Formula>;
            applyFormula: KnockoutObservable<string>;
            selectedFormulaAbove: number;
            selectedFormulaBelow: number;

            // Switch button data source
            usageClsDatasource: KnockoutObservableArray<any>;
            empConClsDatasource: KnockoutObservableArray<any>;
            perfClsDatasource: KnockoutObservableArray<any>;
            atrDataSource: EnumConstantDto[];

            // flag
            hasChanged: boolean;
            isUsed: KnockoutObservable<boolean>;
            checkedAllFormula: KnockoutObservable<boolean>;
            isCheckedFromChild = false;

            // stash
            optionalItemAtrStash: number;
            performanceAtrStash: number;

            constructor() {
                this.optionalItemNo = ko.observable('');
                this.optionalItemName = ko.observable('');
                this.optionalItemAtr = ko.observable(0);
                this.usageAtr = ko.observable(0);
                this.empConditionAtr = ko.observable(0);
                this.performanceAtr = ko.observable(0);
                this.calcResultRange = new CalculationResultRange();
                this.calcFormulas = ko.observableArray<Formula>([]);
                this.applyFormula = ko.observable('test');
                this.hasChanged = false;
                this.isUsed = ko.observable(false);
                this.checkedAllFormula = ko.observable(false);
                OptionalItem.selectedFormulas = ko.observableArray([]);
                this.selectedFormulaAbove = 0;
                this.selectedFormulaBelow = 0;

                // init datasource
                this.initDatasource();

                // init subscribe
                this.initSubscribe();

            }

            /**
             * Initial datasource
             */
            private initDatasource(): void {
                this.usageClsDatasource = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KMK002_15") }, // not used
                    { code: 1, name: nts.uk.resource.getText("KMK002_14") } // used
                ]);
                this.empConClsDatasource = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KMK002_17") }, // not apply
                    { code: 1, name: nts.uk.resource.getText("KMK002_18") } // apply
                ]);
                this.perfClsDatasource = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KMK002_23") }, // monthly
                    { code: 1, name: nts.uk.resource.getText("KMK002_22") } // daily
                ]);
                this.atrDataSource = [];
            }

            /**
             * Initial subscribe
             */
            private initSubscribe(): void {
                this.checkedAllFormula.subscribe(vl => {

                    // if the value is changed because of a child element (formula) then do nothing.
                    if (this.isCheckedFromChild == true) {

                        // reset flag.
                        this.isCheckedFromChild = false;

                        // return and do nothing.
                        return;
                    }

                    // check all unchecked formula
                    if(vl === true) {
                        _.each(this.calcFormulas(), item => {
                            if (item.selected() == false) {
                                item.isCheckFromParent = true
                                item.check();
                            }
                        });
                        return;
                    }

                    // uncheck all checked formula
                    if(vl === false) {
                        _.each(this.calcFormulas(), item => {
                            if (item.selected() == true) {
                                item.isCheckFromParent = true
                                item.uncheck();
                            }
                        });
                    }
                });

                // selected subscribe
                OptionalItem.selectedFormulas.subscribe(vl => {
                    // set selected formula below and above.
                    this.setSelectedFormulaBelowAndAbove(vl);

                    console.log(vl);
                    //console.log('above='+this.selectedFormulaAbove);
                    //console.log('below='+this.selectedFormulaBelow);
                    console.log('\n');
                });

                this.optionalItemNo.subscribe(v => {
                    this.hasChanged = true;
                });

                // Event on performanceAtr value changed
                this.performanceAtr.subscribe(value => {

                    // if value change because of select new optional item
                    // or new value == value in stash
                    // then do nothing
                    if (this.hasChanged || this.performanceAtr() == this.performanceAtrStash) {
                        return;
                    }

                    // if has formulas
                    if (this.isFormulaSet()) {
                        nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage('Msg_506')).ifYes(() => {

                            // remove all formulas
                            this.calcFormulas([]);

                            // save new value to stash
                            this.performanceAtrStash = this.performanceAtr();

                        }).ifNo(() => {
                            // get old value from stash
                            this.performanceAtr(this.performanceAtrStash);
                        });
                    }
                });

                // Event on optionalItemAtr value changed
                this.optionalItemAtr.subscribe(value => {

                    // if value change because of select new optional item
                    // or new value == value in stash
                    // then do nothing
                    if (this.hasChanged || this.optionalItemAtr() == this.optionalItemAtrStash) {
                        return;
                    }

                    // Check whether has formula or calculation result range is set.
                    if (this.isFormulaSet() || this.calcResultRange.isSet(this.optionalItemAtr())) {
                        nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage('Msg_573')).ifYes(() => {

                            // remove all formulas
                            this.calcFormulas([]);

                            // reset calc result range.
                            this.calcResultRange.resetValue();

                            // save new value to stash
                            this.optionalItemAtrStash = this.optionalItemAtr();

                        }).ifNo(() => {
                            // get old value from stash
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

                // check before add
                // if zz is used
                // or list formula has at least 1 item and no formula checked
                // => show error message and return
                if (!self.canAddFormula() && self.isFormulaSet()) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_508' });
                    return;
                }

                let aboveOrder = this.selectedFormulaAbove;

                let f = new Formula();

                // bind function
                f.reCheckAll = self.reCheckAll.bind(self);

                // Set order
                f.orderNo = aboveOrder;
                // Set symbol
                f.symbolValue = FormulaSorter.getNextSymbolOf(self.getLastSymbol());

                // TODO move ra cho khac sau.
                f.optionalItemNo = self.optionalItemNo();

                // update order of below items.
                self.updateOrderAfter(aboveOrder - 1);

                // add new formula
                self.calcFormulas.push(f);

                // sort by orderNo
                self.sortListFormula();

                // recheck the checkAll checkbox
                self.reCheckAll();

            }

            /**
             * Check / unchecked the check all Checkbox.
             */
            private reCheckAll(): void {
                let self = this;

                // is checked from child flag.
                self.isCheckedFromChild = true;

                if (self.isAllFormulaChecked() == true) {
                    self.checkedAllFormula(true);
                } else {
                    self.checkedAllFormula(false);
                }

            }

            /**
             * Check whether all formulas are checked
             */
            private isAllFormulaChecked(): boolean {
                let self = this;
                let checked = true;

                _.each(self.calcFormulas(), item => {
                    if (item.selected() == false) {
                        checked = false;
                    }
                });

                return checked;
            }

            /**
             * Check whether all formulas are unchecked
             */
            private isAllFormulaUnChecked(): boolean {
                let self = this;
                let unchecked = true;

                _.each(self.calcFormulas(), item => {
                    if (item.selected() == true) {
                        unchecked = false;
                    }
                });

                return unchecked;
            }

            /**
             * Update order of all formula after selected order
             */
            private updateOrderAfter(orderNo: number): void {
                let self = this;
                self.updateAllFormulaOrder(orderNo);
                self.updateSelectedFormulaOrder(orderNo);
            }

            /**
             * Update order of selected formulas
             */
            private updateSelectedFormulaOrder(orderNo: number): void {
                let self = this;
                let updatedList = OptionalItem.selectedFormulas()
                    .filter(item => item > orderNo)
                    .map(item => item += 1);
                OptionalItem.selectedFormulas(updatedList);
            }

            /**
             * Update order of all formulas
             */
            private updateAllFormulaOrder(orderNo: number): void {
                let self = this;
                let updatedList = self.calcFormulas()
                    .filter(item => item.orderNo > orderNo)
                    .forEach(item => {
                        item.orderNo += 1;
                    });
            }

            /**
             * Add formula below
             */
            public addFormulaBelow(): void {
                 let self = this;

                // check before add
                // if zz is used
                // or list formula has at least 1 item and no formula checked
                // => show error message and return
                if (!self.canAddFormula() && self.isFormulaSet()) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_508' });
                    return;
                }

                let belowOrder = this.selectedFormulaBelow;

                let f = new Formula();

                // bind function
                f.reCheckAll = self.reCheckAll.bind(self);

                // Set order
                f.orderNo = belowOrder;
                // Set symbol
                f.symbolValue = FormulaSorter.getNextSymbolOf(self.getLastSymbol());

                // TODO move ra cho khac sau.
                f.optionalItemNo = self.optionalItemNo();

                // update order of below items.
                self.updateOrderAfter(belowOrder - 1);

                // add new formula
                self.calcFormulas.push(f);

                // sort by orderNo
                self.sortListFormula();

                // recheck the checkAll checkbox
                self.reCheckAll();

            }

            /**
             * Confirm the check status of calculation formula
             */
            private canAddFormula(): boolean {
                let self = this;
                if (self.hasSelectedFormula() && !self.hasReachedZZ()) {
                    return true;
                }
                return false;
            }

            /**
             * Confirm whether a calculation formula can be added (Check whether ZZ is used in the symbol)
             */
            private hasReachedZZ(): boolean {
                return false;
            }

            /**
             * Check if one or more calculation expressions are checked
             */
            private hasSelectedFormula(): boolean {
                let self = this;
                if (nts.uk.util.isNullOrEmpty(OptionalItem.selectedFormulas())) {
                    return false;
                }
                return true;
            }

            /**
             * Check whether the symbol of the line to be deleted is included in the settings of other lines
             */
            private isInUse(): boolean {
                //TODO
                return false;
            }

            /**
             * Sort list formula by order number
             */
            private sortListFormula(): void {
                let self = this;
                // sort by orderNo
                let sortedList = _.sortBy(self.calcFormulas(), item => item.orderNo);
                self.calcFormulas(sortedList);
            }

            /**
             * Add formula below
             */
            public removeFormula(): void {
                let self = this;

                // Check before remove
                if (!self.hasSelectedFormula()) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_508' });
                    return;
                }
                if (self.isInUse()) {
                    nts.uk.ui.dialog.alert({ messageId: 'Msg_113' });
                    return;
                }

                // Remove selected formulas.
                OptionalItem.selectedFormulas().forEach(order => {
                    //_.remove(self.calcFormulas(), item => item.orderNo == order);
                    self.calcFormulas.remove(item => item.orderNo == order);
                });

                // clear selected
                OptionalItem.selectedFormulas([]);

                // reset formula order
                self.resetFormulaOrder();

            }

            /**
             * reset formula order.
             */
            private resetFormulaOrder(): void {
                let self = this;
                let index = 0;
                _.each(self.calcFormulas(), item => {
                    item.orderNo = index + 1;
                    index++;
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

                        // bind function
                        formula.reCheckAll = self.reCheckAll.bind(self);

                        // convert dto to viewmodel
                        formula.fromDto(item);

                        //TODO remove later.
                        formula.optionalItemNo = itemNo;

                        return formula;
                    });
                    self.calcFormulas(list);
                    //console.log(self.calcFormulas());

                    // sort.
                    self.sortListFormula();

                    // force to check enable/disable condition for nts grid.
                    self.usageAtr.valueHasMutated();

                    dfd.resolve();
                });
                return dfd.promise();

            }

            /**
             * check if at least one formula exist 
             */
            public isFormulaSet(): boolean {
                if (!nts.uk.util.isNullOrEmpty(this.calcFormulas()) && this.calcFormulas().length > 0) {
                    return true;
                }
                return false;
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

            /**
             * Convert dto to view model
             */
            public fromDto(dto: OptionalItemDto): void {
                let self = this;
                self.optionalItemNo(dto.optionalItemNo);
                self.optionalItemName(dto.optionalItemName);
                self.optionalItemAtr(dto.optionalItemAtr);
                self.usageAtr(dto.usageAtr);
                self.empConditionAtr(dto.empConditionAtr);
                self.performanceAtr(dto.performanceAtr);
                self.calcResultRange.fromDto(dto.calcResultRange);

                // set data source
                self.atrDataSource = Enums.ENUM_OPT_ITEM.itemAtr;

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

            /**
             * Get last symbol value of list formula.
             */
            private getLastSymbol(): string {
                let self = this;
                let lastSymbol = 'a';
                self.calcFormulas().forEach(item => {
                    if (item.symbolValue.localeCompare(lastSymbol) > 0) {
                        lastSymbol = item.symbolValue;
                    }
                });
                return lastSymbol;
            }

            /**
             * Set selected formula below and above
             */
            private setSelectedFormulaBelowAndAbove(array: Array<number>): void {
                let self = this;
                let above = 1;
                let below = 2;

                array.forEach(order => {

                    // below = selected order
                    if (order >= below) {
                        below = order + 1;
                    }

                    // for single selection
                    // set above = selected order - 1
                    if (order > 1) {
                        above = order;
                    }

                    // for multi selection
                    // set above = lowest selected order
                    if (order < above) {
                        above = order;
                    }
                });

                self.selectedFormulaAbove = above;
                self.selectedFormulaBelow = below;
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

            public initialize(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // Select first item
                let itemNo = self.optionalItemHeaders()[0].itemNo;
                self.selectedCode(itemNo);

                self.loadOptionalItemDetail(itemNo).done(() => {

                    // resolve
                    dfd.resolve();

                    // init usageAtr subscribe.
                    self.optionalItem.usageAtr.subscribe(vl => {
                        if (vl === 1) {
                            self.optionalItem.isUsed(true);
                        } else {
                            self.optionalItem.isUsed(false);
                        }
                    });

                    // force to check enable/disable condition
                    self.optionalItem.usageAtr.valueHasMutated();

                    // init selected code subscribe
                    self.selectedCode.subscribe(itemNo => {
                        if (itemNo) {
                            self.loadOptionalItemDetail(itemNo);
                        }
                    });
                });

                return dfd.promise();
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
                    nts.uk.ui.dialog.info({ messageId: 'Msg_15' });
                }).fail(res => {
                    nts.uk.ui.dialog.alertError(res);
                }).always(() => nts.uk.ui.block.clear());

                // save formulas.
                self.saveFormulas();

                return dfd.promise();
            }

            /**
             * Save formulas.
             */
            private saveFormulas(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // Validate
                if (!self.isListFormulaValid()) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_111' });
                };

                // convert to dtos.
                let formulas: Array<FormulaDto> = self.optionalItem.calcFormulas().map(item => {
                    return item.toDto();
                });

                // set command.
                let command = <service.model.FormulaCommand>{};
                command.optItemNo = self.optionalItem.optionalItemNo();
                command.calcFormulas = formulas;

                // call saveFormula service.
                service.saveFormula(command).done(() => dfd.resolve());

                return dfd.promise();
            }

            /**
             * Checks whether an item of a nonexistent line is set in a formula
             */
            private isListFormulaValid(): boolean {
                // TODO chi toi dong bi loi.
                // xem lai 計算式登録時チェック処理 trong EA.
                if (1 == 1) {
                    return true;
                }
                return false;
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
            formulaName: KnockoutObservable<string>;
            formulaAtr: KnockoutObservable<number>;
            symbolValue: string;
            orderNo: number;

            // Calculation setting
            calcAtr: KnockoutObservable<number>;
            formulaSetting: FormulaSettingDto;
            itemSelection: ItemSelectionDto;

            //Rounding
            monthlyRounding: KnockoutObservable<number>;
            monthlyUnit: KnockoutObservable<number>;
            dailyRounding: KnockoutObservable<number>;
            dailyUnit: KnockoutObservable<number>;

            // flags
            selected: KnockoutObservable<boolean>;
            isUsed = true;
            isCheckFromParent = false;

            // function
            reCheckAll: () => void;

            // Enums datasource
            formulaAtrDs: EnumConstantDto[];
            calcAtrDs: EnumConstantDto[];
            roundingUnitDs: EnumConstantDto[];
            roundingDs: EnumConstantDto[];

            // stash
            formulaAtrStash: number;
            calcAtrStash: number;

            constructor() {
                this.formulaId = nts.uk.util.randomId();
                this.optionalItemNo = '001';
                this.formulaName = ko.observable('asdvxzc');
                this.formulaAtr = ko.observable(1);
                this.symbolValue = 'aa';
                this.orderNo = 1;
                this.selected = ko.observable(false);

                // Calculation setting.
                this.calcAtr = ko.observable(1);
                this.formulaSetting = this.getDefaultFormulaSetting();
                this.itemSelection = this.getDefaultItemSelection();

                // Rounding
                this.monthlyRounding = ko.observable(1);
                this.monthlyUnit = ko.observable(1);
                this.dailyRounding = ko.observable(1);
                this.dailyUnit = ko.observable(1);

                // initial data source
                this.formulaAtrDs = Enums.ENUM_FORMULA.formulaAtr;
                this.calcAtrDs = Enums.ENUM_FORMULA.calcAtr;
                this.roundingUnitDs = Enums.ENUM_FORMULA.timeRounding.unit;
                this.roundingDs = Enums.ENUM_FORMULA.timeRounding.rounding;

                // init subscribe
                this.initSubscribe();
            }

            /**
             * Initial subscribe
             */
            private initSubscribe(): void {
                this.selected.subscribe(vl => {

                    if (this.isCheckFromParent == false) {
                        // check if all formula checked
                        this.reCheckAll();

                    }

                    // add to selected formulas if checked
                    if (vl === true) {
                        OptionalItem.selectedFormulas.push(this.orderNo);
                    }

                    // remove from selected formulas if unchecked
                    if (vl === false) {
                        OptionalItem.selectedFormulas.remove(this.orderNo);
                    }

                    // reset flag
                    this.isCheckFromParent = false;

                });

                // Event on formulaAtr value changed
                this.formulaAtr.subscribe(value => {

                    // if new value == value in stash then do nothing
                    if (this.formulaAtr() == this.formulaAtrStash) {
                        return;
                    }

                    // Check whether the formula has setting or not
                    if (this.hasSetting()) {
                        nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage('Msg_192')).ifYes(() => {
                            // TODO remove the setting
                            // save new value to stash
                            this.formulaAtrStash = this.formulaAtr();
                        }).ifNo(() => {
                            // get old value from stash
                            this.formulaAtr(this.formulaAtrStash);
                        });
                    }
                });

                // Event on calcAtr value changed
                this.calcAtr.subscribe(value => {

                    // if new value == value in stash then do nothing
                    if (this.calcAtr() == this.calcAtrStash) {
                        return;
                    }
                    // Check whether the formula has setting or not
                    if (this.hasSetting()) {
                        nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage('Msg_126')).ifYes(() => {
                            // TODO remove the setting
                            // save new value to stash
                            this.calcAtrStash = this.calcAtr();
                        }).ifNo(() => {
                            // get old value from stash
                            this.calcAtr(this.calcAtrStash);
                        });
                    }
                });
            }

            /**
             * get default formula setting.
             */
            private getDefaultFormulaSetting(): FormulaSettingDto {
                let data = <FormulaSettingDto>{};
                //TODO
                return data;
            }

            /**
             * get default item selection
             */
            private getDefaultItemSelection(): ItemSelectionDto {
                let data = <ItemSelectionDto>{};
                //TODO
                return data;
            }

            /**
             * Check whether formula has setting
             */
            private hasSetting(): boolean {
                //TODO 
                if (1 > 0) {
                    return true;
                }
                return false;
            }

            /**
             * set to selected
             */
            public check(): void {
                let self = this;
                self.selected(true);
            }

            /**
             * set to unselected
             */
            public uncheck(): void {
                let self = this;
                self.selected(false);
            }

            /**
             * Check calculation attribute is formula setting or item selection 
             */
            public isFormulaSettingSelected(): boolean {
                let self = this;
                if (self.calcAtr() == 1) {
                    return true;
                }
                return false;
            }

            /**
            * Open dialog C: Item selection
            */
            public openDialogC(): void {
                let self = this;

                // Set param
                let dto = self.toDto();
                let param = <ParamToC>{};
                param.formulaId = dto.formulaId;
                param.performanceAtr = 1; //TODO ??
                param.formulaAtr = EnumAdaptor.localizedNameOf(dto.formulaAtr, Enums.ENUM_FORMULA.formulaAtr);
                param.formulaName = dto.formulaName;
                param.itemSelection = self.itemSelection;
                nts.uk.ui.windows.setShared('paramToC', param);

                // Open dialog.
                nts.uk.ui.windows.sub.modal('/view/kmk/002/c/index.xhtml');
            }

            /**
             * Open dialog D: Formula setting
             */
            public openDialogD(): void {
                let self = this;

                // set pram.
                let dto = self.toDto();
                let param = <ParamToD>{};
                param.formulaId = dto.formulaId;
                param.performanceAtr = 1; //TODO ??
                param.formulaAtr = EnumAdaptor.localizedNameOf(dto.formulaAtr, Enums.ENUM_FORMULA.formulaAtr);
                param.formulaName = dto.formulaName;
                param.formulaSetting = self.formulaSetting;

                nts.uk.ui.windows.setShared('paramToD', param);

                // open dialog D.
                nts.uk.ui.windows.sub.modal('/view/kmk/002/d/index.xhtml').onClosed(() => {
                    let dto = nts.uk.ui.windows.getShared('returnFromD');
                    //TODO: lay gia tri tra ve
                });
            }

            /**
             * Convert viewmodel to dto
             */
            public toDto(): FormulaDto {
                let self = this;
                let dto: FormulaDto = <FormulaDto>{};

                dto.formulaId = self.formulaId;
                dto.optionalItemNo = self.optionalItemNo;
                dto.orderNo = self.orderNo;
                dto.formulaName = self.formulaName();
                dto.formulaAtr = self.formulaAtr();
                dto.symbolValue = self.symbolValue;
                dto.calcAtr = self.calcAtr();
                dto.formulaSetting = self.formulaSetting;
                dto.itemSelection = self.itemSelection;

                // Rounding
                //TODO mock data.
                let monthly = <RoundingDto>{};
                monthly.numberRounding = self.monthlyRounding();
                monthly.numberUnit = self.monthlyUnit();
                monthly.timeRounding = self.monthlyRounding();
                monthly.timeUnit = self.monthlyUnit();
                monthly.amountRounding = self.monthlyRounding();
                monthly.amountUnit = self.monthlyUnit();

                let daily = <RoundingDto>{};
                daily.numberRounding = self.dailyRounding();
                daily.numberUnit = self.dailyUnit();
                daily.timeRounding = self.dailyRounding();
                daily.timeUnit = self.dailyUnit();
                daily.amountRounding = self.dailyRounding();
                daily.amountUnit = self.dailyUnit();

                dto.monthlyRounding = monthly;
                dto.dailyRounding = daily;

                return dto;
            }

            /**
             * Convert dto to viewmodel
             */
            public fromDto(dto: FormulaDto): void {
                let self = this;
                self.formulaId = dto.formulaId;
                self.optionalItemNo = dto.optionalItemNo;
                self.formulaName(dto.formulaName);
                self.formulaAtr(dto.formulaAtr);
                self.symbolValue = dto.symbolValue;

                // save to stash
                self.formulaAtrStash = dto.formulaAtr;
                self.calcAtrStash = dto.calcAtr;

                // Calc setting
                self.calcAtr(dto.calcAtr);
                self.formulaSetting = dto.formulaSetting;
                self.itemSelection = dto.itemSelection;

                // rounding
                //                self.monthlyRounding = dto.monthlyRounding.numberRounding;
                //                self.monthlyUnit = dto.monthlyRounding.numberUnit;
                //                self.dailyRounding = dto.dailyRounding.numberRounding;
                //                self.dailyUnit = dto.dailyRounding.numberUnit;

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

            /**
             * convert dto to viewmodel
             */
            public fromDto(dto: ItemSelectionDto): void {
                this.minusSegment(dto.minusSegment);
                this.attendanceItems = []; //TODO
            }

            /**
             * convert viewmodel to dto
             */
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
                this.id = nts.uk.util.randomId();
                this.operator = ko.observable(1);
            }

            /**
             * Convert dto to view model
             */
            public fromDto(dto: AttendanceItemDto): void {
                this.id = dto.id;
                this.operator(dto.operator);
            }

            /**
             * Convert viewmodel to dto
             */
            public toDto(): AttendanceItemDto {
                let self = this;
                let dto: AttendanceItemDto = <AttendanceItemDto>{};

                dto.id = this.id;
                dto.operator = this.operator();

                return dto;
            }
        }

        class Enums {
            static ENUM_OPT_ITEM: OptItemEnumDto;
            static ENUM_FORMULA: FormulaEnumDto;
        }

        class FormulaSorter {
            static SORTER: Array<Sorter>;

            /**
             * initial sorter
             */
            public static initSorter(): void {
                let arr = new Array<Sorter>();
                let order = 1;

                for (let i = 97; i <= 122; i++) {
                    arr.push({ order: order, value: String.fromCharCode(i) });
                    order++;
                }

                for (let i = 97; i <= 122; i++) {
                    for (let j = 97; j <= 122; j++) {
                        arr.push({ order: order, value: String.fromCharCode(i) + String.fromCharCode(j) });
                        order++;
                    }
                }
                FormulaSorter.SORTER = arr;
            }

            /**
             * get value of
             */
            public static getValueOf(order: number): string {
                let item = _.find(FormulaSorter.SORTER, item => item.order == order);
                if (item) {
                    return item.value;
                }
                return 'NOT FOUND';
            }

            /**
             * get order of
             */
            public static getOrderOf(value: string): number {
                let item = _.find(FormulaSorter.SORTER, item => item.value == value);
                if (item) {
                    return item.order;
                }
                // Error.
                return 0;
            }

            /**
             * get next symbol of
             */
            public static getNextSymbolOf(symbolValue: string): string {
                let nextOd = FormulaSorter.getOrderOf(symbolValue) + 1;
                return FormulaSorter.getValueOf(nextOd);
            }
        }
        interface Sorter {
            order: number;
            value: string;
        }
        export interface ParamToC {
            formulaId: string;
            performanceAtr: number;
            formulaAtr: string;
            formulaName: string;
            itemSelection: ItemSelectionDto;
        }
        export interface ParamToD {
            formulaId: string;
            formulaName: string;
            formulaAtr: string;
            performanceAtr: number;
            formulaSetting: FormulaSettingDto;
        }
    }
}