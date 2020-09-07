/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmp001.h {
	const template = `
		<div id="functions-area">
			<button class="proceed" data-bind= "text: $i18n('KMP001_5'), click: viewA"></button>
		</div>
	`;

	interface Params {
		model: KnockoutObservable<string>;
	}

	@component({
		name: 'view-h',
		template
	})
	export class ViewCComponent extends ko.ViewModel {

		public params!: Params;

		created(params: Params) {
			const vm = this;
			vm.params = params;
		}

		viewA() {
			const vm = this;
			
			vm.params.model('KMP001_A');
		}
	}
}