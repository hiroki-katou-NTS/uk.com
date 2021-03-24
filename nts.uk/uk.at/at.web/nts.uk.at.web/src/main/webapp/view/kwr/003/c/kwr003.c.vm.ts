/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr003.c {

  const PATHS = {
    cloneSettingClassification: 'at/function/kwr/003/c/duplicate'
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
    }

    created(params: any) {
      const vm = this;

      vm.params({
        settingCategory: 0, //設定区分
        settingId: null, //複製元の設定ID
        settingCode: null,//複製先_コード
        settingName: null//複製先_名称
      });

      if (!_.isNil(params)) {
        vm.oldCode(params.code);
        vm.oldName(params.name);
        vm.params().settingId = params.settingId;
        vm.params().settingCode = params.code;
        vm.params().settingName = params.name;
        vm.params().settingCategory = params.settingCategory;
      }
    }

    mounted() {
      const vm = this;

      $('#KWR003_C23').focus();
    }

    cloneAttendance() {
      const vm = this;
      $(".new-code-name").trigger("validate");
      if (nts.uk.ui.errors.hasError()) return;
      vm.cloneSettingClassification();
    }

    cancel() {
      const vm = this;
      let setShare = vm.isDeleted() ? { code: null, name: null } : null;
      vm.$window.close(setShare);
    }

    /**
     * Clones setting classification
     */
    cloneSettingClassification() {
      const vm = this;

      vm.$blockui('show');
      vm.params().settingCode = vm.newCode();
      vm.params().settingName = vm.newName();

      vm.$ajax(PATHS.cloneSettingClassification, vm.params())
        .done((response) => {
          vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
            let setShare = {
              code: vm.newCode(),
              name: vm.newName()
            };
            vm.$window.close(setShare);
            vm.$blockui('hide');
          });
        })
        .fail((error) => {
          //データが先に削除された - 1903, コードの重複 - Msg_1753
          let ctrlId = (error.messageId === 'Msg_1753') ? '#KWR003_C23' : '#closeDialog';
          vm.isDeleted(error.messageId === 'Msg_1903');
          vm.$dialog.error({ messageId: error.messageId }).then(() => {            
            $(ctrlId).focus();
            vm.$blockui('hide');
          });               
        });
    }
  }
}