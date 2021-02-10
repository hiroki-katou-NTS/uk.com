module nts.uk.knockout.binding.widget {
    type WG_SIZE_STORAGE = {
        [name: string]: {
            set: boolean;
            value: string;
        };
    };

    @handler({
        bindingName: 'widget',
        validatable: true,
        virtual: false
    })
    export class WidgetBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLDivElement, valueAccessor: () => string | KnockoutObservable<string> | { name: string; params?: any }, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
            if (element.tagName !== 'DIV') {
                element.innerText = 'Please use [div] tag as container of widget';
                return;
            }

            const accessor = valueAccessor();

            ko.computed({
                read: () => {
                    element.innerHTML = '';

                    ko.cleanNode(element);

                    const component = ko.unwrap(accessor);

                    ko.applyBindingsToNode(element, { component }, bindingContext);
                },
                disposeWhenNodeIsRemoved: element
            });

            element.removeAttribute('data-bind');
            element.classList.add('widget-container');

            return { controlsDescendantBindings: true };
        }
    }

    @handler({
        bindingName: 'widget-content',
        validatable: true,
        virtual: false
    })
    export class WidgetResizeContentBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLDivElement, valueAccessor: () => number | undefined | KnockoutObservable<number | undefined>, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: { widget: string; }, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
            const { widget } = viewModel;
            const WG_SIZE = 'WIDGET_SIZE';
            const mkv = new ko.ViewModel();
            const minHeight = valueAccessor();
            const key = ko.unwrap<string>(widget);

            const src: string | undefined = allBindingsAccessor.get('src');

            if (element.tagName !== 'DIV') {
                element.innerText = 'Please use [div] tag with [widget-content] binding';

                return { controlsDescendantBindings: false };
            }

            ko.computed({
                read: () => {
                    const mh = ko.unwrap<number | undefined>(minHeight);

                    if (!mh) {
                        element.style.minHeight = '';
                    } else {
                        element.style.minHeight = `${ko.unwrap(mh)}px`;
                    }
                },
                disposeWhenNodeIsRemoved: element
            });

            if (src) {
                element.innerHTML = '';

                const frame = document.createElement('iframe');

                frame.src = src;

                element.appendChild(frame);
            }

            $(element)
                .removeAttr('data-bind')
                .addClass('widget-content')
                .resizable({
                    handles: 's',
                    stop: () => {
                        const { offsetHeight } = element;

                        if (key) {
                            mkv
                                .$window
                                .storage(WG_SIZE)
                                .then((size: WG_SIZE_STORAGE) => size || {})
                                .then((size: WG_SIZE_STORAGE) => {
                                    size[key] = {
                                        set: true,
                                        value: offsetHeight + 'px'
                                    };

                                    mkv.$window.storage(WG_SIZE, size);
                                });
                        }
                    }
                })
                .find('.ui-resizable-s')
                // support quick toggle widget height
                .on('dblclick', () => {
                    const fx = element.style.height;

                    mkv
                        .$window
                        .storage(WG_SIZE)
                        .then((size: WG_SIZE_STORAGE | undefined) => size || {})
                        .then((size: WG_SIZE_STORAGE) => {
                            const height = size[key] || { value: '' };
                            const { value } = height;

                            if (fx) {
                                element.style.height = '';
                            } else {
                                element.style.height = value;
                            }

                            size[key] = { set: !fx, value };

                            mkv.$window.storage(WG_SIZE, size);
                        });
                });

            if (widget) {
                mkv
                    .$window
                    .storage(WG_SIZE)
                    .then((size: WG_SIZE_STORAGE) => {
                        if (size) {
                            const height = size[key];

                            if (height && height.set) {
                                element.style.height = height.value;
                            }
                        }
                    });
            }

            return { controlsDescendantBindings: false };
        }
    }
}