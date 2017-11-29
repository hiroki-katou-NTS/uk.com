module nts.uk.at.view.kdw007.b.viewmodel {

    export class ScreenModel {
        enumConditionAtr: KnockoutObservableArray<any> = ko.observableArray([
            { code: 0, name: "回数" },
            { code: 1, name: "時間" },
            { code: 2, name: "時刻" },
            { code: 3, name: "金額" },
        ]);
        enumConditionType: KnockoutObservableArray<any> = ko.observableArray([
            { code: 0, name: "固定値", enable: true },
            { code: 1, name: "勤怠項目", enable: true }
        ]);
        enumCompareOperator: KnockoutObservableArray<any> = ko.observableArray([
            { code: 0, name: "等しい（＝）" },
            { code: 1, name: "等しくない（≠）" },
            { code: 2, name: "より大きい（＞）" },
            { code: 3, name: "以上（≧）" },
            { code: 4, name: "より小さい（＜）" },
            { code: 5, name: "以下（≦）" },
            { code: 6, name: "範囲の間（境界値を含まない）（＜＞）" },
            { code: 7, name: "範囲の間（境界値を含む）（≦≧）" },
            { code: 8, name: "範囲の外（境界値を含まない）（＞＜）" },
            { code: 9, name: "範囲の外（境界値を含む）（≧≦）" }
        ]);
        currentAtdItemCondition: any;
        displayTargetAtdItems: KnockoutObservable<string> = ko.observable("");
        displayCompareAtdItems: KnockoutObservable<string> = ko.observable("");

        constructor() {
            let self = this;
            self.currentAtdItemCondition = ko.mapping.fromJS(new ErAlAtdItemCondition());
            self.currentAtdItemCondition.compareOperator.subscribe((value) => {
                if (value > 5) {
                    self.enumConditionType([
                        { code: 0, name: "固定値", enable: true },
                        { code: 1, name: "勤怠項目", enable: false }
                    ]);
                    self.currentAtdItemCondition.conditionType(0);
                } else {
                    self.enumConditionType([
                        { code: 0, name: "固定値", enable: true },
                        { code: 1, name: "勤怠項目", enable: true }
                    ]);
                }
            });
        }

        openSelectAtdItemDialogTarget() {
            let self = this;
            if (self.currentAtdItemCondition.conditionAtr() === 2) {
                //Open dialog KDL021
                nts.uk.ui.windows.setShared('Multiple', false);
                nts.uk.ui.windows.setShared('AllAttendanceObj', [1,2,3]);
                nts.uk.ui.windows.setShared('SelectedAttendanceId', []);
                nts.uk.ui.windows.sub.modal("at", "/view/kdl/021/a/index.xhtml").onClosed(() => {
                    let output = nts.uk.ui.windows.getShared("selectedChildAttendace");
                    if (output) {
                        debugger;
                    }
                });
            } else {
                //Open dialog KDW007C
                let param = {
                    lstAllItems: ["1", "2"],
                    lstAddItems: [],
                    lstSubItems: []    
                };
                nts.uk.ui.windows.setShared("KDW007Params", param);
                nts.uk.ui.windows.sub.modal("at", "/view/kdw/007/c/index.xhtml").onClosed(() => {
                    let output = getShared("KDW007CResults");
                    if (output) {
                        debugger;
                    }
                });

            }
        }
    }

    export class ErAlAtdItemCondition {

        NO: number;
        conditionAtr: number;
        useAtr: boolean;
        uncountableAtdItem: number;
        countableAddAtdItems: Array<number>;
        countableSubAtdItems: Array<number>;
        conditionType: number;
        compareOperator: number;
        singleAddAtdItems: Array<number>;
        singleSubAtdItems: Array<number>;
        compareStartValue: number;
        compareEndValue: number;

        constructor() {
            this.NO = 0;
            this.conditionAtr = 0;
            this.useAtr = false;
            this.uncountableAtdItem = null;
            this.countableAddAtdItems = [];
            this.countableSubAtdItems = [];
            this.conditionType = 0;
            this.singleAddAtdItems = [];
            this.singleSubAtdItems = [];
            this.compareStartValue = 0;
            this.compareEndValue = 0;
            this.compareOperator = 0;
        }
    }

}