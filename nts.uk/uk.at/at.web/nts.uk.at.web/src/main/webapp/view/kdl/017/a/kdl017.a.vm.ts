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
        nts.uk.ui.block.grayout();
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
        }).fail(vm.onError)
        .always(() => nts.uk.ui.block.clear());
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
      vm.dataItems([]);
      nts.uk.ui.block.grayout();
      service.get60hOvertimeDisplayInfoDetail(employeeId, baseDate)
        .done((data: any) => {
          if (data.remainHourDetailDtos !== null) {
            data.remainHourDetailDtos.forEach(item => {
              let kdl017TableDto = new KDL017TableDto();
              // <!-- A3_6_1 -->
              if (moment.utc(data.deadline, 'YYYY/MM/DD').isSameOrAfter(moment.utc(item.startPeriod, 'YYYY/MM/DD'))
                && moment.utc(data.deadline, 'YYYY/MM/DD').isSameOrBefore(moment.utc(item.endPeriod, 'YYYY/MM/DD'))) {
                kdl017TableDto.deadline = nts.uk.resource.getText("KDL005_38", [item.deadline]);
              } else {
                kdl017TableDto.deadline = nts.uk.resource.getText("KDL005_37", [item.deadline]);
              }
              // <!-- A3_2_1 -->
              kdl017TableDto.occurrenceMonth = item.occurrenceMonth ? moment.utc(item.occurrenceMonth, 'YYYYMM').format('YYYY/MM') : '';
              // <!-- A3_2_2 -->
              kdl017TableDto.occurrenceTime = item.occurrenceTime ? (nts.uk.time as any).format.byId("Time_Short_HM", [item.occurrenceTime]) : '';

              if (item.usageDateDtos !== null) {
                let usageDateDtos : UsageDateDto[] = [];
                item.usageDateDtos.forEach(usageDateDto => {
                  let usageDateItem = new UsageDateDto();
                  // <!-- A3_3_1 -->
                  usageDateItem.usageDate = (usageDateDto.creationCategory === CreateAtr["申請(事前)"]
                                          || usageDateDto.creationCategory === CreateAtr["申請(事後)"]
                                          || usageDateDto.creationCategory === CreateAtr["予定"])
                              ? nts.uk.resource.getText("KDL005_36", [(nts.uk.time as any).applyFormat("Short_YMDW", [usageDateDto.usageDate])])
                              : (nts.uk.time as any).applyFormat("Short_YMDW", [usageDateDto.usageDate]);
                  // <!-- A3_3_2 -->
                  usageDateItem.usageTime = usageDateDto.usageTime ? (nts.uk.time as any).format.byId("Time_Short_HM", [usageDateDto.usageTime]) : '';
                  usageDateItem.creationCategory = usageDateDto.creationCategory;
                  usageDateDtos.push(usageDateItem);
                });
                kdl017TableDto.numberUsageDate = item.usageDateDtos.length;
                kdl017TableDto.usageDateDtos = usageDateDtos;
              }
              let kdl017Model = new KDL017TableModel();
              vm.dataItems.push(kdl017Model.fromDto(kdl017TableDto));
            });
          }
          vm.carryoverNumber(data.carryoverNumber ? (nts.uk.time as any).format.byId("Time_Short_HM", [data.carryoverNumber]) : '');
          vm.usageNumber(data.usageNumber ? (nts.uk.time as any).format.byId("Time_Short_HM", [data.usageNumber]) : '');
          vm.residual(data.residual ? (nts.uk.time as any).format.byId("Time_Short_HM", [data.residual]) : '');
        })
        .fail((err) => vm.onError(err))
        .always(() => nts.uk.ui.block.clear());
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
    occurrenceMonth: KnockoutObservable<string> = ko.observable('');
    occurrenceTime: KnockoutObservable<number> = ko.observable(null);
    deadline: KnockoutObservable<string> = ko.observable('');
    numberUsageDate: KnockoutObservable<number> = ko.observable(null);
    usageDateDtos: KnockoutObservableArray<UsageDateDto> = ko.observableArray([]);

    fromDto(init?: Partial<KDL017TableDto>) {
      this.occurrenceTime(init.occurrenceTime);
      this.deadline(init.deadline);
      this.numberUsageDate(init.numberUsageDate);
      this.occurrenceMonth(init.occurrenceMonth);
      this.usageDateDtos(init.usageDateDtos);
      return this;
    }
  }

  class KDL017TableDto {
    occurrenceMonth: string;
    occurrenceTime: number;
    deadline: string;
    numberUsageDate: number;
    usageDateDtos: UsageDateDto[];

    constructor(init?: Partial<KDL017TableDto>) {
      $.extend(this, init);
    }
  }


  export class UsageDateDto {
    /** 使用日 */
    usageDate: string;

    /** 使用時間 */
    usageTime: number;

    /** 作成区分 */
    creationCategory: number;
  }

  export enum CreateAtr {
    "予定",//0
    "実績",//1
    "申請(事前)",//2
    "申請(事後)",//3
    "フレックス補填",//4
  }
}
