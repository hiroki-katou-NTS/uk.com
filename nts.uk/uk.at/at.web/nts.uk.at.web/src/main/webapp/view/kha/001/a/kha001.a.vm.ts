/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
///<reference path="../../../../lib/generic/jquery/jquery.d.ts"/>
module nts.uk.com.view.kha001.a {
  const PATH = {
    getSetting: "at/screen/kha001/query/get-data",
    registerSetting:
      "at/share/supportOperationSetting/updateSupportOperationSetting",
    registerJudgmentCriteria: "at/ctx/record/stampmanagement/register-judgment-criteria",
    registerTravelTime: "at/ctx/record/dailyattendance/register-travel-time"
  };

  const ENUM_USING = {
    YES: 1,
    NO: 0
  };

  @bean()
  class ViewModel extends ko.ViewModel {
    switchItems: KnockoutObservableArray<any>;
    itemListCbb: KnockoutObservableArray<any> = ko.observableArray([]);
    itemListEnum: KnockoutObservableArray<any> = ko.observableArray([]);
    isUse: KnockoutObservable<number> = ko.observable(0);
    selectedCode: KnockoutObservable<number> = ko.observable(1);
    accountingOfMoveTime: KnockoutObservable<number> = ko.observable(0);
    langId: KnockoutObservable<string> = ko.observable("ja");
    sameStampRanceInMinutes: KnockoutObservable<Number> = ko.observable(1);
    constructor() {
      super();
      const vm = this;
      vm.switchItems = ko.observableArray([
        { code: 1, name: vm.$i18n("KHA001_10") },
        { code: 0, name: vm.$i18n("KHA001_11") },
      ]);
      vm.itemListCbb = ko.observableArray([
        { code: 1, name: vm.$i18n("KHA001_16") },
        { code: 2, name: vm.$i18n("KHA001_17") },
        { code: 3, name: vm.$i18n("KHA001_18") },
        { code: 4, name: vm.$i18n("KHA001_19") },
        { code: 5, name: vm.$i18n("KHA001_20") },
        { code: 6, name: vm.$i18n("KHA001_21") },
        { code: 7, name: vm.$i18n("KHA001_22") },
        { code: 8, name: vm.$i18n("KHA001_23") },
        { code: 9, name: vm.$i18n("KHA001_24") },
        { code: 10, name: vm.$i18n("KHA001_25") },
        { code: 11, name: vm.$i18n("KHA001_26") },
        { code: 12, name: vm.$i18n("KHA001_27") },
        { code: 13, name: vm.$i18n("KHA001_28") },
        { code: 14, name: vm.$i18n("KHA001_29") },
        { code: 15, name: vm.$i18n("KHA001_30") },
        { code: 16, name: vm.$i18n("KHA001_31") },
        { code: 17, name: vm.$i18n("KHA001_32") },
        { code: 18, name: vm.$i18n("KHA001_33") },
        { code: 19, name: vm.$i18n("KHA001_34") },
        { code: 20, name: vm.$i18n("KHA001_35") },
      ]);
      vm.itemListEnum = ko.observableArray([
        { code: 0, name: vm.$i18n("KHA001_13") },
        { code: 1, name: vm.$i18n("KHA001_14") },
        { code: 2, name: vm.$i18n("KHA001_15") }
      ]);
    }

    mounted() {
      const vm = this;
      vm.getSetting();

      const inputMinutes = $(".inp-same-stamp-rance-in-minutes");
      inputMinutes.blur(() => {
        if (inputMinutes.val() < 1) {
          inputMinutes.val(1);
          vm.sameStampRanceInMinutes(1);
        }
        if (inputMinutes.val() > 60) {
          inputMinutes.val(60);
          vm.sameStampRanceInMinutes(60);
        }
      });
    }

    getSetting() {
      const vm = this;
      vm.$blockui("show");
      vm.$ajax("at", PATH.getSetting)
        .done((data) => {
          if (!data) {
            return;
          }
          vm.isUse(data.supportOperationSettingDto.used ? 1 : 0);
          vm.selectedCode(data.supportOperationSettingDto.maxNumberOfSupportOfDay);
          vm.sameStampRanceInMinutes(data.judgmentCriteriaSameStampOfSupportDto.sameStampRanceInMinutes || 1);
          vm.accountingOfMoveTime(data.supportWorkSettingDto.accountingOfMoveTime || 0);
        })
        .fail((error) => {
          vm.$dialog.error(error);
        })
        .always(() => {
          $("#KHA001_3").focus();
          vm.$blockui("hide");
        });
    }

    exportExcel(): void {
      var self = this;
      self.$blockui("grayout");
      let langId = self.langId();
      self
        .saveAsExcel(langId)
        .done(function () {})
        .fail(function (error) {
          self.$dialog.error({ messageId: error.messageId });
        })
        .always(function () {
          self.$blockui("clear");
        });
    }

    saveAsExcel(languageId: string): JQueryPromise<any> {
      let program = __viewContext.program.programName;
      let domainType = "KHA001";
      if (program) {
        domainType = `${domainType}_${program}`;
      }
      let _params = {
        domainId: "SupportOperationSettingExport",
        domainType: domainType,
        languageId: languageId,
        reportType: 0,
      };
      return nts.uk.request.exportFile("/masterlist/report/print", _params);
    }

    saveData() {
      const vm = this;
      vm.$validate(".nts-input .ntsControl").then((valid: boolean) => {
        if (!valid) {
          return;
        }
        const dataSetting = {
          isUsed: vm.isUse() ? ENUM_USING.YES : ENUM_USING.NO,
          maxNumberOfSupportOfDay: vm.selectedCode(),
        };
        const dataJudgmentCriteria = {
          sameStampRanceInMinutes: vm.sameStampRanceInMinutes()
        }
        const dataTraveTime = {
          isUse: vm.isUse() ? ENUM_USING.YES : ENUM_USING.NO,
          accountingOfMoveTime: vm.accountingOfMoveTime()
        }
        vm.$blockui("invisible");
        $.when(
          vm.$ajax(PATH.registerSetting, dataSetting),
          vm.$ajax(PATH.registerJudgmentCriteria, dataJudgmentCriteria),
          vm.$ajax(PATH.registerTravelTime, dataTraveTime),
        )
          .done(() => {
            vm.$dialog.info({ messageId: "Msg_15" }).done(() => {
              vm.getSetting();
            });
          })
          .fail((err: any) => {
            vm.$dialog.error({ messageId: err.messageId });
          })
          .always(() => vm.$blockui("clear"));
      });
    }
  }
}
