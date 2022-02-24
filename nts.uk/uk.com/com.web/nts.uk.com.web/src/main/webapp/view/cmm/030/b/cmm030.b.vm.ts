/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.cmm030.b {

  import TreeComponentOption = kcp.share.tree.TreeComponentOption;
  import ComponentOption = kcp.share.list.ComponentOption;
	import StartMode = kcp.share.tree.StartMode;
	import SelectionType = kcp.share.tree.SelectionType;
  import ListType = kcp.share.list.ListType;
  import UnitModel = kcp.share.list.UnitModel;

  const API = {
    getWorkplaceInfo: "screen/com/cmm030/getWorkplaceInfo",
    getApprovalAuthorityHolders: "screen/com/cmm030/getApprovalAuthorityHolders"
  };

  @bean()
  export class ScreenModel extends ko.ViewModel {

    baseDate: moment.Moment;
    selectedWorkplaceId: KnockoutObservable<string> = ko.observable("");
    selectedEmployeeId: KnockoutObservable<string> = ko.observable("");
    employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray([]);

    created(params?: any): void {
      const vm = this;
      vm.selectedWorkplaceId.subscribe(value => vm.getApprovalAuthorityHolders(value));

      vm.baseDate = moment.utc(params.baseDate, "YYYY/MM/DD");
      vm.selectedEmployeeId(params.sid);
      $.when(vm.initKcp004(), vm.initKcp005());
    }

    mounted(): void {
      const vm = this;
      vm.$blockui("grayout");
      vm.getWorkplaceInfo().always(() => {
        vm.$nextTick(() => $("#B2_1").focus());
        vm.$blockui("clear");
      });
    }

    public processSave() {
      const vm = this;
      const result = {
        sid: vm.selectedEmployeeId() || "選択無し",
        name: _.find(vm.employeeList(), { id: vm.selectedEmployeeId() })?.name || "選択無し"
      };
      vm.$window.close(result);
    }

    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }

    private initKcp004() {
      const vm = this;
      const kcp004ComponentParams: TreeComponentOption = {
        isShowAlreadySet: false,
        isMultipleUse: false,
        isMultiSelect: false,
        startMode: StartMode.WORKPLACE,
        selectedId: vm.selectedWorkplaceId,
        baseDate: ko.observable(vm.baseDate.toDate()),
        selectType: SelectionType.SELECT_FIRST_ITEM,
        isShowSelectButton: true,
        isDialog: true,
        maxRows: 12,
        tabindex: 1,
        systemType: 2,
        restrictionOfReferenceRange: false,
        listDataDisplay: ko.observableArray([])
      };
      $("#B2_1").ntsTreeComponent(kcp004ComponentParams);
    }

    private initKcp005() {
      const vm = this;
      const kcp005ComponentParams: ComponentOption = {
        isShowAlreadySet: false,
        isMultiSelect: false,
        listType: ListType.EMPLOYEE,
        employeeInputList: vm.employeeList,
        selectType: SelectionType.SELECT_BY_SELECTED_CODE,
        selectedCode: vm.selectedEmployeeId,
        isDialog: true,
        isShowNoSelectRow: true,
        alreadySettingList: ko.observableArray([]),
        isShowWorkPlaceName: false,
        isShowSelectAllButton: false,
        disableSelection: false,
        isMultipleUse: false,
        maxRows: 12
      };
      $("#B4").ntsListComponent(kcp005ComponentParams);
    }

    /**
     * Ｂ：所属職場IDを取得する
     */
    private getWorkplaceInfo(): JQueryPromise<any> {
      const vm = this;
      const param = {
        sids: [vm.selectedEmployeeId()],
        baseDate: vm.baseDate.toISOString()
      };
      return vm.$ajax(API.getWorkplaceInfo, param)
      .then(result => {
        if (!_.isEmpty(result)) {
          vm.selectedWorkplaceId(result[0].workplaceId);
        } else {
          vm.selectedWorkplaceId(null);
        }
      });
    }

    /**
     * Ｂ：承認権限保持者を取得する
     */
    private getApprovalAuthorityHolders(workplaceId: string): JQueryPromise<any> {
      const vm = this;
      const param = {
        workplaceId: workplaceId,
        baseDate: vm.baseDate.toISOString()
      };
      return vm.$ajax(API.getApprovalAuthorityHolders, param)
      .then(result => vm.employeeList(_.map(result.employees, (data: any) => {
        return {
          id: data.sid,
          code: data.employeeCode,
          name: data.employeeName
        };
      })))
    }
  }
}