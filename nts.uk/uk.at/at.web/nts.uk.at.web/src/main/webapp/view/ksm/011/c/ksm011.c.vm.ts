/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm011.c {
  
  const PATH = {
    getSetting: ''
  }

  @bean()
  class ViewModel extends ko.ViewModel {

    switchItems: KnockoutObservableArray<any>;
    switchItems1: KnockoutObservableArray<any>;
    regularWork: KnockoutObservable<number> = ko.observable(1);
    fluidWork: KnockoutObservable<number> = ko.observable(0);
    flexTime: KnockoutObservable<number> = ko.observable(0);
    workTypeControl: KnockoutObservable<number> = ko.observable(1);
    achievementDisplay: KnockoutObservable<number> = ko.observable(1);
    workTypeList: KnockoutObservableArray<string> = ko.observableArray([]);
    workTypeListText: KnockoutObservable<string> = ko.observable('勤務種類リスト + 勤務種類リスト + 勤務種類リスト');

    selectedCode: KnockoutObservable<string>;
    selectableCode: KnockoutObservable<string>;

    constructor(params: any) {
      super();
      const vm = this;

      vm.switchItems = ko.observableArray([
        { code: 1, name: vm.$i18n('KSM011_38') },
        { code: 0, name: vm.$i18n('KSM011_39') }
      ]);

      vm.switchItems1 = ko.observableArray([
        { code: 1, name: vm.$i18n('KSM011_21') },
        { code: 0, name: vm.$i18n('KSM011_22') }
      ]);

      //vm.getSetting();
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }

    list(str: string):Array<string>{
      return _.split(str, ',');
    }

    openDialogKDl002() {
      const vm = this;
      let lstselectedCode = vm.list(vm.selectedCode());
      let lstselectableCode = vm.list(vm.selectableCode());
      nts.uk.ui.windows.setShared('KDL002_Multiple', true, true);
      //all possible items
      nts.uk.ui.windows.setShared('KDL002_AllItemObj', lstselectableCode, true);
      nts.uk.ui.windows.setShared('kdl002isSelection', true);
      //selected items
      nts.uk.ui.windows.setShared('KDL002_SelectedItemId', lstselectedCode, true);
      nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(function (): any {
        var lst = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
        vm.selectedCode();
        let strLstCode: Array<string> = [],
            strLstName: Array<string> = [];
        
            _.each(lst, (item, index: number) => {
          strLstCode.push(item.code);          
          strLstName.push(item.name);          
        });

        vm.workTypeListText(_.join(strLstName, ' + '));
        //vm.workTypeList(strLstCode);
      });
    }

    getSetting() {
      const vm = this;

      vm.$ajax(PATH.getSetting).done((data) => {
        if( data ) {
          vm.regularWork(data.regularWork);
          vm.flexTime(data.flexTime);
          vm.fluidWork(data.fluidWork);
          vm.workTypeControl(data.workTypeControl);
          vm.achievementDisplay(data.achievementDisplay);
          //vm.selectedCode(data.selectableCode);
          //vm.selectableCode(data.selectableCode);
        }
      });
    }

    saveData() {
      const vm = this;

      if( vm.workTypeList().length <= 0 && vm.workTypeControl() === 1) {
        vm.$dialog.error({ messageId: 'Msg_1690', messageParams: [vm.$i18n('KSM011_47')]}).then(() => {
          $('#KSM011_C3_6').focus();
        });
        return;
      }

      let params = {
        regularWork: vm.regularWork(),
        flexTime: vm.flexTime(),
        fluidWork: vm.fluidWork(),
        workTypeControl: vm.workTypeControl(),
        achievementDisplay: vm.achievementDisplay()
        //vm.selectedCode(data.selectableCode);
        //vm.selectableCode(data.selectableCode);
      };

      /* vm.$ajax(PATH.saveData, params).done((data) => {
        if( data ) {
          vm.$dialog.info({ messageId: 'Msg_15'}).then(() => {
          return;
          });
        }
      }).fail(error => {
        vm.$dialog.info({ messageId: error.messageId}).then(() => {
          return;
          });
      });  */

    }   
  }
}