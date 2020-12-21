
module nts.ui.controls.buttons {

    export module schedules {
        const COMPONENT_NAME = 'btn-schedule';

        @handler({
            bindingName: COMPONENT_NAME,
            validatable: true,
            virtual: false
        })
        export class SvgIconBindingHandler implements KnockoutBindingHandler {
            init(element: SVGElement, valueAccessor: () => string | KnockoutObservable<string>, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
                element.removeAttribute('data-bind');
                const name = COMPONENT_NAME;

                const text = valueAccessor();
                const icon = allBindingsAccessor.get('icon');
                const state = allBindingsAccessor.get('state');
                const width = allBindingsAccessor.get('size') || allBindingsAccessor.get('width');
                const height = allBindingsAccessor.get('size') || allBindingsAccessor.get('height');

                const params = { text, icon, width, state, height };
                ko.applyBindingsToNode(element, { component: { name, params } }, bindingContext);

                return { controlsDescendantBindings: true };
            }
        }

        type BS_PARAMS = {
            text: string | KnockoutObservable<string>;
            icon: string | KnockoutObservable<string>;
            state: boolean | KnockoutObservable<boolean>;
            width: number | KnockoutObservable<number>;
            height: number | KnockoutObservable<number>;
        }

        @component({
            name: COMPONENT_NAME,
            template: `<svg data-bind="
                svg-icon: $component.icon,
                width: $component.params.width,
                height: $component.params.height
            "></svg>
            <span data-bind="i18n: $component.params.text"></span>`
        })
        export class ScheduleButtonViewModel extends ko.ViewModel {
            icon!: KnockoutComputed<string>;
            active: KnockoutObservable<boolean> = ko.observable(false);

            constructor(private params: BS_PARAMS) {
                super();

                this.icon = ko.computed({
                    read: () => {
                        const icon = ko.unwrap(this.params.icon);
                        const state = ko.unwrap(this.params.state);
                        const active = ko.unwrap(this.active);

                        if (state || active) {
                            return `${icon}_SELECT`;
                        } else {
                            return `${icon}_UNSELECT`;
                        }
                    }
                });
            }

            mounted() {
                const vm = this;
                const { params } = vm;

                $(vm.$el)
                    .addClass('proceed')
                    .addClass('schedule')
                    .on('click', () => {
                        if (ko.isObservable(params.state)) {
                            params.state(!ko.unwrap(params.state));
                        } else {
                            params.state = !ko.unwrap(params.state);
                        }
                    })
                    .on('mouseup', () => vm.active(false))
                    .on('mousedown', () => vm.active(true));

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

                        if (state) {
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