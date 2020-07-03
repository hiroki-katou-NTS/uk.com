/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const modeEditActionTemplate = `
	<div class="d-inline actions">
		<div>
			<button class="large mb-1" data-bind="text: $component.$i18n('KSU001_126')"></button>
			<button class="large mb-1" data-bind="text: $component.$i18n('KSU001_127')"></button>
			<button class="large mb-1" data-bind="text: $component.$i18n('KSU001_128')"></button>
		</div>
		<div>
			<button class="large undo" data-bind="icon: 44"></button>
			<button class="large redo" data-bind="icon: 154"></button>
			<button class="small align-bottom" data-bind="text: $component.$i18n('？')"></button>
		</div>
	</div>
	<div class="d-inline">
		<div data-bind="component: { name: 'ksu-shift-pallet' }"></div>
	</div>
`;


@component({
	name: 'ksu-mode-edit-action',
	template: modeEditActionTemplate
})
class KSU001AModeEditActionComponent extends ko.ViewModel {	
	created() {
	}

	mounted() {
		const vm = this;

		$(vm.$el).addClass('ksu-mode-edit-action');
	}
}