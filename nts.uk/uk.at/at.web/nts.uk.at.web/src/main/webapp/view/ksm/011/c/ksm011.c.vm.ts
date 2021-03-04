/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm011.c {
  @bean()
  class ViewModel extends ko.ViewModel {

    switchItems: KnockoutObservableArray<any>;
    publicMethod: KnockoutObservable<number> = ko.observable(0);
    
    constructor(params: any) {
      super();
      const vm = this;

      vm.switchItems = ko.observableArray([
        { code: 1, name: vm.$i18n('KSM011_21') },
        { code: 0, name: vm.$i18n('KSM011_22') }
      ]);
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }
  }
}