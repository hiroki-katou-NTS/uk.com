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
        bindingName: 'ui-master-notification',
        validatable: true,
        virtual: false
    })
    export class MasterUINotificationBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
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
            element.removeAttribute('data-bind');
            element.id = "header";
            element.classList.add('header');

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

            element.removeAttribute('data-bind');

            element.id = 'master-content';
            element.classList.add('master-content');

            // element.style.height = `calc(100vh - ${element.getBoundingClientRect().top}px)`;

            return { controlsDescendantBindings: false };
        }
        update(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext) {
            // update notification at here
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
                element.id = "functions-area";

                if (title && mode === 'view') {
                    const $title = document.createElement('div');

                    $title.classList.add('pg-name');

                    const { programId, programName } = __viewContext.program;

                    ko.applyBindingsToNode($title, { i18n: _.isString(title) ? title.trim() : `${programId || ''} ${programName || ''}`.trim() }, bindingContext);

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
                }
            } else {
                element.id = "functions-area-bottom";

                ko.applyBindingsToNode(element, null, bindingContext);
            }

            element.removeAttribute('data-bind');

            return { controlsDescendantBindings: true };
        }
    }


    @handler({
        bindingName: 'ui-contents',
        validatable: true,
        virtual: false
    })
    export class MasterUIContentBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => number, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
            element.removeAttribute('data-bind');

            element.id = 'contents-area';
            element.classList.add('contents-area');

            element.style.height = `calc(100vh - ${element.getBoundingClientRect().top + (valueAccessor() || ($(element).parent().hasClass('master-content') ? 0 : 20))}px)`;

            return { controlsDescendantBindings: false };
        }
        update(element: HTMLElement, valueAccessor: () => number, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext) {
            const root: nts.uk.ui.RootViewModel = bindingContext.$root;
            const header = ko.unwrap<boolean>(root.kiban.header);
            const size = ko.unwrap<nts.uk.ui.WindowSize>(root.kiban.size);

            $.Deferred()
                .resolve({ size, header })
                .then(() => {
                    element.classList.add('padding-0');
                    element.classList.add('overflow-hidden');
                })
                .then(() => {
                    const mb = $(element).next();
                    const zero = $(element).closest('#master-wrapper.modal').length || $(element).parent().hasClass('master-content');

                    if (!mb.length) {
                        element.style.height = `calc(100vh - ${Math.floor(element.getBoundingClientRect().top + (valueAccessor() || (zero ? 0 : 20)) - 2)}px)`;
                    } else {
                        const bd = mb.get(0).getBoundingClientRect();

                        element.style.height = `calc(100vh - ${Math.floor(element.getBoundingClientRect().top + (valueAccessor() || (zero ? (bd.height || 0) : 20)) - 2)}px)`;
                    }
                })
                .always(() => {
                    element.classList.remove('padding-0');
                    element.classList.remove('overflow-hidden');
                });
        }
    }

}