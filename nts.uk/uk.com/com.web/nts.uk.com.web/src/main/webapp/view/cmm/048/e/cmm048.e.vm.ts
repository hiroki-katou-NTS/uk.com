/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cmm048.e {

  @bean()
  export class ViewModel extends ko.ViewModel {
    url: KnockoutObservable<string> = ko.observable("");
    showOff: KnockoutObservable<boolean> = ko.observable(false);

    mounted() {
      const vm = this;
      vm.show();
      if (vm.url()) {
        vm.showOff(!vm.showOff())
      }
      $("#upload").ready(() => {
        $(".comfirm-checkbox").remove();
        $(`<button class='upload-webcam'> ${vm.$i18n('CMM048_107')} </button>`).insertAfter(".upload-btn");
      });
    }
    
    show() {
      const vm = this;
      if (!nts.uk.util.isNullOrEmpty(vm.url().trim())) {
        $("#upload").ntsImageEditor("showByUrl", vm.url());
      }
    }

    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }
  }
}