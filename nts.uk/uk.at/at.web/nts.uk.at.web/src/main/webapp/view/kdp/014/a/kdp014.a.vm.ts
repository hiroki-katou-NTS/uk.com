/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kdp014.a {
  
  import block  = nts.uk.ui.block;

  const API = {
    INSERT_UPDATE_STAMPING_SETTING: "at/record/stampmanagement/setting/preparation/smartphonestamping/employee/insertUpdateStampingSetting",
    GET_STATUS_STAMPING_EMP: "at/record/stampmanagement/setting/preparation/smartphonestamping/employee/getStatusStampingEmpl"
  };

  @bean()
  export class ViewModel extends ko.ViewModel {

selectedDate: KnockoutObservable<string> = ko.observable(moment().format('YYYY/MM/DD'));
listComponentOption: any;
alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([new UnitAlreadySettingModel("設定済", true)]);
isShowWorkPlaceName: KnockoutObservable<boolean> = ko.observable(false);

employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray([]);
selectedEmployee: KnockoutObservableArray<EmployeeSearchDto> = ko.observableArray([]);
selectedEmployeeCode : KnockoutObservable<string> = ko.observable(null);
selectedEmployeeName :  KnockoutObservable<string> = ko.observable(null);
// Radio 
itemList: KnockoutObservableArray<any>;
selectedId: KnockoutObservable<number>;
enable: KnockoutObservable<boolean>;

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
    new BoxModel(2, nts.uk.resource.getText('KDP014_8')),
  ]);
  self.selectedId = ko.observable(null);
  self.enable = ko.observable(true);

  self.itemList2 = ko.observableArray([
    new BoxModel(1, nts.uk.resource.getText('KDP014_10')),
    new BoxModel(2, nts.uk.resource.getText('KDP014_11')),
    new BoxModel(3, nts.uk.resource.getText('KDP014_12'))
  ])
  self.selectedId2 = ko.observable(1);

  const data = {
    employeeId: '000000000003',
    locationInformation: 1,
    isLimitArea: 1
  }
  // self.$ajax(API.INSERT_UPDATE_STAMPING_SETTING, data).then((res: any) => {
  //   console.log(res);
  // });

  // self.$ajax(API.GET_STATUS_STAMPING_EMP, ['000000000003']).then((res: any) => {
  //   console.log(res);
  // });

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
    } else {
       
    }
});


 
self.mode.subscribe(function(value) {
  if(self.isStart == true){
    $( "#A1_1" ).addClass("disabled");
    $( "#A1_2" ).addClass("disabled");
    $(".A_4").addClass("disabled");
    $(".A_5").hide();
    $(".rd1").hide();
    $(".rd2").hide();
  }
  else {
    $( "#A1_1" ).removeClass("disabled");
    $( "#A1_2" ).removeClass("disabled");
    $(".A_4").removeClass("disabled");
    $(".A_5").show();
    $(".rd1").show();
    $(".rd2").show();
  }
});
self.mode(self.UNSELECT_MODE);
}


getDetail(employmentCategoryCode: string) {
  const self = this;
  // vm.isShowButton(true);

  let employeeModel: UnitModel = _.find(self.employeeList(), item => {
      return item.code === employmentCategoryCode;
  });
  
  self.isStart = false;
  self.selectedEmployeeCode(employeeModel.code);
  self.selectedEmployeeName(employeeModel.name);
  self.mode(self.UPDATE_MODE);

}

applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): void {
  const self = this;
  self.employeeList.removeAll();
  let employeeSearchs: Array<UnitModel> = [];
  for (let employeeSearch of dataList) {
    let employee: UnitModel = {
      code: employeeSearch.employeeCode,
      name: employeeSearch.employeeName
    };
    employeeSearchs.push(employee);
  }
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
}




start(): JQueryPromise<any> {
  let self = this;
  let dfd = $.Deferred();
  block.invisible();
  block.clear();
  dfd.resolve();
  $("#A4_2").focus();
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

  export class UnitAlreadySettingModel {
    code: string;
    isAlreadySetting: boolean;
    constructor(code: string, isAlreadySetting: boolean){
      var self = this;
      self.code = code;
      self.isAlreadySetting = isAlreadySetting;
    }
  }

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

