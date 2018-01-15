module nts.custombinding {
    export class SortListControl implements KnockoutBindingHandler {
        constructor() { }
        init = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
            let data = valueAccessor(),
                key = data.optionsValue,
                value = ko.observableArray([]),
                options = ko.observableArray(data.options),
                optionsSorted: KnockoutObservableArray<any> = data.optionsSorted,
                sources: Array<any> = ko.toJS(data.options);

            $.extend(data, {
                value: value,
                options: options
            });

            ko.bindingHandlers['ntsGridList'].init(element, () => data, allBindingsAccessor, viewModel, bindingContext);

            $(element).find('tbody.ui-iggrid-tablebody')
            .sortable({
                axis: "y",
                scroll: false,
                update: function(event, ui) {
                    optionsSorted.removeAll();
                    _($(event.target).find('tr'))
                        .map((x: Element) => x.attributes['data-id'])
                        .filter(x => !!x)
                        .map(x => x.value)
                        .value().forEach(x => {
                            optionsSorted.push(_.find(sources, item => item && item[key] == x));
                        });
                }
            });
        }
        update = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
            let data = valueAccessor(),
                value = ko.observable([]),
                options = ko.observableArray(data.options);

            $.extend(data, {
                value: value,
                options: options
            });

            ko.bindingHandlers['ntsGridList'].update(element, () => data, allBindingsAccessor, viewModel, bindingContext);
        }
    }
}

ko.bindingHandlers["ntsSortList"] = new nts.custombinding.SortListControl();