/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr003.b {
  //import common = nts.uk.at.view.kwr003.common; 

  const NUM_ROWS = 10;
  const KWR003_B13 = 'KWR003_B_DATA';
  const KWR003_C = 'KWR003_C_DATA';

  @bean()
  class ViewModel extends ko.ViewModel {

    settingListItems: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    columns: KnockoutObservableArray<any>;
    currentCode: KnockoutObservable<any>;
    currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
    currentSettingCodeList: KnockoutObservableArray<any>;
    settingRules: KnockoutObservableArray<any>;
    attendanceCode: KnockoutObservable<string> = ko.observable(null);
    attendanceName: KnockoutObservable<string> = ko.observable(null);
    settingRuleCode: KnockoutObservable<number> = ko.observable(0);
    settingListItemsDetails: KnockoutObservableArray<SettingForPrint> = ko.observableArray([]);
    model: KnockoutObservable<Model> = ko.observable(new Model());

    isSelectAll: KnockoutObservable<boolean> = ko.observable(false);
    isEnableAddButton: KnockoutObservable<boolean> = ko.observable(false);
    isEnableAttendanceCode: KnockoutObservable<boolean> = ko.observable(false);
    isEnableDeleteButton: KnockoutObservable<boolean> = ko.observable(false);
    isEnableDuplicateButton: KnockoutObservable<boolean> = ko.observable(false);

    constructor(params: any) {
      super();
      let vm = this;

      vm.getSettingList();
      vm.currentCodeList.subscribe((code: any) => {
        vm.getSettingListForPrint(code);
      });

      vm.getSettingListItemsDetails();
      vm.currentSettingCodeList = ko.observableArray([]);

      vm.settingRules = ko.observableArray([
        { code: 0, name: vm.$i18n('KWR003_217') },
        { code: 1, name: vm.$i18n('KWR003_218') }
      ]);

      vm.settingListItemsDetails.subscribe((newList) => {
        if (!newList || newList.length <= 0) {
          vm.isSelectAll(false);
          return;
        }
        //Check if all the values in the settingListItemsDetails array are true:
        let isSelectedAll: any = vm.settingListItemsDetails().every(item => item.isChecked() === true);
        //there is least one item which is not checked
        if (isSelectedAll === false) isSelectedAll = null;
        vm.isSelectAll(isSelectedAll);
      });

      // subscribe isSelectAll
      vm.isSelectAll.subscribe(newValue => {
        vm.selectAllChange(newValue);
      });
    }

    created(params: any) {
      let vm = this;
    }

    mounted() {
      let vm = this;
      $("#multiGridList").ntsFixedTable({ height: 370 });
    }

    addRowItem(newRow?: SettingForPrint) {
      let vm = this,
        row: SettingForPrint = newRow;

      if (!newRow) {
        let lastItem: any = _.last(vm.settingListItemsDetails());
        let id = lastItem ? lastItem.id : 1;
        row = new SettingForPrint(id, null, 0, null, false);
      }

      row.isChecked.subscribe((value: boolean) => {
        vm.settingListItemsDetails.valueHasMutated();
      });

      vm.settingListItemsDetails.push(row);
    }

    addNewRow() {
      let vm = this;
      //vm.addRowItem();
      vm.creatDefaultSettingDetails();

      vm.isEnableDuplicateButton(false);
      vm.isEnableDeleteButton(false);

      vm.attendanceCode(null);
      vm.attendanceName(null);
      vm.isEnableAttendanceCode(true);

      $('#KWR003_B42').focus();
    }

    registerSetting() {
      let vm = this;
    }

    deteleSetting() {
      let vm = this;
      //get all items that will be remove
      let listCheckedItems: Array<any> = vm.settingListItemsDetails().filter((row) => row.isChecked() === true);
      if (listCheckedItems.length <= 0) return;

      //get all items that will be not remove
      let listNotCheckedItems: Array<any> = vm.settingListItemsDetails().filter((row) => row.isChecked() === false);
      vm.settingListItemsDetails(listNotCheckedItems);
    }

    /**
     * Duplicate Setting
     * */

    showDialogC() {
      let vm = this;

      let params = {
        code: vm.attendanceCode(),
        name: vm.attendanceName()
      }

      vm.$window.storage(KWR003_B13, ko.toJS(params)).then(() => {
        vm.$window.modal('/view/kwr/003/c/index.xhtml').then(() => {
          vm.$window.storage(KWR003_C).then((data) => {
            if (_.isNil(data)) {
              return;
            }

            vm.attendanceCode(data.code);
            vm.attendanceName(data.name);

          });
        });
      });
    }

    closeDialog() {
      let vm = this;

      vm.$window.close();
    }

    getSettingListItemsDetails() {
      let vm = this;

      //vm.creatDefaultSettingDetails();
      for (let i = 0; i < NUM_ROWS; i++) {
        let newIitem = new SettingForPrint(i + 1, '予定勤務種類', 0, '予定勤務種類', false);
        vm.addRowItem(newIitem);
      }
      _.orderBy(vm.settingListItemsDetails(), ['id', 'name'], ['asc', 'asc']);
    }

    creatDefaultSettingDetails() {
      let vm = this;
      //clear
      vm.settingListItemsDetails([])
      for (let i = 0; i < NUM_ROWS; i++) {
        let newIitem = new SettingForPrint(i + 1, null, 0, null, false);
        vm.addRowItem(newIitem);
      }
    }
    getSettingListForPrint(code: string) {
      let vm = this;
      if (!_.isNil(code)) {
        let selectedObj = _.find(vm.settingListItems(), (x: any) => x.code === code);
        if (!_.isNil(selectedObj)) {
          vm.attendanceCode(selectedObj.code);
          vm.attendanceName(selectedObj.name);

          vm.isEnableAttendanceCode(false);
          vm.isEnableAddButton(true);
          vm.isEnableDeleteButton(true);
          vm.isEnableDuplicateButton(true);
        }
      }
    }

    getSettingList() {
      let vm = this;
      vm.settingListItems = ko.observableArray(
        [
          new ItemModel('0001', '予定勤務種類'),
          new ItemModel('0003', '予定勤務種類'),
          new ItemModel('0004', '予定勤務種類'),
          new ItemModel('0005', '予定勤務種類'),
          new ItemModel('0002', 'Seoul Korea'),
          new ItemModel('0006', 'Paris France'),
          new ItemModel('0007', '予定勤務種類'),
          new ItemModel('0008', '予定勤務種類'),
          new ItemModel('0009', '予定勤務種類'),
          new ItemModel('0010', '予定勤務種類'),
        ]
      );

      if (vm.settingListItems().length > 0) {
        _.orderBy(vm.settingListItems(), ['code'], ['asc']);
        let firstItem: any = _.head(vm.settingListItems());
        vm.currentCodeList.push(firstItem.code);
        vm.getSettingListForPrint(firstItem.code);
      }
    }

    openDialogKDL(data: SettingForPrint) {
      let vm = this;

      if (!data.setting())
        vm.openDialogKDL048(data.id);
      else
        vm.openDialogKDL047(data.id);
    }

    openDialogKDL047(id: number) {
      console.log(id);
    }

    openDialogKDL048(id: number) {
      console.log(id);
    }

    checkItem(data: SettingForPrint) {
      console.log(data);
      return true
    }

    selectAllChange(newValue: boolean) {
      let vm = this;

      if (newValue === null) return;

      _.forEach(vm.settingListItemsDetails(), (row, index) => {
        row.isChecked(newValue);
      })
    }
  }

  //=================================================================
  export class ItemModel {
    code: string;
    name: string;
    constructor(code?: string, name?: string) {
      this.code = code;
      this.name = name;
    }
  }

  export class SettingForPrint {
    id: number;
    isChecked: KnockoutObservable<boolean> = ko.observable(false);
    name: KnockoutObservable<string> = ko.observable(null);
    setting: KnockoutObservable<number> = ko.observable(0);
    selectionItem: string;
    constructor(
      id?: number,
      name?: string,
      setting?: number,
      selectionItem?: string,
      checked?: boolean) {
      this.name(name || '');
      this.setting(setting);
      this.isChecked(checked || false);
      this.selectionItem = selectionItem || '';
      this.id = id;
    }
  }

  export class Model {
    code: string;
    name: string;
    settingForPrint: Array<SettingForPrint>;
    constructor(code?: string, name?: string, settings?: Array<SettingForPrint>) {
      this.code = code;
      this.name = name;
      this.settingForPrint = settings;
    }
  }
}