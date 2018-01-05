module nts.custombinding {

    export class ComboBoxControl implements KnockoutBindingHandler {
        init = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => {
            let $element = $(element),
                access: any = valueAccessor(),
                value: KnockoutObservable<any> = _.has(access, "value") ?
                    (ko.isObservable(access.value) ? access.value
                        : ko.observable(access.value))
                    : ko.observable(undefined),
                dataSource: KnockoutObservableArray<any> = _.has(access, "dataSource") ?
                    (ko.isObservable(access.dataSource) ? access.dataSource
                        : (_.isArray(access.dataSource) ? ko.observableArray(access.dataSource)
                            : ko.observableArray([access.dataSource])))
                    : ko.observableArray([]),
                textKey: string = _.has(access, 'textKey') ? access.textKey : 'text',
                valueKey: string = _.has(access, 'valueKey') ? access.valueKey : 'value',
                visibleItemsCount: number = _.has(access, 'visibleItemsCount') ? access.visibleItemsCount : 10;

            $element.igCombo({
                dataSource: [],
                textKey: textKey,
                valueKey: valueKey,
                mode: "dropdown",
                enableClearButton: false,
                visibleItemsCount: visibleItemsCount,
                selectionChanged: function(evt, ui) {
                    if (ui.items[0]) {
                        value(ui.items[0]["data"][valueKey]);
                    }
                },
                dropDownWidth: "auto",
                dropDownOpening: function(evt, ui) {
                    $(ui.list).css('min-width', $(evt.target).width() + 'px');
                }
            });

            value.subscribe(v => {
                if ($element.data("igCombo")) {
                    $element.igCombo("value", v);
                }
            });
            value.valueHasMutated();

            dataSource.subscribe(data => {
                if ($element.data("igCombo")) {
                    $element.igCombo("option", "dataSource", _.filter(data, x => _.isObject(x)));
                }
            });
            dataSource.valueHasMutated();
        }
        update = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => { }
    }
}


ko.bindingHandlers["ntsDropDownList"] = new nts.custombinding.ComboBoxControl();