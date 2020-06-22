/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const useGuideTemplate = `
	<button class="ksu-popup-toggle btn-show-use-guide"></button>
	<div class="ksu-popup bg-yellow use-guide">
		<div class="group">
			<div data-bind="ntsFormLabel: { text: $i18n('KSU001_13') }"></div>
			<hr />
			<div class="row">
			</div>
		</div>
	</div>
`;

@component({
	name: 'ksu-use-guide',
	template: useGuideTemplate
})
class KSU001AUseGuideComponent extends ko.ViewModel {
	created() {
	}

	mounted() {
	}
}