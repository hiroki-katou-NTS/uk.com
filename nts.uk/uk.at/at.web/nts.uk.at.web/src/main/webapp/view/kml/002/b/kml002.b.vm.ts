/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.b {

  @bean()
  class ViewModel extends ko.ViewModel {

    laborCostTime: KnockoutObservable<number> = ko.observable(LaborCostTimeUsage.Cost);
    externalBudgetResults: KnockoutObservable<number> = ko.observable(LaborCostTimeUsage.Cost);
    countingNumberTimes: KnockoutObservable<number> = ko.observable(LaborCostTimeUsage.Cost);
    numberPassengersWorkingHours: KnockoutObservable<number> = ko.observable(LaborCostTimeUsage.Cost);
    timeZoneNumberPeople: KnockoutObservable<number> = ko.observable(LaborCostTimeUsage.Cost);
    numberOfEmployees: KnockoutObservable<number> = ko.observable(LaborCostTimeUsage.Cost);
    numberOfPeople: KnockoutObservable<number> = ko.observable(LaborCostTimeUsage.Cost);
    numberOfPositions: KnockoutObservable<number> = ko.observable(LaborCostTimeUsage.Cost);

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

    openDialogKDL024() {
      const vm = this;

      vm.$window.modal('/view/kdl/024/a/index.xhtml').then(() => {
      });
    }

    openDialogScreenD() {
      const vm = this;

      vm.$window.modal('/view/kml/002/d/index.xhtml').then(() => {
      });
    }

    openDialogScreenG() {
      const vm = this;

      vm.$window.modal('/view/kml/002/g/index.xhtml').then(() => {
      });
    }

    openDialogScreenE() {
      const vm = this;

      vm.$window.modal('/view/kml/002/e/index.xhtml').then(() => {
      });
    }
  }

  export enum LaborCostTimeUsage {
    Cost = 0,
    Time = 1
  }
}