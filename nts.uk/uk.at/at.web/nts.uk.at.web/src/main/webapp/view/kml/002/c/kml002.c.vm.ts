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
    count2: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    count3: KnockoutObservable<number> = ko.observable(UsageClassification.Use);

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
    
    openDialogScreenG() {
      const vm = this;

      vm.$window.modal('/view/kml/002/g/index.xhtml').then(() => {
      });
    }
  }

  export enum UsageClassification {
    NotUse = 0,
    Use = 1
  }
}