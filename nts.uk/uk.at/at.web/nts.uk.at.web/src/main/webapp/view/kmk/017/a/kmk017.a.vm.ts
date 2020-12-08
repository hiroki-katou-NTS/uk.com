/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

import { data } from "jquery";

module nts.uk.at.view.kmk017.a {

  const API_PATH = {
    getWorkingHoursDetails: 'screen/at/kmk017/a/getDetail',
    registerWorkingTime: 'at/shared/workTimeWorkplace/register',
    updateWorkingTime: 'at/shared/workTimeWorkplace/update',
    deleteWorkingTime: 'at/shared/workTimeWorkplace/remove',
    getMultipleWork: 'screen/at/kmk017/a/multipleWork',
    getWorkTimeSetting: 'screen/at/kmk017/a/workTimeSetting',
    getWorkTimeWorkplace: 'screen/at/kmk017/a/workTimeWorkplace',
  };

  @bean()
  class ViewModel extends ko.ViewModel {

    //grid list  
    gridListHeader: KnockoutObservableArray<NtsGridListColumn>;

    //kcp004
    multiSelectedId: KnockoutObservable<any>;
    baseDate: KnockoutObservable<Date>;
    alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
    kcp004Grid: TreeComponentOption;

    //model
    workplaceName: KnockoutObservable<string> = ko.observable(null);
    workplaceWorkingTimeList: KnockoutObservableArray<ItemModel> = ko.observableArray(null);
    workplaceSelectedCode: KnockoutObservableArray<any> = ko.observableArray(null);
    workplaceDataSource: KnockoutObservableArray<any> = ko.observableArray(null);

    constructor(params: any) {
      super();
      const vm = this;

      vm.baseDate = ko.observable(new Date());
      vm.multiSelectedId = ko.observableArray([]);
      vm.alreadySettingList = ko.observableArray([]);
      vm.kcp004Grid = {
        isShowAlreadySet: true,
        isMultipleUse: false,
        isMultiSelect: false,
        startMode: 0, //WORKPLACE = 0, DEPARTMENT = 1,
        selectedId: vm.multiSelectedId,
        baseDate: vm.baseDate,
        selectType: 3, //SELECT_BY_SELECTED_CODE = 1; SELECT_ALL = 2; SELECT_FIRST_ITEM = 3; NO_SELECT = 4;
        isShowSelectButton: true,
        isDialog: false,
        alreadySettingList: vm.alreadySettingList,
        maxRows: 10,
        tabindex: 6,
        systemType: 2, // 個人情報 1;  就業 2; 給与 3; 人事 4;  管理者 5;
        restrictionOfReferenceRange: true,
        //width: 380,
      };

      $('#kcp004').ntsTreeComponent(vm.kcp004Grid);
      vm.workplaceDataSource($('#kcp004').getDataList());

      vm.gridListHeader = ko.observableArray([
        { headerText: 'コード', key: 'code', width: 70 },
        { headerText: '名称', key: 'name', width: 120 },
        { headerText: '就業時刻', key: 'workingTime1', width: 120 },
        { headerText: '就業時刻2', key: 'workingTime2', width: 150 },
        { headerText: '備考', key: 'remarks', width: 160 },
      ]);

      vm.multiSelectedId.subscribe((newWorkplaceId) => {
        vm.findWorkPlaceName(newWorkplaceId);
        /*  console.log(newWorkplaceId);
         var data1 = $('#kcp004').getRowSelected();
         console.log(data1);
         */
      });

      vm.workplaceWorkingTimeList([
        { code: '001', name: '名称A900-1730', workingTime1: '9:00 ~ 17:00', workingTime2: '就業時刻', remarks: '就業時刻' }
      ]);

      vm.$ajax(API_PATH.getMultipleWork).done((data) => {
        console.log(data);
      });
      vm.$ajax(API_PATH.getWorkTimeWorkplace).done((data) => {
        console.log(data);
      });
      vm.$ajax(API_PATH.getWorkTimeSetting).done((data) => {
        console.log(data);
      });

    }

    created(params: any) {
      const vm = this;
    }

    //after render is ok
    mounted() {
      const vm = this;
      //$('#kcp004').focusTreeGridComponent();

    }

    //A4_2
    openDialogKDL001() {
      const vm = this;

      vm.$window.storage('kml001multiSelectMode', true);
      vm.$window.storage('kml001selectAbleCodeList', []);
      vm.$window.storage('kml001selectedCodeList', []);
      vm.$window.storage('kml001isSelection', []);

      vm.$window.modal('/view/kdl/001/a/index.xhtml').then(() => {
        //const kml001selectedCodeList = vm.$window.storage('kml001selectedCodeList');
        vm.$window.storage('kml001selectedCodeList').then((result) => {
          if (!_.isNil(result) && result.length > 0) {
            let tempList = vm.workplaceWorkingTimeList();
            _.forEach(result, (x) => {
              tempList.push({ code: '001', name: '名称A900-1730', workingTime1: '9:00 ~ 17:00', workingTime2: '就業時刻', remarks: '就業時刻' });
            });

            tempList = _.orderBy(tempList, 'code', 'asc');

            vm.workplaceWorkingTimeList([]);
            vm.workplaceWorkingTimeList(tempList);
          }
        });
      });
    }

    //A4_3
    deleteWorkingTimeRow() {
      const vm = this;
      vm.workplaceWorkingTimeList().shift();
    }

    registerWorkingTime() {
      const vm = this;

      const workPlace = $('#kcp004').getRowSelected();

      vm.$ajax(API_PATH.registerWorkingTime, {
        workplaceId: workPlace.workplaceId,
        workTimeCodes: []
      }).done((data) => {
        console.log(data);
      }).fail((error) => { console.log(error); });
    }

    deleteWorkingTime() {
      const vm = this;

      const workPlace = $('#kcp004').getRowSelected();

      vm.$ajax(API_PATH.deleteWorkingTime, {
        workplaceId: workPlace.workplaceId,
        workTimeCodes: []
      }).done((data) => {
        console.log(data);
      }).fail((error) => { console.log(error); });
    }

    getWorkingHoursDetails(workplaceId: string) {
      const vm = this;
      vm.$ajax(API_PATH.getWorkingHoursDetails, { workplaceId: workplaceId }).done((data) => {
        if (!_.isNil(data)) {
          let wpTimeDetails = _.orderBy(data, 'code', 'asc');
        }
      }).fail((error) => { console.log(error); });
    }

    findWorkPlaceName(wpId: string) {
      const vm = this;

      let wpFound = _.find(vm.workplaceDataSource(), (x) => wpId === x.id);
      vm.workplaceName(null);
      if (!_.isNil(wpFound)) {
        vm.workplaceName(wpFound.name);
      }
    }
  }

  //kcp004 component
  export interface UnitModel {
    id: string;
    code: string;
    name: string;
    nodeText?: string;
    level: number;
    heirarchyCode: string;
    isAlreadySetting?: boolean;
  }

  export interface RowSelection {
    id: string;
    code: string;
  }

  export interface UnitAlreadySettingModel {
    id: string;
    isAlreadySetting: boolean;
  }

  export class ItemModel {
    code: string;
    name: string;
    workingTime1: string;
    workingTime2: string;
    remarks: string;

    constructor(init?: Partial<ItemModel>) {
      $.extend(this, init);
    }
  }
}