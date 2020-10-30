/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.d {

  @bean()
  class ViewModel extends ko.ViewModel {

    d31totalUsage: KnockoutObservable<number> = ko.observable(0);
    d31TimeReference: KnockoutObservable<number> = ko.observable(0);
    d31LaborCosts: KnockoutObservable<number> = ko.observable(0);
    d31Budget: KnockoutObservable<number> = ko.observable(0);
    d31IsEnable: KnockoutObservable<boolean> = ko.observable(true);

    d41WorkingHours: KnockoutObservable<number> = ko.observable(0);
    d41TimeReference: KnockoutObservable<number> = ko.observable(0);
    d41LaborCosts: KnockoutObservable<number> = ko.observable(0);
    d41IsEnable: KnockoutObservable<boolean> = ko.observable(true);

    d51Overtime: KnockoutObservable<number> = ko.observable(0);
    d51TimeReference: KnockoutObservable<number> = ko.observable(0);
    d51LaborCosts: KnockoutObservable<number> = ko.observable(0);
    d51IsEnable: KnockoutObservable<boolean> = ko.observable(true);

    switchOptions: KnockoutObservableArray<any> = ko.observableArray([]);
    constructor(params: any) {
      super();
      const vm = this;
      
      vm.switchOptions =  ko.observableArray([
        { code: 0, name: vm.$i18n('KML002_20') },
        { code: 1, name: vm.$i18n('KML002_21') },
      ]);

      vm.d31totalUsage.subscribe((value) => {{
        vm.d31IsEnable(value === 0);
      }})

      vm.d41WorkingHours.subscribe((value) => {{
        vm.d41IsEnable(value === 0);
      }})

      vm.d51Overtime.subscribe((value) => {{
        vm.d51IsEnable(value === 0);
      }})
      
    }

    created(params: any) {
      const vm = this;
      _.extend(window, { vm });
    }

    mounted() {
      const vm = this;

      $('#D32').focus();
    }

    closeDialog() {
      const vm = this;
      vm.$window.close();
    }
  }
}