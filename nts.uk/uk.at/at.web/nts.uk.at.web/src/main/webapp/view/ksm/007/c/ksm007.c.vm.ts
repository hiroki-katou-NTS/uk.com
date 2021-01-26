/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm007.c {
  @bean()
  class ViewModel extends ko.ViewModel {

    startDate: KnockoutObservable<string> = ko.observable('2021/01/20');
    
    constructor(params: any) {
      super();
      const vm = this;
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
      $('#startDate').focus();
    }

    closeDialog() {
      const vm = this;
      vm.$window.close({});
    }

    saveData() {

    }
  }
}