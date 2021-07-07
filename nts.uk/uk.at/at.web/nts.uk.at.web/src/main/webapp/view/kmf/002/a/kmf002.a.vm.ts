/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.cmf003.i {

  const API = {
    save: "at/shared/holidaysetting/config/save",
    findAll: "at/shared/holidaysetting/config/find",
  };

  @bean()
  export class ScreenModel extends ko.ViewModel {
    
    managePublicHoliday: KnockoutObservable<number> = ko.observable(0);
    publicHdCarryOverDeadline: KnockoutObservable<number> = ko.observable(null);
    carryOverNumberOfPublicHdIsNegative: KnockoutObservable<number> = ko.observable(0);
    publicHolidayPeriod: KnockoutObservable<number> = ko.observable(null);

    companyManageClassification: KnockoutObservableArray<any> = ko.observableArray([]);
    lstCarryOverDeadline: KnockoutObservableArray<any> = ko.observableArray([]);
    lstManagementPeriod: KnockoutObservableArray<any> = ko.observableArray([]); 

    created() {
      const vm = this;
      vm.carryOverNumberOfPublicHdIsNegative.subscribe((newValue: number) => {
        if (newValue == 1) {
          vm.carryOverNumberOfPublicHdIsNegative(BoolValue.TRUE);
        } else {
          vm.carryOverNumberOfPublicHdIsNegative(BoolValue.FALSE);
        }
      });
    }

    mounted() {
      const vm = this;
      vm.$blockui("grayout");
      vm.getAllData().always(() => vm.$blockui("clear"));
     
      //Init enum
      const enums = (__viewContext as any).enums;
      vm.companyManageClassification(_.reverse(enums.ManagementDistinction));
      vm.lstCarryOverDeadline(enums.PublicHolidayCarryOverDeadline);
      vm.lstManagementPeriod(enums.PublicHolidayPeriod);
       $("#managePubHDDiv").focus();
    }

    private getAllData(): JQueryPromise<any> {
      const vm = this;
      return vm.$ajax(API.findAll).then(data => {
        if (!_.isUndefined(data) && !_.isNull(data) && !_.isEmpty(data)) {
          vm.managePublicHoliday(data.managePublicHoliday);
          vm.carryOverNumberOfPublicHdIsNegative(data.carryOverNumberOfPublicHdIsNegative);
          vm.publicHolidayPeriod(data.publicHolidayPeriod);
          vm.publicHdCarryOverDeadline(data.publicHdCarryOverDeadline);
        } else {
          vm.managePublicHoliday(ManagePubHD.MANAGE);
        }
      });
    }

    public save() {
      const vm = this,
      data: any = {};
      data.managePublicHoliday = vm.managePublicHoliday();
      data.publicHdCarryOverDeadline = vm.publicHdCarryOverDeadline();
      data.carryOverNumberOfPublicHdIsNegative = vm.carryOverNumberOfPublicHdIsNegative();
      data.publicHolidayPeriod = vm.publicHolidayPeriod();

      if (!nts.uk.ui.errors.hasError()) {
        vm.$blockui("grayout");
        vm.$ajax(API.save, data)
        .then(() => {
          vm.$dialog.info({ messageId: "Msg_15" });
          vm.getAllData();
        })
        .fail(err => vm.$dialog.error({ messageId: err.messageId }))
        .always(() => vm.$blockui("clear"));
      }
    }

    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }
  }

  export enum BoolValue {
    TRUE = 1,
    FALSE = 0
  }

  export enum ManagePubHD {
    MANAGE = 1,
    NOT_MANAGE = 0,
  }
}