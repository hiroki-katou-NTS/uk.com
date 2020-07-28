/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kmp001.a {

	const template = `<div></div>`;

	@component({
		name: 'ccg-component',
		template
	})
	class CcgComponent extends ko.ViewModel {
		employees!: KnockoutObservableArray<any>;


		public created(params: any) {
			const vm = this;

			vm.employees = params.employees || ko.observableArray([]);
		}

		public mounted() {
			const vm = this;
			const dataFormate = 'YYYY/MM/DD';

			$(vm.$el)
				.ntsGroupComponent({
					/** Common properties */
					systemType: 2, //システム区分	
					showEmployeeSelection: true,
					showQuickSearchTab: true, //クイック検索
					showAdvancedSearchTab: true,
					showBaseDate: true, //基準日利用
					showClosure: false, //就業締め日利用
					showAllClosure: false, //全締め表示
					showPeriod: false, //対象期間利用
					periodFormatYM: true, //対象期間精度
					maxPeriodRange: 'oneMonth', //最長期間

					/** Required parameter */
					baseDate: ko.observable(moment().format(dataFormate)),
					periodStartDate: ko.observable(moment.utc('1900/01/01', dataFormate).format(dataFormate)),
					periodEndDate: ko.observable(moment.utc('9999/12/31', dataFormate).format(dataFormate)),
					inService: true,
					leaveOfAbsence: true,
					closed: true,
					retirement: true,

					/** Quick search tab options */
					showAllReferableEmployee: true, //参照可能な社員すべて
					showOnlyMe: true, //自分だけ
					showSameDepartment: true,
					showSameDepartmentAndChild: true,
					showSameWorkplace: true, //同じ職場の社員
					showSameWorkplaceAndChild: true, //同じ職場とその配下の社員

					/** Advanced search properties */
					showEmployment: true, //雇用条件
					showDepartment: true,
					showWorkplace: true, //職場条件
					showClassification: true, //分類条件
					showJobTitle: true, //職位条件
					showWorktype: false, //勤種条件
					isMutipleCheck: true, //選択モード

					/**
					* Self-defined function: Return data from CCG001
					* @param: data: the data return from CCG001
					*/
					returnDataFromCcg001: function(data: any) {
						const employees = data.listEmployee
							.map(m => ({
								
							}));

						// xu ly lay casc thong tin lien quan toi code o day

						vm.employees(employees);
					}
				});
		}
	}
}