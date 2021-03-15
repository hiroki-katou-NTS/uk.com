/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm007.c {
  const FORMAT_DAY = 'YYYY/MM/DD';
  const PATH = {
    saveData: 'bs/schedule/employeeinfo/workplacegroup/updatehospitalbusinessofficehist',
    deleteData: 'bs/schedule/employeeinfo/workplacegroup/deletehospitalbusinessofficehist',
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
      $('#startDate').focus();
    }

    closeDialog() {
      const vm = this;
      vm.$window.close({});
    }

    saveData() {
      const vm = this;
      let params = {};
     
      if( moment(vm.startFromDate()).format(FORMAT_DAY) >= '9999/12/31') {
        vm.$dialog.error({ messageId: 'Msg_917'}).then(() => {
          $('#startDate').focus();
        });
        return;
      }

      let url = vm.selectedId() === 0 ? PATH.deleteData : PATH.saveData;
      
      if( vm.selectedId() === 0 ) {
        params = { 
          workplaceGroupId: vm.inputScreenD().wpGroupId,
          historyId: vm.inputScreenD().historyId
        };
      } else {
        params = { 
          workplaceGroupId: vm.inputScreenD().wpGroupId,
          historyId: vm.inputScreenD().historyId,
          startDate: moment(vm.startFromDate()).format('YYYY/MM/DD')
        };
      }

      
      vm.$blockui('grayout');
      vm.$ajax('com', url, params).done(() => {
        //vm.$dialog.info({ messageId: 'Msg_15'}).then(() => {
          vm.$window.close({ isSave: true });
          vm.$blockui('hide');
        //});        
      }).fail((error) => {
        vm.$dialog.error({ messageId: error.messageId }).then(() => {
          vm.$blockui('hide');
        });        
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
        let beginStartDate = moment(params.startDate).format(FORMAT_DAY);//.add(+1, 'days')
        vm.startFromDate(beginStartDate);
        let limitDate = moment(vm.inputScreenD().startDateLimit).add(+1, 'days').format(FORMAT_DAY);
        vm.beginStartDate(limitDate);
      }

      $('#startDate').focus();

    }
  }
}