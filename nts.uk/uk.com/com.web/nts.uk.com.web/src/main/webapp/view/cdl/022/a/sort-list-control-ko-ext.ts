module nts.custombinding {
    export class SortListControl implements KnockoutBindingHandler {
        init = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
            let data = valueAccessor(),
                key = data.optionsValue || 'id',
                height = data.height || (data.rows || 15) * 28 + 3,
                columns = ko.unwrap(data.columns) || [
                    { headerText: 'コード', key: 'id', width: 0, hidden: true },
                    { headerText: '名称', key: 'name', width: 280 }
                ];

            $.extend(data, { value: ko.observableArray([]) });

            ko.bindingHandlers['ntsGridList'].init(element, () => data, allBindingsAccessor, viewModel, bindingContext);
            ko.bindingHandlers['ntsGridList'].update(element, () => data, allBindingsAccessor, viewModel, bindingContext);

            $(element)
                .on("iggriddatarendered", (evt, ui) => {
                    let rows = $(element).find('tbody.ui-iggrid-tablebody tr');
                    _.each(rows, (element, i) => {
                        $(element).data("item", ko.unwrap(data.options)[i]);
                    });
                })
                .find('tbody.ui-iggrid-tablebody')
                .sortable({
                    axis: "y",
                    scroll: false,
                    update: function(event, ui) {
                        setTimeout(() => {
                            let _options = [],
                                rows = $(event.target).find('tr');

                            _.each(rows, element => {
                                _options.push($(element).data("item"));
                            });

                            data.options(_options);
                        }, 0);
                    }
                });
        }
        update = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
            let unwrap = ko.unwrap(valueAccessor().options);

            $(element).igGrid("option", "dataSource", _.map(unwrap, x => x));
        }
    }
}

ko.bindingHandlers["ntsSortList"] = new nts.custombinding.SortListControl();