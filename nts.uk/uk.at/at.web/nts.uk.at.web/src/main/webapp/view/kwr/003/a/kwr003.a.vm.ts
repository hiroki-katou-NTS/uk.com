/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr003.a {

  import ComponentOption = kcp.share.list.ComponentOption;

  //===============================================
  const DATE_FORMAT_YYYY_MM_DD = "YYYY/MM/DD";


  @bean()
  class ViewModel extends ko.ViewModel {

    // start variable of CCG001
    ccg001ComponentOption: GroupOption;
    // end variable of CCG001

    //panel left
    yearMonth: KnockoutObservable<number> = ko.observable(202010);;

    //panel right
    rdgSelectedId: KnockoutObservable<number> = ko.observable(0);
    standardSelectedCode: KnockoutObservable<string> = ko.observable(null);
    freeSelectedCode: KnockoutObservable<string> = ko.observable(null);

    isEnableSelectedCode: KnockoutObservable<boolean> = ko.observable(true);
    zeroDisplayClassification: KnockoutObservable<number> = ko.observable(0);
    specifyingPageBreaks: KnockoutObservable<number> = ko.observable(0);

    // start declare KCP005
    listComponentOption: any;
    selectedCode: KnockoutObservable<string>;
    multiSelectedCode: KnockoutObservableArray<string>;
    isShowAlreadySet: KnockoutObservable<boolean>;
    alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
    isDialog: KnockoutObservable<boolean>;
    isShowNoSelectRow: KnockoutObservable<boolean>;
    isMultiSelect: KnockoutObservable<boolean>;
    isShowWorkPlaceName: KnockoutObservable<boolean>;
    isShowSelectAllButton: KnockoutObservable<boolean>;
    disableSelection : KnockoutObservable<boolean>;
    
    employeeList: KnockoutObservableArray<UnitModel>;
    baseDate: KnockoutObservable<Date>;
    // end KCP005

    constructor(params: any) {
      super();
      let vm = this;

      vm.rdgSelectedId.subscribe((value) => {
        vm.isEnableSelectedCode(value === StandardOrFree.Standard);
      });

      vm.CCG001_load();
      vm.KCP005_load();
    }

    created(params: any) {
      let vm = this;
    }

    mounted() {
      let vm = this;
    }

    CCG001_load() {
      let vm = this;
      // Set component option
      vm.ccg001ComponentOption = {
        /** Common properties */
        systemType: 2,
        showEmployeeSelection: true,
        showQuickSearchTab: true,
        showAdvancedSearchTab: true,
        showBaseDate: true,
        showClosure: true,
        showAllClosure: true,
        showPeriod: true,
        periodFormatYM: false,

        /** Required parameter */
        baseDate: moment().toISOString(),
        //periodStartDate: periodStartDate,
        //periodEndDate: periodEndDate,
        inService: true,
        leaveOfAbsence: true,
        closed: true,
        retirement: true,

        /** Quick search tab options */
        showAllReferableEmployee: false,
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
        isMutipleCheck: true,

        /**
        * vm-defined function: Return data from CCG001
        * @param: data: the data return from CCG001
        */
        returnDataFromCcg001: function (data: Ccg001ReturnedData) {
        }
      }
      // Start component
      $('#CCG001').ntsGroupComponent(vm.ccg001ComponentOption);
    }

    KCP005_load() {
      let vm = this;

      // start define KCP005
      vm.baseDate = ko.observable(new Date());
      vm.selectedCode = ko.observable('1');
      vm.multiSelectedCode = ko.observableArray(['0', '1', '4']);
      vm.isShowAlreadySet = ko.observable(false);
      vm.alreadySettingList = ko.observableArray([
        { code: '1', isAlreadySetting: true },
        { code: '2', isAlreadySetting: true }
      ]);
      vm.isDialog = ko.observable(true);
      vm.isShowNoSelectRow = ko.observable(false);
      vm.isMultiSelect = ko.observable(true);
      vm.isShowWorkPlaceName = ko.observable(true);
      vm.isShowSelectAllButton = ko.observable(true);
      //vm.disableSelection = ko.observable(false);
      //vm.employeeList = ko.observableArray<UnitModel>([]);
      vm.employeeList = ko.observableArray<UnitModel>([
        { id: '1a', code: '1', name: 'Angela Babykas', affiliationName: 'HN'},
        { id: '2b', code: '2', name: 'Xuan Toc Doas', affiliationName: 'HN'},
        { id: '3c', code: '3', name: 'Park Shin Hye', affiliationName: 'HCM'},
        { id: '3d', code: '4', name: 'Vladimir Nabokov', affiliationName: 'HN'}
      ]);

      vm.listComponentOption = {
        isShowAlreadySet: vm.isShowAlreadySet(),
        isMultiSelect: vm.isMultiSelect(),
        listType: ListType.EMPLOYEE,
        employeeInputList: vm.employeeList,
        selectType: SelectType.SELECT_BY_SELECTED_CODE,
        selectedCode: vm.multiSelectedCode,
        isDialog: vm.isDialog(),
        isShowNoSelectRow: vm.isShowNoSelectRow(),
        alreadySettingList: vm.alreadySettingList,
        isShowWorkPlaceName: vm.isShowWorkPlaceName(),
        isShowSelectAllButton: vm.isShowSelectAllButton(),
        isSelectAllAfterReload: false,
        tabindex: 5,
        maxRows: 10
      };

      $('#kcp005').ntsListComponent(vm.listComponentOption)
    }
  }
  export class ItemModel {
    code: string;
    name: string;

    constructor(code: string, name: string) {
      this.code = code;
      this.name = name;
    }
  }

  export class BoxModel {
    id: number;
    name: string;
    constructor(id: number, name: string) {
      var vm = this;
      vm.id = id;
      vm.name = name;
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

  // start CCG001
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

  export interface Ccg001ReturnedData {
    baseDate: string; // 基準日
    closureId?: number; // 締めID
    periodStart: string; // 対象期間（開始)
    periodEnd: string; // 対象期間（終了）
    listEmployee: Array<EmployeeSearchDto>; // 検索結果
  }

  export interface EmployeeSearchDto {
    employeeId: string;
    employeeCode: string;
    employeeName: string;
    workplaceId: string;
    workplaceName: string;
  }

  export interface UnitModel {
    id?: string;
    code: string;
    name?: string;
    affiliationName?: string;
    isAlreadySetting?: boolean;
    optionalColumn?: any;
  }

  export interface UnitAlreadySettingModel {
    code: string;
    isAlreadySetting: boolean;
  }

  export enum StandardOrFree {
    Standard = 0,
    Free = 1
  }

  export enum DisplayClassification {
    Standard = 0,
    Free = 1
  }
  export enum SpecifyingPageBreaks {
    Standard = 0,
    Free = 1
  }
}