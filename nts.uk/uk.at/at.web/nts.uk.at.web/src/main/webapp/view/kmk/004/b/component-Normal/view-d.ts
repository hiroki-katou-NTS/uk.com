/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
	const template = `
		<p>viewD</p>
	`;

	interface Params {

	}

	@component({
		name: 'view-d',
		template
    })
    
	export class ViewDComponent extends ko.ViewModel {
		
		created(params: Params) {

		}

		mounted() {
			
		}
    }
}