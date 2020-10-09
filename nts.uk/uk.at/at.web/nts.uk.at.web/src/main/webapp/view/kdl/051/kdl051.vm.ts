/// <reference path='../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kdl051.screenModel {
  import getShared = nts.uk.ui.windows.getShared;
  import text = nts.uk.resource.getText;
  import formatById = nts.uk.time.format.byId;

  const API = {
    startPage: "at/shared/holidaysetting/employee/startPage",
  };
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
   

    created() {
      const vm =this;
      let shareParam = getShared('KDL051A_PARAM');
      let startParam = {
        baseDate: shareParam.baseDate,
        employeeIds: shareParam.employeeIds
      }
      vm.$ajax(API.startPage, startParam).then((res: any)=>{
        if(res && res.lstEmp) {
          let mappedList: any =
                        _.map(res.lstEmp, item => {
                            return { id: item.employeeId, code: item.employeeCode, name: item.employeeName };
                        });
          vm.employeeList(mappedList);
          vm.selectedCode(mappedList[0].code);
              
          if(res.nursingLeaveSt.manageType !== null){
            if(res.nursingLeaveSt.manageType ===0) {
              vm.error(true);
            }
          }
        }
      })

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

    public genDateTime(date: number, time: number) {
      if(time) {
        return date + text('KDL051_17') + '  ' + formatById("Clock_Short_HM", time);
      }
      return date + text('KDL051_17')
    }

    public close() {
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