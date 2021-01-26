/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm007.c {
  @bean()
  class ViewModel extends ko.ViewModel {

    startDate: KnockoutObservable<string> = ko.observable('2021/01/20');
    selectedId: KnockoutObservable<number> = ko.observable(0);
    isEnable: KnockoutObservable<boolean> = ko.observable(true);
    itemList : KnockoutObservableArray<any> = ko.observableArray([]);

    constructor(params: any) {
      super();
      const vm = this;

      vm.itemList([
        { id: 0, name: vm.$i18n('KSM007_45')},
        { id: 1, name: vm.$i18n('KSM007_46')}
      ]);
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }

    closeDialog() {
      const vm = this;
      vm.$window.close({});
    }

    saveData() {

    }
  }
}