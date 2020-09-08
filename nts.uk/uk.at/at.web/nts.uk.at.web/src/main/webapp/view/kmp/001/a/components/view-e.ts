/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmp001.e {
	const template = `
		<div data-bind="component: { 
					name: 'ccg001', 
					params: { employees: employees, baseDate: baseDate, employeeIds: employeeIds}}">
		</div>
		<div id="functions-area">
			<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmp/001/a/index.xhtml' },text: $i18n('KMP001_100')"></a>
		</div>
		<div class="view-kmp">
			<div class="list-component float-left viewa">
				<div id="list-employee"></div>
			</div>
			<div class="float-left model-component" >
				<div class="label" data-bind= "text: $i18n('KMP001_71')"></div>
				<div class="view-e-content">
					<div data-bind="foreach: _.chunk(paddingTypes, 2)">
						<div class="label-column-select" data-bind="foreach: $data">
							<label class="ntsRadioBox" id="viewd-radio-box">
								<input type="radio" data-bind="checkedValue: value, checked: $vm.paddingType"  />
								<span class="box"></span><span class="label" data-bind="text: $vm.$i18n(label)"></span>
							<label class="ntsRadioBox" id="viewd-radio-box">
							<div class="panel panel-frame">
								<div class="label" data-bind= "text: $i18n('KMP001_75')"></div>
							</div>
						</div>
					</div>
					<div class="view-e-bg-button" >
						<button class="proceed large view-e-button" data-bind="text: $i18n('KMP001_77')"></button>
					</div>
				</div>
			</div>
		</div>
	`;

	interface Params {

	}

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

		paddingTypes!: PaddingType[];

		created(params: Params) {
			const vm = this;
			
			vm.paddingTypes = [
				{
					value: StampCardEditMethod.EMPLOYEE_CODE,
					label: 'KMP001_73'
				}, {
					value: StampCardEditMethod.COMPANY_CODE_AND_EMPLOYEE_CODE,
					label: 'KMP001_74'
				}];
			
		}

		mounted() {
			const vm = this;
			
			$('#list-employee').ntsListComponent(
				{
					isShowAlreadySet: false, //設定済表示
					isMultiSelect: true,
					listType: 4,
					employeeInputList: vm.employees,
					selectType: 2,
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
	}
	
	export enum StampCardEditMethod {

		EMPLOYEE_CODE = 0,

		COMPANY_CODE_AND_EMPLOYEE_CODE = 1,
	}
	
	interface PaddingType {
		value: StampCardEditMethod;
		label: 'KMP001_73' | 'KMP001_74' ;
	}
}