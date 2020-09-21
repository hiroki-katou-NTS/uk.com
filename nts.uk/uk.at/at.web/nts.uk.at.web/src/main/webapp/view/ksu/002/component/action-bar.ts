/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.at.ksu002.a {
	const template = ` `;
	
	const COMPONENT_NAME = 'action-bar';
	
    @handler({
        bindingName: COMPONENT_NAME,
        validatable: true,
        virtual: false
    })
    export class ActionBarComponentBindingHandler implements KnockoutBindingHandler {
        init(element: any, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
            const name = COMPONENT_NAME;

            element.classList.add('cf');
            element.classList.add('action-bar');

            ko.applyBindingsToNode(element, { component: { name: name, params: { } } }, bindingContext);

            return { controlsDescendantBindings: true };
        }
    }

	@component({
		name: COMPONENT_NAME,
		template
	})
	export class ActionBarComponent extends ko.ViewModel {
		created() {
			
		}
		
		mounted() {
			
		}
	}	
}