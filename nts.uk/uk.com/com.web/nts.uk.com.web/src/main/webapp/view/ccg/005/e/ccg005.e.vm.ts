/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.view.ccg005.e.screenModel {

  const API = {
    getDisplayAttendanceData: "screen/com/ccg005/get-display-attendance-data",
    getAttendanceInformation: "screen/com/ccg005/get-attendance-information",
    getDisplayInfoAfterSelect: "screen/com/ccg005/get-information-after-select",
    searchForEmployee: "screen/com/ccg005/get-employee-search",
    getGoOutInformation: "screen/com/ccg005/get-go-out-information"
  };
  @bean()
  export class ViewModel extends ko.ViewModel {
    goOutDate: KnockoutObservable<string> = ko.observable(moment().utc().format("YYYY/MM/DD"));
    goOutTime: KnockoutObservable<number> = ko.observable();
    comebackTime: KnockoutObservable<number> = ko.observable();
    goOutReason: KnockoutObservable<string> = ko.observable();
    sid: KnockoutObservable<string> = ko.observable();
    employeeName: KnockoutObservable<string> = ko.observable("Dong Den");
    
    created() {
      const vm = this;
      ////
      vm.$ajax(API.getDisplayAttendanceData);
      ////
      vm.$ajax(API.getAttendanceInformation, {
        empIds: [
          {
            sid: "e49f9bde-02a6-4559-852a-cb7b5f14502b",
            pid: "a0dcdd3e-71c7-4fe7-93db-6ea11dc128cf"
          }
        ],
        baseDate: moment.utc(),
        emojiUsage: true
      });
      ////
      vm.$ajax(API.getDisplayInfoAfterSelect, {
        wkspIds: ["1b8b003e-8348-44bc-8cca-54aecb1a74ef","77628222-087c-43c0-8dd7-a34de161bf7d","4865aea2-48cb-43ed-b1c5-150e24f7fa99"],
        baseDate: moment.utc(),
        emojiUsage: true
      });
     ////
     vm.$ajax(API.searchForEmployee, {
      keyWorks: "稲熊", // 	稲熊　０１ 000001
      baseDate: moment.utc(),
      emojiUsage: true
    });
     ////
     vm.$ajax(API.getGoOutInformation, {
      sid: "e49f9bde-02a6-4559-852a-cb7b5f14502b", 
      date: moment.utc(),
    });
    }

    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }

    public deleteConfirm() {
      const vm = this;
      vm.$dialog.confirm({ messageId: "Msg_18" }).then((result) => {
        if (result === "yes") {
            const gouOutInfo = new GoOutEmployeeInformationDel({
                gouOutDate: vm.goOutDate(),
                sid:  vm.sid(),
              });
              console.log("delete: ");
              console.log(gouOutInfo)
              //TODO: Call API here
        }
      });
    }

    public save() {
      const vm = this;
      const gouOutInfo = new GoOutEmployeeInformation({
        goOutTime: vm.goOutTime(),
        goOutReason: vm.goOutReason(),
        gouOutDate: vm.goOutDate(),
        comebackTime: vm.comebackTime(),
        sid:  vm.sid(),
      });
      console.log("save: ");
      console.log(gouOutInfo)
      //TODO: Call API here
    }
  }

  //社員の外出情報 Save or Update
  class GoOutEmployeeInformation {
    // 外出時刻
    goOutTime: number;

    // 外出理由
    goOutReason: string;

    // 年月日
    gouOutDate: string;

    // 戻り時刻
    comebackTime: number;

    // 社員ID
    sid: string;

    constructor(init?: Partial<GoOutEmployeeInformation>) {
      $.extend(this, init);
    }
  }

  //社員の外出情報 Delete
  class GoOutEmployeeInformationDel {
    // 年月日
    gouOutDate: string;

    // 社員ID
    sid: string;

    constructor(init?: Partial<GoOutEmployeeInformationDel>) {
      $.extend(this, init);
    }
  }
}
