module nts.uk.at.view.kml002.a.viewmodel {
    import blockUI = nts.uk.ui.block;

    export class ScreenModel {
        settingItems: KnockoutObservableArray<SettingItemModel>;
        settingColumns: KnockoutObservable<any>;
        singleSelectedCode: KnockoutObservable<any>;
        code: KnockoutObservable<string>;
        editMode: KnockoutObservable<boolean>;
        name: KnockoutObservable<string>;
        useCls: KnockoutObservableArray<any>;
        useClsSelected: any;
        workSchedule: KnockoutObservableArray<any>;
        workScheduleSelected: any;
        units: KnockoutObservableArray<any>;
        unitSelected: any;
        calculatorItems: KnockoutObservableArray<CalculatorItem>;
        cbxAttribute: KnockoutObservableArray<any>;
        itemName: KnockoutObservable<string>;
        methods: KnockoutObservableArray<any>;
        cbxDisplayAtr: KnockoutObservableArray<any>;
        cbxTotal: KnockoutObservableArray<any>;
        cbxRounding: KnockoutObservableArray<any>;
        cbxFraction: KnockoutObservableArray<any>;
        allSelectedItems: KnockoutObservable<boolean>;
        workScheduleEnable: KnockoutObservable<boolean>;
        unitEnable: KnockoutObservable<boolean>;
        addLineEnable: KnockoutObservable<boolean>;
        deleteLineEnable: KnockoutObservable<boolean>;
        dataB: any;
        dataC: any;
        dataD: any;
        dataE: any;
        dataF: any;
        dataG: any;
        allItemsData: any;
        dailyItems: any;
        peopleItems: any;
        numericalItems: any;
        amountItems: any;
        unitItems: any;
        timeItems: any;
        timeItemsPlus: any;
        isparentCall: KnockoutObservable<boolean>;

        constructor() {
            var self = this;

            self.isparentCall = ko.observable(false);

            self.allSelectedItems = ko.observable(false);

            self.workScheduleEnable = ko.observable(true);
            self.unitEnable = ko.observable(true);

            self.addLineEnable = ko.observable(true);
            self.deleteLineEnable = ko.observable(true);

            self.settingItems = ko.observableArray([]);

            self.settingColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_6"), prop: 'verticalCalCd', width: 50 },
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'verticalCalName', width: 200, formatter: _.escape }
            ]);

            self.singleSelectedCode = ko.observable("");

            self.code = ko.observable("");
            self.editMode = ko.observable(true);
            self.name = ko.observable("");

            //A6_3 + A6_4
            self.useCls = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText("Enum_UseAtr_NotUse") },
                { code: '1', name: nts.uk.resource.getText("Enum_UseAtr_Use") }
            ]);

            self.useClsSelected = ko.observable(0);

            //A3_10 + A3_11
            self.workSchedule = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText("Enum_IncludeAtr_Exclude") },
                { code: '1', name: nts.uk.resource.getText("Enum_IncludeAtr_Include") }
            ]);

            self.workScheduleSelected = ko.observable(0);

            //A3_6 + A3_7
            self.units = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText("Enum_Unit_DAILY") },
                { code: '1', name: nts.uk.resource.getText("Enum_Unit_BY_TIME_ZONE") }
            ]);

            self.unitSelected = ko.observable(0);

            self.calculatorItems = ko.observableArray([]);

            self.cbxAttribute = ko.observableArray([
                { attrCode: 0, attrName: nts.uk.resource.getText("Enum_Attributes_TIME") },
                { attrCode: 1, attrName: nts.uk.resource.getText("Enum_Attribute_Section_Money") },
                { attrCode: 2, attrName: nts.uk.resource.getText("Enum_Attributes_NUMBER_OF_PEOPLE") },
                { attrCode: 3, attrName: nts.uk.resource.getText("Enum_Attributes_NUMBER") },
                { attrCode: 4, attrName: nts.uk.resource.getText("Enum_Attributes_AVERAGE_PRICE") }
            ]);

            self.itemName = ko.observable("");

            self.methods = ko.observableArray([
                { methodCode: 0, methodName: nts.uk.resource.getText("KML002_25") },
                { methodCode: 1, methodName: nts.uk.resource.getText("KML002_26") }
            ]);

            self.cbxDisplayAtr = ko.observableArray([
                { displayAttrCode: 0, displayAttrName: nts.uk.resource.getText("Enum_DisplayArt_Display") },
                { displayAttrCode: 1, displayAttrName: nts.uk.resource.getText("Enum_DisplayArt_NonDisplay") }
            ]);

            self.cbxTotal = ko.observableArray([
                { totalCode: 0, totalName: nts.uk.resource.getText("Enum_CumulativeAtr_NOT_ACCUMULATE") },
                { totalCode: 1, totalName: nts.uk.resource.getText("Enum_CumulativeAtr_ACCUMULATE") }
            ]);

            self.dailyItems = [];
            self.peopleItems = [];
            self.numericalItems = [];
            self.amountItems = [];
            self.unitItems = [];
            self.timeItems = [];
            self.timeItemsPlus = [];

            $('#popup-area').ntsPopup({
                position: {
                    my: 'left top',
                    at: 'left bottom',
                    of: $('#toggle-popup')
                },
                dismissible: false
            });

            // Show or hide
            $('#toggle-popup').click(function() {
                $(this).siblings('#popup-area').ntsPopup('toggle');
            });

            //Bind data to from when user select item on grid
            self.singleSelectedCode.subscribe(function(value) {
                $("#input-name").focus();

                // clear all error
                nts.uk.ui.errors.clearAll();

                self.isparentCall(true);

                if (value.length > 0) {
                    self.calculatorItems.removeAll();

                    service.getVerticalCalSetByCode(value).done(function(data) {
                        var items = [];

                        self.editMode(false);
                        self.code(data.verticalCalCd);
                        self.name(data.verticalCalName);
                        self.unitSelected(data.unit);
                        self.useClsSelected(data.useAtr);
                        self.workScheduleSelected(data.assistanceTabulationAtr);
                        self.workScheduleEnable(false);
                        self.unitEnable(false);

                        self.calculatorItems([]);
                        self.allItemsData = [];
                        var sortedItems = _.sortBy(data.verticalCalItems, [function(o) { return o.dispOrder; }]);
                        self.allItemsData = sortedItems;

                        if (self.allItemsData.length < 50) {
                            self.addLineEnable(true);
                        } else {
                            self.addLineEnable(false);
                        }

                        $.when(self.getDailyItems(), self.getPeopleItems(), self.getNumericalItems(), self.formulaTimeUnit(), self.formulaTime(), self.getAmountItems()).done(function() {
                            for (var i = 0; i < sortedItems.length; i++) {
                                var item: ICalculatorItem = {
                                    isChecked: false,
                                    itemCd: data.verticalCalItems[i].itemId,
                                    attribute: data.verticalCalItems[i].attributes,
                                    itemName: data.verticalCalItems[i].itemName,
                                    settingMethod: data.verticalCalItems[i].calculateAtr,
                                    formula: "",
                                    displayAtr: data.verticalCalItems[i].displayAtr,
                                    total: data.verticalCalItems[i].cumulativeAtr,
                                    rounding: data.verticalCalItems[i].rounding,
                                    fraction: data.verticalCalItems[i].roundingProcessing,
                                    order: data.verticalCalItems[i].dispOrder,
                                    attrEnable: false,
                                    settingMethodEnable: false,
                                    totalEnable: data.verticalCalItems[i].attributes == 4 ? false : true,
                                    formBuilt: data.verticalCalItems[i].formBuilt,
                                    formTime: data.verticalCalItems[i].formTime,
                                    formPeople: data.verticalCalItems[i].formPeople,
                                    formulaAmount: data.verticalCalItems[i].formulaAmount,
                                    numerical: data.verticalCalItems[i].numerical,
                                    unitPrice: data.verticalCalItems[i].unitPrice
                                };

                                var curDataItem = null;
                                var beforeFormula = "";

                                if (item.formBuilt != null) {
                                    curDataItem = item.formBuilt
                                } else if (item.formTime != null) {
                                    curDataItem = item.formTime
                                } else if (item.formPeople != null) {
                                    curDataItem = item.formPeople
                                } else if (item.formulaAmount != null) {
                                    curDataItem = item.formulaAmount
                                } else if (item.numerical.length > 0) {
                                    curDataItem = item.numerical
                                } else if (item.unitPrice != null) {
                                    curDataItem = item.unitPrice
                                }

                                if (i > 0) {
                                    beforeFormula = sortedItems[i - 1].formula;
                                }

                                var formulaResult = self.formulaGeneration(item.itemName, item.settingMethod, item.attribute,
                                    i, curDataItem, beforeFormula, true);
                                item.formula = formulaResult;

                                items.push(new CalculatorItem(item));
                            }

                            var sorted = _.sortBy(items, [function(o) { return o.order(); }]);
                            self.calculatorItems(sorted);
                        });
                    });
                }
            });

            self.allSelectedItems.subscribe(function(value) {
                var items = [];
                var flag = false;

                if (value == true) {
                    for (var i = 0; i < self.calculatorItems().length; i++) {
                        if (i == 0) {
                            flag = true;
                        }

                        var item: ICalculatorItem = {
                            isChecked: true,
                            itemCd: self.calculatorItems()[i].itemCd(),
                            attribute: self.calculatorItems()[i].attribute(),
                            itemName: self.calculatorItems()[i].itemName(),
                            settingMethod: self.calculatorItems()[i].settingMethod(),
                            settingMethodEnable: flag == false ? false : self.calculatorItems()[i].settingMethodEnable(),
                            formula: self.calculatorItems()[i].formula(),
                            displayAtr: self.calculatorItems()[i].displayAtr(),
                            total: self.calculatorItems()[i].total(),
                            rounding: self.calculatorItems()[i].rounding(),
                            fraction: self.calculatorItems()[i].fraction(),
                            order: self.calculatorItems()[i].order(),
                            formBuilt: self.calculatorItems()[i].formBuilt,
                            formTime: self.calculatorItems()[i].formTime,
                            formPeople: self.calculatorItems()[i].formPeople,
                            formulaAmount: self.calculatorItems()[i].formulaAmount,
                            numerical: self.calculatorItems()[i].numerical,
                            unitPrice: self.calculatorItems()[i].unitPrice,
                            attrEnable: self.calculatorItems()[i].attrEnable(),
                            totalEnable: self.calculatorItems()[i].totalEnable()
                        };

                        items.push(new CalculatorItem(item));
                    }

                    self.calculatorItems([]);
                    self.calculatorItems(items);
                } else {
                    //Just remove check all checkbox
                    var flag = self.checkSelectedItems();
                    var isFirst = false;

                    if (!flag) {
                        //Remove all selected checkbox
                        for (var i = 0; i < self.calculatorItems().length; i++) {
                            if (i == 0) {
                                isFirst = true;
                            }

                            var item: ICalculatorItem = {
                                isChecked: false,
                                itemCd: self.calculatorItems()[i].itemCd(),
                                attribute: self.calculatorItems()[i].attribute(),
                                itemName: self.calculatorItems()[i].itemName(),
                                settingMethod: self.calculatorItems()[i].settingMethod(),
                                settingMethodEnable: isFirst == false ? false : self.calculatorItems()[i].settingMethodEnable(),
                                formula: self.calculatorItems()[i].formula(),
                                displayAtr: self.calculatorItems()[i].displayAtr(),
                                total: self.calculatorItems()[i].total(),
                                rounding: self.calculatorItems()[i].rounding(),
                                fraction: self.calculatorItems()[i].fraction(),
                                order: self.calculatorItems()[i].order(),
                                formBuilt: self.calculatorItems()[i].formBuilt,
                                formTime: self.calculatorItems()[i].formTime,
                                formPeople: self.calculatorItems()[i].formPeople,
                                formulaAmount: self.calculatorItems()[i].formulaAmount,
                                numerical: self.calculatorItems()[i].numerical,
                                unitPrice: self.calculatorItems()[i].unitPrice,
                                attrEnable: self.calculatorItems()[i].attrEnable(),
                                totalEnable: self.calculatorItems()[i].totalEnable()
                            };

                            items.push(new CalculatorItem(item));
                        }

                        self.calculatorItems([]);
                        self.calculatorItems(items);
                    }
                }
            });

            var devChange = false;

            self.unitSelected.subscribe(function(value) {
                if (!devChange) {
                    if (!self.editMode() && !self.isparentCall() && self.calculatorItems().length > 0) {
                        nts.uk.ui.dialog.confirm({ messageId: "Msg_125" }).ifYes(() => {
                            devChange = false;
                            self.calculatorItems([]);
                            self.deleteLineEnable(false);
                        }).ifNo(() => {
                            devChange = true;

                            if (value == 1) {
                                self.unitSelected(0);
                                return;
                            } else {
                                self.unitSelected(1);
                                return;
                            }
                        })
                    }
                    self.isparentCall(false);
                }

                devChange = false;
            });

            self.workScheduleSelected.subscribe(function(value) {
                if (!devChange) {
                    if (!self.editMode() && !self.isparentCall() && self.calculatorItems().length > 0) {
                        nts.uk.ui.dialog.confirm({ messageId: "Msg_191" }).ifYes(() => {
                            devChange = false;
                            self.calculatorItems([]);
                            self.deleteLineEnable(false);
                        }).ifNo(() => {
                            devChange = true;

                            if (value == 1) {
                                self.workScheduleSelected(0);
                                return;
                            } else {
                                self.workScheduleSelected(1);
                                return;
                            }
                        })
                    }
                    self.isparentCall(false);
                }

                devChange = false;
            });
            self.bindingComboBoxEvent();
            $("#fixed-table").ntsFixedTable({ height: 300, width: 700 });
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            blockUI.invisible();

            $.when(self.getData(), self.getDailyItems(), self.getPeopleItems(), self.getNumericalItems(), self.formulaTimeUnit(), self.formulaTime(), self.getAmountItems()).done(function() {

                if (self.settingItems().length > 0) {
                    self.singleSelectedCode(self.settingItems()[0].verticalCalCd);
                }

                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                blockUI.clear();
            });

            return dfd.promise();
        }
        /**
         * subcriber combobox change
         */
        private bindingComboBoxEvent(): void {
            let self = this;
            $(document).delegate(".attributes.reset-element", "igcomboselectionchanging", function(evt, ui) {
                //use to obtain reference to the event browser
                evt.originalEvent;
                //use to obtain reference to igCombo
                ui.owner;
                //use to obtain reference to array of new selected items. That can be null.
                ui.items;
                //use to obtain reference to array of old selected items. That can be null.
                ui.currentItems;
                if (ui.items.length > 0) {
                    let indexAtr = parseInt($($(ui.owner.element).parent().parent()[0]).find(".indexAtr").html()) - 1;
                    let calculatorItem = self.calculatorItems()[indexAtr];
                    if (!self.isparentCall()) {
                        var data = null;

                        if (ui.currentItems[0].data.attrCode == 0) {
                            data = calculatorItem.formTime;
                        } else if (ui.currentItems[0].data.attrCode == 1) {
                            data = calculatorItem.formulaAmount;
                        } else if (ui.currentItems[0].data.attrCode == 2) {
                            data = calculatorItem.formPeople;
                        } else if (ui.currentItems[0].data.attrCode == 3) {
                            data = calculatorItem.numerical;
                        } else if (ui.currentItems[0].data.attrCode == 4) {
                            data = calculatorItem.unitPrice;
                        }

                        if (data != null) {
                            nts.uk.ui.dialog.confirm({ messageId: "Msg_192" }).ifYes(() => {
                                calculatorItem.attribute(ui.items[0].data.attrCode);

                                if (ui.currentItems[0].data.attrCode == 0) {
                                    calculatorItem.formTime = null;
                                } else if (ui.currentItems[0].data.attrCode == 1) {
                                    calculatorItem.formulaAmount = null;
                                } else if (ui.currentItems[0].data.attrCode == 2) {
                                    calculatorItem.formPeople = null;
                                } else if (ui.currentItems[0].data.attrCode == 3) {
                                    calculatorItem.numerical = null;
                                } else if (ui.currentItems[0].data.attrCode == 4) {
                                    calculatorItem.unitPrice = null;
                                }

                                calculatorItem.formula('');

                                if (ui.items[0].data.attrCode == 0) {
                                    calculatorItem.roundingItems([
                                        { roundingCode: 0, roundingName: nts.uk.resource.getText("Enum_RoundingTime_1Min") },
                                        { roundingCode: 1, roundingName: nts.uk.resource.getText("Enum_RoundingTime_5Min") },
                                        { roundingCode: 2, roundingName: nts.uk.resource.getText("Enum_RoundingTime_6Min") },
                                        { roundingCode: 3, roundingName: nts.uk.resource.getText("Enum_RoundingTime_10Min") },
                                        { roundingCode: 4, roundingName: nts.uk.resource.getText("Enum_RoundingTime_15Min") },
                                        { roundingCode: 5, roundingName: nts.uk.resource.getText("Enum_RoundingTime_20Min") },
                                        { roundingCode: 6, roundingName: nts.uk.resource.getText("Enum_RoundingTime_30Min") },
                                        { roundingCode: 7, roundingName: nts.uk.resource.getText("Enum_RoundingTime_60Min") }
                                    ]);

                                    calculatorItem.fractionItems([
                                        { fractionCode: 0, fractionName: nts.uk.resource.getText("Enum_Rounding_Down") },
                                        { fractionCode: 1, fractionName: nts.uk.resource.getText("Enum_Rounding_Up") },
                                        { fractionCode: 2, fractionName: nts.uk.resource.getText("Enum_Rounding_Down_Over") }
                                    ]);
                                } else {
                                    calculatorItem.roundingItems([
                                        { roundingCode: 0, roundingName: nts.uk.resource.getText("Enum_Unit_NONE") },
                                        { roundingCode: 1, roundingName: nts.uk.resource.getText("Enum_Unit_Int_1_Digits") },
                                        { roundingCode: 2, roundingName: nts.uk.resource.getText("Enum_Unit_Int_2_Digits") },
                                        { roundingCode: 3, roundingName: nts.uk.resource.getText("Enum_Unit_Int_3_Digits") },
                                        { roundingCode: 4, roundingName: nts.uk.resource.getText("Enum_Unit_Int_4_Digits") },
                                        { roundingCode: 5, roundingName: nts.uk.resource.getText("Enum_Unit_Int_5_Digits") },
                                        { roundingCode: 6, roundingName: nts.uk.resource.getText("Enum_Unit_Int_6_Digits") },
                                        { roundingCode: 7, roundingName: nts.uk.resource.getText("Enum_Unit_Int_7_Digits") },
                                        { roundingCode: 8, roundingName: nts.uk.resource.getText("Enum_Unit_Int_8_Digits") },
                                        { roundingCode: 9, roundingName: nts.uk.resource.getText("Enum_Unit_Int_9_Digits") },
                                        { roundingCode: 10, roundingName: nts.uk.resource.getText("Enum_Unit_Int_10_Digits") },
                                        { roundingCode: 11, roundingName: nts.uk.resource.getText("Enum_Unit_Int_11_Digits") },
                                        { roundingCode: 12, roundingName: nts.uk.resource.getText("Enum_Unit_Decimal_1st") },
                                        { roundingCode: 13, roundingName: nts.uk.resource.getText("Enum_Unit_Decimal_2nd") },
                                        { roundingCode: 14, roundingName: nts.uk.resource.getText("Enum_Unit_Decimal_3rd") }
                                    ]);

                                    calculatorItem.fractionItems([
                                        { fractionCode: 0, fractionName: nts.uk.resource.getText("Enum_Rounding_Truncation") },
                                        { fractionCode: 1, fractionName: nts.uk.resource.getText("Enum_Rounding_Round_Up") },
                                        { fractionCode: 2, fractionName: nts.uk.resource.getText("Enum_Rounding_Down_4_Up_5") }
                                    ]);
                                }
                            }).ifNo(() => {
                                calculatorItem.attribute(ui.currentItems[0].data.attrCode);
                            });
                        }
                    }
                    self.isparentCall(false);
                }
            });
        }

        /**
         * Get C screen data.
         */
        getDailyItems() {
            var self = this;
            var dfd = $.Deferred();
            self.dailyItems = [];

            var dailyAttendanceAtrs = [];
            dailyAttendanceAtrs.push(5);
            var paramC = {
                dailyAttendanceItemAtrs: dailyAttendanceAtrs,
                scheduleAtr: 0,
                budgetAtr: 0,
                unitAtr: self.unitSelected()
            };
            service.getDailyItems(paramC).done(function(data) {
                let temp = [];
                let items = _.sortBy(data, ['companyId', 'dispOrder']);

                _.forEach(items, function(item: service.BaseItemsDto) {
                    var name = item.itemName + nts.uk.resource.getText("KML002_43");
                    temp.push({ id: item.id, name: name, itemType: item.itemType });
                });

                self.dailyItems = temp;

                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);
            });

            return dfd.promise();
        }

        /**
         * Get D screen data.
         */
        getPeopleItems() {
            var self = this;
            var dfd = $.Deferred();

            let param = {
                budgetAtr: 1,
                // received from mother screen 0: day or 1: time
                unitAtr: self.unitSelected()
            }

            service.getByAtr(param).done((data) => {
                var temp = data;
                let a = {
                    budgetAtr: 1,
                    externalBudgetCode: (data.length + 1).toString(),
                    externalBudgetName: nts.uk.resource.getText("KML002_109"),
                    unitAtr: 0
                }
                let b = {
                    budgetAtr: 1,
                    externalBudgetCode: (data.length + 2).toString(),
                    externalBudgetName: nts.uk.resource.getText("KML002_110"),
                    unitAtr: 0
                }

                temp.push(b);
                self.peopleItems = _.clone(temp);


                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);
            });

            return dfd.promise();
        }

        formulaTimeUnit(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            var dailyAttendanceAtrs = [];
            dailyAttendanceAtrs.push(5);
            var param = {
                dailyAttendanceItemAtrs: dailyAttendanceAtrs,
                scheduleAtr: 0,
                budgetAtr: 1,
                unitAtr: self.unitSelected()
            };
            service.getDailyItems(param).done(function(data) {
                let temp = [];
                let items = _.sortBy(data, ['companyId', 'dispOrder']);

                _.forEach(items, function(item: service.BaseItemsDto) {
                    var name = item.itemName + nts.uk.resource.getText("KML002_43");
                    temp.push({ id: item.id, name: name, itemType: item.itemType });
                });

                self.unitItems = temp;

                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);
            });

            return dfd.promise();
        }

        formulaTime(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            var dailyAttendanceAtrs = [];
            dailyAttendanceAtrs.push(3);
            var param = {
                dailyAttendanceItemAtrs: dailyAttendanceAtrs,
                scheduleAtr: 6,
                budgetAtr: 2,
                unitAtr: 0
            };
            service.getDailyItems(param).done(function(data) {
                let temp = [];
                let items = _.sortBy(data, ['companyId', 'dispOrder']);

                _.forEach(items, function(item: service.BaseItemsDto) {
                    var name = item.itemName + nts.uk.resource.getText("KML002_43");
                    temp.push({ id: item.id, name: name, itemType: item.itemType });
                });

                self.timeItems = temp;

                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);
            });
            
             var dailyAttendanceAtrs = [];
            dailyAttendanceAtrs.push(3);
            var param1 = {
                dailyAttendanceItemAtrs: dailyAttendanceAtrs,
                scheduleAtr: 6,
                budgetAtr: 2,
                unitAtr: 1
            };
            service.getDailyItems(param1).done(function(data) {
                let temp1 = [];
                let items = _.sortBy(data, ['companyId', 'dispOrder']);

                _.forEach(items, function(item: service.BaseItemsDto) {
                    var name = item.itemName + nts.uk.resource.getText("KML002_43");
                    temp1.push({ id: item.id, name: name, itemType: item.itemType });
                });

                self.timeItemsPlus = temp1;

                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);
            });


            return dfd.promise();
        }

        getAmountItems() {
            var self = this;
            var dfd = $.Deferred();

            let param = {
                budgetAtr: 2,
                // received from mother screen 0: day or 1: time
                unitAtr: self.unitSelected()
            }

            service.getByAtr(param).done((data) => {
                var temp = data;
                let a = {
                    budgetAtr: 2,
                    externalBudgetCode: (data.length + 1).toString(),
                    externalBudgetName: nts.uk.resource.getText("KML002_109"),
                    unitAtr: 0
                }
                let b = {
                    budgetAtr: 2,
                    externalBudgetCode: (data.length + 2).toString(),
                    externalBudgetName: nts.uk.resource.getText("KML002_110"),
                    unitAtr: 0
                }

                temp.push(a);
                self.amountItems = _.clone(temp);


                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);
            });

            return dfd.promise();
        }

        /**
         * Get F screen data.
         */
        getNumericalItems() {
            var self = this;
            var dfd = $.Deferred();

            let param = {
                budgetAtr: 3,
                unitAtr: self.unitSelected()
            }

            service.getByAtr(param).done((data) => {
                var temp = data;
                let a = {
                    budgetAtr: 1,
                    externalBudgetCode: (data.length + 1).toString(),
                    externalBudgetName: nts.uk.resource.getText("KML002_109"),
                    unitAtr: 0
                }

                temp.push(a);

                self.numericalItems = temp;

                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);
            });

            return dfd.promise();
        }

        /**
         * Get data from db.
         */
        getData(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.settingItems([]);
            service.findAllVerticalCalSet().done(function(data: Array<SettingItemModel>) {
                self.settingItems(data);
                //new SettingItemModel(item.verticalCalCd, item.verticalCalName)

                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);
            });

            return dfd.promise();
        }

        /**
         * Check selected items to set value for all selected checkbox.
         */
        checkSelectedItems() {
            var self = this;
            var selectedItems = 0;

            for (var i = 0; i < self.calculatorItems().length; i++) {
                if (self.calculatorItems()[i].isChecked()) {
                    selectedItems = selectedItems + 1;
                }
            }

            if (selectedItems < self.calculatorItems().length && selectedItems > 0) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * Bind calculator items by vertical cal set.
         */
        bindCalculatorItems() {
            var self = this;

            var item: ICalculatorItem = {
                isChecked: false,
                itemCd: nts.uk.util.randomId(),
                attribute: 0,
                itemName: '',
                settingMethod: 0,
                formula: '',
                displayAtr: 0,
                total: 0,
                rounding: 0,
                fraction: 0,
                order: 1,
                attrEnable: true,
                settingMethodEnable: false,
                totalEnable: true,
                formBuilt: null,
                formTime: null,
                formPeople: null,
                formulaAmount: null,
                numerical: null,
                unitPrice: null
            };

            self.calculatorItems.push(new CalculatorItem(item));
        }

        /**
         * Formula filter.
         */
        formularFilter() {
            let self = this;


        }

        /**
         * Clear form data to new mode.
         */
        newBtn() {
            var self = this;

            self.addLineEnable(true);
            self.singleSelectedCode("");
            self.code("");
            self.editMode(true);
            self.name("");
            self.unitSelected(0);
            self.useClsSelected(0);
            self.workScheduleSelected(0);
            self.calculatorItems([]);

            self.workScheduleEnable(true);
            self.unitEnable(true);
            self.allItemsData = [];
            $("#input-code").focus();

            // clear all error
            nts.uk.ui.errors.clearAll();
        }

        /**
         * Add or Update data to db.
         */
        registrationBtn() {
            var self = this;

            // clear all error
            nts.uk.ui.errors.clearAll();

            blockUI.invisible();

            // validate
            $(".input-code").trigger("validate");
            $(".input-name").trigger("validate");
            $(".item-name").trigger("validate");

            if (nts.uk.ui.errors.hasError()) {
                blockUI.clear();
                return;
            }

            if (self.editMode()) {
                var filter = _.filter(self.settingItems(), function(o) { return o.verticalCalCd == self.code(); });

                if (filter.length > 0) {
                    $('#input-code').ntsError('set', { messageId: "Msg_3" });
                    blockUI.clear();
                    return;
                }
            }

            var code = self.code();
            var name = self.name();
            var unit = self.unitSelected();
            var useAtr = self.useClsSelected();
            var assistanceTabulationAtr = self.workScheduleSelected();
            var verticalCalItems = new Array<VerticalCalItemDto>();

            for (var i = 0; i < self.calculatorItems().length; i++) {
                if (self.calculatorItems()[i].formula() == "") {
                    if (self.calculatorItems()[i].settingMethod() == 0) {
                        if (self.calculatorItems()[i].attribute() == 0) {
                            self.dataC = null;
                        } else if (self.calculatorItems()[i].attribute() == 1) {
                            self.dataE = null;
                        } else if (self.calculatorItems()[i].attribute() == 2) {
                            self.dataD = null;
                        } else if (self.calculatorItems()[i].attribute() == 3) {
                            self.dataF = null;
                        } else if (self.calculatorItems()[i].attribute() == 4) {
                            self.dataG = null;
                        }
                    } else {
                        self.dataB = null;
                    }
                }

                var dataB = self.calculatorItems()[i].formBuilt != null ? self.calculatorItems()[i].formBuilt : self.dataB;
                var dataC = self.calculatorItems()[i].formTime != null ? self.calculatorItems()[i].formTime : self.dataC;
                var dataD = self.calculatorItems()[i].formPeople != null ? self.calculatorItems()[i].formPeople : self.dataD;
                var dataE = self.calculatorItems()[i].formulaAmount != null ? self.calculatorItems()[i].formulaAmount : self.dataE;
                var dataF = self.calculatorItems()[i].numerical != null ? self.calculatorItems()[i].numerical : self.dataF;
                var dataG = self.calculatorItems()[i].unitPrice != null ? self.calculatorItems()[i].unitPrice : self.dataG;

                var item = {
                    verticalCalCd: code,
                    itemId: self.calculatorItems()[i].itemCd(),
                    itemName: self.calculatorItems()[i].itemName(),
                    calculateAtr: self.calculatorItems()[i].settingMethod(),
                    displayAtr: self.calculatorItems()[i].displayAtr(),
                    cumulativeAtr: self.calculatorItems()[i].total(),
                    attributes: self.calculatorItems()[i].attribute(),
                    rounding: self.calculatorItems()[i].rounding(),
                    roundingProcessing: self.calculatorItems()[i].fraction(),
                    dispOrder: self.calculatorItems()[i].order(),
                    //for B screen
                    formBuilt: self.calculatorItems()[i].settingMethod() == 1 ? dataB : null,
                    //for C screen
                    formTime: self.calculatorItems()[i].settingMethod() == 0 && self.calculatorItems()[i].attribute() == 0 ? dataC : null,
                    //for D screen
                    formPeople: self.calculatorItems()[i].settingMethod() == 0 && self.calculatorItems()[i].attribute() == 2 ? dataD : null,
                    //for E screen
                    formulaAmount: self.calculatorItems()[i].settingMethod() == 0 && self.calculatorItems()[i].attribute() == 1 ? dataE : null,
                    //for F screen
                    numerical: self.calculatorItems()[i].settingMethod() == 0 && self.calculatorItems()[i].attribute() == 3 ? dataF : null,
                    //for G screen
                    unitPrice: self.calculatorItems()[i].settingMethod() == 0 && self.calculatorItems()[i].attribute() == 4 ? dataG : null
                };

                verticalCalItems.push(item);
            }

            if (verticalCalItems.length > 0) {
                var data = new VerticalSettingDto(code, name, unit, useAtr, assistanceTabulationAtr, verticalCalItems);

                service.addVerticalCalSet(data).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    self.getData();
                    self.singleSelectedCode(data.verticalCalCd);
                    self.singleSelectedCode.valueHasMutated();
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId, messageParams: error.parameterIds });
                }).always(function() {
                    blockUI.clear();
                });
            } else {
                $('#checkall').ntsError('set', { messageId: "Msg_110" });
                blockUI.clear();
            }
        }

        /**
         * Open setting dialog.
         */
        settingBtn() {
            var self = this;

            nts.uk.ui.windows.sub.modal("/view/kdl/024/a/index.xhtml");
        }

        /**
         * Delete vertical cal set.
         */
        deleteBtn() {
            var self = this;

            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                blockUI.invisible();

                let count = 0;
                for (let i = 0; i <= self.settingItems().length; i++) {
                    if (self.settingItems()[i].verticalCalCd == self.singleSelectedCode()) {
                        count = i;
                        break;
                    }
                }

                service.deleteVerticalCalSet(self.singleSelectedCode()).done(function() {
                    self.getData().done(function() {
                        // if number of item from list after delete == 0 
                        if (self.settingItems().length == 0) {
                            self.newBtn();
                            return;
                        }
                        // delete the last item
                        if (count == ((self.settingItems().length))) {
                            self.singleSelectedCode(self.settingItems()[count - 1].verticalCalCd);
                            return;
                        }
                        // delete the first item
                        if (count == 0) {
                            self.singleSelectedCode(self.settingItems()[0].verticalCalCd);
                            return;
                        }
                        // delete item at mediate list 
                        else if (count > 0 && count < self.settingItems().length) {
                            self.singleSelectedCode(self.settingItems()[count].verticalCalCd);
                            return;
                        }
                    });

                    nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error.message);
                }).always(function() {
                    blockUI.clear();
                });
            }).ifNo(() => {
                return;
            });

        }

        /**
         * Add new calculator item.
         */
        addLineBtn() {
            var self = this;
            var flag = false;

            if (self.calculatorItems().length > 0) {
                flag = true;
            }

            var item: ICalculatorItem = {
                isChecked: false,
                itemCd: nts.uk.util.randomId(),
                attribute: 0,
                itemName: '',
                settingMethod: 0,
                formula: '',
                displayAtr: 0,
                total: 0,
                rounding: 0,
                fraction: 0,
                order: self.calculatorItems().length + 1,
                attrEnable: true,
                settingMethodEnable: flag,
                totalEnable: true,
                formBuilt: null,
                formTime: null,
                formPeople: null,
                formulaAmount: null,
                numerical: null,
                unitPrice: null
            };
            self.isparentCall(false);
            self.calculatorItems.push(new CalculatorItem(item));

            if (self.calculatorItems().length < 50) {
                self.addLineEnable(true);
            } else {
                self.addLineEnable(false);
            }

            if (self.calculatorItems().length > 0) {
                self.deleteLineEnable(true);
            }

            $('#itemName').focus();
        }

        /**
         * Delete calculator item.
         */
        deleteLineBtn() {
            var self = this;
            var selectedItems = [];

            nts.uk.ui.dialog.confirm({ messageId: "Msg_113" }).ifYes(() => {
                for (var i = 0; i < self.calculatorItems().length; i++) {
                    if (!self.calculatorItems()[i].isChecked()) {
                        var item: ICalculatorItem = {
                            isChecked: self.calculatorItems()[i].isChecked(),
                            itemCd: nts.uk.util.randomId(),
                            attribute: self.calculatorItems()[i].attribute(),
                            itemName: self.calculatorItems()[i].itemName(),
                            settingMethod: self.calculatorItems()[i].settingMethod(),
                            formula: self.calculatorItems()[i].formula(),
                            displayAtr: self.calculatorItems()[i].displayAtr(),
                            total: self.calculatorItems()[i].total(),
                            rounding: self.calculatorItems()[i].rounding(),
                            fraction: self.calculatorItems()[i].fraction(),
                            order: i + 1,
                            attrEnable: self.calculatorItems()[i].attrEnable(),
                            settingMethodEnable: self.calculatorItems()[i].settingMethodEnable(),
                            totalEnable: self.calculatorItems()[i].totalEnable(),
                            formBuilt: self.calculatorItems()[i].formBuilt,
                            formTime: self.calculatorItems()[i].formTime,
                            formPeople: self.calculatorItems()[i].formPeople,
                            formulaAmount: self.calculatorItems()[i].formulaAmount,
                            numerical: self.calculatorItems()[i].numerical,
                            unitPrice: self.calculatorItems()[i].unitPrice
                        };

                        selectedItems.push(new CalculatorItem(item));
                    }
                }

                self.calculatorItems([]);
                var flag = false;

                for (var i = 0; i < selectedItems.length; i++) {
                    if (i > 0) {
                        flag = true;
                    }

                    var newItem: ICalculatorItem = {
                        isChecked: selectedItems[i].isChecked(),
                        itemCd: nts.uk.util.randomId(),
                        attribute: selectedItems[i].attribute(),
                        itemName: selectedItems[i].itemName(),
                        settingMethod: selectedItems[i].settingMethod(),
                        formula: selectedItems[i].formula(),
                        displayAtr: selectedItems[i].displayAtr(),
                        total: selectedItems[i].total(),
                        rounding: selectedItems[i].rounding(),
                        fraction: selectedItems[i].fraction(),
                        order: i + 1,
                        attrEnable: selectedItems[i].attrEnable(),
                        settingMethodEnable: flag == false ? false : selectedItems[i].settingMethodEnable(),
                        totalEnable: selectedItems[i].totalEnable(),
                        formBuilt: selectedItems[i].formBuilt,
                        formTime: selectedItems[i].formTime,
                        formPeople: selectedItems[i].formPeople,
                        formulaAmount: selectedItems[i].formulaAmount,
                        numerical: selectedItems[i].numerical,
                        unitPrice: selectedItems[i].unitPrice
                    };

                    self.calculatorItems.push(new CalculatorItem(newItem));
                }

                self.allSelectedItems(false);

                if (self.calculatorItems().length == 0) {
                    self.deleteLineEnable(false);
                }

                if (self.calculatorItems().length >= 0) {
                    self.addLineEnable(true);
                }
            }).ifNo(() => {
                return;
            })
        }

        /**
         * Get selected calculator items.
         */
        getSelectedCalculatorItems() {
            var self = this;
            var selectedItems = [];

            for (var i = 0; i < self.calculatorItems().length; i++) {
                if (self.calculatorItems()[i].isChecked()) {
                    selectedItems.push(self.calculatorItems()[i]);
                }
            }

            return selectedItems;
        }

        /**
         * Reset formula.
         */
        resetFormula(temp: any) {
            var self = this;
            var beforeFormula = "";
            var formularTxt = "";

            for (var i = 0; i < temp.length; i++) {
                var curDataItem = null;

                if (temp[i].formBuilt != null) {
                    curDataItem = temp[i].formBuilt
                } else if (temp[i].formTime != null) {
                    curDataItem = temp[i].formTime
                } else if (temp[i].formPeople != null) {
                    curDataItem = temp[i].formPeople
                } else if (temp[i].formulaAmount != null) {
                    curDataItem = temp[i].formulaAmount
                } else if (temp[i].numerical != null && temp[i].numerical.length > 0) {
                    curDataItem = temp[i].numerical
                } else if (temp[i].unitPrice != null) {
                    curDataItem = temp[i].unitPrice
                }

                if (i > 0) {
                    beforeFormula = temp[i - 1].formula();
                }

                if (i == 0) {
                    formularTxt = self.formulaGeneration(temp[i].itemName(), temp[i].settingMethod(), temp[i].attribute(), i, curDataItem, beforeFormula, true);
                } else {
                    formularTxt = self.formulaGeneration(temp[i].itemName(), temp[i].settingMethod(), temp[i].attribute(), i, curDataItem, beforeFormula, false);
                }

                temp[i].formula(formularTxt);
            }

            return temp;
        }

        /**
         * Move up calculator item.
         */
        upBtn() {
            var self = this;
            var prevIdx = -1;
            var $scope = _.clone(self.calculatorItems());
            var temp = [];

            var multiRowSelected = _.filter($scope, function(item: CalculatorItem) {
                return item.isChecked();
            });

            _.forEach(multiRowSelected, function(item) {
                var idx = _.findIndex($scope, function(o: CalculatorItem) { return o.itemCd() == item.itemCd(); });
                if (idx - 1 === prevIdx) {
                    prevIdx = idx
                } else if (idx > 0) {
                    var itemToMove = $scope.splice(idx, 1)
                    $scope.splice(idx - 1, 0, itemToMove[0]);
                }
            });

            _.forEach($scope, function(item, index) {
                item.order(index + 1);
                temp.push(item);
            });

            var data = self.resetFormula(temp);

            self.calculatorItems(data);
        }

        /**
         * Move down calculator item.
         */
        downBtn() {
            var self = this;
            var $scope = _.clone(self.calculatorItems());
            var prevIdx = $scope.length;
            var temp = [];

            var multiRowSelected = _.filter($scope, function(item: CalculatorItem) {
                return item.isChecked();
            });

            var revPerson = multiRowSelected.concat();
            revPerson.reverse();

            for (var i = 0; i < revPerson.length; i++) {
                var item = revPerson[i];
                var idx = _.findIndex($scope, function(o: CalculatorItem) { return o.itemCd() == item.itemCd(); });
                if (idx + 1 === prevIdx) {
                    prevIdx = idx
                } else if (idx < $scope.length - 1) {
                    var itemToMove = $scope.splice(idx, 1)
                    $scope.splice(idx + 1, 0, itemToMove[0]);
                }
            }

            _.forEach($scope, function(item, index) {
                item.order(index + 1);
                temp.push(item);
            });

            var data = self.resetFormula(temp);

            self.calculatorItems(data);
        }

        /**
         * Pass data to dialog.
         */
        passDataToDialogs(itemCd: number, settingMethod: number, attribute: number, itemName: string) {
            var self = this;
            var attrValue = "";

            if (attribute == 0 && settingMethod == 0) {
                attrValue = nts.uk.resource.getText("Enum_Attributes_TIME");
            } else if (attribute == 1) {
                attrValue = nts.uk.resource.getText("Enum_Attribute_Section_Money");
            } else if (attribute == 2) {
                attrValue = nts.uk.resource.getText("Enum_Attributes_NUMBER_OF_PEOPLE");
            } else if (attribute == 3) {
                attrValue = nts.uk.resource.getText("Enum_Attributes_NUMBER");
            } else if (attribute == 4) {
                attrValue = nts.uk.resource.getText("Enum_Attributes_AVERAGE_PRICE");
            }

            // Get all items before current selected item if setting method = 1 to binding for dropdownlist
            var verticalCalItems = new Array<VerticalCalItemDto>();
            var currentItem = _.find(self.calculatorItems(), function(o) { return o.itemCd() == itemCd; });

            for (var i = 0; i < currentItem.order() - 1; i++) {
                var item = {
                    verticalCalCd: self.code(),
                    itemId: self.calculatorItems()[i].itemCd(),
                    itemName: self.calculatorItems()[i].itemName(),
                    calculateAtr: self.calculatorItems()[i].settingMethod(),
                    displayAtr: self.calculatorItems()[i].displayAtr(),
                    cumulativeAtr: self.calculatorItems()[i].fraction(),
                    attributes: self.calculatorItems()[i].attribute(),
                    rounding: self.calculatorItems()[i].rounding(),
                    dispOrder: self.calculatorItems()[i].order()
                };

                verticalCalItems.push(item);
            }

            var data = {
                verticalCalCd: self.code(),
                itemId: itemCd,
                attributeId: attribute,
                attribute: attrValue,
                unit: self.unitSelected(),
                itemName: itemName,
                verticalItems: currentItem.settingMethod() == 1 ? verticalCalItems : null,
                formBuilt: currentItem.formBuilt,
                formTime: currentItem.formTime,
                formPeople: currentItem.formPeople,
                formulaAmount: currentItem.formulaAmount,
                numerical: currentItem.numerical,
                unitPrice: currentItem.unitPrice
            };

            nts.uk.ui.windows.setShared("KML002_A_DATA", data);
        }

        /**
         * Check conditions to open dialog.
         */
        openDialog(itemCd: number, settingMethod: number, attribute: number, itemName: string) {
            var self = this;

            if (nts.uk.ui.errors.hasError()) {
                return;
            }

            if (settingMethod == 1) {
                self.passDataToDialogs(itemCd, settingMethod, attribute, itemName);
                nts.uk.ui.windows.sub.modal("/view/kml/002/b/index.xhtml").onClosed(() => {
                    self.dataB = nts.uk.ui.windows.getShared("KML002_B_DATA");

                    for (var i = 0; i < self.calculatorItems().length; i++) {
                        if (self.dataB == null) {
                            return;
                        }

                        if (self.calculatorItems()[i].itemCd() == self.dataB.verticalCalItemId) {
                            self.calculatorItems()[i].formBuilt = self.dataB;
                            var formulaResult = self.formulaGeneration(itemName, settingMethod, attribute, i, self.dataB, "", false);
                            self.calculatorItems()[i].formula(formulaResult);
                        }
                    }
                });
            } else {
                if (attribute == 0) {
                    self.passDataToDialogs(itemCd, settingMethod, attribute, itemName);
                    nts.uk.ui.windows.sub.modal("/view/kml/002/c/index.xhtml").onClosed(() => {
                        self.dataC = nts.uk.ui.windows.getShared("KML002_C_DATA");

                        for (var i = 0; i < self.calculatorItems().length; i++) {
                            if (self.dataC == null) {
                                return;
                            }

                            if (self.calculatorItems()[i].itemCd() == self.dataC.verticalCalItemId) {
                                self.calculatorItems()[i].formTime = self.dataC;
                                var formulaResult = self.formulaGeneration(itemName, settingMethod, attribute, i, self.dataC, "", false);
                                self.calculatorItems()[i].formula(formulaResult);
                            }
                        }
                    });
                } else if (attribute == 1) {
                    self.passDataToDialogs(itemCd, settingMethod, attribute, itemName);
                    nts.uk.ui.windows.sub.modal("/view/kml/002/e/index.xhtml").onClosed(() => {
                        self.dataE = nts.uk.ui.windows.getShared("KML002_E_DATA");

                        for (var i = 0; i < self.calculatorItems().length; i++) {
                            if (self.dataE == null) {
                                return;
                            }

                            if (self.calculatorItems()[i].itemCd() == self.dataE.verticalCalItemId) {
                                self.calculatorItems()[i].formulaAmount = self.dataE;
                                var formulaResult = self.formulaGeneration(itemName, settingMethod, attribute, i, self.dataE, "", false);
                                self.calculatorItems()[i].formula(formulaResult);
                            }
                        }
                    });
                } else if (attribute == 2) {
                    self.passDataToDialogs(itemCd, settingMethod, attribute, itemName);
                    nts.uk.ui.windows.sub.modal("/view/kml/002/d/index.xhtml").onClosed(() => {
                        self.dataD = nts.uk.ui.windows.getShared("KML002_D_Budget");

                        for (var i = 0; i < self.calculatorItems().length; i++) {
                            if (self.dataD == null) {
                                return;
                            }

                            if (self.calculatorItems()[i].itemCd() == self.dataD.verticalCalItemId) {
                                self.calculatorItems()[i].formPeople = self.dataD;
                                var formulaResult = self.formulaGeneration(itemName, settingMethod, attribute, i, self.dataD, "", false);
                                self.calculatorItems()[i].formula(formulaResult);
                            }
                        }
                    });
                } else if (attribute == 3) {
                    self.passDataToDialogs(itemCd, settingMethod, attribute, itemName);
                    nts.uk.ui.windows.sub.modal("/view/kml/002/f/index.xhtml").onClosed(() => {
                        self.dataF = nts.uk.ui.windows.getShared("KML002_F_DATA");

                        for (var i = 0; i < self.calculatorItems().length; i++) {
                            if (self.dataF == null) {
                                return;
                            }

                            if (self.calculatorItems()[i].itemCd() == self.dataF[0].verticalCalItemId) {
                                self.calculatorItems()[i].numerical = self.dataF;
                                var formulaResult = self.formulaGeneration(itemName, settingMethod, attribute, i, self.dataF, "", false);
                                self.calculatorItems()[i].formula(formulaResult);
                            }
                        }
                    });
                } else if (attribute == 4) {
                    self.passDataToDialogs(itemCd, settingMethod, attribute, itemName);
                    nts.uk.ui.windows.sub.modal("/view/kml/002/g/index.xhtml").onClosed(() => {
                        self.dataG = nts.uk.ui.windows.getShared("KML002_G_DATA");

                        for (var i = 0; i < self.calculatorItems().length; i++) {
                            if (self.dataG == null) {
                                return;
                            }

                            if (self.calculatorItems()[i].itemCd() == self.dataG.verticalCalItemId) {
                                self.calculatorItems()[i].unitPrice = self.dataG;
                                var formulaResult = self.formulaGeneration(itemName, settingMethod, attribute, i, self.dataG, "", false);
                                self.calculatorItems()[i].formula(formulaResult);
                            }
                        }
                    });
                }
            }
        }

        /**
         * Generate the formula when the dialog setting passed data to A screen.
         */

        formulaGeneration(itemName: string, settingMethod: number, attribute: number, index: number, data: any, beforeFormula: string, isFirstLoad: boolean) {
            let self = this;
            beforeFormula = "";
            var formulaResult = "";

            // 計算式設定
            if (settingMethod == 1) {
                if (data != null) {
                    var operator = "";
                    var text1 = "";
                    var text2 = "";

                    if (data.operatorAtr == 0) { operator = nts.uk.resource.getText("Enum_OperatorAtr_ADD"); }
                    if (data.operatorAtr == 1) { operator = nts.uk.resource.getText("Enum_OperatorAtr_SUBTRACT"); }
                    if (data.operatorAtr == 2) { operator = nts.uk.resource.getText("Enum_OperatorAtr_MULTIPLY"); }
                    if (data.operatorAtr == 3) { operator = nts.uk.resource.getText("Enum_OperatorAtr_DIVIDE"); }

                    if (data.settingMethod1 == 0) {
                        if (data.verticalCalItemName1 != null) {
                            text1 = data.verticalCalItemName1;
                        } else {
                            var item = _.find(self.allItemsData, function(o) { return o.itemId == data.verticalCalItem1; });
                            text1 = item.itemName;
                        }
                    } else {
                        text1 = data.verticalInputItem1;
                    }

                    if (data.settingMethod2 == 0) {
                        if (data.verticalCalItemName2 != null) {
                            text2 = data.verticalCalItemName2;
                        } else {
                            var item = _.find(self.allItemsData, function(o) { return o.itemId == data.verticalCalItem2; });
                            text2 = item.itemName;
                        }
                    } else {
                        text2 = data.verticalInputItem2;
                    }

                    formulaResult = beforeFormula + " " + nts.uk.resource.getText("KML002_37") + " " + text1 + " " + operator + " " + text2;
                } else {
                    formulaResult = "";
                }
            } else { // 項目選択
                if (data != null) {
                    // 平均単価
                    if (attribute == 4) {
                        // If is first item
                        var unitPrices = [
                            { uPCd: 0, uPName: nts.uk.resource.getText("KML002_53") },
                            { uPCd: 1, uPName: nts.uk.resource.getText("KML002_54") },
                            { uPCd: 2, uPName: nts.uk.resource.getText("KML002_55") },
                            { uPCd: 3, uPName: nts.uk.resource.getText("KML002_56") },
                            { uPCd: 4, uPName: nts.uk.resource.getText("KML002_57") }
                        ];
                        var unit = _.find(unitPrices, function(o) { return o.uPCd == data.unitPrice; });

                        formulaResult = data.unitName != null ? data.unitName : (unit != null ? unit.uPName : "");
                    } else { // Other attributes
                        // If is first item
                        if (index == 0) {
                            if (attribute == 0) {
                                if (data.lstFormTimeFunc.length <= 0) {
                                    formulaResult = "";
                                } else {
                                    for (var i = 0; i < data.lstFormTimeFunc.length; i++) {
                                        var operator = data.lstFormTimeFunc[i].operatorAtr == 0 ? nts.uk.resource.getText("KML002_37") : nts.uk.resource.getText("KML002_38");
                                        var itemName = data.lstFormTimeFunc[i].name != null ? data.lstFormTimeFunc[i].name : "";
                                        var attendanceItem = _.find(self.dailyItems, function(o) { return o.id.slice(0, -1) == data.lstFormTimeFunc[i].attendanceItemId; });
                                        var presetItem = _.find(self.dailyItems, function(o) { return o.id.slice(0, -1) == data.lstFormTimeFunc[i].presetItemId; });
                                        var externalItem = _.find(self.dailyItems, function(o) { return o.id.slice(0, -1) == data.lstFormTimeFunc[i].externalBudgetCd; });

                                        if (itemName != "") {
                                            formulaResult += operator + " " + itemName + " ";
                                        } else if (attendanceItem != null) {
                                            formulaResult += operator + " " + attendanceItem.name + " ";
                                        } else if (presetItem != null) {
                                            formulaResult += operator + " " + presetItem.name + " ";
                                        } else if (externalItem != null) {
                                            formulaResult += operator + " " + externalItem.name + " ";
                                        }
                                    }
                                }
                            } else if (attribute == 1) {
                                if (data.timeUnit != null) {
                                    if (data.timeUnit.lstTimeUnitFuncs.length <= 0 && data.moneyFunc.lstMoney.length <= 0) {
                                        formulaResult = "";
                                    }
                                }

                                if (data.moneyFunc.lstMoney.length > 0) {
                                    for (var i = 0; i < data.moneyFunc.lstMoney.length; i++) {
                                        var operatorAtr = data.moneyFunc.lstMoney[i].operatorAtr == 0 ? nts.uk.resource.getText("KML002_37") : nts.uk.resource.getText("KML002_38");
                                        var name = data.moneyFunc.lstMoney[i].name != null ? data.moneyFunc.lstMoney[i].name : "";
                                            
                                        if (name != "") {
                                            formulaResult += operatorAtr + " " + name + " ";
                                        } else if (item != null) {
                                            formulaResult += operatorAtr + " " + item.externalBudgetName + " ";
                                            }
                                        if (self.unitSelected() == 1) {
                                            var item = _.find(self.amountItems, function(o) { return o.externalBudgetCode == data.moneyFunc.lstMoney[i].externalBudgetCd; });
                                            formulaResult += operatorAtr + " " + item.externalBudgetName + " ";
                                        }
                                        if (data.moneyFunc.categoryIndicator == 0 && data.moneyFunc.actualDisplayAtr == 1) {
                                            var presetTime = _.find(self.timeItems, function(o) { return o.id.slice(0, -1) == data.moneyFunc.lstMoney[i].presetItemId; });
                                            formulaResult += operatorAtr + " " + presetTime.name + " ";
                                        }
                                        if (data.moneyFunc.categoryIndicator == 0 && data.moneyFunc.actualDisplayAtr == 0) {
                                            var presetTime = _.find(self.timeItems, function(o) { return o.id.slice(0, -1) == data.moneyFunc.lstMoney[i].presetItemId; });
                                            formulaResult += operatorAtr + " " + itemTime.name + " ";
                                        }
                                        if (data.moneyFunc.categoryIndicator == 1) {
                                            var item = _.find(self.timeItemsPlus, function(o) { return o.externalBudgetCode == data.moneyFunc.lstMoney[i].externalBudgetCd; });
                                            formulaResult += operatorAtr + " " + item.externalBudgetName + " ";
                                        }
                                    }
                                } else if (data.timeUnit.lstTimeUnitFuncs.length > 0) {
                                    for (var i = 0; i < data.timeUnit.lstTimeUnitFuncs.length; i++) {
                                        var operator = data.timeUnit.lstTimeUnitFuncs[i].operatorAtr == 0 ? nts.uk.resource.getText("KML002_37") : nts.uk.resource.getText("KML002_38");
                                        var name = data.timeUnit.lstTimeUnitFuncs[i].name != null ? data.timeUnit.lstTimeUnitFuncs[i].name : "";
                                        var itemAttendance = _.find(self.unitItems, function(o) { return o.id.slice(0, -1) == data.timeUnit.lstTimeUnitFuncs[i].attendanceItemId && o.itemType == 0; });
                                        var itemPreset = _.find(self.unitItems, function(o) { return o.id.slice(0, -1) == data.timeUnit.lstTimeUnitFuncs[i].presetItemId; });

                                        if (name != "") {
                                            formulaResult += operator + " " + name + " ";
                                        } else if (itemAttendance != null) {
                                            formulaResult += operator + " " + itemAttendance.name + " ";
                                        } else if (itemPreset != null) {
                                            formulaResult += operator + " " + itemPreset.name + " ";
                                        }
                                    }
                                }
                            } else if (attribute == 2) {
                                if (data.lstPeopleFunc.length <= 0) {
                                    formulaResult = "";
                                } else {
                                    for (var i = 0; i < data.lstPeopleFunc.length; i++) {
                                        var operator = data.lstPeopleFunc[i].operatorAtr == 0 ? nts.uk.resource.getText("KML002_37") : nts.uk.resource.getText("KML002_38");
                                        var name = data.lstPeopleFunc[i].name != null ? data.lstPeopleFunc[i].name : "";
                                        var item = _.find(self.peopleItems, function(o) { return o.externalBudgetCode == data.lstPeopleFunc[i].externalBudgetCd; });

                                        if (name != "") {
                                            formulaResult += operator + " " + name + " ";
                                        } else if (item != null) {
                                            formulaResult += operator + " " + item.externalBudgetName + " ";
                                        }
                                    }
                                }
                            } else if (attribute == 3) {
                                if (data == null || data.length <= 0) {
                                    formulaResult = "";
                                } else {
                                    for (var i = 0; i < data.length; i++) {
                                        var operator = data[i].operatorAtr == 0 ? nts.uk.resource.getText("KML002_37") : nts.uk.resource.getText("KML002_38");
                                        var name = data[i].name != null ? data[i].name : "";
                                        var item = _.find(self.numericalItems, function(o) { return o.externalBudgetCode == data[i].externalBudgetCd; });

                                        if (name != "") {
                                            formulaResult += operator + " " + name + " ";
                                        } else if (item != null) {
                                            formulaResult += operator + " " + item.externalBudgetName + " ";
                                        }
                                    }
                                }
                            }
                        } else {
                            var before = "";

                            if (attribute == 0) {
                                if (data.lstFormTimeFunc.length <= 0) {
                                    formulaResult = "";
                                } else {
                                    for (var i = 0; i < data.lstFormTimeFunc.length; i++) {
                                        var operator = data.lstFormTimeFunc[i].operatorAtr == 0 ? nts.uk.resource.getText("KML002_37") : nts.uk.resource.getText("KML002_38");
                                        var itemName = data.lstFormTimeFunc[i].name != null ? data.lstFormTimeFunc[i].name : "";
                                        var attendanceItem = _.find(self.dailyItems, function(o) { return o.id.slice(0, -1) == data.lstFormTimeFunc[i].attendanceItemId; });
                                        var presetItem = _.find(self.dailyItems, function(o) { return o.id.slice(0, -1) == data.lstFormTimeFunc[i].presetItemId; });
                                        var externalItem = _.find(self.dailyItems, function(o) { return o.id.slice(0, -1) == data.lstFormTimeFunc[i].externalBudgetCd; });

                                        if (itemName != "") {
                                            formulaResult += operator + " " + itemName + " ";
                                        } else if (attendanceItem != null) {
                                            formulaResult += operator + " " + attendanceItem.name + " ";
                                        } else if (presetItem != null) {
                                            formulaResult += operator + " " + presetItem.name + " ";
                                        } else if (externalItem != null) {
                                            formulaResult += operator + " " + externalItem.name + " ";
                                        }
                                    }
                                }
                            } else if (attribute == 1) {
                                if (data.timeUnit != null) {
                                    if (data.timeUnit.lstTimeUnitFuncs.length <= 0 && data.moneyFunc.lstMoney.length <= 0) {
                                        formulaResult = "";
                                    }
                                }

                                if (data.moneyFunc.lstMoney.length > 0) {
                                    for (var i = 0; i < data.moneyFunc.lstMoney.length; i++) {
                                        var operatorAtr = data.moneyFunc.lstMoney[i].operatorAtr == 0 ? nts.uk.resource.getText("KML002_37") : nts.uk.resource.getText("KML002_38");
                                        var name = data.moneyFunc.lstMoney[i].name != null ? data.moneyFunc.lstMoney[i].name : "";
                                        var item = _.find(self.amountItems, function(o) { return o.externalBudgetCode == data.moneyFunc.lstMoney[i].externalBudgetCd; });
                                        var itemTime = _.find(self.timeItems, function(o) { return o.externalBudgetCode == data.moneyFunc.lstMoney[i].externalBudgetCd; });
                                        var attendanceTime = _.find(self.timeItems, function(o) { return o.id.slice(0, -1) == data.moneyFunc.lstMoney[i].attendanceItemIdTime && o.itemType == 0; });
                                        var presetTime = _.find(self.timeItems, function(o) { return o.id.slice(0, -1) == data.moneyFunc.lstMoney[i].presetItemIdTime; });
                                        if (name != "") {
                                            formulaResult += operatorAtr + " " + name + " ";
                                        } else if (item != null) {
                                            formulaResult += operatorAtr + " " + item.externalBudgetName + " ";
                                        } else if (itemTime != null) {
                                            formulaResult += operatorAtr + " " + itemTime.name + " ";
                                        } else if (attendanceTime != null) {
                                            formulaResult += operatorAtr + " " + attendanceTime.name + " ";
                                        } else if (presetTime != null) {
                                            formulaResult += operatorAtr + " " + presetTime.name + " ";
                                        }
                                    }
                                } else if (data.timeUnit.lstTimeUnitFuncs.length > 0) {
                                    for (var i = 0; i < data.timeUnit.lstTimeUnitFuncs.length; i++) {
                                        var operator = data.timeUnit.lstTimeUnitFuncs[i].operatorAtr == 0 ? nts.uk.resource.getText("KML002_37") : nts.uk.resource.getText("KML002_38");
                                        var name = data.timeUnit.lstTimeUnitFuncs[i].name != null ? data.timeUnit.lstTimeUnitFuncs[i].name : "";
                                        var itemAttendance = _.find(self.unitItems, function(o) { return o.id.slice(0, -1) == data.timeUnit.lstTimeUnitFuncs[i].attendanceItemId && o.itemType == 0; });
                                        var itemPreset = _.find(self.unitItems, function(o) { return o.id.slice(0, -1) == data.timeUnit.lstTimeUnitFuncs[i].presetItemId; });

                                        if (name != "") {
                                            formulaResult += operator + " " + name + " ";
                                        } else if (itemAttendance != null) {
                                            formulaResult += operator + " " + itemAttendance.name + " ";
                                        } else if (itemPreset != null) {
                                            formulaResult += operator + " " + itemPreset.name + " ";
                                        }
                                    }
                                }
                            } else if (attribute == 2) {
                                if (data.lstPeopleFunc.length <= 0) {
                                    formulaResult = "";
                                } else {
                                    for (var i = 0; i < data.lstPeopleFunc.length; i++) {
                                        var operator = data.lstPeopleFunc[i].operatorAtr == 0 ? nts.uk.resource.getText("KML002_37") : nts.uk.resource.getText("KML002_38");
                                        var name = data.lstPeopleFunc[i].name != null ? data.lstPeopleFunc[i].name : "";
                                        var item = _.find(self.peopleItems, function(o) { return o.externalBudgetCode == data.lstPeopleFunc[i].externalBudgetCd; });

                                        if (name != "") {
                                            formulaResult += operator + " " + name + " ";
                                        } else if (item != null) {
                                            formulaResult += operator + " " + item.externalBudgetName + " ";
                                        }
                                    }
                                }
                            } else if (attribute == 3) {
                                if (data == null || data.length <= 0) {
                                    formulaResult = "";
                                } else {
                                    for (var i = 0; i < data.length; i++) {
                                        var operator = data[i].operatorAtr == 0 ? nts.uk.resource.getText("KML002_37") : nts.uk.resource.getText("KML002_38");
                                        var name = data[i].name != null ? data[i].name : "";
                                        var item = _.find(self.numericalItems, function(o) { return o.externalBudgetCode == data[i].externalBudgetCd; });

                                        if (name != "") {
                                            formulaResult += operator + " " + name + " ";
                                        } else if (item != null) {
                                            formulaResult += operator + " " + item.externalBudgetName + " ";
                                        }
                                    }
                                }
                            }

                            formulaResult = before + " " + formulaResult;
                        }
                    }
                } else {
                    formulaResult = "";
                }
            }

            if (_.startsWith(formulaResult.trim(), nts.uk.resource.getText("KML002_37"))) {
                formulaResult = formulaResult.substr(2);
            }

            return formulaResult.trim();
        }
    }


    export class SettingItemModel {
        verticalCalCd: string;
        verticalCalName: string;
        constructor(code: string, name: string) {
            this.verticalCalCd = code;
            this.verticalCalName = name;
        }
    }

    class VerticalSettingDto {
        verticalCalCd: string;
        verticalCalName: string;
        unit: number;
        useAtr: number;
        assistanceTabulationAtr: number;
        verticalCalItems: Array<VerticalCalItemDto>;

        constructor(verticalCalCd: string, verticalCalName: string, unit: number, useAtr: number, assistanceTabulationAtr: number, verticalCalItems: Array<VerticalCalItemDto>) {
            this.verticalCalCd = verticalCalCd;
            this.verticalCalName = verticalCalName;
            this.unit = unit;
            this.useAtr = useAtr;
            this.assistanceTabulationAtr = assistanceTabulationAtr;
            this.verticalCalItems = verticalCalItems;
        }
    }

    class VerticalCalItemDto {
        verticalCalCd: string;
        itemId: string;
        itemName: string;
        calculateAtr: number;
        displayAtr: number;
        cumulativeAtr: number;
        attributes: number;
        rounding: number;
        dispOrder: number;

        constructor(verticalCalCd: string, itemId: string, itemName: string, calculateAtr: number, displayAtr: number,
            cumulativeAtr: number, attributes: number, rounding: number, dispOrder: number) {
            this.verticalCalCd = verticalCalCd;
            this.itemId = itemId;
            this.itemName = itemName;
            this.calculateAtr = calculateAtr;
            this.displayAtr = displayAtr;
            this.cumulativeAtr = cumulativeAtr;
            this.attributes = attributes;
            this.rounding = rounding;
            this.dispOrder = dispOrder;
        }
    }

    export class CalculatorItem {
        isChecked: KnockoutObservable<boolean>;
        itemCd: KnockoutObservable<string>;
        attribute: KnockoutObservable<number>;
        itemName: KnockoutObservable<string>;
        settingMethod: KnockoutObservable<number>;
        formula: KnockoutObservable<string>;
        displayAtr: KnockoutObservable<number>;
        total: KnockoutObservable<number>;
        rounding: KnockoutObservable<number>;
        fraction: KnockoutObservable<number>;
        order: KnockoutObservable<number>;
        roundingItems: KnockoutObservableArray<any>;
        fractionItems: KnockoutObservableArray<any>;
        attrEnable: KnockoutObservable<boolean>;
        settingMethodEnable: KnockoutObservable<boolean>;
        totalEnable: KnockoutObservable<boolean>;
        formBuilt: any;
        formTime: any;
        formPeople: any;
        formulaAmount: any;
        numerical: any;
        unitPrice: any;

        constructor(param: ICalculatorItem) {
            var self = this;
            self.isChecked = ko.observable(param.isChecked);
            self.itemCd = ko.observable(param.itemCd);
            self.attribute = ko.observable(param.attribute);
            self.itemName = ko.observable(param.itemName);
            self.settingMethod = ko.observable(param.settingMethod);
            self.formula = ko.observable(param.formula);
            self.displayAtr = ko.observable(param.displayAtr);
            self.total = ko.observable(param.total);
            self.rounding = ko.observable(param.rounding);
            self.fraction = ko.observable(param.fraction);
            self.order = ko.observable(param.order);
            if (param.attribute == 0) {
                self.roundingItems = ko.observableArray([
                    { roundingCode: 0, roundingName: nts.uk.resource.getText("Enum_RoundingTime_1Min") },
                    { roundingCode: 1, roundingName: nts.uk.resource.getText("Enum_RoundingTime_5Min") },
                    { roundingCode: 2, roundingName: nts.uk.resource.getText("Enum_RoundingTime_6Min") },
                    { roundingCode: 3, roundingName: nts.uk.resource.getText("Enum_RoundingTime_10Min") },
                    { roundingCode: 4, roundingName: nts.uk.resource.getText("Enum_RoundingTime_15Min") },
                    { roundingCode: 5, roundingName: nts.uk.resource.getText("Enum_RoundingTime_20Min") },
                    { roundingCode: 6, roundingName: nts.uk.resource.getText("Enum_RoundingTime_30Min") },
                    { roundingCode: 7, roundingName: nts.uk.resource.getText("Enum_RoundingTime_60Min") }
                ]);

                self.fractionItems = ko.observableArray([
                    { fractionCode: 0, fractionName: nts.uk.resource.getText("Enum_Rounding_Down") },
                    { fractionCode: 1, fractionName: nts.uk.resource.getText("Enum_Rounding_Up") },
                    { fractionCode: 2, fractionName: nts.uk.resource.getText("Enum_Rounding_Down_Over") }
                ]);
            } else {
                self.roundingItems = ko.observableArray([
                    { roundingCode: 0, roundingName: nts.uk.resource.getText("Enum_Unit_NONE") },
                    { roundingCode: 1, roundingName: nts.uk.resource.getText("Enum_Unit_Int_1_Digits") },
                    { roundingCode: 2, roundingName: nts.uk.resource.getText("Enum_Unit_Int_2_Digits") },
                    { roundingCode: 3, roundingName: nts.uk.resource.getText("Enum_Unit_Int_3_Digits") },
                    { roundingCode: 4, roundingName: nts.uk.resource.getText("Enum_Unit_Int_4_Digits") },
                    { roundingCode: 5, roundingName: nts.uk.resource.getText("Enum_Unit_Int_5_Digits") },
                    { roundingCode: 6, roundingName: nts.uk.resource.getText("Enum_Unit_Int_6_Digits") },
                    { roundingCode: 7, roundingName: nts.uk.resource.getText("Enum_Unit_Int_7_Digits") },
                    { roundingCode: 8, roundingName: nts.uk.resource.getText("Enum_Unit_Int_8_Digits") },
                    { roundingCode: 9, roundingName: nts.uk.resource.getText("Enum_Unit_Int_9_Digits") },
                    { roundingCode: 10, roundingName: nts.uk.resource.getText("Enum_Unit_Int_10_Digits") },
                    { roundingCode: 11, roundingName: nts.uk.resource.getText("Enum_Unit_Int_11_Digits") },
                    { roundingCode: 12, roundingName: nts.uk.resource.getText("Enum_Unit_Decimal_1st") },
                    { roundingCode: 13, roundingName: nts.uk.resource.getText("Enum_Unit_Decimal_2nd") },
                    { roundingCode: 14, roundingName: nts.uk.resource.getText("Enum_Unit_Decimal_3rd") }
                ]);

                self.fractionItems = ko.observableArray([
                    { fractionCode: 0, fractionName: nts.uk.resource.getText("Enum_Rounding_Truncation") },
                    { fractionCode: 1, fractionName: nts.uk.resource.getText("Enum_Rounding_Round_Up") },
                    { fractionCode: 2, fractionName: nts.uk.resource.getText("Enum_Rounding_Down_4_Up_5") }
                ]);
            }

            self.attrEnable = ko.observable(param.attrEnable);
            self.settingMethodEnable = ko.observable(param.settingMethodEnable);
            self.totalEnable = ko.observable(param.totalEnable);

            self.isChecked.subscribe(function(value) {
                if (!value) {
                    nts.uk.ui._viewModel.content.viewmodelA.allSelectedItems(false);
                }
            });

            self.formBuilt = param.formBuilt;
            self.formTime = param.formTime;
            self.formPeople = param.formPeople;
            self.formulaAmount = param.formulaAmount;
            self.numerical = param.numerical;
            self.unitPrice = param.unitPrice;

            var devChange = false;

            self.attribute.subscribe(function(value) {
                if (value == 4) {
                    self.settingMethodEnable(false);
                    self.totalEnable(false);
                } else {
                    if (self.order() == 1) {
                        self.settingMethodEnable(false);
                        self.totalEnable(true);
                    } else {
                        self.settingMethodEnable(true);
                        self.totalEnable(true);
                    }
                }
            });

            self.settingMethod.subscribe(function(value) {
                if (!devChange) {
                    if (!nts.uk.ui._viewModel.content.viewmodelA.isparentCall()) {
                        if (self.formBuilt != null) {
                            nts.uk.ui.dialog.confirm({ messageId: "Msg_126" }).ifYes(() => {
                                devChange = false;
                                self.formula("");
                                self.formBuilt = null;
                            }).ifNo(() => {
                                devChange = true;

                                if (value == 1) {
                                    self.settingMethod(0);
                                    return;
                                } else {
                                    self.settingMethod(1);
                                    return;
                                }
                            });
                        } else {
                            if (self.attribute() == 0 && self.formTime != null) {
                                nts.uk.ui.dialog.confirm({ messageId: "Msg_126" }).ifYes(() => {
                                    devChange = false;
                                    self.formula("");
                                    self.formTime = null;
                                }).ifNo(() => {
                                    devChange = true;

                                    if (value == 1) {
                                        self.settingMethod(0);
                                        return;
                                    } else {
                                        self.settingMethod(1);
                                        return;
                                    }
                                });
                            } else if (self.attribute() == 1 && self.formulaAmount != null) {
                                nts.uk.ui.dialog.confirm({ messageId: "Msg_126" }).ifYes(() => {
                                    devChange = false;
                                    self.formula("");
                                    self.formulaAmount = null;
                                }).ifNo(() => {
                                    devChange = true;

                                    if (value == 1) {
                                        self.settingMethod(0);
                                        return;
                                    } else {
                                        self.settingMethod(1);
                                        return;
                                    }
                                });
                            } else if (self.attribute() == 2 && self.formPeople != null) {
                                nts.uk.ui.dialog.confirm({ messageId: "Msg_126" }).ifYes(() => {
                                    devChange = false;
                                    self.formula("");
                                    self.formPeople = null;
                                }).ifNo(() => {
                                    devChange = true;

                                    if (value == 1) {
                                        self.settingMethod(0);
                                        return;
                                    } else {
                                        self.settingMethod(1);
                                        return;
                                    }
                                });
                            } else if (self.attribute() == 3 && self.numerical != null) {
                                nts.uk.ui.dialog.confirm({ messageId: "Msg_126" }).ifYes(() => {
                                    devChange = false;
                                    self.formula("");
                                    self.numerical = null;
                                }).ifNo(() => {
                                    devChange = true;

                                    if (value == 1) {
                                        self.settingMethod(0);
                                        return;
                                    } else {
                                        self.settingMethod(1);
                                        return;
                                    }
                                });
                            } else if (self.attribute() == 4 && self.unitPrice != null) {
                                nts.uk.ui.dialog.confirm({ messageId: "Msg_126" }).ifYes(() => {
                                    devChange = false;
                                    self.formula("");
                                    self.unitPrice = null;
                                }).ifNo(() => {
                                    devChange = true;

                                    if (value == 1) {
                                        self.settingMethod(0);
                                        return;
                                    } else {
                                        self.settingMethod(1);
                                        return;
                                    }
                                });
                            }
                        }
                    }

                    nts.uk.ui._viewModel.content.viewmodelA.isparentCall(false);
                }

                devChange = false;
            });
        }
    }

    export interface ICalculatorItem {
        isChecked: boolean;
        itemCd: string;
        attribute: number;
        itemName: string;
        settingMethod: number;
        formula: string;
        displayAtr: number;
        total: number;
        rounding: number;
        fraction: number;
        order: number;
        roundingItems: Array<any>;
        fractionItems: Array<any>;
        attrEnable: boolean;
        settingMethodEnable: boolean;
        totalEnable: boolean;
        formBuilt: any;
        formTime: any;
        formPeople: any;
        formulaAmount: any;
        numerical: any;
        unitPrice: any;
    }
}
