/// <reference path='../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kdl052.screenModel {

  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;

  @bean()
  export class ViewModel extends ko.ViewModel {
    listComponentOption: any;
    selectedCode: KnockoutObservable<string> = ko.observable('');
    multiSelectedCode: KnockoutObservableArray<string> = ko.observableArray([]);
    isShowAlreadySet: KnockoutObservable<boolean> = ko.observable(false);
    alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
    isDialog: KnockoutObservable<boolean> = ko.observable(false);
    isShowNoSelectRow: KnockoutObservable<boolean> = ko.observable(false);
    isMultiSelect: KnockoutObservable<boolean> = ko.observable(false);
    isShowWorkPlaceName: KnockoutObservable<boolean> = ko.observable(false);
    isShowSelectAllButton: KnockoutObservable<boolean> = ko.observable(false);
    disableSelection : KnockoutObservable<boolean> = ko.observable(false);
    employeeList: KnockoutObservableArray<EmployeeModel> = ko.observableArray([]);
    baseDate: any;
    selectedItemName: KnockoutObservable<string> = ko.observable('');
    manageDisabled : KnockoutObservable<boolean> = ko.observable(true);
    // Value for ntsGridList A2_4
    currentCode: KnockoutObservable<any> = ko.observable('');
    tableDatas: KnockoutObservableArray<any> = ko.observableArray([
      { id: '1', date: '2020/10/07', periodDate: '10:00', classification: 'test' },
      { id: '2', date: '2020/10/07', periodDate: '10:00', classification: 'test' },
      { id: '3', date: '2020/10/07', periodDate: '10:00', classification: 'test' },
      { id: '4', date: '2020/10/07', periodDate: '10:00', classification: 'test' },
      { id: '5', date: '2020/10/07', periodDate: '10:00', classification: 'test' },
      { id: '6', date: '2020/10/07', periodDate: '10:00', classification: 'test' }
    ]);
    tableColumns: KnockoutObservableArray<any> = ko.observableArray([
      { headerText: '', width: 25 , template:'<div style = "background-color: #CFF1A5;">${id}</div>', key: 'id'},
      { headerText: this.$i18n('KDL052_7'), prop: 'date', width: 112 },
      { headerText: this.$i18n('KDL052_8'), prop: 'periodDate', width: 95 },
      { headerText: this.$i18n('KDL052_9'), prop: 'classification', width: 95 }
    ]);
    
    created(params: any) {
      let vm = this;
      vm.getData(params);
      vm.selectedCode = ko.observable('')
      vm.baseDate = ko.observable(new Date());
      vm.selectedCode = ko.observable('1');
      vm.multiSelectedCode = ko.observableArray(['0', '1', '4']);
      vm.alreadySettingList = ko.observableArray([
        {code: '1', isAlreadySetting: true},
        {code: '2', isAlreadySetting: true}
      ]);
      vm.isDialog = ko.observable(false);
      vm.isShowNoSelectRow = ko.observable(false);
      vm.isMultiSelect = ko.observable(false);
      vm.isShowWorkPlaceName = ko.observable(false);
      vm.isShowSelectAllButton = ko.observable(false);
      vm.disableSelection = ko.observable(false);
      
      vm.listComponentOption = {
        isShowAlreadySet: vm.isShowAlreadySet(),
        isMultiSelect: vm.isMultiSelect(),
        listType: ListType.EMPLOYEE,
        employeeInputList: vm.employeeList,
        selectType: SelectType.SELECT_BY_SELECTED_CODE,
        selectedCode: vm.selectedCode,
        isDialog: vm.isDialog(),
        isShowNoSelectRow: vm.isShowNoSelectRow(),
        alreadySettingList: vm.alreadySettingList,
        isShowWorkPlaceName: vm.isShowWorkPlaceName(),
        isShowSelectAllButton: vm.isShowSelectAllButton(),
        disableSelection : vm.disableSelection()
      };
    }

    getData(params: any) {
      const vm = this
          , command = {
            lstEmployees: params.employeeList,
            baseDate: params.baseDate
          }
      // Call api get data
      vm.$ajax('ctx/at/share/vacation/setting/nursingleave/find/childnursingleave', command).done((result: any) => {
        if (result && result.lstEmployee) {
          if (result.lstEmployee.length > 0) {
            let mappedList =
                _.map(result.lstEmployee, (item: any) => {
                    return { id: item.employeeId, code: item.employeeCode, name: item.employeeName };
                });
            vm.employeeList(mappedList);
            vm.selectedCode(mappedList[0].code);
            // vm.changeEmployee(mappedList[0]);
            // vm.changeData(data);
            $('#component-items-list').ntsListComponent(vm.listComponentOption);
          }
        }
        // 取得したObject＜介護看護休暇設定＞．管理区分をチェックする。
        if (result && result.managementClassification) {
          if (result.managementClassification.manageType === 1) {
            vm.manageDisabled(true);
          } else {
            vm.manageDisabled(false);
          }
        }
        // do any bussiness logic after request done at here
      });
    }

    // changeEmployee(item: any) {
    //   const vm = this;
    //   if (item && item.employeeCode) {
    //     vm.selectedItemName = `${item.employeeCode}”　”　＋　${item.employeeCode}`;
    //   }
      
    // }



    close() {
      nts.uk.ui.windows.close();
    }
  }

  export class NtsGridListColumn {
    headerText?: string;
    prop?: string;
    width?: number;
    hidden?: boolean;
  }

  export class ItemModel {
    date: any;
    day: any;
    note: string;

    constructor(init?: Partial<ItemModel>) {
      $.extend(this, init);
    }
  }

  export class ListType {
    static EMPLOYMENT = 1;
    static Classification = 2;
    static JOB_TITLE = 3;
    static EMPLOYEE = 4;
  }

  export class EmployeeModel {
    id?: string;
    code?: string;
    name?: string;
  }

  export class SelectType {
    static SELECT_BY_SELECTED_CODE = 1;
    static SELECT_ALL = 2;
    static SELECT_FIRST_ITEM = 3;
    static NO_SELECT = 4;
  }
  
  export interface UnitAlreadySettingModel {
    code: string;
    isAlreadySetting: boolean;
  }
}