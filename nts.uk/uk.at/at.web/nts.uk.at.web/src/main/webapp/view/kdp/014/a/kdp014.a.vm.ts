/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kdp014.a {
  const API = {
    INSERT_UPDATE_STAMPING_SETTING: "at/record/stampmanagement/setting/preparation/smartphonestamping/employee/insertUpdateStampingSetting",
    GET_STATUS_STAMPING_EMP: "at/record/stampmanagement/setting/preparation/smartphonestamping/employee/getStatusStampingEmpl",
    GET_ONE_BY_EMPID: "at/record/stampmanagement/setting/preparation/smartphonestamping/employee/findOneById",
    REMOVESTAMP: "at/record/stampmanagement/setting/preparation/smartphonestamping/employee/delete"
  };
  const NEW_MODE = 1;
  const UPDATE_MODE = 2;
  const UNSELECT_MODE = 3;
  @bean()
  export class ViewModel extends ko.ViewModel {

    selectedDate: KnockoutObservable<string> = ko.observable(moment().format('YYYY/MM/DD'));
    listComponentOption: any;
    alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
    isShowWorkPlaceName: KnockoutObservable<boolean> = ko.observable(false);
    employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray([]);
    selectedEmployee: KnockoutObservableArray<EmployeeSearchDto> = ko.observableArray([]);
    selectedEmployeeId: KnockoutObservable<string> = ko.observable(null);
    selectedEmployeeCode: KnockoutObservable<string> = ko.observable(null);
    selectedEmployeeName: KnockoutObservable<string> = ko.observable(null);
    listEmplId: KnockoutObservableArray<string> = ko.observableArray([]);
    listEmployee: EmployeeSearchDto[];
    // Radio
    itemList: KnockoutObservableArray<any>;
    listId: KnockoutObservableArray<any>;
    selectedId: KnockoutObservable<number>;
    selectedEmpl: KnockoutObservable<number>;
    enableLocation: KnockoutObservable<boolean>;
    enableStamp: KnockoutObservable<boolean>;
    enabledelete: KnockoutObservable<boolean>;
    itemList2: KnockoutObservableArray<any>;
    selectedId2: KnockoutObservable<number>;
    mode: KnockoutObservable<number> = ko.observable(null);
    isStart = true;
    created() {
      const vm = this;
      // Radio
      vm.itemList = ko.observableArray([
        new BoxModel(1, nts.uk.resource.getText('KDP014_7')),
        new BoxModel(0, nts.uk.resource.getText('KDP014_8')),
      ]);
      vm.selectedId = ko.observable(0);
      vm.enableLocation = ko.observable(false);

      vm.itemList2 = ko.observableArray([
        new BoxModel(2, nts.uk.resource.getText('KDP014_10')),
        new BoxModel(1, nts.uk.resource.getText('KDP014_11')),
        new BoxModel(0, nts.uk.resource.getText('KDP014_12'))
      ]);
      vm.selectedId2 = ko.observable(0);
      vm.enableStamp = ko.observable(false);
      vm.enabledelete = ko.observable(false);

      vm.applyKCP005ContentSearch([]);
      // Set component option
      const ccg001ComponentOption: GroupOption = {
        /** Common properties */
        systemType: 2,
        showEmployeeSelection: false,
        showQuickSearchTab: true,
        showAdvancedSearchTab: true,
        showBaseDate: true,
        showClosure: true,
        showAllClosure: true,
        showPeriod: true,
        periodFormatYM: false,

        /** Required parameter */
        baseDate: vm.selectedDate(),
        periodStartDate: moment().toISOString(),
        periodEndDate: moment().toISOString(),
        inService: true,
        leaveOfAbsence: true,
        closed: true,
        retirement: true,

        /** Quick search tab options */
        showAllReferableEmployee: true,
        showOnlyMe: true,
        showSameDepartment: true,
        showSameDepartmentAndChild: true,
        showSameWorkplace: true,
        showSameWorkplaceAndChild: true,

        /** Advanced search properties */
        showEmployment: true,
        showDepartment: true,
        showWorkplace: true,
        showClassification: true,
        showJobTitle: true,
        showWorktype: true,
        isMutipleCheck: false,

        /**
        * vm-defined function: Return data from CCG001
        * @param: data: the data return from CCG001
        */
        returnDataFromCcg001: function (data: Ccg001ReturnedData) {
          vm.applyKCP005ContentSearch(data.listEmployee);
          vm.selectedEmployee(data.listEmployee);
          vm.selectedDate(data.baseDate);
        }
        // Start component
      }
      $('#ccg001component').ntsGroupComponent(ccg001ComponentOption); // '#com-ccg-001' is the component container's id

      vm.selectedEmployeeCode.subscribe(newValue => {
        if (!nts.uk.text.isNullOrEmpty(newValue)) {
          vm.getDetail(vm.selectedEmployeeCode());
        }
      });

      vm.mode.subscribe(function (value) {
        if (vm.isStart == true) {
          vm.enableLocation(false);
        } else {
          vm.enableLocation(true);
        }
      });

      vm.selectedId.subscribe(function (value) {
        if (vm.selectedId() == 1) {
          vm.enableStamp(true);
          vm.selectedId2(0);
        }
        if (vm.selectedId() == 0) {
          vm.enableStamp(false);
        }
      });
      vm.mode(UNSELECT_MODE);
    }
    mounted(): void {
      const vm = this;
      setTimeout(() => $('#kcp005component .ui-iggrid.nts-gridlist').focus(), 500);
    }

    register() {
      const vm = this;
      const data = {
        employeeId: vm.selectedEmployeeId(),
        locationInformation: vm.selectedId(),
        isLimitArea: vm.selectedId2()
      };

      vm.$ajax(API.INSERT_UPDATE_STAMPING_SETTING, data)
      .then(() => {
        vm.$dialog.info({ messageId: "Msg_15" });
        vm.enabledelete(true);
        vm.getDetail(vm.selectedEmployeeCode());
        vm.getAll();
      });
    }

    getDetail(employmentCategoryCode: string) {
      const vm = this;
      vm.$blockui("grayout");
      let employeeModel: UnitModel = _.find(vm.employeeList(), item => {
        return item.code === employmentCategoryCode;
      });
      if(employeeModel) {
        const selectedEmpl = employeeModel.id;
        vm.$ajax(`${API.GET_ONE_BY_EMPID}/${selectedEmpl}`).done((res: any) => {
          if (res) {
            vm.selectedId(res.isLimitArea);
            vm.selectedId2(res.locationInformation);
            vm.enabledelete(true);
          } else {
            vm.selectedId(0);
            vm.selectedId2(0);
            vm.enabledelete(false);
          }
          vm.isStart = false;
          vm.selectedEmployeeId(employeeModel.id);
          vm.selectedEmployeeCode(employeeModel.code);
          vm.selectedEmployeeName(employeeModel.name);
          vm.mode(UPDATE_MODE);
        }).always(() => {
          $("#A6_2").focus();
          vm.$blockui("clear");
        });
      }
    }

    applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): void {
      const vm = this;
      vm.$blockui("grayout");
      const firstcode = dataList.length > 0 ? dataList[0].employeeCode : null;
      vm.employeeList.removeAll();
      let employeeSearchs: Array<UnitModel> = [];
      for (let employeeSearch of dataList) {
        let employee: UnitModel = {
          id  : employeeSearch.employeeId,
          code: employeeSearch.employeeCode,
          name: employeeSearch.employeeName
        };
        vm.listEmplId.push(employeeSearch.employeeId);
        employeeSearchs.push(employee);
      }
      const dataEmpl = {
        listId: vm.listEmplId(),
      };

      vm.$ajax(API.GET_STATUS_STAMPING_EMP, dataEmpl).then((res: []) => {
        vm.alreadySettingList.removeAll();
        const convertAlreadySettingList: any = [];
        res.forEach(item => {
          vm.alreadySettingList.push({
            id: item,
            code: vm.convertIdToCode(employeeSearchs, item),
            isAlreadySetting: true
          })
          convertAlreadySettingList.push({
            code: vm.convertIdToCode(employeeSearchs, item),
            isAlreadySetting: true,
          })
        });
        vm.employeeList(employeeSearchs);
        vm.listComponentOption = {
          isShowAlreadySet: true,
          listType: ListType.EMPLOYEE,
          employeeInputList: vm.employeeList,
          selectType: SelectType.SELECT_BY_SELECTED_CODE,
          selectedCode: vm.selectedEmployeeCode,
          isDialog: false,
          alreadySettingList: ko.observableArray(convertAlreadySettingList)
        };
        $('#kcp005component').ntsListComponent(vm.listComponentOption);
      });


      if (firstcode != null) {
        const selectedEmpl = firstcode;
        vm.$ajax(`${API.GET_ONE_BY_EMPID}/${selectedEmpl}`).done((res: any) => {
          if (res) {
            vm.selectedId(res.isLimitArea);
            vm.selectedId2(res.locationInformation);
          } else {
            vm.selectedId(0);
            vm.selectedId2(0);
          }
          vm.isStart = false;
          vm.selectedEmployeeId(dataList[0].employeeId);
          vm.selectedEmployeeCode(dataList[0].employeeCode);
          vm.selectedEmployeeName(dataList[0].employeeName);
          vm.mode(UPDATE_MODE);
        }).always(() => {
          $("#A6_2").focus();
          vm.$blockui("clear");
        });
      }
    }

    convertIdToCode(empList: any, id: any){
      let employeeModel: UnitModel = _.find(empList, item => {
        return item.id === id;
      });
      return employeeModel ? employeeModel.code : "";
    }

    getAll() {
      const vm = this;
      const dataEmpl = {
        listId: vm.listEmplId(),
      };
      vm.$blockui("grayout");
      vm.$ajax(API.GET_STATUS_STAMPING_EMP, dataEmpl).then((res: []) => {
        vm.alreadySettingList.removeAll();
        const convertAlreadySettingList: any = [];
        res.forEach(item => {
          vm.alreadySettingList.push({
            id: item,
            code: vm.convertIdToCode(vm.employeeList(), item),
            isAlreadySetting: true
          })
          convertAlreadySettingList.push({
            code: vm.convertIdToCode(vm.employeeList(), item),
            isAlreadySetting: true,
          })
        });
        vm.listComponentOption = {
          isShowAlreadySet: true,
          listType: ListType.EMPLOYEE,
          employeeInputList: vm.employeeList,
          selectType: SelectType.SELECT_BY_SELECTED_CODE,
          selectedCode: vm.selectedEmployeeCode,
          isDialog: false,
          alreadySettingList: ko.observableArray(convertAlreadySettingList)
        };
        $('#kcp005component').ntsListComponent(vm.listComponentOption);
      }).always(() => vm.$blockui("clear"));
    }

    public remove() {
      const vm = this;
      const data = {
        employeeId: vm.selectedEmployeeId()
      };
      vm.$dialog.confirm({ messageId: "Msg_18" })
        .then((result: 'no' | 'yes' | 'cancel') => {
          if (result === 'yes') {
            vm.$blockui('grayout');
            vm.$ajax(API.REMOVESTAMP, data).then((res: any) => {
              vm.getAll();
              vm.$dialog.info({ messageId: "Msg_16" });
            })
              .fail((res) => {
                vm.$dialog.error({ messageId: res.messageId });
              }).always(() => {
                vm.$blockui("clear");
                vm.getDetail(vm.selectedEmployeeCode());
              });
          }
        });
    }
  }
  // Note: Defining these interfaces are optional
  export interface GroupOption {
    /** Common properties */
    showEmployeeSelection?: boolean; // 検索タイプ
    systemType: number; // システム区分
    showQuickSearchTab?: boolean; // クイック検索
    showAdvancedSearchTab?: boolean; // 詳細検索
    showBaseDate?: boolean; // 基準日利用
    showClosure?: boolean; // 就業締め日利用
    showAllClosure?: boolean; // 全締め表示
    showPeriod?: boolean; // 対象期間利用
    periodFormatYM?: boolean; // 対象期間精度
    maxPeriodRange?: string; // 最長期間
    showSort?: boolean; // 並び順利用
    nameType?: number; // 氏名の種類

    /** Required parameter */
    baseDate?: any; // 基準日 KnockoutObservable<string> or string
    periodStartDate?: any; // 対象期間開始日 KnockoutObservable<string> or string
    periodEndDate?: any; // 対象期間終了日 KnockoutObservable<string> or string
    dateRangePickerValue?: KnockoutObservable<any>;
    inService: boolean; // 在職区分
    leaveOfAbsence: boolean; // 休職区分
    closed: boolean; // 休業区分
    retirement: boolean; // 退職区分

    /** Quick search tab options */
    showAllReferableEmployee?: boolean; // 参照可能な社員すべて
    showOnlyMe?: boolean; // 自分だけ
    showSameDepartment?: boolean; //同じ部門の社員
    showSameDepartmentAndChild?: boolean; // 同じ部門とその配下の社員
    showSameWorkplace?: boolean; // 同じ職場の社員
    showSameWorkplaceAndChild?: boolean; // 同じ職場とその配下の社員

    /** Advanced search properties */
    showEmployment?: boolean; // 雇用条件
    showDepartment?: boolean; // 部門条件
    showWorkplace?: boolean; // 職場条件
    showClassification?: boolean; // 分類条件
    showJobTitle?: boolean; // 職位条件
    showWorktype?: boolean; // 勤種条件
    isMutipleCheck?: boolean; // 選択モード

    /** Optional properties */
    isInDialog?: boolean;
    showOnStart?: boolean;
    isTab2Lazy?: boolean;
    tabindex?: number;

    /** Data returned */
    returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
  }
  export interface EmployeeSearchDto {
    employeeId: string;
    employeeCode: string;
    employeeName: string;
    affiliationId: string; // departmentId or workplaceId based on system type
    affiliationName: string; // departmentName or workplaceName based on system type
  }
  export interface Ccg001ReturnedData {
    baseDate: string; // 基準日
    closureId?: number; // 締めID
    periodStart: string; // 対象期間（開始)
    periodEnd: string; // 対象期間（終了）
    listEmployee: Array<EmployeeSearchDto>; // 検索結果
  }

  export class ListType {
    static EMPLOYMENT = 1;
    static Classification = 2;
    static JOB_TITLE = 3;
    static EMPLOYEE = 4;
  }

  export interface UnitModel {
    id?: string;
    code: string;
    name?: string;
    workplaceName?: string;
    isAlreadySetting?: boolean;
    optionalColumn?: any;
  }

  export class SelectType {
    static SELECT_BY_SELECTED_CODE = 1;
    static SELECT_ALL = 2;
    static SELECT_FIRST_ITEM = 3;
    static NO_SELECT = 4;
  }

  export interface UnitAlreadySettingModel {
    id: string;
    code: string;
    isAlreadySetting: boolean;
  }

  // export class UnitAlreadySettingModel {
  //   code: string;
  //   isAlreadySetting: boolean;
  //   constructor(code: string, isAlreadySetting: boolean){
  //     var vm = this;
  //     vm.code = code;
  //     vm.isAlreadySetting = isAlreadySetting;
  //   }
  // }

  class BoxModel {
    id: number;
    name: string;
    constructor(id: any, name: any) {
      var vm = this;
      vm.id = id;
      vm.name = name;
    }
  }
}

