/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr005.a {
  import common = nts.uk.at.view.kwr005.common;
  import ComponentOption = kcp.share.list.ComponentOption;

  const KWR005_SAVE_DATA = 'WORK_SCHEDULE_STATUS_OUTPUT_CONDITIONS';

  const PATH = {
    exportExcelPDF: 'at/function/kwr/005/report/export',
    getSettingListWorkStatus: 'at/function/kwr/005/a/listworkledger',
    checkDailyAuthor: 'at/function/kwr/checkdailyauthor',
    getStartFromMonthly: 'at/function/kwr/005/a/beginningmonth',
      getInitDateLoginEmployee:'at/function/kwr/initdateloginemployee'
    //getInit: 'at/screen/kwr/005/b/getinfor',    
  };

  @bean()
  class ViewModel extends ko.ViewModel {

    // start variable of CCG001
    ccg001ComponentOption: common.GroupOption;
    closureId: KnockoutObservable<number> = ko.observable(null);
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

    zeroDisplayClassification: KnockoutObservable<number> = ko.observable(0);
    pageBreakSpecification: KnockoutObservable<number> = ko.observable(0);
    isWorker: KnockoutObservable<boolean> = ko.observable(true);
    settingListItems1: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    settingListItems2: KnockoutObservableArray<ItemModel> = ko.observableArray([]);

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

    isPermission51: KnockoutObservable<boolean> = ko.observable(false);
    itemSelection: KnockoutObservableArray<any> = ko.observableArray([]);

    periodDate: KnockoutObservable<any> = ko.observable(null);

      startDate: KnockoutObservable<any> = ko.observable(moment());
      endDate: KnockoutObservable<any> = ko.observable(moment());
    constructor(params: any) {
      super();
      const vm = this;


      //パラメータ.就業担当者であるか = true || false
      vm.isWorker(vm.$user.role.isInCharge.attendance);
      vm.getStartFromMonthly();
      vm.getItemSelection();

      vm.getSettingListItems();
      vm.getWorkScheduleOutputConditions();

      vm.rdgSelectedId.subscribe((value) => {
        vm.isEnableSelectedCode(value === common.StandardOrFree.Standard);
        vm.isEnableStdBtn(!nts.uk.util.isNullOrEmpty(vm.standardSelectedCode()));
        vm.isEnableFreeBtn(!nts.uk.util.isNullOrEmpty(vm.freeSelectedCode()));
        //focus
        let focusId = value === 0 ? '#KWR005_105' : '#KWR005_106';
        $(focusId).focus();
        nts.uk.ui.errors.clearAll();
      });


      vm.KCP005_load();

    }

    created(params: any) {
      const vm = this;

      vm.periodDate({ startDate: '2017/02/02', endDate: '2017/02/02' });
    }

    mounted() {
      const vm = this;
        vm.periodDate.subscribe((data)=>{
            vm.startDate(vm.periodDate().startDate);
            vm.endDate(vm.periodDate().endDate)
        });
      $('#kcp005 table').attr('tabindex', '-1');
      $('#btnExportExcel').focus();
    }

    CCG001_load() {
      const vm = this;
      // Set component option
      vm.ccg001ComponentOption = {
        /** Common properties */
        systemType: 2, //システム区分 - 2: 就業
          // 2022.03.18 - 3S - chinh.hm - issues #123480   - 変更 START
          //showEmployeeSelection: true,
          showEmployeeSelection: false,
          // 2022.03.18 - 3S - chinh.hm - issues #123480   - 変更 END
        showQuickSearchTab: true, //クイック検索
        showAdvancedSearchTab: true, //詳細検索
        showBaseDate: false, //基準日利用
        showClosure: true,
        showAllClosure: true, //氏名の種類	-> ビジネスネーム（日本語）
        showPeriod: true, //対象期間利用
        periodFormatYM: true,
          maxPeriodRange: 'oneYear',
        /** Required parameter */
        baseDate: moment().toISOString(), //基準日
        periodStartDate:  vm.startDate, //対象期間開始日
        periodEndDate:  vm.endDate, //対象期間終了日
        //dateRangePickerValue: vm.datepickerValue
        inService: true, //在職区分 = 対象
        leaveOfAbsence: true, //休職区分 = 対象
        closed: true, //休業区分 = 対象
        retirement: false, // 退職区分 = 対象外

        /** Quick search tab options */
        showAllReferableEmployee: true,// 参照可能な社員すべて
        showOnlyMe: true,// 自分だけ
        showSameDepartment: false,//同じ部門の社員
        showSameDepartmentAndChild: false,// 同じ部門とその配下の社員
        showSameWorkplace: true, // 同じ職場の社員
        showSameWorkplaceAndChild: true,// 同じ職場とその配下の社員

        /** Advanced search properties */
        showEmployment: true,// 雇用条件
        showDepartment: false, // 部門条件
        showWorkplace: true,// 職場条件
        showClassification: true,// 分類条件
        showJobTitle: true,// 職位条件
        showWorktype: true,// 勤種条件
        isMutipleCheck: true,// 選択モード

        tabindex: - 1,
        /**
        * vm-defined function: Return data from CCG001
        * @param: data: the data return from CCG001
        */
        returnDataFromCcg001: function (data: common.Ccg001ReturnedData) {
          vm.closureId(data.closureId);
          vm.getListEmployees(data);

            vm.periodDate({
                startDate: data.periodStart,
                endDate: data.periodEnd
            });
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
        isSelectAllAfterReload: false,
        tabindex: 5,
        maxRows: 20
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
      const vm = this;

      let attendanceItem = vm.rdgSelectedId() ? vm.freeSelectedCode() : vm.standardSelectedCode();
      let attendance: any = _.find((vm.rdgSelectedId() ? vm.settingListItems2() : vm.settingListItems1()), (x) => x.code === attendanceItem);

      let params = {
        code: null, //項目選択コード
        name: null,
        settingId: null,
        standOrFree: vm.rdgSelectedId() //設定区分								
      }

      if (!_.isNil(attendance) && !_.isNil(attendance.code)) {
        params.code = attendance.code;
        params.name = attendance.name;
        params.settingId = attendance.id;
        params.standOrFree = vm.rdgSelectedId();
      }

      vm.$window.modal('/view/kwr/005/b/index.xhtml', ko.toJS(params)).then((data: any) => {
        if (data) {
          nts.uk.ui.errors.clearAll();
          vm.getSettingListWorkStatus(vm.rdgSelectedId(), data);
        }
        //$('.yearmonth-picker').trigger('validate');
        $('#btnExportExcel').focus();
      });

    }

    /**
     * Gets setting listwork status
     * @param type 
     * 定型選択	: 0
     * 自由設定 : 1
     */
    getSettingListWorkStatus(type: number, dataFromB?: any) {
      const vm = this;
      let listWorkStatus: Array<ItemModel> = [];
     
      //定型選択		
      vm.$ajax(PATH.getSettingListWorkStatus, { setting: type }).done((data) => {
        if (!_.isNil(data)) {
          let selectedCode: any = null;
          _.forEach(data, (item) => {
            let code = _.padStart(item.settingDisplayCode, 2, '0');
            if (!selectedCode) selectedCode = code;
            listWorkStatus.push(new ItemModel(code, _.trim(item.settingName), item.settingId));
          });

          if (!_.isNil(dataFromB) && !_.isNil(dataFromB.code)) selectedCode = dataFromB.code;

          if (type === 0) {
            vm.settingListItems1(listWorkStatus);
            vm.standardSelectedCode(selectedCode);
          } else {
            vm.settingListItems2(listWorkStatus);
            vm.freeSelectedCode(selectedCode);
          }

          $('#btnExportExcel').focus();
        }
      });
    }

    getSettingListItems() {
      const vm = this;
      vm.getSettingListWorkStatus(0);
      vm.getSettingListWorkStatus(1);
    }

    checkErrorConditions() {
      const vm = this;

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
        vm.$dialog.error({ messageId: 'Msg_1923' }).then(() => { });
        hasError.error = true;
        hasError.focusId = 'kcp005';
        return hasError;
      }
      //自由設定が選択されていません。 
      if (vm.rdgSelectedId() === 1 && nts.uk.util.isNullOrEmpty(vm.freeSelectedCode())) {
        vm.$dialog.error({ messageId: 'Msg_1924' }).then(() => { });

        hasError.error = true;
        hasError.focusId = 'KWR005_106';
        //$('#' + hasError.focusId).ntsError('check');
        return hasError;
      }
      //定型選択が選択されていません。 
      if (vm.rdgSelectedId() === 0 && nts.uk.util.isNullOrEmpty(vm.standardSelectedCode())) {
        vm.$dialog.error({ messageId: 'Msg_1925' }).then(() => { });
        hasError.error = true;
        hasError.focusId = 'KWR005_105';
        //$('#' + hasError.focusId).ntsError('check');
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

    exportExcelPDF(mode: number = 1) {
      let vm = this,
        validateError: any = {}; //not error
      
      $('.ntsDatepicker').trigger('validate');
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

        let findObj = null;

        if (vm.rdgSelectedId() === 0) {
          findObj = _.find(vm.settingListItems1(), (x) => x.code === vm.standardSelectedCode());
        } else {
          findObj = _.find(vm.settingListItems2(), (x) => x.code === vm.freeSelectedCode());
        }

        let params = {
          //mode: mode, //ExcelPdf区分
          lstEmpIds: lstEmployeeIds, //社員リスト
          startMonth: _.toInteger(vm.periodDate().startDate.replace('/', '')), //対象年月,
          endMonth: _.toInteger(vm.periodDate().endDate.replace('/', '')),
          isZeroDisplay: vm.zeroDisplayClassification() ? true : false,//ゼロ表示区分選択肢
          code: vm.pageBreakSpecification() ? true : false, //改ページ指定選択肢,
          standardFreeClassification: vm.rdgSelectedId(), //自由設定: A5_4_2   || 定型選択 : A5_3_2,
          settingId: findObj.id, //ゼロ表示区分,
          closureId: vm.closureId() //締め日
        }       

        nts.uk.request.exportFile(PATH.exportExcelPDF, params).done((response) => {
          vm.$blockui('hide');
        }).fail((error) => {
          vm.$dialog.error({ messageId: error.messageId }).then(() => { 
            $('#kcp005').focus();
            vm.$blockui('hide'); 
          });          
        }).always(() => vm.$blockui('hide'));
      });
      //create an excel file and redirect to download
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

      let storageKey: string = KWR005_SAVE_DATA + "_companyId_" + companyId + "_employeeId_" + employeeId;
      vm.$window.storage(storageKey, data).then(() => {
        dfd.resolve();
      });

      return dfd.promise();
    }

    getWorkScheduleOutputConditions() {
      const vm = this,
        //dfd = $.Deferred<void>(),
        companyId: string = vm.$user.companyId,
        employeeId: string = vm.$user.employeeId;

      let storageKey: string = KWR005_SAVE_DATA + "_companyId_" + companyId + "_employeeId_" + employeeId;

      vm.$window.storage(storageKey).then((data: WorkScheduleOutputConditions) => {

        if (!_.isNil(data)) {
          let standardCode = _.find(vm.settingListItems1(), ['code', data.standardSelectedCode]);
          let freeCode = _.find(vm.settingListItems2(), ['code', data.freeSelectedCode]);
          let zeroDisplay = _.isEmpty(data.zeroDisplayClassification) ? 0 : data.zeroDisplayClassification;
          let pageBreak = _.isEmpty(data.pageBreakSpecification) ? 0 : data.pageBreakSpecification;
          vm.rdgSelectedId(!vm.isPermission51() ? 0 : data.itemSelection); //項目選択
          vm.standardSelectedCode(!_.isNil(standardCode) ? data.standardSelectedCode : null); //定型選択
          vm.freeSelectedCode(!_.isNil(freeCode) ? data.freeSelectedCode : null); //自由設定
          vm.zeroDisplayClassification(data.zeroDisplayClassification); //自由の選択済みコード
          vm.pageBreakSpecification(data.pageBreakSpecification); //改ページ指定
        }
      }).always(() => { });
    }

    getItemSelection() {
      const vm = this;

      vm.itemSelection.push({ id: 0, name: vm.$i18n('KWR005_6') });
      vm.$ajax(PATH.checkDailyAuthor, { roleId: vm.$user.role.attendance }).done((permission) => {
        vm.isPermission51(permission);
        vm.itemSelection.push({ id: 1, name: vm.$i18n('KWR005_7'), enable: permission });
      });
    }

    getStartFromMonthly() {
      const vm = this;

      let startDate = moment().toDate(),
        endDate = moment(startDate).add(1, 'year').subtract(1, 'month').toDate();

      vm.periodDate({
        startDate: startDate,
        endDate: endDate
      });

      const currentYear = moment().format('YYYY');

      vm.$ajax(PATH.getStartFromMonthly)
        .done((result) => {
          if (result && _.isNumber(result.startMonth)) {
              vm.$ajax(PATH.getInitDateLoginEmployee).done((data) => {
                  if(!_.isNil(data))
                      vm.dpkYearMonth(data);
                  let date = data + '01';
                  let processingDate = moment(date,"YYYY/MM/DD").toDate();
                  let yearInit = null;
                  //システム日付の月　＜　期首月　

                  if (_.toInteger(result.startMonth) > _.toInteger(processingDate.getMonth()+1)) {
                      yearInit = processingDate.getFullYear() -1;
                  } else { //システム日付の月　＞=　期首月　
                      yearInit = processingDate.getFullYear();
                  }
                  startDate = moment(yearInit + '/' + result.startMonth + '/01').toDate();
                  endDate = moment(startDate).add(11, 'month').toDate();
                  vm.periodDate({
                      startDate: startDate,
                      endDate: endDate
                  });
                  vm.startDate(vm.periodDate().startDate);
                  vm.endDate(vm.periodDate().endDate)
                  vm.CCG001_load();
              });

            vm.periodDate({
              startDate: startDate,
              endDate: endDate
            });
          }

        })
        .fail(() => { });
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
    id: string;
    code: string;
    name: string;
    constructor(code?: string, name?: string, id?: string) {
      this.code = code;
      this.name = name;
      this.id = id;
    }
  }
}