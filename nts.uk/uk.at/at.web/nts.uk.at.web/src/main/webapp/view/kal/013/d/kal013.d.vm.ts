/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kal013.d {

  import common = nts.uk.at.view.kal013.common;

  @bean()
  class ViewModel extends ko.ViewModel {

    selectedCategory: KnockoutObservable<any> = ko.observable(null);
    categoryList: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedCategoryCode: KnockoutObservable<number> = ko.observable(0);

    constructor(params: any) {
      super();
      const vm = this;

      vm.categoryList(common.workplaceCategory());
      vm.selectedCategoryCode( common.WorkplaceCategory.MASTER_CHECK_BASIC);
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }

    proceed() {

    }

    cancel() {
      const vm = this;
      vm.$window.close();
    }
  }
}