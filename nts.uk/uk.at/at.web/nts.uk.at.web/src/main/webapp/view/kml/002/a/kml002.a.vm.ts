/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.a {
  
  @bean()
  class ViewModel extends ko.ViewModel {

    constructor(params: any) {
      super();
      const vm = this;
    }

    created(params: any) {
      const vm = this;
      //_.extend(window, { vm });
    }

    mounted(params: any) {
      const vm = this;
      $('#A1-3').focus();
    }

    goToScreenB() {
      nts.uk.request.jump("/view/kml/002/b/index.xhtml")
    }

    goToScreenC() {
      nts.uk.request.jump("/view/kml/002/c/index.xhtml")
    }
  }
}