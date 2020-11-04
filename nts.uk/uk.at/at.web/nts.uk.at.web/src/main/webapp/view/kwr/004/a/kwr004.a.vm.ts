/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr004.a {
  import common = nts.uk.at.view.kwr004.common;
  //import ComponentOption = kcp.share.list.ComponentOption;

  const WORK_STATUS = 'WorkStatus';
  const KWR004_B_INPUT = 'KWR004_WORK_STATUS_DATA';
  const KWR004_B_OUTPUT = 'KWR004_WORK_STATUS_RETURN';
  const KWR004_SAVE_DATA = 'WORK_SCHEDULE_STATUS_CONDITIONS';
  @bean()
  class ViewModel extends ko.ViewModel {

    // start variable of CCG001
    ccg001ComponentOption: common.GroupOption;
    // end variable of CCG001

    //panel left
    dpkYearMonth: KnockoutObservable<number> = ko.observable(202010);
    startDate: KnockoutObservable<Date> = ko.observable(new Date());
    endDate: KnockoutObservable<Date> = ko.observable(new Date());
    periodDate: KnockoutObservable<any> = ko.observable({});
    yearMonth: KnockoutObservable<number> = ko.observable(202010);;

    //panel right
    rdgSelectedId: KnockoutObservable<number> = ko.observable(0);
    standardSelectedCode: KnockoutObservable<string> = ko.observable(null);
    freeSelectedCode: KnockoutObservable<string> = ko.observable(null);

    isEnableSelectedCode: KnockoutObservable<boolean> = ko.observable(true);
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
    allowFreeSetting: KnockoutObservable<boolean> = ko.observable(true);
    itemListSetting: KnockoutObservableArray<any> = ko.observableArray([]);

    constructor(params: any) {
      super();
      const vm = this;

      vm.periodDate({
        startDate: moment(new Date()),
        endDate: moment(new Date()).add(1, 'year').subtract(1, 'month')
      });

      vm.getSettingListItems();      

      vm.rdgSelectedId.subscribe((value) => {
        vm.isEnableSelectedCode(value === common.StandardOrFree.Standard);
      });

      vm.CCG001_load();
      vm.KCP005_load();
      vm.initialWorkStatusInformation();
      vm.getItemListSetting();
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;

      $('#kcp005 table').attr('tabindex', '-1');
      $('#btnExportExcel').focus();
    }

    CCG001_load() {
      const vm = this;
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
      const vm = this;

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
      });

      vm.employeeList(employeeSearchs);
    }

    removeDuplicateItem( listItems: Array<any>) : Array<any> {
      if( listItems.length <= 0 ) return [];
 
      let newListItems = _.filter(listItems, (element, index, self) => {
        return index === _.findIndex(self, (x) => { return x.employeeCode === element.employeeCode; });
      });

      return newListItems;
    }
    
    /**
     * Duplicate Setting
     * */

    showDialogScreenB() {
      const vm = this;

      let selectedItem = vm.rdgSelectedId();
      let attendenceItem = selectedItem ? vm.freeSelectedCode() : vm.standardSelectedCode();
      let attendence: any = _.find(vm.settingListItems(), (x) => x.code === attendenceItem);

      if (_.isNil(attendence)) attendence = _.head(vm.settingListItems());

      let params = {
        code: attendence.code,
        name: attendence.name,
      }

      vm.$window.storage(KWR004_B_INPUT, ko.toJS(params)).then(() => {
        vm.$window.modal('/view/kwr/004/b/index.xhtml').then(() => {         
          vm.$window.storage(KWR004_B_OUTPUT).then((data: any) => {

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

    initialWorkStatusInformation() {
      const vm = this;

      //パラメータ.就業担当者であるか = true || false
      vm.isWorker(vm.$user.role.isInCharge.attendance);
      
      //社員ごとの出力項目設定を登録することができる
      vm.allowFreeSettingForeachEmployee();

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

    allowFreeSettingForeachEmployee() {
      const vm = this;
      vm.allowFreeSetting(true);
    }

    getSettingListItems() {
      const vm = this;

      let listItems: any = [
        new ItemModel('', '----'),
        new ItemModel('001', '予定勤務種類'),
        new ItemModel('003', '予定勤務種類'),
        new ItemModel('004', '予定勤務種類'),
        new ItemModel('005', '予定勤務種類'),
        new ItemModel('002', 'Seoul Korea'),
        new ItemModel('006', 'Paris France'),
        new ItemModel('007', '予定勤務種類'),
        new ItemModel('008', '予定勤務種類'),
        new ItemModel('009', '予定勤務種類'),
        new ItemModel('010', '予定勤務種類'),
        new ItemModel('011', '予定勤務種類'),
        new ItemModel('013', '予定勤務種類'),
        new ItemModel('014', '予定勤務種類'),
        new ItemModel('015', '予定勤務種類'),
        new ItemModel('012', 'Seoul Korea'),
        new ItemModel('016', 'Paris France'),
        new ItemModel('017', '予定勤務種類'),
        new ItemModel('018', '予定勤務種類'),
        new ItemModel('019', '予定勤務種類'),
        new ItemModel('020', '予定勤務種類'),
      ];

      listItems = _.orderBy(listItems, 'code', 'asc');
      vm.settingListItems(listItems);
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

    getItemListSetting() {
      const vm = this;

      vm.itemListSetting.push( { id: 0, name: vm.$i18n('KWR004_14') });
      if( vm.allowFreeSetting() ) {
        vm.itemListSetting.push( { id: 1, name: vm.$i18n('KWR004_15') });
      }
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
        vm.$dialog.error({ messageId: 'Msg_1862' }).then(() => { });
        hasError.error = true;
        hasError.focusId = 'kcp005';
        return hasError;
      }
      //自由設定が選択されていません。 
      if (vm.rdgSelectedId() === 1 && nts.uk.util.isNullOrEmpty(vm.freeSelectedCode())) {
        vm.$dialog.error({ messageId: 'Msg_1864' }).then(() => { });

        hasError.error = true;
        hasError.focusId = 'KWR004_106';
        $('#' + hasError.focusId).ntsError('check');
        return hasError;
      }
      //定型選択が選択されていません。 
      if (vm.rdgSelectedId() === 0 && nts.uk.util.isNullOrEmpty(vm.standardSelectedCode())) {
        vm.$dialog.error({ messageId: 'Msg_1863' }).then(() => { });
        hasError.error = true;
        hasError.focusId = 'KWR004_105';
        $('#' + hasError.focusId).ntsError('check');
        return hasError;
      }

      return hasError;
      //勤務状況表の対象ファイルを出力する | 対象データがありません
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

      let storageKey: string = KWR004_SAVE_DATA + "_companyId_" + companyId + "_employeeId_" + employeeId;
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
        
      let storageKey: string = KWR004_SAVE_DATA + "_companyId_" + companyId + "_employeeId_" + employeeId;

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
      this.name = name;
    }
  }
}