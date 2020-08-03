/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.view.kdl017.a {
  @bean()
  export class KDL017ViewModel extends ko.ViewModel {
    //_____KCP005________
    listComponentOption: any;
    kcp005ComponentOption: any;
    visibleKcp005: KnockoutObservable<boolean> = ko.observable(true);
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

    value01: KnockoutObservable<string> = ko.observable('');
    value02: KnockoutObservable<string> = ko.observable('');
    hint02: KnockoutObservable<string> = ko.observable('');
    value03: KnockoutObservable<string> = ko.observable('');
    hint03: KnockoutObservable<string> = ko.observable('');

    constructor() {
      super();
      let self = this;
      self.dataItems = ko.observableArray([]);
      self.employeeInfo = ko.observable('');

      // set data to kcp005 component
      self.selectedCode = ko.observable('0001');
      self.multiSelectedCode = ko.observableArray([]);
      self.isShowAlreadySet = ko.observable(false);
      self.alreadySettingList = ko.observableArray([]);
      self.isDialog = ko.observable(false);
      self.isShowNoSelectRow = ko.observable(false);
      self.isMultiSelect = ko.observable(false);
      self.isShowWorkPlaceName = ko.observable(false);
      self.isShowSelectAllButton = ko.observable(false);
      self.disableSelection = ko.observable(false);
      self.employeeList = ko.observableArray<UnitModel>([
        { id: '1a', code: '0001', name: 'Phí Thị Kim liên', workplaceName: 'HN' },
        { id: '2b', code: '0002', name: 'Vũ Duy Tùng', workplaceName: 'HN' },
        { id: '3c', code: '0003', name: 'Nguyễn Thanh Đức', workplaceName: 'HCM' },
        { id: '3d', code: '0004', name: 'Lê Tuấn Anh', workplaceName: 'HN' }
      ]);
      self.listComponentOption = {
        isShowAlreadySet: self.isShowAlreadySet(),
        isMultiSelect: self.isMultiSelect(),
        listType: ListType.EMPLOYEE,
        employeeInputList: self.employeeList,
        selectType: SelectType.SELECT_BY_SELECTED_CODE,
        selectedCode: self.selectedCode,
        isDialog: self.isDialog(),
        isShowNoSelectRow: self.isShowNoSelectRow(),
        alreadySettingList: self.alreadySettingList,
        isShowWorkPlaceName: self.isShowWorkPlaceName(),
        isShowSelectAllButton: self.isShowSelectAllButton(),
        disableSelection: self.disableSelection()
      };
      $('#kcp005-component').ntsListComponent(this.listComponentOption);
    }

    mounted() {
      let params = nts.uk.ui.windows.getShared('KDL017_PARAM');
      // init table
      $('#date-fixed-table').ntsFixedTable({ height: 250 });
      this.onChangeKcp005();
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
      const self = this;
      self.employeeInfo(employeeCode + '　' + employeeName);
      // TODO set data schedule to table
    }

    onChangeKcp005() {
      const self = this;
      self.selectedCode.subscribe((value) => {
        const itemSelected: any = _.find(self.employeeList(), ['code', value]);
        self.onSelectEmployee(
          null,
          itemSelected.employeeId,
          itemSelected.code,
          itemSelected.name,
        );
      });
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
