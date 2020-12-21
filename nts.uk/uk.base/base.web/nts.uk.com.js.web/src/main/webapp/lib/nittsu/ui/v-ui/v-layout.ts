/// <reference path="../viewcontext.d.ts" />

module nts.uk.ui.layout {

    @handler({
        bindingName: 'ui-master-notification',
        validatable: true,
        virtual: false
    })
    export class MasterUINotificationBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
            element.removeAttribute('data-bind');

            return { controlsDescendantBindings: true };
        }
        update(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext) {
            // update notification at here
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

            if (__viewContext.noHeader) {
                element.classList.add('hidden');
            } else {

            }

            return { controlsDescendantBindings: true };
        }
        update(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext) {
            // update notification at here
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
            const position: 'top' | 'bottom' = valueAccessor();
            const back: string = allBindingsAccessor.get('back');
            const title: boolean | string = allBindingsAccessor.get('title');

            if (position === 'top') {
                element.id = "functions-area";

                if (title) {
                    const $title = document.createElement('div');

                    $title.classList.add('pg-name');

                    const { programId, programName } = __viewContext.program;

                    ko.applyBindingsToNode($title, { i18n: _.isString(title) ? title.trim() : `${programId || ''} ${programName || ''}`.trim() }, bindingContext);

                    if (back) {
                        $title.classList.add('navigator');

                        $($title)
                            .prepend(nts.ui.icons.ARROW_LEFT_SQUARE)
                            .on('click', () => bindingContext.$vm.$jump(back));
                    }

                    if (element.childNodes.length) {
                        const $btnGroup = document.createElement('div');
                        $btnGroup.classList.add('button-group');

                        $(element).children().each((__: null, e: HTMLElement) => $($btnGroup).append(e));

                        $(element).append($btnGroup);

                        ko.applyBindingsToDescendants(bindingContext, $btnGroup);
                    }

                    $(element).prepend($title);
                }
            } else {
                element.id = "functions-area-bottom";

                ko.applyBindingsToDescendants(bindingContext, element);
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

            element.style.height = `calc(100vh - ${element.getBoundingClientRect().top + (valueAccessor() || 20)}px)`;

            return { controlsDescendantBindings: false };
        }
    }

}