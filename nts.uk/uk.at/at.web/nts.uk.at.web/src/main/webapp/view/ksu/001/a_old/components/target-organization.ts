/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const targetTemplate = `
	<div class="left-area d-inline">
		<div class="d-inline ksu-settings">
			<button data-bind="text: $i18n('KSU001_37')" />
			<div class="organization-name" data-bind="text: 'Organization Name'"></div>
		</div>
	</div>
	<div class="right-area d-inline">
		<div class="d-inline ksu-settings align-top" data-bind="component: { name: 'ksu-setting-period' }"></div>
		<div class="d-inline ksu-settings">
			<div class="d-inline align-top" data-bind="component: { name: 'ksu-setting-display'}, popper: true"></div>
			<div class="d-inline align-top" data-bind="component: { name: 'ksu-use-guide' }, popper: true"></div>
		</div>
	</div>
`;

@component({
	name: 'ksu-target-organization',
	template: targetTemplate
})
class KSU001ATargetOrganizationComponent extends ko.ViewModel {
	created() {
	}

	mounted() {
		const vm = this;

		$(vm.$el).addClass('target-organization');
	}
}