/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ktg026.a.viewmodel {
  import windows = nts.uk.ui.windows;
  import format = nts.uk.time.format;

  @bean()
  export class ViewModel extends ko.ViewModel {
    paths = {
      startScreen: 'screen/at/ktg26/startScreen',
      extractOvertime: 'screen/at/ktg26/startScreen/extractOvertime'
    };
    // A1_2 対象年選択
    targetYear: KnockoutObservable<string> = ko.observable('');
    excessTimes: KnockoutObservable<number> = ko.observable(0);;
    // A1_5 超過回数
    exceededNumber: KnockoutComputed<string> = ko.computed(() => {
      return this.$i18n('KTG026_6', [`${this.targetYear()}`, `${this.excessTimes()}回`]);
    });
    referenceMode: KnockoutObservable<number> = ko.observable(1); // 1: 従業員参照モード | 2: 上長参照モード
    // A1_6 社員名
    employeeName: KnockoutObservable<String>;

    dataTable: KnockoutObservableArray<EmployeeOvertimeHours>;

    employeeId: string;
    
    created() {
      const vm = this;
      vm.$blockui('show');
      vm.employeeName = ko.observable('');
      vm.dataTable = ko.observableArray([]);
      vm.employeeId = (__viewContext as any).user.employeeId;
      let targetDate = null;
      const cache = windows.getShared('cache');

      const requestBody = {
        employeeId: vm.employeeId,
        targetDate: targetDate,
        targetYear: null,
        currentOrNextMonth: cache.currentOrNextMonth
      }

      vm.$ajax('at', vm.paths.startScreen, requestBody).then((response: EmployeesOvertimeDisplay) => {
        if (response) {
          vm.excessTimes(response.agreeInfo.excessTimes);
          vm.targetYear(response.displayYear.toString());
          vm.employeeName(response.empInfo.businessName);
          if (response.ymOvertimes) {
            const dataTable = response.ymOvertimes.map(item => new EmployeeOvertimeHours(
              item.yearMonth,
              item.agreeTime ? item.agreeTime.agreementTime.agreementTime : 0,
              item.agreeTime ? item.agreeTime.agreementMaxTime.agreementTime : 0
            ));
            vm.dataTable(dataTable);
          }
        }
      })
      .always(() => vm.$blockui('clear'));
      
    }

    mounted() {
      const vm = this;
      const cache = windows.getShared('cache');
      vm.targetYear.subscribe(year => {
        let requestBody: any;
        // トップページ表示年月．表示年月＝当月表示
        if (cache.currentOrNextMonth === 1) {
          const requestBody = {
            employeeId: vm.employeeId,
            closingId: cache.closingId,
            targetYear: vm.targetYear(),
            currentOrNextMonth: cache.currentOrNextMonth
          }
        }

        // トップページ表示年月．表示年月＝翌月表示
        if (cache.currentOrNextMonth === 2) {

        }
      })
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
    ymOvertimes: Array<any>;
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
    // 対象月の時間外時間
    agreementTime: string;
    agreementMaxTime: string;
    // 法定外時間のグラフ
    nonStatutoryTime: string
    // 法定内時間のグラフ
    legalTime: string;

    constructor(yearMonth: number, agreementTime: number, agreementMaxTime: number) {
        this.yearMonth = moment.utc(yearMonth, "YYYYMM").format("YYYY/MM");
        // 法定外時間外時間のグラフの領域0:00～100:00
        // 100:00以上の時間は表示できない
        this.agreementTime = format.byId('Clock_Short_HM', agreementTime);
        this.agreementMaxTime = format.byId('Clock_Short_HM', agreementMaxTime - agreementTime);
        this.nonStatutoryTime = agreementTime > 6000 ? '200px' : `${2*agreementTime/60}px`;
        this.legalTime = agreementTime > 6000 ? '' : `${2*(agreementMaxTime - agreementTime)/60}px`;
    }
  }

}

