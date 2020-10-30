/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.c {

  @bean()
  class ViewModel extends ko.ViewModel {

    estimatedMonthlySalary: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    estimatedAnnualSalary: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    comparisonStandardWorkingHours: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    workingTime: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    nightShiftTime: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    weeklyHolidayDays: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    attendanceHolidayDays: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    count1: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    count1Details: KnockoutObservable<any> = ko.observable({});//回数集計		

    count2: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    count2Details: KnockoutObservable<any> = ko.observable({});//回数集計		

    count3: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    count3Details: KnockoutObservable<any> = ko.observable({});//回数集計		

    switchOptions: KnockoutObservableArray<any> = ko.observableArray([]);
    constructor(params: any) {
      super();
      const vm = this;

      vm.switchOptions = ko.observableArray([
        { code: UsageClassification.Use, name: vm.$i18n('KML002_20') },
        { code: UsageClassification.NotUse, name: vm.$i18n('KML002_21') }
      ]);

    }

    created(params: any) {
      const vm = this;
      _.extend(window, { vm });
    }

    mounted() {
      const vm = this;

      $('#C322').focus();
    }

    openDialogScreenG(count: number) {
      const vm = this;

      let params = vm.count1Details();

      switch (count) {
        case 1:
          params = vm.count1Details();
          break;
        case 2:
          params = vm.count2Details();
          break;
        case 3:
          params = vm.count3Details();
          break;
      }

      vm.$window.storage('KWL002_SCREEN_G_INPUT', params).then(() => {
        vm.$window.modal('/view/kml/002/g/index.xhtml').then(() => {
          vm.$window.storage('KWL002_SCREEN_G_OUTPUT').then((data) => {
            if (!_.isNil(data)) {
              switch (count) {
                case 1:
                  vm.count1Details(data);
                  break;
                case 2:
                  vm.count2Details(data);
                  break;
                case 3:
                  vm.count3Details(data);
                  break;
              }
            }
          });
        });
      });
    }
  }

  export enum UsageClassification {
    NotUse = 0,
    Use = 1
  }
}