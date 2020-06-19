/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const template = `
	<button data-bind="text: $i18n('KSU001_1')" class="proceed" />
	<button data-bind="text: $i18n('KSU001_2')" class="proceed" />
	<button data-bind="text: $i18n('KSU001_3')" />
	<button data-bind="text: $i18n('KSU001_4')" />
	<button data-bind="text: $i18n('KSU001_5')" />
	<button data-bind="text: $i18n('KSU001_6')" />
	<button data-bind="text: $i18n('KSU001_7')" />
	<button data-bind="text: $i18n('KSU001_8')" />
	<button data-bind="text: $i18n('KSU001_9')" />
	<button data-bind="text: $i18n('KSU001_10')" />
	<button data-bind="text: $i18n('KSU001_11')" />
	<button data-bind="text: $i18n('KSU001_12')" />
`;

@component({
	name: 'ksu-toolbar',
	template
})
class KSU001AToolbarComponent extends ko.ViewModel {
	created() {		
	}
	
	mounted() {
		const vm = this;
		
		$(vm.$el).attr('id', 'functions-area');
	}
}