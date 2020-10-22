/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.c {
  
  @bean()
  class ViewModel extends ko.ViewModel {

    estimatedMonthlySalary: KnockoutObservable<number> = ko.observable(LaborCostTimeUsage.Cost);
    estimatedAnnualSalary: KnockoutObservable<number> = ko.observable(LaborCostTimeUsage.Cost);
    comparisonStandardWorkingHours: KnockoutObservable<number> = ko.observable(LaborCostTimeUsage.Cost);
    workingTime: KnockoutObservable<number> = ko.observable(LaborCostTimeUsage.Cost);
    nightShiftTime: KnockoutObservable<number> = ko.observable(LaborCostTimeUsage.Cost);
    weeklyHolidayDays: KnockoutObservable<number> = ko.observable(LaborCostTimeUsage.Cost);
    attendanceHolidayDays: KnockoutObservable<number> = ko.observable(LaborCostTimeUsage.Cost);
    count1: KnockoutObservable<number> = ko.observable(LaborCostTimeUsage.Cost);
    count2: KnockoutObservable<number> = ko.observable(LaborCostTimeUsage.Cost);
    count3: KnockoutObservable<number> = ko.observable(LaborCostTimeUsage.Cost);

    constructor(params: any) {
      super();
      const vm = this;
    }

    created(params: any) {
      const vm = this;
      _.extend(window, { vm });
    }

    mounted(params: any) {
      const vm = this;
    }  
    
    openDialogScreenG() {
      const vm = this;

      vm.$window.modal('/view/kml/002/g/index.xhtml').then(() => {
      });
    }
  }

  export enum LaborCostTimeUsage {
    Cost = 0,
    Time = 1
  }
}