/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr003.a {
  import common = nts.uk.at.view.kwr003.common;
  import ComponentOption = kcp.share.list.ComponentOption;

  const WORK_STATUS = 'WorkStatus';
  const KWR003_B_INPUT = 'KWR003_WORK_STATUS_DATA';
  const KWR003_B_OUTPUT = 'KWR003WORK_STATUS_RETURN';
  const KWR003_SAVE_DATA = 'WorkScheduleStatusOutputConditions';

  const PATHS = {
    exportExcel: '',
    exportPDF: ''
  };

  @bean()
  class ViewModel extends ko.ViewModel {

    // start variable of CCG001
    ccg001ComponentOption: common.GroupOption;
    // end variable of CCG001

    //panel left
    dpkYearMonth: KnockoutObservable<number> = ko.observable(202010);;

    //panel right
    rdgSelectedId: KnockoutObservable<number> = ko.observable(0);
    standardSelectedCode: KnockoutObservable<string> = ko.observable(null);
    isEnableStdBtn: KnockoutObservable<boolean> = ko.observable(true);
    
    freeSelectedCode: KnockoutObservable<string> = ko.observable(null);
    isEnableSelectedCode: KnockoutObservable<boolean> = ko.observable(true);
    isEnableFreeBtn: KnockoutObservable<boolean> = ko.observable(true);

    zeroDisplayClassification: KnockoutObservable<number> = ko.observable(0);
    pageBreakSpecification: KnockoutObservable<number> = ko.observable(0);
    isWorker: KnockoutObservable<boolean> = ko.observable(true);
    settingListItems: KnockoutObservableArray<any> = ko.observableArray([]);

    // start declare KCP005
    listComponentOption: any;
    selectedCode: KnockoutObservable<string>;
    multiSelectedCode: KnockoutObservableArray<string>;
    isShowAlreadySet: KnockoutObservable<boolean>;
    alreadySettingList: KnockoutObservableArray<common.UnitAlreadySettingModel>;
    isDialog: KnockoutObservable<boolean>;
    isShowNoSelectRow: KnockoutObservable<boolean>;
    isMultiSelect: KnockoutObservable<boolean>;
    isShowWorkPlaceName: KnockoutObservable<boolean>;
    isShowSelectAllButton: KnockoutObservable<boolean>;
    disableSelection: KnockoutObservable<boolean>;

    employeeList: KnockoutObservableArray<common.UnitModel>;
    baseDate: KnockoutObservable<Date>;
    // end KCP005

    mode: KnockoutObservable<common.UserSpecificInformation> = ko.observable(null);

    constructor(params: any) {
      super();
      let vm = this;

      vm.getWorkScheduleOutputConditions();
      vm.getSettingListItems();

      vm.rdgSelectedId.subscribe((value) => {
        vm.isEnableSelectedCode(value === common.StandardOrFree.Standard);

        vm.isEnableStdBtn(!nts.uk.util.isNullOrEmpty(vm.standardSelectedCode()));
        vm.isEnableFreeBtn(!nts.uk.util.isNullOrEmpty(vm.freeSelectedCode()));
      });

      vm.standardSelectedCode.subscribe((value) => {
        vm.isEnableStdBtn(!nts.uk.util.isNullOrEmpty(value));
      });

      vm.freeSelectedCode.subscribe((value) => {
        vm.isEnableFreeBtn(!nts.uk.util.isNullOrEmpty(value));
      });

      vm.CCG001_load();
      vm.KCP005_load();
      
    }

    created(params: any) {
      let vm = this;
    }

    mounted() {
      let vm = this;

      $('#kcp005 table').attr('tabindex', '-1');
      $('#btnExportExcel').focus();
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
        baseDate: moment().toISOString(), //基準日
        //periodStartDate: periodStartDate, //対象期間開始日
        //periodEndDate: periodEndDate, //対象期間終了日
        //dateRangePickerValue: vm.datepickerValue
        inService: true, //在職区分 = 対象
        leaveOfAbsence: true, //休職区分 = 対象
        closed: true, //休業区分 = 対象
        retirement: false, // 退職区分 = 対象外

        /** Quick search tab options */
        showAllReferableEmployee: true,
        showOnlyMe: false,
        showSameDepartment: false,
        showSameDepartmentAndChild: false,
        showSameWorkplace: true,
        showSameWorkplaceAndChild: true,

        /** Advanced search properties */
        showEmployment: true,
        showDepartment: false,
        showWorkplace: true,
        showClassification: true,
        showJobTitle: true,
        showWorktype: true,
        isMutipleCheck: true,

        tabindex: - 1,
        /**
        * vm-defined function: Return data from CCG001
        * @param: data: the data return from CCG001
        */
        returnDataFromCcg001: function (data: common.Ccg001ReturnedData) {
          vm.getListEmployees(data);
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
      vm.multiSelectedCode = ko.observableArray([]);
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
      vm.employeeList = ko.observableArray<common.UnitModel>([]);

      vm.listComponentOption = {
        isShowAlreadySet: vm.isShowAlreadySet(),
        isMultiSelect: vm.isMultiSelect(),
        listType: common.ListType.EMPLOYEE,
        employeeInputList: vm.employeeList,
        selectType: common.SelectType.SELECT_BY_SELECTED_CODE,
        selectedCode: vm.multiSelectedCode,
        isDialog: vm.isDialog(),
        isShowNoSelectRow: vm.isShowNoSelectRow(),
        alreadySettingList: vm.alreadySettingList,
        isShowWorkPlaceName: vm.isShowWorkPlaceName(),
        isShowSelectAllButton: vm.isShowSelectAllButton(),
        isSelectAllAfterReload: true,
        tabindex: 5,
        maxRows: 15
      };

      $('#kcp005').ntsListComponent(vm.listComponentOption)
    }

    /**
     *  get employees from CCG001
     */

    getListEmployees(data: common.Ccg001ReturnedData) {
      let vm = this,
        multiSelectedCode: Array<string> = [],
        employeeSearchs: Array<common.UnitModel> = [];

      let newListEmployee: Array<any> = vm.removeDuplicateItem(data.listEmployee);

      _.forEach(newListEmployee, function (value: any) {
        var employee: common.UnitModel = {
          id: value.employeeId,
          code: value.employeeCode,
          name: value.employeeName,
          affiliationName: value.affiliationName
        };
        employeeSearchs.push(employee);
        multiSelectedCode.push(value.employeeCode);
      });

      vm.employeeList(employeeSearchs);
      vm.multiSelectedCode(multiSelectedCode);
    }

    /**
     * Duplicate Setting
     * */

    showDialogScreenB() {
      let vm = this;

      let selectedItem = vm.rdgSelectedId();
      let attendenceItem = selectedItem ? vm.freeSelectedCode() : vm.standardSelectedCode();
      let attendence: any = _.find(vm.settingListItems(), (x) => x.code === attendenceItem);

      if (_.isNil(attendence)) attendence = _.head(vm.settingListItems());

      let params = {
        code: attendence.code,
        name: attendence.name,
      }

      vm.$window.storage(KWR003_B_INPUT, ko.toJS(params)).then(() => {
        vm.$window.modal('/view/kwr/003/b/index.xhtml').then(() => {
          //KWR003_B_OUTPUT
        });
      });
    }

    initialWorkStatusInformation() {
      let vm = this;

      //パラメータ.就業担当者であるか = true || false
      vm.isWorker(vm.$user.role.isInCharge.attendance);

      vm.$window.storage(WORK_STATUS).then((data: any) => {
        if (!_.isNil(data)) {
          vm.rdgSelectedId(data.itemSelection); //項目選択
          vm.standardSelectedCode(data.standardSelectedCode); //定型選択
          vm.freeSelectedCode(data.freeSelectedCode); //自由設定
          vm.zeroDisplayClassification(data.zeroDisplayClassification); //自由の選択済みコード
          vm.pageBreakSpecification(data.pageBreakSpecification); //改ページ指定
        }
      });
    }

    getSettingListItems() {
      let vm = this;

      let listItems: any = [
        new ItemModel('', ''),
        new ItemModel('0001', '項目選択'),
        new ItemModel('0003', '定型選択'),
        new ItemModel('0004', '自由の選択済みコード'),
        new ItemModel('0005', '自由設定'),
        new ItemModel('0002', 'Seoul Korea'),
        new ItemModel('0006', 'Paris France'),
        new ItemModel('0007', '改ページ指定'),
        new ItemModel('0008', '就業担当者'),
        new ItemModel('0009', 'パラメータ'),
        new ItemModel('0010', '者であるか'),
      ];

      listItems = _.orderBy(listItems, 'code', 'asc');
      vm.settingListItems(listItems);
    }

    checkErrorConditions() {
      let vm = this;

      let hasError: any = {
        error: false,
        focusId: ''
      };

      if (nts.uk.ui.errors.hasError()) {
        hasError.error = true;
        hasError.focusId = '';
        return hasError;
      }

      //【社員】が選択されていません。
      if (nts.uk.util.isNullOrEmpty(vm.multiSelectedCode())) {    
        vm.$dialog.error({ messageId: 'Msg_1812'}).then(() => {});        
        hasError.error = true;
        hasError.focusId = 'kcp005';
        return hasError;
      }
      //自由設定が選択されていません。 
      if (vm.rdgSelectedId() === 1 && nts.uk.util.isNullOrEmpty(vm.freeSelectedCode())) {
        vm.$dialog.error({ messageId: 'Msg_1815' }).then(() => {});
        hasError.error = true;
        hasError.focusId = 'KWR003_106';
        return hasError;
      }
      //定型選択が選択されていません。 
      if (vm.rdgSelectedId() === 0 && nts.uk.util.isNullOrEmpty(vm.standardSelectedCode())) {
        vm.$dialog.error({ messageId: 'Msg_1818' }).then(() => {});
        hasError.error = true;
        hasError.focusId = 'KWR003_105';
        return hasError;
      }      

      return hasError;
      //勤務状況表の対象ファイルを出力する | 対象データがありません
    }

    removeDuplicateItem( listItems: Array<any>) : Array<any> {
      if( listItems.length <= 0 ) return [];
 
      let newListItems = _.filter(listItems, (element, index, self) => {
        return index === _.findIndex(self, (x) => { return x.employeeCode === element.employeeCode; });
      });

      return newListItems;
    }

    exportExcel() {
      let vm = this,
      validateError: any = {}; //not error

      validateError = vm.checkErrorConditions();

      if( validateError.error ) {       
        if( !_.isNull(validateError.focusId))
          $('#' + validateError.focusId).focus();
        return;
      }
      
      //save conditions 
      let multiSelectedCode: Array<string> = vm.multiSelectedCode();     
      let lstEmployeeIds: Array<string> = [];
      _.forEach(multiSelectedCode, (employeeCode) => {
        let employee = _.find(vm.employeeList(), (x) => x.code.trim() === employeeCode.trim());        
        if( !_.isNil(employee)) {          
          lstEmployeeIds.push(employee.id);
        }
      });

      vm.saveWorkScheduleOutputConditions().done(() => {
        vm.$blockui('grayout');        
        let params = {
          lstEmployeeId: lstEmployeeIds,
          baseDate: vm.dpkYearMonth()
        }
        vm.$blockui('hide');
        
        /* nts.uk.request.exportFile(PATHS.exportExcel, params).done((response) => {
        });*/
      });
      //create an excel file and redirect to download
    }

    exportPdf() {

    }

    
    saveWorkScheduleOutputConditions(): JQueryPromise<void> {
      let vm = this,
      dfd = $.Deferred<void>(),
      companyId: string = vm.$user.companyId,
      employeeId: string = vm.$user.employeeId;
      
      let data: WorkScheduleOutputConditions = {
        itemSelection: vm.rdgSelectedId(), //項目選択
        standardSelectedCode: vm.standardSelectedCode(), //定型選択
        freeSelectedCode: vm.freeSelectedCode(), //自由設定
        zeroDisplayClassification: vm.zeroDisplayClassification(), //自由の選択済みコード
        pageBreakSpecification: vm.pageBreakSpecification() //改ページ指定
      };

      let storageKey: string = KWR003_SAVE_DATA + "_companyId_" + companyId + "_employeeId_" + employeeId;
      vm.$window.storage(storageKey, data).then(() => {
        dfd.resolve();      
      });
      
      return dfd.promise();

    }

    getWorkScheduleOutputConditions() {
      let vm = this,
      dfd = $.Deferred<void>(),
      companyId: string = vm.$user.companyId,
      employeeId: string = vm.$user.employeeId; 
      let storageKey: string = KWR003_SAVE_DATA + "_companyId_" + companyId + "_employeeId_" + employeeId;
      vm.$window.storage(storageKey).then((data: WorkScheduleOutputConditions) => {    
        if (!_.isNil(data)) {
          vm.rdgSelectedId(data.itemSelection); //項目選択
          vm.standardSelectedCode(data.standardSelectedCode); //定型選択
          vm.freeSelectedCode(data.freeSelectedCode); //自由設定
          vm.zeroDisplayClassification(data.zeroDisplayClassification); //自由の選択済みコード
          vm.pageBreakSpecification(data.pageBreakSpecification); //改ページ指定
        }
        dfd.resolve();      
      }).always(() => {
        dfd.resolve();
      });
      
      return dfd.promise();
    }
  }

  //=================================================================
  export interface WorkScheduleOutputConditions {
    itemSelection?: number, //項目選択
    standardSelectedCode?: string, //定型選択
    freeSelectedCode?: string, //自由設定
    zeroDisplayClassification?: number, //自由の選択済みコード
    pageBreakSpecification?: number //改ページ指定
  }
  
  export class ItemModel {
    code: string;
    name: string;
    constructor(code?: string, name?: string) {
      this.code = code;
      this.name = name;
    }
  }
}