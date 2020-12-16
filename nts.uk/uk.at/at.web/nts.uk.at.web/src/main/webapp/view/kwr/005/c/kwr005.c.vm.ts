/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr005.c {
  //import common = nts.uk.at.view.kwr003.common; 
  const KWR005_OUTPUT = 'KWR005_OUTPUT';
  const KWR005_B13 = 'KWR005_B_DATA';
  const KWR005_C_INPUT = 'KWR005_C_DATA';
  const KWR005_C_OUTPUT = 'KWR005_C_RETURN';

  const PATHS = {
    cloneSettingClassification: 'at/function/kwr/005/c/duplicate'
  };

  @bean()
  class ViewModel extends ko.ViewModel {

    oldCode: KnockoutObservable<string> = ko.observable(null);
    oldName: KnockoutObservable<string> = ko.observable(null);

    newCode: KnockoutObservable<string> = ko.observable(null);
    newName: KnockoutObservable<string> = ko.observable(null);

    params: KnockoutObservable<any> = ko.observable({});

    constructor(params: any) {
      super();
      const vm = this;

      vm.params({
        settingCategory: 0, //設定区分
        dupSrcId: null, //複製元の設定ID
        dupCode: null,//複製先_コード
        dupName: null//複製先_名称
      });

      if (!_.isNil(params)) {
        vm.oldCode(params.code);
        vm.oldName(params.name);
        vm.params().dupSrcId = params.settingId;
        vm.params().dupCode = params.code;
        vm.params().dupName = params.name;
        vm.params().settingCategory = params.settingCategory;
      }
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;

      $('#KWR005_C23').focus();
    }

    proceed() {
      const vm = this;
      vm.cloneSettingClassification();
    }

    cancel() {
      const vm = this;   
      vm.$window.close(null);
    }

    /**
     * Clones setting classification
     */
    cloneSettingClassification() {
      const vm = this;

      $('.clone-item').trigger('validate');
      if (nts.uk.ui.errors.hasError()) return;

      vm.$blockui('show');
      vm.params().dupCode = vm.newCode();
      vm.params().dupName = vm.newName();

      vm.$ajax(PATHS.cloneSettingClassification, vm.params())
        .done((response) => {
          vm.$blockui('hide');
          vm.$window.close({ code: vm.newCode(), name: vm.newName() });
        })
        .fail((error) => {
          //データが先に削除された - 1903    
          //コードの重複 - Msg_1753
          vm.$dialog.error({ messageId: error.messageId }).then(() => {
            if (error.messageId == 1928)
              $('#closeDialog').focus();
            else
              $('#KWR005_C23').focus();

            vm.$blockui('hide');
          });
        })
        .always(() => vm.$blockui('hide'));
    }
  }
}