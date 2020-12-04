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
					
						<div style="display:inline-block; padding: 30px 18px 30px 30px;"> 
							<div id="work-place-list"></div>
						</div>
						<div id="right-layout" >
							<label id="flex-title" data-bind="i18n:'KMK004_268'"></label>
							<hr/>
							<label id="selected-work-place" data-bind="i18n: screenData().selectedName"></label>
							<div style="margin-top: 20px;" data-bind="component: {
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
const COMPONENT_NAME = 'screen-h-component';

const API_URL = {
	START_PAGE: 'screen/at/kmk004/h/start-page'
};

@component({
	name: COMPONENT_NAME,
	template
})

class ScreenHComponent extends ko.ViewModel {

	screenData: KnockoutObservable<FlexScreenData> = ko.observable(new FlexScreenData());

	screenMode = '';

	created(params: any) {
		const vm = this;

		vm.screenMode = params.screenMode;
		/*	vm.$blockui('invisible')
				.then(() => vm.$ajax(API_URL.START_PAGE))
				.then((data: IScreenData) => {
					vm.screenData(new FlexScreenData(data));
				})
				.then(() => vm.$blockui('clear'));*/
		vm.initWorkPlaceList();

	}

	initWorkPlaceList() {
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

		let workPlaceGrid = {
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
			maxRows: 10,
			tabindex: 1,
			systemType: 2
		};

		vm.$blockui('invisible');
		$('#work-place-list').ntsTreeComponent(workPlaceGrid).done(() => {

			vm.screenData().selected.subscribe((value) => {
				let datas: Array<UnitModel> = $('#work-place-list').getDataList()

					, flat: any = function(wk: UnitModel) {
						return [wk, _.flatMap(wk.children, flat)];
					},
					flatMapItems = _.flatMapDeep(datas, flat),

					selectedItem: UnitModel = _.find(flatMapItems, ['id', value]);
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

