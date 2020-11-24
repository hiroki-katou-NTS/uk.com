/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ktg031.a {

  const API = {
    findBySystem: "sys/portal/pginfomation/findBySystem",
    updateLogSetting: "sys/portal/logsettings/update",
    // ...
  }

  @component({
    name: 'ktg031-component',
    template: '/nts.uk.com.web/view/ktg/031/a/index.xhtml'
  })
  export class Ktg031ComponentViewModel extends ko.ViewModel {

    mounted() {
      // Code xử lý lúc khởi động màn hình
    }

    // ...

  }
}