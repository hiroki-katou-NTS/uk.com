/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />


const tabPanelTemplate = `
<div class="ui-tabs ui-corner-all ui-widget ui-widget-content horizontal">
<ul class="ui-tabs-nav ui-corner-all ui-helper-reset ui-helper-clearfix ui-widget-header" data-bind="foreach: $component.model.tabs">
	<li tabindex="0" class="ui-tabs-tab ui-corner-top ui-state-default ui-tab" data-bind="css: { 'ui-tabs-active ui-state-active': $component.model.selected() === id } ">
		<a href="#" class="ui-tabs-anchor" data-bind="text: title, click: function() { $component.model.selected(id) }"></a>
	</li>
</ul>
</div>
`;

interface Model {
	tabs: KnockoutObservableArray<{ id: string; title: string }>;
	selected: KnockoutObservable<string>;
}

@component({
	name: 'kdp-tab-panel',
	template: tabPanelTemplate
})
class KDPTabPanelComponent extends ko.ViewModel {
	model!: Model;

	created(params: Model) {
		const vm = this;
		vm.model = params;
	}

	mounted() {

	}
}