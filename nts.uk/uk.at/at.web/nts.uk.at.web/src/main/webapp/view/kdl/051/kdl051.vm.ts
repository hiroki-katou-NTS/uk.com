/// <reference path='../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kdl051.screenModel {
  @bean()
  export class ViewModel extends ko.ViewModel {
    listComponentOption: any;
    employeeList: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedCode: KnockoutObservable<string> = ko.observable('');
    selectedName: KnockoutObservable<string> = ko.observable('');
    code:  KnockoutObservable<string> = ko.observable('');
    error: KnockoutObservable<boolean> = ko.observable(false);
    alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
    tableDatas: KnockoutObservableArray<any> = ko.observableArray([
        { id: '1', date: '2020/10/07', periodDate: '10:00', classification: 'test' },
        { id: '2', date: '2020/10/07', periodDate: '10:00', classification: 'test' },
        { id: '3', date: '2020/10/07', periodDate: '10:00', classification: 'test' },
        { id: '4', date: '2020/10/07', periodDate: '10:00', classification: 'test' },
        { id: '5', date: '2020/10/07', periodDate: '10:00', classification: 'test' },
        { id: '6', date: '2020/10/07', periodDate: '10:00', classification: 'test' }
      ]);
    tableColumns: KnockoutObservableArray<any> = ko.observableArray([
      { headerText: '', prop: 'id', width: 20 },
      { headerText: this.$i18n('KDL051_7'), prop: 'date', width: 150 },
      { headerText: this.$i18n('KDL051_8'), prop: 'periodDate', width: 100 },
      { headerText: this.$i18n('KDL051_9'), prop: 'classification', width: 100 }
    ]);
    created() {
      const vm =this;
      vm.employeeList = ko.observableArray<any>([
        { id: '1a', code: '1', name: 'Angela Babykasjgdkajsghdkahskdhaksdhasd', workplaceName: 'HN' },
        { id: '2b', code: '2', name: 'Xuan Toc Doaslkdhasklhdlashdhlashdl', workplaceName: 'HN' },
        { id: '3c', code: '3', name: 'Park Shin Hye', workplaceName: 'HCM' },
        { id: '3d', code: '4', name: 'Vladimir Nabokov', workplaceName: 'HN' }
        ]);
      vm.alreadySettingList = ko.observableArray([
        { code: '1', isAlreadySetting: true },
        { code: '2', isAlreadySetting: true }
      ]);
      vm.listComponentOption = {
        isShowAlreadySet: false,
        isMultiSelect: false,
        listType: ListType.EMPLOYEE,
        employeeInputList: vm.employeeList,
        selectType: SelectType.SELECT_BY_SELECTED_CODE,
        selectedCode: vm.selectedCode,
        isDialog: false,
        isShowNoSelectRow: false,
        alreadySettingList: ko.observableArray([]),
        isShowWorkPlaceName: false,
        isShowSelectAllButton: false,
        maxRows: 15
    };
      vm.selectedCode.subscribe(value =>{
        if(value){
          vm.selectedName(_.find(vm.employeeList(), ['code', value]).name);
        }
      });
    $('#component-items-list').ntsListComponent(vm.listComponentOption);
    }
    close() {
      nts.uk.ui.windows.close();
  }
  }
  export class ListType {
    static EMPLOYMENT = 1;
    static Classification = 2;
    static JOB_TITLE = 3;
    static EMPLOYEE = 4;
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