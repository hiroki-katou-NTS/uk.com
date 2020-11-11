/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.l {
	const template = `
		<p>viewn</p>
	`;

	interface Params {

	}

	@component({
		name: 'view-n',
		template
    })
    
	export class ViewNComponent extends ko.ViewModel {
		
		created(params: Params) {

		}

		mounted() {
			
		}
    }
}