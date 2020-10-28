/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kal013.b {
  @bean()
  class ViewModel extends ko.ViewModel {

    selectedCategory: KnockoutObservable<any> = ko.observable(null);
    categoryList: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedCategoryCode: KnockoutObservable<string> = ko.observable(null);

    constructor(params: any) {
      super();
      const vm = this;
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