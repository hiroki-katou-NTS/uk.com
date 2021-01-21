/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const settingDisplayTemplate = `
	<button class="ksu-popup-toggle" data-bind="text: $i18n('KSU001_67')"></button>
	<div class="ksu-popup bg-yellow setting-display">
		<div class="group">
			<table>
				<tbody>
					<tr>
						<td class="">
							<div class="row">
								<div class="d-inline title" data-bind="text: $component.$i18n('KSU001_42')"></div>
								<div class="d-inline" data-bind="text: $component.$i18n('KSU001_69')"></div>
							</div>
						</td>
						<td>
							<div class="row">
								<div class="cf" data-bind="ntsSwitchButton: {
									name: $component.$i18n('KSU001_42'),
									options: [{
										name: $component.$i18n('KSU001_43'),
										code: '1'
									}, {
										name: $component.$i18n('KSU001_44'),
										code: '2'
									}, {
										name: $component.$i18n('KSU001_45'),
										code: '3'
									}],
									optionsValue: 'code',
									optionsText: 'name',
									value: ko.observable('2'),
									required: true,
								}"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="">
							<div class="row">
								<div class="d-inline title" data-bind="text: $component.$i18n('KSU001_133')"></div>
								<div class="d-inline" data-bind="text: $component.$i18n('KSU001_69')"></div>
							</div>
						</td>
						<td>
							<div class="row">
								<div class="cf"
									data-bind="ntsSwitchButton: {
										name: $component.$i18n('KSU001_133'),
										options: [{
											name: $component.$i18n('KSU001_135'),
											code: '1'
										}, {
											name: $component.$i18n('KSU001_136'),
											code: '2'
										}],
										optionsValue: 'code',
										optionsText: 'name',
										value: ko.observable('1'),
										required: true,
									}"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div data-bind="text: $component.$i18n('KSU001_125')"></div>
							<hr />
						</td>
					</tr>
					<tr>
						<td class="">
							<div class="d-inline title" data-bind="text: $component.$i18n('KSU001_134')"></div>
							<div class="d-inline" data-bind="text: $component.$i18n('KSU001_69')"></div>
						</td>
						<td>
							<div id="combo-box"
								data-bind="ntsComboBox: {
									name: $component.$i18n('KSU001_134'),
									options: [],
									optionsValue: 'code',
									visibleItemsCount: 5,
									value: ko.observable(''),
									optionsText: 'name',
									columns: [
										{ prop: 'code', length: 4 },
										{ prop: 'name', length: 10 },
									]
								}"></div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
`;

@component({
	name: 'ksu-setting-display',
	template: settingDisplayTemplate
})
class KSU001ASettingDisplayComponent extends ko.ViewModel {
	created() {
	}
}