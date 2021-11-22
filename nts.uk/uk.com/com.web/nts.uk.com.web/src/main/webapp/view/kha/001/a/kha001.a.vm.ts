/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.kha001.a {
  
  const PATH = {
    getSetting: 'at/screen/kha001/query/get-data',
    registerSetting: 'screen/at/schedule/basicsetting/register'
  };

  @bean()
  class ViewModel extends ko.ViewModel {

    switchItems: KnockoutObservableArray<any>;
    itemListCbb: KnockoutObservableArray<any>= ko.observableArray([]);
    isUse: KnockoutObservable<number> = ko.observable(0);
    selectedCode: KnockoutObservable<number> = ko.observable(0);
    langId: KnockoutObservable<string> = ko.observable('ja');

    constructor(params: any) {
      super();
      const vm = this;
      vm.switchItems = ko.observableArray([
        { code: 1, name: vm.$i18n('KHA001_10') },
        { code: 0, name: vm.$i18n('KHA001_11') }
      ]);

      vm.getSetting();
    }
    mounted() {
      const vm = this;
    }


    getSetting() {
      const vm = this;
      vm.$blockui('show');
      vm.$ajax('at',PATH.getSetting).done((data) => {
        if( data ) {
          vm.isUse(data.isUsed);
          vm.itemListCbb(data.maxNumberOfSupportOfDay);
          vm.selectedCode(data.maxNumberOfSupportOfDay.value);
        }
      }).fail(error => {
        vm.$dialog.error(error);
      }).always(() => {
        $(".switchButton-wrapper")[0].focus();
        vm.$blockui('hide');
      });
    }

    exportExcel(): void {
      var self = this;
      nts.uk.ui.block.grayout();
      let langId = self.langId();
      self.saveAsExcel(langId).done(function() {
      }).fail(function(error) {
          nts.uk.ui.dialog.alertError({ messageId: error.messageId });
      }).always(function() {
          nts.uk.ui.block.clear();
      });
    }

    saveAsExcel(languageId: string): JQueryPromise<any> {
      let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
      let domainType = "KHA001";
      if (program.length > 1){
          program.shift();
          domainType = domainType + program.join(" ");
      }
      let _params = {domainId: "SupportOperationSettingExport",
          domainType: domainType,
          languageId: languageId,
          reportType: 0};
      return nts.uk.request.exportFile('/masterlist/report/print', _params);
    }

    saveData() {
      const vm = this;

    }   
  }
}