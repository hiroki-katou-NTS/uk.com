/// <reference path='../../../../../lib/nittsu/viewcontext.d.ts' />

const adminModeTemplate = `
<tr>
	<td data-bind="i18n: 'KDP003_3'"></td>
	<td>
		<div tabindex="1" id="company-code-select"
			data-bind="ntsComboBox: {
				width: '350px',
				name: '',
				options: listCompany,
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
</tr>`;

@component({
	name: 'kdp-003-f-admin-mode',
	template: adminModeTemplate
})
class Kdp003FLoginWithAdminModeCoponent extends ko.ViewModel {
	subscriber!: any;
	listCompany: KnockoutObservableArray<Kdp003FCompanyItem> = ko.observableArray([]);

	constructor(public model: Kdp003FModel) {
		super();
	}

	created() {
		const vm = this;
		const { model } = vm;

		vm.subscriber = vm.model.companyCode
			.subscribe((code: string) => {
				const dataSources: Kdp003FCompanyItem[] = ko.toJS(vm.listCompany);

				if (dataSources.length) {
					const exist = _.find(dataSources, (item: Kdp003FCompanyItem) => item.companyCode === code);

					if (exist) {
						// update companyId by subscribe companyCode
						vm.model.companyId(exist.companyId);
					}
				}
			});

		vm.$ajax('/ctx/sys/gateway/kdp/login/getLogginSetting')
			.done((data: Kdp003FCompanyItem[]) => {
				const exist: Kdp003FCompanyItem = _.first(data);

				if (exist) {
					vm.listCompany(data);

					model.companyCode(exist.companyCode);
					model.companyName(exist.companyName);
				}
			});
	}

	mounted() {
		const vm = this;
	}
	
	destroy() {
		const vm = this;
		
		console.log('shit');
	}
}