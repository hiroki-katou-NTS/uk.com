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
      //_.extend(window, { vm });
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
      } else if ( vm.endTime() - vm.startTime() < 60) {
        vm.$dialog.error({ messageId: "Msg_1819" }).then(() => {
          $('#endTime').focus(); 
        }); 
        return;
      } else {
        let start15m = vm.startTime() - _.floor(vm.startTime() / 60) * 60;
        let end15m = vm.endTime() - _.floor(vm.endTime() / 60) * 60;
        let over24h = (vm.endTime() - end15m) - (vm.startTime() - start15m);
     
        if (start15m % 15 !== 0) {
          $('#startTime').ntsError('set', { messageId: "Msg_1845" }).focus();
          return;
        } else if (end15m % 15 !== 0) {
          $('#endTime').ntsError('set', { messageId: "Msg_1845" }).focus();
          return;
        } else if( over24h / 60 >= 24) {
          vm.$dialog.error({ messageId: "Msg_1819" }).then(() => {
            $('#endTime').focus(); //.ntsError('set', { messageId: "Msg_1819" })
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
    }

    closeDialog() {
      const vm = this;
      vm.$window.storage('REGISTER_TIME_ZONE', null);
      vm.$window.close();
    }

  }
}