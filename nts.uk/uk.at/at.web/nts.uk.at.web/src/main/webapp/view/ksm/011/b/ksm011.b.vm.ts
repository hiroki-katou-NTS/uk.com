/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm011.b {
  @bean()
  class ViewModel extends ko.ViewModel {

    viewModelTab1: any;
    viewModelTab2: any;

    constructor(params: any) {
      super();
      const vm = this;

      vm.viewModelTab1 = new ksm011.b.tabs.tab1.ViewModel();
      vm.viewModelTab2 = new ksm011.b.tabs.tab2.ViewModel();
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }

    tabPanel1Click() {
      const vm = this;
      vm.viewModelTab1.initialLoadPage(true);
      vm.viewModelTab2.active(false);
    }

    tabPanel2Click() {
      const vm = this;
      vm.viewModelTab2.initialLoadPage(true);
    }
  }
}