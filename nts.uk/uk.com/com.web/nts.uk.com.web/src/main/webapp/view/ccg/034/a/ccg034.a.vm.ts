/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.a {

  // URL API backend
  const API = {
    // ...
  }

  @bean()
  export class ScreenModel extends ko.ViewModel {
    toppagePartCode: KnockoutObservable<string> = ko.observable(null);
    toppagePartName: KnockoutObservable<string> = ko.observable(null);

    mounted() {
      // Code xử lý lúc khởi động màn hình
    }

    public test() {

    }

    public openDialogB() {

    }

    public openDialogD() {
      const vm = this;
      const params = {};
      vm.$window.modal('/view/ccg/034/d/index.xhtml', params);
    }

  }

}