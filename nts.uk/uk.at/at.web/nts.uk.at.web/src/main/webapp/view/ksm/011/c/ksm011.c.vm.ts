/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm011.c {
  
  const PATH = {
    getSetting: 'screen/at/schedule/basicsetting/init',
    registerSetting: 'screen/at/schedule/basicsetting/register'
  };

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
    workTypeListText: KnockoutObservable<string>;

    selectableWorkTypes: KnockoutObservableArray<string> = ko.observableArray([]);

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

      vm.workTypeListText = ko.computed(() => {
        return vm.selectableWorkTypes()
            .filter((i: any) => vm.workTypeList().indexOf(i.code) >= 0)
            .map((i: any) => i.name).join(" + ");
      });

      vm.getSetting();
    }

    mounted() {
      const vm = this;
    }

    openDialogKDl002() {
      const vm = this;
      const lstselectedCode = vm.workTypeList();
      const lstselectableCode = vm.selectableWorkTypes().map((i: any) => i.code);
      nts.uk.ui.windows.setShared('KDL002_Multiple', true, true);
      //all possible items
      nts.uk.ui.windows.setShared('KDL002_AllItemObj', lstselectableCode, true);
      nts.uk.ui.windows.setShared('kdl002isSelection', true);
      //selected items
      nts.uk.ui.windows.setShared('KDL002_SelectedItemId', lstselectedCode, true);
      nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(function (): any {
        var lst = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
        if (lst) {
          vm.workTypeList(lst.map((i: any) => i.code));
        }
      });
    }

    getSetting() {
      const vm = this;
      vm.$blockui('show');
      vm.$ajax(PATH.getSetting).done((data) => {
        if( data ) {
          vm.regularWork(data.changeableFix);
          vm.flexTime(data.changeableFlex);
          vm.fluidWork(data.changeableFluid);
          vm.workTypeControl(data.displayWorkTypeControl);
          vm.workTypeList(data.workTypeCodeList || []);
          vm.achievementDisplay(data.displayActual);
          vm.selectableWorkTypes(data.displayableWorkTypeList || []);
        }
      }).fail(error => {
        vm.$dialog.error(error);
      }).always(() => {
        $(".switchButton-wrapper")[0].focus();
        vm.$blockui('hide');
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
          changeableFix: vm.regularWork(),
          changeableFlex: vm.flexTime(),
          changeableFluid: vm.fluidWork(),
          displayWorkTypeControl: vm.workTypeControl(),
          displayActual: vm.achievementDisplay(),
          displayableWorkTypeCodeList: vm.workTypeList()
      };
      vm.$blockui('show');
      vm.$ajax(PATH.registerSetting, params).done((data) => {
        vm.$dialog.info({ messageId: 'Msg_15'});
      }).fail(error => {
        vm.$dialog.info(error);
      }).always(() => {
        vm.$blockui('hide');
      });

    }   
  }
}