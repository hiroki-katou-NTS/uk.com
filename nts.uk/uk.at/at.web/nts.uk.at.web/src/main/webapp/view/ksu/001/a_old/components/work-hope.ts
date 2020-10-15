/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const workHopeTemplate = `
	<button class="ksu-popup-toggle" data-bind="text: $i18n('KSU001_7')"></button>
	<div class="ksu-popup bg-yellow work-hope">
		<div class="group">
			<div class="row">
				<button data-bind="text: $i18n('KSU001_13')" class="margin-0" auto-close="true"></button>
				<div class="text">
					<span data-bind="html: $html('KSU001_14', { Com_person: 'VuongNT' })"></span>
				</div>
			</div>
			<div class="row">
				<button data-bind="text: $i18n('KSU001_15')" class="margin-0" auto-close="true"></button>
				<div class="text">
					<span data-bind="text: $html('KSU001_16', { Com_person: 'VuongNT' })"></span>
				</div>
			</div>
		</div>
	</div>
`;

@component({
	name: 'ksu-work-hope',
	template: workHopeTemplate
})
class KSU001AWorkHopeComponent extends ko.ViewModel {
	created() {
	}

	mounted() {
	}

	$html(msg: string, params: string[]) {
		const vm = this;

		return vm.$i18n(msg, params).replace(/\n/g, '<br />');
	}
}