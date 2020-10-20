/// <reference path='../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kdl051.screenModel {
  import getShared = nts.uk.ui.windows.getShared;
  import text = nts.uk.resource.getText;
  import formatById = nts.uk.time.format.byId;

  const API = {
    startPage: "at/shared/holidaysetting/employee/startPage",
    changeId: "at/record/monthly/nursingleave/changeId"
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

      tableColumns: KnockoutObservableArray<any> = ko.observableArray([
        { headerText: '', width: 20 , template:'<div style = "background-color: #CFF1A5;">${id}</div>', key: 'id'},
        { headerText: this.$i18n('KDL051_7'), prop: 'date', width: 150 },
        { headerText: this.$i18n('KDL051_8'), prop: 'periodDate', width: 100 },
        { headerText: this.$i18n('KDL051_9'), prop: 'classification', width: 100 }
      ]);

    created() {
      const vm =this;
      // get Param
      let shareParam = getShared('KDL051A_PARAM');
      let startParam = {
        baseDate: shareParam.baseDate,
        employeeIds: shareParam.employeeIds
      }
      // call API startPage
      vm.$ajax(API.startPage, startParam).then((res: any)=>{
        if(res && res.lstEmp) {
          let mappedList: any =
                        _.map(res.lstEmp, item => {
                            return { id: item.employeeId, code: item.employeeCode, name: item.employeeName };
                        });
          vm.employeeList(mappedList);
          vm.selectedCode(mappedList[0].code);
          if(res.nursingLeaveSt.manageType !== null && res.nursingLeaveSt.manageType ===0){
              vm.error(true);
          }
        }
      })

      vm.alreadySettingList = ko.observableArray([
        { code: '1', isAlreadySetting: true },
        { code: '2', isAlreadySetting: true }
      ]);

      // KCP005
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
          vm.onChangeId(value);
        }
      });
      $('#component-items-list').ntsListComponent(vm.listComponentOption);
      
    }
    public onChangeId(sId : string) {
      const vm = this;
      let changeIDparam = {
        employeeId: sId
      }
      // call API changeId
      vm.$ajax(API.changeId, changeIDparam).then((res: any)=>{
      });
    }
    public genDateTime(date: number, time: number) {
      if(time) {
        return date + text('KDL051_17') + '  ' + formatById("Clock_Short_HM", time);
      }
      return date + text('KDL051_17')
    }

    public close() {
      const vm = this;
      vm.$window.close();
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