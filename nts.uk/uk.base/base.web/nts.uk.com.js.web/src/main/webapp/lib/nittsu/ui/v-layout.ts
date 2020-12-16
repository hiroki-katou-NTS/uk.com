/// <reference path="viewcontext.d.ts" />

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

            element.style.height = `calc(100vh - ${element.getBoundingClientRect().top}px)`;

            return { controlsDescendantBindings: false };
        }
        update(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext) {
            // update notification at here
        }
    }

    // Handler for fixed functional area on top or bottom page
    @handler({
        bindingName: 'ui-master-functional',
        validatable: true,
        virtual: false
    })
    export class MasterUIFunctionalBottomBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => 'top' | 'bottom', allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: nts.uk.ui.vm.ViewModel, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
            const position: 'top' | 'bottom' = valueAccessor();

            if (position === 'top') {
                element.id = "functions-area";
            } else {
                element.id = "functions-area-bottom";
            }

            element.removeAttribute('data-bind');

            return { controlsDescendantBindings: false };
        }
    }

}