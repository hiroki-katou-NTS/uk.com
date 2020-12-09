/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr004.c {

  const KWR004_OUTPUT = 'KWR004_OUTPUT';
  const KWR004_B13 = 'KWR004_B_DATA';
  const KWR004_C_INPUT = 'KWR004_C_DATA';
  const KWR004_C_OUTPUT = 'KWR004_C_RETURN';

  const PATH = {
    cloneSettingClassification: 'at/function/kwr004/duplicate'
  };

  @bean()
  class ViewModel extends ko.ViewModel {

    oldCode: KnockoutObservable<string> = ko.observable();
    oldName: KnockoutObservable<string> = ko.observable();

    newCode: KnockoutObservable<string> = ko.observable();
    newName: KnockoutObservable<string> = ko.observable();

    params: KnockoutObservable<any> = ko.observable({});

    constructor(params: any) {
      super();
      const vm = this;

      vm.params({
        settingCategory: 1,
        settingId: null,
        settingCode: null,
        settingName: null
      });

      if (!_.isNil(params)) {
        vm.oldCode(params.code);
        vm.oldName(params.name);
        vm.params().settingId = params.settingId;
        vm.params().settingName = params.code;
        vm.params().settingName = params.name;
        vm.params().settingCategory = params.settingCategory;
      }

    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;

      $('#KWR004_C23').focus();
    }

    proceedClone() {
      const vm = this;
      $('.attendance-code-name').trigger('validate');
      if (nts.uk.ui.errors.hasError()) return;

      vm.$blockui('show');

      vm.params().settingCode = vm.newCode();
      vm.params().settingName = vm.newName();

      vm.$ajax(PATH.cloneSettingClassification, vm.params()).done((result) => {
        vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {                  
          vm.$window.close({ code: vm.newCode(), name: vm.newName() });
          vm.$blockui('hide');
        });
      }).fail((err) => {
        vm.showError(err.messageId);
        vm.$blockui('hide');
      }).always(() => vm.$blockui('hide'));
    }

    showError(messageId: string) {
      const vm = this;
      switch (messageId) {
        case 'Msg_1898':
          vm.$dialog.error({ messageId: messageId }).then(() => {
            $('#btnClose').focus();
          });
          break;

        case 'Msg_1753':
          $('#KWR004_C23').ntsError('set', { messageId: messageId });
          $('#KWR004_C23').focus();
          break;
      }
    }

    cancel() {
      const vm = this; 
      vm.$window.close(null);
    }    

    checkErrors() {
      const vm = this;
      //データが先に削除された
      vm.$dialog.error({ messageId: 'Msg_1898' }).then(() => {
        $('#closeDialog').focus();
        vm.$blockui('hide');
      });

      //コードの重複
      vm.$dialog.error({ messageId: 'Msg_1859' }).then(() => {
        $('#KWR003_C23').focus();
        vm.$blockui('hide');
      });

      return false;
    }
  }
}