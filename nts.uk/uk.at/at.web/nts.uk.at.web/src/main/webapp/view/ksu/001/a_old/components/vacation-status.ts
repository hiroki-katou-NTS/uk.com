/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const vacationStatusTemplate = `
	<button class="ksu-popup-toggle" data-bind="text: $i18n('KSU001_10')"></button>
	<div class="ksu-popup bg-yellow vacation-status">
		<div class="group" data-bind="foreach: _.chunk(models, 3)">
			<div class="row" data-bind="foreach: $data">
				<button data-bind="text: $component.$i18n(text), css: { 'ml-0': $index() === 0 }" auto-close="true"></button>
			</div>
		</div>
	</div>
`;

interface VacationButton {
	text: string;
}

@component({
	name: 'ksu-vacation-status',
	template: vacationStatusTemplate
})
class KSU001AVacationStatusComponent extends ko.ViewModel {
	models: VacationButton[] = [{
		text: 'Com_CompensationHoliday'
	}, {
		text: 'Com_PaidHoliday'
	}, {
		text: 'Com_ExsessHoliday'
	}, {
		text: 'Com_SubstituteHoliday'
	}, {
		text: 'Com_FundedPaidHoliday'
	}, {
		text: 'KSU001_29'
	}];
	
	created() {
	}

	mounted() {
		
	}
}