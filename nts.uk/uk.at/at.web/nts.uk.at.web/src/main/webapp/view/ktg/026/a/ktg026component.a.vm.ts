/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ktg026.a.Ktg026ComponentViewModel {
  import windows = nts.uk.ui.windows;
  import format = nts.uk.time.format;

  const API = {
    startScreen: 'screen/at/ktg26/startScreen',
    extractOvertime: 'screen/at/ktg26/extractOvertime'
  };

  const STYLE = `
  #ktg027-contents-area {
    padding-top: 5px;
    padding-left: 5px;
    width: 440px;
    border: 1px groove;
  }

  .ktg027-grid {
    display: grid;
  }
  .ktg027-block {
    display: block;
  }
  .big-text {
    font-size: 1.5rem;
    font-weight: bold;
  }
  .title-border {
    padding: 10px;
    border-left: 5px solid #48b5cb;
    border-top: 1px groove;
    border-bottom: 1px groove;
  }
  .ktg027-mt-10 {
    margin-top: 10px;
  }
  .p-10 {
    padding: 10px;
  }
  .pl-0 {
    padding-left: 0px;
  }
  .non-statutory-color {
    color: #49bfa8;
  }
  .non-statutory-bg-color {
    background-color: #49bfa8;
  }
  .time-outside-color {
    color: #e05f4e;
  }
  .time-outside-bg-color {
    background-color: #e05f4e;
  }
  .pr-20 {
    padding-right: 20px;
  }
  .pb-0 {
    padding-bottom: 0px;
  }
  .scroll {
    overflow-y: auto;
    max-height: 225px;
    margin-bottom: 20px;
  }
  .scroll thead th {
    position: sticky;
    top: 0;
    background: white;
  }
  .pl-10 {
    padding-left: 10px;
  }
  .right {
    float: right;
  }
  .flex100 {
    display: flex;
    width: 100%;
  }
  .flex {
    display: flex;
  }
  .left65 {
    float: left;
    width: 65%;
  }
  .pl-20 {
    padding-left: 20px;
  }
  .dashed45 {
    width: 90px;
    height: 30px;
    position: absolute;
    border-right: 1px dashed;
  }
  .dashed80 {
    width: 160px;
    height: 30px;
    position: absolute;
    border-right: 1px dashed;
  }
  tr td .label {
    margin-top: 5px;
    height: 20px;
  }
  .w-200 {
    width: 200px;
  }
  .inline-flex {
    display:inline-flex;
  }
  .border-bot {
    border-bottom: 1px groove;
  }
  .fixed45 {
    padding-left: 90px;
    position: absolute;
  }
  .fixed80 {
    padding-left: 160px;
  }
  .data-text {
    padding: 3px 20px 3px 0px;
  }
  .mw-200 {
    max-width: 200px;
  }
  .gray-bg {
    background-color: #e0e0e0;
  }
  table thead tr th {
    z-index: 1;
  }
  .bold {
    font-weight: bold;
  }
  .relative {
    position: relative;
  }
  .text-right {
    text-align: right;
  }
  .h385 {
    height: 385px;
  }
  .mh-195 {
    max-height: 195px;
  }
  .h-350 {
    height: 350px;
  }
  .mh-200 {
    max-height: 200px;
  }
  
  /* 限度アラーム時間超過 */
  .exceeding-limit-alarm {
    background-color: #f6f636; /* 36協定アラーム */
    color: #ff0000; /* 36協定アラーム文字 */
  }
  /* 限度エラー時間超過 */
  .exceeding-limit-error {
    background-color: #0c0606; /* 36協定エラー */
    color: #ffffff; /* 36協定エラー文字 */
  }
  /* 限度アラーム時間超過（特例あり） */
  .special-exceeding-limit {
    background-color: #eb9152; /* 36協定特例 */
  }
  /* 限度エラー時間超過（特例あり） */
  .special-exceeding-limit-error {
    background-color: #eb9152; /* 36協定特例 */
  }
  /* 特例限度アラーム時間超過 */
  .special-exceeded-limit-alarm  {
    background-color: #f6f636; /* 36協定アラーム */
    color: #ff0000; /* 36協定アラーム文字 */
  }
  /* 特例限度エラー時間超過 */
  .special-exceeded-limit-error {
    background-color: #0c0606; /* 36協定エラー */
    color: #ffffff; /* 36協定エラー文字 */
  }
  
  `

  @component({
    name: 'ktg026-component',
    template: `<div id="contents-area">
    <div class="title-border">
      <!-- A1_1 時間外労働・休日労働状況 -->
      <label id="A1_1" class="big-text" data-bind="i18n: 'KTG026_5'"></label>
    </div>

    <table class="flex">
      <tbody class="flex100">
        <tr class="flex100">
          <td class="p-10 left65">
            <!-- A1_2 年 -->
            <div id="A1_2" tabindex="1" data-bind="ntsDatePicker: {
              name: '#[KTG026_1]',
              value: targetYear,
              dateFormat: 'YYYY',
              valueFormat: 'YYYY',
              showJumpButtons: true
            }"></div>
            <!-- A1_5 js-exceededNumber -->
            <label id="A1_5" class="ktg027-grid ktg027-mt-10" data-bind="text: exceededNumber"></label>
          </td>
          <td class="ktg027-block p-10" style="width: 60%">
            <div class="right">
              <!-- A1_3 ■時間外労働時間 -->
              <label id="A1_3" class="ktg027-grid non-statutory-color" data-bind="i18n: 'KTG026_2'"></label>
              <!-- A1_4 ■休日労働時間 -->
              <label id="A1_4" class="ktg027-grid time-outside-color" data-bind="i18n: 'KTG026_3'"></label>
            </div>
          </td>
        </tr>
    </tbody>
    </table>
    <table class="ktg027-block pl-10 scroll relative mh-200">
      <thead>
        <tr>
          <!-- A2_1 年月-->
          <th id="A2_1" data-bind="i18n: 'KTG026_7'"></th>
          <!-- A2_2 時間外時間 -->
          <th id="A2_2" data-bind="i18n: 'KTG026_4'"></th>
          <th class="w-200">
            <!-- A2_3 45:00 -->
            <span id="A2_3" class="fixed45">45:00</span>
            <!-- A2_4 80:00 -->
            <span id="A2_4" class="fixed80">80:00</span>
          </th>
        </tr>
      </thead>
      <tbody data-bind="foreach: dataTable">
        <tr class="mw-200">
          <!-- A3_1 js-yearMonth -->
          <td id="A3_1" class="border-bot data-text" data-bind="text: yearMonth"></td>
          <!-- A3_2 js-agreementTime -->
          <td id="A3_2" class="border-bot text-right" data-bind="text: agreementTimeDisplay, class: cssClass"></td>
          <td class="pl-20 inline-flex">
            <span class="dashed45"></span>
            <span class="dashed80"></span>
            <!-- A3_3 js-nonStatutoryTime -->
            <com:ko-if bind="agreementTime > 0">
              <span id="A3_3" class="label non-statutory-bg-color" data-bind="style: {width: nonStatutoryTime}"></span>
            </com:ko-if>
            <!-- A3_4 js-legalTime -->
            <com:ko-if bind="subAgreement > 0">
              <span id="A3_4" class="label time-outside-bg-color" data-bind="style: {width: legalTime}"></span>
            </com:ko-if>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <style>${STYLE}</style>
  `
  })

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

