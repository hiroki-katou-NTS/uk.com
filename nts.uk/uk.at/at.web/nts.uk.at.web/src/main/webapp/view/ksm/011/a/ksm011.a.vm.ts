/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm011.a {
  
  @bean()
  class ViewModel extends ko.ViewModel {

    constructor(params: any) {
      super();
      const vm = this;
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
      $(".row.button button")[0].focus()
    }

    gotoScreenB() {      
      nts.uk.request.jump("/view/ksm/011/b/index.xhtml");
    }
    
    gotoScreenC() {
      nts.uk.request.jump("/view/ksm/011/c/index.xhtml");
    }

    gotoScreenD() {
      nts.uk.request.jump("/view/ksm/011/d/index.xhtml");
    }

    gotoScreenE() {
      nts.uk.request.jump("/view/ksm/011/e/index.xhtml");
    }

    goToScreenKML002B() {
        nts.uk.request.jump("/view/kml/002/b/index.xhtml")
    }

    goToScreenKML002C() {
        nts.uk.request.jump("/view/kml/002/c/index.xhtml")
    }
  }
}