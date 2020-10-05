/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmp001.e {
	const template = `
		<div data-bind="component: { 
					name: 'ccg001', 
					params: { employees: employees, baseDate: baseDate, employeeIds: employeeIds}}">
		</div>
		<div id="functions-area">
			<a class="goback" data-bind="i18n: 'KMP001_100', ntsLinkButton: { jump: '/view/kmp/001/a/index.xhtml' }"></a>
		</div>
		<div class="view-kmp">
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
	`;

	interface Params {

	}

	const KMP001E_API = {
		GENERATE_STAMP_CARD: 'screen/pointCardNumber/getStampCardGenerated'
	};

	@component({
		name: 'view-e',
		template
	})
	export class ViewCComponent extends ko.ViewModel {
		public params!: Params;

		public employees: KnockoutObservableArray<IModel> = ko.observableArray([]);
		public baseDate: KnockoutObservable<string> = ko.observable('');
		public employeeIds: KnockoutObservableArray<string> = ko.observableArray([]);
		public selectedCode: KnockoutObservableArray<string> = ko.observableArray([]);
		public paddingType: KnockoutObservable<StampCardEditMethod | null> = ko.observable(null);

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
		}

		mounted() {
			const vm = this;

			$('#list-employee').ntsListComponent(
				{
					isShowAlreadySet: false, //設定済表示
					isMultiSelect: true,
					listType: 4,
					employeeInputList: vm.employees,
					selectType: 3,
					selectedCode: vm.selectedCode,
					isShowNoSelectRow: false, //未選択表示
					isShowWorkPlaceName: true,  //職場表示
					isShowSelectAllButton: false,  //全選択表示
					isSelectAllAfterReload: true,
					disableSelection: false,
					maxRows: 15,
					maxWidth: 400
				} as any
			);
		}

		addStampCard() {
			const vm = this;

			console.log(ko.toJS(vm.paddingType));
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
}

interface IGenerateCard {
	employeeCd: string;
	cardNumber: string;
	duplicateCard: string;
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