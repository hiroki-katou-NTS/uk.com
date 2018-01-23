module nts.custombinding {
    import random = nts.uk.util.randomId;

    export class GridMultipleControl implements KnockoutBindingHandler {
        private style: string = `<style type="text/css" rel="stylesheet" id="multi_grid_style">
            .layout-multi-control {
                
            }

            .layout-multi-control .ui-igcombo-wrapper {
                display: block;
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
                    height: 300,
                    avgRowHeight: "35px",
                    enableHoverStyles: false,
                    features: [
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
                    key: "ProductID",
                    dataType: "number",
                    template: "<div class='checkbox'>${ProductID}</div>"
                },
                {
                    headerText: "Product Name",
                    key: "Name",
                    dataType: "string",
                    template: "<input class='textbox' />"
                },
                {
                    headerText: "ProductNumber",
                    key: "ProductNumber",
                    dataType: "string",
                    template: "<div data-control='combobox' class='combobox'></div>"
                },
                {
                    headerText: "Color",
                    key: "Color",
                    dataType: "string",
                    unbound: true,
                    template: "<div data-control='combobox' class='combobox'></div>"
                },
                {
                    headerText: "StandardCost",
                    key: "StandardCost",
                    dataType: "number",
                    unbound: true,
                    template: "<div data-control='combobox' class='combobox'></div>"
                },
            ]);

            $element.igGrid("option", "dataSource", inData);
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