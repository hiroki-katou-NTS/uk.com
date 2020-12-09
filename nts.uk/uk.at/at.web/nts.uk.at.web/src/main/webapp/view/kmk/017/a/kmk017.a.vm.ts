/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

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
    usageClassification: KnockoutObservable<boolean> = ko.observable(true);

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
    isNewMode: KnockoutObservable<boolean> = ko.observable(true);

    //kdl001>
    kml001selectedCodeList: KnockoutObservableArray<string> = ko.observableArray([]);
    workplaceTimeSetting: KnockoutObservableArray<string> = ko.observableArray([]);
    kml001ExcludedCodeList: KnockoutObservableArray<string> = ko.observableArray([]);

    constructor(params: any) {
      super();
      const vm = this;

      vm.KCP001Init();
      vm.getWorkTimeSetting();
    }

    created(params: any) {
      const vm = this;
    }

    //after render is ok
    mounted() {
      const vm = this;
      $('#kcp004').focusTreeGridComponent();
    }

    getWorkTimeSetting() {
      const vm = this;
      vm.$ajax(API_PATH.getWorkTimeSetting).done((data) => {
        if (!data) return;
        vm.workplaceTimeSetting(data);
        _.forEach(data, (x) => {
          vm.kml001selectedCodeList.push(x.workTimeCode);
        })
      });
    }

    KCP001Init() {
      const vm = this;
      vm.$blockui('grayout');
      vm.baseDate = ko.observable(new Date());
      vm.multiSelectedId = ko.observable(null);
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
        tabindex: 3,
        systemType: 2, // 個人情報 1;  就業 2; 給与 3; 人事 4;  管理者 5;
        restrictionOfReferenceRange: true,
        height: 500,
        //width: 380,
      };

      $.when($('#kcp004').ntsTreeComponent(vm.kcp004Grid)).then(() => {
        vm.workplaceDataSource($('#kcp004').getDataList());
        vm.multiSelectedId.subscribe((newWorkplaceId) => { 
          vm.getWorkingHoursDetails(newWorkplaceId);
        });

        vm.$blockui('hide');
        const wp = $('#kcp004').getRowSelected();
        if (wp.length > 0 && wp[0].id !== '') {
          vm.getWorkingHoursDetails(wp[0].id);
        }
      });

      vm.$ajax(API_PATH.getMultipleWork).done((data) => {
        if (data) {
          vm.usageClassification(data.useATR);
        }
      });

      vm.gridListHeader = ko.observableArray([
        { headerText: vm.$i18n('KMK017_7'), key: 'code', width: 70 },
        { headerText: vm.$i18n('KMK017_8'), key: 'name', width: 120 },
        { headerText: vm.$i18n('KMK017_9'), key: 'workingTime1', width: 120 },
        { headerText: vm.$i18n('KMK017_10'), key: 'workingTime2', width: 150, hidden: vm.usageClassification() },
        { headerText: vm.$i18n('KMK017_11'), key: 'remarks', width: 160 },
      ]);

    }
    //A4_2
    openDialogKDL001() {
      const vm = this;
      let newSelectedCodeList:Array<string> = [];
      let selectedCodeList:Array<string> = [];

      _.forEach(vm.workplaceWorkingTimeList(), (x) => {
        selectedCodeList.push(x.code);
      });

      newSelectedCodeList = vm.kml001selectedCodeList().filter((x) => !_.includes(selectedCodeList, x));

      nts.uk.ui.windows.setShared('kml001multiSelectMode', true);
      nts.uk.ui.windows.setShared('kml001selectAbleCodeList', newSelectedCodeList);
      nts.uk.ui.windows.setShared('kml001selectedCodeList', []);
      nts.uk.ui.windows.setShared('kml001isSelection', []);

      nts.uk.ui.windows.sub.modal("/view/kdl/001/a/index.xhtml", {
        title: "割増項目の設定",
        dialogClass: "no-close"
      })
        .onClosed(function () {
          let kml001selectedTimes = nts.uk.ui.windows.getShared("kml001selectedTimes");
          const KDL001_IsCancel = nts.uk.ui.windows.getShared("KDL001_IsCancel");

          if (!_.isNil(kml001selectedTimes) && kml001selectedTimes.length > 0) {
            vm.$blockui('show');
            let tempList: Array<any> = [], workplaceListItems = vm.workplaceWorkingTimeList();
            //remove duplicate
            _.forEach(kml001selectedTimes, (x) => {
              let found = _.some(vm.workplaceWorkingTimeList(), ['code', x.selectedWorkTimeCode]);
              if (!found) tempList.push(x);
            });

            _.forEach(tempList, (x) => {
              if (!_.isNil(x.selectedWorkTimeCode) && x.selectedWorkTimeCode.length > 0) {
                let remark: any = _.find(vm.workplaceTimeSetting(), (o: any) => o.workTimeCode === x.selectedWorkTimeCode);
                workplaceListItems.push({
                  code: x.selectedWorkTimeCode,
                  name: x.selectedWorkTimeName,
                  workingTime1: vm.workingTimeFormat(x.first.start, x.first.end),
                  workingTime2: vm.workingTimeFormat(x.second.start, x.second.end),
                  remarks: !_.isNil(remark) ? remark.note : '',
                });
              }
            });

            workplaceListItems = _.orderBy(workplaceListItems, 'code', 'asc');
            vm.workplaceWorkingTimeList([]);
            vm.workplaceWorkingTimeList(workplaceListItems);
            vm.$blockui('hide');
          }
        });
    }

    convertNumberToTime(time: number) {
      let intHour = _.floor(time / 60, 0);
      let intMinute = time % 60;
      return (intHour < 10 ? '0' + intHour : intHour) + ':' + (intMinute < 10 ? '0' + intMinute : intMinute);
    }

    workingTimeFormat(start: number, end: number) {
      const vm = this;
      if (start === null && end === null) return '';
      return vm.convertNumberToTime(start) + ' ~ ' + vm.convertNumberToTime(end);
    }

    //A4_3
    deleteWorkingTimeRow() {
      const vm = this;
      let workplaceWorkingTimeList = vm.workplaceWorkingTimeList();
      workplaceWorkingTimeList = workplaceWorkingTimeList.filter((x) => !_.includes(vm.workplaceSelectedCode(), x.code));
      vm.workplaceWorkingTimeList([]);
      vm.workplaceWorkingTimeList(workplaceWorkingTimeList);
    }

    registerWorkingTime() {
      const vm = this;

      const workPlace = $('#kcp004').getRowSelected();
      if (vm.workplaceWorkingTimeList().length === 0) {        
        //$('#grid-list').ntsError('set', {messageId:"Msg_1911"});
        //$('.grid-list').focus();
        vm.$errors("#grid-list", "Msg_1911").then((valid: boolean) => {         
          $('.grid-list').focus();
        });
        return;
      } else {
        vm.$blockui('show');

        let workTimeCodes: Array<string> = [];
        _.forEach(vm.workplaceWorkingTimeList(), (x) => workTimeCodes.push(x.code));

        vm.$ajax(vm.isNewMode() ? API_PATH.registerWorkingTime : API_PATH.updateWorkingTime, {
          workplaceId: workPlace[0].id,
          workTimeCodes: workTimeCodes
        }).done((data) => {
          vm.$dialog.error({ messageId: 'Msg_15' }).then(() => {
            vm.getWorkingHoursDetails(workPlace[0].id);
            vm.isNewMode(false);
            vm.$blockui('hide');
          });
        })
        .fail((error) => { console.log(error); })
        .always(() => vm.$blockui('hide'));
      }
    }

    deleteWorkingTime() {
      const vm = this;
      const workPlace = $('#kcp004').getRowSelected();

      vm.$dialog.confirm({ messageId: 'Msg_18' }).then((result) => {
        if (result === 'yes') {
          vm.$blockui('show');
          vm.$ajax(API_PATH.deleteWorkingTime, {
            workplaceId: workPlace[0].id
          }).done((data) => {
            vm.$dialog.info({ messageId: 'Msg_16' }).then(() => {
              vm.getWorkingHoursDetails(workPlace[0].id);
              vm.isNewMode(true);
              vm.$blockui('hide');
            });
          })
          .fail((error) => { console.log(error); })
          .always(() => vm.$blockui('hide'));
        }
      });
    }

    getWorkingHoursDetails(workplaceId: string) {
      const vm = this;
      vm.$blockui('show');

      vm.findElement(vm.workplaceDataSource(), workplaceId);

      vm.$ajax(API_PATH.getWorkingHoursDetails, { workplaceId: workplaceId }).done((data) => {
        if (!_.isNil(data)) {
          let wpTimeDetails: Array<any> = _.orderBy(data, 'workTimeCode', 'asc');

          vm.workplaceWorkingTimeList([]);
          _.forEach(wpTimeDetails, (x) => {
            vm.workplaceWorkingTimeList.push({
              code: x.workTimeCode,
              name: x.workTimeName,
              workingTime1: vm.workingTimeFormat(x.timezone1.startTime, x.timezone1.endTime),
              workingTime2: vm.workingTimeFormat(x.timezone2.startTime, x.timezone2.endTime),
              remarks: x.note,
            });
          })

          vm.isNewMode(false); //edit
        } else
          vm.isNewMode(true);
        vm.$blockui('hide');
      }).fail((error) => { console.log(error); }).always(() => vm.$blockui('hide'));

      if (vm.isNewMode())
        $('#kcp004').focusTreeGridComponent();
      else
        $('.grid-list').focus();
    }


    findElement(listItems:Array<any>, wpId: string) {
      const vm = this;      
      _.forEach(listItems, (x) => {
        if (wpId === x.id) {
          vm.workplaceName(x.name);
        } else if(x.children.length > 0) {
          vm.findElement(x.children, wpId);
        }
      });
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