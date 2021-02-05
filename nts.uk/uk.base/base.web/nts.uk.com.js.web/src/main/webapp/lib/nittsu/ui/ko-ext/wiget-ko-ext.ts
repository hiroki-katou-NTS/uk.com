module nts.uk.knockout.binding.widget {
    type WG_SIZE_STORAGE = { [name: string]: string } | undefined;

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
        init(element: HTMLDivElement, valueAccessor: () => number | undefined, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: { widget: string; }, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
            const { widget } = viewModel;
            const WG_SIZE = 'WIDGET_SIZE';
            const mkv = new ko.ViewModel();
            const minHeight = valueAccessor();

            const src: string | undefined = allBindingsAccessor.get('src');

            if (element.tagName !== 'DIV') {
                element.innerText = 'Please use [div] tag with [widget-content] binding';

                return { controlsDescendantBindings: false };
            }

            if (minHeight) {
                element.style.minHeight = `${minHeight}px`;
            }

            if (src) {
                element.innerHTML = '';

                const frame = document.createElement('iframe');

                frame.src = src;

                element.appendChild(frame);


                frame
                    .contentWindow
                    .addEventListener('DOMContentLoaded', () => {
                        const wgd = frame.contentWindow;
                        const event = frame.contentDocument.createEvent('Event');

                        console.log('dispatching');

                        event.initEvent('wg.resize', true, false);

                        wgd.dispatchEvent(event);
                    });
            }

            $(element)
                .removeAttr('data-bind')
                .addClass('widget-content')
                .resizable({
                    handles: 's',
                    resize: () => {
                        const { offsetHeight } = element;

                        if (widget) {
                            mkv
                                .$window
                                .storage(WG_SIZE)
                                .then((size: WG_SIZE_STORAGE) => size || {})
                                .then((size: WG_SIZE_STORAGE) => {
                                    size[widget] = offsetHeight + 'px';

                                    mkv.$window.storage(WG_SIZE, size);
                                });
                        }
                    }
                });

            if (widget) {
                mkv
                    .$window
                    .storage(WG_SIZE)
                    .then((size: WG_SIZE_STORAGE) => {
                        if (size) {
                            $(element).css({
                                height: size[widget]
                            });
                        }
                    });
            }

            return { controlsDescendantBindings: false };
        }
    }
}