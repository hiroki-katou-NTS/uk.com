/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const modeTemplate = `
	<div class="d-inline ksu-mode-buttons">
		<button class="large mb-1" data-bind="text: $vm.$i18n('KSU001_140'), css: { 'active': mode() === 'edit' }, click: function() { $component.mode('edit'); }"></button>
		<button class="large" data-bind="text: $vm.$i18n('KSU001_141'), css: { 'active': mode() === 'confirm' }, click: function() { $component.mode('confirm'); }"></button>
	</div>
	<div class="d-inline ml-1" data-bind="component: { name: 'ksu-mode-edit-action'  }"></div>
`;

type MODE = 'edit' | 'confirm';


@component({
	name: 'ksu-mode',
	template: modeTemplate
})
class KSU001AModeComponent extends ko.ViewModel {
	mode: KnockoutObservable<MODE> = ko.observable('edit');
	
	created() {
	}

	mounted() {
		const vm = this;

		$(vm.$el).addClass('ksu-mode');
	}
}