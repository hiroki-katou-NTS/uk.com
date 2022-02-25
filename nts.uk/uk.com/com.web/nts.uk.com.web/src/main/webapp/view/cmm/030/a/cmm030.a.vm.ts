/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.cmm030.a {

  import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;
  import Ccg001ReturnedData = nts.uk.com.view.ccg.share.ccg.service.model.Ccg001ReturnedData;

  const API = {
    initScreen: "screen/com/cmm030/initScreenA",
    displayEmployeeApprovers: "screen/com/cmm030/displayEmployeeApprovers",
    getSetEmployeeList: "screen/com/cmm030/getSetEmployeeList",
    register: "workflow/approvermanagement/workroot/selfapproversetting/register",
    update: "workflow/approvermanagement/workroot/selfapproversetting/update"
  };

  // 自分のみ
  const ONLY_MYSELF = 3;

  @bean()
  export class ScreenModel extends ko.ViewModel {

    inputProcedure: KnockoutObservable<string> = ko.observable("");
    startDate: KnockoutObservable<string> = ko.observable("");
    approverInputList: KnockoutObservableArray<ApproverInput> = ko.observableArray([]);
    employees: KnockoutObservableArray<EmployeeModel> = ko.observableArray([]);
    selectedEmployee: KnockoutObservable<string> = ko.observable(null);
    returnUrl: KnockoutComputed<string>;
    columnHeaders: KnockoutObservableArray<string> = ko.observableArray([]);
    caution: KnockoutObservable<string> = ko.observable("");

    isVisibleFuncButton: KnockoutComputed<boolean>;
    isEnableFuncButton: KnockoutComputed<boolean>;
    isVisibleA4_3: KnockoutComputed<boolean>;

    approvalLevelNo: KnockoutObservable<number> = ko.observable(0);
    isNewMode: KnockoutObservable<boolean> = ko.observable(true);
    role: KnockoutObservable<RoleModel> = ko.observable(null);
    // 修正中フラグ
    isUpdating: KnockoutObservable<boolean> = ko.observable(false);
    settingTypeUseds: KnockoutObservableArray<SettingTypeUsed> = ko.observableArray([]);

    created(params?: any): void {
      const vm = this;
      vm.initCCG001();

      vm.returnUrl = ko.computed(() => {
        switch (params?.returnType as number) {
          case ParentType.CMM018R: return "/view/cmm/018/r/index.xhtml";
          case ParentType.CMM018X: return "/view/cmm/018/x/index.xhtml";
        }
        return null;
      });
      vm.isVisibleFuncButton = ko.computed(() => {
        if (_.isNil(vm.role())) {
          return false;
        }
        return vm.role().approvalAuthority && vm.role().employeeReferenceRange !== ONLY_MYSELF;
      });
      vm.isEnableFuncButton = ko.computed(() => {
        if (_.isNil(vm.role()) || vm.isNewMode()) {
          return false;
        }
        return vm.role().approvalAuthority && vm.role().employeeReferenceRange !== ONLY_MYSELF && !vm.isUpdating();
      });
      vm.isVisibleA4_3 = ko.computed(() => {
        return !vm.isNewMode() && moment.utc(vm.startDate(), "YYYY/MM/DD").isAfter(moment.utc());
      });

      vm.selectedEmployee.subscribe(value => {
        vm.$blockui("grayout");
        vm.displayEmployeeApprovers(value).always(() => {
          vm.$blockui("clear");
          vm.focusA7_1();
        });
      });
    }

    mounted(): void {
      const vm = this;
      vm.$blockui("grayout");
      vm.initScreen().always(() => {
        vm.focusA7_1();
        $("#A4_1").attr("readonly", "readonly");
        setTimeout(() => $(".approver-input").on("click", e => vm.openDialogB($(e.target))), 500);
        vm.$blockui("clear");
      });
    }

    public openDialogB(elem: JQuery) {
      const vm = this;
      if (nts.uk.text.isNullOrEmpty(vm.startDate())) {
        vm.$dialog.error({ messageId: "Msg_3299" });
      }
      const rowId = elem.data("row-id");
      const colId = elem.data("col-id");
      const approverInfo = _.find(vm.approverInputList(), { id: rowId }).approvers()[colId];
      const param = {
        baseDate: vm.startDate(),
        sid: approverInfo.sid
      };
      vm.$window.modal("/view/cmm/030/b/index.xhtml", param)
      .then(result => {
        if (!_.isNil(result)) {
          approverInfo.sid = result.sid;
          approverInfo.name(result.name);
          vm.approverInputList.valueHasMutated();
        }
      });
    }

    public openDialogC() {
      const vm = this;
      const param = {
        sid: vm.selectedEmployee()
      };
      vm.$window.modal("/view/cmm/030/c/index.xhtml", param)
      .then(result => {
        if (!_.isNil(result)) {
          vm.startDate(result.startDate);
          if (result.transfer === 1) {
            _.forEach(vm.approverInputList(), data => data.wipeData(vm.$i18n("CMM030_25")));
          }
          vm.isNewMode(true);
          vm.isUpdating(true);
        }
      });
    }

    public processSave() {
      const vm = this;
      vm.validateInput().then(isValid => {
        if (isValid) {
          vm.$blockui("grayout");
          if (vm.isNewMode()) {
            vm.register().always(() => vm.$blockui("clear"));
          } else {
            vm.update().always(() => vm.$blockui("clear"));
          }
        }
      });
    }

    public openDialogG() {
      const vm = this;
      const param = {
        baseDate: vm.startDate()
      };
      vm.$window.modal("/view/cmm/030/g/index.xhtml", param);
    }

    private initCCG001() {
      const vm = this;
      const ccg001ComponentOption: GroupOption = {
        /** Common properties */
        systemType: 1,
        showEmployeeSelection: true,
        showQuickSearchTab: true,
        showAdvancedSearchTab: true,
        showBaseDate: true,
        showClosure: true,
        showAllClosure: true,
        showPeriod: true,
        periodFormatYM: false,

        /** Required parameter */
        baseDate: moment.utc(),
        periodStartDate: moment.utc(),
        periodEndDate: moment.utc(),
        inService: true,
        leaveOfAbsence: true,
        closed: true,
        retirement: true,

        /** Quick search tab options */
        showAllReferableEmployee: true,
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
        * Self-defined function: Return data from CCG001
        * @param: data: the data return from CCG001
        */
        returnDataFromCcg001: (data: Ccg001ReturnedData) => {
          vm.employees(_.map(data.listEmployee, x => new EmployeeModel({
            id: x.employeeId,
            code: x.employeeCode,
            businessName: x.employeeName,
            workplaceName: x.affiliationName,
          })));
          vm.initKcp009();
          vm.$nextTick(() => vm.focusA7_1());
        }
      };

      // Start component
      $('#A3_1').ntsGroupComponent(ccg001ComponentOption);
    }

    private initKcp009() {
      const vm = this;
      const kcp009ComponentOption = {
        systemReference: 1,
        isDisplayOrganizationName: true,
        employeeInputList: vm.employees,
        targetBtnText: "対象者",
        selectedItem: vm.selectedEmployee,
        tabIndex: 6
      };
      $('#A3_2').ntsLoadListComponent(kcp009ComponentOption);
    }

    private focusA7_1() {
      $("table tr:first-child td:nth-child(2) .approver-input:first-child").focus()
    }

    /**
     * Ａ：自分の承認者設定を起動する
     */
    private initScreen(): JQueryPromise<any> {
      const vm = this;
      const param = {
        baseDate: moment.utc().toISOString()
      };
      return vm.$ajax(API.initScreen, param)
      .then(result => {
        // Ａ：起動可能かチェックする
        const approverOperationSettings = result.approverOperationSettings;
        vm.settingTypeUseds(approverOperationSettings.settingTypeUseds);
        vm.approvalLevelNo(approverOperationSettings.approvalLevelNo);
        vm.approverInputList(_.chain(approverOperationSettings.settingTypeUseds)
        .orderBy(["employmentRootAtr", "applicationType", "confirmRootType"], ["asc", "asc", "asc"])
        .map((data: SettingTypeUsed) => {
          if (data.notUseAtr === NotUseAtr.USE ) {
            return new ApproverInput(vm.getSettingId(data), vm.getAppTypeDesc(data.employmentRootAtr, data.applicationType | data.confirmRootType)
            + vm.$i18n("CMM030_110"), vm.$i18n("CMM030_25"));
          }
          return null;
        }).filter(data => !_.isNull(data)).value());
        const approverSettingScreenInfor = approverOperationSettings.approverSettingScreenInfor;
        vm.columnHeaders([
          approverSettingScreenInfor.firstItemName, approverSettingScreenInfor.secondItemName, approverSettingScreenInfor.thirdItemName, 
          approverSettingScreenInfor.fourthItemName, approverSettingScreenInfor.fifthItemName, 
        ]);
        vm.inputProcedure(String(approverSettingScreenInfor.processMemo));
        vm.inputProcedure.valueHasMutated();
        vm.caution(approverSettingScreenInfor.attentionMemo);

        // Ａ：ログイン社員の情報取得する
        const currentEmployeeInfo = result.currentEmployeeInfo;
        vm.employees(_.map(currentEmployeeInfo.employees, (data: any) => new EmployeeModel({
          id: data.sid,
          code: data.employeeCode,
          businessName: data.employeeName,
          workplaceName: currentEmployeeInfo.workplaceInfos[0].workplaceName
        })));
        vm.initKcp009();
        vm.role(currentEmployeeInfo.roleDto);

        // Ａ：承認者表示データの取得する 
        const approverDisplayData = result.approverDisplayData;
        vm.updateApproverData(approverDisplayData);
      }).fail(err => vm.$dialog.error({ messageId: err.messageId }));
    }

    /**
     * Ａ：社員の承認者を表示する
     */
    private displayEmployeeApprovers(sid: string): JQueryPromise<any> {
      const vm = this;
      const param = {
        sid: sid,
        baseDate: moment.utc().toISOString()
      };
      return vm.$ajax(API.displayEmployeeApprovers, param)
      .then(result => vm.updateApproverData(result.approverDisplayData))
      .fail(err => vm.$dialog.error({ messageId: err.messageId }));
    }

    /**
     * Ａ：複写のため既に設定されている社員を取得する
     */
    private getSetEmployeeList(): JQueryPromise<any> {
      const vm = this;
      const param = {
        baseDate: vm.startDate()
      };
      return vm.$ajax(API.getSetEmployeeList, param);
    }

    /**
     * 自分の承認者を新規登録する
     */
    private register(): JQueryPromise<any> {
      const vm = this;
      const params = vm.createApprovalSettingParam();
      const param = {
        sid: vm.selectedEmployee(),
        baseDate: moment.utc(vm.startDate(), "YYYY/MM/DD").toISOString(),
        params: _.filter(params, data => !_.isNil(data))
      };
      return vm.$ajax(API.register, param)
      .then(() => vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
        vm.isNewMode(false);
        vm.isUpdating(false);
      }))
      .fail(err => vm.$dialog.error({ messageId: err.messageId }));
    }

    /**
     * 自分の承認者の修正更新をする
     */
    private update(): JQueryPromise<any> {
      const vm = this;
      const params = vm.createApprovalSettingParam();
      const param = {
        sid: vm.selectedEmployee(),
        startDate: moment.utc(vm.startDate(), "YYYY/MM/DD").toISOString(),
        endDate: moment.utc("9999/12/31", "YYYY/MM/DD").toISOString(),
        params: params
      };
      return vm.$ajax(API.update, param)
      .then(() => vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
        vm.isNewMode(false);
        vm.isUpdating(false);
      }))
      .fail(err => vm.$dialog.error({ messageId: err.messageId }));
    }

    private createApprovalSettingParam(): any {
      const vm = this;
      return _.map(vm.settingTypeUseds(), settingTypeUsed => {
        if (settingTypeUsed.notUseAtr === NotUseAtr.USE) {
          const approvalRootInfo = {
            startDate: moment.utc(vm.startDate(), "YYYY/MM/DD").toISOString(),
            endDate: moment.utc("9999/12/31", "YYYY/MM/DD").toISOString(),
            employmentRootAtr: settingTypeUsed.employmentRootAtr,
            applicationType: settingTypeUsed.applicationType,
            confirmationRootType: settingTypeUsed.confirmRootType
          };
          const approverInput = _.find(vm.approverInputList(), { id: vm.getSettingId(settingTypeUsed) });
          const approvalPhases = _.chain(approverInput.approvers()).reverse().map((approver, index) => {
            return {
              phaseOrder: index,
              approverId: approver.sid
            };
          });
          return {
            approvalRootInfo: approvalRootInfo,
            approvalPhases: approvalPhases
          };
        }
      });
    }

    private updateApproverData(approverDisplayData: any) {
      const vm = this;
      vm.isNewMode(_.isEmpty(approverDisplayData.approvalSettingInformations));
      vm.isUpdating(false);
      _.forEach(vm.approverInputList(), approverInput => {
        const settingInfo = _.find(approverDisplayData.approvalSettingInformations, 
          (data: any) => vm.getSettingId(data.personApprovalRoot.apprRoot) === approverInput.id);
        if (!_.isNil(settingInfo)) {
          if (!_.isNil(settingInfo.personApprovalRoot)) {
            vm.startDate(settingInfo.personApprovalRoot.apprRoot.historyItems[0].startDate);
          }
          const approvers = _.chain(settingInfo.approvalPhases)
          .orderBy("phaseOrder", "desc")
          .map((phase: any) => phase.approver).flatten()
          .map((approver: any, index) => {
            const emp: any = _.find(approverDisplayData.employeeNames, { sid: approver.employeeId });
            let data = !_.isNil(emp) ? new ApproverInfo(emp.sid, emp.employeeName) : new ApproverInfo(null, vm.$i18n("CMM030_25"));
            data.colId = ko.observable(index);
            data.rowId = ko.observable(approverInput.id);
            return data;
          }).value();
          approverInput.approvers(approvers);
        }
      });
    }

    private validateInput(): JQueryPromise<boolean> {
      const vm = this;
      return vm.$validate().then(isValid => {
        if (vm.approverInputList()[0].approvers()[0].sid === null) {
          vm.$dialog.error({ messageId: "Msg_3293" });
          return false;
        }
        let hasBlank = !!_.find(vm.approverInputList(), data => _.find(data.approvers(), approver => approver.sid === null));
        if (hasBlank) {
          vm.$dialog.error({ messageId: "Msg_3294" });
          return false;
        }
        return isValid;
      });
    }

    private getAppTypeDesc(employmentRootAtr: number, typeAtr?: number): string {
      const vm = this;
      let enums: any[];
      switch (employmentRootAtr) {
        case EmploymentRootAtr.COMMON: return vm.$i18n("CMM030_11");
        case EmploymentRootAtr.APPLICATION: enums = __viewContext.enums["ApplicationType"]; break;
        case EmploymentRootAtr.CONFIRMATION: enums = __viewContext.enums["ConfirmationRootType"]; break;
      }
      return _.find(enums, { value: typeAtr }).name;
    }

    private getSettingId(data: SettingTypeUsed) {
      return `${data.employmentRootAtr}-${data.applicationType ?? "null"}-${data.confirmRootType ?? "null"}`;
    }
  }

  export class ApproverInput {
    id: string;
    appTypeDescription: KnockoutObservable<string>;
    approvers: KnockoutObservableArray<ApproverInfo>;

    constructor(id: string, appTypeDescription: string, defaultName: string) {
      this.id = id;
      this.appTypeDescription = ko.observable(appTypeDescription);
      let arr = new Array(5);
      _.fill(arr, ko.observable(_.clone(new ApproverInfo(null, defaultName))));
      this.approvers = ko.observableArray(arr);
    }

    public wipeData(defaultName: string) {
      _.forEach(this.approvers(), data => {
        data.sid = null;
        data.name(defaultName);
      });
    }
  }

  export class ApproverInfo {
    sid: string;
    name: KnockoutObservable<string> = ko.observable(null);
    rowId: KnockoutObservable<string> = ko.observable(null);
    colId: KnockoutObservable<number> = ko.observable(null);

    constructor(sid: string, name: string) {
      this.sid = sid;
      this.name(name);
    }
  }

  class EmployeeModel {
    id: string;
    code: string;
    businessName: string;
    depName?: string;
    workplaceName?: string;

    constructor(init?: Partial<EmployeeModel>) {
      $.extend(this, init);
    }
  }

  export class SettingTypeUsed {
    /** 承認ルート区分 */
    employmentRootAtr: number;
    
    /** 申請種類 */
    applicationType: number;
    
    /** 確認ルート種類 */
    confirmRootType: number;
    
    /** 利用する */
    notUseAtr: number;
  }

  interface RoleModel {
    // 参照範囲
	  employeeReferenceRange: number;

    // 承認権限
    approvalAuthority: Boolean;
  }

  enum EmploymentRootAtr {
    /** 共通*/
    COMMON = 0,
    /** 申請*/
    APPLICATION = 1,
    /** 確認*/
    CONFIRMATION = 2,
    /** 任意項目*/
    ANYITEM = 3,
    /**届出*/
    NOTICE = 4,
    /**各業務エベント*/
    BUS_EVENT = 5
  }

  enum NotUseAtr {
    NOT_USE = 0,
    USE = 1
  }

  export enum ParentType {
    CMM018X = 0,
    CMM018R = 1
  } 
}
