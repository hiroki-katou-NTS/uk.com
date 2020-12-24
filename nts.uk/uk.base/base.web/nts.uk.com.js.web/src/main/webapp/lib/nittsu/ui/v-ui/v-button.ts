
module nts.ui.controls.buttons {

    export module links {
        const COMPONENT_NAME = 'btn-link';

        type BS_PARAMS = {
            state: any | KnockoutObservable<any> | KnockoutObservableArray<any>;
            value: any | KnockoutObservable<any>;
            text: string | KnockoutObservable<string>;
            icon: string | KnockoutObservable<string>;
            width: number | KnockoutObservable<number>;
            height: number | KnockoutObservable<number>;
            disabled: boolean | KnockoutObservable<boolean>;
        }

        @handler({
            bindingName: COMPONENT_NAME,
            validatable: true,
            virtual: false
        })
        export class ButtonLinkBindingHandler implements KnockoutBindingHandler {
            init(element: SVGElement, valueAccessor: () => string | KnockoutObservable<string>, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
                element.removeAttribute('data-bind');
                const name = COMPONENT_NAME;

                const text = valueAccessor();
                const icon = allBindingsAccessor.get('icon');
                const state = allBindingsAccessor.get('state');
                const value = allBindingsAccessor.get('value');
                const disabled = allBindingsAccessor.get('disabled') || false;
                const width = allBindingsAccessor.get('size') || allBindingsAccessor.get('width');
                const height = allBindingsAccessor.get('size') || allBindingsAccessor.get('height');

                const params = { text, icon, width, height, state, value, disabled };
                ko.applyBindingsToNode(element, { component: { name, params } }, bindingContext);

                return { controlsDescendantBindings: true };
            }
        }

        @component({
            name: COMPONENT_NAME,
            template: `<svg class="svg" data-bind="
                svg-icon: $component.icon,
                width: $component.params.width,
                height: $component.params.height
            "></svg>
            <span data-bind="i18n: $component.params.text"></span>
            <svg data-bind="
                svg-icon: $component.arrowIcon,
                size: 10
            "></svg>`
        })
        export class ButtonLinkViewModel extends ko.ViewModel {
            icon!: KnockoutComputed<string>;
            arrowIcon!: KnockoutComputed<string>;

            active: KnockoutObservable<boolean> = ko.observable(false);

            constructor(private params: BS_PARAMS) {
                super();

                this.icon = ko.computed({
                    read: () => {
                        const { params } = this;
                        const active = ko.unwrap(this.active);

                        const icon = ko.unwrap(params.icon);
                        const state = ko.unwrap(params.state);
                        const value = ko.unwrap(params.value);
                        const disabled = ko.unwrap<boolean>(params.disabled);

                        if (disabled) {
                            return `${icon}_UNSELECT`;
                        }

                        if (active) {
                            return `${icon}_SELECT`;
                        }

                        if (value === undefined) {
                            if (!!state) {
                                return `${icon}_SELECT`;
                            }

                            return `${icon}_UNSELECT`;
                        }

                        if (!_.isArray(state)) {
                            if (_.isEqual(state, value)) {
                                return `${icon}_SELECT`;
                            }

                            return `${icon}_UNSELECT`;
                        }

                        return _.some(state, (c: any) => _.isEqual(c, value)) ? `${icon}_SELECT` : `${icon}_UNSELECT`;
                    }
                });

                this.arrowIcon = ko.computed({
                    read: () => {
                        const { params } = this;
                        const active = ko.unwrap(this.active);

                        const icon = 'ARROW_RIGHT';
                        const state = ko.unwrap(params.state);
                        const value = ko.unwrap(params.value);
                        const disabled = ko.unwrap(params.disabled);

                        if (disabled) {
                            return `${icon}_UNSELECT`;
                        }

                        if (active) {
                            return `${icon}_SELECT`;
                        }

                        if (value === undefined) {
                            if (!!state) {
                                return `${icon}_SELECT`;
                            }

                            return `${icon}_UNSELECT`;
                        }

                        if (!_.isArray(state)) {
                            if (_.isEqual(state, value)) {
                                return `${icon}_SELECT`;
                            }

                            return `${icon}_UNSELECT`;
                        }

                        return _.some(state, (c: any) => _.isEqual(c, value)) ? `${icon}_SELECT` : `${icon}_UNSELECT`;
                    }
                });
            }

            mounted() {
                const vm = this;
                const { params } = vm;

                ko.computed({
                    read: () => {
                        const icon = ko.unwrap(params.icon);

                        if (icon && !vm.$el.classList.contains('large')) {
                            $(vm.$el).addClass('icon');
                        } else {
                            $(vm.$el).removeClass('icon');
                        }
                    },
                    disposeWhenNodeIsRemoved: vm.$el
                });

                $(vm.$el)
                    .addClass('link')
                    .on('mouseup', () => vm.active(false))
                    .on('mousedown', () => {
                        if (vm.$el.hasAttribute('disabled')) {
                            return;
                        }

                        vm.active(true);

                        if (ko.isObservable(params.state)) {
                            const value = ko.unwrap(params.value);

                            if (value !== undefined) {
                                if (_.get(params.state, 'remove')) {
                                    const state = ko.unwrap<any[]>(params.state);

                                    if (_.some(state, (c: any) => _.isEqual(value, c))) {
                                        _.remove(state, (c: any) => _.isEqual(value, c));
                                    } else {
                                        state.push(value);
                                    }

                                    params.state(state);
                                } else {
                                    params.state(value);
                                }
                            } else {
                                params.state(!ko.unwrap(params.state));
                            }
                        }
                    })
                    .on('click', () => {
                        if (ko.isObservable(params.state)) {
                            const value = ko.unwrap(params.value);

                            if (value !== undefined) {
                                if (_.get(params.state, 'remove')) {
                                    const state = ko.unwrap<any[]>(params.state);

                                    if (_.some(state, (c: any) => _.isEqual(value, c))) {
                                        _.remove(state, (c: any) => _.isEqual(value, c));
                                    } else {
                                        state.push(value);
                                    }

                                    params.state(state);
                                } else {
                                    params.state(value);
                                }
                            } else {
                                params.state(!ko.unwrap(params.state));
                            }
                        }
                    });

                ko.computed({
                    read: () => {
                        const state = ko.unwrap(params.state);
                        const value = ko.unwrap(params.value);

                        if (value === undefined ? !!state : (_.get(state, 'push') ? _.some(state, (c: any) => _.isEqual(c, value)) : _.isEqual(state, value))) {
                            $(vm.$el).addClass('selected');
                        } else {
                            $(vm.$el).removeClass('selected');
                        }
                    },
                    disposeWhenNodeIsRemoved: vm.$el
                });
            }

            destroyed() {

            }
        }
    }

    export module schedules {
        const COMPONENT_NAME = 'btn-schedule';

        @handler({
            bindingName: COMPONENT_NAME,
            validatable: true,
            virtual: false
        })
        export class ButtonScheduleBindingHandler implements KnockoutBindingHandler {
            init(element: SVGElement, valueAccessor: () => string | KnockoutObservable<string>, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
                element.removeAttribute('data-bind');
                const name = COMPONENT_NAME;

                const text = valueAccessor();
                const icon = allBindingsAccessor.get('icon');
                const state = allBindingsAccessor.get('state');
                const value = allBindingsAccessor.get('value');
                const disabled = allBindingsAccessor.get('disabled') || false;
                const width = allBindingsAccessor.get('size') || allBindingsAccessor.get('width');
                const height = allBindingsAccessor.get('size') || allBindingsAccessor.get('height');

                const params = { text, icon, width, state, value, height, disabled };
                ko.applyBindingsToNode(element, { component: { name, params } }, bindingContext);

                return { controlsDescendantBindings: true };
            }
        }

        type BS_PARAMS = {
            state: any | KnockoutObservable<any> | KnockoutObservableArray<any>;
            value: any | KnockoutObservable<any>;
            text: string | KnockoutObservable<string>;
            icon: string | KnockoutObservable<string>;
            width: number | KnockoutObservable<number>;
            height: number | KnockoutObservable<number>;
            disabled: boolean | KnockoutObservable<boolean>;
        }

        @component({
            name: COMPONENT_NAME,
            template: `<svg class="svg" data-bind="
                svg-icon: $component.icon,
                width: $component.params.width,
                height: $component.params.height
            "></svg>
            <span data-bind="i18n: $component.params.text"></span>`
        })
        export class ButtonScheduleViewModel extends ko.ViewModel {
            icon!: KnockoutComputed<string>;
            active: KnockoutObservable<boolean> = ko.observable(false);

            constructor(private params: BS_PARAMS) {
                super();

                this.icon = ko.computed({
                    read: () => {
                        const { params } = this;
                        const active = ko.unwrap(this.active);

                        const icon = ko.unwrap(params.icon);
                        const state = ko.unwrap(params.state);
                        const value = ko.unwrap(params.value);
                        const disabled = ko.unwrap(params.disabled);

                        if (disabled) {
                            return `${icon}_UNSELECT`;
                        }

                        if (active) {
                            return `${icon}_SELECT`;
                        }

                        if (value === undefined) {
                            if (!!state) {
                                return `${icon}_SELECT`;
                            }

                            return `${icon}_UNSELECT`;
                        }

                        if (!_.isArray(state)) {
                            if (_.isEqual(state, value)) {
                                return `${icon}_SELECT`;
                            }

                            return `${icon}_UNSELECT`;
                        }

                        return _.some(state, (c: any) => _.isEqual(c, value)) ? `${icon}_SELECT` : `${icon}_UNSELECT`;
                    }
                });
            }

            mounted() {
                const vm = this;
                const { params } = vm;

                $(vm.$el)
                    .addClass('proceed')
                    .addClass('schedule')
                    .on('mouseup', () => vm.active(false))
                    .on('mousedown', () => {
                        if (vm.$el.hasAttribute('disabled')) {
                            return;
                        }

                        vm.active(true);

                        if (ko.isObservable(params.state)) {
                            const value = ko.unwrap(params.value);

                            if (value !== undefined) {
                                if (_.get(params.state, 'remove')) {
                                    const state = ko.unwrap<any[]>(params.state);

                                    if (_.some(state, (c: any) => _.isEqual(value, c))) {
                                        _.remove(state, (c: any) => _.isEqual(value, c));
                                    } else {
                                        state.push(value);
                                    }

                                    params.state(state);
                                } else {
                                    params.state(value);
                                }
                            } else {
                                params.state(!ko.unwrap(params.state));
                            }
                        }
                    });

                ko.computed({
                    read: () => {
                        const text = ko.unwrap(params.text);

                        if (!text) {
                            $(vm.$el).addClass('icon');
                        } else {
                            $(vm.$el).removeClass('icon');
                        }
                    },
                    disposeWhenNodeIsRemoved: vm.$el
                });

                ko.computed({
                    read: () => {
                        const state = ko.unwrap(params.state);
                        const value = ko.unwrap(params.value);

                        if (value === undefined ? !!state : (_.get(state, 'push') ? _.some(state, (c: any) => _.isEqual(c, value)) : _.isEqual(state, value))) {
                            $(vm.$el).addClass('selected');
                        } else {
                            $(vm.$el).removeClass('selected');
                        }
                    },
                    disposeWhenNodeIsRemoved: vm.$el
                });
            }

            destroyed() {
                const vm = this;

                vm.icon.dispose();
            }
        }
    }
}