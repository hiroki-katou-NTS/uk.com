module nts.uk.at.view.kfp002.a.viewmodel {

    const DATA_KEY = "KFP002ScreenData";
    const DEFAULT_WIDTH = "100px";
    const FIXED_COLUMNS = [
        { key: 'id', headerText: 'ID', dataType: 'string', width: '0px', hidden: true},
        { key: 'code', headerText: nts.uk.resource.getText('KFP002_9'), dataType: 'string', width: '140px', ntsControl: "Label"},
        { key: 'name', headerText: nts.uk.resource.getText('KFP002_10'), dataType: 'string', width: '140px', ntsControl: "Label"}
    ];

    const Paths = {
        getFrames: "at/screen/kfp002/a/frames",
        getDisplayFormat: "at/screen/kfp002/a/display",
        getColumnWidth: "at/screen/kfp002/a/columns",
        getActualResults: "at/screen/kfp002/a/results",
        saveColWidth: "at/screen/kfp002/a/register-col-width",
        correct: "at/screen/kfp002/a/correct",
    };

    @bean()
    class Kfp002bViewModel extends ko.ViewModel {
        columns: KnockoutObservableArray<any>;
        sheets: KnockoutObservableArray<any>;
        dataSource: KnockoutObservableArray<any>;

//         columnSettings: KnockoutObservableArray<any> = ko.observableArray([]);
//         sheetsGrid: KnockoutObservableArray<any> = ko.observableArray([]);
//         fixColGrid: KnockoutObservableArray<any>;
        cellStates: KnockoutObservableArray<any> = ko.observableArray([]);
//         rowStates: KnockoutObservableArray<any> = ko.observableArray([]);
//         dpData: Array<any> = [];
        headerColors: KnockoutObservableArray<any>;
//         textColors: KnockoutObservableArray<any> = ko.observableArray([]);
//         legendOptions: any;
//
//         //grid user setting
        cursorMoveDirections: KnockoutObservableArray<any> = ko.observableArray([
            { code: 0, name: "縦" },
            { code: 1, name: "横" }
        ]);
//         selectedDirection: KnockoutObservable<any> = ko.observable(0);
//
//         //a4_7
//         closureInfoItems: KnockoutObservableArray<any> = ko.observableArray([]);
//         selectedClosure: KnockoutObservable<any> = ko.observable(0);
//
        cursorDirection: KnockoutObservable<number> = ko.observable(0); //カーソル移動方向
        displayZero: KnockoutObservable<boolean> = ko.observable(false); //ゼロ表示
        displayItemNumber: KnockoutObservable<boolean> = ko.observable(false); //項目番号表示

//
//         showProfileIcon: KnockoutObservable<boolean> = ko.observable(false);
        //ccg001 component: search employee
        ccg001: any;
        lstEmployee: KnockoutObservableArray<any>;
        aggregateFrames: KnockoutObservableArray<any>;
        selectedFrameCode: KnockoutObservable<string>;
        legendOptions: any;
        formatCode: KnockoutObservable<string>;
        displayFormat: KnockoutObservable<any>;
        savedColumnWidths: KnockoutObservableArray<any>;


//         displayFormat: KnockoutObservable<number> = ko.observable(null);
//         lstDate: KnockoutObservableArray<any> = ko.observableArray([]);
//         optionalHeader: Array<any> = [];
//         employeeModeHeader: Array<any> = [];
//         dateModeHeader: Array<any> = [];
//         errorModeHeader: Array<any> = [];
//         formatCodes: KnockoutObservableArray<any> = ko.observableArray([]);
//         lstAttendanceItem: KnockoutObservableArray<any> = ko.observableArray([]);
//         //A13_1 コメント
//         comment: KnockoutObservable<string> = ko.observable('');
//         closureName: KnockoutObservable<string> = ko.observable('');
//         showButton: KnockoutObservable<AuthorityDetailModel> = ko.observable(null);
//         employmentCode: KnockoutObservable<any> = ko.observable("");
//         editValue: KnockoutObservableArray<InfoCellEdit> = ko.observableArray([]);
//         itemValueAll: KnockoutObservableArray<any> = ko.observableArray([]);
//         itemValueAllTemp: KnockoutObservableArray<any> = ko.observableArray([]);
//         lockMessage: KnockoutObservable<any> = ko.observable("");
//         dataHoliday: KnockoutObservable<DataHoliday> = ko.observable(new DataHoliday("0", "0", "0", "0", "0", "0"));
//         comboColumns: KnockoutObservableArray<any> = ko.observableArray([{ prop: 'code', length: 1 },
//             { prop: 'name', length: 2 }]);
//         comboColumnsCalc: KnockoutObservableArray<any> = ko.observableArray([{ prop: 'code', length: 1 },
//             { prop: 'name', length: 8 }]);
//
//         dataAll: KnockoutObservable<any> = ko.observable(null);
//         dataBackup: any;
//         hasLstHeader: boolean = true;
//         dPErrorDto: KnockoutObservable<any> = ko.observable();
//         listCareError: KnockoutObservableArray<any> = ko.observableArray([]);
//         listCareInputError: KnockoutObservableArray<any> = ko.observableArray([]);
//         employIdLogin: any;
//         dialogShow: any;
//         //contain data share
//         screenModeApproval: KnockoutObservable<any> = ko.observable(null);
//         changePeriod: KnockoutObservable<any> = ko.observable(true);
//         errorReference: KnockoutObservable<any> = ko.observable(true);
//         activationSourceRefer: KnockoutObservable<any> = ko.observable(null);
//         tighten: KnockoutObservable<any> = ko.observable(null);
//
//         selectedDate: KnockoutObservable<string> = ko.observable(null);
//         //起動モード　＝　修正モード
//         initMode: KnockoutObservable<number> = ko.observable(0);
//         selectedErrorCodes: KnockoutObservableArray<any> = ko.observableArray([]);
//
//         //Parameter setting
//         monthlyParam: KnockoutObservable<any> = ko.observable(null);
// //        reloadParam: KnockoutObservable<any> = ko.observable(null);
        //Date YYYYMM picker
        baseDate: KnockoutObservable<any>;
//         //Combobox display actual time
//         actualTimeOptionDisp: KnockoutObservableArray<any>;
//         actualTimeSelectedCode: KnockoutObservable<number> = ko.observable(0);
//         actualTimeDats: KnockoutObservableArray<any> = ko.observableArray([]);;
//         actualTimeSelectedDat: KnockoutObservable<any> = ko.observable(null);
//
//         closureId: KnockoutObservable<number> = ko.observable(0);
//
//         closureDateDto: KnockoutObservable<any> = ko.observable(null);
//
//         dailyPerfomanceData: KnockoutObservableArray<any> = ko.observableArray([]);
//
        isStartScreen: KnockoutObservable<any> = ko.observable(true);
//         noCheckColumn: KnockoutObservable<any> = ko.observable(false);
//
//         isCache : boolean = false;
//         isStarted: boolean = false;
//
//         clickCounter: CLickCount = new CLickCount();
//         workTypeNotFound: any = [];
//         flagSelectEmployee: boolean = false;
// 		// 就業確定を利用する ← 就業確定の機能制限.就業確定を行う
// 		employmentConfirm: boolean = false;
        summaryColumns: KnockoutObservableArray<any> = ko.observableArray([{columnKey: 'code', allowSummaries: true, summaryCalculator: '合計'}]);
        
        constructor(value: boolean) {
            super();
            let self = this;
//             self.isCache = value;
            self.baseDate = ko.observable(new Date().toISOString());
            self.lstEmployee = ko.observableArray([]);
            self.aggregateFrames = ko.observableArray([]);
            self.selectedFrameCode = ko.observable(null);
            self.legendOptions = {
                items: [
                    { colorCode: '#94B7FE', labelText: self.$i18n("KFP002_6") },
                    { colorCode: '#CEE6FF', labelText: self.$i18n("KFP002_7") },
                    { colorCode: '#F69164', labelText: self.$i18n("KFP002_8") }
                ]
            };

            $("#setting-content").ntsPopup({
                trigger: "#A2_5",
                position: {
                    my: "left top",
                    at: "left bottom",
                    of: "#A2_5"
                },
                showOnStart: false,
                dismissible: true
            });

            self.formatCode = ko.observable(null);
            self.displayFormat = ko.observable(null);
            self.savedColumnWidths = ko.observableArray([]);
//             self.initLegendButton();
//             //self.initActualTime();
            self.dataSource = ko.observableArray([]);
            self.columns = ko.observableArray(_.cloneDeep(FIXED_COLUMNS));
            self.sheets = ko.observableArray([]);
            self.headerColors = ko.observableArray([]);

            nts.uk.characteristics.restore(DATA_KEY).done((cacheData: ScreenStatus) => {
                self.selectedFrameCode(cacheData ? cacheData.anyPeriodFrameCode : null);
                self.cursorDirection(cacheData ? cacheData.cursorDirection : 0);
                self.displayZero(cacheData ? cacheData.displayZero : false);
                self.displayItemNumber(cacheData ? cacheData.displayItemNumber : false);
            });
//             self.fixColGrid = ko.observableArray([]);
//
//             if (!_.isEmpty(queryString.items)) {
//                 self.initMode(Number(queryString.items["initmode"]));
//             }
//
//             self.monthlyParam({
//                 yearMonth: 0,
//                 actualTime: null,
//                 initMenuMode: self.initMode(),
//                 //抽出対象社員一覧
//                 lstEmployees: [],
//                 formatCodes: self.formatCodes(),
//                 dispItems: [],
//                 correctionOfMonthly: null,
//                 errorCodes: [],
//                 lstLockStatus: [],
//                 closureId: null
//             });
//
// //            self.reloadParam({
// //                processDate: self.yearMonth(),
// //                initScreenMode: self.initMode(),
// //                //抽出対象社員一覧
// //                lstEmployees: [],
// //                closureId: self.closureId(),
// //                lstAtdItemUnique: []
// //            });
//
//             self.actualTimeSelectedCode.subscribe(value => {
//                 self.actualTimeSelectedDat(self.actualTimeDats()[value]);
//                 //                if(self.monthlyParam().actualTime ==null){
//                 //                    let temp = {startDate : moment(self.actualTimeSelectedDat().startDate).toISOString(), endDate : moment(self.actualTimeSelectedDat().endDate).toISOString()  };
//                 //                    self.monthlyParam().actualTime = temp;
//                 //                }else{
//                 //                    self.monthlyParam().actualTime.startDate = moment(self.actualTimeSelectedDat().startDate).toISOString();
//                 //                    self.monthlyParam().actualTime.(self.actualTimeSelectedDat().endDate).toISOString();
//                 //                }
//
//                 //                self.initScreen();
//                 //実績期間を変更する
//                 self.updateActualTime();
//             });
//

            self.selectedFrameCode.subscribe(val => {
                if (!self.isStartScreen()) {
                    nts.uk.characteristics.save(DATA_KEY, {
                        anyPeriodFrameCode: val,
                        cursorDirection: self.cursorDirection(),
                        displayZero: self.displayZero(),
                        displayItemNumber: self.displayItemNumber()
                    });
                    self.getData();
                }
            });

            self.cursorDirection.subscribe((value) => {
                if ($("#anpGrid").data('mGrid')) {
                    if (value == 0) {
                        $("#anpGrid").mGrid("directEnter", "below");
                    } else {
                        $("#anpGrid").mGrid("directEnter", "right");
                    }
                }
                nts.uk.characteristics.save(DATA_KEY, {
                    anyPeriodFrameCode: self.selectedFrameCode(),
                    cursorDirection: value,
                    displayZero: self.displayZero(),
                    displayItemNumber: self.displayItemNumber()
                });
            });
//
//             self.yearMonth.subscribe(value => {
//                 //期間を変更する
//                 if (nts.uk.ui._viewModel && nts.uk.ui.errors.getErrorByElement($("#yearMonthPicker")).length == 0 && value != undefined && !self.isStartScreen())
//                     self.updateDate(value);
//             });
//             $(document).mouseup(function(e) {
//                 let container = $(".ui-tooltip");
//                 if (!container.is(e.target) &&
//                     container.has(e.target).length === 0) {
//                     $("#tooltip").hide();
//                 }
//             });
//
//             self.showProfileIcon.subscribe((val) => {

//                 self.reloadGrid();
//             });
//
            self.displayItemNumber.subscribe((val) => {
                const displayFormat = self.displayFormat();
                if ($("#anpGrid").data('mGrid') && displayFormat) {
                    self.columns().forEach(c => {
                        if (!!Number(c.key.substring(1))) {
                            const item = _.find(displayFormat.items, (i: any) => i.attendanceItemId == Number(c.key.substring(1)));
                            let headerText = self.formatHeaderText(
                                val ? "[" + item.attendanceItemDisplayNumber + "] " + item.oldName : item.attendanceItemName,
                                _.find(displayFormat.monthlyItems, mi => mi.attendanceItemId == item.attendanceItemId),
                                item.nameLineFeedPosition
                            );
                            $("#anpGrid").mGrid("headerText", c.key, headerText, false);
                        }
                    });
                }
                nts.uk.characteristics.save(DATA_KEY, {
                    anyPeriodFrameCode: self.selectedFrameCode(),
                    cursorDirection: self.cursorDirection(),
                    displayZero: self.displayZero(),
                    displayItemNumber: val
                });
            });

            self.displayZero.subscribe(val => {
                if ($("#anpGrid").data('mGrid')) {
                    if (val) {
                        $("#anpGrid").mGrid("hideZero", false);
                    } else {
                        $("#anpGrid").mGrid("hideZero", true);
                    }
                }
                nts.uk.characteristics.save(DATA_KEY, {
                    anyPeriodFrameCode: self.selectedFrameCode(),
                    cursorDirection: self.cursorDirection(),
                    displayZero: val,
                    displayItemNumber: self.displayItemNumber()
                });
            });

            self.lstEmployee.subscribe(val => {
                self.getData();
            });
        }

        created() {
            const self = this;
            self.initCcg001();
            localStorage.removeItem(window.location.href + '/anpGrid');
            self.$blockui('show');
            self.$ajax(Paths.getFrames).done(frames => {
                $.when(self.aggregateFrames(frames)).then(() => {
                    self.isStartScreen(false);
                });
                self.getFormatSetting();
            }).fail(error => {
                self.$blockui('hide');
                self.$dialog.alert(error).then(() => {
                    if (error.messageId == "Msg_3277") {
                        nts.uk.request.jumpToTopPage();
                    }
                });
            });
        }

        mounted() {
            $('#A2_1').focus();
        }

        getFormatSetting() {
            const self = this;
            self.$ajax('at', Paths.getDisplayFormat, self.formatCode()).done(displayFormat => {
                if (displayFormat.formatSetting == null) {
                    self.openDialogB();
                } else {
                    self.displayFormat(displayFormat);
                    self.$ajax(Paths.getColumnWidth).done(colWidths => {
                        self.savedColumnWidths(colWidths);
                        self.getColumns();
                        self.$blockui('hide');
                        self.getData();
                    }).fail(error => {
                        self.$blockui('hide');
                        self.$dialog.alert(error);
                    });
                }
            }).fail(error => {
                self.$blockui('hide');
                self.$dialog.alert(error);
            });
        }

        getColumns() {
            const self = this;
            const displayFormat = self.displayFormat();
            if (displayFormat) {
                // reset
                self.sheets([]);
                self.columns(_.cloneDeep(FIXED_COLUMNS));
                self.summaryColumns([{columnKey: 'code', allowSummaries: true, summaryCalculator: '合計'}]);

                // mapping
                displayFormat.formatSetting.sheetSettings.forEach((sheet: any, index: number) => {
                    const items = _.sortBy(sheet.listDisplayTimeItem, ['displayOrder']);
                    const tmp: Array<any> = [];
                    items.forEach((item1: any) => {
                        displayFormat.items.forEach((item2: any) => {
                            if (item1.itemDaily == item2.attendanceItemId) {
                                let key = "C" + String(item2.attendanceItemId);
                                tmp.push(key);
                                let colWidth = _.find(self.savedColumnWidths(), s => s.itemId == item2.attendanceItemId);
                                let monthlyItem = _.find(displayFormat.monthlyItems, (mi: any) => mi.attendanceItemId == item2.attendanceItemId);
                                let column: any = {
                                    key: key,
                                    headerText: self.formatHeaderText(
                                        self.displayItemNumber() ? "[" + item2.attendanceItemDisplayNumber + "] " + item2.oldName : item2.attendanceItemName,
                                        monthlyItem,
                                        item2.nameLineFeedPosition
                                    ),
                                    dataType: 'string',
                                    width: colWidth ? colWidth.columnWidth + 'px' : (item1.columnWidthTable ? item1.columnWidthTable + 'px' : DEFAULT_WIDTH),
                                    grant: true,
                                    // columnCssClass: "text-right-align",
                                    // headerCssClass: "text-center-align"
                                };
                                if (monthlyItem) {
                                    if (monthlyItem.primitive && self.getPrimitive(monthlyItem.primitive)) {
                                        column.constraint = {
                                            primitiveValue: self.getPrimitive(monthlyItem.primitive),
                                            required: false
                                        };
                                    }
                                    self.summaryColumns.push({
                                        columnKey: column.key,
                                        allowSummaries: true,
                                        summaryCalculator: monthlyItem.monthlyAttendanceAtr == 1 ? 'Time' : 'Number'
                                    });
                                }
                                self.columns.push(column);
                                const monthlyControl = _.find(displayFormat.monthlyItemControls, (i: any) => i.itemMonthlyId == item2.attendanceItemId);
                                if (monthlyControl && monthlyControl.headerBgColorOfMonthlyPer) {
                                    self.headerColors.push({
                                        key: key,
                                        colors: [monthlyControl.headerBgColorOfMonthlyPer]
                                    });
                                }
                            }
                        });
                    });
                    self.sheets.push({
                        name: "sheet" + index,
                        text: sheet.sheetName,
                        columns: tmp
                    });
                });
            }
        }

        formatHeaderText(name: string, monthlyItem: any, nameLineFeedPosition: number): string  {
            if (monthlyItem) {
                if (monthlyItem.nameLineFeedPosition > 0 && monthlyItem.nameLineFeedPosition < name.length) {
                    return name.slice(0, monthlyItem.nameLineFeedPosition) + "\n" + name.slice(monthlyItem.nameLineFeedPosition);
                }
            }
            if (nameLineFeedPosition > 0 && nameLineFeedPosition < name.length) {
                return name.slice(0, nameLineFeedPosition) + "\n" + name.slice(nameLineFeedPosition);
            }
            return name;
        }

        getData(): JQueryPromise<any> {
            const self = this;
            const dfd = $.Deferred();
            self.$blockui('show');
            self.$ajax(Paths.getActualResults, {
                frameCode: self.selectedFrameCode(),
                employeeIds: self.lstEmployee().map(e => e.id),
                itemIds: self.columns().filter(c => !!Number(c.key.substring(1))).map(c => Number(c.key.substring(1)))
            }).done(data => {
                let tmpDataSource: Array<any> = [];
                data.actualResults.forEach((result: any) => {
                    let tmp: any = {id: result.employeeId};
                    let empInfo = _.find(self.lstEmployee(), e => e.id == result.employeeId);
                    tmp["code"] = empInfo ? empInfo.code : "";
                    tmp["name"] = empInfo ? empInfo.name : "";
                    result.itemValues.forEach((item: any) => {
                        let monthlyItem = _.find(self.displayFormat().monthlyItems, (mi: any) => mi.attendanceItemId == item.itemId);
                        let value = item.value;
                        if (monthlyItem) {
                            switch (monthlyItem.monthlyAttendanceAtr) {
                                case 1: // 時間
                                    value = nts.uk.time.format.byId("Time_Short_HM", value);
                                    break;
                                case 2: // 回数
                                    break;
                                case 3: // 日数
                                    break;
                                case 4: // 金額
                                    break;
                                default:
                                    break;
                            }
                        }
                        tmp["C" + String(item.itemId)] = value;
                    });
                    tmpDataSource.push(tmp);
                });
                self.dataSource(_.sortBy(tmpDataSource, ["code"]));
                data.editStates.forEach((state: any) => {
                    self.cellStates.push({
                        columnKey: "C" + String(state.itemId),
                        rowId: state.employeeId,
                        state: state.editState == 0 ? ["mgrid-manual-edit-target"] : ["mgrid-manual-edit-other"]
                    });
                });
                self.loadGrid();
                dfd.resolve();
            }).fail(error => {
                dfd.reject();
                self.$dialog.alert(error);
                self.dataSource([]);
                self.loadGrid();
            }).always(() => {
                self.$blockui('hide');
            });
            return dfd.promise();
        }

        openDialogB() {
            const vm = this;
            vm.$window.modal("/view/kfp/002/b/index.xhtml").then(formatCode => {
                if (formatCode) {
                    vm.formatCode(formatCode);
                    vm.getFormatSetting();
                }
            });
        }

        openCDL027() {
            let self = this,
                selectedFrame = _.find(self.aggregateFrames(), f => f.aggrFrameCode == self.selectedFrameCode()),
                param = {
                    pgid: __viewContext.program.programId,
                    functionId: 4,
                    listEmployeeId: _.map(self.lstEmployee(), emp => { return emp.id; }),
                    period : {
                        startDate: selectedFrame.startDate,
                        endDate: selectedFrame.endDate
                    },
                    displayFormat: 1
                };
            nts.uk.ui.windows.setShared("CDL027Params", param);
            self.$window.modal('com',"/view/cdl/027/a/index.xhtml");
        }

        getPrimitive(primitive: number): string {
            switch (primitive) {
                case 13: return "RecordRemarks";
                case 16: return "AttendanceTimeMonth";
                case 17: return "AttendanceTimeMonthWithMinus";
                case 18: return "AttendanceDaysMonth";
                case 19: return "AttendanceTimesMonth";
                case 20: return "OverTime";
                case 23: return "MonthlyDays";
                case 27: return "YearlyDays";
                case 28: return "LeaveGrantDayNumber";
                case 29: return "AnnualLeaveUsedDayNumber";
                case 30: return "AnnualLeaveRemainingDayNumber";
                case 31: return "AnnualLeaveRemainingTime";
                case 32: return "UsedTimes";
                case 33: return "RemainingTimes";
                case 34: return "UsedTimes";
                case 35: return "UsedMinutes";
                case 36: return "RemainingMinutes";
                case 37: return "LeaveGrantDayNumber";
                case 38: return "ReserveLeaveUsedDayNumber";
                case 39: return "ReserveLeaveRemainingDayNumber";
                case 40: return "TimeOfRemain";
                case 41: return "DayNumberOfRemain";
                case 42: return "LeaveGrantDayNumber";
                case 43: return "UsedTimes";
                case 44: return "TimeOfUse";
                case 45: return "DayNumberOfUse";
                case 46: return "ReserveLeaveUsedDayNumber";
                case 47: return "ReserveLeaveRemainingDayNumber";
                case 48: return "UsedMinutes";
                case 49: return "RemainingMinutes";
                case 50: return "ReserveLeaveRemainingDayNumber";
                case 51: return "ReserveLeaveUsedDayNumber";
                case 54: return "AnyItemAmount";
                case 55: return "AnyAmountMonth";
                case 56: return "AnyItemTime";
                case 57: return "AnyTimeMonth";
                case 58: return "AnyItemTimes";
                case 59: return "AnyTimesMonth";
                default: return null;
            }
        }
        // createSumColumn(data: any) {
        //     var self = this;
        //     _.each(data.lstControlDisplayItem.columnSettings, function(item) {
        //
        //         if (item.typeFormat != null && item.typeFormat != undefined) {
        //             if (item.typeFormat == 2) {
        //                 //so lan
        //                 item['summaryCalculator'] = "Number";
        //             }
        //             else if (item.typeFormat == 3) {
        //                 // so ngay
        //                 item['summaryCalculator'] = "Number";
        //             }
        //             else if (item.typeFormat == 1) {
        //                 //thoi gian
        //                 item['summaryCalculator'] = "Time";
        //             }
        //             else if (item.typeFormat == 4) {
        //                 //so tien
        //                 item['summaryCalculator'] = "Number";
        //             }else{
        //                 if (self.displayFormat() == 0) {
        //                     if (item.columnKey == "date") {
        //                         item['summaryCalculator'] = "合計";
        //                     } else {
        //                         item['summaryCalculator'] = "";
        //                     }
        //                 } else {
        //                     if (item.columnKey == "employeeCode") {
        //                         item['summaryCalculator'] = "合計";
        //                     } else {
        //                         item['summaryCalculator'] = "";
        //                     }
        //                 }
        //             }
        //         } else {
        //             item['summaryCalculator'] = "Number";
        //         }
        //
        //         delete item.typeFormat;
        //         self.columnSettings(data.lstControlDisplayItem.columnSettings);
        //     });
        // }
        
        // setScreenSize() {
        // 	if (window.innerHeight < 447) {
        // 		return;
        // 	}
        // }

        /**********************************
        * Initialize Screen 
        **********************************/
        initScreen(param?: any): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
    //         self.$blockui("show");
    //         localStorage.removeItem(window.location.href + '/anpGrid');
    //         nts.uk.ui.errors.clearAllGridErrors();
    //
    //         if(!_.isNil(param)){
    //             self.monthlyParam().closureId = param.closureId;
    //             self.monthlyParam().yearMonth = param.yearMonth;
    //         }
    //
    //         self.monthlyParam().lstLockStatus = [];
    //         self.noCheckColumn(false);
    //         let checkLoadKdw: boolean = localStorage.getItem('isKmw');
    //         nts.uk.characteristics.restore("cacheKMW003").done(function (cacheData) {
    //             if(cacheData != undefined && self.isCache == true && cacheData.yearMonth !=0 && checkLoadKdw){
    //                 self.monthlyParam(cacheData);
    //             }
    //             localStorage.removeItem('isKmw');
    //             __viewContext.transferred.value = false;
    //             nts.uk.characteristics.save("cacheKMW003",self.monthlyParam());
    //
    //             if (self.monthlyParam().actualTime) {
    //                 self.monthlyParam().actualTime.startDate = moment.utc(self.monthlyParam().actualTime.startDate, "YYYY/MM/DD").toISOString();
    //                 self.monthlyParam().actualTime.endDate = moment.utc(self.monthlyParam().actualTime.endDate, "YYYY/MM/DD").toISOString();
    //             }
    //             self.$ajax(Paths.startScreen, self.monthlyParam()).done((data) => {
    //                 self.employmentConfirm = data.useSetingOutput.employmentConfirm;
    //                 if(self.employmentConfirm){
    //                     let check = _.find(data.authorityDto, function(o) {
    //                         return o.functionNo === 26;
    //                     })
    //                     if (check == null){
    //                         self.employmentConfirm = false;
    //                     }
    //                 }
    //                 if (data.selectedClosure) {
    //                     let closureInfoArray = []
    //                     closureInfoArray = _.map(data.lstclosureInfoOuput, function(item: any) {
    //                         return { code: item.closureName, name: item.closureId };
    //                     });
    //                     self.closureInfoItems(closureInfoArray);
    //                 }
    //                 self.employIdLogin = __viewContext.user.employeeId;
    //                 self.dataAll(data);
    //                 self.monthlyParam(data.param);
    //                 self.dataBackup = _.cloneDeep(data);
    //
    //                 self.itemValueAll(data.itemValues);
    //                 self.receiveData(data);
    //                 self.createSumColumn(data);
    //                 self.columnSettings(data.lstControlDisplayItem.columnSettings);
    //                 /*********************************
    //                  * Screen data
    //                  *********************************/
    //                 // attendance item
    //                 self.lstAttendanceItem(data.param.lstAtdItemUnique);
    //                 // closure ID
    //                 self.closureId(data.closureId);
    // //                self.reloadParam().closureId = data.closureId;
    // //                self.reloadParam().lstAtdItemUnique = data.param.lstAtdItemUnique;
    //                 //Closure name
    //                 self.closureName(data.closureName);
    //                 // closureDateDto
    //                 self.closureDateDto(data.closureDate);
    //                 //actual times
    //                 self.actualTimeDats(data.lstActualTimes);
    //                 self.actualTimeSelectedDat(data.selectedActualTime);
    //                 self.initActualTime();
    //                 //comment
    //                 self.comment(data.comment ? data.comment : null);
    //                 /*********************************
    //                  * Grid data
    //                  *********************************/
    //                 // Fixed Header
    //                 self.setFixedHeader(data.lstFixedHeader);
    //                 self.extractionData();
    //                 self.loadGrid();
    //                 _.forEach(data.mpsateCellHideControl, (cellHide =>{
    //                     $('#anpGrid').mGrid("setState", cellHide.rowId, cellHide.columnKey, ["mgrid-hide"])
    //                 }))
    //
    //                 self.employmentCode(data.employmentCode);
    //                 self.dailyPerfomanceData(self.dpData);
    //                 self.lstEmployee(_.orderBy(data.lstEmployee, ['code'], ['asc']));
    //
    //                 //画面項目の非活制御をする
    //                 self.showButton(new AuthorityDetailModel(data.authorityDto, data.actualTimeState, self.initMode(), data.formatPerformance.settingUnitType));
    //                 if(self.initMode() == 2 && self.showButton().available_A4_7() == false) {
    //                     self.showButton().available_A4_7(true);
    //                     self.showButton().available_A1_11(false);
    //                     //A4_2
    //                     self.showButton().available_A4_2(false);
    //                 }
    //                 self.showButton().enable_multiActualTime(data.lstActualTimes.length > 1);
    // //                if (data.showRegisterButton == false) {
    // //                    self.showButton().enable_A1_1(data.showRegisterButton);
    // //                    self.showButton().enable_A1_2(data.showRegisterButton);
    // //                    self.showButton.valueHasMutated();
    // //                }
    //                 self.$blockui("hide");
    //                 dfd.resolve(data.processDate, data.selectedClosure);
    //             }).fail(function(error) {
    //                 if(error.messageId=="Msg_1430"){
    //                     self.$dialog.error(error).then(function() {
    //                         nts.uk.request.jumpToTopPage();
    //                     });
    //                  } else if(error.messageId=="Msg_916"){
    //                     self.$dialog.error(error).then(function() {
    //                         self.$blockui("hide");
    //                     });
    //                 }else if(error.messageId=="Msg_1452"){
    //                     self.$dialog.error(error).then(function() {
    //                         self.$blockui("hide");
    //                     });
    //                 }else {
    //                     if (error.messageId == "KMW003_SELECT_FORMATCODE") {
    //                         //Open KDM003C to select format code
    //                         self.displayItem().done((x) => {
    //                             dfd.resolve();
    //                         }).fail(function(error) {
    //                             self.$dialog.error(error).then(function() {
    //                                 nts.uk.request.jumpToTopPage();
    //                             });
    //                             dfd.reject();
    //                         });
    //                     } else if (!_.isEmpty(error.errors)) {
    //                         nts.uk.ui.dialog.bundledErrors({ errors: error.errors }).then(function() {
    //                             if(error.errors[0].messageId !="Msg_1403"){
    //                                 nts.uk.request.jumpToTopPage();
    //                             }
    //                             dfd.reject();
    //                         });
    //                     } else {
    //                         self.$dialog.error(error).then(function() {
    //                             nts.uk.request.jumpToTopPage();
    //                         });
    //                         dfd.reject();
    //                     }
    //                 }
    //             });
    //         });
            return dfd.promise();
        }

        initScreenFormat(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
//             self.$blockui("show");
//             localStorage.removeItem(window.location.href + '/anpGrid');
//             nts.uk.ui.errors.clearAllGridErrors();
// 			self.monthlyParam().initMenuMode = self.initMode();
// 			self.monthlyParam().formatCodes = self.formatCodes();
//             self.monthlyParam().lstLockStatus = [];
//             if (self.monthlyParam().actualTime) {
//                 self.monthlyParam().actualTime.startDate = moment.utc(self.monthlyParam().actualTime.startDate, "YYYY/MM/DD").toISOString();
//                 self.monthlyParam().actualTime.endDate = moment.utc(self.monthlyParam().actualTime.endDate, "YYYY/MM/DD").toISOString();
//             }
//             self.$ajax(Paths.startScreen, self.monthlyParam()).done((data) => {
//                 if (data.selectedClosure) {
//                     let closureInfoArray = []
//                     closureInfoArray = _.map(data.lstclosureInfoOuput, function(item: any) {
//                         return { code: item.closureName, name: item.closureId };
//                     });
//                     self.closureInfoItems(closureInfoArray);
//                     self.selectedClosure(data.selectedClosure);
//                 }
//                 self.employIdLogin = __viewContext.user.employeeId;
//                 self.dataAll(data);
//                 self.monthlyParam(data.param);
// 				nts.uk.characteristics.save("cacheKMW003",self.monthlyParam());
//                 self.dataBackup = _.cloneDeep(data);
//                 self.itemValueAll(data.itemValues);
//                 self.receiveData(data);
//                 self.createSumColumn(data);
//                 self.columnSettings(data.lstControlDisplayItem.columnSettings);
//                 /*********************************
//                  * Screen data
//                  *********************************/
//                 // attendance item
//                 self.lstAttendanceItem(data.param.lstAtdItemUnique);
//                 // closure ID
//                 self.closureId(data.closureId);
// //                self.reloadParam().closureId = data.closureId;
// //                self.reloadParam().lstAtdItemUnique = data.param.lstAtdItemUnique;
//                 //Closure name
//                 self.closureName(data.closureName);
//                 // closureDateDto
//                 self.closureDateDto(data.closureDate);
//                 //actual times
//                 self.actualTimeDats(data.lstActualTimes);
//                 self.actualTimeSelectedDat(data.selectedActualTime);
//                 self.initActualTime();
//                 //comment
//                 self.comment(data.comment != null ? '■ ' + data.comment : null);
//                 /*********************************
//                  * Grid data
//                  *********************************/
//                 // Fixed Header
//                 self.setFixedHeader(data.lstFixedHeader);
//                 self.extractionData();
// 				 /*if ($("#anpGrid").data('mGrid')) {
// 	                $("#anpGrid").mGrid("destroy");
// 	                $("#anpGrid").off();
// 	            }*/
// 				if ($("#anpGrid").hasClass("mgrid")) {
// 	                $("#anpGrid").mGrid("destroy");
// 	                $("#anpGrid").removeClass("mgrid");
// 	                $("#anpGrid").off();
// 	            }
//                 self.loadGrid();
//                 _.forEach(data.mpsateCellHideControl, (cellHide =>{
//                     $('#anpGrid').mGrid("setState", cellHide.rowId, cellHide.columnKey, ["mgrid-hide"])
//                 }))
//                 self.employmentCode(data.employmentCode);
//                 self.dailyPerfomanceData(self.dpData);
//                 self.lstEmployee(_.orderBy(data.lstEmployee, ['code'], ['asc']));
//
//                 //画面項目の非活制御をする
//                 self.showButton(new AuthorityDetailModel(data.authorityDto, data.actualTimeState, self.initMode(), data.formatPerformance.settingUnitType));
//                 self.showButton().enable_multiActualTime(data.lstActualTimes.length > 1);
// //                if (data.showRegisterButton == false) {
// //                    self.showButton().enable_A1_1(data.showRegisterButton);
// //                    self.showButton().enable_A1_2(data.showRegisterButton);
// //                    self.showButton.valueHasMutated();
// //                }
//                 self.$blockui("hide");
//                 dfd.resolve(data.processDate);
//             }).fail(function(error) {
//                 self.$dialog.error({ messageId: error.messageId }).then(function() {
//                     nts.uk.request.jumpToTopPage();
//                 });
//                 dfd.reject();
//             });
            return dfd.promise();
        }

        // loadRowScreen() {
        //     let self = this, param = _.cloneDeep(self.monthlyParam()),
        //         dataChange: any =  _.uniqWith($("#anpGrid").mGrid("updatedCells", true),  _.isEqual),
        //         empIds = _.map(_.uniqBy(dataChange, (e: any) => { return e.rowId; }), (value: any) => {
        //             return value.rowId;
        //         }),
        //         employees = _.filter(self.lstEmployee(), (e: any) => {
        //             return _.includes(empIds, e.id);
        //         });
        //     param.lstEmployees = employees;
        //     param.lstLockStatus = [];
        //     param.actualTime.startDate = moment.utc(param.actualTime.startDate, "YYYY/MM/DD").toISOString();
        //     param.actualTime.endDate = moment.utc(param.actualTime.endDate, "YYYY/MM/DD").toISOString();
        //     let dfd = $.Deferred();
        //     self.$ajax(Paths.updateScreen, param).done((data) => {
        //         let lstRowId = [] ;
        //         let dpDataNew = _.map(self.dpData, (value: any) => {
        //             let val = _.find(data.lstData, (item: any) => {
        //                 return item.id == value.id;
        //             });
        //             return val != undefined ? val : value;
        //         });
        //
        //         self.dpData = dpDataNew;
        //         let lstCellStateMerge = [] ;
        //         _.forEach(dpDataNew, (item: any) => {
        //                lstRowId.push(item.rowId);
        //             });
        //         for(let i = 0;i<data.lstCellState.length;i++){
        //             if(data.lstCellState[i].columnKey != "approval"){
        //                 lstCellStateMerge.push(data.lstCellState[i]);
        //                 continue;
        //             }
        //             if(data.lstCellState[i].columnKey == "approval" && _.indexOf(lstRowId, data.lstCellState[i].rowId) != -1 ){
        //                 for(let j =i+1;j<data.lstCellState.length;j++){
        //                     if(data.lstCellState[j].columnKey == "approval" && data.lstCellState[i].rowId == data.lstCellState[j].rowId){
        //                         data.lstCellState[i].state.push(data.lstCellState[j].state[0]);
        //                     }
        //                 }
        //                 _.remove(lstRowId, function(n) {
        //                     return n.rowId == data.lstCellState[i].rowId;
        //                 });
        //                 lstCellStateMerge.push(data.lstCellState[i]);
        //             }
        //         }
        //         let cellStatesNew = _.map(self.cellStates(), (value: any) => {
        //             let val = _.find(lstCellStateMerge, (item: any) => {
        //                 return item.rowId == value.rowId && item.columnKey == value.columnKey;
        //             });
        //             return val != undefined ? val : value;
        //         });
        //         _.each(data.lstCellState, (cs: any) => {
        //             let val = _.find(cellStatesNew, (item: any) => {
        //                 return item.rowId == cs.rowId && item.columnKey == cs.columnKey;
        //             });
        //             if (val == undefined) {
        //                 cellStatesNew.push(cs);
        //             }
        //         });
        //         self.cellStates(cellStatesNew);
        //         self.dailyPerfomanceData(dpDataNew);
        //         let rowIdUpdate =  _.uniq(_.map(_.uniqWith($("#anpGrid").mGrid("updatedCells", true),  _.isEqual), (itemTemp) => {return itemTemp.rowId}));
        //         $("#anpGrid").mGrid("destroy");
        //         $("#anpGrid").off();
        //         self.loadGrid();
        //         _.forEach(data.mpsateCellHideControl, (cellHide =>{
        //             $('#anpGrid').mGrid("setState", cellHide.rowId, cellHide.columnKey, ["mgrid-hide"])
        //         }))
        //
        //         _.forEach(
        //             _.filter(self.cellStates(), (itemTemp) => {
        //                 return itemTemp.columnKey == "approval" && rowIdUpdate.indexOf(itemTemp.rowId) == -1 && itemTemp.state.indexOf("mgrid-error") != -1
        //             }),
        //             (state) => {
        //                 $('#anpGrid').mGrid("setState", state.rowId, state.columnKey, ["mgrid-hide"]);
        //             });
        //         dfd.resolve();
        //     });
        //     return dfd.promise();
        // }



        /**********************************
         * Button Event 
         **********************************/
        /**
         * A1_1 確定ボタン
         */
        insertUpdate() {
            let self = this,
                errorGrid: any = $("#anpGrid").mGrid("errors"),
                dataChange: any = _.uniqWith($("#anpGrid").mGrid("updatedCells", true),  _.isEqual);

            if ((errorGrid == undefined || errorGrid.length == 0) && _.size(dataChange) > 0) {
                let dataSource = $("#anpGrid").mGrid("dataSource"),
                    dataChangeProcess: any = [],
                    dataUpdate: any = {
                        frameCode: self.selectedFrameCode(),
                        items: {}
                    };

                let groupedDataChange = self.groupBy(dataChange, 'rowId');
                for (var empId in groupedDataChange){
                    const cells = groupedDataChange[empId];
                    dataUpdate.items[empId] = cells.map((c: any) => {
                        const monthlyItem = _.find(self.displayFormat().monthlyItems, (mi: any) => mi.attendanceItemId == Number(c.columnKey.substring(1)));
                        let valueType = 1; // time
                        if (monthlyItem.monthlyAttendanceAtr == 2) valueType = 8;
                        if (monthlyItem.monthlyAttendanceAtr == 3) valueType = 12;
                        if (monthlyItem.monthlyAttendanceAtr == 4) valueType = 13;
                        return {
                            itemId: Number(c.columnKey.substring(1)),
                            valueType: valueType,
                            value: valueType == 1 ? String(self.getHoursTime(c.value)) : c.value
                        };
                    });
                }

                self.$blockui("show");
                self.$ajax(Paths.correct, dataUpdate).done((data) => {
                    self.$dialog.info({ messageId: 'Msg_15' });
                    // self.loadRowScreen().done(() => {
                    self.getData().then(() => {
                        self.updateCellIsCal(data);
                    });
                }).fail(function(error: any) {
                    self.$dialog.error(error);
                }).always(() => {
                    self.$blockui("hide");
                });
            }
        }

        groupBy(xs: Array<any>, key: string) {
            return xs.reduce(function(rv, x) {
                (rv[x[key]] = rv[x[key]] || []).push(x);
                return rv;
            }, {});
        };

		updateCellIsCal(data:any){
			_.forEach(data, row => {
				_.forEach(row.calculatedItemIds, item => {
					$("#anpGrid").mGrid("setState", row.employeeId, String(item), ["mgrid-calc"]);
				});
			});
		}

        saveColumnWidth() {
            let self = this,
                commands: Array<any> = [],
                jsonColumnWith = localStorage.getItem(window.location.href + '/anpGrid');
            _.forEach($.parseJSON(jsonColumnWith), (valueP, keyP) =>{
                if (keyP != "reparer") {
                    _.forEach(valueP, (value, key) => {
                        if (!!Number(key.substring(1))) {
                            commands.push({itemId: Number(key.substring(1)), colWidth: value});
                        }
                    });
                }
            });
            self.$blockui("show");
            self.$ajax(Paths.saveColWidth, commands).done(() => {
                self.$ajax(Paths.getColumnWidth).done(colWidths => {
                    self.savedColumnWidths(colWidths);
                    self.$blockui('hide');
                }).fail(error => {
                    self.$blockui('hide');
                    self.$dialog.alert(error);
                });
            }).fail((error) => {
                self.$blockui('hide');
                self.$dialog.alert(error);
            });
        }

        //
        // getPrimitiveValue(value: any, atr: any): string {
        //     var self = this;
        //     let valueResult: string = "";
        //     if (atr != undefined && atr != null) {
        //
        //         if (atr == 1) {
        //             valueResult = value == "" ? null : String(self.getHoursTime(value));
        //         } else {
        //             valueResult = value;
        //         }
        //     } else {
        //         valueResult = value;
        //     }
        //     return valueResult;
        // };
        //
        getHours(value: any): number {
            return Number(value.split(':')[0]) * 60 + Number(value.split(':')[1]);
        }

        //time
        getHoursTime(value: any): number {
            var self = this;
            if (value.indexOf(":") != -1) {
                if (value.indexOf("-") != -1) {
                    let valueTemp = value.split('-')[1];
                    return 0 - self.getHours(valueTemp);
                } else {
                    return self.getHours(value);
                }
            } else {
                return value;
            }
        };
        //
        // //time day
        // getHoursAll(value: any): number {
        //     var self = this;
        //     if (value.indexOf(":") != -1) {
        //         if (value.indexOf("-") != -1) {
        //             let valueTemp = value.split('-')[1];
        //             return self.getHours(valueTemp) - 24 * 60;
        //         } else {
        //             return self.getHours(value);
        //         }
        //     } else {
        //         return value;
        //     }
        // };
        //
        // /**
        //  * 期間を変更する
        //  */
        // updateDate(date: any) {
        //     let self = this;
        //     self.$blockui("show");
        //     self.monthlyParam().closureId = self.closureId();
        //     self.monthlyParam().yearMonth = date;
        //     self.monthlyParam().lstEmployees = self.lstEmployee();
        //
        //     if ($("#anpGrid").data('mGrid')) {
        //         $("#anpGrid").mGrid("destroy");
        //         $("#anpGrid").off();
        //     }
        //     //$("#anpGrid").off();
        //
        //     $.when(self.initScreen()).done((processDate) => {
        //         self.$blockui("hide");
        //     });
        //
        // }
        // /**
        //  * 実績期間: List<DatePeriod> actualTimeDats
        //  * 実績期間選択: DatePeriod actualTimeSelectedDat
        //  */
        // initActualTime() {
        //     let self = this,
        //         selectedIndex = 0;
        //     if (self.actualTimeDats && self.actualTimeDats().length > 0) {
        //         self.actualTimeOptionDisp = ko.observableArray();
        //         for (let i = 0; i < self.actualTimeDats().length; i++) {
        //             self.actualTimeOptionDisp.push({ code: i, name: self.actualTimeDats()[i].startDate + "～" + self.actualTimeDats()[i].endDate });
        //             if (self.actualTimeDats()[i].startDate === self.actualTimeSelectedDat().startDate
        //                 && self.actualTimeDats()[i].endDate === self.actualTimeSelectedDat().endDate)
        //                 selectedIndex = i;
        //         }
        //     }
        //     self.actualTimeSelectedCode(0);
        // };
        // /*********************************/
        // receiveData(data) {
        //     let self = this;
        //     self.dpData = data.lstData;
        //     self.cellStates(data.lstCellState);
        //     self.optionalHeader = data.lstControlDisplayItem.lstHeader;
        //     self.sheetsGrid(data.lstControlDisplayItem.lstSheet);
        //     self.sheetsGrid.valueHasMutated();
        // };
        //
        // extractionData() {
        //     let self = this;
        //     self.headersGrid([]);
        //     self.fixColGrid = self.fixHeaders;
        //
        //     self.loadHeader(self.displayFormat());
        // };
        //init ccg001
        initCcg001() {
            let self = this;
            self.ccg001 = {
                // fix bug 100434
                maxPeriodRange: 'none',
                /** Common properties */
                systemType: 2,
                showEmployeeSelection: false, // 検索タイプ
                showQuickSearchTab: true, // クイック検索
                showAdvancedSearchTab: true, // 詳細検索
                showBaseDate: true, // 基準日利用
                showClosure: false, // 就業締め日利用
                showAllClosure: false, // 全締め表示
                showPeriod: false, // 対象期間利用
                periodFormatYM: false, // 対象期間精度

                /** Required parameter */
                periodStartDate: self.baseDate, // 対象期間開始日
                periodEndDate: self.baseDate, // 対象期間終了日
                inService: true, // 在職区分
                leaveOfAbsence: true, // 休職区分
                closed: true, // 休業区分
                retirement: false, // 退職区分

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
                showWorktype: false, // 勤種条件
                isMutipleCheck: true,// 選択モード

                /** Return data */
                returnDataFromCcg001: function(dataList: any) {
//                     self.flagSelectEmployee = true;
                    self.lstEmployee(dataList.listEmployee.map((data: any) => {
                        return {
                            id: data.employeeId,
                            code: data.employeeCode,
                            name: data.employeeName,
                            // workplaceName: data.workplaceName,
                            // workplaceId: data.workplaceId,
                            // depName: '',
                            // isLoginUser: false
                        };
                    }));
//                     self.closureId(dataList.closureId);
//                     self.lstEmployee(_.orderBy(self.lstEmployee(), ['code'], ['asc']));
//                     //Reload screen
// //                    self.reloadParam().lstEmployees = self.lstEmployee();
//                     let yearMonthNew: any = +moment.utc(dataList.periodEnd, 'YYYYMMDD').format('YYYYMM'),
//                         yearMonthOld = self.yearMonth();
//                     self.yearMonth(yearMonthNew);
//                     if (yearMonthNew == yearMonthOld) {
//                         self.yearMonth.valueHasMutated();
//                     }
                },
            };

            $('#ccg001').ntsGroupComponent(self.ccg001);
        };


        loadGrid() {
            let self = this;
            if ($("#anpGrid").data('mGrid')) {
                $("#anpGrid").mGrid("destroy");
                $("#anpGrid").off();
            }
            // self.setHeaderColor();
            // let dataSource = self.formatDate(self.dpData);
			let subWidth = "50px";
            // if (self.displayFormat() === 0) {
             //    subWidth = "135px";
            // } else if (self.displayFormat() === 1) {
             //    subWidth = "135px";
            // } else {
             //    subWidth = "155px";
            // }
			// let comment = (window.screen.availHeight - 240 - 88) + "px";
			// $('#comment-text').css("margin-top",comment);
            new nts.uk.ui.mgrid.MGrid($("#anpGrid")[0], {
                // subWidth: '500px',
                // subHeight: '200px',
                height: (window.screen.availHeight - 200) + "px",
                width: (window.screen.availWidth - 100) + "px",
                headerHeight: '45px',
                dataSource: self.dataSource(),
                dataSourceAdapter: function(ds) {
                    return ds;
                },
                primaryKey: 'id',
                primaryKeyDataType: 'string',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: self.cursorDirection() == 0 ? 'below' : 'right',
                autoFitWindow: false,
                preventEditInError: false,
                hidePrimaryKey: true,
                userId: __viewContext.user.employeeId,
                getUserId: function(k) { return String(k); },
                // errorColumns: ["ruleCode"],
                // errorsOnPage: true,
                columns: self.columns(),
                features: self.getGridFeatures(),
                ntsFeatures: self.getNtsFeatures(),
                ntsControls: []//self.getNtsControls()
            }).create();
            self.displayItemNumber.valueHasMutated();
            self.displayZero.valueHasMutated();
        }
        /**********************************
        * Grid Data Setting 
        **********************************/
        // setFixedHeader(fixHeader: Array<any>) {
        //     let self = this;
        //     let fixedData = _.map(fixHeader, function(item: any) {
        //         return { columnKey: item.key, isFixed: true };
        //     });
        //     self.fixHeaders(fixedData);
        // };
//         checkIsColumn(dataCell: any, key: any): boolean {
//             let check = false;
//             _.each(dataCell, (item: any) => {
//                 if (item.columnKey.indexOf("NO" + key) != -1) {
//                     check = true;
//                     return;
//                 }
//             });
//             return check;
//         };
//
//         isDisableRow(id) {
//             let self = this;
//             for (let i = 0; i < self.rowStates().length; i++) {
//                 return self.rowStates()[i].rowId == id;
//             }
//         };
//
//         isDisableSign(id) {
//             let self = this;
//             for (let i = 0; i < self.cellStates().length; i++) {
//                 return self.cellStates()[i].rowId == id && self.cellStates()[i].columnKey == 'sign';
//             }
//         };
//
//         formatDate(lstData) {
//             let self = this;
//             let start = performance.now();
//             let data = lstData.map((data) => {
//                 let object = {
//                     id: data.id,
//                     state: data.state,
//                     error: data.error,
//                     employeeId: data.employeeId,
//                     employeeCode: data.employeeCode,
//                     employeeName: data.employeeName,
//                     workplaceId: data.workplaceId,
//                     employmentCode: data.employmentCode,
//                     identify: data.identify,
//                     approval: data.approval,
//                     dailyconfirm: data.dailyConfirm,
//                     dailyCorrectPerformance: data.dailyCorrectPerformance,
//                     typeGroup: data.typeGroup,
//                     startDate: self.actualTimeSelectedDat().startDate,
//                     endDate: self.actualTimeSelectedDat().endDate
//                 }
//                 _.each(data.cellDatas, function(item) { object[item.columnKey] = item.value });
//                 return object;
//             });
//             return data;
//         }
//         /**
//          * Grid Setting features
//          */
        getGridFeatures(): Array<any> {
            let self = this;
            let features: Array<any> = [
                {
                    name: 'Resizing',
                    columnSettings: [{
                        columnKey: 'id', allowResizing: false, minimumWidth: 0
                    }]
                },
                // {name: 'MultiColumnHeaders'},
                {
                    name: 'Paging',
                    pageSize: 100,
                    currentPageIndex: 0
                },
                {
                    name: 'ColumnFixing',
                    fixingDirection: 'left',
                    showFixButtons: false,
                    columnSettings: FIXED_COLUMNS.map(c => ({ columnKey: c.key, isFixed: true }))
                },
                {
                    name: 'Summaries',
                    showSummariesButton: false,
                    showDropDownButton: false,
                    columnSettings: self.summaryColumns(),
                    resultTemplate: '{1}'
                },
                {
                    name: 'CellStyles',
                    states: self.cellStates()
                },
                {
                    name: 'HeaderStyles',
                    columns: self.headerColors()
                }
            ];
            if (self.sheets().length > 0) {
                features.push({
                    name: "Sheet",
                    initialDisplay: self.sheets()[0].name,
                    sheets: self.sheets()
                });
            }
            return features;
        }
//
//         totalDay(data) {
//             if (!$("#anpGrid").data("igGridPaging")) return;
//             let numberBefore = 0;
//             let numberAfter = 0;
//             let currentPageIndex = $("#anpGrid").igGridPaging("option", "currentPageIndex");
//             let pageSize = $("#anpGrid").igGridPaging("option", "pageSize");
//             let startIndex: any = currentPageIndex * pageSize;
//             let endIndex: any = startIndex + pageSize;
//             _.forEach(data, function(d, i) {
//                 let before = String(d).split('.')[0];
//                 let after = String(d).split('.')[1];
//                 if (isNaN(after)) after = 0;
//                 let aaa = parseInt(before);
//                 let bbb = parseInt(after);
//                 numberBefore += aaa;
//                 numberAfter += bbb;
//                 if (numberAfter > 9) {
//                     numberBefore++;
//                     numberAfter = numberAfter - 10;
//                 }
//             });
//             return numberBefore + "." + numberAfter;
//         }
//         totalNumber(data) {
//             if (!$("#anpGrid").data("igGridPaging")) return;
//             let total = 0;
//             let currentPageIndex = $("#anpGrid").igGridPaging("option", "currentPageIndex");
//             let pageSize = $("#anpGrid").igGridPaging("option", "pageSize");
//             let startIndex: any = currentPageIndex * pageSize;
//             let endIndex: any = startIndex + pageSize;
//             _.forEach(data, function(d, i) {
//                 if (i < startIndex || i >= endIndex) return;
//                 let n = parseInt(d);
//                 if (!isNaN(n)) total += n;
//             });
//             return total;
//         }
//         totalTime(data) {
//             if (!$("#anpGrid").data("igGridPaging")) return;
//             let currentPageIndex = $("#anpGrid").igGridPaging("option", "currentPageIndex");
//             let pageSize = $("#anpGrid").igGridPaging("option", "pageSize");
//             let startIndex: any = currentPageIndex * pageSize;
//             let endIndex: any = startIndex + pageSize;
//             let total = 0;
//             _.forEach(data, function(d, i) {
//                 if (i < startIndex || i >= endIndex) return;
//                 if (d != "") {
//                     total = total + moment.duration(d).asMinutes();
//                 }
//             });
//             let hours = total > 0 ? Math.floor(total / 60) : Math.ceil(total / 60);
//             let minus = Math.abs(total % 60);
//             minus = (minus < 10) ? '0' + minus : minus;
//             return ((total < 0 && hours == 0) ? "-" + hours : hours) + ":" + minus;
//         }
//
//         totalMoney(data) {
//             if (!$("#anpGrid").data("igGridPaging")) return;
//             let total = 0;
//             let currentPageIndex = $("#anpGrid").igGridPaging("option", "currentPageIndex");
//             let pageSize = $("#anpGrid").igGridPaging("option", "pageSize");
//             let startIndex: any = currentPageIndex * pageSize;
//             let endIndex: any = startIndex + pageSize;
//             _.forEach(data, function(d, i) {
//                 let valueResult = "";
//                 if (i < startIndex || i >= endIndex) return;
//                 if (String(d).indexOf(",") != -1) {
//                     for (let i = 0; i < String(d).split(',').length; i++) {
//                         valueResult += String(d).split(',')[i];
//                     }
//                     let n = parseFloat(valueResult);
//                     if (!isNaN(n)) total += n;
//                 } else {
//                     let n = parseFloat(d);
//                     if (!isNaN(n)) total += n;
//                 }
//             });
//             return total.toLocaleString('en');
//         }
//
//         /**
//          * Create NtsControls
//          */
        getNtsControls(initMode?: number, closureId?: any): Array<any> {
            let self = this;
            let ntsControls: Array<any> = [
                // { name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                // {
                //     name: 'Link2',
                //     click: function(rowId, key, event) {
                //         if (!self.clickCounter.clickLinkGrid) {
                //             self.clickCounter.clickLinkGrid = true;
                //             let value = $("#anpGrid").mGrid("getCellValue", rowId, "Code" + key.substring(4, key.length));
                //             let dialog: TypeDialog = new TypeDialog(key.substring(4, key.length), self.lstAttendanceItem(), value, rowId);
                //             dialog.showDialog(self);
                //             nts.uk.ui.block.clear();
                //         }
                //     },
                //     controlType: 'LinkLabel'
                // },
                // {
                //     name: 'Button', controlType: 'Button', text: getText("KMW003_29"), enable: true, click: function(data) {
                //         let self = this;
                //         let source: any = $("#anpGrid").mGrid("dataSource");
                //         let rowSelect = _.find(source, (value: any) => {
                //             return value.id == data.id;
                //         })
                //         let initParam = new DPCorrectionInitParam(ScreenMode.NORMAL, [rowSelect.employeeId], false, false, closureId, __viewContext.vm.yearMonth(), '/view/kmw/003/a/index.xhtml?initmode='+ initMode);
                //         let extractionParam = new DPCorrectionExtractionParam(DPCorrectionDisplayFormat.INDIVIDUAl, rowSelect.startDate, rowSelect.endDate, _.map(__viewContext.vm.dpData, data => data.employeeId), rowSelect.employeeId);
                //         nts.uk.request.jump("/view/kdw/003/a/index.xhtml", { initParam: initParam, extractionParam: extractionParam });
                //     }
                // },
                // {
                //     name: 'FlexImage', source: 'ui-icon ui-icon-locked', click: function(key, rowId, evt) {
                //         let data = $("#anpGrid").mGrid("getCellValue", rowId, key);
                //         if (data != "") {
                //             let lock = data.split("|");
                //             let tempD = "<span>";
                //             for (let i = 0; i < lock.length; i++) {
                //                 //月別実績のロック
                //                 if (lock[i] == "monthlyResultLock")
                //                     tempD += getText("KMW003_35") + '<br/>';
                //                 //職場の就業確定
                //                 if (lock[i] == "employmentConfirmWorkplace")
                //                     tempD += getText("KMW003_36") + '<br/>';
                //                 //月別実績の承認
                //                 if (lock[i] == "monthlyResultApprova")
                //                     tempD += getText("KMW003_37") + '<br/>';
                //                 //日別実績の不足
                //                 if (lock[i] == "monthlyResultLack")
                //                     tempD += getText("KMW003_38") + '<br/>';
                //                 //日別実績の確認
                //                 if (lock[i] == "monthlyResultConfirm")
                //                     tempD += getText("KMW003_39") + '<br/>';
                //                 //日別実績のエラー
                //                 if (lock[i] == "monthlyResultError")
                //                     tempD += getText("KMW003_40") + '<br/>';
                //                 //過去実績のロック
                //                 if (lock[i] == "pastPerformaceLock")
                //                     tempD += getText("KMW003_41") + '<br/>';
                //             }
                //             tempD += '</span>';
                //             $('#textLock').html(tempD);
                //         }
                //         self.helps(evt, "");
                //     }, controlType: 'FlexImage'
                // },
                // { name: 'Image', source: 'ui-icon ui-icon-info', controlType: 'Image' },
                { name: 'AmountEditor', controlType: 'TextEditor', constraint: { valueType: 'Integer', required: false, format: "Number_Separated" } },
                { name: 'TimeEditor', controlType: 'TextEditor', constraint: { valueType: 'Time', required: false, format: "Time_Short_HM" } }
            ];
            return ntsControls;
        }
//
         getNtsFeatures(): Array<any> {
            let self = this;
            let features: Array<any> = [
                { name: 'CopyPaste' },
                { name: 'CellEdit' }
            ];
            return features;
        }
//
//         /**
//          * Setting header style
//          */
//         setHeaderColor() {
//             let self = this;
//             self.headerColors([]);
//             _.forEach(self.headersGrid(), (header) => {
//                 //Setting color single header
//                 if (header.color) {
//                     self.headerColors.push({
//                         key: header.key,
//                         colors: [header.color]
//                     });
//                 }
//                 //Setting color group header
//                 if (header.group != null && header.group != undefined && header.group.length > 0) {
//                     self.headerColors.push({
//                         key: header.group[0].key,
//                         colors: [header.group[0].color]
//                     });
//                     self.headerColors.push({
//                         key: header.group[1].key,
//                         colors: [header.group[1].color]
//                     });
//                 }
//             });
//         }
//         reloadGrid() {
//             let self = this;
//             self.$blockui("show");
//             setTimeout(function() {
//                 self.columnSettings(self.dataAll().lstControlDisplayItem.columnSettings);
//                 self.receiveData(self.dataAll());
//                 self.createSumColumn(self.dataAll());
//                 self.extractionData();
//                 self.loadGrid();
//                 self.$blockui("hide");
//             }, 500);
//         }
//         reloadGridLock(data) {
//             let self = this;
//             self.$blockui("show");
//             setTimeout(function() {
//                 self.columnSettings(self.dataAll().lstControlDisplayItem.columnSettings);
//                 self.receiveData(self.dataAll());
//                 self.createSumColumn(self.dataAll());
//                 self.extractionData();
//                 self.loadGrid();
//                 _.forEach(data.mpsateCellHideControl, (cellHide => {
//                     $('#anpGrid').mGrid("setState", cellHide.rowId, cellHide.columnKey, ["mgrid-hide"])
//                 }));
//                 self.$blockui("hide");
//             }, 500);
//         }
//         loadHeader(mode) {
//             let self = this;
//             let tempList = [];
// //            self.dislayNumberHeaderText();
//             self.displayProfileIcon();
//             _.forEach(self.optionalHeader, (header) => {
//                 if (header.constraint == null || header.constraint == undefined) {
//                     delete header.constraint;
//                 } else {
//                     if (header.constraint.cdisplayType == null || header.constraint.cdisplayType == undefined) {
//                         header.constraint.cdisplayType = header.constraint.cDisplayType;
//                     }
//                     header.constraint["cDisplayType"] = header.constraint.cdisplayType;
//                     if (header.constraint.cDisplayType != null && header.constraint.cDisplayType != undefined) {
//                         if (header.constraint.cDisplayType != "Primitive" && header.constraint.cDisplayType != "Combo") {
//                             if (header.constraint.cDisplayType.indexOf("Currency") != -1) {
//                                 header["columnCssClass"] = "currency-symbol halign-right";
//                                 header.constraint["min"] = header.constraint.min;
//                                 header.constraint["max"] = header.constraint.max;
//                             } else if (header.constraint.cDisplayType == "Clock") {
//                                 header["columnCssClass"] = "right-align";
//                                 header.constraint["min"] = "0:00";
//                                 header.constraint["max"] = "999:59"
//                             } else if (header.constraint.cDisplayType == "Integer") {
//                                 header["columnCssClass"] = "right-align";
//                                 header.constraint["min"] = "0";
//                                 header.constraint["max"] = "99"
//                             } else if (header.constraint.cDisplayType == "HalfInt") {
//                                 header["columnCssClass"] = "right-align";
//                                 header.constraint["min"] = "0";
//                                 header.constraint["max"] = "99.5"
//                             }
//                             delete header.constraint.primitiveValue;
//                         } else {
//
//                             if (header.constraint.cDisplayType == "Primitive") {
//                                 if (header.group == undefined || header.group.length == 0) {
//                                     delete header.constraint.cDisplayType;
// //                                    if (header.constraint.primitiveValue.indexOf("AttendanceTime") != -1) {
// //                                        header["columnCssClass"] = "halign-right";
// //                                    }
// //                                    if (header.constraint.primitiveValue == "BreakTimeGoOutTimes" || header.constraint.primitiveValue == "WorkTimes") {
// //                                        header["columnCssClass"] = "halign-right";
// //                                    }
//                                     if (header.constraint.primitiveValue == "BreakTimeGoOutTimes" || header.constraint.primitiveValue == "WorkTimes" || header.constraint.primitiveValue == "AnyItemTimes" || header.constraint.primitiveValue == "AnyTimesMonth") {
//                                         header["columnCssClass"] = "halign-right";
//                                         header.constraint["decimallength"] = 2;
//                                     }
//                                 } else {
//                                     delete header.group[0].constraint.cDisplayType;
//                                     delete header.constraint;
//                                     delete header.group[1].constraint;
//                                 }
//                             } else if (header.constraint.cDisplayType == "Combo") {
//                                 header.group[0].constraint["min"] = 0;
//                                 header.group[0].constraint["max"] = Number(header.group[0].constraint.primitiveValue);
//                                 header.group[0].constraint["cDisplayType"] = header.group[0].constraint.cdisplayType;
//                                 delete header.group[0].constraint.cdisplayType
//                                 delete header.group[0].constraint.primitiveValue;
//                                 delete header.constraint;
//                                 delete header.group[1].constraint;
//                             }
//                         }
//                     }
//                     if (header.constraint != undefined) delete header.constraint.cdisplayType;
//                 }
//                 if (header.group != null && header.group != undefined) {
//                     if (header.group.length > 0) {
//                         if (header.group[0].constraint == undefined) delete header.group[0].constraint;
//                         delete header.group[1].constraint;
//                         delete header.group[0].group;
//                         delete header.key;
//                         delete header.dataType;
//                         // delete header.width;
//                         delete header.ntsControl;
//                         delete header.changedByOther;
//                         delete header.changedByYou;
//                         // delete header.color;
//                         delete header.hidden;
//                         delete header.ntsType;
//                         delete header.onChange;
//                         delete header.group[1].ntsType;
//                         delete header.group[1].onChange;
//                         if (header.group[0].dataType == "String") {
//                             header.group[0].onChange = self.search;
//                             // delete header.group[0].onChange;
//                             delete header.group[0].ntsControl;
//                         } else {
//                             delete header.group[0].onChange;
//                             delete header.group[0].ntsControl;
//                         }
//                         delete header.group[1].group;
//                     } else {
//                         delete header.group;
//                     }
//                 }
//                 tempList.push(header);
//             });
//             return self.headersGrid(tempList);
//         }
//
//         displayProfileIcon() {
//             let self = this;
//             if (self.showProfileIcon()) {
//                 _.remove(self.optionalHeader, function(header) {
//                     return header.key === "picture-person";
//                 });
//                 _.remove(self.fixColGrid(), function(header) {
//                     return header.columnKey === "picture-person";
//                 });
//                 self.optionalHeader.splice(5, 0, { headerText: '', key: "picture-person", dataType: "string", width: '35px', ntsControl: 'Image' });
//                 self.fixColGrid().splice(5, 0, { columnKey: 'picture-person', isFixed: true });
//             } else {
//                 _.remove(self.optionalHeader, function(header) {
//                     return header.key === "picture-person";
//                 });
//                 _.remove(self.fixColGrid(), function(header) {
//                     return header.columnKey === "picture-person";
//                 });
//             }
//         }
//
//         helps(event, data) {
//             var self = this;
//             $('#tooltip').css({
//                 'left': event.pageX + 15,
//                 'top': event.pageY - 12,
//                 'display': 'none',
//                 'position': 'fixed',
//             });
//             self.lockMessage(data);
//             $("#tooltip").show();
//         }
//         /**
//          * 実績期間を変更する
//          */
//         updateActualTime() {
//             let self = this;
//             self.monthlyParam().actualTime = self.actualTimeSelectedDat();
//             //            self.monthlyParam().actualTime.startDate = moment(self.actualTimeSelectedDat().startDate).toISOString();
//             //            self.monthlyParam().actualTime.endDate = moment(self.actualTimeSelectedDat().endDate).toISOString();
//             //            //self.actualTimeSelectedDat
//             //            self.initScreen();
//         }
//         /**
//          * Check all CheckBox
//          */
//         signAll() {
//             let self = this;
//             if(self.noCheckColumn()) return;
//             $("#anpGrid").mGrid("checkAll", "identify", true);
//             $("#anpGrid").mGrid("checkAll", "approval", true);
//         }
//         /**
//          * UnCheck all CheckBox
//          */
//         releaseAll() {
//             let self = this;
//             if(self.noCheckColumn()) return;
//             $("#anpGrid").mGrid("uncheckAll", "identify", true);
//             $("#anpGrid").mGrid("uncheckAll", "approval", true);
//         }
//         /**
//          * ロック解除ボタン　クリック
//          */
//         unlockProcess() {
//             let self = this;
//             //アルゴリズム「ロックを解除する」を実行する
//             self.$dialog.confirm({ messageId: "Msg_983" }).ifYes(() => {
//                 //画面モードを「ロック解除モード」に変更する
//                 self.initMode(ScreenMode.UNLOCK);
//                 self.showButton(new AuthorityDetailModel(self.dataAll().authorityDto, self.dataAll().actualTimeState, self.initMode(), self.dataAll().formatPerformance.settingUnitType));
//                 //ロック状態を画面に反映する
//                 $("#anpGrid").mGrid("destroy");
//                 $("#anpGrid").off();
//                 self.monthlyParam().lstLockStatus = [];
//                 if (self.monthlyParam().actualTime) {
//                     self.monthlyParam().actualTime.startDate = moment.utc(self.monthlyParam().actualTime.startDate, "YYYY/MM/DD").toISOString();
//                     self.monthlyParam().actualTime.endDate = moment.utc(self.monthlyParam().actualTime.endDate, "YYYY/MM/DD").toISOString();
//                 }
//                 self.monthlyParam().initMenuMode = self.initMode();
//                 self.$ajax(Paths.startScreen, self.monthlyParam()).done((data) => {
//                     self.dataAll(data);
//                     self.reloadGridLock(data);
//                 });
//             }).ifNo(() => {
//
//             });
//         }
//         /**
//          * 再ロックボタン　クリック
//          */
//         lockProcess() {
//             let self = this;
//             // 画面モードを「修正モード」に変更する
//             self.initMode(ScreenMode.NORMAL);
//             self.showButton(new AuthorityDetailModel(self.dataAll().authorityDto, self.dataAll().actualTimeState, self.initMode(), self.dataAll().formatPerformance.settingUnitType));
//             //アルゴリズム「ロック状態を表示する」を実行する
//             //確認メッセージ「#Msg_984」を表示する
//             $("#anpGrid").mGrid("destroy");
//             $("#anpGrid").off();
//             self.monthlyParam().lstLockStatus = [];
//             if (self.monthlyParam().actualTime) {
//                 self.monthlyParam().actualTime.startDate = moment.utc(self.monthlyParam().actualTime.startDate, "YYYY/MM/DD").toISOString();
//                 self.monthlyParam().actualTime.endDate = moment.utc(self.monthlyParam().actualTime.endDate, "YYYY/MM/DD").toISOString();
//             }
//             self.monthlyParam().initMenuMode = self.initMode();
//             self.$ajax(Paths.startScreen, self.monthlyParam()).done((data) => {
//                 self.dataAll(data);
//                 self.reloadGridLock(data);
//                 nts.uk.ui.dialog.info({ messageId: "Msg_984" });
//             });
//         }
//         /**
//          * Grid setting
//          */
//         btnSetting_Click() {
//             let container = $("#setting-content");
//             if (container.css("visibility") === 'hidden') {
//                 container.css("visibility", "visible");
//                 container.css("top", "-1px");
//                 container.css("left", "258px");
//             }
//             $(document).mouseup(function(e) {
//                 // if the target of the click isn't the container nor a descendant of the container
//                 if (!container.is(e.target) && container.has(e.target).length === 0) {
//                     container.css("visibility", "hidden");
//                     container.css("top", "-9999px");
//                     container.css("left", "-9999px");
//                 }
//             });
//         }
//         /**
//          * 「KDW003_D_抽出条件の選択」を起動する
//          * 起動モード：月別
//          * 選択済項目：選択している「月別実績のエラーアラームコード」
//          */
//         extractCondition() {
//             let self = this;
//             let errorParam = { initMode: 1, selectedItems: self.selectedErrorCodes() };
//             nts.uk.ui.windows.setShared("KDW003D_ErrorParam", errorParam);
//             nts.uk.characteristics.save("cacheKMW003",self.dataAll());
//             nts.uk.ui.windows.sub.modal("/view/kdw/003/d/index.xhtml").onClosed(() => {
//                 debugger;
//                 let errorCodes: any = nts.uk.ui.windows.getShared("KDW003D_Output");
//                 //アルゴリズム「選択した抽出条件に従って実績を表示する」を実行する
//                 if (!nts.uk.util.isNullOrUndefined(errorCodes)) {
//                     //TODO Client filter
//                     //選択されたエラーコード一覧
//                 }
//             });
//         }
//
//         /**
//          * 「KDW003_C_表示フォーマットの選択」を起動する
//          * 起動モード：月別
//          * 選択済項目：選択している「月別実績のフォーマットコード」
//          */
//         displayItem(): JQueryPromise<any> {
//             let self = this;
//             let dfd = $.Deferred();
//             let formatParam = { initMode: 1, selectedItem: "" };
//             nts.uk.ui.windows.setShared("KDW003C_Param", formatParam);
//             nts.uk.ui.windows.sub.modal("/view/kdw/003/c/index.xhtml").onClosed(() => {
//                 let res = nts.uk.ui.windows.getShared('KDW003C_Err');
//                 if(!_.isEmpty(res) && res.jumpToppage){
//                     nts.uk.request.jumpToTopPage();
//                 }
//                 let formatCd = nts.uk.ui.windows.getShared('KDW003C_Output');
//                 if (formatCd) {
//                     self.formatCodes.removeAll();
//                     self.formatCodes.push(formatCd);
//                     self.initScreenFormat().done((processDate) => {
// 						if(!_.isNil(processDate)){
// 							self.yearMonth(processDate);
// 						}
//                         dfd.resolve();
//                     }).fail(function(error) {
//                         self.$dialog.error(error).then(function() {
//                             nts.uk.request.jumpToTopPage();
//                         });
//                         dfd.reject();
//                     });
//                 } else {
//                     dfd.reject();
//                 }
//             });
//             return dfd.promise();
//         }
//

//

//
//         search(columnKey, rowId, val, valOld) {
//             let dfd = $.Deferred();
//             const self = this;
//             let i = 0, baseDate = null;
//             let data: any = $("#anpGrid").mGrid("dataSource");
//             let rowItemSelect: any = _.find(data, function(value: any) {
//                 return value.id == rowId;
//             })
//
//             let columnId: number = +columnKey.substring(4, columnKey.length);
//             let typeGroup = 0;
//             if(columnId == 192 || columnId == 197){
//                 typeGroup = TypeGroup.EMPLOYMENT;
//             }
//             if(columnId == 193 || columnId == 198){
//                 typeGroup = TypeGroup.POSITION;
//                 if(columnId == 193){
//                     baseDate = __viewContext.vm.actualTimeDats()[0].startDate;
//                 } else {
//                     baseDate = __viewContext.vm.actualTimeDats()[0].endDate;
//                 }
//
//             }
//             if(columnId == 194 || columnId == 199){
//                 typeGroup = TypeGroup.WORKPLACE;
//                 if(columnId == 194){
//                     baseDate = __viewContext.vm.actualTimeDats()[0].startDate;
//                 } else {
//                     baseDate = __viewContext.vm.actualTimeDats()[0].endDate;
//                 }
//             }
//             if(columnId == 195 || columnId == 200){
//                 typeGroup = TypeGroup.CLASSIFICATION;
//             }
//             if(columnId == 196 || columnId == 201){
//                 typeGroup = TypeGroup.BUSINESSTYPE;
//             }
//
//             //remove error
//             _.remove(__viewContext.vm.workTypeNotFound, error => {
//                 return error.columnKey == columnKey && error.rowId == rowId;
//             })
//
//             if (typeGroup != undefined && typeGroup != null) {
//                 let param = {
//                     typeDialog: typeGroup,
//                     param: {
//                         employmentCode: rowItemSelect.employmentCode,
//                         workplaceId: rowItemSelect.workplaceId,
//                         date: moment.utc(baseDate),
//                         selectCode: val,
//                         employeeId: rowItemSelect.employeeId,
//                         itemId: columnId,
//                         valueOld: valOld
//                     }
//                 }
//                 var object = {};
//                 if (val == "") {
//                     dfd.resolve(getText("KDW003_82"));
//                 } else {
//                     self.$ajax(Paths.findCodeName, param).done((data) => {
//                         if (data != undefined) {
//                             let typeError = _.find(__viewContext.vm.workTypeNotFound, data => {
//                                 return data.columnKey == columnKey && data.rowId == rowId;
//                             });
//                             if (data.errorFind == 1) {
//                                 let e = document.createEvent("HTMLEvents");
//                                 e.initEvent("mouseup", false, true);
//                                 $("#anpGrid")[0].dispatchEvent(e);
//                                 if (typeError == undefined) {
//                                     __viewContext.vm.workTypeNotFound.push({ columnKey: columnKey, rowId: rowId, message: nts.uk.resource.getMessage("Msg_1293"), employeeId: rowItemSelect.employeeId, date: moment(rowItemSelect.dateDetail)});
//                                 } else {
//                                     typeError.message = nts.uk.resource.getMessage("Msg_1293");
//                                 }
//                                 nts.uk.ui.dialog.alertError({ messageId: "Msg_1293" })
//                             } else if (data.errorFind == 2) {
//                                 let e = document.createEvent("HTMLEvents");
//                                 e.initEvent("mouseup", false, true);
//                                 $("#anpGrid")[0].dispatchEvent(e);
//                                 if (typeError == undefined) {
//                                     __viewContext.vm.workTypeNotFound.push({ columnKey: columnKey, rowId: rowId, message: nts.uk.resource.getMessage("Msg_1314"), employeeId: rowItemSelect.employeeId, date: moment(rowItemSelect.dateDetail) });
//                                 } else {
//                                     typeError.message = nts.uk.resource.getMessage("Msg_1314");
//                                 }
//                                 nts.uk.ui.dialog.alertError({ messageId: "Msg_1314" })
//                             } else {
//                                 _.remove(__viewContext.vm.workTypeNotFound, dataTemp => {
//                                     return dataTemp.columnKey == columnKey && dataTemp.rowId == rowId;
//                                 });
//                             }
//
//                             dfd.resolve(data == undefined ? getText("KDW003_81") : data.name);
//                         } else {
//                             _.remove(__viewContext.vm.workTypeNotFound, dataTemp => {
//                                 return dataTemp.columnKey == columnKey && dataTemp.rowId == rowId;
//                             });
//                             dfd.resolve(data == undefined ? getText("KDW003_81") : data.name);
//                         }
//                     });
//                 }
//
//             } else {
//                 dfd.resolve("");
//             }
//             return dfd.promise();
//         }
        
    }
    
    // export class AuthorityDetailModel {
    //     //A4_2
    //     available_A4_2: KnockoutObservable<boolean> = ko.observable(true);
    //     //A4_7
    //     available_A4_7: KnockoutObservable<boolean> = ko.observable(false);
    //     //A1_1
    //     available_A1_1: KnockoutObservable<boolean> = ko.observable(false);
    //     enable_A1_1:  /*boolean = false;*/KnockoutObservable<boolean> = ko.observable(false);
    //     //A1_2
    //     available_A1_2: KnockoutObservable<boolean> = ko.observable(false);
    //     enable_A1_2: KnockoutObservable<boolean> = ko.observable(false);
    //     //A1_4
    //     available_A1_4: KnockoutObservable<boolean> = ko.observable(false);
    //     //A1_8
    //     available_A1_8: KnockoutObservable<boolean> = ko.observable(false);
    //     //A1_9
    //     available_A1_9: KnockoutObservable<boolean> = ko.observable(false);
    //     //A1_11
    //     available_A1_11: KnockoutObservable<boolean> = ko.observable(false);
    //     //A5_4
    //     available_A5_4: KnockoutObservable<boolean> = ko.observable(false);
    //     enable_multiActualTime: KnockoutObservable<boolean> = ko.observable(false);
    //     enable_A5_4: KnockoutObservable<boolean> = ko.observable(false);
    //     enable_A1_5: KnockoutObservable<boolean> = ko.observable(true);
    //
    //     prevData: Array<DailyPerformanceAuthorityDto> = null;
    //     prevInitMode: number = 0;
    //     /**
    //      * formatPerformance: 権限 = 0, 勤務種別 = 1
    //      * initMode: 修正モード  = 0,  ロック解除モード    = 1
    //      * actualTimeState: 過去 = 0, 当月 = 1, 未来 = 2
    //      */
    //     constructor(data: Array<DailyPerformanceAuthorityDto>, actualTimeState: number, initMode: number, formatPerformance: number) {
    //         let self = this;
    //         if (!data) return;
    //         $('#cbClosureInfo').hide();
    //         self.available_A4_7(false);
    //         self.available_A1_1(self.checkAvailable(data, 32));
    //         self.available_A1_2(self.checkAvailable(data, 33));
    //         self.available_A1_4(self.checkAvailable(data, 34));
    //         self.available_A5_4(self.checkAvailable(data, 11));
    //         if (initMode == 0) { //修正モード
    //             if (formatPerformance == 0) { //権限
    //                 self.enable_A1_1(actualTimeState == 1 || actualTimeState == 2);
    //                 self.enable_A1_2(actualTimeState == 1 || actualTimeState == 2);
    //                 self.enable_A5_4(actualTimeState == 1 || actualTimeState == 2);
    //             } else if (formatPerformance == 1) { //勤務種別
    //                 self.enable_A1_1(actualTimeState == 1 || actualTimeState == 2);
    //                 self.enable_A1_2(actualTimeState == 1 || actualTimeState == 2);
    //                 self.enable_A5_4(actualTimeState == 1 || actualTimeState == 2);
    //                 self.enable_A1_5(false);
    //             }
    //             self.available_A1_8(self.checkAvailable(data, 12));
    //             //A2_1
    //             $('#ccg001').show();
    //         } else if (initMode == 1) { //ロック解除モード
    //             self.enable_A1_1(true);
    //             self.enable_A1_2(true);
    //             self.enable_A5_4(true);
    //             if (formatPerformance == 1) { //勤務種別
    //                 self.enable_A1_5(false);
    //             }
    //             self.available_A1_9(self.checkAvailable(data, 12));
    //             self.available_A1_11(self.checkAvailable(data, 12));
    //             //A2_1
    //             $('#ccg001').show();
    //
    //         } else if (initMode == 2) {
    //             $('#cbClosureInfo').show();
    //             //A4_7
    //             self.available_A4_7(true);
    //             self.available_A1_11(false);
    //             //A2_1
    //             $('#ccg001').hide();
    //             //A4_2
    //             self.available_A4_2(false);
    //
    //             /**
    //              *Tu Test
    //              **/
    //             //A1_1,A1_2
    //             self.enable_A1_1(true);
    //             self.enable_A1_2(true);
    //             //A5_4
    //             self.enable_A5_4(actualTimeState == 1 || actualTimeState == 2);
    //             if (formatPerformance == 0) { //権限
    //                 self.enable_A1_5(true);
    //             } else if (formatPerformance == 1) { //勤務種別
    //                 self.enable_A1_5(false);
    //             }
    //         }
    //         self.prevData = data;
    //         self.prevInitMode = initMode;
    //     }
    //     checkAvailable(data: Array<DailyPerformanceAuthorityDto>, value: number): boolean {
    //         let self = this;
    //         let check = _.find(data, function(o) {
    //             return o.functionNo === value;
    //         })
    //         if (check == null) return false;
    //         else return check.availability;
    //     };
    // }
    //
    // class ItemModel {
    //     code: string;
    //     name: string;
    //
    //     constructor(code: string, name: string) {
    //         this.code = code;
    //         this.name = name;
    //     }
    // }
    //
    // class CLickCount {
    //     clickLinkGrid: boolean;
    //     clickErrorRefer: boolean;
    //     constructor() {
    //         this.clickLinkGrid = false;
    //         this.clickErrorRefer = false;
    //     }
    // }
    //
    // export interface DailyPerformanceAuthorityDto {
    //     isDefaultInitial: number;
    //     roleID: string;
    //     functionNo: number;
    //     availability: boolean;
    // }
    //
    // //    export interface DPAttendanceItem {
    // //        id: string;
    // //        name: string;
    // //        displayNumber: number;
    // //        userCanSet: boolean;
    // //        lineBreakPosition: number;
    // //        attendanceAtr: number;
    // //        typeGroup: number;
    // //    }
    //
    // class InfoCellEdit {
    //     rowId: any;
    //     itemId: any;
    //     value: any;
    //     valueType: number;
    //     layoutCode: string;
    //     employeeId: string;
    //     typeGroup: number;
    //     version: number;
    //     constructor(rowId: any, itemId: any, value: any, valueType: number, layoutCode: string, employeeId: string, typeGroup?: number) {
    //         this.rowId = rowId;
    //         this.itemId = itemId;
    //         this.value = value;
    //         this.valueType = valueType;
    //         this.layoutCode = layoutCode;
    //         this.employeeId = employeeId;
    //         this.typeGroup = typeGroup;
    //     }
    // }
    //
    // class DataHoliday {
    //     compensation: string;
    //     substitute: string;
    //     paidYear: string;
    //     paidHalf: string;
    //     paidHours: string;
    //     fundedPaid: string;
    //     constructor(compensation: string, substitute: string, paidYear: string, paidHalf: string, paidHours: string, fundedPaid: string) {
    //         this.compensation = getText("KMW003_8", [compensation])
    //         this.substitute = getText("KMW003_8", [substitute])
    //         this.paidYear = getText("KMW003_8", [paidYear])
    //         //            this.paidHalf = getText("KMW003_10", paidHalf)
    //         //            this.paidHours = getText("KMW003_11", paidHours)
    //         this.fundedPaid = getText("KMW003_8", [fundedPaid])
    //     }
    //
    // }
    // class RowState {
    //     rowId: number;
    //     disable: boolean;
    //     constructor(rowId: number, disable: boolean) {
    //         this.rowId = rowId;
    //         this.disable = disable;
    //     }
    // }
    // class TextColor {
    //     rowId: number;
    //     columnKey: string;
    //     color: string;
    //     constructor(rowId: any, columnKey: string, color: string) {
    //         this.rowId = rowId;
    //         this.columnKey = columnKey;
    //         this.color = color;
    //     }
    // }
    // class CellState {
    //     rowId: number;
    //     columnKey: string;
    //     state: Array<any>
    //     constructor(rowId: number, columnKey: string, state: Array<any>) {
    //         this.rowId = rowId;
    //         this.columnKey = columnKey;
    //         this.state = state;
    //     }
    // }
    // export class DPCorrectionInitParam {
    //     //画面モード
    //     screenMode: ScreenMode;
    //     //社員一覧
    //     lstEmployee: any;
    //     //エラー参照を起動する
    //     errorRefStartAtr: boolean;
    //     //期間を変更する
    //     changePeriodAtr: boolean;
    //     //処理締め
    //     targetClosue: number;
    //     //Optional
    //     //打刻初期値
    //     initClock: any;
    //     //遷移先の画面
    //     transitionDesScreen: any;
    //     //対象年月
    //     yearMonth: any;
    //
    //     constructor(screenMode, lstEmployee, errorRefStartAtr, changePeriodAtr, targetClosue, yearMonth, transitionDesScreen) {
    //         let self = this;
    //         self.screenMode = screenMode;
    //         self.lstEmployee = lstEmployee;
    //         self.errorRefStartAtr = errorRefStartAtr;
    //         self.changePeriodAtr = changePeriodAtr;
    //         self.targetClosure = targetClosue;
    //         self.transitionDesScreen = transitionDesScreen;
    //         self.yearMonth = yearMonth;
    //     }
    // }
    //
    // export class DPCorrectionExtractionParam {
    //     //表示形式
    //     displayFormat: number;
    //     //期間
    //     startDate: string;
    //     endDate: string;
    //     //抽出した社員一覧
    //     lstExtractedEmployee: any;
    //     //Optional
    //     //日付別で起動
    //     dateTarget: any;
    //
    //     individualTarget: any;
    //
    //     constructor(displayFormat, startDate, endDate, lstExtractedEmployee, individualTarget) {
    //         let self = this;
    //         self.displayFormat = displayFormat;
    //         self.startDate = startDate;
    //         self.endDate = endDate;
    //         self.lstExtractedEmployee = lstExtractedEmployee;
    //         self.individualTarget = individualTarget;
    //     }
    // }
    // export enum DPCorrectionDisplayFormat {
    //     //個人別
    //     INDIVIDUAl = 0,
    //     //日付別
    //     DATE = 1,
    //     //エラー・アラーム
    //     ErrorAlarm = 2
    // }
    //
    // export enum ScreenMode {
    //     //通常
    //     NORMAL = 0,
    //
    //     UNLOCK = 1,
    //     //承認
    //     APPROVAL = 2
    // }
    //
    // export enum TypeGroup {
    //     WORKPLACE = 5,
    //     CLASSIFICATION = 6,
    //     POSITION = 7,
    //     EMPLOYMENT = 8,
    //     BUSINESSTYPE = 14
    // }
    //
    // export class TypeDialog {
    //
    //     attendenceId: string;
    //     data: Array<DPAttendanceItem>;
    //     alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
    //     selectedCode: KnockoutObservable<string>;
    //     listCode: KnockoutObservableArray<any>;
    //     rowId: KnockoutObservable<any>;
    //     constructor(attendenceId: string, data: Array<DPAttendanceItem>, selectedCode: string, rowId: any) {
    //         var self = this;
    //         self.attendenceId = attendenceId;
    //         self.data = data;
    //         self.selectedCode = ko.observable(selectedCode);
    //         self.listCode = ko.observableArray([]);
    //         self.rowId = ko.observable(rowId);
    //     };
    //
    //     showDialog(parent: any) {
    //         let self = this;
    //         let selfParent = parent;
    //         let item: DPAttendanceItem;
    //         let codeName: any;
    //         nts.uk.ui.block.invisible();
    //         nts.uk.ui.block.grayout();
    //         item = _.find(self.data, function(data) {
    //             return data.id == self.attendenceId;
    //         })
    //         switch (item.id) {
    //             case 194:
    //                 //CDL008
    //                 let dateCon194 = selfParent.displayFormat() === 1 ? moment(selfParent.selectedDate()) : moment(__viewContext.vm.actualTimeDats()[0].startDate).utc().toISOString();
    //
    //                 let param194 = {
    //                     typeDialog: 5,
    //                     param: {
    //                         date: dateCon194
    //                     }
    //                 }
    //                 let data194: any;
    //                 let dfd194 = $.Deferred();
    //                 // service.findAllCodeName(param194).done((data: any) => {
    //                 //     data194 = data;
    //                 //     codeName = _.find(data194, (item: any) => {
    //                 //         return item.code == self.selectedCode();
    //                 //     });
    //                 //     setShared('inputCDL008', {
    //                 //         selectedCodes: codeName == undefined ? "" : codeName.id,
    //                 //         baseDate: dateCon194,
    //                 //         isMultiple: false,
    //                 //         selectedSystemType: 2,
    //                 //         isrestrictionOfReferenceRange: true,
    //                 //         showNoSelection: false,
    //                 //         isShowBaseDate: false
    //                 //     }, true);
    //                     dfd194.resolve()
    //                 // });
    //                 dfd194.promise();
    //                 modal('com', '/view/cdl/008/a/index.xhtml').onClosed(function(): any {
    //                     // Check is cancel.
    //                     if (nts.uk.ui.windows.getShared('CDL008Cancel')) {
    //                         __viewContext.vm.clickCounter.clickLinkGrid = false;
    //                         return;
    //                     }
    //                     //view all code of selected item
    //                     var output = nts.uk.ui.windows.getShared('outputCDL008');
    //                     if (output != "") {
    //                         codeName = _.find(data194, (item: any) => {
    //                             return item.id == output;
    //                         });
    //                         self.updateCodeName(self.rowId(), self.attendenceId, codeName.name, codeName.code, self.selectedCode());
    //                     } else {
    //                         self.updateCodeName(self.rowId(), self.attendenceId, getText("KDW003_82"), "", self.selectedCode());
    //                     }
    //                 })
    //                 break;
    //             case 199:
    //                 //CDL008
    //                 let dateCon = selfParent.displayFormat() === 1 ? moment(selfParent.selectedDate()) : moment(__viewContext.vm.actualTimeDats()[0].endDate).utc().toISOString();
    //
    //                 let param5 = {
    //                     typeDialog: 5,
    //                     param: {
    //                         date: dateCon
    //                     }
    //                 }
    //                 let data5: any;
    //                 let dfd5 = $.Deferred();
    //                 // service.findAllCodeName(param5).done((data: any) => {
    //                 //     data5 = data;
    //                 //     codeName = _.find(data5, (item: any) => {
    //                 //         return item.code == self.selectedCode();
    //                 //     });
    //                 //     setShared('inputCDL008', {
    //                 //         selectedCodes: codeName == undefined ? "" : codeName.id,
    //                 //         baseDate: dateCon,
    //                 //         isMultiple: false,
    //                 //         selectedSystemType: 2,
    //                 //         isrestrictionOfReferenceRange: true,
    //                 //         showNoSelection: false,
    //                 //         isShowBaseDate: false
    //                 //     }, true);
    //                     dfd5.resolve()
    //                 // });
    //                 dfd5.promise();
    //                 modal('com', '/view/cdl/008/a/index.xhtml').onClosed(function(): any {
    //                     // Check is cancel.
    //                     if (nts.uk.ui.windows.getShared('CDL008Cancel')) {
    //                         __viewContext.vm.clickCounter.clickLinkGrid = false;
    //                         return;
    //                     }
    //                     //view all code of selected item
    //                     var output = nts.uk.ui.windows.getShared('outputCDL008');
    //                     if (output != "") {
    //                         codeName = _.find(data5, (item: any) => {
    //                             return item.id == output;
    //                         });
    //                         self.updateCodeName(self.rowId(), self.attendenceId, codeName.name, codeName.code, self.selectedCode());
    //                     } else {
    //                         self.updateCodeName(self.rowId(), self.attendenceId, getText("KDW003_82"), "", self.selectedCode());
    //                     }
    //                 })
    //                 break;
    //             case 195:
    //             case 200:
    //                 //KCP002
    //                 let dfd6 = $.Deferred();
    //                 setShared('inputCDL003', {
    //                     selectedCodes: self.selectedCode(),
    //                     showNoSelection: false,
    //                     isMultiple: false
    //                 }, true);
    //
    //                 modal('com', '/view/cdl/003/a/index.xhtml').onClosed(function(): any {
    //                     //view all code of selected item
    //                     var output = nts.uk.ui.windows.getShared('outputCDL003');
    //                     if (output != undefined && output != "") {
    //                         let param6 = {
    //                             typeDialog: 6
    //                         }
    //                         // service.findAllCodeName(param6).done((data: any) => {
    //                         //     codeName = _.find(data, (item: any) => {
    //                         //         return item.code == output;
    //                         //     });
    //                         //     self.updateCodeName(self.rowId(), self.attendenceId, codeName.name, codeName.code, self.selectedCode());
    //                             dfd6.resolve()
    //                         // });
    //                     } else {
    //                         __viewContext.vm.clickCounter.clickLinkGrid = false;
    //                         if (output == "") self.updateCodeName(self.rowId(), self.attendenceId, getText("KDW003_82"), "", self.selectedCode());
    //                         dfd6.resolve()
    //                     }
    //                 })
    //                 dfd6.promise();
    //                 break;
    //             case 193:
    //                 //KCP003
    //                 let dateCon193 = selfParent.displayFormat() === 1 ? moment(selfParent.selectedDate()) : moment(__viewContext.vm.actualTimeDats()[0].startDate).utc().toISOString();
    //                 let param193 = {
    //                     typeDialog: 7,
    //                     param: {
    //                         date: dateCon193
    //                     }
    //                 }
    //                 let data193: any;
    //                 let dfd193 = $.Deferred();
    //                 // service.findAllCodeName(param193).done((data: any) => {
    //                 //     data193 = data;
    //                 //     codeName = _.find(data, (item: any) => {
    //                 //         return item.code == self.selectedCode();
    //                 //     });
    //                 //     setShared('inputCDL004', {
    //                 //         baseDate: dateCon193,
    //                 //         selectedCodes: codeName == undefined ? "" : codeName.id,
    //                 //         showNoSelection: false,
    //                 //         isMultiple: false,
    //                 //         isShowBaseDate: false
    //                 //     }, true);
    //                     dfd193.resolve();
    //                 // });
    //                 dfd193.promise();
    //                 modal('com', '/view/cdl/004/a/index.xhtml').onClosed(function(): any {
    //                     var isCancel = nts.uk.ui.windows.getShared('CDL004Cancel');
    //                     if (isCancel) {
    //                         __viewContext.vm.clickCounter.clickLinkGrid = false;
    //                         return;
    //                     }
    //                     var output = nts.uk.ui.windows.getShared('outputCDL004');
    //                     if (output != "") {
    //                         codeName = _.find(data193, (item: any) => {
    //                             return item.id == output;
    //                         });
    //                         self.updateCodeName(self.rowId(), self.attendenceId, codeName.name, codeName.code, self.selectedCode());
    //                     } else {
    //                         self.updateCodeName(self.rowId(), self.attendenceId, getText("KDW003_82"), "", self.selectedCode());
    //                     }
    //                 })
    //                 break;
    //             case 198:
    //                 //KCP003
    //                 let dateCon7 = selfParent.displayFormat() === 1 ? moment(selfParent.selectedDate()) : moment(__viewContext.vm.actualTimeDats()[0].endDate).utc().toISOString();
    //                 let param7 = {
    //                     typeDialog: 7,
    //                     param: {
    //                         date: dateCon7
    //                     }
    //                 }
    //                 let data7: any;
    //                 let dfd7 = $.Deferred();
    //                 // service.findAllCodeName(param7).done((data: any) => {
    //                 //     data7 = data;
    //                 //     codeName = _.find(data, (item: any) => {
    //                 //         return item.code == self.selectedCode();
    //                 //     });
    //                 //     setShared('inputCDL004', {
    //                 //         baseDate: dateCon7,
    //                 //         selectedCodes: codeName == undefined ? "" : codeName.id,
    //                 //         showNoSelection: false,
    //                 //         isMultiple: false,
    //                 //         isShowBaseDate: false
    //                 //     }, true);
    //                     dfd7.resolve();
    //                 // });
    //                 dfd7.promise();
    //                 modal('com', '/view/cdl/004/a/index.xhtml').onClosed(function(): any {
    //                     var isCancel = nts.uk.ui.windows.getShared('CDL004Cancel');
    //                     if (isCancel) {
    //                         __viewContext.vm.clickCounter.clickLinkGrid = false;
    //                         return;
    //                     }
    //                     var output = nts.uk.ui.windows.getShared('outputCDL004');
    //                     if (output != "") {
    //                         codeName = _.find(data7, (item: any) => {
    //                             return item.id == output;
    //                         });
    //                         self.updateCodeName(self.rowId(), self.attendenceId, codeName.name, codeName.code, self.selectedCode());
    //                     } else {
    //                         self.updateCodeName(self.rowId(), self.attendenceId, getText("KDW003_82"), "", self.selectedCode());
    //                     }
    //                 })
    //                 break;
    //             case 192:
    //             case 197:
    //                 let dfd8 = $.Deferred();
    //                 setShared('CDL002Params', {
    //                     isMultiple: false,
    //                     selectedCodes: [self.selectedCode()],
    //                     showNoSelection: false
    //                 }, true);
    //
    //                 modal('com', '/view/cdl/002/a/index.xhtml').onClosed(function(): any {
    //                     // nts.uk.ui.block.clear();
    //                     var isCancel = nts.uk.ui.windows.getShared('CDL002Cancel');
    //                     if (isCancel) {
    //                         __viewContext.vm.clickCounter.clickLinkGrid = false;
    //                         return;
    //                     }
    //                     var output = nts.uk.ui.windows.getShared('CDL002Output');
    //                     if (output != "") {
    //                         let param8 = {
    //                             typeDialog: 8,
    //                         }
    //                         // service.findAllCodeName(param8).done((data: any) => {
    //                         //     nts.uk.ui.block.clear();
    //                         //     codeName = _.find(data, (item: any) => {
    //                         //         return item.code == output;
    //                         //     });
    //                         //     self.updateCodeName(self.rowId(), self.attendenceId, codeName.name, codeName.code, self.selectedCode());
    //                             dfd8.resolve();
    //                         // });
    //                     } else {
    //                         self.updateCodeName(self.rowId(), self.attendenceId, getText("KDW003_82"), "", self.selectedCode());
    //                         dfd8.resolve();
    //                     }
    //                 })
    //                 dfd8.promise();
    //                 break;
    //
    //             case 196:
    //             case 201:
    //                 let dfd14 = $.Deferred();
    //                 setShared('CDL024', {
    //                     codeList: [self.selectedCode()],
    //                     selectMultiple: false
    //                 });
    //
    //                 modal('com', '/view/cdl/024/index.xhtml').onClosed(function(): any {
    //                     // nts.uk.ui.block.clear();
    //                     let output =  nts.uk.ui.windows.getShared("currentCodeList");
    //                     if (!output) {
    //                         __viewContext.vm.clickCounter.clickLinkGrid = false;
    //                         return;
    //                     }
    //
    //                     if (output != "") {
    //                         let param14 = {
    //                             typeDialog: 14,
    //                         }
    //                         // service.findAllCodeName(param14).done((data: any) => {
    //                         //     nts.uk.ui.block.clear();
    //                         //     codeName = _.find(data, (item: any) => {
    //                         //         return item.code == output;
    //                         //     });
    //                         //     self.updateCodeName(self.rowId(), self.attendenceId, codeName.name, codeName.code, self.selectedCode());
    //                             dfd14.resolve();
    //                         // });
    //                     } else {
    //                         self.updateCodeName(self.rowId(), self.attendenceId, getText("KDW003_82"), "", self.selectedCode());
    //                         dfd14.resolve();
    //                     }
    //                 })
    //                 dfd14.promise();
    //                 break;
    //         }
    //         nts.uk.ui.block.clear();
    //     }
    //
    //     updateCodeName(rowId: any, itemId: any, name: any, code: any, valueOld: any) {
    //         let dfd = $.Deferred();
    //         __viewContext.vm.clickCounter.clickLinkGrid = false;
    //         if (code == valueOld) {
    //             dfd.resolve();
    //         } else {
    //             nts.uk.ui.block.invisible();
    //             nts.uk.ui.block.grayout();
    //             _.remove(__viewContext.vm.workTypeNotFound, dataTemp => {
    //                 return dataTemp.columnKey == "Code" + itemId && dataTemp.rowId == rowId;
    //             });
    //             $("#anpGrid").mGrid("updateCell", rowId, "Name" + itemId, name)
    //             $("#anpGrid").mGrid("updateCell", rowId, "Code" + itemId, code);
    //         }
    //         nts.uk.ui.block.clear();
    //         return dfd.promise();
    //     }
    // }

    interface ScreenStatus {//任意期間修正の選択状態
        anyPeriodFrameCode: string; //任意集計枠コード
        cursorDirection: number; //カーソル移動方向
        displayZero: boolean; //ゼロ表示
        displayItemNumber: boolean; //項目番号表示
    }
}