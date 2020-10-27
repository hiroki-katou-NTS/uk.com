/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr003.a {
  import common = nts.uk.at.view.kwr003.common;
  import ComponentOption = kcp.share.list.ComponentOption;

  const KWR003_B_INPUT = 'KWR003_WORK_STATUS_DATA';
  const KWR003_B_OUTPUT = 'KWR003WORK_STATUS_RETURN';
  const KWR003_SAVE_DATA = 'WORK_SCHEDULE_STATUS_OUTPUT_CONDITIONS';

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
    dpkYearMonth: KnockoutObservable<number> = ko.observable(202010);

    //panel right
    rdgSelectedId: KnockoutObservable<number> = ko.observable(0);
    standardSelectedCode: KnockoutObservable<string> = ko.observable(null);
    isEnableStdBtn: KnockoutObservable<boolean> = ko.observable(true);

    freeSelectedCode: KnockoutObservable<string> = ko.observable(null);
    isEnableSelectedCode: KnockoutObservable<boolean> = ko.observable(true);
    isEnableFreeBtn: KnockoutObservable<boolean> = ko.observable(true);

    zeroDisplayClassification: KnockoutObservable<number> = ko.observable(1);
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


      //パラメータ.就業担当者であるか = true || false
      vm.isWorker(vm.$user.role.isInCharge.attendance);

      vm.getSettingListItems();
      vm.getWorkScheduleOutputConditions();

      vm.rdgSelectedId.subscribe((value) => {
        vm.isEnableSelectedCode(value === common.StandardOrFree.Standard);
        vm.isEnableStdBtn(!nts.uk.util.isNullOrEmpty(vm.standardSelectedCode()));
        vm.isEnableFreeBtn(!nts.uk.util.isNullOrEmpty(vm.freeSelectedCode()));
        //focus
        let focusId = value === 0 ? '#KWR003_105' : '#KWR003_106';
        $(focusId).focus();
        nts.uk.ui.errors.clearAll();
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
        isSelectAllAfterReload: false,
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
        code: attendence.code, //項目選択コード
        name: attendence.name,
        standOrFree: selectedItem //設定区分								
      }

      vm.$window.storage(KWR003_B_INPUT, ko.toJS(params)).then(() => {
        vm.$window.modal('/view/kwr/003/b/index.xhtml').then(() => {
          vm.$window.storage(KWR003_B_OUTPUT).then((data: any) => {

            if (data) {
              switch (data.status) {
                case 0:
                  //update new name after changed from screen B
                  let index = _.findIndex(vm.settingListItems(), (x) => x.code === data.code);
                  let tempListItems = vm.settingListItems();
                  tempListItems[index] = new ItemModel(
                    data.code,
                    data.name.substring(0, data.name.indexOf('_' + data.code))
                  );
                  vm.settingListItems([]);
                  vm.settingListItems(tempListItems);
                  //reselect
                  vm.standardSelectedCode(data.code);
                  vm.freeSelectedCode(data.code);

                  break;
                case 1:
                  //add new an item into settingListItems
                  vm.settingListItems.push(new ItemModel(data.code, data.name));
                  break;
                //deleted
                case 2:
                  //remove item that's deleted from screen B
                  vm.settingListItems(_.filter(vm.settingListItems(), (x) => x.code !== data.code));
                  break;
              }
            }
            //settingListItems
          });
          $('#btnExportExcel').focus();
        });
      });
    }

    /* initialWorkStatusInformation() {
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
    } */

    getSettingListItems() {
      let vm = this;

      let listItems: any = [
        new ItemModel('', ''),
        new ItemModel('01', '項目選択'),
        new ItemModel('03', '定型選択'),
        new ItemModel('04', '自由の選択済みコード'),
        new ItemModel('05', '自由設定'),
        new ItemModel('02', 'Seoul Korea'),
        new ItemModel('06', 'Paris France'),
        new ItemModel('07', '改ページ指定'),
        new ItemModel('08', '就業担当者'),
        new ItemModel('09', 'パラメータ'),
        new ItemModel('10', '者であるか'),
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
        vm.$dialog.error({ messageId: 'Msg_1812' }).then(() => { });
        hasError.error = true;
        hasError.focusId = 'kcp005';
        return hasError;
      }
      //自由設定が選択されていません。 
      if (vm.rdgSelectedId() === 1 && nts.uk.util.isNullOrEmpty(vm.freeSelectedCode())) {
        vm.$dialog.error({ messageId: 'Msg_1815' }).then(() => { });

        hasError.error = true;
        hasError.focusId = 'KWR003_106';
        $('#' + hasError.focusId).ntsError('check');
        return hasError;
      }
      //定型選択が選択されていません。 
      if (vm.rdgSelectedId() === 0 && nts.uk.util.isNullOrEmpty(vm.standardSelectedCode())) {
        vm.$dialog.error({ messageId: 'Msg_1818' }).then(() => { });
        hasError.error = true;
        hasError.focusId = 'KWR003_105';
        $('#' + hasError.focusId).ntsError('check');
        return hasError;
      }

      return hasError;
      //勤務状況表の対象ファイルを出力する | 対象データがありません
    }

    removeDuplicateItem(listItems: Array<any>): Array<any> {
      if (listItems.length <= 0) return [];

      let newListItems = _.filter(listItems, (element, index, self) => {
        return index === _.findIndex(self, (x) => { return x.employeeCode === element.employeeCode; });
      });

      return newListItems;
    }

    exportExcel() {
      let vm = this,
        validateError: any = {}; //not error

      validateError = vm.checkErrorConditions();

      if (validateError.error) {
        if (!_.isNull(validateError.focusId)) {
          $('#' + validateError.focusId).focus();
        }

        return;
      }

      //save conditions 
      let multiSelectedCode: Array<string> = vm.multiSelectedCode();
      let lstEmployeeIds: Array<string> = [];
      _.forEach(multiSelectedCode, (employeeCode) => {
        let employee = _.find(vm.employeeList(), (x) => x.code.trim() === employeeCode.trim());
        if (!_.isNil(employee)) {
          lstEmployeeIds.push(employee.id);
        }
      });

      vm.saveWorkScheduleOutputConditions().done(() => {
        vm.$blockui('grayout');
        let params = {
          lstEmployeeId: lstEmployeeIds, //社員リスト
          baseDate: vm.dpkYearMonth(), //対象年月,          
          zeroDisplayClassification: 0,//ゼロ表示区分選択肢
          pageBreakSpecification: 0, //改ページ指定選択肢,
          standardFreeDivision: 0, //自由設定: A5_4_2   || 定型選択 : A5_3_2
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
      const vm = this,
        dfd = $.Deferred<void>(),
        companyId: string = vm.$user.companyId,
        employeeId: string = vm.$user.employeeId;
        
      let storageKey: string = KWR003_SAVE_DATA + "_companyId_" + companyId + "_employeeId_" + employeeId;

      vm.$window.storage(storageKey).then((data: WorkScheduleOutputConditions) => {       
        if (!_.isNil(data)) {
          let standardCode = _.find(vm.settingListItems(), ['code', data.standardSelectedCode]);
          let freeCode = _.find(vm.settingListItems(), ['code', data.freeSelectedCode]);
          vm.rdgSelectedId(data.itemSelection); //項目選択
          vm.standardSelectedCode(!_.isNil(standardCode) ? data.standardSelectedCode : null); //定型選択
          vm.freeSelectedCode(!_.isNil(freeCode) ? data.freeSelectedCode : null); //自由設定
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
      this.name = name + (code ? '_' + code : '');
    }
  }
}