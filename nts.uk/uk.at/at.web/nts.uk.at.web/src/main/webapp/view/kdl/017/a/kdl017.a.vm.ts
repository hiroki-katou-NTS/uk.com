/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.view.kdl017.a {
  @bean()
  export class KDL017ViewModel extends ko.ViewModel {
    //_____KCP005________
    listComponentOption: any;
    kcp005ComponentOption: any;
    selectedCode: KnockoutObservable<string>;
    multiSelectedCode: KnockoutObservableArray<string>;
    isShowAlreadySet: KnockoutObservable<boolean>;
    alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
    isDialog: KnockoutObservable<boolean>;
    isShowNoSelectRow: KnockoutObservable<boolean>;
    isMultiSelect: KnockoutObservable<boolean>;
    isShowWorkPlaceName: KnockoutObservable<boolean>;
    isShowSelectAllButton: KnockoutObservable<boolean>;
    disableSelection: KnockoutObservable<boolean>;
    employeeList: KnockoutObservableArray<UnitModel>;

    // employee info
    employeeInfo: KnockoutObservable<string>;
    // table data
    dataItems:  KnockoutObservableArray<KDL017TableModel>;

    carryoverNumber: KnockoutObservable<string> = ko.observable('');
    usageNumber: KnockoutObservable<string> = ko.observable('');
    hint02: KnockoutObservable<string> = ko.observable('');
    residual: KnockoutObservable<string> = ko.observable('');
    hint03: KnockoutObservable<string> = ko.observable('');

    constructor() {
      super();
      let vm = this;
      vm.startPage();
      vm.dataItems = ko.observableArray([]);
      vm.employeeInfo = ko.observable('');
      vm.selectedCode = ko.observable('');
    }

    mounted() {
      const vm = this;
      let params = nts.uk.ui.windows.getShared('KDL017_PARAM');

      // 社員ID(List)から個人社員基本情報を取得
      if (params && params.employeeIds && params.employeeIds.length > 0) {

        // Load employee lst by KDL017_PARAM 
        service.getEmployee(params)
          .done((data: any) => {

            // Input．社員IDリストをチェック
            if (data.employeeBasicInfo.length > 1) {

              // 複数モード
              // add selectedCode subscribe event
              vm.onChangeKcp005(data);
              vm.loadKcp005Lst(data.employeeBasicInfo);
            } else if (data.employeeBasicInfo.length == 1) {

              // 単一モード
              const itemSelected: any = data.employeeBasicInfo[0];
              vm.onSelectEmployee(
                data.baseDate,
                itemSelected.employeeId,
                itemSelected.employeeCode,
                itemSelected.businessName,
              );
            } else {
              vm.employeeInfo('');
            }
        }).fail(vm.onError);
        // init table
        $('#date-fixed-table').ntsFixedTable({ height: 250 });
      }
    }

    startPage(): JQueryPromise<any> {
      var dfd = $.Deferred();
      dfd.resolve();
      return dfd.promise();
  }

    // init KCP005 component
    loadKcp005Lst(data: any) {
      const vm = this;
      // 社員リストの先頭を選択
      // set data to kcp005 component
      vm.selectedCode(data[0].employeeCode);
      vm.multiSelectedCode = ko.observableArray([]);
      vm.isShowAlreadySet = ko.observable(false);
      vm.alreadySettingList = ko.observableArray([]);
      vm.isDialog = ko.observable(false);
      vm.isShowNoSelectRow = ko.observable(false);
      vm.isMultiSelect = ko.observable(false);
      vm.isShowWorkPlaceName = ko.observable(false);
      vm.isShowSelectAllButton = ko.observable(false);
      vm.disableSelection = ko.observable(false);
      vm.employeeList = ko.observableArray<UnitModel>(_.map(data, (x: any) => ({ code: x.employeeCode, name: x.businessName })));
      vm.listComponentOption = {
        isShowAlreadySet: vm.isShowAlreadySet(),
        isMultiSelect: vm.isMultiSelect(),
        listType: ListType.EMPLOYEE,
        employeeInputList: vm.employeeList,
        selectType: SelectType.SELECT_BY_SELECTED_CODE,
        selectedCode: vm.selectedCode,
        isDialog: vm.isDialog(),
        isShowNoSelectRow: vm.isShowNoSelectRow(),
        alreadySettingList: vm.alreadySettingList,
        isShowWorkPlaceName: vm.isShowWorkPlaceName(),
        isShowSelectAllButton: vm.isShowSelectAllButton(),
        disableSelection: vm.disableSelection()
      };
      $('#kcp005-component').ntsListComponent(this.listComponentOption);
    }

    // On error
    onError(res: any) {
      nts.uk.ui.dialog.alertError({ messageId: res.messageId });
    }

    /**
     * Close dialog
     */
    closeDialog() {
      const vm = this;
      vm.$window.close();
    }

    /**
     * Binding data employee when select item in kcp005
     * 
     * @param data: employee data
     */
    onSelectEmployee(baseDate: any, employeeId: string, employeeCode: string, employeeName: string) {
      const vm = this;
      vm.employeeInfo(employeeCode + '　' + employeeName);
      debugger
      // Load data 
      this.loadExcessHoliday(employeeId, baseDate);
    }

    // selectedCode subscribe event
    onChangeKcp005(data: any) {
      const vm = this;
      vm.selectedCode.subscribe((value) => {
        const itemSelected: any = _.find(data.employeeBasicInfo, ['employeeCode', value]);
        vm.onSelectEmployee(
          data.baseDate,
          itemSelected.employeeId,
          itemSelected.employeeCode,
          itemSelected.businessName,
        );
      });
    }

    loadExcessHoliday(employeeId: string, baseDate: string) {
      const vm = this;
      service.get60hOvertimeDisplayInfoDetail(employeeId, baseDate)
        .done((data: any) => {
          vm.dataItems(data.remainNumberDetailDtos);
        }).fail((err) => vm.onError(err));
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

  class KDL017TableModel {
    expirationDate: string;
    digestionNumber: string;
    digestionDate: string;
    digestionHour: string;
    occurrenceNumber: string;
    occurrenceDate: string;
    occurrenceHour: string;

    constructor(init?: Partial<KDL017TableModel>) {
      (<any>Object).assign(this, init);
    }
  }
}
