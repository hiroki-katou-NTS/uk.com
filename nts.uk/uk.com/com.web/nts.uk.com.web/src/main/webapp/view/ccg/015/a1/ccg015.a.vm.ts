/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg015.a1.screenModel {

  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;

  @bean()
  export class ViewModel extends ko.ViewModel {

    openScreenCCG034() {

    }

    openDialogCCG030() {
      const vm = this;
      vm.$window.modal('/view/ccg/030/a/index.xhtml');
    }

    openScreenCCG018() {

    }

    openScreenCCG015() {

    }

  }
}