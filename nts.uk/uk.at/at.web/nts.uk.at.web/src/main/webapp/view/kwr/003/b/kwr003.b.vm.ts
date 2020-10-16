/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr003.b {
  //import common = nts.uk.at.view.kwr003.common; 

  @bean()
  class ViewModel extends ko.ViewModel {

    listItems: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    columns: KnockoutObservableArray<any>;
    currentCode: KnockoutObservable<any>;
    currentCodeList: KnockoutObservableArray<any>;
    currentSettingCodeList: KnockoutObservableArray<any>;
    settingRules: KnockoutObservableArray<any>;
    attendanceCode: KnockoutObservable<string> = ko.observable('35');
    attendanceName: KnockoutObservable<string> = ko.observable('コード／名称');
    settingRuleCode: KnockoutObservable<number> = ko.observable(0);
    settingListItems: KnockoutObservableArray<SettingForPrint> = ko.observableArray([]);
    model: KnockoutObservable<Model> = ko.observable(new Model());

    isSelectAll: KnockoutObservable<boolean> = ko.observable(false);
    isEnaleAddButton: KnockoutObservable<boolean> = ko.observable(false);
    isEnaleRemoveButton: KnockoutObservable<boolean> = ko.observable(false);

    constructor(params: any) {
      super();
      let vm = this;

      vm.listItems = ko.observableArray(
        [
          new ItemModel('0001', 'Hanoi Vietnam'),
          new ItemModel('0003', 'Bangkok Thailand'),
          new ItemModel('0004', 'Tokyo Japan'),
          new ItemModel('0005', 'Jakarta Indonesia'),
          new ItemModel('0002', 'Seoul Korea'),
          new ItemModel('0006', 'Paris France'),
          new ItemModel('0007', 'United States'),
          new ItemModel('0008', 'United States'),
          new ItemModel('0009', 'United States'),
          new ItemModel('0010', 'United States'),
        ]
      );

      vm.currentCodeList = ko.observableArray([]);
      vm.currentCodeList.subscribe((code: any) => {
        vm.getSettingListForPrint(code);
      });

      vm.createSettingListItems();
      vm.currentSettingCodeList = ko.observableArray([]);

      vm.settingRules = ko.observableArray([
        { code: 0, name: vm.$i18n('KWR003_217') },
        { code: 1, name: vm.$i18n('KWR003_218') }
      ]);

      vm.settingListItems.subscribe((newList) => {
        if (!newList || newList.length <= 0) {
          vm.isSelectAll(false);
          return;
        }
        //Check if all the values in the settingListItems array are true:
        let isSelectedAll: any = vm.settingListItems().every(item => item.isChecked() === true);
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
      $("#multiGridList").ntsFixedTable({ height: 368 });
    }

    addRowItem(newRow?: SettingForPrint) {
      let vm = this,
        row: SettingForPrint = newRow;

      if (!newRow) {
        let lastItem: any = _.last(vm.settingListItems());
        let id = lastItem ? lastItem.id : 1;
        row = new SettingForPrint(id, null, 0, null, false);
      }

      row.isChecked.subscribe((value: boolean) => {
        vm.settingListItems.valueHasMutated();
      });

      vm.settingListItems.push(row);
    }

    addNewRow() {
      let vm = this;
      vm.addRowItem();
    }

    registerSetting() {
      let vm = this;
    }

    deteleSetting() {
      let vm = this;
      //get all items that will be remove
      let listCheckedItems: Array<any> = vm.settingListItems().filter((row) => row.isChecked() === true);
      if (listCheckedItems.length <= 0) return;

      //get all items that will be not remove
      let listNotCheckedItems: Array<any> = vm.settingListItems().filter((row) => row.isChecked() === false);
      vm.settingListItems(listNotCheckedItems);
    }

    /**
     * Duplicate Setting
     * */

    showDialogC() {
      let vm = this;
      
    }

    closeDialog() {
      let vm = this;

      vm.$window.close();
    }

    createSettingListItems() {
      let vm = this;

      for (let i = 0; i < 20; i++) {
        let newIitem = new SettingForPrint(i + 1, '予定勤務種類 ' + i, 0, 'Hanoi Vietnam' + i, false);
        vm.addRowItem(newIitem);
      }

      _.orderBy(vm.settingListItems(), ['id', 'name'], ['asc', 'asc']);

      vm.isEnaleAddButton(true);
      if (vm.settingListItems().length > 0) vm.isEnaleRemoveButton(true);
    }

    getSettingListForPrint(code: string) {
      let vm = this;
      if (!_.isNil(code)) {
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

      _.forEach(vm.settingListItems(), (row, index) => {
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
      this.name(name);
      this.setting(setting);
      this.isChecked(checked || false);
      this.selectionItem = selectionItem + '->' + setting;
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