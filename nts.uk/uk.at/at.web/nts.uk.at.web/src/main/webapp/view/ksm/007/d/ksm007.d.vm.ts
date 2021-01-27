/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm007.c {
  const FORMAT_DAY = 'YYYY/MM/DD';
  const PATH = {
    saveData: ''
  }

  @bean()
  class ViewModel extends ko.ViewModel {

    startDate: KnockoutObservable<string> = ko.observable('2021/01/20');
    selectedId: KnockoutObservable<number> = ko.observable(0);
    isEnable: KnockoutObservable<boolean> = ko.observable(true);
    itemList : KnockoutObservableArray<any> = ko.observableArray([]);

    startFromDate: KnockoutObservable<string> = ko.observable(moment().add(+1, 'days').format(FORMAT_DAY));
    beginStartDate: KnockoutObservable<string> = ko.observable(moment().add(+1, 'days').format(FORMAT_DAY));
    inputScreenD: KnockoutObservable<any> = ko.observable(null);

    constructor(params: any) {
      super();
      const vm = this;
      vm.getDataFromScreenC(params);
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }

    closeDialog() {
      const vm = this;
      vm.$window.close({});
    }

    saveData() {
      const vm = this;
      let params = { ...vm.inputScreenD(), startDate: vm.startFromDate() };
     
      if( moment(vm.startFromDate()).format(FORMAT_DAY) >= '9999/12/31') {
        vm.$dialog.error({ messageId: 'Msg_917'}).then(() => {
          $('#startDate').focus();
        });
        return;
      }

      vm.$blockui('grayout');
      vm.$ajax(PATH.saveData, params).done(() => {
        vm.$window.close({ isSave: true });
        vm.$blockui('hide');
      }).fail(() => {
        vm.$window.close({ isSave: false });
        vm.$blockui('hide');
      });

    }

    getDataFromScreenC(params: any) {
      const vm = this;

      vm.inputScreenD(params);
      
      vm.itemList([
        { id: 0, name: vm.$i18n('KSM007_45'), enable: vm.inputScreenD().isDelete },
        { id: 1, name: vm.$i18n('KSM007_46')}
      ]);
      
      //B3_2「履歴一覧」に一つ履歴しかない場合は、履歴削除可能＝false
      vm.selectedId( vm.inputScreenD().isDelete ? 0 : 1 );

      if (params) {
        let beginStartDate = moment(params.startDate).add(+1, 'days').format(FORMAT_DAY);
        vm.startFromDate(beginStartDate);
        vm.beginStartDate(beginStartDate);
      }
    }
  }
}