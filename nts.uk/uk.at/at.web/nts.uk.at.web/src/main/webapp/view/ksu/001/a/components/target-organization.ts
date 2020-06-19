/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const template = `
	<div class="left-area">		
		<button data-bind="text: $i18n('KSU001_37')" />
		<div class="organization-name" data-bind="text: 'Organization Name'"></div>
	</div>
	<div class="right-area">
		<button data-bind="text: $i18n('KSU001_67')" />
	</div>
`;

@component({
	name: 'ksu-target-organization',
	template
})
class KSU001ATargetOrganizationComponent extends ko.ViewModel {
	created() {		
	}
	
	mounted() {
		const vm = this;
		
		$(vm.$el).addClass('target-organization');
	}
}