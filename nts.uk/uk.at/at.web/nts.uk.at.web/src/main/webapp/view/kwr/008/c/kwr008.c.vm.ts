/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.kwr008.c {
  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;

  const API = {
      findCopyAnnualWorkSchedule: 'at/function/annualworkschedule/findAnnualWorkSchedule',
      executeCopy: 'at/function/annualworkschedule/executeCopy',
  }

  @bean()
  export class KWR008CViewModel extends ko.ViewModel {
    selectedCode: KnockoutObservable<string>;
    selectedName: KnockoutObservable<string>;
    isEnable: KnockoutObservable<boolean>;
    isEditable: KnockoutObservable<boolean>;
    duplicateCode: KnockoutObservable<string>;
    duplicateName: KnockoutObservable<string>;

    layoutId: KnockoutObservable<string>;
    settingType: KnockoutObservable<string>;;

    // アルゴリズム「起動処理」を実行する
    created() {
      const vm = this;
      vm.selectedCode = ko.observable('');
      vm.isEnable = ko.observable(true);
      vm.duplicateCode = ko.observable('');
      vm.duplicateName = ko.observable('');
      vm.isEditable = ko.observable(true);

      vm.layoutId = ko.observable('');
      vm.settingType = ko.observable('');

      // get param from B screen 
      const kwr008b = getShared("KWR008CParam");
      if (kwr008b) {
        vm.selectedCode = ko.observable(kwr008b.selectCode);
        vm.selectedName  = ko.observable(kwr008b.selectName);
        vm.layoutId = ko.observable(kwr008b.layoutId);
        vm.settingType = ko.observable(kwr008b.settingType);
      }
    }

    //アルゴリズム「決定ボタン押下時処理」を実行する
    executeCopy() {
      let vm = this;
            $('.save-error').ntsError('check');
            if (!vm.duplicateCode() || !vm.duplicateName() || nts.uk.ui.errors.hasError()) {
                return;
            }

            let dataCopy = new AnnualWorkScheduleDuplicateDto({
                code: vm.selectedCode(),
                name: vm.selectedName(),
                duplicateCode: vm.duplicateCode(),
                duplicateName: vm.duplicateName(),
                selectedType: vm.settingType(),
                layoutId: vm.layoutId(),
            });

            vm.$blockui('show');

            vm.$ajax('at', API.executeCopy, dataCopy).then(() => {
                vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
                    // Set shared param to share with screen B
                    setShared('KWR008CDATA', {
                        code: vm.duplicateCode(),
                        name: vm.duplicateName(),
                        layoutId: vm.layoutId(),
                        settingType: vm.settingType()
                    });
                    // Close screen
                    vm.$window.close()
                });
            })
            .fail(err => vm.$dialog.alert({ messageId: err.messageId }))
            .always(() => vm.$blockui('hide'));
    }

    closeDialog() {
      let vm = this;
      vm.$window.close();
    }
  }
  
  class AnnualWorkScheduleDuplicateDto {
      code: string;
      name: string;
      duplicateCode: string;
      duplicateName: string;
      selectedType: string;
      layoutId: string;

      constructor(init?: Partial<AnnualWorkScheduleDuplicateDto>) {
          $.extend(this, init);
      }
  }

  class DataInfoReturnDto {
    code: string;
    name: string;
  }
}