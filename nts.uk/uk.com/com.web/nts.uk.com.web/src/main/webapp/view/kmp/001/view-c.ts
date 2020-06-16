/// <reference path="../../../lib/nittsu/viewcontext.d.ts" />

//interface Params {
//	model: KnockoutObservable<string>;
//	tabs: KnockoutObservableArray<string>;
//}

const template =`<div class="sidebar-content-header">
						<span class="title" data-bind= "text: $i18n('KMP001_3')"></span>
						<button class="proceed" data-bind= "text: $i18n('KMP001_5')"></button>
					</div>`;

@component({
	name: 'view-c',
	template
})
class ViewC extends ko.ViewModel {
	public params!: Params;	

	created(params: Params) {
		this.params = params;
	}
}