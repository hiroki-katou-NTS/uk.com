/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmf001.n {
  const API = {
    findOne: "at/shared/scherec/leaveCount/get",
    register: "at/shared/scherec/leaveCount/register",
    save: "at/shared/scherec/leaveCount/save",
    findAll: "at/shared/scherec/leaveCount/findAll"
  };
  const LEAVE_TYPE = 3;

  @bean()
  export class ScreenModel extends ko.ViewModel {
    manageDistinctList: KnockoutObservableArray<any> = ko.observableArray(__viewContext.enums.ManageDistinct);
    selectedManageDistinct: KnockoutObservable<number> = ko.observable(0);
    vacationTimeUnitList: KnockoutObservableArray<any> = ko.observableArray(__viewContext.enums.TimeDigestiveUnit);
    timeUnit: KnockoutObservable<number> = ko.observable(0);
    timeManageType: KnockoutObservable<number> = ko.observable(1);
    enableTimeSetting: KnockoutComputed<boolean>= ko.computed(function() {
      return this.timeManageType() == 1;
    }, this);

    mounted() {
      const vm = this;
      vm.$blockui("grayout");
      $.when(vm.$ajax(API.findOne), vm.$ajax(API.findAll))
      .then((result1: WorkDaysNumberOnLeaveCountDto, result2: any) => {
        const isCounting = !!_.includes(result1.countedLeaveList, LEAVE_TYPE);
        vm.selectedManageDistinct(isCounting ? 1 : 0);
      if (result2 !=null) {
        vm.timeManageType(result2.timeManageType);
        vm.timeUnit(result2.timeUnit);
      } else {
        vm.timeManageType(1);
        vm.timeUnit(0);
      }
      })
      .fail(err => vm.$dialog.error({messageId: err.messageId}))
      .always(() => {
        vm.$blockui("clear");
        // Fix tabindex
        vm.$nextTick(() => $("#N1_4").attr("tabindex", 1));
      });
    }

    public processSave() {
      const vm = this;
      vm.$blockui("grayout");
      const param = {
        isCounting: vm.selectedManageDistinct() === 1,
        leaveType: LEAVE_TYPE
      };

      const paramTimeManager = {
        timeManageType: vm.timeManageType(),
        timeUnit: vm.timeUnit()
      };

      $.when(vm.$ajax(API.register, param), vm.$ajax(API.save, paramTimeManager))
      .then(() => vm.$dialog.info({ messageId: "Msg_15" }))
      .then(() => vm.$blockui("clear"))
      .then(() => vm.processCloseDialog());
      }

    public processCloseDialog() {
      const vm = this;
      vm.$window.close();
    }
  }

  export interface WorkDaysNumberOnLeaveCountDto {
    // 会社ID
    cid: string;

    // カウントする休暇一覧
    countedLeaveList: Number[];
  }
}