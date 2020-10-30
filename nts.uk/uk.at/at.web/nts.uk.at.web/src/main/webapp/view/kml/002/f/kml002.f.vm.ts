/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.f {

  @bean()
  class ViewModel extends ko.ViewModel {

    startTime: KnockoutObservable<number> = ko.observable(0); //13*60 -> 13pm
    endTime: KnockoutObservable<number> = ko.observable(0); //16*60 -> 6pm

    option: any = new nts.uk.ui.option.TimeEditorOption({});

    constructor(params: any) {
      super();
      const vm = this;
    }

    created(params: any) {
      const vm = this;
      _.extend(window, { vm });
    }

    mounted() {
      const vm = this;

      $('#startTime').focus();
    }

    proceed() {
      const vm = this;      
      //「開始時刻」＞＝「終了時刻」の場合。
      if (vm.startTime() >= vm.endTime()) {
        vm.$dialog.error({ messageId: 'Msg_307' }).then(() => {
          $('#startTime').focus();
        });
        return;
      } else {
        let params = {
          startTime: vm.startTime(),
          endTime: vm.endTime()
        };
        vm.$window.storage('REGISTER_TIME_ZONE', params);
        vm.$window.close();
      }
    }

    closeDialog() {
      const vm = this;
      vm.$window.storage('REGISTER_TIME_ZONE', null);
      vm.$window.close();
    }

  }
}