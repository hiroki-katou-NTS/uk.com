module nts.uk.at.view.kdw003.a.viewmodel {
    var LIST_FIX_HEADER = [
        { headerText: 'ID', key: 'id', dataType: 'String', width: '30px', ntsControl: 'Label' },
        { headerText: '状<br/>態', key: 'state', dataType: 'String', width: '30px', ntsControl: 'Label' },
        { headerText: 'ER/AL', key: 'error', dataType: 'String', width: '60px', ntsControl: 'Label' },
        { headerText: nts.uk.resource.getText("KDW003_41"), key: 'date', dataType: 'String', width: '90px', ntsControl: 'Label' },
        { headerText: nts.uk.resource.getText("KDW003_42"), key: 'sign', dataType: 'boolean', width: '35px', ntsControl: 'Checkbox' },
        { headerText: nts.uk.resource.getText("KDW003_32"), key: 'employeeCode', dataType: 'String', width: '120px', ntsControl: 'Label' },
        { headerText: nts.uk.resource.getText("KDW003_33"), key: 'employeeName', dataType: 'String', width: '120px', ntsControl: 'Label' },
    ];

    export class ScreenModel {
        //grid user setting
        cursorMoveDirections: KnockoutObservableArray<any> = ko.observableArray([
            { code: 0, name: "縦" },
            { code: 1, name: "横" }
        ]);
        selectedDirection: KnockoutObservable<any> = ko.observable(0);
        displayWhenZero: KnockoutObservable<boolean> = ko.observable(false);
        showHeaderNumber: KnockoutObservable<boolean> = ko.observable(false);
        showProfileIcon: KnockoutObservable<boolean> = ko.observable(false);
        //ccg001 component: search employee
        ccg001: any;
        //kcp009 component: employee picker
        selectedEmployee: KnockoutObservable<any> = ko.observable(null);
        lstEmployee: KnockoutObservableArray<any> = ko.observableArray([]);;
        //data grid
        displayFormatOptions: KnockoutObservableArray<any>;
        displayFormat: KnockoutObservable<number> = ko.observable(null);
        baseDate: KnockoutObservable<any>;
        headersGrid: KnockoutObservableArray<any>;
        sheetsGrid: KnockoutObservableArray<any> = ko.observableArray([]);
        fixColGrid: KnockoutObservableArray<any>;
        dailyPerfomanceData: KnockoutObservableArray<any> = ko.observableArray([]);
        cellStates: KnockoutObservableArray<any> = ko.observableArray([]);
        rowStates: KnockoutObservableArray<any> = ko.observableArray([]);
        allData: Array<any> = [];
        headerColors: KnockoutObservableArray<any> = ko.observableArray([]);
        textColors: KnockoutObservableArray<any> = ko.observableArray([]);
        lstDate: KnockoutObservableArray<any> = ko.observableArray([]);
        optionalHeader: Array<any> = [];
        employeeModeHeader: Array<any> = [LIST_FIX_HEADER[0], LIST_FIX_HEADER[1], LIST_FIX_HEADER[2], LIST_FIX_HEADER[3], LIST_FIX_HEADER[4]];
        dateModeHeader: Array<any> = [LIST_FIX_HEADER[0], LIST_FIX_HEADER[1], LIST_FIX_HEADER[2], LIST_FIX_HEADER[5], LIST_FIX_HEADER[6], LIST_FIX_HEADER[4]];
        errorModeHeader: Array<any> = [LIST_FIX_HEADER[0], LIST_FIX_HEADER[1], LIST_FIX_HEADER[2], LIST_FIX_HEADER[5], LIST_FIX_HEADER[6], LIST_FIX_HEADER[3], LIST_FIX_HEADER[4]];
        employeeModeFixCol: Array<any> = [
            { columnKey: 'id', isFixed: true },
            { columnKey: 'state', isFixed: true },
            { columnKey: 'error', isFixed: true },
            { columnKey: 'date', isFixed: true }
        ];
        dateModeFixCol: Array<any> = [
            { columnKey: 'id', isFixed: true },
            { columnKey: 'state', isFixed: true },
            { columnKey: 'error', isFixed: true },
            { columnKey: 'employeeCode', isFixed: true },
            { columnKey: 'employeeName', isFixed: true }
        ];
        errorModeFixCol: Array<any> = [
            { columnKey: 'id', isFixed: true },
            { columnKey: 'state', isFixed: true },
            { columnKey: 'error', isFixed: true },
            { columnKey: 'employeeCode', isFixed: true },
            { columnKey: 'employeeName', isFixed: true },
            { columnKey: 'date', isFixed: true }
        ];
        // date ranger component
        dateRanger: KnockoutObservable<any> = ko.observable(null);
        // date picker component
        selectedDate: KnockoutObservable<any> = ko.observable(null);

        constructor() {
            var self = this;
            self.dateRanger.subscribe((dateRange) => {
                if (dateRange) {
                    self.selectedDate(dateRange.startDate);
                    var elementDate = dateRange.startDate;
                    while (!moment(elementDate, "YYYY/MM/DD").isAfter(dateRange.endDate)) {
                        self.lstDate.push({ date: elementDate });
                        elementDate = moment(elementDate, "YYYY/MM/DD").add(1, 'd').format("YYYY/MM/DD");
                    }
                }
            });
            self.dateRanger({
                startDate: '2016/09/13',
                endDate: '2016/10/13'
            });
            self.displayFormatOptions = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("Enum_DisplayFormat_Individual") },
                { code: 1, name: nts.uk.resource.getText("Enum_DisplayFormat_ByDate") },
                { code: 2, name: nts.uk.resource.getText("Enum_DisplayFormat_ErrorAlarm") }
            ]);
            self.displayFormat.subscribe((val) => {
                if (val == 0) {
                    $("#emp-component").css("display", "block");
                    $("#cbListDate").css("display", "none");
                } else if (val == 1) {
                    $("#cbListDate").css("display", "block");
                    $("#emp-component").css("display", "none");
                } else {
                    $("#cbListDate").css("display", "none");
                    $("#emp-component").css("display", "none");
                }
            });
            self.displayFormat(0);
            self.headersGrid = ko.observableArray(self.employeeModeHeader);
            self.fixColGrid = ko.observableArray(self.employeeModeFixCol);
            //cursor move direction 
            self.selectedDirection.subscribe((value) => {
                if (value == 0) {
                    $("#dpGrid").ntsGrid("directEnter", "below");
                } else {
                    $("#dpGrid").ntsGrid("directEnter", "right");
                }
            });
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            var param = {
                dateRange: {
                    startDate: moment(self.dateRanger().startDate).utc().toISOString(),
                    endDate: moment(self.dateRanger().endDate).utc().toISOString()
                },
                baseDate: moment(self.dateRanger().endDate).utc().toISOString()
            };
            nts.uk.ui.block.invisible();
            nts.uk.ui.block.grayout();
            service.startScreen(param).done((data) => {
                self.lstEmployee(data.lstEmployee);
                self.allData = data.lstData;
                self.cellStates(data.lstCellState);
                self.selectedEmployee(self.lstEmployee()[self.lstEmployee().length - 1].id);
                self.optionalHeader = data.lstControlDisplayItem.lstHeader;
                self.sheetsGrid(data.lstControlDisplayItem.lstSheet);
                self.sheetsGrid.valueHasMutated();
                self.extractionData();
                self.loadGrid();
                self.extraction();
                nts.uk.ui.block.clear();
                dfd.resolve();
            });
            return dfd.promise();
        }

        btnSetting_Click() {
            var container = $("#setting-wrapper");
            if (container.css("display") === 'none') {
                container.css("display", "block");
            }
            $(document).mouseup(function(e) {
                // if the target of the click isn't the container nor a descendant of the container
                if (!container.is(e.target) && container.has(e.target).length === 0) {
                    container.hide();
                }
            });
        }

        extractionData() {
            var self = this;
            self.headersGrid([]);
            self.fixColGrid([]);
            if (self.displayFormat() == 0) {
                self.fixColGrid(self.employeeModeFixCol);
            } else if (self.displayFormat() == 1) {
                self.fixColGrid(self.dateModeFixCol);
            } else if (self.displayFormat() == 2) {
                self.fixColGrid(self.errorModeFixCol);
            }
            self.loadHeader(self.displayFormat());
            self.dailyPerfomanceData(self.filterData(self.displayFormat()));
        }

        extraction() {
            var self = this;
            self.extractionData();
            $("#dpGrid").ntsGrid("destroy");
            self.loadGrid();
        }

        isDisableRow(id) {
            var self = this;
            for (let i = 0; i < self.rowStates().length; i++) {
                return self.rowStates()[i].rowId == id;
            }
        }

        isDisableSign(id) {
            var self = this;
            for (let i = 0; i < self.cellStates().length; i++) {
                return self.cellStates()[i].rowId == id && self.cellStates()[i].columnKey == 'sign';
            }
        }

        signAll() {
            var self = this;
            _.forEach(self.dailyPerfomanceData(), (data) => {
                if (!self.isDisableRow(data.id)) {
                    data.sign = true;
                }
            });
            self.dailyPerfomanceData.valueHasMutated();
            $("#grid2").ntsGrid("destroy");
            self.loadGrid();
        }

        setColorWeekend() {
            var self = this;
            self.textColors([]);
            _.forEach(self.dailyPerfomanceData(), (data) => {
                if (moment(data.date, "YYYY/MM/DD").day() == 6) {
                    self.textColors.push({
                        rowId: data.id,
                        columnKey: 'date',
                        color: '#4F81BD'
                    });
                } else if (moment(data.date, "YYYY/MM/DD").day() == 0) {
                    self.textColors.push({
                        rowId: data.id,
                        columnKey: 'date',
                        color: '#e51010'
                    });
                }
            });
        }

        setHeaderColor() {
            var self = this;
            self.headerColors([]);
            _.forEach(self.headersGrid(), (header) => {
                if (header.color) {
                    self.headerColors.push({
                        key: header.key,
                        color: header.color
                    });
                }
            });
        }

        formatDate(lstData) {
            var self = this;
            return lstData.map((data) => {
                return {
                    id: data.id,
                    state: data.state,
                    error: data.error,
                    date: moment(data.date, "YYYY/MM/DD").format("MM/DD(dd)"),
                    sign: data.sign,
                    employeeId: data.employeeId,
                    employeeCode: data.employeeCode,
                    employeeName: data.employeeName
                }
            });
        }

        filterData(mode: number) {
            var self = this;
            if (mode == 0) {
                return _.filter(self.allData, (data) => { return data.employeeId == self.selectedEmployee() });
            } else if (mode == 1) {
                return _.filter(self.allData, (data) => { return data.date === moment(self.selectedDate()).format('YYYY/MM/DD') });
            } else if (mode == 2) {
                return _.filter(self.allData, (data) => { return data.error !== '' });
            }
        }

        loadKcp009() {
            let self = this;
            var kcp009Options = {
                systemReference: 1,
                isDisplayOrganizationName: true,
                employeeInputList: self.lstEmployee,
                targetBtnText: nts.uk.resource.getText("KCP009_3"),
                selectedItem: self.selectedEmployee,
                tabIndex: 1
            };
            // Load listComponent
            $('#emp-component').ntsLoadListComponent(kcp009Options);
        }

        loadGrid() {
            var self = this;
            var summary: ISummaryColumn = {
                columnKey: 'salary',
                allowSummaries: true
            }
            var summaries = [];
            var rowState = {
                rowId: 0,
                disable: true
            }
            self.setHeaderColor();
            self.setColorWeekend();
            $("#dpGrid").ntsGrid({
                width: (window.screen.availWidth - 200) + "px",
                height: '500px',
                dataSource: self.formatDate(self.dailyPerfomanceData()),
                primaryKey: 'id',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: self.selectedDirection() == 0 ? 'below' : 'right',
                autoFitWindow: false,
                preventEditInError: false,
                avgRowHeight: 20,
                autoAdjustHeight: true,
                adjustVirtualHeights: true,
                columns: self.headersGrid(),
                hidePrimaryKey: true,
                features: [
                    { name: 'Paging', pageSize: 31, currentPageIndex: 0 },
                    { name: 'ColumnFixing', fixingDirection: 'left', showFixButtons: false, columnSettings: self.fixColGrid() },
                    { name: 'Resizing', columnSettings: [{ columnKey: 'id', allowResizing: false, minimumWidth: 0 }] },
                ],
                ntsFeatures: self.createNtsFeatures(),
                ntsControls: [{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                    { name: 'Image', source: 'ui-icon ui-icon-locked', controlType: 'Image' }]
            });
        }

        createNtsFeatures() {
            var self = this;
            let lstNtsFeature = [
                { name: 'CopyPaste' },
                { name: 'CellEdit' },
                { name: 'CellState', rowId: 'rowId', columnKey: 'columnKey', state: 'state', states: self.cellStates() },
                { name: 'RowState', rows: self.rowStates() },
                { name: 'TextColor', rowId: 'rowId', columnKey: 'columnKey', color: 'color', colorsTable: self.textColors() },
                { name: 'HeaderStyles', columns: self.headerColors() }
            ];
            if (self.sheetsGrid().length > 0) {
                lstNtsFeature.push({
                    name: "Sheet",
                    initialDisplay: self.sheetsGrid()[0].name,
                    sheets: self.sheetsGrid()
                });
            }
            return lstNtsFeature;
        }

        loadHeader(mode) {
            var self = this;
            let tempList = [];
            if (mode == 0) {
                _.forEach(self.employeeModeHeader, (header) => {
                    tempList.push(header);
                });
            } else if (mode == 1) {
                _.forEach(self.dateModeHeader, (header) => {
                    tempList.push(header);
                });
            } else if (mode == 2) {
                _.forEach(self.errorModeHeader, (header) => {
                    tempList.push(header);
                });
            }
            _.forEach(self.optionalHeader, (header) => {
                tempList.push(header);
            });
            self.headersGrid(tempList);
        }
    }
}