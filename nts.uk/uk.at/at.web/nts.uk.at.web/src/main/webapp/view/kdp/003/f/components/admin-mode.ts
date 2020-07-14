/// <reference path='../../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.kdp003.f {
	const adminModeTemplate = `
	<!-- ko with: data -->
	<tr>
		<td data-bind="i18n: 'KDP003_3'"></td>
		<td>
			<!-- ko if: ko.unwrap($component.listCompany).length > 1 -->
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
						options: $component.listCompany,
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
		listCompany: KnockoutObservableArray<CompanyItem> = ko.observableArray([]);

		constructor(public data: { model: Model; params: AdminModeParam; }) {
			super();
		}

		created() {
			const vm = this;
			const { data } = vm;
			const { model, params } = data;

			if (params.companyDesignation) {
				model.companyCode
					.subscribe((code: string) => {
						const dataSources: CompanyItem[] = ko.toJS(vm.listCompany);

						if (dataSources.length) {
							const exist = _.find(dataSources, (item: CompanyItem) => item.companyCode === code);

							if (exist) {
								// update companyId by subscribe companyCode
								model.companyId(exist.companyId);
								model.companyName(exist.companyName);
							} else {
								model.companyId('');
								model.companyName('');
							}
						}

					});
			} else {
				model.companyId
					.subscribe((id: string) => {
						const dataSources: CompanyItem[] = ko.toJS(vm.listCompany);

						if (dataSources.length) {
							const exist = _.find(dataSources, (item: CompanyItem) => item.companyId === id);

							if (exist) {
								// update companyCode by subscribe companyId
								model.companyCode(exist.companyCode);
								model.companyName(exist.companyName);
							}
						}
					});
			}

			vm.$ajax(API.COMPANIES)
				.done((data: CompanyItem[]) => {
					if (params.companyDesignation) {
						const companyId = ko.toJS(model.companyId);
						const exist: CompanyItem = _.find(data, (c) => c.companyId === companyId);

						vm.listCompany(data);

						if (exist) {
							if (!ko.unwrap(model.companyCode)) {
								model.companyCode(exist.companyCode);
							}
						} else {
							vm.$dialog
								.error({ messageId: 'Msg_1527' })
								.then(() => vm.$window.close());
						}
					} else {
						const exist: CompanyItem = _.first(data);

						if (exist) {
							vm.listCompany(data);

							if (!ko.unwrap(model.companyId)) {
								model.companyId(exist.companyId);
							}
						} else {
							vm.$dialog
								.error({ messageId: 'Msg_1527' })
								.then(() => vm.$window.close());
						}
					}
				});
		}
	}
}