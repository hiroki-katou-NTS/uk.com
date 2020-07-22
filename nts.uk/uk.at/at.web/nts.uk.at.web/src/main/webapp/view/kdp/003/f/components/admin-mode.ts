/// <reference path='../../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.kdp003.f {
	const adminModeTemplate = `
	<!-- ko with: data -->
	<tr>
		<td data-bind="i18n: 'KDP003_3'"></td>
		<td>
			<!-- ko if: ko.unwrap(listCompany).length > 1 -->
				<!-- ko if: ko.unwrap(params.companyDesignation) === true -->
					<input tabindex="1" id="company-code"
						data-bind="ntsTextEditor: {
							name: $component.$i18n('KDP003_3'),
							constraint: 'CompanyCode',
							value: model.companyCode,
							option: {
								width: '100px',
								textmode: 'text'
							}
						}" />
				<!-- /ko -->
				<!-- ko if: ko.unwrap(params.companyDesignation) !== true -->
				<div tabindex="1" id="company-code-select"
					data-bind="ntsComboBox: {
						width: '350px',
						name: $component.$i18n('KDP003_3'),
						options: listCompany,
						visibleItemsCount: 5,
						value: model.companyId,
						optionsValue: 'companyId',
						optionsText: 'companyName',
						editable: false,
						required: true,
						columns: [
							{ prop: 'companyCode', length: 5 },
							{ prop: 'companyName', length: 11 },
						]
					}"></div>
				<!-- /ko -->
			<!-- /ko -->
			<!-- ko if: ko.unwrap(listCompany).length === 1 -->
			<div data-bind="text: model.companyCode() + '&nbsp;' + model.companyName()"></div>
			<!-- /ko -->
		</td>
	</tr>
	<tr>
		<td data-bind="i18n: 'KDP003_4'"></td>
		<td>
			<input tabindex="2" id="employee-code-inp-2"
				data-bind="ntsTextEditor: {
					name: $component.$i18n('KDP003_4'),
					required: true,
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
					name: $vm.$i18n('KDP003_5'),
					required: true,
					value: model.password,
					option: {										
						width: '330px',
						textmode: 'password'
					}
				}" />
		</td>
	</tr>
	<!-- /ko -->`;

	@component({
		name: 'kdp-003-f-admin-mode',
		template: adminModeTemplate
	})
	export class LoginWithAdminModeCoponent extends ko.ViewModel {
		constructor(public data: { model: Model; params: AdminModeParam; listCompany: KnockoutObservableArray<CompanyItem>; }) {
			super();
		}
	}
}