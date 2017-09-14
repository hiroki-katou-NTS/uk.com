module nts.uk.at.view.kdw003.a.viewmodel {
    import ComponentOption = kcp009.viewmodel.ComponentOption;
    import EmployeeModel = kcp009.viewmodel.EmployeeModel;
    import SystemType = kcp009.viewmodel.SystemType;
    export class ScreenModel {

        dateRanger: KnockoutObservable<any> = ko.observable(null);
        displayFormatOptions: KnockoutObservableArray<any>;
        displayFormat: KnockoutObservable<number> = ko.observable(null);
        ccg001: any;
        lstEmployee: KnockoutObservableArray<any>;
        baseDate: KnockoutObservable<any>;
        listComponentOption: ComponentOption;
        selectedEmployee: KnockoutObservable<any>;
        headersGrid: KnockoutObservableArray<any>;
        sheetsGrid: KnockoutObservableArray<any>;
        fixColGrid: KnockoutObservableArray<any>;
        dailyPerfomanceData: KnockoutObservableArray<any>;
        cellStates: KnockoutObservableArray<any>;
        rowStates: KnockoutObservableArray<any>;
        allData: Array<any>;
        currentDate: KnockoutObservable<any>;
        headerColors: KnockoutObservableArray<any>;
        textColors: KnockoutObservableArray<any>;
        lstDate: KnockoutObservableArray<any>;
        selectedDate: KnockoutObservable<any>;
        optionalHeader: Array<any> = [];
        employeeModeHeader: Array<any> = [
            {
                headerText: 'ID',
                key: 'id',
                dataType: 'String',
                width: '30px',
                ntsControl: 'Label'
            },
            {
                headerText: '状<br/>態',
                key: 'state',
                dataType: 'String',
                width: '30px',
                ntsControl: 'Label'
            },
            {
                headerText: 'ER/AL',
                key: 'error',
                dataType: 'String',
                width: '60px',
                ntsControl: 'Label'
            },
            {
                headerText: nts.uk.resource.getText("KDW003_41"),
                key: 'date',
                dataType: 'String',
                width: '110px',
                ntsControl: 'Label'
            },
            {
                headerText: nts.uk.resource.getText("KDW003_42"),
                key: 'sign',
                dataType: 'boolean',
                width: '35px',
                ntsControl: 'Checkbox'
            }
        ];
        dateModeHeader: Array<any> = [
            {
                headerText: 'ID',
                key: 'id',
                dataType: 'String',
                width: '30px',
                ntsControl: 'Label'
            },
            {
                headerText: '状<br/>態',
                key: 'state',
                dataType: 'String',
                width: '30px',
                ntsControl: 'Label'
            },
            {
                headerText: 'ER/AL',
                key: 'error',
                dataType: 'String',
                width: '60px',
                ntsControl: 'Label'
            },
            {
                headerText: nts.uk.resource.getText("KDW003_32"),
                key: 'employeeCode',
                dataType: 'String',
                width: '120px',
                ntsControl: 'Label'
            },
            {
                headerText: nts.uk.resource.getText("KDW003_33"),
                key: 'employeeName',
                dataType: 'String',
                width: '120px',
                ntsControl: 'Label'
            },
            {
                headerText: nts.uk.resource.getText("KDW003_42"),
                key: 'sign',
                dataType: 'boolean',
                width: '35px',
                ntsControl: 'Checkbox'
            }
        ];
        errorModeHeader: Array<any> = [
            {
                headerText: 'ID',
                key: 'id',
                dataType: 'String',
                width: '30px',
                ntsControl: 'Label'
            },
            {
                headerText: '状<br/>態',
                key: 'state',
                dataType: 'String',
                width: '30px',
                ntsControl: 'Label'
            },
            {
                headerText: nts.uk.resource.getText("KDW003_32"),
                key: 'employeeCode',
                dataType: 'String',
                width: '120px',
                ntsControl: 'Label'
            },
            {
                headerText: nts.uk.resource.getText("KDW003_33"),
                key: 'employeeName',
                dataType: 'String',
                width: '120px',
                ntsControl: 'Label'
            },
            {
                headerText: nts.uk.resource.getText("KDW003_41"),
                key: 'date',
                dataType: 'String',
                width: '110px',
                ntsControl: 'Label'
            },
            {
                headerText: nts.uk.resource.getText("KDW003_42"),
                key: 'sign',
                dataType: 'boolean',
                width: '35px',
                ntsControl: 'Checkbox'
            }
        ];
        employeeModeFixCol: Array<any> = [
            {
                columnKey: 'id',
                isFixed: true
            },
            {
                columnKey: 'state',
                isFixed: true
            },
            {
                columnKey: 'error',
                isFixed: true
            },
            {
                columnKey: 'date',
                isFixed: true
            },
            {
                columnKey: 'sign',
                isFixed: true
            }
        ];
        dateModeFixCol: Array<any> = [
            {
                columnKey: 'id',
                isFixed: true
            },
            {
                columnKey: 'state',
                isFixed: true
            },
            {
                columnKey: 'error',
                isFixed: true
            },
            {
                columnKey: 'employeeCode',
                isFixed: true
            },
            {
                columnKey: 'employeeName',
                isFixed: true
            },
            {
                columnKey: 'sign',
                isFixed: true
            }
        ];
        errorModeFixCol: Array<any> = [
            {
                columnKey: 'id',
                isFixed: true
            },
            {
                columnKey: 'state',
                isFixed: true
            },
            {
                columnKey: 'employeeCode',
                isFixed: true
            },
            {
                columnKey: 'employeeName',
                isFixed: true
            },
            {
                columnKey: 'date',
                isFixed: true
            },
            {
                columnKey: 'sign',
                isFixed: true
            }
        ];

        constructor() {
            var self = this;
            self.lstDate = ko.observableArray([]);
            self.selectedDate = ko.observable(null);
            self.lstEmployee = ko.observableArray([]);
            self.selectedEmployee = ko.observable(null);
            self.headerColors = ko.observableArray([]);
            self.textColors = ko.observableArray([]);
            self.currentDate = ko.observable(null);
            self.dateRanger.subscribe((dateRange) => {
                if (dateRange) {
                    self.selectedDate(dateRange.startDate);
                    var elementDate = dateRange.startDate;
                    while (!moment(elementDate, "YYYY/MM/DD").isAfter(dateRange.endDate)) {
                        self.lstDate.push({date: elementDate});
                        elementDate = moment(elementDate, "YYYY/MM/DD").add(1, 'd').format("YYYY/MM/DD");
                    }
                }
            });
            self.dateRanger({
                startDate: '2016/09/13',
                endDate: '2016/10/13'
            });
            //            self.displayFormatOptions = ko.observableArray([
            //                {code: 0, name: nts.uk.resource.getText("Enum_DisplayFormat_Individual")},
            //                {code: 1, name: nts.uk.resource.getText("Enum_DisplayFormat_ByDate")},
            //                {code: 2, name: nts.uk.resource.getText("Enum_DisplayFormat_ErrorAlarm")}
            //            ]);
            self.displayFormatOptions = ko.observableArray([
                { code: 0, name: "個人別" },
                { code: 1, name: "日付別" },
                { code: 2, name: "エラー・アラーム" }
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
            self.sheetsGrid = ko.observableArray([]);
            self.allData = [];

            self.dailyPerfomanceData = ko.observableArray([]);
            self.cellStates = ko.observableArray([]);
            self.rowStates = ko.observableArray([]);
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

        extraction() {
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
            $("#grid2").ntsGrid("destroy");
            self.loadGrid();
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
            var cellState: ICellState = {
                rowId: 0,
                columnKey: "salary",
                state: [nts.uk.ui.jqueryExtentions.ntsGrid.color.Error, nts.uk.ui.jqueryExtentions.ntsGrid.color.Alarm]
            }
            var cellStates = [];
            cellStates.push({
                rowId: 2,
                columnKey: "256",
                state: [nts.uk.ui.jqueryExtentions.ntsGrid.color.Error]
            });
            cellStates.push({
                rowId: 2,
                columnKey: "257",
                state: [nts.uk.ui.jqueryExtentions.ntsGrid.color.Alarm]
            });
            self.allData[2].error = 'ER/AL';
            cellStates.push({
                rowId: 5,
                columnKey: "257",
                state: [nts.uk.ui.jqueryExtentions.ntsGrid.color.Alarm]
            });
            self.allData[5].error = 'AL';
            self.dailyPerfomanceData(self.filterData(self.displayFormat()));
            cellStates.push({
                rowId: 9,
                columnKey: "258",
                state: [nts.uk.ui.jqueryExtentions.ntsGrid.color.Disable]
            });
            self.cellStates(cellStates);
            var rowState = {
                rowId: 0,
                disable: true
            }
            let rowStates = [];
            rowStates.push(rowState);
            self.rowStates(rowStates);
            //            var gridData: IGridData = {
            //                id: 0,
            //                code: 0,
            //                name: 'ko',
            //                salary: 10
            //            }

            var headerColors = [];

            headerColors.push({
                key: '',
                color: ''
            });
            self.headerColors(headerColors);

            var textColor = {
                rowId: 1,
                columnKey: '256',
                color: '#fff'
            }
            var textColors = [];
            textColors.push(textColor);
            self.textColors(textColors);
            $("#grid2").ntsGrid({
                width: '1000px',
                height: '500px',
                dataSource: self.dailyPerfomanceData(),
                primaryKey: 'id',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: 'right',
                autoFitWindow: true,
                preventEditInError: false,
                // avgRowHeight: 36,
                autoAdjustHeight: true,
                adjustVirtualHeights: true,
                columns: self.headersGrid(),
                hidePrimaryKey: true,
                features: [
                    //                                { name: 'Sorting', type: 'local' },
                    //                    { name: 'MultiColumnHeaders' },
                    //                                        { name: "Responsive",
                    //                                            enableVerticalRendering: true
                    //                                        },
                    {
                        name: 'Paging',
                        pageSize: 31,
                        currentPageIndex: 0
                    },
                    {
                        name: 'ColumnFixing', fixingDirection: 'left',
                        //                                            syncRowHeights: true,
                        showFixButtons: false,
                        columnSettings: self.fixColGrid()
                    },
                    //                    {
                    //                        name: 'Summaries',
                    //                        showSummariesButton: false,
                    //                        showDropDownButton: false,
                    //                        columnSettings: summaries,
                    //                        resultTemplate: '{1}'
                    //                    }
                    {
                        name: 'Resizing',
                        columnSettings: [{
                            columnKey: 'id', allowResizing: false, minimumWidth: 0
                        }]
                    },
                ],
                ntsFeatures: [
                    { name: 'CopyPaste' },
                    { name: 'CellEdit' },
                    {
                        name: 'CellState',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        state: 'state',
                        states: self.cellStates()
                    },
                    {
                        name: 'RowState',
                        rows: self.rowStates()
                    },
                    {
                        name: 'TextColor',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        color: 'color',
                        colorsTable: self.textColors()
                    },
                    {
                        name: 'HeaderStyles',
                        columns: self.headerColors()
                    },
                    {
                        name: "Sheet",
                        initialDisplay: "1",
                        sheets: self.sheetsGrid()
                    },
                ],
                ntsControls: [{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                    { name: 'Image', source: 'ui-icon ui-icon-locked', controlType: 'Image' }]
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
                let id = 0;
                for (let i = 0; i < self.lstDate().length; i++) {
                    for (let j = 0; j < self.lstEmployee().length; j++) {
                        self.allData.push({
                            id: id,
                            state: '',
                            error: '',
                            date: self.lstDate()[i].date,
                            sign: false,
                            employeeId: self.lstEmployee()[j].id,
                            employeeCode: self.lstEmployee()[j].code,
                            employeeName: '日通太郎'
                        });
                        id++;
                    };
                };
                self.selectedEmployee(self.lstEmployee()[0].id);
                self.dailyPerfomanceData(self.filterData(self.displayFormat()));
                self.optionalHeader = data.lstControlDisplayItem.lstHeader;
                self.loadHeader(self.displayFormat());
                self.sheetsGrid(data.lstControlDisplayItem.lstSheet);
                self.sheetsGrid()[0].columns.push("col1");
                self.sheetsGrid()[0].columns.push("col2");
                self.sheetsGrid()[0].columns.push("col3");
                self.sheetsGrid()[0].columns.push("col4");
                self.sheetsGrid()[0].columns.push("col5");
                self.sheetsGrid.valueHasMutated();
                nts.uk.ui.block.clear();
                dfd.resolve();
            });
            return dfd.promise();
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
            tempList.push({
                headerText: 'Column1',
                key: 'col1',
                dataType: 'string',
                width: '150px',
                color: '#ffffff'
            });
            tempList.push({
                headerText: 'Column2',
                key: 'col2',
                dataType: 'string',
                width: '200px',
                color: '#ffffff'
            });
            tempList.push({
                headerText: 'Column3',
                key: 'col3',
                dataType: 'string',
                width: '100px',
                color: '#ffffff'
            });
            tempList.push({
                headerText: 'Column4',
                key: 'col4',
                dataType: 'string',
                width: '150px',
                color: '#ffffff'
            });
            tempList.push({
                headerText: 'Column5',
                key: 'col5',
                dataType: 'string',
                width: '150px',
                color: '#ffffff'
            });
            self.headersGrid(tempList);
        }
    }

    function totalNumber(data) {
        let total = 0;
        //        let currentPageIndex = $("#grid2").igGridPaging("option", "currentPageIndex");
        //        let pageSize = $("#grid2").igGridPaging("option", "pageSize");
        let currentPageIndex = 0;
        let pageSize = 10;
        let startIndex = currentPageIndex * pageSize;
        let endIndex = startIndex + pageSize;
        _.forEach(data, function(d, i) {
            if (i < startIndex || i >= endIndex) return;
            let n = parseInt(d);
            if (!isNaN(n)) total += n;
        });
        return total;
    }

    interface IHeaderGrid {
        headerText: string;
        key: string;
        dataType: string;
        width: string;
        ntsControl: string;
    }

    interface IFixedColumn {
        columnKey: string;
        isFixed: boolean;
    }

    interface ISummaryColumn {
        columnKey: string;
        allowSummaries: boolean;
        summaryOperands: string;
    }

    interface ICellState {
        rowId: number;
        columnKey: string;
        state: Array<any>;
    }

    interface IGridData {
        id: number;
        code: number;
        name: string;
        salary: number;
    }
}