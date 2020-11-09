/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
	const template = `
		<p>viewc</p>
	`;

	interface Params {

	}

	@component({
		name: 'view-c',
		template
    })
    
	export class ViewCComponent extends ko.ViewModel {
		
		created(params: Params) {

		}

		mounted() {
			
		}
    }
}