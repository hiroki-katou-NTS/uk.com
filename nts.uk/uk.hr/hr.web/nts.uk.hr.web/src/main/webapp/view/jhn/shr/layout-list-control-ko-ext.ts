module nts.custombinding {
    import random = nts.uk.util.randomId;
    import text = nts.uk.resource.getText;

    export class GridMultipleControl implements KnockoutBindingHandler {
        private style: string = `<style type="text/css" rel="stylesheet" id="multi_grid_style">
            .layout-multi-control {
                position: relative;
            }

            .layout-multi-control>.ui-iggrid {
                border: 1px solid #ccc;
            }
            
            .layout-multi-control .ui-igcombo-wrapper {
                display: block;
                width: initial;
                width: auto;
            }
            
            .layout-multi-control .ui-igcombo-wrapper .ui-igcombo {
                border-color: transparent;
                border-radius: 0;
            }

            .layout-multi-control .ui-iggrid .ui-iggrid-tablebody td {
                padding: 0;
            }

            .layout-multi-control .ui-iggrid>.ui-widget-header table tr th:first-child, 
            .layout-multi-control .ui-iggrid>.ui-widget-header table tr th.ui-iggrid-header:first-child,
            .layout-multi-control .ui-igcombo-wrapper .ui-igcombo .ui-igcombo-button {
                border-left: 0;
            }

            .layout-multi-control .ui-iggrid table tr td,
            .layout-multi-control .ui-iggrid table tr th {
                border-top: 0;
                border-left: 0;
                height: 32px;
                min-height: 32px;
                box-sizing: border-box;
            }

            .layout-multi-control .ui-iggrid-fixedcontainer-left {
                border-bottom: 0;
                border-right: 2px solid #ccc !important;
                box-sizing: border-box;
            }

            .layout-multi-control .ui-iggrid .ui-widget-header.ui-iggrid-toolbar {
                    width: calc(100% + 2px);
                    margin: -1px;
                    margin-bottom: 0;
                    min-height: 60px;
                    background-color: #fff;
                    border-width: 0;
                    border-bottom-width: 1px;
                    box-sizing: border-box;
                    padding: 0;
            }

            .layout-multi-control .ui-iggrid .ui-iggrid-toolbar.ui-iggrid-toolbar .ui-iggrid-results {
                float: none;
                text-align: right;
                width: calc(100% - 50px);
                display: block;
                margin: auto;
                padding-top: 35px;
                height: 75px;
                border: 1px solid #ccc;
                border-bottom-width: 0;
                border-radius: 10px 10px 0 0;
                padding-left: calc(100% - 240px);
                box-sizing: border-box;
            }

            .layout-multi-control .ui-iggrid .ui-iggrid-toolbar.ui-iggrid-toolbar .ui-iggrid-results span {
                line-height: 25px;
            }
            
            .ui-igedit-listitem {
                border-top-width: 0;
            }
            </style>`;

        private _constructor() {

        }

        private render: IRender = {
            text: (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
                let data = {
                    value: ko.observable('')
                };

                ko.bindingHandlers["ntsTextEditor"].init(element, () => data, allBindingsAccessor, viewModel, bindingContext);

                ko.bindingHandlers["ntsTextEditor"].update(element, () => data, allBindingsAccessor, viewModel, bindingContext);
            },
            numb: (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
            },
            time: (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
            },
            timewd: (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
            },
            checkbox: (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
                let data = {
                    checked: ko.observable(false),
                    enable: ko.observable(true),
                };

                ko.bindingHandlers["ntsCheckBox"].init(element, () => data, allBindingsAccessor, viewModel, bindingContext);
                ko.bindingHandlers["ntsCheckBox"].update(element, () => data, allBindingsAccessor, viewModel, bindingContext);
            },
            combobox: (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
                let data = {
                    options: ko.observableArray([
                        {
                            code: '001',
                            name: 'name 001'
                        },
                        {
                            code: '002',
                            name: 'name 002'
                        },
                        {
                            code: '003',
                            name: 'name 003'
                        },
                        {
                            code: '004',
                            name: 'name 004'
                        },
                        {
                            code: '005',
                            name: 'name 005'
                        }
                    ]),
                    optionsValue: 'code',
                    visibleItemsCount: 5,
                    value: ko.observable(),
                    optionsText: 'name',
                    editable: false,
                    enable: true,
                    columns: [
                        { prop: 'code', length: 4 },
                        { prop: 'name', length: 10 },
                    ]
                };

                ko.bindingHandlers['ntsComboBox'].init(element, () => data, allBindingsAccessor, viewModel, bindingContext);

                ko.bindingHandlers['ntsComboBox'].update(element, () => data, allBindingsAccessor, viewModel, bindingContext);
            },
            datepicker: () => {
            }
        }


        init = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
            let self = this,
                render = self.render,
                $element = $(element);

            if (element.tagName != 'DIV') {
                throw new Error('Bind this control to <div> element, plz!');
            }

            if (!element.id) {
                element.id = random().replace(/[-_]/g, '');
            }

            // add style to <head> on first run
            if (!$('#multi_grid_style').length) {
                $('head').append(self.style);
            }

            $element
                .addClass("layout-multi-control")
                .igGrid({
                    width: "100%",
                    height: "calc(100vh - 320px)",
                    avgRowHeight: "32px",
                    primaryKey: "ProductID",
                    enableHoverStyles: false,
                    features: [
                        {
                            name: "ColumnFixing",
                            showFixButtons: false
                        },
                        {
                            'name': "Sorting",
                            'type': "local"
                        }, {
                            'name': "Paging",
                            'type': "local",
                            'pageSize': 20,
                            'pageSizeList': [15, 20, 50, 100]
                        }, {
                            name: 'RowSelectors',
                            enableCheckBoxes: false,
                            enableRowNumbering: true, //this feature is not needed
                            rowSelectorColumnWidth: 100
                        },
                        {
                            name: "Selection",
                            mode: "cell",
                            cellSelectionChanged: function(evt, ui) {
                            }
                        }],
                    dataRendering: (evt, ui) => {
                        setTimeout(() => {
                            _.each($('.textbox'), m => {
                                render.text(m, valueAccessor, allBindingsAccessor, viewModel, bindingContext);
                            });
                            _.each($('.checkbox'), m => {
                                render.checkbox(m, valueAccessor, allBindingsAccessor, viewModel, bindingContext);
                            });
                            _.each($('.combobox'), m => {
                                render.combobox(m, valueAccessor, allBindingsAccessor, viewModel, bindingContext);
                            });
                        }, 0);
                    },
                    rendered: (evt, ui) => {

                    }
                })
                //Delegate before the igGrid initialization code
                .on("igcontrolcreated", function(evt, ui) {
                    //return reference to igGrid
                    let ctrls = $element
                        .find('.ui-iggrid-toolbar.ui-widget-header>.ui-iggrid-results>.ui-iggrid-pagesizelabel');

                    if (ctrls.length == 2) {
                        $(ctrls[0]).text(text('CPS003_31'));
                        $(ctrls[1]).text(text('CPS003_33'));
                    }
                });
        }
        update = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
            let $element = $(element),
                accessor = valueAccessor(),
                inData: IInData = ko.toJS(ko.unwrap(accessor.inData)),
                outData = ko.unwrap(accessor.outData),
                keys = _(inData.employees)
                    .filter((x, i) => i == 0)
                    .map(_.keys)
                    .first(),
                columns = keys ? keys
                    .map(key => {
                        return {
                            key: key,
                            headerText: key,
                            width: '100px',
                            dataType: 'string',
                            template: `<div data-pid='\${${key}}'>\${${key}}</div>`,
                        };
                    }) : [];

            $element.igGrid("option", "columns", columns);

            $element.igGrid("option", "dataSource", inData.employees);

            let $grid = $element.find('.ui-iggrid-table');

            if ($grid.data("ui-igGridColumnFixing")) {
                $grid.igGridColumnFixing("unfixAllColumns");

                if (keys.length >= 4) {
                    _.each([0, 1, 2, 3], x => $grid.igGridColumnFixing("fixColumn", keys[x]))
                }
            }
        }
    }

    interface IInData {
        employees: Array<IEmployee>;
        itemDefitions: Array<any>;
    }

    interface IEmployee {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
    }

    interface IRender {
        text(element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext);
        numb(element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext);
        time(element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext);
        timewd(element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext);
        checkbox(element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext);
        combobox(element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext);
        datepicker(element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext);
    }
}

ko.bindingHandlers["ntsGridMultiple"] = new nts.custombinding.GridMultipleControl();