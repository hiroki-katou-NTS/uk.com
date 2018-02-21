module nts.custombinding {
    import random = nts.uk.util.randomId;

    export class GridMultipleControl implements KnockoutBindingHandler {
        private style: string = `<style type="text/css" rel="stylesheet" id="multi_grid_style">
            .layout-multi-control {
                
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
                border-right: 0;
                height: 32px;
                min-height: 32px;
            }

            .layout-multi-control .ui-iggrid-fixedcontainer-left {
                border-right: 2px solid #cccccc !important;
                border-bottom: none;
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
                    height: "600px",
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
                        },
                        {
                            name: "Selection",
                            mode: "cell",
                            cellSelectionChanged: function(evt, ui) {
                                debugger;
                            }
                        }],
                    dataRendering: function(evt, ui) {
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
                    }
                });
        }
        update = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
            let $element = $(element),
                accessor = valueAccessor(),
                inData = ko.unwrap(accessor.inData),
                outData = ko.unwrap(accessor.outData);


            $element.igGrid("option", "columns", [
                {
                    headerText: "Product ID",
                    width: "75px",
                    key: "ProductID",
                    dataType: "number",
                    template: "<div class='checkbox' data-pid='${ProductID}'>${ProductID}</div>"
                },
                {
                    headerText: "Product Name",
                    key: "Name",
                    width: "275px",
                    dataType: "string",
                    template: "<input class='textbox' data-pid='${ProductID}'/>"
                },
                {
                    headerText: "ProductNumber",
                    key: "ProductNumber",
                    width: "275px",
                    dataType: "string",
                    template: "<div data-control='combobox' class='combobox' data-pid='${ProductID}'></div>"
                },
                {
                    headerText: "Color",
                    key: "Color",
                    width: "275px",
                    dataType: "string",
                    template: "<div data-control='combobox' class='combobox' data-pid='${ProductID}'></div>"
                },
                {
                    headerText: "StandardCost",
                    key: "StandardCost",
                    width: "275px",
                    dataType: "number",
                    template: "<div data-control='combobox' class='combobox' data-pid='${ProductID}'></div>"
                },
            ]);

            $element.igGrid("option", "dataSource", inData);

            setTimeout(() => {
                let $grid = $element.find('.ui-iggrid-table');

                $grid.igGridColumnFixing("unfixAllColumns");

                $grid.igGridColumnFixing("fixColumn", "ProductID");
                $grid.igGridColumnFixing("fixColumn", "Name");
                debugger;
            }, 0);
        }
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