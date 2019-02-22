module nts.uk.at.view.kdr002.a.viewmodel {
    import ScheduleBatchCorrectSettingSave = service.model.ScheduleBatchCorrectSettingSave;
    import Ccg001ReturnedData = nts.uk.com.view.ccg.share.ccg.service.model.Ccg001ReturnedData;
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
    import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;

    import alError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog.info;
    import formatDate = nts.uk.time.formatDate;
    import parseYearMonthDate = nts.uk.time.parseYearMonthDate;
    import char = nts.uk.characteristics;

    export class ScreenModel {



        selectedImplementAtrCode: KnockoutObservable<number>;
        resetWorkingHours: KnockoutObservable<boolean>;
        resetDirectLineBounce: KnockoutObservable<boolean>;
        resetMasterInfo: KnockoutObservable<boolean>;
        resetTimeChildCare: KnockoutObservable<boolean>;
        resetAbsentHolidayBusines: KnockoutObservable<boolean>;
        resetTimeAssignment: KnockoutObservable<boolean>;

        periodDate: KnockoutObservable<any>;
        copyStartDate: KnockoutObservable<Date>;
        /** Return data */
        lstSearchEmployee: KnockoutObservableArray<EmployeeSearchDto> = ko.observableArray([]);
        startDateString: KnockoutObservable<string> = ko.observable(moment());
        endDateString: KnockoutObservable<string> = ko.observable(moment());
        closureId: KnockoutObservable<number> = ko.observable(0);

        lstLabelInfomation: KnockoutObservableArray<string>;
        infoPeriodDate: KnockoutObservable<string>;
        lengthEmployeeSelected: KnockoutObservable<string>;

        ccgcomponent: GroupOption = {
            /** Common properties */
            systemType: 2, // システム区分
            showEmployeeSelection: false, // 検索タイプ
            showQuickSearchTab: true, // クイック検索
            showAdvancedSearchTab: true, // 詳細検索
            showBaseDate: false, // 基準日利用
            showClosure: true, // 就業締め日利用
            showAllClosure: true, // 全締め表示
            showPeriod: true, // 対象期間利用
            periodFormatYM: true, // 対象期間精度

            /** Required parameter */
            baseDate: moment().format("YYYY-MM-DD"), // 基準日
            periodStartDate: moment(), // 対象期間開始日
            periodEndDate: moment(), // 対象期間終了日
            inService: true, // 在職区分
            leaveOfAbsence: true, // 休職区分
            closed: true, // 休業区分
            retirement: true, // 退職区分

            /** Quick search tab options */
            showAllReferableEmployee: true, // 参照可能な社員すべて
            showOnlyMe: true, // 自分だけ
            showSameWorkplace: true, // 同じ職場の社員
            showSameWorkplaceAndChild: true, // 同じ職場とその配下の社員

            /** Advanced search properties */
            showEmployment: true, // 雇用条件
            showWorkplace: true, // 職場条件
            showClassification: true, // 分類条件
            showJobTitle: true, // 職位条件
            showWorktype: true, // 勤種条件
            isMutipleCheck: true, // 選択モード

            /** Return data */
            returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                self.lstSearchEmployee(data.listEmployee);
                self.applyKCP005ContentSearch(data.listEmployee);
                self.startDateString(data.periodStart);
                self.endDateString(data.periodEnd);
                self.closureId(data.closureId);
            }
        };

        // Employee tab
        lstPersonComponentOption: any;
        selectedEmployeeCode: KnockoutObservableArray<string> = ko.observableArray([]);
        employeeName: KnockoutObservable<string>;
        employeeList: KnockoutObservableArray<UnitModel>;
        alreadySettingPersonal: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
        ccgcomponentPerson: GroupOption;

        //date type group
        dateTypes: KnockoutObservableArray<HolidayRemainingModel> = ko.observableArray([
            //A3_3
            { name: getText('KDR002_3'), code: 0 }, // 現在の情報
            //A3_4
            { name: getText('KDR002_4'), code: 1 } // 過去の情報
        ]);
        selectedDateType: KnockoutObservable<number> = ko.observable(0);

        // reference type
        referenceTypes: KnockoutObservableArray<HolidayRemainingModel> = ko.observableArray([
            //A3_6
            { name: getText('KDR002_6'), code: 0 }, // 現在の情報
            //A3_7
            { name: getText('KDR002_7'), code: 1 } // 過去の情報
        ]);
        selectedReferenceType: KnockoutObservable<number> = ko.observable(0);

        //print Date
        printDate: KnockoutObservable<number> = ko.observable(Number(moment().format("YYYYMM")));

        // Page Break Type
        pageBreakTypes: KnockoutObservableArray<any> = ko.observableArray([
            { name: getText('なし'), code: 0 },
            { name: getText('職場'), code: 1 }
        ]);
        pageBreakSelected: KnockoutObservable<string> = ko.observable(0);

        closureDate: KnockoutObservable<Closure> = ko.observable();

        constructor() {
            let self = this;
        }

        /**
        * apply ccg001 search data to kcp005
        */
        public applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): void {
            let self = this;
            self.employeeList([]);
            let employeeSearchs: UnitModel[] = [];
            self.selectedEmployeeCode([]);
            for (let employeeSearch of dataList) {
                let employee: UnitModel = {
                    code: employeeSearch.employeeCode,
                    employeeId: employeeSearch.employeeId,
                    name: employeeSearch.employeeName,
                    workplaceCode: employeeSearch.workplaceCode,
                    workplaceId: employeeSearch.workplaceId,
                    workplaceName: employeeSearch.workplaceName
                };
                employeeSearchs.push(employee);
                self.selectedEmployeeCode.push(employee.code);
            }
            self.employeeList(employeeSearchs);
            self.lstPersonComponentOption = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeList,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: self.selectedEmployeeCode,
                isDialog: false,
                isShowNoSelectRow: false,
                alreadySettingList: self.alreadySettingPersonal,
                isShowWorkPlaceName: true,
                isShowSelectAllButton: false,
                maxWidth: 550,
                maxRows: 15
            };
        }

        /**
        * start page data 
        */
        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            nts.uk.ui.block.invisible();
            service.findClosureByEmpID().done(function(closure) {
                if (closure) {
                    self.setDataWhenStart(closure);
                    self.closureDate(closure.closureMonth);
                } else {
                    alError({ messageId: 'Msg_1134' });
                }
                nts.uk.ui.block.clear();
                dfd.resolve(self);
            }).fail(function(res) {
                alError({ messageId: res.messageId });
            }).always(() => {
                nts.uk.ui.block.clear();
            });
            return dfd.promise();
        }

        setDataWhenStart(closure) {
            let self = this;
            char.restore("screenInfo").done((screenInfo: IScreenInfo) => {
                if (screenInfo) {
                    self.setScreenInfo(closure, screenInfo);
                } else {
                    self.printDate(closure.closureMonth);
                }
            });
        }

        //set screen Info

        setScreenInfo(closure, screenInfo: IScreenInfo) {
            let self = this;
            self.selectedDateType(screenInfo.selectedDateType);
            self.selectedReferenceType(screenInfo.selectedReferenceType);
            if (screenInfo.printDate) {
                self.printDate(screenInfo.printDate);
            } else {
                self.printDate(closure.closureMonth);
            }
            self.pageBreakSelected(screenInfo.pageBreakSelected);
        }

        /**
         * function export excel button
         */
        public exportButton() {
            let self = this,
                printQuery = new PrintQuery(self);

            $('.nts-input').trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;
            }

            //印刷前チェック処理
            if (!self.selectedEmployeeCode().length) {
                alError({ messageId: 'Msg_884' });
                return;
            }
            //事前条件①および②をチェックする
            if (printQuery.selectedDateType == 1) {
                self.checkClosureDate().done((isNoError) => {
                    if (!isNoError) {
                        alError({ messageId: "Msg_1500" });
                        return;
                    } else {
                        self.doPrint(printQuery);
                    }
                });
            } else {
                self.doPrint(printQuery);
            }
        }
        
        public doPrint(printQuery) {
            block.invisible();
            service.exportExcel(printQuery).done(() => {
            }).fail(function(res: any) {
                alError({ messageId: res.messageId });
            }).always(() => {
                block.clear();
            });
            char.save('screenInfo', printQuery.toScreenInfo());
        }

        public checkClosureDate() : JQueryPromise<any>{
            let self = this,
                closureId = self.closureId(),
                isNotError = true,
                dfd = $.Deferred() ;
            block.invisible();
            service.findClosureById(closureId).done((closureData) => {

                //①社員範囲選択の就業締め日 ≠ 全締め　&参照区分 = 過去 & 就業締め日の当月 < 指定月→ 出力エラー　(#Msg_1500)
                if (closureData.closureSelected) {
                    if (closureData.month < self.printDate()) {
                        dfd.resolve(false);
                    } else {
                        dfd.resolve(true);
                    }
                }
                else {
                    //is mean 社員範囲選択の就業締め日 = 全締め
                    //②社員範囲選択の就業締め日 = 全締め　&　参照区分 = 過去 &全ての就業締め日の中の一番未来の締め月 < 指定月→ 出力エラー　(#Msg_1500)
                    block.invisible();
                    service.findAllClosure().done((closures) => {
                        _.forEach(closures, (closure) => {
                            if (closure.month < self.printDate()) {
                                dfd.resolve(false);
                            }
                        });
                        dfd.resolve(true);
                    }).always(() => {
                        block.clear();
                    });

                }
            }).always(() => {
                block.clear();
            });

              return dfd.promise();
        }

    }

    export interface UnitModel {
        code: string;
        employeeId: string;
        name?: string;
        workplaceCode?: string;
        workplaceId?: string;
        workplaceName?: string;
    }

    export class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }
    export class PrintQuery {
        programName: string;
        companyName: string;
        exportTime: string;
        // 対象期間
        selectedDateType: number;
        // 参照区分
        selectedReferenceType: number;
        // 参照区分
        printDate: number;
        // 改ページ区分
        pageBreakSelected: number;
        selectedEmployees: Array<UnitModel>;

        constructor(screen: ScreenModel) {
            let self = this,
                program = nts.uk.ui._viewModel.kiban.programName().split(" ");
            self.programName = "KDR002" + (program[1] != null ? program[1] : ""); 
            self.companyName = "";
            self.exportTime = moment();
            self.selectedDateType = screen.selectedDateType();
            self.selectedReferenceType = screen.selectedReferenceType();
            self.printDate = screen.printDate();
            self.pageBreakSelected = screen.pageBreakSelected();
            self.selectedEmployees = _.filter(screen.employeeList(), (e) => { return _.indexOf(screen.selectedEmployeeCode(), e.code); });

        }

        public toScreenInfo() {
            let self = this;
            return {
                selectedDateType: self.selectedDateType,
                selectedReferenceType: self.selectedReferenceType,
                printDate: self.printDate,
                pageBreakSelected: self.pageBreakSelected
            }
        }
    }

    export interface IScreenInfo {
        selectedDateType: number,
        selectedReferenceType: number,
        printDate: number,
        pageBreakSelected: number
    }

    export class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }

    // スケジュール一括修正設定
    export class ScheduleBatchCorrectSetting {
        // 勤務種類
        worktypeCode: string;

        // 社員ID
        employeeId: string;

        // 終了日
        endDate: string;

        // 開始日
        startDate: string;

        // 就業時間帯
        worktimeCode: string;

        constructor() {
            var self = this;
            self.worktypeCode = '';
            self.employeeId = '';
            self.endDate = '';
            self.startDate = '';
            self.worktimeCode = '';
        }
    }


}