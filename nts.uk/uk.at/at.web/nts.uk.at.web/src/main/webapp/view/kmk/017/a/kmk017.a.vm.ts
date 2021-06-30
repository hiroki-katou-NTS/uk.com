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
    gridListHeader: KnockoutObservableArray<NtsGridListColumn> = ko.observableArray([]);
    usageClassification: KnockoutObservable<boolean> = ko.observable(false);

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
    isEnableRegister: KnockoutObservable<boolean> = ko.observable(false);
    //kdl001>
    kml001selectedCodeList: KnockoutObservableArray<string> = ko.observableArray([]);
    workplaceTimeSetting: KnockoutObservableArray<string> = ko.observableArray([]);
    kml001ExcludedCodeList: KnockoutObservableArray<string> = ko.observableArray([]);

    isIE: KnockoutObservable<boolean> = ko.observable(false);
    gridListHeight: number = 312;//default < 1366

    constructor(params: any) {
      super();
      const vm = this;
      vm.getUseAtr();
      vm.KCP001Init();
      vm.getWorkTimeSetting();
    }

    created(params: any) {
      const vm = this;
      const userAgent = window.navigator.userAgent;
      let msie = userAgent.match(/Trident.*rv\:11\./);
      if (!_.isNil(msie) && msie.index > -1) {        
        vm.gridListHeight = $(window).height() > 768 ? 600 : 324; //default < 1366
      } else vm.gridListHeight = $(window).height() > 768 ? 600 : 324; //default < 1366
    }

    //after render is ok
    mounted() {
      const vm = this;
      //$('#kcp004').focusTreeGridComponent();
      $('#single-tree-grid-kcp004_container').focus();
      const userAgent = window.navigator.userAgent;
      let msie = userAgent.match(/Trident.*rv\:11\./);
      if (!_.isNil(msie) && msie.index > -1) $('.grid-list').addClass('fix-ie');
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

      vm.$ajax(API_PATH.getWorkTimeWorkplace).done((data) => {
        if (!data) return;
        let wpAlreadySetting: Array<any> = [];

        vm.alreadySettingList([]);
        _.forEach(data, (x) => {
          wpAlreadySetting.push({ workplaceId: x.workplaceID, isAlreadySetting: true });
        });
        vm.alreadySettingList(wpAlreadySetting);
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
          $('#grid-list_container').focus();
        });

        vm.$blockui('hide');
        const wp = $('#kcp004').getRowSelected();
        if (wp.length > 0 && wp[0].id !== '') {
          vm.getWorkingHoursDetails(wp[0].id);
          $('#single-tree-grid-kcp004_container').focus();
        }
      });
    }
    //A4_2
    openDialogKDL001() {
      const vm = this;
      let newSelectedCodeList: Array<string> = [];
      let selectedCodeList: Array<string> = [];

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

            nts.uk.ui.errors.clearAll();

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
            vm.isEnableRegister(vm.workplaceWorkingTimeList().length > 0);
          }
        });
    }

    convertNumberToTime(time: number) {
      let intHour = _.floor(time / 60, 0);
      let intMinute = time % 60;
      return intHour + ':' + (intMinute < 10 ? '0' + intMinute : intMinute);
    }

    workingTimeFormat(start: number, end: number) {
      const vm = this;
      if (start === null && end === null) return '';
      return vm.convertNumberToTime(start) + ' ~ ' + vm.convertNumberToTime(end);
    }

    //A4_3
    deleteWorkingTimeRow() {
      const vm = this;
      if (vm.workplaceSelectedCode().length <= 0) {
        vm.$dialog.error({ messageId: 'Msg_2060' }).then(() => {
            $('#grid-list_container').focus();
        });
        return;
      }

      let workplaceWorkingTimeList = vm.workplaceWorkingTimeList();
      workplaceWorkingTimeList = workplaceWorkingTimeList.filter((x) => !_.includes(vm.workplaceSelectedCode(), x.code));
      vm.workplaceWorkingTimeList([]);
      vm.workplaceWorkingTimeList(workplaceWorkingTimeList);
    }

    registerWorkingTime() {
      const vm = this;

      const workPlace = $('#kcp004').getRowSelected();
      if (vm.workplaceWorkingTimeList().length === 0) {
        vm.$dialog.error({ messageId: "Msg_1911" }).then(() => {
            $('#grid-list_container').focus();
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
          vm.alreadySettingList.push({ workplaceId: workPlace[0].id, isAlreadySetting: true });          
          vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
            vm.multiSelectedId.valueHasMutated();
            vm.isNewMode(false);
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
              vm.$blockui('hide');
              vm.getWorkingHoursDetails(workPlace[0].id);
              vm.isNewMode(true);
              vm.alreadySettingList(_.filter(vm.alreadySettingList(), (x) => x.workplaceId !== workPlace[0].id));
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
      vm.workplaceWorkingTimeList([]);
      //vm.alreadySettingList([]);      
      vm.$ajax(API_PATH.getWorkingHoursDetails, { workplaceId: workplaceId }).done((data) => {
        if (!_.isNil(data) && data.length > 0) {
          const wpTimeDetails: Array<any> = _.orderBy(data, 'workTimeCode', 'asc');
          let wpTimeItems: Array<ItemModel> = [];

          _.forEach(wpTimeDetails, (x) => {
            let workingTime2 = '';
            if(!_.isEmpty(x.timezone2)) {
              workingTime2 = _.isEmpty(x.workTimeName) ? "" : vm.workingTimeFormat(x.timezone2.startTime, x.timezone2.endTime);
            }
            wpTimeItems.push({
              code: x.workTimeCode,
              name: _.isEmpty(x.workTimeName) ? vm.$i18n.text('KMK017_12') : x.workTimeName,
              workingTime1: _.isEmpty(x.workTimeName) ? "" : vm.workingTimeFormat(x.timezone1.startTime, x.timezone1.endTime),
              workingTime2: workingTime2,
              remarks: _.isEmpty(x.workTimeName) ? "" : x.note,
            });
          });

          vm.workplaceWorkingTimeList(wpTimeItems);
          vm.isNewMode(false); //edit
          vm.isEnableRegister(true);
    
          /* let hasItem = _.some(vm.alreadySettingList(), (x) => x.workplaceId === workplaceId);
          if( !hasItem) vm.alreadySettingList.push({ workplaceId: workplaceId, isAlreadySetting: true });   */      
                      
        } else {
          vm.isNewMode(true);
          vm.isEnableRegister(false);
        }
      
        vm.$blockui('hide');
      }).fail((error) => { console.log(error); }).always(() => vm.$blockui('hide'));
    }


    findElement(listItems: Array<any>, wpId: string) {
      const vm = this;
      _.forEach(listItems, (x) => {
        if (wpId === x.id) {
          vm.workplaceName(x.name);
        } else if (x.children.length > 0) {
          vm.findElement(x.children, wpId);
        }
      });
    }

    getUseAtr() {
      const vm = this;
      let useWorkTime2: boolean = false;


      vm.$ajax(API_PATH.getMultipleWork).done((data) => {
        if (data && data.useATR === 0) {
          vm.gridListHeader([
            { headerText: vm.$i18n('KMK017_7'), key: 'code', width: 70 },
            { headerText: vm.$i18n('KMK017_8'), key: 'name', width: 120 },
            { headerText: vm.$i18n('KMK017_9'), key: 'workingTime1', width: 130 },
            { headerText: vm.$i18n('KMK017_11'), key: 'remarks', width: 310, columnCssClass: 'limited-label' },
          ]);
        } else
          vm.gridListHeader([
            { headerText: vm.$i18n('KMK017_7'), key: 'code', width: 70 },
            { headerText: vm.$i18n('KMK017_8'), key: 'name', width: 120 },
            { headerText: vm.$i18n('KMK017_9'), key: 'workingTime1', width: 130 },
            { headerText: vm.$i18n('KMK017_10'), key: 'workingTime2', width: 130 },
            { headerText: vm.$i18n('KMK017_11'), key: 'remarks', width: 190, columnCssClass: 'limited-label' },
          ]);
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
    workplaceId: string;
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