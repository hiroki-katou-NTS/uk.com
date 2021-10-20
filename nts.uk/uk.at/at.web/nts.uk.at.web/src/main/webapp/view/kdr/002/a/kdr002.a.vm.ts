module nts.uk.at.view.kdr002.a.viewmodel {
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
    import Closure = service.model.Closure;
    import char = nts.uk.characteristics;

    const companyId: string = __viewContext.user.companyId;
    const userId: string = __viewContext.user.employeeId;
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
        startDateString: KnockoutObservable<any> = ko.observable(moment());
        endDateString: KnockoutObservable<any> = ko.observable(moment());
        closureId: KnockoutObservable<number> = ko.observable(0);

        lstLabelInfomation: KnockoutObservableArray<string>;
        infoPeriodDate: KnockoutObservable<string>;
        lengthEmployeeSelected: KnockoutObservable<string>;

        closureData: KnockoutObservable<any> = ko.observable();
        //_____CCG001________
        ccgcomponent : GroupOption;

        // Employee tab
        lstPersonComponentOption: any;
        selectedEmployeeCode: KnockoutObservableArray<string> = ko.observableArray([]);
        employeeName: KnockoutObservable<string>;
        employeeList: KnockoutObservableArray<UnitModel>;
        alreadySettingPersonal: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
        ccgcomponentPerson: GroupOption;

        //date type group
        // A3_2 過去の情報
        dateTypes: KnockoutObservableArray<ItemModel> = ko.observableArray([
            //A3_3
            { code: 0, name: getText('KDR002_3'),  }, // 現在（年休期間の途中）の年休情報をチェックする
            //A3_10
            { code: 1, name: getText('KDR002_41'),  }, // １年経過時点の年休情報をチェックする
            //A3_4
            { code: 2, name: getText('KDR002_4'), } // １年以上前の年休情報をチェックする
        ]);
        selectedDateType: KnockoutObservable<number> = ko.observable(0);

        // reference type
        referenceTypes: KnockoutObservableArray<ItemModel> = ko.observableArray([
            //A3_6
            { code: 0, name: getText('KDR002_6') }, // 実績のみ
            //A3_7
            { code: 1, name: getText('KDR002_7')  } // スケジュール・申請含む
        ]);
        selectedReferenceType: KnockoutObservable<number> = ko.observable(0);

        //print Date
        printDate: KnockoutObservable<number> = ko.observable(Number(moment().format("YYYYMM")));

        // Page Break Type
        // A4_3
        pageBreakTypes: KnockoutObservableArray<any> = ko.observableArray([
            { code: 0 , name: getText('Enum_BreakPageType_NONE') }, //なし
            { code: 1 , name: getText('Enum_BreakPageType_WORKPLACE')  } //職場
        ]);
        pageBreakSelected: KnockoutObservable<number> = ko.observable(0);
        // A6_3
        printAnnualLeaveDate: KnockoutObservableArray<any> = ko.observableArray([
            { code: 0 , name: getText('KDR002_52') }, //年月日
            { code: 1 , name: getText('KDR002_53')  } //月日
        ]);
        printAnnualLeaveDateSelect: KnockoutObservable<number> = ko.observable(1);

        // A7_2
        doubleTrack: KnockoutObservable<boolean> = ko.observable(false);


        closureDate: KnockoutObservable<Closure> = ko.observable();


        referenceTypeA3_5: KnockoutObservableArray<any> = ko.observableArray([
            { code: 0, name: nts.uk.resource.getText("KDR002_6") },
            { code: 1, name: nts.uk.resource.getText("KDR002_7") }
        ]);


        period: KnockoutObservable<any> = ko.observable({ startDate: '', endDate: '' });

        // A5_7
        isExtraction: KnockoutObservable<boolean> = ko.observable(false);
        // A5_2
        inputExtraction: KnockoutObservable<number> = ko.observable(null);
        requiredInputExtraction: KnockoutObservable<boolean>;
        // A5_3
        optionExtractionValue: KnockoutObservable<number> = ko.observable(1);

        selectedDateTypeOption: KnockoutObservableArray<any> = ko.observableArray([
          { code: 0, name: getText("KDR002_3") },
          { code: 1, name: getText("KDR002_41") },
          { code: 2, name: getText("KDR002_4") },
        ]);

        constructor() {
            let self = this;
            
            self.requiredInputExtraction = ko.computed(function() {
                nts.uk.ui.errors.clearAll();
                return self.isExtraction();
            });

            self.closureId.subscribe((value) => {
                if (value === 0 ) {
                  service.findAllClosure().done((closures) => {
                    let tempDate: any = _.minBy(closures, 'closureId');
                    self.updatePeriod(tempDate);
                    // Only set period when start screen
                    if ($("#ccgcomponent").contents().length === 0) {
                      const periodStartDate = moment(tempDate.month, "YYYYMM");
                      const periodEndDate = moment(tempDate.month, "YYYYMM");
                      self.reloadCCG001(periodStartDate, periodEndDate);
                    }
                  });            
                } else {
                  service.findClosureById(value).done(closure => {
                    self.updatePeriod(closure)
                    // Only set period when start screen
                    if ($("#ccgcomponent").contents().length === 0) {
                      const periodStartDate = moment(closure.month, "YYYYMM");
                      const periodEndDate = moment(closure.month, "YYYYMM");
                      self.reloadCCG001(periodStartDate, periodEndDate);
                    } 
                  });
                }
            });
            self.closureId.valueHasMutated();
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
                    affiliationName: employeeSearch.affiliationName
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
                isDialog: true,
                isShowNoSelectRow: false,
                alreadySettingList: self.alreadySettingPersonal,
                isShowWorkPlaceName: true,
                isShowSelectAllButton: false,
                maxRows: 20
            };
        }

        private reloadCCG001(periodStart: any, periodEnd: any) {
          const self = this;
          //_____CCG001________
          self.ccgcomponent = {
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
            periodStartDate: periodStart, // 対象期間開始日
            periodEndDate: periodEnd, // 対象期間終了日
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
        }

        private updatePeriod(closure: any) {
          const self = this;
          let closureDate: number = closure.closureSelected.closureDate;
          let endDateRange = moment(closure.month, "YYYYMM").add(1, 'year').subtract(1, 'month');
          // Case closureDate is last day of month
          if (closureDate === 0) {
            endDateRange = endDateRange.endOf('M');
          } else {
            endDateRange = endDateRange.set("date", closureDate);
          }
          // Fix bug khi tháng bắt đầu là tháng 2 nhuận
          let startDateRange = endDateRange.clone().subtract(1, 'year');
          if (startDateRange.month() === 2) {
            startDateRange = startDateRange.add(1, 'month').startOf('month');
          } else {
            startDateRange = startDateRange.add(1, 'day');
          }
          let monthDate = moment(closure.month, "YYYYMM").subtract(1, 'month').format("YYYYMM");
          self.printDate(parseInt(monthDate));
          self.period({ startDate: startDateRange, endDate: endDateRange });
        }

        //起動する
        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();
            //社員に対応する処理締めを取得する
            service.findClosureByEmpID().done(function(closure: any) {
                if (closure) {
                    // ユーザー固有情報「年休管理表の出力条件」を取得する
                    self.setDataWhenStart(closure);

                    //  取得した「締め.当月」の前月を指定年月へ移送
                    let currentMonth: string  = moment(closure.closureMonth.toString(), 'YYYYMMDD').format('YYYYMMDD');
                    let closureDay: string = closure.closureHistories[0].closureDate.closureDay;
                    let startDateRange: any;
                    let endDateRange: any;
                    // 締め日:末日 - if closure date is last day of month
                    if(closure.closureHistories[0].closureDate.lastDayOfMonth) {
                        startDateRange = moment(currentMonth).format("YYYYMMDD");
                        endDateRange = moment(startDateRange).add(1, 'year').subtract(1, 'day').format("YYYYMMDD");
                    } else {
                        // 締め日: n 日
                        startDateRange = moment(currentMonth).add(closureDay, 'day').subtract(1, 'month').format("YYYYMMDD");
                        endDateRange = moment(startDateRange).add(1, 'year').subtract(1, 'day').format("YYYYMMDD");
                    }
                    let closureMonthAfter: number = parseInt(moment(closure.closureMonth, "YYYYMM").subtract(1, 'month').format("YYYYMM"));
                    self.printDate(closureMonthAfter);
                    //取得した「締め.当月」より期間へ移送
                    self.period({ startDate: startDateRange, endDate: endDateRange });


                    self.closureDate(closure.closureMonth);
                } else {
                    //指定月 = null
                    //指定月のみ取得した「締め.当月」へ移送し、他データを画面に表示する
                    // self.printDate(Number(moment().subtract(1, 'months').format("YYYYMM")))
                    let startDateRange = moment().subtract(1, 'day').format("YYYYMMDD");
                    let endDateRange = moment(startDateRange).add(1, 'year').subtract(1, 'day').format("YYYYMMDD");

                    self.printDate(closure.closureMonth);
                    //取得した「締め.当月」より期間へ移送
                    self.period({ startDate: startDateRange, endDate: endDateRange });

                    //エラーメッセージ(#Msg_1134)を表示
                    alError({ messageId: 'Msg_1134' });
                }

                block.clear();
                dfd.resolve(self);
            }).fail(function(res) {
                alError({ messageId: res.messageId });
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        setDataWhenStart(closure: any) {
            let self = this;
            service.restoreCharacteristic(companyId,userId).done((screenInfo: IScreenInfo) => {
                //ユーザー固有情報「年休管理表の出力条件」をチェックする
                if (screenInfo) {
                    //データあり
                    self.setScreenInfo(closure, screenInfo);
                }
                else {
                    //データなし
                    let closureMonthAfter: number = parseInt(moment(closure.closureMonth, "YYYYMM").subtract(1, 'month').format("YYYYMM"));
                    self.printDate(closureMonthAfter);
                }
            });
        }

        //set screen Info

        setScreenInfo(closure: any, screenInfo: IScreenInfo) {
            let self = this;
            self.selectedDateType(screenInfo.selectedDateType);
            self.selectedReferenceType(screenInfo.selectedReferenceType);
            self.isExtraction(screenInfo.extCondition);
            self.inputExtraction(screenInfo.extConditionSettingDay);
            self.optionExtractionValue(screenInfo.extConditionSettingCoparison);
            self.doubleTrack(screenInfo.doubleTrack);
            self.printAnnualLeaveDateSelect(_.isNil(screenInfo.printAnnualLeaveDate) ? 1 : screenInfo.printAnnualLeaveDate );
            if (screenInfo.printDate) {
                //指定月 ≠ null
                //取得したデータを画面に表示する
                self.printDate(screenInfo.printDate);
            } else {
                //指定月 = null
                //指定月のみ取得した「締め.当月」へ移送し、他データを画面に表示する
                self.printDate(closure.closureMonth);
            }
            self.pageBreakSelected(screenInfo.pageBreakSelected);
        }

        exportExcel() {
            let self = this;

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
            block.invisible();
            self.checkClosureDate().done((isNoError) => {
                block.clear();
                let printQuery: any = new PrintQuery(self);
                if(!_.isNil(self.period)) {
                    printQuery.startDate = new Date(self.period().startDate);
                    printQuery.endDate = new Date(self.period().endDate);
                }
                // set mode print excel
                printQuery.mode = 0;
                if (printQuery.selectedDateType == 2 || printQuery.selectedDateType == 1) {
                    if (!isNoError) {
                        alError({ messageId: "Msg_1500" });
                        return;
                    } else {
                        self.doPrint(printQuery);
                    }
                } else {
                    self.doPrint(printQuery);
                }

            }).fail(() => {
                block.clear();
            });
        }

        exportPDF() {
            let self = this;

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
            block.invisible();
            self.checkClosureDate().done((isNoError) => {
                block.clear();
                let printQuery: any = new PrintQuery(self);
                if(!_.isNil(self.period)) {
                    printQuery.startDate = new Date(self.period().startDate);
                    printQuery.endDate = new Date(self.period().endDate);
                }
                // set mode print PDF
                printQuery.mode = 1;
                if (printQuery.selectedDateType == 2 || printQuery.selectedDateType == 1) {
                    if (!isNoError) {
                        alError({ messageId: "Msg_1500" });
                        return;
                    } else {
                        self.doPrint(printQuery);
                    }
                } else {
                    self.doPrint(printQuery);
                }

            }).fail(() => {
                block.clear();
            });
        }

        public doPrint(printQuery: any) {
            block.grayout();
            service.exportExcel(printQuery).done((res) => {
                block.clear();
            }).fail((res) =>{
                block.clear();
                alError({ messageId: res.messageId || res.message });
            });
            service.saveCharacteristic(companyId, userId, printQuery.toScreenInfo());
        }

        public checkClosureDate(): JQueryPromise<any> {
            let self = this,
                closureId = self.closureId(),
                isNotError = true,
                dfd = $.Deferred();
            //with the case of After_1_year
            if (self.selectedDateType() === 1) {
                let datePeriodValidate = moment(self.period().startDate, "YYYY/MM/DD").format("YYYYMM"); 
                self.printDate(parseInt(datePeriodValidate));
            }

            service.findClosureById(closureId).done((closureData) => {

                //①社員範囲選択の就業締め日 ≠ 全締め　&参照区分 = 過去 & 就業締め日の当月 <= 指定月→ 出力エラー　(#Msg_1500)
                if (closureData.closureSelected) {
                    self.closureData(closureData);
                    if (closureData.month <= self.printDate()) {
                        dfd.resolve(false);
                    } else {
                        dfd.resolve(true);
                    }
                }
                else {
                    //is mean 社員範囲選択の就業締め日 = 全締め
                    //②社員範囲選択の就業締め日 = 全締め　&　参照区分 = 過去 &全ての就業締め日の中の一番未来の締め月 <= 指定月→ 出力エラー　(#Msg_1500)
                    service.findAllClosure().done((closures) => {
                        const closure: any = _.minBy(closures, 'closureId');
                        self.closureData(closure);
                        if (closure.month <= self.printDate()) {
                          dfd.resolve(false);
                        } else {
                          dfd.resolve(true);
                        }
                    });
                }
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
        affiliationName?: string;
    }

    export class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }
    export class PrintQuery {
        programName: string;

        exportTime: string;
        // 対象期間
        selectedDateType: number;
        // 参照区分
        selectedReferenceType: number;
        // 指定月
        printDate: number;
        // 改ページ区分
        pageBreakSelected: number;

        selectedEmployees: Array<UnitModel>;

        closureData: any;

        // 抽出条件
        extCondition: boolean;

        //抽出条件_日数
        extConditionSettingDay:number;

        //抽出条件_比較条件
        extConditionSettingCoparison: number;

        //ダブルトラック時の期間拡張 - Extended period for double track - A7_2
        doubleTrack: boolean;

        //年休取得日の印字方法 - How to print the annual leave acquisition date - A6_2
        printAnnualLeaveDate: any;

        mode: number;


        constructor(screen: ScreenModel) {
            let self = this,
                program = nts.uk.ui._viewModel.kiban.programName().split(" ");
            self.programName = (program[1] != null ? program[1] : "年休管理表");
            self.exportTime = moment().format();
            // 対象期間
            self.selectedDateType = screen.selectedDateType();
            // 参照区分
            self.selectedReferenceType = screen.selectedReferenceType();
            // 改ページ区分
            self.pageBreakSelected = screen.pageBreakSelected();
            self.selectedEmployees = _.filter(screen.employeeList(), (e) => { return _.indexOf(screen.selectedEmployeeCode(), e.code) != -1; });
            self.closureData = screen.closureData();
            // 抽出条件
            self.extCondition = screen.isExtraction();
            // 抽出条件_設定
            self.extConditionSettingDay = screen.inputExtraction();
            // 抽出条件_比較条件
            self.extConditionSettingCoparison = screen.optionExtractionValue();

            // ダブルトラック時の期間拡張
            self.doubleTrack = screen.doubleTrack();
            // 年休取得日の印字方法
            self.printAnnualLeaveDate = screen.printAnnualLeaveDateSelect();
            // A3_9
            self.printDate = screen.printDate();
        }

        public toScreenInfo() {
            let self = this;
            return {
                selectedDateType: self.selectedDateType,
                selectedReferenceType: self.selectedReferenceType,
                printDate: self.printDate,
                pageBreakSelected: self.pageBreakSelected,
                extCondition: self.extCondition,
                extConditionSettingDay: self.extConditionSettingDay,
                extConditionSettingCoparison: self.extConditionSettingCoparison,
                doubleTrack: self.doubleTrack,
                printAnnualLeaveDate: self.printAnnualLeaveDate
            }
        }
    }
    //年休管理表の出力条件
    export interface IScreenInfo {
        //対象期間 - Target period - A3_2
        selectedDateType: number,
        //参照区分 - Reference classification - A3_5
        selectedReferenceType: number,
        //指定月 - Specified month
        printDate: number,
        //抽出条件 - Extraction condition - A5_7
        extCondition: boolean,
        //抽出条件_設定．日数 - Extraction condition_setting. Days - A5_2
        extConditionSettingDay: number,
        //抽出条件_設定．比較条件 - Extraction condition_setting. Comparison conditions - A5_3
        extConditionSettingCoparison: any,
        //ダブルトラック時の期間拡張 - Extended period for double track - A7_2
        doubleTrack: boolean,
        //年休取得日の印字方法 - How to print the annual leave acquisition date - A6_2
        printAnnualLeaveDate: any,
        //参照の選択 - 改ページ区分 - Page break classification - A4_2
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

    export class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    // 抽出条件_設定
    export class ExtractionConditionSetting {
        // 日数
        days: number;
        // 比較条件
        comparisonConditions: number;

        constructor(days: number, comparisonConditions: number) {
            this.days = days;
            this.comparisonConditions = comparisonConditions;
        }
    }

}