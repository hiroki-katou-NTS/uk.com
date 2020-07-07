/// <reference path='../../../../../lib/nittsu/viewcontext.d.ts' />

const adminModeTemplate = `
<!-- ko with: data -->
<tr>
	<td data-bind="i18n: 'KDP003_3'"></td>
	<td>
		<!-- ko if: ko.unwrap($component.listCompany).length > 1 -->
			<!-- ko if: ko.unwrap(params.companyDesignation) === true -->
				<input tabindex="1" id="company-code"
					data-bind="ntsTextEditor: {
						name: '',
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
					name: '',
					options: $component.listCompany,
					visibleItemsCount: 5,
					value: model.companyCode,
					optionsText: 'companyName',
					optionsValue: 'companyCode',
					editable: false,
					columns: [
						{ prop: 'companyCode', length: 5 },
						{ prop: 'companyName', length: 11 },
					]
				}"></div>
			<!-- /ko -->
		<!-- /ko -->
		<!-- ko if: ko.unwrap($component.listCompany).length === 1 -->
		<div data-bind="text: model.companyCode() + '&nbsp;' + model.companyName()"></div>
		<!-- /ko -->
	</td>
</tr>
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
</tr>
<!-- /ko -->`;

const KDP003F_AMIN_MODE_API = {
	COMPANIES: '/ctx/sys/gateway/kdp/login/getLogginSetting'
};

@component({
	name: 'kdp-003-f-admin-mode',
	template: adminModeTemplate
})
class Kdp003FLoginWithAdminModeCoponent extends ko.ViewModel {
	listCompany: KnockoutObservableArray<Kdp003FCompanyItem> = ko.observableArray([]);

	constructor(public data: { model: Kdp003FModel; params: Kdp003FAdminModeParam; }) {
		super();
	}

	created() {
		const vm = this;
		const { data } = vm;
		const { model } = data;

		model.companyCode
			.subscribe((code: string) => {
				const dataSources: Kdp003FCompanyItem[] = ko.toJS(vm.listCompany);

				if (dataSources.length) {
					const exist = _.find(dataSources, (item: Kdp003FCompanyItem) => item.companyCode === code);

					if (exist) {
						// update companyId by subscribe companyCode
						model.companyId(exist.companyId);
					}
				}
			});

		vm.$ajax(KDP003F_AMIN_MODE_API.COMPANIES)
			.done((data: Kdp003FCompanyItem[]) => {
				const exist: Kdp003FCompanyItem = _.first(data);

				if (exist) {
					vm.listCompany(data);

					model.companyCode(exist.companyCode);
					model.companyName(exist.companyName);
				} else {
					vm.$dialog
						.error({ messageId: 'Msg_1527' })
						.then(() => vm.$window.close());
				}
			});
	}
}