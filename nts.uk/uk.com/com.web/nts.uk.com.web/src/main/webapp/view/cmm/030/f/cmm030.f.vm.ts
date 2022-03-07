/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.cmm030.f {

  const API = {
    getApproverHistory: "screen/com/cmm030/getApproverHistory/{0}",
    getSelfApproverSetting: "screen/com/cmm030/getSelfApproverSetting",
    deleteLastHist: "workflow/approvermanagement/workroot/selfapproversetting/deleteLastHist"
  };

  @bean()
  export class ScreenModel extends ko.ViewModel {
    employeeId: KnockoutObservable<string> = ko.observable("");
    employeeCode: KnockoutObservable<string> = ko.observable("");
    employeeName: KnockoutObservable<string> = ko.observable("");
    periodList: KnockoutObservableArray<PeriodModel> = ko.observableArray([]);
    approverList: KnockoutObservableArray<ApproverModel> = ko.observableArray([]);
    periodColumns: KnockoutObservableArray<any> = ko.observableArray([]);
    approverColumns: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedPeriodId: KnockoutObservable<string> = ko.observable(null);
    selectedApproverId: KnockoutObservable<string> = ko.observable(null);
    isFirstLineSelected: KnockoutComputed<boolean>;

    created(params?: any): void {
      const vm = this;
      vm.employeeId(params.employeeId);
      vm.employeeCode(params.employeeCode);
      vm.employeeName(params.employeeName);

      vm.periodColumns([
        { headerText: '', prop: 'id', width: '0px', hidden: true },
        { headerText: vm.$i18n("CMM030_84"), prop: 'period', width: '220px' },
        { headerText: '', prop: 'isOverlap', width: '20px' }
      ]);
      vm.approverColumns([
        { headerText: '', prop: 'id', width: '0px', hidden: true },
        { headerText: vm.$i18n("CMM030_85"), prop: 'applicationType', width: '150px'},
        { headerText: vm.$i18n("CMM030_86"), prop: 'approverName1', width: '100px' },
        { headerText: '', prop: 'approverName2', width: '100px' },
        { headerText: '', prop: 'approverName3', width: '100px' },
        { headerText: '', prop: 'approverName4', width: '100px' },
        { headerText: '', prop: 'approverName5', width: '100px' },
      ]);

      vm.isFirstLineSelected = ko.computed(() => {
        if (_.isEmpty(vm.periodList())) {
          return false;
        }
        return vm.periodList()[0].id === vm.selectedPeriodId();
      });

      vm.selectedPeriodId.subscribe(value => {
        const period = _.find(vm.periodList(), { id: value });
        if (!_.isNil(period)) {
          vm.$blockui("grayout");
          vm.getSelfApproverSetting(period).always(() => vm.$blockui("clear"));
        }
      });
    }

    mounted(): void {
      const vm = this;
      vm.$blockui("grayout");
      vm.getApproverHistory().always(() => {
        $("#F2_1").focus();
        vm.$nextTick(() => $("th#F4_approverName1").attr("colspan", 5));
        vm.$blockui("clear");
      });
    }

    public processDeleteHist() {
      const vm = this;
      const period = _.find(vm.periodList(), { id: vm.selectedPeriodId() });
      if (period.operationMode === 0) {
        vm.$dialog.error({ messageId: "Msg_3305" });
        return;
      }
      vm.$dialog.confirm({ messageId: "Msg_3297" })
      .then(value => {
        if (value === "yes") {
          vm.$blockui("grayout");
          vm.deleteLastHist().then(() => vm.getApproverHistory())
          .always(() => {
            $("#F2_1").focus();
            vm.$blockui("clear");
          });
        }
      });
    }

    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }

    /**
     * Ｆ：承認者の履歴を取得する
     */
    private getApproverHistory(): JQueryPromise<any> {
      const vm = this;
      return vm.$ajax(nts.uk.text.format(API.getApproverHistory, vm.employeeId()))
      .then(result => {
        const periodList = _.chain(result).orderBy("startDate", "desc")
        .map((data: any) => new PeriodModel({
          id: nts.uk.util.randomId(),
          startDate: data.startDate,
          period: vm.$i18n("CMM030_112", [data.startDate, data.endDate]),
          isOverlap: data.overlap ? vm.$i18n("CMM030_172") : null,
          operationMode: data.operationMode,
          approvalIds: data.approvalIds
        })).value();
        vm.periodList(periodList);
        if (!_.isEmpty(periodList)) {
          vm.selectedPeriodId(periodList[0].id);
        }
      });
    }

    /**
     * Ｆ：自分の承認者設定を取得する
     */
    private getSelfApproverSetting(period: PeriodModel): JQueryPromise<any> {
      const vm = this;
      const param = {
        sid: vm.employeeId(),
        approvalIds: period.approvalIds
      };
      return vm.$ajax(API.getSelfApproverSetting, param)
      .then(result => {
        if (_.isEmpty(result)) {
          vm.$dialog.error({ messageId: "Msg_3298" });
          return;
        }
        const approvers: ApproverModel[] = _.chain(result.personApprovalRoots)
        .orderBy((data: any) => [data.apprRoot.employmentRootAtr, data.apprRoot.applicationType, data.apprRoot.confirmRootType], ["asc", "asc", "asc"])
        .map((data: any) => {
          const approverNames = _.chain(result.approvalPhases).orderBy("phaseOrder", "desc")
          .filter((phase: any) => phase.approvalId === data.approvalId)
          .map((phase: any) => {
            const emp: any = _.find(result.employees, { "sid": phase.approver[0].employeeId });
            return _.isNil(emp) ? vm.$i18n("CMM030_25") : emp.employeeName;
          }).uniq().value();
          return new ApproverModel({
            id: nts.uk.util.randomId(),
            periodId: period.id,
            applicationType: vm.getApplicationType(data.apprRoot.employmentRootAtr, data.apprRoot.applicationType, data.apprRoot.confirmationRootType),
            approvalId: data.approvalId,
            approverName1: approverNames[0],
            approverName2: approverNames[1],
            approverName3: approverNames[2],
            approverName4: approverNames[3],
            approverName5: approverNames[4]
          });
        }).value();
        vm.approverList(approvers);
        vm.$nextTick(() => $("#F4 td").addClass("limited-label"));
      });
    }

    /**
     * 最終履歴の削除する
     */
    private deleteLastHist(): JQueryPromise<any> {
      const vm = this;
      const period = _.find(vm.periodList(), { id: vm.selectedPeriodId() });
      const param = {
        sid: vm.employeeId(),
        startDate: moment.utc(period.startDate, "YYYY/MM/DD").toISOString(),
        approvalIds: period.approvalIds
      };
      return vm.$ajax(API.deleteLastHist, param)
      .then(() => vm.$dialog.info({ messageId: "Msg_16" }))
      .fail(err => vm.$dialog.error({ messageId: err.messageId }));
    }

    private getApplicationType(employmentRootAtr: number, applicationType: number, confirmationRootType: number): string {
      const vm = this;
      let enums: any[];
      let typeAtr: number;
      switch (employmentRootAtr) {
        case EmploymentRootAtr.COMMON: return vm.$i18n("CMM030_11");
        case EmploymentRootAtr.APPLICATION: 
          enums = __viewContext.enums["ApplicationType"];
          typeAtr = applicationType;
          break;
        case EmploymentRootAtr.CONFIRMATION:
          enums = __viewContext.enums["ConfirmationRootType"];
          typeAtr = confirmationRootType;
          break;
      }
      return _.find(enums, { value: typeAtr }).name;
    }
  }

  class PeriodModel {
    id: string;
    startDate: string;
    period: string;
    isOverlap: string;
    operationMode: number;
    approvalIds: string[];

    constructor(init?: Partial<PeriodModel>) {
      $.extend(this, init);
    }
  }

  class ApproverModel {
    id: string;
    periodId: string;
    approvalId: string;
    applicationType: string;
    approverName1: string;
    approverName2: string;
    approverName3: string;
    approverName4: string;
    approverName5: string;

    constructor(init?: Partial<ApproverModel>) {
      $.extend(this, init);
    }
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
}