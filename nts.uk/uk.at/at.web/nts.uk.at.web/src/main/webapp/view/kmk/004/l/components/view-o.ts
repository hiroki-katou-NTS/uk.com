/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.l {
	const template = `
		<p>viewo</p>
	`;

	interface Params {

	}

	@component({
		name: 'view-o',
		template
    })
    
	export class ViewOComponent extends ko.ViewModel {
		
		created(params: Params) {

		}

		mounted() {
			
		}
    }
}