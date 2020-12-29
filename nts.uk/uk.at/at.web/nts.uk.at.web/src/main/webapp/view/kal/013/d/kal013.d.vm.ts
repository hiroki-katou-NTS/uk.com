/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kal013.d {

  import common = nts.uk.at.view.kal013.common;
  import errors = nts.uk.ui.errors;

  @bean()
  class ViewModel extends ko.ViewModel {

    selectedCategory: KnockoutObservable<any> = ko.observable(null);
    categoryList: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedCategoryCode: KnockoutObservable<number> = ko.observable(0);

    constructor(params: number) {
      super();
      const vm = this;

      vm.categoryList(common.workplaceCategory());
      vm.selectedCategoryCode(params);
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
      $('#KAL013_10').focus();
    }

    proceed() {
      const vm = this;
        $('.nts-input').filter(":enabled").trigger("validate");
        if (errors.hasError() === true) {
            return;
        }
        let shareParam: number = vm.selectedCategoryCode();
        vm.$window.close({
            shareParam
        });
    }

    cancel() {
      const vm = this;
        vm.$window.close({
        });
    }
  }
}