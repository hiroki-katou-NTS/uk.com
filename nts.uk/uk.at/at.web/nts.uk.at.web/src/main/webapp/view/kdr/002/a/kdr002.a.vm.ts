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
        // A3_2 ???????????????
        dateTypes: KnockoutObservableArray<ItemModel> = ko.observableArray([
            //A3_3
            { code: 0, name: getText('KDR002_3'),  }, // ?????????????????????????????????????????????????????????????????????
            //A3_10
            { code: 1, name: getText('KDR002_41'),  }, // ??????????????????????????????????????????????????????
            //A3_4
            { code: 2, name: getText('KDR002_4'), } // ???????????????????????????????????????????????????
        ]);
        selectedDateType: KnockoutObservable<number> = ko.observable(0);

        // reference type
        referenceTypes: KnockoutObservableArray<ItemModel> = ko.observableArray([
            //A3_6
            { code: 0, name: getText('KDR002_6') }, // ????????????
            //A3_7
            { code: 1, name: getText('KDR002_7')  } // ?????????????????????????????????
        ]);
        selectedReferenceType: KnockoutObservable<number> = ko.observable(0);

        //print Date
        printDate: KnockoutObservable<number> = ko.observable(Number(moment().format("YYYYMM")));

        // Page Break Type
        // A4_3
        pageBreakTypes: KnockoutObservableArray<any> = ko.observableArray([
            { code: 0 , name: getText('Enum_BreakPageType_NONE') }, //??????
            { code: 1 , name: getText('Enum_BreakPageType_WORKPLACE')  } //??????
        ]);
        pageBreakSelected: KnockoutObservable<number> = ko.observable(0);
        // A6_3
        printAnnualLeaveDate: KnockoutObservableArray<any> = ko.observableArray([
            { code: 0 , name: getText('KDR002_52') }, //?????????
            { code: 1 , name: getText('KDR002_53')  } //??????
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
            systemType: 2, // ??????????????????
            showEmployeeSelection: false, // ???????????????
            showQuickSearchTab: true, // ??????????????????
            showAdvancedSearchTab: true, // ????????????
            showBaseDate: false, // ???????????????
            showClosure: true, // ?????????????????????
            showAllClosure: true, // ???????????????
            showPeriod: true, // ??????????????????
            periodFormatYM: true, // ??????????????????

            /** Required parameter */
            baseDate: moment().format("YYYY-MM-DD"), // ?????????
            periodStartDate: periodStart, // ?????????????????????
            periodEndDate: periodEnd, // ?????????????????????
            inService: true, // ????????????
            leaveOfAbsence: true, // ????????????
            closed: true, // ????????????
            retirement: true, // ????????????

            /** Quick search tab options */
            showAllReferableEmployee: true, // ??????????????????????????????
            showOnlyMe: true, // ????????????
            showSameWorkplace: true, // ?????????????????????
            showSameWorkplaceAndChild: true, // ????????????????????????????????????

            /** Advanced search properties */
            showEmployment: true, // ????????????
            showWorkplace: true, // ????????????
            showClassification: true, // ????????????
            showJobTitle: true, // ????????????
            showWorktype: true, // ????????????
            isMutipleCheck: true, // ???????????????

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
          // Fix bug khi th??ng b???t ?????u l?? th??ng 2 nhu???n
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

        //????????????
        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();
            //????????????????????????????????????????????????
            service.findClosureByEmpID().done(function(closure: any) {
                if (closure) {
                    // ???????????????????????????????????????????????????????????????????????????
                    self.setDataWhenStart(closure);

                    //  ?????????????????????.??????????????????????????????????????????
                    let currentMonth: string  = moment(closure.closureMonth.toString(), 'YYYYMMDD').format('YYYYMMDD');
                    let closureDay: string = closure.closureHistories[0].closureDate.closureDay;
                    let startDateRange: any;
                    let endDateRange: any;
                    // ?????????:?????? - if closure date is last day of month
                    if(closure.closureHistories[0].closureDate.lastDayOfMonth) {
                        startDateRange = moment(currentMonth).format("YYYYMMDD");
                        endDateRange = moment(startDateRange).add(1, 'year').subtract(1, 'day').format("YYYYMMDD");
                    } else {
                        // ?????????: n ???
                        startDateRange = moment(currentMonth).add(closureDay, 'day').subtract(1, 'month').format("YYYYMMDD");
                        endDateRange = moment(startDateRange).add(1, 'year').subtract(1, 'day').format("YYYYMMDD");
                    }
                    let closureMonthAfter: number = parseInt(moment(closure.closureMonth, "YYYYMM").subtract(1, 'month').format("YYYYMM"));
                    self.printDate(closureMonthAfter);
                    //?????????????????????.??????????????????????????????
                    self.period({ startDate: startDateRange, endDate: endDateRange });


                    self.closureDate(closure.closureMonth);
                } else {
                    //????????? = null
                    //????????????????????????????????????.????????????????????????????????????????????????????????????
                    // self.printDate(Number(moment().subtract(1, 'months').format("YYYYMM")))
                    let startDateRange = moment().subtract(1, 'day').format("YYYYMMDD");
                    let endDateRange = moment(startDateRange).add(1, 'year').subtract(1, 'day').format("YYYYMMDD");

                    self.printDate(closure.closureMonth);
                    //?????????????????????.??????????????????????????????
                    self.period({ startDate: startDateRange, endDate: endDateRange });

                    //????????????????????????(#Msg_1134)?????????
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
                //?????????????????????????????????????????????????????????????????????????????????
                if (screenInfo) {
                    //???????????????
                    self.setScreenInfo(closure, screenInfo);
                }
                else {
                    //???????????????
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
                //????????? ??? null
                //?????????????????????????????????????????????
                self.printDate(screenInfo.printDate);
            } else {
                //????????? = null
                //????????????????????????????????????.????????????????????????????????????????????????????????????
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

            //???????????????????????????
            if (!self.selectedEmployeeCode().length) {
                alError({ messageId: 'Msg_884' });
                return;
            }
            //????????????????????????????????????????????????
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

            //???????????????????????????
            if (!self.selectedEmployeeCode().length) {
                alError({ messageId: 'Msg_884' });
                return;
            }
            //????????????????????????????????????????????????
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

                //??????????????????????????????????????? ??? ????????????&???????????? = ?????? & ???????????????????????? <= ???????????? ??????????????????(#Msg_1500)
                if (closureData.closureSelected) {
                    self.closureData(closureData);
                    if (closureData.month <= self.printDate()) {
                        dfd.resolve(false);
                    } else {
                        dfd.resolve(true);
                    }
                }
                else {
                    //is mean ???????????????????????????????????? = ?????????
                    //??????????????????????????????????????? = ????????????&??????????????? = ?????? &????????????????????????????????????????????????????????? <= ???????????? ??????????????????(#Msg_1500)
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
        // ????????????
        selectedDateType: number;
        // ????????????
        selectedReferenceType: number;
        // ?????????
        printDate: number;
        // ??????????????????
        pageBreakSelected: number;

        selectedEmployees: Array<UnitModel>;

        closureData: any;

        // ????????????
        extCondition: boolean;

        //????????????_??????
        extConditionSettingDay:number;

        //????????????_????????????
        extConditionSettingCoparison: number;

        //??????????????????????????????????????? - Extended period for double track - A7_2
        doubleTrack: boolean;

        //?????????????????????????????? - How to print the annual leave acquisition date - A6_2
        printAnnualLeaveDate: any;

        mode: number;


        constructor(screen: ScreenModel) {
            let self = this,
                program = nts.uk.ui._viewModel.kiban.programName().split(" ");
            self.programName = (program[1] != null ? program[1] : "???????????????");
            self.exportTime = moment().format();
            // ????????????
            self.selectedDateType = screen.selectedDateType();
            // ????????????
            self.selectedReferenceType = screen.selectedReferenceType();
            // ??????????????????
            self.pageBreakSelected = screen.pageBreakSelected();
            self.selectedEmployees = _.filter(screen.employeeList(), (e) => { return _.indexOf(screen.selectedEmployeeCode(), e.code) != -1; });
            self.closureData = screen.closureData();
            // ????????????
            self.extCondition = screen.isExtraction();
            // ????????????_??????
            self.extConditionSettingDay = screen.inputExtraction();
            // ????????????_????????????
            self.extConditionSettingCoparison = screen.optionExtractionValue();

            // ???????????????????????????????????????
            self.doubleTrack = screen.doubleTrack();
            // ??????????????????????????????
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
    //??????????????????????????????
    export interface IScreenInfo {
        //???????????? - Target period - A3_2
        selectedDateType: number,
        //???????????? - Reference classification - A3_5
        selectedReferenceType: number,
        //????????? - Specified month
        printDate: number,
        //???????????? - Extraction condition - A5_7
        extCondition: boolean,
        //????????????_??????????????? - Extraction condition_setting. Days - A5_2
        extConditionSettingDay: number,
        //????????????_????????????????????? - Extraction condition_setting. Comparison conditions - A5_3
        extConditionSettingCoparison: any,
        //??????????????????????????????????????? - Extended period for double track - A7_2
        doubleTrack: boolean,
        //?????????????????????????????? - How to print the annual leave acquisition date - A6_2
        printAnnualLeaveDate: any,
        //??????????????? - ?????????????????? - Page break classification - A4_2
        pageBreakSelected: number
    }

    export class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }

    // ????????????????????????????????????
    export class ScheduleBatchCorrectSetting {
        // ????????????
        worktypeCode: string;

        // ??????ID
        employeeId: string;

        // ?????????
        endDate: string;

        // ?????????
        startDate: string;

        // ???????????????
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

    // ????????????_??????
    export class ExtractionConditionSetting {
        // ??????
        days: number;
        // ????????????
        comparisonConditions: number;

        constructor(days: number, comparisonConditions: number) {
            this.days = days;
            this.comparisonConditions = comparisonConditions;
        }
    }

}