/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />


const h_template = `
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
					
						<div style="display:inline-block; padding: 30px 20px 30px 30px;"> 
							<div id="work-place-list"></div>
						</div>
						<div id="right-layout" >
							<label id="flex-title" data-bind="i18n:'KMK004_268'"></label>
							<hr style="text-align: left;
						    margin-left: 0;"  />
                        <div style='height: 18px;' >
                            <label id="selected-work-place" data-bind="i18n: screenData().selectedName">
                            </label>
                        </div>
							<div style="margin-top: 10px;" data-bind="component: {
								name: 'basic-settings-company',
								params: {
											screenData:screenData,
											screenMode:screenMode
										}
								}">
						</div>
						<div style=" margin-top:15px; display: inline-block;" data-bind="component: {
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

const BASE_H_URL = 'screen/at/kmk004/h/';

const API_H_URL = {
	START_PAGE: BASE_H_URL + 'init-screen/',
	CHANGE_YEAR: BASE_H_URL + 'change-year/',
	CHANGE_WKPID: BASE_H_URL + 'change-wkpid/',
	REGISTER: BASE_H_URL + 'register',
	UPDATE: BASE_H_URL + 'update',
	DELETE: BASE_H_URL + 'delete',
	AFTER_COPY: BASE_H_URL + 'after-copy/',
	CHANGE_SETTING: BASE_H_URL + 'change-setting/',
};

@component({
	name: 'screen-h-component',
	template: h_template
})

class ScreenHComponent extends ko.ViewModel {

	screenData: KnockoutObservable<FlexScreenData> = ko.observable(new FlexScreenData());

	screenMode = '';

	startYM: KnockoutObservable<number> = ko.observable(0);

	created(params: any) {
		const vm = this;

		vm.screenMode = params.screenMode;

		vm.startYM = params.startYM;

		vm.screenData().initDumpData(params.startYM());

		vm.regSelectedYearEvent();

		vm.initWorkPlaceList().done((selectedId) => {
			vm.startPage(selectedId);
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
				vm.$ajax(API_H_URL.CHANGE_YEAR + vm.screenData().selected() + '/' + year).done((data) => {
					vm.screenData().setYM(year, data);
				}).always(() => { vm.$blockui('clear'); });

			}

		});

	}

	startPage(selectedId: string) {
		const vm = this;
		vm.$blockui('invisible')
			.then(() => vm.$ajax(API_H_URL.START_PAGE + selectedId))
			.done((data) => {
				vm.screenData().updateData(data);
				vm.screenData().alreadySettingList(_.map(data.alreadySettings, (item) => { return { workplaceId: item, isAlreadySetting: true } }));
				vm.screenData().setFocus('load');
			})
			.always(() => vm.$blockui('clear'));
	}



	initWorkPlaceList(): JQueryPromise<any> {
		const vm = this, StartMode = {
			WORKPLACE: 0,
			DEPARTMENT: 1
		},
			SelectionType = {
				SELECT_BY_SELECTED_CODE: 1,
				SELECT_ALL: 2,
				SELECT_FIRST_ITEM: 3,
				NO_SELECT: 4
			}

		let dfd = $.Deferred(), workPlaceGrid = {
			isShowAlreadySet: true,
			isMultipleUse: false,
			isMultiSelect: false,
			startMode: StartMode.WORKPLACE,
			selectedId: vm.screenData().selected,
			baseDate: ko.observable(new Date()),
			selectType: SelectionType.SELECT_FIRST_ITEM,
			isShowSelectButton: true,
			isDialog: false,
			alreadySettingList: vm.screenData().alreadySettingList,
			maxRows: 12,
			tabindex: 1,
			systemType: 2
		};

		vm.$blockui('invisible');
		$('#work-place-list').ntsTreeComponent(workPlaceGrid).done(() => {

                vm.regSelectedEvent();

                vm.$blockui("hide");

                vm.screenData().selected.valueHasMutated();

                dfd.resolve(vm.screenData().selected());
		});
		return dfd.promise();
	}

	regSelectedEvent() {
		const vm = this;

		vm.screenData().selected.subscribe((wkpId) => {

			if (!wkpId) {
				return;
			}

			vm.$blockui('invisible')
				.then(() => vm.$ajax(API_H_URL.CHANGE_WKPID + wkpId))
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

			vm.setSelectedName(wkpId);

		});
	}

	setSelectedName(wkpId: string) {
		const vm = this;

		let datas: Array<UnitModel> = $('#work-place-list').getDataList()

			, flat: any = function(wk: UnitModel) {
				return [wk, _.flatMap(wk.children, flat)];
			},
			flatMapItems = _.flatMapDeep(datas, flat),

			selectedItem: UnitModel = _.find(flatMapItems, ['id', wkpId]);
		vm.screenData().selectedName(selectedItem ? selectedItem.name : '');

	}

	mounted() {
        const vm = this;
            
		$("#year-list").focus();
	}

}

class UnitModel {
	id: string;
	code: string;
	name: string;
	nodeText?: string;
	level: number;
	heirarchyCode: string;
	isAlreadySetting?: boolean;
	children: Array<UnitModel>;
}


class UnitAlreadySettingModel {
	id: string;
	isAlreadySetting: boolean;
}

