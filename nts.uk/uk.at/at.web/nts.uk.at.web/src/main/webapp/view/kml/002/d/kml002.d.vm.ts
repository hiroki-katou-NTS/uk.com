/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.d {

  @bean()
  class ViewModel extends ko.ViewModel {

    d31totalUsage: KnockoutObservable<number> = ko.observable(1);
    d31TimeReference: KnockoutObservable<number> = ko.observable(1);
    d31LaborCosts: KnockoutObservable<number> = ko.observable(1);
    d31Budget: KnockoutObservable<number> = ko.observable(1);

    d41WorkingHours: KnockoutObservable<number> = ko.observable(1);
    d41TimeReference: KnockoutObservable<number> = ko.observable(1);
    d41LaborCosts: KnockoutObservable<number> = ko.observable(1);

    d51Overtime: KnockoutObservable<number> = ko.observable(1);
    d51TimeReference: KnockoutObservable<number> = ko.observable(1);
    d51LaborCosts: KnockoutObservable<number> = ko.observable(1);

    switchOptions: KnockoutObservableArray<any> = ko.observableArray([]);
    laborCostTimeDetails: KnockoutObservableArray<any> = ko.observableArray([]);

    constructor(params: any) {
      super();
      const vm = this;
      
      vm.switchOptions =  ko.observableArray([
        { code: 1, name: vm.$i18n('KML002_20') },
        { code: 0, name: vm.$i18n('KML002_21') },
      ]);
      
      vm.$window.storage('LABOR_COST_TIME_DETAILS').then((data) => {        
        if( !_.isNil(data)) {
          vm.d31totalUsage(data.d31totalUsage);
          vm.d31TimeReference(data.d31TimeReference);
          vm.d31LaborCosts(data.d31LaborCosts);
          vm.d31Budget(data.d31Budget);
          vm.d41WorkingHours(data.d41WorkingHours);
          vm.d41TimeReference(data.d41TimeReference);
          vm.d41LaborCosts(data.d41LaborCosts);
          vm.d51Overtime(data.d51Overtime);
          vm.d51TimeReference(data.d51TimeReference);
          vm.d51LaborCosts(data.d51LaborCosts);          
        }
      });

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

    proceed() {
      const vm = this;
      
      let params = {
        d31totalUsage: vm.d31totalUsage(),
        d31TimeReference: vm.d31TimeReference(),
        d31LaborCosts: vm.d31LaborCosts(),
        d31Budget: vm.d31Budget(),
        d41WorkingHours: vm.d41WorkingHours(),
        d41TimeReference: vm.d41TimeReference(),
        d41LaborCosts: vm.d41LaborCosts(),
        d51Overtime: vm.d51Overtime(),
        d51TimeReference: vm.d51TimeReference(),
        d51LaborCosts: vm.d51LaborCosts(),
      };

      vm.$window.storage('LABOR_COST_TIME_DETAILS', params).then(() => {
        vm.$dialog.error({ messageId: 'Msg_15' }).then(() => { 
          vm.$window.close();
        });
      })      
    }
  }
}