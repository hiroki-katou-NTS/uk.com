/// <reference path="../viewcontext.d.ts" />

module nts.uk.ui.layout {

    @handler({
        bindingName: 'vm'
    })
    export class MasterUIViewModelBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
            const content = valueAccessor();

            element.removeAttribute('data-bind');

            element.setAttribute('id', 'master-wrapper');

            ko.applyBindingsToDescendants(bindingContext.extend({ $vm: content, $data: content }), element);

            return { controlsDescendantBindings: true };
        }
    }

    @handler({
        bindingName: 'kb'
    })
    export class KibanUIViewModelBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
            const content = valueAccessor();
            const { errorDialogViewModel } = content;

            element.removeAttribute('data-bind');

            element.classList.add('view');
            element.setAttribute('id', 'kiban-wrapper');

            ko.applyBindingsToDescendants(bindingContext.extend({ $vm: errorDialogViewModel, $data: errorDialogViewModel }), element);

            return { controlsDescendantBindings: true };
        }
    }

    @handler({
        bindingName: 'ui-master-notification',
        validatable: true,
        virtual: false
    })
    export class MasterUINotificationBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
            element.classList.add('hidden');
            element.removeAttribute('data-bind');

            element.setAttribute('id', 'operation-info');

            if (ko.components.isRegistered('ui-notification')) {
                ko.applyBindingsToNode(element, { component: 'ui-notification' });
            }

            return { controlsDescendantBindings: true };
        }
    }

    @handler({
        bindingName: 'ui-master-header',
        validatable: true,
        virtual: false
    })
    export class MasterUIHeaderBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
            element.id = "header";
            element.classList.add('header');
            element.classList.add('hidden');
            element.removeAttribute('data-bind');

            if (ko.components.isRegistered('ui-header')) {
                ko.applyBindingsToNode(element, { component: 'ui-header' });
            }

            return { controlsDescendantBindings: true };
        }
    }

    @handler({
        bindingName: 'ui-master-content',
        validatable: true,
        virtual: false
    })
    export class MasterUIBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
            element.classList.add('master-content');
            element.removeAttribute('data-bind');

            if (!element.id) {
                element.id = 'master-content';
            }

            ko.applyBindingsToDescendants(bindingContext, element);

            $(element)
                .find('div[id^=functions-area]')
                .each((__: number, e: HTMLElement) => {
                    ko.applyBindingsToNode(e,
                        {
                            'ui-function-bar': e.id.match(/bottom$/) ? 'bottom' : 'top',
                            title: e.getAttribute('data-title') || true,
                            back: e.getAttribute('data-url')
                        }, bindingContext);

                    e.removeAttribute('data-url');
                    e.removeAttribute('data-title');
                });

            $(element)
                .find('div[id^=contents-area]')
                .each((__: number, e: HTMLElement) => {
                    ko.applyBindingsToNode(e, { 'ui-contents': 0 }, bindingContext);
                });

            return { controlsDescendantBindings: true };
        }
    }

    // Handler for fixed functional area on top or bottom page
    @handler({
        bindingName: 'ui-function-bar',
        validatable: true,
        virtual: false
    })
    export class MasterUIFunctionalBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => 'top' | 'bottom', allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext & { $vm: nts.uk.ui.vm.ViewModel }): { controlsDescendantBindings: boolean; } {
            const mvm = new ko.ViewModel();
            const position: 'top' | 'bottom' = valueAccessor();
            const back: string = allBindingsAccessor.get('back');
            const title: boolean | string = allBindingsAccessor.get('title');
            const root: nts.uk.ui.RootViewModel = bindingContext.$root;
            const mode = ko.unwrap<'view' | 'modal'>(root.kiban.mode);

            element.classList.add('functions-area');

            // top area
            if (!$(element).prev().length && position === 'top') {
                if (!element.id) {
                    element.id = "functions-area";
                }

                if (title && mode === 'view') {
                    const $title = document.createElement('div');

                    $title.classList.add('pg-name');

                    const { programId, programName } = __viewContext.program;

                    $title.innerHTML = `<span>${mvm.$i18n(_.isString(title) ? title.trim() : `${programId || ''} ${programName || ''}`.trim())}</span>`;

                    if (back) {
                        $title.classList.add('navigator');

                        const svg = document.createElement('svg');

                        ko.applyBindingsToNode(svg, { 'svg-icon': 'ARROW_LEFT_SQUARE', size: 20 });

                        $($title)
                            .prepend(svg)
                            .on('click', () => mvm.$jump(back));
                    }

                    $(element).prepend($title);

                    if (element.childNodes.length > 1) {
                        const $btnGroup = document.createElement('div');
                        $btnGroup.classList.add('button-group');

                        $(element).children().each((__: null, e: HTMLElement) => {
                            if (!e.classList.contains('pg-name')) {
                                $($btnGroup).append(e);
                            }
                        });

                        $(element).append($btnGroup);

                        ko.applyBindingsToNode($btnGroup, null, bindingContext);
                    }

                    // button error in function bar
                    ko.applyBindingsToNode($('<button>').appendTo($title).get(0), { 'c-error': '' }, bindingContext);
                } else {
                    // button error in function bar
                    ko.applyBindingsToNode($('<button>').appendTo(element).get(0), { 'c-error': '' }, bindingContext);
                }
            } else {
                if (!element.id) {
                    element.id = "functions-area-bottom";
                }

                if (!ko.dataFor(element)) {
                    ko.applyBindingsToDescendants(bindingContext, element);
                }

                // button error in function bar
                ko.applyBindingsToNode($('<button>').prependTo(element).get(0), { 'c-error': '' }, bindingContext);
            }

            element.removeAttribute('data-bind');

            return { controlsDescendantBindings: false };
        }
    }


    @handler({
        bindingName: 'ui-contents',
        validatable: true,
        virtual: false
    })
    export class MasterUIContentBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => number, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
            if (!element.id) {
                element.id = 'contents-area';
            }

            element.classList.add('contents-area');
            element.removeAttribute('data-bind');

            // element.style.height = `calc(100vh - ${element.getBoundingClientRect().top + (valueAccessor() || ($(element).parent().hasClass('master-content') ? 0 : 20))}px)`;

            return { controlsDescendantBindings: false };
        }
        update(element: HTMLElement, valueAccessor: () => number, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext) {
            const root: nts.uk.ui.RootViewModel = bindingContext.$root;
            const size = ko.unwrap<nts.uk.ui.WindowSize>(root.kiban.size);

            $.Deferred()
                .resolve({ size })
                .then(() => {
                    element.classList.add('padding-0');
                    element.classList.add('overflow-hidden');
                    element.style.height = '1px';
                })
                .then(() => {
                    const mb = $(element).next();
                    const md = $(element).closest('.modal');
                    const zero = $(element).closest('#master-wrapper.modal').length || $(element).parent().hasClass('master-content');

                    if (!mb.length) {
                        const height = element.getBoundingClientRect().top + (valueAccessor() || (zero ? 0 : (md.length ? 0 : 20))) - 2;

                        element.style.height = `calc(100vh - ${Math.floor(Math.max(0, height))}px)`;
                    } else {
                        const bd = mb.get(0).getBoundingClientRect();
                        const height = element.getBoundingClientRect().top + (valueAccessor() || (zero ? (bd.height || 0) : 20)) - 2;

                        element.style.height = `calc(100vh - ${Math.floor(Math.max(0, height))}px)`;
                    }
                })
                .always(() => {
                    element.classList.remove('hidden');
                    element.classList.remove('padding-0');
                    element.classList.remove('overflow-hidden');

                    element.style.display = '';
                });
        }
    }

}