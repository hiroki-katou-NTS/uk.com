module nts.uk.at.view.kdw007.b.viewmodel {

    export class ScreenModel {
        enumConditionAtr: KnockoutObservableArray<any> = ko.observableArray([
            { code: 0, name: "回数" },
            { code: 1, name: "時間" },
            { code: 2, name: "時刻" },
            { code: 3, name: "金額" },
            { code: 4, name: "日数" }
        ]);

        enumConditionType: KnockoutObservableArray<any> = ko.observableArray([
            { code: 0, name: "固定値", enable: true },
            { code: 1, name: "勤怠項目", enable: true },
            { code: 2, name: "入力チェック", enable: true }
        ]);
        
        enumInputCheckCondition: KnockoutObservableArray<any> = ko.observableArray([
            { code: 0, name: nts.uk.resource.getText("KDW007_108") },
            { code: 1, name: nts.uk.resource.getText("KDW007_107") }
        ]);

        enumCompareOperator: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: "等しい（＝）" },
            { code: 0, name: "等しくない（≠）" },
            { code: 5, name: "より大きい（＞）" },
            { code: 3, name: "以上（≧）" },
            { code: 4, name: "より小さい（＜）" },
            { code: 2, name: "以下（≦）" },
            { code: 6, name: "範囲の間（境界値を含まない）（＜＞）" },
            { code: 7, name: "範囲の間（境界値を含む）（≦≧）" },
            { code: 8, name: "範囲の外（境界値を含まない）（＞＜）" },
            { code: 9, name: "範囲の外（境界値を含む）（≧≦）" }
        ]);
        
        currentAtdItemCondition: ErAlAtdItemCondition;
        displayTargetAtdItems: KnockoutObservable<string> = ko.observable("");
        displayCompareAtdItems: KnockoutObservable<string> = ko.observable("");
        mode: number;
        
        optionNoOfHolidays: any = {
            decimallength: 1
        }

        constructor() {
            let self = this,
                caic = self.currentAtdItemCondition,
                param = nts.uk.ui.windows.getShared("KDW007BParams");
            self.mode = param.mode;
            if (self.mode == 1) { // monthly
                self.enumConditionAtr.remove( (item) => { return item.code == 2; } );
            } else { //daily
                self.enumConditionAtr.remove( (item) => { return item.code == 4; } );
            }

            /*param.countableAddAtdItems = _.values(param.countableAddAtdItems || []);
            param.countableSubAtdItems = _.values(param.countableSubAtdItems || []);*/

            ko.utils.extend(param.data, {
                countableAddAtdItems: _.values(param.data.countableAddAtdItems || []),
                countableSubAtdItems: _.values(param.data.countableSubAtdItems || [])
            });

            if (_.isEmpty(param.data.countableAddAtdItems) && _.isEmpty(param.data.countableSubAtdItems) && (param.data.uncountableAtdItem == null || param.data.uncountableAtdItem == 0)) {
                param.data.compareStartValue = null;
                param.data.compareEndValue = null;
            }
            
            self.currentAtdItemCondition = caic = ko.mapping.fromJS(param.data);

            if (caic.compareOperator() > 5) {
                self.enumConditionType([
                    { code: 0, name: "固定値", enable: true },
                    { code: 1, name: "勤怠項目", enable: false },
                    { code: 2, name: "入力チェック", enable: true }
                ]);
            } else {
                self.enumConditionType([
                    { code: 0, name: "固定値", enable: true },
                    { code: 1, name: "勤怠項目", enable: true },
                    { code: 2, name: "入力チェック", enable: true }
                ]);
            }
            
            caic.compareStartValue.subscribe(v => {
                self.validateRange();
            });
            
            caic.compareEndValue.subscribe(v => {
                self.validateRange();
            });
            
            caic.conditionAtr.subscribe(v => {
                $(".value-input").ntsError("clear");
                caic.uncountableAtdItem(null);
                caic.countableAddAtdItems([]);
                caic.countableSubAtdItems([]);
                caic.conditionType(0);
                caic.compareOperator(0);
                caic.singleAtdItem(null);
                caic.compareStartValue(null);
                caic.compareEndValue(null);

                self.fillTextDisplayTarget();
                self.fillTextDisplayComparison();
            });

            caic.compareOperator.subscribe((value) => {
                if (value > 5) {
                    self.enumConditionType([
                        { code: 0, name: "固定値", enable: true },
                        { code: 1, name: "勤怠項目", enable: false },
                        { code: 2, name: "入力チェック", enable: true }
                    ]);

                    caic.conditionType(0);
                } else {
                    self.enumConditionType([
                        { code: 0, name: "固定値", enable: true },
                        { code: 1, name: "勤怠項目", enable: true },
                        { code: 2, name: "入力チェック", enable: true }
                    ]);
                }
                self.validateRange();
            });

            caic.conditionType.subscribe((value) => {
                if (value === 0) {
                    $('#display-compare-item').ntsError('clear');
                    $(".value-input").trigger("validate");
                } else if (value === 1) {
                    $('.value-input').ntsError('clear');
                    $("#display-compare-item").trigger("validate");
                } else {
                    $('#display-compare-item').ntsError('clear');
                    $('.value-input').ntsError('clear');
                }
            });

            self.fillTextDisplayTarget();
            self.fillTextDisplayComparison();

        }

        fillTextDisplayTarget() {
            let self = this;
            self.displayTargetAtdItems("");
            if (self.currentAtdItemCondition.conditionAtr() === 2 || self.currentAtdItemCondition.conditionType() === 2) {
                if (self.currentAtdItemCondition.uncountableAtdItem()) {
                    service.getAttendanceItemByCodes([self.currentAtdItemCondition.uncountableAtdItem()], self.mode).done((lstItems) => {
                        if (lstItems && lstItems.length > 0) {
                            self.displayTargetAtdItems(lstItems[0].attendanceItemName);
                            $("#display-target-item").trigger("validate");
                        }
                    });
                }
            } else {
                if (self.currentAtdItemCondition.countableAddAtdItems().length > 0) {
                    service.getAttendanceItemByCodes(self.currentAtdItemCondition.countableAddAtdItems(), self.mode).done((lstItems) => {
                        if (lstItems && lstItems.length > 0) {
                            for (let i = 0; i < lstItems.length; i++) {
                                let operator = (i === (lstItems.length - 1)) ? "" : " + ";
                                self.displayTargetAtdItems(self.displayTargetAtdItems() + lstItems[i].attendanceItemName + operator);
                            }
                            $("#display-target-item").trigger("validate");
                        }
                    }).then(() => {
                        if (self.currentAtdItemCondition.countableSubAtdItems().length > 0) {
                            service.getAttendanceItemByCodes(self.currentAtdItemCondition.countableSubAtdItems(), self.mode).done((lstItems) => {
                                if (lstItems && lstItems.length > 0) {
                                    for (let i = 0; i < lstItems.length; i++) {
                                        let operator = (i === (lstItems.length - 1)) ? "" : " - ";
                                        let beforeOperator = (i === 0) ? " - " : "";
                                        self.displayTargetAtdItems(self.displayTargetAtdItems() + beforeOperator + lstItems[i].attendanceItemName + operator);
                                    }
                                    $("#display-target-item").trigger("validate");
                                }
                            })
                        }
                    });
                } else if (self.currentAtdItemCondition.countableSubAtdItems().length > 0) {
                    service.getAttendanceItemByCodes(self.currentAtdItemCondition.countableSubAtdItems(), self.mode).done((lstItems) => {
                        if (lstItems && lstItems.length > 0) {
                            for (let i = 0; i < lstItems.length; i++) {
                                let operator = (i === (lstItems.length - 1)) ? "" : " - ";
                                let beforeOperator = (i === 0) ? " - " : "";
                                self.displayTargetAtdItems(self.displayTargetAtdItems() + beforeOperator + lstItems[i].attendanceItemName + operator);
                            }
                            $("#display-target-item").trigger("validate");
                        }
                    })
                }

            }
        }

        fillTextDisplayComparison() {
            let self = this;
            self.displayCompareAtdItems("");
            if (self.currentAtdItemCondition.singleAtdItem()) {
                service.getAttendanceItemByCodes([self.currentAtdItemCondition.singleAtdItem()], self.mode).done((lstItems) => {
                    if (lstItems && lstItems.length > 0) {
                        self.displayCompareAtdItems(lstItems[0].attendanceItemName);
                        $("#display-compare-item").trigger("validate");
                    }
                });
            }
        }

        getListItemByAtr() {
            let self = this;
            let dfd = $.Deferred<any>();
            if (self.currentAtdItemCondition.conditionAtr() === 0) {
                //With type 回数 - Times
                service.getAttendanceItemByAtr(self.mode == 1 ? MonthlyAttendanceItemAtr.NUMBER : DailyAttendanceItemAtr.NumberOfTime, self.mode).done((lstAtdItem) => {
                    /*service.getOptItemByAtr(self.mode == 1 ? MonthlyAttendanceItemAtr.NUMBER : 1, self.mode).done((lstOptItem) => {
                        for (let i = 0; i < lstOptItem.length; i++) {
                            lstAtdItem.push(lstOptItem[i]);
                        }
                        dfd.resolve(lstAtdItem);
                    });*/
                    dfd.resolve(lstAtdItem);
                });
            } else if (self.currentAtdItemCondition.conditionAtr() === 1) {
                //With type 時間 - Time
                service.getAttendanceItemByAtr(self.mode == 1 ? MonthlyAttendanceItemAtr.TIME : DailyAttendanceItemAtr.Time, self.mode).done((lstAtdItem) => {
                    /*service.getOptItemByAtr(self.mode == 1 ? MonthlyAttendanceItemAtr.TIME : 0, self.mode).done((lstOptItem) => {
                        for (let i = 0; i < lstOptItem.length; i++) {
                            lstAtdItem.push(lstOptItem[i]);
                        }
                        dfd.resolve(lstAtdItem);
                    });*/
                    dfd.resolve(lstAtdItem);
                });
            } else if (self.currentAtdItemCondition.conditionAtr() === 2) {
                //With type 時刻 - TimeWithDay
                service.getAttendanceItemByAtr(DailyAttendanceItemAtr.TimeOfDay, self.mode).done((lstAtdItem) => {
                    dfd.resolve(lstAtdItem);
                });
            } else if (self.currentAtdItemCondition.conditionAtr() === 3) {
                //With type 金額 - AmountMoney
                service.getAttendanceItemByAtr(self.mode == 1 ? MonthlyAttendanceItemAtr.AMOUNT : DailyAttendanceItemAtr.AmountOfMoney, self.mode).done((lstAtdItem) => {
                    /*service.getOptItemByAtr(self.mode == 1 ? MonthlyAttendanceItemAtr.AMOUNT : 2, self.mode).done((lstOptItem) => {
                        for (let i = 0; i < lstOptItem.length; i++) {
                            lstAtdItem.push(lstOptItem[i]);
                        }
                        dfd.resolve(lstAtdItem);
                    });*/
                    dfd.resolve(lstAtdItem);
                });
            } else { // 日数
                service.getAttendanceItemByAtr(MonthlyAttendanceItemAtr.DAYS, self.mode).done((lstAtdItem) => {
                    dfd.resolve(lstAtdItem);
                });
            }
            return dfd.promise();
        }

        openSelectAtdItemDialogTarget() {
            let self = this;
            nts.uk.ui.block.invisible();
            self.getListItemByAtr().done((lstItem) => {
                let lstItemCode = lstItem.map((item) => { return item.attendanceItemId; });
                if (self.currentAtdItemCondition.conditionAtr() === 2 || self.currentAtdItemCondition.conditionType() === 2) {
                    //Open dialog KDL021
                    nts.uk.ui.block.invisible();
                    nts.uk.ui.windows.setShared('Multiple', false);
                    nts.uk.ui.windows.setShared('MonthlyMode', self.mode == 1);
                    nts.uk.ui.windows.setShared('AllAttendanceObj', lstItemCode);
                    nts.uk.ui.windows.setShared('SelectedAttendanceId', [self.currentAtdItemCondition.uncountableAtdItem()]);
                    nts.uk.ui.windows.sub.modal("at", "/view/kdl/021/a/index.xhtml").onClosed(() => {
                        let output = nts.uk.ui.windows.getShared("selectedChildAttendace");
                        if (output) {
                            self.currentAtdItemCondition.uncountableAtdItem(parseInt(output));
                            self.fillTextDisplayTarget();
                        }
                    });
                    nts.uk.ui.block.clear();
                } else {
                    //Open dialog KDW007C
                    let param = {
                        attr: self.mode,
                        lstAllItems: lstItemCode,
                        lstAddItems: self.currentAtdItemCondition.countableAddAtdItems(),
                        lstSubItems: self.currentAtdItemCondition.countableSubAtdItems()
                    };
                    nts.uk.ui.block.invisible();
                    nts.uk.ui.windows.setShared("KDW007Params", param);
                    nts.uk.ui.windows.sub.modal("at", "/view/kdw/007/c/index.xhtml").onClosed(() => {
                        let output = nts.uk.ui.windows.getShared("KDW007CResults");
                        if (output) {
                            self.currentAtdItemCondition.countableAddAtdItems(output.lstAddItems.map((item) => { return parseInt(item); }));
                            self.currentAtdItemCondition.countableSubAtdItems(output.lstSubItems.map((item) => { return parseInt(item); }));
                            self.fillTextDisplayTarget();
                        }
                    });
                    nts.uk.ui.block.clear();
                }
            });
            nts.uk.ui.block.clear();
        }

        openSelectAtdItemDialogComparison() {
            let self = this;
            //Open dialog KDL021
            self.getListItemByAtr().done((lstItem) => {
                let lstItemCode = lstItem.map((item) => { return item.attendanceItemId; });
                nts.uk.ui.windows.setShared('Multiple', false);
                nts.uk.ui.windows.setShared('MonthlyMode', self.mode == 1);
                nts.uk.ui.windows.setShared('AllAttendanceObj', lstItemCode);
                nts.uk.ui.windows.setShared('SelectedAttendanceId', [self.currentAtdItemCondition.singleAtdItem()]);
                nts.uk.ui.windows.sub.modal("at", "/view/kdl/021/a/index.xhtml").onClosed(() => {
                    let output = nts.uk.ui.windows.getShared("selectedChildAttendace");
                    if (output) {
                        self.currentAtdItemCondition.singleAtdItem(parseInt(output));
                        self.fillTextDisplayComparison();
                    }
                });
            });
        }

        validateRange() {
            let self = this,
                caic = ko.toJS(self.currentAtdItemCondition);
            $('.value-input').ntsError('clear');
            $('#endValue').css('border-color','grey');
            if (caic.conditionType === 0 && [7, 9].indexOf(caic.compareOperator) > -1) {
                // fixbug 99086 : set timeout
                setTimeout(() => {
                    if (parseInt(caic.compareStartValue) > parseInt(caic.compareEndValue)) {
                        $('.value-input').ntsError('clear');
                        $('#startValue').ntsError('set', { messageId: "Msg_927" });
                        $('#endValue').ntsError('set', { messageId: "Msg_927" });
                    }
                }, 25);
            } else if (caic.conditionType === 0 && [6, 8].indexOf(caic.compareOperator) > -1) {
                // fixbug 99086 : set timeout
                $('#endValue').css('border-color', 'grey');
                setTimeout(() => {
                    if (parseInt(caic.compareStartValue) >= parseInt(caic.compareEndValue)) {
                        $('.value-input').ntsError('clear');
                        $('#startValue').ntsError('set', { messageId: "Msg_927" });
                        $('#endValue').css('border-color', 'red');
                    }
                }, 25);
            }
        }

        returnData() {
            let self = this;

            $(".need-check").trigger("validate");
            self.validateRange();
            $('.value-input').filter(":enabled").trigger("validate");

            if (!nts.uk.ui.errors.hasError()) {
                let param = ko.mapping.toJS(self.currentAtdItemCondition);
                param.countableAddAtdItems = _.values(param.countableAddAtdItems);
                param.countableSubAtdItems = _.values(param.countableSubAtdItems);
                nts.uk.ui.windows.setShared('KDW007BResult', param);
                nts.uk.ui.windows.close();
            }
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

        startPage(): JQueryPromise<any> {
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
    }

    export class ErAlAtdItemCondition {

        targetNO: KnockoutObservable<number>;
        conditionAtr: KnockoutObservable<number>;
        useAtr: KnockoutObservable<boolean>;
        uncountableAtdItem: KnockoutObservable<number>;
        countableAddAtdItems: KnockoutObservableArray<number>;
        countableSubAtdItems: KnockoutObservableArray<number>;
        conditionType: KnockoutObservable<number>;
        compareOperator: KnockoutObservable<number>;
        singleAtdItem: KnockoutObservable<number>;
        compareStartValue: KnockoutObservable<number>;
        compareEndValue: KnockoutObservable<number>;
        inputCheckCondition: KnockoutObservable<number> = ko.observable(0);

        constructor(param) {
            this.targetNO = ko.observable(param.targetNO);
            this.conditionAtr = ko.observable(param.conditionAtr);
            this.useAtr = ko.observable(param.useAtr);
            this.uncountableAtdItem = ko.observable(param.uncountableAtdItem);
            this.countableAddAtdItems = ko.observableArray(param.countableAddAtdItems);
            this.countableSubAtdItems = ko.observableArray(param.countableSubAtdItems);
            this.conditionType = ko.observable(param.conditionType);
            this.singleAtdItem = ko.observable(param.singleAtdItem);
            this.compareStartValue = ko.observable(param.compareStartValue);
            this.compareEndValue = ko.observable(param.compareEndValue);
            this.compareOperator = ko.observable(param.compareOperator);
        }
        
    }
    
    enum MonthlyAttendanceItemAtr {
        /* 時間 */
        TIME = 1,
        /* 回数 */
        NUMBER = 2,
        /* 日数 */
        DAYS = 3,
        /* 金額 */
        AMOUNT = 4,
        /* マスタを参照する */
        REFER_TO_MASTER = 5
    }

    enum DailyAttendanceItemAtr {
        /* コード */
        Code = 0,
        /* マスタを参照する */
        ReferToMaster = 1,
        /* 回数*/
        NumberOfTime = 2,
        /* 金額*/
        AmountOfMoney = 3,
        /* 区分 */
        Classification = 4,
        /* 時間 */
        Time = 5,
        /* 時刻*/
        TimeOfDay = 6,
        /* 文字 */
        Character = 7
    }

}
