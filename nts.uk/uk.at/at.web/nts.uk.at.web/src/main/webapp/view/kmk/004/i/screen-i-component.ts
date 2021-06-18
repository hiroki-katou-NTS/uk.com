/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />


const i_template = `
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
    							overflow-y: scroll;">
					
    						<div style="display:inline-block;padding:30px 20px 30px 30px;"> 
    							<div id="empt-list-setting">
                            </div>
						</div>
						<div id="right-layout"> 
    						<div  style="display:inline-block">
    							<label id="flex-title" data-bind="i18n:'KMK004_268'"></label>
    							<hr style="text-align: left;
    						    margin-left: 0;" />
    							<div style='height: 18px;' ><label id="selected-work-place" data-bind="i18n:screenData().selectedName"></label></div>
    							<div style="margin-top: 10px;" data-bind="component: {
    								name: 'basic-settings-company',
    								params: {
    											screenData:screenData,
    											screenMode:screenMode
    										}
    								}">
    						</div>
    						<div style=" margin-top:15px; display: inline-block;"  data-bind="component: {
    								name: 'monthly-working-hours',
    								params: {
    											screenData:screenData,
    											screenMode:screenMode,
    											startYM:startYM
    										}
    								}">
    						</div>
						</div>
					</div>
	`;

const BASE_I_URL = 'screen/at/kmk004/i/';

const API_I_URL = {
	START_PAGE: BASE_I_URL + 'init-screen/',
	CHANGE_YEAR: BASE_I_URL + 'change-year/',
	CHANGE_EMPCD: BASE_I_URL + 'change-empcd/',
	REGISTER: BASE_I_URL + 'register',
	UPDATE: BASE_I_URL + 'update',
	DELETE: BASE_I_URL + 'delete',
	AFTER_COPY: BASE_I_URL + 'after-copy/',
	CHANGE_SETTING: BASE_I_URL + 'change-setting/',
};

@component({
	name: 'screen-i-component',
	template: i_template
})

class ScreenIComponent extends ko.ViewModel {

	screenData: KnockoutObservable<FlexScreenData> = ko.observable(new FlexScreenData());

	screenMode = '';

	alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);

	startYM: KnockoutObservable<number> = ko.observable(0);

	selectedClosureId: KnockoutObservable<number> = ko.observable(null);

	created(params: any) {
		let vm = this;

		vm.screenMode = params.screenMode;

		vm.startYM = params.startYM;

		vm.screenData().initDumpData(params.startYM());

		vm.regSelectedYearEvent();

		vm.initEmploymentList().done((selected) => {
			vm.startPage(selected);
		});



	}

	regSelectedYearEvent() {

		const vm = this;

		vm.screenData().selectedYear.subscribe((yearInput) => {

			if (!yearInput || !vm.screenData().selected()) {
				_.forEach(vm.screenData().monthlyWorkTimeSetComs(), (item) => {
					item.laborTime().checkbox(false);
					item.laborTime().legalLaborTime(null);
					item.laborTime().weekAvgTime(null);
					item.laborTime().withinLaborTime(null);
				});
				vm.screenData().serverData = null;
				vm.screenData().unSaveSetComs = [];
				return;
			}

			let year = Number(yearInput);

			//check if data has in unsave list => bind data from that
			let unsaveItem = _.find(vm.screenData().unSaveSetComs, ['year', year]);

			if (unsaveItem) {
				let isChanged = vm.screenData().saveToUnSaveList();
				if (isChanged) { vm.screenData().setUpdateYear(vm.screenData().serverData.year); }
				vm.screenData().serverData = unsaveItem;
				vm.screenData().monthlyWorkTimeSetComs(_.map(unsaveItem.data, (item: IMonthlyWorkTimeSetCom) => { return new MonthlyWorkTimeSetCom(vm.screenData(), item); }));
			} else {
				let isChanged = vm.screenData().saveToUnSaveList();
				if (isChanged) { vm.screenData().setUpdateYear(vm.screenData().serverData.year); }
				//else load data from sever
				vm.$blockui('invisible');
				vm.$ajax(API_I_URL.CHANGE_YEAR + vm.screenData().selected() + '/' + year).done((data) => {
					vm.screenData().setYM(year, data);
				}).always(() => { vm.$blockui('clear'); });

			}

		});
	}

	startPage(selected: string) {
		const vm = this;

		vm.$blockui('invisible')
			.then(() => vm.$ajax(API_I_URL.START_PAGE + selected))
			.done((data) => {
				vm.screenData().alreadySettingList(_.map(data.alreadySettings, (item) => { return { code: item, isAlreadySetting: true } }));
				vm.screenData().updateData(data);
				vm.screenData().setFocus('load');
			})
			.always(() => vm.$blockui('clear'));
	}

	initEmploymentList(): JQueryPromise<any> {
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
				isDisplayClosureSelection: true,
				isDialog: false,
				isShowNoSelectRow: false,
				isDisplayFullClosureOption: false,
				closureSelectionType: ko.observable(null),
				selectedClosureId: vm.selectedClosureId,
				alreadySettingList: vm.screenData().alreadySettingList,
				maxRows: 12
			};

		let dfd = $.Deferred();
		vm.$blockui('grayout');
		$('#empt-list-setting').ntsListComponent(listComponentOption).done(() => {
            
                vm.regSelectedEvent();

                vm.$blockui("hide");

                vm.screenData().selected.valueHasMutated();

                dfd.resolve(vm.screenData().selected());

                vm.selectedClosureId.subscribe(() => {
                    $('#empt-list-setting').ntsListComponent(listComponentOption);
                });
			
		});

		return dfd.promise();
	}

	regSelectedEvent() {
		const vm = this;
		vm.screenData().selected.subscribe((empCd) => {

			if (!empCd || !empCd.length) {
				return;
			}
            vm.setSelectedName(empCd);
            
			vm.$blockui('invisible')
				.then(() => vm.$ajax(API_I_URL.CHANGE_EMPCD + empCd))
				.done((data) => {
					vm.screenData().yearList(_.chain(data.yearList).map((item: any) => { return new YearItem(item); }).orderBy(['year'], ['desc']).value());
					vm.screenData().serverYears(data.yearList);
					vm.screenData().unSaveSetComs = [];
					data.yearList.reverse();
					if (vm.screenData().selectedYear() == data.yearList[0]) {
						vm.screenData().selectedYear.valueHasMutated();
					} else {
						vm.screenData().selectedYear(data.yearList[0]);
					}

					vm.screenData().flexMonthActCalSet(data.flexBasicSetting.flexMonthActCalSet);
					vm.screenData().setFocus('load');
				})
				.always(() => vm.$blockui('clear'));


		});
	}

	setSelectedName(empCd: string) {

		const vm = this;
		let datas: Array<EmploymentUnitModel> = $('#empt-list-setting').getDataList(),

			selectedItem: EmploymentUnitModel = _.find(datas, ['code', empCd]);
		vm.screenData().selectedName(selectedItem ? selectedItem.name : '');

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

