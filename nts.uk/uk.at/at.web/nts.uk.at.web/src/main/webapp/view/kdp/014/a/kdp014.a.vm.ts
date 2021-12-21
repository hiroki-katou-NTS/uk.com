/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kdp014.a {
  
  import block  = nts.uk.ui.block;
  import dialog = nts.uk.ui.dialog.info;
  const API = {
    INSERT_UPDATE_STAMPING_SETTING: "at/record/stampmanagement/setting/preparation/smartphonestamping/employee/insertUpdateStampingSetting",
    GET_STATUS_STAMPING_EMP: "at/record/stampmanagement/setting/preparation/smartphonestamping/employee/getStatusStampingEmpl",
    GET_ONE_BY_EMPID: "at/record/stampmanagement/setting/preparation/smartphonestamping/employee/findOneById",
    REMOVESTAMP: "at/record/stampmanagement/setting/preparation/smartphonestamping/employee/delete"
  };

  @bean()
  export class ViewModel extends ko.ViewModel {

selectedDate: KnockoutObservable<string> = ko.observable(moment().format('YYYY/MM/DD'));
listComponentOption: any;
alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
isShowWorkPlaceName: KnockoutObservable<boolean> = ko.observable(false);

employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray([]);
selectedEmployee: KnockoutObservableArray<EmployeeSearchDto> = ko.observableArray([]);
selectedEmployeeCode : KnockoutObservable<string> = ko.observable(null);
selectedEmployeeName :  KnockoutObservable<string> = ko.observable(null);
listEmplId : KnockoutObservableArray<string> = ko.observableArray([]);
listEmployee: EmployeeSearchDto[] ;
// Radio 
itemList: KnockoutObservableArray<any>;
selectedId: KnockoutObservable<number>;
enableLocation: KnockoutObservable<boolean>;
enableStamp:KnockoutObservable<boolean>;
enabledelete:KnockoutObservable<boolean>;
itemList2: KnockoutObservableArray<any>;
selectedId2: KnockoutObservable<number>;
NEW_MODE = 1;
UPDATE_MODE = 2;
UNSELECT_MODE = 3;
mode: KnockoutObservable<number> = ko.observable(null);
isStart = true;
created() {
  const self = this;
  
  // Radio
  self.itemList = ko.observableArray([
    new BoxModel(1, nts.uk.resource.getText('KDP014_7')),
    new BoxModel(0, nts.uk.resource.getText('KDP014_8')),
  ]);
  self.selectedId = ko.observable(null);
  self.enableLocation = ko.observable(false);

  self.itemList2 = ko.observableArray([
    new BoxModel(2, nts.uk.resource.getText('KDP014_10')),
    new BoxModel(1, nts.uk.resource.getText('KDP014_11')),
    new BoxModel(0, nts.uk.resource.getText('KDP014_12'))
  ])
  self.selectedId2 = ko.observable(null);
  self.enableStamp = ko.observable(false);
  self.enabledelete = ko.observable(false);

  self.applyKCP005ContentSearch([]);
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
    baseDate: self.selectedDate(),
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
    * Self-defined function: Return data from CCG001
    * @param: data: the data return from CCG001
    */
    returnDataFromCcg001: function(data: Ccg001ReturnedData) {
      self.applyKCP005ContentSearch(data.listEmployee);
      self.selectedEmployee(data.listEmployee);
      self.selectedDate(data.baseDate);
    }
    // Start component
  }
  $('#ccg001component').ntsGroupComponent(ccg001ComponentOption); // '#com-ccg-001' is the component container's id

  self.selectedEmployeeCode.subscribe(newValue => {
    if (!nts.uk.text.isNullOrEmpty(newValue)) {
        self.getDetail(self.selectedEmployeeCode());
    } 
});

self.mode.subscribe(function(value) {
  if(self.isStart == true){
    self.enableLocation(false);
  }else {
    self.enableLocation(true);
  }
});

self.selectedId.subscribe(function(value){
  if(self.selectedId() == 1 ){
    self.enableStamp(true);
    self.selectedId2(0);
  }
  if(self.selectedId() == 0)  {
     self.enableStamp(false);
  }
});
self.mode(self.UNSELECT_MODE);
}

register() {
  const self = this;

  const data = {
    employeeId: self.selectedEmployeeCode(),
    locationInformation: self.selectedId(),
    isLimitArea: self.selectedId2()
  };

  self.$ajax(API.INSERT_UPDATE_STAMPING_SETTING,data).then((res: any) => {
      if(res){
        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
        self.selectedId(res.isLimitArea);
        self.selectedId2(res.locationInformation);
      }
  });
  self.enabledelete(true);
  self.getAll();
}

getDetail(employmentCategoryCode: string) {
  const self = this;
  // vm.isShowButton(true);
  console.log(self.employeeList());
  let employeeModel: UnitModel = _.find(self.employeeList(), item => {
      return item.code === employmentCategoryCode;
  });
  const data = {
    employeeId: employeeModel.code
  };
  self.$ajax(API.GET_ONE_BY_EMPID, data).then((res: any) => {
    if (res) {
      self.selectedId(res.isLimitArea);
      self.selectedId2(res.locationInformation);
      self.enabledelete(true);
    } else {
      self.selectedId(0);
      self.selectedId2(0);
      self.enabledelete(false);
    }
    self.isStart = false;
    self.selectedEmployeeCode(employeeModel.code);
    self.selectedEmployeeName(employeeModel.name);
    self.mode(self.UPDATE_MODE);
  });
}

applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): void {
  const self = this;
  const firstcode = dataList.length > 0 ? dataList[0].employeeCode : null;
  self.employeeList.removeAll();
  let employeeSearchs: Array<UnitModel> = [];
  for (let employeeSearch of dataList) {
    let employee: UnitModel = {
      code: employeeSearch.employeeCode,
      name: employeeSearch.employeeName
    };
    self.listEmplId.push(employeeSearch.employeeCode);
    employeeSearchs.push(employee);
  }
  self.$ajax(API.GET_STATUS_STAMPING_EMP, self.listEmplId()).then((res: []) => {
    self.alreadySettingList.removeAll();
    res.forEach(item => {
      self.alreadySettingList.push({
        code: item,
        isAlreadySetting: true
      })
    });
    self.employeeList(employeeSearchs);
    self.listComponentOption = {
      isShowAlreadySet: true,
      listType: ListType.EMPLOYEE,
      employeeInputList: self.employeeList,
      selectType: SelectType.SELECT_BY_SELECTED_CODE,
      selectedCode: self.selectedEmployeeCode,
      isDialog: false,
      alreadySettingList: self.alreadySettingList,
      maxWidth: 450,
      maxRows: 20
    };
    $('#kcp005component').ntsListComponent(self.listComponentOption);
    self.start();
  });
  if(firstcode !=null){
    
  const data = {
    employeeId: firstcode
  };
    self.$ajax(API.GET_ONE_BY_EMPID, data).then((res: any) => {
      if (res) {
        self.selectedId(res.isLimitArea);
        self.selectedId2(res.locationInformation);
      } else {
        self.selectedId(0);
        self.selectedId2(0);
      }
      self.isStart = false;
      self.selectedEmployeeCode(dataList[0].employeeCode);
      self.selectedEmployeeName(dataList[0].employeeName);
      self.mode(self.UPDATE_MODE);
    });
  }
}

getAll(){
  const self = this;
  self.$ajax(API.GET_STATUS_STAMPING_EMP, self.listEmplId()).then((res: []) => {
    self.alreadySettingList.removeAll();
    res.forEach(item => {
      self.alreadySettingList.push({
        code: item,
        isAlreadySetting: true
      })
    });
    // self.employeeList(employeeSearchs);
    self.listComponentOption = {
      isShowAlreadySet: true,
      listType: ListType.EMPLOYEE,
      employeeInputList: self.employeeList,
      selectType: SelectType.SELECT_BY_SELECTED_CODE,
      selectedCode: self.selectedEmployeeCode,
      isDialog: false,
      alreadySettingList: self.alreadySettingList,
      maxWidth: 450,
      maxRows: 20
    };
    $('#kcp005component').ntsListComponent(self.listComponentOption);
    self.start();
  });
}

public remove() {
  const vm = this;
  const data = {
    employeeId: vm.selectedEmployeeCode()
  };
  vm.$dialog.confirm({ messageId: "Msg_18" })
    .then((result: 'no' | 'yes' | 'cancel') => {
      if (result === 'yes') {
        vm.$blockui('grayout');
        vm.$ajax(API.REMOVESTAMP,data).then((res: any)  => {
            vm.$blockui('clear');
            vm.getAll();
            vm.$dialog.info({ messageId: "Msg_16" });
          })
          .fail((res) => {
            vm.$blockui('clear');
            vm.$dialog.alert({ messageId: res.messageId });
          });
          vm.getDetail(vm.selectedEmployeeCode());
      }
    });
}

start(): JQueryPromise<any> {
  let self = this;
  let dfd = $.Deferred();
  block.invisible();
  block.clear();
  dfd.resolve();
  $("kcp005component").focus();
  return dfd.promise();
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
    code: string;
    isAlreadySetting: boolean;
  }

  // export class UnitAlreadySettingModel {
  //   code: string;
  //   isAlreadySetting: boolean;
  //   constructor(code: string, isAlreadySetting: boolean){
  //     var self = this;
  //     self.code = code;
  //     self.isAlreadySetting = isAlreadySetting;
  //   }
  // }

  class BoxModel {
    id: number;
    name: string;
    constructor(id: any, name: any){
        var self = this;
        self.id = id;
        self.name = name;
    }
  }
}

