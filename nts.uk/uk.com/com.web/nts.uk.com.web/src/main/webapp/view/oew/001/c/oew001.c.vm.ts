/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.oew001.c {

  import model = oew001.share.model;

  const API = {
    getEquipmentInfoList: "com/screen/oew001/getEquipmentInfoList",
    export: "com/file/equipment/data/report"
  };

  @bean()
  export class ScreenModel extends ko.ViewModel {
    
    selectedEquipmentClsCode: KnockoutObservable<string> = ko.observable("");
    equipmentClsName: KnockoutObservable<string> = ko.observable("");
    selectedEquipmentInfoCode: KnockoutObservable<string> = ko.observable("");
    yearMonth: KnockoutObservable<string> = ko.observable("");
    isSelectAll: KnockoutObservable<boolean> = ko.observable(false);

    // dto
    equipmentClassification: KnockoutObservable<model.EquipmentClassificationDto> = ko.observable(null);
    equipmentInformationList: KnockoutObservableArray<model.EquipmentInformationDto> = ko.observableArray([]);
    itemSettings: KnockoutObservableArray<model.EquipmentUsageRecordItemSettingDto> = ko.observableArray([]);
    formatSetting: KnockoutObservable<model.EquipmentPerformInputFormatSettingDto> = ko.observable(null);

    created(param: any) {
      const vm = this;

      vm.selectedEquipmentClsCode.subscribe(value => vm.getEquipmentInfoList(param.equipmentCode));

      vm.selectedEquipmentClsCode(param.equipmentClsCode);
      vm.equipmentClsName(param.equipmentClsName);
      vm.yearMonth(param.yearMonth);  
      vm.formatSetting(param.formatSetting);
      vm.itemSettings(param.itemSettings);
    }

    mounted() {
      $("#C4_1").focus();
    }

    private getEquipmentInfoList(equipmentInfoCode?: string): JQueryPromise<any> {
      const vm = this;
      const param = {
        equipmentClsCode: vm.selectedEquipmentClsCode(),
        isInput: false
      };
      return vm.$ajax(API.getEquipmentInfoList, param).then(result => {
        vm.isSelectAll(param.equipmentClsCode === model.constants.SELECT_ALL_CODE);
        if (vm.isSelectAll()) {
          vm.equipmentInformationList([]);
        } else {
          vm.equipmentInformationList(result);
          vm.equipmentInformationList.unshift(new model.EquipmentInformationDto({
            code: model.constants.SELECT_ALL_CODE,
            name: vm.$i18n("OEW001_70")
          }));
        }
        vm.selectedEquipmentInfoCode(equipmentInfoCode);
      });
    }

    private exportReport(printType: number): JQueryPromise<any> {
      const vm = this;
      const param: EquipmentDataQuery = {
        printType: printType,
        ym: Number(vm.yearMonth()),
        formatSetting: vm.formatSetting(),
        itemSettings: vm.itemSettings()
      }
      if (vm.selectedEquipmentClsCode() !== model.constants.SELECT_ALL_CODE) {
        param.equipmentClsCode = vm.selectedEquipmentClsCode();
      }
      if (vm.selectedEquipmentInfoCode() !== model.constants.SELECT_ALL_CODE) {
        param.equipmentCode = vm.selectedEquipmentInfoCode();
      }
      return nts.uk.request.exportFile(API.export, param).fail(err => vm.$dialog.error({ messageId: err.messageId }));
    }

    public openDialogD() {
      const vm = this;
      const param = {
        equipmentClsCode: vm.selectedEquipmentClsCode(),
        isOpenFromA: false
      };
      vm.$window.modal("/view/oew/001/d/index.xhtml", param)
      .then(result => {
        if (!!result) {
          vm.$blockui("grayout");
          vm.selectedEquipmentClsCode(result.code);
          vm.equipmentClsName(result.name);

          vm.getEquipmentInfoList().always(() => vm.$blockui("clear"));
          vm.$nextTick(() => $("#C2_2").focus());
        }
      });
    }

    public processExportExcel(): void {
      const vm = this;
      vm.$blockui("grayout");
      vm.exportReport(model.enums.PrintType.EXCEL).always(() => vm.$blockui("clear"));
    }

    public processExportCsv(): void {
      const vm = this;
      vm.$blockui("grayout");
      vm.exportReport(model.enums.PrintType.CSV).always(() => vm.$blockui("clear"));
    }

    public processCancel(): void {
      const vm = this;
      vm.$window.close();
    }
  }

  class EquipmentDataQuery {

    // Optional<設備分類コード>
    equipmentClsCode?: string;
    
    // Optional<設備コード>
    equipmentCode?: string;
    
    // 年月
    ym: number;

    // 設備利用実績の項目設定<List> 
    itemSettings: model.EquipmentUsageRecordItemSettingDto[];
    
    // 設備の実績入力フォーマット設定<List>
    formatSetting: model.EquipmentPerformInputFormatSettingDto;
    
    printType: number;
  }
}
