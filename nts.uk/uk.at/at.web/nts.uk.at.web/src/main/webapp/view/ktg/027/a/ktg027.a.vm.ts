/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ktg027.a.Ktg027ComponentViewModel {
  import formatById = nts.uk.time.format.byId;
  import getShared = nts.uk.ui.windows.getShared;
  import setShared = nts.uk.ui.windows.setShared;
  const API = {
    getDataInit: "screen/at/overtimehours/getOvertimedDisplayForSuperiorsDto/",
    getDataWhenChangeDate: "screen/at/overtimehours/onChangeDate/",
  };

  const KTG_027_Style = `#contents-area {
    padding-top: 5px;
    padding-left: 5px;
    width: 445x;
    height: 100%;
    border: 1px solid #B1B1B1;
  }
  .addSign {
    background: url("../image/addSign.png");
    margin: -8px 10px;
    border-radius: 5px;
    height: 125px;
    text-align: center;
    padding-bottom: 5px;
    background-position: center;
    background-repeat: no-repeat;
  }
  .sub {
    margin: 10px;
    border-radius: 5px;
    height: 28px;
    text-align: center;
    word-wrap: break-word;
  }
  .row-inline{
    display: inline-flex;
  }
  .custom-panel {
    width: 115px;
    height: 115px;
    margin: 5px;
    text-align: center;
    word-wrap: break-word;
  }
  .grid {
    display: flex;
  }
  .block {
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
    border-right: 1px groove;
  }
  .mt-10 {
    margin-top: 10px;
  }
  .p-10 {
    padding: 10px;
  }
  .pl-0 {
    width: 400px;
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
    padding-right: 10px;
  }
  .pb-0 {
    padding-bottom: 0px;
  }
  .scroll {
    overflow-y: auto;
    height: 270px;
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
    height: 60px;
  }
  .left40 {
    float: left;
    width: 40%;
  }
  .pl-20 {
    padding-left: 10px;
  }
  .position-relative {
    position: relative !important;
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
    z-index: 100000000;
  }
  .inline-flex {
    display:inline-flex;
  }
  .text-right {
    text-align: right;
  }
  .border-bot {
    border-bottom: 1px groove;
  }
  .fixed45 {
    padding-left: 93px;
    position: absolute;
  }
  .fixed80 {
    padding-left: 163px;
  }
  .data-text {
    padding: 5px 0px 5px 0px;
  }
  .mw-200 {
    max-width: 200px;
  }
  .gray-bg {
    background-color: #e0e0e0;
  }
  a:link, a:visited {
    color: inherit;
  }
  a:hover, a:active {
   color:forestgreen;
  }`;

  @component({
    name: "ktg027-component",
    template: `<div id="contents-area">
    <div class="title-border">
      <!-- A1_1 -->
      <label class="big-text" data-bind="i18n: 'KTG027_5'"></label>
    </div>
    <table class="flex">
      <tbody class="flex100">
        <tr class="flex100">
          <td class="p-10 left40">
            <!-- A1_2 -->
            <div data-bind="ntsDatePicker: {
              value: year,
              dateFormat: 'yearmonth',
              valueFormat: 'YYYYMM',
              showJumpButtons: true
            }"></div>
          </td>
          <td class="block p-10" style="width: 50%">
            <div class="right">
              <!-- A1_3 -->
              <label class="grid non-statutory-color" data-bind="i18n: 'KTG027_2'"></label>
              <!-- A1_4 -->
              <label class="grid time-outside-color" data-bind="i18n: 'KTG027_3'"></label>
            </div>
          </td>
        </tr>
    </tbody>
    </table>
    <div id="table" style="height: 290px;">
      <!-- A3 -->
      <table class="block pl-10 scroll">
        <thead>
          <tr>
            <!-- A2_1 -->
            <th style="width: 133px;" data-bind="i18n: 'KTG027_7'">}/th>
            <!-- A2_2 -->
            <th data-bind="i18n: 'KTG027_4'"></th>
            <th class="w-200">
              <!-- A2_3 -->
              <span class="fixed45">45:00</span>
              <!-- A2_4 -->
              <span class="fixed80">80:00</span>
            </th>
          </tr>
        </thead>
        <tbody data-bind="foreach: $component.listShowData">
          <tr class="mw-200" >
            <!-- A3_1 -->
            <td class="border-bot"><a href="#" data-bind="text: businessName, click: function() { $component.openKTG026($data) }"></a></td>
            <!-- A3_2 -->
            <td class="border-bot text-right"><a href="#" data-bind="text: $component.genTime(agreementTime), click:function() { $component.openKDW003($data) } "></a></td>
            <td class="pl-20 inline-flex position-relative">
              <!-- A3_3 -->
              <div class="label non-statutory-bg-color" data-bind="style: { width: $component.genWidthByTime(agreementTime)}"></div>
              <!-- A3_4 -->
              <div class="label time-outside-bg-color" data-bind="style: { width: $component.genWidthByTime(legalUpperTime)}"></div> 
              <!-- A2_5 -->
              <div class="dashed45"></div>
              <!-- A2_6 -->
              <div class="dashed80"></div>
            </td>
          </tr>
        </tbody>
      </table>  
    </div>
  </div>
  <style>${KTG_027_Style}</style>`
  })

  export class Ktg027ComponentViewModel extends ko.ViewModel {
    // A1_2
    year: KnockoutObservable<number> = ko.observable(0);
    currentOrNextMonth: KnockoutObservable<number> = ko.observable(0);
    listEmp: KnockoutObservableArray<
      PersonEmpBasicInfoImport
    > = ko.observableArray([]);
    // list data for chart
    listShowData: KnockoutObservableArray<
      AgreementTimeDetail
    > = ko.observableArray([]);
    // selected employee
    selectedEmp: any;
    closureId: KnockoutObservable<number> = ko.observable(0);

    created() {
      const vm = this;
      vm.$blockui("grayout");
      //get currentOrNextMonth in cache
      vm.currentOrNextMonth(getShared('cache').currentOrNextMonth);
      //call API init
      vm.$ajax("at", API.getDataInit + vm.currentOrNextMonth())
        .then((response) => {
          if (!response.closingInformationForNextMonth) {
            vm.year(response.closingInformationForCurrentMonth.processingYm);
          } else {
            vm.year(response.closingInformationForNextMonth.processingYm);
          }
          vm.listEmp(response.personalInformationOfSubordinateEmployees);
          let lstTemp: AgreementTimeDetail[] = [];
          _.each(response.overtimeOfSubordinateEmployees, (item: any) => {
            let dataItem: AgreementTimeDetail = new AgreementTimeDetail();
            dataItem.employeeId = item.employeeId;
            dataItem.agreementTime = item.agreementTime.agreementTime;
            dataItem.legalUpperTime =
              item.agreMax.agreementTime - item.agreementTime.agreementTime;
            dataItem.status = item.state;
            lstTemp.push(dataItem);
          });
          _.each(lstTemp, (item: any) => {
            let itemData = _.find(vm.listEmp(), (itemE) => {
              return item.employeeId === itemE.employeeId;
            });
            item.businessName = itemData.businessName;
          });
          vm.listShowData(lstTemp);
          vm.closureId(response.closureId);
        })
        .always(() => vm.$blockui("clear"));
    }

    mounted() {
      const vm = this;
      vm.year.subscribe(function (dateChange) {
        if (vm.closureId() != 0) {
          vm.onChangeDate(dateChange);
        }
      });
    }

    // format number to HM
    public genTime(data: any) {
      return formatById("Clock_Short_HM", data);
    }

    // show chart
    public genWidthByTime(data: any) {
      return (data / 60) * 2 + "px";
    }

    // get text color of 対象時間
    public genTextColor(state: number) {
      if (state === 2 || state === 4) {
        return "#ff0000";
      } else if (state === 1 || state === 3) {
        return "#ffffff";
      }
      return "";
    }

    // get background color of 対象時間
    public genBackgroundColor(state: number) {
      if (state === 1 || state === 3) {
        return "#FD4D4D";
      } else if (state === 6 || state === 7) {
        return "#eb9152";
      } else if (state === 2 || state === 4) {
        return "#F6F636";
      }
      return "";
    }

    // event when select item
    public selectItem(item: any) {
      const vm = this;
      vm.selectedEmp = item;
    }

    // event when change date
    private onChangeDate(dateChange: number) {
      const vm = this;
      let listOvertimeByEmp: AgreementTimeDetail[] = [];
      vm.listShowData([]);
      vm.$blockui("grayout");
      vm.$ajax('at',
        API.getDataWhenChangeDate + vm.closureId() + "/" + dateChange
      ).then((response) => {
        if (!response.overtimeOfSubordinateEmployees) {
          vm.listEmp([]);
        } else {
          vm.listEmp(response.personalInformationOfSubordinateEmployees);
          listOvertimeByEmp = response.overtimeOfSubordinateEmployees;
          let lstTemp: AgreementTimeDetail[] = [];
          _.each(listOvertimeByEmp, (item: any) => {
            let dataItem: AgreementTimeDetail = new AgreementTimeDetail();
            dataItem.employeeId = item.employeeId;
            dataItem.agreementTime = item.agreementTime.agreementTime;
            dataItem.legalUpperTime =
              item.agreMax.agreementTime - item.agreementTime.agreementTime;
            dataItem.status = item.state;
            lstTemp.push(dataItem);
          });
          _.each(lstTemp, (item: any) => {
            let itemData = _.find(vm.listEmp(), (itemE) => {
              return item.employeeId === itemE.employeeId;
            });
            item.businessName = itemData.businessName;
          });
          vm.listShowData(lstTemp);
        }
      })
      .always(() => vm.$blockui("clear"))
    }

    // event open screen KTG026
    public openKTG026(item: any) {
      let companyID: any = ko.observable(__viewContext.user.companyId);
      const vm = this;
      let paramKTG026 = {
        companyId: companyID,
        employeeId: item.employeeID,
        targetDate: vm.year(),
        targetYear: "",
        mode: "Superior",
      };
      vm.$window.storage("KTG026_PARAM", {
        paramKTG026,
      });
      vm.$window.modal('at', '/view/ktg/026/a/superior.xhtml');
    }

    // event open screen KDW003
    public openKDW003(item: any) {
      const vm = this;
      let paramKDW003 = {
        lstEmployeeShare: item.employeeID,
        errorRefStartAtr: false,
        changePeriodAtr: true,
        screenMode: "Normal",
        displayFormat: "individual",
        initClock: "",
      };
      setShared("KDW003_PARAM", {
        paramKDW003,
      });
      vm.$jump("/view/kdw/003/a/index.xhtml");
    }
  }

  export class CurrentClosingPeriod {
    processingYm: number;
    closureStartDate: number;
    closureEndDate: number;
    constructor(init?: Partial<CurrentClosingPeriod>) {
      $.extend(this, init);
    }
  }

  export class PersonEmpBasicInfoImport {
    personId: string;
    employeeId: string;
    gender: number;
    businessName: string;
    birthday: number;
    employeeCode: string;
    jobEntryDate: number;
    retirementDate: number;
    constructor(init?: Partial<CurrentClosingPeriod>) {
      $.extend(this, init);
    }
  }

  export class AgreementTimeDetail {
    employeeId: string;
    // 状態
    status: any;
    // 法定上限対象時間
    legalUpperTime: any;
    // 36協定対象時間
    agreementTime: any;
    // name
    businessName: string;
    constructor(init?: Partial<CurrentClosingPeriod>) {
      $.extend(this, init);
    }
  }

  export class AcquisitionOfOvertimeHoursOfEmployeesDto {
    // 配下社員の個人情報
    personalInformationOfSubordinateEmployees: any;
    // 配下社員の時間外時間
    OvertimeOfSubordinateEmployees: any;
    constructor(init?: Partial<AcquisitionOfOvertimeHoursOfEmployeesDto>) {
      $.extend(this, init);
    }
  }

  //上長用の時間外時間表示
  export class OvertimedDisplayForSuperiorsDto {
    // ログイン者の締めID
    closureId: number;
    // 当月の締め情報
    closingInformationForCurrentMonth: CurrentClosingPeriod;
    // 配下社員の個人情報
    personalInformationOfSubordinateEmployees: PersonEmpBasicInfoImport[];
    // 配下社員の時間外時間
    overtimeOfSubordinateEmployees: AgreementTimeDetail[];
    // 翌月の締め情報
    closingInformationForNextMonth: CurrentClosingPeriod;
    constructor(init?: Partial<OvertimedDisplayForSuperiorsDto>) {
      $.extend(this, init);
    }
  }
}
