module nts.uk.at.view.kdw003.a.viewmodel {
    export class ScreenModel {

        dateRanger: KnockoutObservable<any>;
        displayFormatOptions: KnockoutObservableArray<any>;
        displayFormat: KnockoutObservable<number>;
        ccg001: any;
        lstEmployee: KnockoutObservableArray<any>;
        baseDate: KnockoutObservable<any>;

        constructor() {
            var self = this;
            self.dateRanger = ko.observable({
                startDate: '2000/01/01',
                endDate: '2000/01/31'
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
            self.baseDate = ko.observable(new Date());
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
                onSearchAllClicked: function(dataList) {
                    self.lstEmployee(dataList);
                },
                onSearchOnlyClicked: function(data) {
                    var dataEmployee = [];
                    dataEmployee.push(data);
                    self.lstEmployee(dataEmployee);
                },
                onSearchOfWorkplaceClicked: function(dataList) {
                    self.lstEmployee(dataList);
                },

                onSearchWorkplaceChildClicked: function(dataList) {
                    self.lstEmployee(dataList);
                },
                onApplyEmployee: function(dataEmployee) {
                    self.lstEmployee(dataEmployee);
                }

            }
        }

        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();

            var header: IHeaderGrid = {
                headerText: 'id',
                key: 'id',
                dataType: 'number',
                width: '50px',
                ntsControl: 'Label'
            };
            var headers = [];
            headers.push(header);
            header= {
                headerText: 'code',
                key: 'code',
                dataType: 'number',
                width: '50px',
                ntsControl: 'Label'
            };
            headers.push(header);
            header = {
                headerText: 'name',
                key: 'name',
                dataType: 'string',
                width: '100px'
            };
            headers.push(header);
            header = {
                headerText: 'salary',
                key: 'salary',
                dataType: 'number',
                width: '100px'
            };
            headers.push(header);

            var fixedColumn: IFixedColumn = {
                columnKey: 'code',
                isFixed: true
            }
            var fixedColumns = [];
            fixedColumns.push(fixedColumn);
            fixedColumn = {
                columnKey: 'id',
                isFixed: true
            }
            fixedColumns.push(fixedColumn);

            var summary: ISummaryColumn = {
                columnKey: 'salary',
                allowSummaries: true
            }
            var summaries = [];
            summaries.push(summary);
//            summary = {
//                columnKey: 'id',
//                allowSummaries: false,
//                summaryOperands: ''
//            };
//            summaries.push(summary);
            summary = {
                columnKey: 'code',
                allowSummaries: false
            };
            summaries.push(summary);
            summary = {
                columnKey: 'name',
                allowSummaries: false
            };
            summaries.push(summary);
            _.forEach(summaries, (data, key) => {
                if (data.allowSummaries) {
                    summaries[key].summaryOperands = [{
                        rowDisplayLabel: "",
                        type: "custom",
                        summaryCalculator: $.proxy(totalNumber, this),
                        order: 0
                    }];
                }
            });
            summaries.unshift({
                columnKey: 'id', allowSummaries: true,
                summaryOperands: [{ type: "custom", order: 0, summaryCalculator: function() { return "合計"; } }]
            });

            var cellState: ICellState = {
                rowId: 0,
                columnKey: "salary",
                state: [nts.uk.ui.jqueryExtentions.ntsGrid.color.Error, nts.uk.ui.jqueryExtentions.ntsGrid.color.Alarm]
            }
            var cellStates = [];
            cellStates.push(cellState);
            cellState = {
                rowId: 2,
                columnKey: "salary",
                state: [nts.uk.ui.jqueryExtentions.ntsGrid.color.Error, nts.uk.ui.jqueryExtentions.ntsGrid.color.Alarm]
            }
            cellStates.push(cellState);

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
            for(var i=0; i<4; i++){
                data.push({
                    id: i,
                    code: i,
                    name: 'mot' + i,
                    salary: Number(i + "00")
                });
            }

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
                columns: headers,
                features: [
                    //                                { name: 'Sorting', type: 'local' },
                    { name: 'Resizing' },
//                    { name: 'MultiColumnHeaders' },
                    //                                        { name: "Responsive",
                    //                                            enableVerticalRendering: true
                    //                                        },
                    {
                        name: 'Paging',
                        pageSize: 10,
                        currentPageIndex: 0
                    },
                    {
                        name: 'ColumnFixing', fixingDirection: 'left',
                        //                                            syncRowHeights: true,
                        showFixButtons: false,
                        columnSettings: fixedColumns
                    },
                    { name: 'Summaries', 
                                          showSummariesButton: false,
                                          showDropDownButton: false,
                                          columnSettings: summaries,
                                          resultTemplate: '{1}'
                    }
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
//                    { name: "Sheet", 
//                      initialDisplay: "sheet1",
//                      sheets: [ 
//                                { name: "sheet1", text: "Sheet 1", columns: ["name", "salary"] }
//                              ]
//                    },
                ],
                ntsControls: []
            });
            dfd.resolve();
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