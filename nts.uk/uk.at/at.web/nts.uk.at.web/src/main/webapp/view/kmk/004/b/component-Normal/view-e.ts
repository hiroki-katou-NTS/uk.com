/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
	const template = `
		<p>viewE</p>
	`;

	interface Params {

	}

	@component({
		name: 'view-e',
		template
    })
    
	export class ViewEComponent extends ko.ViewModel {
		
		created(params: Params) {

		}

		mounted() {
			
		}
    }
}