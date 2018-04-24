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
                columns: Array<any> = _.has(access, 'columns') ? ko.unwrap(access.columns) : [],
                textKey: string = _.has(access, 'textKey') ? access.textKey : 'text',
                valueKey: string = _.has(access, 'valueKey') ? access.valueKey : 'value',
                visibleItemsCount: number = _.has(access, 'visibleItemsCount') ? access.visibleItemsCount : 10,
                $show = $('<div>', { 'style': 'line-height: 29px; padding-left: 2px;' }),
                template = '';

            if (_.isArray(access.columns)) {
                template = `<div class='fixed-flex-layout'>${_.map(access.columns, (c, i) => `<div data-class='${c.class}' class='${i == access.columns.length - 1 ? 'fixed-flex-layout-right' : 'fixed-flex-layout-left'}'>\$\{${c.prop}\}</div>`).join('')}</div>`;
            }

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
                itemTemplate: template,
                dropDownWidth: "auto",
                dropDownOpening: function(evt, ui) {
                    $(ui.list).css('min-width', $(evt.target).width() + 'px')
                        .find('[data-class]').each((i, e) => {
                            $(e).addClass($(e).data('class'));
                        });
                }
            });

            let $parent = $element.find('.ui-igcombo-hidden-field').parent();

            /*$parent.append($show).css('overflow', 'hidden');

            $parent.find('.ui-igcombo-fieldholder').addClass('hidden');*/

            $show.on('click', () => {
                $parent.find('.ui-igcombo-field').trigger('click');
            });

            value.subscribe(v => {
                if ($element.data("igCombo")) {
                    $element.igCombo("value", v);
                }

                let option = _.find(ko.unwrap(dataSource), t => t[valueKey] == v),
                    _template = template;

                if (option) {
                    _.each(_.keys(option), k => {
                        _template = _template.replace(`\$\{${k}\}`, option[k]);
                    });

                    $show.html(_template);
                } else {
                    $show.empty();
                }
            });
            value.valueHasMutated();

            dataSource.subscribe(data => {
                if ($element.data("igCombo")) {
                    $element.igCombo("option", "dataSource", _.filter(data, x => _.isObject(x)));
                }
            });
            dataSource.valueHasMutated();

            $(document).on('keydown', (evt) => {
                if (evt.ctrlKey) {
                    $('.ui-igcombo-dropdown.ui-igcombo-orientation-bottom [data-class]')
                        .each((i, e) => {
                            $(e).removeClass($(e).data('class'));
                        });
                }
            }).on('keyup', (evt, close) => {
                if (evt.keyCode == 17) {
                    $('.ui-igcombo-dropdown.ui-igcombo-orientation-bottom [data-class]')
                        .each((i, e) => {
                            $(e).addClass($(e).data('class'));
                        });
                }
            });
        }
        update = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => { }
    }
}

ko.bindingHandlers["ntsDropDownList"] = new nts.custombinding.ComboBoxControl();