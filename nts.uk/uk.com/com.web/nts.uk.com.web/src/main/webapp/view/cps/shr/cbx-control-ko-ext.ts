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
                enable: KnockoutObservable<boolean> = _.has(access, "enable") ?
                    (ko.isObservable(access.enable) ? access.enable : ko.observable(access.enable))
                    : ko.observable(true),
                columns: Array<any> = _.has(access, 'columns') ? ko.unwrap(access.columns) : [],
                textKey: string = _.has(access, 'textKey') ? access.textKey : 'text',
                valueKey: string = _.has(access, 'valueKey') ? access.valueKey : 'value',
                visibleItemsCount: number = _.has(access, 'visibleItemsCount') ? access.visibleItemsCount : 10,
                $show = $('<div>', { 'class': 'nts-toggle-dropdown', 'style': 'line-height: 29px; padding-left: 2px;' }),
                template = '';

            if (_.isArray(access.columns)) {
                template = `<div class='fixed-flex-layout'>${_.map(access.columns, (c, i) => `<div data-ntsclass='${c.class || ''}' class='${i == access.columns.length - 1 ? `fixed-flex-layout-right ${c.class}` : `fixed-flex-layout-left  ${c.class}`}'>\$\{${c.prop}\}</div>`).join('')}</div>`;
            }

            $element.igCombo({
                dataSource: [],
                textKey: textKey,
                valueKey: valueKey,
                mode: "dropdown",
                disabled: !ko.toJS(enable),
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
                        .find('[data-ntsclass]').each((i, e) => {
                            $(e).addClass($(e).data('ntsclass'));
                        });
                }
            });

            $element
                .find('.ui-igcombo-hidden-field')
                .parent()
                .append($show)
                .css('overflow', 'hidden')
                .find('.ui-igcombo-fieldholder').addClass('hidden');
            $show.on('click', () => {
                if ($element.data('igCombo')) {
                    if ($element.igCombo("dropDownOpened")) {
                        $element.igCombo('closeDropDown');
                    } else {
                        $element.igCombo('openDropDown', () => { }, true, true);
                    }
                }            });

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

            enable.subscribe(e => {
                if ($element.data('igCombo')) {
                    $element.igCombo('option', 'disabled', !e);
                }
            });
            //enable.valueHasMutated();

            $(document)
                .on('click', evt => {
                    if ($(evt.target).parents('.ui-igcombo-wrapper')[0] == $element[0]) {
                        return;
                    } else if ($(evt.target).parents('.ui-igcombo-dropdown').length == 0) {
                        if ($element.data('igCombo')) {
                            $element.igCombo("closeDropDown");
                        }
                    }
                });
        }
        update = (element: HTMLElement, valueAccessor: any, allBindingsAccessor: any, viewModel: any, bindingContext: KnockoutBindingContext) => { }
    }
}

ko.bindingHandlers["ntsDropDownList"] = new nts.custombinding.ComboBoxControl();

$(document)
    .on('keydown', (evt) => {
        if (evt.ctrlKey) {
            $('.ui-igcombo-dropdown[class*=ui-igcombo-orientation-] [data-class]')
                .each((i, e) => {
                    $(e).removeClass($(e).data('ntsclass'));
                });

            $('[data-ntsclass]').each((i, e) => {
                $(e).removeClass($(e).data('ntsclass'));
            });
        }
    }).on('keyup', (evt, close) => {
        if (evt.keyCode == 17) {
            $('.ui-igcombo-dropdown[class*=ui-igcombo-orientation-] [data-class]')
                .each((i, e) => {
                    $(e).addClass($(e).data('ntsclass'));
                });

            $('[data-ntsclass]').each((i, e) => {
                $(e).addClass($(e).data('ntsclass'));
            });
        }
    });