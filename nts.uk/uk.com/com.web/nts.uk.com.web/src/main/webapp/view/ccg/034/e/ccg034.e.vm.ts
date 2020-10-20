/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.e {

  // URL API backend
  const API = {
    // ...
  }

  @bean()
  export class ScreenModel extends ko.ViewModel {
    labelContentValue: KnockoutObservable<string> = ko.observable('');
    fontSizeValue: KnockoutObservable<number> = ko.observable(11);
    isBoldValue: KnockoutObservable<boolean> = ko.observable(false);

    mounted() {
      const vm = this;

    }


    /**
     * Close dialog
     */
    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }

  }

}