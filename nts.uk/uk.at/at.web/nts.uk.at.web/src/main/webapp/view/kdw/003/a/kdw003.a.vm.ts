module nts.uk.at.view.kdw003.a.viewmodel {
    import ComponentOption = kcp009.viewmodel.ComponentOption;
    import EmployeeModel = kcp009.viewmodel.EmployeeModel;
    import SystemType = kcp009.viewmodel.SystemType;
    export class ScreenModel {

        dateRanger: KnockoutObservable<any>;
        displayFormatOptions: KnockoutObservableArray<any>;
        displayFormat: KnockoutObservable<number>;
        ccg001: any;
        lstEmployee: KnockoutObservableArray<any>;
        baseDate: KnockoutObservable<any>;
        listComponentOption: ComponentOption;
        selectedEmployee: KnockoutObservable<any>;
        headersGrid: KnockoutObservableArray<any>;
        sheetsGrid: KnockoutObservableArray<any>;
        fixColGrid: KnockoutObservableArray<any>;
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
                width: '160px',
                ntsControl: 'Label'
            },
            {
                headerText: nts.uk.resource.getText("KDW003_42"),
                key: 'sign',
                dataType: 'boolean',
                width: '35px',
                ntsControl: 'Checkbox'
            }
        ];;
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
                key: 'employeeId',
                dataType: 'String',
                width: '180px',
                ntsControl: 'Label'
            },
            {
                headerText: nts.uk.resource.getText("KDW003_33"),
                key: 'employeeName',
                dataType: 'String',
                width: '180px',
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
                key: 'employeeId',
                dataType: 'String',
                width: '180px',
                ntsControl: 'Label'
            },
            {
                headerText: nts.uk.resource.getText("KDW003_33"),
                key: 'employeeName',
                dataType: 'String',
                width: '180px',
                ntsControl: 'Label'
            },
            {
                headerText: nts.uk.resource.getText("KDW003_41"),
                key: 'date',
                dataType: 'String',
                width: '160px',
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
                columnKey: 'employeeId',
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
                columnKey: 'employeeId',
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
            self.lstEmployee = ko.observableArray([]);
            self.selectedEmployee = ko.observable(null);
            self.dateRanger = ko.observable({
                startDate: '2016-09-13',
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
            self.displayFormat = ko.observable(0);
            self.headersGrid = ko.observableArray(self.dateModeHeader);
            self.fixColGrid = ko.observableArray(self.dateModeFixCol);
            self.sheetsGrid = ko.observableArray([]);
        }

        reloadKcp009() {
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

            //            var gridData: IGridData = {
            //                id: 0,
            //                code: 0,
            //                name: 'ko',
            //                salary: 10
            //            }
            var data = [];
            //            data.push(gridData);
            //            gridData = {
            //                id: 1,
            //                code: 1,
            //                name: 'mot',
            //                salary: 100
            //            }
            //            data.push(gridData);
            for (let i = 0; i < 31; i++) {
                data.push({
                    id: i,
                    state: '',
                    error: 'ER/AL',
                    date: moment().add(i, 'd').locale('ja').format("MM/DD(dddd)"),
                    sign: true,
                    employeeId: '1234567890AE',
                    employeeName: 'abc'
                });
            }
            var headerColors = [];

            headerColors.push({
                key: '',
                color: ''
            });
            $("#grid2").ntsGrid({
                width: '500px',
                height: '200px',
                dataSource: ko.toJS(data),
                primaryKey: 'id',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: 'right',
                autoFitWindow: true,
                preventEditInError: false,
                // avgRowHeight: 36,
                //                autoAdjustHeight: false,
                //                adjustVirtualHeights: true,
                columns: self.headersGrid(),
                features: [
                    //                                { name: 'Sorting', type: 'local' },
                    { name: 'Resizing' },
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
                ],
                ntsFeatures: [
                    { name: 'CopyPaste' },
                    { name: 'CellEdit' },
                    {
                        name: 'CellState',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        state: 'state',
                        states: cellStates
                    },
                    {
                        name: 'HeaderStyles',
                        columns: headerColors
                    },
                    {
                        name: "Sheet",
                        initialDisplay: "1",
                        sheets: self.sheetsGrid()
                    },
                ],
                ntsControls: [{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true }]
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
            service.startScreen(param).done((data) => {
                self.lstEmployee(data.lstEmployee);
                _.forEach(data.lstControlDisplayItem.lstHeader, (header) => {
                    self.headersGrid.push(header);
                });
                self.sheetsGrid(data.lstControlDisplayItem.lstSheet);
                dfd.resolve();
            });
            return dfd.promise();
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