module nts.uk.pr.view.qmm018.a.viewmodel {
    import model = nts.uk.pr.view.qmm018.share.model;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import alertError = nts.uk.ui.dialog.alertError;
    import errors = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;

    export class ScreenModel {

        //Checkboxs
        checkWage: KnockoutObservable<boolean> = ko.observable(null);
        exceptionFormula: KnockoutObservable<number> = ko.observable(null);
        targetWageItem: KnockoutObservable<string> = ko.observable('');
        lstTargetWageItem: KnockoutObservableArray<IStatement> = ko.observableArray(null);
        targetWorkingDaysItem: KnockoutObservable<string> = ko.observable('');
        lstTargetWorkingDaysItem: KnockoutObservableArray<IStatement> = ko.observableArray(null);

        categoryAtr: KnockoutObservable<number> = ko.observable(0);
        salaryItemId: KnockoutObservable<string> = ko.observable('');
        displayData: KnockoutObservable<DisplayData> = ko.observable(new DisplayData(null));

        fractionProcessingAtr: KnockoutObservableArray<viewmodel.ItemModel> = ko.observableArray([]);
        selectedFractionProcessingAtr: KnockoutObservableArray<number> = ko.observable(null);

        //Fix swapbutton
        attendanceDays: KnockoutObservableArray<viewmodel.ItemModel> = ko.observableArray([]);
        selectedAttendanceDays: KnockoutObservableArray<number> = ko.observable(null);

        enableFractionProcessingAtr: KnockoutObservable<boolean> = ko.observable(true);
        enableTargetWorkingDaysItem: KnockoutObservable<boolean> = ko.observable(true);

        disExceptionFormula: KnockoutObservable<string> = ko.observable('');

        constructor() {
            let self = this;

            self.exceptionFormula.subscribe(x => {
                self.disExceptionFormula(getText('QMM018_8',[x]));
            });

            self.fractionProcessingAtr(getDaysFractionProcessing());
            self.selectedFractionProcessingAtr.subscribe(x => {
                if (x == 0) {
                    self.displayData().averageWageCalculationSet().daysFractionProcessing(DaysFractionProcessing.AFTER);
                } else {
                    self.displayData().averageWageCalculationSet().daysFractionProcessing(DaysFractionProcessing.BEFORE);
                }
            });

            self.attendanceDays(getAttendanceDays());
            self.selectedAttendanceDays.subscribe(x => {
                if (x == 0) {
                    self.enableFractionProcessingAtr(false);
                    self.enableTargetWorkingDaysItem(true);
                    self.displayData().averageWageCalculationSet().obtainAttendanceDays(AttendanceDays.FROM_STATEMENT_ITEM);
                } else {
                    self.enableFractionProcessingAtr(true);
                    self.enableTargetWorkingDaysItem(false);
                    self.displayData().averageWageCalculationSet().obtainAttendanceDays(AttendanceDays.FROM_EMPLOYMENT);
                }
            });

            self.checkWage.subscribe(x => {
                if (x) {
                    self.displayData().averageWageCalculationSet().decimalPointCutoffSegment(1);
                } else {
                    self.displayData().averageWageCalculationSet().decimalPointCutoffSegment(0);
                }
            });
            self.getAllData();
        }

        getAllData(): JQueryPromise<any>{
            let self = this,
            dfd = $.Deferred();
            service.getStatemetItemData().done(function (dataDisplay: <IDisplayData>) {
                if (dataDisplay) {
                    self.displayData(new DisplayData(dataDisplay));
                    self.checkWage(self.displayData().averageWageCalculationSet().decimalPointCutoffSegment());
                    self.exceptionFormula(self.displayData().averageWageCalculationSet().exceptionFormula());
                    self.selectedAttendanceDays(self.displayData().averageWageCalculationSet().obtainAttendanceDays());
                    self.selectedFractionProcessingAtr(self.displayData().averageWageCalculationSet().daysFractionProcessing());

                    //lstPayment item
                    self.lstTargetWageItem(self.displayData().lstStatemetPaymentItem());
                    let lstname = self.lstTargetWageItem().map(value => value.name);
                    var stringTargetWageItem = lstname.toString();
                    var newStringTargetWageItem = stringTargetWageItem.replace(/,/g, "+");
                    self.targetWageItem(newStringTargetWageItem);

                    //lstAttendanceItem
                    self.lstTargetWorkingDaysItem(self.displayData().lstStatemetAttendanceItem());
                    let lstname = self.lstTargetWorkingDaysItem().map(value => value.name);
                    var stringTargetWageItem = lstname.toString();
                    var newStringTargetWageItem = stringTargetWageItem.replace(/,/g, "+");
                    self.targetWorkingDaysItem(newStringTargetWageItem);
                }
                else {
                    self.displayData(null);
                }
            }).fail(function (error) {
                alertError(error);
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        registration() {
            let self = this;
            if(self.selectedAttendanceDays() == 0 ){
                self.displayData().averageWageCalculationSet().daysFractionProcessing(null);
            }
            self.displayData().averageWageCalculationSet().exceptionFormula(self.exceptionFormula());
            self.displayData().lstStatemetPaymentItem(self.lstTargetWageItem());
            if(self.selectedAttendanceDays() ==  1){
                self.displayData().lstStatemetAttendanceItem()[0];
            }
            else {
                self.displayData().lstStatemetAttendanceItem(self.lstTargetWorkingDaysItem());
            }
            let data = self.displayData();
            $("#A2_4").trigger("validate");
            if (errors.hasError() === false) {
                block.invisible();
                // create
                service.registration(ko.toJS(data)).done(() => {
                    dialog.info({ messageId: "Msg_15" }).then(() => {
                        self.getAllData().done(function() {
                        });
                    });
                }).fail(function (error) {
                    alertError(error);
                }).always(function () {
                    block.clear();
                    $("#A2_3").focus();
                });
            }
        };

        correctionLog() {
        };

        wageItemSet() {
            let self = this;
            setShared("QMM018_A_SETTING", {
                categoryAtr: model.CategoryAtr.PAYMENT_ITEM,
                statementListSelected: self.lstTargetWageItem()
            });

            nts.uk.ui.windows.sub.modal('../b/index.xhtml').onClosed(() => {
                let setting = getShared("QMM018_B_SETTING");

                if(setting.isSetting == true) {
                    self.lstTargetWageItem(setting.statementListSelected);

                    let lstname = self.lstTargetWageItem().map(value => value.name);
                    var stringTargetWageItem = lstname.toString();
                    var newStringTargetWageItem = stringTargetWageItem.replace(/,/g, "+");
                    self.targetWageItem(newStringTargetWageItem);
                }

                $("#A2_3").focus();
            });
        };


        workingDaysItemSet() {
            let self = this;

            setShared("QMM018_A_SETTING", {
                categoryAtr: model.CategoryAtr.ATTEND_ITEM,
                statementListSelected: self.lstTargetWorkingDaysItem()
            });

            nts.uk.ui.windows.sub.modal('../b/index.xhtml').onClosed(() => {
                let setting = getShared("QMM018_B_SETTING");

                if(setting.isSetting == true) {
                    self.lstTargetWorkingDaysItem(setting.statementListSelected);

                    let lstname = self.lstTargetWorkingDaysItem().map(value => value.name);
                    var stringTargetWageItem = lstname.toString();
                    var newStringTargetWageItem = stringTargetWageItem.replace(/,/g, "+");
                    self.targetWorkingDaysItem(newStringTargetWageItem);
                }

                $("#A2_3").focus();
            });
        };
    }

    export interface IAverageWageCalculationSet {
        exceptionFormula: number;
        obtainAttendanceDays: number;
        daysFractionProcessing: number;
        decimalPointCutoffSegment: number;
    }

    export class AverageWageCalculationSet {
        exceptionFormula: KnockoutObservable<number> = ko.observable(null);
        obtainAttendanceDays: KnockoutObservable<number> = ko.observable(null);
        daysFractionProcessing: KnockoutObservable<number> = ko.observable(null);
        decimalPointCutoffSegment: KnockoutObservable<number> = ko.observable(null);


        constructor(params: IAverageWageCalculationSet) {
            let self = this;
            this.exceptionFormula(params ? params.exceptionFormula : 0);
            this.obtainAttendanceDays(params ? params.obtainAttendanceDays : 0);
            this.daysFractionProcessing(params ? params.daysFractionProcessing : 0);
            this.decimalPointCutoffSegment(params ? params.decimalPointCutoffSegment : 0);
        }
    }

    export interface IStatement {
        salaryItemId: string;
        categoryAtr: number;
        itemNameCd: string;
        name: string;
    }

    export class Statement {
        salaryItemId: KnockoutObservable<string> = ko.observable(null);
        categoryAtr: KnockoutObservable<number> = ko.observable(null);
        itemNameCd: KnockoutObservable<string> = ko.observable(null);
        name: KnockoutObservable<string> = ko.observable(null);

        constructor(params: IStatement) {
            this.salaryItemId(params ? params.salaryItemId : null);
            this.categoryAtr(params ? params.categoryAtr : null);
            this.itemNameCd(params ? params.itemNameCd : null);
            this.name(params ? params.name : null);
        }
    }

    export interface IDisplayData {
        averageWageCalculationSet: IAverageWageCalculationSet;
        lstStatemetPaymentItem: IStatement;
        lstStatemetAttendanceItem: IStatement;
    }

    export class DisplayData {
        averageWageCalculationSet: KnockoutObservable<AverageWageCalculationSet>;
        lstStatemetPaymentItem: KnockoutObservableArray<Statement>;
        lstStatemetAttendanceItem: KnockoutObservableArray<Statement>;

        constructor(params: IDisplayData) {
            let self = this;
            if (params) {
                self.averageWageCalculationSet = ko.observable(new AverageWageCalculationSet(params.averageWageCalculationSet));
                self.lstStatemetPaymentItem = ko.observableArray(params.lstStatemetPaymentItem);
                self.lstStatemetAttendanceItem = ko.observableArray(params.lstStatemetAttendanceItem);
            }
            else {
                self.averageWageCalculationSet = ko.observable(new AverageWageCalculationSet(null));
                self.lstStatemetPaymentItem = ko.observableArray([]);
                self.lstStatemetAttendanceItem = ko.observableArray([]);
            }

        }
    }

    export class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export enum AttendanceDays {

        FROM_STATEMENT_ITEM = 0,

        FROM_EMPLOYMENT = 1
    }

    export function getAttendanceDays(): Array<ItemModel> {
        return [
            new ItemModel(AttendanceDays.FROM_EMPLOYMENT.toString(), getText('enum_SelectWorkDays_FROM_EMPLOYMENT')),
            new ItemModel(AttendanceDays.FROM_STATEMENT_ITEM.toString(), getText('enum_SelectWorkDays_FROM_STATEMENT_ITEM'))
        ];
    }

    export enum DaysFractionProcessing {

        AFTER = 0,

        BEFORE = 1
    }

    export function getDaysFractionProcessing(): Array<ItemModel> {
        return [
            new ItemModel(DaysFractionProcessing.AFTER.toString(), getText('enum_DaysFractionProcessing_AFTER')),
            new ItemModel(DaysFractionProcessing.BEFORE.toString(), getText('enum_DaysFractionProcessing_BEFOR'))
        ];
    }

}