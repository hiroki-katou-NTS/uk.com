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
            init(element: HTMLElement, valueAccessor: () => TABPANEL_PARAMS, allBindingsAccessor: () => any, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext & { $vm: nts.uk.ui.vm.ViewModel }): { controlsDescendantBindings: boolean } {
                const accessor = valueAccessor();
                const active = accessor.active;
                const tabindex = element.getAttribute('tabindex');

                element.removeAttribute('tabindex');
                element.classList.add('tabs-panel');
                element.removeAttribute('data-bind');

                const childs = $(element).children();

                const tablist = $('<div>', { class: 'tabs-list', html: `<button data-bind="tab-item: $data" />` });

                tablist
                    .appendTo(element);

                const wrapper = $('<div>', { class: 'tabs-content' });

                wrapper
                    .append(childs)
                    .appendTo(element);

                const buttons = ko.computed({
                    read: () => {
                        const dataSource = ko.unwrap<TabModel[]>(accessor.dataSource);

                        return dataSource
                            .map((d) => ({
                                ...d,
                                active,
                                tabindex
                            }));
                    },
                    disposeWhenNodeIsRemoved: element
                });

                ko.applyBindingsToNode(tablist.get(0), { foreach: buttons }, bindingContext);

                // ko.applyBindingsToNode(wrapper.get(0), bindingContext);

                return { controlsDescendantBindings: false };
            }
            update(element: HTMLElement, valueAccessor: () => TABPANEL_PARAMS, allBindingsAccessor: () => any, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext & { $vm: nts.uk.ui.vm.ViewModel }): void {
                const data = valueAccessor();
                const active = ko.unwrap(data.active);
                const direction = ko.unwrap<'vertical' | 'horizontal'>(data.direction) || 'horizontal';

                const dataSource = ko.unwrap<TabModel[]>(data.dataSource);
                const tab = _.find(dataSource, (t: TabModel) => t.id === active);
                const contents = dataSource.map(({ content }) => content).join(', ');

                $(element)
                    .find(contents)
                    .addClass('hidden');

                const $tabs = $(element).find('.tabs-list').get(0);
                const $contents = $(element).find('.tabs-content').get(0);

                element.classList.remove('vertical');
                element.classList.remove('horizontal');

                element.classList.add(direction);

                if ($contents) {
                    if (direction === 'horizontal') {
                        $contents.style.minHeight = '';
                    } else {
                        $contents.style.minHeight = `${$tabs.offsetHeight}px`;
                    }
                }

                $(element)
                    .find((tab || {}).content || '')
                    .removeClass('hidden');
            }
        }

        @handler({
            bindingName: 'tab-item',
            validatable: true,
            virtual: false
        })
        export class TabPanelItemBindingHandler implements KnockoutBindingHandler {
            init(element: HTMLButtonElement, valueAccessor: () => TabModel & { active: string | KnockoutObservable<string> }, allBindingsAccessor: () => any, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext & { $vm: nts.uk.ui.vm.ViewModel }): { controlsDescendantBindings: boolean } {
                const accessor = valueAccessor();

                element.removeAttribute('data-bind');

                $(element)
                    .on('click', () => {
                        if (ko.isObservable(accessor.active)) {
                            accessor.active(accessor.id);
                        }
                    });

                return { controlsDescendantBindings: true };
            }
            update(element: HTMLButtonElement, valueAccessor: () => TabModel & { tabindex: string; active: string | KnockoutObservable<string> }, allBindingsAccessor: () => any, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext & { $vm: nts.uk.ui.vm.ViewModel }) {
                const mvm = new ko.ViewModel();
                const accessor = valueAccessor();
                const title = ko.unwrap(accessor.title);
                const active = ko.unwrap(accessor.active);
                const enable = ko.unwrap(accessor.enable);
                const visible = ko.unwrap(accessor.visible);
                const tabindex = ko.unwrap(accessor.tabindex);

                element.innerText = mvm.$i18n(title);

                element.setAttribute('tabindex', tabindex);

                if (enable) {
                    element.removeAttribute('disabled');
                } else {
                    element.setAttribute('disabled', 'disabled');
                }

                if (!visible) {
                    element.classList.add('hidden');
                } else {
                    element.classList.remove('hidden');
                }

                if (active === accessor.id) {
                    element.classList.add('active');
                } else {
                    element.classList.remove('active');
                }
            }
        }
    }
}


/*update(element: HTMLElement, valueAccessor: () => TABPANEL_PARAMS, allBindingsAccessor: () => any, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext & { $vm: nts.uk.ui.vm.ViewModel }): void {
                const mvm = new ko.ViewModel();
                const data = valueAccessor();
                const active = ko.unwrap<string>(data.active);
                const dataSource = ko.unwrap<TabModel[]>(data.dataSource);
                const direction = ko.unwrap<'vertical' | 'horizontal'>(data.direction) || 'horizontal';
                const tabindex = element.getAttribute('data-tabindex');

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
                                text: mvm.$i18n(title),
                                disabled: !ko.unwrap<boolean>(enable),
                                class: direction === 'vertical' ? 'link icon' : '',
                                tabindex: tabindex
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
            }*/