/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm011.b {
  @bean()
  class ViewModel extends ko.ViewModel {
    switchItems: KnockoutObservableArray<any>;
    publishing: KnockoutObservable<number> = ko.observable(1);
    workRequest: KnockoutObservable<number> = ko.observable(1);
    daysList: KnockoutObservableArray<any>;
    workRequestInput: KnockoutObservableArray<any>;
    workRequestSelected: KnockoutObservable<number> = ko.observable(1);  
    deadlineSelected: KnockoutObservable<number> = ko.observable(1); 
    deadlineWorkSelected: KnockoutObservable<number> = ko.observable(1); 
    

    constructor(params: any) {
      super();
      const vm = this;
      vm.switchItems = ko.observableArray([
        { code: 1, name: vm.$i18n('KSM011_21') },
        { code: 0, name: vm.$i18n('KSM011_22') }
      ]);

      vm.workRequestInput = ko.observableArray([
        { code: 1, name: vm.$i18n('KSM011_21') },
        { code: 0, name: vm.$i18n('KSM011_22') }
      ]);

      vm.daysList = ko.observableArray([]);
      let days = [];
      for( let day = 0; day < 30; day++) {
        days.push( { day: day, name: (day + 1) + vm.$i18n('KSM011_105')});
      }
      days.push( { day: 30, name: vm.$i18n('KSM011_106')});
      vm.daysList(days);
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }

  }
}