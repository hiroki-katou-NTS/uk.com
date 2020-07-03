/// <reference path='../../../../../lib/nittsu/viewcontext.d.ts' />


const fingerVeinModeTemplate = `
<tr>
	<td data-bind="i18n: 'KDP003_3'"></td>
	<td data-bind="text: ko.toJS(model.companyCode) + ' ' + ko.toJS(model.companyName)"></td>
</tr>
<tr>
	<td data-bind="i18n: 'KDP003_4'"></td>
	<td data-bind="text: ko.toJS(model.employeeCode) + ' ' + ko.toJS(model.employeeName)"></td>
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
</tr>
`;

@component({
	name: 'kdp-003-f-finger-vein-mode',
	template: fingerVeinModeTemplate
})
class Kdp003FLoginWithFingerVeinModeCoponent extends ko.ViewModel {
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