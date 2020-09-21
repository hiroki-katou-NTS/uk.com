/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ktg026.a.viewmodel {
  import windows = nts.uk.ui.windows;
  import format = nts.uk.time.format;

  const API = {
    startScreen: 'screen/at/ktg26/startScreen',
    extractOvertime: 'screen/at/ktg26/startScreen/extractOvertime'
  };

  @bean()
  export class ViewModel extends ko.ViewModel {
    // A1_2 対象年選択
    targetYear: KnockoutObservable<string> = ko.observable('');
    excessTimes: KnockoutObservable<number> = ko.observable(0);
    // A1_5 超過回数
    exceededNumber: KnockoutComputed<string> = ko.computed(() => {
      return this.$i18n('KTG026_6', [`${this.targetYear()}`, `${this.excessTimes()}回`]);
    });
    // A1_6 社員名
    employeeName: KnockoutObservable<String>;

    dataTable: KnockoutObservableArray<EmployeeOvertimeHours>;

    employeeId: string;

    employeesOvertime: EmployeesOvertimeDisplay;

    created() {
      const vm = this;
      vm.$blockui('grayout');
      vm.employeeName = ko.observable('');
      vm.dataTable = ko.observableArray([]);
      const cache = windows.getShared('cache');
      const dataStored = vm.$window.storage('KTG026_PARAM') as any;

      vm.employeeId = !!dataStored ? dataStored.employeeId : (__viewContext as any).user.employeeId;
      const targetDate = !!dataStored ? dataStored.targetDate : null;

      const requestBody: Ktg026BodyParmas = new Ktg026BodyParmas({
        employeeId: vm.employeeId,
        targetDate: targetDate,
        targetYear: null,
        currentOrNextMonth: cache.currentOrNextMonth
      });

      vm.$ajax('at', API.startScreen, requestBody)
        .then((response: EmployeesOvertimeDisplay) => {
          if (!!response) {
            vm.employeesOvertime = response;
            vm.excessTimes(response.agreeInfo.excessTimes);
            vm.targetYear(response.displayYear.toString());
            vm.employeeName(response.empInfo.businessName);
            vm.displayDataTable(response);
            const targetYear = cache.currentOrNextMonth === 1 ? response.yearIncludeThisMonth : response.yearIncludeNextMonth;
            vm.targetYear(targetYear.toString());
          }
        })
        .always(() => vm.$blockui('clear'));

    }

    mounted() {
      const vm = this;
      vm.targetYear.subscribe(() => {
        vm.$blockui('grayout');
        const requestBody: Ktg026BodyParmas = new Ktg026BodyParmas({
          employeeId: vm.employeeId,
          closingId: vm.employeesOvertime.closureID,
          targetYear: Number(vm.targetYear()),
          processingYm: vm.employeesOvertime.closingPeriod.processingYm
        });

        vm.extractOvertime(requestBody);
      });
    }

    private onRefreshToppage() {
      const vm = this;
      vm.$blockui('grayout');
      const cache = windows.getShared('cache');
      const targetYear = cache.currentOrNextMonth === 1 ? vm.employeesOvertime.yearIncludeThisMonth : vm.employeesOvertime.yearIncludeNextMonth;
      const requestBody: Ktg026BodyParmas = new Ktg026BodyParmas({
        employeeId: vm.employeeId,
        closingId: vm.employeesOvertime.closureID,
        targetYear: targetYear,
        processingYm: vm.employeesOvertime.closingPeriod.processingYm
      });

      vm.extractOvertime(requestBody);
    }

    private extractOvertime(requestBody: Ktg026BodyParmas): void {
      const vm = this;
      vm.$blockui('grayout');
      // 指定する年と指定する社員の時間外時間と超過回数の取得
      vm.$ajax('at', API.extractOvertime, requestBody)
        .then((response: EmployeesOvertimeDisplay) => {
          if (!!response) {
            vm.displayDataTable(response);
          }
        })
        .always(() => vm.$blockui('clear'));
    }

    private displayDataTable(data: EmployeesOvertimeDisplay): void {
      const vm = this;
      if (!!data.ymOvertimes) {
        const dataTable = _.map(data.ymOvertimes, item => new EmployeeOvertimeHours(
          item.yearMonth,
          item.agreeTime ? item.agreeTime.agreementTime.agreementTime : 0,
          item.agreeTime ? item.agreeTime.agreementMaxTime.agreementTime : 0
        ));
        vm.dataTable(dataTable);
      }
    }

    /**
     * 閉じる
     */
    onCloseWindow(): void {
      const vm = this;
      vm.$window.close();
    }

  }

  export interface EmployeesOvertimeDisplay {
    // ログイン者の締めID
    closureID: number;
    // 対象社員の個人情報
    empInfo: any;
    // 対象社員の各月の時間外時間
    ymOvertimes: any[];
    // 対象社員の年間超過回数
    agreeInfo: any;
    // 当月の締め情報
    closingPeriod: any;
    // 当月含む年
    yearIncludeThisMonth: number;
    // 翌月含む年
    yearIncludeNextMonth: number;
    // 表示する年
    displayYear: number;

  }

  export class EmployeeOvertimeHours {
    // 年月
    yearMonth: any;
    // agreementTime
    agreementTime: number;
    // agreementMaxTime
    agreementMaxTime: number;
    // subAgreement
    subAgreement: number;
    // 対象月の時間外時間
    agreementTimeDisplay: string;
    // agreementMaxTimeDisplay
    agreementMaxTimeDisplay: string;
    // 法定外時間のグラフ
    nonStatutoryTime: string
    // 法定内時間のグラフ
    legalTime: string;

    constructor(yearMonth: number, agreementTime: number, agreementMaxTime: number) {
      this.yearMonth = moment.utc(yearMonth, "YYYYMM").format("YYYY/MM");
      this.agreementTime = agreementTime;
      this.agreementMaxTime = agreementMaxTime;
      this.subAgreement = agreementMaxTime - agreementTime;
      // 法定外時間外時間のグラフの領域0:00～100:00
      // 100:00以上の時間は表示できない
      this.agreementTimeDisplay = format.byId('Clock_Short_HM', agreementTime);
      this.agreementMaxTimeDisplay = format.byId('Clock_Short_HM', agreementMaxTime - agreementTime);
      this.nonStatutoryTime = agreementTime > 6000 ? '200px' : `${2 * agreementTime / 60}px`;
      this.legalTime = agreementTime > 6000 ? '' : `${2 * (agreementMaxTime - agreementTime) / 60}px`;
    }
  }

  export class Ktg026BodyParmas {
    // 社員ID
    employeeId: string;
    // 締めID
    closingId: number;
    // 対象年月
    targetDate: number | null;
    // 対象年
    targetYear: number | null;
    // 表示年月
    currentOrNextMonth: number;
    // 処理年月
    processingYm: number;

    constructor(init?: Partial<Ktg026BodyParmas>) {
      $.extend(this, init);
    }
  }

}

