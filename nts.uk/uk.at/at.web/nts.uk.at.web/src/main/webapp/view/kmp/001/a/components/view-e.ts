/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmp001.e {
	const template = `
		<div data-bind="component: { 
				name: 'ccg001', 
				params: { employees: employees, baseDate: baseDate }
			}">
		</div>
		<div id="functions-area">
			<a class="goback" data-bind="i18n: 'KMP001_100', ntsLinkButton: { jump: '/view/kmp/001/h/index.xhtml' }"></a>
		</div>
		<div class="view-kmp">
			<div class="view-e">
				<div class="list-component float-left viewa">
					<div id="list-employee"></div>
				</div>
				<div class="float-left model-component" >
					<div class="label" data-bind= "i18n: 'KMP001_71'"></div>
					<div class="view-e-content">
						<div class="label-column-select" data-bind="foreach: $component.paddingTypes">
							<label class="ntsRadioBox">
								<input type="radio" data-bind="value: value, checked: $component.paddingType"  />
								<span class="box"></span>
								<pre class="label" data-bind="i18n: label"></pre>
							</label>
							<div class="panel panel-frame">
								<pre class="label" data-bind= "i18n: content"></pre>
							</div>
						</div>
						<div class="view-e-bg-button" >
							<button class="proceed large view-e-button" data-bind="i18n: 'KMP001_77', click: $component.addStampCard"></button>
						</div>
					</div>
				</div>
			</div>
		</div>
	`;

	interface Params {

	}

	const KMP001E_API = {
		GENERATE_STAMP_CARD: 'screen/pointCardNumber/getStampCardGenerated',
		ADD_STAMP_CARD: 'at/record/register-stamp-card/view-g/registerCardGenarate'
	};

	@component({
		name: 'view-e',
		template
	})
	export class ViewCComponent extends ko.ViewModel {
		public params!: Params;

		public employees: KnockoutObservableArray<IModel> = ko.observableArray([]);
		public baseDate: KnockoutObservable<string> = ko.observable('');
		public selectedCode: KnockoutObservableArray<string> = ko.observableArray([]);
		public paddingType: KnockoutObservable<StampCardEditMethod | null> = ko.observable(0);
		public cardGeneration: KnockoutObservableArray<IGenerateCard> = ko.observableArray([]);

		paddingTypes: PaddingType[] = [
			{
				value: StampCardEditMethod.EMPLOYEE_CODE,
				label: 'KMP001_73',
				content: 'KMP001_75'
			}, {
				value: StampCardEditMethod.COMPANY_CODE_AND_EMPLOYEE_CODE,
				label: 'KMP001_74',
				content: 'KMP001_76'
			}];

		created(params: Params) {
			const vm = this;
			const { NO_SELECT, SELECT_FIRST_ITEM } = SelectType;

			vm.employees
				.subscribe((emps) => {
					$('#list-employee')
						.ntsListComponent({
							isShowAlreadySet: false, //設定済表示
							isMultiSelect: true,
							listType: 4,
							employeeInputList: vm.employees,
							selectType: emps.length ? SELECT_FIRST_ITEM : NO_SELECT,
							selectedCode: vm.selectedCode,
							isShowNoSelectRow: false, //未選択表示
							isShowWorkPlaceName: true,  //職場表示
							isShowSelectAllButton: false,  //全選択表示
							isSelectAllAfterReload: true,
							disableSelection: false,
							maxRows: 12,
							maxWidth: 400
						} as any);
				});
		}

		mounted() {
			const vm = this;

			vm.employees.valueHasMutated();
		}

		addStampCard() {
			const vm = this;
			const paddingType = ko.unwrap(vm.paddingType);
			const codes = ko.unwrap(vm.selectedCode);
			const targetPerson = ko.unwrap(vm.employees)
				.filter(f => codes.indexOf(f.code) > -1)
				.map(({ code, employeeId }) => ({
					employeeCd: code,
					sid: employeeId
				}));

			const param = { loginEmployee: vm.$user.employeeId, makeEmbossedCard: paddingType, targetPerson: targetPerson }

			if (ko.unwrap(vm.selectedCode).length <= 0) {
				vm.$dialog.info({ messageId: 'Msg_184' });
			} else {
				nts.uk.ui.dialog
					.confirm({ messageId: "Msg_2033" })
					.ifYes(() => {
						vm.$ajax(KMP001E_API.GENERATE_STAMP_CARD, param)
							.fail((err: any) => {
								vm.$dialog.error({ messageId: err.messageId });
								// nts.uk.ui.dialog.error({ messageId: err.messageId });
							})
							.then((data: IGenerateCard[]) => {
								vm.cardGeneration(data);
							})
							.then(() => {
								const param = { sid: targetPerson.map(m => m.sid), cardGeneration: ko.unwrap(vm.cardGeneration) };
								vm.$ajax(KMP001E_API.ADD_STAMP_CARD, param);
							})
							.then(() => {
								vm.$dialog.info({ messageId: 'Msg_2034' });
							});
					})
			}

		}
	}
}

enum StampCardEditMethod {

	EMPLOYEE_CODE = 0,

	COMPANY_CODE_AND_EMPLOYEE_CODE = 1,
}

interface PaddingType {
	value: StampCardEditMethod;
	label: 'KMP001_73' | 'KMP001_74';
	content: 'KMP001_75' | 'KMP001_76';
}

interface IGenerateCard {
	employeeCd: string;
	cardNumber: string;
	duplicateCard: string;
}

enum SelectType {
	SELECT_BY_SELECTED_CODE = 1,
	SELECT_ALL = 2,
	SELECT_FIRST_ITEM = 3,
	NO_SELECT = 4
}

class GenerateCard {
	employeeCd: KnockoutObservable<string> = ko.observable('');
	cardNumber: KnockoutObservable<string> = ko.observable('');
	duplicateCard: KnockoutObservable<string> = ko.observable('');

	public create(params?: IGenerateCard) {
		const self = this;

		if (params) {
			self.employeeCd(params.employeeCd);
			self.cardNumber(params.cardNumber);
			self.duplicateCard(params.duplicateCard);
		}
	}
}