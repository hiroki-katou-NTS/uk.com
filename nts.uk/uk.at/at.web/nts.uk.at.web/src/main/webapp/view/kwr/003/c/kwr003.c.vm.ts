/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr003.c {
  //import common = nts.uk.at.view.kwr003.common; 
  const KWR003_OUTPUT = 'KWR003_OUTPUT';
  const KWR003_B13 = 'KWR003_B_DATA';
  const KWR003_C_INPUT = 'KWR003_C_DATA';
  const KWR003_C_OUTPUT = 'KWR003_C_RETURN';

  const PATHS = {
    cloneSettingClassification: ''
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
        settingListItemDetails: [], //設定区分
        sourceCode: null, //複製元の設定ID
        code: null,  //複製先_コード
        name: null //複製先_名称
      });

      vm.$window.storage(KWR003_C_INPUT).then((data) => {
        if (!_.isNil(data)) {
          vm.oldCode(data.code);
          vm.oldName(data.name);

          let cloneCode: any = !_.isNil(data.lastCode) ? parseInt(data.lastCode) + 1 : 1;
          cloneCode = _.padStart(cloneCode, 2, '0');
          vm.newCode(cloneCode);

          let cloneName: any = data.name;
          cloneName = cloneName.substring(0, cloneName.indexOf('_' + data.code));
          cloneName = cloneName + '_' + cloneCode;
          vm.newName(cloneName);

          vm.params().sourceCode = data.code;
          vm.params().code = data.cloneCode;
          vm.params().name = data.cloneName;
          vm.params().settingListItemDetails = data.settingListItemDetails;
        }
      });
    }

    created(params: any) {
      let vm = this;
    }

    mounted() {
      let vm = this;

      $('#KWR003_C23').focus();
    }

    proceed() {
      let vm = this;

      //vm.$window.storage(KWR003_C_OUTPUT, { code: vm.newCode(), name: vm.newName() });
      //vm.$window.close();
      vm.cloneSettingClassification();
    }

    cancel() {
      let vm = this;
      vm.$window.storage(KWR003_C_OUTPUT, null);
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
            vm.$window.storage(KWR003_C_OUTPUT, { code: vm.newCode(), name: vm.newName() });
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
      vm.$dialog.error({ messageId: 'Msg_1903' }).then(() => {
        $('#closeDialog').focus();
        vm.$blockui('hide');
      });

      //コードの重複
      vm.$dialog.error({ messageId: 'Msg_1753' }).then(() => {
        $('#KWR003_C23').focus();
        vm.$blockui('hide');
      });

      return false;
    }
  }
}