/// <reference path='../../../../../lib/nittsu/viewcontext.d.ts' />


const employeeModeTemplate = `
<tr>
	<td data-bind="i18n: 'KDP003_4'"></td>
	<td>
		<input tabindex="2" id="employee-code-inp-2"
			data-bind="ntsTextEditor: {
				name: '',
				constraint: 'EmployeeCode',
				value: model.employeeCode,
				option: {
					width: '200px',
					textmode: 'text'
				}
			}" />
	</td>
</tr>
<tr>
	<td data-bind="i18n: 'KDP003_5'"></td>
	<td>
		<input tabindex="3" id="password-input"
			data-bind="ntsTextEditor: {
				name: $vm.$i18n('CCG007_2'),
				value: model.password,
				option: {										
					width: '330px',
					textmode: 'password'
				}
			}" />
	</td>
</tr>`;


@component({
	name: 'kdp-003-f-employee-mode',
	template: employeeModeTemplate
})
class Kdp003FLoginWithEmployeeModeCoponent extends ko.ViewModel {
	constructor(public model: Kdp003FModel) {
		super();
	}

	created() {
		const vm = this;
	}

	mounted() {
		const vm = this;

	}
}