/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.b {
  import getText = nts.uk.resource.getText;

  // URL API backend
  const API = {
  }

  @bean()
  export class ScreenModel extends ko.ViewModel {

    mounted() {
      let cssFile = document.createElement("link");
      cssFile.rel = "stylesheet";
      cssFile.type = "text/css";
      cssFile.href = 'ccg034.b.style.css';
      const $head = $("#B1_1").contents().find("head");
      $head.append(cssFile);
    }

    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }
  }
}