/// <reference path='../../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.kdp003.f {
	const fingerVeinModeTemplate = `
<!-- ko with: data -->
<tr>
	<td data-bind="i18n: 'KDP003_3'"></td>
	<td>
		<div data-bind="text: $component.companyText"></div>
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
				required: true,
				value: model.employeeCode,
				option: {
					width: '220px',
					textmode: 'text'
				}
			}" />
		<!-- /ko -->
		<!-- ko if: !!ko.unwrap(model.employeeName) -->
		<div data-bind="text: $component.employeeText"></div>
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
				required: true,
				option: {										
					width: '350px',
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
		companyText!: KnockoutComputed<string>;
		employeeText!: KnockoutComputed<string>;

		constructor(public data: { model: Model; params: EmployeeModeParam | FingerVeinModeParam; }) {
			super();
		}

		created() {
			const vm = this;
			const { data } = vm;
			const { model } = data;

			vm.companyText = ko.computed(() => {
				const companyCode = ko.unwrap(model.companyCode);
				const companyName = ko.unwrap(model.companyName);

				if (companyCode && companyName) {
					return `${companyCode} ${companyName}`;
				}

				return '未登録';
			});
			
			vm.employeeText = ko.computed(() => {
				const employeeCode = ko.unwrap(model.employeeCode);
				const employeeName = ko.unwrap(model.employeeName);

				if (employeeCode && employeeName) {
					return `${employeeCode} ${employeeName}`;
				}

				return '未登録';				
			});
		}
	}
}