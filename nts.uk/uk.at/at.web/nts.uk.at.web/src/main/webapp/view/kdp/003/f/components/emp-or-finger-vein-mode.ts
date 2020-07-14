/// <reference path='../../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.kdp003.f {
	const fingerVeinModeTemplate = `
<!-- ko with: data -->
<tr>
	<td data-bind="i18n: 'KDP003_3'"></td>
	<td>
		<div data-bind="text: ko.toJS(model.companyCode) + ' ' + ko.toJS(model.companyName)"></div>
	</td>
</tr>
<tr>
	<td data-bind="i18n: 'KDP003_4'"></td>
	<td>
		<!-- ko if: !ko.unwrap(model.employeeName) -->
		<input tabindex="2" id="employee-code-inp-2"
			data-bind="ntsTextEditor: {
				name: $component.$i18n('KDP003_4'),
				constraint: 'EmployeeCode',
				value: model.employeeCode,
				option: {
					width: '200px',
					textmode: 'text'
				}
			}" />
		<!-- /ko -->
		<!-- ko if: !!ko.unwrap(model.employeeName) -->
		<div data-bind="text: ko.toJS(model.employeeCode) + ' ' + ko.toJS(model.employeeName)"></div>
		<!-- /ko -->
	</td>
</tr>
<!-- ko if: (ko.unwrap(params.mode) === 'employee' && ko.unwrap(params.passwordRequired) !== false) || ko.unwrap(params.mode) !== 'employee' -->
<tr>
	<td data-bind="i18n: 'KDP003_5'"></td>
	<td>
		<input tabindex="3" id="password-input"
			data-bind="ntsTextEditor: {
				name: $vm.$i18n('KDP003_5'),
				value: model.password,
				option: {										
					width: '330px',
					textmode: 'password'
				}
			}" />
	</td>
</tr>
<!-- /ko -->
<!-- /ko -->
`;

	@component({
		name: 'kdp-003-f-finger-vein-mode',
		template: fingerVeinModeTemplate
	})
	export class LoginWithFingerVeinModeCoponent extends ko.ViewModel {
		constructor(public data: { model: Model; params: EmployeeModeParam | FingerVeinModeParam; }) {
			super();
		}
	}
}