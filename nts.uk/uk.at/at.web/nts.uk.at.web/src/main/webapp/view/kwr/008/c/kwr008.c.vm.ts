/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.kwr008.c {
  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;

  const API = {
      executeCopy: '',
  }

  @bean()
  export class KWR008CViewModel extends ko.ViewModel {
    selectedCode: KnockoutObservable<string>;
    selectedName: KnockoutObservable<string>;
    isEnable: KnockoutObservable<boolean>;
    isEditable: KnockoutObservable<boolean>;
    duplicateCode: KnockoutObservable<string>;
    duplicateName: KnockoutObservable<string>;

    created() {
      const vm = this;
      vm.selectedCode = ko.observable('');
      vm.isEnable = ko.observable(true);
      vm.duplicateCode = ko.observable('');
      vm.duplicateName = ko.observable('');
      vm.isEditable = ko.observable(true);

      // get param from B screen 
      const kwr008b = getShared("KWR008CParam");
      if (kwr008b) {
        vm.selectedCode = ko.observable(kwr008b.selectCode);
        vm.selectedName  = ko.observable(kwr008b.selectName);
      }
    }

    executeCopy() {
      /// TODO
    }

    closeDialog() {
      let vm = this;
      vm.$window.close();
    }
  }
}