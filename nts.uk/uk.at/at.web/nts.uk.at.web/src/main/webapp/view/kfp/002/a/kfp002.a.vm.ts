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
                    { colorCode: '#94B7FE', labelText: self.$i18n("KFP002_6") },
                    { colorCode: '#CEE6FF', labelText: self.$i18n("KFP002_7") },
                    { colorCode: '#F69164', labelText: self.$i18n("KFP002_8") }
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
                dismissible: true
            });

            self.formatCode = ko.observable(null);
            self.displayFormat = ko.observable(null);
            self.savedColumnWidths = ko.observableArray([]);

            self.isStartScreen = ko.observable(true);
            self.isEnableRegister = ko.observable(false);

            nts.uk.characteristics.restore(DATA_KEY).done((cacheData: ScreenStatus) => {
                self.selectedFrameCode(cacheData ? cacheData.anyPeriodFrameCode : null);
                self.cursorDirection(cacheData ? cacheData.cursorDirection : 0);
                self.displayZero(cacheData ? cacheData.displayZero : false);
                self.displayItemNumber(cacheData ? cacheData.displayItemNumber : false);
            });

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
                self.headerColors(_.cloneDeep(FIXED_HEADER_COLORS));
                self.summaryColumns(_.cloneDeep(FIXED_SUMMARY_COLUMN));

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
                                } else {
                                    self.headerColors.push({
                                        key: key,
                                        colors: [DEFAULT_HEADER_COLOR]
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
                        if (monthlyItem && monthlyItem.monthlyAttendanceAtr == 1) {
                            value = nts.uk.time.format.byId("Time_Short_HM", value);
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
                    self.isEnableRegister(false);
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
            if ($("#anpGrid").data('mGrid')) {
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
                autoFitWindow: false,
                preventEditInError: false,
                hidePrimaryKey: true,
                userId: __viewContext.user.employeeId,
                getUserId: function(k) { return String(k); },
                columns: self.columns(),
                features: self.getGridFeatures(),
                ntsFeatures: [
                    { name: 'CopyPaste' },
                    { name: 'CellEdit' }
                ],
                ntsControls: []//self.getNtsControls()
            }).create();
            self.displayItemNumber.valueHasMutated();
            self.displayZero.valueHasMutated();
            $("#anpGrid td").on("click keyup", () => {
                if ($("#anpGrid").data('mGrid')
                    && _.size(_.uniqWith($("#anpGrid").mGrid("updatedCells", true),  _.isEqual)) > 0
                    && _.isEmpty($("#anpGrid").mGrid("errors"))) {
                    self.isEnableRegister(true);
                } else {
                    self.isEnableRegister(false);
                }
            });
        }

        getGridFeatures(): Array<any> {
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
        
    }

    interface ScreenStatus {//任意期間修正の選択状態
        anyPeriodFrameCode: string; //任意集計枠コード
        cursorDirection: number; //カーソル移動方向
        displayZero: boolean; //ゼロ表示
        displayItemNumber: boolean; //項目番号表示
    }
}