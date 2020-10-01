/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ktg027.a.viewmodel {
  import formatById = nts.uk.time.format.byId;
  import getShared = nts.uk.ui.windows.getShared;
  import setShared = nts.uk.ui.windows.setShared;
  const API = {
    getDataInit: "screen/at/overtimehours/getOvertimedDisplayForSuperiorsDto/",
    getDataWhenChangeDate: "screen/at/overtimehours/onChangeDate/",
  };

  @bean()
  export class ViewModel extends ko.ViewModel {
    // A1_2
    year: KnockoutObservable<number> = ko.observable(0);
    currentOrNextMonth: KnockoutObservable<number> = ko.observable(0);
    listEmp: KnockoutObservableArray<PersonEmpBasicInfoImport> = ko.observableArray([]);
    // data table
    listOvertimeByEmp: KnockoutObservableArray<any> = ko.observableArray([]);
    // selected employee
    selectedEmp: any;
    closureId: KnockoutObservable<number> = ko.observable(0);

    created() {
      const vm = this;
      vm.$blockui("grayout");
      //get currentOrNextMonth in cache
      vm.currentOrNextMonth(parseInt(getShared("cache").currentOrNextMonth));
      //call API init
      vm.$ajax(API.getDataInit + vm.currentOrNextMonth())
        .then((response) => {
          if (!response.closingInformationForNextMonth) {
            vm.year(response.closingInformationForCurrentMonth.processingYm);
          } else {
            vm.year(response.closingInformationForNextMonth.processingYm);
          }
          vm.listEmp(response.personalInformationOfSubordinateEmployees);
          vm.listOvertimeByEmp(response.overtimeOfSubordinateEmployees);
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

    // event when select item
    public selectItem(item: any) {
      const vm = this;
      vm.selectedEmp = item;
    }

    // event when change date
    private onChangeDate(dateChange: number) {
      const vm = this;
      // vm.$blockui("grayout");
      vm.$ajax(API.getDataWhenChangeDate + vm.closureId() + "/" + dateChange)
        .then((response) => {
          if(!response.overtimeOfSubordinateEmployees){
            vm.listEmp([]);
            vm.listOvertimeByEmp([]);
          }else {
            vm.listEmp(response.personalInformationOfSubordinateEmployees);
            vm.listOvertimeByEmp(response.overtimeOfSubordinateEmployees);
          }
        })
        // .always(() => vm.$blockui("clear"))
    }

    // event open screen KTG026
    public openKTG026(item: any) {
      let companyID: any = ko.observable(__viewContext.user.companyId);
      const vm = this;
      let paramKTG026 = {
        companyId: companyID,
        employeeId: item.employeeCD,
        targetDate: vm.year(),
        targetYear: "s",
        mode: "Superior",
      };
      vm.$window.storage("KTG026_PARAM", {
        paramKTG026,
      });
      vm.$window.modal("/view/ktg/026/a/superior.xhtml");
    }

    // event open screen KDW003
    public openKDW003(item: any) {
      const vm = this;
      let paramKDW003 = {
        lstEmployeeShare: item.employeeCD,
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
    employeeID: string;
    // 状態
    status: any;
    // 法定上限対象時間
    legalUpperTime: any;
    // 36協定対象時間
    agreementTime: any;
    // 年月
    yearMonth: number;
    // 内訳
    breakdown: any;
    constructor(init?: Partial<CurrentClosingPeriod>) {
      $.extend(this, init);
    }
  }

  export class AcquisitionOfOvertimeHoursOfEmployeesDto {
    // 配下社員の個人情報
    personalInformationOfSubordinateEmployees: any;
    // 配下社員の時間外時間
    OvertimeOfSubordinateEmployees: any;
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
  }
}
