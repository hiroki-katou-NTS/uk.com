/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ktg026.a.viewmodel {
  import windows = nts.uk.ui.windows;
  import format = nts.uk.time.format;

  const API = {
    startScreen: 'screen/at/ktg26/startScreen',
    extractOvertime: 'screen/at/ktg26/extractOvertime'
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

    employeesOvertime: EmployeesOvertimeDisplay = new EmployeesOvertimeDisplay();

    created() {
      const vm = this;
      vm.$blockui('grayout');
      vm.employeeId = (__viewContext as any).user.employeeId;
      vm.employeeName = ko.observable('');
      vm.dataTable = ko.observableArray([]);
      let targetDate = null;
      const cache = windows.getShared('cache');
      vm.$window.storage('KTG026_PARAM').then((data: any) => {
        if (data) {
          vm.employeeId = data.employeeId;
          targetDate = data.targetDate;
        }
      });

      const currentOrNextMonth = !!cache ? cache.currentOrNextMonth : 1; // 1: 従業員参照モード 2: 上長参照モード

      const requestBody: Ktg026BodyParmas = new Ktg026BodyParmas({
        employeeId: vm.employeeId,
        targetDate: targetDate,
        targetYear: null,
        currentOrNextMonth: currentOrNextMonth
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
        .fail(err => {
          vm.$dialog.alert(err);
        })
        .always(() => vm.$blockui('clear'));
    }

    mounted() {
      const vm = this;
      vm.targetYear.subscribe(year => {
        vm.$blockui('grayout');
        const processingYm = vm.employeesOvertime.closingPeriod ? vm.employeesOvertime.closingPeriod.processingYm : moment().format('YYYYMM');
        const requestBody: Ktg026BodyParmas = new Ktg026BodyParmas({
          employeeId: vm.employeeId,
          closingId: vm.employeesOvertime.closureID,
          targetYear: Number(year),
          processingYm: processingYm
        });

        vm.extractOvertime(requestBody);
      });
    }

    private onRefreshToppage() {
      const vm = this;
      vm.$blockui('grayout');
      const cache = windows.getShared('cache');
      const processingYm = vm.employeesOvertime.closingPeriod ? vm.employeesOvertime.closingPeriod.processingYm : moment().format('YYYYMM');
      const targetYear = cache.currentOrNextMonth === 1 ? vm.employeesOvertime.yearIncludeThisMonth : vm.employeesOvertime.yearIncludeNextMonth;
      const requestBody: Ktg026BodyParmas = new Ktg026BodyParmas({
        employeeId: vm.employeeId,
        closingId: vm.employeesOvertime.closureID,
        targetYear: targetYear,
        processingYm: processingYm
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
        .fail(err => {
          vm.$dialog.alert(err);
        })
        .always(() => vm.$blockui('clear'));
    }

    private displayDataTable(data: EmployeesOvertimeDisplay): void {
      const vm = this;
      if (!!data.ymOvertimes) {
        const dataTable = _.map(data.ymOvertimes, item => new EmployeeOvertimeHours(
          item.yearMonth,
          item.agreeTime ? item.agreeTime.agreementTime.agreementTime : 0,
          item.agreeTime ? item.agreeTime.agreMax.agreementTime : 0,
          item.agreeTime ? item.agreeTime.state : 0
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

  export class EmployeesOvertimeDisplay {
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

    constructor(init?: Partial<EmployeesOvertimeDisplay>) {
      $.extend(this, init);
    }

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
    // 色
    cssClass: string;

    constructor(yearMonth: number, agreementTime: number, agreementMaxTime: number, state: number) {
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
      this.cssClass = this.getCssClass(state);
    }

    // 色	※色定義-就業.xlsxを参照
    getCssClass(state: number): string {
      if (state === 2) {
        return 'exceeding-limit-alarm';
      } else if (state === 1) {
        return 'exceeding-limit-error';
      } else if (state === 7) {
        return 'special-exceeding-limit';
      } else if (state === 6) {
        return 'special-exceeding-limit-error';
      } else if (state === 4) {
        return 'special-exceeded-limit-alarm';
      } else if (state === 3) {
        return 'special-exceeded-limit-error';
      }
      return '';
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

