/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kwr005.b {
  const NUM_ROWS = 10;
  const KWR005_B_INPUT = 'KWR005_WORK_STATUS_DATA';
  const KWR005_B_OUTPUT = 'KWR005_WORK_STATUS_RETURN';
  const KWR005_C_INPUT = 'KWR005_C_DATA';
  const KWR005_C_OUTPUT = 'KWR005_C_RETURN';

  const PATH = {
    getSettingListWorkStatus: 'at/function/kwr/003/a/listworkstatus',
    getSettingLitsWorkStatusDetails: 'at/function/kwr/005/b/detailworkstatus',
    checkDailyAuthor: 'at/function/kwr/005/a/checkdailyauthor',
    deleteSettingItemDetails: 'at/function/kwr/005/b/delete',
    createSettingItemDetails: 'at/function/kwr/005/b/create',
    updateSettingItemDetails: 'at/function/kwr/005/b/update',
    getFormInfo: 'at/screen/kwr/003/b/getinfor'
  };

  @bean()
  class ViewModel extends ko.ViewModel {

    settingListItems: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    columns: KnockoutObservableArray<any>;
    currentCode: KnockoutObservable<any>;
    currentCodeList: KnockoutObservable<string> = ko.observable(null);
    currentSettingCodeList: KnockoutObservableArray<any>;
    //属性
    printProperties: KnockoutObservableArray<any>;
    printPropertyCode: KnockoutObservable<string> = ko.observable(null);
    //current setting
    attendance: KnockoutObservable<any> = ko.observable(null);
    attendanceCode: KnockoutObservable<string> = ko.observable(null);
    attendanceName: KnockoutObservable<string> = ko.observable(null);
    settingCategory: KnockoutObservable<number> = ko.observable(0);
    settingId: KnockoutObservable<string> = ko.observable(null);

    //----------------------
    isSelectAll: KnockoutObservable<boolean> = ko.observable(false);
    isEnableAddButton: KnockoutObservable<boolean> = ko.observable(false);
    isEnableAttendanceCode: KnockoutObservable<boolean> = ko.observable(false);
    isEnableDeleteButton: KnockoutObservable<boolean> = ko.observable(false);
    isEnableDuplicateButton: KnockoutObservable<boolean> = ko.observable(false);
    isNewMode: KnockoutObservable<boolean> = ko.observable(false);

    //swapList
    currentCodeListSwap: KnockoutObservableArray<AttendanceDto> = ko.observableArray([]);
    listItemsSwap: KnockoutObservableArray<AttendanceDto> = ko.observableArray([]);
    gridHeight: KnockoutObservable<number> = ko.observable(330);

    mode: KnockoutObservable<ModelData> = ko.observable(new ModelData());

    constructor(params: any) {
      super();

      const vm = this;

      //get output info
      //vm.getWorkStatusTableOutput();

      vm.getSettingList();

      vm.currentCodeList.subscribe((newValue: any) => {
        nts.uk.ui.errors.clearAll();
        if (!newValue) return;
        vm.getSettingListForPrint(newValue);
      });

      vm.printProperties = ko.observableArray([
        { code: 5, name: vm.$i18n('KWR005_114') },
        { code: 1, name: vm.$i18n('KWR005_115') },
        { code: 2, name: vm.$i18n('KWR005_116') },
        { code: 3, name: vm.$i18n('KWR005_117') },
        { code: 4, name: vm.$i18n('KWR005_118') },
        { code: 6, name: vm.$i18n('KWR005_119') }
      ]);

      vm.columns = ko.observableArray([
        { headerText: vm.$i18n('KWR005_107'), key: 'attendanceItemId', width: 80, formatter: _.escape },
        { headerText: vm.$i18n('KWR005_108'), key: 'attendanceItemName', width: 160, formatter: _.escape },
      ]);

      for (let i = 0; i < 50; i++) {
        vm.listItemsSwap.push(new AttendanceDto(i.toString(), '基本給 ' + i, i.toString()));
      }

    }

    created(params: any) {
      const vm = this;

      const userAgent = window.navigator.userAgent;
      let msie = userAgent.match(/Trident.*rv\:11\./);
      if (!_.isNil(msie) && msie.index > -1) vm.gridHeight(335);

      vm.printPropertyCode.subscribe((newValue) => {
        $('#swapList-search-area-clear-btn').trigger('click');
      });
    }

    mounted() {
      const vm = this;
      //$("#swapList-grid1").igGrid("container").focus();
    }


    newSetting() {
      const vm = this;

      nts.uk.ui.errors.clearAll();
      vm.currentCodeList(null);
      vm.isEnableDuplicateButton(false);
      vm.isEnableDeleteButton(false);
      vm.isEnableAttendanceCode(true);
      vm.createDefaultSettingDetails();
      vm.isNewMode(true);
      $('#KWR005_B52').focus();
    }

    /**
     * Registers setting
     */
    registerSetting() {
      const vm = this;

      //Msg_1943
      if (vm.currentCodeListSwap().length <= 0) {
        vm.$dialog.error({ messageId: 'Msg_1943' }).then(() => {
          $("#swapList-grid2").igGrid("container").focus();
        });
        return;
      }

      let selectedItems: Array<any> = [];
      _.forEach(vm.mode().selectedItems(), (x, index) => {
        selectedItems.push({ rank: index, AttendanceId: x });
      });

      let params = {
        settingCode: vm.mode().code(),
        settingName: vm.mode().name(),
        settingId: vm.mode().settingId(),
        settingList: selectedItems,
      };

      let path_api = PATH.updateSettingItemDetails;

      if (vm.isNewMode()) {
        params.settingId = null;
        path_api = PATH.createSettingItemDetails;
      }

      vm.$blockui('show');
      vm.$ajax(path_api, params).done(() => {
        vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
          $("#btnB15").focus();
        });
        vm.$blockui('hide');
      }).fail().always(() => vm.$blockui('hide'));

    }


    saveOrUpdateSetting() {
      const vm = this;
    }

    /**
     * Detele setting
     */
    deleteSetting() {
      const vm = this;

      vm.$blockui('show');

      const params = {
        settingId: vm.settingId() //該当する設定ID
      };

      vm.$dialog.confirm({ messageId: 'Msg_18' }).then((answer: string) => {
        if (answer === 'yes') {
          vm.$ajax(PATH.deleteSettingItemDetails, params)
          .done(() => {
            vm.$dialog.info({ messageId: 'Msg_16' }).then(() => {
              vm.loadSettingList({ standOrFree: vm.settingCategory(), code: null });
              vm.$blockui('hide');
            })
          })
          .always(() => {
            vm.$blockui('hide');
          })
          .fail((error) => {
            vm.$dialog.error({ messageId: error.messageId }).then(() => {              
              vm.$blockui('hide');
            })
          });
        }
      });
    }

    /**
     * Duplicate Setting
     * */
    /**
     * Shows dialog C
     */
    showDialogC() {
      const vm = this;

      let selectedObj = _.find(vm.settingListItems(), (x) => x.code === vm.currentCodeList());
      let params = {
        code: selectedObj.code,
        name: selectedObj.name,
        settingCategory: vm.settingCategory(),
        settingId: selectedObj.id //複製元の設定ID 
      }

      vm.$window.storage(KWR005_C_INPUT, params).then(() => {
        vm.$window.modal('/view/kwr/005/c/index.xhtml').then(() => {
          vm.$window.storage(KWR005_C_OUTPUT).then((data) => {
            if (_.isNil(data)) {
              return;
            }

            let duplicateItem = _.find(vm.settingListItems(), (x) => x.code === data.code);
            if (!_.isNil(duplicateItem)) {
              vm.$dialog.error({ messageId: 'Msg_1903' }).then(() => { });
              return;
            }

            vm.settingListItems.push(data);
            vm.currentCodeList(data.code);
          });
        });
      });

      $('#KWR005_B53').focus();
    }

    /**
     * Close dialog
     */
    closeDialog() {
      const vm = this;
      vm.$window.storage(KWR005_B_OUTPUT, vm.attendance());
      vm.$window.close();
    }

    /**
     * Get setting list items details
     */
    getSettingListItemsDetails(settingId: string) : Array<AttendanceDto> {
      const vm = this;

      vm.currentCodeListSwap([]);

      //call to server
      /* vm.$blockui('show');
      vm.$ajax(PATH.getSettingLitsWorkStatusDetails, { settingId: settingId })
      .done((result) => {
        if( result ) {

        }
        vm.$blockui('hide')
      })
      .fail()
      .always(() => vm.$blockui('hide')); */

      vm.currentCodeListSwap.push(new AttendanceDto('001', 'items '));
      vm.currentCodeListSwap.push(new AttendanceDto('002', 'items '));
      vm.currentCodeListSwap.push(new AttendanceDto('045', 'items '));
      vm.currentCodeListSwap.push(new AttendanceDto('007', 'items '));
      vm.currentCodeListSwap.push(new AttendanceDto('099', 'items '));

      return vm.currentCodeListSwap();
    }

    /**
     * Create default setting details
     */
    createDefaultSettingDetails() {
      const vm = this;

      let newMode: ModelData = new ModelData();
      vm.mode(newMode);

    }

    getSettingListForPrint(code: string) {
      const vm = this;
      if (!_.isNil(code)) {
        let selectedObj = _.find(vm.settingListItems(), (x: any) => x.code === code);
        if (!_.isNil(selectedObj)) {

          vm.isEnableAttendanceCode(false);
          vm.isEnableAddButton(true);
          vm.isEnableDeleteButton(true);
          vm.isEnableDuplicateButton(true);
          vm.isNewMode(false);

          let newMode: ModelData = new ModelData();
          newMode.code(selectedObj.code);
          newMode.name(selectedObj.name);
          newMode.settingId(selectedObj.id);          
          newMode.selectedItems(vm.getSettingListItemsDetails(selectedObj.id));
          vm.mode(newMode);
        }
      }

      $('#KWR005_B53').focus();
    }

    getWorkStatusTableOutput() {
      const vm = this;

      vm.$blockui('show');

      vm.$ajax(PATH.getFormInfo, { formNumberDisplay: 8 }).done((result) => {      
        if (result && result.listDaily) {
          _.forEach(result.listDaily, (item) => {
            vm.listItemsSwap.push(new AttendanceDto(
              item.attendanceItemId,
              item.attendanceItemName,
              item.attributes,
              item.attendanceItemDisplayNumber
            ));
          });
        }
        vm.$blockui('hide');
      }).always(() => vm.$blockui('hide'));
    }

    getSettingList() {
      const vm = this;
      vm.$window.storage(KWR005_B_INPUT).then((data: any) => {
        if (!data) return;
        vm.settingCategory(data.standOrFree);
        vm.loadSettingList(data);
      });
    }

    loadSettingList(params: any) {
      const vm = this;
      let listWorkStatus: Array<any> = [];

      vm.$blockui('grayout');

      vm.$ajax(PATH.getSettingListWorkStatus, { setting: params.standOrFree }).then((data) => {
        if (!_.isNil(data) && data.length > 0) {
          _.forEach(data, (item) => {
            let code = _.padStart(item.settingDisplayCode, 2, '0');
            listWorkStatus.push(new ItemModel(code, _.trim(item.settingName), item.settingId));
          });

          //sort by code with asc
          vm.settingListItems([]);
          listWorkStatus = _.orderBy(listWorkStatus, ['code'], ['asc']);
          vm.settingListItems(listWorkStatus);

          let code = (!_.isNil(data) && !_.isNil(params.code)) ? _.padStart(params.code, 2, '0') : null;
          if (vm.settingListItems().length > 0) {
            let firstItem: any = _.head(vm.settingListItems());
            if (!code) code = firstItem.code;
          }

          vm.currentCodeList(code);

        } else {
          //create new the settings list
          vm.newSetting();
        }
        vm.$blockui('hide');
      });
    }
  }

  //================================================================= 
  export class ItemModel {
    id: string;
    code: string;
    name: string;
    constructor(code?: string, name?: string, id?: string) {
      this.code = code;
      this.name = name;
      this.id = id;
    }
  }

  export class ModelData {
    code?: KnockoutObservable<string>;
    name?: KnockoutObservable<string>;
    settingId?: KnockoutObservable<string>;
    selectedItems?: KnockoutObservableArray<AttendanceDto>;

    constructor(code?: string, name?: string, settingId?: string, selectedItems?: Array<AttendanceDto>) {
      this.code = ko.observable(code);
      this.name = ko.observable(name);
      this.settingId = ko.observable(settingId);
      this.selectedItems = ko.observableArray(selectedItems);
    }
  }

  export class AttendanceDto {
    attendanceItemId: string;
    attendanceItemName: string;
    attributes: string;
    displayNumber: string;
    constructor(id?: string, name?: string, attributes?: string, displayNumber?: string) {
      this.attendanceItemId = id;
      this.attendanceItemName = name;
      this.attributes = attributes;
      this.displayNumber = displayNumber;
    }
  }
}