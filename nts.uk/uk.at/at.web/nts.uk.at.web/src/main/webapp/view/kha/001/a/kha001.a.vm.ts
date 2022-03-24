/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
///<reference path="../../../../lib/generic/jquery/jquery.d.ts"/>
module nts.uk.com.view.kha001.a {
  
  const PATH = {
    getSetting: 'at/screen/kha001/query/get-data',
    registerSetting: 'at/share/supportOperationSetting/updateSupportOperationSetting'
  };

  @bean()
  class ViewModel extends ko.ViewModel {

    switchItems: KnockoutObservableArray<any>;
    itemListCbb: KnockoutObservableArray<any>= ko.observableArray([]);
    isUse: KnockoutObservable<number> = ko.observable(0);
    selectedCode: KnockoutObservable<number> = ko.observable(1);
    langId: KnockoutObservable<string> = ko.observable('ja');

    constructor() {
      super();
      const vm = this;
      vm.switchItems = ko.observableArray([
        { code: 1, name: vm.$i18n('KHA001_10') },
        { code: 0, name: vm.$i18n('KHA001_11') }
      ]);
        vm.itemListCbb = ko.observableArray([
            { code: 1, name: vm.$i18n('KHA001_16') },
            { code: 2, name: vm.$i18n('KHA001_17') },
            { code: 3, name: vm.$i18n('KHA001_18') },
            { code: 4, name: vm.$i18n('KHA001_19') },
            { code: 5, name: vm.$i18n('KHA001_20') },
            { code: 6, name: vm.$i18n('KHA001_21') },
            { code: 7, name: vm.$i18n('KHA001_22') },
            { code: 8, name: vm.$i18n('KHA001_23') },
            { code: 9, name: vm.$i18n('KHA001_24') },
            { code: 10, name: vm.$i18n('KHA001_25') },
            { code: 11, name: vm.$i18n('KHA001_26') },
            { code: 12, name: vm.$i18n('KHA001_27') },
            { code: 13, name: vm.$i18n('KHA001_28') },
            { code: 14, name: vm.$i18n('KHA001_29') },
            { code: 15, name: vm.$i18n('KHA001_30') },
            { code: 16, name: vm.$i18n('KHA001_31') },
            { code: 17, name: vm.$i18n('KHA001_32') },
            { code: 18, name: vm.$i18n('KHA001_33') },
            { code: 19, name: vm.$i18n('KHA001_34') },
            { code: 20, name: vm.$i18n('KHA001_35') }
        ]);
    }
    mounted() {
      const vm = this;
      vm.getSetting();
    }

    getSetting() {
      const vm = this;
      vm.$blockui('show');
      vm.$ajax('at',PATH.getSetting).done((data) => {
          if (!data) {
              return;
          }
          vm.isUse(data.used ? 1 : 0);
          vm.selectedCode(data.maxNumberOfSupportOfDay);
      }).fail(error => {
        vm.$dialog.error(error);
      }).always(() => {
        $('#KHA001_3').focus();
        vm.$blockui('hide');
      });
    }

    exportExcel(): void {
      var self = this;
      self.$blockui("grayout");
      let langId = self.langId();
      self.saveAsExcel(langId).done(function() {
      }).fail(function(error) {
          self.$dialog.error({ messageId: error.messageId });
      }).always(function() {
          self.$blockui("clear")
      });
    }

    saveAsExcel(languageId: string): JQueryPromise<any> {
      let program = __viewContext.program.programName;
      let domainType = "KHA001";
      if (program){
          domainType = domainType+"_" + program;
      }
      let _params = {domainId: "SupportOperationSettingExport",
          domainType: domainType,
          languageId: languageId,
          reportType: 0};
      return nts.uk.request.exportFile('/masterlist/report/print', _params);
    }

    saveData() {
      const vm = this;
        vm.$validate(".nts-input .ntsControl").then((valid: boolean) => {
            if (!valid){
                return;
            }
            const dataRegister = {
                isUsed: vm.isUse()?1:0,
                maxNumberOfSupportOfDay: vm.selectedCode()
            }
            vm.$blockui("invisible");
            vm.$ajax(PATH.registerSetting, dataRegister).done(() => {
                vm.$dialog.info({ messageId: "Msg_15" }).done(()=>{
                    vm.getSetting();
                });
            })
            .fail((err: any)=>{
                vm.$dialog.error({ messageId: err.messageId });
            })
            .always(() => vm.$blockui("clear"));
        });
    }
  }
}