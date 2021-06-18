/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr005.c {

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
    isDeleted: KnockoutObservable<boolean> = ko.observable(false);

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
      const setShare: any = vm.isDeleted() ? { code: null, name: null } : null;
      vm.$window.close(setShare);
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
      vm.isDeleted(false);
      vm.$ajax(PATHS.cloneSettingClassification, vm.params())
        .done((response) => {
          vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
            vm.$blockui('hide');
            vm.$window.close({ code: vm.newCode(), name: vm.newName() });
          });
        })
        .fail((error) => {
          let ctrlId = (error.messageId === 'Msg_1927') ? '#KWR005_C23' : '#closeDialog';
          vm.isDeleted(error.messageId === 'Msg_1928');
          vm.$dialog.error({ messageId: error.messageId }).then(() => {
            $(ctrlId).focus();
            vm.$blockui('hide');
          });
        });
    }
  }
}