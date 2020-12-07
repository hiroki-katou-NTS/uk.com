/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />


const template = `
					<div class="sidebar-content-header">
						<!-- ko component: {
											    name: "sidebar-button",
											    params: {
														screenData:screenData ,
														screenMode:screenMode 
												}
											} -->
						<!-- /ko -->
					</div>
					<div style="padding: 10px; 
								display: flex;
								height: calc(100vh - 163px);
    							overflow: hidden scroll;">
					
						<div style="display:inline-block;padding:30px 20px 30px 30px;"> 
							<div id="empt-list-setting"></div>
						</div>
						<div id="right-layout"> 
						<div  style="display:inline-block">
							<label id="flex-title" data-bind="i18n:'KMK004_268'"></label>
							<hr/>
							<label id="selected-work-place" data-bind="i18n:screenData().selectedName"></label>
							<div style="margin-top: 15px;" data-bind="component: {
								name: 'basic-settings-company',
								params: {
											screenData:screenData,
											screenMode:screenMode
										}
								}">
						</div>
						<div data-bind="component: {
								name: 'monthly-working-hours',
								params: {
											screenData:screenData,
											screenMode:screenMode
										}
								}">
						</div>
						</div>
					</div>
	`;
const COMPONENT_NAME = 'screen-i-component';

const API_URL = {
	START_PAGE: 'screen/at/kmk004/i/start-page'
};

@component({
	name: COMPONENT_NAME,
	template
})

class ScreenIComponent extends ko.ViewModel {

	screenData: KnockoutObservable<FlexScreenData> = ko.observable(new FlexScreenData());

	screenMode = '';

	alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);

	created(params: any) {
		let vm = this;

		vm.screenMode = params.screenMode;
		vm.initEmploymentList();
		/*	vm.$blockui('invisible')
				.then(() => vm.$ajax(API_URL.START_PAGE))
				.then((data: IScreenData) => {
					vm.screenData(new FlexScreenData(data));
				})
				.then(() => vm.$blockui('clear'));*/

	}

	initEmploymentList() {
		const vm = this,
			ListType = {
				EMPLOYMENT: 1, // 雇用
				Classification: 2, // 分類
				JOB_TITLE: 3, // 職位
				EMPLOYEE: 4 // 職場
			},
			SelectType = {
				SELECT_BY_SELECTED_CODE: 1,
				SELECT_ALL: 2,
				SELECT_FIRST_ITEM: 3,
				NO_SELECT: 4
			},
			listComponentOption = {
				isShowAlreadySet: true,
				isMultiSelect: false,
				listType: ListType.EMPLOYMENT,
				selectType: SelectType.SELECT_FIRST_ITEM,
				selectedCode: vm.screenData().selected,
				isDisplayClosureSelection:true,
				isDialog: false,
				isShowNoSelectRow: false,
				alreadySettingList: vm.screenData().alreadySettingList,
				maxRows: 12
			};
		vm.$blockui('grayout');
		$('#empt-list-setting').ntsListComponent(listComponentOption).done(() => {

			vm.screenData().selected.subscribe((value) => {
				let datas: Array<EmploymentUnitModel> = $('#empt-list-setting').getDataList(),

					selectedItem: EmploymentUnitModel = _.find(datas, ['code', value]);
				vm.screenData().selectedName(selectedItem ? selectedItem.name : '');
			});
			vm.$blockui("hide");
			vm.screenData().selected.valueHasMutated();
		});
	}
	
	mounted() {
		$("#year-list").focus();
	}

}
class EmploymentUnitModel {
	code: string;
	name?: string;
	isAlreadySetting?: boolean;
}

