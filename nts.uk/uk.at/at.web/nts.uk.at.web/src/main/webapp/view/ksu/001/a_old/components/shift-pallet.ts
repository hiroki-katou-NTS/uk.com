/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const shiftPalletTemplate = `
	<div class="pages-group">
		<div class="d-inline switch-mode">
			<div data-bind="ntsSwitchButton: {
					name: 'Sample Switch',
					options: [{
						code: 'Com_Company',
						name: $component.$i18n('Com_Company')
					}, {
						code: 'Com_Workplace',
						name: $component.$i18n('Com_Workplace')
					}],
					optionsValue: 'code',
					optionsText: 'name',
					value: ko.observable('Com_Company')
				}"></div>
		</div>
		<div class="d-inline tab-mode" data-bind="foreach: tabs">
			<a class="tab-table" data-bind="
					text: 'tabs ' + $data,
					css: { 
						selected: $component.selectedTab() === $data
					},
					click: function() { $component.selectedTab($data); }
				"></a>
		</div>
		<div class="d-inline setting-mode">
			<div class="d-inline" data-bind="ntsCheckBox: { checked: ko.observable(false), text: $component.$i18n('KSU001_92') }"></div>
			<button class="d-inline btn-setting" data-bind="icon: 3"></button>
		</div>
	</div>
	<div class="button-table">
		<table>
			<tbody data-bind="foreach: _.chunk([1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0], 10)">
				<tr data-bind="foreach: $data">
					<td>
						<button class="small">+</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
`;


@component({
	name: 'ksu-shift-pallet',
	template: shiftPalletTemplate
})
class KSU001AShiftPalletComponent extends ko.ViewModel {
	selectedTab: KnockoutObservable<number> = ko.observable(1);
	tabs: KnockoutObservableArray<number> = ko.observableArray([1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);

	created() {
	}

	mounted() {
		const vm = this;
	}
}