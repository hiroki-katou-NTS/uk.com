class NtsSortableBindingHandler implements KnockoutBindingHandler {
    init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        let $element = $(element),
            accessor = valueAccessor();

        $element.sortable({
            update: function(event, ui: any) {
                let sortData = [];
                _.each(ui.item.parent().children(), x => sortData.push($(x).data('json')));
                _.remove(sortData, x => !x);
                accessor.data(sortData);
            }
        });

        // rebind data to view if data has change
        accessor.data.subscribe(data => {
            let $item = $element.children();
            if (!$item.data('id')) {
                console.error('Template item must be required [data-id] for sortable.');
            }
            _.each($item, (d, i) => {
                let _data = data,
                    exist = _.find(data, x => x['id'] == $(d).data('id'));
                debugger;
                if (exist) {
                    $(d).data('json', exist);
                } else {
                    $(d).remove();
                }
            });
        });

        // call data subscribe for first run time
        accessor.data.valueHasMutated();
    }

    update(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
    }
}

ko.bindingHandlers["ntsSorable"] = new NtsSortableBindingHandler();