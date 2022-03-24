module nts.uk.at.view.kfp002.a.viewmodel {

    const DATA_KEY = "KFP002ScreenData";
    const DEFAULT_WIDTH = "100px";
    const DEFAULT_HEADER_COLOR = "#6A6A6A";
    const FIXED_COLUMNS = [
        { key: 'id', headerText: 'ID', dataType: 'string', width: '0px', hidden: true},
        { key: 'code', headerText: nts.uk.resource.getText('KFP002_9'), dataType: 'string', width: '140px', ntsControl: "Label"},
        { key: 'name', headerText: nts.uk.resource.getText('KFP002_10'), dataType: 'string', width: '140px', ntsControl: "Label"}
    ];
    const FIXED_HEADER_COLORS = [
        { key: 'id', colors: [DEFAULT_HEADER_COLOR]},
        { key: 'code', colors: [DEFAULT_HEADER_COLOR]},
        { key: 'name', colors: [DEFAULT_HEADER_COLOR]}
    ];
    const FIXED_SUMMARY_COLUMN = [{columnKey: 'code', allowSummaries: true, summaryCalculator: '合計'}];

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
        cellStates: KnockoutObservableArray<any>;
        headerColors: KnockoutObservableArray<{key: string, colors: Array<string>}>;
        summaryColumns: KnockoutObservableArray<any>;

        cursorDirection: KnockoutObservable<number>; //カーソル移動方向
        displayZero: KnockoutObservable<boolean>; //ゼロ表示
        displayItemNumber: KnockoutObservable<boolean>; //項目番号表示
        selectedFrameCode: KnockoutObservable<string>;

        ccg001: any;
        baseDate: KnockoutObservable<any>;
        lstEmployee: KnockoutObservableArray<any>;

        aggregateFrames: KnockoutObservableArray<any>;
        cursorMoveDirections: KnockoutObservableArray<any>;
        legendOptions: any;

        formatCode: KnockoutObservable<string>;
        displayFormat: KnockoutObservable<any>;
        savedColumnWidths: KnockoutObservableArray<any>;

        isStartScreen: KnockoutObservable<any>;
        isEnableRegister: KnockoutObservable<boolean>;
        
        constructor() {
            super();
            let self = this;
            self.dataSource = ko.observableArray([]);
            self.columns = ko.observableArray([]);
            self.sheets = ko.observableArray([]);
            self.headerColors = ko.observableArray([]);
            self.cellStates = ko.observableArray([]);
            self.summaryColumns = ko.observableArray([]);

            self.baseDate = ko.observable(new Date().toISOString());
            self.lstEmployee = ko.observableArray([]);

            self.selectedFrameCode = ko.observable(null);
            self.cursorDirection = ko.observable(0);
            self.displayZero = ko.observable(false);
            self.displayItemNumber = ko.observable(false);

            self.aggregateFrames = ko.observableArray([]);
            self.legendOptions = {
                items: [
                    { colorCode: '#BFC5FF', labelText: self.$i18n("KFP002_6") },
                    { colorCode: '#C1E6FE', labelText: self.$i18n("KFP002_7") },
                    { colorCode: '#F9D4A9', labelText: self.$i18n("KFP002_8") }
                ]
            };
            self.cursorMoveDirections = ko.observableArray([
                { code: 0, name: "縦" },
                { code: 1, name: "横" }
            ]);
            $("#setting-content").ntsPopup({
                trigger: "#A2_5",
                position: {
                    my: "left top",
                    at: "left bottom",
                    of: "#A2_5"
                },
                showOnStart: false,
                dismissible: false
            });

            $("body").click((evt) => {
                if (evt.target.id != "A2_5"
                    && evt.target.parentElement.id != "A2_5"
                    && $(evt.target).closest("#setting-content").length == 0
                    && ($(evt.target).closest("div[dropdown-for='cbCursorMoveDirection']").length == 0 || $(evt.target).closest("div[dropdown-for='A2_1']").length > 0)) {
                    $("#setting-content").ntsPopup("hide");
                }
            });

            self.formatCode = ko.observable(null);
            self.displayFormat = ko.observable(null);
            self.savedColumnWidths = ko.observableArray([]);

            self.isStartScreen = ko.observable(true);
            self.isEnableRegister = ko.observable(false);

            self.selectedFrameCode.subscribe(val => {
                if (!self.isStartScreen()) {
                    nts.uk.characteristics.save(DATA_KEY, {
                        anyPeriodFrameCode: val,
                        cursorDirection: self.cursorDirection(),
                        displayZero: self.displayZero(),
                        displayItemNumber: self.displayItemNumber(),
                        employees: self.lstEmployee()
                    });
                    if (!_.isEmpty(self.lstEmployee()) && self.displayFormat()) {
                        self.getData();
                    }
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
                if (!self.isStartScreen()) {
                    nts.uk.characteristics.save(DATA_KEY, {
                        anyPeriodFrameCode: self.selectedFrameCode(),
                        cursorDirection: value,
                        displayZero: self.displayZero(),
                        displayItemNumber: self.displayItemNumber()
                        employees: self.lstEmployee()
                    });
                }
            });

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
                if (!self.isStartScreen()) {
                    nts.uk.characteristics.save(DATA_KEY, {
                        anyPeriodFrameCode: self.selectedFrameCode(),
                        cursorDirection: self.cursorDirection(),
                        displayZero: self.displayZero(),
                        displayItemNumber: val,
                        employees: self.lstEmployee()
                    });
                }
            });

            self.displayZero.subscribe(val => {
                if ($("#anpGrid").data('mGrid')) {
                    if (val) {
                        $("#anpGrid").mGrid("hideZero", false);
                    } else {
                        $("#anpGrid").mGrid("hideZero", true);
                    }
                }
                if (!self.isStartScreen()) {
                    nts.uk.characteristics.save(DATA_KEY, {
                        anyPeriodFrameCode: self.selectedFrameCode(),
                        cursorDirection: self.cursorDirection(),
                        displayZero: val,
                        displayItemNumber: self.displayItemNumber(),
                        employees: self.lstEmployee()
                    });
                }
            });

            self.lstEmployee.subscribe(val => {
                if (!_.isEmpty(val) && self.displayFormat()) {
                    self.getData();
                }
                if (!self.isStartScreen()) {
                    nts.uk.characteristics.save(DATA_KEY, {
                        anyPeriodFrameCode: self.selectedFrameCode(),
                        cursorDirection: self.cursorDirection(),
                        displayZero: self.displayZero(),
                        displayItemNumber: self.displayItemNumber(),
                        employees: val
                    });
                }
            });
        }

        created() {
            const self = this;
            self.initCcg001();
            localStorage.removeItem(window.location.href + '/anpGrid');
            self.$blockui('show');
            self.$ajax(Paths.getFrames).done(frames => {
                frames.forEach((f: any) => {
                    f.optionalAggrName = f.aggrFrameCode + "：" + f.optionalAggrName;
                });
                $.when(self.aggregateFrames(_.sortBy(frames, ["aggrFrameCode"]))).then(() => {
                    self.isStartScreen(false);
                    nts.uk.characteristics.restore(DATA_KEY).done((cacheData: ScreenStatus) => {
                        self.selectedFrameCode(cacheData ? cacheData.anyPeriodFrameCode : null);
                        self.cursorDirection(cacheData ? cacheData.cursorDirection : 0);
                        self.displayZero(cacheData ? cacheData.displayZero : false);
                        self.displayItemNumber(cacheData ? cacheData.displayItemNumber : false);
                        self.lstEmployee(cacheData ? cacheData.employees : []);
                    });
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
                        if (!_.isEmpty(self.lstEmployee())) {
                            self.getData();
                        } else {
                            self.dataSource([]);
                            self.loadGrid();
                        }
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
                self.headerColors(_.cloneDeep(FIXED_HEADER_COLORS));
                self.summaryColumns(_.cloneDeep(FIXED_SUMMARY_COLUMN));

                // mapping
                _.sortBy(displayFormat.formatSetting.sheetSettings, ["sheetNo"]).forEach((sheet: any, index: number) => {
                    const items = _.sortBy(sheet.listDisplayTimeItem, ['displayOrder']);
                    const tmp: Array<any> = [];
                    items.forEach((item1: any) => {
                        displayFormat.items.forEach((item2: any) => {
                            if (item1.itemDaily == item2.attendanceItemId) {
                                let key = "C" + String(item2.attendanceItemId);
                                tmp.push(key);
                                if (!_.find(self.columns(), c => c.key == key)) {
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
                                            if (monthlyItem.monthlyAttendanceAtr == 4) {
                                                const constraint = nts.uk.ui.validation.getConstraint(column.constraint.primitiveValue);
                                                column.constraint = {
                                                    cDisplayType: "Currency",
                                                    min: constraint.min,
                                                    max: constraint.max
                                                };
                                                // column.columnCssClass = "currency-symbol";
                                            }
                                        } else {
                                            if (monthlyItem.monthlyAttendanceAtr == 1) {
                                                column.constraint = {
                                                    cDisplayType: "Clock",
                                                    min: "0:00",
                                                    max: "999:59"
                                                };
                                            } else if (monthlyItem.monthlyAttendanceAtr == 2) {
                                                column.constraint = {
                                                    cDisplayType: "Integer",
                                                    min: "0",
                                                    max: "99"
                                                };
                                            } else if (monthlyItem.monthlyAttendanceAtr == 3) {
                                                column.constraint = {
                                                    cDisplayType: "HalfInt",
                                                    min: "0",
                                                    max: "99.5"
                                                };
                                            } else if (monthlyItem.monthlyAttendanceAtr == 4) {
                                                column.constraint = {
                                                    cDisplayType: "Currency",
                                                    min: "0",
                                                    max: "999999999"
                                                };
                                                // column.columnCssClass = "currency-symbol";
                                            }
                                        }
                                        self.summaryColumns.push({
                                            columnKey: key,
                                            allowSummaries: true,
                                            summaryCalculator: monthlyItem.monthlyAttendanceAtr == 1 ? 'Time' : 'Number',
                                            formatter: monthlyItem.monthlyAttendanceAtr == 4 ? 'Currency' : ''
                                        });
                                    }
                                    self.columns.push(column);
                                    const monthlyControl = _.find(displayFormat.monthlyItemControls, (i: any) => i.itemMonthlyId == item2.attendanceItemId);
                                    if (monthlyControl && monthlyControl.headerBgColorOfMonthlyPer) {
                                        self.headerColors.push({
                                            key: key,
                                            colors: [monthlyControl.headerBgColorOfMonthlyPer]
                                        });
                                    } else {
                                        self.headerColors.push({
                                            key: key,
                                            colors: [DEFAULT_HEADER_COLOR]
                                        });
                                    }
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
                        if (monthlyItem && monthlyItem.monthlyAttendanceAtr == 1) {
                            value = nts.uk.time.format.byId("Time_Short_HM", value);
                        }
                        tmp["C" + String(item.itemId)] = value;
                    });
                    tmpDataSource.push(tmp);
                });
                self.dataSource(_.sortBy(tmpDataSource, ["code"]));
                self.cellStates([]);
                self.displayFormat().monthlyItems.forEach((monthlyItem: any) => {
                    tmpDataSource.forEach(row => {
                        if (monthlyItem.userCanUpdateAtr == 0) {
                            self.cellStates.push({
                                columnKey: "C" + String(monthlyItem.attendanceItemId),
                                rowId: row.id,
                                state: [nts.uk.ui.mgrid.color.Disable]
                            });
                        }
                    });
                });
                data.editStates.forEach((state: any) => {
                    const existedState = _.findIndex(self.cellStates(), s => s.columnKey == "C" + String(state.itemId) && s.rowId == state.employeeId);
                    if (existedState >= 0) {
                        self.cellStates()[existedState].state.push(state.editState == 0 ? nts.uk.ui.mgrid.color.ManualEditTarget : nts.uk.ui.mgrid.color.ManualEditOther);
                    } else {
                        self.cellStates.push({
                            columnKey: "C" + String(state.itemId),
                            rowId: state.employeeId,
                            state: state.editState == 0 ? [nts.uk.ui.mgrid.color.ManualEditTarget] : [nts.uk.ui.mgrid.color.ManualEditOther]
                        });
                    }
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
                    vm.$blockui('show');
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
                    displayFormat: 0
                };
            nts.uk.ui.windows.setShared("CDL027Params", param);
            self.$window.modal('com',"/view/cdl/027/a/index.xhtml");
        }

        getPrimitive(primitive: number): string {
            switch (primitive) {
                case 1:
                case 22: return "AttendanceTime";
                case 2: return "AttendanceTimeOfExistMinus";
                case 3: return "WorkTimes";
                case 12: return "BreakTimeGoOutTimes";
                case 15: return "TimeWithDayAttr";
                case 16: return "AttendanceTimeMonth";
                case 17: return "AttendanceTimeMonthWithMinus";
                case 18: //return "AttendanceDaysMonth";
                case 19: return "AttendanceDaysMonth"; //return "AttendanceTimesMonth";
                case 20: return "OverTime";
                case 23: return "MonthlyDays";
                case 26: return "AttendanceRate";
                case 27: return "YearlyDays";
                case 28: return "AnnualLeaveGrantDay";
                case 29: return "AnnualLeaveUsedDayNumber";
                case 30: return "AnnualLeaveRemainingDayNumber";
                case 31: return "AnnualLeaveRemainingTime";
                case 32:
                case 34:
                case 43: return "UsedTimes";
                case 33: return "RemainingTimes";
                case 35: return "LeaveUsedTime";
                case 36: return "LeaveRemainingTime";
                case 37: return "ReserveLeaveGrantDayNumber";
                case 38:
                case 46: return "ReserveLeaveUsedDayNumber";
                case 39:
                case 47:
                case 50: return "ReserveLeaveRemainingDayNumber";
                case 40: return "TimeOfRemain";
                case 41: return "DayNumberOfRemain";
                case 42: return "LeaveGrantDayNumber";
                case 44: return "TimeOfUse";
                case 45: return "DayNumberOfUse";
                case 48: return "UsedMinutes";
                case 49: return "RemainingMinutes";
                case 51: return "AttendanceDaysMonthToTal";
                case 52:
                case 53: return "RemainDataDaysMonth";
                case 54: return "AnyItemAmount";
                case 55: return "AnyAmountMonth";
                case 56: return "AnyItemTime";
                case 57: return "AnyTimeMonth";
                case 58: return "AnyItemTimes";
                case 59: return "AnyTimesMonth";
                case 65: return "AttendanceAmountMonth";
                case 66: return "OrderNumberMonthly";
                case 67: return "OrderAmountMonthly";
                case 69: return "AttendanceAmountDaily";
                case 70: return "WorkingHoursUnitPrice";
                case 72: return "LaborContractTime";
                case 73: return "SuppNumValue";
                default: return null;
            }
        }

        insertUpdate() {
            let self = this,
                errorGrid: any = $("#anpGrid").mGrid("errors"),
                dataChange: any = _.uniqWith($("#anpGrid").mGrid("updatedCells", true),  _.isEqual);

            if ((errorGrid == undefined || errorGrid.length == 0) && _.size(dataChange) > 0) {
                let dataUpdate: any = {
                        frameCode: self.selectedFrameCode(),
                        items: {}
                    };

                let groupedDataChange = self.groupBy(dataChange, 'rowId');
                for (var empId in groupedDataChange){
                    const cells = groupedDataChange[empId];
                    dataUpdate.items[empId] = cells.map((c: any) => {
                        const monthlyItem = _.find(self.displayFormat().monthlyItems, (mi: any) => mi.attendanceItemId == Number(c.columnKey.substring(1)));
                        let valueType = 1; // time
                        if (monthlyItem.monthlyAttendanceAtr == 2) {  // count
                            if (monthlyItem.primitive == 59 || monthlyItem.primitive == 58 || monthlyItem.primitive == 18 || monthlyItem.primitive == 19)
                                valueType = 8;
                            else
                                valueType = 7;
                        }
                        if (monthlyItem.monthlyAttendanceAtr == 3) valueType = 12; // days
                        if (monthlyItem.monthlyAttendanceAtr == 4) valueType = monthlyItem.primitive == 65 ? 17 : 16; // amount
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
                    // self.isEnableRegister(false);
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
					$("#anpGrid").mGrid("setState", row.employeeId, String(item), [nts.uk.ui.mgrid.color.Calculation]);
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

        initCcg001() {
            let self = this;
            self.ccg001 = {
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
                    self.lstEmployee(dataList.listEmployee.map((data: any) => {
                        return {
                            id: data.employeeId,
                            code: data.employeeCode,
                            name: data.employeeName
                        };
                    }));
                },
            };

            $('#ccg001').ntsGroupComponent(self.ccg001);
        };


        loadGrid() {
            let self = this;
            self.isEnableRegister(false);
            let initSheet = null;
            if ($("#anpGrid").data('mGrid')) {
                initSheet = $(".mgrid-sheet-button.ui-state-active")[0].innerText;
                $("#anpGrid").mGrid("destroy");
                $("#anpGrid").off();
            }
            new nts.uk.ui.mgrid.MGrid($("#anpGrid")[0], {
                subWidth: '100px',
                subHeight: '230px',
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
                autoFitWindow: true,
                preventEditInError: false,
                errorsOnPage: true,
                hidePrimaryKey: true,
                userId: __viewContext.user.employeeId,
                getUserId: function(k) { return String(k); },
                columns: self.columns(),
                features: self.getGridFeatures(initSheet),
                ntsFeatures: [
                    { name: 'CopyPaste' },
                    { name: 'CellEdit' }
                ],
                ntsControls: []//self.getNtsControls()
            }).create();
            self.displayItemNumber.valueHasMutated();
            self.displayZero.valueHasMutated();
            self.checkEnableRegisterBinding();
            $(".mgrid-sheet-button").not(".ui-state-active").click(() => {
                self.checkEnableRegisterBinding();
            });
        }

        checkEnableRegisterBinding() {
            const self = this;
            $("#anpGrid>div, #anpGrid td, body").on("click keypress blur", () => {
                if ($("#anpGrid").data('mGrid')
                    && _.size(_.uniqWith($("#anpGrid").mGrid("updatedCells", true),  _.isEqual)) > 0
                    && _.isEmpty($("#anpGrid").mGrid("errors"))) {
                    self.isEnableRegister(true);
                } else {
                    self.isEnableRegister(false);
                }
            });
        }

        getGridFeatures(initSheet: string): Array<any> {
            let self = this;
            let features: Array<any> = [
                {
                    name: 'Resizing',
                    columnSettings: [{
                        columnKey: 'id', allowResizing: false, minimumWidth: 0
                    }]
                },
                {
                    name: 'Paging',
                    pageSize: 100,
                    currentPageIndex: 0
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
                },
                {
                    name: 'Tooltip',
                    error: true
                }
            ];
            if (self.sheets().length > 0) {
                const sheet = _.find(self.sheets(), s => s.text == initSheet);
                features.push({
                    name: "Sheet",
                    initialDisplay: sheet ? sheet.name : self.sheets()[0].name,
                    sheets: self.sheets()
                });
            }
            if (self.displayFormat() && !_.isEmpty(self.displayFormat().items)) {
                features.push({
                    name: 'ColumnFixing',
                    fixingDirection: 'left',
                    showFixButtons: false,
                    columnSettings: FIXED_COLUMNS.map(c => ({ columnKey: c.key, isFixed: true }))
                });
            }
            return features;
        }
        
    }

    interface ScreenStatus {//任意期間修正の選択状態
        anyPeriodFrameCode: string; //任意集計枠コード
        cursorDirection: number; //カーソル移動方向
        displayZero: boolean; //ゼロ表示
        displayItemNumber: boolean; //項目番号表示
        employees: Array<any>;
    }
}