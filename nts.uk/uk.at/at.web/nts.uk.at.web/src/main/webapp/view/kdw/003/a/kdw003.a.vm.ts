module nts.uk.at.view.kdw003.a.viewmodel {
    export interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        workplaceCode: string;
        workplaceId: string;
        workplaceName: string;
    }

    var LIST_FIX_HEADER = [
        { headerText: 'ID', key: 'id', dataType: 'String', width: '30px', ntsControl: 'Label' },
        { headerText: '状<br/>態', key: 'state', dataType: 'String', width: '30px', ntsControl: 'Label' },
        { headerText: 'ER/AL', key: 'error', dataType: 'String', width: '60px', ntsControl: 'Label' },
        { headerText: nts.uk.resource.getText("KDW003_41"), key: 'date', dataType: 'String', width: '90px', ntsControl: 'Label' },
        { headerText: nts.uk.resource.getText("KDW003_42"), key: 'sign', dataType: 'boolean', width: '35px', ntsControl: 'Checkbox' },
        { headerText: nts.uk.resource.getText("KDW003_32"), key: 'employeeCode', dataType: 'String', width: '120px', ntsControl: 'Label' },
        { headerText: nts.uk.resource.getText("KDW003_33"), key: 'employeeName', dataType: 'String', width: '190px', ntsControl: 'Label' },
        { headerText: '', key: "picture-person", dataType: "string", width: '35px', ntsControl: 'Image' }
    ];

    export class ScreenModel {
        legendOptions: any;
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
        baseDate: KnockoutObservable<Date> = ko.observable(new Date());
        //kcp009 component: employee picker
        selectedEmployee: KnockoutObservable<any> = ko.observable(null);
        lstEmployee: KnockoutObservableArray<any> = ko.observableArray([]);;
        //data grid
        displayFormatOptions: KnockoutObservableArray<any>;
        displayFormat: KnockoutObservable<number> = ko.observable(null);
        headersGrid: KnockoutObservableArray<any>;
        sheetsGrid: KnockoutObservableArray<any> = ko.observableArray([]);
        fixColGrid: KnockoutObservableArray<any>;
        dailyPerfomanceData: KnockoutObservableArray<any> = ko.observableArray([]);
        cellStates: KnockoutObservableArray<any> = ko.observableArray([]);
        rowStates: KnockoutObservableArray<any> = ko.observableArray([]);
        dpData: Array<any> = [];
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
            self.initLegendButton();
            self.initDateRanger();
            self.initDisplayFormat();
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
            // show/hide header number
            self.showHeaderNumber.subscribe((val) => {
                self.reloadGrid();
            });
            // show/hide profile icon
            self.showProfileIcon.subscribe((val) => {
                self.reloadGrid();
            });
        }

        initLegendButton() {
            var self = this;
            self.legendOptions = {
                items: [
                    { colorCode: '#94B7FE', labelText: '手修正（本人）' },
                    { colorCode: '#CEE6FF', labelText: '手修正（他人）' },
                    { colorCode: '#BFEA60', labelText: '申請反映' },
                    { colorCode: '#F69164', labelText: '計算値' },
                    { colorCode: '#FD4D4D', labelText: nts.uk.resource.getText("KDW003_44") },
                    { colorCode: '#F6F636', labelText: nts.uk.resource.getText("KDW003_45") },
                ]
            };
        }

        initDateRanger() {
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
                startDate: moment().add(-1, "M").format("YYYY/MM/DD"),
                endDate: moment().format("YYYY/MM/DD")
            });
        }

        initDisplayFormat() {
            var self = this;
            self.displayFormatOptions = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("Enum_DisplayFormat_Individual") },
                { code: 1, name: nts.uk.resource.getText("Enum_DisplayFormat_ByDate") },
                { code: 2, name: nts.uk.resource.getText("Enum_DisplayFormat_ErrorAlarm") }
            ]);
            self.displayFormat(0);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            var param = {
                dateRange: {
                    startDate: moment(self.dateRanger().startDate).utc().toISOString(),
                    endDate: moment(self.dateRanger().endDate).utc().toISOString()
                },
                lstEmployee: []
            };
            nts.uk.ui.block.invisible();
            nts.uk.ui.block.grayout();
            service.startScreen(param).done((data) => {
                self.lstEmployee(_.orderBy(data.lstEmployee, ['code'], ['asc']));
                self.receiveData(data);
                self.selectedEmployee(self.lstEmployee()[0].id);
                self.extractionData();
                self.loadGrid();
                self.extraction();
                self.initCcg001();
                self.loadCcg001();
                nts.uk.ui.block.clear();
                dfd.resolve();
            });
            return dfd.promise();
        }

        receiveData(data) {
            var self = this;
            self.dpData = data.lstData;
            self.cellStates(data.lstCellState);
            self.optionalHeader = data.lstControlDisplayItem.lstHeader;
            self.sheetsGrid(data.lstControlDisplayItem.lstSheet);
            self.sheetsGrid.valueHasMutated();
        }

        btnExtraction_Click() {
            var self = this;
            if (self.displayFormat() == 0) {
                $("#emp-component").css("display", "block");
                $("#cbListDate").css("display", "none");
            } else if (self.displayFormat() == 1) {
                $("#cbListDate").css("display", "block");
                $("#emp-component").css("display", "none");
            } else {
                $("#cbListDate").css("display", "none");
                $("#emp-component").css("display", "none");
            }
            let lstEmployee = [];
            if (self.displayFormat() === 0) {
                lstEmployee.push(_.find(self.lstEmployee(), (employee) => {
                    return employee.id === self.selectedEmployee();
                }));
            } else {
                lstEmployee = self.lstEmployee();
            }
            let param = {
                dateRange: {
                    startDate: moment(self.dateRanger().startDate).utc().toISOString(),
                    endDate: moment(self.dateRanger().endDate).utc().toISOString()
                },
                lstEmployee: lstEmployee
            };
            nts.uk.ui.block.invisible();
            nts.uk.ui.block.grayout();
            service.startScreen(param).done((data) => {
                self.receiveData(data);
                self.extraction();
                nts.uk.ui.block.clear();
            });
        }

        showErrorDialog() {
            var self = this;
            let lstEmployee = [];
            if (self.displayFormat() === 0) {
                lstEmployee.push(_.find(self.lstEmployee(), (employee) => {
                    return employee.id === self.selectedEmployee();
                }));
            } else {
                lstEmployee = self.lstEmployee();
            }
            let param = {
                dateRange: {
                    startDate: moment(self.dateRanger().startDate).utc().toISOString(),
                    endDate: moment(self.dateRanger().endDate).utc().toISOString()
                },
                lstEmployee: lstEmployee
            };
            nts.uk.ui.windows.setShared("paramToGetError", param);
            nts.uk.ui.windows.sub.modal("/view/kdw/003/b/index.xhtml").onClosed(() => {
            });
        }

        btnSetting_Click() {
            var container = $("#setting-content");
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

        btnVacationRemaining_Click() {
            var container = $("#vacationRemaining-content");
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
            $("#dpGrid").ntsGrid("destroy");
            self.extractionData();
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
                    data.sign = !data.sign;
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
                return _.filter(self.dpData, (data) => { return data.employeeId == self.selectedEmployee() });
            } else if (mode == 1) {
                return _.filter(self.dpData, (data) => { return data.date === moment(self.selectedDate()).format('YYYY/MM/DD') });
            } else if (mode == 2) {
                return _.filter(self.dpData, (data) => { return data.error !== '' });
            }
        }

        //load kcp009 component: employee picker
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

        //init ccg001
        initCcg001() {
            let self = this;
            self.ccg001 = {
                baseDate: self.baseDate,
                //Show/hide options
                isQuickSearchTab: true,
                isAdvancedSearchTab: true,
                isAllReferableEmployee: true,
                isOnlyMe: true,
                isEmployeeOfWorkplace: true,
                isEmployeeWorkplaceFollow: true,
                isMutipleCheck: true,
                isSelectAllEmployee: true,
                /**
                * @param dataList: list employee returned from component.
                * Define how to use this list employee by yourself in the function's body.
                */
                onSearchAllClicked: function(dataList: EmployeeSearchDto[]) {
                    self.lstEmployee(dataList.map((data: EmployeeSearchDto) => {
                        return {
                            id: data.employeeId,
                            code: data.employeeCode,
                            businessName: data.employeeName,
                            workplaceName: data.workplaceName,
                            depName: '',
                            isLoginUser: false
                        };
                    }));
                    self.lstEmployee(_.orderBy(self.lstEmployee(), ['code'], ['asc']));
                    self.selectedEmployee(self.lstEmployee()[0].id);
                    self.loadKcp009();
                    self.btnExtraction_Click();
                },
                onSearchOnlyClicked: function(data: EmployeeSearchDto) {
                    self.lstEmployee([]);
                    self.lstEmployee.push({
                        id: data.employeeId,
                        code: data.employeeCode,
                        businessName: data.employeeName,
                        workplaceName: data.workplaceName,
                        depName: '',
                        isLoginUser: false
                    });
                    self.lstEmployee(_.orderBy(self.lstEmployee(), ['code'], ['asc']));
                    self.selectedEmployee(self.lstEmployee()[0].id);
                    self.loadKcp009();
                    self.btnExtraction_Click();
                },
                onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
                    self.lstEmployee(dataList.map((data: EmployeeSearchDto) => {
                        return {
                            id: data.employeeId,
                            code: data.employeeCode,
                            businessName: data.employeeName,
                            workplaceName: data.workplaceName,
                            depName: '',
                            isLoginUser: false
                        };
                    }));
                    self.lstEmployee(_.orderBy(self.lstEmployee(), ['code'], ['asc']));
                    self.selectedEmployee(self.lstEmployee()[0].id);
                    self.loadKcp009();
                    self.btnExtraction_Click();
                },
                onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
                    self.lstEmployee(dataList.map((data: EmployeeSearchDto) => {
                        return {
                            id: data.employeeId,
                            code: data.employeeCode,
                            businessName: data.employeeName,
                            workplaceName: data.workplaceName,
                            depName: '',
                            isLoginUser: false
                        };
                    }));
                    self.lstEmployee(_.orderBy(self.lstEmployee(), ['code'], ['asc']));
                    self.selectedEmployee(self.lstEmployee()[0].id);
                    self.loadKcp009();
                    self.btnExtraction_Click();
                },
                onApplyEmployee: function(dataList: EmployeeSearchDto[]) {
                    self.lstEmployee(dataList.map((data: EmployeeSearchDto) => {
                        return {
                            id: data.employeeId,
                            code: data.employeeCode,
                            businessName: data.employeeName,
                            workplaceName: data.workplaceName,
                            depName: '',
                            isLoginUser: false
                        };
                    }));
                    self.selectedEmployee(self.lstEmployee()[self.lstEmployee().length - 1].id);
                    self.loadKcp009();
                    self.btnExtraction_Click();
                }
            }
        }

        //load ccg001 component: search employee
        loadCcg001() {
            var self = this;
            $('#ccg001').ntsGroupComponent(self.ccg001);
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
                    { name: 'Image', source: 'img-icon icon-people', controlType: 'Image' }]
            });
        }

        reloadGrid() {
            var self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.block.grayout();
            self.extraction();
            nts.uk.ui.block.clear();
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
                self.displayProfileIcon();
                _.forEach(self.dateModeHeader, (header) => {
                    tempList.push(header);
                });
            } else if (mode == 2) {
                _.forEach(self.errorModeHeader, (header) => {
                    tempList.push(header);
                });
            }
            self.dislayNumberHeaderText();
            _.forEach(self.optionalHeader, (header) => {
                tempList.push(header);
            });
            self.headersGrid(tempList);
        }

        displayProfileIcon() {
            var self = this;
            if (self.showProfileIcon()) {
                _.remove(self.dateModeHeader, function(header) {
                    return header.key === "picture-person";
                });
                _.remove(self.dateModeFixCol, function(header) {
                    return header.columnKey === "picture-person";
                });
                self.dateModeHeader.splice(5, 0, LIST_FIX_HEADER[7]);
                self.dateModeFixCol.push({ columnKey: 'picture-person', isFixed: true });
            } else {
                _.remove(self.dateModeHeader, function(header) {
                    return header.key === "picture-person";
                });
                _.remove(self.dateModeFixCol, function(header) {
                    return header.columnKey === "picture-person";
                });
            }
        }

        dislayNumberHeaderText() {
            var self = this;
            if (self.showHeaderNumber()) {
                self.optionalHeader.map((header) => {
                    header.headerText = header.headerText + " " + header.key;
                    return header;
                });
            } else {
                self.optionalHeader.map((header) => {
                    header.headerText = header.headerText.split(" ")[0];
                    return header;
                });
            }
        }
    }
}