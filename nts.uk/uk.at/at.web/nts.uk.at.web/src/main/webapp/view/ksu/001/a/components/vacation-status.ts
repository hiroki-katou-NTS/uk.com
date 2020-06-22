/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const vacationStatusTemplate = `
	<button class="ksu-popup-toggle" data-bind="text: $i18n('KSU001_10')"></button>
	<div class="ksu-popup bg-yellow vacation-status">
		<div class="group">
			<div class="row">
				<button data-bind="text: $i18n('Com_CompensationHoliday')" class="margin-0" auto-close="true"></button>
				<button data-bind="text: $i18n('Com_PaidHoliday')" auto-close="true"></button>
				<button data-bind="text: $i18n('Com_ExsessHoliday')" auto-close="true"></button>
			</div>
			<div class="row">
				<button data-bind="text: $i18n('Com_SubstituteHoliday')" class="margin-0" auto-close="true"></button>
				<button data-bind="text: $i18n('Com_FundedPaidHoliday')" auto-close="true"></button>
				<button data-bind="text: $i18n('KSU001_29')" auto-close="true"></button>
			</div>
		</div>
	</div>
`;

@component({
	name: 'ksu-vacation-status',
	template: vacationStatusTemplate
})
class KSU001AVacationStatusComponent extends ko.ViewModel {
	created() {
	}

	mounted() {
		
	}
}