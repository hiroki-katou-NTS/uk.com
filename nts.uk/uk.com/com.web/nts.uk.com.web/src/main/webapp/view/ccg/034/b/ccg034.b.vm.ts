/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.b {
  import getText = nts.uk.resource.getText;

  // URL API backend
  const API = {
    extract: "sys/portal/flowmenu/extract/{0}"
  }


  @bean()
  export class ScreenModel extends ko.ViewModel {

    fileId: KnockoutObservable<string> = ko.observable('');
    htmlSrc: KnockoutObservable<string> = ko.observable('');

    created(params: any) {
      const vm = this;
      vm.fileId(params);
    }

    mounted() {
      const vm = this;
      vm.extract();
    }

    private extract() {
      const vm = this;
      vm.$blockui("grayout");
      const path = nts.uk.text.format(API.extract, vm.fileId());
      vm.$ajax(path).done((res: any) => {
        $("#B1_1").attr("srcdoc", res.htmlContent);
      }).always(() => vm.$blockui("clear"));
    }

    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }
  }
}