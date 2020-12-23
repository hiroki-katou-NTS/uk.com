/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {
    export module tabpanel {

        type TABPANEL_PARAMS = {
            direction: 'horizontal' | 'vertical' | KnockoutObservable<'horizontal' | 'vertical'>;
            active: string | KnockoutObservable<string>;
            dataSource: TabModel[] | KnockoutObservableArray<TabModel>;
        };

        interface TabModel {
            id: string;
            icon: string;
            title: string;
            content: string;
            enable: boolean;
            visible: boolean;
        }

        @handler({
            bindingName: 'ntsTabPanel',
            validatable: true,
            virtual: false
        })
        export class TabPanelBindingHandler implements KnockoutBindingHandler {
            init(element: HTMLElement): void {
                element.classList.add('tabs-panel');
                element.removeAttribute('data-bind');

                const childs = $(element).children();

                const tablist = $('<div>', { class: 'tabs-list' });

                tablist
                    .appendTo(element);

                const wrapper = $('<div>', { class: 'tabs-content' });

                wrapper
                    .appendTo(element).append(childs);
            }

            update(element: HTMLElement, valueAccessor: () => TABPANEL_PARAMS, allBindingsAccessor: () => any, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext & { $vm: nts.uk.ui.vm.ViewModel }): void {
                const mvm = new ko.ViewModel();
                const data = valueAccessor();
                const active = ko.unwrap<string>(data.active);
                const dataSource = ko.unwrap<TabModel[]>(data.dataSource);
                const direction = ko.unwrap<'vertical' | 'horizontal'>(data.direction) || 'horizontal';

                const $tabs = $(element).find('.tabs-list').get(0);
                const $contents = $(element).find('.tabs-content').get(0);

                element.classList.remove('vertical');
                element.classList.remove('horizontal');

                element.classList.add(direction);

                if ($tabs) {
                    $tabs.innerHTML = '';

                    const contents = dataSource.map(({ content }) => content).join(', ');

                    $(element)
                        .find(contents)
                        .addClass('hidden');

                    _.each(dataSource, (tab: TabModel) => {
                        const { id, title, enable, icon, visible, content } = tab;

                        if (ko.unwrap<boolean>(visible)) {
                            const btn = $('<button>', {
                                id: id || '',
                                text: mvm.$i18n(title),
                                disabled: !ko.unwrap<boolean>(enable),
                                class: direction === 'vertical' ? 'link icon' : ''
                            })
                                .appendTo($tabs)
                                .on('click', () => {
                                    if (direction === 'horizontal') {
                                        if (ko.isObservable(data.active)) {
                                            data.active(id);
                                        }
                                    }
                                });

                            if (direction === 'vertical') {
                                ko.applyBindingsToNode(btn.get(0), {
                                    'btn-link': title,
                                    icon: icon || 'CHECKBOX',
                                    width: 40,
                                    height: 32,
                                    state: data.active,
                                    value: id,
                                    disabled: !ko.unwrap<boolean>(enable),
                                });
                            }

                            if (active === id) {
                                btn
                                    .addClass('active');

                                $(element)
                                    .find(content)
                                    .removeClass('hidden');
                            }
                        }
                    });

                    if ($contents) {
                        if (direction === 'horizontal') {
                            $contents.style.minHeight = '';
                        } else {
                            $contents.style.minHeight = `${$tabs.offsetHeight}px`;
                        }
                    }
                }
            }
        }
    }
}