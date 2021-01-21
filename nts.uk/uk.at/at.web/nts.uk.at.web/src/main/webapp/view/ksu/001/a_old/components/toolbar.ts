/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const toolbarTemplate = `
	<button data-bind="text: $i18n('KSU001_1')" class="proceed" />
	<button data-bind="text: $i18n('KSU001_2')" class="proceed" />
	<button data-bind="text: $i18n('KSU001_3')" />
	<button data-bind="text: $i18n('KSU001_4')" />
	<button data-bind="text: $i18n('KSU001_5')" />
	<button data-bind="text: $i18n('KSU001_6')" />
	<div class="ml-5" data-bind="component: { name: 'ksu-work-hope', params: {} }, popper: true"></div>
	<button data-bind="text: $i18n('KSU001_8')" />
	<button data-bind="text: $i18n('KSU001_9')" />
	<div data-bind="component: { name: 'ksu-vacation-status', params: {} }, popper: true"></div>
	<button class="ml-5" data-bind="text: $i18n('KSU001_11')" />
	<div class="ml-4" data-bind="component: { name: 'ksu-setting-menu', params: {} }, popper: true"></div>
`;

@component({
	name: 'ksu-toolbar',
	template: toolbarTemplate
})
class KSU001AToolbarComponent extends ko.ViewModel {
	created() {
	}

	mounted() {
		const vm = this;

		$(vm.$el).attr('id', 'functions-area');
	}
}