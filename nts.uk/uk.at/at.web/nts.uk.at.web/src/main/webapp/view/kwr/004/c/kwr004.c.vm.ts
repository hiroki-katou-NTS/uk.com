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
        settingCategory : 1,
        settingId : null,
        settingCode :null,
        settingName : null
      });

      vm.$window.storage(KWR004_C_INPUT).then((data) => {
        if (!_.isNil(data)) {
          vm.oldCode(data.code);
          vm.oldName(data.name);

          vm.params().settingId = data.settingId;
          vm.params().settingName = data.code;
          vm.params().settingName = data.name;
          vm.params().settingCategory = data.settingCategory;
        }
      });

    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;

      $('#KWR004_C23').focus();
    }

    proceed() {
      const vm = this;
      vm.$blockui('show');

      vm.params().code = vm.newCode();
      vm.params().name = vm.newName();

      vm.$ajax(PATH.cloneSettingClassification, vm.params()).done(() => {
        vm.$dialog.info( { messageId: 'Msg_15' }).then(() => {
          vm.$window.storage(KWR004_C_OUTPUT, { code: vm.newCode(), name: vm.newName()});
          vm.$blockui('hide');
          vm.$window.close();
        });
       
      }).fail((err) => {
        vm.$dialog.error( { messageId: err.messageId }).then(() => {
          vm.$blockui('hide');
        });
      }).always( () => vm.$blockui('hide'));
     
    }

    cancel() {
      const vm = this;
      vm.$window.storage(KWR004_C_OUTPUT, null);
      vm.$window.close();
    }

    /**
     * Clones setting classification
     */
    cloneSettingClassification() {
      const vm = this;

      vm.$blockui('show');

      vm.$ajax(PATHS.cloneSettingClassification, vm.params())
        .done((response) => {
          let hasErrors = vm.checkErrors();
          if (hasErrors) {
            vm.$window.storage(KWR004_C_OUTPUT, { code: vm.newCode(), name: vm.newName() });
            vm.$blockui('hide');
            vm.$window.close();
          }
        })
        .fail((error) => {
          vm.$blockui('hide');
        })
        .always(() => vm.$blockui('hide'));
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