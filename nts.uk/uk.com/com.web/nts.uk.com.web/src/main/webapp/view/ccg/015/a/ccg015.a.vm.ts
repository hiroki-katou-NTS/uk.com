/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg015.a {

  @bean()
  export class ScreenModel extends ko.ViewModel {
    textButton: KnockoutComputed<string> = ko.computed(() => _.replace(this.$i18n('CCG015_49'), '\n', '<br/>'));

    openScreenCCG034() {
      const vm = this;
      vm.$jump('/view/ccg/034/a/index.xhtml');
    }

    openDialogCCG030() {
      const vm = this;
      vm.$window.modal('/view/ccg/030/a/index.xhtml');
    }

    openScreenCCG018() {
      const vm = this;
      vm.$jump('/view/ccg/018/a/index.xhtml');
    }

    openScreenCCG015() {
      const vm = this;
      vm.$jump('/view/ccg/015/b/index.xhtml');
    }

  }
}