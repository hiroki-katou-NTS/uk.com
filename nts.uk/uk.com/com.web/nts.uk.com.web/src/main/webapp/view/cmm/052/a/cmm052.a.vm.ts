/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.at.view.cmm052.a {

  @bean()
  export class ScreenModel extends ko.ViewModel {

    public openScreenCas003() {
      const vm = this;
      vm.$jump("/view/cas/003/a/index.xhtml");
    }

    public openDialogCdl011() {
      const vm = this;
      nts.uk.ui.windows.setShared("CDL011_INPUT", 1);
      nts.uk.ui.windows.sub.modal("com", "/view/cdl/011/a/index.xhtml");
    }
  }
}