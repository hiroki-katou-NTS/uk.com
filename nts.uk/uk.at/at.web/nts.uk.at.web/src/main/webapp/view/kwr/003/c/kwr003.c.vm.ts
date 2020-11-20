/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kwr003.c {
  //import common = nts.uk.at.view.kwr003.common; 
  const KWR003_OUTPUT = 'KWR003_OUTPUT';
  const KWR003_B13 = 'KWR003_B_DATA';
  const KWR003_C_INPUT = 'KWR003_C_DATA';
  const KWR003_C_OUTPUT = 'KWR003_C_RETURN';

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

      vm.$window.storage(KWR003_C_INPUT).then((data) => {
        if (!_.isNil(data)) {
          vm.oldCode(data.code);
          vm.oldName(data.name);          
          vm.params().settingId = data.settingId;
          vm.params().settingCode = data.code;
          vm.params().settingName = data.name;
          vm.params().settingCategory = data.settingCategory;
        }
      });
    }

    mounted() {
      const vm = this;

      $('#KWR003_C23').focus();
    }

    proceed() {
      const vm = this;      
      vm.cloneSettingClassification();
    }

    cancel() {
      const vm = this;
      vm.$window.storage(KWR003_C_OUTPUT, null);
      vm.$window.close();
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
          vm.$window.storage(KWR003_C_OUTPUT, { code: vm.newCode(), name: vm.newName() });          
          vm.$blockui('hide');
          vm.$window.close();
        })
        .fail((error) => {      
          //データが先に削除された - 1903    
          //コードの重複 - Msg_1753
          $('#KWR003_C23').ntsError('set', { messageId: error.messageId });   
         /*  vm.$dialog.error({ messageId: error.messageId }).then(() => {
            $('#closeDialog').focus();
            vm.$blockui('hide');
          }); */
        })
        .always(() => vm.$blockui('hide'));
    }
  }
}