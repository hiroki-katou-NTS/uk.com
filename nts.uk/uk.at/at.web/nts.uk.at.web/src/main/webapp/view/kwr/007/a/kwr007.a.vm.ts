/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr007.a {
  import common = nts.uk.at.view.kwr007.common;
  import ComponentOption = kcp.share.list.ComponentOption;

  const KWR007_SAVE_DATA = 'WORK_SCHEDULE_STATUS_OUTPUT_CONDITIONS';

  const PATH = {
    exportExcelPDF: 'at/function/kwr/007/report/export',
    getSettingListWorkStatus: 'at/function/kwr/007/a/listoutputsetting',
    checkDailyAuthor: 'at/function/kwr/checkdailyauthor',
    //getPeriodListing: 'at/function/kwr/005/a/beginningmonth',
    getPeriodList: 'at/record/kwr/007/a/arbitraryaggregatio/getlist',
    getPermissions: 'at/function/kwr/007/a/getroleinfor',
      getDatePeriod: 'at/function/kwr/007/a/getperiod'
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
    isEmploymentPerson: KnockoutObservable<boolean> = ko.observable(true);
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

    periodDateList: KnockoutObservableArray<PeriodItem> = ko.observableArray([]);
    aggregateListCode: KnockoutObservable<string> = ko.observable(null);
    workplaceHierarchyList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
    workplaceHierarchyId: KnockoutObservable<any> = ko.observable(null);

    detailsOutputSettings: KnockoutObservableArray<CheckBoxItem> = ko.observableArray([]);
    detailsOutputSelectedIds: KnockoutObservableArray<string> = ko.observableArray([]);
    specifyWorkplaceHierarchy: KnockoutObservableArray<CheckBoxItem> = ko.observableArray([]);
    specifyWPSelectedIds: KnockoutObservableArray<string> = ko.observableArray([]);
    isEnableSpecifyWP: KnockoutObservable<boolean> = ko.observable(false);
    executionDateTime: KnockoutObservable<string> = ko.observable('2020/09/09 10:34:45');

    constructor(params: any) {
      super();
      const vm = this;


      vm.getPeriodListing();
      vm.getItemSelection();
      $.when(
        vm.getSettingListWorkStatus(0),
        vm.getSettingListWorkStatus(1)).done(() => {
          vm.getWorkScheduleOutputConditions();
      });
      vm.rdgSelectedId.subscribe((value) => {
        vm.isEnableSelectedCode(value === common.StandardOrFree.Standard);
        vm.isEnableStdBtn(!nts.uk.util.isNullOrEmpty(vm.standardSelectedCode()));
        vm.isEnableFreeBtn(!nts.uk.util.isNullOrEmpty(vm.freeSelectedCode()));
        //focus
        let focusId = value === 0 ? '#KWR007_105' : '#KWR007_106';
        $(focusId).focus();
        nts.uk.ui.errors.clearAll();
      });
        vm.$ajax(PATH.getDatePeriod).done((data) => {
            vm.CCG001_load(data);
            vm.KCP005_load();
        });

    }

    created(params: any) {
      const vm = this;

      vm.periodDate({ startDate: '2017/02/02', endDate: '2017/02/02' });
    }

    mounted() {
      const vm = this;

      $('#kcp005 table').attr('tabindex', '-1');
      $('#btnExportExcel').focus();
    }

    CCG001_load(date : any) {
      const vm = this;
        if(nts.uk.util.isNullOrUndefined(date)){
            date =  moment().toISOString();
        }
        let baseDate = moment(date,"YYYY/MM/DD").toDate();
      //Set component option
      vm.ccg001ComponentOption = {
        /** Common properties */
        systemType: 2, //?????????????????? - 2: ??????
          // 2022.03.18 - 3S - chinh.hm - issues #123486    - ?????? START
        //showEmployeeSelection: true,
        showEmployeeSelection: false,
          // 2022.03.18 - 3S - chinh.hm - issues #123486    - ?????? END
        showQuickSearchTab: true, //??????????????????
        showAdvancedSearchTab: true, //????????????
        showBaseDate: false, //???????????????
        showClosure: false, //???????????????
        showAllClosure: false, //???????????????	-> ???????????????????????????????????? - ???????????????
        showPeriod: false, //??????????????????
        periodFormatYM: false,

        /** Required parameter */
        baseDate: baseDate, //?????????
        //periodStartDate: periodStartDate, //?????????????????????
        //periodEndDate: periodEndDate, //?????????????????????
        //dateRangePickerValue: vm.datepickerValue
        inService: true, //???????????? = ??????
        leaveOfAbsence: true, //???????????? = ??????
        closed: true, //???????????? = ??????
        retirement: false, // ???????????? = ?????????

        /** Quick search tab options */
        showAllReferableEmployee: true,// ??????????????????????????????
        showOnlyMe: true,// ????????????
        showSameDepartment: false,//?????????????????????
        showSameDepartmentAndChild: false,// ????????????????????????????????????
        showSameWorkplace: true, // ?????????????????????
        showSameWorkplaceAndChild: true,// ????????????????????????????????????

        /** Advanced search properties */
        showEmployment: true,// ????????????
        showDepartment: false, // ????????????
          // 2022.03.21 - 3S - chinh.hm - issues #123486    - ?????? START
        showWorkplace: true,// ????????????
          // 2022.03.21 - 3S - chinh.hm - issues #123486    - ?????? END
        showClassification: true,// ????????????
        showJobTitle: true,// ????????????
        showWorktype: true,// ????????????
        isMutipleCheck: true,// ???????????????

        tabindex: - 1,
        /**
        * vm-defined function: Return data from CCG001
        * @param: data: the data return from CCG001
        */
        returnDataFromCcg001: function (data: common.Ccg001ReturnedData) {
          vm.closureId(data.closureId);
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
        code: null, //?????????????????????
        name: null,
        settingId: null,
        standOrFree: vm.rdgSelectedId() //????????????								
      }

      if (!_.isNil(attendance) && !_.isNil(attendance.code)) {
        params.code = attendance.code;
        params.name = attendance.name;
        params.settingId = attendance.id;
        params.standOrFree = vm.rdgSelectedId();
      }

      vm.$window.modal('/view/kwr/007/b/index.xhtml', ko.toJS(params)).then((data: any) => {
        if (data) {
          nts.uk.ui.errors.clearAll();
          vm.getSettingListWorkStatus(vm.rdgSelectedId(), data).done(() => {
            
          });
        }
        $('#btnExportExcel').focus();
      });

    }

    /**
     * Gets setting listwork status
     * @param type 
     * ????????????	: 0
     * ???????????? : 1
     */
    getSettingListWorkStatus(type: number, dataFromB?: any) : JQueryPromise<any> {
      const vm = this;
      const def = $.Deferred();
      let listWorkStatus: Array<ItemModel> = [];

      //????????????		
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
        def.resolve();
      });

      return def.promise();
    }

    /* getSettingListItems(): JQueryPromise<any> {
      const vm = this;
      const def = $.Deferred();
      vm.getSettingListWorkStatus(0);
      vm.getSettingListWorkStatus(1);

      def.resolve();
      return def.promise();
    } */

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

      //?????????????????????????????????????????????
      if (nts.uk.util.isNullOrEmpty(vm.multiSelectedCode())) {
        vm.$dialog.error({ messageId: 'Msg_1890' }).then(() => { });
        hasError.error = true;
        hasError.focusId = 'kcp005';
        return hasError;
      }

      //????????????????????????????????????????????? 
      if (vm.rdgSelectedId() === 0 && nts.uk.util.isNullOrEmpty(vm.standardSelectedCode())) {
        vm.$dialog.error({ messageId: 'Msg_1891' }).then(() => { });
        hasError.error = true;
        hasError.focusId = 'A5_3_2';
        return hasError;
      }

      //????????????????????????????????????????????? 
      if (vm.rdgSelectedId() === 1 && nts.uk.util.isNullOrEmpty(vm.freeSelectedCode())) {
        vm.$dialog.error({ messageId: 'Msg_1892' }).then(() => { });
        hasError.error = true;
        hasError.focusId = 'A5_4_2';
        return hasError;
      }

      //???????????????????????????
      let isHasOutput: Boolean = true;
      isHasOutput = _.some(vm.detailsOutputSettings(), (x) => x.checked() === true);
      if( !isHasOutput ) {
        vm.$dialog.error({ messageId: 'Msg_2309' }).then(() => { });
        hasError.error = true;
        hasError.focusId = 'A82_85';
        return hasError;
      }

      //????????????????????????????????????
      let isCheckedCumulative: Boolean = true; //Cumulative number of workplaces
      isCheckedCumulative = vm.detailsOutputSettings()[3].checked();
      let totalCumulative = _.filter(vm.specifyWorkplaceHierarchy(), (x) => x.checked() === true).length;
   
      if( (isCheckedCumulative && totalCumulative === 0) || totalCumulative > 5 ) {
        vm.$dialog.error({ messageId: 'Msg_1184' }).then(() => { });
        hasError.error = true;
        hasError.focusId = 'A8_5_1';
        return hasError;
      }

      return hasError;

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

      validateError = vm.checkErrorConditions();

      if (validateError.error) {
        if (!_.isNull(validateError.focusId)) {
          $('#' + validateError.focusId).focus();
        }
        return;
      }
      let hierarchyId = vm.workplaceHierarchyId();
      if(vm.pageBreakSpecification() !=2){
          hierarchyId = null;
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

        let cumulativeSelectedList: Array<number> = [];
        _.forEach(vm.specifyWorkplaceHierarchy(), (x) => {
          if(x.checked() == true){
          cumulativeSelectedList.push(x.code);
          }
        })
        
     
        let code = vm.aggregateListCode();
        let listData  = vm.periodDateList();
        let startDate  = _.find(listData, (x) => x.aggrFrameCode === code).startDate;
        let endDate  = _.find(listData, (x) => x.aggrFrameCode === code).endDate;
        let params = {
          mode: mode,//1
          aggrFrameCode: code, //?????????????????????????????? & ??????(?????????)(From-To) 2        
          startDate: startDate,//3
          endDate: endDate,//4
          lstEmpIds: lstEmployeeIds, //??????????????? 5
          standardFreeClassification: vm.rdgSelectedId(), //????????????: A5_2_1   || ???????????? : A5_2_2, //6    
          settingId: findObj.id, //????????????????????? || ????????????????????? 7             
          isZeroDisplay: vm.zeroDisplayClassification() ? true : false,//??????????????????????????? 7         
          isPageBreakByWpl: vm.pageBreakSpecification() ? true : false, //???????????????????????????,    
          pageBreakWplHierarchy: hierarchyId,//????????????????????????
          isDetail: vm.detailsOutputSettings()[0].checked(), //??????????????????
          isWorkplaceTotal: vm.detailsOutputSettings()[1].checked(),//?????????????????????
          isTotal: vm.detailsOutputSettings()[2].checked(), //?????????????????????
          isCumulativeWorkplace: vm.detailsOutputSettings()[3].checked(),//????????????????????????
          workplacePrintTargetList: cumulativeSelectedList //???????????????????????? : 1?????????????????? -> 9??????????????????
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
    }

    saveWorkScheduleOutputConditions(): JQueryPromise<void> {
      
      let vm = this,
        dfd = $.Deferred<void>(),
        companyId: string = vm.$user.companyId,
        employeeId: string = vm.$user.employeeId;
      let detailsOutput: Array<any> = [];
      _.forEach(vm.detailsOutputSettings(), (x: CheckBoxItem) => {
        detailsOutput.push(x.toSave());
      });

      let levels: Array<any> = [];
      _.forEach(vm.specifyWorkplaceHierarchy(), (x: CheckBoxItem) => {
        levels.push(x.toSave());
      });
        let hierarchyId = vm.workplaceHierarchyId();
        if(vm.pageBreakSpecification() !=2){
            hierarchyId = null;
        }
      let data: WorkScheduleOutputConditions = {
        itemSelection: vm.rdgSelectedId(), //????????????????????????
        standardSelectedCode: vm.standardSelectedCode(), //?????????????????????
        freeSelectedCode: vm.freeSelectedCode(), //?????????????????????
        zeroDisplayClassification: vm.zeroDisplayClassification(), //???????????????????????????
        pageBreakSpecification: vm.pageBreakSpecification(), //????????????????????????
        workplaceHierarchyId: hierarchyId, //			?????????????????????
        detailsOutputSettings: detailsOutput,//??????	+ ????????? + /????????? + ????????????      	
        levels: levels,//1?????? -> 9??????
      };

      let storageKey: string = KWR007_SAVE_DATA + "_companyId_" + companyId + "_employeeId_" + employeeId;
      vm.$window.storage(storageKey, data).then(() => {
        dfd.resolve();
      });

      return dfd.promise();
    }

    getWorkScheduleOutputConditions() {
      const vm = this,
      
      companyId: string = vm.$user.companyId,
      employeeId: string = vm.$user.employeeId;

      let storageKey: string = KWR007_SAVE_DATA + "_companyId_" + companyId + "_employeeId_" + employeeId;

      vm.$window.storage(storageKey).then((data: WorkScheduleOutputConditions) => {             
        if (!_.isNil(data)) {      
          let standardCode = _.filter(vm.settingListItems1(), (x) => x.code === data.standardSelectedCode);       
          let freeCode = _.filter(vm.settingListItems2(),(x) => x.code === data.freeSelectedCode);    
          let zeroDisplay = _.isEmpty(data.zeroDisplayClassification) ? 0 : data.zeroDisplayClassification;
          let pageBreak = _.isEmpty(data.pageBreakSpecification) ? 0 : data.pageBreakSpecification;
          vm.rdgSelectedId(!vm.isPermission51() ? 0 : data.itemSelection); //????????????
          vm.standardSelectedCode(!_.isNil(standardCode) ? data.standardSelectedCode : null); //????????????
          vm.freeSelectedCode(!_.isNil(freeCode) ? data.freeSelectedCode : null); //????????????
          vm.zeroDisplayClassification(data.zeroDisplayClassification); //??????????????????????????????
          vm.pageBreakSpecification(data.pageBreakSpecification); //??????????????????
          vm.workplaceHierarchyId(data.workplaceHierarchyId);
          let detailsOutput = data.detailsOutputSettings;
          vm.specifyWorkplaceHierarchy()

          if (!_.isNil(detailsOutput) && detailsOutput.length > 0) {
            _.forEach(vm.detailsOutputSettings(), (x, index) => {
              if (detailsOutput[index].code === x.code) {
                vm.detailsOutputSettings()[index].checked(detailsOutput[index].checked);
                if (x.code === 4) vm.isEnableSpecifyWP(detailsOutput[index].checked);
              }
            });
          }

          let workplaceHierarchy = data.levels; //Specify Workplace Hierarchy
          if (!_.isNil(workplaceHierarchy) && workplaceHierarchy.length > 0) {
            _.forEach(vm.specifyWorkplaceHierarchy(), (x, index) => {
              if (workplaceHierarchy[index].code === x.code) {
                vm.specifyWorkplaceHierarchy()[index].checked(workplaceHierarchy[index].checked);
              }
            });
          }
        }
      }).always(() => { });
    }

    getItemSelection() {
      const vm = this;

      vm.itemSelection.push({ id: 0, name: vm.$i18n('KWR007_6') });
      vm.$ajax(PATH.checkDailyAuthor, { roleId: vm.$user.role.attendance }).done((permission) => {
        vm.isPermission51(permission);
        vm.itemSelection.push({ id: 1, name: vm.$i18n('KWR007_7'), enable: permission });
      });

      //??????????????????								
      vm.$ajax(PATH.getPermissions).done((permission) => {
        if (permission) vm.isEmploymentPerson(permission.employeeCharge);
      });

    }

    getPeriodListing() {
      const vm = this;
      vm.$ajax(PATH.getPeriodList).done((data) => {
        if (data && data.length > 0) {
          _.forEach(data, (x) => {
            let displayText = x.aggrFrameCode + ' ' + x.optionalAggrName + ' ' + x.startDate + ' ??? ' + x.endDate;
            x.displayText = displayText;
            vm.periodDateList.push(new PeriodItem(x));
          })
        }
      });

      //#KWR007_17???#KWR007_25
      for (let i = 1; i <= 9; i++) {
        vm.workplaceHierarchyList.push(new ItemModel(
          i.toString(),
          vm.$i18n('KWR007_' + (16 + i).toString()),
          i.toString()
        ));
      }

      //??????????????????????????? - #KWR007_27???#KWR007_31
      for (let i = 1; i <= 4; i++) {
        let textResource = 'KWR007_' + (26 + i).toString();
        let item: CheckBoxItem = new CheckBoxItem(i, vm.$i18n(textResource), false, textResource);
        vm.detailsOutputSettings.push(item);

        item.checked.subscribe((value: any) => {
          if (item.code === 4) {
            vm.isEnableSpecifyWP(value);
          }
        });
      }
      //????????????????????????????????????- #KWR007_32???#KWR007_41
      for (let i = 1; i <= 9; i++) {
        let textResource = 'KWR007_' + (31 + i).toString();
        let item = new CheckBoxItem(i, vm.$i18n(textResource), false, textResource);
        vm.specifyWorkplaceHierarchy.push(item);
      }
    }
  }

  //=================================================================

  export interface WorkScheduleOutputConditions {
    itemSelection?: number, //????????????
    standardSelectedCode?: string, //????????????
    freeSelectedCode?: string, //????????????
    zeroDisplayClassification?: number, //??????????????????????????????
    pageBreakSpecification?: number //??????????????????
    workplaceHierarchyId?: any, //			?????????????????????
    detailsOutputSettings?: Array<any>,//??????	+ ????????? + /????????? + ????????????      	
    levels?: Array<any>,//1?????? -> 9??????
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

  export class PeriodItem {
    companyId: string;
    aggrFrameCode: string;
    optionalAggrName: string;
    startDate: string;
    endDate: string;
    displayText: string;

    constructor(init?: Partial<PeriodItem>) {
      $.extend(this, init);
    }
  }

  export class CheckBoxItem {
    code: number;
    name: string;
    checked: KnockoutObservable<boolean> = ko.observable(false);
    nameId: string;
    constructor(code?: number, name?: string, checked: boolean, nameId: string) {
      this.code = code;
      this.name = name;
      this.checked(checked);
      this.nameId = '#[' + nameId + ']';
    }

    toSave() {
      return { code: this.code, checked: this.checked() };
    }
  }
}