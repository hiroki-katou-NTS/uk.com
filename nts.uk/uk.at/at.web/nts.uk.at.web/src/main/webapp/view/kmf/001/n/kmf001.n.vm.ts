/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmf001.n {
  const API = {
    findOne: "at/shared/scherec/leaveCount/get",
    register: "at/shared/scherec/leaveCount/register",
    findManageDistinct: "at/shared/scherec/leaveCount/timemanagementdistinct",
    findTimeUnit: "at/shared/scherec/leaveCount/timeunit",
    save: "at/shared/scherec/leaveCount/save",
    findAll: "at/shared/scherec/leaveCount/findAll"
  };
  const LEAVE_TYPE = 3;

  @bean()
  export class ScreenModel extends ko.ViewModel {
    manageDistinctList: KnockoutObservableArray<any> = ko.observableArray(__viewContext.enums.ManageDistinct);
    selectedManageDistinct: KnockoutObservable<number> = ko.observable(0);
    vacationTimeUnitList: KnockoutObservableArray<EnumerationModel> = ko.observableArray([]);
    timeUnit: KnockoutObservable<number> = ko.observable(0);
    timeManageMentDistinctList: KnockoutObservableArray<EnumerationModel> = ko.observableArray([]);
    timeManageType: KnockoutObservable<number> = ko.observable(1);
    enableTimeSetting: KnockoutObservable<boolean>= ko.computed(function() {
      return this.timeManageType() == 1;
    }, this);

    mounted() {
      const vm = this;
      var dfd = $.Deferred();
      vm.$blockui("grayout");
      vm.$ajax(API.findOne).then((result: WorkDaysNumberOnLeaveCountDto) => {
        const isCounting = !!_.includes(result.countedLeaveList, LEAVE_TYPE);
        vm.selectedManageDistinct(isCounting ? 1 : 0);
      // Fix tabindex
        vm.$nextTick(() => $("#N1_4").attr("tabindex", 1));
      }).always(() => vm.$blockui("clear"));

      // call api time management
      vm.$ajax(API.findManageDistinct).done(function(res: Array<EnumerationModel>) {
        vm.timeManageMentDistinctList(res);
      }).fail(function(res) {
          vm.$dialog.error(res.message);
      });
      // call api time unit
      vm.$ajax(API.findTimeUnit).done(function(res: Array<EnumerationModel>) {
          vm.vacationTimeUnitList(res);

      }).fail(function(res) {
          vm.$dialog.error(res.message);
      });
      // call api find all data
      vm.$ajax(API.findAll).done(function(res) {
        vm.timeManageType(res.timeManageType);
        vm.timeUnit(res.timeUnit);
      }).fail(function(res) {
         vm.$dialog.error(res.message);
      });

      return dfd.promise();
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

      vm.$ajax(API.register, param).then(() =>  vm.$ajax(API.save, paramTimeManager).then(() => vm.$dialog.info({ messageId: "Msg_15" })
      .then(() => vm.$blockui("clear")).then(() => vm.processCloseDialog())));
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

  export class EnumerationModel {

    value: number;
    fieldName: string;
    localizedName: string;

    constructor(value: number, fieldName: string, localizedName: string) {
        let self = this;
        self.value = value;
        self.fieldName = fieldName;
        self.localizedName = localizedName;
    }
  }
}